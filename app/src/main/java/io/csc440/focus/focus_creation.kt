package io.csc440.focus

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_focus_creation.*
import kotlinx.android.synthetic.main.activity_main.*

class focus_creation : AppCompatActivity() {

    /*private var tasks = intent.getBundleExtra("tasks")
    val myIntent = getIntent()
    val bundle = myIntent.getBundleExtra("bundle")
    var tasks = bundle.getParcelableArrayList<Task>("tasks")
    */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus_creation)

        createButton.setOnClickListener(){

        }

    }

    fun backButtonClick(view: View){
        onBackPressed()
    }

    fun createTaskClick(view: View){
        val title = findViewById<EditText>(R.id.taskTitle).toString()
        val date = findViewById<CalendarView>(R.id.taskDate).date.toString()
        val priority = findViewById<Switch>(R.id.prioritySwitch).isChecked

        var newTask = Task(title,priority,date)

        //tasks?.add(newTask)

        val bundle = Bundle()
        bundle.putParcelable("newTask", newTask)
        val myIntent = Intent()
        myIntent.putExtra("bundle", bundle)


        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }
}

