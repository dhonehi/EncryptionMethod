package com.example.encryptionmethod.ciphers

import kotlin.math.abs

class Caesar(
    private val text: String,
    private val alphabet: String
) {
    private var key = 0
    private val lenghtAlphabet = alphabet.length

    private fun isAlphabet(char: Char) = alphabet.contains(char)

    private fun slideSymbolForEncode(symbol: Char): Char {
        val indexCurSymbol = alphabet.indexOf(symbol)
        var keyTmp = this.key
        return if (indexCurSymbol + keyTmp >= lenghtAlphabet) {
            val distance = lenghtAlphabet - indexCurSymbol
            keyTmp -= distance
            val period = keyTmp / lenghtAlphabet
            keyTmp -= lenghtAlphabet * period
            alphabet[keyTmp]
        } else {
            alphabet[indexCurSymbol + keyTmp]
        }
    }

    private fun slideSymbolForDecode(symbol: Char): Char {
        val indexCurSymbol = alphabet.indexOf(symbol)
        var keyTmp = this.key
        return if (indexCurSymbol - keyTmp < 0) {
            keyTmp -= indexCurSymbol + 1
            val period = keyTmp / lenghtAlphabet
            keyTmp -= lenghtAlphabet * period
            keyTmp -= lenghtAlphabet - 1
            alphabet[abs(keyTmp)]
        } else {
            alphabet[indexCurSymbol - keyTmp]
        }
    }


    fun encode(key: Int): String {
        this.key = key
        var encryptedText = ""

        for (i in text.indices) {
            encryptedText += if (isAlphabet(text[i])) slideSymbolForEncode(text[i])
            else text[i]
        }
        return encryptedText
    }

    fun decode(key: Int): String {
        this.key = key
        var decryptedText = ""

        for (i in text.indices) {
            decryptedText += if (isAlphabet(text[i])) slideSymbolForDecode(text[i])
            else text[i]
        }

        return decryptedText
    }
}