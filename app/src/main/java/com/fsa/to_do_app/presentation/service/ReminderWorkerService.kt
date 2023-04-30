package com.fsa.to_do_app.presentation.service

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.fsa.to_do_app.presentation.worker.TaskReminderWorker
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderWorkerService(private val applicationContext: Context) {

    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)
    }

    fun createNotificationWorkRequest(message: String, timeDelayInSeconds: Long): UUID {
        val myWorkRequest = OneTimeWorkRequestBuilder<TaskReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to "Reminder",
                    "message" to message,
                )
            )
            .build()

        val result = workManager.enqueue(myWorkRequest).result
        Log.i("CREATE_WORK", "Work with id ${myWorkRequest.id} is enqueued ${result.isDone}")
        return myWorkRequest.id
    }

    fun cancelNotificationWorkRequest(prevWorkId: UUID) {
        val result = workManager.cancelWorkById(prevWorkId).result
        Log.i("CANCEL_WORK", "Is cancelled ${result.isDone}")
    }
}