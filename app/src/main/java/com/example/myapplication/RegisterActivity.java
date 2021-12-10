package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText usr_name,usr_email,usr_pass,usr_re_pass;
    AppCompatButton usr_reg_but;
    TextInputLayout lay_name , lay_mail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserDataBase db = UserDataBase.getDbInstance(getApplicationContext());

        usr_name = findViewById(R.id.Reg_Name);
        usr_email = findViewById(R.id.Reg_Email);
        usr_pass = findViewById(R.id.Reg_Password);
        usr_re_pass = findViewById(R.id.Reg_Con_Password);
        usr_reg_but = findViewById(R.id.user_register);
        lay_name = findViewById(R.id.lay_name);
        lay_mail = findViewById(R.id.lay_mail);

        usr_reg_but.setOnClickListener(v -> {

            String name = Objects.requireNonNull(usr_name.getText()).toString();
            String email = Objects.requireNonNull(usr_email.getText()).toString();
            String password  = Objects.requireNonNull(usr_pass.getText()).toString();
            String repassword = Objects.requireNonNull(usr_re_pass.getText()).toString();

            if (name.isEmpty()){
                usr_name.setError("Please fill the required field");
                return;
            } else {
                lay_name.setEndIconDrawable(R.drawable.ic_baseline_check_24);
            }
            if (email.isEmpty()){
                usr_email.setError("Please Enter the required fields");
                return;
            }
            if (password.isEmpty()){
                usr_pass.setError("Please Enter required field");
                return;
            }
            if (repassword.isEmpty()){
                usr_re_pass.setError("Please Enter the required field");
                return;
            }
            if (email.endsWith("@gmail.com")){
                if (email.length()==10){
                    usr_email.setError("Please Enter valid Email");
                    return;
                }
                else {
                    List<User> l = db.userDao().checkemail(email);
                    if (l.size() == 1) {
                        usr_email.setError("Gmail already registered");
                        return;
                    }
                    else {
                        lay_mail.setEndIconDrawable(R.drawable.ic_baseline_check_24);
                    }
                }
            }
            else {
                usr_email.setError("Only Gmails are allowed");
            }

            if (password.isEmpty()){
                usr_pass.setError("Please fill the field");
            }
            else {
                if (check(password)){
                    if (password.equals(repassword)){
                        User user = new User();
                        user.username = name;
                        user.useremail = email;
                        user.userpassword = password;
                        db.userDao().insertUser(user);
                        finish();
                    }
                    else {
                        usr_pass.setError("Passwords Doesn't Match");
                        usr_re_pass.setError("Passwords Doesn't Match");
                    }
                }
                else {
                    usr_pass.setError("Password must contain a-z,A-Z,0-9,special character");
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