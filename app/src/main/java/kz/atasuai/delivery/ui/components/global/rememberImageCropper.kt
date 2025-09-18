package kz.atasuai.delivery.ui.components.global

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.yalantis.ucrop.UCrop
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import java.io.File

@Composable
fun rememberImageCropper(
    onCropResult: (Uri?) -> Unit,
    isAvatar: Boolean = false
): (Uri) -> Unit {
    val context = LocalContext.current
    val themeBackgroundColor = AtasuaiTheme.colors.background.toArgb()

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val output = result.data?.let { UCrop.getOutput(it) }
                onCropResult(output)
            }
            UCrop.RESULT_ERROR -> {
                val cropError = result.data?.let { UCrop.getError(it) }
                Log.e("UCrop", "Crop error: ${cropError?.message}")
                onCropResult(null)
            }
            else -> {
                onCropResult(null)
            }
        }
    }

    return { sourceUri ->
        launchImageCropperUcrop(
            context = context,
            sourceUri = sourceUri,
            launcher = cropLauncher,
            backgroundColor = themeBackgroundColor,
            isAvatar = isAvatar
        )
    }
}

private fun launchImageCropperUcrop(
    context: Context,
    sourceUri: Uri,
    launcher: ActivityResultLauncher<Intent>,
    backgroundColor: Int,
    isAvatar: Boolean = false
) {
    val fileName = if (isAvatar) {
        "avatar_cropped_${System.currentTimeMillis()}.png"
    } else {
        "document_cropped_${System.currentTimeMillis()}.jpg"
    }

    val destFile = File(context.cacheDir, fileName)
    if (destFile.exists()) destFile.delete()
    val destUri = Uri.fromFile(destFile)

    val options = UCrop.Options().apply {
        setToolbarColor(backgroundColor)
        setToolbarWidgetColor(android.graphics.Color.BLACK)

        if (isAvatar) {
            // 头像：圆形剪裁设置
            setCircleDimmedLayer(true)
            setDimmedLayerColor(0x80000000.toInt())
            setShowCropGrid(false)
            setShowCropFrame(false)
            setFreeStyleCropEnabled(false)
            setCompressionFormat(Bitmap.CompressFormat.PNG)
            setCompressionQuality(100)
        } else {
            // 证件：矩形剪裁设置
            setCircleDimmedLayer(false)
            setDimmedLayerColor(0x80000000.toInt())
            setShowCropGrid(true)
            setShowCropFrame(true)
            setFreeStyleCropEnabled(true)
            setCompressionFormat(Bitmap.CompressFormat.JPEG)
            setCompressionQuality(90)
        }

        // 底部控制
        setHideBottomControls(false)

        // 控件颜色设置
        setActiveControlsWidgetColor(android.graphics.Color.BLACK)
        setRootViewBackgroundColor(backgroundColor)
    }

    // 启动 UCrop
    val intent = if (isAvatar) {
        UCrop.of(sourceUri, destUri)
            .withOptions(options)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(1024, 1024)
            .getIntent(context)
    } else {
        UCrop.of(sourceUri, destUri)
            .withOptions(options)
            .withAspectRatio(5f, 8f)  // 证件照竖向比例
            .withMaxResultSize(1280, 2048)
            .getIntent(context)
    }

    launcher.launch(intent)
}