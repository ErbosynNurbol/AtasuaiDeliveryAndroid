package kz.atasuai.delivery.common

enum class ButtonStatus {
    Enabled,
    Loading,
    Disabled
}


enum class WhereOption {
    From,
    To
}

enum class VerificationType {
    Register,
    Recover,
    Change_phone;

    override fun toString(): String {
        return name.lowercase().replace("_","")
    }
}

enum class DocumentType {
    IIN,
    TRUCK_TYPE,
    FRONT,
    BACK,
    CERTIFICATE_OF_REGISTRATION
}

enum class CardSide(val code: Int) {
    NONE(0),
    FRONT(1),
    BACK(2)
}

enum class ConnectivityStatus {
    Available,
    Unavailable
}

enum class LoginType{
    Login,
    Regis,
    Recover,
    SmsLogin
}

enum class ErrorType{
    Phone,
    Password,
    UserName,
    ConfirmPassword,
    Empty,
    OldPassword,
}