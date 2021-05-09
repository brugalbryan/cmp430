package com.example.tabbedactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Broadcast Receiver implementation that delivers a custom Toast
 * message when it receives any of the registered broadcasts.
 */
public class CustomReceiver extends BroadcastReceiver {

    // String constant that defines the custom broadcast Action.
    private static final String I_AM_HOME =
            "com.example.basic.I_AM_HOME";

    /**
     * This callback method gets called when the Broadcast Receiver receives a
     * broadcast that it is registered for.
     *
     * @param context The context in which broadcast receiver is running.
     * @param intent The broadcast is delivered in the form of an intent which
     *               contains the broadcast action.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();


        if (intentAction != null) {
            String toastMessage = "Unknown action";
            if((intentAction == I_AM_HOME))
            {
                int total = Integer.parseInt(intent.getStringExtra("total"));
                if(total <=1){
                    toastMessage = "The total class that you selected is " +" "+ total;
                }
                else{
                    toastMessage = "The total classes that you selected are " +" "+ total;
                }

            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}