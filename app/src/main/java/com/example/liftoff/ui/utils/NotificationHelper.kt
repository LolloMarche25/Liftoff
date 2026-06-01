package com.example.liftoff.ui.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    const val CHANNEL_ID = "liftoff_launches"

    fun createChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Lanci Spaziali",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifiche per i lanci spaziali"
        }
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    fun showNotification(context: Context, launchName: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Liftoff \uD83D\uDE80")
            .setContentText("Reminder set fo launch $launchName")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context)
            .notify(launchName.hashCode(), notification)
    }

    fun scheduleNotification(context: Context, launchName: String, netUtc: String) {
        val launchTime = java.time.Instant.parse(netUtc)
        val notifyTime = launchTime.minusSeconds(3600)
        val now = java.time.Instant.now()
        val delay = notifyTime.epochSecond - now.epochSecond

        if (delay <= 0) return

        val data = androidx.work.Data.Builder()
            .putString("launch_name", launchName)
            .build()

        val request = androidx.work.OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, java.util.concurrent.TimeUnit.SECONDS)
            .setInputData(data)
            .build()

        androidx.work.WorkManager.getInstance(context).enqueueUniqueWork(
            "notify_$launchName",
            androidx.work.ExistingWorkPolicy.KEEP,
            request
        )
    }
}