package com.example.tabbedactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class LehmanCollege extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    // Add fragments
    private GeneralCourses generalCourses;
    private AdvancedCmp advancedCmp;

    //Data of element Selected
    static List<Course> mCourseSelect = new ArrayList<>();

    //Badges
    static BadgeDrawable badgeDrawable0;
    static BadgeDrawable badgeDrawable1;

    //MenuItem menuItem;
    static TextView counterItemSelected;
    static TextView cardBadgeTextView;
    static SwitchCompat switchCompat;

    // Broadcasts Receivers
    private CustomReceiver mReceiver = new CustomReceiver();
    // String constant that defines the custom broadcast Action.
    private static final String I_AM_HOME =
            "com.example.basic.I_AM_HOME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lehman_college);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // arrow on the top-left

        // Tabbed Activity
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        generalCourses = new GeneralCourses();
        advancedCmp = new AdvancedCmp();


        // viewPagerAdapter
        viewPagerAdapter.addFragment(generalCourses, "COURSES");
        viewPagerAdapter.addFragment(advancedCmp, "ADVANCED CMP COURSES");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_shop1);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_shop2);

        //Number on the tab

        badgeDrawable0 =  tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable0.setNumber(0);

        badgeDrawable1 =  tabLayout.getTabAt(1).getOrCreateBadge();
        badgeDrawable1.setNumber(0);



        // Broadcasts Receivers

        // Define the IntentFilter.
        IntentFilter filter = new IntentFilter();
        // Register the receiver using the activity context, passing in the
        // IntentFilter.
        this.registerReceiver(mReceiver, filter);

        // Register the receiver to receive custom broadcast.
        LocalBroadcastManager.getInstance(this).registerReceiver
                (mReceiver, new IntentFilter(I_AM_HOME));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_courses, menu);
        MenuItem menuItem1 = menu.findItem(R.id.card_fragment);
        View actionView = menuItem1.getActionView();
        cardBadgeTextView = actionView.findViewById(R.id.card_badge_text_view);

        // Set Text
        int total = badgeDrawable0.getNumber() + badgeDrawable1.getNumber();
        cardBadgeTextView.setText(total+"");

        ////Atcion Listener
        actionView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem1);
            }
        });
        MenuItem menuItem2 = menu.findItem(R.id.switch_id);
        View actionView2 = menuItem2.getActionView();
        switchCompat = actionView2.findViewById(R.id.switch1);
        actionView2.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem2);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.card_fragment:
                //
                sendCustomBroadcast();
                sendIntent();
                return true;
            /*case R.id.switch_id:
                if(item.isChecked()){
                    switchCompat.setChecked(true);
                }

                return true;*/
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendIntent() {
        Intent intent = new Intent(this, SelectedCoursesActivity.class);
        startActivity(intent);
    }

    private void sendCustomBroadcast() {
        Intent customBroadcastIntent = new Intent(I_AM_HOME);
        customBroadcastIntent.putExtra("total", cardBadgeTextView.getText());
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(customBroadcastIntent);
    }

    @Override
    protected void onDestroy() {
        // Unregister the receivers.
        this.unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}