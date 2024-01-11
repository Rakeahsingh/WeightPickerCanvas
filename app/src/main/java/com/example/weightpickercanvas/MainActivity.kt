package com.example.weightpickercanvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weightpickercanvas.ui.theme.WeightPickerCanvasTheme
import com.example.weightpickercanvas.weightPickerScreen.Scale
import com.example.weightpickercanvas.weightPickerScreen.ScaleStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeightPickerCanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Scale(
                            style = ScaleStyle(
                                scaleWidth = 150.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                        ){

                        }
                    }

                }
            }
        }
    }
}









