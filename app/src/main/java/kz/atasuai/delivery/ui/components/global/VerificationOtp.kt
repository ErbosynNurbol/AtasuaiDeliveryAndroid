package kz.atasuai.delivery.ui.components.global

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpInput(
    otpCode: String,
    currentFocus: Int,
    onOtpChange: (String) -> Unit,
    onFocusChange: (Int) -> Unit,
    error: Boolean = false,
    isAddPhone: Boolean = false,
    onErrorCleared: () -> Unit = {}
) {
    val otpLength = 4
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 0 until otpLength) {
            OtpDigit(
                digit = otpCode.getOrNull(i)?.toString() ?: "",
                onDigitChange = { newDigit ->
                    if (error) onErrorCleared()
                    handleDigitInput(
                        currentOtp = otpCode,
                        position = i,
                        newDigit = newDigit,
                        onOtpChange = onOtpChange,
                        onFocusChange = onFocusChange,
                        focusManager = focusManager,
                        otpLength = otpLength
                    )
                },
                onDigitDelete = { currentEmpty ->
                    handleDigitDelete(
                        currentOtp = otpCode,
                        position = i,
                        currentEmpty = currentEmpty,
                        onOtpChange = onOtpChange,
                        onFocusChange = onFocusChange
                    )
                },
                focusRequester = focusRequesters[i],
                isAddPhone = isAddPhone,
                error = error
            )
        }
    }

    // 初始焦点与键盘
    LaunchedEffect(Unit) {
        onFocusChange(0)
        keyboardController?.show()
    }

    // 基于 ViewModel currentFocus 状态管理焦点
    LaunchedEffect(currentFocus) {
        delay(50) // 给 UI 一点时间
        if (currentFocus in 0 until otpLength) {
            focusRequesters[currentFocus].requestFocus()
        }
    }

    // 错误时重置
    LaunchedEffect(error) {
        if (error) {
            onOtpChange("")
            onFocusChange(0)
            keyboardController?.show()
            delay(100)
            onErrorCleared()
        }
    }
}

@Composable
fun OtpDigit(
    digit: String,
    onDigitChange: (String) -> Unit,
    onDigitDelete: (currentEmpty: Boolean) -> Unit,
    focusRequester: FocusRequester,
    isAddPhone: Boolean = false,
    error: Boolean = false
) {
    var textFieldValue by remember(digit) {
        mutableStateOf(TextFieldValue(text = digit, selection = TextRange(digit.length)))
    }

    // 如果 onPreviewKeyEvent 已处理删除，这一轮 onValueChange 不再重复判定删除
    var suppressNextDeleteFromOnValueChange by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = when {
                    error -> Color.Red
                    isAddPhone -> Color(0x382674E9)
                    else -> Color(0xFFFFD9C0)
                },
                shape = RoundedCornerShape(10.dp)
            )
            .padding(1.dp)
            .width(65.dp)
            .height(60.dp)
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { input ->
                val newText = input.text
                val oldText = textFieldValue.text

                // 兜底：软键盘 Backspace 只会导致长度变短
                if (!suppressNextDeleteFromOnValueChange && newText.length < oldText.length) {
                    if (oldText.isNotEmpty() && newText.isEmpty()) {
                        // 有值 -> 空：删除当前
                        textFieldValue = TextFieldValue("", TextRange(0))
                        onDigitDelete(false)
                        return@OutlinedTextField
                    } else if (oldText.isEmpty() && newText.isEmpty()) {
                        // 空 -> 空：退到前一个
                        onDigitDelete(true)
                        return@OutlinedTextField
                    }
                }
                suppressNextDeleteFromOnValueChange = false

                // 输入数字
                when {
                    newText.isEmpty() -> {
                        textFieldValue = TextFieldValue("", TextRange(0))
                    }
                    newText.all { it.isDigit() } && newText.length <= 1 -> {
                        val singleChar = newText.take(1)
                        textFieldValue = TextFieldValue(singleChar, TextRange(1))
                        onDigitChange(singleChar)
                    }
                    newText.length > 1 && newText.all { it.isDigit() } -> {
                        // 多位粘贴：这里只取第一位，剩下交给 handleDigitInput
                        val first = newText.take(1)
                        textFieldValue = TextFieldValue(first, TextRange(1))
                        onDigitChange(newText) // 把完整文本交上层
                    }
                }
            },
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = if (digit.isEmpty())
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                else
                    MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .onPreviewKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace) {
                        if (textFieldValue.text.isEmpty()) {
                            // 空格位：退到前一个
                            onDigitDelete(true)
                        } else {
                            // 有值：删除当前
                            textFieldValue = TextFieldValue("", TextRange(0))
                            onDigitDelete(false)
                        }
                        suppressNextDeleteFromOnValueChange = true
                        true
                    } else false
                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation.None,
            singleLine = true,
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            )
        )
    }
}

private fun handleDigitInput(
    currentOtp: String,
    position: Int,
    newDigit: String,
    onOtpChange: (String) -> Unit,
    onFocusChange: (Int) -> Unit,
    focusManager: FocusManager,
    otpLength: Int
) {
    if (newDigit.isEmpty()) return

    // 粘贴多位
    if (newDigit.length > 1) {
        val digits = newDigit.filter { it.isDigit() }.take(otpLength)
        val prefix = currentOtp.take(position)
        val merged = (prefix + digits).take(otpLength)
        onOtpChange(merged)

        val nextFocus = (position + digits.length).coerceAtMost(otpLength - 1)
        if (merged.length < otpLength) {
            onFocusChange(nextFocus)
        } else {
            focusManager.clearFocus()
        }
        return
    }

    // 单字符
    val single = newDigit.firstOrNull()?.takeIf { it.isDigit() } ?: return
    val newOtp = buildString {
        append(currentOtp.padEnd(otpLength, ' '))
    }.toCharArray().also { arr ->
        arr[position] = single
    }.concatToString().trimEnd().take(otpLength)

    onOtpChange(newOtp)

    if (position < otpLength - 1 && newOtp.length < otpLength) {
        onFocusChange(position + 1)
    } else {
        focusManager.clearFocus()
    }
}

private fun handleDigitDelete(
    currentOtp: String,
    position: Int,
    currentEmpty: Boolean,
    onOtpChange: (String) -> Unit,
    onFocusChange: (Int) -> Unit
) {
    if (!currentEmpty) {
        // 有值：删当前
        val newOtp = buildString {
            append(currentOtp.take(position))
            if (position + 1 < currentOtp.length) {
                append(currentOtp.drop(position + 1))
            }
        }
        onOtpChange(newOtp)
    } else if (position > 0) {
        // 空位：退到前一个
        onFocusChange(position - 1)
    }
}