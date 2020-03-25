package com.example.encryptionmethod.ciphers.analysis

import com.example.encryptionmethod.ciphers.Caesar

class FrequencyAnalysis(
    private val text: String,
    private val alphabet: String
) {
    private val caesar = Caesar(text, alphabet)
    private val lenghtAlphabet = alphabet.length

    private val frequencyOfEngAlphabet = mapOf(
        'e' to 12.7, 't' to 9.06, 'a' to 8.17, 'o' to 7.51, 'i' to 6.97, 'n' to 6.75,
        's' to 6.33, 'h' to 6.09, 'r' to 5.99, 'd' to 4.25, 'l' to 4.03, 'c' to 2.78,
        'u' to 2.76, 'm' to 2.41, 'w' to 2.36, 'f' to 2.23, 'g' to 2.02, 'y' to 1.97,
        'p' to 1.93, 'b' to 1.49, 'v' to 0.98, 'k' to 0.77, 'x' to 0.15, 'j' to 0.15,
        'q' to 0.1, 'z' to 0.05
    )

    private val frequencyOfRusAlphabet = mapOf(
        'о' to 11.01, 'е' to 8.73, 'а' to 7.51, 'и' to 7.44, 'т' to 6.49, 'н' to 6.45,
        'с' to 5.5, 'р' to 4.77, 'в' to 4.53, 'л' to 4.2, 'к' to 3.37, 'м' to 3.12,
        'д' to 3.02, 'п' to 2.8, 'у' to 2.48, 'я' to 2.12, 'ы' to 1.97, 'г' to 1.8,
        'з' to 1.75, 'б' to 1.75, 'ч' to 1.49, 'й' to 1.18, 'х' to 1.07, 'ъ' to 1.01,
        'ж' to 0.97, 'ь' to 0.79, 'ю' to 0.73, 'ш' to 0.68, 'ц' to 0.45, 'щ' to 0.45,
        'э' to 0.32, 'ф' to 0.19, 'ё' to 0.04
    )

    private fun scalarProduct(frLet: Map<Char, Int>, alphabetFlag: Int): Double {
        var scalar = 0.0
        when (alphabetFlag) {
            1 -> frLet.forEach { (key, value) ->
                if (key != ' ')
                    scalar += value * frequencyOfEngAlphabet[key]!!
            }
            2 -> frLet.forEach { (key, value) ->
                if (key != ' ')
                    scalar += value * frequencyOfRusAlphabet[key]!!
            }
        }

        return scalar
    }

    private fun frequencyOfLetters(text: String): Map<Char, Int> {
        val frOfLetters = mutableMapOf<Char, Int>()
        for (i in 0 until lenghtAlphabet) {
            frOfLetters[alphabet[i].toLowerCase()] =
                text.count {
                    it.toLowerCase() == alphabet[i].toLowerCase()
                }
        }

        return frOfLetters
    }

    fun startAnalysis(alphabetFlag: Int): Int {
        var frLet = frequencyOfLetters(text)
        var max = scalarProduct(frLet, alphabetFlag)
        var maxTmp: Double
        var textTmp: String
        var key = 0

        for (i in 1 until lenghtAlphabet) {
            textTmp = caesar.decode(i)
            frLet = frequencyOfLetters(textTmp)
            maxTmp = scalarProduct(frLet, alphabetFlag)
            if (maxTmp > max) {
                max = maxTmp
                key = i
            }
        }

        return key
    }
}