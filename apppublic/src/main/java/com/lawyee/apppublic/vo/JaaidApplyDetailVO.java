package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 法援申请详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidApplyDetailVO extends JaaidApplyVO {
    private static final long serialVersionUID = 3211882252384130732L;
    public static final String CSTR_CASCHEDATAFILE  = "jaaidapplycache";
    /**
     * 性别
     */
    private String gender;
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 证件号码
     */
    private String idCard;
    /**
     * 民族
     */
    private String nation;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 户籍地
     */
    private String domicile;
    /**
     * 选择地区
     *
     */
    private String selectcity;
    /**
     * 住所地
     */
    private String address;
    /**
     * 工作单位
     */
    private String workUnit;
    /**
     * 代理人姓名
     */
    private String agentName;
    /**
     *代理人类型
     */
    private String agentType;
    /**
     * 代理身份证号
     */
    private String agentIdCard;
    /**
     * 案情及申请理由
     */
    private String caseDescription;
    /**
     * 对方当事人姓名
     */
    private String partiesName;
    /**
     * 对方当事人住所地
     */
    private String partiesLocal;
    /**
     * 申请人数
     */
    private String applyUserCount;
    /**
     * 受理机构Id
     */
    private String manageOrgId;
    /**
     * 受理机构名称
     */
    private String manageOrgName;
    /**
     *省
     */
    private String province;
    /**
     *市
     */
    private String city;
    /**
     *县
     */
    private String county;
    /**
     * 案件发生地
     */
    private String caseHappenLocal;
    /**
     * 办案机关所在地
     */
    private String handleOrgAddress;
    /**
     * 指派律所
     */
    private String assignOrg;
    /**
     * 指派律所名称
     */
    private String assignOrgName;
    /**
     * 指派律师
     */
    private String assignLawyer;
    /**
     * 指派律师名称
     */
    private String assignLawyerName;

    /**
     * 指派律师电话
     */
    private String assignTel;
    /**
     * 指派时间 yyyy-MM-dd HH:mm:ss
     */
    private String assignTime;
    /**
     * 审核时间 yyyy-MM-dd HH:mm:ss
     */
    private String auditTime;
    /**
     * 审核意见
     */
	private String auditOpinion;

    /**
     * 材料列表
     */
    private List<AttachmentVO> attachments;
    public String getSelectcity() {
        return selectcity;
    }

    public void setSelectcity(String selectcity) {
        this.selectcity = selectcity;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getAgentIdCard() {
        return agentIdCard;
    }

    public void setAgentIdCard(String agentIdCard) {
        this.agentIdCard = agentIdCard;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getPartiesName() {
        return partiesName;
    }

    public void setPartiesName(String partiesName) {
        this.partiesName = partiesName;
    }

    public String getPartiesLocal() {
        return partiesLocal;
    }

    public void setPartiesLocal(String partiesLocal) {
        this.partiesLocal = partiesLocal;
    }

    public String getApplyUserCount() {
        return applyUserCount;
    }

    public void setApplyUserCount(String applyUserCount) {
        this.applyUserCount = applyUserCount;
    }

    public String getManageOrgId() {
        return manageOrgId;
    }

    public void setManageOrgId(String manageOrgId) {
        this.manageOrgId = manageOrgId;
    }

    public String getManageOrgName() {
        return manageOrgName;
    }

    public void setManageOrgName(String manageOrgName) {
        this.manageOrgName = manageOrgName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCaseHappenLocal() {
        return caseHappenLocal;
    }

    public void setCaseHappenLocal(String caseHappenLocal) {
        this.caseHappenLocal = caseHappenLocal;
    }

    public String getHandleOrgAddress() {
        return handleOrgAddress;
    }

    public void setHandleOrgAddress(String handleOrgAddress) {
        this.handleOrgAddress = handleOrgAddress;
    }

    public String getAssignOrg() {
        return assignOrg;
    }

    public void setAssignOrg(String assignOrg) {
        this.assignOrg = assignOrg;
    }

    public String getAssignOrgName() {
        return assignOrgName;
    }

    public void setAssignOrgName(String assignOrgName) {
        this.assignOrgName = assignOrgName;
    }

    public String getAssignLawyer() {
        return assignLawyer;
    }

    public void setAssignLawyer(String assignLawyer) {
        this.assignLawyer = assignLawyer;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAssignLawyerName() {
        return assignLawyerName;
    }

    public void setAssignLawyerName(String assignLawyerName) {
        this.assignLawyerName = assignLawyerName;
    }

    public String getAssignTel() {
        return assignTel;
    }

    public void setAssignTel(String assignTel) {
        this.assignTel = assignTel;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public List<AttachmentVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentVO> attachments) {
        this.attachments = attachments;
    }


    public static String dataFileName(Context c,String suf) {
        return dataFileName(c,serialVersionUID,suf);
    }
    /**
     * 缓存输入内容文件名称
     * @param c
     * @return
     */
    public static String cacheDataFileName(Context c)
    {
        return BaseVO.dataFileName(c,serialVersionUID,CSTR_CASCHEDATAFILE);
    }
}
