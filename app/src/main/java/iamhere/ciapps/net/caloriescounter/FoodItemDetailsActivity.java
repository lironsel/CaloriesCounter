package iamhere.ciapps.net.caloriescounter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import model.Food;

public class FoodItemDetailsActivity extends AppCompatActivity  {
    private TextView foodName;
    private TextView dateTaken;
    private TextView calories;
    private Button shareButton;
    private int foodID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);
        foodName= (TextView)findViewById(R.id.detsFoodName);
        calories= (TextView) findViewById(R.id.detsCaloriesValue);
        dateTaken=(TextView)findViewById(R.id.detsDateTaken);
        shareButton= (Button) findViewById(R.id.detsShareButton);

        Food food =(Food) getIntent().getSerializableExtra("userObj");
        foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());

        foodID=food.getFoodId();

        calories.setTextSize(34.9f);
        calories.setTextColor(Color.RED );
    }


}
