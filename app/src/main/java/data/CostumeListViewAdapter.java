package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import iamhere.ciapps.net.caloriescounter.FoodItemDetailsActivity;
import iamhere.ciapps.net.caloriescounter.R;
import model.Food;

/**
 * Created by Ofer Dan-On on 9/14/2017.
 */


public class CostumeListViewAdapter extends ArrayAdapter<Food>{
    private int layoutResource;
    private Activity activity;
//    private ClickItemInListListener  mListener;
    private ArrayList<Food> foodList = new ArrayList<>();

    public CostumeListViewAdapter(@NonNull Context context, @LayoutRes int resource,  ArrayList<Food> data) {
        super(context, resource, data);
        layoutResource= resource;
        activity= (Activity)context;
        foodList= data;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return foodList.size();

    }

    @Nullable
    @Override
    public Food getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public int getPosition(@Nullable Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row==null || row.getTag() == null){
            LayoutInflater inflater= LayoutInflater.from(activity);
            row= inflater.inflate(layoutResource, null);
            holder = new ViewHolder();
            holder.foodName = (TextView) row.findViewById(R.id.name);
            holder.foodDate = (TextView) row.findViewById(R.id.dateText);
            holder.foodCalories = (TextView) row.findViewById(R.id.calories);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }
        holder.food = getItem(position);
        holder.foodName.setText(holder.food.getFoodName());
        holder.foodDate.setText(holder.food.getRecordDate());
        holder.foodCalories.setText(String.valueOf(holder.food.getCalories()));

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i= new Intent(activity, FoodItemDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("userObj", finalHolder.food);
                i.putExtras(mBundle);
                activity.startActivity(i);
            }
        });

        return row;
    }
    public class ViewHolder{
        Food food;
        TextView foodName;
        TextView foodCalories;
        TextView foodDate;

    }

}
