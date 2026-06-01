package com.example.liftoff.ui.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        val launchName = inputData.getString("launch_name") ?: return Result.failure()
        NotificationHelper.showNotification(applicationContext, launchName)
        return Result.success()
    }
}