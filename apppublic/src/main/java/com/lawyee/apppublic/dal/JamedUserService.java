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
 * @Title: 调解员/媒体用户相关接口
 * @Package com.lawyee.apppublic.dal
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/3
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedUserService extends BaseJsonService {
    /**
     * @param c
     */
    public JamedUserService(Context c) {
        super(c);
    }
    /**
     * 【调解员/媒体】获取某服务管理人员调解业务列表
     * @param pageNo 第几页,从1开始
     * @param listener 结果回调
     */
    public void getApplyList(int pageNo,
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
        mCommandName = "mmJamedGetApplyList";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_LIST);
        getData(json, null);
    }

    /**
     * 【调解员/媒体】获取某服务管理人员调解业务详情
     * @param oid 调解申请ID
     * @param listener 结果回调
     */
    public void getApplyDetail(String oid,
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
        creater.setParam("oid",oid);
        mCommandName = "mmJamedGetApplyDetail";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 【调解员】提交新增调解业务信息
     * @param detailVO 调解申请信息
     * @param listener 结果回调
     */
    public void postApplyInfo(JamedApplyDetailVO detailVO,
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

        creater.setParam("serialNO",detailVO.getSerialNo());
        creater.setParam("applyName",detailVO.getApplyName());
        creater.setParam("applyGender",detailVO.getApplyGender());
        /*if(detailVO.isTjType())
            creater.setParam("tjType","true");
        else
            creater.setParam("tjType","false");*/
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
        mCommandName = "mmJamedPostApplyInfo";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        getData(json, null);
    }

    /**
     * 【调解员】提交调解业务审核信息
     * @param oid 调解申请id
     * @param mediaApplyType 如果勾选了建议媒体调解，true,未勾选，则为false
     * @param orgAcceptFlag 调解机构是否受理:0：未受理 1：受理 -1：不受理
     * @param orgAcceptOpinion 调解机构审核意见即其他说明
     * @param noAccpectReason 不受理理由id，详细见数据字典
     * @param listener 结果回调
     */
    public void postAccept(String oid,boolean mediaApplyType,String orgAcceptFlag,
                           String orgAcceptOpinion,String noAccpectReason,
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

        creater.setParam("oid",oid);
        if(mediaApplyType)
            creater.setParam("mediaApplyType","2");//则这里固定传2，如果未勾选，则不传此字段
        creater.setParam("orgAcceptFlag",orgAcceptFlag);
        creater.setParam("orgAcceptOpinion",orgAcceptOpinion);
        creater.setParam("noAccpectReason",noAccpectReason);
        mCommandName = "mmJamedPostAccept";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }


    /**
     * 【调解员】提交调解业务调解结束反馈信息
     * @param oid 调解申请id
     * @param successFlag 调解是否成功：1：成功：-1：不成功 0：待调解
     * @param endTime 调解结束时间 yyyy-MM-dd HH:mm:ss
     * @param judconfirmFlag 是否司法确认, 1：是：-1：否 0：待确认
     * @param listener 结果回调
     */
    public void postEndInfo(String oid,String successFlag,
                           String endTime,String judconfirmFlag,
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

        creater.setParam("oid",oid);
        creater.setParam("successFlag",successFlag);
        creater.setParam("endTime",endTime);
        creater.setParam("judconfirmFlag",judconfirmFlag);
        mCommandName = "mmJamedPostEndInfo";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }
    /**
     * 【调解员】提交最终媒体参与信息
     * @param oid 调解申请id
     * @param applyMediaConfirm 最终确认是否媒体调解       1是   -1否   0未确
     * @param applyOpinion 最终不参与其他说明
     * @param applynoAcceptReason 最终不参与原因id，详细见数据字典
     * @param listener 结果回调
     */
    public void postApply(String oid,String applyMediaConfirm,
                            String applyOpinion,String applynoAcceptReason,
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

        creater.setParam("oid",oid);
        creater.setParam("applyMediaConfirm",applyMediaConfirm);
        creater.setParam("applyOpinion",applyOpinion);
        creater.setParam("applynoAcceptReason",applynoAcceptReason);
        mCommandName = "mmJamedPostApply";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }

    /**
     * 【媒体】发起申请媒体参与调解
     * @param oid 调解申请id
     * @param listener 结果回调
     */
    public void postMediaApply(String oid,
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

        creater.setParam("oid",oid);
        mCommandName = "mmJamedPostMediaApply";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }
    /**
     * 【媒体】提交媒体筛选信息
     * @param oid 调解申请id
     * @param mediaConfirm 1：可以参与 -1：不可以参与
     * @param mediaOpinion 媒体审核不参与其他说明
     * @param mediaNoAcceptReason 媒体审核不参与原因id，详细见数据字典
     * @param listener 结果回调
     */
    public void postMediaAccept(String oid,String mediaConfirm,
                          String mediaOpinion,String mediaNoAcceptReason,
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

        creater.setParam("oid",oid);
        creater.setParam("mediaConfirm",mediaConfirm);
        creater.setParam("mediaOpinion",mediaOpinion);
        creater.setParam("mediaNoAcceptReason",mediaNoAcceptReason);
        mCommandName = "mmJamedPostMediaAccept";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }
    /**
     * 【媒体】提交录制信息
     * @param oid 调解申请id
     * @param recordTime 录制时间, yyyy-MM-dd HH:mm:ss
     * @param recordAddress 录制地点
     * @param listener 结果回调
     */
    public void postMediaRecord(String oid,String recordTime,
                          String recordAddress,
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

        creater.setParam("oid",oid);
        creater.setParam("recordTime",recordTime);
        creater.setParam("recordAddress",recordAddress);
        mCommandName = "mmJamedPostMediaRecord";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }
    /**
     * 【媒体】提交媒体播出信息
     * @param oid 调解申请id
     * @param playChannel 播出频道
     * @param playtime 播出时间,yyyy-MM-dd
     * @param programTitle  节目标题
     * @param mediaPlayUrl  视频地址
     * @param netFlag  是否挂网 0否，1是
     * @param listener 结果回调
     */
    public void postMediaPlay(String oid,String playChannel,
                          String playtime,String programTitle,
                          String mediaPlayUrl,String netFlag,
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

        creater.setParam("oid",oid);
        creater.setParam("playChannel",playChannel);
        creater.setParam("playTime",playtime);
        creater.setParam("programTitle",programTitle);
        creater.setParam("mediaPlayUrl",mediaPlayUrl);
        creater.setParam("netFlag",netFlag);
        mCommandName = "mmJamedPostMediaPlay";
        String json = creater.createJson(mCommandName);
        setResultInfoListener(listener);
        setValueType(CINT_VALUETYPE_ENTITY);
        getData(json, null);
    }
}
