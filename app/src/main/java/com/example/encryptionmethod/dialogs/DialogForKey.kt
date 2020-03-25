package com.example.encryptions.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.encryptionmethod.R
import java.lang.ClassCastException

class DialogForKey(
    private val en_de_code: Int,
    private val inputType: Int
) : DialogFragment() {

    companion object {
        const val NUMBER_TYPE = 1
        const val STRING_TYPE = 2
        const val SELECTED_KEY = "key"
        const val SELECTED_FLAG = "en_de_code"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_input_key, null)

        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val userInput = view.findViewById<EditText>(R.id.inputKey)
        when(inputType) {
            NUMBER_TYPE -> userInput.inputType = InputType.TYPE_CLASS_NUMBER
            STRING_TYPE -> userInput.inputType = InputType.TYPE_CLASS_TEXT
        }

        isCancelable = false

        builder.apply {
            setPositiveButton("Ок") { _, _ ->

                val intent = Intent().apply {
                    putExtra(SELECTED_KEY, userInput.text.toString())
                    putExtra(SELECTED_FLAG, en_de_code)
                }
                targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
            }
            setNegativeButton("Отмена") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        }

        return builder.create()
    }

}