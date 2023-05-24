package com.example.dbfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication.Student
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val db = Firebase.database.getReference("Student")

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var imageEditText: EditText
    private lateinit var emailEditText: EditText

    private lateinit var nameTextView: TextView
    private lateinit var surnameTextView: TextView
    private lateinit var idTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var imageView: ImageView

    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        listeners()
    }
    private fun listeners() {
        saveButton.setOnClickListener{
            val name = nameEditText.text.toString()
            val surname = surnameEditText.text.toString()
            val id = idEditText.text.toString()
            val image = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg"
            val email = emailEditText.text.toString()

            if(id.length!=13){
                Toast.makeText(this, "ID must be 13 characters length", Toast.LENGTH_LONG).show()
            } else if(!email.contains("@")){
                Toast.makeText(this, "Provide valid email", Toast.LENGTH_LONG).show()
            } else{
                val studentInfo = Student(
                    name, surname, id, image, email
                )
                db.child("student1").setValue(studentInfo)
            }

        }
    }

    private fun init() {
        imageView = findViewById(R.id.imageView)
        nameTextView = findViewById(R.id.nameTextView)

        nameEditText = findViewById(R.id.nameEditText)
        surnameEditText = findViewById(R.id.surnameEditText)
        idTextView = findViewById(R.id.idTextView)
        emailEditText = findViewById(R.id.emailEditText)
        imageEditText = findViewById(R.id.imageEditText)

        surnameTextView = findViewById(R.id.surnameTextView)
        idTextView = findViewById(R.id.idTextView)
        emailTextView = findViewById(R.id.emailTextView)
        saveButton = findViewById(R.id.saveButton)


        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val studentInfo = snapshot.getValue(Student::class.java) ?: return
                nameTextView.text = studentInfo.name
                surnameTextView.text = studentInfo.surname
                idTextView.text = studentInfo.id
                emailTextView.text = studentInfo.email
                Glide.with(this@MainActivity).load(studentInfo.image).into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}