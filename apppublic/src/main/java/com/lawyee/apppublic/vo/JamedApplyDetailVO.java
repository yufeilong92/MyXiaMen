package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.utils.JavaLangUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 调解申请详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedApplyDetailVO extends JamedApplyVO {
    private static final long serialVersionUID = 2628576853391649534L;
    public static final String CSTR_CACHEFIlE_SUF= "jamedcache";
    /**
     * 	调解机构id
     */
    private String tjOrgId;
    /**
     * 	调解机构名称
     */
    private String tjOrgName;
    /**
     * 申请人性别
     */
    private String applyGender;
    /**
     *民族
     */
    private String applyNation;
    /**
     *年龄
     */
    private String applyAge;
    /**
     * 申请人联系方式
     */
    private String applyTelephone;
    /**
     * 申请人职务
     */
    private String applyJob;
    /**
     * 申请人住址
     */
    private String applyAddress;
    /**
     * 证件号码
     */
    private String applyIdCard;
    /**
     *与被申请人关系
     */
    private String relation;
    /**
     *被申请人姓名
     */
    private String beApplyName;
    /**
     *被申请人性别
     */
    private String beApplyGender;
    /**
     *被申请人民族
     */
    private String beApplyNation;
    /**
     *被申请人年龄
     */
    private String  beApplyAge;
    /**
     * 被申请人联系方式
     */
    private String beApplyTelephone;
    /**
     * 被申请人住址
     */
    private String beApplyAddress;
    /**
     * 纠纷案情简介
     */
    private String introduction;
    /**
     * 当事人申请事项
     */
    private String matter;
	/**
	 *媒体审批意见
	 */
	private String mediaOpinion;
	/**
	 *媒体审批时间
	 */
	private String mediaApplyTime;
	/**
	 *节目预告通知
	 */
	private String mediaTVGuide;
	/**
	 *节目链接地址
	 */
	private String mediaPlayUrl;
	/**
	 *节目状态:0：录制中、1未播、2已播
	 */
	private int mediaPlayStatus;
    /**
     * 调解机构审核意见
     */
    private String orgAcceptOpinion;
    /**
     * 调解机构审批时间
     */
    private String orgAcceptTime;
    /**
     * 不受理理由
     */
    private String noAccpectReason;
    /**
     * 调解结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime;
    /**
     * 是否司法确认, 1：是：-1：否 0：待确认
     */
    private String judconfirmFlag;
    /**
     *调解机构联系电话
     */
    private String tjOrgTelephone;
    /**
     *录制时间, yyyy-MM-dd HH:mm:ss
     */
    private String recordTime;
    /**
     *录制地点
     */
    private String recordAddress;
    /**
     *播出频道
     */
    private String playChannel;
    /**
     *播出时间,yyyy-MM-dd HH:mm:ss
     */
    private String playTime;
    /**
     *节目标题
     */
    private String programTitle;
    /**
     *是否挂网 0否 1是
     */
    private String netFlag;
    /**
     *媒体不受理原因
     */
    private String mediaNoAcceptReason;
    /**
     *最终不参与原因
     */
    private String applynoAcceptReason;
    /**
     *最终不参与其他说明
     */
    private String applyOpinion;
    /**
     *最终确认是否媒体调解       1是   -1否   0未确认
     */
    private String applyMediaConfirm;

    /**
     *录制信息提交时间,yyyy-MM-dd HH:mm:ss
     */
    private String recordSubmitTime;
    /**
     *播出计划提交时间,yyyy-MM-dd HH:mm:ss
     */
    private String playSubmitTime;

    /**
     *确认是否媒体调解提交时间,yyyy-MM-dd HH:mm:ss
     */
    private String applySubmitTime;
    /**
     *调解员发起媒体调解申请时间,yyyy-MM-dd HH:mm:ss
     */
    private String tjApplyMediaTime;
    /**
     *媒体发起媒体参与调解时间,yyyy-MM-dd HH:mm:ss
     */
    private String tvApplyMediaTime;
    /**
     *调解结束提交时间,yyyy-MM-dd HH:mm:ss
     */
    private String endSubmitTime;

    public String getTjOrgId() {
        return tjOrgId;
    }

    public void setTjOrgId(String tjOrgId) {
        this.tjOrgId = tjOrgId;
    }

    public String getTjOrgName() {
        return tjOrgName;
    }

    public void setTjOrgName(String tjOrgName) {
        this.tjOrgName = tjOrgName;
    }

    public String getApplyGender() {
        return applyGender;
    }

    public void setApplyGender(String applyGender) {
        this.applyGender = applyGender;
    }

    public String getApplyNation() {
        return applyNation;
    }

    public void setApplyNation(String applyNation) {
        this.applyNation = applyNation;
    }

    public String getApplyTelephone() {
        return applyTelephone;
    }

    public void setApplyTelephone(String applyTelephone) {
        this.applyTelephone = applyTelephone;
    }

    public String getApplyJob() {
        return applyJob;
    }

    public void setApplyJob(String applyJob) {
        this.applyJob = applyJob;
    }

    public String getApplyAddress() {
        return applyAddress;
    }

    public void setApplyAddress(String applyAddress) {
        this.applyAddress = applyAddress;
    }

    public String getApplyIdCard() {
        return applyIdCard;
    }

    public void setApplyIdCard(String applyIdCard) {
        this.applyIdCard = applyIdCard;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getBeApplyName() {
        return beApplyName;
    }

    public void setBeApplyName(String beApplyName) {
        this.beApplyName = beApplyName;
    }

    public String getBeApplyGender() {
        return beApplyGender;
    }

    public void setBeApplyGender(String beApplyGender) {
        this.beApplyGender = beApplyGender;
    }

    public String getBeApplyNation() {
        return beApplyNation;
    }

    public void setBeApplyNation(String beApplyNation) {
        this.beApplyNation = beApplyNation;
    }

    public String getBeApplyTelephone() {
        return beApplyTelephone;
    }

    public void setBeApplyTelephone(String beApplyTelephone) {
        this.beApplyTelephone = beApplyTelephone;
    }

    public String getBeApplyAddress() {
        return beApplyAddress;
    }

    public void setBeApplyAddress(String beApplyAddress) {
        this.beApplyAddress = beApplyAddress;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getMediaOpinion() {
        return mediaOpinion;
    }

    public void setMediaOpinion(String mediaOpinion) {
        this.mediaOpinion = mediaOpinion;
    }

    public String getMediaApplyTime() {
        return mediaApplyTime;
    }

    public void setMediaApplyTime(String mediaApplyTime) {
        this.mediaApplyTime = mediaApplyTime;
    }

    public String getMediaTVGuide() {
        return mediaTVGuide;
    }

    public void setMediaTVGuide(String mediaTVGuide) {
        this.mediaTVGuide = mediaTVGuide;
    }

    public String getMediaPlayUrl() {
        return mediaPlayUrl;
    }

    public void setMediaPlayUrl(String mediaPlayUrl) {
        this.mediaPlayUrl = mediaPlayUrl;
    }

    public int getMediaPlayStatus() {
        return mediaPlayStatus;
    }

    public void setMediaPlayStatus(int mediaPlayStatus) {
        this.mediaPlayStatus = mediaPlayStatus;
    }
    public void setMediaPlayStatus(String mediaPlayStatus) {
        this.mediaPlayStatus = JavaLangUtil.StrToInteger(mediaPlayStatus,0);
    }

    public String getOrgAcceptOpinion() {
        return orgAcceptOpinion;
    }

    public void setOrgAcceptOpinion(String orgAcceptOpinion) {
        this.orgAcceptOpinion = orgAcceptOpinion;
    }

    public String getNoAccpectReason() {
        return noAccpectReason;
    }

    public void setNoAccpectReason(String noAccpectReason) {
        this.noAccpectReason = noAccpectReason;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getJudconfirmFlag() {
        return judconfirmFlag;
    }

    public void setJudconfirmFlag(String judconfirmFlag) {
        this.judconfirmFlag = judconfirmFlag;
    }

    public String getOrgAcceptTime() {
        return orgAcceptTime;
    }

    public void setOrgAcceptTime(String orgAcceptTime) {
        this.orgAcceptTime = orgAcceptTime;
    }

    public String getApplyAge() {
        return applyAge;
    }

    public void setApplyAge(String applyAge) {
        this.applyAge = applyAge;
    }

    public String getBeApplyAge() {
        return beApplyAge;
    }

    public void setBeApplyAge(String beApplyAge) {
        this.beApplyAge = beApplyAge;
    }

    public String getTjOrgtelephone() {
        return tjOrgTelephone;
    }

    public void setTjOrgTelephone(String tjOrgTelephone) {
        this.tjOrgTelephone = tjOrgTelephone;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordAddress() {
        return recordAddress;
    }

    public void setRecordAddress(String recordAddress) {
        this.recordAddress = recordAddress;
    }

    public String getPlayChannel() {
        return playChannel;
    }

    public void setPlayChannel(String playChannel) {
        this.playChannel = playChannel;
    }

    public String getPlaytime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getNetFlag() {
        return netFlag;
    }

    public void setNetFlag(String netFlag) {
        this.netFlag = netFlag;
    }

    public String getMediaNoAcceptReason() {
        return mediaNoAcceptReason;
    }

    public void setMediaNoAcceptReason(String mediaNoAcceptReason) {
        this.mediaNoAcceptReason = mediaNoAcceptReason;
    }

    public String getApplynoAcceptReason() {
        return applynoAcceptReason;
    }

    public void setApplynoAcceptReason(String applynoAcceptReason) {
        this.applynoAcceptReason = applynoAcceptReason;
    }

    public String getApplyOpinion() {
        return applyOpinion;
    }

    public void setApplyOpinion(String applyOpinion) {
        this.applyOpinion = applyOpinion;
    }

    public String getApplyMediaConfirm() {
        return applyMediaConfirm;
    }

    public void setApplyMediaConfirm(String applyMediaConfirm) {
        this.applyMediaConfirm = applyMediaConfirm;
    }
    public String getRecordSubmitTime() {
        return recordSubmitTime;
    }

    public void setRecordSubmitTime(String recordSubmitTime) {
        this.recordSubmitTime = recordSubmitTime;
    }

    public String getPlaySubmitTime() {
        return playSubmitTime;
    }

    public void setPlaySubmitTime(String playSubmitTime) {
        this.playSubmitTime = playSubmitTime;
    }

    public String getApplySubmitTime() {
        return applySubmitTime;
    }

    public void setApplySubmitTime(String applySubmitTime) {
        this.applySubmitTime = applySubmitTime;
    }

    public String getTjApplyMediaTime() {
        return tjApplyMediaTime;
    }

    public void setTjApplyMediaTime(String tjApplyMediaTime) {
        this.tjApplyMediaTime = tjApplyMediaTime;
    }

    public String getTvApplyMediaTime() {
        return tvApplyMediaTime;
    }

    public void setTvApplyMediaTime(String tvApplyMediaTime) {
        this.tvApplyMediaTime = tvApplyMediaTime;
    }

    public String getEndSubmitTime() {
        return endSubmitTime;
    }

    public void setEndSubmitTime(String endSubmitTime) {
        this.endSubmitTime = endSubmitTime;
    }
    /**
     * 缓存输入内容文件名称
     * @param c
     * @return
     */
    public static String cacheDataFileName(Context c)
    {
        return BaseVO.dataFileName(c,serialVersionUID,CSTR_CACHEFIlE_SUF);
    }
}
