package io.csc440.focus

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var tasks = ArrayList<Task?>()
    private var tasks_st = ArrayList<String>()
    private var task_code = 1
    private lateinit var myAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        //  Test comment for source control test.


        //  Data base stuff
        var created = false;
        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)


        if (!created) {
            myDb.execSQL("CREATE TABLE IF NOT EXISTS Tasks (description VARCHAR(100), date DATE, priority BOOLEAN);")
            created = true;
            myDb.close()
        }

        //lateinit var focusTasks.adapter = myAdapter
        //  2nd test comment for guide.
         myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks_st)

         //var listview:ListView = findViewById<ListView>(R.id.focusTasks)
         focusTasks?.adapter = myAdapter


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun newTaskClick(view: View){
        val myIntent = Intent(this, focus_creation::class.java)
        startActivityForResult(myIntent, 1)
    }


    fun printTasks(view: View){
        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, myIntent: Intent?) {
        Log.d("main", "IN MAIN")
        super.onActivityResult(requestCode, resultCode, myIntent)
        if(requestCode == task_code){
            if(myIntent != null){
                val bundle = myIntent.getBundleExtra("bundle")
                var newTask = bundle.getParcelable<Task>("newTask")
                tasks.add(newTask)
                tasks_st.add(tasks[0].toString())
                myAdapter.notifyDataSetChanged()

                var string = tasks[0].toString()
                Log.d("TASKS", "$string")

            }
        }
    }



}
