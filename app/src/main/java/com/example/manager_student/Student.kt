package com.example.manager_student

import java.io.Serializable

data class Student(
    val id: String = "",
    var name: String = "",
    var studentId: String = "",
    var email: String = "",
    var phone: String = ""
) : Serializable