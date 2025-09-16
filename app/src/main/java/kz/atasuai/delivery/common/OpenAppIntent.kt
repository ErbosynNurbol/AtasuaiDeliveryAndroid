package kz.atasuai.delivery.common

import android.content.Context
import android.content.Intent
import android.net.Uri


fun openFacebook(context: Context, facebookUrl: String) {
    val intent = try {
        context.packageManager.getPackageInfo("com.facebook.katana", 0)
        Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$facebookUrl"))
    } catch (e: Exception) {
        Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
    }
    context.startActivity(intent)
}

fun openInstagram(context: Context, instagramUrl: String) {
    val intent = try {
        context.packageManager.getPackageInfo("com.instagram.android", 0)
        val username = instagramUrl.substringAfterLast("/").removeSuffix("/")
        Intent(Intent.ACTION_VIEW, Uri.parse("instagram://user?username=$username"))
    } catch (e: Exception) {
        Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
    }
    context.startActivity(intent)
}

fun openTelegram(context: Context, telegramData: String) {
    val intent = if (telegramData.startsWith("+")) {
        try {
            context.packageManager.getPackageInfo("org.telegram.messenger", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?phone=${telegramData.replace("+", "")}"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/${telegramData}"))
        }
    } else {
        try {
            context.packageManager.getPackageInfo("org.telegram.messenger", 0)
            val username = telegramData.substringAfterLast("/").removeSuffix("/")
            Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=$username"))
        } catch (e: Exception) {
            Intent(Intent.ACTION_VIEW, Uri.parse(telegramData))
        }
    }
    context.startActivity(intent)
}

fun openWhatsApp(context: Context, phoneNumber: String) {
    val intent = try {
        context.packageManager.getPackageInfo("com.whatsapp", 0)
        Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber"))
    } catch (e: Exception) {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber"))
    }
    context.startActivity(intent)
}

fun openVK(context: Context, vkProfileId: String, vkUrl: String) {
    val intent = try {
        context.packageManager.getPackageInfo("com.vkontakte.android", 0)
        Intent(Intent.ACTION_VIEW, Uri.parse("vk://vk.com/id$vkProfileId"))
    } catch (e: Exception) {
        Intent(Intent.ACTION_VIEW, Uri.parse(vkUrl))
    }
    context.startActivity(intent)
}

fun openTwitter(context: Context, twitterUsername: String, twitterUrl: String) {
    val intent = try {
        context.packageManager.getPackageInfo("com.twitter.android", 0)
        Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=$twitterUsername"))
    } catch (e: Exception) {
        Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl))
    }
    context.startActivity(intent)
}

fun openTikTok(context: Context, tiktokUrl: String) {
    val intent = try {
        context.packageManager.getPackageInfo("com.zhiliaoapp.musically", 0)
        Intent(Intent.ACTION_VIEW, Uri.parse(tiktokUrl))
    } catch (e: Exception) {
        Intent(Intent.ACTION_VIEW, Uri.parse(tiktokUrl))
    }
    context.startActivity(intent)
}

fun openDialer(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
    context.startActivity(intent)
}

fun open2GISNavigation(context: Context,latitude:Double,longitude:Double){
    val uriString = "dgis://2gis.ru/routeSearch/rsType/car/to/$longitude,$latitude"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        val webUriString = "https://2gis.ru/routeSearch/rsType/car/to/$longitude,$latitude"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUriString)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(webIntent)
    }
}


fun open2GISFromUrl(context: Context, map2gisUrl: String) {
    try {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(map2gisUrl.replace("https://", "dgis://")))

        if (appIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(appIntent)
        } else {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(map2gisUrl))
            context.startActivity(webIntent)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun openYouTube(context: Context, youtubeUrl: String) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)).apply {
        setPackage("com.google.android.youtube")
    }

    try {
        context.startActivity(appIntent)
    } catch (e: Exception) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
        context.startActivity(webIntent)
    }
}