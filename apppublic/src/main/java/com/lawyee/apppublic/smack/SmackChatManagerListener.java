package com.lawyee.apppublic.smack;

import com.lawyee.apppublic.util.db.IMDBHelper;
import com.nostra13.universalimageloader.utils.L;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * Smack普通消息监听处理
 */
public class SmackChatManagerListener implements ChatManagerListener {
    private static final String PATTERN = "[a-zA-Z0-9_]+@";

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {

        chat.addMessageListener(new ChatMessageListener() {
            @Override
            public void processMessage(Chat chat, Message message) {
                //不会收到自己发送过来的消息
                L.d("SmackChatManagerListener",message.toString());


                IMDBHelper.getInstance().processMessage(message,true);
            }
        });
    }
}
