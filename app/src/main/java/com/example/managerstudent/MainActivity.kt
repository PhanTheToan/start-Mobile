package com.example.managerstudent

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextId: TextInputEditText
    private lateinit var buttonAdd: ImageButton
    private lateinit var recyclerViewStudents: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextId = findViewById(R.id.editTextId)
        buttonAdd = findViewById(R.id.buttonAdd)
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents)

        studentList = ArrayList()
        studentAdapter = StudentAdapter(studentList) { student ->
            studentList.remove(student)
            studentAdapter.notifyDataSetChanged()
        }

        recyclerViewStudents.layoutManager = LinearLayoutManager(this)
        recyclerViewStudents.adapter = studentAdapter

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val id = editTextId.text.toString().trim()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                studentList.add(0, Student(name, id))
                studentAdapter.notifyDataSetChanged()

                editTextName.setText("")
                editTextId.setText("")
            }
        }
    }
}