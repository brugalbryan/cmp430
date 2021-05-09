package com.example.tabbedactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectedCoursesActivity extends AppCompatActivity {
    private RecyclerView myrecyclerview3;
    private RecyclerViewSelectedCourseAdapter mAdapter;
    private Toolbar toolbarSelected;
    View screenView;
    int[] images;

    public static final int REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_courses);

        toolbarSelected = findViewById(R.id.toolBar_selcted);
        setSupportActionBar(toolbarSelected);
        getSupportActionBar().setTitle("Classes Selected");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        screenView = findViewById(R.id.screen_view);
        images = new int[]{R.drawable.logo,R.drawable.l_night, R.drawable.snow};



        myrecyclerview3 = findViewById(R.id.courses_selected_recyclerview);
        mAdapter = new RecyclerViewSelectedCourseAdapter(getApplicationContext(), LehmanCollege.mCourseSelect);
        myrecyclerview3.setLayoutManager(new LinearLayoutManager(getApplication()));
        myrecyclerview3.setAdapter(mAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.selected_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logo:
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(),images[0]));
                return true;
            case R.id.night:
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(),images[1]));
                return true;

            case R.id.snow:
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(),images[2]));
                return true;
            case R.id.call_advisor:
                callAdvisor();
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    private void callAdvisor()
    {
        String dial = "tel:" + "718-960-7371";
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(dial));
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            //startActivity(intent);

            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                //need to override onRequestPermissionsResult method for when the REQUEST_CALL_PHONE triggers the callback method
            }
            else
            {//have permission to CALL_PHONE so we can start the activity by passing in the intent
                startActivity(intent);
            }
        }else
        {

            displayToast("Cannot place phone call");
        }
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

}