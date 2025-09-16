package kz.atasuai.market.ui.components.welcome

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText:String = "",
    confirmError:Boolean = false,
    modifier: Modifier = Modifier
) {
    var textState by remember { mutableStateOf(text) }
    val  textStyle = TextStyle(
        fontSize = responsiveFontSize(14f),
        lineHeight = responsiveFontSize(14f),
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(500),
        color = AtasuaiTheme.colors.textPrimary,
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
                color =  AtasuaiTheme.colors.placeholderCo,
                style = TextStyle(
                    fontSize = responsiveFontSize(14f),
                    fontWeight = FontWeight(300),
                    lineHeight = responsiveFontSize(14f),
                    textAlign = TextAlign.Start
                )
            )
        },
        textStyle = textStyle,
        modifier = modifier
            .border(width = 1.dp, color = if(confirmError) Color(0xFFF03024)  else Color(0xFFE8EBF1),shape = RoundedCornerShape(10.dp))
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White.copy(alpha = 0.5f),
            errorContainerColor = if (confirmError) Color.Red.copy(alpha = 0.1f) else MaterialTheme.colorScheme.tertiary,
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
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        )
    )
}