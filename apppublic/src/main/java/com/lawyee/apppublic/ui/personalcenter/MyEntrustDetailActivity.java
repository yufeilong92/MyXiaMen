package com.lawyee.apppublic.ui.personalcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.MyEntrustDetailAdpater;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JalawLawyerEntrustDetailVO;
import com.lawyee.apppublic.vo.JalawLawyerEntrustVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

import static com.lawyee.apppublic.R.id.tv_select;
import static com.lawyee.apppublic.ui.personalcenter.EntrystEvaluateActivity.ENTRY;
import static com.lawyee.apppublic.ui.personalcenter.EntrystEvaluateActivity.ENTRYTYPE;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description:    公众端-委托详情
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MyEntrustDetailActivity extends BaseActivity {
    public final static String MYENTRUST="MyEntrus";
    private RecyclerView mRvEntrustDetail;
    private Context mContext;
    private JalawLawyerEntrustDetailVO mJalawLawyerEntrustDetailVO;// 律师委托详情VO
    private MyEntrustDetailAdpater mAdpater;
    private JalawLawyerEntrustVO mJalawLawyerEntrustVO;
    private TextView mTvselect;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_entrust_detail);
        mContext=this;
        mJalawLawyerEntrustVO= (JalawLawyerEntrustVO) getIntent().getSerializableExtra(MYENTRUST);
        if(mJalawLawyerEntrustVO==null){
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
        intent.putExtra(ENTRY,mJalawLawyerEntrustDetailVO.getOid());
        intent.putExtra(ENTRYTYPE,0);
            startActivity( intent);
    }
    private void loadData() {
        if(getInProgess())
            return;
        setInProgess(true);
        UserService service = new UserService(this);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getJalawEntrustDetail(mJalawLawyerEntrustVO.getOid(),  new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()||!(values.get(0) instanceof JalawLawyerEntrustDetailVO))
                {
                    T.showLong(mContext,getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJalawLawyerEntrustDetailVO  = (JalawLawyerEntrustDetailVO)values.get(0);
                if(mJalawLawyerEntrustDetailVO.getEntrutStatus()==1){
                    mTvselect.setVisibility(View.VISIBLE);
                }
                GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,1);
                mRvEntrustDetail.setLayoutManager(gridLayoutManager);
                if(mJalawLawyerEntrustDetailVO.isEvaluateStatus()){
                    mTvselect.setVisibility(View.INVISIBLE);
                    mAdpater=new MyEntrustDetailAdpater(mContext,mJalawLawyerEntrustDetailVO,3);
                }else {
                    mAdpater=new MyEntrustDetailAdpater(mContext,mJalawLawyerEntrustDetailVO,2);
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
