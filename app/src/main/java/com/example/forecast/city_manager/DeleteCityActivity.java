package com.example.forecast.city_manager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.forecast.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView errorIv,rightIv;
    ListView deleteLv;
    List<String> mDatas;   //listview的数据源
    List<String>deleteCitys;  //表示存储了删除的城市信息

    List<String> colCity; //存储了收藏城市信息
    List<String> nolcolCity; //存储了未收藏城市信息
    private DeleteCityAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        errorIv = findViewById(R.id.delete_iv_error);
        rightIv = findViewById(R.id.delete_iv_right);
        deleteLv = findViewById(R.id.delete_lv);
        deleteCitys = new ArrayList<>();
        colCity = new ArrayList<>();
        nolcolCity = new ArrayList<>();
//        设置点击监听事件
        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);

        SharedPreferences pref = getSharedPreferences("CityData",MODE_PRIVATE);
        mDatas = new ArrayList<>(Arrays.asList(pref.getString("city", "").split(","))).stream().distinct().filter(element -> element != null && !element.isEmpty())
                .collect(Collectors.toList());
//        适配器的设置
        adapter = new DeleteCityAdapter(this, mDatas, deleteCitys,colCity);
        adapter.setNolcolCity(nolcolCity);
        deleteLv.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_iv_error:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("您确定要舍弃更改么？")
                        .setPositiveButton("舍弃更改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();   //关闭当前的activity
                            }
                        });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:

                for (String item : deleteCitys) {
                    // 使用 item 进行操作
                    for (String item1 : mDatas) {
                        // 使用 item 进行操作
                        if (item.equals(item1)){
                            mDatas.remove(item);
                        }
                    }
                }
                SharedPreferences.Editor editor
                        = getSharedPreferences("CityData",MODE_PRIVATE).edit();

                editor.putString("city", String.join(",",mDatas));
                editor.apply();
//                删除成功返回上一级页面

//                for (String item : colCity.stream().distinct().collect(Collectors.toList())) {
//                    // 使用 item 进行操作
//                    for (String item1 : mDatas) {
//                        // 使用 item 进行操作
//                        if (!item.substring(0,12).equals(item1.substring(0,12))){
//                          colCity.add(item1);
//                        }
//                    }
//                }
//
//                SharedPreferences.Editor editor1
//                        = getSharedPreferences("CityData",MODE_PRIVATE).edit();
//
//                editor1.putString("city", String.join(",",colCity));
//                editor1.apply();
//
////                for (String item : nolcolCity.stream().distinct().collect(Collectors.toList())) {
////                    // 使用 item 进行操作
////                    for (String item1 : mDatas) {
////                        // 使用 item 进行操作
////                        if (item.substring(0,12).equals(item1.substring(0,12))){
////                            mDatas.remove(item1);
////                            mDatas.add(item);
////                        }
////                    }
////                }
//
//                if (!nolcolCity.isEmpty()){
//                    List<String> newDatas = nolcolCity.stream().distinct().collect(Collectors.toList());
//                    for (int i = 0; i < newDatas.size(); i++ ) {
//                        for (int j = 0; j < mDatas.size(); j++) {
//                            if (newDatas.get(i).substring(0, 12).equals(mDatas.get(j).substring(0, 12))) {
//                                mDatas.remove(mDatas.get(j));
//                                mDatas.add(newDatas.get(i));
//                            }
//                        }
//                    }
//
//                    SharedPreferences.Editor editor2
//                            = getSharedPreferences("CityData",MODE_PRIVATE).edit();
//
//                    editor2.putString("city", String.join(",",mDatas));
//                    editor2.apply();
//                }

                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}