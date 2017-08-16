package com.lawyee.apppublic.vo;

import android.content.Context;

import com.lawyee.apppublic.config.ApplicationSet;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.BaseVO;

import static com.lawyee.apppublic.vo.BaseCommonDataVO.getNameWithOid;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 法援机构VO
 * @Package com.lawyee.apppublic.vo
 * @Description:用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidOrgVO extends BaseVO {

    private static final long serialVersionUID = -8657920436547865297L;
    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构电话
     */
    private String telephone;
    /**
     * 省份
     */
    private String province;// 省份
    /**
     * 省份名称
     */
    private String provinceName;// 省份
    /**
     * 市
     */
    private String city;// 市区
    /**
     * 市名称
     */
    private String cityName;// 市区
    /**
     * 区县
     */
    private String county;// 区域
    /**
     * 区县名称
     */
    private String countyName;// 区域
    /**
     * 机构地址
     */
    private String completeAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        if(StringUtil.isEmpty(provinceName)&&!StringUtil.isEmpty(province)) {
            provinceName = getNameWithOid(ApplicationSet.getInstance().getAreas(), province);
        }
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        if(StringUtil.isEmpty(cityName)&&!StringUtil.isEmpty(city))
            cityName = getNameWithOid(ApplicationSet.getInstance().getAreas(),city);
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyName() {
        if(StringUtil.isEmpty(countyName)&&!StringUtil.isEmpty(county))
            countyName = getNameWithOid(ApplicationSet.getInstance().getAreas(),county);
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


    public static String dataFileName(Context c) {
        return dataFileName(c,serialVersionUID);
    }

    public static String dataListFileName(Context c,String filterName) {
        return dataListFileName(c,serialVersionUID,filterName);
    }
    
}
