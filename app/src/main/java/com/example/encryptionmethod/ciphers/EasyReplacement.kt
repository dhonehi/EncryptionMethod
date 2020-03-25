package com.example.encryptionmethod.ciphers

class EasyReplacement(
    private val alphabet: String,
    private val text: String
) {

    private var key: String? = null

    fun startEncode(key: String): String? {
        this.key = key
        if (key.length > alphabet.length || key.length < alphabet.length) return null
        var encodeText = ""
        text.forEach { symbol ->
            encodeText += getSymbolIntoKey(symbol)
        }
        return encodeText
    }

    fun startDecode(key: String): String? {
        this.key = key
        if (key.length > alphabet.length || key.length < alphabet.length) return null
        var decodeText = ""
        text.forEach { symbol ->
            decodeText += getSymbolIntoAlphabet(symbol)
        }
        return decodeText
    }

    private fun getSymbolIntoKey(symbol: Char): Char {
        val index = alphabet.indexOf(symbol)
        return if (index == -1 || index >= key!!.length) symbol
        else key!![index]
    }

    private fun getSymbolIntoAlphabet(symbol: Char): Char {
        val indexIntoKey = key!!.indexOf(symbol)
        return if (indexIntoKey == -1) symbol
        else alphabet[indexIntoKey]
    }
}