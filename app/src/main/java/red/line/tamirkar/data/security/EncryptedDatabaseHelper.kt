package red.line.tamirkar.data.security

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import net.sqlcipher.database.SupportFactory
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.data.local.seed.SeedData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object EncryptedDatabaseHelper {

    private const val DB_NAME = "repair_database_encrypted"
    private var INSTANCE: RepairDatabase? = null

    fun getDatabase(context: Context, seedData: SeedData): RepairDatabase {
        return INSTANCE ?: synchronized(this) {
            val passphrase = DatabasePassphrase.getOrCreate(context)
            val factory = SupportFactory(passphrase)

            val instance = Room.databaseBuilder(
                context.applicationContext,
                RepairDatabase::class.java,
                DB_NAME
            )
            .openHelperFactory(factory)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        INSTANCE?.let { database ->
                            database.brandDao().insertAll(seedData.brands())
                            database.problemCategoryDao().insertAll(seedData.categories())
                            database.problemDao().insertAll(seedData.problems())
                            database.deviceModelDao().insertAll(seedData.models())
                        }
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()

            INSTANCE = instance
            instance
        }
    }
}
