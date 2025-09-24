package kz.atasuai.delivery.ui.components.global

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
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.CargoPlaceStyle
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.models.LanguageModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextFiled(
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
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(400),
        color = AtasuaiTheme.colors.textPrimary,
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
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .border(width = 1.dp, color = Color(0xFFE8EAEE), shape = RoundedCornerShape(size = 10.dp))
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
            cursorColor = Color.Black,
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
//            Image(
//                painter = painterResource(id = R.drawable.clear_icon),
//                contentDescription = "clear",
//                modifier = Modifier
//                    .size(28.dp)
//                    .noRippleClickable {
//                        onTextChange("")
//                    }
//            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "clear",
                tint = PrimaryColor,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable {
                        onChange()
                    }
            )

        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
    )
}
