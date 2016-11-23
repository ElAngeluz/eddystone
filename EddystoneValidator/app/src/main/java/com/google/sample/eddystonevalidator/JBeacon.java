package com.google.sample.eddystonevalidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * almacena los beacons en el
 * Created by angel on 11/12/2016.
 */

public class JBeacon {
    private String Address;
    private int rssi;


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }


    public String toString(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("mac", getAddress().toString());
            jsonObject.put("rssi",getRssi());
            return jsonObject.toString();
        }catch (JSONException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
