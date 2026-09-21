package red.line.tamirkar.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import red.line.tamirkar.domain.repository.BackupRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BackupRepository {

    override suspend fun createBackup(): String {
        val dir = File(context.filesDir, "backups").apply { mkdirs() }
        val file = File(dir, "backup_${System.currentTimeMillis()}.json")
        file.writeText("{}")
        return file.absolutePath
    }

    override suspend fun restoreBackup(path: String): Boolean {
        return File(path).exists()
    }

    override suspend fun getBackupList(): List<String> {
        val dir = File(context.filesDir, "backups")
        if (!dir.exists()) return emptyList()
        return dir.listFiles()?.map { it.name } ?: emptyList()
    }

    override suspend fun deleteBackup(name: String): Boolean {
        val dir = File(context.filesDir, "backups")
        return File(dir, name).delete()
    }
}