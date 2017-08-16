package com.lawyee.apppublic.services;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;

import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.exception.IMException;
import com.lawyee.apppublic.smack.SmackImpl;
import com.lawyee.apppublic.vo.UserVO;
import com.nostra13.universalimageloader.utils.L;

import net.lawyee.mobilelib.utils.ActivityUtil;

import java.util.HashSet;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 在线咨询服务
 * @Package com.lawyee.apppublic.services
 * @Description:
 * @author:wuzhu
 * @date: 2017/6/29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class IMService extends BaseService {
    private static final String TAG = IMService.class.getSimpleName();
    public static final int CINT_IM_CONNECTED = 0;
    public static final int CINT_IM_DISCONNECTED = -1;
    public static final int CINT_IM_CONNECTING = 1;

    public static final String CSTR_IM_PONG_TIMEOUT = "连接超时";// 连接超时 pong timeout
    public static final String CSTR_IM_NETWORK_ERROR = "network error";// 网络错误 network error
    public static final String CSTR_IM_LOGOUT = "logout";// 手动退出 logout
    public static final String CSTR_IM_CONFLICT = "conflict";//登录冲突
    public static final String CSTR_IM_NOTAUTHORIZED = "not-authorized";//未有帐号
    public static final String CSTR_IM_LOGIN_FAILED = "登录失败";// 登录失败 login failed


    private int mConnectedState = CINT_IM_DISCONNECTED; // 是否已经连接

    private IConnectionStatusCallback mConnectionStatusCallback;
    private Handler mMainHandler = new Handler();
    private SmackImpl mSmackable;
    private HashSet<String> mIsBoundTo = new HashSet<String>();
    private UserVO mUserVO;
    private Thread mConnectingThread;


    public int getConnectedState()
    {
        return mConnectedState;
    }
    /**
     * 注册注解面和聊天界面时连接状态变化回调
     *
     * @param cb
     */
    public void registerConnectionStatusCallback(IConnectionStatusCallback cb) {
        mConnectionStatusCallback = cb;
    }

    public void unRegisterConnectionStatusCallback() {
        mConnectionStatusCallback = null;
    }

    private IBinder mBinder = new IMBinder();

    public UserVO getPublicUser()
    {
        if(mUserVO==null)
           mUserVO = ApplicationSet.getInstance().getUserVO();
        return mUserVO;
    }

    public String getUserID()
    {
        UserVO user = getPublicUser();
        if(user==null)
            return "";
        return user.getLoginId();
    }

    @Override
    public IBinder onBind(Intent intent) {
        L.i(IMService.class.getSimpleName(), "[IMSERVICE] onBind");
        String chatPartner = intent.getDataString();
        if ((chatPartner != null)) {
            mIsBoundTo.add(chatPartner);
        }
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        String chatPartner = intent.getDataString();
        if ((chatPartner != null)) {
            mIsBoundTo.add(chatPartner);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        super.onUnbind(intent);
        L.i(IMService.class.getSimpleName(), "[IMSERVICE] onUnbind");
        String chatPartner = intent.getDataString();
        if ((chatPartner != null)) {
            mIsBoundTo.remove(chatPartner);
        }
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.i(IMService.class.getSimpleName(), "[IMSERVICE] onCreate");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i(IMService.class.getSimpleName(), "[IMSERVICE] onDestroy");
        Logout(false);
    }

    public class IMBinder extends Binder {
        public IMService getService() {
            return IMService.this;
        }
    }
    // 登录
    public void Login(final String account, final String password) {
        if(mConnectingThread != null||isAuthenticated())
        {
            if(isAuthenticated())
                postConnectionScuessed();
            else
                L.i(TAG,"已经在登录中，无法重新连接");
            return;
        }
        if (mConnectingThread != null) {
            L.i("a connection is still going on!");
            return;
        }
        startLogin(account,password);
    }
    private void startLogin(final String account, final String password)
    {
        mConnectingThread = new Thread() {
            @Override
            public void run() {
                try {
                    postConnecting();
                    mSmackable = new SmackImpl(IMService.this);
                    if (mSmackable.login(account, password)) {
                        // 登陆成功
                        postConnectionScuessed();
                    } else {
                        // 登陆失败
                        postConnectionFailed(CSTR_IM_LOGIN_FAILED,false);
                    }
                } catch (IMException e) {
                    String message = e.getLocalizedMessage();
                    if("SASLError using DIGEST-MD5: not-authorized".equalsIgnoreCase(message))
                    {
                        postConnectionFailed(CSTR_IM_NOTAUTHORIZED,false);
                        return;
                    }
                    // 登陆失败
                    if (e.getCause() != null)
                        message += "\n" + e.getCause().getLocalizedMessage();
                    postConnectionFailed(message,false);
                    e.printStackTrace();
                } finally {
                    if (mConnectingThread != null)
                        synchronized (mConnectingThread) {
                            mConnectingThread = null;
                        }
                }
            }

        };
        mConnectingThread.start();
    }

    public boolean Logout(boolean syncuserstatus) {
        boolean isLogout = false;
        if (mConnectingThread != null) {
            synchronized (mConnectingThread) {
                try {
                    mConnectingThread.interrupt();
                    mConnectingThread.join(50);
                } catch (InterruptedException e) {
                    L.e("doDisconnect: failed catching connecting thread");
                } finally {
                    mConnectingThread = null;
                }
            }
        }
        if (mSmackable != null) {
            isLogout = mSmackable.logout();
            mSmackable = null;
        }
        postConnectionFailed(CSTR_IM_LOGOUT,syncuserstatus);// 手动退出
        return isLogout;
    }


    // 连接中，通知界面线程做一些处理
    private void postConnecting() {
        mMainHandler.post(new Runnable() {
            public void run() {
                mConnectedState = CINT_IM_CONNECTING;// 连接中
                if (mConnectionStatusCallback != null)
                    mConnectionStatusCallback.connectionStatusChanged(mConnectedState,
                            "");
            }
        });
    }
    private void postConnectionScuessed() {
        mMainHandler.post(new Runnable() {
            public void run() {
                mConnectedState = CINT_IM_CONNECTED;// 已经连接上

                if (mConnectionStatusCallback != null)
                    mConnectionStatusCallback.connectionStatusChanged(mConnectedState,
                            "");
            }

        });
    }
    // 是否连接上服务器
    public boolean isAuthenticated() {
        if (mSmackable != null) {
            return mSmackable.isAuthenticated();
        }

        return false;
    }

    /**
     * 非UI线程连接失败反馈
     *
     * @param reason
     */
    public void postConnectionFailed(final String reason,final boolean syncuserstatus) {
        mMainHandler.post(new Runnable() {
            public void run() {
                connectionFailed(reason,syncuserstatus);
            }
        });
    }

    /**
     * UI线程反馈连接失败
     *
     * @param reason
     */
    private void connectionFailed(String reason,boolean syncuserstatus) {
        L.i(TAG, "connectionFailed: " + reason);
        mConnectedState = CINT_IM_DISCONNECTED;// 更新当前连接状态
        //失败就移除所有的上传任务
        //UploadService.stopAllUploads();
        if (TextUtils.equals(reason,CSTR_IM_LOGOUT)) {// 如果是手动退出
            L.d(TAG, "connectionFailed Logout");
            return;
        }
        if (mConnectionStatusCallback != null)
            mConnectionStatusCallback.connectionStatusChanged(mConnectedState,
                    reason);
        if(TextUtils.equals(reason,CSTR_IM_CONFLICT)//多地登录冲突了
                ||TextUtils.equals(reason,CSTR_IM_NOTAUTHORIZED))
        {
            L.d(TAG, "connectionFailed CONFLICT");
            Logout(syncuserstatus);
            return;
        }
    }

    /**
     * 收到新消息
     * msg.getBusinessId(),msg.getConsultType(),
     msg.getStaffName(),msg.getUsername()
     */
    public void newMessage(final String from, final String message,
                           final String businessId,final String consultType,
                           final String userName,final String staffId,
                           final String staffName) {
        mMainHandler.post(new Runnable() {
            public void run() {
                //声音提醒
                /*if (!PreferenceUtils.getPrefBoolean(IMService.this,
                        PreferenceConstants.SCLIENTNOTIFY, false))
                    MediaPlayer.create(XXService.this, R.raw.office).start();*/
                if (!ActivityUtil.isAppOnForeground(getApplicationContext()))
                    notifyClient(from, mSmackable.getNameForJID(from), message,
                            !mIsBoundTo.contains(from),businessId,consultType,
                            userName,staffId,staffName);
                // T.showLong(XXService.this, from + ": " + message);

            }

        });
    }
}
