package com.example.encryptionmethod.ciphers

class Vigenere(
    private var alphabet: String,
    private var text: String
) {

    private lateinit var key: String
    private var lenghtText = 0
    private var lenghtKey = 0
    private var lenghtAlphabet = 0

    init {
        text = text.toLowerCase()
        alphabet = alphabet.toLowerCase()
        lenghtText = text.length
        lenghtAlphabet = alphabet.length
    }

    fun encode(keyTmp: String): String {
        var result = ""
        var index: Int

        key = keyTmp
        lenghtKey = key.length

        if (lenghtText > lenghtKey) {
            addSymbolsToKey()
        } else if (lenghtText < lenghtKey) return ""

        lenghtKey = key.length

        for (i in 0 until text.length) {
            if (text[i] == ' ') continue

            index = (alphabet.indexOf(text[i]) + alphabet.indexOf(key[i])) % lenghtAlphabet

            result += alphabet[index]
        }

        return result
    }

    fun decode(keyTmp: String): String {
        var result = ""
        var index: Int

        this.key = keyTmp
        lenghtKey = key.length

        if (lenghtText > lenghtKey) {
            addSymbolsToKey()
        } else if (lenghtText < lenghtKey) return ""

        lenghtKey = key.length

        for (i in 0 until text.length) {
            if (text[i] == ' ') continue

            index =
                (alphabet.indexOf(text[i]) + alphabet.length - alphabet.indexOf(key[i])) % alphabet.length

            result += alphabet[index]
        }

        return result
    }

    private fun addSymbolsToKey() {
        val tmp = lenghtText / lenghtKey - 1
        val tmpKey = key
        for (i in 0 until tmp) {
            key += tmpKey
        }
        val restChar = lenghtText % lenghtKey
        for (i in 0 until restChar) {
            key += key[i];
        }
    }
}