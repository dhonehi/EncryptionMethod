package com.example.encryptionmethod.ui.frequency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.encryptionmethod.R

class FrequencyResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_frequency_result, container, false)
        fragmentManager!!.beginTransaction().addToBackStack(null)

        return root
    }
}