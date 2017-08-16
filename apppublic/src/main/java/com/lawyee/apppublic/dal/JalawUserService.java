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
 * @Title: 律师管理人员相关接口
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/7/24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawUserService extends BaseJsonService {
    /**
     * @param c
     */
    public JalawUserService(Context c) {
        super(c);
    }

    /**
     * 【服务管理端】获取某律师的委托列表
     * @param pageNo 第几页,从1开始
     * @param listener 结果回调
     */
    public void getEntrustList(int pageNo,
                                    IResultInfoListener listener) {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(userVO==null|| StringUtil.isEmpty(userVO.getLoginId())||StringUtil.isEmpty(userVO.getPassword()))
        {
            listener.onError("请先进行用户登录","");
            return;
        }

        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("loginId", userVO.getLoginId());
        creater.setParam("role", userVO.getRole());
        creater.setParam("password", SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        mCommandName = "mmJalawGetEntrustList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 【服务管理端】获取某律师委托详情
     * @param entrustId 预申请id
     * @param listener 结果回调
     */
    public void getEntrustDetail(String entrustId,
                                      IResultInfoListener listener) {
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
        creater.setParam("entrustId",entrustId);
        mCommandName = "mmJalawGetEntrustDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 【服务管理端】获取某律师委托详情
     * @param entrustId 委托id
     * @param entrustLawyerMobilePhone 律师手机号
     * @param entrustLawyerFixedPhone 律师座机
     * @param entrustLawyerAnswer 回复内容
     * @param listener 结果回调
     */
    public void postEntrustReply(String entrustId,String entrustLawyerMobilePhone,
                                 String entrustLawyerFixedPhone,String entrustLawyerAnswer,
                                 IResultInfoListener listener) {
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
        creater.setParam("entrustId",entrustId);
        creater.setParam("entrustLawyerMobilePhone",entrustLawyerMobilePhone);
        creater.setParam("entrustLawyerFixedPhone",entrustLawyerFixedPhone);
        creater.setParam("entrustLawyerAnswer",entrustLawyerAnswer);
        mCommandName = "mmJalawPostEntrustReply";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }




}
