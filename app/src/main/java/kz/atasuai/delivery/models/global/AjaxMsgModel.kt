package kz.atasuai.market.models

data class AjaxMsgModel(
    val status: String,
    val message: String,
    val backUrl: String = "",
    val data: Any? = null
)