package com.example.managerstudent

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENTS = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_STUDENT_ID = "student_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_STUDENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_STUDENT_ID TEXT NOT NULL UNIQUE
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }

    fun addStudent(student: Student): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.name)
            put(COLUMN_STUDENT_ID, student.id)
        }
        val result = db.insert(TABLE_STUDENTS, null, values)
        db.close()
        return result != -1L
    }

    fun getAllStudents(): List<Student> {
        val studentList = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.query(TABLE_STUDENTS, null, null, null, null, null, "$COLUMN_ID DESC")
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val studentId = getString(getColumnIndexOrThrow(COLUMN_STUDENT_ID))
                studentList.add(Student(name, studentId))
            }
            close()
        }
        db.close()
        return studentList
    }

    fun deleteStudent(studentId: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_STUDENTS, "$COLUMN_STUDENT_ID = ?", arrayOf(studentId))
        db.close()
        return result > 0
    }
}