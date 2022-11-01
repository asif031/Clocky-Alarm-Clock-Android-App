package com.example.clocky;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    String hour;
    Button button1,button2,button3,button4;
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmTimePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        button1=findViewById(R.id.stopwatchbutt);
        button2=findViewById(R.id.timerButt);
        button3=findViewById(R.id.historyButt);
        button4=findViewById(R.id.biobutt);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStopwatchActivity();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimerActivity();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAlarmHistory();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBio();
            }
        });

    }
    public void OnToggleClicked(View view)
    {
        long time;
        if (((ToggleButton) view).isChecked())
        {

            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            hour=String.format((alarmTimePicker.getCurrentHour()).toString()+":"+(alarmTimePicker.getCurrentMinute()).toString());
            sendData();
            time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));


            if(System.currentTimeMillis()>time)
            {
                if (calendar.AM_PM == 0)
                    time = time + (1000*60*60*12);
                else
                    time = time + (1000*60*60*24);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
        }
        else
        {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }

    }

    public void openStopwatchActivity()
    {
        Intent intent2=new Intent(this,StopwatchActivity.class);
        startActivity(intent2);
    }
    public void openTimerActivity()
    {
        Intent intent3=new Intent(this,TimerActivity.class);
        startActivity(intent3);
    }
    public void onAlarmHistory(){
        Intent intent4=new Intent(this,historyActivity.class);
        startActivity(intent4);
    }
    public void sendData(){
        databaseReference=database.getReference();
        try {String id=databaseReference.push().getKey();
            databaseReference.child(id).setValue(hour);

        }
        catch (NullPointerException Ignored){}

    }
    public void openBio(){
        Intent intent5=new Intent(this,BIOActivity.class);
        startActivity(intent5);
    }

}
