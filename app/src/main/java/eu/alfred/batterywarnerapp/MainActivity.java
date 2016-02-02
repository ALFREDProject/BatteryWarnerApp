package eu.alfred.batterywarnerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

import eu.alfred.api.PersonalAssistant;
import eu.alfred.api.PersonalAssistantConnection;
import eu.alfred.api.proxies.interfaces.ICadeCommand;
import eu.alfred.api.speech.Cade;
import eu.alfred.batterywarnerapp.actions.BatteryStatusAction;


public class MainActivity extends ActionBarActivity implements ICadeCommand {

    private PersonalAssistant personalAssistant;
    private Cade cade;

    private final String BATTERY_STATUS_ACTION = "BatteryStatusAction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(eu.alfred.batterywarnerapp.R.layout.activity_main);

        personalAssistant = new PersonalAssistant(getApplicationContext());

        personalAssistant.setOnPersonalAssistantConnectionListener(new PersonalAssistantConnection() {
            @Override
            public void OnConnected() {
                cade = new Cade(personalAssistant.getMessenger());
                onNewIntent(getIntent());
            }

            @Override
            public void OnDisconnected() {
                // Do some cleanup stuff
            }
        });

        personalAssistant.Init();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String command = intent.getStringExtra("command");
        HashMap args = (HashMap) intent.getSerializableExtra("args");
        if(command!=null) {
            performAction(command, args);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(eu.alfred.batterywarnerapp.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == eu.alfred.batterywarnerapp.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void performAction(String command, Map<String, String> map) {

        switch(command) {
            case(BATTERY_STATUS_ACTION):
                BatteryStatusAction bsa = new BatteryStatusAction(this, cade);
                bsa.performAction(command,map);
                break;
            default:
                break;
        }
    }
}
