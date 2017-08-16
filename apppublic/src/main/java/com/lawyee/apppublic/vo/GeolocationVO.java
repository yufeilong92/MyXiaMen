package com.lawyee.apppublic.vo;

import android.content.Context;

import com.lawyee.apppublic.util.UrlUtil;

import net.lawyee.mobilelib.utils.JavaLangUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * 经纬度信息
 * Created by wuzhu on 16/3/21.
 */
public class GeolocationVO extends BaseVO {
    /**
     * 地址名称
     */
    private String address;

    /**
     * 静态地图地址
     */
    private String mapAddress;

    //经度
    private double lng;
    //纬度
    private double lat;

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLng(String lng) {
        this.lng = JavaLangUtil.StrToDouble(lng,-180.1d);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLat(String lat) {
        this.lat = JavaLangUtil.StrToDouble(lat,-90.1d);
    }

    /**
     * 最东端 东经135度2分30秒 黑龙江和乌苏里江交汇处
     最西端 东经73度40分 帕米尔高原乌兹别里山口（乌恰县）
     最南端 北纬3度52分 南沙群岛曾母暗沙
     最北端 北纬53度33分 漠河以北黑龙江主航道（漠河县）2日本朝鲜韩国
     * @return
     */
    public boolean isEffect()
    {
        if(lng>136.0f||lng<73.0f)
            return false;

        if(lat>54.0f||lat<4.0f)
            return  false;

        return  true;
    }

    public String getStaticImgUrl(Context c)
    {
        if(!isEffect())
            return "";

        return UrlUtil.getStaticMapImgUrl(c,lng,lat);
    }
}
