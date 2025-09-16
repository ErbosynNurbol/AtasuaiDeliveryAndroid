package kz.atasuai.delivery.common

import java.util.Locale
import java.util.UUID

object SigSigner {
    @Volatile var secretKey: String = "N3234DQatsdfbVXs${'$'}dffd=234zHrT&Zyutren%y!5654s5kpYGh84mSxfFw^^S24${'$'}1gC%W~PAM6a?K#"

    // 等价 TS: const apiSig.nonce —— 整个进程生命周期内固定一次
    val nonce: String by lazy { UUID.randomUUID().toString().substringBefore('-') }

    /** 生成 X-Sig：encrypt(JSON.stringify({ url, method, nonce, ts, clientPlatform })) */
    fun sig(url: String, method: String): String {
        val upper = method.uppercase(Locale.ROOT)
        val ts = System.currentTimeMillis()
        val payload = buildPayload(url = url, method = upper, nonce = nonce, ts = ts)
        return encrypt(payload, secretKey)
    }

    // ---- 内部实现 ----
    private fun encrypt(plainText: String, key: String): String {
        val textBytes = plainText.toByteArray(Charsets.UTF_8)
        val encrypted = xorEncryptDecrypt(textBytes, key)
        // 用 Android Base64，兼容所有 API 等级
        return android.util.Base64.encodeToString(encrypted, android.util.Base64.NO_WRAP)
    }

    private fun xorEncryptDecrypt(input: ByteArray, key: String): ByteArray {
        require(key.isNotEmpty()) { "secretKey must not be empty" }
        val out = ByteArray(input.size)
        val keyLen = key.length
        for (i in input.indices) {
            val k = key[i % keyLen].code and 0xFF
            out[i] = (input[i].toInt() xor k).toByte()
        }
        return out
    }

    private fun buildPayload(url: String, method: String, nonce: String, ts: Long): String {
        fun esc(s: String): String {
            val sb = StringBuilder(s.length + 16)
            for (ch in s) when (ch) {
                '\\' -> sb.append("\\\\")
                '\"' -> sb.append("\\\"")
                '\b' -> sb.append("\\b")
                '\u000C' -> sb.append("\\f")
                '\n' -> sb.append("\\n")
                '\r' -> sb.append("\\r")
                '\t' -> sb.append("\\t")
                else -> if (ch.code < 0x20) sb.append("\\u%04x".format(ch.code)) else sb.append(ch)
            }
            return sb.toString()
        }
        return """{"url":"${esc(url)}","method":"${esc(method)}","nonce":"${esc(nonce)}","ts":$ts,"platform":"Android"}"""
    }
}