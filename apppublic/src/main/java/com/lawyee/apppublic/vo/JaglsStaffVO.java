package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 基层法律工作者VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/7/27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaglsStaffVO extends BaseVO {
    private static final long serialVersionUID = -2570771999111839219L;
    /**
     * 姓名
     */
    private String name;

    /**
     * 服务所id
     */
    private String jaglsOrganization;

    /**
     * 服务所名称
     */
    private String jaglsOrganizationName;

    /**
     * 头像id
     */
    private String photo;

    /**
     * 省
     */
    private String province;

    /**
     *市
     */
    private String city;

    /**
     * 区
     */
    private String county;



    /**
     *基层法律服务所所地址
     */
    private String address;

    /** 服务所联系电话 */
    private String orgTelephone;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJaglsOrganization() {
        return jaglsOrganization;
    }

    public void setJaglsOrganization(String jaglsOrganization) {
        this.jaglsOrganization = jaglsOrganization;
    }

    public String getJaglsOrganizationName() {
        return jaglsOrganizationName;
    }

    public void setJaglsOrganizationName(String jaglsOrganizationName) {
        this.jaglsOrganizationName = jaglsOrganizationName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getOrgTelephone() {
        return orgTelephone;
    }

    public void setOrgTelephone(String orgTelephone) {
        this.orgTelephone = orgTelephone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
