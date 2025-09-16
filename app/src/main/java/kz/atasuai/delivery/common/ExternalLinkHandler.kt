@file:Suppress("DEPRECATION")
package kz.atasuai.delivery.common

import android.content.Context
import android.content.Intent
import android.net.Uri

object ExternalLinkHandler {

    // 需要外跳的 host 域名
    private val deepLinkHosts = setOf(
        // Telegram
        "t.me", "telegram.me",
        // WhatsApp
        "wa.me", "api.whatsapp.com",
        // Facebook & Messenger
        "facebook.com", "m.facebook.com", "fb.me",
        // Instagram
        "instagram.com", "www.instagram.com",
        // Twitter / X
        "twitter.com", "x.com",
        // TikTok
        "tiktok.com", "www.tiktok.com"
    )

    // 常见 Scheme
    private val knownSchemes = setOf(
        // IM / 社交
        "tg", "telegram",
        "whatsapp",
        "fb", "fb-messenger", "facebook",
        "instagram",
        "twitter", "x",
        "viber", "skype", "line",
        "vk", "snapchat",
        "snssdk1233", // TikTok 部分版本
        // 通信
        "tel", "mailto", "sms", "smsto", "mms", "mmsto",
        // 其它
        "geo", "market", "intent"
    )

    fun tryHandle(context: Context, uri: Uri): Boolean {
        val scheme = uri.scheme?.lowercase() ?: return false
        return when (scheme) {
            "http", "https" -> handleHttpOrHttps(context, uri)
            else -> handleCustomScheme(context, uri, scheme)
        }
    }

    private fun handleHttpOrHttps(context: Context, uri: Uri): Boolean {
        val host = (uri.host ?: "").lowercase()
        if (host in deepLinkHosts) {
            return startActivitySafe(context, Intent(Intent.ACTION_VIEW, uri))
        }
        return false
    }

    private fun handleCustomScheme(context: Context, uri: Uri, scheme: String): Boolean {
        if (scheme !in knownSchemes) {
            return startActivitySafe(context, Intent(Intent.ACTION_VIEW, uri), swallowFail = true)
        }

        return when (scheme) {
            "tg", "telegram",
            "whatsapp",
            "fb", "fb-messenger", "facebook",
            "instagram",
            "twitter", "x",
            "viber", "skype", "line",
            "vk", "snapchat",
            "snssdk1233" -> openWithView(context, uri)

            "tel"        -> startActivitySafe(context, Intent(Intent.ACTION_DIAL, uri))
            "mailto"     -> startActivitySafe(context, Intent(Intent.ACTION_SENDTO, uri))
            "sms", "smsto",
            "mms", "mmsto" -> startActivitySafe(context, Intent(Intent.ACTION_SENDTO, uri))
            "geo"        -> openWithView(context, uri)
            "market"     -> openWithView(context, uri)

            "intent"     -> handleIntentUri(context, uri)

            else         -> openWithView(context, uri)
        }
    }

    private fun openWithView(context: Context, uri: Uri): Boolean {
        return startActivitySafe(context, Intent(Intent.ACTION_VIEW, uri))
    }

    private fun handleIntentUri(context: Context, intentUri: Uri): Boolean {
        return try {
            val intent = Intent.parseUri(intentUri.toString(), Intent.URI_INTENT_SCHEME)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.component = null
            if (startActivitySafe(context, intent)) {
                true
            } else {
                val pkg = intent.`package`
                if (!pkg.isNullOrEmpty()) {
                    openMarketForPackage(context, pkg)
                } else {
                    true
                }
            }
        } catch (_: Exception) {
            true
        }
    }

    private fun openMarketForPackage(context: Context, pkg: String): Boolean {
        val marketOk = startActivitySafe(
            context,
            Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkg")),
            swallowFail = true
        )
        if (marketOk) return true
        return startActivitySafe(
            context,
            Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pkg")),
            swallowFail = true
        )
    }

    private fun startActivitySafe(
        context: Context,
        intent: Intent,
        swallowFail: Boolean = true
    ): Boolean {
        return try {
            context.startActivity(intent)
            true
        } catch (_: Exception) {
            swallowFail
        }
    }
}