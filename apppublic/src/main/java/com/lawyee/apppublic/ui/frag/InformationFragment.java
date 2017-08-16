package com.lawyee.apppublic.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.InformRecyclerViewAdapter;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.InfoService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.infom.InfomDetailsActivity;
import com.lawyee.apppublic.vo.InfomationVO;
import com.lawyee.apppublic.vo.VideoInformationVO;

import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 资讯展示列表fragment
 * @author: YFL
 * @date: 2017/5/15 14:24
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class InformationFragment extends Fragment {
    private static  final String TAG = InformationFragment.class.getSimpleName();

    /**
     * 传入参数-资讯类别名称
     */
    public static final String CSTR_EXTRA_INFOTYPENAME_STR = "infotypename";
    /**
     * 传入参数-资讯类别ID
     */
    public static final String CSTR_EXTRA_INFOTYPEID_STR = "infotypeid";
    /**
     * 传入参数-机构ID
     */
    public static final String CSTR_EXTRA_ORGID_STR = "orgid";

    /**
     * 资讯类型为视频
     */
    public static final String CSTR_INFOTYPE_VIDEO = "video";

    private RecyclerView mRlvInformItem;
    private XRefreshView mXrefreshview;
    private InformRecyclerViewAdapter mDataAdapter;
    private String mTypeName;
    private String mTypeId;
    private String mOrgId;

    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    private Boolean mInProgess= false;
    /**
     * 数据列表
     */
    @SuppressWarnings("rawtypes")
    protected ArrayList mDataList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTypeName = bundle.getString(CSTR_EXTRA_INFOTYPENAME_STR);
        mTypeId = bundle.getString(CSTR_EXTRA_INFOTYPEID_STR);
        mOrgId = bundle.getString(CSTR_EXTRA_ORGID_STR);
        if(StringUtil.isEmpty(mTypeId))
            T.showLong(getActivity(),"无效的资讯类型");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, null);
        initView(view);
        loadData();
        return view;
    }

    private void initView(View view) {
        mRlvInformItem = (RecyclerView) view.findViewById(R.id.information_rv);
        mXrefreshview = (XRefreshView) view.findViewById(R.id.information_xrv);
        // 设置是否可以下拉刷新
        mXrefreshview.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        mXrefreshview.setPullLoadEnable(false);
        mXrefreshview.restoreLastRefreshTime(0l);
     /*   mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);*/
        mXrefreshview.setEmptyView(view.findViewById(R.id.information_empty_tv));
    }

    /**
     * 是否为视频类型
     * @return
     */
    private boolean isVideoType()
    {
        return CSTR_INFOTYPE_VIDEO.equals(mTypeId);
    }

    /**
     * 根据类型取类型ID
     * @return
     */
    private String getTypeID()
    {
        if(isVideoType())
            return getResources().getStringArray(R.array.FXSPInfoID)[0];
        else
            return mTypeId;
    }

    private void setAdapetData() {
        mDataAdapter = new InformRecyclerViewAdapter(mDataList, getActivity());
        mDataAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRlvInformItem.setLayoutManager(gridLayoutManager);
        mDataAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        mRlvInformItem.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRlvInformItem.setAdapter(mDataAdapter);

        mDataAdapter.setOnRecyclerViewClickListener(new InformRecyclerViewAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                L.d(TAG,"onItemClickListener:"+position);
                if(position<0||position>=mDataList.size())
                    return;
                InfomationVO ivo = (InfomationVO) mDataList.get(position);
                if(ivo instanceof VideoInformationVO)
                {
                    //TODO 视频资讯处理方式
                    String videourl = ((VideoInformationVO) ivo).getVideoUrl();
                    if(StringUtil.isEmpty(videourl))
                    {
                        T.showShort(getActivity(),"无效的视频访问地址");
                        return;
                    }
                    BaseActivity.runBrowser(videourl,getActivity());
                    return;
                }
                Intent intent = new Intent(getActivity(), InfomDetailsActivity.class);
                intent.putExtra(InfomDetailsActivity.CSTR_EXTRA_TITLE_STR, mTypeName);
                intent.putExtra(InfomDetailsActivity.CSTR_EXTRA_INFORMATIONVO_VO,ivo);
                startActivity(intent);
            }
        });
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

    /**
     * 当前数据有几页
     * @return
     */
    private int getNowPage()
    {
        if(mDataList==null||mDataList.isEmpty())
            return 0;
        if(mDataList.size()%Constants.CINT_PAGE_SIZE==0)
            return mDataList.size()/Constants.CINT_PAGE_SIZE;
        else
            return mDataList.size()/Constants.CINT_PAGE_SIZE+1;
    }

    /**
     * 读取数据
     */
    private void loadData()
    {
        clearDataList();
        //读取缓存
        List list = InfomationVO.loadVOList(
                InfomationVO.dataListFileName(getActivity(),getTypeID()+(StringUtil.isEmpty(mOrgId)?"":mOrgId)));
        if(list!=null&&!list.isEmpty())
        {
            addDataList(list);
        }
        setAdapetData();
        Boolean mustRefresh = true;
        //判断是否在有效期内
        if(mDataList!=null&&!mDataList.isEmpty())
        {
            InfomationVO vo;
            Object o = mDataList.get(0);
            if(o instanceof  InfomationVO)
            {
                vo = (InfomationVO)o;
                mXrefreshview.restoreLastRefreshTime(vo.getVoCreateDate().getTime());
                if(vo.isEffectiveTimeData(Constants.CINT_EFFECTIVE_NEWS_TIME))
                    mustRefresh =false;
            }
            if(mDataList.size()%Constants.CINT_PAGE_SIZE==0)
                // 设置是否可以上拉加载
                mXrefreshview.setPullLoadEnable(true);
        }
        if(mustRefresh) {
            mXrefreshview.startRefresh();
        }
    }

    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({ "unchecked" })
    private void addDataList(List<?> list) {
        if (mDataList == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mDataList.addAll(list);
    }

    /**
     * 清除数据
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void clearDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList();
        } else
            mDataList.clear();
    }
    /**
     * 刷新数据
     */
    protected void loadNewData()
    {
        if(mInProgess)
            return;
        mInProgess=true;
        InfoService service = new InfoService(getActivity());
        service.getList(isVideoType(),getTypeID(), 1, mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                mXrefreshview.stopRefresh();
                if(values==null||values.isEmpty()) {
                    T.showShort(getActivity(),content);
                    return;
                }

                ArrayList list = (ArrayList) values
                        .get(0);
                clearDataList();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }else {
                    mDataAdapter.notifyDataSetChanged();
                    mXrefreshview.setLoadComplete(true);
                    return;
                }
                //缓存数据
                InfomationVO.saveVOList(mDataList,
                        InfomationVO.dataListFileName(getActivity(),getTypeID()+(StringUtil.isEmpty(mOrgId)?"":mOrgId)));
                if(!mDataList.isEmpty()&&mDataList.size()%Constants.CINT_PAGE_SIZE==0) {
                    // 设置是否可以上拉加载
                    mXrefreshview.setPullLoadEnable(true);
                    mXrefreshview.setLoadComplete(false);
                }
                else
                    mXrefreshview.setLoadComplete(true);
                mDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                mInProgess = false;
                mXrefreshview.stopRefresh();
                T.showLong(getActivity(),msg);
            }
        });
    }

    /**
     * 加载更多数据
     */
    private void loadMoreData()
    {
        if(mInProgess)
            return;
        mInProgess=true;
        InfoService service = new InfoService(getActivity());
        service.getList(isVideoType(),getTypeID(), getNowPage()+1, mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                if(values==null||values.isEmpty()) {
                    mXrefreshview.setLoadComplete(true);
                    return;
                }

                ArrayList list = (ArrayList) values
                        .get(0);
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }else
                {
                    mXrefreshview.setLoadComplete(true);
                    return;
                }
                InfomationVO.saveVOList(mDataList,
                        InfomationVO.dataListFileName(getActivity(),getTypeID()+(StringUtil.isEmpty(mOrgId)?"":mOrgId)));
                if(!mDataList.isEmpty()&&mDataList.size()%Constants.CINT_PAGE_SIZE==0){
                    // 设置是否可以上拉加载
                    mXrefreshview.setPullLoadEnable(true);
                    mXrefreshview.setLoadComplete(false);
                }
                else
                    mXrefreshview.setLoadComplete(true);
                mDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                mInProgess = false;
                mXrefreshview.stopLoadMore();
                T.showLong(getActivity(),msg);
            }
        });
    }
}
