package com.example.lemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonapp.ui.theme.LemonAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonAPPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonadeApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    // Application state variables
    var currentStep by remember { mutableStateOf(LemonadeStep.SELECT) }
    var squeezeCount by remember { mutableStateOf(0) }
    var requiredSqueezes by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (currentStep) {
            LemonadeStep.SELECT -> {
                LemonadeStepContent(
                    text = stringResource(R.string.tap_lemon_tree),
                    imageRes = R.drawable.lemon_tree,
                    onImageClick = {
                        currentStep = LemonadeStep.SQUEEZE
                        requiredSqueezes = (2..4).random()
                        squeezeCount = 0
                    }
                )
            }
            LemonadeStep.SQUEEZE -> {
                LemonadeStepContent(
                    text = stringResource(R.string.keep_tapping),
                    imageRes = R.drawable.lemon_squeeze,
                    onImageClick = {
                        squeezeCount++
                        if (squeezeCount >= requiredSqueezes) {
                            currentStep = LemonadeStep.DRINK
                        }
                    }
                )
            }
            LemonadeStep.DRINK -> {
                LemonadeStepContent(
                    text = stringResource(R.string.tap_lemonade),
                    imageRes = R.drawable.lemon_drink,
                    onImageClick = {
                        currentStep = LemonadeStep.RESTART
                    }
                )
            }
            LemonadeStep.RESTART -> {
                LemonadeStepContent(
                    text = stringResource(R.string.tap_empty_glass),
                    imageRes = R.drawable.lemon_restart,
                    onImageClick = {
                        currentStep = LemonadeStep.SELECT
                    }
                )
            }
        }
    }
}

@Composable
fun LemonadeStepContent(
    text: String,
    imageRes: Int,
    onImageClick: () -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )
    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .clickable(onClick = onImageClick)
    )
}

enum class LemonadeStep {
    SELECT, SQUEEZE, DRINK, RESTART
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonAPPTheme {
        LemonadeApp()
    }
}
