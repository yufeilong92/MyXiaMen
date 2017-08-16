package com.lawyee.apppublic.vo;

import net.lawyee.mobilelib.vo.BaseVO;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  登录openfire结果
 * @Package com.lawyee.apppublic.vo
 * @Description:    
 * @author:wuzhu
 * @date:   2017/7/4
 * @version
 * @verdescript   2017/7/4  wuzhu 初建
 * @Copyright: 2017/7/4 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LoginResult extends BaseVO {

    private UserVO mUser;
    private boolean mSuccess;
    private String mErrorMsg;

    public LoginResult(UserVO user, boolean success) {

        mUser = user;
        mSuccess = success;
    }

    public LoginResult(boolean success, String errorMsg) {

        mSuccess = success;
        mErrorMsg = errorMsg;
    }

    public boolean isSuccess() {

        return mSuccess;
    }

    public String getErrorMsg() {

        return mErrorMsg;
    }

    public UserVO getUser() {

        return mUser;
    }
}
