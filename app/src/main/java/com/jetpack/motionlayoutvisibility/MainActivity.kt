package com.jetpack.motionlayoutvisibility

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.layoutId
import com.jetpack.motionlayoutvisibility.ui.theme.MotionLayoutVisibilityTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutVisibilityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "MotionLayout Visibility",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            )
                        }
                    ) {
                        MotionLayoutVisibility()
                    }
                }
            }
        }
    }
}

@Composable
fun MotionLayoutVisibility() {
    var animatedToEnd by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animatedToEnd) 1f else 0f,
        animationSpec = tween(6000)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MotionLayout(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(Color.White),
            start = ConstraintSet(
                """
                    {
                      a: {
                        width: 40,
                        height: 40,
                        start: ['parent', 'start', 16],
                        bottom: ['parent', 'bottom', 16]
                      },
                      b: {
                        width: 40,
                        height: 40,
                        bottom: ['a', 'top', 16],
                        start: ['a', 'end', 16]
                      }
                    }
                """.trimIndent()
            ),
            end = ConstraintSet(
                """ 
                    {
                      a: {
                        width: 40,
                        height: 40,
                        visibility: 'gone',
                        end: ['parent', 'end', 100],
                        top: ['parent', 'top', 100]
                      },
                      b: {
                        width: 40,
                        height: 40,
                        top: ['a', 'bottom', 16, 20],
                        end: ['a', 'start', 16, 20]
                      }
                    }
                """.trimIndent()
            ),
            debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
        ) {
            Box(
                modifier = Modifier
                    .layoutId("a")
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .layoutId("b")
                    .background(Color.Blue)
            )
        }

        Button(
            onClick = { animatedToEnd = !animatedToEnd }
        ) {
            Text(text = "Run")
        }
    }
}






















