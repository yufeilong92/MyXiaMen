package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.utils.JavaLangUtil;
import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师委托VO
 * @Package com.lawyee.apppublic.vo
 * @Description:用于列表
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawLawyerEntrustVO extends BaseVO {
    /**
     * 服务项目
     */
    private String service;
    /**
     * 服务项目名称
     */
    private String serviceName;

    /**
     * 办理状态:0：未办理 1：已办理
     */
    private int	entrustStatus;

    /**
     * 委托人
     */
    private String entrustPerson;

    /**
     * 委托人姓名
     */
    private String entrustPersonName;

    /**
     * 委托人头像
     */
    private String entrustPersonPhoto;

    /**
     * 委托编号
     */
    private String serialNumber;

    /**
     * 委托时间 yyyy-MM-dd HH:mm:ss
     */
    private String entrustTime;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getEntrutStatus() {
        return entrustStatus;
    }

    public void setEntrustStatus(int entrutsStatus) {
        this.entrustStatus = entrustStatus;
    }
    public void setEntrustStatus(String entrutsStatus) {
        if(JavaLangUtil.StrToBool( entrutsStatus,false)){
            this.entrustStatus =1;
        }else{
            this.entrustStatus =0;
        }
    }
    public String getEntrustPerson() {
        return entrustPerson;
    }

    public void setEntrustPerson(String entrustPerson) {
        this.entrustPerson = entrustPerson;
    }

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getEntrustTime() {
        return entrustTime;
    }

    public void setEntrustTime(String entrustTime) {
        this.entrustTime = entrustTime;
    }
}
