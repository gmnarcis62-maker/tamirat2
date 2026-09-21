package red.line.tamirkar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.data.local.seed.SeedData
import red.line.tamirkar.data.security.EncryptedDatabaseHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        seedData: SeedData
    ): RepairDatabase {
        return EncryptedDatabaseHelper.getDatabase(context, seedData)
    }

    // ==== DAO Providers ====

    @Provides
    fun provideBrandDao(db: RepairDatabase) = db.brandDao()

    @Provides
    fun provideDeviceSeriesDao(db: RepairDatabase) = db.deviceSeriesDao()

    @Provides
    fun provideDeviceModelDao(db: RepairDatabase) = db.deviceModelDao()

    @Provides
    fun provideProblemCategoryDao(db: RepairDatabase) = db.problemCategoryDao()

    @Provides
    fun provideProblemDao(db: RepairDatabase) = db.problemDao()

    @Provides
    fun provideSymptomDao(db: RepairDatabase) = db.symptomDao()

    @Provides
    fun provideProblemSymptomDao(db: RepairDatabase) = db.problemSymptomDao()

    @Provides
    fun provideDiagnosisTreeDao(db: RepairDatabase) = db.diagnosisTreeDao()

    @Provides
    fun provideDiagnosisNodeDao(db: RepairDatabase) = db.diagnosisNodeDao()

    @Provides
    fun provideDiagnosisOptionDao(db: RepairDatabase) = db.diagnosisOptionDao()

    @Provides
    fun provideDiagnosisSessionDao(db: RepairDatabase) = db.diagnosisSessionDao()

    @Provides
    fun provideDiagnosisAnswerDao(db: RepairDatabase) = db.diagnosisAnswerDao()

    @Provides
    fun provideComponentDao(db: RepairDatabase) = db.componentDao()

    @Provides
    fun provideErrorCodeDao(db: RepairDatabase) = db.errorCodeDao()

    @Provides
    fun provideCustomerDao(db: RepairDatabase) = db.customerDao()

    @Provides
    fun provideRepairCaseDao(db: RepairDatabase) = db.repairCaseDao()

    @Provides
    fun provideTechnicianNoteDao(db: RepairDatabase) = db.technicianNoteDao()

    @Provides
    fun provideFavoriteDao(db: RepairDatabase) = db.favoriteDao()

    @Provides
    fun provideSearchHistoryDao(db: RepairDatabase) = db.searchHistoryDao()

    @Provides
    fun provideSynonymDao(db: RepairDatabase) = db.synonymDao()

    @Provides
    fun provideDataPackageVersionDao(db: RepairDatabase) = db.dataPackageVersionDao()

    @Provides
    fun provideInventoryItemDao(db: RepairDatabase) = db.inventoryItemDao()

    @Provides
    fun provideInvoiceDao(db: RepairDatabase) = db.invoiceDao()
}