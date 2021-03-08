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
package com.example.androiddevchallenge.ui.view

import android.os.SystemClock
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.NumbersUtil
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.typography
import kotlinx.coroutines.isActive
import java.util.Calendar

@Composable
fun Countdown() {

    Surface(color = MaterialTheme.colors.background) {

        val calendar = remember { Calendar.getInstance() }

        val isFinished = rememberSaveable { mutableStateOf(false) }
        val isRunning = rememberSaveable { mutableStateOf(true) }
        val timeLeft = rememberSaveable { mutableStateOf(0L) }
        val elapsedTime = rememberSaveable { mutableStateOf(0L) }
        val initialTime = rememberSaveable { mutableStateOf(0L) }
        val timeOfStart = remember { mutableStateOf(0L) }

        val timerState = rememberSaveable { mutableStateOf(TimerState.Paused) }

        LaunchedEffect(key1 = isRunning.value, key2 = initialTime.value, key3 = isFinished.value) {

            timerState.value = when {
                isFinished.value -> {
                    TimerState.Finished
                }
                isRunning.value -> {
                    TimerState.Running
                }
                else -> {
                    TimerState.Paused
                }
            }

            if (isRunning.value) {
                timeOfStart.value = SystemClock.uptimeMillis() - elapsedTime.value
                while (isActive && isRunning.value) {
                    withInfiniteAnimationFrameMillis {
                        elapsedTime.value = (SystemClock.uptimeMillis() - timeOfStart.value)
                        timeLeft.value = initialTime.value - elapsedTime.value

                        if (timeLeft.value < 1000) {
                            isFinished.value = true
                            isRunning.value = false
                            elapsedTime.value = 0
                            initialTime.value = 0
                            timeLeft.value = initialTime.value - elapsedTime.value
                        } else {
                            isFinished.value = false
                        }
                    }
                }
            } else {
                timeLeft.value = initialTime.value - elapsedTime.value
                if (timeLeft.value >= 1000) {
                    isFinished.value = false
                }
            }
        }

        calendar.timeInMillis = timeLeft.value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(contentAlignment = Alignment.Center) {

                pauseAndPlayIndicator(timerState = timerState.value)

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val offset = remember { mutableStateOf(0f) }

                    Column(
                        modifier = Modifier.scrollable(
                            orientation = Orientation.Vertical,
                            state = rememberScrollableState { delta ->
                                isRunning.value = false
                                offset.value += delta
                                if (offset.value > 30) {
                                    if (initialTime.value + 60_000 < 3_600_000 + elapsedTime.value) {
                                        initialTime.value += 60_000
                                    } else {
                                        elapsedTime.value = 0
                                        initialTime.value = 3_599_000
                                    }
                                    offset.value = 0f
                                } else if (offset.value < -30) {
                                    if (initialTime.value - 60_000 > 0) {
                                        initialTime.value -= 60_000
                                    } else {
                                        elapsedTime.value = 0
                                        initialTime.value = 0
                                    }
                                    offset.value = 0f
                                }
                                delta
                            }
                        )
                    ) {
                        if (calendar[Calendar.MINUTE] <= 58) {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.MINUTE].plus(1),
                                0.4f,
                                color = MaterialTheme.colors.onBackground
                            )
                        } else {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.MINUTE].plus(1),
                                0f,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        TimerDigits(
                            calendarNumbers = calendar[Calendar.MINUTE],
                            color = MaterialTheme.colors.primary
                        )
                        if (calendar[Calendar.MINUTE] > 0) {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.MINUTE].minus(1),
                                0.4f,
                                color = MaterialTheme.colors.onBackground
                            )
                        } else {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.MINUTE].minus(1),
                                0f,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }

                    Text(
                        text = ":",
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary
                    )

                    Column(
                        modifier = Modifier.scrollable(
                            orientation = Orientation.Vertical,
                            state = rememberScrollableState { delta ->
                                isRunning.value = false
                                offset.value += delta
                                if (offset.value > 30) {
                                    if (initialTime.value + 1_000 < 3_600_000 + elapsedTime.value) {
                                        initialTime.value += 1_000
                                    } else {
                                        elapsedTime.value = 0
                                        initialTime.value = 3_599_000
                                    }
                                    offset.value = 0f
                                } else if (offset.value < -30) {
                                    if (initialTime.value - 1_000 > 0) {
                                        initialTime.value -= 1_000
                                    } else {
                                        elapsedTime.value = 0
                                        initialTime.value = 0
                                    }
                                    offset.value = 0f
                                }
                                delta
                            }
                        )
                    ) {
                        if (calendar[Calendar.SECOND] <= 58) {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.SECOND].plus(1),
                                0.4f,
                                color = MaterialTheme.colors.onBackground
                            )
                        } else {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.SECOND].plus(1),
                                0f,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        TimerDigits(
                            calendarNumbers = calendar[Calendar.SECOND],
                            color = MaterialTheme.colors.primary
                        )
                        if (calendar[Calendar.SECOND] > 0) {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.SECOND].minus(1),
                                0.4f,
                                color = MaterialTheme.colors.onBackground
                            )
                        } else {
                            TimerDigits(
                                calendarNumbers = calendar[Calendar.SECOND].minus(1),
                                0f,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
            }

            Row {
                Box(Modifier.clickable { isRunning.value = !isRunning.value }) {
                    when (timerState.value) {
                        TimerState.Running ->
                            Image(
                                modifier = Modifier.size(42.dp),
                                painter = painterResource(id = R.drawable.ic_pause),
                                contentDescription = "Pause timer",
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                            )
                        else ->
                            Image(
                                modifier = Modifier.size(42.dp),
                                painter = painterResource(id = R.drawable.ic_play_arrow),
                                contentDescription = "Start timer",
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                            )
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    Modifier.clickable {
                        elapsedTime.value = 0
                        initialTime.value = 0
                        isRunning.value = false
                        isFinished.value = false
                    }
                ) {
                    Image(
                        modifier = Modifier.size(42.dp),
                        painter = painterResource(id = R.drawable.ic_refresh),
                        contentDescription = "Reset timer to 0",
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                    )
                }
            }
        }
    }
}

@Composable
fun TimerDigits(calendarNumbers: Int, alpha: Float = 1.0f, color: Color) {
    val numbersArray = NumbersUtil.getDigitsInNumber(calendarNumbers)
    when (numbersArray.size) {
        2 -> {
            Row(modifier = Modifier.alpha(alpha)) {
                DigitText(text = numbersArray[0].toString(), color = color)
                DigitText(text = numbersArray[1].toString(), color = color)
            }
        }
        1 -> {
            Row(modifier = Modifier.alpha(alpha)) {
                DigitText(text = "0", color = color)
                DigitText(text = numbersArray[0].toString(), color = color)
            }
        }
        else -> {
            Row(modifier = Modifier.alpha(alpha)) {
                DigitText(text = "0", color = color)
                DigitText(text = "0", color = color)
            }
        }
    }
}

@Composable
fun DigitText(text: String, color: Color) {
    Text(
        text = text,
        style = typography.h2,
        modifier = Modifier.width(40.dp),
        textAlign = TextAlign.Center,
        color = color
    )
}

enum class TimerState {
    Running, Finished, Paused
}

private class TransitionData(
    val color: State<Color>,
    val shape: State<Dp>
)

@Composable
private fun updateTransitionData(timerState: TimerState): TransitionData {
    val transition = updateTransition(timerState)
    val color = transition.animateColor { state ->
        when (state) {
            TimerState.Finished -> MaterialTheme.colors.onError
            TimerState.Running -> MaterialTheme.colors.secondaryVariant
            TimerState.Paused -> MaterialTheme.colors.error
        }
    }
    val shape = transition.animateDp { state ->
        when (state) {
            TimerState.Running -> 50.dp
            TimerState.Finished -> 100.dp
            TimerState.Paused -> 10.dp
        }
    }
    return remember(transition) { TransitionData(color, shape) }
}

@Composable
fun pauseAndPlayIndicator(timerState: TimerState) {

    val transitionData = updateTransitionData(timerState)

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(transitionData.shape.value))
            .background(transitionData.color.value)
    )
}
