package com.lawyee.apppublic.ui.org;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.InformationViewPageAdapter;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.BackHandledFragment;
import com.lawyee.apppublic.ui.frag.BackHandlerInterface;
import com.lawyee.apppublic.ui.frag.InformationFragment;
import com.lawyee.apppublic.ui.frag.JaauthOrgFragment;
import com.lawyee.apppublic.ui.frag.JanotaOrgFragment;
import com.lawyee.apppublic.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JanotaOrgActivity.java
 * @Package com.lawyee.apppublic.ui.org
 * @Description: 公证服务
 * @author: YFL
 * @date: 2017/5/23 17:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/23 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JanotaOrgActivity extends BaseActivity implements View.OnClickListener, BackHandlerInterface {

    /**
     * 传入机构类型
     */
    public static final String JANOTATYPE = "Janotatype";
    /**
     * 调用机构传参内容
     * 0  公证机构
     * 1  鉴定机构
     */
    public static final String JANOTATITLEZONE = "0";
    public static final String JANOTATITLEONE = "1";
    /**
     * fragment 回调返回按钮接口
     */
    private BackHandledFragment backHandledFragment;
    private TabLayout mTabJanotaorg;
    private NoScrollViewPager mViewpageOrg;
    /**
     * 要展示的碎片布局集合
     */
    private List<Fragment> fragments = new ArrayList<>();
    /**
     * tab list 集合
     */
    private List<String> titles = new ArrayList<>();

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_janota_org);
        Intent intent = getIntent();
        String mIntentType = intent.getStringExtra(JANOTATYPE);
        initView();
        selectView(mIntentType);
    }
    /**
     * 选择处理显示不同view
     *
     * @param type
     */
    private void selectView(String type) {
        if (type.isEmpty())
            return;
        if (type.equals(JANOTATITLEZONE)) {//公证机构
            titles.add(getResources().getString(R.string.janotaorg));
            titles.add(getResources().getString(R.string.notary_guide));
            initData(type);
        } else if (type.equals(JANOTATITLEONE)) { //司法鉴定
            titles.add(getResources().getString(R.string.org_authi));
            titles.add(getResources().getString(R.string.org_authi_work));
            titles.add(getResources().getString(R.string.org_authi_statute));
            initData(type);
        }
    }
    /**
     * 根据标题多少判断显示的布局
     * 2 是公证机构展示
     * 3 是鉴定机构展示
     *
     */
    private void initData(String type) {
        if (type.equals(JANOTATITLEZONE)) {
            JanotaOrgFragment janotaOrgFragment = new JanotaOrgFragment();
            InformationFragment caseFragment = new InformationFragment();
            Bundle bundle = new Bundle();
            bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPENAME_STR, getResources().getStringArray(R.array.GZFWInfoName)[0]);
            bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPEID_STR, getResources().getStringArray(R.array.GZFWInfoID)[0]);
            caseFragment.setArguments(bundle);
            fragments.add(janotaOrgFragment);
            fragments.add(caseFragment);
        } else if (type.equals(JANOTATITLEONE)) {
            JaauthOrgFragment jauuthOrgFragment = new JaauthOrgFragment();
            InformationFragment caseFragment = new InformationFragment();
            Bundle bundle = new Bundle();
            bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPENAME_STR, getResources().getStringArray(R.array.SFZDInfoName)[0]);
            bundle.putString(InformationFragment.CSTR_EXTRA_INFOTYPEID_STR, getResources().getStringArray(R.array.SFZDInfoID)[0]);
            caseFragment.setArguments(bundle);
            InformationFragment caseFragment1 = new InformationFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString(InformationFragment.CSTR_EXTRA_INFOTYPENAME_STR, getResources().getStringArray(R.array.SFZDInfoName)[1]);
            bundle1.putString(InformationFragment.CSTR_EXTRA_INFOTYPEID_STR, getResources().getStringArray(R.array.SFZDInfoID)[1]);
            caseFragment1.setArguments(bundle1);
            fragments.add(jauuthOrgFragment);
            fragments.add(caseFragment);
            fragments.add(caseFragment1);
        }
        InformationViewPageAdapter informationViewPageAdapter = new InformationViewPageAdapter(getSupportFragmentManager(), titles, fragments);
        mViewpageOrg.setAdapter(informationViewPageAdapter);
        mViewpageOrg.setPagingEnabled(false);
        mTabJanotaorg.setupWithViewPager(mViewpageOrg);
    }


    private void initView() {
        mTabJanotaorg = (TabLayout) findViewById(R.id.tab_janotaorg);
        mViewpageOrg = (NoScrollViewPager) findViewById(R.id.viewpage_org);
    }

    @Override
    public void setSelectedFragment(BackHandledFragment fragment) {
        this.backHandledFragment = fragment;
    }
    @Override
    public void onBackPressed() {
        if (backHandledFragment == null || !backHandledFragment.onBackPressed())
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
    }
}
