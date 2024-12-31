package com.example.alivia.ui.screens

import android.net.Uri
import android.os.CountDownTimer
import android.widget.VideoView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.alivia.models.stretchingSessions
import com.example.alivia.ui.components.sendNotification

@Composable
fun ExerciseExampleScreen(exerciseId: String?, isNotificationsEnabled: Boolean) {
    val context = LocalContext.current

    // Encontra o exercício com base no ID fornecido
    val exercise = stretchingSessions
        .flatMap { it.exercises }
        .find { it.id.toString() == exerciseId }

    // Estado para o tempo do cronômetro
    var timeLeft by remember { mutableStateOf(30) }  // Exemplo: 30 segundos
    var isTimerRunning by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    // Referência para o cronômetro
    var countDownTimer by remember { mutableStateOf<CountDownTimer?>(null) }

    // Estado para armazenar o VideoView
    var videoView: VideoView? by remember { mutableStateOf(null) }

    // Função para enviar notificação quando o cronômetro acabar
    fun onTimerComplete() {
        if (isNotificationsEnabled) {
            sendNotification(context, "O cronômetro chegou a zero!")
        }
    }

    // Função para iniciar o cronômetro
    fun startTimer() {
        if (!isTimerRunning) {
            countDownTimer = object : CountDownTimer(timeLeft * 1000L, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeft = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    isTimerRunning = false
                    isPaused = false
                    onTimerComplete()  // Chama a função de notificação quando o cronômetro chegar a 0
                }
            }.apply { start() }

            isTimerRunning = true
            isPaused = false
        }
    }

    // Função para pausar o cronômetro
    fun pauseTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        isPaused = true
    }

    // Função para reiniciar o cronômetro e o vídeo
    fun resetTimer() {
        countDownTimer?.cancel()
        timeLeft = 30  // Resetando o tempo para 30 segundos
        isTimerRunning = false
        isPaused = false

        // Reinicia o vídeo
        videoView?.seekTo(0)
        videoView?.start()
    }

    exercise?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título do exercício
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Descrição do exercício
            Text(
                text = exercise.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Exibindo o vídeo do exercício
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(bottom = 16.dp)
            ) {
                AndroidView(
                    factory = { context ->
                        VideoView(context).apply {
                            videoView = this
                            val videoUri =
                                Uri.parse("android.resource://${context.packageName}/raw/${exercise.videoFileName}")
                            setVideoURI(videoUri)
                            setOnPreparedListener {
                                it.isLooping = true
                                // Pausa ou inicia o vídeo dependendo do estado do cronômetro
                                if (isTimerRunning) start() else pause()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Exibindo o tempo restante do cronômetro com formatação mais bonita e centralizada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Botão de Iniciar/Pausar cronômetro
            Button(
                onClick = {
                    videoView?.let {
                        if (isTimerRunning) {
                            pauseTimer()
                            it.pause()  // Pausa o vídeo quando o cronômetro é pausado
                        } else {
                            startTimer()
                            it.start()  // Inicia o vídeo quando o cronômetro é iniciado
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF267A9C),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (isTimerRunning) "Pausar Exercício" else "Iniciar Exercício"
                )
            }

            // Botão para reiniciar o cronômetro
            Button(
                onClick = { resetTimer() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF267A9C),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Reiniciar")
            }
        }
    } ?: run {
        // Exibe uma mensagem se o exercício não for encontrado
        Text(
            text = "Exercício não encontrado",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

