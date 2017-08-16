package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jauker.widget.BadgeView;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.personalcenter.LoginActivity;
import com.lawyee.apppublic.ui.personalcenter.MyMessageActivity;
import com.lawyee.apppublic.util.db.ChatProvider;
import com.lawyee.apppublic.util.db.IMDBHelper;

import net.lawyee.mobilelib.utils.T;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.lawyee.apppublic.R.id.rl_my_entrust;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    律师端-个人中心
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class LawyerPCenterActivity extends BaseActivity implements View.OnClickListener{


    private RelativeLayout mRlMyEntrust;
    private RelativeLayout mRlMyConsult;
    private RelativeLayout mRlMyMessage;
    private View mTvMessage;
    private TextView mTvLoginOut;
    private Context mContext;
    private BadgeView badgeView;
    private int mNum;
    private ChatObserver mChatObserver = new ChatObserver();
    private Handler mChatHandler = new Handler();
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lawyer_pcenter);
        mContext=this;
        initView();
        getContentResolver().registerContentObserver(
                ChatProvider.CONTENT_URI, true, mChatObserver);// 开始聊天数据库
    }

    private void initView() {
        mRlMyEntrust= (RelativeLayout) findViewById(rl_my_entrust);
        mRlMyConsult= (RelativeLayout) findViewById(R.id.rl_my_consult);
        mRlMyMessage= (RelativeLayout) findViewById(R.id.rl_my_message);
        mTvMessage= findViewById(R.id.iv_message_right);
        mTvLoginOut= (TextView) findViewById(R.id.tv_login_out);
        mRlMyEntrust.setOnClickListener(this);
        mRlMyConsult.setOnClickListener(this);
        mRlMyMessage.setOnClickListener(this);
        mTvLoginOut.setOnClickListener(this);
        badgeView = new BadgeView(this);
        badgeView.setTargetView(mTvMessage);
        badgeView.setBadgeGravity(Gravity.CENTER);
        badgeView.setTypeface(Typeface.create(Typeface.SANS_SERIF,
                Typeface.ITALIC));

        badgeView.setVisibility(View.GONE);
        if(ApplicationSet.getInstance().getUserVO()!=null){
            if(ApplicationSet.getInstance().getUserVO().getRealName()!=null&&!ApplicationSet.getInstance().getUserVO().getRealName().equals("")){
                setTitle(ApplicationSet.getInstance().getUserVO().getRealName());
            }else{
                setTitle(ApplicationSet.getInstance().getUserVO().getLoginId());
            }
        }
    }
    public void onToolbarClick(View view) {
        startActivity(new Intent(mContext, SettingActivity.class));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case rl_my_entrust://委托
                startActivity(new Intent(mContext,LawyerEntrustActivity.class));
                break;
            case R.id.rl_my_consult://咨询
                startActivity(new Intent(mContext,ConsultListActivity.class));
                break;
            case R.id.rl_my_message://咨询
                startActivity(new Intent(mContext,MyMessageActivity.class));
                break;
            case R.id.tv_login_out://退出登录
                new MaterialDialog.Builder(this)
                        .content("您确定要退出当前帐号吗?")
                        .positiveText(R.string.dl_btn_confirm)
                        .negativeText(R.string.dl_btn_cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ApplicationSet.getInstance().setUserVO(null,true);
                                startActivity(new Intent(mContext, LoginActivity.class));
                                LawyerPCenterActivity.this.finish();
                            }
                        })
                        .show();
                break;

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadMessageNum();
    }
    //加载聊天数
    private void loadMessageNum() {
        if(ApplicationSet.getInstance().getUserVO()!=null) {
            int mNum = IMDBHelper.getInstance().getNoReadMessage(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
            setBadgView(badgeView,mNum);
        }
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

    private void updateChatStatus() {
        Observable<Boolean> observable = Observable.create(
                new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                        int num  = IMDBHelper.getInstance().getNoReadMessage(ApplicationSet.getInstance().getUserVO().getOpenfireLoginId());
                        if(mNum!=num){
                            mNum=num;
                            e.onNext(true);
                        }

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
                    setBadgView(badgeView,mNum);
                }
            }

            @Override
            public void onError(Throwable t) {
                T.showLong(mContext, t.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mChatObserver);
    }
}
