package kz.atasuai.delivery.common

import android.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class RegexHelper {
    fun isPhoneNumber(phone: String): String? {
        val cleanPhone = phone.replace(Regex("[^0-9+]"), "")
        val isValid = when {
            cleanPhone.startsWith("1") && cleanPhone.length == 11 -> true
            cleanPhone.startsWith("+861") && cleanPhone.length == 14 -> true

            cleanPhone.startsWith("87") && cleanPhone.length == 11 -> true
            cleanPhone.startsWith("77") && cleanPhone.length == 11 -> true
            cleanPhone.startsWith("+77") && cleanPhone.length == 12 -> true

            cleanPhone.startsWith("7") && cleanPhone.length == 10 -> true
            cleanPhone.startsWith("+7") && cleanPhone.length == 12 -> true

            else -> false
        }
        return if (isValid) phone else null
    }

    fun formatPhoneNumber(phone: String): String? {
        val cleanPhone = phone.replace(Regex("[^0-9+]"), "")

        return when {
            cleanPhone.startsWith("+1") && cleanPhone.length == 12 ->
                cleanPhone
            cleanPhone.startsWith("1") && cleanPhone.length == 11 ->
                "+$cleanPhone"

            cleanPhone.startsWith("+86") && cleanPhone.length == 14 ->
                cleanPhone
            cleanPhone.startsWith("86") && cleanPhone.length == 13 ->
                "+$cleanPhone"

            cleanPhone.startsWith("+7") && cleanPhone.length == 12 ->
                cleanPhone

            cleanPhone.startsWith("87") && cleanPhone.length == 11 ->
                "+7${cleanPhone.substring(2)}"

            cleanPhone.startsWith("77") && cleanPhone.length == 11 ->
                "+7${cleanPhone.substring(2)}"

            cleanPhone.startsWith("7") && cleanPhone.length == 11 ->
                "+$cleanPhone"

            cleanPhone.length == 10 ->
                "+7$cleanPhone"

            else -> null
        }
    }
    fun isEmail(email: String): Boolean {
        val trimmedEmail = email.trim()
        val emailPattern = Regex(
            "^[a-zA-Z0-9]([a-zA-Z0-9._-]*[a-zA-Z0-9])?@[a-zA-Z0-9]([a-zA-Z0-9.-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}$"
        )
        return emailPattern.matches(trimmedEmail) && trimmedEmail.length <= 254
    }
     fun normalizeKazakhstanPhone(phoneRaw: String): String {
        // 移除所有非数字字符
        val digitsOnly = phoneRaw.replace(Regex("[^\\d]"), "")

        return when {
            digitsOnly.isEmpty() -> ""
            // 如果已经是+7开头的格式
            digitsOnly.startsWith("7") && digitsOnly.length == 11 -> "+$digitsOnly"
            // 如果是8开头的俄罗斯/哈萨克斯坦格式
            digitsOnly.startsWith("8") && digitsOnly.length == 11 -> "+7${digitsOnly.substring(1)}"
            // 如果是10位数字，假设是哈萨克斯坦号码
            digitsOnly.length == 10 -> "+7$digitsOnly"
            // 其他情况保持原样或返回空
            else -> if (digitsOnly.length >= 10) "+7${digitsOnly.takeLast(10)}" else ""
        }
    }

    fun hashPhone(phone: String): String {
        val secretKey = "74jXod2ogpd9eKXVJGno48h4iSOZXxQhKmJsGvuLy5c5leVsiAsQA/37ZJ5+c59Il8c3s29/1T0A2CRRr9oq0w=="
        val salt = "PzLsK/Msxb03xudml+ZXp9raOIdW9GvkKe23DGY1FuU="

        // Combine data with salt
        val saltedData = "$phone$salt"

        // Convert base64 secret key to bytes (使用 NO_WRAP)
        val keyBytes = Base64.decode(secretKey, Base64.NO_WRAP)

        // Create HMAC-SHA256
        val mac = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(keyBytes, "HmacSHA256")
        mac.init(secretKeySpec)

        // Compute hash
        val hashBytes = mac.doFinal(saltedData.toByteArray(StandardCharsets.UTF_8))

        // Return as URL-safe base64 string
        return Base64.encodeToString(hashBytes, Base64.NO_WRAP)
            .trimEnd('=')  // Remove padding
            .replace('+', '-')  // URL-safe
            .replace('/', '_')   // URL-safe
    }

}