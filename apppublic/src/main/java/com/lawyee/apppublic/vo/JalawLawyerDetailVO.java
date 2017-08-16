package com.lawyee.apppublic.vo;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description: 用一句话描述该文件做什么
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawLawyerDetailVO extends JalawLawyerVO {
    /**
     *电话
     */
    private String mobile;
    /**
     *性别
     */
    private String gender;
    /**
     *执业证号
     */
    private String licenseNumber;
    /**
     *执业流水证号
     */
    private String practisingCertificateSerialNumber;

    /**
     *律师职称
     */
    private String lawyerTitle;
    /**
     *执业类别
     */
    private String practiceType;
    /**
     *年龄
     */
    private String birthday;
    /**
     * 政治面貌
     */
    private String politic;
    /**
     * 首次执业时间
     */
    private String firstPracticeDate;
    /**
     * 主管司法行政机关
     */
    private String justiceBureauId;
    /**
     * 主管司法行政机关名称
     */
    private String justiceBureauName;
    /**
     * 执业状态
     */
    private String practiceStatus;
    /**
     * 年度考核情况
     */
    private String annualAssessment;
    /**
     * 近3年奖励信息
     */
    private List<JalawRewardVO> rewards;
    /**
     * 近3年处罚信息
     */
    private List<JalawPunishVO> punishs;
    /**
     * 律师简介信息
     */
    private String introduction;
    /**
     *服务项目列表
     */
    private List<JalawLawyerServiceVO> services;
    /**
     * 客户评价列表
     */
    private List<JalawEvaluateVO> evaluates;
    /**
     * 律所地址
     */
    private String lawfirmAddress;
    /**
     * 律所服务电话
     */
    private String lawfirmTelephone;
    /**
     * 地图信息-坐标
     */
    private String lawfirmAxis;
    /**
     *律所服务邮箱
     */
    private String lawfirmEmail;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPractisingCertificateSerialNumber() {
        return practisingCertificateSerialNumber;
    }

    public void setPractisingCertificateSerialNumber(String practisingCertificateSerialNumber) {
        this.practisingCertificateSerialNumber = practisingCertificateSerialNumber;
    }

    public String getLawyerTitle() {
        return lawyerTitle;
    }

    public void setLawyerTitle(String lawyerTitle) {
        this.lawyerTitle = lawyerTitle;
    }

    public String getPracticeType() {
        return practiceType;
    }

    public void setPracticeType(String practiceType) {
        this.practiceType = practiceType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPolitic() {
        return politic;
    }

    public void setPolitic(String politic) {
        this.politic = politic;
    }

    public String getFirstPracticeDate() {
        return firstPracticeDate;
    }

    public void setFirstPracticeDate(String firstPracticeDate) {
        this.firstPracticeDate = firstPracticeDate;
    }

    public String getJusticeBureauId() {
        return justiceBureauId;
    }

    public void setJusticeBureauId(String justiceBureauId) {
        this.justiceBureauId = justiceBureauId;
    }

    public String getJusticeBureauName() {
        return justiceBureauName;
    }

    public void setJusticeBureauName(String justiceBureauName) {
        this.justiceBureauName = justiceBureauName;
    }

    public String getPracticeStatus() {
        return practiceStatus;
    }

    public void setPracticeStatus(String practiceStatus) {
        this.practiceStatus = practiceStatus;
    }

    public String getAnnualAssessment() {
        return annualAssessment;
    }

    public void setAnnualAssessment(String annualAssessment) {
        this.annualAssessment = annualAssessment;
    }

    public List<JalawRewardVO> getRewards() {
        return rewards;
    }

    public void setRewards(List<JalawRewardVO> rewards) {
        this.rewards = rewards;
    }

    public List<JalawPunishVO> getPunishs() {
        return punishs;
    }

    public void setPunishs(List<JalawPunishVO> punishs) {
        this.punishs = punishs;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<JalawLawyerServiceVO> getServices() {
        return services;
    }

    public void setServices(List<JalawLawyerServiceVO> services) {
        this.services = services;
    }

    public List<JalawEvaluateVO> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<JalawEvaluateVO> evaluates) {
        this.evaluates = evaluates;
    }

    public String getLawfirmAddress() {
        return lawfirmAddress;
    }

    public void setLawfirmAddress(String lawfirmAddress) {
        this.lawfirmAddress = lawfirmAddress;
    }

    public String getLawfirmTelephone() {
        return lawfirmTelephone;
    }

    public void setLawfirmTelephone(String lawfirmTelephone) {
        this.lawfirmTelephone = lawfirmTelephone;
    }

    public String getLawfirmAxis() {
        return lawfirmAxis;
    }

    public void setLawfirmAxis(String lawfirmAxis) {
        this.lawfirmAxis = lawfirmAxis;
    }

    public String getLawfirmEmail() {
        return lawfirmEmail;
    }

    public void setLawfirmEmail(String lawfirmEmail) {
        this.lawfirmEmail = lawfirmEmail;
    }
}
