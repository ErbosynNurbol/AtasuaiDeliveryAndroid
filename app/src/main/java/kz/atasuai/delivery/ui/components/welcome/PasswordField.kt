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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    placeholderText: String,
    confirmError:Boolean = false,
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
) {
    var passwordState by remember { mutableStateOf(password) }
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = passwordState,
        onValueChange = { input ->
            passwordState = input.take(20)
            onPasswordChange(passwordState)
        },
        placeholder = {
            Text(
                text = placeholderText,
                color =  Color(0xFFCBCBCB),
                style = TextStyle(
                    fontSize = responsiveFontSize(14f),
                    fontWeight = FontWeight(500),
                    lineHeight = responsiveFontSize(14f),
                    textAlign = TextAlign.Start
                )
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(500),
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(responsiveWidth(50f))
            .border(width = 1.dp, color =if(confirmError) Color(0xFFF03024)  else Color(0xFFE8EBF1), shape = RoundedCornerShape(size = 10.dp))
        ,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
            errorTextColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White.copy(alpha = 0.5f),
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
            focusedPlaceholderColor = Color(0xFFB3B3B3),
            unfocusedPlaceholderColor = Color(0xFFB3B3B3),
            disabledPlaceholderColor = Color(0xFFB3B3B3).copy(alpha = 0.5f),
            errorPlaceholderColor = Color(0xFFB3B3B3)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onLogin()
            }
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else AsteriskVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                painterResource(id = R.drawable.show_password)
            else painterResource(id = R.drawable.hidden_password)

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = image, contentDescription = if (passwordVisible) "Hide password" else "Show password", tint = Color.Unspecified)
            }
        }
    )
}

class AsteriskVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = AnnotatedString("*".repeat(text.length))
        return TransformedText(transformedText, OffsetMapping.Identity)
    }
}