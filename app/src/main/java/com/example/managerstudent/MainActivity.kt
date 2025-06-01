package com.example.managerstudent

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextId: TextInputEditText
    private lateinit var buttonAdd: ImageButton
    private lateinit var recyclerViewStudents: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextId = findViewById(R.id.editTextId)
        buttonAdd = findViewById(R.id.buttonAdd)
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents)

        databaseHelper = DatabaseHelper(this)
        val studentList = databaseHelper.getAllStudents().toMutableList()

        studentAdapter = StudentAdapter(studentList) { student ->
            if (databaseHelper.deleteStudent(student.id)) {
                studentList.remove(student)
                studentAdapter.notifyDataSetChanged()
            }
        }

        recyclerViewStudents.layoutManager = LinearLayoutManager(this)
        recyclerViewStudents.adapter = studentAdapter

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val id = editTextId.text.toString().trim()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                val student = Student(name, id)
                if (databaseHelper.addStudent(student)) {
                    studentList.add(0, student)
                    studentAdapter.notifyDataSetChanged()
                    editTextName.setText("")
                    editTextId.setText("")
                }
            }
        }
    }
}