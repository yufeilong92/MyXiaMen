package com.lawyee.apppublic.dal;

import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.json.JsonCreater;
import net.lawyee.mobilelib.utils.SecurityUtil;
import net.lawyee.mobilelib.utils.StringUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 人民调解业务相关接口
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedService extends BaseJsonService {
    /**
     * @param c
     */
    public JamedService(Context c) {
        super(c);
    }

    /**
     * 查询人民调解机构列表
     * @param pageNo 第几页,从1开始
     * @param isMajorTj  调解类型：直接传名称：专业调解，一般调解,如果是全部则传空
     * @param city 市id,全部时为空
     * @param county 区id，全部时为空
     * @param listener 结果回调
     */
    public void queryOrgList(int pageNo, String isMajorTj,String city,String county,
                             IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        if("专业调解".equals(isMajorTj))
            creater.setParam("isMajorTj","true");
        else if("一般调解".equals(isMajorTj))
            creater.setParam("isMajorTj","false");

        if(!StringUtil.isEmpty(city))
            creater.setParam("city",city);
        if(!StringUtil.isEmpty(county))
            creater.setParam("county",county);
        mCommandName = "mpJamedQueryOrgList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取人民调解机构详情
     * @param oid 机构id
     * @param listener 结果回调
     */
    public void getOrgDetail(String oid,
                             IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("oid",oid);
        mCommandName = "mpJamedGetOrgDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }


    /**
     * 获取人民调解机构调解员列表
     * @param oid 机构id
     * @param listener 结果回调
     */
    public void getStaffList(String oid,
                                      IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("oid",oid);
        mCommandName = "mpJamedGetStaffList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 提交调解申请信息
     * @param detailVO 调解申请信息
     * @param listener 结果回调
     */
    public void postApply(JamedApplyDetailVO detailVO,
                          IResultInfoListener listener)
    {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(userVO==null||StringUtil.isEmpty(userVO.getLoginId())||StringUtil.isEmpty(userVO.getPassword()))
        {
            listener.onError("请先进行用户登录","");
            return;
        }

        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("loginId", userVO.getLoginId());
        creater.setParam("role", userVO.getRole());
        creater.setParam("password", SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));

        creater.setParam("applyName",detailVO.getApplyName());
        creater.setParam("applyGender",detailVO.getApplyGender());
        if(detailVO.isTjType())
            creater.setParam("tjType","true");
        else
            creater.setParam("tjType","false");
        creater.setParam("tjOrgId",detailVO.getTjOrgId());
        creater.setParam("tjOrgName",detailVO.getTjOrgName());
        if(detailVO.isMediaFlag())
            creater.setParam("mediaFlag","true");
        else
            creater.setParam("mediaFlag","false");
        creater.setParam("applyIdCard",detailVO.getApplyIdCard());
        creater.setParam("applyAge",detailVO.getApplyAge());
        creater.setParam("applyNation",detailVO.getApplyNation());
        creater.setParam("applyTelephone",detailVO.getApplyTelephone());
        creater.setParam("applyAddress",detailVO.getApplyAddress());
        creater.setParam("relation",detailVO.getRelation());
        creater.setParam("beApplyName",detailVO.getBeApplyName());
        creater.setParam("beApplyGender",detailVO.getBeApplyGender());
        creater.setParam("beApplyNation",detailVO.getBeApplyNation());
        creater.setParam("beApplyAge",detailVO.getBeApplyAge());
        creater.setParam("beApplyTelephone",detailVO.getBeApplyTelephone());
        creater.setParam("beApplyAddress",detailVO.getBeApplyAddress());
        creater.setParam("introduction",detailVO.getIntroduction());
        creater.setParam("matter",detailVO.getMatter());
        mCommandName = "mpJamedPostApply";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        getData(json, null);
    }
}
