package com.tetsoft.typego.game.mode

import com.tetsoft.typego.data.ScreenOrientation
import java.io.Serializable
import java.lang.ArithmeticException

@Deprecated("To be deleted")
abstract class GameMode(
    open val suggestionsActivated: Boolean,
    open val screenOrientation: ScreenOrientation
) : Serializable {

    class Empty : GameMode(false, ScreenOrientation.PORTRAIT)
}