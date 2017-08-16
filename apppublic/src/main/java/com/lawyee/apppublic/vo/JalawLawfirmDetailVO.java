package com.lawyee.apppublic.vo;

import com.lawyee.apppublic.config.ApplicationSet;

import net.lawyee.mobilelib.utils.StringUtil;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律所详细信息VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawLawfirmDetailVO extends JalawLawfirmVO {
    /**
     * 所训
     */
    private String doctrine;
    /**
     * 执业许可证号
     */
    private String licenseNumber;

    /**
     * 主管机关id
     */
    private String justiceBureauId;

    /**
     * 主管机关名称
     */
    private String justiceBureauName;

    /**
     * 组织形式
     */
    private String organizationForms;
    /**
     * 成立时间-即发证日期
     */
    private String issueDate;
    /**
     * 负责人-律所主任
     */
    private String director;


    /**
     * 负责人-律所主任名称
     */
    private String directorName;
    /**
     * 律师人数
     */
    private String lawyerNumber;
    /**
     * 省份
     */
    private String province;// 省份
    /**
     * 省份名称
     */
    private String provinceName;// 省份
    /**
     * 市
     */
    private String city;// 市区
    /**
     * 市名称
     */
    private String cityName;// 市区
    /**
     * 区县
     */
    private String county;// 区域
    /**
     * 区县名称
     */
    private String countyName;// 区域
    /**
     * 街道地址信息
     */
    private String address;
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
     * 客户评价列表
     */
    private List<JalawEvaluateVO> evaluates;
    /**
     * 律所简介
     */
    private String introduction;
    /**
     * 地图信息-坐标
     */
    private String axis;
    /**
     *律所服务邮箱
     */
    private String email;

    public String getDoctrine() {
        return doctrine;
    }

    public void setDoctrine(String doctrine) {
        this.doctrine = doctrine;
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

    public String getOrganizationForms() {
        return organizationForms;
    }

    public void setOrganizationForms(String organizationForms) {
        this.organizationForms = organizationForms;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
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
    public String getLawyerNumber() {
        return lawyerNumber;
    }

    public void setLawyerNumber(String lawyerNumber) {
        this.lawyerNumber = lawyerNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        if(StringUtil.isEmpty(provinceName)&&!StringUtil.isEmpty(province))
            provinceName = AreaVO.getNameWithOid(ApplicationSet.getInstance().getAreas(),province);
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        if(StringUtil.isEmpty(cityName)&&!StringUtil.isEmpty(city))
            cityName = AreaVO.getNameWithOid(ApplicationSet.getInstance().getAreas(),city);
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyName() {
        if(StringUtil.isEmpty(countyName)&&!StringUtil.isEmpty(county))
            countyName = AreaVO.getNameWithOid(ApplicationSet.getInstance().getAreas(),county);
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAnnualAssessment() {
        return annualAssessment;
    }

    public void setAnnualAssessment(String annualAssessment) {
        this.annualAssessment = annualAssessment;
    }

    public List<JalawEvaluateVO> getEvaluates() {
        return evaluates;
    }

    public void setEvaluates(List<JalawEvaluateVO> evaluates) {
        this.evaluates = evaluates;
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
}
