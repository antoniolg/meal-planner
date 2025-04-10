package io.devexpert.mealplanner.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Crear la nueva tabla shopping_items
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `shopping_items` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `quantity` TEXT NOT NULL,
                `isChecked` INTEGER NOT NULL,
                `createdAt` INTEGER NOT NULL
            )
            """
        )
    }
}
