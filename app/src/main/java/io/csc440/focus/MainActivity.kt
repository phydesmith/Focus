package io.csc440.focus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var tasks = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        //  Test comment for source control test.

        var myAdapter = ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, tasks)
        //lateinit var focusTasks.adapter = myAdapter
        //  2nd test comment for guide.


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun newTaskClick(view: View){
        val myIntent = Intent(this, focus_creation::class.java)
        val bundle = Bundle()
        bundle.putSerializable("tasks", tasks)
        myIntent.putExtra("bundle", bundle)
        startActivity(myIntent)
    }



}
