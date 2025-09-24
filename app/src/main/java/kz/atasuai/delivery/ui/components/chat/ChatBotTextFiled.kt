package kz.atasuai.delivery.ui.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotTextFiled(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText: String,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var textState by remember { mutableStateOf(text) }
    LaunchedEffect(text) {
        textState = text
    }
    val textStyle = TextStyle(
        fontSize = responsiveFontSize(16f),
        lineHeight = responsiveFontSize(26f),
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(500),
        color = MaterialTheme.colorScheme.onBackground,
    )
    TextField(
        value = textState,
        onValueChange = { input ->
            textState = input
            onTextChange(textState)
        },
        placeholder = {
            Text(
                text = placeholderText,
                color =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 26.sp,
                    textAlign = TextAlign.Start
                )
            )
        },
        textStyle = textStyle,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF2F2F2), shape = RoundedCornerShape(size = 110.dp))
            .height(54.dp),
        shape = RoundedCornerShape(110.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color(0xFFF2F2F2),
            unfocusedContainerColor = Color(0xFFF2F2F2),
            disabledContainerColor = Color.White.copy(alpha = 0.5f),
            errorContainerColor = if (isError) Color.Red.copy(alpha = 0.1f) else MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.onBackground,
            errorCursorColor = Color.Red,
            selectionColors = TextSelectionColors(
                handleColor = Color.Black,
                backgroundColor = Color.LightGray.copy(alpha = 0.4f)
            ),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            unfocusedPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            disabledPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            errorPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        ),
        keyboardOptions = keyboardOptions
    )
}