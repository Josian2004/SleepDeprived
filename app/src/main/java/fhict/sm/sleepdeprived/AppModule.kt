package fhict.sm.sleepdeprived

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fhict.sm.sleepdeprived.data.AppDatabase
import fhict.sm.sleepdeprived.data.caffeine.db.CaffeineDao
import fhict.sm.sleepdeprived.data.sleep.db.SleepSegmentDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideSleepSegmentDao(appDatabase: AppDatabase): SleepSegmentDao {
        return appDatabase.sleepSegmentDao()
    }

    @Provides
    fun provideCaffeineDao(appDatabase: AppDatabase): CaffeineDao {
        return appDatabase.caffeineDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}