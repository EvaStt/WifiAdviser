package com.example.mydisapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WifiDirectActivity extends AppCompatActivity {

    public static final String MY_NAME = "com.example.myfirstapp.MY_NAME";
    public static final String MY_MESSAGE = "com.example.myfirstapp.MY_MESSAGE";
    public static final String MY_WISH = "com.example.myfirstapp.MY_WISH";

    String myOwnName;
    String myOwnMessage;
    String myOwnWish;

    ListView listView;

    WifiManager wifiManager;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    IntentFilter intentFilter;


    private WifiP2pDnsSdServiceRequest serviceRequest;
    List<WifiServiceInfo> serviceList = new ArrayList<WifiServiceInfo>();
    List<String> serviceNameArray = new ArrayList<String>();
    final Map<String, String> deviceToUUINFO = new HashMap<String, String>();
    final Map<String, String> deviceToUsername = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_direct);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Check if WiFi is already enabled
        if (wifiManager.isWifiEnabled()) {

        } else {
            wifiManager.setWifiEnabled(true);
        }

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        myOwnName = intent.getStringExtra(MainActivity.EXTRA_NAME);
        myOwnMessage = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        myOwnWish = intent.getStringExtra(MainActivity.EXTRA_WISH);

        setupUI(myOwnName, myOwnMessage);
        startRegistration(myOwnName, myOwnMessage);
    }

    private void setupUI(String name, String message) {
        TextView textView = findViewById(R.id.textView);
        textView.setText("Hi," + name + "!");

        listView = (ListView) findViewById(R.id.ListView);


    }

    private void startRegistration(String name, String message) {
        final Map<String, String> record = new HashMap<String, String>();
        record.put("available", "visible");
        record.put("msg", message);
        record.put("name", name);

        WifiP2pDnsSdServiceInfo service = WifiP2pDnsSdServiceInfo.newInstance(
                "_test", "_presence._tcp", record);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

        manager.addLocalService(channel, service, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Successfully added " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int error) {
                Toast.makeText(getApplicationContext(), "Failed to add service", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startDiscovery(View view) {
        Intent intent = new Intent(this, AdviceDiscoveryActivity.class);

        intent.putExtra(MY_NAME, myOwnName);
        intent.putExtra(MY_MESSAGE, myOwnMessage);
        intent.putExtra(MY_WISH, myOwnWish);

        startActivity(intent);
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
    }
}