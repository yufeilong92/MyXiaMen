package com.lawyee.apppublic.vo;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.vo
 * @Description: 法援申请页申请事件
 * @author: YFL
 * @date: 2017/6/8 14:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaaidApplyTwoSubmitEven {
    /**
     * 申请人信息
     */
     private JaaidApplyDetailVO jaaidApplyDetailVO;

    /**
     * 是否能下一步
     * @return
     */
    private boolean isNext;

    public boolean isNext() {
        return isNext;
    }

    public JaaidApplyDetailVO getJaaidApplyDetailVO() {
        return jaaidApplyDetailVO;
    }

    public JaaidApplyTwoSubmitEven(JaaidApplyDetailVO jaaidApplyDetailVO, Boolean isNext) {
        this.jaaidApplyDetailVO = jaaidApplyDetailVO;
        this.isNext=isNext;
    }
}
