package com.tetsoft.typego.core.utils

interface ListIterator<T> {
    fun canGoBack() : Boolean

    fun canGoNext() : Boolean

    fun nextElement() : T

    fun previousElement() : T

    fun currentElement() : T

    fun currentIndex() : Int

    fun init(list: List<T>)

    class Standard<T> : ListIterator<T> {
        private var currentElementId = 0
        private var elements = ArrayList<T>()

        override fun init(list: List<T>) {
            elements.clear()
            currentElementId = 0
            elements.addAll(list)
            if (elements.isEmpty())
                throw IllegalStateException("The list cannot be empty.")
        }

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

        override fun currentElement(): T {
            return elements[currentElementId]
        }

        override fun currentIndex(): Int {
            return currentElementId
        }

        override fun canGoBack(): Boolean {
            return currentElementId > 0
        }
    }
}

