package red.line.tamirkar.ui.widget

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import red.line.tamirkar.MainActivity
import red.line.tamirkar.R

object AppShortcutHelper {

    fun createShortcuts(context: Context) {
        try {
            val shortcut = ShortcutInfoCompat.Builder(context, "new_repair")
                .setShortLabel("تعمیر جدید")
                .setLongLabel("شروع تعمیر جدید")
                .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(
                    Intent(context, MainActivity::class.java).apply {
                        action = Intent.ACTION_VIEW
                    }
                )
                .build()

            ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
        } catch (e: Exception) {
            // نادیده بگیر
        }
    }
}