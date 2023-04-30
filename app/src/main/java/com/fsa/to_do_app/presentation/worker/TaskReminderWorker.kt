package com.fsa.to_do_app.presentation.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fsa.to_do_app.presentation.service.ReminderNotificationService
import org.koin.java.KoinJavaComponent.inject

class TaskReminderWorker(
    context: Context, private val workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val notificationService: ReminderNotificationService by inject(
        ReminderNotificationService::class.java
    )

    override suspend fun doWork(): Result {
        Log.i("REMINDER_WORKER", "doWork started")
        
        val title = workerParams.inputData.keyValueMap["title"] as String
        val message = workerParams.inputData.keyValueMap["message"] as String

        Log.i("REMINDER_WORKER", message)
        notificationService.showNotification(title = title, message = message)

        return Result.success()
    }


}