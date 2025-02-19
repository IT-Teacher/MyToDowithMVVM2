package uz.itteacher.mytodowithmvvm.util

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import uz.itteacher.mytodowithmvvm.database.Notes
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit


fun scheduleReminder(context: Context, notes: Notes) {
    val dateString = notes.dateTime+" "+notes.time
    val reminderTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(dateString)?.time
    val oneHourBefore = reminderTime?.minus(60 * 60 * 1000) ?: return
    val delay = oneHourBefore - System.currentTimeMillis()
    Log.d("TAG", "scheduleReminder: $delay")
    if (delay > 0) {
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    "title" to notes.title,
                    "description" to notes.description
                )
            )
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
