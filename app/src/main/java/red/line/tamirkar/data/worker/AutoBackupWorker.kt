package red.line.tamirkar.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import red.line.tamirkar.domain.model.BackupResult
import red.line.tamirkar.domain.repository.BackupRepository

@HiltWorker
class AutoBackupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val backupRepository: BackupRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val result = backupRepository.createBackup(password = null)
            when (result) {
                is BackupResult.Success -> Result.success()
                is BackupResult.Error -> Result.retry()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
