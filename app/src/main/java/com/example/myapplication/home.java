package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class home extends AppCompatActivity{

    String tag = "android:switcher:" + R.id.viewPager + ":" + 3;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    // FIXME: 12/5/2021  action bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void check(String name , Integer pic, Integer price , Integer type){

        order f = (order) getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            Toast.makeText(home.this, "Loading", Toast.LENGTH_SHORT).show();   // FIXME: 12/5/2021 slow loading object
        } else {
            f.displayReceivedData(name, pic, price, type);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);
            Intent i = new Intent(home.this , fin_pur.class);
            i.putExtra("name",cardHolderName);
            i.putExtra("num",cardNumber);
            i.putExtra("exp",expiry);
            i.putExtra("cvv",cvv);
            i.putStringArrayListExtra("list", (ArrayList<String>) menu_adapter_order.name);
            startActivity(i);
        }
    }
}