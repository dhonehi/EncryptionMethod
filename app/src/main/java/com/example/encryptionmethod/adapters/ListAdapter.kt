package com.example.encryptionmethod.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import com.example.encryptionmethod.Item
import com.example.encryptionmethod.R
import org.w3c.dom.Text

class ListAdapter(
    private val data: List<Item>,
    _context: Context
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(_context)

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): String? {
        return data[position].key + data[position].text
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        Log.d("MSG", convertView?.findViewById<TextView>(R.id.key)?.text.toString())
        /*var rowView: View? = convertView
        if(convertView == null) {
            rowView = inflater.inflate(R.layout.item, parent, false)
        }*/
        val item = data[position]
        /*val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater*/
        /*(rowView!!.findViewById(R.id.key) as TextView).text = item.key
        (rowView!!.findViewById(R.id.text) as TextView).text = item.text*/
        return inflater.inflate(R.layout.item, parent, false).apply {
            this.findViewById<TextView>(R.id.key).text = item.key
            this.findViewById<TextView>(R.id.text).text = item.text
        }
    }
}
