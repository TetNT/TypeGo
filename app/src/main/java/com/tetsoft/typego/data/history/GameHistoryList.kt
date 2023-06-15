package com.tetsoft.typego.data.history

import com.tetsoft.typego.game.GameOnNumberOfWords
import com.tetsoft.typego.game.GameOnTime
import com.tetsoft.typego.game.GameResult

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
    gameOnTimeList: List<GameOnTime>,
    gameOnNumberOfWordsList: List<GameOnNumberOfWords>
) : GameHistoryList<GameResult.Classic>() {
    constructor() : this(emptyList(), emptyList())
    init {
        this.addAll(gameOnTimeList)
        this.addAll(gameOnNumberOfWordsList)
        sortBy { it.getCompletionDateTime() }
    }
}