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

public class starter extends Fragment {

    List<Integer> starter_pic = new ArrayList<>(Arrays.asList(R.drawable.s1_dahi_vada,R.drawable.s2_paneer_tikka,R.drawable.s3_sweet_potatotikki,
    R.drawable.s4_paneer_tikka,R.drawable.s5_spring_rolls));

    List<String> starter_names = new ArrayList<>(Arrays.asList("Dahi Vada","Paneer Tikka","Sweet Patato tikki","Paneer Tikka","Spring Rolls"));

    List<Integer> starter_price = new ArrayList<>(Arrays.asList(100,200,300,400,500));

    List<Integer> starter_type = new ArrayList<>(Arrays.asList(R.drawable.veglogo,R.drawable.veglogo,R.drawable.veglogo,R.drawable.veglogo,R.drawable.veglogo));

    ListView starter_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_starter, container, false);
        starter_list = rootView.findViewById(R.id.starter_lv);

        menu_adapter adapter = new menu_adapter(getActivity(),starter_pic,starter_names,starter_price,starter_type,getContext());
        starter_list.setAdapter(adapter);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}