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
 * @Description: 鉴定机构文件缓存
 * @author: YFL
 * @date: 2017/6/1 11:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaauthFilterVo extends BaseVO {

    private static final long serialVersionUID = 7563475909972856390L;
    private List<BaseCommonDataVO> mBusinessList;//业务list
    private List<BaseCommonDataVO> mJaauthCitys;//业务list
    private List<BaseCommonDataVO> mJaauthAreas;//业务list

    private int mNowSelBusiness;//记录业务id
    private int mNowSelCity;//记录鉴定机构城市id
    private int mNowSelArea;//记录鉴定机构区域id

    public JaauthFilterVo() {
        mBusinessList = new ArrayList<>();
        mJaauthAreas = new ArrayList<>();
        mJaauthCitys = new ArrayList<>();

       mNowSelBusiness=-1;
       mNowSelCity=-1;
       mNowSelArea=-1;

    }

    public List<BaseCommonDataVO> getmBusinessList() {
        return mBusinessList;
    }

    public void setmBusinessList(List<BaseCommonDataVO> mBusinessList) {
        this.mBusinessList = mBusinessList;
    }

    public List<BaseCommonDataVO> getmJaauthCitys() {
        return mJaauthCitys;
    }

    public void setmJaauthCitys(List<BaseCommonDataVO> mJaauthCitys) {
        this.mJaauthCitys = mJaauthCitys;
    }

    public List<BaseCommonDataVO> getmJaauthAreas() {
        return mJaauthAreas;
    }

    public void setmJaauthAreas(List<BaseCommonDataVO> mJaauthAreas) {
        this.mJaauthAreas = mJaauthAreas;
    }

    public int getmNowSelBusiness() {
        return mNowSelBusiness;
    }

    public void setmNowSelBusiness(int mNowSelBusiness) {
        this.mNowSelBusiness = mNowSelBusiness;
    }

    public int getmNowSelCity() {
        return mNowSelCity;
    }

    public void setmNowSelCity(int mNowSelCity) {
        this.mNowSelCity = mNowSelCity;
    }

    public int getmNowSelArea() {
        return mNowSelArea;
    }

    public void setmNowSelArea(int mNowSelArea) {
        this.mNowSelArea = mNowSelArea;
    }

    public static String dataFileName(Context c,String filtername) {
        return dataFileName(c, serialVersionUID,filtername);
    }


}
