package com.lawyee.apppublic.ui.org.japub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.GirdDropDownAdapter;
import com.lawyee.apppublic.adapter.LegalActivitiesRlvAdapter;
import com.lawyee.apppublic.adapter.ListDropDownAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JapubService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.ActivityVO;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.LegalActivityFilterVo;
import com.lawyee.apppublic.widget.RecycleViewDivider;
import com.yyydjk.library.DropDownMenu;

import net.lawyee.mobilelib.utils.T;
import net.lawyee.mobilelib.vo.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LegalActivityListsActivity.java
 * @Package com.lawyee.apppublic.ui.org
 * @Description: 法制创建列表
 * @author: YFL
 * @date: 2017/7/27 11:05
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/7/27 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class LegalActivityListsActivity extends BaseActivity {

    /**
     * 缓存标示
     */
    private static final String SAVEFILTERNAME = "legalListActivity";
    /**
     * 列表缓存标示
     */
    private static final String SAVELISTDATAS = "legalListData";

    /**
     * 访问网络的活动类型参数
     */
    private String mActivityTypeParameter;
    /**
     * 访问网络的市参数
     */
    private String mCityParameter;
    /**
     * 访问网络的区县参数
     */
    private String mAreaParameter;

    private DropDownMenu mDropDownMenu;
    //列表首项全部
    private static final String CSTR_ALL = "全部";
    private List<View> mPopupViews = new ArrayList<>();
    // 选择的标题
    private List<String> mDrawDownMenuHeaders;
    //活动类型
    private List<String> mActivityTypeList;
    //市级名字集合
    private List<String> mCityStrList;
    //区县级名字集合
    private List<String> mAreasStrList;

    /**
     * 数据列表
     */
    @SuppressWarnings("rawtypes")
    protected ArrayList mDataList;

    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    boolean mInProgess = false;

    private LegalActivityFilterVo mFilterVO;
    private GirdDropDownAdapter mActivityTypeAdapter;
    private ListDropDownAdapter mCityAdapter;
    private ListDropDownAdapter mAreaAdapter;
    private View mContentView;
    private XRefreshView mXrefreshView;
    private LegalActivitiesRlvAdapter mRlvAdapter;

    private Context mContext;
    private RecyclerView mRlvLegalActivityOrg;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_legal_activities);
        initView();
        Object o = BaseVO.loadVO(LegalActivityFilterVo.dataFileName(this, SAVEFILTERNAME));
        if (o != null && (o instanceof LegalActivityFilterVo)) {
            mFilterVO = (LegalActivityFilterVo) o;
            initActivitiyTypes(mFilterVO.getNowSelActivityType() < 0 ? null : mFilterVO.getActivityTypes());
            initCity(mFilterVO.getNowSelCity() < 0 ? null : mFilterVO.getCityDatas());
            initAreas(mFilterVO.getNowSelCity() < 0 || mFilterVO.getCityDatas() == null ||
                    mFilterVO.getNowSelCity() >= mFilterVO.getCityDatas().size() ?
                    null : mFilterVO.getCityDatas().get(mFilterVO.getNowSelCity()));
        }
        if (mFilterVO == null) {
            mFilterVO = new LegalActivityFilterVo();
            initActivitiyTypes(null);
            initCity(null);
            initAreas(null);
        }
        initDrawDownMenuData();
        loadData();
    }

    private void initDrawDownMenuData() {
        if (mDrawDownMenuHeaders == null)
            mDrawDownMenuHeaders = new ArrayList<>();
        mDrawDownMenuHeaders.add(getString(R.string.legal_activity_type));
        mDrawDownMenuHeaders.add(getResources().getString(R.string.org_city));
        mDrawDownMenuHeaders.add(getResources().getString(R.string.org_area));
        ListView activityLists = new ListView(this);
        mActivityTypeAdapter = new GirdDropDownAdapter(this, mActivityTypeList);
        activityLists.setDividerHeight(0);
        mActivityTypeAdapter.setCheckItem(mFilterVO.getNowSelActivityType() + 1);
        activityLists.setAdapter(mActivityTypeAdapter);
        //init age menu
        final ListView city = new ListView(this);
        city.setDividerHeight(0);
        mCityAdapter = new ListDropDownAdapter(this, mCityStrList);
        mCityAdapter.setCheckItem(mFilterVO.getNowSelCity() + 1);
        city.setAdapter(mCityAdapter);

        //init sex menu
        final ListView area = new ListView(this);
        area.setDividerHeight(0);
        mAreaAdapter = new ListDropDownAdapter(this, mAreasStrList);
        mAreaAdapter.setCheckItem(mFilterVO.getNowSelAreas() + 1);
        area.setAdapter(mAreaAdapter);

        //init mPopupViews
        mPopupViews.add(activityLists);
        mPopupViews.add(city);
        mPopupViews.add(area);

        //add item click event
        activityLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelActivityType(position == 0 ? -1 : position - 1);
                mActivityTypeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(0) : mActivityTypeList.get(position));
                mCityAdapter.notifyDataSetChanged();
                mAreaAdapter.notifyDataSetChanged();
                mDropDownMenu.closeMenu();
                updata();
            }
        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelCity(position - 1);
                mFilterVO.setNowSelAreas(-1);
                mCityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(position));
                initAreas(position == 0 || mFilterVO.getCityDatas() == null ||
                        position - 1 >= mFilterVO.getCityDatas().size() ?
                        null : mFilterVO.getCityDatas().get(position - 1));
                mAreaAdapter.notifyDataSetChanged();
                mAreaAdapter.setCheckItem(0);
                mDropDownMenu.setTabText(2, mDrawDownMenuHeaders.get(2));
                mDropDownMenu.closeMenu();
                updata();
            }


        });

        area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelAreas(position - 1);
                mAreaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(2) : mAreasStrList.get(position));
                mDropDownMenu.closeMenu();
                updata();
            }
        });

        //init context view
        mContentView = (LayoutInflater.from(this).inflate(R.layout.activity_jaaidorg_content, null));
        mXrefreshView = (XRefreshView) mContentView.findViewById(R.id.xrefreshview_org);
        mRlvLegalActivityOrg = (RecyclerView) mContentView.findViewById(R.id.rlv_jaaidorg);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initRefresh(mContentView);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(mDrawDownMenuHeaders, mPopupViews, mContentView);
        if (mFilterVO.getNowSelActivityType() != -1 && mFilterVO.getActivitiesTypes().get(mFilterVO.getNowSelActivityType()).equals("全部")) {
            mDropDownMenu.setTabText(0, getResources().getString(R.string.legal_activity_type));
        } else {
            mDropDownMenu.setTabText(0, mFilterVO.getNowSelActivityType() == -1 ?
                    mDrawDownMenuHeaders.get(0) : mActivityTypeList.get(mFilterVO.getNowSelActivityType() + 1));
        }
        mDropDownMenu.setTabText(1, mFilterVO.getNowSelCity() == -1 ?
                mDrawDownMenuHeaders.get(1) : mCityStrList.get(mFilterVO.getNowSelCity() + 1));
        mDropDownMenu.setTabText(2, mFilterVO.getNowSelAreas() == -1 ?
                mDrawDownMenuHeaders.get(2) : mAreasStrList.get(mFilterVO.getNowSelAreas() + 1));

    }

    private void initActivitiyTypes(List<String> acts) {
        if (mFilterVO.getActivityTypes() != null)
            mFilterVO.getActivityTypes().clear();
        if (mActivityTypeList == null) {
            mActivityTypeList = new ArrayList<>();
            mActivityTypeList.add(CSTR_ALL);
        } else {
            mActivityTypeList.clear();
            mActivityTypeList.add(CSTR_ALL);
        }
        if (acts == null || mFilterVO.getActivityTypes().isEmpty()) {
            List<BaseCommonDataVO> activityType = DataManage.getInstance().getActivityType();
            if (activityType != null) {
                mFilterVO.setmActivitiesTypes(activityType);
            }
            if (mFilterVO.getActivitiesTypes() == null || mFilterVO.getActivitiesTypes().isEmpty()) {
                return;
            }

            for (BaseCommonDataVO datavo : mFilterVO.getActivitiesTypes()) {
                mActivityTypeList.add(datavo.getName());
            }

        } else {
            for (String act : acts) {
                mActivityTypeList.add(act);
            }
        }

    }

    /**
     * 初始化市数据
     *
     * @param dvo
     */
    private void initCity(List<BaseCommonDataVO> dvo) {
        if (mFilterVO.getCityDatas() != null)
            mFilterVO.getCityDatas().clear();
        if (mCityStrList == null) {
            mCityStrList = new ArrayList<>();
            mCityStrList.add(CSTR_ALL);
        } else {
            mCityStrList.clear();
            mCityStrList.add(CSTR_ALL);
        }
        if (dvo == null || mFilterVO.getCityDatas().isEmpty()) {
            List<BaseCommonDataVO> dataVOs = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(),
                    Constants.PROVINCE_GUIZHOU_ID);
            mFilterVO.setCityDatas(dataVOs);
            if (mFilterVO.getCityDatas() == null || mFilterVO.getCityDatas().isEmpty()) {
                return;
            }
            for (BaseCommonDataVO datavo : mFilterVO.getCityDatas()) {
                mCityStrList.add(datavo.getName());
            }
        } else {
            for (BaseCommonDataVO vo : dvo
                    ) {
                mCityStrList.add(vo.getName());
            }
        }
    }

    /**
     * 初始化地区数据
     *
     * @param dvo
     */
    private void initAreas(BaseCommonDataVO dvo) {
        if (mFilterVO.getAreasDatas() != null)
            mFilterVO.getAreasDatas().clear();
        if (mAreasStrList == null) {
            mAreasStrList = new ArrayList<>();
            mAreasStrList.add(CSTR_ALL);
        } else {
            mAreasStrList.clear();
            mAreasStrList.add(CSTR_ALL);
        }

        if (dvo == null)
            return;
        mFilterVO.setAreasDatas(BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(),
                dvo.getOid()));
        if (mFilterVO.getAreasDatas() == null || mFilterVO.getAreasDatas().isEmpty())
            return;
        for (BaseCommonDataVO bdvo : mFilterVO.getAreasDatas()
                ) {
            mAreasStrList.add(bdvo.getName());
        }
    }

    private void initView() {
        mContext = this;
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
    }


    private void initRefresh(View view) {
        //设置是否能上拉刷新
        mXrefreshView.setPullLoadEnable(false);
        //设置是否下拉刷新
        mXrefreshView.setPullRefreshEnable(true);
        mXrefreshView.restoreLastRefreshTime(0l);
        mXrefreshView.setEmptyView(view.findViewById(R.id.jaaidorg_content_empty_tv));
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
    private void LoadNewData() {
        if (mInProgess)
            return;
        mInProgess = true;
        selectCondition();
        handlerRequestService(mActivityTypeParameter, 1, mCityParameter, mAreaParameter);

    }
    /**
     * 判读是否有选择
     */
    private void selectCondition() {
        mActivityTypeParameter = (mFilterVO.getNowSelActivityType() > -1 ?
                mFilterVO.getActivitiesTypes().get(mFilterVO.getNowSelActivityType()).getOid() : null);
        mCityParameter = (mFilterVO.getNowSelCity() > -1 ?
                mFilterVO.getCityDatas().get(mFilterVO.getNowSelCity()).getOid() : null);
        mAreaParameter = (mFilterVO.getNowSelAreas() > -1 ? mFilterVO.getAreasDatas().get(mFilterVO.getNowSelAreas()).getOid() : null);
    }


    private void updata() {
        BaseVO.saveVO(mFilterVO, LegalActivityFilterVo.dataFileName(LegalActivityListsActivity.this, SAVEFILTERNAME));
        selectCondition();
        JapubService japubService = new JapubService(mContext);
        japubService.setShowProgress(true);
        japubService.setProgressShowContent(getResources().getString(R.string.get_ing));
        japubService.getActivityList(mActivityTypeParameter, 1, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
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
                ActivityVO.saveVOList(mDataList, ActivityVO.dataListFileName(getApplicationContext(), SAVELISTDATAS));
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

    private void loadData() {
        clearDataList();
        //读取缓存
        List list = ActivityVO.loadVOList(ActivityVO.dataListFileName(this, SAVELISTDATAS));
        if (list != null && !list.isEmpty()) {
            addDataList(list);
        } else {
            handlerRequestService(null, 1, null, null);
        }
        setAdapterData();
        Boolean mustRefresh = true;
        //判断是否有效期
        if (mDataList != null && !mDataList.isEmpty()) {
            ActivityVO vo;
            Object o = mDataList.get(0);
            if (o instanceof ActivityVO) {
                vo = (ActivityVO) o;
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

    private void setAdapterData() {
        mRlvAdapter = new LegalActivitiesRlvAdapter(mDataList, LegalActivityListsActivity.this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(LegalActivityListsActivity.this, 1);
        mRlvLegalActivityOrg.addItemDecoration(new RecycleViewDivider(LegalActivityListsActivity.this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvLegalActivityOrg.setLayoutManager(linearLayoutManager);
        mRlvLegalActivityOrg.setAdapter(mRlvAdapter);
        mRlvAdapter.setCustomLoadMoreView(new XRefreshViewFooter(LegalActivityListsActivity.this));
        mRlvAdapter.setItemClickListener(new LegalActivitiesRlvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void ItemClickListenet(View view, ActivityVO vo, int position) {
                if (vo.getOid().equals(Constants.VOTEOID)) {//投票活动
                    Intent intent = new Intent(LegalActivityListsActivity.this, LawVoteActivity.class);
                    intent.putExtra(LawActivityLookActivity.CSTR_EXTRA_TITLE_STR, "法制宣传-投票活动");
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(LegalActivityListsActivity.this, LawActivityLookActivity.class);
                    intent.putExtra(LawActivityLookActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.law_Propaganda));
                    intent.putExtra(LawActivityLookActivity.ACTIVITYLOOK,vo.getOid());
                    startActivity(intent);
                }
            }
        });
    }

    private void handlerRequestService(String mCardType, int i, String mCityParameter, String mAreaParameter) {
        JapubService service = new JapubService(mContext);
        service.getActivityList(mCardType, i, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
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
                ActivityVO.saveVOList(mDataList, ActivityVO.dataListFileName(getApplicationContext(), SAVELISTDATAS));
                if (!mDataList.isEmpty()&&mDataList.size()%Constants.CINT_PAGE_SIZE ==0) {
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
                mXrefreshView.stopRefresh();
                T.showShort(getApplicationContext(), msg);
            }
        });
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

    private void loadMoreDatas() {
        if (mInProgess) {
            return;
        }
        mInProgess = true;
        selectCondition();
        JapubService japubService = new JapubService(mContext);
        japubService.getActivityList(mActivityTypeParameter, getNowPage() + 1, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
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
                ActivityVO.saveVOList(mDataList, ActivityVO.dataListFileName(getApplicationContext(), SAVELISTDATAS));
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



    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
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
}
