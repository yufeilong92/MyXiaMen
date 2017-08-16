package com.lawyee.apppublic.dal;

import android.content.Context;

import com.lawyee.apppublic.config.Constants;

import net.lawyee.mobilelib.json.JsonCreater;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 法制活动相关Service
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/2
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JapubService extends BaseJsonService {
    /**
     * @param c
     */
    public JapubService(Context c) {
        super(c);
    }
    /**
     * 获取法宣活动列表
     * @param category 活动类型ID，全部时此参数为空
     * @param pageNo 第几页,从1开始
     * @param city  市id,可以空
     * @param county 区县id,可以空
     * @param listener 结果回调
     */
    public void getActivityList(String category, int pageNo, String city,
                                String county,IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        creater.setParamAutoCheckEmpty("category",category);
        creater.setParamAutoCheckEmpty("activityCitySearch",city);
        creater.setParamAutoCheckEmpty("activityCountySearch",county);
        mCommandName = "mmJapubGetActivityList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }
    /**
     * 获取法宣活动详情
     * @param oid 法宣活动id
     * @param listener 结果回调
     */
    public void getActivityDetail(String oid,
                          IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("oid",oid);
        mCommandName = "mmJapubGetActivityDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

}
