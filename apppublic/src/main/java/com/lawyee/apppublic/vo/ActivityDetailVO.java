package com.lawyee.apppublic.vo;

import android.content.Context;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 法宣活动详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/2
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ActivityDetailVO extends ActivityVO {
    private static final long serialVersionUID = -9192644501568515351L;

    /**
     * 省份
     */
    private String province;// 省份
    /**
     * 市
     */
    private String city;// 市区
    /**
     * 区县
     */
    private String county;// 区域
    /**
     * 具体地址
     */
    private String address;


    /**
     * 活动开始时间,yyyy-MM-dd HH:mm:ss
     */
    private String activityTimeStart;
    /**
     *活动结束时间,yyyy-MM-dd HH:mm:ss
     */
    private String activityTimeEnd;
    /**
     *承办方
     */
    private List<JapubHandleVO> undertakehandle;
    /**
     *协办方
     */
    private List<JapubHandleVO> cohandle;
    /**
     * 活动详情
     */
    private String introduce;
    /**
     *活动地图(经纬度)
     */
    private String axis;

    /**
     * 活动照片列表
     */
    private List<AttachmentVO> attachments;

    public String getActivityTimeStart() {
        return activityTimeStart;
    }

    public void setActivityTimeStart(String activityTimeStart) {
        this.activityTimeStart = activityTimeStart;
    }

    public String getActivityTimeEnd() {
        return activityTimeEnd;
    }

    public void setActivityTimeEnd(String activityTimeEnd) {
        this.activityTimeEnd = activityTimeEnd;
    }

    public List<JapubHandleVO> getUndertakehandle() {
        return undertakehandle;
    }

    public void setUndertakehandle(List<JapubHandleVO> undertakehandle) {
        this.undertakehandle = undertakehandle;
    }

    public List<JapubHandleVO> getCohandle() {
        return cohandle;
    }

    public void setCohandle(List<JapubHandleVO> cohandle) {
        this.cohandle = cohandle;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAxis() {
        return axis;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }

    public List<AttachmentVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentVO> attachments) {
        this.attachments = attachments;
    }
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static String dataListFileName(Context c, String filterName) {
        return dataListFileName(c,serialVersionUID,filterName);
    }
}
