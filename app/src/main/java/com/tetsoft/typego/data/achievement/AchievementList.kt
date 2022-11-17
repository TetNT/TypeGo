package com.tetsoft.typego.data.achievement

import android.content.Context
import com.tetsoft.typego.R
import com.tetsoft.typego.data.achievement.requirement.RequirementFactory

class AchievementList(val context: Context) : ArrayList<Achievement>() {
    init {
        this.add(
            Achievement(
                1,
                context.getString(R.string.the_first_step),
                context.getString(R.string.the_first_step_desc),
                R.drawable.ic_achievement_star,
                false,
                RequirementFactory.passedTestsAmount(1)
            )
        )
        this.add(
            Achievement(
                2,
                context.getString(R.string.beginner),
                context.getString(R.string.typewriter_wpm, 20),
                R.drawable.ic_child,
                true,
                RequirementFactory.wpmIsMoreThan(20)
            )
        )
        this.add(
            Achievement(
                3,
                context.getString(R.string.promising),
                context.getString(R.string.typewriter_wpm, 40),
                R.drawable.ic_star,
                true,
                RequirementFactory.wpmIsMoreThan(40)
            )
        )
        this.add(
            Achievement(
                4,
                context.getString(R.string.typewriter),
                context.getString(R.string.typewriter_wpm, 50),
                R.drawable.ic_keyboard,
                true,
                RequirementFactory.wpmIsMoreThan(50)
            )
        )
        this.add(
            Achievement(
                5,
                context.getString(R.string.type_bachelor),
                context.getString(R.string.typewriter_wpm, 60),
                R.drawable.ic_bachelor,
                true,
                RequirementFactory.wpmIsMoreThan(60)
            )
        )
        this.add(
            Achievement(
                6,
                context.getString(R.string.mastermind),
                context.getString(R.string.typewriter_wpm, 70),
                R.drawable.ic_brain,
                true,
                RequirementFactory.wpmIsMoreThan(70)
            )
        )
        this.add(
            Achievement(
                7,
                context.getString(R.string.alien),
                context.getString(R.string.typewriter_wpm, 80),
                R.drawable.ic_alien,
                true,
                RequirementFactory.wpmIsMoreThan(80)
            )
        )
        this.add(
            Achievement(
                8,
                context.getString(R.string.unmistakable, "I"),
                context.getString(R.string.unmistakable_desc_with_seconds, 30, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(30)
            )
        )
        this.add(
            Achievement(
                9,
                context.getString(R.string.unmistakable, "II"),
                context.getString(R.string.unmistakable_desc_with_minutes, 1, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(60)
            )
        )
        this.add(
            Achievement(
                10,
                context.getString(R.string.unmistakable, "III"),
                context.getString(R.string.unmistakable_desc_with_minutes, 2, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(120)
            )
        )
        this.add(
            Achievement(
                11,
                context.getString(R.string.unmistakable, "IV"),
                context.getString(R.string.unmistakable_desc_with_minutes, 3, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(180)
            )
        )
        this.add(
            Achievement(
                12,
                context.getString(R.string.unmistakable, "V"),
                context.getString(R.string.unmistakable_desc_with_minutes, 5, 30),
                R.drawable.ic_unmistakable,
                false,
                RequirementFactory.timeModeNoMistakes(300)
            )
        )
        this.add(
            Achievement(
                13,
                context.getString(R.string.big_fan, "I"),
                context.getString(R.string.big_fan_desc, 10),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(10)
            )
        )
        this.add(
            Achievement(
                14,
                context.getString(R.string.big_fan, "II"),
                context.getString(R.string.big_fan_desc, 50),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(50)
            )
        )
        this.add(
            Achievement(
                15,
                context.getString(R.string.big_fan, "III"),
                context.getString(R.string.big_fan_desc, 100),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(100)
            )
        )
        this.add(
            Achievement(
                16,
                context.getString(R.string.big_fan, "IV"),
                context.getString(R.string.big_fan_desc, 200),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(200)
            )
        )
        this.add(
            Achievement(
                17,
                context.getString(R.string.big_fan, "V"),
                context.getString(R.string.big_fan_desc, 500),
                R.drawable.ic_times_played,
                true,
                RequirementFactory.passedTestsAmount(500)
            )
        )
    }
    fun get(): AchievementList {
        return this
    }

    fun getById(id: Int) : Achievement {
        forEach {
            if (it.id == id) return it
        }
        return Achievement.Empty()
    }
}