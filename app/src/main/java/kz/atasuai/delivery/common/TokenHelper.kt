package kz.atasuai.delivery.common

import com.auth0.android.jwt.JWT
import kz.atasuai.market.models.global.PersonModel

object TokenHelper {

    fun decode(token: String?): PersonModel? {
        return try {
            if (token.isNullOrEmpty()) return null

            val jwt = JWT(token)

            PersonModel(
                personId = jwt.getClaim("PersonId").asString().orEmpty(),
                name = jwt.getClaim("Name").asString().orEmpty(),
                avatarUrl = jwt.getClaim("AvatarUrl").asString().orEmpty(),
                email = jwt.getClaim("Email").asString().orEmpty(),
                phone = jwt.getClaim("Phone").asString().orEmpty(),
                whatsApp = jwt.getClaim("WhatsApp").asString().orEmpty(),
                surName = jwt.getClaim("SurName").asString().orEmpty(),
                storeId = jwt.getClaim("StoreId").asInt() ?: 0,
                hasPassword = jwt.getClaim("HasPassword").asBoolean() ?: false,
                canPush = jwt.getClaim("CanPush").asInt() ?: 0,
                birthday = jwt.getClaim("Birthday").asString().orEmpty(),
            )
        } catch (ex: Exception) {
            null
        }
    }
}
