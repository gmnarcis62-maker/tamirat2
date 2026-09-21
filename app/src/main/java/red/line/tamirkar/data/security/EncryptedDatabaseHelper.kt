package red.line.tamirkar.data.security

import android.content.Context
import androidx.room.Room
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.data.local.seed.SeedData

object EncryptedDatabaseHelper {

    @Volatile
    private var INSTANCE: RepairDatabase? = null

    fun getDatabase(context: Context, seedData: SeedData): RepairDatabase {
        return INSTANCE ?: synchronized(this) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                RepairDatabase::class.java,
                "tamirkar.db"
            )
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = db
            db
        }
    }
}