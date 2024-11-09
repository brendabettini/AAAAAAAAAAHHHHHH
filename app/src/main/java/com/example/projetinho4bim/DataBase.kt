package com.example.projetinho4bim

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "Expenses"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_TYPEDOIS = "type2"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_TYPE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_TYPEDOIS TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insere dados no banco
    fun insertData(title: String, date: String, type: String, description: String, type2: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_DATE, date)
            put(COLUMN_TYPE, type)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_TYPEDOIS, type2)  // Aqui estamos usando o nome correto da coluna
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()  // Fechar o banco após a operação
        return result
    }

    // Lê todos os dados do banco
    fun readAllData(): Cursor? {
        val db = readableDatabase
        // Seleciona todas as colunas da tabela
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    // Atualiza os dados com base no id
    fun updateData(id: String, title: String, date: String, type: String, description: String, type2: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_DATE, date)
            put(COLUMN_TYPE, type)
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_TYPEDOIS, type2)  // Usando o nome correto da coluna
        }
        val result = db.update(TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(id))
        db.close()  // Fechar o banco após a operação
        return result > 0
    }

    // Deleta um item pelo ID
    fun deleteData(id: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id))
        db.close()  // Fechar o banco após a operação
        return result > 0
    }

    // Lê um único item pelo ID
    fun readDataById(id: String): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?", arrayOf(id))
    }
}
