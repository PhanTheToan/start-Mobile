package com.example.manager_student


import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.UUID

class AddStudentActivity : AppCompatActivity() {
    private lateinit var etName: TextInputEditText
    private lateinit var etStudentId: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var btnSave: Button

    private val studentManager = StudentManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Hiển thị nút quay lại trên ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Thêm Sinh Viên"

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etName = findViewById(R.id.etName)
        etStudentId = findViewById(R.id.etStudentId)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun setupListeners() {
        btnSave.setOnClickListener {
            if (validateInputs()) {
                saveStudent()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val name = etName.text.toString().trim()
        val studentId = etStudentId.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        if (name.isEmpty()) {
            etName.error = "Vui lòng nhập họ tên"
            isValid = false
        }

        if (studentId.isEmpty()) {
            etStudentId.error = "Vui lòng nhập MSSV"
            isValid = false
        }

        if (email.isEmpty()) {
            etEmail.error = "Vui lòng nhập email"
            isValid = false
        }

        if (phone.isEmpty()) {
            etPhone.error = "Vui lòng nhập số điện thoại"
            isValid = false
        }

        return isValid
    }

    private fun saveStudent() {
        val name = etName.text.toString().trim()
        val studentId = etStudentId.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        val newStudent = Student(
            id = UUID.randomUUID().toString(),
            name = name,
            studentId = studentId,
            email = email,
            phone = phone
        )

        studentManager.addStudent(newStudent)
        Toast.makeText(this, "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show()

        setResult(RESULT_OK)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}