/**
 * Project:RLSClient
 * File:UserService.java
 * Copyright 2013 WUZHUPC Co., Ltd. All rights reserved.
 */
package com.lawyee.apppublic.dal;

import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.json.JsonCreater;
import net.lawyee.mobilelib.utils.SecurityUtil;
import net.lawyee.mobilelib.utils.StringUtil;

/**
 * 公众用户相关接口
 * @author wuzhu
 */
public class UserService extends BaseJsonService {

	/**
	 * 取验证码action-用户注册
	 */
	public static final String CSTR_GETIDENTCODE_ACTION_REGISTERUSER = "registeruser";

	/**
	 * 取验证码action-找回密码
	 */
	public static final String CSTR_GETIDENTCODE_ACTION_FINDPWD = "findpwd";

	/**
	 * @param c
	 */
	public UserService(Context c) {
		super(c);
	}

	/**
	 * 公众用户登录
	 * @param loginId 登录帐号
	 * @param password 密码
	 * @param role  登入用户的身份
	 *                 （公众人员=0、机关工作人员=1、法援工作者=2、律师=3、鉴定人员=4、公证人员=5、人民调解员=6、新闻媒体工作人员=m,
	 *                 详细见UserVO中的定义CSTR_USERROLE_*
	 * @param listener 结果回调
	 */
	public void userLogin(String loginId, String password,String role,
			IResultInfoListener listener) {
		JsonCreater creater = JsonCreater.startJson(getDevID());
		creater.setParam("loginId", loginId);
		creater.setParam("password", SecurityUtil.Encrypt(password,SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
		creater.setParam("role", role);
		mCommandName = "mmUserLogin";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}

	/**
	 * 公众用户注册
	 * @param mobile 手机号
	 * @param password 密码
	 * @param identifyingCode  验证码
	 * @param listener 结果回调
	 */
	public void userRegister(String mobile, String password,String identifyingCode,
						  IResultInfoListener listener) {
		if(StringUtil.isEmpty(identifyingCode))
		{
			listener.onError("请输入有效的验证码","");
			return;
		}
		if(!StringUtil.validateMoblie(mobile)|| !UserVO.isEffPassword(password)) {
			listener.onError("无效的登录手机号或密码","");
			return;
		}
		JsonCreater creater = JsonCreater.startJson(getDevID());
		creater.setParam("mobile", mobile);
		creater.setParam("password", SecurityUtil.Encrypt(password,SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
		creater.setParam("identifyingCode",identifyingCode);
		mCommandName = "mpUserRegister";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		getData(json, null);
	}

	/**
	 * 公众用户请求验证码
	 * @param mobile 手机号
	 * @param action registeruser  用户注册 findpwd  找回密码 传入：CSTR_GETIDENTCODE_ACTION_*
	 * @param listener 结果回调
	 */
	public void getIdentifyingCode(String mobile,String action,
							 IResultInfoListener listener) {
		if(!StringUtil.validateMoblie(mobile)) {
			listener.onError("无效的手机号","");
			return;
		}
		JsonCreater creater = JsonCreater.startJson(getDevID());
		creater.setParam("mobile", mobile);
		creater.setParam("password", SecurityUtil.Encrypt(mobile,SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
		creater.setParam("action", action);
		mCommandName = "mpUserGetIdentifyingCode";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		getData(json, null);
	}

	/**
	 * 公众用户修改密码
	 * @param mobile 手机号
	 * @param password 新密码
	 * @param identifyingCode  验证码
	 * @param listener 结果回调
	 */
	public void changePwd(String mobile, String password,String identifyingCode,
							 IResultInfoListener listener) {
		if(StringUtil.isEmpty(identifyingCode))
		{
			listener.onError("请输入有效的验证码","");
			return;
		}
		if(!StringUtil.validateMoblie(mobile)|| !UserVO.isEffPassword(password)) {
			listener.onError("无效的登录手机号或密码","");
			return;
		}
		JsonCreater creater = JsonCreater.startJson(getDevID());
		creater.setParam("mobile", mobile);
		creater.setParam("password", SecurityUtil.Encrypt(password,SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS));
		creater.setParam("identifyingCode",identifyingCode);
		mCommandName = "mpUserChangePwd";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		getData(json, null);
	}

	/**
	 * 修改某公众个人信息
	 * @param nickName 昵称,空时请传入空字符串
	 * @param realName 真实姓名,空时请传入空字符串
	 * @param gender 性别id,空时请传入空字符串
	 * @param mobile 手机号码,空时请传入空字符串
	 * @param idCard 身份证号,空时请传入空字符串
	 * @param province 省id,空时请传入空字符串
	 * @param city 市id,空时请传入空字符串
	 * @param county 区县id,空时请传入空字符串
	 * @param address 具体地址,空时请传入空字符串
	 * @param birthday 出生日期，格式yyyy-MM-dd
	 * @param listener 结果回调
	 */
	public void changeUserInfo(String nickName, String realName,String gender,
							   String mobile,String idCard,String province,
							   String city,String county,String address,String birthday,
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
		String pwd = SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS);
		creater.setParam("password", pwd);
		creater.setParamAutoCheckEmpty("nickName",nickName);
		creater.setParamAutoCheckEmpty("realName",realName);
		creater.setParamAutoCheckEmpty("gender",gender);
		creater.setParamAutoCheckEmpty("mobile",mobile);
		creater.setParamAutoCheckEmpty("idCard",idCard);
		creater.setParamAutoCheckEmpty("province",province);
		creater.setParamAutoCheckEmpty("city",city);
		creater.setParamAutoCheckEmpty("county",county);
		creater.setParamAutoCheckEmpty("address",address);
		creater.setParamAutoCheckEmpty("birthday",birthday);
		mCommandName = "mpUserChangeUserInfo";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}

	/**
	 * 获取某公众用户法律援助预申请列表
	 * @param pageNo 第几页,从1开始
	 * @param listener 结果回调
	 */
	public void getJaaidApplyList(int pageNo,
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
		String pwd = SecurityUtil.Encrypt(userVO.getPassword(),SecurityUtil.getLegalKey(creater.getId()), Constants.CSTR_IVS);
		creater.setParam("password", pwd);
		//L.d("service","pwd:"+pwd+" mappwd"+creater.getParamsMap().get("password"));
		creater.setParam("pageNo",pageNo<1?1:pageNo);
		creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
		mCommandName = "mpUserGetJaaidApplyList";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_LIST);
		getData(json, null);
	}

	/**
	 * 获取某法律援助预申请详情
	 * @param oid 预申请id
	 * @param listener 结果回调
	 */
	public void getJaaidApplyDetail(String oid,
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
		mCommandName = "mpUserGetJaaidApplyDetail";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}

	/**
	 * 获取某公众用户调解预申请列表
	 * @param pageNo 第几页,从1开始
	 * @param listener 结果回调
	 */
	public void getJamedApplyList(int pageNo,
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
		creater.setParam("pageNo",pageNo<1?1:pageNo);
		creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
		mCommandName = "mpUserGetJamedApplyList";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_LIST);
		getData(json, null);
	}

	/**
	 * 获取某调解预申请详情
	 * @param oid 预申请id
	 * @param listener 结果回调
	 */
	public void getJamedApplyDetail(String oid,
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
		mCommandName = "mpUserGetJamedApplyDetail";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}

	/**
	 * 获取某公众用户律师委托列表
	 * @param pageNo 第几页,从1开始
	 * @param listener 结果回调
	 */
	public void getJalawEntrustList(int pageNo,
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
		creater.setParam("pageNo",pageNo<1?1:pageNo);
		creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
		mCommandName = "mpUserGetJalawEntrustList";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_LIST);
		getData(json, null);
	}

	/**
	 * 获取某律师委托详情
	 * @param entrustId 委托id
	 * @param listener 结果回调
	 */
	public void getJalawEntrustDetail(String entrustId,
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
		mCommandName = "mpUserGetJalawEntrustDetail";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}

	/**
	 * 提交律师委托评价接口
	 * @param entrustId 委托id
	 * @param evaluateLawyerScore  评价律师服务,1不满意 2 满意 3 非常满意
	 * @param evaluateLawyerDescribe 对律师服务建议
	 * @param evaluateLawfirmScore  对律所评价,1不满意 2 满意 3 非常满
	 * @param evaluateLawfirmDescribe 对律所评价建议
	 * @param listener 结果回调
	 */
	public void postEntrustEvaluate(String entrustId,String evaluateLawyerScore,
									String evaluateLawyerDescribe,String evaluateLawfirmScore,
									String evaluateLawfirmDescribe,IResultInfoListener listener) {
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
		creater.setParam("evaluateLawyerScore",evaluateLawyerScore);
		creater.setParam("evaluateLawyerDescribe",evaluateLawyerDescribe);
		creater.setParam("evaluateLawfirmScore",evaluateLawfirmScore);
		creater.setParam("evaluateLawfirmDescribe",evaluateLawfirmDescribe);
		mCommandName = "mpUserPostEntrustEvaluate";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}

	/**
	 * 获取某公众用户基层法律服务委托列表
	 * @param pageNo 第几页,从1开始
	 * @param listener 结果回调
	 */
	public void getJaglsEntrustList(int pageNo,
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
		creater.setParam("pageNo",pageNo<1?1:pageNo);
		creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
		mCommandName = "mpUserGetJaglsEntrustList";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_LIST);
		getData(json, null);
	}

	/**
	 * 获取某基层法律服务工作者委托详情
	 * @param entrustId 委托id
	 * @param listener 结果回调
	 */
	public void getJaglsEntrustDetail(String entrustId,
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
		mCommandName = "mpUserGetJaglsEntrustDetail";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}

	/**
	 * 提交基层法律服务工作者委托评价接口
	 * @param entrustId 委托id
	 * @param evaluateStaffScore   评价工作者服务,1不满意 2 满意 3 非常满意
	 * @param evaluateStaffDescribe 对工作者服务建议
	 * @param evaluateOrgScore   对服务所评价,1不满意 2 满意 3 非常满
	 * @param evaluateOrgDescribe 对服务所评价建议
	 * @param listener 结果回调
	 */
	public void postJaglsEntrustEvaluate(String entrustId,String evaluateStaffScore,
									String evaluateStaffDescribe,String evaluateOrgScore,
									String evaluateOrgDescribe,IResultInfoListener listener) {
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
		creater.setParam("evaluateStaffScore",evaluateStaffScore);
		creater.setParam("evaluateStaffDescribe",evaluateStaffDescribe);
		creater.setParam("evaluateOrgScore",evaluateOrgScore);
		creater.setParam("evaluateOrgDescribe",evaluateOrgDescribe);
		mCommandName = "mpUserPostJaglsEntrustEvaluate";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_ENTITY);
		getData(json, null);
	}



	/**
	 * 获取某公众用户消息列表
	 * @param pageNo 第几页,从1开始
	 * @param listener 结果回调
	 */
	public void getMessageList(int pageNo,
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
		creater.setParam("pageNo",pageNo<1?1:pageNo);
		creater.setParam("pageSize", Constants.CINT_PAGE_SIZE);
		mCommandName = "mpUserGetMessageList";
		String json = creater.createJson(mCommandName);
		setResultInfoListener(listener);
		setValueType(CINT_VALUETYPE_LIST);
		getData(json, null);
	}
}
