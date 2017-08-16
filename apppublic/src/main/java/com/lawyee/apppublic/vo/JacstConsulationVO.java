package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 咨询服务VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/8/3
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JacstConsulationVO extends BaseVO {

    private static final long serialVersionUID = -6573689902254109445L;
    /**
     * 咨询类型
     */
    private String consultType;
    /**
     *咨询类型名称
     */
    private String consultTypeName;
    /**
     *咨询时间 yyyy-MM-dd HH:mm:ss 
     */
    private String consultDate;
    /**
     *咨询人id
     */
    private String consultPersonId ;



    /**
     *咨询人
     */
    private String consultPerson;
    /**
     *被咨询人id
     */
    private String answerPersonId;
    /**
     *被咨询人
     */
    private String answerPerson;
    /**
     *被咨询人所在机构id
     */
    private String acceptUnit;
    /**
     *被咨询人所在机构名称
     */
    private String acceptUnitName;
    /**
     *咨询内容
     */
    private String consultCotent;
    /**
     *评价
     */
    private String evaluateScore;
    /**
     *咨询服务服务建议
     */
    private String evaluateDescribe;

    public String getConsultType() {
        return consultType;
    }

    public void setConsultType(String consultType) {
        this.consultType = consultType;
    }

    public String getConsultTypeName() {
        return consultTypeName;
    }

    public void setConsultTypeName(String consultTypeName) {
        this.consultTypeName = consultTypeName;
    }

    public String getConsultDate() {
        return consultDate;
    }

    public void setConsultDate(String consultDate) {
        this.consultDate = consultDate;
    }

    public String getConsultPersonId() {
        return consultPersonId;
    }

    public void setConsultPersonId(String consultPersonId) {
        this.consultPersonId = consultPersonId;
    }

    public String getConsultPerson() {
        return consultPerson;
    }

    public void setConsultPerson(String consultPerson) {
        this.consultPerson = consultPerson;
    }





    public String getAnswerPersonId() {
        return answerPersonId;
    }

    public void setAnswerPersonId(String answerPersonId) {
        this.answerPersonId = answerPersonId;
    }

    public String getAnswerPerson() {
        return answerPerson;
    }

    public void setAnswerPerson(String answerPerson) {
        this.answerPerson = answerPerson;
    }

    public String getAcceptUnit() {
        return acceptUnit;
    }

    public void setAcceptUnit(String acceptUnit) {
        this.acceptUnit = acceptUnit;
    }

    public String getAcceptUnitName() {
        return acceptUnitName;
    }

    public void setAcceptUnitName(String acceptUnitName) {
        this.acceptUnitName = acceptUnitName;
    }

    public String getConsultCotent() {
        return consultCotent;
    }

    public void setConsultCotent(String consultCotent) {
        this.consultCotent = consultCotent;
    }

    public String getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(String evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public String getEvaluateDescribe() {
        return evaluateDescribe;
    }

    public void setEvaluateDescribe(String evaluateDescribe) {
        this.evaluateDescribe = evaluateDescribe;
    }
}
