package red.line.tamirkar.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import red.line.tamirkar.data.repository.BackupRepositoryImpl
import red.line.tamirkar.data.repository.OtaRepositoryImpl
import red.line.tamirkar.domain.repository.BackupRepository
import red.line.tamirkar.domain.repository.OtaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBackupRepository(impl: BackupRepositoryImpl): BackupRepository

    @Binds
    @Singleton
    abstract fun bindOtaRepository(impl: OtaRepositoryImpl): OtaRepository
}