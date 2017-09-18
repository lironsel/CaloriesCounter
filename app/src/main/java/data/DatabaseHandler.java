package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

/**
 * Created by Ofer Dan-On on 9/14/2017.
 */


public class DatabaseHandler extends SQLiteOpenHelper{

    private final ArrayList<Food> foodList = new ArrayList<>();


    public DatabaseHandler (Context context){
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY, " + Constants.FOOD_NAME + " TEXT, " + Constants.FOOD_CALORIES_NANE + " INT, "
                + Constants.DATE_NAME + " LONG);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXiSTS " + Constants.TABLE_NAME);
        onCreate(db);
    }
    //get total items
    public int getTotalItems(){
        int totalItems =0;
        String query = "SELECT * FROM "+ Constants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query,null);
        totalItems=cursor.getCount();
        cursor.close();
        return totalItems;
    }

    public int totalCalories(){
        int cals=0;
        SQLiteDatabase dba = this.getReadableDatabase();
        String query = "SELECT SUM( "+ Constants.FOOD_CALORIES_NANE + " ) "+
                "FROM " + Constants.TABLE_NAME;

        Cursor cursor = dba.rawQuery(query, null);
        if (cursor.moveToFirst()){
            cals= cursor.getInt(0);

        }
        cursor.close();
        dba.close();
        return cals;
    }

//delete item
    public void deleteFood(int id){
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ?",
                new String []{String.valueOf(id)});
        dba.close();
    }

    public void deleteAll(){
        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME,null, null);
        dba.close();
    }

    //addFood
    public void addFood(Food food){
        SQLiteDatabase dba= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put (Constants.FOOD_NAME,food.getFoodName());
        values.put (Constants.FOOD_CALORIES_NANE,food.getCalories());
        values.put (Constants.DATE_NAME,food.getRecordDate());
        dba.insert(Constants.TABLE_NAME, null , values);
        Log.v("added food item", "YES!!!");
        dba.close();

    }

    public ArrayList <Food> getFoods(){
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query (Constants.TABLE_NAME, new String [] {Constants.KEY_ID, Constants.FOOD_NAME,
                Constants.FOOD_CALORIES_NANE, Constants.DATE_NAME},
                null, null, null, null,Constants.FOOD_CALORIES_NANE+ " DESC");
        if (cursor.moveToFirst()){
            do{
                Food food = new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                Log.v("fg", "name when reading   "+ cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CALORIES_NANE)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
//                DateFormat dateFormat = DateFormat.getDateInstance();
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);


                try {
                    Date parsedDate = format.parse(cursor.getString(cursor.getColumnIndex(Constants.DATE_NAME)));
                    food.setRecordDate(format.format(parsedDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                String d= cursor.getString(cursor.getColumnIndex(Constants.DATE_NAME));
//                Log.v("log", "date is--------------  "+  d);
                foodList.add(food);
            }while(cursor.moveToNext());
        }
        cursor.close();
        dba.close();
        return foodList;
    }
}
