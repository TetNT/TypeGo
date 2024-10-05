package com.tetsoft.typego.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.tetsoft.typego.R
import com.tetsoft.typego.core.domain.Translatable

class LocalizedSpinnerAdapter<T : Translatable>(
    context: Context,
    private val data : List<T>,
) : ArrayAdapter<T>(context, R.layout.simple_spinner_item, data), SpinnerAdapter {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(i: Int): T {
        return data[i]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View = inflater.inflate(R.layout.simple_spinner_item, parent, false)
        val textView : TextView = view.findViewById(R.id.textview)
        textView.text = data[position].getTranslation(context)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View = inflater.inflate(R.layout.simple_spinner_item, parent, false)
        val textView : TextView = view.findViewById(R.id.textview)
        textView.text = data[position].getTranslation(context)
        return view
    }
}