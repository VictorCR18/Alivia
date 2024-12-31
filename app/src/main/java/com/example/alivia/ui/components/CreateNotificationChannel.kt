package com.example.alivia.ui.components

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

// Função para criar o canal de notificação
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Configurações do App"
        val descriptionText = "Canal para notificações do aplicativo"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("SETTINGS_CHANNEL", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

// Função para enviar a notificação
@SuppressLint("MissingPermission", "NotificationPermission")
fun sendNotification(context: Context, eventTitle: String) {
    val builder = NotificationCompat.Builder(context, "SETTINGS_CHANNEL")
        .setSmallIcon(android.R.drawable.ic_notification_overlay) // Use um ícone apropriado
        .setContentTitle("Notificação")
        .setContentText(eventTitle)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        notify(eventTitle.hashCode(), builder.build()) // ID único por evento
    }
}