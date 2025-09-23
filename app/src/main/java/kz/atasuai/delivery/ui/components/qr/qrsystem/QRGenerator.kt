package kz.atasuai.delivery.ui.components.qr.qrsystem

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


object QRGenerator {

    fun generateQR(personId: String, size: Int = 500): QRResult {
        return try {
            val qrData = QRCodeCrypto.generateQRContent(personId)
            val bitmap = createQRBitmap(qrData, size)
            val imageBitmap = bitmap?.asImageBitmap()

            QRResult(
                success = true,
                qrData = qrData,
                qrImageBitmap = imageBitmap,
                timeLeft = 60
            )
        } catch (e: Exception) {
            QRResult(
                success = false,
                qrData = "",
                qrImageBitmap = null,
                timeLeft = 0
            )
        }
    }

    private fun createQRBitmap(content: String, size: Int): Bitmap? {
        return try {
            val writer = QRCodeWriter()
            val hints = hashMapOf<EncodeHintType, Any>(
                EncodeHintType.CHARACTER_SET to "UTF-8",
                EncodeHintType.MARGIN to 1
            )

            val bitMatrix: BitMatrix = writer.encode(
                content, BarcodeFormat.QR_CODE, size, size, hints
            )

            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
            for (x in 0 until size) {
                for (y in 0 until size) {
                    bitmap.setPixel(
                        x, y,
                        if (bitMatrix[x, y]) Color.Black.toArgb() else Color.White.toArgb()
                    )
                }
            }
            bitmap
        } catch (e: WriterException) {
            null
        }
    }
}

data class QRResult(
    val success: Boolean,
    val qrData: String,
    val qrImageBitmap: ImageBitmap?,
    val timeLeft: Int
)