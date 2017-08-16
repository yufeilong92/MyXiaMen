package com.lawyee.apppublic.vo;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 基层法律工作者详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/7/27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaglsStaffDetailVO extends JaglsStaffVO {
    private static final long serialVersionUID = -7813168995926130225L;

    /**
     *邮箱
     */
    private String email;
    /**
     *执业证号
     */
    private String licenseNumber;
    /**
     *主管机关
     */
    private String justiceBureauId;

    /**
     *主管机关名称
     */
    private String justiceBureauName;



    /**
     * 执业区域
     */
    private String practiceArea;
    /**
     *政治面貌
     */
    private String politic;
    /**
     *发证时间
     */
    private String firstPracticeDate;
    /**
     * 手机电话
     */
    private String mobile;
    /**
     * 性别
     */
    private String gender;
    /**
     * 学历
     */
    private String education;
    /**
     * 民族
     */
    private String nation;
    /**
     * 本年度考核结果
     */
    private String currentAssessmentResult;
    /**
     * 简介信息
     */
    private String introduction;
    /**
     *服务项目列表
     */
    private List<JaglsServiceVO> services;
    /**
     * 客户评价列表
     */
    private List<JaglsEvaluateVO> evaluates;
    /**
     * 地图信息-坐标
     */
    private String orgAxis;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCurrentAssessmentResult() {
        return currentAssessmentResult;
    }

    public void setCurrentAssessmentResult(String currentAssessmentResult) {
        this.currentAssessmentResult = currentAssessmentResult;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<JaglsServiceVO> getServices() {
        return services;
    }

    public void setServices(List<JaglsServiceVO> services) {
        this.services = services;
    }

    public List<JaglsEvaluateVO> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<JaglsEvaluateVO> evaluates) {
        this.evaluates = evaluates;
    }

    public String getOrgAxis() {
        return orgAxis;
    }

    public void setOrgAxis(String orgAxis) {
        this.orgAxis = orgAxis;
    }
    public String getPracticeArea() {
        return practiceArea;
    }

    public void setPracticeArea(String practiceArea) {
        this.practiceArea = practiceArea;
    }
}
