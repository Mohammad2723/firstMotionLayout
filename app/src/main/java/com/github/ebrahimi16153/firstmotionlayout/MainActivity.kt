package com.github.ebrahimi16153.firstmotionlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import com.github.ebrahimi16153.firstmotionlayout.ui.theme.FirstMotionLayoutTheme
import java.util.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMotionApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()

        }
    }
}

@ExperimentalMotionApi
@ExperimentalMaterialApi
@Composable
fun MyApp() {
    FirstMotionLayoutTheme {
        ////////////////////////////some variable for implement onSwipe/////////////////////////////

        val componentHeight by remember { mutableStateOf(1000f) }
        val swappableState = rememberSwipeableState("Top")
        val anchors = mapOf(0f to "Top", componentHeight to "Bottom")
        val progress = (swappableState.offset.value / componentHeight)


        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(state = swappableState, anchors = anchors, thresholds = { _, _ ->
                    FractionalThreshold(1f)

                }, orientation = Orientation.Vertical)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                MyHeader(progress = progress)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

}


@ExperimentalMotionApi
@Composable
fun MyHeader(progress: Float) {
    // A surface container using the 'background' color from the theme

    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }
    MotionLayout(
        modifier = Modifier.fillMaxWidth(),
        motionScene = MotionScene(content = motionScene),
        progress = progress,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )
        Image(
            painter = painterResource(id = R.drawable.e),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                )
                .layoutId("icon")
        )
        
        Text(text = "ELECTROMotor", modifier = Modifier.layoutId("title"), color = Color.White)


    }


}


@OptIn(ExperimentalMotionApi::class)
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}