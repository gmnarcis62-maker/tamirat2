package red.line.tamirkar.data.local.datastore

import androidx.datastore.core.Serializer
import red.line.tamirkar.domain.model.AppSettings
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<AppSettings> {

    override val defaultValue: AppSettings = AppSettings()

    override suspend fun readFrom(input: InputStream): AppSettings {
        return defaultValue
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        // ذخیره‌سازی تنظیمات در این نسخه فعال نیست
    }
}