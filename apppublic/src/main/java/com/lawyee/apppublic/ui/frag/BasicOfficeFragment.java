package com.lawyee.apppublic.ui.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.BasicLawOfficeAdpater;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaglsService;
import com.lawyee.apppublic.util.LawFirmMap;
import com.lawyee.apppublic.vo.JaglsOrgVO;

import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.lawyee.apppublic.R.id.fragment_lawfirm_xrefreshview;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SAVEWORKERAREA;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SAVEWORKERFIELD;
import static com.lawyee.apppublic.ui.basiclaw.BasicScreenMuchActivity.SEARCHWORKERNAME;


public class BasicOfficeFragment extends Fragment {


    private RecyclerView fragment_lawfirm_rv;
    private XRefreshView mXrefreshview;
    private TextView fragment_lawfirm_empty_tv;
    private BasicLawOfficeAdpater mAdpater;
    private Context mContext;
    private GridLayoutManager mLayoutManager;
    private ArrayList<JaglsOrgVO> mList=new ArrayList();
    private boolean mInProgess;
    private Map<String,String > mSreenJaglsorgmap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_basic_law_firm, container, false);
        initView(view);
        loadData();
        return view;
    }

    private void loadData() {
        clearDataList();
        initRecycleView();
        loadNewData();
    }

    private void initView(View view) {
        fragment_lawfirm_rv = (RecyclerView) view.findViewById(R.id.fragment_lawfirm_rv);
        mXrefreshview = (XRefreshView) view.findViewById(fragment_lawfirm_xrefreshview);
        mXrefreshview.setPullRefreshEnable(true);
        mXrefreshview.setPullLoadEnable(false);
        mXrefreshview.restoreLastRefreshTime(0l);
        mXrefreshview.setEmptyView(view.findViewById(R.id.fragment_lawfirm_empty_tv));
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
    private void initRecycleView() {

        mAdpater = new BasicLawOfficeAdpater(mContext,mList);
        mAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        fragment_lawfirm_rv.setAdapter(mAdpater);
        
        mLayoutManager = new GridLayoutManager(mContext, 1);
        fragment_lawfirm_rv.setLayoutManager(mLayoutManager);
        fragment_lawfirm_rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
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

    private void loadMoreData() {
        if(mInProgess)
            return;
            mInProgess=true;
            JaglsService jaglsService= new JaglsService(mContext);
            String name =null;
            String area =null;
            String service =null;
//            String fiel =null;
//            String option =null;
//            String online =null;
            if(mSreenJaglsorgmap!=null){
                name= (String) mSreenJaglsorgmap.get(SEARCHWORKERNAME);
                area= (String) mSreenJaglsorgmap.get(SAVEWORKERAREA);
                service= (String) mSreenJaglsorgmap.get(SAVEWORKERFIELD);
//                fiel= (String) mSreenJaglsStaffmap.get(SAVEFIELD);
//                option= (String) mSreenJaglsStaffmap.get(SAVEOPERATION);
//                online= (String) mSreenJaglsStaffmap.get(SAVEONLINE);
            }
        jaglsService.queryOrgList(getNowPage()+1, name, area, service,  new BaseJsonService.IResultInfoListener() {
                @Override
                public void onComplete(ArrayList<Object> values, String content) {
                    mInProgess = false;
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
                    // saveVOList((ArrayList<?>) mJalawLawyerVOs,JalawLawyerVO.dataListFileName(mContext,LAW));
                    if(!mList.isEmpty()&&mList.size()%Constants.CINT_PAGE_SIZE==0){
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
                    mInProgess = false;
                    mXrefreshview.stopLoadMore();
                    T.showLong(mContext,msg);
                }
            });


    }

    private void loadNewData() {
        if(mInProgess)
            return;
        mInProgess=true;

            JaglsService jaglsService= new JaglsService(mContext);
            String name =null;
            String area =null;
            String service =null;
//            String fiel =null;
//            String option =null;
//            String online =null;
            if(mSreenJaglsorgmap!=null){
                name= (String) mSreenJaglsorgmap.get(SEARCHWORKERNAME);
                area= (String) mSreenJaglsorgmap.get(SAVEWORKERAREA);
                service= (String) mSreenJaglsorgmap.get(SAVEWORKERFIELD);
//                fiel= (String) mSreenJaglsStaffmap.get(SAVEFIELD);
//                option= (String) mSreenJaglsStaffmap.get(SAVEOPERATION);
//                online= (String) mSreenJaglsStaffmap.get(SAVEONLINE);
            }
        jaglsService.queryOrgList(1, name, area, service,  new BaseJsonService.IResultInfoListener() {
                @Override
                public void onComplete(ArrayList<Object> values, String content) {
                    mInProgess = false;
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
                    //缓存数据
                   // saveVOList((ArrayList<?>) mList, JalawLawyerVO.dataListFileName(mContext,LAW));
                    if(!mList.isEmpty()&&mList.size()% Constants.CINT_PAGE_SIZE==0) {
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
                    mInProgess = false;
                    mXrefreshview.stopRefresh();
                    T.showLong(mContext,msg);
                }
            });



    }
    private void clearDataList() {

            if (mList == null) {
                mList = new ArrayList();
            } else
                mList.clear();
    }
    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({ "unchecked" })
    private void addDataList(List<?> list) {

            if (mList == null)
                clearDataList();
            if (list == null || list.isEmpty())
                return;
        mList.addAll((Collection<? extends JaglsOrgVO>) list);


    }
    /**
     * 当前数据有几页
     * @return
     */
    private int getNowPage() {
            if(mList==null||mList.isEmpty())
                return 0;
            if(mList.size()%Constants.CINT_PAGE_SIZE==0)
                return mList.size()/Constants.CINT_PAGE_SIZE;
            else
                return mList.size()/Constants.CINT_PAGE_SIZE+1;
    }
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onEventJaglsorgMainThread( LawFirmMap lawFirmMap) {
        Log.e("czq","88888888888");
        mSreenJaglsorgmap=lawFirmMap.getMap();
        loadNewData();
    }
}
