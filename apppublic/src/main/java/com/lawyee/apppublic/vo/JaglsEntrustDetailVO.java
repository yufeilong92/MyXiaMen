package com.lawyee.apppublic.vo;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 基层法律服务委托详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/7/31
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaglsEntrustDetailVO extends JaglsEntrustVO {
    private static final long serialVersionUID = 2454580309502620075L;

    /**
     * 委托人
     */
    private String entrustPerson;

    /**
     * 委托人姓名
     */
    private String entrustPersonName;

    /**
     * 委托人头像
     */
    private String entrustPersonPhoto;

    /**
     *委托内容
     */
    private String entrustContent;
    /**
     * 委托回复
     */
    private String entrustStaffAnswer;
    /**
     * 手机号码
     */
    private String entrustStaffMobilePhone;
    /**
     * 座机
     */
    private String entrustStaffFixedPhone;

    /**
     * 评价时间,yyyy-MM-dd
     */
    private String evaluateTime;
    /**
     *评价工作者服务,1不满意 2 满意 3 非常满意
     */
    private String evaluateStaffScore;
    /**
     *对工作者服务服务建议
     */
    private String evaluateStaffDescribe;
    /**
     *对服务所评价,1不满意 2 满意 3 非常满意
     */
    private String evaluateOrgScore;
    /**
     *对服务所评价建议
     */
    private String evaluateOrgDescribe;

    public String getEntrustPerson() {
        return entrustPerson;
    }

    public void setEntrustPerson(String entrustPerson) {
        this.entrustPerson = entrustPerson;
    }

    public String getEntrustPersonName() {
        return entrustPersonName;
    }

    public void setEntrustPersonName(String entrustPersonName) {
        this.entrustPersonName = entrustPersonName;
    }

    public String getEntrustPersonPhoto() {
        return entrustPersonPhoto;
    }

    public void setEntrustPersonPhoto(String entrustPersonPhoto) {
        this.entrustPersonPhoto = entrustPersonPhoto;
    }

    public String getEntrustContent() {
        return entrustContent;
    }

    public void setEntrustContent(String entrustContent) {
        this.entrustContent = entrustContent;
    }

    public String getEntrustStaffAnswer() {
        return entrustStaffAnswer;
    }

    public void setEntrustStaffAnswer(String entrustStaffAnswer) {
        this.entrustStaffAnswer = entrustStaffAnswer;
    }

    public String getEntrustStaffMobilePhone() {
        return entrustStaffMobilePhone;
    }

    public void setEntrustStaffMobilePhone(String entrustStaffMobilePhone) {
        this.entrustStaffMobilePhone = entrustStaffMobilePhone;
    }

    public String getEntrustStaffFixedPhone() {
        return entrustStaffFixedPhone;
    }

    public void setEntrustStaffFixedPhone(String entrustStaffFixedPhone) {
        this.entrustStaffFixedPhone = entrustStaffFixedPhone;
    }

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getEvaluateStaffScore() {
        return evaluateStaffScore;
    }

    public void setEvaluateStaffScore(String evaluateStaffScore) {
        this.evaluateStaffScore = evaluateStaffScore;
    }

    public String getEvaluateStaffDescribe() {
        return evaluateStaffDescribe;
    }

    public void setEvaluateStaffDescribe(String evaluateStaffDescribe) {
        this.evaluateStaffDescribe = evaluateStaffDescribe;
    }

    public String getEvaluateOrgScore() {
        return evaluateOrgScore;
    }

    public void setEvaluateOrgScore(String evaluateOrgScore) {
        this.evaluateOrgScore = evaluateOrgScore;
    }

    public String getEvaluateOrgDescribe() {
        return evaluateOrgDescribe;
    }

    public void setEvaluateOrgDescribe(String evaluateOrgDescribe) {
        this.evaluateOrgDescribe = evaluateOrgDescribe;
    }
}
