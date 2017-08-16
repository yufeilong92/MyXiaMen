package com.lawyee.apppublic.dal;

import android.content.Context;

import com.lawyee.apppublic.config.Constants;

import net.lawyee.mobilelib.json.JsonCreater;
import net.lawyee.mobilelib.utils.StringUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 资讯类接口
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class InfoService extends BaseJsonService {
    /**
     * @param c
     */
    public InfoService(Context c) {
        super(c);
    }

    /**
     * 获取资讯列表接口
     * @param isvideo  是否是视频
     * @param category 资讯类型id
     * @param pageNo 第几页,从1开始
     * @param orgId  机构ID，只用于法律援助某机构-工作动态、公证某机构-工作动态,其他时候请转入null
     * @param listener 结果回调
     */
    public void getList(boolean isvideo,String category, int pageNo, String orgId,
                        IResultInfoListener listener)
    {
        if(isvideo)
            getVideoList(category,pageNo,orgId,listener);
        else
            getList(category,pageNo,orgId,listener);
    }

    /**
     * 获取资讯列表接口
     * @param category 资讯类型id
     * @param pageNo 第几页,从1开始
     * @param orgId  机构ID，只用于法律援助某机构-工作动态、公证某机构-工作动态,其他时候请传入null
     * @param listener 结果回调
     */
    public void getList(String category, int pageNo, String orgId,
                          IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("category",category);
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize",Constants.CINT_PAGE_SIZE);
        if(!StringUtil.isEmpty(orgId))
        creater.setParam("orgId",orgId);

        mCommandName = "mpInfoGetList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }


    /**
     * 获取视频资讯列表接口
     * @param category 资讯类型id
     * @param pageNo 第几页,从1开始
     * @param orgId  机构ID，只用于法律援助某机构-工作动态、公证某机构-工作动态,其他时候请转入null
     * @param listener 结果回调
     */
    public void getVideoList(String category, int pageNo, String orgId,
                        IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("category",category);
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize",Constants.CINT_PAGE_SIZE);
        if(!StringUtil.isEmpty(orgId))
            creater.setParam("orgId",orgId);

        mCommandName = "mpInfoGetVideoList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取资讯详情接口
     * @param oid 资讯id
     * @param listener 结果回调
     */
    public void getDetail(String oid,
                        IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("oid",oid);
        mCommandName = "mpInfoGetDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }
}
