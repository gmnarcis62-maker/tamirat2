package red.line.tamirkar.data.local.entity

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import red.line.tamirkar.domain.model.DiagnosisOption
import red.line.tamirkar.domain.model.ProblemSeverity

class DiagnosisConverters {

    @TypeConverter
    fun fromDiagnosisOptionList(value: List<DiagnosisOption>?): String {
        return Json.encodeToString(value ?: emptyList())
    }

    @TypeConverter
    fun toDiagnosisOptionList(value: String?): List<DiagnosisOption> {
        if (value.isNullOrBlank()) return emptyList()
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromProblemSeverity(value: ProblemSeverity?): String? {
        return value?.name
    }

    @TypeConverter
    fun toProblemSeverity(value: String?): ProblemSeverity? {
        if (value == null) return null
        return try {
            ProblemSeverity.valueOf(value)
        } catch (e: Exception) {
            null
        }
    }
}