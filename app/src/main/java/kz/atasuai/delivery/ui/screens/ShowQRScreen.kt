package kz.atasuai.delivery.ui.screens

import android.Manifest
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.activities.home.CameraPermissionContent
import kz.atasuai.delivery.ui.activities.home.ScanOverlayWithCorners
import kz.atasuai.delivery.ui.activities.home.ScanProcessingContent
import kz.atasuai.delivery.ui.activities.home.ScanResultContent
import kz.atasuai.delivery.ui.activities.home.ScanSuccessContent
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.home.CameraPreview
import kz.atasuai.delivery.ui.components.home.ConfirmLoginModal
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.viewmodels.home.ScanQRViewModel
import kz.atasuai.market.models.LanguageModel
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation.width
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.AtasuaiApp.Companion.currentLanguage
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.qr.ManualQRCode
import kz.atasuai.delivery.ui.components.qr.QRShowSwitch
import kz.atasuai.delivery.ui.components.qr.QRTypeSwitch
import kz.atasuai.delivery.ui.components.qr.qrsystem.QRGenerator
import kz.atasuai.delivery.ui.components.qr.qrsystem.QRResult
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily



@OptIn(ExperimentalPermissionsApi::class,ExperimentalMaterial3Api::class)
@Composable
fun ScanQRModal(
    onDismissRequest: () -> Unit,
    context: Context,
    darkTheme: Boolean,
    viewModel: ScanQRViewModel,
    currentLanguage: LanguageModel
){
    DisposableEffect(Unit) {
        onDispose {
            viewModel.onDestroy()
        }
    }
    val isScanQR by viewModel.isScanQR.collectAsStateWithLifecycle()
    var showManualQR by remember { mutableStateOf(false) }
    if(showManualQR){
        ManualQRCode(
            viewModel,
            currentLanguage = currentLanguage,
            onDismissRequest = {
                showManualQR = false
            }
        )
    }
    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            VSpacerHi(38f)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(26.dp)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                Image(
                    painter = painterResource(id = R.drawable.close_icon_red),
                    contentDescription = "back",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            onDismissRequest()
                        }
                        .align(Alignment.CenterStart)
                )
                TitleStyle(
                    text = T("ls_Scanqrcode",currentLanguage),
                    fontSize = 16f, fontWeight = 500,
                    modifier = Modifier.align(Alignment.Center)
                )

            }
            VSpacerHi(12f)

            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val scanResult by viewModel.scanResult.collectAsStateWithLifecycle()
            val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
            val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
            val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
            val apiSuccess by viewModel.apiSuccess.collectAsStateWithLifecycle()
            val apiMessage by viewModel.apiMessage.collectAsStateWithLifecycle()
            val confirmLogin by viewModel.confirmLogin.collectAsStateWithLifecycle()
            val isFlashlightOn by viewModel.isFlashlightOn.collectAsStateWithLifecycle()

            if(confirmLogin){
                ConfirmLoginModal(onDismissRequest = { viewModel.onConfirmLogin(false) },
                    onChange = { viewModel.loadTokenToScan(context) },
                    context = context,
                    message = T("ls_Cwlvqc",currentLanguage),
                    hint = T("ls_Ctcl",currentLanguage)
                )
            }

            val cameraPermission = rememberPermissionState(permission = Manifest.permission.CAMERA)
           if(isScanQR){
               Box(
                   modifier = Modifier
                       .fillMaxSize()
                       .noRippleClickable {
                           focusManager.clearFocus()
                           keyboardController?.hide()
                       }
                       .background(Color.Black)
               ) {
                   when {
                       !cameraPermission.status.isGranted -> {
                           CameraPermissionContent(
                               onRequestPermission = { cameraPermission.launchPermissionRequest()},
                               currentLanguage = currentLanguage
                           )
                       }

                       isScanning -> {
                           CameraPreview(
                               isFlashlightOn = isFlashlightOn, // 传递状态
                               onQRCodeScanned = { result ->
                                   viewModel.onQRCodeScanned(result)
                               },
                               onError = { error ->
                                   viewModel.onScanError(error)
                               }
                           )
                       }

                       scanResult != null -> {
                           when {
                               isLoading -> {
                                   ScanProcessingContent(
                                       result = scanResult!!,
                                       onCancel = { viewModel.restartScanning() },
                                       currentLanguage
                                   )
                               }
                               apiSuccess -> {
                                   ScanSuccessContent(
                                       result = scanResult!!,
                                       message = apiMessage ?: "",
                                       onScanAgain = { viewModel.restartScanning() },
                                       currentLanguage
                                   )
                               }
                               else -> {
                                   if(!confirmLogin){
                                       ScanResultContent(
                                           result = scanResult!!,
                                           onScanAgain = { viewModel.restartScanning() },
                                           onRetry = { viewModel.retryTokenScan() },
                                           hasError = !errorMessage.isNullOrEmpty(),
                                           currentLanguage
                                       )
                                   }

                               }
                           }
                       }
                   }
                   if (isScanning) {
                       Canvas(modifier = Modifier.fillMaxSize()) {
                           val scanFrameSize = 250.dp.toPx()
                           val borderRadius = 12.dp.toPx()

                           val upperHalfHeight = size.height / 2

                           // 往下移动更多一点
                           val scanFrameCenterY = upperHalfHeight - scanFrameSize / 2 + 15.dp.toPx() // 改成+20dp
                           val centerX = size.width / 2

                           val left = centerX - scanFrameSize / 2
                           val top = scanFrameCenterY - scanFrameSize / 2
                           val right = centerX + scanFrameSize / 2
                           val bottom = scanFrameCenterY + scanFrameSize / 2

                           // 其他代码保持不变
                           val fullScreenPath = Path().apply {
                               addRect(Rect(0f, 0f, size.width, size.height))
                           }

                           val scanFramePath = Path().apply {
                               addRoundRect(
                                   RoundRect(
                                       left = left,
                                       top = top,
                                       right = right,
                                       bottom = bottom,
                                       radiusX = borderRadius,
                                       radiusY = borderRadius
                                   )
                               )
                           }

                           val maskPath = Path().apply {
                               op(fullScreenPath, scanFramePath, PathOperation.Difference)
                           }

                           drawPath(
                               path = maskPath,
                               color = Color.Black.copy(alpha = 0.5f)
                           )
                       }
                   }
                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                           .statusBarsPadding()
                   ) {
                       Column(modifier = Modifier.weight(0.5f).fillMaxWidth(),
                           horizontalAlignment = Alignment.CenterHorizontally,
                           verticalArrangement = Arrangement.Bottom
                       ){
                           if (isScanning) {
                               ScanOverlayWithCorners()
                           }
                       }
                       Column(modifier = Modifier.weight(0.5f).fillMaxWidth(),
                           horizontalAlignment = Alignment.CenterHorizontally,
                           verticalArrangement = Arrangement.Center
                       ){
                           if(!confirmLogin){
                               if(isScanning){
                                   Row(modifier = Modifier.fillMaxWidth(),
                                       horizontalArrangement = Arrangement.Center
                                   ){
                                       Row(modifier = Modifier.wrapContentSize()
                                           .noRippleClickable {
                                               showManualQR = true
                                           }
                                           .background(
                                               brush = Brush.verticalGradient(
                                                   colors = listOf(
                                                       Color(0x33FFFFFF).copy(alpha = 0.25f),
                                                       Color(0x33FFFFFF).copy(alpha = 0.15f)
                                                   )
                                               ),
                                               shape = RoundedCornerShape(53.dp)
                                           )
                                           .border(
                                               width = 0.5.dp,
                                               brush = Brush.verticalGradient(
                                                   colors = listOf(
                                                       Color.White.copy(alpha = 0.4f),
                                                       Color.White.copy(alpha = 0.1f)
                                                   )
                                               ),
                                               shape = RoundedCornerShape(53.dp)
                                           )
                                           .padding(start = 20.dp, top = 13.dp, end = 20.dp, bottom = 13.dp),
                                           verticalAlignment = Alignment.CenterVertically
                                       ){
                                           Icon(
                                               painter = painterResource(id = R.drawable.code_write),
                                               contentDescription = "code",
                                               tint = Color.White,
                                               modifier = Modifier.size(14.dp)
                                           )
                                           VSpacerWi(10f)
                                           Text(
                                               text = "Кодты қолмен енгізу",
                                               style = TextStyle(
                                                   fontSize = 14.sp,
                                                   lineHeight = 14.sp,
                                                   fontFamily = PrimaryFontFamily,
                                                   fontWeight = FontWeight(400),
                                                   color = Color(0xFFFFFFFF),
                                               )
                                           )
                                       }
                                   }
                                   VSpacerHi(28f)
                                   Row(modifier = Modifier.fillMaxWidth(),
                                       verticalAlignment = Alignment.CenterVertically,
                                       horizontalArrangement = Arrangement.Center
                                   ){
                                       val painter = if (isFlashlightOn) {
                                           painterResource(id = R.drawable.off_light)
                                       } else {
                                           painterResource(id = R.drawable.on_light)
                                       }
                                       Icon(
                                           painter = painter,
                                           contentDescription = "light",
                                           tint = Color.Unspecified,
                                           modifier = Modifier.size(54.dp)
                                               .noRippleClickable {
                                                   viewModel.toggleFlashlight()
                                               }
                                       )
                                   }
                                   VSpacerHi(28f)
                                   Row( modifier = Modifier
                                       .fillMaxWidth()
                                       .height(56.dp)
                                       .padding(horizontal = 20.dp),
                                       verticalAlignment = Alignment.CenterVertically,
                                       horizontalArrangement = Arrangement.Center
                                   ){
                                       QRTypeSwitch(
                                           isOnline = isScanQR,
                                           onStatusChange = { viewModel.setIsOnline(it) },
                                           darkTheme = darkTheme,
                                           currentLanguage = currentLanguage,
                                           modifier = Modifier.height(52.dp).width(265.dp)
                                       )
                                   }
                               }else{
                                   Text(
                                       text = when {
                                           !cameraPermission.status.isGranted -> T("ls_Pgcp",currentLanguage) + " !"
//                                isScanning -> T("ls_Ptqcitsf",currentLanguage) + " !"
                                           isLoading -> T("ls_Processingscanresult",currentLanguage) + "..."
                                           apiSuccess -> ""
                                           scanResult != null -> T("ls_Scancompleted",currentLanguage)
                                           else -> ""
                                       },
                                       fontSize = 16.sp,
                                       color = Color.White,
                                       textAlign = TextAlign.Center,
                                   )
                               }
                           }
                       }
                   }
               }
           }else{
               ShowScanQR(viewModel,darkTheme,currentLanguage)
           }

        }
    }

}


@Composable
fun ShowScanQR(viewModel: ScanQRViewModel,darkTheme:Boolean,currentLanguage:LanguageModel){
    val isScanQR by viewModel.isScanQR.collectAsState()
    var qrResult by remember { mutableStateOf<QRResult?>(null) }
    LaunchedEffect(Unit) {
        AtasuaiApp.currentPerson?.personId.let{
            qrResult = QRGenerator.generateQR("${AtasuaiApp.currentPerson?.personId}")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9EAF2)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_dark_logo),
            contentDescription = "logo",
            modifier = Modifier.width(119.dp).height(26.dp)
        )
        VSpacerHi(52f)
        Box(modifier = Modifier.width(329.dp)
            .height(329.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 30.dp))
            .padding(20.dp)
        ){
            qrResult?.qrImageBitmap?.let { imageBitmap ->
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "QR Code",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        VSpacerHi(110f)
        QRShowSwitch(
            isOnline = isScanQR,
            onStatusChange = { viewModel.setIsOnline(it) },
            darkTheme = darkTheme,
            currentLanguage = currentLanguage,
            modifier = Modifier.height(52.dp).width(265.dp)
        )

    }
}