package eu.alfred.batterywarnerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.batterywarnerapp.actions.BatteryStatusQuery;
import eu.alfred.ui.AppActivity;
import eu.alfred.ui.CircleButton;

public class MainActivity extends AppActivity implements ICadeCommand {

    private final String BATTERY_STATUS_QUERY = "selected_battery_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Change your view contents. Note, the the button has to be included last.
        //setContentView(eu.alfred.batterywarnerapp.R.layout.activity_main);

        circleButton = (CircleButton) findViewById(R.id.voiceControlBtn);
        circleButton.setOnTouchListener(new CircleTouchListener());
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.i("onNewIntent", getPackageName());
        super.onNewIntent(intent);
        setIntent(intent);
        String command = intent.getStringExtra("command");
        HashMap args = (HashMap) intent.getSerializableExtra("args");
        if(command!=null) {
            performAction(command, args);
        }
    }

    @Override
    public void performAction(String command, Map<String, String> map) {
    }

    @Override
    public void performWhQuery(String command, Map<String, String> map) {
        //Add custom events here
        switch (command) {
            case (BATTERY_STATUS_QUERY):
                BatteryStatusQuery bsq = new BatteryStatusQuery(this, cade);
                bsq.performWhQuery(command, map);
                break;
            default:
                break;

        }
    }

    @Override
    public void performValidity(String command, Map<String, String> map) {

    }

    @Override
    public void performEntityRecognizer(String command, Map<String, String> map) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }
}
