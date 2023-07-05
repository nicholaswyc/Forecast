package com.example.forecast.city_manager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.forecast.MainActivity;
import com.example.forecast.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CityManagerActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addIv,backIv,deleteIv;

    ListView cityLv;
    List<String> mDatas;  //显示列表数据源
    private CityManagerAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        addIv = findViewById(R.id.city_iv_add);
        backIv = findViewById(R.id.city_iv_back);
        deleteIv = findViewById(R.id.city_iv_delete);
        cityLv = findViewById(R.id.city_lv);
        mDatas = new ArrayList<>();
//        添加点击监听事件
        addIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
//        设置适配器
        adapter = new CityManagerAdapter(this, mDatas);
        cityLv.setAdapter(adapter);
        listView = (ListView)findViewById(R.id.city_lv);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                SharedPreferences pref = getSharedPreferences("CityData",MODE_PRIVATE);
//                List<String> list =
//                        new ArrayList<>(Arrays.asList(pref.getString("city", "").split(","))).stream().distinct().filter(element -> element != null && !element.isEmpty())
//                                .collect(Collectors.toList());
//                SharedPreferences.Editor editor
//                        = getSharedPreferences("Data",MODE_PRIVATE).edit();
//                editor.putString("city",list.get(i).substring(0,2) );
//                Intent ii = new Intent(CityManagerActivity.this, MainActivity.class);
//                ii.putExtra("city_name",list.get(i).substring(0,2));
//                startActivity(ii);

                finish();

            }
        });
    }
/*  获取数据库当中真实数据源，添加到原有数据源当中，提示适配器更新*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("CityData",MODE_PRIVATE);
        List<String> list =
                new ArrayList<>(Arrays.asList(pref.getString("city", "").split(","))).stream().distinct().filter(element -> element != null && !element.isEmpty())
                        .collect(Collectors.toList());
        mDatas.clear(); //先清空
        mDatas.addAll(list);  //再添加
        adapter.notifyDataSetChanged(); //提示更新
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city_iv_add:
                SharedPreferences pref = getSharedPreferences("CityData",MODE_PRIVATE);
                List<String> list =
                        new ArrayList<>(Arrays.asList(pref.getString("city", "").split(","))).stream().distinct().collect(Collectors.toList());
                 list.remove(0);
                if (list.size()<5) {
                    Intent intent = new Intent(this, SearchCityActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"存储城市数量已达上限，请删除后再增加",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.city_iv_back:
                finish();
                break;
            case R.id.city_iv_delete:
                Intent intent1 = new Intent(this, DeleteCityActivity.class);
                startActivity(intent1);
                break;
        }
    }
}