package com.example.mystudy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class AlertDetails extends AppCompatActivity {
    TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasknotification);
        textView = findViewById(R.id.tv_message);
        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("message"));
    }

}
