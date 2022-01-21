package com.tetsoft.typego.adapter.language

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.tetsoft.typego.R
import com.tetsoft.typego.data.Language
import java.util.*


class LanguageSpinnerAdapter(
    context: Context, private val languageItems : ArrayList<LanguageSpinnerItem>
    ) : ArrayAdapter<LanguageSpinnerItem>(context, R.layout.spinner_item, languageItems),
    SpinnerAdapter {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return languageItems.size
    }

    override fun getItem(i: Int): LanguageSpinnerItem {
        return languageItems[i]
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View = inflater.inflate(R.layout.spinner_item, parent, false)
        val tvLanguage : TextView = view.findViewById(R.id.language)
        tvLanguage.text = languageItems[position].languageTranslation
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : View = inflater.inflate(R.layout.spinner_item, parent, false)
        val tvLanguage : TextView = view.findViewById(R.id.language)
        tvLanguage.text = languageItems[position].languageTranslation
        return view
    }

    fun getItemIndexByLanguage(language: Language) : Int {
        var i = 0
        for (spinnerLanguage in languageItems) {
            if (spinnerLanguage.language.identifier == language.identifier)
                return i
            else i = i.inc()
        }
        return 0
    }

    fun getItemIndexBySystemLanguage() : Int {
        val systemLanguage = Locale.getDefault().displayLanguage.toLowerCase(Locale.ROOT)
        var i = 0
        for (spinnerLanguage in languageItems) {
            if (spinnerLanguage.languageTranslation.equals(systemLanguage, ignoreCase = true))
                return i
            else
            i = i.inc()
        }
        return 0
    }

}