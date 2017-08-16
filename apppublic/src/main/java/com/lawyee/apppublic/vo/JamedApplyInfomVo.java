package com.lawyee.apppublic.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.vo
 * @Description: 人民调解申请页记录申请人选择民族
 * @author: YFL
 * @date: 2017/6/6 16:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JamedApplyInfomVo {
    private List<BaseCommonDataVO> mNationDatas;//申请人民族
    private List<BaseCommonDataVO> mQuiltNationDatas;//被申请人民族

    //当前选中的省索引
    private int mNowSelApplyNatione;//记录选中申请人民族的id
    private int mNowSelApplyQuiltNation;//记录选中被申请人民族的id
    public JamedApplyInfomVo()
    {
        mNationDatas = new ArrayList<>();
        mQuiltNationDatas = new ArrayList<>();
        mNowSelApplyNatione = -1;
        mNowSelApplyQuiltNation = -1;
    }


    public List<BaseCommonDataVO> getNationDatas() {
        return mNationDatas;
    }

    public void setNationDatas(List<BaseCommonDataVO> nationDatas) {
        mNationDatas = nationDatas;
    }

    public List<BaseCommonDataVO> getQuiltNationDatas() {
        return mQuiltNationDatas;
    }

    public void setQuiltNationDatas(List<BaseCommonDataVO> quiltNationDatas) {
        mQuiltNationDatas = quiltNationDatas;
    }


    public int getNowSelApplyNatione() {
        return mNowSelApplyNatione;
    }

    public void setNowSelApplyNatione(int nowSelApplyNatione) {
        mNowSelApplyNatione = nowSelApplyNatione;
    }

    public int getNowSelApplyQuiltNation() {
        return mNowSelApplyQuiltNation;
    }

    public void setNowSelApplyQuiltNation(int nowSelApplyQuiltNation) {
        mNowSelApplyQuiltNation = nowSelApplyQuiltNation;
    }

}
