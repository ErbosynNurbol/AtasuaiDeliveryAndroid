package kz.atasuai.delivery.ui.components.global

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VSpacerHi(dp:Float){
    Spacer(modifier= Modifier.height(responsiveWidth(dp)))
}

@Composable
fun VSpacerWi(dp:Float){
    Spacer(modifier= Modifier.width(responsiveWidth(dp)))
}