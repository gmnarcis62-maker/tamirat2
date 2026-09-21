package red.line.tamirkar.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import red.line.tamirkar.domain.repository.OtaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OtaRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : OtaRepository {

    override suspend fun checkForUpdate(): Boolean {
        return false
    }

    override suspend fun downloadUpdate(url: String): Boolean {
        return false
    }

    override suspend fun getCurrentVersion(): Int {
        return 1
    }
}