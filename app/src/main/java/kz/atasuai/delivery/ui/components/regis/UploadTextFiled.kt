package kz.atasuai.delivery.ui.components.regis

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.CardSide
import kz.atasuai.delivery.common.MediaPermissionScreen
import kz.atasuai.delivery.common.ToastHelper
import kz.atasuai.delivery.common.ToastType
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.SelectCamera
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.welcome.DocumentType
import kz.atasuai.delivery.ui.viewmodels.welcome.RegisDeliveryViewModel
import kz.atasuai.market.models.LanguageModel


@Composable
fun UploadTextField(
    viewModel: RegisDeliveryViewModel,
    documentType: DocumentType,
    currentLanguage: LanguageModel,
    context: Context,
) {
    var showDialog by remember { mutableStateOf(false) }
    var isGrantedBy by remember { mutableStateOf(false) }
    var isCheck by remember { mutableStateOf(false) }

    val documentData by viewModel.documentData.collectAsState()

    val perms = remember {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            )
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
            else -> arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        val granted = perms.all { perm -> resultMap[perm] == true }
        if (granted) {
            showDialog = true
        } else {
            ToastHelper.showMessage(
                context,
                ToastType.WARNING,
                T("ls_Pgtrptutfp", currentLanguage)
            )
        }
    }

    if (isCheck) {
        MediaPermissionScreen { isGranted ->
            isGrantedBy = isGranted
        }
    }

    if (showDialog) {
        SelectCamera(
            onDismissRequest = { showDialog = false },
            viewModel = viewModel,
            documentType = documentType,
            currentLanguage = currentLanguage,
            context = context
        )
    }

    val croppedImageUri = when (documentType) {
        DocumentType.IDCardFront -> documentData.idCardFront
        DocumentType.IDCardBack -> documentData.idCardBack
        DocumentType.LicenseFront -> documentData.licenseFront
        DocumentType.LicenseBack -> documentData.licenseBack
        DocumentType.CarIDFront -> documentData.carIDFront
        DocumentType.CarIDBack -> documentData.carIDBack
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(responsiveWidth(211f))
                .background(color = Color(0xFFF9FBFC), shape = RoundedCornerShape(10.dp))
                .border(1.dp, AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(model = croppedImageUri ?: "")

            if (croppedImageUri != null && croppedImageUri != Uri.EMPTY) {
                Box {
                    Image(
                        painter = painter,
                        contentDescription = "Selected image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 13.dp)
                            .background(
                                color = Color(0x0DC4C4C4),
                                shape = RoundedCornerShape(size = 10.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(23.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(39.dp)
                                .background(Color.White, shape = RoundedCornerShape(size = 6.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.camera_upload_icon),
                                contentDescription = "Camera Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.Center)
                                    .clickable {
                                        isCheck = true
                                        if (isGrantedBy) {
                                            showDialog = true
                                        } else {
                                            permissionLauncher.launch(perms)
                                        }
                                    }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(39.dp)
                                .background(Color.White, shape = RoundedCornerShape(size = 6.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.delete_icon),
                                contentDescription = "Close Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.Center)
                                    .clickable { viewModel.removeDocument(documentType) }
                            )
                        }
                    }
                }
            } else {
                Button(
                    onClick = {
                        isCheck = true
                        if (isGrantedBy) {
                            showDialog = true
                        } else {
                            permissionLauncher.launch(perms)
                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(Color(0xFFECF2FF)),
                    modifier = Modifier
                        .width(195.dp)
                        .height(44.dp)
                        .padding(0.dp)

                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camera_upload_icon),
                            contentDescription = "upload",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = T("ls_Snappicture", currentLanguage),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = PrimaryFontFamily,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF4F89FC),
                            )
                        )
                    }
                }
            }
        }
    }
}