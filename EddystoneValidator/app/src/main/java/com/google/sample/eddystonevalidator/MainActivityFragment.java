// Copyright 2015 Google Inc. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sample.eddystonevalidator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Main UI and logic for scanning and validation of results.
 */
public class MainActivityFragment extends Fragment{

  private static final String TAG = "EddystoneValidator";
  private static final int REQUEST_ENABLE_BLUETOOTH = 1;

  // An aggressive scan for nearby devices that reports immediately.
  private static final ScanSettings SCAN_SETTINGS =
      new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(0).build();

  // The Eddystone Service UUID, 0xFEAA.
  private static final ParcelUuid EDDYSTONE_SERVICE_UUID =
    ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB");

  private BluetoothLeScanner scanner;
  private BeaconArrayAdapter arrayAdapter;

  private List<ScanFilter> scanFilters;
  private ScanCallback scanCallback;

  private String _User;
  private String _Server;
  private String _Group;

    HttpHandler cliente;
    private Switch swTracking;
    private pl.droidsonroids.gif.GifTextView gif;

    private long time_Send;
   final private long interval = 2050;
    final private long wait = 950;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cliente = new HttpHandler(); // para enviar los datos al servidor

        time_Send = System.currentTimeMillis();

        init();


        scanFilters = new ArrayList<>();
        scanFilters.add(new ScanFilter.Builder().setServiceUuid(EDDYSTONE_SERVICE_UUID).build());
        arrayAdapter = new BeaconArrayAdapter(getActivity(), R.layout.beacon_list_item, new ArrayList<Beacon>());
        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, final ScanResult result) {

                ScanRecord scanRecord = result.getScanRecord();
                if (scanRecord == null) {
                    return;
                }
                if (arrayAdapter.ExistBeacon(result.getDevice().getAddress()))
                    arrayAdapter.getItem(result.getDevice().getAddress()).rssi = result.getRssi();
                else
                    arrayAdapter.add(new Beacon(result.getDevice().getAddress(), result.getRssi()));

                Log.v(TAG, result.getDevice().getAddress() + " " + result.getRssi());

                EnviarDatos();
            }

            @Override
            public void onScanFailed(int errorCode) {
                switch (errorCode) {
                    case SCAN_FAILED_ALREADY_STARTED:
                        //logErrorAndShowToast("SCAN_FAILED_ALREADY_STARTED");
                        break;
                    case SCAN_FAILED_APPLICATION_REGISTRATION_FAILED:
                        logErrorAndShowToast("SCAN_FAILED_APPLICATION_REGISTRATION_FAILED");
                        break;
                    case SCAN_FAILED_FEATURE_UNSUPPORTED:
                        logErrorAndShowToast("SCAN_FAILED_FEATURE_UNSUPPORTED");
                        break;
                    case SCAN_FAILED_INTERNAL_ERROR:
                        logErrorAndShowToast("SCAN_FAILED_INTERNAL_ERROR");
                        break;
                    default:
                        logErrorAndShowToast("Scan failed, unknown error code");
                        break;
                }
            }
        };

    }

    public void EnviarDatos(){
        if (swTracking.isChecked()) {
            onResume();
            if ((time_Send + interval) < System.currentTimeMillis()) {
                if (CargarDatos()) {
                    cliente.request(_Server, toJSON());
                }
                try { //deja de escanear por un intervalo de tiempo
                    synchronized (this) {
                        wait(wait);
                    }
                } catch (InterruptedException ex) {
                }

                time_Send = System.currentTimeMillis();
            }
        }else
            scanner.stopScan(scanCallback);
    }

    /**
     * trae los datos que se cargan desde el mainactivity para enviarlos
     * @return true en caso de que tengan valores validos
     */
  public boolean CargarDatos(){

    MainActivity _main = (MainActivity) getActivity();
    _User = _main.getUserName();
    _Server = _main.getServer();
    _Group = _main.getGroup();
      if (_User == null || _Server == null  || _Group ==null)
            return false;
      return true;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_main, container, false);
      swTracking = (Switch) view.findViewById(R.id.TrackButton);
        swTracking.setChecked(false); //inicia apagado
      gif = (pl.droidsonroids.gif.GifTextView) view.findViewById(R.id.gifLoader);
      gif.setVisibility(view.INVISIBLE);
      swTracking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (isChecked){
                  gif.setVisibility(view.VISIBLE);
                  onResume();
              }else{
                  scanner.stopScan(scanCallback);
                  gif.setVisibility(view.INVISIBLE);
              }
          }
      });
    registerForContextMenu(view);
    return view;
  }

  @Override
  public void onPause() {
    super.onPause();/*
    if (scanner != null) {
      scanner.stopScan(scanCallback);
    }*/
  }

  @Override
  public void onResume() {
    super.onResume();
    if (scanner != null) {
      scanner.startScan(scanFilters, SCAN_SETTINGS, scanCallback);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
      if (resultCode == Activity.RESULT_OK) {
        init();
      } else {
        getActivity().finish();
      }
    }
  }

  // Attempts to create the scanner.
  private void init() {
    BluetoothManager manager = (BluetoothManager) getActivity().getApplicationContext()
        .getSystemService(Context.BLUETOOTH_SERVICE); //solicita el bluetooth
    BluetoothAdapter btAdapter = manager.getAdapter();
    if (btAdapter == null) {
      showFinishingAlertDialog("Bluetooth Error", "Bluetooth not detected on device");
    } else if (!btAdapter.isEnabled()) {
      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      this.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
    } else {
      scanner = btAdapter.getBluetoothLeScanner();
    }
  }

  // Pops an AlertDialog that quits the app on OK.
  private void showFinishingAlertDialog(String title, String message) {
    new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            getActivity().finish();
          }
        }).show();
  }

    private void logErrorAndShowToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Log.e(TAG, message);
    }

    /**
     * Funcion que convierte el array encontrado de los Beacons en una la sentencia jSON que recibe el servidor
     * @return estructura Json para el servidor FIND
     */
    private String toJSON(){
        JSONObject jsonObject= new JSONObject();
        ArrayList<JBeacon> arrayJBeacons = new ArrayList<>();

        try {
            jsonObject.put("group", _Group);
            jsonObject.put("username", _User);

            for (int x=0; x<arrayAdapter.getCount();x++){
                JBeacon jb = new JBeacon();
                jb.setAddress(arrayAdapter.getItem(x).deviceAddress);
                jb.setRssi(arrayAdapter.getItem(x).rssi);
                arrayJBeacons.add(jb);
            }

            arrayAdapter.clear();

            jsonObject.put("wifi-fingerprint",new JSONArray(arrayJBeacons.toString()));

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
