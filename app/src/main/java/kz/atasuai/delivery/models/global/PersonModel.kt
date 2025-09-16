package kz.atasuai.market.models.global

data class PersonModel(
    val personId:String = "",
    val canPush:Int = 0,
    val surName:String = "",
    val name: String = "",
    val email: String = "",
    val whatsApp : String = "",
    val avatarUrl: String = "",
    val phone:String = "",
    val storeId:Int = 0,
    val birthday:String = "",
    val hasPassword:Boolean = false
)
