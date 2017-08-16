package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.utils.JavaLangUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 调解申请VO
 * @Package com.lawyee.apppublic.vo
 * @Description:用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedApplyVO extends BaseVO {
    private static final long serialVersionUID = -8909645999954170309L;


    /**
     * 调解编号
     */
    private String serialNo;

    /**
     * 姓名
     */
    private String applyName;

    /**
     * 申请时间 yyyy-MM-dd HH:mm:ss
     */
    private String applyTime;

    /**
     * 调解状态  -1 未受理  0 未审核， 1 受理 2 调解结束
     */
    private String status;

    /**
     * 是否参加媒体调解，false 不参加，true 参加
     */
    private boolean mediaFlag;

	/**
	 *调解机构是否受理:0：未受理 1：受理 -1：不受理
	 */
	private int orgAcceptFlag;
    /**
     * 调解是否成功：1：成功：-1：不成功 0：待调解
     */
    private int successFlag;
    /**
     *是否可以参加媒体调解:0：电视台未确认 1：可以参与 -1：不可以参与
     */
	private int mediaConfirm;

    /**
     * 来源-线下申请或网上申请
     */
    private String source;

    /**
     *  调解类型，专业调解”true” 一般调解”false”
     */
    private boolean tjType;

    /**
     * 媒体调解发起人的类型   1 当事人  2调解员 3电视台
     */
    private String mediaApplyType;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
    public boolean isMediaFlag() {
        return mediaFlag;
    }

    public void setMediaFlag(boolean mediaFlag) {
        this.mediaFlag = mediaFlag;
    }



    public void setMediaFlag(String mediaFlag) {
        this.mediaFlag = JavaLangUtil.StrToBool(mediaFlag,false);
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrgAcceptFlag() {
        return orgAcceptFlag;
    }

    public void setOrgAcceptFlag(int orgAcceptFlag) {
        this.orgAcceptFlag = orgAcceptFlag;
    }

    public void setOrgAcceptFlag(String orgAcceptFlag) {
        this.orgAcceptFlag = JavaLangUtil.StrToInteger(orgAcceptFlag,0);
    }

    public int getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(int successFlag) {
        this.successFlag = successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = JavaLangUtil.StrToInteger(successFlag,0);
    }

    public int getMediaConfirm() {
        return mediaConfirm;
    }

    public void setMediaConfirm(int mediaConfirm) {
        this.mediaConfirm = mediaConfirm;
    }

    public void setMediaConfirm(String mediaConfirm) {
        this.mediaConfirm = JavaLangUtil.StrToInteger(mediaConfirm,0);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isTjType() {
        return tjType;
    }

    public void setTjType(boolean tjType) {
        this.tjType = tjType;
    }

    public void setTjType(String tyjType) {
        this.tjType = JavaLangUtil.StrToBool(tyjType,false);
    }

    public String getMediaApplyType() {
        return mediaApplyType;
    }

    public void setMediaApplyType(String mediaApplyType) {
        this.mediaApplyType = mediaApplyType;
    }

    public static String dataListFileName(Context c, String filterName) {
        return dataListFileName(c,serialVersionUID,filterName);
    }

}
