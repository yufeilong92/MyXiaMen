package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawUserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.vo.JalawLawyerEntrustDetailVO;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    律师端我的委托回复
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawyerEntrustReplyActivity extends BaseActivity {

    public static  final  String ENTRUSTREPLY="EntrustReply";
    private TextView mTvTitle;
    private TextView mTvEntrustPeople;
    private TextView mTvEntrustMatter;
    private TextView mTvEntrustTime;
    private TextView mTvEntrustDetail;
    private EditText mEtPhone;
    private EditText mEtSpecialLane2;
    private EditText mEtReplyDetail;
    private TextView mTvSure;
    private Context mContext;
    private String mOid;
    private JalawLawyerEntrustDetailVO mJalawLawyerEntrustDetailVO;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lawyer_entrust_reply);
        mContext=this;
        initView();
        loadData();
    }

    private void initView() {
        mTvEntrustPeople= (TextView) findViewById(R.id.tv_entrust_people);
        mTvEntrustMatter= (TextView) findViewById(R.id.tv_entrust_matter);
        mTvEntrustTime= (TextView) findViewById(R.id.tv_entrust_time);
        mTvEntrustDetail= (TextView) findViewById(R.id.tv_entrust_detail);
        mEtPhone= (EditText) findViewById(R.id.et_phone);
        mEtSpecialLane2= (EditText) findViewById(R.id.et_special_lane2);
        mEtReplyDetail= (EditText) findViewById(R.id.et_reply_detail);
        mTvSure= (TextView) findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReply(mEtPhone.getText().toString(),mEtSpecialLane2.getText().toString(),mEtReplyDetail.getText().toString());
            }
        });
        mOid=getIntent().getStringExtra(ENTRUSTREPLY);
        if(mOid==null||mOid.equals("")){
            finish();
        }
    }
    //提交委托
    private void submitReply(String phone ,String planNum,String detail) {
        if(StringUtil.isEmpty(detail)){
            T.showLong(mContext,getString(R.string.please_input_reply));
            mEtReplyDetail.requestFocus();
            return;
        }
        if (getInProgess())
            return;
        setInProgess(true);
        JalawUserService service = new JalawUserService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.postEntrustReply(mJalawLawyerEntrustDetailVO.getOid(), phone, planNum, detail, new BaseJsonService.IResultInfoListener() {
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
//加载详情数据
    private void loadData() {
        if (getInProgess())
            return;
        setInProgess(true);
        JalawUserService service = new JalawUserService(this);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getEntrustDetail(mOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JalawLawyerEntrustDetailVO)) {
                    T.showLong(mContext, getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJalawLawyerEntrustDetailVO = (JalawLawyerEntrustDetailVO) values.get(0);
                initData();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext, msg);
                setInProgess(false);
            }
        });
    }
//设置数据
    private void initData() {
        TextViewUtil.isEmpty(mTvEntrustPeople,mJalawLawyerEntrustDetailVO.getEntrustPersonName());
        TextViewUtil.isEmpty(mTvEntrustMatter,mJalawLawyerEntrustDetailVO.getServiceName());
        TextViewUtil.isEmpty(mTvEntrustTime, mJalawLawyerEntrustDetailVO.getEntrustTime());
        TextViewUtil.isEmpty(mTvEntrustDetail,mJalawLawyerEntrustDetailVO.getEntrustContent());
    }
}
