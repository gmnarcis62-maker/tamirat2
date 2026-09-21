package red.line.tamirkar.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import red.line.tamirkar.data.local.dao.*
import red.line.tamirkar.data.local.entity.*

@Database(
    entities = [
        BrandEntity::class,
        DeviceSeriesEntity::class,
        DeviceModelEntity::class,
        ProblemCategoryEntity::class,
        ProblemEntity::class,
        SymptomEntity::class,
        ProblemSymptomEntity::class,
        DiagnosisTreeEntity::class,
        DiagnosisNodeEntity::class,
        DiagnosisOptionEntity::class,
        DiagnosisSessionEntity::class,
        DiagnosisAnswerEntity::class,
        ComponentEntity::class,
        ErrorCodeEntity::class,
        CustomerEntity::class,
        RepairCaseEntity::class,
        TechnicianNoteEntity::class,
        FavoriteEntity::class,
        SearchHistoryEntity::class,
        SynonymEntity::class,
        DataPackageVersionEntity::class,
        InventoryItemEntity::class,
        InvoiceEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class RepairDatabase : RoomDatabase() {
    abstract fun brandDao(): BrandDao
    abstract fun deviceSeriesDao(): DeviceSeriesDao
    abstract fun deviceModelDao(): DeviceModelDao
    abstract fun problemCategoryDao(): ProblemCategoryDao
    abstract fun problemDao(): ProblemDao
    abstract fun symptomDao(): SymptomDao
    abstract fun problemSymptomDao(): ProblemSymptomDao
    abstract fun diagnosisTreeDao(): DiagnosisTreeDao
    abstract fun diagnosisNodeDao(): DiagnosisNodeDao
    abstract fun diagnosisOptionDao(): DiagnosisOptionDao
    abstract fun diagnosisSessionDao(): DiagnosisSessionDao
    abstract fun diagnosisAnswerDao(): DiagnosisAnswerDao
    abstract fun componentDao(): ComponentDao
    abstract fun errorCodeDao(): ErrorCodeDao
    abstract fun customerDao(): CustomerDao
    abstract fun repairCaseDao(): RepairCaseDao
    abstract fun technicianNoteDao(): TechnicianNoteDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun synonymDao(): SynonymDao
    abstract fun dataPackageVersionDao(): DataPackageVersionDao
    abstract fun inventoryItemDao(): InventoryItemDao
    abstract fun invoiceDao(): InvoiceDao
}
