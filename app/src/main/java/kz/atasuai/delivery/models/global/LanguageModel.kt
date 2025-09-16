package kz.atasuai.market.models

data class LanguageModel(
    val flagUrl:String,
    val fullName: String,
    val shortName: String,
    val culture: String,
    val isDefault:Boolean,
    val uniqueSeoCode:String = "",
)