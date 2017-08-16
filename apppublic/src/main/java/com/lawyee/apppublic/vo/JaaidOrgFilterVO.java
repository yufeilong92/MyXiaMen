package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 缓存机构下拉选择VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidOrgFilterVO extends BaseVO {

    private static final long serialVersionUID = 1631259653130050545L;
    private List<BaseCommonDataVO> mProvinceDatas;//省
    private List<BaseCommonDataVO> mCityDatas;//市
    private List<BaseCommonDataVO> mAreasDatas;//区县
    //当前选中的省索引
    private int mNowSelProvince;//记录选中省的id
    private int mNowSelCity;//记录选中市的id
    private int mNowSelAreas;//记录选中区县的id
    public JaaidOrgFilterVO()
    {
        mProvinceDatas = new ArrayList<>();
        mCityDatas = new ArrayList<>();
        mAreasDatas = new ArrayList<>();
        mNowSelProvince = -1;
        mNowSelCity = -1;
        mNowSelAreas = -1;
    }
    public List<BaseCommonDataVO> getProvinceDatas() {
        return mProvinceDatas;
    }

    public void setProvinceDatas(List<BaseCommonDataVO> mProvinceDatas) {
        this.mProvinceDatas = mProvinceDatas;
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

    public int getNowSelProvince() {
        return mNowSelProvince;
    }

    public void setNowSelProvince(int mNowSelProvince) {
        this.mNowSelProvince = mNowSelProvince;
    }

    public int getNowSelCity() {
        return mNowSelCity;
    }

    public void setNowSelCity(int mNowSelCity) {
        this.mNowSelCity = mNowSelCity;
    }

    public int getNowSelAreas() {
        return mNowSelAreas;
    }

    public void setNowSelAreas(int mNowSelAreas) {
        this.mNowSelAreas = mNowSelAreas;
    }



    public static String dataFileName(Context c,String filename) {
        return dataFileName(c,serialVersionUID,filename);
    }
}
