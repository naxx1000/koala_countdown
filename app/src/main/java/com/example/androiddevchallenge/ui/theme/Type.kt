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

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R

// Set of Material typography styles to start with
val typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.W600,
        fontSize = 32.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    )
)

val clockTypography = ClockTypography(
    zero = TextStyle(
        fontFamily = FontFamily(Font(R.font.akaya_telivigala_regular)),
        fontSize = 42.sp
    ),
    one = TextStyle(
        fontFamily = FontFamily(Font(R.font.comfortaa_variablefont_wght)),
        fontSize = 42.sp
    ),
    two = TextStyle(
        fontFamily = FontFamily(Font(R.font.dotgothic16_regular)),
        fontSize = 42.sp
    ),
    three = TextStyle(
        fontFamily = FontFamily(Font(R.font.fascinate_regular)),
        fontSize = 42.sp
    ),
    four = TextStyle(
        fontFamily = FontFamily(Font(R.font.fredokaone_regular)),
        fontSize = 42.sp
    ),
    five = TextStyle(
        fontFamily = FontFamily(Font(R.font.indie_flower_regular)),
        fontSize = 42.sp
    ),
    six = TextStyle(
        fontFamily = FontFamily(Font(R.font.josefin_sans_variablefont_wght)),
        fontSize = 42.sp
    ),
    seven = TextStyle(
        fontFamily = FontFamily(Font(R.font.lato_regular)),
        fontSize = 42.sp
    ),
    eight = TextStyle(
        fontFamily = FontFamily(Font(R.font.libre_baskerville_italic)),
        fontSize = 42.sp
    ),
    nine = TextStyle(
        fontFamily = FontFamily(Font(R.font.permanent_marker_regular)),
        fontSize = 42.sp
    ),
)

data class ClockTypography(
    val zero: TextStyle,
    val one: TextStyle,
    val two: TextStyle,
    val three: TextStyle,
    val four: TextStyle,
    val five: TextStyle,
    val six: TextStyle,
    val seven: TextStyle,
    val eight: TextStyle,
    val nine: TextStyle
)
