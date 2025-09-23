package kz.atasuai.delivery.ui.components.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@Composable
fun ConfirmLoginModal(
    onDismissRequest: () -> Unit,
    onChange: () -> Unit,
    context: Context,
    message: String,
    hint:String,
) {
    val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .width(331.dp)
                .height(200.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 30.dp))
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .background(color = Color(0xFFFFFFFF))
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = message,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF05151E),
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = hint,
                        style = TextStyle(
                            fontSize = 13.sp,
                            lineHeight = 18.2.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF4A4A4A),
                        )
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                        ){
                        Button(
                            onClick = {
                                onDismissRequest()
                            },
                            shape = RoundedCornerShape(30),
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .width(125.dp)
                                .border(width = 1.dp, color = Color(0xFFB1B1B1), shape = RoundedCornerShape(size = 5.dp))
                            .height(42.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(size = 5.dp))
                                .padding(0.dp),
                        ) {
                            val text1 = T("ls_Cancel",currentLanguage)
                            Text(
                                text = text1,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = PrimaryFontFamily,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF4A4A4A),
                                )
                            )

                        }
                        val rightBtnColor =  PrimaryColor
                        Button(
                            onClick = {
                                onChange()
                                onDismissRequest()
                            },
                            shape = RoundedCornerShape(30),
                            colors = ButtonDefaults.buttonColors(rightBtnColor),
                            modifier = Modifier
                                .width(115.dp)
                                .height(42.dp)
                                .background(color = rightBtnColor, shape = RoundedCornerShape(size = 5.dp))
                                .padding(0.dp),
                        ) {
                            val text = T("ls_Login",currentLanguage)
                            Text(
                                text = text,
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontFamily = PrimaryFontFamily,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFFFFFFF),
                                )
                            )

                        }

                    }
                }
            }
        }
    }
}