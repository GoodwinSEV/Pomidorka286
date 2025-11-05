package com.example.pomidorka286

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomidorka286.ui.theme.Pomidorka286Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pomidorka286Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Op()
                }
            }
        }
    }
}

@Composable
fun Op() {
    var currentStep by remember { mutableStateOf(1) }

    when (currentStep) {
        1 -> WelcomeScreen { currentStep = 2 }
        2 -> GameScreen {}
    }
}

@Composable
fun WelcomeScreen(onNext: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.fonn),
        contentDescription = "fonn",
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Добро пожаловать в игру 'Угадай число'!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
            ,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        )
        Text(
            text = "Программа загадает число от 0 до 100, а вы должны его угадать.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
            ,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.knp),
            contentDescription = "Начать игру",
            modifier = Modifier
                .size(120.dp)
                .clickable { onNext() }
        )
        Text(
            text = "Начать игру",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(top = 16.dp)
                .background(
                    color = Color.White.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun GameScreen(onFinish: () -> Unit) {
    var input by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Попробуйте угадать число (0-100)") }
    var gameFinished by remember { mutableStateOf(false) }
    var guess by remember { mutableStateOf((0..100).random()) } // Изменяемое состояние
    Image(
        painter = painterResource(R.drawable.fon),
        contentDescription = "fon",
        modifier = Modifier.fillMaxSize()
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = message,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
                ,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = input,
                onValueChange = {
                    if (it.all { char -> char.isDigit() } || it.isEmpty()) {
                        input = it
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Введите число от 0 до 100") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFFAFAFA),
                    unfocusedContainerColor = Color(0xFFFAFAFA),
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Кнопка проверки
            Button(
                onClick = {
                    if (gameFinished) {
                        // Сброс игры с новым случайным числом
                        guess = (0..100).random()
                        input = ""
                        message = "Попробуйте угадать число (0-100)"
                        gameFinished = false
                    } else {
                        // Проверка числа
                        if (input.isNotEmpty()) {
                            val number = input.toInt()
                            if (number < 0 || number > 100) {
                                message = "Ошибка! Введите число от 0 до 100"
                                return@Button
                            }
                            when {
                                number > guess -> message = "Перелет! Попробуйте меньше"
                                number < guess -> message = "Недолет! Попробуйте больше"
                                else -> {
                                    message = "В точку! Вы угадали число $guess"
                                    gameFinished = true
                                }
                            }
                        } else {message = "Ошибка! Введите число"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF58736)
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = Color(0xFFFF4A02)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (gameFinished) "Сыграть еще" else "Проверить",
                    fontSize = 16.sp
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GamePreview() {
    Pomidorka286Theme {
        GameScreen(onFinish = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    Pomidorka286Theme {
        WelcomeScreen(onNext = {})
    }
}