package com.lawyee.apppublic.exception;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 标题
 * @Package com.lawyee.apppublic.exception
 * @Description:
 * @author:wuzhu
 * @date: 2017/6/29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class IMException extends Exception {
    private static final long serialVersionUID = 356340795288685244L;
    public IMException(String message) {
        super(message);
    }

    public IMException(String message, Throwable cause) {
        super(message, cause);
    }
}
