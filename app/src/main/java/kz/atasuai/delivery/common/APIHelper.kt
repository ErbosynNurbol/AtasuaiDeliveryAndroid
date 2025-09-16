
package kz.atasuai.delivery.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.market.models.AjaxMsgModel
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

object APIHelper {
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val url = "/api" + originalRequest.url.toString().removePrefix(AtasuaiApp.siteUrl)
                val method = originalRequest.method
                val androidVersion = android.os.Build.VERSION.RELEASE

                val request = originalRequest.newBuilder()
                    .addHeader("User-Agent", "AtasuaiApp/1.0")
                    .addHeader("language", AtasuaiApp.currentLanguage.value.culture ?: "enw")
                    .addHeader("X-Client-Platform", "Android-$androidVersion")
                    .addHeader("Authorization", "Bearer ${AtasuaiApp.currentToken}")
                    .addHeader("X-Sig", SigSigner.sig(url, method))
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    suspend fun queryAsync(
        url: String,
        method: String,
        headers: Map<String, String> = emptyMap(),
        paraDic: Map<String, Any>? = null,
        filePart: MultipartBody.Part? = null,
        requestBody: RequestBody? = null,
    ): Result<AjaxMsgModel> {
        return withContext(Dispatchers.IO) {
            try {
                val fullUrl = "${AtasuaiApp.siteUrl}$url"
                val request = buildRequest(fullUrl, method, headers, paraDic, filePart, requestBody)
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        val result = JsonHelper.deserializeObject<AjaxMsgModel>(responseBody)
                        Result.success(result ?: AjaxMsgModel("Error", "Failed to parse response"))
                    } else {
                        val errorMsg = responseBody ?: "HTTP error ${response.code}"
                        Result.failure(IOException(errorMsg))
                    }
                }
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }
    }

    private fun buildRequest(
        url: String,
        method: String,
        headers: Map<String, String>,
        paraDic: Map<String, Any>?,
        filePart: MultipartBody.Part? = null,
        requestBody: RequestBody? = null
    ): Request {
        val builder = Request.Builder().url(url)
        headers.forEach { (key, value) ->
            builder.addHeader(key, value)
        }

        when (method.uppercase()) {
            "GET" -> builder.get()
            "POST", "PUT" -> {
                val body = if (requestBody != null) {
                    requestBody
                } else if (filePart != null) {
                    MultipartBody.Builder().setType(MultipartBody.FORM).apply {
                        addPart(filePart)
                        paraDic?.forEach { (key, value) ->
                            addFormDataPart(key, value.toString())
                        }
                    }.build()
                } else if (paraDic != null) {
                    // 使用 FormBody 构建表单数据而不是 JSON
                    FormBody.Builder().apply {
                        paraDic.forEach { (key, value) ->
                            add(key, value.toString())
                        }
                    }.build()
                } else {
                    FormBody.Builder().build() // 空表单
                }

                if (method.uppercase() == "POST") {
                    builder.post(body)
                } else {
                    builder.put(body)
                }
            }
            "DELETE" -> {
                val body = if (paraDic != null) {
                    // 对于 DELETE 请求也使用表单数据
                    FormBody.Builder().apply {
                        paraDic.forEach { (key, value) ->
                            add(key, value.toString())
                        }
                    }.build()
                } else {
                    FormBody.Builder().build() // 空表单
                }
                builder.delete(body)
            }
            else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
        }

        return builder.build()
    }
}
