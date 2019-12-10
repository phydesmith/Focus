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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    private var tasks_st = ArrayList<String>()
    private lateinit var myAdapter : ArrayAdapter<String>
    private var priorities = ArrayList<String>()
    private lateinit var myPriorityAdapter :ArrayAdapter<String>

    private var tasks = ArrayList<Task?>()
    private var task_code = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  DB Creation
        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)
        myDb.execSQL("CREATE TABLE IF NOT EXISTS Tasks (description TEXT, date INTEGER, priority INTEGER);")
        myDb.execSQL("DELETE FROM Tasks;"); // deletes all tasks for testing purposes
        myDb.close()

        // Adapter Stuff
        tasks_st.add(""); // so 0 index exists for add task method
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks_st)
        focusTasks.adapter = myAdapter

        myPriorityAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, priorities)
        priorityTasks.adapter = myPriorityAdapter

    }


    fun newTaskClick(view: View){
        val myIntent = Intent(this, focus_creation::class.java)
        startActivityForResult(myIntent, 1)
    }

    fun toPomodoroClick(view: View){
        val myIntent = Intent(this, pomodoro::class.java)
        startActivity(myIntent)
    }

    fun updateTasks(){
        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)
        var nextTask = myDb.rawQuery("SELECT description, MIN(date), priority, date FROM Tasks", null)

        nextTask.moveToFirst()
        Log.d("afterDelete", nextTask.toString())

        var focus = Task(
            nextTask.getString( nextTask.getColumnIndex("description") ),
            nextTask.getInt(    nextTask.getColumnIndex("priority") ),
            nextTask.getLong(   nextTask.getColumnIndex("date"))
        )
        Log.d("afterDelete", focus.toString())

        tasks_st[0] = focus.lToString()
        myAdapter.notifyDataSetChanged()
        Log.d("tasks", tasks_st.get(0));

        // close
        //nextTask.close()
        myDb.close()

    }

    fun updateTasksClick(view: View){
        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)
        var nextTask = myDb.rawQuery("SELECT description, MIN(date), priority, date FROM Tasks", null)
        nextTask.moveToFirst()


        var focus = Task(
            nextTask.getString( nextTask.getColumnIndex("description") ),
            nextTask.getInt(    nextTask.getColumnIndex("priority") ),
            nextTask.getLong(   nextTask.getColumnIndex("date"))
        )

        tasks_st.set(0, focus.lToString())
        myAdapter.notifyDataSetChanged()
        Log.d("tasks", tasks_st.get(0));

        nextTask.close()
        myDb.close()

    }

    fun updatePriorityTasks(){
        priorities.clear();

        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)
        var priorityTasks = myDb.rawQuery("SELECT description, priority, date FROM Tasks WHERE priority == 1", null)

        priorityTasks.moveToFirst()

        while (!priorityTasks.isAfterLast) {
            var priority = Task(
                priorityTasks.getString( priorityTasks.getColumnIndex("description") ),
                priorityTasks.getInt(    priorityTasks.getColumnIndex("priority") ),
                priorityTasks.getLong(   priorityTasks.getColumnIndex("date"))
            )

            priorities.add(priority.lToString())
            Log.d("tasks", priority.lToString())
            priorityTasks.moveToNext()
        }
        myPriorityAdapter.notifyDataSetChanged()

        // close
        priorityTasks.close()
        myDb.close()
    }

    fun deleteTaskClick(view: View){


        tasks_st.removeAt(0)

        val database_name = "tasks"
        val myDb = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null)
        var nextTask = myDb.rawQuery("SELECT description, MIN(date), priority, date FROM Tasks", null)
        nextTask.moveToFirst()

        var desc = nextTask.getString(nextTask.getColumnIndex("description"))
        Log.d("var", desc)
        Log.d("countBefore", nextTask.toString())
        myDb.execSQL("DELETE FROM Tasks WHERE description LIKE '$desc' ")
        Log.d("after", "after delete")
        Log.d("countAfter", nextTask.toString())
        //myAdapter.notifyDataSetChanged()
        myDb.close()
        tasks_st.add("")
        updateTasks()
        updatePriorityTasks()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, myIntent: Intent?) {
        Log.d("main", "IN MAIN")
        super.onActivityResult(requestCode, resultCode, myIntent)

        updateTasks()
        updatePriorityTasks()

        /*
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

         */
    }



}