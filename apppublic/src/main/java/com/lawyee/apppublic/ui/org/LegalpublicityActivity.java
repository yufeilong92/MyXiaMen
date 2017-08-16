package com.lawyee.apppublic.ui.org;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.infom.InformationActivity;
import com.lawyee.apppublic.ui.org.japub.LegalActivityListsActivity;

import net.lawyee.mobilelib.utils.ScreenUtils;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LegalpublicityActivity.java
 * @Package com.lawyee.apppublic.ui.org
 * @Description: 法制宣传
 * @author: YFL
 * @date: 2017/7/10 14:41
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/7/10 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LegalpublicityActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageView;
    private LinearLayout mLiLegalInfom;
    private LinearLayout mLiLegalVideo;
    private LinearLayout mLiLagalActivity;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_legalpublicity);
        int screenHeight = ScreenUtils.getScreenHeight(this);
        initView();
        LinearLayout.LayoutParams InfomParams = (LinearLayout.LayoutParams) mLiLegalInfom.getLayoutParams();
        InfomParams.height = (int) (screenHeight * 0.28);
        mLiLegalInfom.setLayoutParams(InfomParams);
        LinearLayout.LayoutParams VideoParams = (LinearLayout.LayoutParams) mLiLegalVideo.getLayoutParams();
        VideoParams.height = (int) (screenHeight * 0.28);
        mLiLegalVideo.setLayoutParams(VideoParams);
        LinearLayout.LayoutParams Activityparams = (LinearLayout.LayoutParams) mLiLagalActivity.getLayoutParams();
        Activityparams.height = (int) (screenHeight * 0.20);
        mLiLagalActivity.setLayoutParams(Activityparams);
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mLiLegalInfom = (LinearLayout) findViewById(R.id.li_legal_infom);
        mLiLegalVideo = (LinearLayout) findViewById(R.id.li_legal_video);
        mLiLagalActivity = (LinearLayout) findViewById(R.id.li_lagal_activity);
        mLiLagalActivity.setOnClickListener(this);
        mLiLegalInfom.setOnClickListener(this);
        mLiLegalVideo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_legal_infom:
                Intent infom = new Intent(LegalpublicityActivity.this, InformationActivity.class);
                infom.putExtra(CSTR_EXTRA_TITLE_STR, getString(R.string.legal_law_infom_));
                infom.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPENAME_STRARRAY,getResources().getStringArray(R.array.LEGALIOFOM));
                infom.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPEID_STRARRAY,getResources().getStringArray(R.array.LEGALINFOMWITHOID));
                startActivity(infom);
                break;
            case R.id.li_legal_video:
                Intent video = new Intent(LegalpublicityActivity.this, InformationActivity.class);
                video.putExtra(CSTR_EXTRA_TITLE_STR, getString(R.string.legal_law_video));
                video.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPENAME_STRARRAY, getResources().getStringArray(R.array.LEGALVIDEO));
                video.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPEID_STRARRAY, getResources().getStringArray(R.array.LEGALVIDEOWITHOID));
                startActivity(video);
                break;
            case R.id.li_lagal_activity:
                Intent activity = new Intent(LegalpublicityActivity.this, LegalActivityListsActivity.class);
                activity.putExtra(CSTR_EXTRA_TITLE_STR, getString(R.string.lega_activities));
                startActivity(activity);
                break;
            default:
                break;
        }
    }
}
