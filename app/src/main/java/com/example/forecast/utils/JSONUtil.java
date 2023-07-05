package com.example.forecast.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.forecast.entity.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JSONUtil {



    private AssetManager assetManager;

    public JSONUtil(Context context) {
        assetManager = context.getAssets();
    }
    //定义文件路径
    // 读取 JSON 文件内容
    String jsonContent = "";


    public List<City> getJson(){

        Gson gson = new Gson();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("city.json")));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent += line;
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(jsonContent.toString(), new TypeToken<List<City>>() {}.getType());
    }
}
