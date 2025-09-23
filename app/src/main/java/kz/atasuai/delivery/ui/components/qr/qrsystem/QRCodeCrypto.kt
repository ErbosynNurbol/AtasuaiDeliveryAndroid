package kz.atasuai.delivery.ui.components.qr.qrsystem

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random


object QRCodeCrypto {
    private const val SECRET_KEY = "Atasuai125678901QAR3456789o01234"
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"
    private const val HMAC_ALGORITHM = "HmacSHA256"


    fun generateQRContent(personId: String): String {
        val timestamp = System.currentTimeMillis()
        val nonce = generateNonce()

        val complexData = createComplexData(personId, timestamp, nonce)

        val encryptedData = encrypt(complexData)

        val signature = createSignature(encryptedData)

        return "$encryptedData|$signature"
    }

    /**
     * 解密QR码内容 (后端使用)
     * @param qrContent 扫码得到的字符串
     * @return 解密后的数据，失败返回null
     */
    fun decryptQRContent(qrContent: String): QRCodeData? {
        try {
            val parts = qrContent.split("|")
            if (parts.size != 2) return null

            val encryptedData = parts[0]
            val signature = parts[1]

            if (!verifySignature(encryptedData, signature)) return null

            val decryptedData = decrypt(encryptedData)

            return parseComplexData(decryptedData)
        } catch (e: Exception) {
            return null
        }
    }


    private fun createComplexData(personId: String, timestamp: Long, nonce: String): String {
        val timestampStr = timestamp.toString()

        val personIdParts = personId.chunked(maxOf(1, personId.length / 3))

        val parts = mutableListOf<String>()
        parts.add("T$timestampStr")
        parts.add("P1${personIdParts.getOrNull(0) ?: ""}")
        parts.add("N$nonce")
        parts.add("X${Random.nextInt(100000, 999999)}")
        parts.add("P2${personIdParts.getOrNull(1) ?: ""}")
        parts.add("Y${Random.nextLong(1000000000, 9999999999)}")
        parts.add("P3${personIdParts.getOrNull(2) ?: ""}")
        parts.add("Z${generateRandomString(8)}")

        return parts.joinToString("::")
    }

    /**
     * 解析复杂数据 (后端使用)
     */
    private fun parseComplexData(complexData: String): QRCodeData? {
        try {
            val parts = complexData.split("::")

            var timestamp = 0L
            val personIdParts = mutableListOf<String>()
            var nonce = ""

            for (part in parts) {
                when {
                    part.startsWith("T") -> timestamp = part.substring(1).toLong()
                    part.startsWith("P1") -> personIdParts.add(part.substring(2))
                    part.startsWith("P2") -> {
                        if (personIdParts.size >= 1) personIdParts.add(part.substring(2))
                        else personIdParts.add(0, part.substring(2))
                    }
                    part.startsWith("P3") -> {
                        if (personIdParts.size >= 2) personIdParts.add(part.substring(2))
                        else personIdParts.add(part.substring(2))
                    }
                    part.startsWith("N") -> nonce = part.substring(1)
                }
            }

            val personId = personIdParts.joinToString("")

            return QRCodeData(
                personId = personId,
                timestamp = timestamp,
                nonce = nonce
            )
        } catch (e: Exception) {
            return null
        }
    }

    private fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = SecretKeySpec(SECRET_KEY.toByteArray(), ALGORITHM)

        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        val encryptedData = cipher.doFinal(data.toByteArray())

        val combined = iv + encryptedData
        return Base64.encodeToString(combined, Base64.NO_WRAP)
    }

    private fun decrypt(encryptedData: String): String {
        val combined = Base64.decode(encryptedData, Base64.NO_WRAP)

        val iv = combined.sliceArray(0..15)
        val encrypted = combined.sliceArray(16 until combined.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKey = SecretKeySpec(SECRET_KEY.toByteArray(), ALGORITHM)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
        val decryptedData = cipher.doFinal(encrypted)

        return String(decryptedData)
    }

    private fun createSignature(data: String): String {
        val mac = Mac.getInstance(HMAC_ALGORITHM)
        val secretKey = SecretKeySpec(SECRET_KEY.toByteArray(), HMAC_ALGORITHM)
        mac.init(secretKey)

        val signature = mac.doFinal(data.toByteArray())
        return Base64.encodeToString(signature, Base64.NO_WRAP)
    }

    private fun verifySignature(data: String, signature: String): Boolean {
        val expectedSignature = createSignature(data)
        return expectedSignature == signature
    }

    private fun generateNonce(): String {
        return Random.nextLong(100000000000, 999999999999).toString()
    }

    private fun generateRandomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}

data class QRCodeData(
    val personId: String,
    val timestamp: Long,
    val nonce: String
) {

    fun isExpired(validDurationMs: Long = 60000): Boolean {
        return System.currentTimeMillis() - timestamp > validDurationMs
    }
}