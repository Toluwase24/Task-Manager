package com.example.mystudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystudy.CreateAccount;
import com.example.mystudy.R;

public class Login extends AppCompatActivity {

    Button loginButton;
    TextView signUpButton;
    EditText loginemailaddress, loginpassword;
    AccountDatabase accountdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginButton =  findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        loginemailaddress = findViewById(R.id.loginemailaddress);
        loginpassword = findViewById(R.id.loginpassword);
        accountdb = new AccountDatabase(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginemailaddress.getText().toString();
                String pass = loginpassword.getText().toString();

                if(email.equals("")||pass.equals(""))
                    Toast.makeText(Login.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkemailpass = accountdb.checkemailaddresspassword(email, pass);
                    if(checkemailpass == true){
                        Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(Login.this, "Incorrect Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });


    }
}