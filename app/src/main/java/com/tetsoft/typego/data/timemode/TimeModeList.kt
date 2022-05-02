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
}