package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.utils.JavaLangUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 法援申请列表VO
 * @Package com.lawyee.apppublic.vo
 * @Description:用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JaaidApplyVO extends BaseVO {
    /**
     * 预申请流水号
     */
    private String serialNO;

    /**
     * 预申请人姓名
     */

    private String name;
    /**
     * 申请时间 yyyy-MM-dd HH:mm:ss
     */
    private String applyDate;
    /**
     * 审核状态:0：未审核/1：审核通过/-1：审核未通过
     */
    private int auditStatus;
    /**
     * 反馈状态:1：已反馈、0：未反馈
     */
    private int feedbackStatus;


    public String getSerialNO() {
        return serialNO;
    }

    public void setSerialNO(String serialNO) {
        this.serialNO = serialNO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }
    public void setAuditStatus(String  auditStatus) {

        this.auditStatus = JavaLangUtil.StrToInteger( auditStatus,0);
    }
    public int getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(int feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }
    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus =  JavaLangUtil.StrToInteger( feedbackStatus,0);
    }
}
