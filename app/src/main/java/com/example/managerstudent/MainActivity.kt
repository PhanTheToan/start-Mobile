package com.example.managerstudent

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextId: TextInputEditText
    private lateinit var recyclerViewStudents: RecyclerView
    private lateinit var buttonAdd: MaterialButton
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentDao: StudentDao
    private val studentList = mutableListOf<Student>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextId = findViewById(R.id.editTextId)
        buttonAdd = findViewById(R.id.buttonAdd)
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents)

        // Khởi tạo Room database
        val db = StudentDatabase.getDatabase(this)
        studentDao = db.studentDao()

        // Load danh sách sinh viên
        CoroutineScope(Dispatchers.IO).launch {
            val students = studentDao.getAllStudents()
            withContext(Dispatchers.Main) {
                studentList.addAll(students)
                studentAdapter = StudentAdapter(studentList) { student ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val deletedRows = studentDao.delete(student)
                        if (deletedRows > 0) {
                            withContext(Dispatchers.Main) {
                                studentList.remove(student)
                                studentAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
                recyclerViewStudents.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerViewStudents.adapter = studentAdapter
            }
        }

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val id = editTextId.text.toString().trim()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                val student = Student(studentId = id, name = name)
                CoroutineScope(Dispatchers.IO).launch {
                    studentDao.insert(student)
                    withContext(Dispatchers.Main) {
                        studentList.add(0, student)
                        studentAdapter.notifyDataSetChanged()
                        editTextName.setText("")
                        editTextId.setText("")
                    }
                }
            }
        }
    }
}