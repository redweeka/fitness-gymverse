package com.example.fitnessgymverse

import WorkoutsManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessgymverse.data.Exercise
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutTopAppBar() {
    TopAppBar(
        title = { Text("My Workout") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun FilterRow() {
    // TODO: move it to data manager
    val rowItems = listOf(
        "Muscles (16)",
        "45-60 Min",
        "Schedule",
        "Basic Exercises",
        "Equipment (64)",
        "Goals (1)",
        "Refresh Plan"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rowItems.forEach { item ->
            FilterChip(item)
        }
    }
}

@Composable
fun FilterChip(text: String) {
    AssistChip(
        onClick = {},
        label = { Text(text, fontSize = 12.sp) },
        shape = RoundedCornerShape(16.dp),
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Color.DarkGray,
            labelColor = Color.White
        )
    )
}

@Composable
fun DaySelector() {
    val workoutDays = WorkoutsManager.getWorkoutDays()
    var selectedDayIndex by remember { mutableIntStateOf(0) }
    val workoutForDay = remember(selectedDayIndex) {
        WorkoutsManager.getWorkoutsByDay(workoutDays[selectedDayIndex])
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WorkoutsManager.getWorkoutDays().forEachIndexed { index, day ->
                DayChip(label = "Day $day", selected = index == 0) {
                    selectedDayIndex = index
                }
            }
        }

        ExerciseCard(workoutForDay)
    }
}

@Composable
fun ExerciseCard(workoutForDay: List<Exercise>?) {
    LazyColumn(
        modifier = Modifier
            .padding(bottom = 64.dp) // Leave space for FAB
    ) {
        workoutForDay?.let {
            items(workoutForDay) { exercise ->
                ExerciseRow(exercise)
            }
        }
    }
}

@Composable
fun ExerciseRow(exercise: Exercise) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .background(Color.DarkGray, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.exc_t_150_ronald),
            contentDescription = exercise.exercise_thumbnail,
            modifier = Modifier
                .size(60.dp)
                .background(Color.Gray, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(exercise.exercise_name, fontWeight = FontWeight.Bold, color = Color.White)
            Text("${exercise.amount_of_sets} sets x ${exercise.rep_range} reps x ${exercise.weight_amount}",
                color = Color.LightGray, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.musclegroups1),
            contentDescription = exercise.muscle_group_image,
            modifier = Modifier
                .size(24.dp)
                .background(Color.Blue, CircleShape)
        )
    }
}

@Composable
fun getExerciseDrawablePainter(drawableName: String): Painter {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(
        drawableName.substringBeforeLast("."), // remove .jpg/.png
        "drawable",
        context.packageName
    )

    return painterResource(id = resId)
}

@Composable
fun DayChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color.Blue,
            containerColor = Color.DarkGray,
            labelColor = Color.White,
            selectedLabelColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    )
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
    val selectedIndex = remember { mutableIntStateOf(0) }

    val navItems = listOf(
        Icons.Default.FitnessCenter to "My Workout",
        Icons.Default.List to "Exercises",
        Icons.Default.ShowChart to "Progress",
        Icons.Default.Settings to "Settings"
    )

    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Yellow,
        selectedTextColor = Color.Yellow,
        unselectedIconColor = Color.Gray,
        unselectedTextColor = Color.Gray,
        indicatorColor = Color.Transparent
    )

    // TODO: make it switch screens
    NavigationBar(containerColor = Color.Black) {
        navItems.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                selected = selectedIndex.intValue == index,
                onClick = { selectedIndex.intValue = index },
                icon = { Icon(icon, contentDescription = null) },
                label = { Text(label) },
                colors = itemColors
            )
        }
    }
}
