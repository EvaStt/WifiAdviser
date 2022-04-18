package com.example.mydisapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdviceDiscoveryActivity extends AppCompatActivity {
    ListView listView;

    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    IntentFilter intentFilter;


    private WifiP2pDnsSdServiceRequest serviceRequest;
    List<WifiServiceInfo> serviceList = new ArrayList<WifiServiceInfo>();
    List<String> serviceNameArray = new ArrayList<String>();
    final Map<String, String> msgMap = new HashMap<String, String>();
    final Map<String, String> usernameMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice_discovery);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String name = intent.getStringExtra(WifiDirectActivity.MY_NAME);
        String message = intent.getStringExtra(WifiDirectActivity.MY_MESSAGE);
        String wishMsg = intent.getStringExtra(WifiDirectActivity.MY_WISH);

        setupUI(name, message, wishMsg);
        setupWifiDirect();
        discoverService(name, message, wishMsg);
    }

    private void setupUI(String name, String message, String wish) {
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText("You login as " + name + "\n\nYour broadcasting message: "
                + message + "\n\n Wish for a matching: " + wish + "\n\n\n\n\nLOCAL OFFER LIST\n");

        listView = (ListView) findViewById(R.id.ListView);
    }

    private void setupWifiDirect() {
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    private void discoverService(String name, String message, String wishMsg) {
        serviceList.clear();
        serviceNameArray.clear();

//        String arrayStartString = name + "(" + message + ")";
//        serviceNameArray.add(arrayStartString);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, serviceNameArray);
//        listView.setAdapter(adapter);

//        Boolean wasMatch = message.contains(wishMsg);

        WifiP2pManager.DnsSdServiceResponseListener dnsSdServiceResponseListener = (instanceName, registrationType, serviceInfo) -> {
            String deviceAddress = serviceInfo.deviceAddress;
            if (msgMap.containsKey(deviceAddress)) {
                WifiServiceInfo service = new WifiServiceInfo();
                service.setDeviceName(service.deviceName);
                service.setDeviceAddress(service.deviceAddress);
                service.instanceName = instanceName;
                service.serviceRegistrationType = registrationType;
                service.msg = msgMap.get(deviceAddress);
                service.username = usernameMap.get(deviceAddress);
                service.wasMatched = service.msgContains(wishMsg);

                serviceList.add(service);

                serviceNameArray.add(service.username+ "(" + service.msg + ") matched: " + service.wasMatched);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, serviceNameArray);
                listView.setAdapter(adapter);
            }
        };

        WifiP2pManager.DnsSdTxtRecordListener dnsSdTxtRecordListener = (fullDomainName, txtRecordMap, serviceInfo) -> {
//            Toast.makeText(getApplicationContext(), "RecordListener began listen \n\n-txtRecordMap\n\n" + txtRecordMap, Toast.LENGTH_SHORT).show();
            msgMap.put(serviceInfo.deviceAddress, txtRecordMap.get("msg"));
            usernameMap.put(serviceInfo.deviceAddress, txtRecordMap.get("name"));
        };

        manager.setDnsSdResponseListeners(channel, dnsSdServiceResponseListener, dnsSdTxtRecordListener);

        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        manager.addServiceRequest(channel, serviceRequest, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {}

            @Override
            public void onFailure(int reason) {}
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        manager.discoverServices(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Service Discovery Searching...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(getApplicationContext(), "Service Discovery Searching failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}