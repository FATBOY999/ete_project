package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.google.android.material.button.MaterialButton;


class menu_adapter extends ArrayAdapter<String> {

    List<Integer> image , price , type;
    List<String> name;
    foodDataBase db;

    List<food> temp = new ArrayList<>();

    private final Context mContext;

    private final Activity context;

    public menu_adapter(Activity context, List<Integer> pic , List<String> name , List<Integer> price , List<Integer> type , Context con) {
        super(context, R.layout.menu_viewer, name);
        this.context = context;
        this.image = pic;
        this.name = name;
        this.price = price;
        this.type = type;
        db = foodDataBase.getDbInstance(context);
        this.mContext = con;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.menu_viewer, null, true);

        ImageView food_pic =  row.findViewById(R.id.image);
        TextView food_name =  row.findViewById(R.id.name);
        TextView food_price = row.findViewById(R.id.price);
        ImageView food_type = row.findViewById(R.id.vnv);
        RatingBar food_rate = row.findViewById(R.id.ratee);
        MaterialButton food_order = row.findViewById(R.id.adddd);

        food_pic.setImageResource(image.get(position));
        food_name.setText(name.get(position));
        food_price.setText(String.valueOf(price.get(position)));
        food_type.setImageResource(type.get(position));

        food_order.setOnClickListener(v -> {
            if (mContext instanceof home){
                ((home) mContext).check(name.get(position),image.get(position),price.get(position),type.get(position));
            }
        });

        temp = db.itemDao().checkpresent(name.get(position));
        if (temp.size() == 0){
            food f = new food();
            f.name = name.get(position);
            f.totalcounted = 0;
            f.totalratings = 0;
            db.itemDao().insert(f);
            food_rate.setRating(0);
        }
        else {
            int total_ratings = db.itemDao().get_totalratings(name.get(position));
            if (total_ratings == 0)
                food_rate.setRating(0);
            else
                food_rate.setRating((float) (db.itemDao().get_totalcounted(name.get(position))/total_ratings));
        }

        return row;
    }

}