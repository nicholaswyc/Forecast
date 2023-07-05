package com.example.forecast.utils;

import android.util.Log;

import com.example.forecast.entity.Root;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpUtils {

    private String url_Code;

    public void setUrl_Code(String url_Code) {
        this.url_Code = url_Code;
    }


    public Root getURlContext(){
        Gson gson = new Gson();
        new Thread(runnable).start();
//        String s = searchRequest("101010100");
        Log.v("gsearch","gsearch url:"+url_Code);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Root shopInfo = gson.fromJson(url_Code, Root.class);
        return shopInfo;
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO: http request.
            String newFeed = "http://t.weather.sojson.com/api/weather/city/" + url_Code;
            StringBuilder response = new StringBuilder();

            Log.v("gsearch", "gsearch url:" + newFeed);
            try {
                URL url = new URL(newFeed);
                HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
                httpconn.setReadTimeout(100);
                httpconn.setConnectTimeout(150);
                httpconn.setRequestMethod("GET");
                httpconn.setDoInput(true);

                httpconn.connect();
                if (httpconn.getResponseCode() == 200) {//HttpURLConnection.HTTP_OK
                    BufferedReader input = new BufferedReader(new InputStreamReader(httpconn.getInputStream()), 8192);
                    String strLine = null;
                    while ((strLine = input.readLine()) != null) {
                        response.append(strLine);
                    }
                    input.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            url_Code = response.toString();
        }
    };
}
