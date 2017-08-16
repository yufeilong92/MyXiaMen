package com.lawyee.apppublic.ui.personalcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.JaglsEntrustDetailAdpater;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JaglsEntrustDetailVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

import static com.lawyee.apppublic.R.id.tv_select;
import static com.lawyee.apppublic.ui.personalcenter.EntrystEvaluateActivity.ENTRY;
import static com.lawyee.apppublic.ui.personalcenter.EntrystEvaluateActivity.ENTRYTYPE;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description:    公众端-个人中心-基层法律服务-我的委托详情
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MyJaglsEntrustDetailActivity extends BaseActivity {

    public final static String MYJAGLSENTRUST="MyJaglsEntrus";
    private RecyclerView mRvEntrustDetail;
    private Context mContext;
    private JaglsEntrustDetailVO mJaglsEntrustDetailVO;// 律师委托详情VO
    private JaglsEntrustDetailAdpater mAdpater;
    private String mOid;
    private TextView mTvselect;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_jagls_entrust_detail);
        mContext=this;
        mOid= (String) getIntent().getSerializableExtra(MYJAGLSENTRUST);
        if(mOid==null){
            finish();
        }
        initView();

    }

    private void initView() {
        mRvEntrustDetail= (RecyclerView) findViewById(R.id.rv_entrust_detail);
        mTvselect= (TextView) findViewById(tv_select);
        mTvselect.setVisibility(View.INVISIBLE);





    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void onToolbarClick(View view) {
        Intent intent=new Intent(mContext,EntrystEvaluateActivity.class);
        intent.putExtra(ENTRY,mJaglsEntrustDetailVO.getOid());
        intent.putExtra(ENTRYTYPE,1);
        startActivity( intent);
    }
    private void loadData() {
        if(getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(this);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getJaglsEntrustDetail(mOid,  new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()||!(values.get(0) instanceof JaglsEntrustDetailVO))
                {
                    T.showLong(mContext,getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJaglsEntrustDetailVO  = (JaglsEntrustDetailVO) values.get(0);
                if(mJaglsEntrustDetailVO.getEntrustStatus()==1){
                    mTvselect.setVisibility(View.VISIBLE);
                }
                GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,1);
                mRvEntrustDetail.setLayoutManager(gridLayoutManager);
                if(mJaglsEntrustDetailVO.getEvaluateStaffScore()!=null && !mJaglsEntrustDetailVO.equals("")){
                    mTvselect.setVisibility(View.INVISIBLE);
                    mAdpater=new JaglsEntrustDetailAdpater(mContext,mJaglsEntrustDetailVO,3);
                }else {
                    mAdpater=new JaglsEntrustDetailAdpater(mContext,mJaglsEntrustDetailVO,2);
                }

                mRvEntrustDetail.setAdapter(mAdpater);
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
            }
        });
    }
}
