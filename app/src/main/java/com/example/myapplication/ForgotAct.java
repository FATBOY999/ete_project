package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        TextInputEditText for_email , for_pass , for_re_pass;
        AppCompatButton for_reg;

        for_email = findViewById(R.id.forg_email);
        for_pass = findViewById(R.id.forg_password);
        for_re_pass = findViewById(R.id.forg_re_password);
        for_reg = findViewById(R.id.forg_register);

        for_reg.setOnClickListener(v -> {
            String email = Objects.requireNonNull(for_email.getText()).toString() , pass = Objects.requireNonNull(for_pass.getText()).toString() , repass = Objects.requireNonNull(for_re_pass.getText()).toString();

            if (email.isEmpty()){
                for_email.setError("Please Fill The Field");
                return;
            }

            if (pass.isEmpty()){
                for_pass.setError("Please fill the field");
                return;
            }

            if (repass.isEmpty()){
                for_re_pass.setError("Please fill the field");
                return;
            }

            UserDataBase db = UserDataBase.getDbInstance(getApplicationContext());
            List<User> list = db.userDao().checkemail(email);
            if (list.size() == 0){
                for_email.setError("No such email exists");
            }
            else {
                if (pass.equals(repass)) {
                    if (check(pass)) {
                        Intent i = new Intent(ForgotAct.this, OTP.class);
                        i.putExtra("content", "to_login");
                        i.putExtra("email", email);
                        i.putExtra("password", pass);
                        startActivity(i);
                    }
                    else {
                        for_pass.setError("Password must contain a-z,A-Z,0-9,special character");
                    }
                }
                else {
                    for_pass.setError("Passwords Doesn't Match");
                    for_re_pass.setError("Passwords Doesn't Match");
                }
            }
        });
    }
    public static boolean check(String str)
    {
        String regex = "^(?=.*[a-z])(?=." + "*[A-Z])(?=.*\\d)" + "(?=.*[-+_!@#$%^&*., ?]).+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}