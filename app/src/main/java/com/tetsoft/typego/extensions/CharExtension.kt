package com.tetsoft.typego.extensions

fun Char.equalsTo(other: Char, ignoreCase: Boolean) : Boolean {
    if (ignoreCase) {
        return this.lowercaseChar() == other.lowercaseChar()
    }
    return this == other
}