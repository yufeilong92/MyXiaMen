package com.lawyee.apppublic.ui.lawyerService;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaglsService;
import com.lawyee.apppublic.dal.JalawService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JaglsServiceVO;
import com.lawyee.apppublic.vo.JaglsStaffDetailVO;
import com.lawyee.apppublic.vo.JalawLawyerDetailVO;
import com.lawyee.apppublic.vo.JalawLawyerServiceVO;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.basiclaw.ServiceWorkerDetailActivity.JAGLSSTAFFIOD;
import static com.lawyee.apppublic.ui.lawyerService.LawyerDetailActivity.JALAWLAWYERVO;


public class OnlineEntrustActivity extends BaseActivity {

    public final static String ONLINEENTRUS = "OnlineEntrus";
    public final static String ONLINEENTRUS_JAGLS = "OnlineEntrus_jagls";
    private TextView mTvUserName;
    private EditText mEtInput;
    private TextView mTvSubmit;
    private CheckBox mCbPhone;
    private EditText mEtPhone;
    private TextView mTvBottom;
    private Context mContext;
    private JalawLawyerDetailVO mJalawLawyerVO;
    private JalawLawyerServiceVO mJalawLawyerServiceVO;
    private JaglsStaffDetailVO mJaglsStaffDetailVO;
    private JaglsServiceVO mJaglsServiceVO ;
    private  boolean isType;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_online_entrust);
        mContext = this;
        mJalawLawyerVO= (JalawLawyerDetailVO) getIntent().getSerializableExtra(JALAWLAWYERVO);
        mJalawLawyerServiceVO= (JalawLawyerServiceVO) getIntent().getSerializableExtra(ONLINEENTRUS);
        mJaglsStaffDetailVO= (JaglsStaffDetailVO) getIntent().getSerializableExtra(JAGLSSTAFFIOD);
        mJaglsServiceVO= (JaglsServiceVO) getIntent().getSerializableExtra(ONLINEENTRUS_JAGLS);
        if(mJalawLawyerVO==null&&mJalawLawyerServiceVO==null&&mJaglsStaffDetailVO==null&&mJaglsServiceVO==null){
            finish();
        }
        if(mJalawLawyerVO!=null){
            isType=true;
        }else{
            isType=false;
        }
        initView();
    }



    private void initView() {
        mTvUserName= (TextView) findViewById(R.id.tv_user_name);
        mEtInput= (EditText) findViewById(R.id.et_input);
        mTvSubmit= (TextView) findViewById(R.id.tv_submit);
        mCbPhone= (CheckBox) findViewById(R.id.cb_phone);
        mEtPhone= (EditText) findViewById(R.id.et_phone);
        mTvBottom= (TextView) findViewById(R.id.tv_bottom);
        mCbPhone.setChecked(true);
        UserVO mUserVO=ApplicationSet.getInstance().getUserVO();
        if(mUserVO.getNickName()!=null&&!mUserVO.getNickName().equals("")) {
            mTvUserName.setText(getString(R.string.entrust_tip5)+mUserVO.getNickName()+getString(R.string.entrust_tip6));
        }else if(mUserVO.getRealName()!=null&&!mUserVO.getRealName().equals("")){
            mTvUserName.setText(getString(R.string.entrust_tip5)+mUserVO.getRealName()+getString(R.string.entrust_tip6));
        }else{
            mTvUserName.setText(getString(R.string.entrust_tip5)+mUserVO.getLoginId()+getString(R.string.entrust_tip6));
        }

        mEtPhone.setText(ApplicationSet.getInstance().getUserVO().getMobile());
        if(isType){
            mTvBottom.setText(getString(R.string.entrust_tip3)+mJalawLawyerVO.getName()+getString(R.string.entrust_tip4));
        }else {
            mTvBottom.setText(getString(R.string.entrust_tip7)+mJaglsStaffDetailVO.getName()+getString(R.string.entrust_tip8));
        }

        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String phone="";
                        if(mCbPhone.isChecked()){
                            phone=mEtPhone.getText().toString().trim();
                            if(!StringUtil.validateMoblie(phone))
                            {
                                T.showLong(mContext,getString(R.string.please_input_phone_errorhint));
                                mEtPhone.requestFocus();
                                return;
                            }
                        }
                        if(isType) {
                            submitEntrust(phone, mEtInput.getText().toString().trim(), mJalawLawyerVO.getOid(), mJalawLawyerServiceVO.getService());
                        }else{
                            submitEntrustStaff(phone, mEtInput.getText().toString().trim(), mJaglsStaffDetailVO.getOid(), mJaglsServiceVO.getService());
                        }
            }
        });

    }
    private void submitEntrust(String phone ,String entrustContent,String entrustLawyer,String serviceId) {
        if(StringUtil.isEmpty(entrustContent)){
            T.showLong(mContext,getString(R.string.please_input_content_errorhint));
            mEtInput.requestFocus();
            return;
        }
        if (getInProgess())
            return;
        setInProgess(true);
        JalawService service = new JalawService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.postEntrust(phone, entrustContent, entrustLawyer, serviceId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                T.showShort(mContext, R.string.submit_success);
                finish();
            }

            @Override
            public void onError(String msg, String content) {
                setInProgess(false);
                T.showLong(mContext, msg);
            }
        });
    }
    private void submitEntrustStaff(String phone ,String entrustContent,String entrustLawyer,String serviceId) {
        if(StringUtil.isEmpty(entrustContent)){
            T.showLong(mContext,getString(R.string.please_input_content_errorhint));
            mEtInput.requestFocus();
            return;
        }
        if (getInProgess())
            return;
        setInProgess(true);
        JaglsService service = new JaglsService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.postEntrust(phone, entrustContent, entrustLawyer, serviceId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                T.showShort(mContext, R.string.submit_success);
                finish();
            }

            @Override
            public void onError(String msg, String content) {
                setInProgess(false);
                T.showLong(mContext, msg);
            }
        });
    }
}
