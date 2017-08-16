package com.lawyee.apppublic.ui.lawyerService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.LawFirmDetailAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.JalawLawfirmDetailVO;
import com.lawyee.apppublic.vo.JalawLawfirmVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description:    律所详情页面
 * @author:czq
 * @date:   2017/5/31
 * @version
 * @verdescript   2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class LawFirmDetailActivity extends BaseActivity {

    public final static String JALAWFIRMVO = "JalawLawfirmVO";
    private ImageView mIvLawyerFirm;
    private TextView mTvCheck;
    private TextView mTvLawyerFirmName;
    private TextView mTvAddress;
    private TextView mTvCall;
    private RecyclerView mRvLawyerFirm;
    private Context mContext;
    private LawFirmDetailAdpater mAdpater;
    private JalawLawfirmDetailVO mJalawLawfirmDetailVO;
    private JalawLawfirmVO mJalawLawfirmVO;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_law_firm_detail);
        mContext=this;
        initView();
    }

    private void initView() {
        mIvLawyerFirm= (ImageView) findViewById(R.id.iv_lawyer_firm);
        mTvCheck= (TextView) findViewById(R.id.tv_check);
        mTvLawyerFirmName= (TextView) findViewById(R.id.tv_lawyer_firm_name);
        mTvAddress= (TextView) findViewById(R.id.tv_address);
        mTvCall= (TextView) findViewById(R.id.tv_call);
        mRvLawyerFirm= (RecyclerView) findViewById(R.id.rv_lawyer_firm);
        initData();
        String imageUrl = mJalawLawfirmVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,imageUrl),mIvLawyerFirm, ApplicationSet.CDIO_LAW);
        }else {
            mIvLawyerFirm.setImageResource(R.drawable.ic_default_avatar);
        }
        mTvLawyerFirmName.setText(mJalawLawfirmVO.getName());
        mTvCall.setText(mJalawLawfirmVO.getTelephone());
        mTvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext,LawyerListActivity.class);
                    intent.putExtra(JALAWFIRMVO,mJalawLawfirmVO);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mJalawLawfirmVO= (JalawLawfirmVO) getIntent().getSerializableExtra(JALAWFIRMVO);
        if(mJalawLawfirmVO==null){
            finish();
        }
        LoadData(mJalawLawfirmVO.getOid());
    }

    /**
     * 加载律所详情数据
     * @param oid  律所ID
     */
    private void LoadData(String oid) {
        if(getInProgess())
            return;
        setInProgess(true);
        JalawService service = new JalawService(mContext);
        service.setProgressShowContent(getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getLawfirmDetail(oid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()||!(values.get(0) instanceof JalawLawfirmDetailVO))
                {
                    T.showLong(mContext,getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJalawLawfirmDetailVO = (JalawLawfirmDetailVO) values.get(0);
                mRvLawyerFirm.setVisibility(View.VISIBLE);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
                mRvLawyerFirm.setLayoutManager(gridLayoutManager);
                mAdpater = new LawFirmDetailAdpater(mContext, mJalawLawfirmDetailVO);
                mRvLawyerFirm.setAdapter(mAdpater);
                mTvAddress.setText(mJalawLawfirmDetailVO.getAddress());
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
            }
        });
    }


}
