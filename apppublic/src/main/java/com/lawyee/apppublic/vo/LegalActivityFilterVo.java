package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.vo
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/7/10 15:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LegalActivityFilterVo extends BaseVO {
    private static final long serialVersionUID = -945938542740410274L;
    private List<String> mActivityTypes;//活动类型
    private List<BaseCommonDataVO> mCityDatas;
    private List<BaseCommonDataVO> mAreasDatas;
    private List<BaseCommonDataVO> mActivitiesTypes;


    private int mNowSelAreas;//记录选中的id
    private int mNowSelActivityType;//活动类型
    private int mNowSelCity;//记录选中市的id
    private int mNowActivitiesTypes;//记录选中市的id

    public LegalActivityFilterVo() {
        mActivityTypes = new ArrayList<>();
        mCityDatas = new ArrayList<>();
        mAreasDatas = new ArrayList<>();
        mActivitiesTypes=new ArrayList<>();

        mNowSelActivityType = -1;
        mNowSelCity = -1;
        mNowSelAreas = -1;
        mNowActivitiesTypes=-1;
    }

    public int getNowActivitiesTypes() {
        return mNowActivitiesTypes;
    }

    public void setmNowActivitiesTypes(int mNowActivitiesTypes) {
        this.mNowActivitiesTypes = mNowActivitiesTypes;
    }

    public List<BaseCommonDataVO> getActivitiesTypes() {
        return mActivitiesTypes;
    }

    public void setmActivitiesTypes(List<BaseCommonDataVO> mActivitiesTypes) {
        this.mActivitiesTypes = mActivitiesTypes;
    }

    public static String dataFileName(Context c, String filename) {
        return dataFileName(c,serialVersionUID,filename);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getActivityTypes() {
        return mActivityTypes;
    }

    public void setActivityTypes(List<String> mActivityTypes) {
        this.mActivityTypes = mActivityTypes;
    }

    public List<BaseCommonDataVO> getCityDatas() {
        return mCityDatas;
    }

    public void setCityDatas(List<BaseCommonDataVO> mCityDatas) {
        this.mCityDatas = mCityDatas;
    }

    public List<BaseCommonDataVO> getAreasDatas() {
        return mAreasDatas;
    }

    public void setAreasDatas(List<BaseCommonDataVO> mAreasDatas) {
        this.mAreasDatas = mAreasDatas;
    }

    public int getNowSelAreas() {
        return mNowSelAreas;
    }

    public void setNowSelAreas(int mNowSelAreas) {
        this.mNowSelAreas = mNowSelAreas;
    }

    public int getNowSelActivityType() {
        return mNowSelActivityType;
    }

    public void setNowSelActivityType(int mNowSelActivityType) {
        this.mNowSelActivityType = mNowSelActivityType;
    }

    public int getNowSelCity() {
        return mNowSelCity;
    }

    public void setNowSelCity(int mNowSelity) {
        this.mNowSelCity = mNowSelity;
    }
}
