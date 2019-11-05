package io.csc440.focus

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
            val create = findViewById<Button>(R.id.createButton)
            createTaskClick(create)
        }

    }

    fun backButtonClick(view: View){
        onBackPressed()
    }

    fun createTaskClick(view: View){


        Log.d("CREATE", "button clicked")

        val title = findViewById<EditText>(R.id.taskTitle).text.toString()
        val date = findViewById<CalendarView>(R.id.taskDate).date.toString()
        val priority = findViewById<Switch>(R.id.prioritySwitch).isChecked

        // DB Stuff
        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)
        myDb.execSQL("INSERT INTO tasks (description) VALUES ('$title');")


        var resultSet = myDb.rawQuery("SELECT * FROM Tasks", null)
        resultSet.moveToFirst()
        var descriptionIndex = resultSet.getColumnIndex("description")
        //var dateIndex = resultSet.getColumnIndex("date")
        //var priIndex = resultSet.getColumnIndex("priority")
        var description = resultSet.getString(descriptionIndex)
        //var priorityOP = resultSet.getString(priIndex)
        //var date = resultSet
        Log.d("dataBasee", "HERE")
        System.out.println("HERE MOFO: " + description + " PRIORITY? ")// + priorityOP)

        myDb.close()
        // end DB Stuff

        var newTask = Task(title,priority,date)

        //tasks?.add(newTask)

        val bundle = Bundle()
        bundle.putParcelable("newTask", newTask)
        val myIntent = Intent()
        myIntent.putExtra("bundle", bundle)


        setResult(Activity.RESULT_OK, myIntent)


        this.finish()
    }
}

