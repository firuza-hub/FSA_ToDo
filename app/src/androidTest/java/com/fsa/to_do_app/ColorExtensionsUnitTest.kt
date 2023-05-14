package com.fsa.to_do_app

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import com.fsa.to_do_app.util.getTextOnBackground
import org.junit.Assert
import org.junit.Test

class ColorExtensionsUnitTest {

    @Test
    fun getTextOnBackground_isCorrect() {
        val backgroundColor = Color(100,91,38,255)
        val expectedTextColor = Color.White
        val textColor = backgroundColor.getTextOnBackground()
        Assert.assertEquals(expectedTextColor, textColor)
    }
}