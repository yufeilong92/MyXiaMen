package com.lawyee.apppublic.vo;

import android.content.Context;

import com.lawyee.apppublic.config.Constants;

import net.lawyee.mobilelib.utils.SecurityUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.vo.BaseVO;

import java.util.List;

import static com.lawyee.apppublic.config.Constants.CINT_PASSWORD_LEN_MIN;

/**
 * 用户VO
 */
public class UserVO extends BaseVO {

    private static final long serialVersionUID = -7755982071141601864L;
    /**
     * 用户角色-公众人员=0
     */
    public static final String CSTR_USERROLE_PUBLIC="0";
    /**
     * 用户角色-机关工作人员=1
     */
    public static final String CSTR_USERROLE_OFFICEWORKER="1";
    /**
     * 用户角色-法援工作者=2
     */
    public static final String CSTR_USERROLE_JAAID="2";
    /**
     * 用户角色-律师=3
     */
    public static final String CSTR_USERROLE_JALAW="3";
    /**
     * 用户角色-鉴定人员=4
     */
    public static final String CSTR_USERROLE_JAAUTH="4";
    /**
     * 用户角色-公证人员=5
     */
    public static final String CSTR_USERROLE_JANOTA="5";
    /**
     * 用户角色-人民调解员=6
     */
    public static final String CSTR_USERROLE_JAMED="6";
    /**
     * 用户角色-新闻媒体工作人员=m
     */
    public static final String CSTR_USERROLE_MEDIAWORKER="m";
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 登录帐号
     */
    private String loginId;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像URL
     */
    private String photo;
    /**
     *真实姓名
     */
    private String realName;
    /*
     * 性别
     */
    private String gender;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * openfire登入id
     */
    private String openfireLoginId;
    /**
     * openfire登入密码
     */
    private String openfirePassword;

    /**
     *用户的身份
     *                 （公众人员=0、机关工作人员=1、法援工作者=2、律师=3、鉴定人员=4、公证人员=5、人民调解员=6、新闻媒体工作人员=m,
     *                 详细见UserVO中的定义CSTR_USERROLE_*
     */
    private String role;

    /**
     *专业领域
     */
    private List<JalawBusinessVO> business;

    /**
     * 所属机构Id
     */
    private String orgId;

    /**
     * 所属机构名称
     */
    private String orgName;

    /**
     * 是否记住密码
     */
    private boolean isRememblePwd;

    /**
     * 身份证号
     */
    private String idCard;

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
     * 具体地址
     */
    private String address;

    /**
     * 出生日期
     */
    private String birthday;

    public boolean isRememblePwd() {
        return isRememblePwd;
    }

    public void setRememblePwd(boolean rememblePwd) {
        isRememblePwd = rememblePwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return SecurityUtil.Decrypt(password,SecurityUtil.getLegalKey(String.valueOf(serialVersionUID)), Constants.CSTR_IVS);
    }

    public void setPassword(String password) {
        this.password = SecurityUtil.Encrypt(password,SecurityUtil.getLegalKey(String.valueOf(serialVersionUID)), Constants.CSTR_IVS);;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenfireLoginId() {
        if(StringUtil.isEmpty(openfireLoginId))
        {
            //公众端：登录帐号和密码都是手机号—loginId
            //服务端：登录帐号和密码都是用户的oid
            if(isPublicUser())
                openfireLoginId = getLoginId();
            else
                openfireLoginId = getOid();
        }
        return openfireLoginId;
    }

    public void setOpenfireLoginId(String openfireLoginId) {
        this.openfireLoginId = openfireLoginId;
    }

    public String getOpenfirePassword() {
        if(StringUtil.isEmpty(openfirePassword))
        {
            //公众端：登录帐号和密码都是手机号—loginId
            //服务端：登录帐号和密码都是用户的oid
            if(isPublicUser())
                openfirePassword = getLoginId();
            else
                openfirePassword = getOid();
        }
        return openfirePassword;
    }

    public void setOpenfirePassword(String openfirePassword) {
        this.openfirePassword = openfirePassword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<JalawBusinessVO> getBusiness() {
        return business;
    }

    public void setBusiness(List<JalawBusinessVO> business) {
        this.business = business;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
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
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 是否有效的密码
     * @param str
     * @return
     */
    public static boolean isEffPassword(String str)
    {
        if(StringUtil.isEmpty(str)||str.length()<CINT_PASSWORD_LEN_MIN)
            return false;
        return true;
    }

    public static String dataFileName(Context c) {
        return dataFileName(c,serialVersionUID);
    }

    /**
     * 是否是公众用户
     * @return
     */
    public boolean isPublicUser()
    {
        if(StringUtil.isEmpty(role))
            return false;
        return CSTR_USERROLE_PUBLIC.equals(role);
    }

}
