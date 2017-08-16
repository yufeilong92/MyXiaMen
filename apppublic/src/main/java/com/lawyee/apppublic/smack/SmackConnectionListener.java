package com.lawyee.apppublic.smack;

import com.nostra13.universalimageloader.utils.L;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  服务器连接监听
 * @Package com.lawyee.apppublic.smack
 * @Description:    
 * @author:wuzhu
 * @date:   2017/7/4
 * @version
 * @verdescript   2017/7/4  wuzhu 初建
 * @Copyright: 2017/7/4 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SmackConnectionListener implements ConnectionListener {

    @Override
    public void connected(XMPPConnection connection) {

        L.d("connection connected");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {

        L.d("connection authenticated");
    }

    @Override
    public void connectionClosed() {

        L.d("connection connectionClosed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {

        L.d("connectionClosedOnError");
    }

    @Override
    public void reconnectingIn(int seconds) {

        L.d("connection reconnectingIn " + seconds + " second");
    }

    @Override
    public void reconnectionFailed(Exception e) {

        L.d("reconnectionFailed");
    }

    @Override
    public void reconnectionSuccessful() {

        L.d("reconnectionSuccessful");
    }
}
