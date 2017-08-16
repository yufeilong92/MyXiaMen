package com.lawyee.apppublic.ui.basiclaw;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.BasicWorkerAdpater;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaglsService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JaglsStaffVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.lawyee.apppublic.ui.basiclaw.ServicePlaceDetailActivity.JAGLSORGIOD;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.basiclaw
 * @Description:    本所工作者
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaglsStaffOfOrgActivity extends BaseActivity {


    private RecyclerView mRvContent;
    private List<JaglsStaffVO> mJaglsStaffVOs = new ArrayList<>();
    private BasicWorkerAdpater mLawyerAdpater;
    private GridLayoutManager mLayoutManager;
    private Context mContext;
    private TextView mLawyerListEmptyTv;
    private String mOid;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jagls_staff_of_org);
        mContext = this;
        initView();
        loadData(mOid);
    }

    private void loadData(String id) {
        if (getInProgess())
            return;
        setInProgess(true);
        JaglsService service = new JaglsService(mContext);
        service.setProgressShowContent(getString(R.string.get_ing));
        service.setShowProgress(true);
        service.getStaffWithOrg(id, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if (values == null || values.isEmpty()) {
                    T.showShort(mContext, content);
                    return;
                }
                ArrayList list = (ArrayList) values
                        .get(0);
                if(list==null||list.size()==0){
                    mRvContent.setVisibility(View.GONE);
                    mLawyerListEmptyTv.setVisibility(View.VISIBLE);
                    return;
                }
                clearDataList();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }
                mLawyerAdpater.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext, msg);
                setInProgess(false);
            }
        });
    }

    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({"unchecked"})
    private void addDataList(List<?> list) {
        if (mJaglsStaffVOs == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mJaglsStaffVOs.addAll((Collection<? extends JaglsStaffVO>) list);
    }

    /**
     * 清除数据
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void clearDataList() {
        if (mJaglsStaffVOs == null) {
            mJaglsStaffVOs = new ArrayList();
        } else
            mJaglsStaffVOs.clear();
    }


    private void initView() {
        mOid= getIntent().getStringExtra(JAGLSORGIOD);
        if (mOid == null) {
            finish();
        }
        mRvContent = (RecyclerView) findViewById(R.id.lawyer_list_rv);
        mLawyerListEmptyTv= (TextView) findViewById(R.id.lawyer_list_empty_tv);
        initRecycleView();
    }

    private void initRecycleView() {
        mLawyerAdpater = new BasicWorkerAdpater(mContext, mJaglsStaffVOs);
        mRvContent.setAdapter(mLawyerAdpater);
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRvContent.setLayoutManager(mLayoutManager);
        mRvContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

    }


}


