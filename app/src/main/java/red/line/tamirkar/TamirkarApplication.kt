package red.line.tamirkar

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import red.line.tamirkar.core.security.SecureLogger

@HiltAndroidApp
class TamirkarApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SecureLogger.plantDebugTree()
    }
}