package com.lawyee.apppublic.ui.personalcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description:    找回密码Adpater
 * @author:czq
 * @date:   2017/5/31
 * @version
 * @verdescript   2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{


    private EditText mEtPhone;
    private EditText mEtAuthCode;
    private TextView mTvGetAuthCode;
    private EditText mEtPwd;
    private TextView mTvSure;
    private String mUserName;
    private Timer mTime;
    private long durationTime = 1000;
    private int countdown = 59;
    private  String mSecond;
    private String mRetry;
    private Context mContext;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_forget_pwd);
        mContext=this;
        initView();
    }

    private void initView() {
        mEtPhone= (EditText) findViewById(R.id.et_phone);
        mEtAuthCode= (EditText) findViewById(R.id.et_auth_code);
        mTvGetAuthCode= (TextView) findViewById(R.id.tv_get_auth_code);
        mEtPwd= (EditText) findViewById(R.id.et_pwd);
        mTvSure= (TextView) findViewById(R.id.tv_sure);
        mTvGetAuthCode.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
        mSecond=mContext.getString(R.string.second);
        mRetry=mContext.getString(R.string.retry);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_get_auth_code:
                mUserName=mEtPhone.getText().toString().trim();
                getCode(mUserName);
                break;
            case R.id.tv_sure:
                String pwd=mEtPwd.getText().toString().trim();
                mUserName=mEtPhone.getText().toString().trim();
                String code=mEtAuthCode.getText().toString().trim();
                findPwd(mUserName,code,pwd);
                break;
        }
    }
    /**
     *
     * @param userName 手机号
     * @param code 验证码
     * @param pwd 密码
     *           请求找回密码
     */
    private void findPwd(String userName, String code, String pwd) {
        //判断有效性
        if(!StringUtil.validateMoblie(userName))
        {
            T.showLong(this,getString(R.string.please_input_phone_errorhint));
            mEtPhone.requestFocus();
            return;
        }
        if(StringUtil.isEmpty(code))
        {
            T.showLong(this,getString(R.string.please_input_vercode_errorhint));
            mEtAuthCode.requestFocus();
            return;
        }
        if(!UserVO.isEffPassword(pwd))
        {
            T.showLong(this,getString(R.string.please_input_pwd_errorhint));
            mEtPwd.requestFocus();
            return;
        }
        //
        if(getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.find_pwd_ing));
        service.setShowProgress(true);
        service.changePwd(userName, pwd, code, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                T.showShort(mContext,R.string.find_pwd_success);
                finish();
            }

            @Override
            public void onError(String msg, String content) {
                setInProgess(false);
                T.showLong(mContext,msg);
            }
        });
    }

    /***
     *
     * @param name  手机号
     *    获取验证码
     */
    private void getCode(String name){
        //判断有效性
        if(!StringUtil.validateMoblie(name))
        {
            T.showLong(this,getString(R.string.please_input_phone_errorhint));
            mEtPhone.requestFocus();
            return;
        }
        mTvGetAuthCode.setText(60+mSecond+mRetry);
        startTimer();
        if(getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(mContext);
        service.getIdentifyingCode(name,UserService.CSTR_GETIDENTCODE_ACTION_FINDPWD, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);

            }

            @Override
            public void onError(String msg, String content) {
                setInProgess(false);
                T.showLong(mContext,msg);
                cancelTimer();
            }
        });
    }
    //验证码读秒
    private void startTimer() {
        cancelTimer();
        mTvGetAuthCode.setSelected(true);
        mTvGetAuthCode.setClickable(false);
        mTime = new Timer();
        mTime.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (countdown > 0) {
                            if(countdown<10){
                                mTvGetAuthCode.setText("0"+
                                        countdown +mSecond+mRetry);
                            }else {
                                mTvGetAuthCode.setText(
                                        countdown + mSecond + mRetry);
                            }
                            countdown--;
                        } else {
                            cancelTimer();
                        }

                    }
                });

            }
        }, durationTime, durationTime);

    }
    //取消定时器以及状态恢复
    private void cancelTimer() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                countdown = 59;
                mTvGetAuthCode.setText(R.string.get_auth_code);
                mTvGetAuthCode.setClickable(true);
                mTvGetAuthCode.setSelected(false);
            }
        });
        if (mTime != null)
            mTime.cancel();
    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }
}
