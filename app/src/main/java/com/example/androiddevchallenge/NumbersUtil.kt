package com.example.androiddevchallenge

object NumbersUtil {

    fun getDigitsInNumber(number: Number): Array<Char?> {

        val numberString = number.toString()
        val numberByteArray = arrayOfNulls<Char>(numberString.length)

        for (i in numberString.indices) {
            numberByteArray[i] = numberString[i]
        }

        return numberByteArray
    }

    fun getDigitsInString(numberString: String): Array<Char?> {

        val numberByteArray = arrayOfNulls<Char>(numberString.length)

        for (i in numberString.indices) {
            numberByteArray[i] = numberString[i]
        }

        return numberByteArray
    }
}