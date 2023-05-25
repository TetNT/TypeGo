package com.tetsoft.typego.data.history

import com.tetsoft.typego.game.GameOnNumberOfWords
import com.tetsoft.typego.game.GameOnTime

abstract class GameHistoryList<T> : ArrayList<T> {
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