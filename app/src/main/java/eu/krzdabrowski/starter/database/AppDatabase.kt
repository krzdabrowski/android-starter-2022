package eu.krzdabrowski.starter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import eu.krzdabrowski.starter.basicfeature.data.local.dao.RocketDao
import eu.krzdabrowski.starter.basicfeature.data.local.model.RocketCached

private const val DATABASE_VERSION = 1

@Database(
    entities = [RocketCached::class],
    version = DATABASE_VERSION,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rocketDao(): RocketDao
}
