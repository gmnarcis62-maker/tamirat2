package red.line.tamirkar.core.result

sealed class RepairResult<out T> {
    data class Success<T>(val data: T) : RepairResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : RepairResult<Nothing>()
    object Loading : RepairResult<Nothing>()
}
