package com.example.hearingaid

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    var isLeftMode by remember { mutableStateOf(true) }

    var leftValues = remember { mutableStateListOf(60f, 60f, 60f, 60f, 60f, 60f, 60f) }
    var rightValues = remember { mutableStateListOf(60f, 60f, 60f, 60f, 60f, 60f, 60f) }

    var speechOptimization by remember { mutableStateOf(50f) }
    var volumeBoost by remember { mutableStateOf(50f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionTitle(title = "HEARING OPTIMIZATION")
            Switch(
                checked = isLeftMode,
                onCheckedChange = { isLeftMode = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF6A0DAD), // Purple like Figma design
                    uncheckedThumbColor = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val frequencies = listOf("125", "250", "500", "1000", "2000", "4000", "8000")
            frequencies.forEachIndexed { index, freq ->
                SliderColumn(freq, leftValues[index], rightValues[index], isLeftMode) { newValue ->
                    if (isLeftMode) leftValues[index] = newValue else rightValues[index] = newValue
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "SPEECH OPTIMIZATION")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Slider(value = speechOptimization, onValueChange = { speechOptimization = it }, valueRange = 0f..100f)
            Switch(checked = true, onCheckedChange = {})
        }

        Spacer(modifier = Modifier.height(8.dp))

        SectionTitle(title = "VOLUME BOOST")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Slider(value = volumeBoost, onValueChange = { volumeBoost = it }, valueRange = 0f..100f)
            Switch(checked = true, onCheckedChange = {})
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle permissions */ },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "App Permissions", color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.github_mark), // Replace with actual icon
            contentDescription = "GitHub",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun SliderColumn(freq: String, leftValue: Float, rightValue: Float, isLeftMode: Boolean, onValueChange: (Float) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.height(250.dp)
    ) {
        Text(text = freq, fontSize = 14.sp)
        Slider(
            value = if (isLeftMode) leftValue else rightValue,
            onValueChange = onValueChange,
            valueRange = 0f..120f,
            modifier = Modifier
                .rotate(270f)
                .height(200.dp)
                .width(40.dp)
        )
        Text(text = if (isLeftMode) leftValue.toInt().toString() else rightValue.toInt().toString(), fontSize = 14.sp)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(text = title, fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
