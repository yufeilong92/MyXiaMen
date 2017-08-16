package com.lawyee.apppublic.ui.lawyerService;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.SessionAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
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
import com.lawyee.apppublic.vo.FileMessageVO;
import com.lawyee.apppublic.vo.GeolocationVO;
import com.lawyee.apppublic.vo.IMBusinessIdVO;
import com.lawyee.apppublic.vo.LoginResult;
import com.lawyee.apppublic.vo.Message;
import com.lawyee.apppublic.vo.UserVO;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;
import com.lqr.recyclerview.LQRRecyclerView;

import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Presence;
import org.reactivestreams.Publisher;

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
public class SessionActivity extends BaseActivity implements IEmotionSelectedListener, View.OnClickListener {
    /**
     * 传入参数-聊天对象，如果是律师，传入oid
     */
    public static final String CSTR_EXTRA_SESSION_STR = "session";
    public static final int IMAGE_PICKER = 100;
    public static final int MAP_PICKER = 10001;
    public static final int DEAL = 20001;
    private LQRRecyclerView mCvMessage;
    private EditText mEtContent;
    private ImageView mIvEmo;
    private ImageView mIvMore;
    private Button mBtnSend;
    private LinearLayout mLlContent;
    private EmotionLayout mElEmotion;
    private ImageView mIvAlbum;
    private ImageView mIvLocation;
    private FrameLayout mFlEmotionView;
    private EmotionKeyboard mEmotionKeyboard;
    private LinearLayout mLlMore;
    private Intent mIntent;
    private Context mContext;
    private SessionAdapter mAdapter;
    private List<Message> mMessages = new ArrayList<>();//消息队列
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mChatObserver);
        getContentResolver().unregisterContentObserver(mContactObserver);
    }

    private void initView() {
        mCvMessage = (LQRRecyclerView) findViewById(R.id.cvMessage);
        mEtContent = (EditText) findViewById(R.id.etContent);
        mIvEmo = (ImageView) findViewById(R.id.ivEmo);
        mIvMore = (ImageView) findViewById(R.id.ivMore);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        mLlContent = (LinearLayout) findViewById(R.id.llContent);
        mElEmotion = (EmotionLayout) findViewById(R.id.elEmotion);
        mLlMore = (LinearLayout) findViewById(R.id.llMore);
        mIvAlbum = (ImageView) findViewById(R.id.ivAlbum);
        mIvLocation = (ImageView) findViewById(R.id.ivLocation);
        mFlEmotionView = (FrameLayout) findViewById(R.id.flEmotionView);
        mViewNull=findViewById(R.id.view_null);
        mTvFinish= (TextView) findViewById(R.id.tv_finish);
        mTvSave= (TextView) findViewById(R.id.tv_save);
        mElEmotion.attachEditText(mEtContent);
        if (!ApplicationSet.getInstance().getUserVO().isPublicUser()) {
            mViewNull.setVisibility(View.GONE);
            mTvFinish.setVisibility(View.VISIBLE);
            mTvSave.setVisibility(View.VISIBLE);
        }
        initEmotionKeyboard();
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

    public void setBusinessId(String businessId) {
        if (StringUtil.isEmpty(businessId) || businessId.equals(getBusinessId()))
            return;
        mBusinessId = businessId;
        UserVO userVO = ApplicationSet.getInstance().getUserVO();
        if (userVO == null || userVO.isPublicUser())//公众则不管业务id
            return;
        IMBusinessIdVO vo = new IMBusinessIdVO();
        vo.setBusinessId(mBusinessId);
        vo.setJid(mChatOid);
        vo.setUserid(userVO.getOpenfireLoginId());
        IMBusinessIdVO.saveVO(vo, IMBusinessIdVO.dataFileName(SessionActivity.this, userVO.getOpenfireLoginId(), mChatOid));
    }

    public String getBusinessId() {
        if (StringUtil.isEmpty(mBusinessId)) {
            UserVO userVO = ApplicationSet.getInstance().getUserVO();
            if (userVO == null || userVO.isPublicUser())//公众则不管业务id
                return "";
            //律师，如果为空时，则读取原来的，如果原来的也为空，则重新生成一个（并记录这个）
            IMBusinessIdVO vo = (IMBusinessIdVO) IMBusinessIdVO.loadVO(IMBusinessIdVO.dataFileName(SessionActivity.this,
                    userVO.getOpenfireLoginId(), mChatOid));
            if (vo == null || StringUtil.isEmpty(vo.getBusinessId())) {

                mBusinessId = StringUtil.getUUID();
                IMBusinessIdVO.setNewBusinessId(mContext,mBusinessId,mChatOid,userVO.getOpenfireLoginId());
//                vo = new IMBusinessIdVO();
//                vo.setBusinessId(mBusinessId);
//                vo.setJid(mChatOid);
//                vo.setUserid(userVO.getOpenfireLoginId());
//                IMBusinessIdVO.saveVO(vo, IMBusinessIdVO.dataFileName(SessionActivity.this, userVO.getOpenfireLoginId(), mChatOid));
            } else
                mBusinessId = vo.getBusinessId();
        }
        return mBusinessId;
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
        initListener();
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
        mEtContent.clearFocus();
    }

    //设置 表情布局监听及其他监听
    public void initListener() {
        mIvAlbum.setOnClickListener(this);
        mIvLocation.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);
        mTvSave.setOnClickListener(this);
        mElEmotion.setEmotionSelectedListener(this);
        mElEmotion.setEmotionAddVisiable(true);
        mElEmotion.setEmotionSettingVisiable(true);

        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
            @Override
            public void onEmotionAddClick(View view) {
                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEmotionSettingClick(View view) {
                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
            }
        });
        mLlContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        closeBottomAndKeyboard();
                        break;
                }
                return false;
            }
        });
        mEtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEmotionLayout();
                hideMoreLayout();
            }
        });
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtContent.getText().toString().trim().length() > 0) {
                    mBtnSend.setVisibility(View.VISIBLE);
                    mIvMore.setVisibility(View.GONE);
                } else {
                    mBtnSend.setVisibility(View.GONE);
                    //隐藏add按钮
                  //  mIvMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEtContent.getText().toString();
                sendMessage(content, true);

            }
        });
    }

    //初始化 EmotionKeyboard
    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with(this);
        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.bindToContent(mLlContent);
        mEmotionKeyboard.setEmotionLayout(mFlEmotionView);
        mEmotionKeyboard.bindToEmotionButton(mIvEmo, mIvMore);
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
                switch (view.getId()) {
                    case R.id.ivEmo:
                        if (!mElEmotion.isShown()) {
                            if (mLlMore.isShown()) {
                                showEmotionLayout();
                                hideMoreLayout();
                                return true;
                            }
                        } else if (mElEmotion.isShown() && !mLlMore.isShown()) {
                            mIvEmo.setSelected(false);
                            return false;
                        }
                        showEmotionLayout();
                        hideMoreLayout();
                        break;
                    case R.id.ivMore:
                        if (!mLlMore.isShown()) {
                            if (mElEmotion.isShown()) {
                                showMoreLayout();
                                hideEmotionLayout();
                                return true;
                            }
                        } else if (mLlMore.isShown() && !mElEmotion.isShown()) {
                            mIvMore.setSelected(false);
                            return false;
                        }
                        showMoreLayout();
                        hideEmotionLayout();
                        ;
                        break;
                }
                return false;
            }
        });
    }

    //显示表情视图
    private void showEmotionLayout() {
        mElEmotion.setVisibility(View.VISIBLE);
        mIvEmo.setSelected(true);
    }

    //隐藏表情视图
    private void hideEmotionLayout() {
        mElEmotion.setVisibility(View.GONE);
        mIvEmo.setSelected(false);
    }

    //显示底部视图
    private void showMoreLayout() {
        mLlMore.setVisibility(View.VISIBLE);
        mIvMore.setSelected(true);
    }

    //隐藏底部视图
    private void hideMoreLayout() {
        mLlMore.setVisibility(View.GONE);
        mIvMore.setSelected(false);
    }

    //关闭底部视图和键盘
    private void closeBottomAndKeyboard() {
        mElEmotion.setVisibility(View.GONE);
        mLlMore.setVisibility(View.GONE);
        if (mEmotionKeyboard != null) {
            mEmotionKeyboard.interceptBackPress();
        }
    }

    @Override
    public void onBackPressed() {
        if (mElEmotion.isShown() || mLlMore.isShown()) {
            mEmotionKeyboard.interceptBackPress();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onEmojiSelected(String key) {
        Log.e("czq", "onEmojiSelected : " + key);
    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
        Toast.makeText(getApplicationContext(), stickerBitmapPath, Toast.LENGTH_SHORT).show();
        Log.e("czq", "stickerBitmapPath : " + stickerBitmapPath);
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
                intent1.putExtra(CONSULTBUSINESS,getBusinessId());
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
                        m.setBody(message);
                        m.setBusinessId(getBusinessId());
                        mChat.sendMessage(m);
                        //入库
                        if (bsave)
                            IMDBHelper.getInstance().addChatMessageToDB(ApplicationSet.getInstance().getOpenfireLoginId(),
                                    ChatProvider.ChatConstants.OUTGOING,
                                    mChatOid, getBaseTitle(), ChatProvider.ChatConstants.DS_SENT_OR_READ, System.currentTimeMillis(),
                                    message, getBusinessId(), ChatProvider.ChatConstants.BP_NEW);
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
                mEtContent.setText("");
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
        Message message = new Message();
        message.setContent(content);
        message.setSend(isSend);
        message.setDate(time);
        message.setType(Message.CINT_MESSAGE_TYPE_NR);
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
        Message message = new Message();
        message.setType(Message.CINT_MESSAGE_TYPE_IMAGE);
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
        Message message = new Message();
        message.setContent(gvo.getAddress());
        message.setSend(isSend);
        message.setDate(time);
        message.setType(Message.CINT_MESSAGE_TYPE_ADDRESS);
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
                    IMDBHelper.getInstance().deleteChat(mChatOid);
                    mBusinessId = StringUtil.getUUID();
                    IMBusinessIdVO.setNewBusinessId(mContext,mBusinessId,mChatOid,ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mElEmotion.isShown()) {
                hideEmotionLayout();
                mFlEmotionView.setVisibility(View.GONE);
                return true;
            } else if (mLlMore.isShown()) {
                hideMoreLayout();
                mFlEmotionView.setVisibility(View.GONE);
                return true;
            }
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
                            setBusinessId(cursor.getString(cursor.getColumnIndex(ChatProvider.ChatConstants.BUSINESSID)));
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
}
