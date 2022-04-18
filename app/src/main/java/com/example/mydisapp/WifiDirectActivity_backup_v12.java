//package com.example.mydisapp;
//
//import android.Manifest;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageManager;
//import android.net.wifi.p2p.WifiP2pManager;
//import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
//import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//public class WifiDirectActivity_backup_v12 extends AppCompatActivity {
//    //public class WifiDirectActivity<WiFiDirectServicesList, WiFiDevicesAdapter>
////        extends AppCompatActivity implements WifiP2pManager.ConnectionInfoListener {
//    ListView listView;
//
//    WifiP2pManager manager;
//    WifiP2pManager.Channel channel;
//    BroadcastReceiver receiver;
//    IntentFilter intentFilter;
//
//
//    private WifiP2pDnsSdServiceRequest serviceRequest;
//    List<WifiServiceInfo> serviceList = new ArrayList<WifiServiceInfo>();
//    List<String> serviceNameArray = new ArrayList<String>();
//    final Map<String, String> deviceToUUINFO = new HashMap<String, String>();
//    final Map<String, String> deviceToUsername = new HashMap<String, String>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wifi_direct);
//
//                // Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();
//        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//        setupUI(name, message);
//        setupWifiDirect();
//        startRegistration(name, message);
//        discoverService();
//    }
//
//    private void setupUI(String name, String message) {
//        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText("Name:" + name + "\n\nMSG:" + message);
//
//        listView = (ListView) findViewById(R.id.ListView);
//
////        Button discoveryButton = findViewById(R.id.discoveryButton);
////        discoveryButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(getApplicationContext(), AdviceDiscoveryActivity.class);
////                startActivity(intent);
////
////            }
////        });
//    }
//
//    private void setupWifiDirect() {
//        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
//        channel = manager.initialize(this, getMainLooper(), null);
//        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
//
//        intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
//        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
//    }
//
//    private void startRegistration(String name, String message) {
//        final Map<String, String> record = new HashMap<String, String>();
//        record.put("available", "visible");
//        record.put("msg", message);
//        record.put("name", name);
////        record.put("myUUINFO", message);
////        record.put("myUsername", name);
//
//        WifiP2pDnsSdServiceInfo service = WifiP2pDnsSdServiceInfo.newInstance(
//                "_test", "_presence._tcp", record);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            manager.addLocalService(channel, service, new WifiP2pManager.ActionListener() {
////                @Override
////                public void onSuccess() {
////                    Toast.makeText(getApplicationContext(), "Successfully added " + message, Toast.LENGTH_SHORT).show();
////                }
////
////                @Override
////                public void onFailure(int error) {
////                    Toast.makeText(getApplicationContext(), "Failed to add service", Toast.LENGTH_SHORT).show();
////                }
////            });
////            return;
//        }
//        manager.addLocalService(channel, service, new WifiP2pManager.ActionListener() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(getApplicationContext(), "Successfully added " + message, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int error) {
//                Toast.makeText(getApplicationContext(), "Failed to add service. err=" + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void discoverService() {
//        serviceList.clear();
//        serviceNameArray.clear();
//        Intent intent = getIntent();
//
//        String arrayStartString = intent.getStringExtra(MainActivity.EXTRA_NAME) + "(" +
//                intent.getStringExtra(MainActivity.EXTRA_MESSAGE) + ")";
//        serviceNameArray.add(arrayStartString);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, serviceNameArray);
//        listView.setAdapter(adapter);
//        Toast.makeText(getApplicationContext(), arrayStartString, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "Discovery began", Toast.LENGTH_SHORT).show();
//
//        WifiP2pManager.DnsSdServiceResponseListener dnsSdServiceResponseListener = (instanceName, registrationType, serviceInfo) -> {
//            // checking onnly whe
//            Toast.makeText(getApplicationContext(), "Listener began listen", Toast.LENGTH_SHORT).show();
//            if (deviceToUUINFO.containsKey(serviceInfo.deviceAddress)) {
//                WifiServiceInfo service = new WifiServiceInfo();
//                service.setDeviceName(service.deviceName);
//                service.setDeviceAddress(service.deviceAddress);
//                service.instanceName = instanceName;
//                service.serviceRegistrationType = registrationType;
//                service.setUuinfo(deviceToUUINFO.get(service.deviceAddress));
//                service.setUsername(deviceToUsername.get(service.deviceAddress));
//                serviceList.add(service);
//                serviceNameArray.add(service.deviceName + "(" + deviceToUUINFO.get(service.deviceAddress) + ")");
//                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, serviceNameArray);
//                listView.setAdapter(adapter2);
//            }
//        };
//
//        WifiP2pManager.DnsSdTxtRecordListener dnsSdTxtRecordListener = (fullDomainName, txtRecordMap, serviceInfo) -> {
//            deviceToUUINFO.put(serviceInfo.deviceAddress, txtRecordMap.get("myUUINFO"));
//            deviceToUsername.put(serviceInfo.deviceAddress, txtRecordMap.get("myUsername"));
//        };
//         /*
//         deviceToUUINFO.put(srcDevice.deviceAddress, txtRecordMap.get("myUUINFO"));
//                }*/
//
//        manager.setDnsSdResponseListeners(channel, dnsSdServiceResponseListener, dnsSdTxtRecordListener);
//
//        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
//        manager.addServiceRequest(channel, serviceRequest, new WifiP2pManager.ActionListener() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onFailure(int reason) {
//
//            }
//        });
//
//        // CHECK PERMISSION ON MY PHONE
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getApplicationContext(), "Checking", Toast.LENGTH_SHORT).show();
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            manager.discoverServices(channel, new WifiP2pManager.ActionListener() {
//                @Override
//                public void onSuccess() {
////                searchStatus.setText("Searching...");
//                    Toast.makeText(getApplicationContext(), "Service Discovery Initiated 2", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(int reason) {
////                searchStatus.setText("Searching failed");
//                    Toast.makeText(getApplicationContext(), "Service Discovery Could not be initiated 2", Toast.LENGTH_SHORT).show();
//                }
//            });
//            return;
//        }
//        Toast.makeText(getApplicationContext(), "After check", Toast.LENGTH_SHORT).show();
//        manager.discoverServices(channel, new WifiP2pManager.ActionListener() {
//            @Override
//            public void onSuccess() {
////                searchStatus.setText("Searching...");
//                Toast.makeText(getApplicationContext(), "Service Discovery Initiated 2", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int reason) {
////                searchStatus.setText("Searching failed");
//                Toast.makeText(getApplicationContext(), "Service Discovery Could not be initiated: err=" + reason, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    /* register the broadcast receiver with the intent values to be matched */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(receiver, intentFilter);
//    }
//
//    /* unregister the broadcast receiver */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(receiver);
//    }
//}