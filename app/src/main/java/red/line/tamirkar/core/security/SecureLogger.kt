package red.line.tamirkar.core.security

import timber.log.Timber

object SecureLogger {

    private val sensitivePatterns = listOf(
        Regex("\b\d{15}\d?\b"),
        Regex("\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}\b")
    )

    fun d(tag: String, message: String) {
        Timber.tag(tag).d(sanitize(message))
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Timber.tag(tag).e(throwable, sanitize(message))
        } else {
            Timber.tag(tag).e(sanitize(message))
        }
    }

    fun i(tag: String, message: String) {
        Timber.tag(tag).i(sanitize(message))
    }

    private fun sanitize(message: String): String {
        var sanitized = message
        sensitivePatterns.forEach { pattern ->
            sanitized = pattern.replace(sanitized, "***")
        }
        return sanitized
    }

    fun plantDebugTree() {
        if (Timber.treeCount == 0) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
