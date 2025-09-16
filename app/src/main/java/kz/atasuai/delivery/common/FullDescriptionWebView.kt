package kz.atasuai.delivery.common

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.theme.AtasuaiTheme


@Composable
fun WebViewWithHeaders(
    url: String,
//    extraHeaders: Map<String, String>,
    modifier: Modifier = Modifier,
    context: Context
) {
    val themeManager = remember { ThemeManager(context) }
    val themeMode by themeManager.themeMode.collectAsState()
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }
    val webViewRef = remember { mutableStateOf<WebView?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val backgroundColor = AtasuaiTheme.colors.background

    LaunchedEffect(darkTheme) {
        webViewRef.value?.evaluateJavascript("window.toggleTheme();", null)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewRef.value = this

                    setBackgroundColor(android.graphics.Color.TRANSPARENT)

                    if (darkTheme) {
                        settings.domStorageEnabled = true
                        settings.javaScriptEnabled = true

                        evaluateJavascript("""
                            (function() {
                                document.documentElement.style.backgroundColor = 'transparent';
                                document.body.style.backgroundColor = 'transparent';
                                
                                var style = document.createElement('style');
                                style.textContent = 'html, body { background-color: transparent !important; }';
                                document.head.appendChild(style);
                            })();
                        """.trimIndent(), null)
                    }

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            isLoading = true

                            if (darkTheme) {
                                view?.evaluateJavascript("""
                                    document.documentElement.style.backgroundColor = 'transparent';
                                    document.body.style.backgroundColor = 'transparent';
                                """.trimIndent(), null)
                            }
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)

                            if (darkTheme) {
                                view?.evaluateJavascript("""
                                    document.documentElement.style.backgroundColor = 'transparent';
                                    document.body.style.backgroundColor = 'transparent';
                                """.trimIndent(), null)
                            }

                            Handler(Looper.getMainLooper()).postDelayed({
                                isLoading = false
                            }, 100)
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            super.onReceivedError(view, request, error)
                            isLoading = false
                        }
                    }

                    loadUrl(url)
                }
            },
            update = { webView ->
                if (webView.url != url) {
                    isLoading = true
                    webView.loadUrl(url)
                }
            }
        )

        if (isLoading) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.White), // 添加背景色确保遮罩效果
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.app_dark_logo),
                    contentDescription = "logo",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}