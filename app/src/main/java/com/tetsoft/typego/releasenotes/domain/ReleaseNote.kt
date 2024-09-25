package com.tetsoft.typego.releasenotes.domain

interface ReleaseNote {
    fun getVersion(): String

    fun getDescription(): String

    fun getText() : CharSequence
}