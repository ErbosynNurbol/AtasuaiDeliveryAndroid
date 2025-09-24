package kz.atasuai.delivery.common.navigtion

import android.app.Activity
import kotlin.reflect.KClass

enum class ActivityList(val activityClass: KClass<out Activity>) {
    MainActivity(kz.atasuai.delivery.ui.activities.MainActivity::class),
    LoginActivity(kz.atasuai.delivery.ui.activities.welcome.LoginActivity::class),
    RegisActivity(kz.atasuai.delivery.ui.activities.welcome.RegisActivity::class),
    OrderActivity(kz.atasuai.delivery.ui.activities.notification.OrderActivity::class),
    IncomeActivity(kz.atasuai.delivery.ui.activities.notification.IncomeActivity::class),
    ScanQRActivity(kz.atasuai.delivery.ui.activities.home.ScanQRActivity::class),
    SupportActivity(kz.atasuai.delivery.ui.activities.profile.SupportActivity::class),
}