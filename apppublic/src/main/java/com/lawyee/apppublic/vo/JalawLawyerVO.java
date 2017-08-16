package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师VO
 * @Package com.lawyee.apppublic.vo
 * @Description: 用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JalawLawyerVO extends BaseVO {
    private static final long serialVersionUID = 4493800268958793990L;
    /**
     * 律师姓名
     */
    private String name;

    /**
     * 律师所属律所id
     */
    private String lawfirm;

    /**
     * 律师所属律所名称
     */
    private String lawfirmName;

    /**
     * 头像URL
     */
    private String photo;

    /**
     *市
     */
    private String city;

    /**
     * 区
     */
    private String county;

    /** 专业领域 */
    private List<JalawBusinessVO> business;

    public String getName() {
        return name;
    }

    public String getLawfirm() {
        return lawfirm;
    }

    public String getLawfirmName() {
        return lawfirmName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public List<JalawBusinessVO> getBusiness() {
        return business;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLawfirm(String lawfirm) {
        this.lawfirm = lawfirm;
    }

    public void setLawfirmName(String lawfirmName) {
        this.lawfirmName = lawfirmName;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setBusiness(List<JalawBusinessVO> business) {
        this.business = business;
    }
    /**
     * 数据存储文件名(注意，这个是读取单个对象的名称，读取列表请使用dataListFileName)
     *
     * @return
     */
    public static String dataFileName(Context c, String suf) {

        return dataFileName(c,serialVersionUID,suf);
    }

    /**
     * 数据列表存储文件名
     *
     * @return
     */
    public static String dataListFileName(Context c,String suf) {
        return dataListFileName(c,serialVersionUID,suf);
    }

    @Override
    public String toString() {
        return "JalawLawyerVO{" +
                "name='" + name + '\'' +
                ", lawfirm='" + lawfirm + '\'' +
                ", lawfirmName='" + lawfirmName + '\'' +
                ", photo='" + photo + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", business=" + business +
                '}';
    }
}
