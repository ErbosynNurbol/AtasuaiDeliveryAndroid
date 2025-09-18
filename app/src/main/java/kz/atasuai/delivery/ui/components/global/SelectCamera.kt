package kz.atasuai.delivery.ui.components.global


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.MediaPermissionScreen
import kz.atasuai.delivery.common.TakeFullResPhotoContract
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.welcome.DocumentType
import kz.atasuai.delivery.ui.viewmodels.welcome.RegisDeliveryViewModel
import kz.atasuai.market.models.LanguageModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCamera(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    viewModel: RegisDeliveryViewModel,
    documentType: DocumentType,
    currentLanguage: LanguageModel,
    context: Context,
) {
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }

    // 图片剪裁处理
    val startImageCrop = rememberImageCropper ({ croppedUri ->
        if (croppedUri != null) {
            viewModel.updateCroppedImageUri(croppedUri, context, documentType)
        }
        onDismissRequest()
    },isAvatar = false)

    if (showDelete) {
        HasPhoneModal(
            onDismissRequest = {
                showDelete = false
                onDismissRequest()
            },
            onChange = {
                viewModel.deleteResource(context)
                showDelete = false
                onDismissRequest()
            },
            context = context,
            message = T("ls_Deletephoto", currentLanguage),
            hint = T("ls_Aysywtd", currentLanguage),
            isDelete = true
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = TakeFullResPhotoContract()
    ) { uri: Uri? ->
        if (uri != null) {
            startImageCrop(uri)
        } else {
            onDismissRequest()
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startImageCrop(uri) // 启动剪裁而不是直接更新
        } else {
            onDismissRequest()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        }
    }

    val legacyGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            startImageCrop(uri) // 启动剪裁而不是直接更新
        } else {
            onDismissRequest()
        }
    }

    if (showPermissionDialog) {
        MediaPermissionScreen { granted ->
            showPermissionDialog = false
            if (granted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                } else {
                    legacyGalleryLauncher.launch("image/*")
                }
            }
        }
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier.padding(0.dp),
                width = 65.dp,
                height = 5.dp,
                color = colorResource(id = R.color.switch_bac_co),
                shape = RoundedCornerShape(10.dp)
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 13.dp)
        ) {
            Row(
                modifier = Modifier
                    .noRippleClickable {
                        try {
                            cameraLauncher.launch(null)
                        } catch (e: SecurityException) {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        } catch (e: Exception) {
                            Log.e("Camera", "Failed to launch camera", e)
                        }
                    }
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = T("ls_Camera", currentLanguage))
                Image(
                    painter = painterResource(id = R.drawable.camera_icon),
                    contentDescription = T("ls_Camera", currentLanguage),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        } else {
                            val hasPermission = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED

                            if (hasPermission) {
                                legacyGalleryLauncher.launch("image/*")
                            } else {
                                showPermissionDialog = true
                            }
                        }
                    }
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = T("ls_Uploadfromgallery", currentLanguage))
                Image(
                    painter = painterResource(id = R.drawable.potos_icon),
                    contentDescription = T("ls_Camera", currentLanguage),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
//            Row(
//                modifier = Modifier
//                    .noRippleClickable {
//                        showDelete = true
//                    }
//                    .fillMaxWidth()
//                    .padding(18.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = T("ls_Delete", currentLanguage),
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        lineHeight = 16.sp,
//                        fontFamily = PrimaryFontFamily,
//                        fontWeight = FontWeight(400),
//                        color = Color(0xFFF93C2F),
//                    )
//                )
//                Image(
//                    painter = painterResource(id = R.drawable.delete_icon),
//                    contentDescription = "Delete photo",
//                    modifier = Modifier.size(20.dp)
//                )
//            }
        }
        Spacer(modifier = Modifier.height(54.dp))
    }
}