package com.lawyee.apppublic.ui.personalcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.FunctionAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.lawAdministration.PersionActivity;
import com.lawyee.apppublic.ui.personalcenter.lawyer.LawyerPCenterActivity;
import com.lawyee.apppublic.vo.UserVO;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @Title: 标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description: 登陆Activity
 * @author:czq
 * @date: 2017/5/31
 * @verdescript 2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private final static String TAG = LoginActivity.class.getSimpleName();
    public final static String OTHER = "other";
    private EditText mEtPhone;
    private EditText mEtPwd;
    private TextView mTvLogin;
    private CheckBox mCbRememberPwd;
    private TextView mTvForgetPwd;
    private Context mContext;
    private boolean isOtherLogin = false;//登陆后不跳转个人中心的标识
    private TextView mTvChooseType;
    private MaterialDialog mPopWindowsShow;
    private String mRoleType="0";

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
        initData();
    }

    private void initData() {
        isOtherLogin = getIntent().getBooleanExtra(OTHER, false);
        mCbRememberPwd.setChecked(true);//默认记住密码是选中的

        UserVO userVO = (UserVO) UserVO.loadVO(UserVO.dataFileName(LoginActivity.this));
        if(userVO==null||StringUtil.isEmpty(userVO.getLoginId()))
            return;
        mEtPhone.setText(userVO.getLoginId());
        if (!userVO.isRememblePwd())
            mCbRememberPwd.setChecked(false);
        else
            mEtPwd.setText(userVO.getPassword());

    }

    private void initView() {
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        mCbRememberPwd = (CheckBox) findViewById(R.id.cb_remember_pwd);
        mTvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        mTvChooseType= (TextView) findViewById(R.id.tv_choose_type);
        mTvLogin.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
        mTvChooseType.setOnClickListener(this);
        mTvChooseType.setText(R.string.public_people);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                String mobile = mEtPhone.getText().toString();
                 String pwd = mEtPwd.getText().toString();
                if(mRoleType==null||mRoleType.equals("")){
                    T.showLong(mContext, getString(R.string.please_choose_role));
                    return;
                }
                userLogin(mobile, pwd);


                break;
            case R.id.tv_forget_pwd:
                startActivity(new Intent(mContext, ForgetPwdActivity.class));
                break;
            case R.id.tv_choose_type:
                List<String> mData=new ArrayList<>();
                mData.add("公众");
                mData.add("律师");
                mData.add("调解员");
                mData.add("媒体");
                handlerPopWindos(mData);
                break;
        }
    }



    public void onToolbarClick(View view) {
        startActivity(new Intent(mContext, RegisterActivity.class));
    }

    /**
     * 用户登录
     */
    private void userLogin(String mobile, final String pwd) {
        if(mRoleType.equals("0")){
            if(!StringUtil.validateMoblie(mobile)) {
                T.showLong(this,getString(R.string.please_input_phone_errorhint));
                mEtPhone.requestFocus();
                return;
            }
        }
        if(!UserVO.isEffPassword(pwd))
        {
            T.showLong(this,getString(R.string.please_input_pwd_errorhint));
            mEtPwd.requestFocus();
            return;
        }
        if(getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(this);
        service.setProgressShowContent("登录中...");
        service.setShowProgress(true);
        service.userLogin(mobile, pwd,mRoleType, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);

                if(values==null||values.isEmpty()||!(values.get(0) instanceof UserVO))
                {
                    T.showLong(LoginActivity.this,getString(R.string.login_error_noeffectdata));

                    return;
                }

                UserVO userVO = (UserVO)values.get(0);

                userVO.setPassword(pwd);
                userVO.setRememblePwd(mCbRememberPwd.isChecked());
                ApplicationSet.getInstance().setUserVO(userVO, true);
                T.showLong(LoginActivity.this, getString(R.string.login_sucess));
                if (!isOtherLogin) {
                    if (mRoleType.equals(UserVO.CSTR_USERROLE_PUBLIC)) {
                        startActivity(new Intent(mContext, PersonCenterActivity.class));
                    } else {
                        if (mRoleType.equals(UserVO.CSTR_USERROLE_JALAW)) {
                            startActivity(new Intent(mContext, LawyerPCenterActivity.class));
                        }else if(mRoleType.equals(UserVO.CSTR_USERROLE_JAMED)){//用户角色-人民调解员=6
                            Intent intent = new Intent(mContext, PersionActivity.class);
                            intent.putExtra(PersionActivity.CONTENT_PARAMETER_TYPE, PersionActivity.CONTENT_PARAMETER_JAMED);
                            startActivity(intent);
                        }else if (mRoleType.equals(UserVO.CSTR_USERROLE_MEDIAWORKER)){//用户角色-新闻媒体工作人员=m
                            Intent intent = new Intent(mContext, PersionActivity.class);
                            intent.putExtra(PersionActivity.CONTENT_PARAMETER_TYPE, PersionActivity.CONTENT_PARAMETER_MEDIA);
                            startActivity(intent);
                        }
                    }
                }
                LoginActivity.this.finish();
            }

            @Override
            public void onError(String msg, String content) {
                L.v(TAG, "UserLogin onError:" + content);
                T.showLong(LoginActivity.this, msg);
                setInProgess(false);
            }
        });
    }

    /**
     * @param mData  数据
     */
    private void handlerPopWindos(final List<String> mData) {
        final FunctionAdpater applyPopAdapter = new FunctionAdpater(mData, this);
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        if (mPopWindowsShow == null || !mPopWindowsShow.isShowing()) {
            mPopWindowsShow = new MaterialDialog.Builder(this)
                    .adapter(applyPopAdapter, manager)
                    .backgroundColorRes(R.color.activity_content_bg)
                    .show();
            mPopWindowsShow.getRecyclerView().addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        }
        applyPopAdapter.setOnRecyclerItemClickListener(new FunctionAdpater.OnRecyclerItemClickListener() {
            @Override
            public void OnItemClickListener(View view, String itemVo, int position) {
                mTvChooseType.setText(itemVo);
                switch (position){
                    case 0:
                        mRoleType= UserVO.CSTR_USERROLE_PUBLIC;
                        break;
                    case 1:
                        mRoleType= UserVO.CSTR_USERROLE_JALAW;
                        break;
                    case 2:
                        mRoleType= UserVO.CSTR_USERROLE_JAMED;
                        break;
                    case 3:
                        mRoleType= UserVO.CSTR_USERROLE_MEDIAWORKER;
                        break;
                    case 4://// TODO: 2017/7/24  基层法律
                        break;
                }
                mPopWindowsShow.dismiss();
            }
        });
    }
}
