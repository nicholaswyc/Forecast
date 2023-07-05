package com.example.forecast.city_manager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.forecast.MainActivity;
import com.example.forecast.R;
import com.example.forecast.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener {
    EditText searchEt;
    ImageView submitIv;
    GridView searchGv;
    String[] hotCitys = {"北京", "上海", "广州", "深圳", "珠海", "佛山", "南京", "苏州", "厦门", "长沙", "成都", "福州",
            "杭州", "武汉", "青岛", "西安", "太原", "沈阳", "重庆", "天津", "南宁"};
    private ArrayAdapter<String> adapter;

    String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        searchEt = findViewById(R.id.search_et);
        submitIv = findViewById(R.id.search_iv_submit);
        searchGv = findViewById(R.id.search_gv);
        submitIv.setOnClickListener(this);
//        因为这里只有一个TextView，所以设置一个简单的适配器，即ArrayAdapter
        adapter = new ArrayAdapter<>(this, R.layout.item_hotcity, hotCitys);
        searchGv.setAdapter(adapter);
        setListener();


    }

    /* 设置监听事件*/
    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCitys[position];

                SharedPreferences pref = getSharedPreferences("JsonData",MODE_PRIVATE);
                SharedPreferences pref1 = getSharedPreferences("CityData",MODE_PRIVATE);
                String name_code = pref.getString(city,"");
                List<String> list = new ArrayList<>(Arrays.asList(pref1.getString("city", "").split(","))).stream().distinct().filter(element -> element != null && !element.isEmpty())
                        .collect(Collectors.toList());
                if (!name_code.equals("")){
                    SharedPreferences.Editor editor
                            = getSharedPreferences("CityData",MODE_PRIVATE).edit();
                    SharedPreferences.Editor editor1
                            = getSharedPreferences("CityData",MODE_PRIVATE).edit();
                    for(int i = 0; i < list.size(); i++){
                        if (list.get(i).substring(0,2).equals(city)){
                            list.remove(list.get(i));
                            editor.putString("city", String.join(",",list));
                            editor.apply();
                        }
                    }
                    editor1.putString("city", pref1.getString("city","")+","+city+":"+name_code+":未收藏");
                    editor1.apply();
                    onSuccess();
                }else{
                    onError();
                }


            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv_submit:
                city = searchEt.getText().toString();
                if (!TextUtils.isEmpty(city)) {
//                    判断是否能够找到这个城市
                    SharedPreferences pref = getSharedPreferences("JsonData",MODE_PRIVATE);
                    SharedPreferences pref1 = getSharedPreferences("CityData",MODE_PRIVATE);
                    String name_code = pref.getString(city,"");
                    List<String> list = new ArrayList<>(Arrays.asList(pref1.getString("city", "").split(","))).stream().distinct().filter(element -> element != null && !element.isEmpty())
                            .collect(Collectors.toList());
                    if (!name_code.equals("")) {
                        SharedPreferences.Editor editor
                                = getSharedPreferences("CityData", MODE_PRIVATE).edit();
                        SharedPreferences.Editor editor1
                                = getSharedPreferences("CityData", MODE_PRIVATE).edit();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).substring(0, 2).equals(city)) {
                                list.remove(list.get(i));
                                editor.putString("city", String.join(",", list));
                                editor.apply();
                            }
                        }
                        editor1.putString("city", pref1.getString("city", "") + "," + city + ":" + name_code+":未收藏");
                        editor1.apply();
                        onSuccess();
                    }
                } else {
                    Toast.makeText(this, "输入内容不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void onSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onError() {

        Toast.makeText(this, "暂时未收入此城市天气信息...", Toast.LENGTH_SHORT).show();
    }
}