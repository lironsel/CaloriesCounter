package iamhere.ciapps.net.caloriescounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import data.Constants;
import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodName, foodCals;
    private Button submitButton;
    private DatabaseHandler dba;

    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);
        foodCals= (EditText) findViewById( R.id.caloriesEditText);
        foodName = (EditText) findViewById(R.id.editText);
        submitButton = (Button) findViewById(R.id.submitButton);
        clearButton=(Button) findViewById(R.id.clearID);
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                saveDataToDB();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
               dba.deleteAll();
            }
        });
    }

    private void saveDataToDB(){
        Food food = new Food();
        String name = foodName.getText().toString().trim();
        Log.v("name in saving****", name);
        String calsString = foodCals.getText().toString().trim();
        int cals=0;
        if (!calsString.equals(""))
             cals = Integer.parseInt(calsString);
        if (name.equals("") || calsString.equals("")){
            Toast.makeText(getApplicationContext(), "no empty fields allowd", Toast.LENGTH_LONG).show();
        }
        else{
            food.setFoodName(name);
            food.setCalories(cals);
            Date d= new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            String formattedDate = sdf.format(d);

            food.setRecordDate(formattedDate);
            Log.v("write to db", "read from DB "+d);
            dba.addFood(food);
            dba.close();

            foodName.setText("");
            foodCals.setText("");

            startActivity(new Intent(MainActivity.this, DisplayActivity.class));
        }
    }

}
