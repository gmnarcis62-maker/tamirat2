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
}
