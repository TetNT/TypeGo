package com.tetsoft.typego.releasenotes.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.releasenotes.domain.ReleaseNote

class ReleaseNotesAdapter(private val context: Context, private val releaseNotes: List<ReleaseNote>) : RecyclerView.Adapter<ReleaseNotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvVersion : TextView = itemView.findViewById(R.id.tv_version)
        val tvDescription : TextView = itemView.findViewById(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.release_notes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvVersion.text = releaseNotes[position].getVersion()
        holder.tvDescription.text = releaseNotes[position].getText()
    }

    override fun getItemCount(): Int {
        return 7
    }
}