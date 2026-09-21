package red.line.tamirkar.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import red.line.tamirkar.data.worker.AutoBackupWorker
import red.line.tamirkar.ui.widget.WidgetUpdateWorker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON"
        ) {
            WidgetUpdateWorker.schedule(context)
            AutoBackupWorker.schedule(context)
        }
    }
}
