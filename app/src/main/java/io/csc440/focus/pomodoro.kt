package io.csc440.focus

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_pomodoro.*
import java.text.DecimalFormat

class pomodoro : AppCompatActivity() {

    //Notification Builder
    var builder = NotificationCompat.Builder(this, "pomodoroChannel")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro)

        pomoStart.setOnClickListener {
            timer(1);
        }

        //Calling notification channel builder
        createNotificationChannel()

        //Setting Notification Attributes
        val intent = Intent(this, pomodoro::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //Setting Notification Content

        builder.setSmallIcon(R.drawable.clockicon)
            .setContentTitle("Focus List")
            .setContentText("Your Pomodoro Timer is Finished!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

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
                with(NotificationManagerCompat.from(this@pomodoro )){
                    notify(1, builder.build())
                }

            }
        }.start()
    }

    // onclick in xml for pomoBack
    public fun pomoBackButtonClick(view: View){
        onBackPressed()
    }

    //Building Notification Channel
    @TargetApi(26)
    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Focus List"
            val descriptionText = "Your Pomodoro timer is finished!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("pomodoroChannel", name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

    }

}
