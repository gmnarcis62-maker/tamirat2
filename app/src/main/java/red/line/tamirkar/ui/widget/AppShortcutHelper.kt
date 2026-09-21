package red.line.tamirkar.ui.widget

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import red.line.tamirkar.MainActivity

object AppShortcutHelper {

    fun createShortcuts(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return

        val shortcutManager = context.getSystemService(ShortcutManager::class.java) ?: return

        val shortcuts = listOf(
            ShortcutInfo.Builder(context, "shortcut_new_repair")
                .setShortLabel("تعمیر جدید")
                .setLongLabel("ثبت تعمیر جدید")
                .setIcon(Icon.createWithResource(context, android.R.drawable.ic_menu_add))
                .setIntent(
                    Intent(context, MainActivity::class.java).apply {
                        action = RepairWidgetActions.ACTION_NEW_REPAIR
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
                .build(),

            ShortcutInfo.Builder(context, "shortcut_scanner")
                .setShortLabel("اسکنر")
                .setLongLabel("اسکن بارکد و QR")
                .setIcon(Icon.createWithResource(context, android.R.drawable.ic_menu_camera))
                .setIntent(
                    Intent(context, MainActivity::class.java).apply {
                        action = RepairWidgetActions.ACTION_SCANNER
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
                .build(),

            ShortcutInfo.Builder(context, "shortcut_new_customer")
                .setShortLabel("مشتری جدید")
                .setLongLabel("ثبت مشتری جدید")
                .setIcon(Icon.createWithResource(context, android.R.drawable.ic_menu_myplaces))
                .setIntent(
                    Intent(context, MainActivity::class.java).apply {
                        action = RepairWidgetActions.ACTION_NEW_CUSTOMER
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
                .build(),

            ShortcutInfo.Builder(context, "shortcut_customers")
                .setShortLabel("مشتریان")
                .setLongLabel("لیست مشتریان")
                .setIcon(Icon.createWithResource(context, android.R.drawable.ic_menu_sort_by_size))
                .setIntent(
                    Intent(context, MainActivity::class.java).apply {
                        action = RepairWidgetActions.ACTION_OPEN_APP
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
                .build()
        )

        shortcutManager.dynamicShortcuts = shortcuts
    }

    fun reportShortcutUsed(context: Context, shortcutId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            shortcutManager?.reportShortcutUsed(shortcutId)
        }
    }
}
