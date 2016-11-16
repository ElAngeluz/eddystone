package com.google.sample.eddystonevalidator;

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

/**
 * Created by angel on 11/9/2016.
 */


public class HttpHandler {
    public String request(String _urlString, String _Data) {
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        StringBuffer chaine = new StringBuffer("");
        try{
            URL url = new URL(_urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");


            connection.setChunkedStreamingMode(0);

            connection.setRequestProperty("User-Agent", "");

            connection.connect();


            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

            wr.writeBytes(_Data);
            wr.flush();
            wr.close();

            /*
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            //OutputStream out = connection.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(out, "UTF-8"));

            writer.write(_Data);
            writer.flush();
            writer.close();
            out.close();
            */


            connection.setDoInput(true);


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
        }
        return chaine.toString();

    }

    private void writeStream(OutputStream out) {
    }

}
