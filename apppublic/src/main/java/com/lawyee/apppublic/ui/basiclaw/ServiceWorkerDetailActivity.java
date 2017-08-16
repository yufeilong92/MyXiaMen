package com.lawyee.apppublic.ui.basiclaw;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.ServiceWorkerAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaglsService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaglsStaffDetailVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.basiclaw
 * @Description:    工作者详情页
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ServiceWorkerDetailActivity extends BaseActivity {

    public final static String JAGLSSTAFFIOD = "jaglsstaffOid";
    private ImageView mIvWorkerAvatar;
    private TextView mTvWorkerName;
    private TextView mTvWorkerOffice;
    private TextView mTvPhone;
    private TextView mTvServesMailbox;
    private TextView mTvAddress;
    private RecyclerView mRvWorker;
    private Context mContext;
    private ServiceWorkerAdpater mAdpater;
    private JaglsStaffDetailVO mJaglsStaffDetailVO;
    private String mOid;
    private TextView mTvSpecialityTip1;
    private TextView mTvSpecialityTip2;
    private TextView mTvSpecialityTip3;
    private LinearLayout mLlSpeciality;
    private TextView mTvSpecialityTip4;
    private TextView mTvSpecialityTip5;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_service_worker_detail);
        mContext = this;
        initView();
    }

    private void initView() {
        mIvWorkerAvatar = (ImageView) findViewById(R.id.iv_worker_avatar);
        mTvWorkerName = (TextView) findViewById(R.id.tv_worker_name);
        mTvWorkerOffice = (TextView) findViewById(R.id.tv_worker_office);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvServesMailbox = (TextView) findViewById(R.id.tv_serves_mailbox);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mRvWorker = (RecyclerView) findViewById(R.id.rv_worker);
        mTvSpecialityTip1 = (TextView) findViewById(R.id.tv_speciality_tip1);
        mTvSpecialityTip2 = (TextView) findViewById(R.id.tv_speciality_tip2);
        mTvSpecialityTip3 = (TextView) findViewById(R.id.tv_speciality_tip3);
        mTvSpecialityTip4 = (TextView) findViewById(R.id.tv_speciality_tip4);
        mTvSpecialityTip5 = (TextView) findViewById(R.id.tv_speciality_tip5);
        initData();
    }

    private void initData() {
        mOid = getIntent().getStringExtra(JAGLSSTAFFIOD);
        if (mOid == null) {
            finish();
        }
        LoadData(mOid);
    }

    /**
     * 加载工作者详情数据
     *
     * @param oid 工作者ID
     */
    private void LoadData(String oid) {
        if (getInProgess())
            return;
        setInProgess(true);
        JaglsService jaglsService = new JaglsService(mContext);
        jaglsService.setProgressShowContent(getString(R.string.get_ing));
        jaglsService.setShowProgress(true);
        jaglsService.getStaffDetail(oid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JaglsStaffDetailVO)) {
                    T.showLong(mContext, getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJaglsStaffDetailVO = (JaglsStaffDetailVO) values.get(0);

                initDetailView();

            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext, msg);
                setInProgess(false);
            }
        });
    }

    private void initDetailView() {
        mTvWorkerName.setText(mJaglsStaffDetailVO.getName() + mContext.getString(R.string.basic_lawyer));
        mTvWorkerOffice.setText(mJaglsStaffDetailVO.getJaglsOrganizationName());
        TextViewUtil.isEmpty(mTvPhone, mJaglsStaffDetailVO.getMobile());
        TextViewUtil.isEmpty(mTvServesMailbox, mJaglsStaffDetailVO.getEmail());
        BaseCommonDataVO baseCommonDataVO2 = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mJaglsStaffDetailVO.getProvince());
        String province = "";
        if (baseCommonDataVO2 != null) {
            province = baseCommonDataVO2.getName();
        }
        BaseCommonDataVO baseCommonDataVO = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mJaglsStaffDetailVO.getCity());
        String city = "";
        if (baseCommonDataVO != null) {
            city = baseCommonDataVO.getName();
        }
        BaseCommonDataVO baseCommonDataVO1 = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mJaglsStaffDetailVO.getCounty());
        String country = "";
        if (baseCommonDataVO1 != null) {
            country = baseCommonDataVO1.getName();
        }
        mTvAddress.setText(province + city + country + mJaglsStaffDetailVO.getAddress());
        StringBuffer major = new StringBuffer("");
//        for (int i = 0; i < mJaglsStaffDetailVO.getServices().size(); i++) {
//            major.append(BaseCommonToStringUtil.toString(mJaglsStaffDetailVO.getServices().get(i).getService()) + "  ");
//        }
//        mTvMajor.setText(major);
        String imageUrl = mJaglsStaffDetailVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext, imageUrl), mIvWorkerAvatar, ApplicationSet.CDIO_LAW);
        } else {
            mIvWorkerAvatar.setImageResource(R.drawable.ic_default_avatar);
        }
        if (mJaglsStaffDetailVO.getServices() != null && mJaglsStaffDetailVO.getServices().size() > 0) {
            for (int i = 0; i < mJaglsStaffDetailVO.getServices().size(); i++) {
                switch (i) {
                    case 0:
                        String str1 = BaseCommonToStringUtil.toString(mJaglsStaffDetailVO.getServices().get(0).getService());
                        if (str1 == null || str1.equals("")) {
                            mTvSpecialityTip1.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip1.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip1, str1);
                        }

                        break;
                    case 1:
                        String str2 = BaseCommonToStringUtil.toString(mJaglsStaffDetailVO.getServices().get(1).getService());
                        if (str2 == null || str2.equals("")) {
                            mTvSpecialityTip2.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip2.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip2, str2);
                        }
                        break;
                    case 2:
                        String str3 = BaseCommonToStringUtil.toString(mJaglsStaffDetailVO.getServices().get(2).getService());
                        if (str3 == null || str3.equals("")) {
                            mTvSpecialityTip3.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip3.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip3, str3);
                        }
                        break;
                    case 3:
                        String str4 = BaseCommonToStringUtil.toString(mJaglsStaffDetailVO.getServices().get(3).getService());
                        if (str4 == null || str4.equals("")) {
                            mTvSpecialityTip4.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip4.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip4, str4);
                        }
                        break;
                    case 4:
                        String str5 = BaseCommonToStringUtil.toString(mJaglsStaffDetailVO.getServices().get(4).getService());
                        if (str5 == null || str5.equals("")) {
                            mTvSpecialityTip5.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip5.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip5, str5);
                        }
                        break;
                }
            }
        }
        mRvWorker.setVisibility(View.VISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        mRvWorker.setLayoutManager(gridLayoutManager);
        mAdpater = new ServiceWorkerAdpater(mContext, mJaglsStaffDetailVO, mTvAddress.getText().toString());
        mRvWorker.setAdapter(mAdpater);
    }



}
