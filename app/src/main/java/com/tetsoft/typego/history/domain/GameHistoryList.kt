package com.tetsoft.typego.history.domain

import com.tetsoft.typego.core.domain.GameResult
import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.domain.RandomWords

// TODO: replace with GameHistory
abstract class GameHistoryList<T : GameResult> : ArrayList<T> {
    constructor()
    constructor(list: List<T>) {
        this.addAll(list)
    }
}

class GameOnTimeHistoryList : GameHistoryList<RandomWords> {
    constructor()
    constructor(list: List<RandomWords>) : super(list)
}

class OwnTextGameHistoryList : GameHistoryList<OwnText> {
    constructor()
    constructor(list: List<OwnText>) : super(list)
}

