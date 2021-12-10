package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText email , password;
    AppCompatButton loginn;
    AppCompatTextView registerr , forgott;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        loginn = findViewById(R.id.appCompatButtonLogin);
        registerr = findViewById(R.id.textViewLinkRegister);
        forgott = findViewById(R.id.forgot_password);

        loginn.setOnClickListener(v -> {

            if (email.getText().toString().isEmpty()) {
                email.setError("Please fill the field");
                return;
            }
            if (password.getText().toString().isEmpty()) {
                password.setError("Please fill the field");
                return;
            } else {
                UserDataBase db = UserDataBase.getDbInstance(getApplicationContext());
                List<User> list = db.userDao().checkUser(Objects.requireNonNull(email.getText()).toString(), Objects.requireNonNull(password.getText()).toString());
                if (list.size() == 1) {
                    Intent i = new Intent(LoginActivity.this, OTP.class);
                    i.putExtra("content", "to_home");
                    startActivity(i);
                } else {
                    email.setError("Wrong Credentials");
                    password.setError("Wrong Credentials");
                }
            }
        });

        registerr.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
            startActivity(intent);
        });

        forgott.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this , ForgotAct.class);
            startActivity(i);
        });
    }
}