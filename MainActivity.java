package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String SUCCESS ="successful added ";
    public static final String TAG = "MainActivity";

//    References to buttons and other controls
    Button btn_viewAll, btn_Add;
    EditText className, profName;
    ListView recyclerView;
    Switch Switch1;

    ArrayAdapter<Model> tableAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_viewAll = findViewById(R.id.buton_viewAll);
        btn_Add = findViewById(R.id.button_add);
        className = findViewById(R.id.editTextClassName);
        profName = findViewById(R.id.editTextProfName);
        Switch1 = findViewById(R.id.switch1);
        recyclerView = findViewById(R.id.recycle_View);

      dataBaseHelper =   new DataBaseHelper(MainActivity.this);


       // ShowItemsOnLIstView(dataBaseHelper);

        //set listeneer for btn_add
        btn_Add.setOnClickListener(new View.OnClickListener() {

            Model model;
            @Override
            public void onClick(View v) {
                if(className == null && profName == null) {

                    Toast.makeText(MainActivity.this, "You have to enter classe name and prof name", Toast.LENGTH_LONG).show();
                   model = null;

                    Log.d(TAG, "enside if statement");
                }else {

                    try {
                        model = new Model(-1, className.getText().toString(),
                                profName.getText().toString(), Switch1.isChecked());
                        // Toast.makeText(MainActivity.this,model.toString(),Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        model = new Model(-1, "Error ", "Occured", false);
                    }
                }

                //genereate the dabase we created making reference to new Customer database
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                // add the new customer record into the database
                //call methd addOne and pass the classe Model as parameter
                boolean success = dataBaseHelper.addOne(model);
                Toast.makeText(MainActivity.this, SUCCESS + success, Toast.LENGTH_LONG).show();

                ShowItemsOnLIstView(dataBaseHelper);


            }


        });

        //set listener for btn View all
        btn_viewAll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              //  DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                //    List<Model> table = dataBaseHelper.getTable();
                  //  Toast.makeText(MainActivity.this,table.toString(),Toast.LENGTH_LONG).show();

                ShowItemsOnLIstView(dataBaseHelper);

            }

        });


        //create function that will listen to click
        //iterclicklistener give a number of item clicked

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG,"inside the onItemClick");
                Model clickedItem = (Model) parent.getItemAtPosition(position);

                dataBaseHelper.deleteItem(clickedItem);//call delete mthd

                Log.d(TAG,"after deletion");

                //update the listeview
                ShowItemsOnLIstView(dataBaseHelper);

                Log.d(TAG,"end of OnItemClick");

                Toast.makeText(MainActivity.this, "Deleted "+ clickedItem.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void ShowItemsOnLIstView(DataBaseHelper dataBaseHelper2) {
        tableAdapter =
                new ArrayAdapter<Model>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getTable());
        recyclerView.setAdapter(tableAdapter);
    }
}