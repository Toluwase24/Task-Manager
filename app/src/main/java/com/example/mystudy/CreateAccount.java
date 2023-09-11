package com.example.mystudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAccount extends AppCompatActivity {

    Button SignUpButton;
    TextView LoginButton;
    EditText name, emailaddress, password, repeatpassword;
    AccountDatabase accountdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        SignUpButton =  findViewById(R.id.SignUpButton);
        LoginButton = findViewById(R.id.LoginButton);
        name = findViewById(R.id.name);
        emailaddress = findViewById(R.id.emailaddress);
        password = findViewById(R.id.password);
        repeatpassword = findViewById(R.id.repassword);
        accountdb = new AccountDatabase(this);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yourname = name.getText().toString();
                String email = emailaddress.getText().toString();
                String pass = password.getText().toString();
                String repass =  repeatpassword.getText().toString();

                if(yourname.equals("")||email.equals("")||pass.equals("")||repass.equals(""))
                    Toast.makeText(CreateAccount.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(repass)){
                        Boolean checkemail = accountdb.checkemailaddress(email);
                        if(checkemail == false){
                            Boolean insert = accountdb.insertaccountdata(email, yourname, pass);
                            if(insert == true){
                                Toast.makeText(CreateAccount.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(CreateAccount.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(CreateAccount.this, "Account already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CreateAccount.this, "Passwords don't match! kindly check again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(CreateAccount.this, Login.class);
                startActivity(intent);
            }
        });


    }
}