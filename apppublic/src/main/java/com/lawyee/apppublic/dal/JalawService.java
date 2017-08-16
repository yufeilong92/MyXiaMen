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
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师服务相关接口
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawService extends BaseJsonService {
    /**
     * @param c
     */
    public JalawService(Context c) {
        super(c);
    }

    /**
     * 查询律师列表接口
     * @param pageNo 第几页,从1开始
     * @param name 律师名,支持模糊查询
     * @param area 地区id,全部时为空
     * @param service 服务内容id，全部时为空
     * @param business 专业领域id，全部时为空
     * @param workingYears 从业年限id，全部时为空
     * @param isOnline 是否在线id，全部时为空
     * @param listener 结果回调
     */
    public void queryLawyerList(int pageNo, String name,String area,String service,
                                String business,String workingYears,String isOnline,
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
        if(!StringUtil.isEmpty(business))
            creater.setParam("business",business);
        if(!StringUtil.isEmpty(workingYears))
            creater.setParam("workingYears",workingYears);
        if(!StringUtil.isEmpty(isOnline))
            creater.setParam("isOnline",isOnline);
        mCommandName = "mpJalawQueryLawyerList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取律师详情接口
     * @param lawyerId 律师id
     * @param listener 结果回调
     */
    public void getLawyerDetail(String lawyerId,
                                IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("lawyerId",lawyerId);
        mCommandName = "mpJalawGetLawyerDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 查询律所列表接口
     * @param pageNo 第几页,从1开始
     * @param name 律所名,支持模糊查询
     * @param area 地区id,全部时为空
     * @param service 服务内容id，全部时为空
     * @param business 专业领域id，全部时为空
     * @param listener 结果回调
     */
    public void queryLawfirmList(int pageNo, String name,String area,String service,
                                String business,
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
        if(!StringUtil.isEmpty(business))
            creater.setParam("business",business);
        mCommandName = "mpJalawQueryLawfirmList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取律所详情接口
     * @param lawfirmId 律所id
     * @param listener 结果回调
     */
    public void getLawfirmDetail(String lawfirmId,
                                IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("lawfirmId",lawfirmId);
        mCommandName = "mpJalawGetLawfirmDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 发起律师委托接口
     * @param entrustPersonTelephone 用户联系电话
     * @param entrustContent 委托内容
     * @param entrustLawyer 律师ID
     * @param service 服务项目id
     * @param listener 结果回调
     */
    public void postEntrust(String entrustPersonTelephone, String entrustContent,String entrustLawyer,String service,
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
        creater.setParam("entrustLawyer",entrustLawyer);
        creater.setParam("service",service);
        mCommandName = "mpJalawPostEntrust";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        getData(json, null);
    }

    /**
     * 获取本所律师列表
     * @param lawfirmId 律所id
     * @param listener 结果回调
     */
    public void getLawyersWithLawfirm(String lawfirmId,
                                 IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("lawfirmId",lawfirmId);
        mCommandName = "mpJalawGetLawyersWithLawfirm";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取值班律师的列表
     * @param pageNo 第几页,从1开始计数
     * @param sorttype 排序方式,0表示按默认排序1表示按经验排序
     * @param listener 结果回调
     */
    public void getDutyLawyerList(int pageNo,int sorttype,
                                      IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        creater.setParam("dutyFlag","1");//表示值班律师
        if(sorttype==1)//经验排序 ”orderBy”:”first_practice_date DESC”
            creater.setParam("orderBy","first_practice_date DESC");
        mCommandName = "mpJalawGetDutyLawyerList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }
}
