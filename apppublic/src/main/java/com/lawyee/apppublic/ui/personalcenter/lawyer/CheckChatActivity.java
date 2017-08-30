package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.SessionAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JacstService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.UIUtils;
import com.lawyee.apppublic.vo.ChatMessage;
import com.lawyee.apppublic.vo.ConsulationRecordVO;
import com.lawyee.apppublic.vo.GeolocationVO;
import com.lawyee.apppublic.widget.recycleView.LQRRecyclerView;

import net.lawyee.mobilelib.utils.T;
import net.lawyee.mobilelib.utils.TimeUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lawyee.apppublic.ui.lawyerService.SessionActivity.CSTR_EXTRA_SESSION_STR;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  查看聊天记录界面
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    注释
 * @author:lzh
 * @date:   2017/8/8
 * @version
 * @verdescript   2017/8/8  czq 初建
 * @Copyright: 2017/8/8 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class CheckChatActivity extends BaseActivity {

    private LQRRecyclerView mCvMessage;
    private Context mContext;
    private SessionAdapter mAdapter;
    private List<ChatMessage> mMessages = new ArrayList<>();//消息队列
    private ArrayList <ConsulationRecordVO> mConsulationRecordVOs=new ArrayList<>();
    private String mChatOid;
    /**
     * 进度条
     */
    private ProgressDialog mProgressDialog;
    //滚到最后一行
    private Runnable mCvMessageScrollToBottomTask = new Runnable() {
        @Override
        public void run() {
            mCvMessage.moveToPosition(mMessages.size() - 1);
        }
    };
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_check_chat);
        mContext = this;
        initView();
        mChatOid = getIntent().getStringExtra(CSTR_EXTRA_SESSION_STR);
        if (mChatOid==null) {
            finish();
            return;
        }
        setAdapter();
    }

    private void initView() {
        mCvMessage = (LQRRecyclerView) findViewById(R.id.cvMessage);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }















    public void setAdapter() {
        updateChat();



    }

    private void initMessage() {

        for(int i = 0; i <mConsulationRecordVOs.size() ; i++) {
            ChatMessage message = new ChatMessage();
            String str= null;
            try {
                str = URLDecoder.decode(mConsulationRecordVOs.get(i).getContent(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            message.setContent(str);
            if(mConsulationRecordVOs.get(i).getFrom().equals(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId())){
                message.setSend(true);
            }else{
                message.setSend(false);
            }
            message.setDate(TimeUtil.strToDate(mConsulationRecordVOs.get(i).getSendTime(),new Date()));
            message.setType(ChatMessage.CINT_MESSAGE_TYPE_NR);
        /*if(!isSend){
            message.setPhoto(mJalawLawyerVO.getPhoto());
        }*/
            mMessages.add(message);
        }
        mAdapter = new SessionAdapter(this, mMessages);
        mCvMessage.setAdapter(mAdapter);
    }

    private void updateChat() {
        if(getInProgess())
            return;
        setInProgess(true);
        JacstService service = new JacstService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getConsulationRecordList(mChatOid,new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()) {
                    T.showShort(mContext,content);
                    return;
                }
                ArrayList list = (ArrayList) values
                        .get(0);

                if (list != null && !list.isEmpty()) {
                    mConsulationRecordVOs=list;
                    initMessage();
                }else {
                        T.showShort(mContext,R.string.no_chat);
                      //  finish();
                }


            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
            }
        });
    }

    /**
     * 消息列表滚动至最后
     */
    private void cvScrollToBottom() {
        UIUtils.postTaskDelay(mCvMessageScrollToBottomTask, 100);

    }



    /**
     * 发送正常消息
     *
     * @param content 内容
     * @param isSend  接收或者发送的标识
     * @param ref     是否刷新界面
     */
    private void sendNor(String content, long time, boolean isSend, boolean ref) {
        ChatMessage message = new ChatMessage();
        message.setContent(content);
        message.setSend(isSend);
        message.setDate(time);
        message.setType(ChatMessage.CINT_MESSAGE_TYPE_NR);
        /*if(!isSend){
            message.setPhoto(mJalawLawyerVO.getPhoto());
        }*/
        mMessages.add(message);
        if (!ref)
            return;
        mAdapter.notifyDataSetChanged();
        cvScrollToBottom();
    }

    /**
     * 发送图片消息
     *
     * @param image  图片地址
     * @param isSend 接收或者发送的标识
     * @param ref    是否刷新界面
     */
    private void sendImagesMsg(String image, long time, boolean isSend, boolean ref) {
        ChatMessage message = new ChatMessage();
        message.setType(ChatMessage.CINT_MESSAGE_TYPE_IMAGE);
        message.setContent(image);
        message.setSend(isSend);
        message.setDate(time);
        /*if(!isSend){
            message.setPhoto(mJalawLawyerVO.getPhoto());
        }*/
        mMessages.add(message);
        if (!ref)
            return;
        mAdapter.notifyDataSetChanged();
        cvScrollToBottom();
    }

    /**
     * 发送地图消息
     *
     * @param isSend 接收或者发送的标识
     * @param ref    是否刷新界面
     */
    private void sendAddressMessage(GeolocationVO gvo, long time, boolean isSend, boolean ref) {
        if (gvo == null)
            return;
        ChatMessage message = new ChatMessage();
        message.setContent(gvo.getAddress());
        message.setSend(isSend);
        message.setDate(time);
        message.setType(ChatMessage.CINT_MESSAGE_TYPE_ADDRESS);
        message.setAddress(gvo.getMapAddress());
        message.setLatitude(gvo.getLat());
        message.setLongitude(gvo.getLng());
        /*if(!isSend){
            message.setPhoto(mJalawLawyerVO.getPhoto());
        }*/
        mMessages.add(message);
        if (!ref)
            return;
        mAdapter.notifyDataSetChanged();
        cvScrollToBottom();
    }









    /**
     * 聊天信息变更
     */
//    private void updateChatStatus() {
//        final String selection = ChatProvider.ChatConstants.USERID + "='" + ApplicationSet.getInstance().getOpenfireLoginId() + "' and " +
//                ChatProvider.ChatConstants.JID + " = '" + mChatOid + "' and " +
//                ChatProvider.ChatConstants._ID + " > " + mLastChatDBID;
//        Observable<Boolean> observable = Observable.create(
//                new ObservableOnSubscribe<Boolean>() {
//                    @Override
//                    public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
//                        //Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder
//                        Cursor cursor = getContentResolver().query(ChatProvider.CONTENT_URI, null, selection, null, ChatProvider.ChatConstants.DEFAULT_SORT_ORDER);
//                        if (cursor == null)
//                            return;
//                        int count = mMessages.size();
//                        while (cursor.moveToNext()) {
//                            mLastChatDBID = cursor.getInt(cursor.getColumnIndex(ChatProvider.ChatConstants._ID));
//                            int direction = cursor.getInt(cursor.getColumnIndex(ChatProvider.ChatConstants.DIRECTION));
//                            boolean issend = direction == ChatProvider.ChatConstants.OUTGOING;
//                            long ts = cursor.getLong(cursor.getColumnIndex(ChatProvider.ChatConstants.DATE));
//                            String content = cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.MESSAGE));
//                            setBusinessId(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.BUSINESSID)));
//                            GeolocationVO gvo = XMPPHelper.getMapMessageInfo(content);
//                            if (gvo != null) {
//                                sendAddressMessage(gvo, ts, issend, false);
//                                continue;
//                            }
//                            //TODO 图片及文件这部分后续需要完善
//                            FileMessageVO fvo = XMPPHelper.getFileMessageFileInfo(content);
//                            if (fvo != null && fvo.isImg()) {
//                                sendImagesMsg(fvo.getFilelocurl(), ts, issend, false);
//                                continue;
//                            }
//                            sendNor(content, ts, issend, false);
//                        }
//                        e.onNext(mAdapter != null && count != mMessages.size());
//                    }
//                }
//        );

//    }







}
