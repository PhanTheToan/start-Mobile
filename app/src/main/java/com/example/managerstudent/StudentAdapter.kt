package com.example.managerstudent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class StudentAdapter(
    private val studentList: List<Student>,
    private val deleteListener: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.textViewName.text = student.name
        holder.textViewId.text = student.studentId

        holder.buttonDelete.setOnClickListener {
            deleteListener(student)
        }
    }

    override fun getItemCount(): Int = studentList.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewId: TextView = itemView.findViewById(R.id.textViewId)
        val buttonDelete: MaterialButton = itemView.findViewById(R.id.buttonDelete)
    }
}