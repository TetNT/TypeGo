package com.tetsoft.typego.data.timemode

class TimeModeList : ArrayList<TimeMode>() {
    init {
        this.add(TimeMode(15))
        this.add(TimeMode(30))
        this.add(TimeMode(60))
        this.add(TimeMode(120))
        this.add(TimeMode(180))
        this.add(TimeMode(300))
        this.add(TimeMode(600))
    }

    fun getIndexByTimeMode(timeMode: TimeMode) : Int {
        for (i in 0 until size) {
            if (this[i].timeInSeconds == timeMode.timeInSeconds)
                return i
        }
        return 0
    }

    fun getTimeModeByIndex(index: Int) : TimeMode {
        if (index >= this.size) return TimeMode(60)
        return this[index]
    }
}