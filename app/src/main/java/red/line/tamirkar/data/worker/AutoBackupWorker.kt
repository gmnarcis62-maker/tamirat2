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


    companion object {
        private const val WORK_NAME = "auto_backup_work"

        fun schedule(context: android.content.Context) {
            val constraints = androidx.work.Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()

            val request = androidx.work.PeriodicWorkRequestBuilder<AutoBackupWorker>(
                1, java.util.concurrent.TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .build()

            androidx.work.WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }

        fun cancel(context: android.content.Context) {
            androidx.work.WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
        }
    }
}
