package com.lawyee.apppublic.ui.lawyerService;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.SessionAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawUserService;
import com.lawyee.apppublic.exception.IMException;
import com.lawyee.apppublic.smack.SmackListenerManager;
import com.lawyee.apppublic.smack.SmackManager;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.lawyerService.map.SessionMapActivity;
import com.lawyee.apppublic.ui.personalcenter.lawyer.ConsultDealActivity;
import com.lawyee.apppublic.util.UIUtils;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.util.XMPPHelper;
import com.lawyee.apppublic.util.db.ChatProvider;
import com.lawyee.apppublic.util.db.IMDBHelper;
import com.lawyee.apppublic.util.db.RosterProvider;
import com.lawyee.apppublic.vo.ChatMessage;
import com.lawyee.apppublic.vo.FileMessageVO;
import com.lawyee.apppublic.vo.GeolocationVO;
import com.lawyee.apppublic.vo.IMBusinessIdVO;
import com.lawyee.apppublic.vo.LoginResult;
import com.lawyee.apppublic.vo.UserVO;
import com.lawyee.apppublic.widget.recycleView.LQRRecyclerView;

import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Presence;
import org.kymjs.chat.OnOperationListener;
import org.kymjs.chat.bean.Emojicon;
import org.kymjs.chat.bean.Faceicon;
import org.kymjs.chat.emoji.DisplayRules;
import org.kymjs.chat.widget.KJChatKeyboard;
import org.reactivestreams.Publisher;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.lawyee.apppublic.ui.personalcenter.lawyer.ConsultDealActivity.CONSULTBUSINESS;
import static com.lawyee.apppublic.ui.personalcenter.lawyer.ConsultDealActivity.CONSULTPERSON;
import static com.lawyee.apppublic.ui.personalcenter.lawyer.ConsultDealActivity.CONSULTPERSONID;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @Title: 标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description: 聊天界面
 * @author:czq
 * @date: 2017/6/29
 * @verdescript 2017/6/29  czq 初建
 * @Copyright: 2017/6/29 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SessionActivity extends BaseActivity implements  View.OnClickListener {
    /**
     * 传入参数-聊天对象，如果是律师，传入oid
     */
    public static final String CSTR_EXTRA_SESSION_STR = "session";
    public static final int IMAGE_PICKER = 100;
    public static final int MAP_PICKER = 10001;
    public static final int DEAL = 20001;
    private LQRRecyclerView mCvMessage;
    private KJChatKeyboard mBox;

    private Intent mIntent;
    private Context mContext;
    private SessionAdapter mAdapter;
    private List<ChatMessage> mMessages = new ArrayList<>();//消息队列
    private String mChatOid;
    /**
     * 业务Id
     */
    private String mBusinessId;
    /**
     * 聊天窗口对象
     */
    private Chat mChat;

    private ContentObserver mContactObserver = new ContactObserver();// 联系人数据监听，主要是监听对方在线状态
    private ChatObserver mChatObserver = new ChatObserver();

    private Handler mRosterHandler = new Handler();
    private Handler mChatHandler = new Handler();
    private long mLastChatDBID = 0l;
    /**
     * 进度条
     */
    private ProgressDialog mProgressDialog;
    /**
     * 对方是否在线
     */
    private boolean mIsOnline;
    //滚到最后一行
    private Runnable mCvMessageScrollToBottomTask = new Runnable() {
        @Override
        public void run() {
            mCvMessage.moveToPosition(mMessages.size() - 1);
        }
    };
    private View mViewNull;
    private TextView mTvFinish;
    private TextView mTvSave;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_session);
        mContext = this;
        initView();
        mChatOid = getIntent().getStringExtra(CSTR_EXTRA_SESSION_STR);
        if (!checkChat()) {
            finish();
            return;
        }
        initMessageInputToolBox();
        getBusinessId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mChatObserver);
        getContentResolver().unregisterContentObserver(mContactObserver);
    }

    private void initView() {
        mCvMessage = (LQRRecyclerView) findViewById(R.id.cvMessage);
        mBox=  (KJChatKeyboard) findViewById(R.id.chat_msg_input_box);
        mViewNull=findViewById(R.id.view_null);
        mTvFinish= (TextView) findViewById(R.id.tv_finish);
        mTvSave= (TextView) findViewById(R.id.tv_save);
        if (!ApplicationSet.getInstance().getUserVO().isPublicUser()) {
            mViewNull.setVisibility(View.GONE);
            mTvFinish.setVisibility(View.VISIBLE);
            mTvSave.setVisibility(View.VISIBLE);
        }
        mTvFinish.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
        //initEmotionKeyboard();
    }

    public boolean ismIsOnline() {
        return mIsOnline;
    }

    public void setmIsOnline(boolean isOnline, boolean isfirst) {
        if (!isfirst && this.mIsOnline == isOnline)
            return;
        this.mIsOnline = isOnline;
        mChangeTitleHandler.sendEmptyMessageDelayed(0, 200);
    }



    public void  getBusinessId() {
        if (getInProgess())
            return;
        setInProgess(true);
        String lawyerId;
        String publicid;
        if(ApplicationSet.getInstance().getUserVO().isPublicUser()){
            publicid=ApplicationSet.getInstance().getUserVO().getOpenfireLoginId();
            lawyerId=mChatOid;
        }else{
            lawyerId=ApplicationSet.getInstance().getUserVO().getOpenfireLoginId();
            publicid=mChatOid;
        }
        JalawUserService service = new JalawUserService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.getBusinessid(lawyerId, publicid,  new BaseJsonService.IResultInfoListener() {
                    @Override
                    public void onComplete(ArrayList<Object> values, String content) {
                        setInProgess(false);
                        if(values==null||values.isEmpty()||!(values.get(0) instanceof IMBusinessIdVO))
                        {
                            T.showLong(mContext,getString(R.string.get_error_noeffectdata));
                             finish();
                        }
                        mBusinessId= ((IMBusinessIdVO) values.get(0)).getBusinessId();
                    }

                    @Override
                    public void onError(String msg, String content) {
                        setInProgess(false);
                        T.showLong(mContext, msg);
                        finish();
                    }
                });

    }

    Handler mChangeTitleHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                if (ismIsOnline())
                    setTitle(getBaseTitle());
                else
                    setTitle(getBaseTitle() + "(离线)");
            }
        }
    };

    /**
     * @return
     */
    private boolean checkChat() {
        if (StringUtil.isEmpty(ApplicationSet.getInstance().getOpenfireLoginId())) {
            T.showLong(this, "请先进行登录");
            return false;
        }
        if (StringUtil.isEmpty(mChatOid)) {
            T.showLong(this, "未知的咨询对象");
            return false;
        }
        //未连接服务器，则进行连接
        if (!SmackManager.getInstance().isConnected() || !SmackManager.getInstance().isAuthenticated()) {
            mProgressDialog = ProgressDialog.show(this, "连接咨询服务器", "连接中，请稍候...", true, false);
            Flowable flowable = Flowable.just(ApplicationSet.getInstance().getUserVO())
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Function<UserVO, Publisher<LoginResult>>() {
                        @Override
                        public Publisher<LoginResult> apply(@NonNull UserVO userVO) throws Exception {
                            if (!SmackManager.getInstance().isConnected())
                                SmackManager.getInstance().initConnect();
                            LoginResult result = SmackManager.getInstance().login(userVO);
                            if (result.isSuccess()) {
                                IMDBHelper.getInstance().deleteAllRosterEntryFromDB();
                                IMDBHelper.getInstance().addAllRosterToDB(SmackManager.getInstance().getAllFriends());

                                IMDBHelper.getInstance().offlieMessageProcess(SmackManager.getInstance().getConnection());
                            }
                            return Flowable.just(result);
                        }
                    });
            flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<LoginResult>() {
                @Override
                public void accept(@NonNull LoginResult loginResult) throws Exception {
                    mProgressDialog.dismiss();
                    L.d(TAG, "LoginResult:" + loginResult.isSuccess() + " " + loginResult.getErrorMsg());
                    if (loginResult.isSuccess()) {
                        //普通消息接收监听
                        SmackListenerManager.getInstance().addGlobalListener();
                        initChatData();
                    } else {
                        new MaterialDialog.Builder(SessionActivity.this)
                                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                                .title("无法连接咨询服务器，请稍候再进行偿试")
                                .positiveText(R.string.dl_btn_ok)
                                .cancelable(false)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@android.support.annotation.NonNull MaterialDialog dialog, @android.support.annotation.NonNull DialogAction which) {
                                        SessionActivity.this.finish();
                                    }
                                }).show();
                    }
                }
            });
            return true;
        }
        return initChatData();
    }

    private boolean initChatData() {
        mChat = SmackManager.getInstance().createChat(SmackManager.getInstance().getChatJid(mChatOid, ""));
        if (mChat == null) {
            T.showLong(this, "无法创建聊天窗口对象");
            return false;
        }
        //如果不在好友列表或没有好友名称，则请求添加为好友
        String fname = IMDBHelper.getInstance().getName(SmackManager.getInstance().getChatJid(mChatOid, ""));
        if (StringUtil.isEmpty(fname) || !fname.equals(getBaseTitle())) {
            SmackManager.getInstance().addFriend(SmackManager.getInstance().getChatJid(mChatOid, ""), getBaseTitle(), "");
            //不是好友时要发一条固定格式消息
            sendMessage("start_consult", false);
        }
        checkOnline(true);
      //  initListener();
        setAdapter();
        //设置相关聊天消息已读
        IMDBHelper.getInstance().updateChatMessageIsRead(ApplicationSet.getInstance().getOpenfireLoginId(),mChatOid,"");
        getContentResolver().registerContentObserver(
                RosterProvider.CONTENT_URI, true, mContactObserver);// 开始监听联系人数据库


        getContentResolver().registerContentObserver(
                ChatProvider.CONTENT_URI, true, mChatObserver);// 开始聊天数据库
        return true;
    }

    /**
     * 检查对方是否在线
     */
    private void checkOnline(boolean isfirst) {
        Presence presence = SmackManager.getInstance().getPresence(SmackManager.getInstance().getChatJid(mChatOid, ""));
        if (presence == null)
            setmIsOnline(true, isfirst);
        else
            setmIsOnline(presence.getType() != Presence.Type.unavailable, isfirst);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mEtContent.clearFocus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAlbum:
                Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.ivLocation:
                checkLocal();
            case R.id.tv_finish:
                alertToFinish();
                break;
            case R.id.tv_save:
                Intent intent1=new Intent(mContext, ConsultDealActivity.class);
                intent1.putExtra(CONSULTBUSINESS,mBusinessId);
                intent1.putExtra(CONSULTPERSON,getBaseTitle());
                intent1.putExtra(CONSULTPERSONID,mChatOid);
                startActivityForResult(intent1, DEAL);
                break;
        }
    }


    public void setAdapter() {
        updateChatStatus();
        if (mAdapter == null) {
            mAdapter = new SessionAdapter(this, mMessages);
            mCvMessage.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 消息列表滚动至最后
     */
    private void cvScrollToBottom() {
        UIUtils.postTaskDelay(mCvMessageScrollToBottomTask, 100);

    }

    private void sendMessage(final String message, final boolean bsave) {
        if (!mIsOnline) {
            T.showLong(this, getBaseTitle() + "已经离线，可能无法收到您的消息!");
        }

        Observable<Boolean> observable = Observable.create(
                new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                        if (!SmackManager.getInstance().isConnected())
                            throw new IMException("服务器未连接");
                        if (!SmackManager.getInstance().isAuthenticated())
                            throw new IMException("用户未登录");
                        if (mChat == null)
                            throw new IMException("无法创建聊天对象");
                        org.jivesoftware.smack.packet.Message m = new org.jivesoftware.smack.packet.Message();
                        String str=URLEncoder.encode(message,"UTF-8");
                        m.setBody(str);
                        m.setBusinessId(mBusinessId);
                        mChat.sendMessage(m);
                        //入库
                        if (bsave)

                                IMDBHelper.getInstance().addChatMessageToDB(ApplicationSet.getInstance().getOpenfireLoginId(),
                                        ChatProvider.ChatConstants.OUTGOING,
                                        mChatOid, getBaseTitle(), ChatProvider.ChatConstants.DS_SENT_OR_READ, System.currentTimeMillis(),
                                        message, mBusinessId, ChatProvider.ChatConstants.BP_NEW);

                        e.onComplete();
                    }
                }
        );
        Observer<Boolean> subscriber = new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean b) {

            }

            @Override
            public void onError(Throwable t) {
                T.showLong(SessionActivity.this, t.getMessage());
            }

            @Override
            public void onComplete() {

                //mEtContent.setText("");
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_PICKER://图片回调
                if (data == null || data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT) == null) {
                    return;
                }
                ArrayList<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path != null || path.size() > 0) {
                    //是否发送原图
                    for (String name : path
                            ) {
                        sendImagesMsg(name, System.currentTimeMillis(), true, true);
                    }
                }
                break;
            case MAP_PICKER://地图回调
                if (data != null) {
                    GeolocationVO gvo = new GeolocationVO();
                    gvo.setLat(data.getDoubleExtra("latitude", 0.0d));
                    gvo.setLat(data.getDoubleExtra("longitude", 0.0d));
                    gvo.setAddress(data.getStringExtra("address"));
                    String mapAddress = UrlUtil.getStaticMapImgUrl(this, gvo.getLng(), gvo.getLat());
                    gvo.setMapAddress(mapAddress);
                    sendAddressMessage(gvo, System.currentTimeMillis(), true, true);
                }
                break;
            case DEAL://咨询处理回调
                if (data != null) {
//                    IMDBHelper.getInstance().deleteChat(mChatOid);
//                    mBusinessId = StringUtil.getUUID();
//                    IMBusinessIdVO.setNewBusinessId(mContext,mBusinessId,mChatOid,ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mElEmotion.isShown()) {
//                hideEmotionLayout();
//                mFlEmotionView.setVisibility(View.GONE);
//                return true;
//            } else if (mLlMore.isShown()) {
//                hideMoreLayout();
//                mFlEmotionView.setVisibility(View.GONE);
//                return true;
//            }
            SessionActivity.this.finish();
        }
        return true;
    }

    /**
     * 检查是否有访问定位的权限
     */
    public void checkLocal() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        performCodeWithPermission(getString(R.string.rationale_location), RC_LOCATION_PERM, perms, new PermissionCallback() {
            @Override
            public void hasPermission(List<String> allPerms) {
                mIntent = new Intent(mContext, SessionMapActivity.class);
                startActivityForResult(mIntent, MAP_PICKER);
            }

            @Override
            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
                if (hasPermanentlyDenied) {
                    alertAppSetPermission(getString(R.string.rationale_ask_again), RC_SETTINGS_SCREEN);
                }
            }
        });
    }

    /**
     * 聊天对方状态变化时
     */
    private void updateContactStatus() {
        //TODO 聊天对方状态变化时
        checkOnline(false);
    }

    /**
     * 聊天信息变更
     */
    private void updateChatStatus() {
        final String selection = ChatProvider.ChatConstants.USERID + "='" + ApplicationSet.getInstance().getOpenfireLoginId() + "' and " +
                ChatProvider.ChatConstants.JID + " = '" + mChatOid + "' and " +
                ChatProvider.ChatConstants._ID + " > " + mLastChatDBID;
        Observable<Boolean> observable = Observable.create(
                new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                        //Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder
                        Cursor cursor = getContentResolver().query(ChatProvider.CONTENT_URI, null, selection, null, ChatProvider.ChatConstants.DEFAULT_SORT_ORDER);
                        if (cursor == null)
                            return;
                        int count = mMessages.size();
                        while (cursor.moveToNext()) {
                            mLastChatDBID = cursor.getInt(cursor.getColumnIndex(ChatProvider.ChatConstants._ID));
                            int direction = cursor.getInt(cursor.getColumnIndex(ChatProvider.ChatConstants.DIRECTION));
                            boolean issend = direction == ChatProvider.ChatConstants.OUTGOING;
                            long ts = cursor.getLong(cursor.getColumnIndex(ChatProvider.ChatConstants.DATE));
                            String content = cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.MESSAGE));
                           // setBusinessId(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.BUSINESSID)));
                            GeolocationVO gvo = XMPPHelper.getMapMessageInfo(content);
                            if (gvo != null) {
                                sendAddressMessage(gvo, ts, issend, false);
                                continue;
                            }
                            //TODO 图片及文件这部分后续需要完善
                            FileMessageVO fvo = XMPPHelper.getFileMessageFileInfo(content);
                            if (fvo != null && fvo.isImg()) {
                                sendImagesMsg(fvo.getFilelocurl(), ts, issend, false);
                                continue;
                            }
                            sendNor(content, ts, issend, false);
                        }
                        e.onNext(mAdapter != null && count != mMessages.size());
                    }
                }
        );
        Observer<Boolean> subscriber = new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Boolean b) {
                if (b) {
                    mAdapter.notifyDataSetChanged();
                    cvScrollToBottom();
                }
            }

            @Override
            public void onError(Throwable t) {
                T.showLong(SessionActivity.this, t.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }



    /**
     * 聊天数据库变化监听
     */
    private class ChatObserver extends ContentObserver {
        public ChatObserver() {
            super(mChatHandler);
        }

        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            updateChatStatus();
        }
    }

    /**
     * 联系人数据库变化监听
     */
    private class ContactObserver extends ContentObserver {
        public ContactObserver() {
            super(mRosterHandler);
        }

        public void onChange(boolean selfChange) {
            L.d("ContactObserver.onChange: " + selfChange);
            updateContactStatus();// 联系人状态变化时，刷新界面
        }
    }

    public String getChatOid() {
        return mChatOid;
    }

    //
    public  void alertToFinish() {
        new MaterialDialog.Builder(mContext)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.dl_title_finishchat)
                .positiveText(R.string.dl_btn_ok)
                .negativeText(R.string.dl_btn_cancel)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        IMDBHelper.getInstance().deleteChat(mChatOid);
                        mBusinessId = StringUtil.getUUID();
                        IMBusinessIdVO.setNewBusinessId(mContext,mBusinessId,mChatOid,ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
                        finish();
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    /**
     * 若软键盘或表情键盘弹起，点击上端空白处应该隐藏输入法键盘
     *
     * @return 会隐藏输入法键盘的触摸事件监听器
     */
    private View.OnTouchListener getOnTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBox.hideLayout();
                mBox.hideKeyboard(mContext);
                return false;
            }
        };
    }
    private void initMessageInputToolBox() {
        mBox.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
//                Message message = new Message(Message.MSG_TYPE_TEXT, Message.MSG_STATE_SUCCESS,
//                        "Tom", "avatar", "Jerry",
//                        "avatar", content, true, true, new Date());
//                datas.add(message);
//                adapter.refresh(datas);
//                createReplayMsg(message);
                sendMessage(content, true);
            }

            @Override
            public void selectedFace(Faceicon content) {
//                Message message = new Message(Message.MSG_TYPE_FACE, Message.MSG_STATE_SUCCESS,
//                        "Tom", "avatar", "Jerry", "avatar", content.getPath(), true, true, new
//                        Date());
//                datas.add(message);
//                adapter.refresh(datas);
//                createReplayMsg(message);
            }

            @Override
            public void selectedEmoji(Emojicon emoji) {
                mBox.getEditTextBox().append(emoji.getValue());
            }

            @Override
            public void selectedBackSpace(Emojicon back) {
                DisplayRules.backspace(mBox.getEditTextBox());
            }

            @Override
            public void selectedFunction(int index) {

            }
        });

        List<String> faceCagegory = new ArrayList<>();
//        File faceList = FileUtils.getSaveFolder("chat");
        File faceList = new File("");
        if (faceList.isDirectory()) {
            File[] faceFolderArray = faceList.listFiles();
            for (File folder : faceFolderArray) {
                if (!folder.isHidden()) {
                    faceCagegory.add(folder.getAbsolutePath());
                }
            }
        }

        mBox.setFaceData(faceCagegory);
    }
}

