package com.tetsoft.typego

import com.tetsoft.typego.core.domain.OwnText
import com.tetsoft.typego.core.data.ScreenOrientation

open class OwnTextMock :
    OwnText(
        "",
        0.0,
        0,
        0,
        0,
        0,
        false,
        ScreenOrientation.PORTRAIT.name,
        false,
        0L,
        0,
        0
    )