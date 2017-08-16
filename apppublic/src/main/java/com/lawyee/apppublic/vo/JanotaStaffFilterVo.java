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
 * @Description: 公证缓存数据vo
 * @author: YFL
 * @date: 2017/6/2 10:12
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JanotaStaffFilterVo extends BaseVO {
    private static final long serialVersionUID = -2564154902692683328L;
    private List<BaseCommonDataVO> mSercieAreas;//服务范围
    private List<BaseCommonDataVO> mNtzCityDatas;//（公证）市
    private List<BaseCommonDataVO> mNtzAreasDatas;//（公证）区县

    private int mNowSelNtzAreas;//记录选中（公证）区县的id
    private int mNowSelServiceArea;//记录选中服务范围的id
    private int mNowSelNtzCity;//记录选中(公证)市的id
    public JanotaStaffFilterVo() {
        mNtzCityDatas=new ArrayList<>();
        mNtzAreasDatas=new ArrayList<>();
        mSercieAreas=new ArrayList<>();
        mNowSelServiceArea=-1;
        mNowSelNtzCity=-1;
        mNowSelNtzAreas=-1;
    }
    public List<BaseCommonDataVO> getSercieAreas() {
        return mSercieAreas;
    }

    public void setSercieAreas(List<BaseCommonDataVO> sercieAreas) {
        mSercieAreas = sercieAreas;
    }

    public List<BaseCommonDataVO> getNtzCityDatas() {
        return mNtzCityDatas;
    }

    public void setNtzCityDatas(List<BaseCommonDataVO> ntzCityDatas) {
        mNtzCityDatas = ntzCityDatas;
    }
    public List<BaseCommonDataVO> getNtzAreasDatas() {
        return mNtzAreasDatas;
    }

    public void setNtzAreasDatas(List<BaseCommonDataVO> ntzAreasDatas) {
        mNtzAreasDatas = ntzAreasDatas;
    }

    public int getNowSelNtzAreas() {
        return mNowSelNtzAreas;
    }

    public void setNowSelNtzAreas(int nowSelNtzAreas) {
        mNowSelNtzAreas = nowSelNtzAreas;
    }

    public int getNowSelServiceArea() {
        return mNowSelServiceArea;
    }

    public void setNowSelServiceArea(int nowSelServiceArea) {
        mNowSelServiceArea = nowSelServiceArea;
    }

    public int getNowSelNtzCity() {
        return mNowSelNtzCity;
    }

    public void setNowSelNtzCity(int nowSelNtzCity) {
        mNowSelNtzCity = nowSelNtzCity;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
    public static String dataFileName(Context c,String filtername) {
        return dataFileName(c, serialVersionUID,filtername);
    }

}
