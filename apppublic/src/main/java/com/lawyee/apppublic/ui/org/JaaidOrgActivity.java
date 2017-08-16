package com.lawyee.apppublic.ui.org;

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
import com.lawyee.apppublic.adapter.JaaidOrgRlvAdapter;
import com.lawyee.apppublic.adapter.ListDropDownAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaaidService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaaidOrgFilterVO;
import com.lawyee.apppublic.vo.JaaidOrgVO;
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
 * @Title: JaaidOrgActivity.java
 * @Package com.lawyee.apppublic.ui.mechanism
 * @Description: 法援机构列表页
 * @author: YFL
 * @date: 2017/5/22 10:44
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/22 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaaidOrgActivity extends BaseActivity {
    /**
     * 缓存标示
     */
    private static final String SAVEFILTERNAME = "JaaidOrgActivity";
    /**
     * 机构列表缓存数据
     */
    private static final String SAVEORGLISTS = "JaaidOrgLists";
    /**
     * 传递参数-申请机构标示
     */
    public static final String SELECTORG = "selectorg";
    /**
     * 数据列表
     */
    protected ArrayList mDataList;
    private XRefreshView mXrefreshView;
    /**
     * 访问网络的省参数
     */
    private String mProvinceParameter;
    /**
     * 访问网络的市参数
     */
    private String mCityParameter;
    /**
     * 访问网络的区县参数
     */
    private String mAreaParameter;
    //列表首项全部
    private static final String CSTR_ALL = "全部";
    private List<View> mPopupViews = new ArrayList<>();
    private GirdDropDownAdapter mProvinceAdapter;
    private ListDropDownAdapter mCityAdapter;
    private ListDropDownAdapter mAreaAdapter;
    private JaaidOrgFilterVO mFilterVO;
    // 选择的标题
    private List<String> mDrawDownMenuHeaders = new ArrayList<>();
    //省级名字集合
    private List<String> mProvinceStrList = new ArrayList<>();
    //市级名字集合
    private List<String> mCityStrList = new ArrayList<>();
    //区县级名字集合
    private List<String> mAreasStrList = new ArrayList<>();
    private DropDownMenu mDropDownMenu;
    private JaaidOrgRlvAdapter mRlvAdapter;
    private View mContentView;


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jaaid_org);
        //初始化缓存数据
        Object o = BaseVO.loadVO(JaaidOrgFilterVO.dataFileName(this, SAVEFILTERNAME));
        if (o != null && (o instanceof JaaidOrgFilterVO)) {
            mFilterVO = (JaaidOrgFilterVO) o;
            initProvince(false);
            initCity(mFilterVO.getNowSelProvince() < 0 ||mFilterVO.getProvinceDatas()==null||
                    mFilterVO.getNowSelProvince()>=mFilterVO.getProvinceDatas().size() ?
                    null : mFilterVO.getProvinceDatas().get(mFilterVO.getNowSelProvince()));
            initAreas(mFilterVO.getNowSelCity() < 0 ||mFilterVO.getCityDatas()==null||
                    mFilterVO.getNowSelCity()>=mFilterVO.getCityDatas().size()?
                    null : mFilterVO.getCityDatas().get(mFilterVO.getNowSelCity()));
        }
        if (mFilterVO == null) {
            mFilterVO = new JaaidOrgFilterVO();
            initProvince(true);
            initCity(null);
            initAreas(null);
        }

        //init context view
        mContentView = (LayoutInflater.from(this).inflate(R.layout.activity_jaaidorg_content, null));
        mXrefreshView = (XRefreshView) mContentView.findViewById(R.id.xrefreshview_org);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initRefresh(mContentView);
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        initDrawDownMenuData();
        loadData();
    }

    /**
     * 初始化省数据
     *
     * @param initdvo
     */
    private void initProvince(boolean initdvo) {
        mProvinceStrList.add(CSTR_ALL);
        if (initdvo || mFilterVO.getProvinceDatas().isEmpty()) {
            BaseCommonDataVO dvo = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getAreas(), Constants.PROVINCE_GUIZHOU_ID);
            if (dvo != null) {
                mFilterVO.getProvinceDatas().add(dvo);
                mProvinceStrList.add(dvo.getName());
            }
        } else {
            mProvinceStrList.add(mFilterVO.getProvinceDatas().get(0).getName());
        }
    }

    /**
     * 初始化市数据
     *
     * @param dvo
     */
    private void initCity(BaseCommonDataVO dvo) {
        if(mCityStrList!=null)
            mCityStrList.clear();
        if(mFilterVO.getCityDatas()!=null)
            mFilterVO.getCityDatas().clear();

        mCityStrList.add(CSTR_ALL);
        if (dvo == null)
            return;
        mFilterVO.setCityDatas(BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(),
                dvo.getOid()));
        if (mFilterVO.getCityDatas() == null || mFilterVO.getCityDatas().isEmpty())
            return;
        for (BaseCommonDataVO bdvo : mFilterVO.getCityDatas()
                ) {
            mCityStrList.add(bdvo.getName());
        }
    }

    /**
     * 初始化地区数据
     *
     * @param dvo
     */
    private void initAreas(BaseCommonDataVO dvo) {
        if(mFilterVO.getAreasDatas()!=null)
            mFilterVO.getAreasDatas().clear();
        mAreasStrList.clear();

        mAreasStrList.add(CSTR_ALL);
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

    /**
     * 刷新控件设置
     */
    private void initRefresh(View view) {
//       设置是否能上拉刷新
        mXrefreshView.setPullLoadEnable(false);
//       设置是否能下拉刷新
        mXrefreshView.setPullRefreshEnable(true);
        mXrefreshView.restoreLastRefreshTime(0l);
        mXrefreshView.setEmptyView(view.findViewById(R.id.jaaidorg_content_empty_tv));
        mXrefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }

        });

    }

    /**
     * 刷新的数据
     */
    private void loadNewData() {
        selectCondition();
        handlerAassceService(false, mProvinceParameter, mCityParameter, mAreaParameter);
    }

    /**
     * 判读是否有选择
     */
    private void selectCondition() {
        mProvinceParameter = (mFilterVO.getNowSelProvince() > -1 ? mFilterVO.getProvinceDatas().get(mFilterVO.getNowSelProvince()).getOid() : null);
        mCityParameter = (mFilterVO.getNowSelCity() > -1 ? mFilterVO.getCityDatas().get(mFilterVO.getNowSelCity()).getOid() : null);
        mAreaParameter = (mFilterVO.getNowSelAreas() > -1 ? mFilterVO.getAreasDatas().get(mFilterVO.getNowSelAreas()).getOid() : null);
    }

    /**
     * 刷新加载更多
     */
    private void loadMoreData() {
        if (getInProgess())
            return;
        setInProgess(true);
        JaaidService service = new JaaidService(this);
        selectCondition();
        service.queryOrgList(getNowPage() + 1, mProvinceParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
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
                JaaidOrgVO.saveVOList(mDataList, JaaidOrgVO.dataListFileName(getApplicationContext(), SAVEFILTERNAME));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PASSWORD_LEN_MIN == 0) {
                    //设置是否可以上拉加载
                    mXrefreshView.setPullLoadEnable(true);
                    mXrefreshView.setLoadComplete(false);
                } else
                    mXrefreshView.setLoadComplete(true);
                mRlvAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String msg, String content) {
                setInProgess(false);
                mXrefreshView.stopLoadMore();
                T.showLong(getApplicationContext(), msg);
            }
        });

    }

    /**
     * 初始drrawDoen视图
     */
    private void initDrawDownMenuData() {
        mDrawDownMenuHeaders.add(getResources().getString(R.string.org_province));
        mDrawDownMenuHeaders.add(getResources().getString(R.string.org_city));
        mDrawDownMenuHeaders.add(getResources().getString(R.string.org_area));
        final ListView province = new ListView(this);
        mProvinceAdapter = new GirdDropDownAdapter(this, mProvinceStrList);
        province.setDividerHeight(0);
        mProvinceAdapter.setCheckItem(mFilterVO.getNowSelProvince() + 1);
        province.setAdapter(mProvinceAdapter);
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
        mPopupViews.add(province);
        mPopupViews.add(city);
        mPopupViews.add(area);

        //add item click event
        province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelProvince(position - 1);
                mProvinceAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(0) : mProvinceStrList.get(position));
                initCity(position == 0 ||mFilterVO.getProvinceDatas()==null||
                        mFilterVO.getProvinceDatas().size()<=position-1? null : mFilterVO.getProvinceDatas().get(position - 1));
                initAreas(null);
                mCityAdapter.notifyDataSetChanged();
                mAreaAdapter.notifyDataSetChanged();
                mFilterVO.setNowSelCity(-1);
                mFilterVO.setNowSelAreas(-1);
                mDropDownMenu.setTabText(1, mDrawDownMenuHeaders.get(1));
                mDropDownMenu.setTabText(2, mDrawDownMenuHeaders.get(2));
                mDropDownMenu.closeMenu();
                updata();
            }
        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelCity(position - 1);
                mCityAdapter.setCheckItem(position);
                mAreaAdapter.setCheckItem(0);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(position));
                initAreas(position == 0 ||mFilterVO.getCityDatas()==null||
                        position-1>=mFilterVO.getCityDatas().size()?
                        null : mFilterVO.getCityDatas().get(position - 1));
                mAreaAdapter.notifyDataSetChanged();
                mFilterVO.setNowSelAreas(-1);
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

        //init dropdownview
        mDropDownMenu.setDropDownMenu(mDrawDownMenuHeaders, mPopupViews, mContentView);
        mDropDownMenu.setTabText(0, mFilterVO.getNowSelProvince() == -1 ? mDrawDownMenuHeaders.get(0) : mProvinceStrList.get(mFilterVO.getNowSelProvince() + 1));
        mDropDownMenu.setTabText(1, mFilterVO.getNowSelCity() == -1 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(mFilterVO.getNowSelCity() + 1));
        mDropDownMenu.setTabText(2, mFilterVO.getNowSelAreas() == -1 ? mDrawDownMenuHeaders.get(2) : mAreasStrList.get(mFilterVO.getNowSelAreas() + 1));
    }

    /**
     * 读取缓存数据
     */
    private void loadData() {
        clearDataList();
        //读取缓存数据
        List list = JaaidOrgVO.loadVOList(JaaidOrgVO.dataListFileName(this, SAVEORGLISTS));
        if (list != null && !list.isEmpty()) {//有缓存
            addDataList(list);
        } else {//无缓存
            handlerAassceService(false, null, null, null);
        }
        setAdapterData();
        Boolean mustRefresh = true;
        //无缓存数据就加载更新
        if (mDataList != null && !mDataList.isEmpty()) {
            Object o = mDataList.get(0);
            if (o instanceof JaaidOrgVO) {
                mustRefresh = false;
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

    /**
     * 访问服务数据
     *
     * @param showprocess 是否显示加载中
     * @param province 省oid
     * @param city     市oid
     * @param area     区县oid
     */
    private void handlerAassceService(boolean showprocess, String province, String city, String area) {
        if(getInProgess())
            return;
        JaaidService jaaidService = new JaaidService(this);
        if(showprocess) {
            jaaidService.setProgressShowContent(getString(R.string.loading));
            jaaidService.setShowProgress(true);
        }
        setInProgess(true);
        jaaidService.queryOrgList(1, province, city, area, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                setInProgess(false);
                mXrefreshView.stopRefresh();
                if (values == null || values.isEmpty()) {
                    T.showShort(getApplicationContext(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                clearDataList();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                }else {
                    mXrefreshView.setLoadComplete(true);
                    mRlvAdapter.notifyDataSetChanged();
                    return;
                }
                //缓存数据
                JaaidOrgVO.saveVOList(mDataList, JaaidOrgVO.dataListFileName(getApplicationContext(), SAVEORGLISTS));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PAGE_SIZE == 0) {
                    //设置是否可以上拉加载
                    mXrefreshView.setPullLoadEnable(true);
                    mXrefreshView.setLoadComplete(false);
                } else {
                    mXrefreshView.setPullLoadEnable(false);
                    mXrefreshView.setLoadComplete(false);
                }
                mRlvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg, String content) {
                setInProgess(false);
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
        } else
            mDataList.clear();
    }

    /**
     * 增加列表数据
     *
     * @param list
     */
    private void addDataList(List<?> list) {
        if (mDataList == null)
            clearDataList();
        if (list == null || list.isEmpty())
            return;
        mDataList.addAll(list);
    }

    /**
     * 查询条件变更
     */
    private void updata() {
        //先缓存查询条件
        BaseVO.saveVO(mFilterVO, JaaidOrgFilterVO.dataFileName(JaaidOrgActivity.this, SAVEFILTERNAME));
        selectCondition();
        handlerAassceService(true, mProvinceParameter, mCityParameter, mAreaParameter);
    }

    private void setAdapterData() {
        mRlvAdapter = new JaaidOrgRlvAdapter(mDataList, this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);
        RecyclerView mRlvJaaidOrg = (RecyclerView) mContentView.findViewById(R.id.rlv_jaaidorg);
        mRlvJaaidOrg.addItemDecoration(new RecycleViewDivider(this, GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvJaaidOrg.setLayoutManager(linearLayoutManager);
        mRlvJaaidOrg.setAdapter(mRlvAdapter);
        mRlvAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mRlvAdapter.setItemClickListener(new JaaidOrgRlvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void ItemClickListenet(View view, JaaidOrgVO vo, int position) {
                Intent intent = new Intent(JaaidOrgActivity.this, OrgDetailActivity.class);
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.intent_jaaid_type));
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_DETAIL_TYEP, OrgDetailActivity.CSTR_EXTRA_JAAIDDETAIL_TYPE);
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_ORGID_ID, vo.getOid());
                startActivity(intent);
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

