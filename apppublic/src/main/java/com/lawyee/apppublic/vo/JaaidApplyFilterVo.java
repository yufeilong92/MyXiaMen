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
 * @Description: 申请须知vo
 * @author: YFL
 * @date: 2017/6/3 22:45
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaaidApplyFilterVo extends BaseVO {
    private static final long serialVersionUID = -789073779001508085L;
    private List<BaseCommonDataVO> mApplyProvinceDatas;//省
    private List<BaseCommonDataVO> mApplyCityDatas;//市
    private List<BaseCommonDataVO> mApplyAreasDatas;//区县

    private List<BaseCommonDataVO> mApplyNationDatas;//民族
    private List<BaseCommonDataVO> mApplyCertificatersDatas;//证件类型
    //当前选中的省索引
    private int mNowSelApplyProvince;//记录选中省的id
    private int mNowSelApplyCity;//记录选中市的id
    private int mNowSelApplyAreas;//记录选中区县的id
    private int mNowSelApplyNation;//记录选中区县的id
    private int mNowSelApplyCertificaters;//记录选中区县的id
    public JaaidApplyFilterVo()
    {
        mApplyProvinceDatas = new ArrayList<>();
        mApplyCityDatas = new ArrayList<>();
        mApplyAreasDatas = new ArrayList<>();
        mNowSelApplyProvince = -1;
        mNowSelApplyCity = -1;
        mNowSelApplyAreas = -1;
        mNowSelApplyCertificaters=-1;
        mNowSelApplyNation=-1;
    }

    public List<BaseCommonDataVO> getmApplyNationDatas() {
        return mApplyNationDatas;
    }

    public void setmApplyNationDatas(List<BaseCommonDataVO> mApplyNationDatas) {
        this.mApplyNationDatas = mApplyNationDatas;
    }

    public List<BaseCommonDataVO> getmApplyCertificatersDatas() {
        return mApplyCertificatersDatas;
    }

    public void setmApplyCertificatersDatas(List<BaseCommonDataVO> mApplyCertificatersDatas) {
        this.mApplyCertificatersDatas = mApplyCertificatersDatas;
    }

    public int getmNowSelApplyNation() {
        return mNowSelApplyNation;
    }

    public void setmNowSelApplyNation(int mNowSelApplyNation) {
        this.mNowSelApplyNation = mNowSelApplyNation;
    }

    public int getmNowSelApplyCertificaters() {
        return mNowSelApplyCertificaters;
    }

    public void setmNowSelApplyCertificaters(int mNowSelApplyCertificaters) {
        this.mNowSelApplyCertificaters = mNowSelApplyCertificaters;
    }

    public static String dataFileName(Context c, String filename) {
        return dataFileName(c,serialVersionUID,filename);
    }

    public List<BaseCommonDataVO> getApplyProvinceDatas() {
        return mApplyProvinceDatas;
    }

    public void setApplyProvinceDatas(List<BaseCommonDataVO> applyProvinceDatas) {
        mApplyProvinceDatas = applyProvinceDatas;
    }

    public List<BaseCommonDataVO> getApplyCityDatas() {
        return mApplyCityDatas;
    }

    public void setApplyCityDatas(List<BaseCommonDataVO> applyCityDatas) {
        mApplyCityDatas = applyCityDatas;
    }

    public List<BaseCommonDataVO> getApplyAreasDatas() {
        return mApplyAreasDatas;
    }

    public void setApplyAreasDatas(List<BaseCommonDataVO> applyAreasDatas) {
        mApplyAreasDatas = applyAreasDatas;
    }

    public int getNowSelApplyProvince() {
        return mNowSelApplyProvince;
    }

    public void setNowSelApplyProvince(int nowSelApplyProvince) {
        mNowSelApplyProvince = nowSelApplyProvince;
    }

    public int getNowSelApplyCity() {
        return mNowSelApplyCity;
    }

    public void setNowSelApplyCity(int nowSelApplyCity) {
        mNowSelApplyCity = nowSelApplyCity;
    }

    public int getNowSelApplyAreas() {
        return mNowSelApplyAreas;
    }

    public void setNowSelApplyAreas(int nowSelApplyAreas) {
        mNowSelApplyAreas = nowSelApplyAreas;
    }


}
