package com.example.manager_student

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var adapter: StudentAdapter
    private val studentManager = StudentManager.getInstance()

    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            updateStudentList()
        }
    }

    private val updateStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            updateStudentList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        tvEmpty = findViewById(R.id.tvEmpty)

        // Add this code for FAB
        val fabAddStudent = findViewById<FloatingActionButton>(R.id.fabAddStudent)
        fabAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            addStudentLauncher.launch(intent)
        }

        setupRecyclerView()
        updateStudentList()
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(this, studentManager.getStudents())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        registerForContextMenu(recyclerView)
    }

    private fun updateStudentList() {
        val students = studentManager.getStudents()
        adapter.updateData(students)

        if (students.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = adapter.getPosition()
        val student = adapter.getItem(position)

        return when (item.itemId) {
            0 -> { // Cập nhật
                val intent = Intent(this, UpdateStudentActivity::class.java).apply {
                    putExtra("STUDENT_ID", student.id)
                }
                updateStudentLauncher.launch(intent)
                true
            }
            1 -> { // Xóa
                showDeleteConfirmationDialog(student)
                true
            }
            2 -> { // Gọi điện
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    setData(Uri.parse("tel:${student.phone}"))
                }
                startActivity(intent)
                true
            }
            3 -> { // Gửi email
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    setData(Uri.parse("mailto:${student.email}"))
                }
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showDeleteConfirmationDialog(student: Student) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                studentManager.deleteStudent(student)
                updateStudentList()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}