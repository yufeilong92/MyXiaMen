package com.lawyee.apppublic.vo;

import android.content.Context;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 法援机构详细信息VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JaaidOrgDetailVO extends JaaidOrgVO {
    private static final long serialVersionUID = -6966697835966706915L;
    /**
     * 主管机构id
     */
    private String justiceBureauId;
    /**
     * 主管机构名称
     */
    private String justiceBureauName;

    /**
     * 上级机构id
     */
    private String parentId;

    /**
     * 上级机构名称
     */
    private String parentName;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 鉴定机构位置的经纬度
     */
    private String axis;

    public String getJusticeBureauId() {
        return justiceBureauId;
    }

    public void setJusticeBureauId(String justiceBureauId) {
        this.justiceBureauId = justiceBureauId;
    }

    public String getJusticeBureauName() {
        return justiceBureauName;
    }

    public void setJusticeBureauName(String justiceBureauName) {
        this.justiceBureauName = justiceBureauName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAxis() {
        return axis;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }
    public static String dataFileName(Context c, String filename) {
        return dataFileName(c,serialVersionUID,filename);
    }
}
