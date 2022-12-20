package com.tetsoft.typego.storage

import android.os.SystemClock
import com.tetsoft.typego.data.DictionaryType
import com.tetsoft.typego.data.ScreenOrientation
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.timemode.TimeMode
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResult
import com.tetsoft.typego.game.result.GameResultList

interface GameResultsDataSource {
    fun get(): GameResultList

    fun add(gameResult: GameResult)

    class Mock : GameResultsDataSource {

        private val list = GameResultList()

        init {
            for (i in 0 until 1000) {
                list.add(
                    GameResult(
                        GameOnTime(
                            Language("RU"),
                            TimeMode(60),
                            DictionaryType.BASIC,
                            true,
                            ScreenOrientation.PORTRAIT
                        ), 200 + i, 60, 60, 60, SystemClock.currentThreadTimeMillis()
                    )
                )
            }
        }

        override fun get(): GameResultList {
            return list
        }

        override fun add(gameResult: GameResult) {
            list.add(gameResult)
        }

    }
}