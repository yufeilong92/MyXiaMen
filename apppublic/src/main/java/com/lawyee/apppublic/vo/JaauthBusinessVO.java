package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 鉴定业务VO
 * @Package com.lawyee.apppublic.vo
 * @Description:用于列表项
 * @author:wuzhu
 * @date: 2017/5/16
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaauthBusinessVO extends BaseVO {
    /**
     * 鉴定类型 1、个人申请 2、机构委托
     */
    private String identifyType;

    /**
     * 申请人姓名
     */
    private String applyUser;

    /**
     * 委托人单位
     */
    private String trustUnit;

    /**
     * 鉴定完成时间
     */
    private String identifyCompleteTime;
}
