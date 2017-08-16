package com.lawyee.apppublic.vo;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.vo
 * @Description: fragment 申请event
 * @author: YFL
 * @date: 2017/5/20 9:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaaidApplyEvent {
    /**
     * 要跳转的fragment
     */
    private int id;
    private boolean isCheck;

    public int getId() {
        return id;
    }

    public boolean getisCheck() {
        return isCheck;
    }


    public JaaidApplyEvent(int id, boolean isCheck) {

        this.id=id;
         this.isCheck=isCheck;
    }


}
