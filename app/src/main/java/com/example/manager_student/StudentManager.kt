package com.example.manager_student
import java.util.UUID

class StudentManager private constructor() {
    private val students = mutableListOf<Student>()

    init {
        // Thêm một số sinh viên mẫu
        addStudent(Student(UUID.randomUUID().toString(), "Nguyễn Văn An", "SV001", "an@example.com", "0901234567"))
        addStudent(Student(UUID.randomUUID().toString(), "Trần Thị Bình", "SV002", "binh@example.com", "0901234568"))
        addStudent(Student(UUID.randomUUID().toString(), "Lê Văn Cường", "SV003", "cuong@example.com", "0901234569"))
    }

    fun getStudents(): List<Student> {
        return students
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(student: Student) {
        val index = students.indexOfFirst { it.id == student.id }
        if (index != -1) {
            students[index] = student
        }
    }

    fun deleteStudent(student: Student) {
        students.removeIf { it.id == student.id }
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    companion object {
        private var instance: StudentManager? = null

        fun getInstance(): StudentManager {
            if (instance == null) {
                instance = StudentManager()
            }
            return instance!!
        }
    }
}