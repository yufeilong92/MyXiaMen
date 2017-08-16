package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律所(师）评价VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawEvaluateVO extends BaseVO {
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
     * 律师评价内容
     */
    private String evaluateLawyerDescribe;
    /**
     * 律所评价内容
     */
    private String evaluateLawfirmDescribe;

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

    public String getEvaluateLawyerDescribe() {
        return evaluateLawyerDescribe;
    }

    public void setEvaluateLawyerDescribe(String evaluateLawyerDescribe) {
        this.evaluateLawyerDescribe = evaluateLawyerDescribe;
    }

    public String getEvaluateLawfirmDescribe() {
        return evaluateLawfirmDescribe;
    }

    public void setEvaluateLawfirmDescribe(String evaluateLawfirmDescribe) {
        this.evaluateLawfirmDescribe = evaluateLawfirmDescribe;
    }
}
