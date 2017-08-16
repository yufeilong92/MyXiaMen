package com.lawyee.apppublic.smack;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.exception.IMException;
import com.lawyee.apppublic.services.IMService;
import com.lawyee.apppublic.util.db.ChatProvider;
import com.lawyee.apppublic.util.db.ChatProvider.ChatConstants;

import net.lawyee.mobilelib.utils.JavaLangUtil;
import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.StringUtil;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmackImpl implements Smack ,ConnectionListener {
    private IMService mService;
    private XMPPTCPConnection mConnection;
    private ChatManager mchatManager;


    private final ContentResolver mContentResolver;


    public SmackImpl(IMService service) throws IMException
    {
        mService = service;
        mContentResolver = service.getContentResolver();
        SmackConfiguration.setDefaultPacketReplyTimeout(10000);
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost(mService.getString(R.string.url_openfire_host))//服务器IP地址
                //服务器端口
                .setPort(JavaLangUtil.StrToInteger(mService.getString(R.string.url_openfire_port),5222))
                //服务器名称
                .setServiceName(mService.getString(R.string.url_openfire_servername))
                //是否开启安全模式
                .setSecurityMode(XMPPTCPConnectionConfiguration.SecurityMode.disabled)
                //是否开启压缩
                .setCompressionEnabled(false)
                //开启调试模式
                .setDebuggerEnabled(true).build();
        ReconnectionManager reconnectionManager = ReconnectionManager.getInstanceFor(mConnection);
        reconnectionManager.enableAutomaticReconnection();//允许自动重连
        reconnectionManager.setFixedDelay(2);//重连间隔时间
        mConnection.addConnectionListener(this);//连接监听
        mConnection = new XMPPTCPConnection(config);

    }

    @Override
    public boolean login(String account, String password) throws IMException {
        if(!mConnection.isConnected())
        {
            try {
                mConnection.connect();
                mchatManager = ChatManager.getInstanceFor(mConnection);
                mchatManager.addChatListener(chatManagerListener);
            } catch (SmackException e) {
                e.printStackTrace();
                throw new IMException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                throw new IMException(e.getMessage());
            } catch (XMPPException e) {
                e.printStackTrace();
                throw new IMException(e.getMessage());
            }
            if (!mConnection.isConnected()) {
                throw new IMException("连接在线咨询服务器失败");
            }
            mConnection.addConnectionListener(this);
        }
        try {
            mConnection.login(account,password,mService.getString(R.string.url_openfire_ressource));
        } catch (XMPPException e) {
            e.printStackTrace();
            throw new IMException(e.getMessage());
        } catch (SmackException e) {
            e.printStackTrace();
            throw new IMException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IMException(e.getMessage());
        }
        return mConnection.isAuthenticated();
    }

    @Override
    public boolean logout() {
        L.d("unRegisterCallback()");
        if (mConnection.isConnected()) {
            new Thread() {
                public void run() {
                    mConnection.disconnect();
                }
            }.start();
        }
        //TODO 用户下线状态
        this.mService = null;
        return true;
    }

    @Override
    public boolean isAuthenticated() {
        if (mConnection != null) {
            return (mConnection.isConnected() && mConnection
                    .isAuthenticated());
        }
        return false;
    }

    @Override
    public void setStatusFromConfig() throws IMException {

    }

    @Override
    public void sendMessage(String user, String message, String businessId,
                            String consultType, String staffName, String staffId, String userName) throws IMException{
        Chat chat = mchatManager.createChat(user, new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {

            }
        });
        Message msg = new Message();
        msg.setBody(message);
        msg.setBusinessId(businessId);
        msg.setConsultType(consultType);
        if(isAuthenticated())
        {
            try {
                chat.sendMessage(msg);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            addChatMessageToDB(ChatConstants.OUTGOING, user, message,
                    ChatConstants.DS_SENT_OR_READ, System.currentTimeMillis(),
                    msg.getStaffId(), businessId, consultType, staffName, staffId, userName, ChatConstants.BP_NEW);
        }else
            throw  new IMException("未登录或无法连接服务器");
    }

    @Override
    public String sendFileMessageStart(String user, String filepath, String businessId, String
            consultType, String staffName, String staffId, String userName, int dstype) {
        return null;
    }

    @Override
    public void sendFileMessageWithUploadComplte(String id, String fileid) throws IMException {

    }

    @Override
    public void removeFileMessage(String id) {

    }

    @Override
    public String getNameForJID(String jid) {
        return jid;
    }

    //创建聊天对象管理器监听
    private ChatManagerListener chatManagerListener = new ChatManagerListener() {
        @Override
        public void chatCreated(Chat chat, boolean createdLocally) {
            chat.addMessageListener(new ChatMessageListener() {
                @Override
                public void processMessage(Chat chat, Message message) {
                    //不会收到自己发送过来的消息
                    L.i("接收到消息："+message.getBody());
                    //TODO 接收到消息Message之后进行消息展示处理，这个地方可以处理所有人的消息
                    L.i(SmackImpl.class, "BusinessId:" + message.getBusinessId());
                    processReciviceMessage(message,false);
                }
            });
        }
    };

    private static final String PATTERN = "[a-zA-Z0-9_]+@";

    /**
     * 接收到消息处理
     * @param message
     * @param offline
     */
    private void processReciviceMessage(Message message,boolean offline)
    {
        String chatMessage = message.getBody();
        String fromJID = message.getFrom();//消息发送人，格式:laohu@171.17.100.201/Smack

        String to = message.getTo();//消息接收人(当前登陆用户)，格式:laohu@171.17.100.201/Smack
        Matcher matcherFrom = Pattern.compile(PATTERN).matcher(fromJID);
        Matcher matcherTo = Pattern.compile(PATTERN).matcher(to);
        if(matcherFrom.find() && matcherTo.find()) {
            try {
                /*String fromUser = matcherFrom.group(0);
                fromUser = fromUser.substring(0, fromUser.length() - 1);//去掉@
                String toUser = matcherTo.group(0);
                toUser = toUser.substring(0, toUser.length() - 1);//去掉@*/
                long ts = System.currentTimeMillis();
                addChatMessageToDB(ChatConstants.INCOMING, fromJID,
                        chatMessage, ChatConstants.DS_NEW, ts,
                        message.getStaffId(), message.getBusinessId(), message.getConsultType(),
                        message.getStaffName(), message.getStaffId(), message.getUsername(), ChatConstants.BP_NEW);
                if(!offline)
                    mService.newMessage(fromJID, chatMessage, message.getBusinessId(), message.getConsultType(),
                            message.getUsername(), message.getStaffId(), message.getStaffName());
            } catch (Exception e) {
                L.e("发送的消息格式不正确");
            }
        } else {
            L.e("发送人格式不正确");
        }
    }



    /**********************数据存储相关 start***********************/
    /**
     * @param direction       发送还是接收到消息,ChatConstants.OUTGOING ChatConstants.INCOMING
     * @param JID             发送给谁？或谁发送来的ID
     * @param message         内容主体
     * @param delivery_status 消息发送状态，@ChatConstants.DS_NEW DS_SENT_OR_READ DS_ACKED
     * @param ts              发送或接收时间
     * @param packetID        消息ID
     */
    private void addChatMessageToDB(int direction, String JID, String message,
                                    int delivery_status, long ts, String packetID,
                                    String businessId, String consultType, String staffName,
                                    String staffId, String userName, int bprocess) {
        //将欢迎及结束语不记录在数据库
        if (mService.getString(R.string.content_welcome).equals(message) ||
                mService.getString(R.string.content_finish).equals(message))
            return;
        ContentValues values = new ContentValues();

        values.put(ChatConstants.DIRECTION, direction);
        values.put(ChatConstants.JID, JID);
        values.put(ChatConstants.MESSAGE, message);
        values.put(ChatConstants.DELIVERY_STATUS, delivery_status);
        values.put(ChatConstants.DATE, ts);
        values.put(ChatConstants.PACKET_ID, packetID);
        values.put(ChatConstants.BUSINESSID, businessId);
        values.put(ChatConstants.CONSULTTYPE, consultType);
        values.put(ChatConstants.STAFFNAME, staffName);
        values.put(ChatConstants.STAFFID, staffId);
        values.put(ChatConstants.USERNAME, userName);
        values.put(ChatConstants.BPROCESS, bprocess);
        values.put(ChatConstants.USERID, mService.getUserID());

        mContentResolver.insert(ChatProvider.CONTENT_URI, values);
        //TODO 待优化
        updateMessageBProcess(JID, businessId);
    }


    public void changeMessageDeliveryStatus(String packetID, int new_status) {
        ContentValues cv = new ContentValues();
        cv.put(ChatProvider.ChatConstants.DELIVERY_STATUS, new_status);
        Uri rowuri = Uri.parse("content://" + ChatProvider.AUTHORITY + "/"
                + ChatProvider.TABLE_NAME);
        mContentResolver.update(rowuri, cv, ChatConstants.PACKET_ID
                + " = ? AND " + ChatConstants.DIRECTION + " = "
                + ChatConstants.OUTGOING, new String[]{packetID});
    }
    /**
     * 修改数据库里可回复状态为可回复状态
     *
     * @param JID        发送给谁？或谁发送来的ID
     * @param businessId 业务ID，如果非空时，将所有JID的记录中非此业务ID设置为不可回复，如果为空则设置所有JID的记录为不可回复
     */
    private void updateMessageBProcessNew(String JID, String businessId) {
        if (StringUtil.isEmpty(JID)||StringUtil.isEmpty(businessId))
            return;
        ContentValues values = new ContentValues();
        values.put(ChatConstants.BPROCESS, ChatConstants.BP_NEW);

        mContentResolver.update(ChatProvider.CONTENT_URI, values,
                ChatConstants.JID + " = ? AND " + ChatConstants.BUSINESSID + " != ?", new String[]{JID, businessId});
    }
    /**
     * 修改数据库里可回复状态为不可回复状态
     *
     * @param JID        发送给谁？或谁发送来的ID
     * @param businessId 业务ID，如果非空时，将所有JID的记录中非此业务ID设置为不可回复，如果为空则设置所有JID的记录为不可回复
     */
    private void updateMessageBProcess(String JID, String businessId) {
        if (StringUtil.isEmpty(JID))
            return;
        deleteMessageWithUploadInfo(JID,businessId);
        ContentValues values = new ContentValues();
        values.put(ChatConstants.BPROCESS, ChatConstants.BP_OLD);
        if (StringUtil.isEmpty(businessId))
            mContentResolver.update(ChatProvider.CONTENT_URI, values, ChatConstants.JID + " = ?", new String[]{JID});
        else {
            mContentResolver.update(ChatProvider.CONTENT_URI, values,
                    ChatConstants.JID + " = ? AND " + ChatConstants.BUSINESSID + " != ?", new String[]{JID, businessId});
            updateMessageBProcessNew(JID,businessId);
        }
    }

    private void deleteMessageWithUploadInfo(String JID, String businessId)
    {
        if (StringUtil.isEmpty(JID))
            return;
        if(StringUtil.isEmpty(businessId))
            mContentResolver.delete(ChatProvider.CONTENT_URI,
                    ChatConstants.JID + " = ? AND ("+ChatConstants.DELIVERY_STATUS+"="+ChatConstants.DS_UPLOADFILE
                            +" OR "+ChatConstants.DELIVERY_STATUS+"="+ChatConstants.DS_UPLOADIMG+")",
                    new String[]{JID});
        else
            mContentResolver.delete(ChatProvider.CONTENT_URI,
                    ChatConstants.JID + " = ? AND " + ChatConstants.BUSINESSID +
                            " != ? AND ("+ChatConstants.DELIVERY_STATUS+"="+ChatConstants.DS_UPLOADFILE+
                            " OR "+ChatConstants.DELIVERY_STATUS+"="+ChatConstants.DS_UPLOADIMG+")", new String[]{JID,businessId});
    }

    /**
     * 删除包消息
     * @param pid
     */
    private boolean deleteMessageWithPID(String pid)
    {
        if (StringUtil.isEmpty(pid))
            return false;

        int count = mContentResolver.delete(ChatProvider.CONTENT_URI,
                ChatConstants.PACKET_ID + " = ?", new String[]{pid});
        return count!=0;
    }

    /**
     * 设置某个用户的数据中所有为不可回复状态
     *
     * @param JID
     */
    private void updateAllMessageBProcess(String JID) {
        updateMessageBProcess(JID, null);
    }

    /**********************数据存储相关 end***********************/

    /**********************XMPPConnection connection start***********************/

    @Override
    public void connected(XMPPConnection connection) {
        L.i("ConnectionListener", "connected");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        L.i("ConnectionListener", "authenticated");
    }

    @Override
    public void connectionClosed() {
        L.i("ConnectionListener", "connectionClosed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        L.i("ConnectionListener", "connectionClosedOnError" + e.getLocalizedMessage());
    }

    @Override
    public void reconnectionSuccessful() {
        L.i("ConnectionListener", "reconnectionSuccessful");
    }

    @Override
    public void reconnectingIn(int seconds) {
        L.i("ConnectionListener", "reconnectingIn:" + seconds);
    }

    @Override
    public void reconnectionFailed(Exception e) {
        L.i("ConnectionListener", "reconnectionFailed:" + e.getLocalizedMessage());
    }
    /**********************XMPPConnection connection end***********************/
}
