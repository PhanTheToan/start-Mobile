package com.example.managerstudent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val studentId: String,
    val name: String
)