package com.lawyee.apppublic.ui.org;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.JaaidOrgRlvAdapter;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaaidService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.JaaidApplyOtherInformationFragment;
import com.lawyee.apppublic.vo.JaaidOrgVO;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.ui.personalcenter
 * @Description: 申请页选择法援机构
 * @author: YFL
 * @date: 2017/6/10 9:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaaidSelectOrgActivity extends BaseActivity {
    /**
     * 市级机构id
     */
    public static final String SELECTORGOID = "selectorgoid";
    private RecyclerView mRlvSelectOrg;
    private String mOrgId;
    private ArrayList adapterData;
    private TextView mSelectContentEmptyTv;
    private JaaidOrgRlvAdapter adapter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jaaid_selectorg);
        initView();
        Intent intent = getIntent();
        mOrgId = intent.getStringExtra(SELECTORGOID);
        handlerRequestOrg();
    }

    private void handlerRequestOrg() {
        JaaidService jaaidService = new JaaidService(this);
        jaaidService.setShowProgress(true);
        jaaidService.setProgressShowContent(getString(R.string.loading));
        jaaidService.queryOrgList(1, Constants.PROVINCE_GUIZHOU_ID, mOrgId, null, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(getApplicationContext(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                if (list == null || list.isEmpty()){
                    mRlvSelectOrg.setVisibility(View.GONE);
                    mSelectContentEmptyTv.setVisibility(View.VISIBLE);
                    return;
                }
                setAdapterData(list);
            }

            @Override
            public void onError(String msg, String content) {

            }
        });
    }

    private void initView() {
        mRlvSelectOrg = (RecyclerView) findViewById(R.id.rlv_select_org);
        mSelectContentEmptyTv= (TextView) findViewById(R.id.select_content_empty_tv);
    }

    public void setAdapterData(ArrayList list) {
        adapter = new JaaidOrgRlvAdapter(list, this);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        manager.setOrientation(GridLayout.VERTICAL);
        mRlvSelectOrg.setLayoutManager(manager);
        mRlvSelectOrg.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvSelectOrg.setAdapter(adapter);

        adapter.setItemClickListener(new JaaidOrgRlvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void ItemClickListenet(View view, JaaidOrgVO vo, int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(JaaidApplyOtherInformationFragment.ORGNAME, vo.getName());
                bundle.putString(JaaidApplyOtherInformationFragment.ORGOID, vo.getOid());
                intent.putExtras(bundle);
                setResult(1000, intent);
                finish();
            }
        });
    }


}
