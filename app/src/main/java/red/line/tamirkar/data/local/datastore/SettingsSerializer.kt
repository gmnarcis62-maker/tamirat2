package red.line.tamirkar.data.local.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import red.line.tamirkar.domain.model.AppSettings
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<AppSettings> {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    override val defaultValue: AppSettings = AppSettings()

    override suspend fun readFrom(input: InputStream): AppSettings {
        return try {
            json.decodeFromString(input.readBytes().decodeToString())
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        output.write(json.encodeToString(t).encodeToByteArray())
    }
}