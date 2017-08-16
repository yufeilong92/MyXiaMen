package com.lawyee.apppublic.ui.personalcenter.lawyer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.MyConsultListAdpater;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JacstService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.JacstConsulationVO;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  我的咨询
 * @Package com.lawyee.apppublic.ui.personalcenter.lawyer
 * @Description:    注释
 * @author:czq
 * @date:   2017/8/8
 * @version
 * @verdescript   2017/8/8  czq 初建
 * @Copyright: 2017/8/8 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ConsultListActivity extends BaseActivity {


    private RecyclerView mRv;
    private XRefreshView mXrefreshview;
    private GridLayoutManager mLayoutManager;
    private Context mContext;
    private ArrayList<JacstConsulationVO> mJacstConsulationVOs = new ArrayList<>();//调解申请VOList
    private MyConsultListAdpater mAdpater;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_consult_list);
        mContext = this;
        initView();
        setReAdpater();
        mXrefreshview.startRefresh();
        loadNewData();
    }
    private void setReAdpater() {
        mAdpater = new MyConsultListAdpater(mJacstConsulationVOs, mContext);
        mAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRv.setLayoutManager(mLayoutManager);
        mAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        mRv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRv.setAdapter(mAdpater);
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
        JacstService service = new JacstService(mContext);
        service.getConsulationList(1,new BaseJsonService.IResultInfoListener() {
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
                if(!mJacstConsulationVOs.isEmpty()&&mJacstConsulationVOs.size()% Constants.CINT_PAGE_SIZE==0) {
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
        JacstService service = new JacstService(mContext);
        service.getConsulationList(getNowPage()+1,new BaseJsonService.IResultInfoListener() {
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
                if(!mJacstConsulationVOs.isEmpty()&&mJacstConsulationVOs.size()% Constants.CINT_PAGE_SIZE==0) {
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



    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        mXrefreshview= (XRefreshView) findViewById(R.id.xrefreshview);
        // 设置是否可以下拉刷新
        mXrefreshview.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mXrefreshview.setPullLoadEnable(false);
        mXrefreshview.restoreLastRefreshTime(0l);
     /*   mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);*/
        mXrefreshview.setEmptyView(findViewById(R.id.my_consult_empty_tv));
    }
    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({ "unchecked" })
    private void addDataList(List<?> list) {
        if (mJacstConsulationVOs == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mJacstConsulationVOs.addAll((Collection<? extends JacstConsulationVO>) list);
    }

    /**
     * 清除数据
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void clearDataList() {
        if (mJacstConsulationVOs == null) {
            mJacstConsulationVOs = new ArrayList();
        } else
            mJacstConsulationVOs.clear();
    }
    /**
     * 当前数据有几页
     * @return
     */
    private int getNowPage()
    {
        if(mJacstConsulationVOs==null||mJacstConsulationVOs.isEmpty())
            return 0;
        if(mJacstConsulationVOs.size()% Constants.CINT_PAGE_SIZE==0)
            return mJacstConsulationVOs.size()/Constants.CINT_PAGE_SIZE;
        else
            return mJacstConsulationVOs.size()/Constants.CINT_PAGE_SIZE+1;
    }

}
