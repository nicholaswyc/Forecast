package com.example.forecast;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.forecast.base.BaseFragment;
import com.example.forecast.entity.Data;
import com.example.forecast.entity.Forecast;
import com.example.forecast.entity.Root;
import com.example.forecast.utils.HttpUtils;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityWeatherFragment extends BaseFragment implements View.OnClickListener {
    TextView cityTv,conditionTv,windTv,tempRangeTv,dateTv,clothIndexTv,carIndexTv,coldIndexTv,sportIndexTv,raysIndexTv,airIndexTv;
    ImageView dayIv;
    LinearLayout futureLayout;
    ScrollView outLayout;
    String city;
    private SharedPreferences pref;
    private int bgNum;

    private List<Forecast> forecast;

    private Data  data;
    private String st;

    public CityWeatherFragment() {

    }
    public CityWeatherFragment(String st) {
        super();
        this.st = st;
    }

    //        换壁纸的函数
    public void exchangeBg(){
        pref = getActivity().getSharedPreferences("bg_pref", MODE_PRIVATE);
        bgNum = pref.getInt("bg", 2);
        switch (bgNum) {
            case 0:
                outLayout.setBackgroundResource(R.mipmap.bg);
                break;
            case 1:
                outLayout.setBackgroundResource(R.mipmap.bg2);
                break;
            case 2:
                outLayout.setBackgroundResource(R.mipmap.bg3);
                break;
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        exchangeBg();

        Bundle bundle = getArguments();
        city = bundle.getString("city");

        parseShowData(city.substring(3,12));

        return view;
    }



    private void parseShowData(String result) {
//        使用gson解析数据

        HttpUtils utils = new HttpUtils();
        utils.setUrl_Code(result);
        Root root = utils.getURlContext();
        forecast = root.getData().getForecast();
        data = root.getData();

//        设置TextView
        dateTv.setText(root.getTime()); // 获取今日的日期
        cityTv.setText(root.getCityInfo().getCity()); // 获取今日的城市
        windTv.setText(forecast.get(0).getFx()+forecast.get(0).getFl()); //风向
        tempRangeTv.setText(forecast.get(0).getHigh()+"/"+forecast.get(0).getLow()); //最低温/最高温
        conditionTv.setText(forecast.get(0).getType()); //天气情况
//        获取未来七天的天气情况，加载到layout当中

        for (int i = 1; i < 8; i++) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView windTv = itemView.findViewById(R.id.item_center_tv_wind);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
//          获取对应的位置的天气情况
            Forecast dailyBean = forecast.get(i);
            idateTv.setText(dailyBean.getDate());
            iconTv.setText(dailyBean.getType());
            itemprangeTv.setText(dailyBean.getLow()+"/"+dailyBean.getHigh());
            windTv.setText(dailyBean.getFx());
        }
    }



    private void initView(View view) {
//        用于初始化控件操作
        cityTv = view.findViewById(R.id.frag_tv_city);
        conditionTv = view.findViewById(R.id.frag_tv_condition);
        windTv = view.findViewById(R.id.frag_tv_wind);
        tempRangeTv = view.findViewById(R.id.frag_tv_temprange);
        dateTv = view.findViewById(R.id.frag_tv_date);
        clothIndexTv = view.findViewById(R.id.frag_index_tv_dress);
        carIndexTv = view.findViewById(R.id.frag_index_tv_washcar);
        coldIndexTv = view.findViewById(R.id.frag_index_tv_cold);
        sportIndexTv = view.findViewById(R.id.frag_index_tv_sport);
        raysIndexTv = view.findViewById(R.id.frag_index_tv_rays);
        airIndexTv = view.findViewById(R.id.frag_index_tv_air);
        dayIv = view.findViewById(R.id.frag_iv_today);
        futureLayout = view.findViewById(R.id.frag_center_layout);
        outLayout = view.findViewById(R.id.out_layout);
//        设置点击事件的监听
        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
        airIndexTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (v.getId()) {
            case R.id.frag_index_tv_dress:
                builder.setTitle("温度");
                String msg = "暂无信息";
                if (data!=null){
                    msg = data.getWendu();
                }
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_washcar:
                builder.setTitle("湿度");
                msg = "暂无信息";
                if (data!=null){
                    msg = data.getShidu();
                }
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_cold:
                builder.setTitle("感冒指数");
                msg = "暂无信息";
                if (data!=null){
                    msg = data.getGanmao();
                }
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_sport:
                builder.setTitle("空气质量");
                msg = "暂无信息";
                if (data!=null){
                    msg = data.getQuality();
                }
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_rays:
                builder.setTitle("PM2.5");
                msg = "暂无信息";
                if (data!=null){
                    msg = String.valueOf(data.getPm25());
                }
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_air:
                builder.setTitle("PM10");
                msg = "暂无信息";
                if (data!=null){
                    msg = String.valueOf(data.getPm10());
                }
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
        }
        builder.create().show();
    }

}