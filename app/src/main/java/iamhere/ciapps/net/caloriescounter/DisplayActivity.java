package iamhere.ciapps.net.caloriescounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CostumeListViewAdapter;
import data.DatabaseHandler;
import model.Food;
import util.Utils;

public class DisplayActivity extends AppCompatActivity {
    private DatabaseHandler dba;
    private ArrayList<Food> foodList=new ArrayList<>();
    private ListView listView;
    private CostumeListViewAdapter foodAdapter;
    private Food myFood;
    private TextView totalCals, totalFood;

    public DisplayActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        listView= (ListView)findViewById(R.id.list);
        totalCals = (TextView) findViewById(R.id.totalAmountTextView);
        totalFood = (TextView) findViewById(R.id.totalItemsTextView);
        refreshData();

    }

    private void refreshData() {
        foodList.clear();
        dba= new DatabaseHandler (getApplicationContext());
        ArrayList<Food> foodFromDB= dba.getFoods();
        int calsValue = dba.totalCalories();
        int totalItems= dba.getTotalItems();
//        foodList=foodFromDB;
        String formattedVal= Utils.formatNumber(calsValue);
        String formattedItems= Utils.formatNumber(totalItems);

        totalCals.setText("Total Calories: " + formattedVal);
        totalFood.setText("Total Foods: " + formattedItems);

        for (int i= 0; i<foodFromDB.size(); i++){
            String name = foodFromDB.get(i).getFoodName();
            String dateText = foodFromDB.get(i).getRecordDate();
            int cals = foodFromDB.get(i).getCalories();
            int foodId=foodFromDB.get(i).getFoodId();

            Log.v("food ids: ", String.valueOf(foodId));
            myFood=new Food();
            myFood.setFoodName(name);
            myFood.setCalories(cals);
            myFood.setRecordDate(dateText);
            myFood.setFoodId(foodId);

            foodList.add(myFood);
        }
        dba.close();

        foodAdapter= new CostumeListViewAdapter(DisplayActivity.this, R.layout.list_row,foodList);
//        foodAdapter= new CostumeListViewAdapter(DisplayActivity.this, R.layout.list_row);
//        foodAdapter.addAll(foodList);
        listView.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

    }
}
