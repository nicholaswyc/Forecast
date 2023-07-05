package com.example.forecast.city_manager;



import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.forecast.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteCityAdapter extends BaseAdapter {
    Context context;
    List<String>mDatas;
    List<String>deleteCitys;

    List<String> colCity;
    List<String> nolcolCity;
    public DeleteCityAdapter(Context context,
                             List<String> mDatas,List<String> deleteCitys,List<String> colCity) {
        this.context = context;
        this.mDatas = mDatas;
        this.deleteCitys = deleteCitys;
        this.colCity = colCity;

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setNolcolCity(List<String> nolcolCity) {
        this.nolcolCity = nolcolCity;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_deletecity,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        SharedPreferences pref = context.getSharedPreferences("CityData",MODE_PRIVATE);
        this.mDatas = new ArrayList<>(Arrays.asList(pref.getString("city", "").split(","))).stream().distinct().filter(element -> element != null && !element.isEmpty())
                .collect(Collectors.toList());
        final String[] city = {mDatas.get(position)};
        ViewHolder finalHolder = holder;

        holder.tv.setText(city[0].substring(0,2));
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(city[0]);
                deleteCitys.add(city[0]); // 临时的删除
                notifyDataSetChanged();  //删除了提示适配器更新
            }
        });
        if (city[0].substring(13,16).equals("已收藏")){
            finalHolder.col.setImageResource(R.mipmap.col1);
        }
        holder.col.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (city[0].substring(13,16).equals("已收藏")){
                    String col_city = city[0].substring(0,12)+":未收藏";
                    nolcolCity.clear();
                    nolcolCity.add(col_city);
                    colCity.clear();
                    StringBuilder t = new StringBuilder(city[0]);
                    t.setCharAt(13,'未');
                    city[0] = t.toString();
                    //finalHolder.col.setBackgroundDrawable();
                    finalHolder.col.setImageResource(R.mipmap.col);
//                    convertView.findViewById()
                    notifyDataSetChanged();  //删除了提示适配器更新
                }else if (city[0].substring(13,16).equals("未收藏")){
                    String col_city = city[0].substring(0,12)+":已收藏";
                    colCity.clear();
                    colCity.add(col_city);
                    nolcolCity.clear();
                    StringBuilder t = new StringBuilder(city[0]);
                    t.setCharAt(13,'已');
                    city[0] = t.toString();
                    //finalHolder.col.setBackgroundDrawable();
                    finalHolder.col.setImageResource(R.mipmap.col1);
                    notifyDataSetChanged();  //删除了提示适配器更新
                }

                for (String item : colCity.stream().distinct().collect(Collectors.toList())) {
                    // 使用 item 进行操作
                    for (String item1 : mDatas) {
                        // 使用 item 进行操作
                        if (!item.substring(0,12).equals(item1.substring(0,12))){
                            colCity.add(item1);
                        }
                    }
                }

                SharedPreferences.Editor editor1
                        = context.getSharedPreferences("CityData",MODE_PRIVATE).edit();

                editor1.putString("city", String.join(",",colCity));
                editor1.apply();


                if (!nolcolCity.isEmpty()) {
                    List<String> newDatas = nolcolCity.stream().distinct().collect(Collectors.toList());
                    for (int i = 0; i < newDatas.size(); i++) {
                        for (int j = 0; j < mDatas.size(); j++) {
                            if (newDatas.get(i).substring(0, 12).equals(mDatas.get(j).substring(0, 12))) {
                                mDatas.remove(mDatas.get(j));
                                mDatas.add(newDatas.get(i));
                            }
                        }
                    }

//                    SharedPreferences.Editor editor2
//                            = context.getSharedPreferences("CityData", MODE_PRIVATE).edit();

                    editor1.putString("city", String.join(",", mDatas));
                    editor1.apply();


                }
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView tv;
        ImageView iv,col;
        public ViewHolder(View itemView){
            tv = itemView.findViewById(R.id.item_delete_tv);
            iv = itemView.findViewById(R.id.item_delete_iv);
            col = itemView.findViewById(R.id.item_col_iv);
        }
    }
}
