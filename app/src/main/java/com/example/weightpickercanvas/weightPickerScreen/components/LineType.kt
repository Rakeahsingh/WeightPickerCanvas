package com.example.weightpickercanvas.weightPickerScreen.components

sealed class LineType {

    data object NormalLine: LineType()
    data object FiveStep: LineType()
    data object TenStep: LineType()

}