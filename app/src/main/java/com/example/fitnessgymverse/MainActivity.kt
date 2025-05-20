package com.example.fitnessgymverse

import WorkoutsManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessgymverse.data.WorkoutPlan
import com.example.fitnessgymverse.ui.theme.FitnessGymverseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WorkoutsManager.loadFromAssets(this)

        setContent {
            FitnessGymverseTheme {
                MaterialTheme(colorScheme = darkColorScheme()) {
                    WorkoutScreen()
                }
            }
        }
    }
}

@Composable
fun WorkoutScreen() {
    val exercises = remember { WorkoutsManager.getWorkoutPlan() }

    Scaffold(
        topBar = { WorkoutTopAppBar() },
        bottomBar = { WorkoutBottomNavBar() },
        floatingActionButton = {
            StartWorkoutButton()
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            FilterRow()
            DaySelector()
            WorkoutInfo()
            ExerciseList(exercises)
        }
    }
}

@Composable
fun WorkoutTopAppBar() {
    Box {  }
}

@Composable
fun FilterRow() {
    Box {  }
}

@Composable
fun DaySelector() {
    Box {  }
}

@Composable
fun WorkoutInfo() {
    Box {  }
}

@Composable
fun ExerciseList(exercises: WorkoutPlan?) {
    Box { }
}

@Composable
fun StartWorkoutButton() {
    ExtendedFloatingActionButton(
        onClick = {},
        icon = {},
        text = {
            Text(
                "START WORKOUT",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        },
        // TODO: get real color
        containerColor = Color.Yellow,
        contentColor = Color.Black,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .padding(horizontal = 26.dp)
            .fillMaxWidth()
    )
}

@Composable
fun WorkoutBottomNavBar() {
    NavigationBar(Modifier.background(Color.Black)) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.FitnessCenter, contentDescription = null) },
            label = { Text("My Workout") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.List, contentDescription = null) },
            label = { Text("Exercises") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.ShowChart, contentDescription = null) },
            label = { Text("Progress") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FitnessGymverseTheme {
        MaterialTheme(colorScheme = darkColorScheme()) {
            WorkoutScreen()
        }
    }
}