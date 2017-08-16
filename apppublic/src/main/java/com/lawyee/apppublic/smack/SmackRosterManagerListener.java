package com.lawyee.apppublic.smack;

import android.content.ContentValues;

import com.lawyee.apppublic.util.db.IMDBHelper;
import com.nostra13.universalimageloader.utils.L;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;

import java.util.Collection;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 好友变更Listener
 * @Package com.lawyee.apppublic.smack
 * @Description:
 * @author:wuzhu
 * @date: 2017/7/14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SmackRosterManagerListener implements RosterListener {

    @Override
    public void entriesAdded(Collection<String> entries) {
        L.i("entriesAdded(" + entries + ")");
        ContentValues[] cvs = new ContentValues[entries.size()];
        int i = 0;
        for (String entry : entries) {
            RosterEntry rosterEntry = SmackManager.getInstance().getFriend(entry);
            cvs[i++] = IMDBHelper.getInstance().getContentValuesForRosterEntry(rosterEntry);
        }
        IMDBHelper.getInstance().addRosterEntrySToDB(cvs);
    }

    @Override
    public void entriesUpdated(Collection<String> entries) {
        L.i("entriesUpdated(" + entries + ")");
        for (String entry : entries) {
            RosterEntry rosterEntry = SmackManager.getInstance().getFriend(entry);
            IMDBHelper.getInstance().updateRosterEntryInDB(rosterEntry);
        }
    }

    @Override
    public void entriesDeleted(Collection<String> entries) {
        L.i("entriesDeleted(" + entries + ")");
        for (String entry : entries) {
            IMDBHelper.getInstance().deleteRosterEntryFromDB(entry);
        }
    }

    @Override
    public void presenceChanged(Presence presence) {
        L.i("presenceChanged(" + presence.getFrom() + "): " + presence);
        String jabberID = IMDBHelper.getInstance().getJabberID(presence.getFrom());
        if(presence.getType().equals(Presence.Type.subscribe))
        {
            //收到添加请求
            Presence presencePacket2 = new Presence(Presence.Type.subscribed);
            presencePacket2.setTo(jabberID);
            try {
                SmackManager.getInstance().getConnection().sendStanza(presencePacket2);
            } catch (SmackException.NotConnectedException e) {
                L.e("SmackRosterManagerListener",e.getMessage());
            }
        }else if (presence.getType().equals(Presence.Type.subscribed))
        {
            //对方同意添加好友
        }else if (presence.getType().equals(Presence.Type.unsubscribe))
        {
            //对方拒绝添加好友，将你从好友列表移除！
        }else if (presence.getType().equals(Presence.Type.unavailable))
        {
            //对方下线
        }else if (presence.getType().equals(Presence.Type.available))
        {
            //对方上线
        }


        RosterEntry rosterEntry = SmackManager.getInstance().getFriend(jabberID);
        IMDBHelper.getInstance().updateRosterEntryInDB(rosterEntry);
         /*//增加对上下线处理
                if (!presence.isAvailable()&&isAuthenticated()) {
                    updateAllMessageBProcess(jabberID);
                }*/
    }
}
