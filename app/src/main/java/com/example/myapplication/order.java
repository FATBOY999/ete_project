package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;

import java.util.ArrayList;
import java.util.List;

public class order extends Fragment {

    ListView order_lv;
    ExtendedFloatingActionButton order_order;
    List<Integer> order_pic = new ArrayList<>();
    List<String> order_name = new ArrayList<>();
    List<Integer> order_type = new ArrayList<>();
    List<Integer> order_price = new ArrayList<>();
    View rootview;
    List<Integer> count = new ArrayList<>();

    menu_adapter_order adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_order, container, false);
        order_lv = rootview.findViewById(R.id.order_lv);
        order_order = rootview.findViewById(R.id.order_order);

        order_order.setOnClickListener(v -> {
            int total_bill = 0 , size = menu_adapter_order.price.size();
            for (int i=0;i<size;i++){
                total_bill += menu_adapter_order.price.get(i)*menu_adapter_order.count.get(i);
            }
            if (total_bill == 0){
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Empty cart",Snackbar.LENGTH_SHORT).show();
            }
            else {
                new FancyGifDialog.Builder(getActivity())
                        .setTitle("Proceed to Pay?")
                        .setMessage("Total bill is " + total_bill)
                        .setTitleTextColor(R.color.black)
                        .setDescriptionTextColor(R.color.black)
                        .setNegativeBtnText("Cancel")
                        .setPositiveBtnBackground(R.color.colorPrimaryDark)
                        .setPositiveBtnText("Pay")
                        .setNegativeBtnBackground(R.color.teal_200)
                        .setGifResource(R.drawable.gif1)   //Pass your Gif here
                        .isCancellable(true)
                        .OnPositiveClicked(() -> {
                            final int GET_NEW_CARD = 2;
                            Intent intent = new Intent(getActivity(), CardEditActivity.class);
                            startActivityForResult(intent, GET_NEW_CARD);
                        })
                        .OnNegativeClicked(() -> {
                        }).build();
            }
        });

        adapter = new menu_adapter_order(getActivity(),order_pic,order_name,order_price,order_type,count);
        order_lv.setAdapter(adapter);


        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void displayReceivedData(String name , Integer pic , Integer price , Integer type) {
        if (order_name.contains(name)) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), name+" is already added", Snackbar.LENGTH_LONG).show();
        } else {
            order_name.add(name);
            order_pic.add(pic);
            order_price.add(price);
            order_type.add(type);
            count.add(1);
            Snackbar.make(getActivity().findViewById(android.R.id.content), name+" is added", Snackbar.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
    }
}