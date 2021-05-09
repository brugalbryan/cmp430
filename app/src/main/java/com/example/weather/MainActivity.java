package com.example.weather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    TextView where;
    TextView weatherCondition;
    EditText weatherCast;
    TextView climateDescription;
    ImageView weatherImage;
    FloatingActionButton searchFloating;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        where =findViewById(R.id.place);
        where.setText("=======");
        weatherCondition =findViewById(R.id.temperature);
        weatherCondition.setText("0");
        weatherCast = findViewById(R.id.weatherSearch);
        climateDescription = findViewById(R.id.weatherPrecipitation);
        climateDescription.setText("Please type a location");
        weatherImage = findViewById(R.id.climateImage);
        searchFloating = findViewById(R.id.floatingSearchButton);

        searchFloating.setOnClickListener(v -> {
            InputMethodManager imm=(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(),0);

            String search_converted = String.valueOf(weatherCast.getText());
            apiKey(upperCase(search_converted));
        });

    }

    public static String upperCase(String str){

        String[] word = str.split("\\s");
        StringBuilder upperCase= new StringBuilder();
        for(String w:word){
            String first = w.substring(0,1);
            String aftereffect = w.substring(1);
            upperCase.append(first.toUpperCase()).append(aftereffect).append(" ");
        }
        return upperCase.toString().trim();
    }

    //        HttpURLConnection and Apache Client interfaces are implemented by operating directly on top of java Socket
    private void apiKey(final String States) {

        /*
        Sending and Receiving Network Requests
        First, we must instantiate an OkHttpClient and create a Request object.
         */

        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+States+"&appid=a3d71e67567bb8e3aefd4bfc3eb67a5c&units=metric")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            /*
            Synchronous Network Calls
            We can create a Call object and dispatch the network request synchronously:
             */
            Response response= client.newCall(request).execute();

            /*
            We can also make asynchronous network calls too by creating a Call object, using
            the enqueue() method, and passing an anonymous Callback object that implements both
            onFailure() and onResponse().
             */

            // Get a handler that can be used to post to the main thread
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    // ... check for failure using `isSuccessful` before proceeding
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {

//                        Toast.makeText(new main.Activity(), "Connection failed", Toast.LENGTH_LONG).show();
//                    }
//                }


                        // Read data on the worker thread
                        String responseData = Objects.requireNonNull(response.body()).string();
                        //Processing JSON data
                        try {

                            //Decode the data by converting it to a JSONObject or JSONArray, depending on the response data

                            JSONObject json = new JSONObject(responseData);
                            JSONArray array = json.getJSONArray("weather");
                            JSONObject object = array.getJSONObject(0);
                            String description = object.getString("description");
                            String icons = object.getString("icon");
                            JSONObject temperature = json.getJSONObject("main");
                            double climate = temperature.getDouble("temp");

                            setText(where, States);
                            String fahrenheit = ((int) Math.floor(Math.round(climate) * 1.8 + 32) + "");
                            setText(weatherCondition, fahrenheit);
                            setText(climateDescription, description);
                            setImage(weatherImage, icons);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    /*
    Updating Views on UIThread
    OkHttp normally creates a new worker thread to dispatch the network request and uses the same thread to handle the response.
    It is built primarily as a Java library so it does not handle the Android framework limitations that only permit views to be updated on the main UI thread.

    **For this reason, if you try to access or update views from outside the main thread in the Callback, you will probably receive an exception**
     */

    private void setText(final TextView text, final String value){
        //to update any views from within a response, you will need to use runOnUiThread() or post the result back on the main thread.

        runOnUiThread(() -> text.setText(value));
    }

    @SuppressLint("UseCompatLoadingForDrawables")

    //When the user clicks the button in the code above, It will use runOnUiThread() to update the text view, as shown below.

    private void setImage(final ImageView imageView, final String value){

        //RunOnUiThread is used to perform background operations on a worker thread and then update the result on the main thread.
        runOnUiThread(() -> {
            switch (value){
                case "02d":
                case "02n":
                case "03d":
                case "03n":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.mostly_sunny));
                    break;
                case "04d":
                case "04n":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.mostly_cloudy));
                    break;
                case "09d":
                case "09n":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.slight_chance_rain));
                    break;
                case "10d":
                case "10n":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.forecast_rain));
                    break;
                case "11d":
                case "11n":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.storm_weather));
                    break;
                case "13d":
                case "13n":
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.snowfall));
                    break;
                case "50n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.haze));
                    break;
                default:
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.clear_sun));
            }
        });
    }
}
