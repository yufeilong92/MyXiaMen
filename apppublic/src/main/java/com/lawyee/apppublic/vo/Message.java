package com.lawyee.apppublic.vo;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.vo
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/6/27 16:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class Message implements Serializable {
    /**
     * 消息类型-普通消息
     */
    public final  static  int CINT_MESSAGE_TYPE_NR = 1;
    /**
     * 消息类型-图片消息
     */
    public final  static  int CINT_MESSAGE_TYPE_IMAGE = 2;
    /**
     * 消息类型-位置消息
     */
    public final  static  int CINT_MESSAGE_TYPE_ADDRESS = 3;
    /**
     * 消息类型，对应CINT_MESSAGE_TYPE_*
     */
    private int type ;
    private boolean isSend;
    private double latitude;
    private double longitude;
    private String address;
    private String content;
    private long date;



    private String dateIsString;
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String photo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public void setDate(long time)
    {
        this.date = time;
    }
    public String getDateIsString() {
        return dateIsString;
    }

    public void setDateIsString(String dateIsString) {
        this.dateIsString = dateIsString;
    }
}

