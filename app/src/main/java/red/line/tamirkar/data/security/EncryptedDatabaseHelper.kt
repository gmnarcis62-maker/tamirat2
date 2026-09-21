package red.line.tamirkar.data.security

import android.content.Context
import androidx.room.Room
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import red.line.tamirkar.data.local.database.RepairDatabase
import red.line.tamirkar.data.local.seed.SeedData

object EncryptedDatabaseHelper {

    @Volatile
    private var INSTANCE: RepairDatabase? = null

    fun getDatabase(context: Context, seedData: SeedData): RepairDatabase {
        return INSTANCE ?: synchronized(this) {
            try {
                System.loadLibrary("sqlcipher")
            } catch (e: Throwable) {
                // ignore
            }

            val passphrase = "tamirkar_secure_key_2024".toByteArray()
            val factory = SupportOpenHelperFactory(passphrase)

            val db = Room.databaseBuilder(
                context.applicationContext,
                RepairDatabase::class.java,
                "tamirkar.db"
            )
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = db
            db
        }
    }
}