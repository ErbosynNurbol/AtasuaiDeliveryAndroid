package kz.atasuai.delivery.common.navigtion

import android.app.Activity
import kotlin.reflect.KClass

enum class ActivityList(val activityClass: KClass<out Activity>) {
    MainActivity(kz.atasuai.delivery.ui.activities.MainActivity::class),
}