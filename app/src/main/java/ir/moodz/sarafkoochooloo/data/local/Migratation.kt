package ir.moodz.sarafkoochooloo.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("DROP TABLE IF EXISTS Prices")

        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS Currencies (
                id INTEGER NOT NULL,
                title TEXT NOT NULL PRIMARY KEY,
                currentPrice INTEGER NOT NULL,
                updatedTime TEXT NOT NULL,
                type TEXT NOT NULL
            );
            """.trimIndent()
        )
    }
}
