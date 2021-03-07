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

import org.junit.Assert.assertArrayEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NumbersUtilTest {

    @Test
    @Throws(Exception::class)
    fun getDigitsInNumber() {
        assertArrayEquals(
            arrayOf<Char?>('3', '2', '9'),
            NumbersUtil.getDigitsInNumber(329)
        )
        assertArrayEquals(
            arrayOf<Char?>('4', '8', '3', '2'),
            NumbersUtil.getDigitsInNumber(4832)
        )
        assertArrayEquals(
            arrayOf<Char?>('4', '6'),
            NumbersUtil.getDigitsInNumber(46)
        )
        assertArrayEquals(
            arrayOf<Char?>('4'),
            NumbersUtil.getDigitsInNumber(4)
        )
        assertArrayEquals(
            arrayOf<Char?>('1'),
            NumbersUtil.getDigitsInNumber(1)
        )
        assertArrayEquals(
            arrayOf<Char?>('0'),
            NumbersUtil.getDigitsInNumber(0)
        )
    }
}
