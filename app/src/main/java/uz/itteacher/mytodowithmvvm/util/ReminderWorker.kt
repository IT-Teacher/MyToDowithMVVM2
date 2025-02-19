package uz.itteacher.mytodowithmvvm.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import uz.itteacher.mytodowithmvvm.R
import java.util.Random

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Task Reminder"
        val message = inputData.getString("description") ?: "You have a task coming up!"

        showNotification(title, message)
        return Result.success()
    }

     fun showNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(Random().nextInt(), notificationBuilder.build())
    }

}
