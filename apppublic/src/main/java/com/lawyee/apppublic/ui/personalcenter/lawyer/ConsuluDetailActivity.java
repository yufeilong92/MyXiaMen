package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.MyConsultDetailAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JacstService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JacstConsulationDetailVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

import static com.lawyee.apppublic.R.id.tv_select;
import static com.lawyee.apppublic.ui.personalcenter.lawyer.ConsultEvaluateActivity.CONSULTEVALUAT;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  我的咨询详情
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    注释
 * @author:czq
 * @date:   2017/8/8
 * @version
 * @verdescript   2017/8/8  czq 初建
 * @Copyright: 2017/8/8 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ConsuluDetailActivity extends BaseActivity {

    private TextView mTvSelect;
    private RecyclerView mRvConsultDetail;

    public final static String CONSULUDETAIL="ConsuluDetail";
    private Context mContext;
    private JacstConsulationDetailVO mJacstConsulationDetailVO ;// 详情VO
    private MyConsultDetailAdpater mAdpater;
    private String mOid;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_consulu_detail);
        mContext=this;
        mOid= (String) getIntent().getSerializableExtra(CONSULUDETAIL);
        if(mOid==null){
            finish();
        }
        initView();
    }

    private void initView() {
        mTvSelect = (TextView) findViewById(tv_select);
        mRvConsultDetail = (RecyclerView) findViewById(R.id.rv_consult_detail);
        mTvSelect.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void onToolbarClick(View view) {
        Intent intent=new Intent(mContext,ConsultEvaluateActivity.class);
        intent.putExtra(CONSULTEVALUAT,mJacstConsulationDetailVO.getOid());
        startActivity( intent);
    }
    private void loadData() {
        if(getInProgess())
            return;
        setInProgess(true);
        JacstService service = new JacstService(this);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getConsulationDetail(mOid,  new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()||!(values.get(0) instanceof JacstConsulationDetailVO))
                {
                    T.showLong(mContext,getString(R.string.get_error_noeffectdata));
                    return;
                }
                mTvSelect.setVisibility(View.VISIBLE);
                mJacstConsulationDetailVO  = (JacstConsulationDetailVO)values.get(0);

                GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,1);
                mRvConsultDetail.setLayoutManager(gridLayoutManager);
                if(mJacstConsulationDetailVO.getEvaluateScore()!=null&&!mJacstConsulationDetailVO.getEvaluateScore().equals("")){
                    mTvSelect.setVisibility(View.INVISIBLE);
                    mAdpater=new MyConsultDetailAdpater(mContext,mJacstConsulationDetailVO,2);
                }else {
                    if(!ApplicationSet.getInstance().getUserVO().isPublicUser()){
                        mTvSelect.setVisibility(View.INVISIBLE);
                    }
                    mAdpater=new MyConsultDetailAdpater(mContext,mJacstConsulationDetailVO,1);
                }

                mRvConsultDetail.setAdapter(mAdpater);
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
            }
        });
    }


}
