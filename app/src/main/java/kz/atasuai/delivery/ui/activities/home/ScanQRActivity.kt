package kz.atasuai.delivery.ui.activities.home

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.journeyapps.barcodescanner.CameraPreview
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ConnectivityObserver
import kz.atasuai.delivery.common.ConnectivityStatus
import kz.atasuai.delivery.common.ThemeMode
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.NoInternetPage
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.home.ConfirmLoginModal
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiScreen
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.viewmodels.home.ScanQRViewModel
import kz.atasuai.market.models.LanguageModel
import kz.atasuai.delivery.ui.components.home.CameraPreview
import kz.atasuai.delivery.ui.components.qr.QRTypeSwitch
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


class ScanQRActivity:ComponentActivity(){
    val viewModel: ScanQRViewModel by viewModels()
    private val themeManager by lazy { AtasuaiApp.themeManager }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by themeManager.themeMode.collectAsState()
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            }
            val context = LocalContext.current

            AtasuaiTheme(darkTheme = darkTheme) {
                AtasuaiScreen(
                    applySystemBarsPadding = false,
                    statusBarDarkIcons = false,
                    navigationBarDarkIcons = false,
                    navigationBarColor = Color.Black
                ) { screenModifier ->
                    val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
                    val connectivityObserver = remember { ConnectivityObserver(context) }
                    val status by connectivityObserver.status.collectAsState(initial = ConnectivityStatus.Available)
                    when(status) {
                        ConnectivityStatus.Available -> {
                            ScanQRScreen(
                                viewModel,
                                currentLanguage,
                                context,
                                darkTheme,
                                modifier = screenModifier,
                            )
                        }
                        ConnectivityStatus.Unavailable -> {
                            NoInternetPage()
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanQRScreen(
    viewModel: ScanQRViewModel,
    currentLanguage: LanguageModel,
    context: Context,
    darkTheme: Boolean,
    modifier: Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scanResult by viewModel.scanResult.collectAsStateWithLifecycle()
    val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val apiSuccess by viewModel.apiSuccess.collectAsStateWithLifecycle()
    val apiMessage by viewModel.apiMessage.collectAsStateWithLifecycle()
    val confirmLogin by viewModel.confirmLogin.collectAsStateWithLifecycle()
    val isScanQR by viewModel.isScanQR.collectAsStateWithLifecycle()
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
                val topPadding = 102.dp.toPx() // 你的固定padding
                val scanFrameSize = 250.dp.toPx()
                val borderRadius = 12.dp.toPx()

                // 计算content区域的中心点
                val contentHeight = size.height - topPadding
                val contentCenterY = topPadding + contentHeight / 2
                val centerX = size.width / 2

                val left = centerX - scanFrameSize / 2
                val top = contentCenterY - scanFrameSize / 2
                val right = centerX + scanFrameSize / 2
                val bottom = contentCenterY + scanFrameSize / 2

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
                    color = Color.Black.copy(alpha = 0.7f)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_left),
                    contentDescription = "back",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(24.dp)
                        .noRippleClickable {
                            viewModel.onBack(context)
                        }
                        .align(Alignment.CenterStart)
                )

                Text(
                    text = T("ls_Scanqrcode",currentLanguage),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (isScanning) {
                    ScanOverlayWithCorners()
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(!confirmLogin){
                        if(isScanning){
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ){
                                Row(modifier = Modifier.wrapContentSize()
                                    .background(color = Color(0x33FFFFFF), shape = RoundedCornerShape(size = 50.dp))
                                    .padding(start = 20.dp, top = 13.dp, end = 20.dp, bottom = 13.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    androidx.compose.material.Icon(
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
                            VSpacerHi(24f)
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                androidx.compose.material.Icon(
                                    painter = painterResource(id = R.drawable.on_light),
                                    contentDescription = "light",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(54.dp)
                                )
                            }
                            VSpacerHi(16f)
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
    }
}

@Composable
fun ScanOverlayWithCorners() {
    val cornerLength = 50.dp
    val cornerWidth = 3.dp
    val scanFrameSize = 250.dp
    val borderRadius = 12.dp

    Box(
        modifier = Modifier.size(scanFrameSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = cornerWidth.toPx()
            val cornerLengthPx = cornerLength.toPx()
            val size = scanFrameSize.toPx()
            val radiusPx = borderRadius.toPx()

            // 左上角
            drawLine(
                color = PrimaryColor,
                start = Offset(radiusPx, 0f),
                end = Offset(cornerLengthPx, 0f),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = PrimaryColor,
                start = Offset(0f, radiusPx),
                end = Offset(0f, cornerLengthPx),
                strokeWidth = strokeWidth
            )
            drawArc(
                color = PrimaryColor,
                startAngle = 180f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(0f, 0f),
                size = Size(radiusPx * 2, radiusPx * 2),
                style = Stroke(width = strokeWidth)
            )

            drawLine(
                color = PrimaryColor,
                start = Offset(size - cornerLengthPx, 0f),
                end = Offset(size - radiusPx, 0f),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = PrimaryColor,
                start = Offset(size, radiusPx),
                end = Offset(size, cornerLengthPx),
                strokeWidth = strokeWidth
            )
            drawArc(
                color = PrimaryColor,
                startAngle = 270f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(size - radiusPx * 2, 0f),
                size = Size(radiusPx * 2, radiusPx * 2),
                style = Stroke(width = strokeWidth)
            )

            drawLine(
                color = PrimaryColor,
                start = Offset(0f, size - cornerLengthPx),
                end = Offset(0f, size - radiusPx),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = PrimaryColor,
                start = Offset(radiusPx, size),
                end = Offset(cornerLengthPx, size),
                strokeWidth = strokeWidth
            )
            drawArc(
                color = PrimaryColor,
                startAngle = 90f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(0f, size - radiusPx * 2),
                size = Size(radiusPx * 2, radiusPx * 2),
                style = Stroke(width = strokeWidth)
            )

            drawLine(
                color = PrimaryColor,
                start = Offset(size, size - cornerLengthPx),
                end = Offset(size, size - radiusPx),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = PrimaryColor,
                start = Offset(size - radiusPx, size),
                end = Offset(size - cornerLengthPx, size),
                strokeWidth = strokeWidth
            )
            drawArc(
                color = PrimaryColor,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(size - radiusPx * 2, size - radiusPx * 2),
                size = Size(radiusPx * 2, radiusPx * 2),
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

@Composable
fun CameraPermissionContent(onRequestPermission: () -> Unit, currentLanguage: LanguageModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = "Camera",
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = T("ls_Cpirtstqc", currentLanguage),
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = T("ls_Pecpis",currentLanguage) + "!",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onRequestPermission,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    T("ls_Grantcamerapermission",currentLanguage) + "!",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ScanProcessingContent(
    result: String,
    onCancel: () -> Unit,
    currentLanguage: LanguageModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(64.dp),
                strokeWidth = 4.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = T("ls_Processingscanresult",currentLanguage),
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = onCancel,
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    T("ls_Cancel",currentLanguage),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ScanSuccessContent(
    result: String,
    message: String,
    onScanAgain: () -> Unit,
    currentLanguage: LanguageModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = "Success",
                tint = PrimaryColor,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onScanAgain,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    T("ls_Continuescanning",currentLanguage),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ScanResultContent(
    result: String,
    onScanAgain: () -> Unit,
    onRetry: () -> Unit,
    hasError: Boolean = false,
    currentLanguage: LanguageModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = if (hasError) "Error" else "Info",
                tint = PrimaryColor,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (hasError) {
                    OutlinedButton(
                        onClick = onRetry,
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            T("ls_Retry",currentLanguage),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Button(
                    onClick = onScanAgain,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text(
                        T("ls_Rescan",currentLanguage),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}