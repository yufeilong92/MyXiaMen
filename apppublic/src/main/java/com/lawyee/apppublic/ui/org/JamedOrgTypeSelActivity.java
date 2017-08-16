package com.lawyee.apppublic.ui.org;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.GirdDropDownAdapter;
import com.lawyee.apppublic.adapter.JamedOrgRlvAdapter;
import com.lawyee.apppublic.adapter.ListDropDownAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JamedService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.frag.JamedApplyInformationFragment;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JamedOrgFilterVo;
import com.lawyee.apppublic.vo.JamedOrgVO;
import com.lawyee.apppublic.widget.RecycleViewDivider;
import com.yyydjk.library.DropDownMenu;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JaaidOrgActivity.java
 * @Package com.lawyee.apppublic.ui.mechanism
 * @Description: 人民机构列表页
 * @author: YFL
 * @date: 2017/5/22 10:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/22 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedOrgTypeSelActivity extends BaseActivity {

    /**
     * 列表缓存标示
     */
    private static final String SAVELISTDATAS = "JamedOrgListData";
    /**
     * 访问网络的省参数
     */
    private String mMediationTypeParameter;
    /**
     * 访问网络的市参数
     */
    private String mCityParameter;
    /**
     * 访问网络的区县参数
     */
    private String mAreaParameter;
    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    boolean mInProgess;
    /**
     * 数据列表
     */
    private ArrayList mDataList;

    /**
     * 传递参数-调解机构回调
     */
    public static final String SELECTORGSELECTTYPE = "selectorgtype";
    //列表首项全部
    private static final String CSTR_ALL = "全部";
    private List<View> mPopupViews = new ArrayList<>();
    private GirdDropDownAdapter mProvinceAdapter;
    private ListDropDownAdapter mCityAdapter;
    private ListDropDownAdapter mAreaAdapter;
    private JamedOrgFilterVo mFilterVO;
    // 选择的标题
    private List<String> mDrawDownMenuHeaders = new ArrayList<>();
    //调解类型
    private List<String> mMediationTypeList = new ArrayList<>();
    //市级名字集合
    private List<String> mCityStrList = new ArrayList<>();
    //区县级名字集合
    private List<String> mAreasStrList = new ArrayList<>();

    private DropDownMenu mDropDownMenu;
    private JamedOrgRlvAdapter mRlvAdapter;
    private View mContentView;
    private XRefreshView mXrefreshView;
    private String mSelectType;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jaaid_org);
        Intent intent = getIntent();
        mSelectType = intent.getStringExtra(SELECTORGSELECTTYPE);
        if (TextUtils.isEmpty(mSelectType))
            return;
        initView();
        mFilterVO = new JamedOrgFilterVo();
        initCity(null);
        initAreas(null);
        initDrawDownMenuData();
        loadData();
    }


    /**
     * 初始化市数据
     *
     * @param dvo
     */
    private void initCity(List<BaseCommonDataVO> dvo) {
        if(mFilterVO.getJamedCityDatas()!=null)
            mFilterVO.getJamedCityDatas().clear();
        mCityStrList.clear();
        mCityStrList.add(CSTR_ALL);
        if (dvo == null || mFilterVO.getJamedCityDatas().isEmpty()) {
            List<BaseCommonDataVO> dataVOs = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(),
                    Constants.PROVINCE_GUIZHOU_ID);
            mFilterVO.setJamedCityDatas(dataVOs);
            if (mFilterVO.getJamedCityDatas() == null || mFilterVO.getJamedCityDatas().isEmpty()) {
                return;
            }
            for (BaseCommonDataVO datavo : mFilterVO.getJamedCityDatas()) {
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
        if(mFilterVO.getJamedAreasDatas()!=null)
            mFilterVO.getJamedAreasDatas().clear();
        mAreasStrList.clear();
        mAreasStrList.add(CSTR_ALL);
        if (dvo == null)
            return;
        mFilterVO.setJamedAreasDatas(BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(),
                dvo.getOid()));
        if (mFilterVO.getJamedAreasDatas() == null || mFilterVO.getJamedAreasDatas().isEmpty())
            return;
        for (BaseCommonDataVO bdvo : mFilterVO.getJamedAreasDatas()
                ) {
            mAreasStrList.add(bdvo.getName());
        }
    }

    private void initView() {
        //init city menu
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
    }

    /**
     * 刷新控件设置
     */
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

    /**
     * 加载新的数据
     */
    private void LoadNewData() {
        if (mInProgess)
            return;
        mInProgess = true;
        selectCondition();
        handlerRequestService(1, mMediationTypeParameter, mCityParameter, mAreaParameter);
    }

    private void loadMoreDatas() {
        if (mInProgess) {
            return;
        }
        mInProgess = true;
        selectCondition();
        JamedService service = new JamedService(this);
        service.queryOrgList(getNowPage() + 1, mMediationTypeParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
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


    private void handlerRequestService(int id, String mMediationTypeParameter, String mCityParameter, String mAreaParameter) {
        JamedService service = new JamedService(this);
        service.queryOrgList(id, mMediationTypeParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
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

    /**
     * 判读是否有选择
     */
    private void selectCondition() {
        mMediationTypeParameter = mSelectType;
        mCityParameter = (mFilterVO.getNowSelJamedCity() > -1 ?
                mFilterVO.getJamedCityDatas().get(mFilterVO.getNowSelJamedCity()).getOid() : null);
        mAreaParameter = (mFilterVO.getNowSelJamedAreas() > -1 ? mFilterVO.getJamedAreasDatas().get(mFilterVO.getNowSelJamedAreas()).getOid() : null);
    }


    /**
     * 初始drrawDoen视图
     */
    private void initDrawDownMenuData() {
        mDrawDownMenuHeaders.add(mSelectType);
        mDrawDownMenuHeaders.add(getResources().getString(R.string.org_city));
        mDrawDownMenuHeaders.add(getResources().getString(R.string.org_area));
        final ListView province = new ListView(this);
        mProvinceAdapter = new GirdDropDownAdapter(this, mMediationTypeList);
        province.setDividerHeight(0);
        mProvinceAdapter.setCheckItem(mFilterVO.getNowSelMediationType());
        province.setAdapter(mProvinceAdapter);
        //init age menu
        final ListView city = new ListView(this);
        city.setDividerHeight(0);
        mCityAdapter = new ListDropDownAdapter(this, mCityStrList);
        mCityAdapter.setCheckItem(mFilterVO.getNowSelJamedCity() + 1);
        city.setAdapter(mCityAdapter);

        //init sex menu
        final ListView area = new ListView(this);
        area.setDividerHeight(0);
        mAreaAdapter = new ListDropDownAdapter(this, mAreasStrList);
        mAreaAdapter.setCheckItem(mFilterVO.getNowSelJamedAreas() + 1);
        area.setAdapter(mAreaAdapter);

        //init mPopupViews
        mPopupViews.add(province);
        mPopupViews.add(city);
        mPopupViews.add(area);

        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDropDownMenu.closeMenu();
            }
        });
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelJamedCity(position - 1);
                mFilterVO.setNowSelJamedAreas(-1);
                mCityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(position));
                initAreas(position == 0 ? null : mFilterVO.getJamedCityDatas().get(position - 1));
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
                mFilterVO.setNowSelJamedAreas(position - 1);
                mAreaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(2) : mAreasStrList.get(position));
                mDropDownMenu.closeMenu();
                updata();
            }
        });

        //init context view
        mContentView = (LayoutInflater.from(this).inflate(R.layout.activity_jaaidorg_content, null));
        mXrefreshView = (XRefreshView) mContentView.findViewById(R.id.xrefreshview_org);

        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initRefresh(mContentView);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(mDrawDownMenuHeaders, mPopupViews, mContentView);
        mDropDownMenu.setTabChick(0, false);
    }


    private void loadData() {
        clearDataList();
        selectCondition();
        handlerRequestService(1, mMediationTypeParameter, mCityParameter, mAreaParameter);
        setAdapterData();
        mXrefreshView.stopRefresh();
    }

    private void updata() {
        selectCondition();
        JamedService service = new JamedService(this);
        service.setShowProgress(true);
        service.setProgressShowContent(getString(R.string.loading));
        service.queryOrgList(1, mMediationTypeParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
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
        mRlvAdapter = new JamedOrgRlvAdapter(mDataList, this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        RecyclerView mRlvJaaidOrg = (RecyclerView) mContentView.findViewById(R.id.rlv_jaaidorg);
        mRlvJaaidOrg.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvJaaidOrg.setLayoutManager(linearLayoutManager);
        mRlvJaaidOrg.setAdapter(mRlvAdapter);
        mRlvAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mRlvAdapter.setItemClickListener(new JamedOrgRlvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void ItemClickListenet(View view, JamedOrgVO vo, int position) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(JamedApplyInformationFragment.SELECTCALLBACKNAME, vo.getName());
                bundle.putString(JamedApplyInformationFragment.SELECTCALLBACKOID, vo.getOid());
                bundle.putString(JamedApplyInformationFragment.SELECTCALLBACKAPPLY, vo.getOpenOnline());
                intent.putExtras(bundle);
                setResult(1000, intent);
                finish();
                return;


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

