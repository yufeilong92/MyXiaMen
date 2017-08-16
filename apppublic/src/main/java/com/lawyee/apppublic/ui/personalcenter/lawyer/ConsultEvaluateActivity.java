package com.lawyee.apppublic.ui.personalcenter.lawyer;

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
import com.lawyee.apppublic.dal.JacstService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title: 咨询评价填写
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    注释
 * @author:czq
 * @date:   2017/8/8
 * @version
 * @verdescript   2017/8/8  czq 初建
 * @Copyright: 2017/8/8 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class ConsultEvaluateActivity extends BaseActivity implements View.OnClickListener{

    public static  final String CONSULTEVALUAT="ConsultEvaluate";
    private TextView mTvEvaluateConsult;
    private EditText mEtSuggest3Input;
    private TextView mTvSubmit;
    private Context mContext;
    private MaterialDialog mPopWindowsShow;
    private List<String> mData= new ArrayList<>();
    private String mOid;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_consult_evaluate);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        mTvEvaluateConsult= (TextView) findViewById(R.id.tv_evaluate_consult);
        mEtSuggest3Input= (EditText) findViewById(R.id.et_suggest3_input);
        mTvSubmit= (TextView) findViewById(R.id.tv_submit);
        mTvEvaluateConsult.setOnClickListener(this);
        mTvSubmit.setOnClickListener(this);

    }





    private void initData() {
        mOid=getIntent().getStringExtra(CONSULTEVALUAT);
        mData.add(mContext.getString(R.string.unsatisfactory));
        mData.add(mContext.getString(R.string.satisfied));
        mData.add(mContext.getString(R.string.great_satisfaction));
    }


    /**
     * @param mData 数据
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
                mTvEvaluateConsult.setText(itemVo);
                mPopWindowsShow.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_evaluate_consult:
                handlerPopWindos(mData);
                break;
            case R.id.tv_submit:
                saveBusiness(toNum(mTvEvaluateConsult.getText().toString()),mEtSuggest3Input.getText().toString());
                break;
        }
    }

    private void saveBusiness(String  evaluateScore, String evaluateDescribe) {
        if (StringUtil.isEmpty(evaluateDescribe)) {
            T.showLong(mContext, getString(R.string.please_input_content_errorhint));
            mEtSuggest3Input.requestFocus();
            return;
        }
        if (getInProgess())
            return;
        setInProgess(true);
        JacstService service = new JacstService(mContext);
        service.setProgressShowContent(mContext.getString(R.string.submit_ing));
        service.setShowProgress(true);
        service.postJacstConsulationEvaluate(mOid,evaluateScore,
                 evaluateDescribe,new BaseJsonService.IResultInfoListener() {
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
