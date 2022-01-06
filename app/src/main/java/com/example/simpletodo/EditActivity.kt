package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Edit Task")
        setContentView(R.layout.activity_edit)
        val rowText = getIntent().getStringExtra("row")
        val position = getIntent().getIntExtra("position", 0)
        findViewById<EditText>(R.id.editTask).setText(rowText)


        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val data = Intent()
            val updatedRow = findViewById<EditText>(R.id.editTask)
            data.putExtra("newRow", updatedRow.text.toString())
            data.putExtra("position", position)
            data.putExtra("code", 200)
            setResult(RESULT_OK, data)
            finish()
        }
    }
}