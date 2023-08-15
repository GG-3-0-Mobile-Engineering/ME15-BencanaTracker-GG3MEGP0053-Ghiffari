package com.example.bencanatracker.ui.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bencanatracker.R
import com.example.bencanatracker.SettingPreferences
import com.example.bencanatracker.response.FloodResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val pref: SettingPreferences,
    private val context: Context
) : ViewModel() {
    private val CHANNEL_ID = "floodresponse" // Define your channel ID here
    private val NOTIFICATION_ID = 123

    init {
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Flood Response Channel"
        val descriptionText = "Channel for flood response notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    fun getThemeSettings(): androidx.lifecycle.LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun processFloodData(
        floodResponse: FloodResponse,
        pendingIntent: PendingIntent
    ) {
        val severityDescription =
            floodResponse.result.objects.output.geometries.firstOrNull()?.properties?.areaName
                ?: "Unknown"

        val notificationLayout =
            RemoteViews(context.packageName, R.layout.layout_notification)
        notificationLayout.setTextViewText(R.id.notification_title, "Flood Alert")
        notificationLayout.setTextViewText(
            R.id.notification_message,
            "Area $severityDescription"
        )

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notif)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setCustomContentView(notificationLayout)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}
