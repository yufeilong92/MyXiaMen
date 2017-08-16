package com.lawyee.apppublic.ui.personalcenter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.FunctionAdpater;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description:    委托评价页
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class EntrystEvaluateActivity extends BaseActivity implements View.OnClickListener {

    public static final String ENTRY = "entry";
    public static final String ENTRYTYPE = "entrytype";
    private TextView mTvTitle;
    private TextView mTvLawyerChoose;
    private EditText mEtLawyerInput;
    private TextView mTvLawfirmChoose;
    private EditText mEtLawfirmInput;
    private TextView mTvSubmit;
    private MaterialDialog mPopWindowsShow;
    List<String> mData = new ArrayList<>();
    private Context mContext;
    private String mOid;
    private int mType;
    private TextView mTvWorker;
    private TextView mTvWorkerDetail;
    private TextView mTvOrg;
    private TextView mTvOrgDetail;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_entryst_evaluate);
        mContext = this;
        initView();
    }

    private void initView() {
        mTvWorker= (TextView) findViewById(R.id.tv_worker);
        mTvWorkerDetail= (TextView) findViewById(R.id.tv_worker_detail);
        mTvOrg= (TextView) findViewById(R.id.tv_org);
        mTvOrgDetail= (TextView) findViewById(R.id.tv_org_detail);
        mTvLawyerChoose = (TextView) findViewById(R.id.tv_lawyer_choose);
        mEtLawyerInput = (EditText) findViewById(R.id.et_lawyer_input);
        mTvLawfirmChoose = (TextView) findViewById(R.id.tv_lawfirm_choose);
        mEtLawfirmInput = (EditText) findViewById(R.id.et_lawfirm_input);
        mTvSubmit = (TextView) findViewById(R.id.tv_submit);
        mTvLawfirmChoose.setOnClickListener(this);
        mTvLawyerChoose.setOnClickListener(this);
        mTvSubmit.setOnClickListener(this);
        mData.add(mContext.getString(R.string.unsatisfactory));
        mData.add(mContext.getString(R.string.satisfied));
        mData.add(mContext.getString(R.string.great_satisfaction));
        mOid = getIntent().getStringExtra(ENTRY);
        mType = getIntent().getIntExtra(ENTRYTYPE, 0);
        if (mOid == null) {
            finish();
        }
        if (mType== 1) {
            mTvWorker.setText(R.string.evaluate_worker);
            mTvWorkerDetail.setText(R.string.worker_suggest);
            mTvOrg.setText(R.string.evaluate_org);
            mTvOrgDetail.setText(R.string.orgsuggest);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lawyer_choose:
                handlerPopWindos(mData, 0);
                break;
            case R.id.tv_lawfirm_choose:
                handlerPopWindos(mData, 1);
                break;
            case R.id.tv_submit:
                if (mType == 0) {
                    toSubmit(toNum(mTvLawyerChoose.getText().toString()), mEtLawyerInput.getText().toString(),
                            toNum(mTvLawfirmChoose.getText().toString()), mEtLawfirmInput.getText().toString());
                } else {
                    toJaglsSubmit(toNum(mTvLawyerChoose.getText().toString()), mEtLawyerInput.getText().toString(),
                            toNum(mTvLawfirmChoose.getText().toString()), mEtLawfirmInput.getText().toString());
                }

                break;
        }
    }

    private void toJaglsSubmit(String evaluateLawyerScore, String evaluateLawyerDescribe,
                               String evaluateLawfirmScore, String evaluateLawfirmDescribe) {
        if (getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.postJaglsEntrustEvaluate(mOid, evaluateLawyerScore, evaluateLawyerDescribe,
                evaluateLawfirmScore, evaluateLawfirmDescribe, new BaseJsonService.IResultInfoListener() {
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

    private void toSubmit(String evaluateLawyerScore, String evaluateLawyerDescribe,
                          String evaluateLawfirmScore, String evaluateLawfirmDescribe) {

        if (getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.postEntrustEvaluate(mOid, evaluateLawyerScore, evaluateLawyerDescribe,
                evaluateLawfirmScore, evaluateLawfirmDescribe, new BaseJsonService.IResultInfoListener() {
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

    /**
     * @param mData 数据
     */
    private void handlerPopWindos(final List<String> mData, final int type) {
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
                if (type == 0) {
                    mTvLawyerChoose.setText(itemVo);
                } else {
                    mTvLawfirmChoose.setText(itemVo);
                }

                mPopWindowsShow.dismiss();
            }
        });
    }

    private String toNum(String num) {
        String sore = "3";
        if (num.equals(mContext.getString(R.string.unsatisfactory))) {
            sore = "1";
        } else if (num.equals(mContext.getString(R.string.satisfied))) {
            sore = "2";
        }
        return sore;
    }


}
