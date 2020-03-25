package com.example.encryptionmethod.ui.frequency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.encryptionmethod.R
import com.example.encryptionmethod.ui.caesar.CaesarFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FrequencyAnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_frequency_analysis, container, false)
        val fab = root.findViewById<FloatingActionButton>(R.id.fab)


        fab.setOnClickListener {
            val newFragment = FrequencyResultFragment()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.nav_host_fragment, newFragment)
            ft.addToBackStack(null)
            ft.commit()
        }

        return root
    }




}