package com.lawyee.apppublic.util.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.smack.IMNotificationManager;
import com.lawyee.apppublic.smack.SmackManager;
import com.lawyee.apppublic.smack.StatusMode;
import com.lawyee.apppublic.ui.lawyerService.SessionActivity;
import com.lawyee.apppublic.vo.ConsulationRecordVO;
import com.lawyee.apppublic.vo.UserVO;
import com.nostra13.universalimageloader.utils.L;

import net.lawyee.mobilelib.app.AppContext;
import net.lawyee.mobilelib.processes.AndroidProcesses;
import net.lawyee.mobilelib.utils.StringUtil;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 在线咨询数据操作工具类
 * @Package com.lawyee.apppublic.util.db
 * @Description:
 * @author:wuzhu
 * @date: 2017/7/13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class IMDBHelper {
    private static final String PATTERN = "[a-zA-Z0-9_]+@";
    private static volatile IMDBHelper mHelper;

    private ContentResolver mContentResolver;
    private IMDBHelper()
    {
        mContentResolver = AppContext.context().getContentResolver();
    }
    public static IMDBHelper getInstance() {
        if(mHelper == null) {
            synchronized (IMDBHelper.class) {
                if(mHelper == null) {
                    mHelper = new IMDBHelper();
                }
            }
        }
        return mHelper;
    }

    /**
     * 取离线消息
     * @param connection
     */
    public void offlieMessageProcess(XMPPTCPConnection connection)
    {
        OfflineMessageManager offlineManager = new OfflineMessageManager(connection);
        try {
            List<Message> messages =  offlineManager.getMessages();
            if(messages!=null||!messages.isEmpty())
            {
                for(int i=0;i<messages.size();i++)
                {
                    Message msg = messages.get(i);
                    processMessage(msg,i==messages.size()-1);
                }
            }
            offlineManager.deleteMessages();
        }catch (Exception ex)
        {
            L.e("IMDBHelper","offlieMessageProcess:"+ex.getLocalizedMessage());
        }
    }

    /**
     * 处理聊天消息
     * @param message
     * @param hint 是否进行提示
     */
    public void processMessage(Message message,boolean hint)
    {
        String from = message.getFrom();//消息发送人，格式:laohu@171.17.100.201/Smack

        String to = message.getTo();//消息接收人(当前登陆用户)，格式:laohu@171.17.100.201/Smack
        Matcher matcherFrom = Pattern.compile(PATTERN).matcher(from);
        Matcher matcherTo = Pattern.compile(PATTERN).matcher(to);
        if(!matcherFrom.find() ||! matcherTo.find())
        {
            L.e("SmackChatManagerListener","发送人格式不正确");

            return;
        }
        String fromUser = matcherFrom.group(0);
        fromUser = fromUser.substring(0, fromUser.length() - 1);//去掉@
        String toUser = matcherTo.group(0);
        toUser = toUser.substring(0, toUser.length() - 1);//去掉@

        String username = getName(SmackManager.getInstance().getChatJid(fromUser,""));
        if(StringUtil.isEmpty(username))
            username = fromUser;
        int readstatus = ChatProvider.ChatConstants.DS_NEW;

        //判断应用在前台且聊天窗口是否在最顶层，是就不处理
        if (AndroidProcesses.isMyProcessInTheForeground()&&
                ApplicationSet.getInstance().checkActivityIsForeground(SessionActivity.class))
        {
            //判断当前聊天对象是否当前
            Activity activity = ApplicationSet.getInstance().getForegroundActivity();
            if(activity instanceof SessionActivity)
            {
                SessionActivity sessionActivity = (SessionActivity)activity;
                if(fromUser.equals(sessionActivity.getChatOid()))
                    readstatus = ChatProvider.ChatConstants.DS_SENT_OR_READ;
            }

        }
        Log.e("czqmessage.getBody()1",message.getBody());
        String str1 = null;
        try {
            str1 = URLDecoder.decode(message.getBody(),"UTF-8");
            message.setBody(null);
            message.setBody(str1);
            Log.e("czqmessage.getBody()2",str1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e("czqmessage.getBody()3",message.getBody());
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if(message.getBody().equals("start_consult")&&!userVO.isPublicUser()&&userVO != null) {
            String str = "您好！请问有什么可以帮助您？";
            String mBusinessId = message.getBusinessId();
//            if (StringUtil.isEmpty(message.getBusinessId())) {
//                mBusinessId = StringUtil.getUUID();
//                IMBusinessIdVO.setNewBusinessId
//                        (ApplicationSet.getInstance().getApplicationContext(), mBusinessId, fromUser, userVO.getOpenfireLoginId());
//            }
            org.jivesoftware.smack.packet.Message m = new org.jivesoftware.smack.packet.Message();
            String str3= null;
            try {
                str3 = URLEncoder.encode(str,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            m.setBody(str3);
            m.setBusinessId(mBusinessId);
            Chat mChat = SmackManager.getInstance().createChat(SmackManager.getInstance().getChatJid(fromUser, ""));
            try {

                mChat.sendMessage(m);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }

                IMDBHelper.getInstance().addChatMessageToDB(ApplicationSet.getInstance().getOpenfireLoginId(),
                        ChatProvider.ChatConstants.OUTGOING,
                        fromUser, username, ChatProvider.ChatConstants.DS_SENT_OR_READ, System.currentTimeMillis(),
                        str, mBusinessId, ChatProvider.ChatConstants.BP_NEW);


        }else {
        //入库
        IMDBHelper.getInstance().addChatMessageToDB(toUser, ChatProvider.ChatConstants.INCOMING,
                fromUser, username, readstatus, System.currentTimeMillis(), message,
                ChatProvider.ChatConstants.BP_NEW);
        if(!hint||readstatus==ChatProvider.ChatConstants.DS_SENT_OR_READ)
            return;
        //提醒
        IMNotificationManager.getInstance().notifyClient(fromUser,username,
                message.getBody(),message.getBusinessId());
    }

}

    /**
     * 聊天信息入库
     * @param userid 所属用户id
     * @param direction       发送还是接收到消息,ChatConstants.OUTGOING ChatConstants.INCOMING
     * @param jid             发送给谁？或谁发送来的ID
     * @param fromUserName    发送给谁？或谁发送来的姓名
     * @param delivery_status 消息发送状态，@ChatConstants.DS_NEW DS_SENT_OR_READ DS_ACKED
     * @param ts              发送或接收时间
     * @param message         内容主体
     * @param bprocess 是否可以进行回复处理，即在对方已经结束会话时，要设置为不可以再回复及
     *                 在某个咨询人ID已经出现且有新的businessid时旧的咨询人ID记录要设置为不可回复
     *                 @ChatConstants.BP_NEW @ChatConstants.BP_OLD
     */
    public void addChatMessageToDB(String userid,int direction,String jid,String fromUserName,int delivery_status, long ts,
                                   Message message,int bprocess)  {

            addChatMessageToDB(userid,direction,jid,fromUserName,delivery_status,ts,message.getBody(),message.getBusinessId(),bprocess);

    }
    /**
     * 聊天信息入库
     * @param userid 所属用户id
     * @param direction       发送还是接收到消息,ChatConstants.OUTGOING ChatConstants.INCOMING
     * @param jid             发送给谁？或谁发送来的ID
     * @param fromUserName    发送给谁？或谁发送来的姓名
     * @param delivery_status 消息发送状态，@ChatConstants.DS_NEW DS_SENT_OR_READ DS_ACKED
     * @param ts              发送或接收时间
     * @param message         内容主体
     * @param businessid      业务ID
     * @param bprocess 是否可以进行回复处理，即在对方已经结束会话时，要设置为不可以再回复及
     *                 在某个咨询人ID已经出现且有新的businessid时旧的咨询人ID记录要设置为不可回复
     *                 @ChatConstants.BP_NEW @ChatConstants.BP_OLD
     */
    public void addChatMessageToDB(String userid,int direction,String jid,String fromUserName,int delivery_status, long ts,
                                   String message,String businessid,int bprocess)   {
        if(StringUtil.isEmpty(message))//空信息不入库
            return;
        ContentValues values = new ContentValues();
//        String str = null;
//        try {
//            str = URLDecoder.decode(message,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        values.put(ChatProvider.ChatConstants.USERID, userid);
        values.put(ChatProvider.ChatConstants.DIRECTION, direction);
        values.put(ChatProvider.ChatConstants.JID, jid);
        values.put(ChatProvider.ChatConstants.USERNAME, fromUserName);
        values.put(ChatProvider.ChatConstants.DELIVERY_STATUS, delivery_status);
        values.put(ChatProvider.ChatConstants.DATE, ts);
        values.put(ChatProvider.ChatConstants.MESSAGE, message);
        if(!StringUtil.isEmpty(businessid))
            values.put(ChatProvider.ChatConstants.BUSINESSID, businessid);
        values.put(ChatProvider.ChatConstants.BPROCESS, bprocess);
        mContentResolver.insert(ChatProvider.CONTENT_URI, values);
    }

    /**
     * 设置消息为已读取
     * @param userid
     * @param jid
     * @param businessId
     */
    public void updateChatMessageIsRead(String userid,String jid,String businessId)
    {
        ContentValues values = new ContentValues();
        values.put(ChatProvider.ChatConstants.DELIVERY_STATUS, ChatProvider.ChatConstants.DS_SENT_OR_READ);
        if (StringUtil.isEmpty(businessId))
            mContentResolver.update(ChatProvider.CONTENT_URI, values,
                    ChatProvider.ChatConstants.USERID + " = ? and "+ChatProvider.ChatConstants.JID + " = ?",
                    new String[]{userid,jid});
        else {
            mContentResolver.update(ChatProvider.CONTENT_URI, values,
                    ChatProvider.ChatConstants.USERID + " = ? and "+ChatProvider.ChatConstants.JID + " = ? and "+
                            ChatProvider.ChatConstants.BUSINESSID + " = ?",
                    new String[]{userid,jid,businessId});
        }
    }

    public void updateRosterEntryInDB(final RosterEntry entry) {
        final ContentValues values = getContentValuesForRosterEntry(entry);

        if (mContentResolver.update(RosterProvider.CONTENT_URI, values,
                RosterProvider.RosterConstants.JID + " = ?", new String[]{entry.getUser()}) == 0)
            addRosterEntryToDB(entry);
    }

    public void addRosterEntryToDB(final RosterEntry entry) {
        ContentValues values = getContentValuesForRosterEntry(entry);
        Uri uri = mContentResolver.insert(RosterProvider.CONTENT_URI, values);
        L.i("addRosterEntryToDB: Inserted " + uri);
    }

    public void addRosterEntrySToDB(final ContentValues[] cvs) {
        mContentResolver.bulkInsert(RosterProvider.CONTENT_URI, cvs);
    }

    public void addAllRosterToDB(Set<RosterEntry> entries)
    {
        if(entries==null||entries.isEmpty())
            return;
        ContentValues[] cvs = new ContentValues[entries.size()];
        int i = 0;
        for (RosterEntry rosterEntry:entries
                ) {
            cvs[i++] = IMDBHelper.getInstance().getContentValuesForRosterEntry(rosterEntry);
        }
        addRosterEntrySToDB(cvs);
    }

    public void deleteAllRosterEntryFromDB() {
        int count = mContentResolver.delete(RosterProvider.CONTENT_URI,"'1'=?",new String[]{"1"});
    }

    public void deleteRosterEntryFromDB(final String jabberID) {
        int count = mContentResolver.delete(RosterProvider.CONTENT_URI,
                RosterProvider.RosterConstants.JID + " = ?", new String[]{jabberID});
        L.i("deleteRosterEntryFromDB: Deleted " + count + " entries");
    }

    /**
     * 从好友列表中取好友名称
     * @param jid usid@servername
     * @return
     */
    public String getName(String jid)
    {
        Cursor cursor =  mContentResolver.query(RosterProvider.CONTENT_URI,
                new String[]{RosterProvider.RosterConstants.JID,RosterProvider.RosterConstants.ALIAS},
                RosterProvider.RosterConstants.JID+" = ?",
                new String[]{jid},null);
        if(cursor==null)
            return "";
        final int aliascol = cursor.getColumnIndexOrThrow(RosterProvider.RosterConstants.ALIAS);
        if(cursor.moveToNext())
            return cursor.getString(aliascol);
        return "";
    }
    /**
     * 查跟这个用户聊过天的好友 根据时间逆序
     * @param id usid@servername
     * @return
     */
    public List<String> getFriend(String id)
    {
        List list =new ArrayList<String>();
        Cursor cursor =  mContentResolver.query(ChatProvider.CONTENT_FRIEND_URI,
                new String[]{ChatProvider.ChatConstants.JID},
                ChatProvider.ChatConstants.USERID+" = ?",
                new String[]{id},ChatProvider.ChatConstants.DATE +" DESC");
        if(cursor==null)
            return list;
        while (cursor.moveToNext()){
            String jid=  cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.JID));
            list.add(jid);
        }
        return list;
    }
    /**
     * 查跟这个用户聊过天的好友 最新的一条消息
     * @param
     * @return
     */
    public ConsulationRecordVO getFristWithFriend(String id, String jid)
    {

        ConsulationRecordVO consulationRecordVO=new ConsulationRecordVO();
        Cursor cursor =  mContentResolver.query(ChatProvider.CONTENT_FRIEND_URI,
                new String[]{ChatProvider.ChatConstants.JID,
                        ChatProvider.ChatConstants.DATE,ChatProvider.ChatConstants.USERNAME,
                        ChatProvider.ChatConstants.MESSAGE},
                ChatProvider.ChatConstants.USERID+" = ? and "+ChatProvider.ChatConstants.JID+" = ?",
                new String[]{id,jid},ChatProvider.ChatConstants.DATE +" DESC");
        if(cursor==null)
            return consulationRecordVO;
        while (cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.USERNAME))==null){
                consulationRecordVO.setFriendName(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.JID)));
            }else{
                consulationRecordVO.setFriendName(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.USERNAME)));
            }
            consulationRecordVO.setFriendId(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.JID)));
            consulationRecordVO.setContent(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.MESSAGE)));
            consulationRecordVO.setSendTime(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.DATE)));
            return consulationRecordVO;
        }
        return consulationRecordVO;
    }
    /**
     * 查询未读消息数
     * @param
     * @return
     */
    public int getNoReadMessage(String id )
    {

        ConsulationRecordVO consulationRecordVO=new ConsulationRecordVO();
        Cursor cursor =  mContentResolver.query(ChatProvider.CONTENT_FRIEND_URI,
                new String[]{ChatProvider.ChatConstants.DATE},
                ChatProvider.ChatConstants.USERID+" = ? and "+ChatProvider.ChatConstants.DELIVERY_STATUS+" = ?",
                new String[]{id,"0"},null);
        if(cursor==null)
            return 0;
        return cursor.getCount();
    }
    /**
     * 查询与好友的未读消息数
     * @param
     * @return
     */
    public int getNoReadMessageWithFriend(String id,String jid )
    {

        ConsulationRecordVO consulationRecordVO=new ConsulationRecordVO();
        Cursor cursor =  mContentResolver.query(ChatProvider.CONTENT_FRIEND_URI,
                new String[]{ChatProvider.ChatConstants.DATE},
                ChatProvider.ChatConstants.USERID+" = ? and "+ChatProvider.ChatConstants.JID+" = ? and " +
                        ChatProvider.ChatConstants.DELIVERY_STATUS+" = ?",
                new String[]{id,jid,"0"},null);
        if(cursor==null)
            return 0;
        return cursor.getCount();
    }
    /**
     * 删除和这个好友的聊天记录
     * @param
     * @return
     */
    public void deleteChat(String id){
        mContentResolver.delete(ChatProvider.CONTENT_URI,ChatProvider.ChatConstants.JID+" = ?",new String[]{id});
    }
    private String getGroup(Collection<RosterGroup> groups) {
        if(groups==null||groups.isEmpty())
        {
            return "";
        }
        for (RosterGroup group : groups) {
            return group.getName();
        }
        return "";
    }

    private String getName(RosterEntry rosterEntry) {
        String name = rosterEntry.getName();
        if (name != null && name.length() > 0) {
            return name;
        }
        name = parseName(rosterEntry.getUser());
        if (name.length() > 0) {
            return name;
        }
        return rosterEntry.getUser();
    }

    public ContentValues getContentValuesForRosterEntry(final RosterEntry entry) {
        final ContentValues values = new ContentValues();

        values.put(RosterProvider.RosterConstants.JID, entry.getUser());
        values.put(RosterProvider.RosterConstants.ALIAS, getName(entry));
        values.put(RosterProvider.RosterConstants.USERID, ApplicationSet.getInstance().getOpenfireLoginId());

        Presence presence = SmackManager.getInstance().getPresence(entry.getUser());
        if(presence!=null) {
            values.put(RosterProvider.RosterConstants.STATUS_MODE, getStatusInt(presence));
            values.put(RosterProvider.RosterConstants.STATUS_MESSAGE, presence.getStatus());
        }
        values.put(RosterProvider.RosterConstants.GROUP, getGroup(entry.getGroups()));

        return values;
    }
    public StatusMode getStatus(Presence presence) {
        if(presence==null)
            return StatusMode.offline;
        if (presence.getType() == Presence.Type.available) {
            if (presence.getMode() != null) {
                return StatusMode.valueOf(presence.getMode().name());
            }
            return StatusMode.available;
        }
        return StatusMode.offline;
    }

    public int getStatusInt(final Presence presence) {
        return getStatus(presence).ordinal();
    }

    public static String parseName(String XMPPAddress) {
        if (XMPPAddress == null) {
            return null;
        }
        int atIndex = XMPPAddress.lastIndexOf("@");
        if (atIndex <= 0) {
            return "";
        }
        else {
            return XMPPAddress.substring(0, atIndex);
        }
    }


    @NonNull
    public static String getJabberID(String from) {
        if(StringUtil.isEmpty(from))
            return "";
        String[] res = from.split("/");
        return res[0].toLowerCase();
    }
}
