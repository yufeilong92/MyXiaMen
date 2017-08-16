package com.lawyee.apppublic.vo;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 鉴定业务详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaauthBusinessDetailVO extends JaauthBusinessVO {
    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idCard;

    /**
     *申请人行为能力：1、完全民事行为能力人 2、限制民事行为能力人 3、无民事行为能力人
     */
    private String applyUserCapacity;

    /**
     *代理人姓名
     */
    private String agentName;

    /**
     * 代理人证件号码
     */
    private String agentIdCard;

    /**
     *鉴定事项
     */
    private String identifyMatter;

    /**
     *案情简介
     */
    private String caseSynopsis;

    /**
     *鉴定结论信息
     */
    private String identifyVerdict;

    /**
     * 鉴定书编号
     */
    private String testimonialNo;

    /**
     * 材料清单
     */
    private String dataList;
}
