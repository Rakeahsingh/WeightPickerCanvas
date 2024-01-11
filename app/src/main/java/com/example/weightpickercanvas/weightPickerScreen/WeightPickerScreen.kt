package com.example.weightpickercanvas.weightPickerScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weightpickercanvas.weightPickerScreen.components.Scale
import com.example.weightpickercanvas.weightPickerScreen.components.ScaleStyle

@Composable
fun WeightPickerScreen() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scale(
            style = ScaleStyle(
                scaleWidth = 150.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
        ) {

        }
    }

}