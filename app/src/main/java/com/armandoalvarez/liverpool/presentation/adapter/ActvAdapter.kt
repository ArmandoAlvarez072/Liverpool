package com.armandoalvarez.liverpool.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.armandoalvarez.liverpool.R

class ActvAdapter<T>(context: Context, private val list: List<T>) :
    ArrayAdapter<T>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_actv, parent, false)
        }

        val item = list[position]

        listItem!!.rootView.setPadding(0,0,0,0)

        val textView = listItem.findViewById<TextView>(R.id.tvValue)
        textView.text = item.toString()

        return listItem
    }
}