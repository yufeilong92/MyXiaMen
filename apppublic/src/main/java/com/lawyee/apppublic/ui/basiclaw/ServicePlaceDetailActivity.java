package com.lawyee.apppublic.ui.basiclaw;

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
import com.lawyee.apppublic.adapter.ServiceofficeDetailAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaglsService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaglsOrgDetailVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.basiclaw
 * @Description:    服务所详情页
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ServicePlaceDetailActivity extends BaseActivity {

    public final static String JAGLSORGIOD = "jaglsOrgOid";
    private TextView mTvTitle;
    private ImageView mIvServiceOffice;
    private TextView mTvCheck;
    private TextView mTvServiceOfficeName;
    private TextView mTvPhone;
    private TextView mTvServesMailbox;
    private TextView mTvAddress;
    private RecyclerView mRvServiceOffice;
    private Context mContext;
    private ServiceofficeDetailAdpater mAdpater;
    private JaglsOrgDetailVO mJaglsOrgDetailVO;
    private String mOid;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_service_place_detail);
        mContext=this;
        initView();
    }

    private void initView() {
        mIvServiceOffice= (ImageView) findViewById(R.id.iv_service_office);
        mTvCheck= (TextView) findViewById(R.id.tv_check);
        mTvServiceOfficeName= (TextView) findViewById(R.id.tv_service_office_name);
        mTvPhone= (TextView) findViewById(R.id.tv_phone);
        mTvServesMailbox= (TextView) findViewById(R.id.tv_serves_mailbox);
        mTvAddress= (TextView) findViewById(R.id.tv_address);
        mRvServiceOffice= (RecyclerView) findViewById(R.id.rv_service_office);
        initData();
        mTvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, JaglsStaffOfOrgActivity.class);
                intent.putExtra(JAGLSORGIOD,mOid);
                mContext.startActivity(intent);
            }
        });

    }
    private void initData() {
        mOid=  getIntent().getStringExtra(JAGLSORGIOD);
        if(mOid==null){
            finish();
        }
        LoadData(mOid);
    }
    /**
     * 加载律所详情数据
     * @param oid  律所ID
     */
    private void LoadData(String oid) {
        if(getInProgess())
            return;
        setInProgess(true);
        JaglsService jaglsService = new JaglsService(mContext);
        jaglsService.setProgressShowContent(getString(R.string.get_ing));
        jaglsService.setShowProgress(true);
        jaglsService.getOrgDetail(oid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()||!(values.get(0) instanceof JaglsOrgDetailVO))
                {
                    T.showLong(mContext,getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJaglsOrgDetailVO = (JaglsOrgDetailVO) values.get(0);

                initDetailView();

            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
            }
        });
    }

    private void initDetailView() {
        mTvServiceOfficeName.setText(mJaglsOrgDetailVO.getName());
        mTvPhone.setText(mJaglsOrgDetailVO.getTelephone());
        mTvServesMailbox.setText(mJaglsOrgDetailVO.getEmail());
        BaseCommonDataVO baseCommonDataVO2= BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(),mJaglsOrgDetailVO.getProvince());
        String province= "";
        if(baseCommonDataVO2!=null){
            province=baseCommonDataVO2.getName();
        }
        BaseCommonDataVO baseCommonDataVO= BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(),mJaglsOrgDetailVO.getCity());
        String city= "";
        if(baseCommonDataVO!=null){
            city=baseCommonDataVO.getName();
        }
        BaseCommonDataVO baseCommonDataVO1= BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(),mJaglsOrgDetailVO.getCounty());
        String country="";
        if(baseCommonDataVO1!=null){
            country=baseCommonDataVO1.getName();
        }
        mTvAddress.setText(province+city+country+mJaglsOrgDetailVO.getAddress());
        String imageUrl = mJaglsOrgDetailVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext,imageUrl),mIvServiceOffice ,ApplicationSet.CDIO_LAW);
        }else {
            mIvServiceOffice.setImageResource(R.drawable.ic_default_avatar);
        }
                mRvServiceOffice.setVisibility(View.VISIBLE);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
                mRvServiceOffice.setLayoutManager(gridLayoutManager);
                mAdpater = new ServiceofficeDetailAdpater(mContext, mJaglsOrgDetailVO,mTvAddress.getText().toString());
                mRvServiceOffice.setAdapter(mAdpater);
    }
}
