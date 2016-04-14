package eu.alfred.batterywarnerapp.actions;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.api.speech.Cade;
import eu.alfred.batterywarnerapp.MainActivity;

/**
 * Created by Gary on 02.02.2016.
 */
public class BatteryStatusQuery implements ICadeCommand {

    private MainActivity main;
    private Cade cade;

    public BatteryStatusQuery(MainActivity main, Cade cade) {
        this.main = main;
        this.cade = cade;
    }

    @Override
    public void performAction(String commands, Map<String, String> map) {

    }

    @Override
    public void performWhQuery(String s, Map<String, String> map) {
        Intent batteryIntent = main.registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent != null ? batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : 0;
        int scale = batteryIntent != null ? batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : 0;

        String battery_status;
        // Error checking that probably isn't needed but I added just in case.
        if(level <= 0 || scale <= 0) {
            battery_status = String.valueOf(50.0f);
        }
        battery_status = String.valueOf(((float) level / (float) scale) * 100.0f);
        
        List<Map<String, String>> result = new ArrayList<>();
        HashMap tempMap = new HashMap<>();
        tempMap.put("grammar_entry", battery_status);
        result.add(tempMap);
        Log.i("grammar_entry", battery_status);
        cade.sendWHQueryResult(result);
        main.finish();
    }

    @Override
    public void performValidity(String s, Map<String, String> map) {

    }

    @Override
    public void performEntityRecognizer(String s, Map<String, String> map) {

    }
}



