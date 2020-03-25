package com.example.encryptionmethod.ui.caesar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ListView

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.encryptionmethod.Item
import com.example.encryptionmethod.R
import com.example.encryptionmethod.adapters.ListAdapter
import com.example.encryptionmethod.ciphers.Caesar
import com.example.encryptionmethod.ciphers.Settings
import com.example.encryptionmethod.showSnackbar
import com.example.encryptions.ciphers.languages.Languages
import com.example.encryptions.dialogs.DialogForKey
import com.getbase.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_caesar.*

class CaesarFragment : Fragment() {

    private var russianLangFlag = false
    private var englishLangFlag = false

    private lateinit var alphabet: EditText
    private lateinit var inputText: EditText

    private lateinit var caesarViewModel: CaesarViewModel
    private lateinit var list: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        /*caesarViewModel =
            ViewModelProviders.of(this).get(CaesarViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_caesar, container, false)
        val fabEncode: FloatingActionButton = root.findViewById(R.id.fab_encode)
        val fabDecode: FloatingActionButton = root.findViewById(R.id.fab_decode)
        inputText = root.findViewById(R.id.inputText)
        alphabet = root.findViewById(R.id.alphabet)
        list = root.findViewById(R.id.listOfResult)

        fabEncode.setOnClickListener {
            showDialog(Settings.ENCODE)
        }

        fabDecode.setOnClickListener {
            showDialog(Settings.DECODE)
        }

        return root
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

    private fun showItemList(itemList: List<Item>) {
        list.adapter = ListAdapter(itemList, context!!)
    }

    private fun startEncoding(key: String) {
        try {
            val (alphabet, text) = getData()
            val caesar = Caesar(text, alphabet)
            val list = mutableListOf<Item>()
            for (i in 1..key.toInt()) {
                list.add(Item("$i", caesar.encode(i)))
            }
            showItemList(list)
        } catch (e: IllegalStateException) {
            showSnackbar(view!!, "Не все поля заполнены")
        }
    }

    private fun startDecoding(key: String) {
        try {
            val (alphabet, text) = getData()
            val caesar = Caesar(text, alphabet)
            val list = mutableListOf<Item>()
            for (i in 1..key.toInt()) {
                list.add(Item("$i", caesar.decode(i)))
            }
            showItemList(list)
        } catch (e: IllegalStateException) {
            showSnackbar(view!!,"Не все поля заполнены")
        }
    }

    private fun showDialog(en_de_code: Int) {
        inputText.clearFocus()
        alphabet.clearFocus()
        val fragment: DialogFragment = DialogForKey(en_de_code, DialogForKey.NUMBER_TYPE)
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