package com.lawyee.apppublic.dal;


import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.json.JsonCreater;
import net.lawyee.mobilelib.utils.SecurityUtil;
import net.lawyee.mobilelib.utils.StringUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 标题
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidService extends BaseJsonService {
    /**
     * @param c
     */
    public JaaidService(Context c) {
        super(c);
    }

    /**
     * 查询法援机构列表
     * @param pageNo 第几页,从1开始
     * @param province 省id，全部时为空
     * @param city 市id,全部时为空
     * @param county 区id，全部时为空
     * @param listener 结果回调
     */
    public void queryOrgList(int pageNo, String province,String city,String county,
                                IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        if(!StringUtil.isEmpty(province))
            creater.setParam("province",province);
        if(!StringUtil.isEmpty(city))
            creater.setParam("city",city);
        if(!StringUtil.isEmpty(county))
            creater.setParam("county",county);
        mCommandName = "mpJaaidQueryOrgList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取法援机构详情
     * @param oid 机构id
     * @param listener 结果回调
     */
    public void getOrgDetail(String oid,
                                IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("oid",oid);
        mCommandName = "mpJaaidGetOrgDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }
    /**
     * 提交法援预申请
     * @param detailVO 法援申请信息
     * @param listener 结果回调
     */
    public void postApply(JaaidApplyDetailVO detailVO,
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
        if(!checkRequired(listener,detailVO.getOid(),"未提供业务ID"))
            return;
        creater.setParam("oid",detailVO.getOid());
        creater.setParam("name",detailVO.getName());
        creater.setParam("gender",detailVO.getGender());
        creater.setParam("idType",detailVO.getIdType());
        creater.setParam("idCard",detailVO.getIdCard());
        creater.setParam("birthday",detailVO.getBirthday());
        creater.setParam("nation",detailVO.getNation());
        creater.setParam("telephone",detailVO.getTelephone());
        creater.setParam("province",detailVO.getProvince());
        creater.setParam("city",detailVO.getCity());
        creater.setParam("county",detailVO.getCounty());
        creater.setParam("address",detailVO.getAddress());
        creater.setParam("workUnit",detailVO.getWorkUnit());
        creater.setParam("caseDescription",detailVO.getCaseDescription());
        creater.setParam("agentName",detailVO.getAgentName());
        creater.setParam("agentType",detailVO.getAgentType());
        creater.setParam("agentIdCard",detailVO.getAgentIdCard());
        creater.setParam("partiesName",detailVO.getPartiesName());
        creater.setParam("applyUserCount",detailVO.getApplyUserCount());
        creater.setParam("partiesLocal",detailVO.getPartiesLocal());
        creater.setParam("caseHappenLocal",detailVO.getCaseHappenLocal());
        creater.setParam("handleOrgAddress",detailVO.getHandleOrgAddress());
        creater.setParam("manageOrgId",detailVO.getManageOrgId());
        creater.setParam("manageOrgName",detailVO.getManageOrgName());
        mCommandName = "mpJaaidPostApply";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        getData(json, null);
    }
}
