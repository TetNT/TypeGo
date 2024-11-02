package com.tetsoft.typego.gamesetup.data

import com.tetsoft.typego.core.utils.ListIterator

class LoopedListIterator<T> : ListIterator.Base<T>() {

    override fun canGoBack(): Boolean {
        return true
    }

    override fun canGoNext(): Boolean {
        return true
    }

    override fun nextElement(): T {
        currentElementId++
        if(currentElementId >= elements.size) {
            currentElementId = 0
            return elements.first()
        }
        return elements[currentElementId]
    }

    override fun previousElement(): T {
        currentElementId--
        if(currentElementId < 0) {
            currentElementId = elements.size - 1
            return elements.last()
        }
        return elements[currentElementId]
    }
}