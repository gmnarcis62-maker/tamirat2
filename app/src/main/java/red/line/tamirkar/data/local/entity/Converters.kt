package red.line.tamirkar.data.local.entity

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import red.line.tamirkar.domain.model.*

class ProblemConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromCategory(value: ProblemCategory): String = value.name
    @TypeConverter
    fun toCategory(value: String): ProblemCategory = ProblemCategory.valueOf(value)

    @TypeConverter
    fun fromSeverity(value: ProblemSeverity): String = value.name
    @TypeConverter
    fun toSeverity(value: String): ProblemSeverity = ProblemSeverity.valueOf(value)

    @TypeConverter
    fun fromDifficulty(value: RepairDifficulty): String = value.name
    @TypeConverter
    fun toDifficulty(value: String): RepairDifficulty = RepairDifficulty.valueOf(value)

    @TypeConverter
    fun fromStringList(value: List<String>): String = json.encodeToString(value)
    @TypeConverter
    fun toStringList(value: String): List<String> = json.decodeFromString(value)
}

class RepairGuideConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromSteps(value: List<RepairStep>): String = json.encodeToString(value)
    @TypeConverter
    fun toSteps(value: String): List<RepairStep> = json.decodeFromString(value)

    @TypeConverter
    fun fromTestSteps(value: List<TestStep>): String = json.encodeToString(value)
    @TypeConverter
    fun toTestSteps(value: String): List<TestStep> = json.decodeFromString(value)

    @TypeConverter
    fun fromDifficulty(value: RepairDifficulty): String = value.name
    @TypeConverter
    fun toDifficulty(value: String): RepairDifficulty = RepairDifficulty.valueOf(value)

    @TypeConverter
    fun fromStringList(value: List<String>): String = json.encodeToString(value)
    @TypeConverter
    fun toStringList(value: String): List<String> = json.decodeFromString(value)
}

class DiagnosisConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromOptions(value: List<DiagnosisOption>): String = json.encodeToString(value)
    @TypeConverter
    fun toOptions(value: String): List<DiagnosisOption> = json.decodeFromString(value)

    @TypeConverter
    fun fromSeverity(value: ProblemSeverity?): String? = value?.name
    @TypeConverter
    fun toSeverity(value: String?): ProblemSeverity? = value?.let { ProblemSeverity.valueOf(it) }
}
