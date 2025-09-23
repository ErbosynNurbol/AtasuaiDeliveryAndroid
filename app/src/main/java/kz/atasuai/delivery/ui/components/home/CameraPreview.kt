package kz.atasuai.delivery.ui.components.home

import android.hardware.camera2.CameraAccessException
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    isFlashlightOn: Boolean, // 新增参数
    onQRCodeScanned: (String) -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var camera by remember { mutableStateOf<Camera?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    // 监听手电筒状态变化
    LaunchedEffect(isFlashlightOn) {
        try {
            camera?.cameraControl?.enableTorch(isFlashlightOn)
        } catch (e: Exception) {
            onError("手电筒控制失败: ${e.message}")
        }
    }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(cameraExecutor, QRCodeAnalyzer(
                                onQRCodeScanned = onQRCodeScanned,
                                onError = onError
                            ))
                        }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    cameraProvider.unbindAll()
                    camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )

                    // 初始设置手电筒状态
                    camera?.cameraControl?.enableTorch(isFlashlightOn)

                } catch (e: CameraAccessException) {
                    onError("相机访问异常: ${e.message} (错误代码: ${e.reason})")
                } catch (e: IllegalArgumentException) {
                    onError("相机参数错误: ${e.message}")
                } catch (e: IllegalStateException) {
                    onError("相机状态错误: ${e.message}")
                } catch (e: Exception) {
                    onError("相机初始化失败: ${e.message}")
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}
/**
 * QR码分析器
 */
private class QRCodeAnalyzer(
    private val onQRCodeScanned: (String) -> Unit,
    private val onError: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()
    private var lastAnalyzedTimestamp = 0L

    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimestamp >= 500) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )

                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { value ->
                                onQRCodeScanned(value)
                                return@addOnSuccessListener
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        onError("QR код танылмады: ${exception.message}")
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }

                lastAnalyzedTimestamp = currentTimestamp
            } else {
                imageProxy.close()
            }
        } else {
            imageProxy.close()
        }
    }
}