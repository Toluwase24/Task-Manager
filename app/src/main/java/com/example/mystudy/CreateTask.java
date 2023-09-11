package com.example.mystudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateTask extends AppCompatActivity {

    EditText task_title, task_description;
    EditText task_date, task_time;
    TextView add_taskreminder;
    String timeTonotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        add_taskreminder = findViewById(R.id.savetaskreminder);

        task_title = findViewById(R.id.inputTaskTitle);
        task_description = findViewById(R.id.inputTaskDescription);
        task_date = findViewById(R.id.date_text);
        task_time = findViewById(R.id.time_text);

        task_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        task_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               selectTime();
            }
        });

        add_taskreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateTask.this, MainActivity.class));

                String title = task_title.getText().toString();
                String description = task_description.getText().toString();
                String date = task_date.getText().toString();
                String time = task_time.getText().toString();


                if (title.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You didn't create any task", Toast.LENGTH_SHORT).show();
                } else {
                    if (time.equals("time") || date.equals("date")) {
                        Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                    } else {
                        proccessinsert(title, description, date, time);
                    }
                }
            }
        });

    }

    private  void proccessinsert(String title, String description, String date, String time) {
        String result = new TaskDatabase(this).addtaskReminders(title, description, date, time);
        setAlarm(title, date, time);
        task_title.setText("");
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    private void selectTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;                                                        //temp variable to store the time to set alarm
                task_time.setText(FormatTime(i, i1));                                                //sets the button text as selected time
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    private void selectDate() {                                                                     //this method performs the date picker task
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                task_date.setText(day + "-" + (month + 1) + "-" + year);                             //sets the selected date as test for button
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {                                                //this method converts the time into 12hr farmat and assigns am or pm

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }





    private void setAlarm( String text, String date, String time){
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        //create an intent to show notification
        Intent intent = new Intent(CreateTask.this, TaskNotificationAlarm.class);
        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent,  PendingIntent.FLAG_IMMUTABLE);
        String dateandtime = date + " " +  timeTonotify;
        DateFormat formatter =  new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            alarmManager.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);
            Toast.makeText(getApplicationContext(), "Alarm", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intentBack = new Intent(getApplicationContext(), MainActivity.class);
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentBack);
    }
}
