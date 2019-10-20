package io.csc440.focus

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

    fun createTaskClick(view: View) : Task {
        val title = findViewById<EditText>(R.id.taskTitle).toString()
        val date = findViewById<CalendarView>(R.id.taskDate).date.toString()
        val priority = findViewById<Switch>(R.id.prioritySwitch).isChecked

        return Task(title, priority, date)

    }
}

