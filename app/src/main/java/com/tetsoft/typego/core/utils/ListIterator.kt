package com.tetsoft.typego.core.utils

interface ListIterator<T> {
    fun canGoBack() : Boolean

    fun canGoNext() : Boolean

    fun nextElement() : T

    fun previousElement() : T

    fun currentElement() : T

    fun currentIndex() : Int

    fun init(list: List<T>)

    abstract class Base<T> : ListIterator<T> {
        protected var currentElementId = 0
        protected var elements = emptyList<T>()

        override fun init(list: List<T>) {
            currentElementId = 0
            elements = list
            if (elements.isEmpty())
                throw IllegalStateException("The list cannot be empty.")
        }

        override fun currentElement(): T {
            return elements[currentElementId]
        }

        override fun currentIndex(): Int {
            return currentElementId
        }
    }

    // TODO: JUnits and test functionality after refactoring
    class Standard<T> : Base<T>() {

        override fun canGoNext(): Boolean {
            return currentElementId + 1 < elements.size
        }

        override fun nextElement(): T {
            if (canGoNext()) {
                currentElementId++
                return elements[currentElementId]
            } else throw ArrayIndexOutOfBoundsException()
        }

        override fun previousElement(): T {
            if (canGoBack()) {
                currentElementId--
                return elements[currentElementId]
            } else throw ArrayIndexOutOfBoundsException()
        }

        override fun canGoBack(): Boolean {
            return currentElementId > 0
        }
    }
}

