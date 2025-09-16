package kz.atasuai.delivery.ui.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.ErrorType
import kz.atasuai.delivery.common.LoginType
import kz.atasuai.delivery.common.ToastHelper
import kz.atasuai.delivery.common.ToastType
import kz.atasuai.delivery.common.navigtion.ActivityList
import java.io.Serializable
import kotlin.reflect.KClass

sealed class State<T> {
    object Loading : State<Any?>()
    object Success : State<Any?>()
    object Offline : State<Any?>()
    object Error : State<Any?>()
    object Empty : State<Any?>()
    data class Custom(val key: String) : State<Any?>()
}

open class QarBaseViewModel : ViewModel() {
    val _loginType = MutableStateFlow(LoginType.Login)
    val loginType: StateFlow<LoginType> = _loginType.asStateFlow()
    fun setLoginType(value: LoginType) {
        _loginType.value = value
    }
     val _confirmError = MutableStateFlow(ErrorType.Empty)
    val confirmError: StateFlow<ErrorType> = _confirmError.asStateFlow()
    fun setConfirmError(value: ErrorType) {
        _confirmError.value = value
    }
    val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    protected fun setRefreshing(value: Boolean) {
        _isRefreshing.value = value
    }

    val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isBusy = MutableStateFlow(false)
    val isBusy: StateFlow<Boolean> = _isBusy.asStateFlow()

    private val _canLoadMore = MutableStateFlow(false)
    open val canLoadMore: StateFlow<Boolean> = _canLoadMore.asStateFlow()

    private val _canStateChange = MutableStateFlow(false)
    val canStateChange: StateFlow<Boolean> = _canStateChange.asStateFlow()

    private val _currentState = MutableStateFlow<State<Any?>>(State.Loading)
    val currentState: StateFlow<State<Any?>> = _currentState.asStateFlow()

    private val _customStateKey = MutableStateFlow<String>("")
    val customStateKey: StateFlow<String> = _customStateKey.asStateFlow()

    private var isNavigating = false

     fun navigateToActivity(
        context: Context,
        targetActivity: KClass<*>,
        paraDic: Map<String, Any>? = null,
        clearTask: Boolean = false
    ) {
        if (isNavigating) {
            return
        }
        isNavigating = true
        try {
            val intent = Intent(context, targetActivity.java).apply {
                paraDic?.forEach { (key, value) ->
                    when (value) {
                        is Int -> putExtra(key, value)
                        is Boolean -> putExtra(key, value)
                        is String -> putExtra(key, value)
                        // Add more types as needed
                        else -> putExtra(key, value.toString()) // Fallback to string
                    }
                }
                if (clearTask) {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
            context.startActivity(intent)
        } finally {
            isNavigating = false
        }
    }


    private var backPressedTime = 0L
    protected fun navigateToBack(context: Context) {
        if (isNavigating) return

        val activity = context as? Activity ?: return
        isNavigating = true

        try {
            if (activity.isTaskRoot) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime < 2000) {
                    activity.finish()
                } else {
                    backPressedTime = currentTime
                    Navigator.navigateAndClearTask(context, ActivityList.MainActivity)
                    ToastHelper.showMessage(context, ToastType.ERROR, "Press back again to exit the app")
                }
            } else {
                activity.finish()
            }
        } finally {
            isNavigating = false
        }
    }
    fun  onBack(context: Context){
        navigateToBack(context)
    }

    val currentRoute = mutableStateOf("home")

    fun updateCurrentRoute(route: String) {
        currentRoute.value = route
    }

    fun resetRoute() {
        updateCurrentRoute("home")
    }
    protected fun updateBusyStatus(isBusy: Boolean) {
        _isBusy.value = isBusy
    }
    protected fun updateState(newState: State<Any?>) {
        _currentState.value = newState
    }

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    protected open fun handleError(e: Exception) {
        e.printStackTrace()
        _currentState.value = State.Error
    }

    object Navigator {
        private var isNavigating = false

        fun navigate(
            context: Context,
            destination: ActivityList,
            params: Map<String, Any>? = null,
            flags: Int? = null,
            clearTask: Boolean = false,
            singleTop: Boolean = false
        ) {
            if (isNavigating) return

            isNavigating = true

            try {
                val intent = Intent(context, destination.activityClass.java).apply {
                    params?.forEach { (key, value) ->
                        when (value) {
                            is Int -> putExtra(key, value)
                            is Long -> putExtra(key, value)
                            is Float -> putExtra(key, value)
                            is Double -> putExtra(key, value)
                            is Boolean -> putExtra(key, value)
                            is String -> putExtra(key, value)
                            is Bundle -> putExtra(key, value)
                            is Serializable -> putExtra(key, value)
                            is Parcelable -> putExtra(key, value)
                            else -> putExtra(key, value.toString())
                        }
                    }

                    when {
                        clearTask -> this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        singleTop -> this.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                        flags != null -> this.flags = flags
                    }
                }

                context.startActivity(intent)

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                Handler(Looper.getMainLooper()).postDelayed({
                    isNavigating = false
                }, 500)
            }
        }

        fun navigateAndClearTask(context: Context, destination: ActivityList) {
            navigate(context, destination, clearTask = true)
        }
        fun navigateAndClearTask(context: Context, destination: ActivityList, params: Map<String, Any>) {
            navigate(context, destination, params = params, clearTask = true)
        }

        fun navigateWithParams(context: Context, destination: ActivityList, params: Map<String, Any>) {
            navigate(context, destination, params = params)
        }



//        // 基本跳转
//        Navigator.navigate(context, ActivityList.OrderActivity)
//
//// 带参数
//        Navigator.navigateWithParams(context, ActivityList.OrderActivity, mapOf("id" to 123))
//
//// 清除任务栈
//        Navigator.navigateAndClearTask(context, ActivityList.OrderActivity)
//
//// 完整参数
//        Navigator.navigate(
//        context = context,
//        destination = ActivityList.OrderActivity,
//        params = mapOf("id" to 123),
//        singleTop = true
//        )
    }


}