/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.clockTypography
import kotlinx.coroutines.*
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        color = MaterialTheme.colors.background
    ) {

        val date = remember { mutableStateOf(Date().time) }
        val calendar = remember { Calendar.getInstance() }
        val countdownTimeInMillis = rememberSaveable { mutableStateOf(18_000L) }
        val isRunning = rememberSaveable { mutableStateOf(false) }

        /* Sets the countdown value to the value of an observable state. The value produced is a
           specific time in the future minus the current time. */
        countdownTimeInMillis.value =
            produceState(initialValue = countdownTimeInMillis.value, key1 = isRunning.value) {
                date.value = Date().time + value
                while (isActive && isRunning.value) {
                    withInfiniteAnimationFrameMillis {
                        value = (date.value - System.currentTimeMillis())
                        if (value < 1000) {
                            cancel()
                        }
                    }
                }
            }.value

        calendar.timeInMillis = countdownTimeInMillis.value

        KoalaCountdownTimer(calendar)

        Column {
            Button(
                onClick = {
                    isRunning.value = !isRunning.value
                }
            ) {
                Text(text = "Start/Pause")
            }
        }
    }
}

@Composable
fun KoalaCountdownTimer(calendar: Calendar) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimerDigits(calendarNumbers = calendar[Calendar.MINUTE])
        Text(text = ":", style = MaterialTheme.typography.h1, textAlign = TextAlign.Center)
        TimerDigits(calendarNumbers = calendar[Calendar.SECOND])
    }
}

@Composable
fun TimerDigits(calendarNumbers: Int) {
    with(NumbersUtil.getDigitsInNumber(calendarNumbers)) {
        when (size) {
            2 -> {
                DigitText(text = this[0].toString())
                DigitText(text = this[1].toString())
            }
            1 -> {
                DigitText(text = "0")
                DigitText(text = this[0].toString())
            }
            else -> {
                DigitText(text = "0")
                DigitText(text = "0")
            }
        }
    }
}

@Composable
fun DigitText(text: String) {
    val seed = remember { nextInt(0, 10) }
    Text(
        text = text,
        style = getClockTypographyFromInt(text.toInt() + seed),
        modifier = Modifier.width(40.dp),
        textAlign = TextAlign.Center
    )
}

private fun getClockTypographyFromInt(number: Int): TextStyle =
    when (number) {
        0 -> clockTypography.zero
        1 -> clockTypography.one
        2 -> clockTypography.two
        3 -> clockTypography.three
        4 -> clockTypography.four
        5 -> clockTypography.five
        6 -> clockTypography.six
        7 -> clockTypography.seven
        8 -> clockTypography.eight
        9 -> clockTypography.nine
        else -> clockTypography.zero
    }