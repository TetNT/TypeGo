package com.tetsoft.typego.ui.fragment.game

import com.tetsoft.typego.game.data.WordSelection
import org.junit.Assert.assertEquals
import org.junit.Test

class WordSelectionTest {

    @Test
    fun selectFirstWord_text() {
        val text = "wordA wordB wordC wordD"
        val selection = WordSelection(text)
        selection.selectFirstWord()
        assertEquals("wordA", text.substring(selection.getStartPosition(), selection.getEndPosition()))
    }

    @Test
    fun selectFirstWord_empty() {
        val text = ""
        val selection = WordSelection(text)
        selection.selectFirstWord()
        assertEquals(0, selection.getStartPosition())
        assertEquals(0, selection.getEndPosition())
        assertEquals("", selection.getSelectedWord())
    }

    @Test
    fun selectNextWord_empty() {
        val text = ""
        val selection = WordSelection(text)
        selection.selectFirstWord()
        assertEquals(0, selection.getStartPosition())
        assertEquals(0, selection.getEndPosition())
        assertEquals("", selection.getSelectedWord())
        selection.selectFirstWord()
        assertEquals(0, selection.getStartPosition())
        assertEquals(0, selection.getEndPosition())
        assertEquals("", selection.getSelectedWord())
    }

    @Test
    fun selectNextWord_singleLineText() {
        val text = "wordA wordB wordC"
        val selection = WordSelection(text)
        selection.selectFirstWord()
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 6)
        assertEquals(selection.getEndPosition(), 11)
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 12)
        assertEquals(selection.getEndPosition(), 17)
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 18)
        assertEquals(selection.getEndPosition(), 18)
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 18)
        assertEquals(selection.getEndPosition(), 18)
    }

    @Test
    fun selectNextWord_spaceAtTheEnd() {
        val text = "wordA wordB "
        val selection = WordSelection(text)
        selection.selectFirstWord()
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 6)
        assertEquals(selection.getEndPosition(), 11)
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 12)
        assertEquals(selection.getEndPosition(), 12)
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 13)
        assertEquals(selection.getEndPosition(), 13)
    }

    @Test
    fun selectNextWord_specialCharacters() {
        val text = "wordA wordB"
        val selection = WordSelection(text)
        selection.selectFirstWord()
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 6)
        assertEquals(selection.getEndPosition(), 11)
        selection.selectNextWord()
        assertEquals(selection.getStartPosition(), 12)
        assertEquals(selection.getEndPosition(), 12)
    }

    @Test
    fun getSelectedWord_specialCharacters() {
        val text = "This  is a_sample!text↓"
        val selection = WordSelection(text)
        selection.selectFirstWord()
        assertEquals("This", selection.getSelectedWord())
        selection.selectNextWord()
        assertEquals("is", selection.getSelectedWord())
        selection.selectNextWord()
        assertEquals("a_sample!text↓", selection.getSelectedWord())
        selection.selectNextWord()
        assertEquals("", selection.getSelectedWord())
    }
}