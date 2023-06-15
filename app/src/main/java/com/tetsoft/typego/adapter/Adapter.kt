package com.tetsoft.typego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class Adapter<VH : RecyclerView.ViewHolder>(private val context: Context, @LayoutRes private val viewHolderLayout: Int) :
    RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(viewHolderLayout, parent, false)
        return ViewHolder(view) as VH
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}