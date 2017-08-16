package com.lawyee.apppublic.smack;


import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterListener;

import java.util.Collection;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  Smack全局监听器管理
 * @Package com.lawyee.apppublic.smack
 * @Description:
 * @author:wuzhu
 * @date:   2017/7/4
 * @version
 * @verdescript   2017/7/4  wuzhu 初建
 * @Copyright: 2017/7/4 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SmackListenerManager {
    private static volatile SmackListenerManager sSmackListenerManager;
    /**
     * 单聊消息监听管理器
     */
    private SmackChatManagerListener mChatManagerListener;
    private SmackRosterManagerListener mRostermanagerListener;

    private SmackListenerManager() {

        mChatManagerListener = new SmackChatManagerListener();
        mRostermanagerListener = new SmackRosterManagerListener();
    }

    public static SmackListenerManager getInstance() {
        if(sSmackListenerManager == null) {
            synchronized (SmackListenerManager.class) {
                if(sSmackListenerManager == null) {
                    sSmackListenerManager = new SmackListenerManager();
                }
            }
        }
        return sSmackListenerManager;
    }

    public void addGlobalListener() {
        addMessageListener();
        addRosterListener();
    }

    private void addRosterListener()
    {
        if(SmackManager.getInstance().getRosterManager()!=null)
            SmackManager.getInstance().getRosterManager().addRosterListener(mRostermanagerListener);
    }

    /**
     * 添加单聊消息全局监听
     */
    private void addMessageListener() {
        if(SmackManager.getInstance().getChatManager()!=null)
            SmackManager.getInstance().getChatManager().addChatListener(mChatManagerListener);
    }

    public void destroy() {
        if(SmackManager.getInstance().getChatManager()!=null)
            SmackManager.getInstance().getChatManager().removeChatListener(mChatManagerListener);
        mChatManagerListener = null;
        if(SmackManager.getInstance().getRosterManager()!=null)
            SmackManager.getInstance().getRosterManager().removeRosterListener(mRostermanagerListener);
        mRostermanagerListener = null;
    }
}
