package com.google.sample.eddystonevalidator;

import android.app.DownloadManager;
import android.net.http.HttpResponseCache;
import android.os.StrictMode;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.os.StrictMode.*;

/**
 * Created by angel on 11/9/2016.
 */


public class HttpHandler {
    //navigator.app.loadUrl(servername + "login?group=" + groupname, { openExternal:true });
    public String request(String _urlString, String _Data) {
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        StringBuffer chaine = new StringBuffer("");
        HttpURLConnection connection = null;
        try{
            URL url = new URL(_urlString);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            //connection.setRequestProperty("Content-Type", "text/plain");
            //connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            //connection.setRequestProperty("Content-Type","application/json");

            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches (false);
            //connection.setChunkedStreamingMode(0);

            connection.setRequestProperty("User-Agent", "");

            //connection.connect();

            //connection.getOutputStream().write(_Data.getBytes("UTF-8"));

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            //OutputStream out = connection.getOutputStream();

            out.write(_Data.getBytes());
            out.close();


            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = rd.readLine()) != null) {
                chaine.append(line);
            }
        }
        catch (IOException e) {
            // Writing exception to log
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return chaine.toString();

    }
}
