package com.lawyee.apppublic.ui.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.LawOfficeAdpater;
import com.lawyee.apppublic.adapter.LawyerListAdpater;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JalawService;
import com.lawyee.apppublic.util.LawFirmMap;
import com.lawyee.apppublic.util.SerializableHashMap;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JalawFilterVO;
import com.lawyee.apppublic.vo.JalawLawfirmVO;
import com.lawyee.apppublic.vo.JalawLawyerVO;

import net.lawyee.mobilelib.utils.T;
import net.lawyee.mobilelib.vo.BaseVO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lawyee.apppublic.ui.lawyerService.LawServiceActivity.FTO;
import static com.lawyee.apppublic.ui.lawyerService.LawServiceActivity.TY;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEAREA;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEFIELD;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVELAWFIRMAREA;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVELAWFIRMFIELD;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVELAWFIRMSERVICE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEONLINE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVEOPERATION;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SAVESERVICE;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SEARCHLAWFIRMNAME;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SEARCHLAWNAME;
import static com.lawyee.apppublic.ui.lawyerService.ScreenMuchActivity.SEARCHNAME;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: ${todo}(律师，律所列表的fragment)
 * @author: czq
 * @date: 2017/5/23 08:46
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LawyerListFragment extends Fragment {
    public  final static String LAW="law";
    public final static String LAWFIEM="lawfirm";
    public final static String DUTYLAW="dutylaw";
    private RecyclerView mRv_content;
    private List<JalawLawyerVO> mJalawLawyerVOs = new ArrayList<>();
    private List<JalawLawfirmVO> mJalawLawfirmVOs = new ArrayList<>();
    private Context mContext;
    private LawyerListAdpater mLawyerAdpater;
    private LawOfficeAdpater mLawofficeAdpater;
    private GridLayoutManager mLayoutManager;
    private int mType;//0：律师 1：律所
    private int mFromTo;//从哪个页面来
    private RecyclerView mRvContent;
    private XRefreshView mXrefreshview;
    private Map <String,String >mSreenLawmap;
    private Map <String,String >mSreenLawFirmmap;
    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    private Boolean mInProgess= false;
    //筛选条件VO
    private BaseCommonDataVO mAreaData;//地区
    private BaseCommonDataVO mServiceData;//服务内容
    private BaseCommonDataVO mFieldData;//专业领域
    private BaseCommonDataVO mOperationData;//从业年限
    private BaseCommonDataVO mOnlineData;//在线情况
    private BaseCommonDataVO mNameData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        mType = bundle.getInt(TY);
        mFromTo = bundle.getInt(FTO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_law, null);
        initView(view);
        loadData();
        return view;
    }

    /**
     * 当前数据有几页
     * @return
     */
    private int getNowPage() {
        if(mType==0){
            if(mJalawLawyerVOs==null||mJalawLawyerVOs.isEmpty())
                return 0;
            if(mJalawLawyerVOs.size()%Constants.CINT_PAGE_SIZE==0)
                return mJalawLawyerVOs.size()/Constants.CINT_PAGE_SIZE;
            else
                return mJalawLawyerVOs.size()/Constants.CINT_PAGE_SIZE+1;
        }else if(mType==1){
            if(mJalawLawfirmVOs==null||mJalawLawfirmVOs.isEmpty())
                return 0;
            if(mJalawLawfirmVOs.size()%Constants.CINT_PAGE_SIZE==0)
                return mJalawLawfirmVOs.size()/Constants.CINT_PAGE_SIZE;
            else
                return mJalawLawyerVOs.size()/Constants.CINT_PAGE_SIZE+1;
        }
        return  0;
    }

    /**
     * 读取数据
     */
    private void loadData()
    {
       // getScreenData();
        clearDataList();
        initRecycleView(mType, mFromTo);
        loadNewData();
        //读取缓存
//        List list = null;
//        if(mType==0){
//            list= JalawLawyerVO.loadVOList(JalawLawyerVO.dataListFileName(mContext,LAW));
//        }else if(mType==1)
//        {
//            list= JalawLawfirmVO.loadVOList(JalawLawfirmVO.dataListFileName(mContext,LAWFIEM));
//        }
//        if(list!=null&&!list.isEmpty())
//        {
//            addDataList(list);
//        }
//        initRecycleView(mType, mFromTo);
//        Boolean mustRefresh = true;
//        //判断是否在有效期内
//        if(mType==0) {
//            if(mJalawLawyerVOs!=null&&!mJalawLawyerVOs.isEmpty())
//            {
//                JalawLawyerVO vo;
//                Object o = mJalawLawyerVOs.get(0);
//                if(o instanceof  JalawLawyerVO)
//                {
//                    vo = (JalawLawyerVO)o;
//                    mXrefreshview.restoreLastRefreshTime(vo.getVoCreateDate().getTime());
//                    if(vo.isEffectiveTimeData(Constants.CINT_EFFECTIVE_NEWS_TIME))
//                        mustRefresh =false;
//                }
//                if(mJalawLawyerVOs.size()%Constants.CINT_PAGE_SIZE==0)
//                    // 设置是否可以上拉加载
//                    mXrefreshview.setPullLoadEnable(true);
//            }
//        }else if(mType==1){
//            if(mJalawLawfirmVOs!=null&&!mJalawLawfirmVOs.isEmpty())
//            {
//                JalawLawfirmVO vo;
//                Object o = mJalawLawfirmVOs.get(0);
//                if(o instanceof  JalawLawfirmVO)
//                {
//                    vo = (JalawLawfirmVO)o;
//                    mXrefreshview.restoreLastRefreshTime(vo.getVoCreateDate().getTime());
//                    if(vo.isEffectiveTimeData(Constants.CINT_EFFECTIVE_NEWS_TIME))
//                        mustRefresh =false;
//                }
//                if(mJalawLawfirmVOs.size()%Constants.CINT_PAGE_SIZE==0)
//                    // 设置是否可以上拉加载
//                    mXrefreshview.setPullLoadEnable(true);
//            }
//        }
//
//        if(mustRefresh) {
//            mXrefreshview.startRefresh();
//        }
    }



    /**
     * 增加列表数据
     *
     * @param list
     */
    @SuppressWarnings({ "unchecked" })
    private void addDataList(List<?> list) {
        if(mType==0){
            if (mJalawLawyerVOs == null)
                clearDataList();
            if (list == null || list.isEmpty())
                return;
            mJalawLawyerVOs.addAll((Collection<? extends JalawLawyerVO>) list);
        }else if(mType==1){
            if (mJalawLawfirmVOs == null)
                clearDataList();
            if (list == null || list.isEmpty())
                return;
            mJalawLawfirmVOs.addAll((Collection<? extends JalawLawfirmVO >) list);
        }

    }

    private void clearDataList() {
        if(mType==0){
            if (mJalawLawyerVOs == null) {
                mJalawLawyerVOs = new ArrayList();
            } else
                mJalawLawyerVOs.clear();
        }else if(mType==1){
            if (mJalawLawfirmVOs == null) {
                mJalawLawfirmVOs = new ArrayList();
            } else
                mJalawLawfirmVOs.clear();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView(View view) {
        mRv_content = (RecyclerView) view.findViewById(R.id.fragment_law_rv);
        mXrefreshview = (XRefreshView) view.findViewById(R.id.fragment_law_xrefreshview);
        mXrefreshview.setPullRefreshEnable(true);
        mXrefreshview.setPullLoadEnable(false);
        mXrefreshview.restoreLastRefreshTime(0l);
        mXrefreshview.setEmptyView(view.findViewById(R.id.fragment_law_empty_tv));

    }

    private void initRecycleView(int type, int fromTo) {
        if (type == 0) {
            mLawyerAdpater = new LawyerListAdpater(mContext, mJalawLawyerVOs, fromTo);
            mLawyerAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
            mRv_content.setAdapter(mLawyerAdpater);

        } else {
            mLawofficeAdpater = new LawOfficeAdpater(mContext, mJalawLawfirmVOs, fromTo);
            mLawofficeAdpater.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
            mRv_content.setAdapter(mLawofficeAdpater);
        }
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRv_content.setLayoutManager(mLayoutManager);
        mRv_content.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
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
        if(mInProgess)
            return;
        mInProgess=true;
        if(mType==0){
            JalawService jalawService=new JalawService(mContext);
            String name =null;
            String area =null;
            String service =null;
            String fiel =null;
            String option =null;
            String online =null;
            if(mSreenLawmap!=null){
                name= (String) mSreenLawmap.get(SEARCHNAME);
                area= (String) mSreenLawmap.get(SAVEAREA);
                service= (String) mSreenLawmap.get(SAVESERVICE);
                fiel= (String) mSreenLawmap.get(SAVEFIELD);
                option= (String) mSreenLawmap.get(SAVEOPERATION);
                online= (String) mSreenLawmap.get(SAVEONLINE);
            }
            jalawService.queryLawyerList(1, name, area, service, fiel, option, online, new BaseJsonService.IResultInfoListener() {
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
                   // saveVOList((ArrayList<?>) mJalawLawyerVOs,JalawLawyerVO.dataListFileName(mContext,LAW));
                    if(!mJalawLawyerVOs.isEmpty()&&mJalawLawyerVOs.size()%Constants.CINT_PAGE_SIZE==0) {
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
                    mInProgess = false;
                    mXrefreshview.stopRefresh();
                    T.showLong(mContext,msg);
                }
            });

        }else if(mType==1){
                JalawService jalawService=new JalawService(mContext);
            String name =null;
            String area =null;
            String service =null;
            String fiel =null;
            if(mSreenLawFirmmap!=null){
                name= (String) mSreenLawFirmmap.get(SEARCHNAME);
                area= (String) mSreenLawFirmmap.get(SAVEAREA);
                service= (String) mSreenLawFirmmap.get(SAVESERVICE);
                fiel= (String) mSreenLawFirmmap.get(SAVEFIELD);
            }
                jalawService.queryLawfirmList(1, name, area, service,  fiel, new BaseJsonService.IResultInfoListener() {
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
                       // saveVOList((ArrayList<?>) mJalawLawfirmVOs,JalawLawfirmVO.dataListFileName(mContext,LAWFIEM));
                        if(!mJalawLawfirmVOs.isEmpty()&&mJalawLawfirmVOs.size()%Constants.CINT_PAGE_SIZE==0) {
                            // 设置是否可以上拉加载
                            mXrefreshview.setPullLoadEnable(true);
                            mXrefreshview.setLoadComplete(false);
                        }
                        else
                            mXrefreshview.setLoadComplete(true);
                            mLawofficeAdpater.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String msg, String content) {
                        mInProgess = false;
                        mXrefreshview.stopRefresh();
                        T.showLong(mContext,msg);
                    }
                });
        }
    }



    private void loadMoreData() {
        if(mInProgess)
            return;
        mInProgess=true;
        if(mType==0){
            JalawService jalawService=new JalawService(mContext);
            String name =null;
            String area =null;
            String service =null;
            String fiel =null;
            String option =null;
            String online =null;
            if(mSreenLawmap!=null){
                name= (String) mSreenLawmap.get(SEARCHNAME);
                area= (String) mSreenLawmap.get(SAVEAREA);
                service= (String) mSreenLawmap.get(SAVESERVICE);
                fiel= (String) mSreenLawmap.get(SAVEFIELD);
                option= (String) mSreenLawmap.get(SAVEOPERATION);
                online= (String) mSreenLawmap.get(SAVEONLINE);
            }
            jalawService.queryLawyerList(getNowPage()+1, name, area, service, fiel, option, online, new BaseJsonService.IResultInfoListener() {
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
                    if(!mJalawLawyerVOs.isEmpty()&&mJalawLawyerVOs.size()%Constants.CINT_PAGE_SIZE==0){
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
                    mInProgess = false;
                    mXrefreshview.stopLoadMore();
                    T.showLong(mContext,msg);
                }
            });

        }else if(mType==1){
            JalawService jalawService=new JalawService(mContext);
            String name =null;
            String area =null;
            String service =null;
            String fiel =null;
            if(mSreenLawFirmmap!=null){
                name= (String) mSreenLawFirmmap.get(SEARCHNAME);
                area= (String) mSreenLawFirmmap.get(SAVEAREA);
                service= (String) mSreenLawFirmmap.get(SAVESERVICE);
                fiel= (String) mSreenLawFirmmap.get(SAVEFIELD);
            }
            jalawService.queryLawfirmList(getNowPage()+1, name, area, fiel, null, new BaseJsonService.IResultInfoListener() {
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
                    //JalawLawfirmVO.saveVOList((ArrayList<?>) mJalawLawfirmVOs,JalawLawfirmVO.dataListFileName(mContext,LAWFIEM));
                    if(!mJalawLawfirmVOs.isEmpty()&&mJalawLawfirmVOs.size()%Constants.CINT_PAGE_SIZE==0){
                        // 设置是否可以上拉加载
                        mXrefreshview.setPullLoadEnable(true);
                        mXrefreshview.setLoadComplete(false);
                    }
                    else
                        mXrefreshview.setLoadComplete(true);
                        mLawofficeAdpater.notifyDataSetChanged();
                }

                @Override
                public void onError(String msg, String content) {
                    mInProgess = false;
                    mXrefreshview.stopLoadMore();
                    T.showLong(mContext,msg);
                }
            });
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onEventLawMainThread( SerializableHashMap serializableHashMap) {
        mSreenLawmap=serializableHashMap.getMap();
        loadNewData();
    }
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onEventLawFirmMainThread( LawFirmMap lawFirmMap) {
        mSreenLawFirmmap=lawFirmMap.getMap();
        loadNewData();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //获取缓存条件
    private void getScreenData() {

        if(mType==0)
        {
            mNameData=getSave(SEARCHLAWNAME);
            mAreaData = getSave(SAVEAREA);
            mServiceData = getSave(SAVESERVICE);
            mFieldData = getSave(SAVEFIELD);
            mOperationData = getSave(SAVEOPERATION);
            mOnlineData = getSave(SAVEONLINE);
            HashMap<String ,String > map=new HashMap<>();
            if(mNameData!=null&&mNameData.getName()!=null){
                map.put(SEARCHNAME,mNameData.getName());
            }
            if(mAreaData!=null){
                map.put(SAVEAREA,mAreaData.getOid());
            }
            if(mServiceData!=null){
                map.put(SAVESERVICE,mServiceData.getOid());
            }
            if(mFieldData!=null){
                map.put(SAVEFIELD,mFieldData.getOid());
            }
            if(mOperationData!=null){
                map.put(SAVEOPERATION,mOperationData.getOid());
            }
            if(mOnlineData!=null){
                map.put(SAVEONLINE,mOnlineData.getOid());
            }
            mSreenLawmap=map;
        }else if(mType==1)
        {
            mNameData=getSave(SEARCHLAWFIRMNAME);
            mAreaData = getSave(SAVELAWFIRMAREA);
            mServiceData = getSave(SAVELAWFIRMSERVICE);
            mFieldData = getSave(SAVELAWFIRMFIELD);
            HashMap<String ,String > map=new HashMap<>();
            if(mNameData!=null&&mNameData.getName()!=null){
                map.put(SEARCHNAME,mNameData.getName());
            }
            if(mAreaData!=null){
                map.put(SAVEAREA,mAreaData.getOid());
            }
            if(mServiceData!=null){
                map.put(SAVESERVICE,mServiceData.getOid());
            }
            if(mFieldData!=null){
                map.put(SAVEFIELD,mFieldData.getOid());
            }
            mSreenLawFirmmap=map;
        }

    }
    /**
     *
     * @param filename  文件名（所属类型）
     * @return 筛选VO
     */
    private BaseCommonDataVO getSave(String filename){
        Object o = BaseVO.loadVO(JalawFilterVO.dataFileName(mContext,filename));
        if(o!=null&&(o instanceof BaseCommonDataVO))
        {
            return (BaseCommonDataVO) o;
        }else {
            return null;
        }
    }
}
