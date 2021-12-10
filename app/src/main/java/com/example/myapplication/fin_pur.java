package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cooltechworks.creditcarddesign.CreditCardView;
import com.google.android.material.snackbar.Snackbar;
import com.mukesh.OtpView;

import java.util.Random;

public class fin_pur extends AppCompatActivity {


    Random rand = new Random();
    int min = 100000;
    int max = 999999;
    int upperBound = max - min + 1;
    int otpp =  min + rand.nextInt(upperBound);
    int n = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_pur);

        AppCompatTextView fin_res = findViewById(R.id.fin_resend);
        OtpView fin_otp = findViewById(R.id.pur_otp);
        CreditCardView fin_card = new CreditCardView(getApplicationContext());
        fin_card = findViewById(R.id.pur_card);

        NotificationChannel channel = new NotificationChannel("My notificationn","My notificationn", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager =getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"My notificationn");
        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);

        builder.setContentTitle("OTP FOR LOGIN");
        builder.setContentText(String.valueOf(otpp));
        builder.setSmallIcon(R.drawable.ic_baseline_message_24);
        builder.setAutoCancel(true);
        managerCompat.notify(1,builder.build());


        String name = getIntent().getStringExtra("name") , number = getIntent().getStringExtra("num") , exp = getIntent().getStringExtra("exp") , cvv = getIntent().getStringExtra("cvv");

        fin_card.setCardNumber(number);
        fin_card.setCardExpiry(exp);
        fin_card.setCardHolderName(name);
        fin_card.setCVV(cvv);

        CreditCardView finalFin_card = fin_card;
        fin_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (n%2==1){
                  finalFin_card.showBack();
              }
              else {
                  finalFin_card.showFront();
              }
              ++n;
              if (n>2){
                  n=1;
              }
            }
        });

        fin_otp.setOtpCompletionListener(otp -> {
            if (otp.equals(String.valueOf(otpp))){
                Intent i = new Intent(fin_pur.this , rateee.class);
                i.putStringArrayListExtra("list_name", getIntent().getStringArrayListExtra("list"));
                startActivity(i);
            }
            else {
                Snackbar.make(findViewById(android.R.id.content),"Wrong Otp",Snackbar.LENGTH_SHORT).show();
            }
        });

        fin_res.setOnClickListener(v -> {
            otpp = min + rand.nextInt(upperBound);
            builder.setContentText(String.valueOf(otpp));
            managerCompat.notify(1,builder.build());
        });

    }
}