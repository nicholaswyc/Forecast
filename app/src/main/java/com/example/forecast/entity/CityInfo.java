package com.example.forecast.entity;

public class CityInfo
{
    private String city;

    private String citykey;

    private String parent;

    private String updateTime;

    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setCitykey(String citykey){
        this.citykey = citykey;
    }
    public String getCitykey(){
        return this.citykey;
    }
    public void setParent(String parent){
        this.parent = parent;
    }
    public String getParent(){
        return this.parent;
    }
    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
    }
    public String getUpdateTime(){
        return this.updateTime;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "city='" + city + '\'' +
                ", citykey='" + citykey + '\'' +
                ", parent='" + parent + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}