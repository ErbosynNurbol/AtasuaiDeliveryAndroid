package kz.atasuai.delivery.common

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kz.atasuai.delivery.R


enum class ToastType { SUCCESS, ERROR, WARNING }

object ToastHelper {
    fun showMessage(context: Context, type: ToastType, message: String) {
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_toast_layout, null)

        val toastIcon = layout.findViewById<ImageView>(R.id.toast_icon)
        val toastText = layout.findViewById<TextView>(R.id.toast_message)

        toastText.text = message

        when (type) {
            ToastType.SUCCESS -> toastIcon.setImageResource(R.drawable.success_icon)
            ToastType.ERROR -> toastIcon.setImageResource(R.drawable.error_icon)
            ToastType.WARNING -> toastIcon.setImageResource(R.drawable.waring_icon)
        }

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)

        toast.show()
    }
}