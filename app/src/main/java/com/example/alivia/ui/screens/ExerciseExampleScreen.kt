package com.example.alivia.ui.screens

import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.alivia.alarm.setAlarmForExercise
import com.example.alivia.models.stretchingSessions
import com.example.alivia.ui.components.sendNotification
import com.example.alivia.viewmodel.SettingsViewModel
import java.util.Calendar

@Composable
fun AndroidTimePickerDialog(
    context: Context,
    initialHour: Int,
    initialMinute: Int,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        val timePicker = android.app.TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                onTimeSelected(selectedHour, selectedMinute)
            },
            initialHour,
            initialMinute,
            true // is24HourView
        )
        timePicker.setOnCancelListener {
            onDismiss()
        }
        timePicker.show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseExampleScreen(
    exerciseId: String?,
    settingsViewModel: SettingsViewModel,
) {
    val context = LocalContext.current
    val isNotificationsEnabled = settingsViewModel.isNotificationsEnabled.collectAsState()

    var showTimePicker by remember { mutableStateOf(false) }
    var chosenHour by remember { mutableStateOf(0) }
    var chosenMinute by remember { mutableStateOf(0) }

    val exercise = stretchingSessions
        .flatMap { it.exercises }
        .find { it.id.toString() == exerciseId }

    var isTimerRunning by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var videoDuration by remember { mutableStateOf(0) }
    var currentProgress by remember { mutableStateOf(0) }

    var videoView: VideoView? by remember { mutableStateOf(null) }

    if (showTimePicker) {
        val currentTime = Calendar.getInstance()
        val initialHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val initialMinute = currentTime.get(Calendar.MINUTE)

        AndroidTimePickerDialog(
            context = context,
            initialHour = initialHour,
            initialMinute = initialMinute,
            onTimeSelected = { hour, minute ->
                chosenHour = hour
                chosenMinute = minute
                showTimePicker = false

                // Se o exercise estiver disponível, agendamos o alarme
                exercise?.let {
                    setAlarmForExercise(
                        context = context,
                        year = currentTime.get(Calendar.YEAR),  // se quiser data do dia
                        month = currentTime.get(Calendar.MONTH),
                        day = currentTime.get(Calendar.DAY_OF_MONTH),
                        hour = chosenHour,
                        minute = chosenMinute,
                        exerciseName = it.name
                    )
                }
            },
            onDismiss = {
                showTimePicker = false
            }
        )
    }

    LaunchedEffect(videoView) {
        while (true) {
            videoView?.let {
                if (it.isPlaying) {
                    currentProgress = it.currentPosition
                }
            }
            kotlinx.coroutines.delay(40)
        }
    }

    fun onTimerComplete() {
        if (isNotificationsEnabled.value) {
            sendNotification(context, "O exercicio terminou!")
        }
    }

    fun startTimer() {
        videoView?.let {
            if (!isTimerRunning) {
                it.start()
                isTimerRunning = true
                isPaused = false
            }
        }
    }

    fun pauseTimer() {
        videoView?.let {
            it.pause()
            isTimerRunning = false
            isPaused = true
        }
    }

    fun resetTimer() {
        videoView?.let {
            it.pause()
            it.seekTo(0)
            currentProgress = 0
            isTimerRunning = false
            isPaused = false
            it.start()
            startTimer()
        }
    }

    exercise?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = exercise.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                AndroidView(
                    factory = { context ->
                        VideoView(context).apply {
                            videoView = this
                            val videoUri =
                                Uri.parse("android.resource://${context.packageName}/raw/${exercise.videoFileName}")
                            setVideoURI(videoUri)
                            setOnPreparedListener { mediaPlayer ->
                                videoDuration = mediaPlayer.duration
                                mediaPlayer.isLooping = false
                            }
                            setOnCompletionListener {
                                pauseTimer()
                                it.pause()
                                onTimerComplete()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    LinearProgressIndicator(
                        progress = if (videoDuration > -1) currentProgress / videoDuration.toFloat() else 0f,
                        color = Color(0xFF267A9C),
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .align(Alignment.CenterStart)
                    )

                    Text(
                        text = String.format(
                            "%02d:%02d / %02d:%02d",
                            currentProgress / 60000,
                            (currentProgress / 1000) % 60,
                            videoDuration / 60000,
                            (videoDuration / 1000) % 60
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }

            Button(
                onClick = {
                    videoView?.let {
                        if (isTimerRunning) {
                            videoView?.pause()
                            pauseTimer()
                            it.pause()
                        } else {
                            videoView?.start()
                            startTimer()
                            it.start()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF267A9C),
                    contentColor = Color.White,
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp,    // Elevação padrão
                    pressedElevation = 16.dp,  // Elevação ao pressionar o botão
                    focusedElevation = 12.dp,  // Elevação ao focar no botão
                    hoveredElevation = 10.dp   // Elevação ao passar o mouse (desktop)
                ),
            ) {
                Text(text = if (isTimerRunning) "Pausar Exercício" else "Iniciar Exercício")
            }

            Button(
                onClick = { resetTimer() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF267A9C),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp,    // Elevação padrão
                    pressedElevation = 16.dp,  // Elevação ao pressionar o botão
                    focusedElevation = 12.dp,  // Elevação ao focar no botão
                    hoveredElevation = 10.dp   // Elevação ao passar o mouse (desktop)
                ),
            ) {
                Text(text = "Reiniciar")
            }

            Button(
                onClick = { showTimePicker = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF267A9C),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Agendar Notificação")
            }

        }
    } ?: run {
        Text(
            text = "Exercício não encontrado",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
