import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.bencanatracker.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationData = remoteMessage.data
        val notificationMessage = notificationData["message"]

        // Show the notification using the custom layout
        showNotification(notificationMessage)
    }

    private fun showNotification(message: String?) {
        val channelId = "my_notification_channel"
        val notificationId = 1

        val notificationLayout = RemoteViews(packageName, R.layout.layout_notification)
        notificationLayout.setTextViewText(R.id.tv_notiftitle, "Flood Alert")
        notificationLayout.setTextViewText(R.id.tv_notifmsg, message ?: "Default notification message")

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notif)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "My Notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(notificationId, notificationBuilder.build())
    }
}
