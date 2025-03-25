package ir.moodz.sarafkoochooloo.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Step 1: Rename the old table
        database.execSQL(
            """
            ALTER TABLE Prices RENAME TO Prices_old;
            """.trimIndent()
        )

        // Step 2: Create the new table with the updated schema
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS Currencies (
                id INTEGER PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                currentPrice TEXT NOT NULL,
                updatedTime TEXT NOT NULL,
                maxPrice TEXT NOT NULL,
                minPrice TEXT NOT NULL
            );
            """.trimIndent()
        )

        // Step 3: Migrate data from the old table to the new one
        database.execSQL(
            """
            INSERT INTO Currencies (id, title, currentPrice, updatedTime, maxPrice, minPrice)
            SELECT CAST(id AS INTEGER), title, current_price, time, max_price, min_price FROM Prices_old;
            """.trimIndent()
        )

        // Step 4: Drop the old table
        database.execSQL(
            """
            DROP TABLE Prices_old;
            """.trimIndent()
        )
    }
}
