package kz.atasuai.delivery.common

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

object JsonHelper {
    val gson: Gson = Gson()

    fun <T> serializeObject(obj: T): String {
        return gson.toJson(obj)
    }

    inline fun <reified T> deserializeObject(json: String): T? {
        return try {
            gson.fromJson(json, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    inline fun <reified T> convertAnyToObject(anyObject: Any?): T? {
        if (anyObject == null) return null
        val json = gson.toJson(anyObject)
        return deserializeObject(json)
    }
    fun toJson(obj: Any): String {
        return try {
            gson.toJson(obj)
        } catch (e: Exception) {
            "[]"
        }
    }
    inline fun <reified T> parseJsonArray(json: String): List<T> {
        return try {
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson<List<T>>(json, type) ?: emptyList()
        } catch (e: JsonSyntaxException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    inline fun <reified T> parseJson(json: String): T? {
        return try {
            gson.fromJson(json, T::class.java)
        } catch (e: JsonSyntaxException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}