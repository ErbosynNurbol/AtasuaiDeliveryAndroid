package kz.atasuai.delivery.ui.activities.profile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ThemeMode
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.common.chat.ChatBotViewModelFactory
import kz.atasuai.delivery.common.chat.ChatDataStore
import kz.atasuai.delivery.common.chat.ChatMessage
import kz.atasuai.delivery.common.chat.MessageType
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.chat.ChatBotTextFiled
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiScreen
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.theme.ProposalNameStyle
import kz.atasuai.delivery.ui.viewmodels.chat.ChatBotViewModel
import kz.atasuai.market.models.LanguageModel

val Context.chatDataStore: DataStore<Preferences> by preferencesDataStore(name = "chat_datastore")
class SupportActivity: ComponentActivity(){
    private val themeManager by lazy { AtasuaiApp.themeManager }
    private val chatRepository by lazy {
        ChatDataStore(this.chatDataStore)
    }
    val viewModel: ChatBotViewModel by viewModels{ChatBotViewModelFactory(chatRepository)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by themeManager.themeMode.collectAsState()
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            }

            val context = LocalContext.current
            val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
            AtasuaiTheme(darkTheme = darkTheme) {
                AtasuaiScreen(
                    applySystemBarsPadding = false,
                    statusBarDarkIcons = true,
                    navigationBarDarkIcons = true,
                    navigationBarColor = AtasuaiTheme.colors.welcomeBac
                ) { screenModifier ->
                    SupportScreen(viewModel,currentLanguage,context,modifier = screenModifier)
                }
            }
        }
    }
}

@Composable
fun SupportScreen(viewModel: ChatBotViewModel, currentLanguage: LanguageModel, context: Context, modifier: Modifier = Modifier){
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val messages by viewModel.messages.collectAsState()
    val inputText by viewModel.inputText.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    Column(modifier=modifier.fillMaxSize()
        .noRippleClickable {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
        .background(AtasuaiTheme.colors.background)
        .navigationBarsPadding()
        .imePadding()
    ){
        Column(modifier=Modifier.fillMaxWidth()
            .height(100.dp)
            .background(AtasuaiTheme.colors.welcomeBac)
            .padding(horizontal = 20.dp)
        ){
            VSpacerHi(54f)
            Box(modifier=Modifier.fillMaxWidth()
                ,
                contentAlignment = Alignment.CenterStart
            ){
                Icon(
                    painter = painterResource(id = R.drawable.back_left_icon),
                    contentDescription = "arrow down",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                        .align(Alignment.CenterStart)
                        .noRippleClickable {
                            viewModel.onBack(context)
                        }
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                    ,verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.support_chat_icon),
                        contentDescription = "shop_share_icon",
                        modifier=Modifier
                            .padding(0.dp)
                            .size(25.dp)
                    )
                    VSpacerWi(10f)
                    Text(
                        text = T("ls_Support2",currentLanguage),
                        style = AtasuaiTheme.typography.ProposalNameStyle
                    )

                }
            }

        }
        Column(modifier=Modifier.weight(1f).fillMaxWidth()
        ){
            Column(modifier = Modifier.weight(0.85f).fillMaxWidth()) {
                VSpacerHi(16f)
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = responsiveWidth(20f))
                    ,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(messages) { message ->
                        ChatMessageItem(message = message)
                    }

                    if (isLoading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .wrapContentHeight()
                                        .background(
                                            color = Color(0x66DADADA),
                                            shape = RoundedCornerShape(size = 5.dp)
                                        )
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = "...",
                                        style = TextStyle(
                                            fontSize = responsiveFontSize(13f),
                                            lineHeight = responsiveFontSize(13f),
                                            fontFamily = PrimaryFontFamily,
                                            fontWeight = FontWeight(500),
                                            color = Color(0xFF3E3E3E),
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.weight(0.15f).fillMaxWidth()
                    .background(AtasuaiTheme.colors.welcomeBac)
                ,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = responsiveWidth(20f)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.upload_file_icon),
                        contentDescription = "logo",
                        modifier = Modifier.size(30.dp)
                    )
                    VSpacerWi(10f)
                    ChatBotTextFiled(
                        modifier = Modifier.weight(1f),
                        text = inputText,
                        onTextChange = viewModel::updateInputText,
                        placeholderText = "Сұрағыңызды жазыңыз...",
                        isError = false,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    )
                    VSpacerWi(10f)
                    Icon(
                        painter = painterResource(id = R.drawable.send_sms_icon),
                        contentDescription = "send",
                        modifier = Modifier
                            .size(54.dp)
                            .noRippleClickable {
                                if (!isLoading && inputText.isNotEmpty()) {
                                    viewModel.sendMessage()
                                }
                            },
                        tint = Color.Unspecified
                    )
                }
            }
        }

    }
}


@Composable
fun ChatMessageItem(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.type == MessageType.USER)
            Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        val painter = if (message.type == MessageType.USER) painterResource(id = R.drawable.avatar) else painterResource(id = R.drawable.chat_logo)

        if (message.type == MessageType.USER){
            Column(
                modifier = Modifier
                    .widthIn(max = 230.dp)
                    .wrapContentHeight()
                    .background(
                        color = if (message.type == MessageType.USER)
                            Color(0x1A3C49EE) else Color(0x66DADADA),
                        shape = RoundedCornerShape(size = 5.dp)
                    )
                    .padding(10.dp)
            ) {
                if(message.type == MessageType.BOT){
                    Text(
                        text = "Admin",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.2.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF163063),
                        )
                    )
                    VSpacerHi(3f)
                }
                Text(
                    text = message.content,
                    style = TextStyle(
                        fontSize = responsiveFontSize(13f),
                        lineHeight = responsiveFontSize(13f),
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = if (message.type == MessageType.USER)
                            Color(0xFF3C49EE) else Color(0xFF3E3E3E),
                    )
                )
                VSpacerHi(3f)
                Text(
                    text = "08:15",
                    style = TextStyle(
                        fontSize = 10.sp,
                        lineHeight = 13.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF999999),
                    )
                )
            }
            VSpacerWi(4f)
            Image(
                painter = painter,
                contentDescription = "logo",
                modifier = Modifier.size(38.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }else{
            Image(
                painter = painter,
                contentDescription = "logo",
                modifier = Modifier.size(38.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            VSpacerWi(4f)
            Column(
                modifier = Modifier
                    .widthIn(max = 230.dp)
                    .wrapContentHeight()
                    .background(
                        color = if (message.type == MessageType.USER)
                            Color(0x1A3C49EE) else Color(0x66DADADA),
                        shape = RoundedCornerShape(size = 5.dp)
                    )
                    .padding(10.dp)
            ) {
                if(message.type == MessageType.BOT){
                    Text(
                        text = "Admin",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.2.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF163063),
                        )
                    )
                    VSpacerHi(3f)
                }
                Text(
                    text = message.content,
                    style = TextStyle(
                        fontSize = responsiveFontSize(13f),
                        lineHeight = responsiveFontSize(13f),
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = if (message.type == MessageType.USER)
                            Color(0xFF3C49EE) else Color(0xFF3E3E3E),
                    )
                )
                VSpacerHi(3f)
                Text(
                    text = "08:15",
                    style = TextStyle(
                        fontSize = 10.sp,
                        lineHeight = 13.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF999999),
                    )
                )
            }
        }

    }
}