package com.tetsoft.typego.history.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.tetsoft.typego.R
import com.tetsoft.typego.core.data.ScreenOrientation
import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.domain.RandomWords
import com.tetsoft.typego.core.utils.extensions.setDrawableStart
import com.tetsoft.typego.core.utils.extensions.setDrawableTint
import com.tetsoft.typego.core.utils.TimeConvert
import com.tetsoft.typego.core.utils.Translation
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

class GameHistoryAdapter(
    private val context: Context,
    private val gameHistory: List<GameResult>,
    private val listener: RecyclerViewOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val MAX_ITEM_COUNT = 15
        const val RANDOM_WORDS_VIEW_TYPE = 0
        const val OWN_TEXT_VIEW_TYPE = 1
        val DATE_FORMAT: DateFormat = SimpleDateFormat.getDateTimeInstance(
            SimpleDateFormat.DEFAULT,
            SimpleDateFormat.SHORT
        )
        val SCREEN_ORIENTATION_DRAWABLE_MAP = mapOf(
            ScreenOrientation.PORTRAIT to R.drawable.ic_portrait,
            ScreenOrientation.LANDSCAPE to R.drawable.ic_landscape
        )
        val ACTIVE_INACTIVE_COLOR_MAP = mapOf(
            true to R.color.white,
            false to R.color.background_variant
        )
    }

    interface RecyclerViewOnClickListener {
        fun onClick(v: View?, position: Int)
    }

    inner class OwnTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemWpm: TextView
        val itemText: TextView
        val itemChosenTime: TextView
        val itemSpentTime: TextView
        val itemSuggestions: TextView
        val itemOrientation: TextView
        val itemCompletionTime: TextView

        override fun onClick(v: View) {
            listener.onClick(v, bindingAdapterPosition)
        }

        init {
            itemWpm = itemView.findViewById(R.id.history_item_wpm)
            itemText = itemView.findViewById(R.id.history_attribute_text)
            itemChosenTime = itemView.findViewById(R.id.history_attribute_chosen_time)
            itemSpentTime = itemView.findViewById(R.id.history_attribute_spent_time)
            itemSuggestions = itemView.findViewById(R.id.history_attribute_suggestions)
            itemOrientation = itemView.findViewById(R.id.history_attribute_orientation)
            itemCompletionTime = itemView.findViewById(R.id.completion_time)
            itemView.setOnClickListener(this)
        }
    }

    inner class RandomWordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemWpm: TextView
        val itemTime: TextView
        val itemLanguage: TextView
        val itemDictionary: TextView
        val itemSuggestions: TextView
        val itemOrientation: TextView
        val itemSeed: TextView
        val itemCompletionTime: TextView

        override fun onClick(v: View) {
            listener.onClick(v, bindingAdapterPosition)
        }

        init {
            itemWpm = itemView.findViewById(R.id.history_item_wpm)
            itemTime = itemView.findViewById(R.id.history_attribute_time)
            itemLanguage = itemView.findViewById(R.id.history_attribute_language)
            itemDictionary = itemView.findViewById(R.id.history_attribute_dictionary)
            itemSuggestions = itemView.findViewById(R.id.history_attribute_suggestions)
            itemOrientation = itemView.findViewById(R.id.history_attribute_orientation)
            itemSeed = itemView.findViewById(R.id.history_attribute_seed)
            itemCompletionTime = itemView.findViewById(R.id.completion_time)
            itemView.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return Integer.min(gameHistory.size, MAX_ITEM_COUNT)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == RANDOM_WORDS_VIEW_TYPE) RandomWordsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_history_random_words, parent, false)
        )
        else OwnTextViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_history_own_text, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if(gameHistory[position] is RandomWords)
            RANDOM_WORDS_VIEW_TYPE
        else
            OWN_TEXT_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == RANDOM_WORDS_VIEW_TYPE) {
            val randomWordsItem = holder as RandomWordsViewHolder
            val history = gameHistory[position] as RandomWords
            with(randomWordsItem) {
                itemWpm.text = history.getWpm().roundToInt().toString()
                itemDictionary.text = Translation(context).get(history.getDictionaryType())
                itemTime.text = TimeConvert.convertSecondsToStamp(history.getTimeSpent())
                itemLanguage.text = history.getLanguageCode()
                itemCompletionTime.text = DATE_FORMAT.format(Date(history.getCompletionDateTime()))
                val orientationDrawable = SCREEN_ORIENTATION_DRAWABLE_MAP.getOrDefault(history.getScreenOrientation(), R.drawable.ic_portrait)
                itemOrientation.setDrawableStart(AppCompatResources.getDrawable(context, orientationDrawable))
                itemSuggestions.setDrawableTint(ACTIVE_INACTIVE_COLOR_MAP.getOrDefault(history.areSuggestionsActivated(), R.color.background_variant))
                itemSeed.setDrawableTint(ACTIVE_INACTIVE_COLOR_MAP.getOrDefault(history.getSeed().isNotEmpty(), R.color.background_variant))
            }
        }
        else if (getItemViewType(position) == OWN_TEXT_VIEW_TYPE) {
            val ownTextItem = holder as OwnTextViewHolder
            val history = gameHistory[position] as OwnText
            with(ownTextItem) {
                itemWpm.text = history.getWpm().roundToInt().toString()
                itemText.text = history.getText()
                itemChosenTime.text = TimeConvert.convertSecondsToStamp(history.getChosenTimeInSeconds())
                itemSpentTime.text = TimeConvert.convertSecondsToStamp(history.getTimeSpent())
                itemCompletionTime.text = DATE_FORMAT.format(Date(history.getCompletionDateTime()))
                val orientationDrawable = SCREEN_ORIENTATION_DRAWABLE_MAP.getOrDefault(history.getScreenOrientation(), R.drawable.ic_portrait)
                itemOrientation.setDrawableStart(AppCompatResources.getDrawable(context, orientationDrawable))
                itemSuggestions.setDrawableTint(ACTIVE_INACTIVE_COLOR_MAP.getOrDefault(history.areSuggestionsActivated(), R.color.background_variant))
            }
        }
    }

}