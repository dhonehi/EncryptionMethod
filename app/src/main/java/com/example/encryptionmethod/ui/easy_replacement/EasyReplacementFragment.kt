package com.example.encryptionmethod.ui.easy_replacement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.encryptionmethod.R
import com.example.encryptionmethod.ciphers.EasyReplacement
import com.example.encryptionmethod.ciphers.Settings
import com.example.encryptionmethod.showSnackbar
import com.example.encryptions.ciphers.languages.Languages
import com.example.encryptions.dialogs.DialogForKey
import com.getbase.floatingactionbutton.FloatingActionButton

class EasyReplacementFragment : Fragment() {

    private var russianLangFlag = false
    private var englishLangFlag = false

    lateinit var inputText: EditText
    lateinit var alphabet: EditText
    lateinit var result: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_easy_replacement, container, false)
        alphabet = root.findViewById(R.id.alphabet)
        inputText = root.findViewById(R.id.inputText)
        result = root.findViewById(R.id.result)
        val fabEncode: FloatingActionButton = root.findViewById(R.id.fab_encode)
        val fabDecode: FloatingActionButton = root.findViewById(R.id.fab_decode)

        fabEncode.setOnClickListener {
            showDialog(Settings.ENCODE)
        }

        fabDecode.setOnClickListener {
            showDialog(Settings.DECODE)
        }

        return root
    }

    private fun startEncoding(key: String) {
        try {
            val (alphabet, text) = getData()
            val easyRepl = EasyReplacement(alphabet, text)
            val resultText = easyRepl.startEncode(key)
            if (resultText == null) showSnackbar(view!!, "Неверный ключ!")
            else result.text = resultText
        } catch (e: IllegalStateException) {
            showSnackbar(view!!, "Не все поля заполнены!")
        }
    }

    private fun startDecoding(key: String) {
        try {
            val (alphabet, text) = getData()
            val easyRepl = EasyReplacement(alphabet, text)
            val resultText = easyRepl.startDecode(key)
            if (resultText == null) showSnackbar(view!!, "Неверный ключ")
            else result.text = resultText
        } catch (e: IllegalStateException) {
            showSnackbar(view!!, "Не все поля заполнены!")
        }
    }

    private fun getData(): List<String> {
        val list = mutableListOf<String>()

        var alphabetTmp = alphabet.text.toString()
        if (russianLangFlag) alphabetTmp += Languages.RUSSIAN.letters
        if (englishLangFlag) alphabetTmp += Languages.ENGLISH.letters

        if (alphabetTmp == "" || inputText.text.toString() == "") throw IllegalStateException()

        list.add(alphabetTmp)
        list.add(inputText.text.toString())

        return list
    }

    private fun showDialog(en_de_code: Int) {
        inputText.clearFocus()
        alphabet.clearFocus()
        val fragment: DialogFragment = DialogForKey(en_de_code, DialogForKey.STRING_TYPE)
        fragment.setTargetFragment(this, Settings.REQUEST_WEIGHT)
        fragment.show(fragmentManager!!, fragment.javaClass.name)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Settings.REQUEST_WEIGHT -> {
                    val enDeCode = data!!.getIntExtra(DialogForKey.SELECTED_FLAG, -1)
                    data.getStringExtra(DialogForKey.SELECTED_KEY)!!.run {
                        if (this != "")
                            when (enDeCode) {
                                Settings.ENCODE -> startEncoding(this)
                                Settings.DECODE -> startDecoding(this)
                            }
                        else showSnackbar(view!!, "Введите ключ!")
                    }
                }
            }
        }
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

}