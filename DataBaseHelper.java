package com.example.todo_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CLASE_NAME = "CLASE_NAME";
    public static final String COLUMN_PROF_NAME = "PROF_NAME";
    public static final String COLUMN_IS_ACTIVE = "IS_ACTIVE";



    public DataBaseHelper(@Nullable Context context) {
        super(context, "Customer.db", null, 1);
    }

    //called the 1st time the database is accessed or created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateCustomerTable = "CREATE TABLE "+ CUSTOMER_TABLE + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLASE_NAME + " TEXT, "+ COLUMN_PROF_NAME +" TEXT, "+ COLUMN_IS_ACTIVE + " BOOL) ";
        db.execSQL(CreateCustomerTable);

    }

    //callled when the version is changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // create a mthd to adds customer to table
    public boolean addOne(Model model){
        SQLiteDatabase db = this.getReadableDatabase(); //to insert date in table
        ContentValues contentValues = new ContentValues(); //to store data in pairs values

                                        // it similar to putExtras(name,content)
        contentValues.put(COLUMN_CLASE_NAME,model.getClassName());
        contentValues.put(COLUMN_PROF_NAME,model.getProfName());
        contentValues.put(COLUMN_IS_ACTIVE, model.isActive());


        //check if the insertoin is negative
        long insert =  db.insert(CUSTOMER_TABLE,null,contentValues);
        if(insert == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean deleteItem(Model model){
        //find model in the database and if found then return true or false otherwise
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ CUSTOMER_TABLE +  " WHERE "+ COLUMN_ID +" = "+ model.getClassId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()) {
            return true;
        }else {
            return false;
        }


    }
    public List<Model> getTable(){

        List<Model> returnList = new ArrayList<>();

        //get data from the dataabes
        String queryString = "SELECT * FROM "+ CUSTOMER_TABLE;

        //READ
        SQLiteDatabase db = this.getReadableDatabase();

        //result set
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){

            //loop thru resultset and create a new customer object and then put them in
            //return list
            do{
                //get the id
                int _id = cursor.getInt(0);
                String className = cursor.getString(1);
                String profName = cursor.getString(2);
                boolean _isActive = cursor.getInt(3) == 1? true:false;

                //instantiate the Model class object and add the list to it
                Model model = new Model(_id, className,profName, _isActive);
                returnList.add(model);

            }while (cursor.moveToNext());

        }else {

            //leave empty in case of faillure
        }
      //  cursor.close();
      //   db.close();
        return  returnList;
    }
}
