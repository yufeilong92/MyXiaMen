package com.lawyee.apppublic.dal;

import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.json.JsonCreater;
import net.lawyee.mobilelib.utils.SecurityUtil;
import net.lawyee.mobilelib.utils.StringUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com

 * @version V1.0.xxxxxxxx
 * @Title: 基层法律服务VO
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/7/27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaglsService extends BaseJsonService {
    /**
     * @param c
     */
    public JaglsService(Context c) {
        super(c);
    }
    /**
     * 查询基层法律工作者列表接口
     * @param pageNo 第几页,从1开始
     * @param name 基层法律工作者名,支持模糊查询
     * @param area 地区id,全部时为空
     * @param service 服务内容id，全部时为空
     * @param isOnline 是否在线id，全部时为空
     * @param listener 结果回调
     */
    public void queryStaffList(int pageNo, String name,String area,String service,
                                String isOnline,IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        if(!StringUtil.isEmpty(name))
            creater.setParam("name",name);
        if(!StringUtil.isEmpty(area))
            creater.setParam("area",area);
        if(!StringUtil.isEmpty(service))
            creater.setParam("service",service);
        if(!StringUtil.isEmpty(isOnline))
            creater.setParam("isOnline",isOnline);
        mCommandName = "mpJaglsQueryStaffList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取基层法律工作者详情接口
     * @param staffId 基层法律工作者id
     * @param listener 结果回调
     */
    public void getStaffDetail(String staffId,
                                IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("staffId",staffId);
        mCommandName = "mpJaglsGetStaffDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 查询基层法律服务所列表接口
     * @param pageNo 第几页,从1开始
     * @param name 律所名,支持模糊查询
     * @param area 地区id,全部时为空
     * @param service 服务内容id，全部时为空
     * @param listener 结果回调
     */
    public void queryOrgList(int pageNo, String name,String area,String service,
                                 IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        if(!StringUtil.isEmpty(name))
            creater.setParam("name",name);
        if(!StringUtil.isEmpty(area))
            creater.setParam("area",area);
        if(!StringUtil.isEmpty(service))
            creater.setParam("service",service);
        mCommandName = "mpJaglsQueryOrgList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取基层法律服务所详情接口
     * @param orgId 基层法律服务所id
     * @param listener 结果回调
     */
    public void getOrgDetail(String orgId,
                                 IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("orgId",orgId);
        mCommandName = "mpJaglsGetOrgDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 发起法律工作者委托接口
     * @param entrustPersonTelephone 用户联系电话
     * @param entrustContent 委托内容
     * @param jaglsStaff 律师ID
     * @param service 服务项目id
     * @param listener 结果回调
     */
    public void postEntrust(String entrustPersonTelephone, String entrustContent,String jaglsStaff,String service,
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
        creater.setParam("entrustPersonTelephone",entrustPersonTelephone);
        creater.setParam("entrustContent",entrustContent);
        creater.setParam("jaglsStaff",jaglsStaff);
        creater.setParam("service",service);
        mCommandName = "mpJaglsPostEntrust";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        getData(json, null);
    }

    /**
     * 获取本所执业工作者列表
     * @param orgId 律所ID
     * @param listener 结果回调
     */
    public void getStaffWithOrg(String orgId,
                                      IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("orgId",orgId);
        mCommandName = "mpJaglsGetStaffWithOrg";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

}
