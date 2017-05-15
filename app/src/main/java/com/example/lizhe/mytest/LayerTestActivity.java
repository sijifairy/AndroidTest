package com.example.lizhe.mytest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LayerTestActivity extends AppCompatActivity {

    private View view;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int UUID_MY = 22;

    private List<String> mArrayAdapter = new ArrayList<>();
    private BluetoothAdapter mBluetoothAdapter;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                Toast.makeText(LayerTestActivity.this, device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();

                if (device.getName().equals("Hopeupon")) {
                    mBluetoothAdapter.cancelDiscovery();
//                    device.createInsecureRfcommSocketToServiceRecord("1k2");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_test);

//        view = findViewById(R.id.hello_test);
//
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        view.setTranslationY(event.getRawX() / 1080f * 400);
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP:
//                        view.setLayerType(View.LAYER_TYPE_NONE, null);
//                        break;
//                }
//                return true;
//            }
//        });

        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "not supported", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }

        mBluetoothAdapter.startDiscovery();
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }
}
