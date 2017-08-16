package com.lawyee.apppublic.vo;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 公证业务详情VO
 * @Package com.lawyee.apppublic.vo
 * @Description:
 * @author:wuzhu
 * @date: 2017/5/17
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JanotaBusinessDetailVO extends JanotaBusinessVO {

    /**
     * 证件类型
     */
    private String credentialsType;

    /**
     * 证件号码
     */
    private String credentialsId;

    /**
     *公证事项
     */
    private String notarizationMatter;

    /**
     * 是否存在虚假证明
     */
    private String falseVerification;

    /**
     *公证书编号
     */
    private String notarizationNo;

    /**
     *其他说明
     */
    private String otherExplication;

    /**
     * 材料清单
     */
    private String materialList;
}
