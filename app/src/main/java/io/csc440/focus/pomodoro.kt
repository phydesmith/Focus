package io.csc440.focus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_pomodoro.*
import java.text.DecimalFormat

class pomodoro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro)

        pomoStart.setOnClickListener {
            timer(25);
        }

    }

    fun timer(minutes : Long) {
        var df = DecimalFormat("00");
        object : CountDownTimer(minutes * 60000 , 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var minutesLeft = (millisUntilFinished/1000) / 60;
                var seconds = (millisUntilFinished/1000) % 60
                mainHeader.setText("" + df.format(minutesLeft) + ":" + df.format(seconds) )
            }
            override fun onFinish() {
                mainHeader.setText("Finished!")

            }
        }.start()
    }

    // onclick in xml for pomoBack
    fun pomoBackButtonClick(view: View){
        onBackPressed()
    }
}
