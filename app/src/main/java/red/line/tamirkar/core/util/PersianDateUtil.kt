package red.line.tamirkar.core.util

import android.icu.util.Calendar
import android.icu.util.ULocale
import java.text.SimpleDateFormat
import java.util.*

object PersianDateUtil {

    fun nowShamsiDate(): String {
        return try {
            val locale = ULocale("fa_IR@calendar=persian")
            val calendar = Calendar.getInstance(locale)
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            String.format(Locale("fa"), "%04d/%02d/%02d", year, month, day)
        } catch (e: Exception) {
            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            sdf.format(Date())
        }
    }

    fun nowShamsiDateTime(): String {
        return try {
            val locale = ULocale("fa_IR@calendar=persian")
            val calendar = Calendar.getInstance(locale)
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            String.format(Locale("fa"), "%04d/%02d/%02d %02d:%02d", year, month, day, hour, minute)
        } catch (e: Exception) {
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
            sdf.format(Date())
        }
    }

    fun formatTimestamp(ts: Long): String {
        return try {
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
            sdf.format(Date(ts))
        } catch (_: Exception) {
            ""
        }
    }

    fun getCurrentYear(): Int {
        return try {
            val locale = ULocale("fa_IR@calendar=persian")
            val calendar = Calendar.getInstance(locale)
            calendar.get(Calendar.YEAR)
        } catch (_: Exception) {
            1403
        }
    }
}
