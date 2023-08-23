package com.tetsoft.typego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.adapter.GamesHistoryAdapter.TestViewHolder
import com.tetsoft.typego.data.language.PrebuiltTextGameMode
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.utils.TimeConvert.convertSecondsToStamp
import java.lang.Integer.min
import java.text.SimpleDateFormat
import java.util.*

class GamesHistoryAdapter(
    private val context: Context,
    private var results: GameResultList?,
    private val listener: RecyclerViewOnClickListener
) : RecyclerView.Adapter<TestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.passed_tests_item, parent, false)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val completionTime = Date(results!![position].completionDateTime)
        val dateFormat =
            SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.SHORT)
        holder.itemDateTime.text = dateFormat.format(completionTime)
        holder.itemWPM.text = context.getString(R.string.wpm_pl, results!![position].wpm.toInt())
        val mode = results!![position].gameMode
        if (mode is GameOnTime) {
            holder.itemTimeInSeconds.text =
                convertSecondsToStamp(mode.timeMode.timeInSeconds)
        }
        if (mode is PrebuiltTextGameMode) {
            holder.itemLanguage.text = (mode as PrebuiltTextGameMode).getLanguage().identifier
        }
    }

    // TODO: refactor this, so a user can select how many items do they want to see.
    override fun getItemCount(): Int {
        if (results == null) return 0
        val MAX_ITEM_COUNT = 20
        return min(results!!.size, MAX_ITEM_COUNT)
    }

    interface RecyclerViewOnClickListener {
        fun onClick(v: View?, position: Int)
    }

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var itemDateTime: TextView
        var itemWPM: TextView
        var itemTimeInSeconds: TextView
        var itemLanguage: TextView
        var itemImage: ImageView
        override fun onClick(v: View) {
            listener.onClick(v, adapterPosition)
        }

        init {
            itemDateTime = itemView.findViewById(R.id.itemDateTime)
            itemWPM = itemView.findViewById(R.id.itemWPM)
            itemImage = itemView.findViewById(R.id.itemImage)
            itemTimeInSeconds = itemView.findViewById(R.id.itemTimeInSeconds)
            itemLanguage = itemView.findViewById(R.id.itemLanguage)
            itemView.setOnClickListener(this)
        }
    }
}