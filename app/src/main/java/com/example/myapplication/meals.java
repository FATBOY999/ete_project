package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class meals extends Fragment {

    List<Integer> meals_pic = new ArrayList<>(Arrays.asList(R.drawable.meals_chickendumbiryani,R.drawable.meals_muttonbiryani,R.drawable.meals_paneerbiryani));
    List<String> meals_names = new ArrayList<>(Arrays.asList("Chicken Dum Biryani","Mutton Biryani","Paneer Biryani"));
    List<Integer> meals_price = new ArrayList<>(Arrays.asList(100,200,300,400,500));
    List<Integer> meals_type = new ArrayList<>(Arrays.asList(R.drawable.nonveglogo,R.drawable.nonveglogo,R.drawable.veglogo));

    ListView meals_lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_meals, container, false);
        meals_lv = rootview.findViewById(R.id.meals_lv);

        menu_adapter adapter = new menu_adapter(getActivity(),meals_pic,meals_names,meals_price,meals_type,getContext());
        meals_lv.setAdapter(adapter);


        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}