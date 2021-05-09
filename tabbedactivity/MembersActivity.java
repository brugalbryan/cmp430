package com.example.tabbedactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MembersActivity extends AppCompatActivity {

    private ImageView imageViewM;
    private int[] imagesM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        imageViewM = findViewById(R.id.mathias_image);
        imagesM = new int[]{R.drawable.mathias1,R.drawable.mathias2, R.drawable.mathias3};

        startAsyncTask();
    }

    public void openApp(View view) {
        switch (view.getId()) {

            case R.id.mathias_image:
                Intent intent = new Intent(this, LehmanCollege.class);
                startActivity(intent);
                break;
            case R.id.bryan_image:

                break;
            case R.id.aboulaye_image:

                break;

            case R.id.angel_image:

                break;

            default:
                // Do nothing
        }

    }
    public void startAsyncTask() {
        ExampleAsyncTask task = new  ExampleAsyncTask(this);
        task.execute(10);
    }

    private static class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<MembersActivity> activityWeakReference;

        ExampleAsyncTask(MembersActivity activity){
            activityWeakReference = new WeakReference<MembersActivity>(activity);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MembersActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }

            activity.imageViewM.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Integer... integers) {
            for(int i = 0; i < integers[0]; i--){
                publishProgress((100 +i)%3);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Finished";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            MembersActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            //activity.progressBar.setProgress(values[0]);
            activity.imageViewM.setImageDrawable(ContextCompat.getDrawable(activity.getApplicationContext(),activity.imagesM[values[0]]));

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MembersActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }

            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.imageViewM.setImageDrawable(ContextCompat.getDrawable(activity.getApplicationContext(),activity.imagesM[0]));
            activity.imageViewM.setVisibility(View.VISIBLE);
        }

    }
}