package com.lawyee.apppublic.vo;

import android.content.Context;

import net.lawyee.mobilelib.vo.BaseVO;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律所VO
 * @Package com.lawyee.apppublic.vo
 * @Description:用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JalawLawfirmVO extends BaseVO {
    private static final long serialVersionUID = 1038689014737445055L;
    //律所ID、律所图片、律所名称、律所电话、律所专业领域信息
    /**
     * 律所中文全称
     */
    private String name;
    /**
     * 律所图片
     */
    private String photo;
    /**
     * 律所电话
     */
    private String telephone;
    /**
     * 律所专业领域信息
     */
    private List<JalawBusinessVO> business;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<JalawBusinessVO> getBusiness() {
        return business;
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

}
