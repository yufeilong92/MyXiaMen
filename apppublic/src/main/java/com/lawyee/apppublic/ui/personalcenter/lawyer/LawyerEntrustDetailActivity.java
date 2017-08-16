package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.MyEntrustDetailAdpater;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawUserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JalawLawyerEntrustDetailVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    律师端-我的委托详情
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawyerEntrustDetailActivity extends BaseActivity {
    public final static String LAWENTRUSTDETAIL="EntrustDetail";
    private JalawLawyerEntrustDetailVO mJalawLawyerEntrustDetailVO;// 律师委托详情VO
    private MyEntrustDetailAdpater mAdpater;
    private String  mOid;
    private RecyclerView rv_entrust_detail;
    private Context mContext;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lawyer_entrust_detail);
        mContext=this;
        mOid= (String) getIntent().getSerializableExtra(LAWENTRUSTDETAIL);
        if(mOid==null||mOid.equals("")){
            finish();
        }
        rv_entrust_detail= (RecyclerView) findViewById(R.id.rv_entrust_detail);
        loadData();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadData() {
        if(getInProgess())
            return;
        setInProgess(true);
        JalawUserService service = new JalawUserService(this);
        service.setProgressShowContent(mContext.getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getEntrustDetail(mOid,  new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()||!(values.get(0) instanceof JalawLawyerEntrustDetailVO))
                {
                    T.showLong(mContext,getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJalawLawyerEntrustDetailVO  = (JalawLawyerEntrustDetailVO)values.get(0);

                GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,1);
                rv_entrust_detail.setLayoutManager(gridLayoutManager);
                mAdpater=new MyEntrustDetailAdpater(mContext,mJalawLawyerEntrustDetailVO,2);
                rv_entrust_detail.setAdapter(mAdpater);
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
            }
        });
    }

}
