package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.MyEntrustAdpater;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawUserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JalawLawyerEntrustVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    律师端——我的委托
 * @author:lzh
 * @date:   2017/8/9
 * @version
 * @verdescript   2017/8/9  lzh 初建
 * @Copyright: 2017/8/9 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawyerEntrustActivity extends BaseActivity {
    private RecyclerView mRvEntrust;
    private MyEntrustAdpater mAdpater;
    private GridLayoutManager mLayoutManager;
    private Context mContext;
    private ArrayList<JalawLawyerEntrustVO> mJalawLawyerEntrustVOs = new ArrayList<>();//律师委托VOList
    private XRefreshView mXrefreshview;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lawyer_entrust);
        mContext = this;
        initView();
        setReAdpater();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mXrefreshview.startRefresh();
        loadNewData();
    }

    private void initView() {
        mRvEntrust = (RecyclerView) findViewById(R.id.my_entrust_rv);
        mXrefreshview = (XRefreshView) findViewById(R.id.my_entrust_xrefreshview);
        // 设置是否可以下拉刷新
        mXrefreshview.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mXrefreshview.setPullLoadEnable(false);
        mXrefreshview.restoreLastRefreshTime(0l);
     /*   mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);*/
        mXrefreshview.setEmptyView(findViewById(R.id.my_entrust_empty_tv));

    }
    private void setReAdpater() {
        mAdpater = new MyEntrustAdpater(mJalawLawyerEntrustVOs, mContext,true);
        mAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRvEntrust.setLayoutManager(mLayoutManager);
        mAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mRvEntrust.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRvEntrust.setAdapter(mAdpater);
        mXrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }



            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
            }
        });
    }


    private void loadNewData() {
        if(getInProgess())
            return;
        setInProgess(true);
        JalawUserService service = new JalawUserService(mContext);
        service.getEntrustList(1,new BaseJsonService.IResultInfoListener() {
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
                if(!mJalawLawyerEntrustVOs.isEmpty()&&mJalawLawyerEntrustVOs.size()% Constants.CINT_PAGE_SIZE==0) {
                    // 设置是否可以上拉加载
                    mXrefreshview.setPullLoadEnable(true);
                    mXrefreshview.setLoadComplete(false);
                }
                else
                    mXrefreshview.setLoadComplete(true);
                mAdpater.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(mContext,msg);
                setInProgess(false);
                mXrefreshview.stopRefresh();
            }
        });
    }
    private void loadMoreData() {
        if(getInProgess())
            return;
        setInProgess(true);
        JalawUserService service = new JalawUserService(mContext);
        service.getEntrustList(getNowPage()+1,new BaseJsonService.IResultInfoListener() {
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
                if(!mJalawLawyerEntrustVOs.isEmpty()&&mJalawLawyerEntrustVOs.size()% Constants.CINT_PAGE_SIZE==0) {
                    // 设置是否可以上拉加载
                    mXrefreshview.setPullLoadEnable(true);
                    mXrefreshview.setLoadComplete(false);
                }
                else
                    mXrefreshview.setLoadComplete(true);
                mAdpater.notifyDataSetChanged();
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
        if (mJalawLawyerEntrustVOs == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mJalawLawyerEntrustVOs.addAll((Collection<? extends JalawLawyerEntrustVO>) list);
    }

    /**
     * 清除数据
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void clearDataList() {
        if (mJalawLawyerEntrustVOs == null) {
            mJalawLawyerEntrustVOs = new ArrayList();
        } else
            mJalawLawyerEntrustVOs.clear();
    }
    /**
     * 当前数据有几页
     * @return
     */
    private int getNowPage()
    {
        if(mJalawLawyerEntrustVOs==null||mJalawLawyerEntrustVOs.isEmpty())
            return 0;
        if(mJalawLawyerEntrustVOs.size()% Constants.CINT_PAGE_SIZE==0)
            return mJalawLawyerEntrustVOs.size()/Constants.CINT_PAGE_SIZE;
        else
            return mJalawLawyerEntrustVOs.size()/Constants.CINT_PAGE_SIZE+1;
    }
}
