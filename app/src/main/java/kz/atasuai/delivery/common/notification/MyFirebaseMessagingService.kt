package kz.atasuai.delivery.common.notification

//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        AtasuaiApp.setCurrentAndroidToken(token)
//
//    }
//    var articleId :Int = 0
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//        val articleIdString = remoteMessage.data["articleId"]
//        articleId = try {
//            articleIdString?.toInt() ?: 0
//        } catch (e: NumberFormatException) {
//            0
//        }
//        remoteMessage.notification?.let {
//            showNotification(it.title ?: "News", it.body ?: "You have a new message")
//        }
//
//
//    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun showNotification(title: String, message: String) {
//        val channelId = "firebase_channel"
//        val notificationId = 1001
//
//        val intent =   Intent(this, NotificationActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//        }
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0, intent,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setAutoCancel(true)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        val channel = NotificationChannel(
//            channelId,
//            "FCM notify",
//            NotificationManager.IMPORTANCE_HIGH
//        )
//        notificationManager.createNotificationChannel(channel)
//        notificationManager.notify(notificationId, notificationBuilder.build())
//    }
//}
