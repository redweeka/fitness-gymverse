import android.content.Context
import com.example.fitnessgymverse.data.WorkoutPlan
import kotlinx.serialization.json.Json
import java.io.IOException

object WorkoutsManager {
    private val jsonParser = Json { ignoreUnknownKeys = true }
    private var workoutPlan: WorkoutPlan? = null

    fun loadFromAssets(context: Context, fileName: String = "workouts.json"): Boolean {
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            workoutPlan = jsonParser.decodeFromString(jsonString)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            workoutPlan = null
            false
        }
    }

    fun getWorkoutPlan(): WorkoutPlan? = workoutPlan
}