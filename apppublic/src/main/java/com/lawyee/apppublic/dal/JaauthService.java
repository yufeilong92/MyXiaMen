package com.lawyee.apppublic.dal;

import android.content.Context;

import com.lawyee.apppublic.config.Constants;

import net.lawyee.mobilelib.json.JsonCreater;
import net.lawyee.mobilelib.utils.StringUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 鉴定业务相关接口
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaauthService extends BaseJsonService {
    /**
     * @param c
     */
    public JaauthService(Context c) {
        super(c);
    }

    /**
     * 查询鉴定机构列表
     * @param pageNo 第几页,从1开始
     * @param serviceScope 业务范围id，全部时为空
     * @param city 市id,全部时为空
     * @param county 区id，全部时为空
     * @param listener 结果回调
     */
    public void queryOrgList(int pageNo, String serviceScope,String city,String county,
                             IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        if(!StringUtil.isEmpty(serviceScope))
            creater.setParam("serviceScope",serviceScope);
        if(!StringUtil.isEmpty(city))
            creater.setParam("city",city);
        if(!StringUtil.isEmpty(county))
            creater.setParam("county",county);
        mCommandName = "mpJaauthQueryOrgList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取鉴定机构详情
     * @param oid 机构id
     * @param listener 结果回调
     */
    public void getOrgDetail(String oid,
                             IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("oid",oid);
        mCommandName = "mpJaauthGetOrgDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }


    /**
     * 获取鉴定机构鉴定员列表
     * @param oid 机构id
     * @param listener 结果回调
     */
    public void getStaffList(String oid,
                                      IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("oid",oid);
        mCommandName = "mpJaauthGetStaffList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }
}
