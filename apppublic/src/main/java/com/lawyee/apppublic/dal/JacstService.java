package com.lawyee.apppublic.dal;

import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.json.JsonCreater;
import net.lawyee.mobilelib.utils.SecurityUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.util.Date;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 咨询服务Service
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/3
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JacstService extends BaseJsonService {
    /**
     * @param c
     */
    public JacstService(Context c) {
        super(c);
    }

    /**
     * 【公众/律师】获取咨询服务列表
     * @param pageNo 第几页,从1开始
     * @param listener 结果回调
     */
    public void getConsulationList(int pageNo,
                                  IResultInfoListener listener) {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(userVO==null|| StringUtil.isEmpty(userVO.getLoginId())||StringUtil.isEmpty(userVO.getPassword()))
        {
            listener.onError("请先进行用户登录","");
            return;
        }

        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("loginId", userVO.getLoginId());
        creater.setParam("password", SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
        creater.setParam("role", userVO.getRole());
        creater.setParam("pageNo",pageNo<1?1:pageNo);
        creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
        if(userVO.isPublicUser())
            mCommandName = "mpUserGetConsulationList";
        else
            mCommandName = "mmUserGetConsulationList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 【公众/律师】获取咨询服务详情
     * @param oid 咨询服务id
     * @param listener 结果回调
     */
    public void getConsulationDetail(String oid,
                                    IResultInfoListener listener) {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(userVO==null||StringUtil.isEmpty(userVO.getLoginId())||StringUtil.isEmpty(userVO.getPassword()))
        {
            listener.onError("请先进行用户登录","");
            return;
        }

        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("loginId", userVO.getLoginId());
        creater.setParam("password", SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
        creater.setParam("role", userVO.getRole());
        creater.setParam("oid",oid);
        mCommandName = "mmUserGetConsulationDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 【公众/律师】获取某条咨询服务聊天记录接口
     * @param oid 咨询服务id
     * @param listener 结果回调
     */
    public void getConsulationRecordList(String oid,
                                   IResultInfoListener listener) {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(userVO==null|| StringUtil.isEmpty(userVO.getLoginId())||StringUtil.isEmpty(userVO.getPassword()))
        {
            listener.onError("请先进行用户登录","");
            return;
        }

        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("loginId", userVO.getLoginId());
        creater.setParam("password", SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
        creater.setParam("role", userVO.getRole());
        creater.setParam("oid",oid);
        mCommandName = "mpUserGetConsulationRecordList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 【公众】发起咨询服务评价接口
     * @param oid 咨询服务oid
     * @param evaluateScore    咨询服务评价,1不满意 2 满意 3 非常满意
     * @param evaluateDescribe 咨询服务建议
     * @param listener 结果回调
     */
    public void postJacstConsulationEvaluate(String oid,String evaluateScore,
                                         String evaluateDescribe,IResultInfoListener listener) {
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
        creater.setParam("oid",oid);
        creater.setParam("evaluateScore",evaluateScore);
        creater.setParam("evaluateDescribe",evaluateDescribe);
        mCommandName = "mpUserPostJacstConsulationEvaluate";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        getData(json, null);
    }

     /**
     * 【律师】咨询处理接口
     * @param oid 咨询服务oid
     * @param consultType  咨询类型
     * @param consultTypeName 咨询类型名称
     * @param consultCotent 咨询类型名称
     * @param dealResult 处理结果
     * @param consultPerson 咨询人姓名
     * @param consultPersonId 咨询人id
     * @param listener
     */
    public void postJacstConsulationHandle(String oid,String consultType,String consultTypeName,
                                             String consultCotent,String dealResult,
                                             String consultPerson,String consultPersonId,
                                             IResultInfoListener listener) {
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(userVO==null||StringUtil.isEmpty(userVO.getLoginId())||StringUtil.isEmpty(userVO.getPassword()))
        {
            listener.onError("请先进行用户登录","");
            return;
        }

        JsonCreater creater = JsonCreater.startJson(getDevID());
        creater.setParam("loginId", userVO.getLoginId());
        creater.setParam("password", SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
        creater.setParam("role", userVO.getRole());
        creater.setParam("oid",oid);
        creater.setParam("consultType",consultType);
        creater.setParam("consultTypeName",consultTypeName);
        creater.setParam("consultCotent",consultCotent);
        creater.setParam("consultWay","138fa6b221464fb0b8a7cc37d61f8114");//咨询方式，数据字典里固定值-在线咨询id
        creater.setParam("consultWayName","在线咨询");//
        creater.setParam("acceptUnit",userVO.getOrgId());
        creater.setParam("acceptUnitName",userVO.getOrgName());
        creater.setParam("province",userVO.getProvince());
        creater.setParam("city",userVO.getCity());
        creater.setParam("county",userVO.getCounty());
        creater.setParam("dealResult",dealResult);
        creater.setParam("consultPerson",consultPerson);
        creater.setParam("consultPersonId",consultPersonId);
        creater.setParam("answerPersonId",userVO.getOid());
        creater.setParam("answerPerson",userVO.getRealName());
        creater.setParam("answerDate", TimeUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        creater.setParam("consultStatus","1bae189eeb2d4c7a8a309fece6da39df");//状态，数据字典里固定值-已办理id” //对应数据字典
        mCommandName = "mmLawyerPostJacstConsulationHandle";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        getData(json, null);
    }
}
