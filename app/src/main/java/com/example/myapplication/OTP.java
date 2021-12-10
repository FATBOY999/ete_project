package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.mukesh.OtpView;
import java.util.Random;

public class OTP extends AppCompatActivity {

    OtpView opt;
    TextView resend;
    Random rand = new Random();
    int min = 100000;
    int max = 999999;
    int upperBound = max - min + 1;
    int otpp =  min + rand.nextInt(upperBound);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        NotificationChannel channel = new NotificationChannel("My notification","My notification",NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager =getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        opt = findViewById(R.id.otp_view);
        resend = findViewById(R.id.resendd);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"My notification");
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);

        opt.setOtpCompletionListener(otp -> {
            if (String.valueOf(otpp).equals(otp)){
                String cont = getIntent().getStringExtra("content");
                switch (cont) {
                    case "to_login": {
                        UserDataBase db = UserDataBase.getDbInstance(getApplicationContext());
                        db.userDao().updatepassword(getIntent().getStringExtra("email"), getIntent().getStringExtra("password"));
                        Snackbar.make(findViewById(android.R.id.content),"Update Successful",Snackbar.LENGTH_SHORT).show();
                        Intent i = new Intent(OTP.this, LoginActivity.class);
                        startActivity(i);
                        break;
                    }
                    case "to_home":
                        Snackbar.make(findViewById(android.R.id.content),"Verification Success",Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(OTP.this, home.class);
                        startActivity(intent);
                        break;
                }
            }
            else {
                Snackbar.make(findViewById(android.R.id.content),"Incorrect OTP",Snackbar.LENGTH_LONG).show();
            }
        });

        builder.setContentTitle("OTP FOR LOGIN");
        builder.setContentText(String.valueOf(otpp));
        builder.setSmallIcon(R.drawable.ic_baseline_message_24);
        builder.setAutoCancel(true);
        managerCompat.notify(1,builder.build());

        resend.setOnClickListener(v -> {
            otpp = min + rand.nextInt(upperBound);
            builder.setContentText(String.valueOf(otpp));
            managerCompat.notify(1,builder.build());

        });

    }
}