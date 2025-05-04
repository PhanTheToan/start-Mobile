package com.example.manager_student

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val context: Context,
    private var students: List<Student>
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var position: Int = 0

    fun getPosition(): Int = position
    fun setPosition(position: Int) {
        this.position = position
    }

    fun getItem(position: Int): Student = students[position]

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)

        holder.itemView.setOnLongClickListener {
            setPosition(holder.adapterPosition)
            false
        }
    }

    override fun getItemCount(): Int = students.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvStudentId: TextView = itemView.findViewById(R.id.tvStudentId)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(student: Student) {
            tvName.text = student.name
            tvStudentId.text = student.studentId
            tvEmail.text = student.email
            tvPhone.text = student.phone
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(0, 0, 0, "Cập nhật")
            menu?.add(0, 1, 1, "Xóa")
            menu?.add(0, 2, 2, "Gọi điện")
            menu?.add(0, 3, 3, "Gửi email")
        }
    }
}