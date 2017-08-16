package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师/所奖励信息VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawRewardVO extends BaseVO {
    /**
     * 奖励名称
     */
    private String name;

    /**
     * 奖励年度
     */
    private String year;

    /**
     * 奖励级别
     */
    private String level;

    /**
     * 授奖单位
     */
    private String awardOrganization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAwardOrganization() {
        return awardOrganization;
    }

    public void setAwardOrganization(String awardOrganization) {
        this.awardOrganization = awardOrganization;
    }
}
