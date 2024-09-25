package com.tetsoft.typego.keynotes.presentation

import android.view.LayoutInflater
import android.view.animation.AnimationSet
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import com.tetsoft.typego.R
import com.tetsoft.typego.keynotes.data.KeyNote
import com.tetsoft.typego.keynotes.domain.KeyNotesList
import com.tetsoft.typego.keynotes.domain.KeyNotesStateStorage
import com.tetsoft.typego.core.utils.AnimationsPreset
import com.tetsoft.typego.core.utils.ListIterator
import com.tetsoft.typego.keynotes.data.KeyNotesListImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeyNotesViewModel @Inject constructor(
    private val keyNotesStateStorage: KeyNotesStateStorage,
    private val keyNotesList: KeyNotesList,
) : ViewModel() {

    private val iterator = ListIterator.Standard<KeyNote>()

    private var notesList: List<KeyNote> = emptyList()

    fun init(showAll: Boolean) {
        if (showAll) {
            this.notesList = KeyNotesListImpl().get()
        } else {
            val uncheckedNotes = ArrayList<KeyNote>()
            for (note in keyNotesList.get()) {
                if (!keyNotesStateStorage.getBoolean(note.identifier)) {
                    uncheckedNotes.add(note)
                }
            }
            this.notesList = uncheckedNotes
        }
        iterator.init(this.notesList)
    }

    fun nextFeature() = iterator.nextElement()

    fun currentFeature(): KeyNote {
        keyNotesStateStorage.store(iterator.currentElement().identifier, true)
        return iterator.currentElement()
    }

    fun previousFeature() = iterator.previousElement()

    fun canGoNext() = iterator.canGoNext()

    fun canGoBack() = iterator.canGoBack()

    fun switchIndicatorState(pageIndicators: LinearLayout, activated: Boolean) {
        pageIndicators.getChildAt(iterator.currentIndex()).isActivated = activated
    }

    fun initIndicators(linearLayout: LinearLayout) {
        for (i in notesList.indices) {
            LayoutInflater.from(linearLayout.context)
                .inflate(R.layout.page_indicator_item, linearLayout)
        }
        switchIndicatorState(linearLayout, true)
    }

    fun getRightToLeftOutAnimationSet(duration: Long, displayWidth: Int): AnimationSet {
        val set = AnimationSet(false)
        set.addAnimation(
            AnimationsPreset.SlideOut(duration, displayWidth.toFloat() * -1, 0f).get()
        )
        set.addAnimation(AnimationsPreset.FadeOut(duration).get())
        set.fillAfter = true
        return set
    }

    fun getLeftToRightOutAnimationSet(duration: Long, displayWidth: Int): AnimationSet {
        val set = AnimationSet(false)
        set.addAnimation(
            AnimationsPreset.SlideOut(duration, displayWidth.toFloat(), 0f).get()
        )
        set.addAnimation(AnimationsPreset.FadeOut(duration).get())
        set.fillAfter = true
        return set
    }

    fun getPreviousFeatureInAnimationSet(duration: Long, displayWidth: Int): AnimationSet {
        val set = AnimationSet(false)
        set.addAnimation(
            AnimationsPreset.SlideIn(duration, displayWidth.toFloat() * -1, 0f).get()
        )
        set.addAnimation(AnimationsPreset.FadeIn(duration).get())
        return set
    }

    fun getNextFeatureInAnimationSet(duration: Long, displayWidth: Int): AnimationSet {
        val set = AnimationSet(false)
        set.addAnimation(
            AnimationsPreset.SlideIn(duration, displayWidth.toFloat(), 0f).get()
        )
        set.addAnimation(AnimationsPreset.FadeIn(duration).get())
        return set
    }
}
