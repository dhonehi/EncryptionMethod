package com.example.encryptionmethod.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.encryptionmethod.R
import com.example.encryptionmethod.ciphers.Permutation
import com.example.encryptionmethod.ciphers.Settings
import com.example.encryptionmethod.ciphers.Vigenere
import com.example.encryptionmethod.showSnackbar
import com.example.encryptions.ciphers.languages.Languages
import com.example.encryptions.dialogs.DialogForKey
import com.getbase.floatingactionbutton.FloatingActionButton

class PermutationFragment : Fragment() {
    lateinit var inputText: EditText
    lateinit var result: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_permutation, container, false)

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

    private fun startEncoding(key: String) {
        try {
            val text = getData()
            val vigenere = Permutation(text)
            val resultText = vigenere.encode(key)
            result.text = resultText
        } catch (e: IllegalStateException) {
            showSnackbar(view!!, e.message.toString())
        }
    }

    private fun startDecoding(key: String) {
        try {
            val text = getData()
            val vigenere = Permutation(text)
            val resultText = vigenere.decode(key)
            result.text = resultText
        } catch (e: IllegalStateException) {
            showSnackbar(view!!, e.message.toString())
        }

    }

    private fun getData(): String {

        if (inputText.text.toString() == "") throw IllegalStateException("Не все поля заполнены")

        return inputText.text.toString()
    }

    private fun showDialog(en_de_code: Int) {
        inputText.clearFocus()
        val fragment: DialogFragment = DialogForKey(en_de_code, DialogForKey.NUMBER_TYPE)
        fragment.setTargetFragment(this, Settings.REQUEST_WEIGHT)
        fragment.show(fragmentManager!!, fragment.javaClass.name)
    }
}