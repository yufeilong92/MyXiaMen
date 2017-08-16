package com.lawyee.apppublic.ui.frag;

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
import com.lawyee.apppublic.adapter.JaauthOrgRlvAdapter;
import com.lawyee.apppublic.adapter.ListDropDownAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaauthService;
import com.lawyee.apppublic.ui.org.OrgDetailActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaaidOrgVO;
import com.lawyee.apppublic.vo.JaauthFilterVo;
import com.lawyee.apppublic.vo.JaauthOrgVO;
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
 * @Title: JanotaOrgFragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 机构信息列表(鉴定)
 * @author: YFL
 * @date: 2017/5/24 15:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/24 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JaauthOrgFragment extends BackHandledFragment {
    //列表首项全部
    private static final String CSTR_ALL = "全部";
    /**
     * 缓存标示
     */
    private static final String SAVEFILTERNAME = "JauuthOrgFragment";
    /**
     * 列表缓存标示
     */
    private static final String SAVELISTDATAS = "JauuthOrgList";
    /**
     * 访问网络的服务范围参数
     */
    private String mBusinessParameter;
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
    boolean mInProgess = false;
    /**
     * 数据列表
     */
    protected ArrayList mDataList;
    private List<String> mDrawDownMenuHeaders = new ArrayList<>();
    private List<View> mPopupViews = new ArrayList<>();
    private DropDownMenu mDropDownMenu;
    private JaauthFilterVo mFilterVO;
    //业务范围
    private List<String> mBusinessAreaList = new ArrayList<>();
    //市级名字集合
    private List<String> mCityStrList = new ArrayList<>();
    //区县级名字集合
    private List<String> mAreasStrList = new ArrayList<>();
    private JaauthOrgRlvAdapter mRlvAdapter;
    private GirdDropDownAdapter mServceAreaAdapter;
    private ListDropDownAdapter mCityAdapter;
    private ListDropDownAdapter mAreaAdapter;
    private boolean hadIntercept;
    private RecyclerView mRlvJaaidOrg;
    private XRefreshView mXrefreshView;

    @Override
    public boolean onBackPressed() {
        if (hadIntercept) {
            return false;
        } else {
            if (mDropDownMenu.isShowing()) {
                mDropDownMenu.closeMenu();
            }
            hadIntercept = true;
            return true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_janota_org, container, false);
        initView(view);
        Object o = BaseVO.loadVO(JaauthFilterVo.dataFileName(getActivity(), SAVEFILTERNAME));
        if (o != null && (o instanceof JaauthFilterVo)) {
            mFilterVO = (JaauthFilterVo) o;
            initBusinessArea(mFilterVO.getmNowSelBusiness() < 0 ? null : mFilterVO.getmBusinessList());
            initCity(mFilterVO.getmNowSelCity() < 0 ? null : mFilterVO.getmJaauthCitys());
            initAreas(mFilterVO.getmNowSelCity() < 0 || mFilterVO.getmJaauthCitys() == null ||
                    mFilterVO.getmNowSelCity() >= mFilterVO.getmJaauthCitys().size() ?
                    null : mFilterVO.getmJaauthCitys().get(mFilterVO.getmNowSelCity()));
        }
        if (mFilterVO == null) {
            mFilterVO = new JaauthFilterVo();
            initBusinessArea(null);
            initCity(null);
            initAreas(null);
        }
        initDrawDownMenuData();
        initDefaultData();
        return view;
    }


    /**
     * 初始化业务范围
     */
    private void initBusinessArea(List<BaseCommonDataVO> bdvo) {
        if(mFilterVO.getmBusinessList()!=null)
            mFilterVO.getmBusinessList().clear();
        mBusinessAreaList.clear();
        mBusinessAreaList.add(CSTR_ALL);

        if (bdvo == null || mFilterVO.getmBusinessList().isEmpty()) {
            List<BaseCommonDataVO> dataVOs = DataManage.getInstance().getJaauthBusinessArae();
            if (dataVOs != null && !dataVOs.isEmpty()) {
                mFilterVO.setmBusinessList(dataVOs);
                for (BaseCommonDataVO mbvo : dataVOs) {
                    mBusinessAreaList.add(mbvo.getName());
                }
            }
        } else {
            for (BaseCommonDataVO bd : bdvo
                    ) {
                mBusinessAreaList.add(bd.getName());
            }
        }
    }

    /**
     * 初始化城市（市）
     */
    private void initCity(List<BaseCommonDataVO> vo) {
        if(mFilterVO.getmJaauthCitys()!=null)
            mFilterVO.getmJaauthCitys().clear();
        mCityStrList.clear();
        mCityStrList.add(CSTR_ALL);
        if (vo == null || mFilterVO.getmJaauthCitys().isEmpty()) {
            List<BaseCommonDataVO> dvo = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(),
                    Constants.PROVINCE_GUIZHOU_ID);
            mFilterVO.setmJaauthCitys(dvo);
            if (mFilterVO.getmJaauthCitys() == null || mFilterVO.getmJaauthCitys().isEmpty()) {
                return;
            }
            for (BaseCommonDataVO dataVO : mFilterVO.getmJaauthCitys()) {
                mCityStrList.add(dataVO.getName());
            }
        } else {
            for (BaseCommonDataVO v : vo
                    ) {
                mCityStrList.add(v.getName());
            }
        }
    }

    /**
     * .初始化区县
     */
    private void initAreas(BaseCommonDataVO dvo) {
        if(mFilterVO.getmJaauthAreas()!=null)
            mFilterVO.getmJaauthAreas().clear();
        mAreasStrList.clear();
        mAreasStrList.add(CSTR_ALL);
        if (dvo == null) {
            return;
        } else {
            mFilterVO.setmJaauthAreas(BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas()
                    , dvo.getOid()));
            if (mFilterVO.getmJaauthAreas() == null || mFilterVO.getmJaauthAreas().isEmpty()) {
                return;
            }
            for (BaseCommonDataVO bdvo : mFilterVO.getmJaauthAreas()) {
                mAreasStrList.add(bdvo.getName());
            }
        }
    }

    private void initView(View view) {
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu_org);
        clearHearderDatas();
    }

    /**
     * 处理数据
     */
    private void clearHearderDatas() {
        if (mDrawDownMenuHeaders.size() == 0) {
            mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_business));
            mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_city));
            mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_area));
        } else {
            mDrawDownMenuHeaders.clear();
            mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_business));
            mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_city));
            mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_area));
        }
    }

    private void initDrawDownMenuData() {
        final ListView business = new ListView(getActivity());
        mServceAreaAdapter = new GirdDropDownAdapter(getActivity(), mBusinessAreaList);
        mServceAreaAdapter.setCheckItem(mFilterVO.getmNowSelBusiness() + 1);
        business.setDividerHeight(0);
        business.setAdapter(mServceAreaAdapter);

        //init age menu
        final ListView city = new ListView(getActivity());
        city.setDividerHeight(0);
        mCityAdapter = new ListDropDownAdapter(getActivity(), mCityStrList);
        mCityAdapter.setCheckItem(mFilterVO.getmNowSelCity() + 1);
        city.setAdapter(mCityAdapter);

        //init sex menu
        final ListView area = new ListView(getActivity());
        area.setDividerHeight(0);
        mAreaAdapter = new ListDropDownAdapter(getActivity(), mAreasStrList);
        mAreaAdapter.setCheckItem(mFilterVO.getmNowSelArea() + 1);
        area.setAdapter(mAreaAdapter);

        //init mPopupViews
        if (mPopupViews.size() == 0) {
            mPopupViews.add(business);
            mPopupViews.add(city);
            mPopupViews.add(area);
        } else {
            mPopupViews.clear();
            mPopupViews.add(business);
            mPopupViews.add(city);
            mPopupViews.add(area);
        }

        //add item click event
        business.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setmNowSelBusiness(position - 1);
                mServceAreaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(0) : mBusinessAreaList.get(position));
                mCityAdapter.notifyDataSetChanged();
                mAreaAdapter.notifyDataSetChanged();
                mDropDownMenu.closeMenu();
                upData();
            }


        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setmNowSelCity(position - 1);
                mFilterVO.setmNowSelArea(-1);
                mCityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(position));
                initAreas(position == 0 || mFilterVO.getmJaauthCitys() == null ||
                        position - 1 >= mFilterVO.getmJaauthCitys().size() ?
                        null : mFilterVO.getmJaauthCitys().get(position - 1));
                mAreaAdapter.setCheckItem(0);
                mAreaAdapter.notifyDataSetChanged();
                mDropDownMenu.setTabText(2, mDrawDownMenuHeaders.get(2));
                mDropDownMenu.closeMenu();
                upData();
            }
        });

        area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAreaAdapter.setCheckItem(position);
                mFilterVO.setmNowSelArea(position - 1);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(2) : mAreasStrList.get(position));
                mDropDownMenu.closeMenu();
                upData();
            }
        });

        //init context view

        View mContentView = (LayoutInflater.from(getActivity()).inflate(R.layout.activity_jaaidorg_content, null));
        mRlvJaaidOrg = (RecyclerView) mContentView.findViewById(R.id.rlv_jaaidorg);
        mXrefreshView = (XRefreshView) mContentView.findViewById(R.id.xrefreshview_org);

        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initRefresh(mContentView);
        //init dropdownview
        mDropDownMenu.setDropDownMenu(mDrawDownMenuHeaders, mPopupViews, mContentView);
        mDropDownMenu.setTabText(0, mFilterVO.getmNowSelBusiness() == -1 ? mDrawDownMenuHeaders.get(0) : mBusinessAreaList.get(mFilterVO.getmNowSelBusiness() + 1));
        mDropDownMenu.setTabText(1, mFilterVO.getmNowSelCity() == -1 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(mFilterVO.getmNowSelCity() + 1));
        mDropDownMenu.setTabText(2, mFilterVO.getmNowSelArea() == -1 ? mDrawDownMenuHeaders.get(2) : mAreasStrList.get(mFilterVO.getmNowSelArea() + 1));
    }

    /**
     * 读取缓存数据
     */
    private void initDefaultData() {
        //读取缓存最后的列表数据
        clearListDatas();
        ArrayList<?> list = JaauthOrgVO.loadVOList(JaauthOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
        if (list != null && !list.isEmpty()) {
            addDataList(list);
        } else {
            handlerRequestJaauthService(1, null, null, null);
        }
        setAdapterData();
        Boolean museRefresh = true;
        if (mDataList != null && !mDataList.isEmpty()) {
            JaauthOrgVO vo;
            Object o = mDataList.get(0);
            if (o instanceof JaauthOrgVO) {
                vo = (JaauthOrgVO) o;
                mXrefreshView.restoreLastRefreshTime(vo.getVoCreateDate().getTime());
                if (vo.isEffectiveTimeData(Constants.CINT_EFFECTIVE_NEWS_TIME)) {
                    museRefresh = false;
                }
            }
            if (mDataList.size() % Constants.CINT_PAGE_SIZE == 0) {
                //设置是否可以上拉加载
                mXrefreshView.setPullLoadEnable(true);
            }
        }
        if (museRefresh) {
            mXrefreshView.stopRefresh();
        }

    }

    /**
     * 判读是否有选择
     */
    private void selectCondition() {
        mBusinessParameter = (mFilterVO.getmNowSelBusiness() > -1 ? mFilterVO.getmBusinessList().get(mFilterVO.getmNowSelBusiness()).getOid() : null);
        mCityParameter = (mFilterVO.getmNowSelCity() > -1 ? mFilterVO.getmJaauthCitys().get(mFilterVO.getmNowSelCity()).getOid() : null);
        mAreaParameter = (mFilterVO.getmNowSelArea() > -1 ? mFilterVO.getmJaauthAreas().get(mFilterVO.getmNowSelArea()).getOid() : null);
    }

    /**
     * 请求鉴定机构数据
     *
     * @param id
     * @param mBusinessParameter
     * @param mCityParameter
     * @param mAreaParameter
     */
    private void handlerRequestJaauthService(int id, String mBusinessParameter, String mCityParameter, String mAreaParameter) {
        JaauthService jaauthService = new JaauthService(getActivity());
        jaauthService.queryOrgList(id, mBusinessParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                mXrefreshView.stopRefresh();
                if (values == null || values.isEmpty()) {
                    T.showShort(getActivity(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                clearListDatas();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                } else {
                    mXrefreshView.setLoadComplete(true);
                    mRlvAdapter.notifyDataSetChanged();
                    return;
                }
                //缓存数据
                JaauthOrgVO.saveVOList(mDataList, JaauthOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PAGE_SIZE == 0) {
                    //设置是否可以下拉加载
                    mXrefreshView.setPullLoadEnable(true);
                    mXrefreshView.setLoadComplete(false);
                } else
                    mXrefreshView.setLoadComplete(true);
                mRlvAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String msg, String content) {
                mInProgess = false;
                mXrefreshView.stopLoadMore();
                T.showShort(getActivity(), msg);
            }
        });

    }

    /**
     * 清楚数据
     */
    private void clearListDatas() {
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
            clearListDatas();
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        mDataList.addAll(list);
    }

    /**
     * 更新数据
     */
    private void upData() {
        //先缓存查询数据
        BaseVO.saveVO(mFilterVO, JaauthFilterVo.dataFileName(getActivity(), SAVEFILTERNAME));
        selectCondition();
        JaauthService jaauthService = new JaauthService(getActivity());
        jaauthService.setShowProgress(true);
        jaauthService.setProgressShowContent(getString(R.string.loading));
        jaauthService.queryOrgList(1, mBusinessParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                mXrefreshView.stopRefresh();
                if (values == null || values.isEmpty()) {
                    T.showShort(getActivity(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                clearListDatas();
                if (list != null && !list.isEmpty()) {
                    addDataList(list);
                } else {
                    mXrefreshView.setLoadComplete(true);
                    mRlvAdapter.notifyDataSetChanged();
                    return;
                }
                //缓存数据
                JaauthOrgVO.saveVOList(mDataList, JaauthOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PAGE_SIZE == 0) {
                    //设置是否可以下拉加载
                    mXrefreshView.setPullLoadEnable(true);
                    mXrefreshView.setLoadComplete(false);
                } else
                    mXrefreshView.setLoadComplete(true);
                mRlvAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String msg, String content) {
                mInProgess = false;
                mXrefreshView.stopLoadMore();
                T.showShort(getActivity(), msg);
            }
        });


    }

    /**
     * 设置适配器
     */
    private void setAdapterData() {
        mRlvAdapter = new JaauthOrgRlvAdapter(mDataList, getActivity());
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRlvJaaidOrg.addItemDecoration(new RecycleViewDivider(getActivity(), GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvJaaidOrg.setLayoutManager(linearLayoutManager);
        mRlvJaaidOrg.setAdapter(mRlvAdapter);
        mRlvAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        mRlvAdapter.setItemClickListener(new JaauthOrgRlvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void ItemClickListenet(View view, JaauthOrgVO vo, int position) {
                Intent intent = new Intent(getActivity(), OrgDetailActivity.class);
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_ORGID_ID, vo.getOid());
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_DETAIL_TYEP, OrgDetailActivity.CSTR_EXTRA_JAAUTHDETAIL_TYPE);
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.intent_jaauth_type));
                startActivity(intent);
            }
        });
    }

    /**
     * 刷新控件设置
     */
    private void initRefresh(View view) {
        //设置是否上拉加载
        mXrefreshView.setPullLoadEnable(false);
        mXrefreshView.restoreLastRefreshTime(01);
        //设置是否能下拉刷新
        mXrefreshView.setPullRefreshEnable(true);
        mXrefreshView.setEmptyView(view.findViewById(R.id.jaaidorg_content_empty_tv));
        mXrefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                loadNewDatas();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadMoreData();
            }
        });

    }

    /**
     * 加载新的数据
     */
    private void loadNewDatas() {
        if (mInProgess) {
            return;
        }
        mInProgess = true;
        selectCondition();
        handlerRequestJaauthService(1, mBusinessParameter, mCityParameter, mAreaParameter);
    }

    /**
     * 加载更多数据
     */
    private void loadMoreData() {
        if (mInProgess)
            return;
        JaauthService jaauthService = new JaauthService(getActivity());
        selectCondition();
        jaauthService.queryOrgList(getNowPage() + 1, mBusinessParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
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
                //是否有效期
                JaauthOrgVO.saveVOList(mDataList, JaaidOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PASSWORD_LEN_MIN == 0) {
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
                T.showLong(getActivity(), msg);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mDropDownMenu.removeAllViews();
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
