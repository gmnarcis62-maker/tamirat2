package red.line.tamirkar.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import red.line.tamirkar.core.biometric.BiometricAuthManager
import red.line.tamirkar.core.security.AppLockManager
import red.line.tamirkar.core.util.PdfGenerator
import red.line.tamirkar.data.local.seed.SeedData
import red.line.tamirkar.data.repository.RepairRepositoryImpl
import red.line.tamirkar.domain.diagnosis.DiagnosisEngine
import red.line.tamirkar.domain.diagnosis.DiagnosisEngineImpl
import red.line.tamirkar.domain.repository.RepairRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRepairRepository(impl: RepairRepositoryImpl): RepairRepository

    @Binds
    @Singleton
    abstract fun bindDiagnosisEngine(impl: DiagnosisEngineImpl): DiagnosisEngine

    companion object {
        @Provides
        @Singleton
        fun provideSeedData(): SeedData = SeedData()

        @Provides
        @Singleton
        fun provideAppLockManager(@ApplicationContext context: Context): AppLockManager = AppLockManager(context)

        @Provides
        @Singleton
        fun provideBiometricAuthManager(@ApplicationContext context: Context): BiometricAuthManager = BiometricAuthManager(context)

        @Provides
        @Singleton
        fun providePdfGenerator(@ApplicationContext context: Context): PdfGenerator = PdfGenerator(context)
    }
}
