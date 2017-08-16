package com.lawyee.apppublic.dal;

import android.content.Context;

import net.lawyee.mobilelib.json.JsonCreater;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 通用的一些数据接口
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class CommonService extends BaseJsonService {
    /**
     * @param c
     */
    public CommonService(Context c) {
        super(c);
    }

    /**
     * 获取数据字典接口
     * @param listener 结果回调
     */
    public void getDataDictionary(IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        mCommandName = "mpCommonGetDataDictionary";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 获取行政区划数据
     * @param listener 结果回调
     */
    public void getArea(IResultInfoListener listener) {
        JsonCreater creater = JsonCreater.startJson(getDevID());
        mCommandName = "mpCommonGetArea";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

}
