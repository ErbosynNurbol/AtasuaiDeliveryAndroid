package kz.atasuai.market.ui.components.welcome

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberField(
    phoneNumber: String,
    placeholderText:String = "",
    confirmError:Boolean = false,
    onPhoneNumberChange: (String) -> Unit,
    onLogin: () -> Unit,
) {
    var phoneNumberState by remember { mutableStateOf(phoneNumber) }
    LaunchedEffect(phoneNumber) {
        phoneNumberState = phoneNumber
    }
    TextField(
        value = phoneNumberState,
        onValueChange = { input ->
            phoneNumberState = input.filter { it.isDigit() }.take(10)
            onPhoneNumberChange(phoneNumberState)
        },

        placeholder = {
            Text(
                text = placeholderText,
                color =  AtasuaiTheme.colors.placeholderCo,
                style = TextStyle(
                    fontSize = responsiveFontSize(14f),
                    fontWeight = FontWeight(300),
                    lineHeight =responsiveFontSize(14f),
                    textAlign = TextAlign.Start
                )
            )
        },
        textStyle = TextStyle(
            fontSize = responsiveFontSize(14f),
            lineHeight = responsiveFontSize(14f),
            fontFamily = PrimaryFontFamily,
            fontWeight = FontWeight(500),
            color = AtasuaiTheme.colors.textPrimary,
        ),
        modifier = Modifier
            .border(width = 1.dp, color = if(confirmError) Color(0xFFF03024)  else Color(0xFFE8EBF1),shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(responsiveWidth(50f)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color(0xFFFFFFFF),
            unfocusedContainerColor = Color(0xFFFFFFFF),
            disabledContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.5f),
            errorContainerColor = MaterialTheme.colorScheme.tertiary,
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
            focusedPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            unfocusedPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            disabledPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f).copy(alpha = 0.5f),
            errorPlaceholderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onLogin()
            }
        ),
        visualTransformation = PhoneNumberVisualTransformation()
    )
}

fun formatPhoneNumber(input: String): String {
    val digits = input.filter { it.isDigit() }
    return buildString {
        for (i in digits.indices) {
            when (i) {
                0 -> append("(${digits[i]}")
                2 -> append("${digits[i]}) ")
                5 -> append("${digits[i]} ")
                else -> append(digits[i])
            }
        }
    }
}

class PhoneNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = formatPhoneNumber(text.text)
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset == 0 -> 0
                    offset <= 3 -> offset + 1
                    offset <= 6 -> offset + 3
                    offset <= 10 -> offset + 4
                    else -> formattedText.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset == 0 -> 0
                    offset <= 1 -> 0
                    offset <= 4 -> offset - 1
                    offset <= 8 -> offset - 3
                    offset <= 13 -> offset - 4
                    else -> text.length
                }
            }
        }
        return TransformedText(AnnotatedString(formattedText), offsetMapping)
    }
}
