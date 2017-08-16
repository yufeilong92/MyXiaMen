package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.utils.JavaLangUtil;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师委托详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawLawyerEntrustDetailVO extends JalawLawyerEntrustVO {
    /**
     *委托内容
     */
    private String entrustContent;
    /**
     * 律师委托回复
     */
    private String entrustLawyerAnswer;
    /**
     * 律师手机号码
     */
    private String entrustLawyerMobilePhone;
    /**
     * 律师座机
     */
    private String entrustLawyerFixedPhone;

	/**
	 * 评价时间,yyyy-MM-dd
	 */
	private String evaluateTime;
    /**
     * 评价状态: false:未评价、true：已评价
     */
	private boolean evaluateStatus;
	/**
	 *对律所评价： 1、不满意 2、比较满意 3、非常满意
	 */
	private String evaluateLawfirmScore;
	/**
	 *对律所评价内容
	 */
	private String evaluateLawfirmDescribe;
	/**
	 *对律师评价： 1、不满意 2、比较满意 3、非常满意
	 */
	private String evaluateLawyerScore;
	/**
	 *对律师评价内容
	 */
	private String evaluateLawyerDescribe;
	/**
	 *对评价是否发布 true:发布 false：不发布
	 */
	private boolean evaluatePublicStatic;

	public String getEntrustContent() {
		return entrustContent;
	}

	public void setEntrustContent(String entrustContent) {
		this.entrustContent = entrustContent;
	}

	public String getEntrustLawyerAnswer() {
		return entrustLawyerAnswer;
	}

	public void setEntrustLawyerAnswer(String entrustLawyerAnswer) {
		this.entrustLawyerAnswer = entrustLawyerAnswer;
	}

	public String getEntrustLawyerMobilePhone() {
		return entrustLawyerMobilePhone;
	}

	public void setEntrustLawyerMobilePhone(String entrustLawyerMobilePhone) {
		this.entrustLawyerMobilePhone = entrustLawyerMobilePhone;
	}

	public String getEntrustLawyerFixedPhone() {
		return entrustLawyerFixedPhone;
	}

	public void setEntrustLawyerFixedPhone(String entrustLawyerFixedPhone) {
		this.entrustLawyerFixedPhone = entrustLawyerFixedPhone;
	}

	public boolean isEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(boolean evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}
	public void setEvaluateStatus(String evaluateStatus) {
		this.evaluateStatus = JavaLangUtil.StrToBool( evaluateStatus,false);
	}

	public String getEvaluateLawyerDescribe() {
		return evaluateLawyerDescribe;
	}

	public void setEvaluateLawyerDescribe(String evaluateLawyerDescribe) {
		this.evaluateLawyerDescribe = evaluateLawyerDescribe;
	}

	public boolean isEvaluatePublicStatic() {
		return evaluatePublicStatic;
	}

	public void setEvaluatePublicStatic(boolean evaluatePublicStatic) {
		this.evaluatePublicStatic = evaluatePublicStatic;
	}

	public String getEvaluateTime() {
		return evaluateTime;
	}

	public void setEvaluateTime(String evaluateTime) {
		this.evaluateTime = evaluateTime;
	}



	public String getEvaluateLawfirmDescribe() {
		return evaluateLawfirmDescribe;
	}

	public void setEvaluateLawfirmDescribe(String evaluateLawfirmDescribe) {
		this.evaluateLawfirmDescribe = evaluateLawfirmDescribe;
	}

	public String getEvaluateLawfirmScore() {
		return evaluateLawfirmScore;
	}

	public void setEvaluateLawfirmScore(String evaluateLawfirmScore) {
		this.evaluateLawfirmScore = evaluateLawfirmScore;
	}

	public String getEvaluateLawyerScore() {
		return evaluateLawyerScore;
	}

	public void setEvaluateLawyerScore(String evaluateLawyerScore) {
		this.evaluateLawyerScore = evaluateLawyerScore;
	}
}
