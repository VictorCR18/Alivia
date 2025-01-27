package com.example.alivia.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.alivia.R
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Resgata o nome do exercício (se você passou pelo PendingIntent)
        val exerciseName = intent.getStringExtra("EXTRA_EXERCISE_NAME") ?: "Exercício"

        // Dispara a notificação
        showAlarmNotification(context, exerciseName)
    }

    private fun showAlarmNotification(context: Context, exerciseName: String) {
        val channelId = "EXERCISE_ALARM_CHANNEL"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon)
            .setContentTitle("Lembrete de Exercício")
            .setContentText("Hora de fazer: $exerciseName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

}
