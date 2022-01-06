package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener  {
            override fun onItemLongClicked(position: Int) {
                //1. Remove item from list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
//        // Detect when user clicks on 'Add' button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            //This code is executed when button is clicked
//            Log.i("Jonathan", "User clicked on a button")
//        }

        loadItems()

        // look up recyclerview in the layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up button and input field, so user can add a task
        val inputTextField = findViewById<EditText>(R.id.addtaskfield)
        findViewById<Button>(R.id.button).setOnClickListener {
            //1. Grab text in addtaskfield field
            val userInputTask = inputTextField.text.toString()

            //2. Add string to list of tasks
            listOfTasks.add(userInputTask)

            // Notify adapter that data has been updated. Important to update view.
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //3. Reset text field
            inputTextField.setText("")

            saveItems()
        }
    }

    // Save the data that user has added, by writing and readiing from a file

    // method to get the file we need
    fun getDataFile() : File {

        //Every line is going to represent a specific task in our list
        return File(filesDir, "data.txt")
    }

    // Create method to load items by reading every line in file

    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into the data file

    fun saveItems() {
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}

