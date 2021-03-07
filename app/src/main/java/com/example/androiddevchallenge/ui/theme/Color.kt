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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val grey200 = Color(0xFFE0E0E0)
val grey600 = Color(0xFF464646)
val grey900 = Color(0xFF333333)
val white = Color(0xFFFFFFFF)
val yellow900 = Color(0xFFFBC02D)
val yellow700 = Color(0xFFFBC02D)

// Play, pause, finished
val blue100 = Color(0xFFB3E5FC)
val blue900 = Color(0xFF0D47A1)
val green100 = Color(0xFFDCEDC8)
val green900 = Color(0xFF33691E)
val red100 = Color(0xFFFFCCBC)
val red900 = Color(0xFFBF360C)

private val tis = darkColors()

val timerColorLight = TimerColor(
    running = blue100,
    paused = red100,
    finished = green100
)

val timerColorDark = TimerColor(
    running = blue900,
    paused = red900,
    finished = green900
)

data class TimerColor(
    val running: Color,
    val paused: Color,
    val finished: Color
)

