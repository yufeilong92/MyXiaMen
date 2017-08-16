package com.lawyee.apppublic.vo;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 基层法律服务所详细信息VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaglsOrgDetailVO extends JaglsOrgVO {


    private static final long serialVersionUID = 8175142998250093986L;
    /**
     * 主管机关id
     */
    private String justiceBureauId;

    /**
     * 主管机关名称
     */
    private String justiceBureauName;
    /**
     * 区域类型/行政级别
     */
    private String admLevel;
    /**
     * 成立时间，yyyy-MM-dd
     */
    private String setupDate;

    /**
     * 组织形式
     */
    private String organizationForms;
    /**
     * 执业工作人数
     */
    private String staffNumber;
    /**
     * 服务所负责人
     */
    private String director;
    /**
     * 服务所负责人中文名
     */
    private String directorName;
    /**
     * 本年度考核情况
     */
    private String currentAssessmentResult;

    /**
     * 服务所简介
     */
    private String introduction;
    /**
     * 地图信息-坐标
     */
    private String axis;
    /**
     *服务邮箱
     */
    private String email;

    /**
     *服务项目列表
     */
    private List<JaglsServiceVO> services;
    /**
     * 客户评价列表
     */
    private List<JaglsEvaluateVO> evaluates;

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

    public String getAdmLevel() {
        return admLevel;
    }

    public void setAdmLevel(String admLevel) {
        this.admLevel = admLevel;
    }

    public String getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(String setupDate) {
        this.setupDate = setupDate;
    }

    public String getOrganizationForms() {
        return organizationForms;
    }

    public void setOrganizationForms(String organizationForms) {
        this.organizationForms = organizationForms;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
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

    public String getAxis() {
        return axis;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
