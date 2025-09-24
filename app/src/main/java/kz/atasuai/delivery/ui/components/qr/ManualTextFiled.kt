package kz.atasuai.delivery.ui.components.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.CargoPlaceStyle
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.models.LanguageModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualTextFiled(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText: String,
    currentLanguage: LanguageModel,
    isError: Boolean = false,
    onChange:()->Unit,
) {
    var textState by remember { mutableStateOf(text) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val textStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 20.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(600),
        color = AtasuaiTheme.colors.textSecondary,
    )
    TextField(
        value = textState,
        onValueChange = { input ->
            if (input.length <= 255) {
                textState = input
                onTextChange(input)
            }
        },
        placeholder = {
            Text(
                text = T(placeholderText, currentLanguage),
                style = MaterialTheme.typography.CargoPlaceStyle
            )
        },
        textStyle = textStyle,
        modifier = modifier
            .fillMaxWidth()
    ,
        shape = RoundedCornerShape(10.dp),
        keyboardActions = KeyboardActions(
            onSearch = {
                onChange()
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color(0xFFFFFFFF),
            unfocusedContainerColor =Color(0xFFFFFFFF),
            disabledContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.5f),
            errorContainerColor = if (isError) Color.Red.copy(alpha = 0.1f) else Color.White,
            cursorColor = PrimaryColor,
            errorCursorColor = Color.Red,
            selectionColors = TextSelectionColors(
                handleColor = Color.Black,
                backgroundColor = Color.LightGray.copy(alpha = 0.4f)
            ),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedPlaceholderColor = Color.Transparent.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            errorPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        ),
        trailingIcon = {
        },
        leadingIcon = {
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
    )
}
