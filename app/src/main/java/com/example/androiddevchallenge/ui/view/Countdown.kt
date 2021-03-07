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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.NumbersUtil
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.clockTypography
import kotlinx.coroutines.isActive
import java.util.*
import kotlin.random.Random

@Composable
fun Countdown() {

    Surface(color = MaterialTheme.colors.background) {

        val calendar = remember { Calendar.getInstance() }

        var isFinished by rememberSaveable { mutableStateOf(false) }
        var isRunning by rememberSaveable { mutableStateOf(true) }
        var timeLeft by rememberSaveable { mutableStateOf(0L) }
        var elapsedTime by rememberSaveable { mutableStateOf(0L) }
        var initialTime by rememberSaveable { mutableStateOf(0L) }
        var timeOfStart by remember { mutableStateOf(0L) }

        var timerState by rememberSaveable { mutableStateOf(TimerState.Paused) }

        LaunchedEffect(key1 = isRunning, key2 = initialTime, key3 = isFinished) {

            timerState = when {
                isFinished -> {
                    TimerState.Finished
                }
                isRunning -> {
                    TimerState.Running
                }
                else -> {
                    TimerState.Paused
                }
            }

            if (isRunning) {
                timeOfStart = SystemClock.uptimeMillis() - elapsedTime
                while (isActive && isRunning) {
                    withInfiniteAnimationFrameMillis {
                        elapsedTime = (SystemClock.uptimeMillis() - timeOfStart)
                        timeLeft = initialTime - elapsedTime

                        if (timeLeft < 1000) {
                            isFinished = true
                            isRunning = false
                            elapsedTime = 0
                            initialTime = 0
                            timeLeft = initialTime - elapsedTime
                        } else {
                            isFinished = false
                        }
                    }
                }
            } else {
                timeLeft = initialTime - elapsedTime
                if (timeLeft >= 1000) {
                    isFinished = false
                }
            }
        }

        calendar.timeInMillis = timeLeft

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(contentAlignment = Alignment.Center) {

                pauseAndPlayIndicator(timerState = timerState)

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    var offset by remember { mutableStateOf(0f) }

                    Column(
                        modifier = Modifier.scrollable(
                            orientation = Orientation.Vertical,
                            state = rememberScrollableState { delta ->
                                isRunning = false
                                offset += delta
                                if (offset > 30) {
                                    if (initialTime + 60_000 < 3_600_000 + elapsedTime) {
                                        initialTime += 60_000
                                    } else {
                                        elapsedTime = 0
                                        initialTime = 3_599_000
                                    }
                                    offset = 0f
                                } else if (offset < -30) {
                                    if (initialTime - 60_000 > 0) {
                                        initialTime -= 60_000
                                    } else {
                                        elapsedTime = 0
                                        initialTime = 0
                                    }
                                    offset = 0f
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
                                isRunning = false
                                offset += delta
                                if (offset > 30) {
                                    if (initialTime + 1_000 < 3_600_000 + elapsedTime) {
                                        initialTime += 1_000
                                    } else {
                                        elapsedTime = 0
                                        initialTime = 3_599_000
                                    }
                                    offset = 0f
                                } else if (offset < -30) {
                                    if (initialTime - 1_000 > 0) {
                                        initialTime -= 1_000
                                    } else {
                                        elapsedTime = 0
                                        initialTime = 0
                                    }
                                    offset = 0f
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
                Box(Modifier.clickable { isRunning = !isRunning }) {
                    when (timerState) {
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
                Box(Modifier.clickable {
                    elapsedTime = 0
                    initialTime = 0
                    isRunning = false
                    isFinished = false
                }) {
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
    val seed = remember { Random.nextInt(0, 10) }
    Text(
        text = text,
        style = getClockTypographyFromInt(4),//getClockTypographyFromInt(text.toInt() + seed),
        modifier = Modifier.width(40.dp),
        textAlign = TextAlign.Center,
        color = color
    )
}

enum class TimerState {
    Running, Finished, Paused
}

private class TransitionData(
    color: State<Color>,
    shape: State<Dp>
) {
    val color by color
    val shape by shape
}

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
            .clip(RoundedCornerShape(transitionData.shape))
            .background(transitionData.color)
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

@Preview
@Composable
fun CountdownPreview() {
    Countdown()
}