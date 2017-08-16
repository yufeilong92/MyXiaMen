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
 * @Description: 人民调解缓存数据
 * @author: YFL
 * @date: 2017/6/2 13:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JamedOrgFilterVo extends BaseVO {
    private static final long serialVersionUID = 1766220539888919199L;
    private List<String> mMediationTypeDatas;//调解类型
    private List<BaseCommonDataVO> mJamedCityDatas;//（人民调解）市
    private List<BaseCommonDataVO> mJamedAreasDatas;//（人民调解）区县

    private int mNowSelJamedAreas;//记录选中（人民调解）区县的id
    private int mNowSelMediationType;//调解类型id
    private int mNowSelJamedCity;//记录选中(人民调解)市的id

    public JamedOrgFilterVo() {
        mMediationTypeDatas =new ArrayList<>();
        mJamedCityDatas =new ArrayList<>();
        mJamedAreasDatas=new ArrayList<>();
        mNowSelJamedAreas=-1;
        mNowSelJamedCity=-1;
        mNowSelMediationType=-1;
    }

    public static String dataFileName(Context c, String filename) {
        return dataFileName(c,serialVersionUID,filename);
    }

    public List<String> getmMediationTypeDatas() {
        return mMediationTypeDatas;
    }

    public void setmMediationTypeDatas(List<String> mMediationTypeDatas) {
        this.mMediationTypeDatas = mMediationTypeDatas;
    }

    public List<BaseCommonDataVO> getJamedCityDatas() {
        return mJamedCityDatas;
    }

    public void setJamedCityDatas(List<BaseCommonDataVO> jamedCityDatas) {
        mJamedCityDatas = jamedCityDatas;
    }

    public List<BaseCommonDataVO> getJamedAreasDatas() {
        return mJamedAreasDatas;
    }

    public void setJamedAreasDatas(List<BaseCommonDataVO> jamedAreasDatas) {
        mJamedAreasDatas = jamedAreasDatas;
    }

    public int getNowSelJamedAreas() {
        return mNowSelJamedAreas;
    }

    public void setNowSelJamedAreas(int nowSelJamedAreas) {
        mNowSelJamedAreas = nowSelJamedAreas;
    }

    public int getNowSelMediationType() {
        return mNowSelMediationType;
    }

    public void setNowSelMediationType(int nowSelMediationType) {
        mNowSelMediationType = nowSelMediationType;
    }

    public int getNowSelJamedCity() {
        return mNowSelJamedCity;
    }

    public void setNowSelJamedCity(int nowSelJamedCity) {
        mNowSelJamedCity = nowSelJamedCity;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


}
