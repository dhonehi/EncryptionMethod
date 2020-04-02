package com.example.encryptionmethod.ciphers

import android.text.BoringLayout
import android.util.Log
import java.lang.IllegalStateException

class Permutation(
    var text: String
) {
    private var lenghtText: Int = 0

    private fun keyToIntArray(key: String): List<Int> {
        val tmpList = mutableListOf<Int>()
        for (i in key) {
            tmpList.add(i.toString().toInt())
        }
        return tmpList
    }

    private fun validate(key: List<Int>): Boolean {
        if (key.size > text.length) return false
        for (i in key) {
            if (i < 1 || i > key.size) return false
        }
        return true
    }

    fun encode(_key: String): String {
        val key = keyToIntArray(_key)
        if (!validate(key)) throw IllegalStateException("Неверный ключ!")
        lenghtText = text.length

        val period = lenghtText / key.size
        if (period != 0) {
            val tmp = text
            for (i in 0 until period) {
                text += tmp
            }
            for (i in 0 until lenghtText - key.size * period) {
                text += text[i]
            }
        }

        for (i in 0 until lenghtText % key.size) {
            text += text[i]
        }
        var result = ""

        for (i in 0 until lenghtText step key.size) {
            val transposition = arrayOfNulls<Char>(key.size)

            for (j in key.indices) {
                transposition[key[j] - 1] = text[i + j]
            }

            for (j in key.indices)
                result += transposition[j]
        }

        return result
    }

    fun decode(_key: String): String {
        val key = keyToIntArray(_key)

        if (!validate(key)) throw IllegalStateException("Неверный ключ")
        lenghtText = text.length

        var result = ""

        for (i in 0 until lenghtText step key.size) {
            val transposition = arrayOfNulls<Char>(key.size)

            for (j in key.indices)
                transposition[j] = text[i + key[j] - 1]

            for (j in key.indices)
                result += transposition[j]
        }

        return result
    }
}