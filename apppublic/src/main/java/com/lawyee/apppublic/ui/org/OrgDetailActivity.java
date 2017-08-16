package com.lawyee.apppublic.ui.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.InformationViewPageAdapter;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.InformationFragment;
import com.lawyee.apppublic.ui.frag.OrgInformationFragment;
import com.lawyee.apppublic.ui.frag.OrgMemberFragment;
import com.lawyee.apppublic.util.ObjectToList;
import com.lawyee.apppublic.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JaaidOrgDetailActivity.java
 * @Package com.lawyee.apppublic.ui.org
 * @Description: 机构详情页
 * @author: YFL
 * @date: 2017/5/23 11:26
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/23 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class OrgDetailActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 传入参数(机构id)
     */
    public static final String CSTR_EXTRA_ORGID_ID = "orgid";
    /**
     * 传入参数(类型)
     */
    public static final String CSTR_EXTRA_DETAIL_TYEP = "detailtype";
    /**
     * 传入参数 - 人民调解机构
     */
    public static final String CSTR_EXTRA_JAEMDDETAIL_TYPE = "jamedtype";

    /**
     * 传入参数 - 鉴定机构
     */
    public static final String CSTR_EXTRA_JAAUTHDETAIL_TYPE = "jaauthtype";
    /**
     * 传入参数 - 公证机构
     */
    public static final String CSTR_EXTRA_JANOTADETAIL_TYPE = "janotatype";
    /**
     * 传入参数- 法援机构
     */
    public static final String CSTR_EXTRA_JAAIDDETAIL_TYPE = "jaaidtype";
    /**
     * 传入参数- 人民调解申请查看页
     */
    public static final String CSTR_EXTRA_JAMEDSERVICELOOK_TYPE = "jamedServiceLook";
    private ArrayList<Fragment> fragments;
    private TabLayout mTabOrgLayout;
    private List<String> mTabList;
    private NoScrollViewPager mViewpagerOrg;
    private String mDetailType;
    private String mOrgId;
    private Context mContext;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jaaid_org_detail);
        mContext = getApplicationContext();
        initView();
        Intent intent = getIntent();
        mDetailType = intent.getStringExtra(CSTR_EXTRA_DETAIL_TYEP);
        mOrgId = intent.getStringExtra(CSTR_EXTRA_ORGID_ID);
        handlerShowView(mDetailType);
    }
    private void handlerShowView(String mTitle) {

        if (!TextUtils.isEmpty(mTitle)) {
            if (mTitle.equals(CSTR_EXTRA_JAAIDDETAIL_TYPE)) {//法律援助机构
                initJaaidData();
            } else if (mTitle.equals(CSTR_EXTRA_JANOTADETAIL_TYPE)) {//公证机构
                initjanotaData();
            } else if (mTitle.equals(CSTR_EXTRA_JAEMDDETAIL_TYPE)) {//人民调解机构
                initjamedData();
            } else if (mTitle.equals(CSTR_EXTRA_JAAUTHDETAIL_TYPE)) {//鉴定机构
                initjaauthData();
            }
        }

    }

    /**
     * 鉴定机构
     */
    private void initjaauthData() {
        if (mTabList == null)
            mTabList = ObjectToList.ArrayToList(mContext, R.array.jaauthorgtab);
        fragments = new ArrayList<>();
        OrgInformationFragment org_information_fragment = new OrgInformationFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_TYPE_TYPE, OrgInformationFragment.CSTR_EXTRA_JAAUTH_TYPE);
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_INFOTYPEID_OID, mOrgId);
        org_information_fragment.setArguments(bundle1);
        OrgMemberFragment orgFragment = new OrgMemberFragment();
        Bundle bundle = new Bundle();
        bundle.putString(OrgMemberFragment.CSTR_EXTRA_TYPE_TYPE, OrgMemberFragment.CSTR_EXTRA_TYPE_JAAUTHSTAFF);
        bundle.putString(OrgMemberFragment.CSTR_EXTRA_INFOTYPEID_OID, mOrgId);
        orgFragment.setArguments(bundle);
        fragments.add(org_information_fragment);
        fragments.add(orgFragment);
        initViewPager();
    }

    /**
     * 人民调解机构
     */
    private void initjamedData() {
        if (mTabList == null)
            mTabList = ObjectToList.ArrayToList(mContext, R.array.jamedorgtab);
        fragments = new ArrayList<>();
        OrgInformationFragment org_information_fragment = new OrgInformationFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_TYPE_TYPE, OrgInformationFragment.CSTR_EXTRA_JAMED_TYPE);
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_INFOTYPEID_OID, mOrgId);
        org_information_fragment.setArguments(bundle1);
        OrgMemberFragment orgFragment = new OrgMemberFragment();
        Bundle bundle = new Bundle();
        bundle.putString(OrgMemberFragment.CSTR_EXTRA_TYPE_TYPE, OrgMemberFragment.CSTR_EXTRA_TYPE_JAMEDSTAFF);
        bundle.putString(OrgMemberFragment.CSTR_EXTRA_INFOTYPEID_OID, mOrgId);
        orgFragment.setArguments(bundle);
        fragments.add(org_information_fragment);
        fragments.add(orgFragment);
        initViewPager();
    }

    /**
     * 公证机构信息
     */
    private void initjanotaData() {
        if (mTabList == null)
            mTabList = ObjectToList.ArrayToList(mContext, R.array.janotaorgtab);
        fragments = new ArrayList<>();
        OrgInformationFragment org_information_fragment = new OrgInformationFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_TYPE_TYPE, OrgInformationFragment.CSTR_EXTRA_JANOTA_TYPE);
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_INFOTYPEID_OID, mOrgId);
        org_information_fragment.setArguments(bundle1);
        OrgMemberFragment orgFragment = new OrgMemberFragment();
        Bundle bundle = new Bundle();
        bundle.putString(OrgMemberFragment.CSTR_EXTRA_INFOTYPEID_OID, mOrgId);
        bundle.putString(OrgMemberFragment.CSTR_EXTRA_TYPE_TYPE, OrgMemberFragment.CSTR_EXTRA_TYPE_JANOTASTAFF);
        orgFragment.setArguments(bundle);
        InformationFragment org_work_fragment = new InformationFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(InformationFragment.CSTR_EXTRA_INFOTYPENAME_STR, getResources().getStringArray(R.array.GZFWORGInfoName)[0]);
        bundle2.putString(InformationFragment.CSTR_EXTRA_INFOTYPEID_STR, getResources().getStringArray(R.array.GZFWORGInfoID)[0]);
        bundle2.putString(InformationFragment.CSTR_EXTRA_ORGID_STR, mOrgId);
        org_work_fragment.setArguments(bundle2);
        fragments.add(org_information_fragment);
        fragments.add(orgFragment);
        fragments.add(org_work_fragment);
        initViewPager();

    }

    /**
     * 法援机构信息
     */
    private void initJaaidData() {
        if (mTabList == null) {
            mTabList = new ArrayList<>();
            mTabList = ObjectToList.ArrayToList(mContext, R.array.jaaidorgtab);
        }
        fragments = new ArrayList<>();
        OrgInformationFragment org_information_fragment = new OrgInformationFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_INFOTYPEID_OID, mOrgId);
        bundle1.putString(OrgInformationFragment.CSTR_EXTRA_TYPE_TYPE, OrgInformationFragment.CSTR_EXTRA_JAAID_TYPE);
        org_information_fragment.setArguments(bundle1);
        InformationFragment org_work_fragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPENAME_STR, getResources().getStringArray(R.array.FLYZORGInfoName)[0]);
        bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPEID_STR, getResources().getStringArray(R.array.FLYZORGInfoID)[0]);
        bundle.putString(InformationFragment.CSTR_EXTRA_ORGID_STR, mOrgId);
        org_work_fragment.setArguments(bundle);
        fragments.add(org_information_fragment);
        fragments.add(org_work_fragment);
        initViewPager();

    }

    private void initViewPager() {
        if (mTabList.size() < 2)
            mTabOrgLayout.setVisibility(View.GONE);
        else
            mTabOrgLayout.setVisibility(View.VISIBLE);
        InformationViewPageAdapter pageAdapter = new InformationViewPageAdapter(getSupportFragmentManager(), mTabList, fragments);
        mViewpagerOrg.setAdapter(pageAdapter);
        mTabOrgLayout.setupWithViewPager(mViewpagerOrg);
        mViewpagerOrg.setOffscreenPageLimit(3);
    }


    private void initView() {
        mTabOrgLayout = (TabLayout) findViewById(R.id.tab_org_layout);
        mTabOrgLayout.setOnClickListener(this);
        mViewpagerOrg = (NoScrollViewPager) findViewById(R.id.viewpager_org);
        mViewpagerOrg.setPagingEnabled(false);
        mViewpagerOrg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
