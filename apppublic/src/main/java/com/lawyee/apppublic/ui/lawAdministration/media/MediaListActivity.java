package com.lawyee.apppublic.ui.lawAdministration.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.JamedServiceListAdapter;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedUserService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.lawAdministration.ShowInfomActivity;
import com.lawyee.apppublic.vo.JamedApplyVO;
import com.lawyee.apppublic.vo.JamedOrgVO;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

public class MediaListActivity extends BaseActivity {
    /**
     * 列表缓存标示
     */
    private static final String SAVELISTDATAS = "MediaListActivity";
    private RecyclerView mRlvMedia;
    private XRefreshView mXrefreshView;
    private TextView mMediaContentEmptyTv;
    /**
     * 数据列表
     */
    @SuppressWarnings("rawtypes")
    protected ArrayList mDataList;
    private Context mContext;
    private JamedServiceListAdapter mRlvAdapter;
    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    boolean mInProgess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);
        initView();
        initRefresh();
        loadData();
    }

    private void loadData() {
        clearDataList();
        //读取缓存
        List list = JamedOrgVO.loadVOList(JamedOrgVO.dataListFileName(this, SAVELISTDATAS));
        if (list != null && !list.isEmpty()) {
            addDataList(list);
        } else {
            handlerRequestService(1, true);
        }
        setAdapterData();
        Boolean mustRefresh = true;
        //判断是否有效期
        if (mDataList != null && !mDataList.isEmpty()) {
            JamedOrgVO vo;
            Object o = mDataList.get(0);
            if (o instanceof JamedOrgVO) {
                vo = (JamedOrgVO) o;
                mXrefreshView.restoreLastRefreshTime(vo.getVoCreateDate().getTime());
                if (vo.isEffectiveTimeData(Constants.CINT_EFFECTIVE_NEWS_TIME)) {
                    mustRefresh = false;
                }
            }
            if (mDataList.size() % Constants.CINT_PAGE_SIZE == 0) {
                //设置是否可以上拉加载
                mXrefreshView.setPullLoadEnable(true);
            }

        }
        if (mustRefresh) {
            mXrefreshView.stopRefresh();
        }
    }

    private void handlerRequestService(int i, boolean isfirst) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.setShowProgress(isfirst);
        jamedUserService.getApplyList(i, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                Log.e(TAG, "onComplete: " + values + "///" + content);
                mInProgess = false;
                mXrefreshView.stopRefresh();
                if (values == null || values.isEmpty()) {
                    T.showShort(getApplication(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                clearDataList();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                } else {
                    mXrefreshView.setLoadComplete(true);
                    mRlvAdapter.notifyDataSetChanged();
                    return;
                }
                //缓存数据
                JamedApplyVO.saveVOList(mDataList, JamedApplyVO.dataListFileName(getApplicationContext(), SAVELISTDATAS));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PAGE_SIZE == 0) {
                    //设置是否可以上拉加载
                    mXrefreshView.setPullLoadEnable(true);
                    mXrefreshView.setLoadComplete(false);
                } else
                    mXrefreshView.setLoadComplete(true);
                mRlvAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String msg, String content) {
                mInProgess = false;
                mXrefreshView.stopRefresh();
                T.showShort(getApplicationContext(), msg);
            }
        });
    }


    private void initRefresh() {
        //设置是否能上拉刷新
        mXrefreshView.setPullLoadEnable(false);
        //设置是否下拉刷新
        mXrefreshView.setPullRefreshEnable(true);
        mXrefreshView.restoreLastRefreshTime(0l);
        View view = findViewById(R.id.media_content_empty_tv);
        mXrefreshView.setEmptyView(view);
        mXrefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                LoadNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreDatas();
            }
        });
    }

    private void loadMoreDatas() {
        if (mInProgess) {
            return;
        }
        mInProgess = true;
        JamedUserService service = new JamedUserService(mContext);
        service.getApplyList(getNowPage() + 1, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                if (values == null || values.isEmpty()) {
                    mXrefreshView.setLoadComplete(true);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                } else {
                    mXrefreshView.setLoadComplete(true);
                    mRlvAdapter.notifyDataSetChanged();
                    return;
                }
                //缓存数据
                JamedApplyVO.saveVOList(mDataList, JamedApplyVO.dataListFileName(getApplicationContext(), SAVELISTDATAS));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PAGE_SIZE == 0) {
                    //设置是否可以上拉加载
                    mXrefreshView.setPullLoadEnable(true);
                    mXrefreshView.setLoadComplete(false);
                } else {
                    mXrefreshView.setLoadComplete(true);
                }
                mRlvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                mInProgess = false;
                mXrefreshView.stopLoadMore();
                T.showShort(getApplicationContext(), msg);
            }
        });
    }

    private void LoadNewData() {
        if (mInProgess)
            return;
        mInProgess = true;
        handlerRequestService(1, false);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_media_list);
        initView();
    }

    private void initView() {
        mContext = this;
        mRlvMedia = (RecyclerView) findViewById(R.id.rlv_media);
        mXrefreshView = (XRefreshView) findViewById(R.id.xrefreshview_media);
        mMediaContentEmptyTv = (TextView) findViewById(R.id.media_content_empty_tv);
    }

    /**
     * 清除数据
     */
    private void clearDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList();
        } else {
            mDataList.clear();
        }
    }

    /**
     * 增加列表数据
     */
    private void addDataList(List<?> list) {
        if (mDataList == null) {
            clearDataList();
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        mDataList.addAll(list);
    }

    private void setAdapterData() {
        mRlvAdapter = new JamedServiceListAdapter(mDataList, this, "");
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        mRlvMedia.setLayoutManager(manager);
        mRlvMedia.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mRlvMedia.setAdapter(mRlvAdapter);
        mRlvAdapter.setOnRecyclerViewItemChickListener(new JamedServiceListAdapter.OnRecyclerViewItemChickListener() {
            @Override
            public void OnItemChickListener(View view, JamedApplyVO vo) {
                Intent intent = new Intent(mContext, ShowInfomActivity.class);
                intent.putExtra(ShowInfomActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.media_mediation));
                intent.putExtra(ShowInfomActivity.CONTENT_PARAMETER_TYPE, ShowInfomActivity.CONTENT_PARAMETER_MEDIA);

                Bundle bundle = new Bundle();
                bundle.putString(ShowInfomActivity.CONTENT_PARAMETER_MEDIAAPPLYTYPE, vo.getMediaApplyType());
                bundle.putString(ShowInfomActivity.CSTR_EXTRA_ORGID_ID, vo.getOid());
                bundle.putBoolean(ShowInfomActivity.CONTENT_PARAMETER_MEDIAFLAG, vo.isMediaFlag());
                bundle.putInt(ShowInfomActivity.CONTENT_PARAMETER_MEDIACONFIRM, vo.getMediaConfirm());
                bundle.putString(ShowInfomActivity.CONTENT_PARAMETER_STATUS, vo.getStatus());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mDataList == null || mDataList.isEmpty())
            return 0;
        if (mDataList.size() % Constants.CINT_PAGE_SIZE == 0)
            return mDataList.size() / Constants.CINT_PAGE_SIZE;
        else
            return mDataList.size() / Constants.CINT_PAGE_SIZE + 1;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mXrefreshView != null)
            mXrefreshView.startRefresh();
    }
}
