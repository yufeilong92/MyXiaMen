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
import com.lawyee.apppublic.adapter.JanotaOrgRlvAdapter;
import com.lawyee.apppublic.adapter.ListDropDownAdapter;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.Constants;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JanotaService;
import com.lawyee.apppublic.ui.org.OrgDetailActivity;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JanotaOrgVO;
import com.lawyee.apppublic.vo.JanotaStaffFilterVo;
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
 * @Description: 机构信息列表(公证)
 * @author: YFL
 * @date: 2017/5/24 15:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/24 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JanotaOrgFragment extends BackHandledFragment {
    /**
     * 传入参数 公证界面-机构
     */
    public static final String CSTR_EXTRA_TITLE_STR = "jantoatitle";
    /**
     * 缓存标示
     */
    private static final String SAVEFILTERNAME = "JanotaOrgFragment";
    /**
     * 列表缓存标示
     */
    private static final String SAVELISTDATAS = "JanotaOrgList";
    /**
     * 访问网络的服务范围参数
     */
    private String mServiceParameter;
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
    private List<String> mDrawDownMenuHeaders = new ArrayList<>();
    private List<View> mPopupViews = new ArrayList<>();
    private DropDownMenu mDropDownMenu;
    private JanotaStaffFilterVo mFilterVO;

    //服务范围
    private List<String> mSercviceAreaList = new ArrayList<>();
    //市级名字集合
    private List<String> mCityStrList = new ArrayList<>();
    //区县级名字集合
    private List<String> mAreasStrList = new ArrayList<>();

    private JanotaOrgRlvAdapter mRlvAdapter;
    private GirdDropDownAdapter mServceAreaAdapter;
    private ListDropDownAdapter mCityAdapter;
    private ListDropDownAdapter mAreaAdapter;
    private boolean hadIntercept;
    private RecyclerView mRlvJaaidOrg;
    /**
     * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
     */
    boolean mInProgess = false;


    /**
     * 数据列表
     */
    protected ArrayList mDataList;
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
        Object o = BaseVO.loadVO(JanotaStaffFilterVo.dataFileName(getActivity(), SAVEFILTERNAME));
        if (o != null && (o instanceof JanotaStaffFilterVo)) {
            mFilterVO = (JanotaStaffFilterVo) o;
            initServiceArea(mFilterVO.getNowSelServiceArea() < 0 ? null : mFilterVO.getSercieAreas());
            initCity(mFilterVO.getNowSelNtzCity() < 0 ? null : mFilterVO.getNtzCityDatas());
            initAreas(mFilterVO.getNowSelNtzCity() < 0 || mFilterVO.getNtzCityDatas() == null ||
                    mFilterVO.getNowSelNtzCity() >= mFilterVO.getNtzCityDatas().size() ?
                    null : mFilterVO.getNtzCityDatas().get(mFilterVO.getNowSelNtzCity()));
        } else if (mFilterVO == null) {
            mFilterVO = new JanotaStaffFilterVo();
            initServiceArea(null);
            initCity(null);
            initAreas(null);
        }
        initDrawDownMenuData();
        initDefaultData();
        return view;
    }


    /**
     * 初始化服务范围
     */
    private void initServiceArea(List<BaseCommonDataVO> bdvo) {

        if (mFilterVO.getSercieAreas() != null)
            mFilterVO.getSercieAreas().clear();
        mFilterVO.getSercieAreas().clear();
        mSercviceAreaList.clear();
        mSercviceAreaList.add(CSTR_ALL);
        if (bdvo == null || mFilterVO.getSercieAreas().isEmpty()) {
            List<BaseCommonDataVO> dataVOs = DataManage.getInstance().getJanotaServiceArae();
            if (dataVOs != null && !dataVOs.isEmpty()) {
                mFilterVO.setSercieAreas(dataVOs);
                for (BaseCommonDataVO mbvo : dataVOs) {
                    mSercviceAreaList.add(mbvo.getName());
                }
            }
        } else {
            for (BaseCommonDataVO bd : bdvo
                    ) {
                mSercviceAreaList.add(bd.getName());
            }
        }
    }

    /**
     * 初始化城市（市）
     */
    private void initCity(List<BaseCommonDataVO> vo) {

        if(mFilterVO.getNtzCityDatas()!=null)
            mFilterVO.getNtzCityDatas().clear();
        mCityStrList.clear();
        mCityStrList.add(CSTR_ALL);
        if (vo == null || mFilterVO.getNtzCityDatas().isEmpty()) {
            List<BaseCommonDataVO> dvo = BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas(),
                    Constants.PROVINCE_GUIZHOU_ID);
            mFilterVO.setNtzCityDatas(dvo);
            if (mFilterVO.getNtzCityDatas() == null || mFilterVO.getNtzCityDatas().isEmpty()) {
                return;
            }
            for (BaseCommonDataVO dataVO : mFilterVO.getNtzCityDatas()) {
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
        if(mFilterVO.getNtzAreasDatas()==null)
            mFilterVO.getNtzAreasDatas().clear();
        mAreasStrList.clear();
        mAreasStrList.add(CSTR_ALL);
        if (dvo == null) {
            return;
        }
        mFilterVO.setNtzAreasDatas(BaseCommonDataVO.getDataVOListWithParentId(ApplicationSet.getInstance().getAreas()
                , dvo.getOid()));
        if (mFilterVO.getNtzAreasDatas() == null || mFilterVO.getNtzAreasDatas().isEmpty()) {
            return;
        }
        for (BaseCommonDataVO bdvo : mFilterVO.getNtzAreasDatas()) {
            mAreasStrList.add(bdvo.getName());

        }


    }

    private void initView(View view) {
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu_org);
        mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_service));
        mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_city));
        mDrawDownMenuHeaders.add(getActivity().getResources().getString(R.string.org_area));

    }

    /**
     * 判读是否有选择
     */
    private void selectCondition() {
        mServiceParameter = (mFilterVO.getNowSelServiceArea() > -1 ? mFilterVO.getSercieAreas().get(mFilterVO.getNowSelServiceArea()).getOid() : null);
        mCityParameter = (mFilterVO.getNowSelNtzCity() > -1 ? mFilterVO.getNtzCityDatas().get(mFilterVO.getNowSelNtzCity()).getOid() : null);
        mAreaParameter = (mFilterVO.getNowSelNtzAreas() > -1 ? mFilterVO.getNtzAreasDatas().get(mFilterVO.getNowSelNtzAreas()).getOid() : null);
    }

    private void initDrawDownMenuData() {
        final ListView serviceArea = new ListView(getActivity());
        mServceAreaAdapter = new GirdDropDownAdapter(getActivity(), mSercviceAreaList);
        mServceAreaAdapter.setCheckItem(mFilterVO.getNowSelServiceArea() + 1);
        serviceArea.setDividerHeight(0);
        serviceArea.setAdapter(mServceAreaAdapter);

        //init age menu
        final ListView city = new ListView(getActivity());
        city.setDividerHeight(0);
        mCityAdapter = new ListDropDownAdapter(getActivity(), mCityStrList);
        mCityAdapter.setCheckItem(mFilterVO.getNowSelNtzCity() + 1);
        city.setAdapter(mCityAdapter);

        //init sex menu
        final ListView area = new ListView(getActivity());
        area.setDividerHeight(0);
        mAreaAdapter = new ListDropDownAdapter(getActivity(), mAreasStrList);
        mAreaAdapter.setCheckItem(mFilterVO.getNowSelNtzAreas() + 1);
        area.setAdapter(mAreaAdapter);

        //init mPopupViews
        if (mPopupViews.size() == 0) {
            mPopupViews.add(serviceArea);
            mPopupViews.add(city);
            mPopupViews.add(area);
        } else {
            mPopupViews.clear();
            mPopupViews.add(serviceArea);
            mPopupViews.add(city);
            mPopupViews.add(area);
        }
        //add item click event
        serviceArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelServiceArea(position - 1);
                mServceAreaAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(0) : mSercviceAreaList.get(position));
                mCityAdapter.notifyDataSetChanged();
                mAreaAdapter.notifyDataSetChanged();
                mDropDownMenu.closeMenu();
                upData();
            }


        });

        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFilterVO.setNowSelNtzCity(position - 1);
                mFilterVO.setNowSelNtzAreas(-1);
                mCityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(position));
                initAreas(position == 0 || mFilterVO.getNtzCityDatas() == null ||
                        position - 1 >= mFilterVO.getNtzCityDatas().size() ?
                        null : mFilterVO.getNtzCityDatas().get(position - 1));
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
                mFilterVO.setNowSelNtzAreas(position - 1);
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
        mDropDownMenu.setTabText(0, mFilterVO.getNowSelServiceArea() == -1 ? mDrawDownMenuHeaders.get(0) : mSercviceAreaList.get(mFilterVO.getNowSelServiceArea() + 1));
        mDropDownMenu.setTabText(1, mFilterVO.getNowSelNtzCity() == -1 ? mDrawDownMenuHeaders.get(1) : mCityStrList.get(mFilterVO.getNowSelNtzCity() + 1));
        mDropDownMenu.setTabText(2, mFilterVO.getNowSelNtzAreas() == -1 ? mDrawDownMenuHeaders.get(2) : mAreasStrList.get(mFilterVO.getNowSelNtzAreas() + 1));
    }

    /**
     * 更新数据
     */
    private void upData() {
        //先缓存查询数据
        BaseVO.saveVO(mFilterVO, JanotaStaffFilterVo.dataFileName(getActivity(), SAVEFILTERNAME));
        selectCondition();
//        handlerRequestService(1, mServiceParameter, mCityParameter, mAreaParameter);
        JanotaService service = new JanotaService(getActivity());
        service.setProgressShowContent(getString(R.string.loading));
        service.setShowProgress(true);
        service.queryOrgList(1, mServiceParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                mXrefreshView.stopRefresh();
                if (values == null || values.isEmpty()) {
                    T.showShort(getActivity(), content);
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
                JanotaOrgVO.saveVOList(mDataList, JanotaOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
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
                mXrefreshView.stopRefresh();
                T.showShort(getActivity(), msg);

            }
        });

    }

    /**
     * 读取缓存数据
     */
    private void initDefaultData() {
        clearDataList();
//        读取缓存
        ArrayList<?> list = JanotaOrgVO.loadVOList(JanotaOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
        if (list != null && !list.isEmpty()) {//有缓存添加数据
            addDataList(list);
        } else {//无缓存请求网络
            handlerRequestService(1, null, null, null);
        }
        setAdapterData();
        Boolean museRefresh = true;
        //判断是否有效期
        if (mDataList != null && !mDataList.isEmpty()) {
            JanotaOrgVO vo;
            Object o = mDataList.get(0);
            if (o instanceof JanotaOrgVO) {
                vo = (JanotaOrgVO) o;
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
            mXrefreshView.startRefresh();
        }
    }

    /**
     * 处理网络请求
     *
     * @param id
     * @param province
     * @param city
     * @param area
     */
    private void handlerRequestService(int id, String province, String city, String area) {
        JanotaService service = new JanotaService(getActivity());
        service.queryOrgList(id, province, city, area, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                mInProgess = false;
                mXrefreshView.stopRefresh();
                if (values == null || values.isEmpty()) {
                    T.showShort(getActivity(), content);
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
                JanotaOrgVO.saveVOList(mDataList, JanotaOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
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
                mXrefreshView.stopRefresh();
                T.showShort(getActivity(), msg);

            }
        });

    }

    /**
     * 设置适配器
     */
    private void setAdapterData() {
        mRlvAdapter = new JanotaOrgRlvAdapter(mDataList, getActivity());
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRlvJaaidOrg.addItemDecoration(new RecycleViewDivider(getActivity(), GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        mRlvJaaidOrg.setLayoutManager(linearLayoutManager);
        mRlvJaaidOrg.setAdapter(mRlvAdapter);
        mRlvAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        mRlvAdapter.setItemClickListener(new JanotaOrgRlvAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void ItemClickListenet(View view, JanotaOrgVO vo, int position) {

                Intent intent = new Intent(getActivity(), OrgDetailActivity.class);
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.intent_janota_type));
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_DETAIL_TYEP, OrgDetailActivity.CSTR_EXTRA_JANOTADETAIL_TYPE);
                intent.putExtra(OrgDetailActivity.CSTR_EXTRA_ORGID_ID, vo.getOid());
                startActivity(intent);
            }
        });
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
     * 清除数据
     */
    private void clearDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList();
        } else
            mDataList.clear();
    }

    /**
     * 刷新控件设置
     */
    private void initRefresh(View view) {
        //设置是否能上拉刷新
        mXrefreshView.setPullLoadEnable(false);
        mXrefreshView.restoreLastRefreshTime(01);
        //设置是否能下拉刷新
        mXrefreshView.setPullRefreshEnable(true);
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
     * 加载更多数据
     */
    private void loadMoreData() {
        if (mInProgess)
            return;
        mInProgess = true;
        JanotaService service = new JanotaService(getActivity());
        selectCondition();
        service.queryOrgList(getNowPage() + 1, mServiceParameter, mCityParameter, mAreaParameter, new BaseJsonService.IResultInfoListener() {
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
                }else {
                    mXrefreshView.setLoadComplete(true);
                    mRlvAdapter.notifyDataSetChanged();
                    return;
                }
                //是否有效期
                JanotaOrgVO.saveVOList(mDataList, JanotaOrgVO.dataListFileName(getActivity(), SAVELISTDATAS));
                if (!mDataList.isEmpty() && mDataList.size() % Constants.CINT_PASSWORD_LEN_MIN == 0) {//设置是否可以上拉加载
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
                T.showLong(getActivity(), msg);
            }
        });
    }

    /**
     * 刷新下拉加载更多
     */
    private void loadNewData() {
        if (mInProgess)
            return;
        mInProgess = true;
        selectCondition();
        handlerRequestService(1, mServiceParameter, mCityParameter, mAreaParameter);
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
    public void onDestroy() {
        super.onDestroy();
        mDropDownMenu.removeAllViews();
    }
}
