package com.example.myapplication;

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
import com.google.android.material.snackbar.Snackbar;


class menu_adapter_order extends ArrayAdapter<String> {

    static List<Integer> image , price , type , count;
    static List<String> name;
    foodDataBase db;

    List<food> temp = new ArrayList<>();

    private final Activity context;

    public menu_adapter_order(Activity context, List<Integer> pic , List<String> name , List<Integer> price , List<Integer> type,List<Integer> count) {
        super(context, R.layout.menu_viewer_order, name);
        this.context = context;
        image = pic;
        menu_adapter_order.name = name;
        menu_adapter_order.price = price;
        menu_adapter_order.type = type;
        menu_adapter_order.count = count;
        db = foodDataBase.getDbInstance(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.menu_viewer_order, null, true);

        ImageView food_pic =  row.findViewById(R.id.order_image);
        TextView food_name =  row.findViewById(R.id.order_name);
        TextView food_price = row.findViewById(R.id.order_price) , food_count = row.findViewById(R.id.order_count);
        ImageView food_type = row.findViewById(R.id.order_vnv);
        RatingBar food_rate = row.findViewById(R.id.order_ratee);
        MaterialButton food_incre = row.findViewById(R.id.order_incre) , food_deecre = row.findViewById(R.id.order_decre);

        food_pic.setImageResource(image.get(position));
        food_name.setText(name.get(position));
        food_price.setText(String.valueOf(price.get(position)));
        food_type.setImageResource(type.get(position));
        food_count.setText(String.valueOf(count.get(position)));

        food_incre.setOnClickListener(v -> {
            Integer val  = count.get(position);
            food_count.setText(String.valueOf(++val));
            count.set(position,val);
        });

        View finalRow = row;
        food_deecre.setOnClickListener(v -> {
            int val = count.get(position);
            --val;
            if (val<=0){
                Snackbar.make(finalRow,name.get(position)+" is remooved",Snackbar.LENGTH_SHORT).show();
                image.remove(position);
                price.remove(position);
                type.remove(position);
                count.remove(position);
                name.remove(position);
                notifyDataSetChanged();
            }
            else {
                count.set(position,val);
                food_count.setText(String.valueOf(count.get(position)));
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
