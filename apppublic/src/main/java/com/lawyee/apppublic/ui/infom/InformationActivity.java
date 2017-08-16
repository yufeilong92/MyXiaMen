package com.lawyee.apppublic.ui.infom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.InformationViewPageAdapter;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.InformationFragment;
import com.lawyee.apppublic.widget.NoScrollViewPager;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;


/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: InformationActivity.java
 * @Package com.lawyee.apppublic.ui
 * @Description: 资讯列表页
 * @author: YFL
 * @date: 2017/5/15 16:14
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/15 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class InformationActivity extends BaseActivity {
    private static  final String TAG = InformationActivity.class.getSimpleName();
    /**
     * 传入参数-资讯类别名称
     */
    public static final String CSTR_EXTRA_INFOTYPENAME_STRARRAY = "infotypename";
    /**
     * 传入参数-资讯类别ID
     */
    public static final String CSTR_EXTRA_INFOTYPEID_STRARRAY = "infotypeid";

    private TabLayout mTabInformalayout;
    private ArrayList<Fragment> mListfrag = new ArrayList<>();
    private NoScrollViewPager mViewpage;
    /**
     * 资讯名称
     */
    private String[] mInfoTypeName;
    /**
     * 资讯类型
     */
    private String[] mInfoTypeId;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_information);
        Intent intent = getIntent();
        mInfoTypeName = intent.getStringArrayExtra(CSTR_EXTRA_INFOTYPENAME_STRARRAY);
        mInfoTypeId = intent.getStringArrayExtra(CSTR_EXTRA_INFOTYPEID_STRARRAY);
        if(mInfoTypeName==null||mInfoTypeId==null||mInfoTypeName.length==0||
                mInfoTypeName.length!=mInfoTypeId.length)
        {
            //传入无效参数时
            T.showLong(this,"无效的资讯展示参数");
            return;
        }
        initViewWithInfoType();
    }

    private void initViewWithInfoType()
    {
        mTabInformalayout = (TabLayout) findViewById(R.id.tab_informalayout);
        mViewpage = (NoScrollViewPager) findViewById(R.id.viewpage);
        mViewpage.setPagingEnabled(false);
        if(mInfoTypeName.length<2)
            mTabInformalayout.setVisibility(View.GONE);
        else
            mTabInformalayout.setVisibility(View.VISIBLE);
        ArrayList<String> tabtitles = new ArrayList<>(mInfoTypeName.length);
        for(int i=0;i<mInfoTypeName.length;i++) {
            tabtitles.add(mInfoTypeName[i]);
            Fragment mfragment = new InformationFragment();
            Bundle bundle = new Bundle();
            bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPENAME_STR, mInfoTypeName[i]);
            bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPEID_STR,mInfoTypeId[i]);
            mfragment.setArguments(bundle);
            mListfrag.add(mfragment);
        }

        InformationViewPageAdapter pageAdapter = new InformationViewPageAdapter(getSupportFragmentManager(), tabtitles, mListfrag);
        mViewpage.setAdapter(pageAdapter);
        mTabInformalayout.setupWithViewPager(mViewpage);
    }
}
