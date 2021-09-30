package se.idoapps.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.idoapps.composetest.ui.theme.ComposeTestTheme

const val title = "Hello Android!"
const val animationDurationMillis = 1000

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }

        val tmp1 = MyDataClassImpl(1)
        println(tmp1.tmp)

        val tmp2: MyTest = MyTestImpl()
        tmp2.test()
    }
}

@ExperimentalAnimationApi
@Composable
fun Greeting(name: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //AnimatedVisibility1()
        //AnimatedVisibility2()
        //AnimatedVisibility3()
        //AnimatedVisibility4()
        AnimatedVisibility5()
    }

}

@Composable
fun TitleText() {
    Text(text = title, fontSize = 24.sp)
}

@Composable
fun MyButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Click Me!")
    }
}

fun animationSpecsDelay(): KeyframesSpec<Float> {
    return keyframes {
        this.durationMillis = animationDurationMillis
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibility1() {
    var visible by remember { mutableStateOf(true) }

    MyButton {
        visible = !visible
    }
    AnimatedVisibility(visible = visible) {
        TitleText()
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibility2() {
    var visible by remember { mutableStateOf(true) }

    MyButton {
        visible = !visible
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = keyframes {
            this.durationMillis = animationDurationMillis
        }),
        exit = fadeOut(animationSpec = keyframes {
            this.durationMillis = animationDurationMillis
        })
    ) {
        TitleText()
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibility3() {
    var visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    MyButton {
        visible = !visible
    }
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            // Slide in from 40 dp from the top.
            initialOffsetY = { with(density) { -40.dp.roundToPx() } }
        ) + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        TitleText()
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibility4() {
    // Create a MutableTransitionState<Boolean> for the AnimatedVisibility.
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    Column {
        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn(animationSpec = animationSpecsDelay()),
            exit = fadeOut(animationSpec = animationSpecsDelay())
        ) {
            TitleText()
        }

        // Use the MutableTransitionState to know the current animation state
        // of the AnimatedVisibility.
        Text(
            text = when {
                state.isIdle && state.currentState -> "Visible"
                !state.isIdle && state.currentState -> "Disappearing"
                state.isIdle && !state.currentState -> "Invisible"
                else -> "Appearing"
            }
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimatedVisibility5() {
    var visible by remember { mutableStateOf(true) }

    MyButton {
        visible = !visible
    }
    AnimatedVisibility(
        visible = visible,
        // Fade in/out the background and the foreground.
        enter = fadeIn(),
        exit = fadeOut(animationSpec = animationSpecsDelay())
    ) {
        Box(Modifier.fillMaxSize().background(Color.DarkGray)) {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .animateEnterExit(
                        // Slide in/out the inner box.
                        enter = slideInVertically(animationSpec = keyframes {
                            this.durationMillis = animationDurationMillis
                        }),
                        exit = slideOutVertically()
                    )
                    .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                    .background(Color.Red)
            ) {
                TitleText()
            }
        }
    }
}












@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        Greeting("Android")
    }
}