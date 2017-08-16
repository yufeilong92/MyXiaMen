package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 基层法律服务评价VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaglsEvaluateVO extends BaseVO {
    /**
     * 评价人姓名
     */
    private String entrustPersonName;
    /**
     * 评价人头像
     */
    private String entrustPersonPhoto;
    /**
     * 评价日期 yyyy-MM-dd
     */
    private String evaluateTime;
    /**
     * 服务人员评价内容
     */
    private String evaluateStaffDescribe;

    /**
     * 服务所评价内容
     */
    private String evaluateOrgDescribe;

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

    public String getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getEvaluateOrgDescribe() {
        return evaluateOrgDescribe;
    }

    public void setEvaluateOrgDescribe(String evaluateOrgDescribe) {
        this.evaluateOrgDescribe = evaluateOrgDescribe;
    }

    public String getEvaluateStaffDescribe() {
        return evaluateStaffDescribe;
    }

    public void setEvaluateStaffDescribe(String evaluateStaffDescribe) {
        this.evaluateStaffDescribe = evaluateStaffDescribe;
    }
}
