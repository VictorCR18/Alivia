package com.example.alivia.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import java.util.Calendar

@SuppressLint("ShortAlarm")
fun setAlarmForExercise(
    context: Context,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int,
    exerciseName: String
) {
    // 1) Verificar se temos permissão de agendar alarmes exatos em Android 12+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (!alarmManager.canScheduleExactAlarms()) {
            requestExactAlarmPermission(context)
            Toast.makeText(
                context,
                "Permissão necessária para configurar alarmes exatos.",
                Toast.LENGTH_LONG
            ).show()
            return
        }
    }

    // 2) Monta a data/hora
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)

        // Se a data/hora já passou, avança 1 dia (opcional: ajuste conforme sua lógica)
        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    // 3) Cria o PendingIntent para chamar o AlarmReceiver
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("EXTRA_EXERCISE_NAME", exerciseName)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        exerciseName.hashCode(), // RequestCode único por exercício
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // 4) Agendar o alarme
    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    Toast.makeText(
        context,
        "Alarme configurado para $hour:$minute - $exerciseName",
        Toast.LENGTH_SHORT
    ).show()
}

// Se precisar solicitar a permissão para Android 12+
private fun requestExactAlarmPermission(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}
