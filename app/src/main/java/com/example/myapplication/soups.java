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

public class soups extends Fragment {

    ListView soups_lv;

    List<Integer> soup_pic = new ArrayList<>(Arrays.asList(R.drawable.soup_frenchonion,R.drawable.soup_tomato,R.drawable.soup_newenglandclamchouder));
    List<String> soup_names = new ArrayList<>(Arrays.asList("French onion Soup","Tomato Soup","New England clam chouder"));
    List<Integer> soup_price = new ArrayList<>(Arrays.asList(100,200,300,400,500));
    List<Integer> soup_type = new ArrayList<>(Arrays.asList(R.drawable.veglogo,R.drawable.veglogo,R.drawable.veglogo));


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_extras, container, false);
        soups_lv = rootview.findViewById(R.id.soups_lv);

        menu_adapter adapter = new menu_adapter(getActivity(),soup_pic,soup_names,soup_price,soup_type,getContext());
        soups_lv.setAdapter(adapter);

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}