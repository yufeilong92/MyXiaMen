package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 律师/所专业领域VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JalawBusinessVO extends BaseVO {
    /**
     * 专业领域id
     */
    private String business;
    
    /**
     * 专业领域名称
     */
    private String businessName;

    /**
     * 专业领域描述
     */
    private String introduction;

    public String getBusiness() {
        return business;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
