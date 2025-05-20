package com.example.fitnessgymverse.data

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutPlan(
    val workouts: List<DayWorkout>
)

@Serializable
data class DayWorkout(
    val day: Int,
    val workout: List<Exercise>
)

@Serializable
data class Exercise(
    val exercise_id: Int,
    val exercise_name: String,
    val exercise_thumbnail: String,
    val muscle_group: String,
    val muscle_group_image: String,
    val amount_of_sets: Int,
    val rep_range: String,
    val weight_amount: String? = null // nullable because JSON has null sometimes
)
