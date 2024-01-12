package com.example.weightpickercanvas.weightPickerScreen.components

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    style: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 250,
    initialWeight: Int = 60,
    onWeightChange: (Int) -> Unit
) {

    val radius = style.radius
    val scaleWidth = style.scaleWidth

    var center by remember {
        mutableStateOf(Offset.Zero)
    }

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var angle by remember {
        mutableFloatStateOf(0f)
    }
    var dragStartedAngle by remember {
        mutableFloatStateOf(0f)
    }
    var oldAngle by remember {
        mutableFloatStateOf(angle)
    }

    Canvas(
        modifier = modifier
            .pointerInput(true){
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartedAngle = -atan2(
                            y = circleCenter.x - offset.x,
                            x = circleCenter.y - offset.y
                        ) * (180f / PI.toFloat())
                    },
                    onDragEnd = {
                        oldAngle = angle
                    }
                ) { change, _ ->
                    val touchAngle = -atan2(
                        y = circleCenter.x - change.position.x,
                        x = circleCenter.y - change.position.y
                    ) * (180f / PI.toFloat())

                    val newAngle = oldAngle + (touchAngle - dragStartedAngle)
                    angle = newAngle.coerceIn(
                        minimumValue = initialWeight - maxWeight.toFloat(),
                        maximumValue = initialWeight - minWeight.toFloat()
                    )

                    onWeightChange((initialWeight - angle).roundToInt())

                }
            }
    ){
        center = this.center
        circleCenter = Offset(center.x, scaleWidth.toPx() / 2f + radius.toPx() )

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        drawContext.canvas.nativeCanvas.apply {

            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    setStyle(Paint.Style.STROKE)
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        Color.argb(50,50,0,0,)
                    )
                }
              )

        }

        // draw Lines in weightScale
       for (i in minWeight..maxWeight){
           val angleInRed = (i - initialWeight + angle - 90) * (PI / 180f).toFloat()
           val lineType = when {
               i % 10 == 0 -> LineType.TenStep
               i % 5 == 0 -> LineType.FiveStep
               else -> LineType.NormalLine
           }
           val lineLength = when(lineType){
               LineType.NormalLine -> style.normalLineLength.toPx()
               LineType.FiveStep -> style.fiveStepsLineLength.toPx()
               LineType.TenStep -> style.tenStepsLineLength.toPx()
           }
           val lineColor = when(lineType){
               LineType.NormalLine -> style.normalLineColor
               LineType.FiveStep -> style.fiveStepsLineColor
               LineType.TenStep -> style.tenStepsLineColor
           }
           val lineStart = Offset(
               x = (outerRadius - lineLength)  * cos(angleInRed) + circleCenter.x,
               y = (outerRadius - lineLength) * sin(angleInRed) + circleCenter.y
           )
           val lineEnd = Offset(
               x = outerRadius   * cos(angleInRed) + circleCenter.x,
               y = outerRadius * sin(angleInRed) + circleCenter.y
           )

           drawContext.canvas.nativeCanvas.apply {
               if (lineType is LineType.TenStep){
                   val textRadius = outerRadius - lineLength - 5.dp.toPx() - style.textSize.toPx()
                   val x = textRadius * cos(angleInRed) + circleCenter.x
                   val y = textRadius * sin(angleInRed) + circleCenter.y

                   withRotation(
                       degrees = angleInRed * (180 / PI.toFloat()) + 90f,
                       pivotX = x,
                       pivotY = y
                   ) {

                       drawText(
                           abs(i).toString(),
                           x,
                           y,
                           Paint().apply {
                               textSize = style.textSize.toPx()
                               textAlign = Paint.Align.CENTER
                           }
                       )
                   }


               }
           }

           drawLine(
               color = lineColor,
               start = lineStart,
               end = lineEnd,
               strokeWidth = 1.dp.toPx()
           )

           val midTop = Offset(
               x = circleCenter.x,
               y = circleCenter.y - innerRadius - style.scaleIndicatorLength.toPx()
           )
           val bottLeft = Offset(
               x = circleCenter.x - 4f,
               y = circleCenter.y - innerRadius
           )
           val bottRight = Offset(
               x = circleCenter.x + 4f,
               y = circleCenter.y - innerRadius
           )

           val indicator = Path().apply {
               moveTo(midTop.x, midTop.y)
               lineTo(bottLeft.x, bottLeft.y)
               lineTo(bottRight.x, bottRight.y)
               lineTo(midTop.x, midTop.y)
           }

           drawPath(
               path = indicator,
               color = style.scaleIndicatorColor
           )


       }


    }

}