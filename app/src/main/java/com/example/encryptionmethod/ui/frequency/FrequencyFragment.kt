package com.example.encryptionmethod.ui.frequency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.encryptionmethod.R
import com.example.encryptionmethod.ciphers.Caesar
import com.example.encryptionmethod.ciphers.analysis.FrequencyAnalysis
import com.example.encryptionmethod.showSnackbar
import com.example.encryptions.ciphers.languages.Languages
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_caesar.*

class FrequencyFragment : Fragment() {

    private var russianLangFlag = false
    private var englishLangFlag = false

    private lateinit var frequencyViewModel: FrequencyViewModel
    private lateinit var inputText: EditText
    private var alphabet = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        frequencyViewModel =
            ViewModelProviders.of(this).get(FrequencyViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_frequency, container, false)
        inputText = root.findViewById(R.id.inputText)
        val viewDecodeText: TextView = root.findViewById(R.id.decode_text)
        val possibleKey: TextView = root.findViewById(R.id.possible_key)
        val fab: FloatingActionButton = root.findViewById(R.id.fab)

        fab.setOnClickListener {
            findKey()?.run {
                viewDecodeText.text = decodeText(this)
                possibleKey.text = "Возможный ключ: $this"
            }
        }

        setHasOptionsMenu(true)

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.russian) {
            item.isChecked = !item.isChecked
            russianLangFlag = item.isChecked
        } else {
            item.isChecked = !item.isChecked
            englishLangFlag = item.isChecked
        }

        return super.onOptionsItemSelected(item)
    }

    private fun decodeText(key: Int): String =
        Caesar(inputText.text.toString(), alphabet).decode(key)

    private fun findKey(): Int? {
        val alphabetFlag = if (russianLangFlag) 2 else 1
        var key: Int? = null
        try {
            val (alphabet, text) = getData()
            val analysis = FrequencyAnalysis(text, alphabet)
            key = analysis.startAnalysis(alphabetFlag)
        } catch (e: IllegalStateException) {
            showSnackbar(view!!,"Не все поля заполнены")
        }

        return key
    }

    private fun getData(): List<String> {
        val list = mutableListOf<String>()

        alphabet = ""
        if (russianLangFlag) alphabet += Languages.RUSSIAN.letters
        else if (englishLangFlag) alphabet += Languages.ENGLISH.letters

        if (alphabet == "" || inputText.text.toString() == "") throw IllegalStateException()

        list.add(alphabet)
        list.add(inputText.text.toString())

        return list
    }

}
