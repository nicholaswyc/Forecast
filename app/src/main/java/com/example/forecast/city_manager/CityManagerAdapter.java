package com.example.forecast.city_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.forecast.R;

import com.example.forecast.entity.Forecast;
import com.example.forecast.entity.Root;
import com.example.forecast.utils.HttpUtils;


import java.util.List;

public class CityManagerAdapter extends BaseAdapter {
    Context context;
    List<String> mDatas;

    public CityManagerAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String bean = mDatas.get(position);

        holder.cityTv.setText(bean.substring(0,2));

        HttpUtils utils = new HttpUtils();
        utils.setUrl_Code(bean.substring(3,12));
        Root root = utils.getURlContext();

//        获取今日天气情况
        Forecast forecast = root.getData().getForecast().get(0);
        holder.temp.setText(root.getData().getWendu()+"℃");
        holder.conTv.setText("天气:"+ forecast.getType());
        holder.windTv.setText(forecast.getFx()+forecast.getFl());
        holder.tempRangeTv.setText(forecast.getLow()+"/"+forecast.getHigh());
        return convertView;
    }

    class ViewHolder{
        TextView cityTv,conTv,windTv,tempRangeTv,temp;
        public ViewHolder(View itemView){
            cityTv = itemView.findViewById(R.id.item_city_tv_city);
            conTv = itemView.findViewById(R.id.item_city_tv_condition);
            windTv = itemView.findViewById(R.id.item_city_wind);
            tempRangeTv = itemView.findViewById(R.id.item_city_temprange);
            temp = itemView.findViewById(R.id.item_city_tv_temp);

        }
    }
}
