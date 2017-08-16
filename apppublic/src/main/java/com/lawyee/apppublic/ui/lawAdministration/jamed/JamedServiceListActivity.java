package com.lawyee.apppublic.ui.lawAdministration.jamed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JaaidListActivity.java
 * @Package com.lawyee.appservice.ui
 * @Description: 法援业务信息列表页
 * @author: YFL
 * @date: 2017/7/3 17:00
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/7/3 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedServiceListActivity extends BaseActivity implements View.OnClickListener {


    private static final String SAVELISTDATAS = "JamedServiceListActivity";

    public static final String CONTENT_PARAMETER_TYPE = "type";
    public static final String CONTENT_PARAMETER_JAMED = "jamed";
    public static final String CONTENT_PARAMETER_MEDIA = "media";
    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    boolean mInProgess = false;
    private ArrayList mDataList;

    private RecyclerView mRlvContent;
    private XRefreshView mXrefreshView;
    private TextView mJaaidorgContentEmptyTv;
    private Context mContext;
    private JamedServiceListAdapter mRlvAdapter;
    private Button mBtnAddNBV;
    private String mType;
    private String mTitle = "";
    private String mContentType = "";
    private TextView mTitles;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jameservice_list);
        initView();
        selectShowRole();
        initRefresh();
        loadData();
    }

    private void selectShowRole() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(CSTR_EXTRA_TITLE_STR);
        mTitles.setText(title);
        mType = intent.getStringExtra(CONTENT_PARAMETER_TYPE);
        if (mType.equals(CONTENT_PARAMETER_JAMED)) {
            mBtnAddNBV.setVisibility(View.VISIBLE);
            mTitle = getString(R.string.people_mediation);
            mContentType = ShowInfomActivity.CONTENT_PARAMETER_JAMED;
        } else if (mType.equals(CONTENT_PARAMETER_MEDIA)) {
            mBtnAddNBV.setVisibility(View.INVISIBLE);
            mTitle = getString(R.string.media_mediation);
            mContentType = ShowInfomActivity.CONTENT_PARAMETER_MEDIA;
        }
    }

    private void initView() {
        mContext = this;
        mRlvContent = (RecyclerView) findViewById(R.id.rlv_content);
        mXrefreshView = (XRefreshView) findViewById(R.id.mXrefreshView);
        mJaaidorgContentEmptyTv = (TextView) findViewById(R.id.jaaidorg_content_empty_tv);
        mBtnAddNBV = (Button) findViewById(R.id.btn_addNbv);
        mTitles = (TextView) findViewById(R.id.activity_title_text);
        mBtnAddNBV.setOnClickListener(this);
    }

    private void initRefresh() {
        //设置是否可以上拉刷新
        mXrefreshView.setPullLoadEnable(false);
        mXrefreshView.setPullRefreshEnable(true);
        mXrefreshView.setEmptyView(findViewById(R.id.jamedorg_content_empty_tv));
        mXrefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                LoadNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                LoadMoreData();
            }
        });
    }


    private void LoadNewData() {
        if (mInProgess)
            return;
        mInProgess = true;
        handlerRequestService(1, false);

    }

    private void LoadMoreData() {
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

    /**
     * 清楚数据
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
     *
     * @param list
     */
    @SuppressWarnings({"unchecked"})
    private void addDataList(List<?> list) {
        if (mDataList == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mDataList.addAll(list);
    }

    private void loadData() {
        clearDataList();
        List list = JamedApplyVO.loadVOList(JamedApplyVO.dataListFileName(this, SAVELISTDATAS));
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

    private void handlerRequestService(int i, boolean b) {
        JamedUserService jamedUserService = new JamedUserService(mContext);
        jamedUserService.setProgressShowContent(getString(R.string.get_ing));
        jamedUserService.setShowProgress(b);
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


    private void setAdapterData() {
        mRlvAdapter = new JamedServiceListAdapter(mDataList, this, mType);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        mRlvContent.setLayoutManager(manager);
        mRlvContent.addItemDecoration(new RecycleViewDivider(mContext, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mRlvContent.setAdapter(mRlvAdapter);
        mRlvAdapter.setOnRecyclerViewItemChickListener(new JamedServiceListAdapter.OnRecyclerViewItemChickListener() {
            @Override
            public void OnItemChickListener(View view, JamedApplyVO vo) {
                Intent intent = new Intent(mContext, ShowInfomActivity.class);
                intent.putExtra(ShowInfomActivity.CSTR_EXTRA_TITLE_STR, mTitle);
                intent.putExtra(ShowInfomActivity.CONTENT_PARAMETER_TYPE, mContentType);
                intent.putExtra(ShowInfomActivity.CSTR_EXTRA_ORGID_ID,vo.getOid());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addNbv:
                Intent intent = new Intent(JamedServiceListActivity.this, JamedApplyNBVActivity.class);
                intent.putExtra(JamedApplyNBVActivity.CSTR_EXTRA_TITLE_STR, "人民调解申请");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mXrefreshView != null)
            mXrefreshView.startRefresh();
    }
}
