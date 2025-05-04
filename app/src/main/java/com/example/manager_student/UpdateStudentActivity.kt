package com.example.manager_student
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class UpdateStudentActivity : AppCompatActivity() {
    private lateinit var etName: TextInputEditText
    private lateinit var etStudentId: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var btnUpdate: Button

    private val studentManager = StudentManager.getInstance()
    private var studentId: String? = null
    private var currentStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        // Hiển thị nút quay lại trên ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cập Nhật Sinh Viên"

        studentId = intent.getStringExtra("STUDENT_ID")
        if (studentId == null) {
            Toast.makeText(this, "Không tìm thấy thông tin sinh viên", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        currentStudent = studentManager.getStudentById(studentId!!)
        if (currentStudent == null) {
            Toast.makeText(this, "Không tìm thấy thông tin sinh viên", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initViews()
        fillStudentData()
        setupListeners()
    }

    private fun initViews() {
        etName = findViewById(R.id.etName)
        etStudentId = findViewById(R.id.etStudentId)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnUpdate = findViewById(R.id.btnUpdate)
    }

    private fun fillStudentData() {
        currentStudent?.let { student ->
            etName.setText(student.name)
            etStudentId.setText(student.studentId)
            etEmail.setText(student.email)
            etPhone.setText(student.phone)
        }
    }

    private fun setupListeners() {
        btnUpdate.setOnClickListener {
            if (validateInputs()) {
                updateStudent()
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

    private fun updateStudent() {
        val name = etName.text.toString().trim()
        val studentIdText = etStudentId.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()

        currentStudent?.let { student ->
            student.name = name
            student.studentId = studentIdText
            student.email = email
            student.phone = phone

            studentManager.updateStudent(student)
            Toast.makeText(this, "Cập nhật thông tin sinh viên thành công", Toast.LENGTH_SHORT).show()

            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}