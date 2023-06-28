package com.tetsoft.typego.data.history

import com.tetsoft.typego.game.GameOnNumberOfWords
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.game.GameResult
import com.tetsoft.typego.storage.history.GameOnNumberOfWordsHistoryStorage
import com.tetsoft.typego.storage.history.GameOnTimeHistoryStorage

abstract class GameHistoryList<T : GameResult> : ArrayList<T> {
    constructor()
    constructor(list: List<T>) {
        this.addAll(list)
    }
}

class GameOnTimeHistoryList : GameHistoryList<GameOnTime> {
    constructor()
    constructor(list: List<GameOnTime>) : super(list)
}

class GameOnNumberOfWordsHistoryList : GameHistoryList<GameOnNumberOfWords> {
    constructor()
    constructor(list: List<GameOnNumberOfWords>) : super(list)
}

class ClassicGameModesHistoryList(
    gameOnTimeList: GameOnTimeHistoryStorage,
    gameOnNumberOfWordsList: GameOnNumberOfWordsHistoryStorage
) : GameHistoryList<GameResult.Classic>() {
    init {
        this.addAll(gameOnTimeList.get())
        this.addAll(gameOnNumberOfWordsList.get())
        sortBy { it.getCompletionDateTime() }
    }
}