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
import com.lawyee.apppublic.adapter.LawyerDetailAdpater;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.BaseCommonToStringUtil;
import com.lawyee.apppublic.util.TextViewUtil;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JalawLawyerDetailVO;
import com.lawyee.apppublic.vo.JalawLawyerVO;
import com.lawyee.apppublic.vo.UserVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

import static com.lawyee.apppublic.R.id.tv_lawyer_name;
import static com.lawyee.apppublic.ui.lawyerService.SessionActivity.CSTR_EXTRA_SESSION_STR;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertTiptoLogin;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertToLogin;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @Title: 标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description: 律师详情页面
 * @author:czq
 * @date: 2017/5/31
 * @verdescript 2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class LawyerDetailActivity extends BaseActivity {
    public final static String JALAWLAWYERVO = "JalawLawyerVO";
    private ImageView mIvLawyerAvatar;
    private TextView mTvConsult;
    private TextView mTvLawyerName;
    private TextView mTvOffice;
    private TextView mTvArea1;
    private TextView mTvSpecialityTip1;
    private TextView mTvSpecialityTip2;
    private TextView mTvSpecialityTip3;
    private TextView mTvSpecialityTip4;
    private TextView mTvSpecialityTip5;
    private RecyclerView mRvLawyer;
    private LawyerDetailAdpater mAdpater;
    private JalawLawyerDetailVO mJalawLawyerDetailVO;
    private JalawLawyerVO mJalawLawyerVO;
    private Context mContext;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lawyer_detail);
        mContext = this;
        initView();
    }

    private void initView() {
        mIvLawyerAvatar = (ImageView) findViewById(R.id.iv_lawyer_avatar);
        mTvConsult = (TextView) findViewById(R.id.tv_consult);
        mTvLawyerName = (TextView) findViewById(tv_lawyer_name);
        mTvOffice = (TextView) findViewById(R.id.tv_office);
        mTvArea1 = (TextView) findViewById(R.id.tv_area1);
        mTvSpecialityTip1 = (TextView) findViewById(R.id.tv_speciality_tip1);
        mTvSpecialityTip2 = (TextView) findViewById(R.id.tv_speciality_tip2);
        mTvSpecialityTip3 = (TextView) findViewById(R.id.tv_speciality_tip3);
        mTvSpecialityTip4 = (TextView) findViewById(R.id.tv_speciality_tip4);
        mTvSpecialityTip5 = (TextView) findViewById(R.id.tv_speciality_tip5);
        mRvLawyer = (RecyclerView) findViewById(R.id.rv_lawyer);
        initData();
        String imageUrl = mJalawLawyerVO.getPhoto();
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(mContext, imageUrl), mIvLawyerAvatar, ApplicationSet.CDIO_LAW);
        } else {
            mIvLawyerAvatar.setImageResource(R.drawable.ic_default_avatar);
        }
        mTvLawyerName.setText(mJalawLawyerVO.getName() + "    " + mContext.getString(R.string.lawyer));
        mTvOffice.setText(mJalawLawyerVO.getLawfirmName());
        BaseCommonDataVO baseCommonDataVO = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mJalawLawyerVO.getCity());
        String city = "";
        if (baseCommonDataVO != null) {
            city = baseCommonDataVO.getName();
        }
        BaseCommonDataVO baseCommonDataVO1 = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), mJalawLawyerVO.getCounty());
        String country = "";
        if (baseCommonDataVO1 != null) {
            country = baseCommonDataVO1.getName();
        }
        mTvArea1.setText(city + " " + country);
        if (mJalawLawyerVO.getBusiness() != null && mJalawLawyerVO.getBusiness().size() > 0) {
            for (int i = 0; i < mJalawLawyerVO.getBusiness().size(); i++) {
                switch (i) {
                    case 0:
                        String str1 = BaseCommonToStringUtil.toString(mJalawLawyerVO.getBusiness().get(0).getBusiness());
                        if (str1 == null || str1.equals("")) {
                            mTvSpecialityTip1.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip1.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip1, str1);
                        }

                        break;
                    case 1:
                        String str2 = BaseCommonToStringUtil.toString(mJalawLawyerVO.getBusiness().get(1).getBusiness());
                        if (str2 == null || str2.equals("")) {
                            mTvSpecialityTip2.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip2.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip2, str2);
                        }
                        break;
                    case 2:
                        String str3 = BaseCommonToStringUtil.toString(mJalawLawyerVO.getBusiness().get(2).getBusiness());
                        if (str3 == null || str3.equals("")) {
                            mTvSpecialityTip3.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip3.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip3, str3);
                        }
                        break;
                    case 3:
                        String str4 = BaseCommonToStringUtil.toString(mJalawLawyerVO.getBusiness().get(3).getBusiness());
                        if (str4 == null || str4.equals("")) {
                            mTvSpecialityTip4.setVisibility(View.GONE);
                        } else {
                            mTvSpecialityTip4.setVisibility(View.VISIBLE);
                            TextViewUtil.isEmpty(mTvSpecialityTip4, str4);
                        }
                        break;
                    case 4:
                        String str5 = BaseCommonToStringUtil.toString(mJalawLawyerVO.getBusiness().get(4).getBusiness());
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

        mTvConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserVO userVO = ApplicationSet.getInstance().getUserVO();
                if(userVO==null|| StringUtil.isEmpty(userVO.getLoginId())) {
                        alertToLogin(mContext);

                }else {
                    if(!userVO.isPublicUser()){
                            alertTiptoLogin(mContext);
                    }else{
                            Intent intent = new Intent(mContext, SessionActivity.class);
                            intent.putExtra(CSTR_EXTRA_TITLE_STR, mJalawLawyerVO.getName());
                            intent.putExtra(CSTR_EXTRA_SESSION_STR, mJalawLawyerVO.getOid());
                            startActivity(intent);
                    }

                }
            }
        });
    }

    //初始化数据
    private void initData() {
        mJalawLawyerVO = (JalawLawyerVO) getIntent().getSerializableExtra(JALAWLAWYERVO);
        if (mJalawLawyerVO == null) {
            finish();
        }
        LoadData(mJalawLawyerVO.getOid());
    }

    /**
     * 加载律师详情数据
     *
     * @param id 律师ID
     */
    private void LoadData(String id) {
        if (getInProgess())
            return;
        setInProgess(true);
        JalawService service = new JalawService(mContext);
        service.setProgressShowContent(getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getLawyerDetail(id, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JalawLawyerDetailVO)) {
                    T.showLong(mContext, getString(R.string.get_error_noeffectdata));
                    return;
                }
                mJalawLawyerDetailVO = (JalawLawyerDetailVO) values.get(0);
                mRvLawyer.setVisibility(View.VISIBLE);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
                mRvLawyer.setLayoutManager(gridLayoutManager);
                mAdpater = new LawyerDetailAdpater(mContext, mJalawLawyerDetailVO);
                mRvLawyer.setAdapter(mAdpater);
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext, msg);
                setInProgess(false);
            }
        });
    }


}
