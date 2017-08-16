package com.lawyee.apppublic.ui.lawyerService;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.LawyerListAdpater;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JalawLawyerVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @Title: 标题
 * @Package com.lawyee.apppublic.ui.lawyerService
 * @Description: 值班律师界面
 * @author:czq
 * @date: 2017/5/31
 * @verdescript 2017/5/31  czq 初建
 * @Copyright: 2017/5/31 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class DutyLawyersActivity extends BaseActivity {


    private TextView mTvDefaut;
    private TextView mTvExperience;
    private int mType;
    private RecyclerView mRvContent;
    private XRefreshView mXrefreshview;
    private List<JalawLawyerVO> mJalawLawyerVOs = new ArrayList<>();
    private Context mContext;
    private LawyerListAdpater mLawyerAdpater;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_duty_lawyers);
        mContext=this;
        initView();
        initRecycleView();
        loadNewData(mType);
    }



    private void initView() {
        mTvDefaut = (TextView) findViewById(R.id.tv_defaut);
        mTvExperience = (TextView) findViewById(R.id.tv_experience);
        mRvContent = (RecyclerView) findViewById(R.id.duty_lawters_rv);
        mXrefreshview= (XRefreshView) findViewById(R.id.duty_lawters_xrefreshview);
        // 设置是否可以下拉刷新
        mXrefreshview.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mXrefreshview.setPullLoadEnable(false);
        mXrefreshview.restoreLastRefreshTime(0l);
     /*   mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);*/
        mXrefreshview.setEmptyView(findViewById(R.id.duty_lawters_empty_tv));
        switchTab(0);
        mTvDefaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType != 0) {
                    mType = 0;
                    switchTab(0);
                    loadNewData(mType);
                }
            }
        });
        mTvExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType != 1) {
                    mType = 1;
                    switchTab(1);
                    loadNewData(mType);
                }
            }
        });

    }
    private void initRecycleView() {
        mLawyerAdpater = new LawyerListAdpater(mContext, mJalawLawyerVOs, 0);
        mLawyerAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRvContent.setLayoutManager(mLayoutManager);
        mLawyerAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mRvContent.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRvContent.setAdapter(mLawyerAdpater);
        mXrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData(mType);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData(mType);
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
            }
        });
    }



    private void switchTab(int type) {
        if (type == 0) {
            mTvDefaut.setSelected(true);
            mTvExperience.setSelected(false);
        } else {
            mTvDefaut.setSelected(false);
            mTvExperience.setSelected(true);
        }
    }

    private void loadNewData(int typing) {
        if(getInProgess())
            return;
        setInProgess(true);
        JalawService service = new JalawService(mContext);
        service.getDutyLawyerList(1,typing,new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                mXrefreshview.stopRefresh();
                if(values==null||values.isEmpty()) {
                    T.showShort(mContext,content);
                    return;
                }
                ArrayList list = (ArrayList) values
                        .get(0);
                clearDataList();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }
                if(!mJalawLawyerVOs.isEmpty()&&mJalawLawyerVOs.size()% Constants.CINT_PAGE_SIZE==0) {
                    // 设置是否可以上拉加载
                    mXrefreshview.setPullLoadEnable(true);
                    mXrefreshview.setLoadComplete(false);
                }
                else
                    mXrefreshview.setLoadComplete(true);
                   mLawyerAdpater.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
                mXrefreshview.stopRefresh();
            }
        });
    }
    private void loadMoreData(int type2) {
        if(getInProgess())
            return;
        setInProgess(true);
        JalawService service = new JalawService(mContext);
        service.getDutyLawyerList( getNowPage()+1,type2,new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                if(values==null||values.isEmpty()) {
                    mXrefreshview.setLoadComplete(true);
                    return;
                }
                ArrayList list = (ArrayList) values
                        .get(0);
                if(list==null||list.isEmpty()) {
                    mXrefreshview.setLoadComplete(true);
                    return;
                }
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }
                if(!mJalawLawyerVOs.isEmpty()&&mJalawLawyerVOs.size()% Constants.CINT_PAGE_SIZE==0) {
                    // 设置是否可以上拉加载
                    mXrefreshview.setPullLoadEnable(true);
                    mXrefreshview.setLoadComplete(false);
                }
                else
                    mXrefreshview.setLoadComplete(true);
                mLawyerAdpater.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
                mXrefreshview.stopLoadMore();
            }
        });
    }
    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({ "unchecked" })
    private void addDataList(List<?> list) {
        if (mJalawLawyerVOs == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mJalawLawyerVOs.addAll((Collection<? extends JalawLawyerVO>) list);
    }

    /**
     * 清除数据
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void clearDataList() {
        if (mJalawLawyerVOs == null) {
            mJalawLawyerVOs = new ArrayList();
        } else
            mJalawLawyerVOs.clear();
    }
    /**
     * 当前数据有几页
     * @return
     */
    private int getNowPage()
    {
        if(mJalawLawyerVOs==null||mJalawLawyerVOs.isEmpty())
            return 0;
        if(mJalawLawyerVOs.size()%Constants.CINT_PAGE_SIZE==0)
            return mJalawLawyerVOs.size()/Constants.CINT_PAGE_SIZE;
        else
            return mJalawLawyerVOs.size()/Constants.CINT_PAGE_SIZE+1;
    }
}
