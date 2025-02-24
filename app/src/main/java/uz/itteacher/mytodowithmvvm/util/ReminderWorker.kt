package uz.itteacher.mytodowithmvvm.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Message
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.itteacher.mytodowithmvvm.R
import java.util.Random

class ReminderWorker(
    var context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Task Reminder"
        val message = inputData.getString("description") ?: "You have a task coming up!"

        sendNotification(context, title, message)
        return Result.success()
    }

    fun sendNotification(context: Context, title: String, message: String) {
        val channelId = "todo_channel"
        val notificationId = 1

        // Android 8.0 (API 26+) uchun kanal yaratish
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "ToDo Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val manager = NotificationManagerCompat.from(context)
        manager.notify(notificationId, notification)
    }

}
