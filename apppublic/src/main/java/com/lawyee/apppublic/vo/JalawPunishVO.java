package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师/所惩罚信息VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawPunishVO extends BaseVO {

    /**
     * 被惩罚人所在机构/被惩罚机构名称
     */
    private String organization;
    /**
     * 惩罚类别
     */
    private String category;
    /**
     * 惩罚结果
     */
    private String result;
    /**
     * 惩罚开始时间 yyyy-MM-dd
     */
    private String punishTimeStart;
    /**
     * 惩罚结束时间 yyyy-MM-dd
     */
    private String punishTimeEnd;
    /**
     * 处理单位
     */
    private String punishOrganization;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPunishTimeStart() {
        return punishTimeStart;
    }

    public void setPunishTimeStart(String punishTimeStart) {
        this.punishTimeStart = punishTimeStart;
    }

    public String getPunishTimeEnd() {
        return punishTimeEnd;
    }

    public void setPunishTimeEnd(String punishTimeEnd) {
        this.punishTimeEnd = punishTimeEnd;
    }

    public String getPunishOrganization() {
        return punishOrganization;
    }

    public void setPunishOrganization(String punishOrganization) {
        this.punishOrganization = punishOrganization;
    }
}
