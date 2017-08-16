package com.lawyee.apppublic.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.config.DataManage;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaaidService;
import com.lawyee.apppublic.dal.JaauthService;
import com.lawyee.apppublic.dal.JamedService;
import com.lawyee.apppublic.dal.JanotaService;
import com.lawyee.apppublic.ui.WalkingRouteActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.lawyee.apppublic.vo.BaseCommonDataVO;
import com.lawyee.apppublic.vo.JaaidOrgDetailVO;
import com.lawyee.apppublic.vo.JaauthOrgDetailVO;
import com.lawyee.apppublic.vo.JamedOrgDetailVO;
import com.lawyee.apppublic.vo.JanotaOrgDetailVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Org_Information_Fragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 机构详情信息
 * @author: YFL
 * @date: 2017/5/23 11:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/23 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class OrgInformationFragment extends Fragment implements View.OnClickListener {
    /**
     * 传入机构oid
     */
    public static final String CSTR_EXTRA_INFOTYPEID_OID = "orgdetailoid";

    /**
     * 传入参数 类型
     */
    public static final String CSTR_EXTRA_TYPE_TYPE = "type";

    /**
     * 传入类型 人民调解机构
     */
    public static final String CSTR_EXTRA_JAMED_TYPE = "jamedtype";

    /**
     * 传入 类型 法律援助机构
     */
    public static final String CSTR_EXTRA_JAAID_TYPE = "jaaidtype";
    /**
     * 传入 类型 公证机构
     */
    public static final String CSTR_EXTRA_JANOTA_TYPE = "janotatype";
    /**
     * 传入 类型 公证机构
     */
    public static final String CSTR_EXTRA_JAAUTH_TYPE = "jaauthtype";

    /**
     * 地图标题
     */
    private String mMapTitel;
    /**
     * 地图经度
     */
    private String mMapJing;
    /**
     * 地图纬度
     */
    private String mMapWei;
    /**
     * 机构地址
     */
    private String mMapAdress;

    /**
     * 机构电话
     */
    private String mMapPhone;
    /**
     * 服务时间
     */
    private String mServiceData;
    /**
     * 服务范围
     */
    private String mServiceRange;
    /**
     * 简介
     */
    private String mServiceSummary;
    private TextView mTvIntroduction;
    private TextView mTvServiceRange;
    private TextView mTvServiceTime;
    private LinearLayout mLiJamedserviceOrg;
    private String mOrgDetailOid;//机构oid
    private String mStrTypeData;
    private TextView mTvOrginfomTitle;
    private TextView mTvOrginfomPhoe;
    private TextView mTvOrginfomAddress;
    private LinearLayout mLiIntroduction;
    private LinearLayout mLiServiceRange;
    private LinearLayout mLiServiceTime;
    private ImageView mIvOrginfomMap;
    private TextView mTvOrgmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrgDetailOid = getArguments().getString(CSTR_EXTRA_INFOTYPEID_OID);
            mStrTypeData = getArguments().getString(CSTR_EXTRA_TYPE_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_org_information, container, false);
        handlerRequestData();
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTvIntroduction = (TextView) view.findViewById(R.id.tv_Introduction);
        mTvServiceRange = (TextView) view.findViewById(R.id.tv_service_range);
        mTvServiceTime = (TextView) view.findViewById(R.id.tv_service_time);
        mLiJamedserviceOrg = (LinearLayout) view.findViewById(R.id.li_jamedservice_org);
        mTvOrginfomTitle = (TextView) view.findViewById(R.id.tv_orginfom_title);
        mTvOrginfomPhoe = (TextView) view.findViewById(R.id.tv_orginfom_phoe);
        mTvOrginfomAddress = (TextView) view.findViewById(R.id.tv_orginfom_address);
        mLiIntroduction = (LinearLayout) view.findViewById(R.id.li_Introduction);
        mLiServiceRange = (LinearLayout) view.findViewById(R.id.li_service_range);
        mLiServiceTime = (LinearLayout) view.findViewById(R.id.li_service_time);
        mIvOrginfomMap = (ImageView) view.findViewById(R.id.iv_orginfom_map);
        mIvOrginfomMap.setOnClickListener(this);
        mTvOrgmap = (TextView) view.findViewById(R.id.tv_orgmap);
        mTvOrgmap.setOnClickListener(this);
    }

    private void handlerRequestData() {
        if (mStrTypeData.equals(CSTR_EXTRA_JAAID_TYPE)) {//法援机构详情
            requestJaaidService();
        } else if (mStrTypeData.equals(CSTR_EXTRA_JAAUTH_TYPE)) {//司法鉴定详情
            requestJaauthService();
        } else if (mStrTypeData.equals(CSTR_EXTRA_JAMED_TYPE)) {//人民调解机构详情
            requestJamedService();
        } else if (mStrTypeData.equals(CSTR_EXTRA_JANOTA_TYPE)) {//公证机构详情
            requestJanotaService();
        }

    }

    /**
     * 公证机构详情
     */
    private void requestJanotaService() {
        JanotaService janotaService = new JanotaService(getActivity());
        janotaService.setProgressShowContent("加载中...");
        janotaService.setShowProgress(true);
        janotaService.getOrgDetail(mOrgDetailOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JanotaOrgDetailVO)) {
                    T.showShort(getActivity(), content);
                    return;
                }
                JanotaOrgDetailVO vo = (JanotaOrgDetailVO) values.get(0);
                if (vo == null)
                    return;
                setJanotaOrgData(vo);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(getActivity(), msg);
            }
        });

    }

    /**
     * 司法鉴定机构
     *
     * @param vo
     */
    private void setJanotaOrgData(JanotaOrgDetailVO vo) {
        if(vo==null)
            return;
        if (vo.getAxis() != null&&!TextUtils.isEmpty(vo.getAxis())) {
            List<String> mJaaidOrgMap = getAxis(vo.getAxis());
            mMapJing = mJaaidOrgMap.get(0);
            mMapWei = mJaaidOrgMap.get(1);
        } else {
            mTvOrgmap.setVisibility(View.VISIBLE);
            mIvOrginfomMap.setVisibility(View.GONE);
        }
        String address = DataManage.getInstance().getAddress(vo.getProvince(), vo.getCity(), vo.getCounty(), vo.getAddress());

        mMapTitel = vo.getName();
        mMapAdress = address;
        mMapPhone = vo.getTelephone();
        mTvOrginfomTitle.setText(vo.getName());

        mTvOrginfomAddress.setText(address);
        mTvOrginfomPhoe.setText(vo.getTelephone());
        String mapurl = UrlUtil.getStaticMapImgUrl(getActivity(),mMapJing,mMapWei);
        if(!StringUtil.isEmpty(mapurl))
        {
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(mapurl, mIvOrginfomMap);
        }
        mLiJamedserviceOrg.setVisibility(View.VISIBLE);
        mTvServiceRange.setText(getServiceAddress(vo.getServiceScope()));
        mTvServiceTime.setText(vo.getServiceTime());
        mTvIntroduction.setText(vo.getIntroduction());
    }

    /**
     * 人民调解机构详情
     */
    private void requestJamedService() {
        JamedService jamedService = new JamedService(getActivity());
        jamedService.setProgressShowContent("加载中...");
        jamedService.setShowProgress(true);
        jamedService.getOrgDetail(mOrgDetailOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JamedOrgDetailVO)) {
                    T.showShort(getActivity(), content);
                    return;
                }
                JamedOrgDetailVO jamedOrgDetailVO = (JamedOrgDetailVO) values.get(0);
                if (jamedOrgDetailVO == null)
                    return;
                setJamedOrgData(jamedOrgDetailVO);
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(getActivity(), msg);
            }
        });
    }

    /**
     * 人民调解机构
     *
     * @param vo
     */
    private void setJamedOrgData(JamedOrgDetailVO vo) {
        if(vo==null)
            return;
        if (vo.getAxis() != null&&!TextUtils.isEmpty(vo.getAxis())) {
            List<String> mJaaidOrgMap = getAxis(vo.getAxis());
            mMapJing = mJaaidOrgMap.get(0);
            mMapWei = mJaaidOrgMap.get(1);
        } else {
            mTvOrgmap.setVisibility(View.VISIBLE);
            mIvOrginfomMap.setVisibility(View.GONE);
        }
        String address = DataManage.getInstance().getAddress(vo.getProvince(), vo.getCity(), vo.getCounty(), vo.getCompleteAddress());
        mMapTitel = vo.getName();
        mMapAdress = address;
        mMapPhone = vo.getTelephone();
        mTvOrginfomTitle.setText(vo.getName());
        mTvOrginfomAddress.setText(address);
        mTvOrginfomPhoe.setText(vo.getTelephone());
        String mapurl = UrlUtil.getStaticMapImgUrl(getActivity(),mMapJing,mMapWei);
        if(!StringUtil.isEmpty(mapurl))
        {
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(mapurl, mIvOrginfomMap);
        }
        mLiJamedserviceOrg.setVisibility(View.VISIBLE);
        mLiServiceTime.setVisibility(View.GONE);
        mLiServiceRange.setVisibility(View.GONE);
        mTvIntroduction.setText(vo.getIntroduction());
    }

    /**
     * 司法鉴定详情
     */
    private void requestJaauthService() {
        JaauthService service = new JaauthService(getActivity());
        service.setProgressShowContent("加载中...");
        service.setShowProgress(true);
        service.getOrgDetail(mOrgDetailOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JaauthOrgDetailVO)) {
                    T.showShort(getActivity(), content);
                    return;
                }
                JaauthOrgDetailVO jaauthOrgDetailVO = (JaauthOrgDetailVO) values.get(0);
                if (jaauthOrgDetailVO == null)
                    return;
                setJaauthOrgData(jaauthOrgDetailVO);
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(getActivity(), msg);
            }
        });
    }

    /**
     * 司法鉴定
     *
     * @param vo
     */
    private void setJaauthOrgData(JaauthOrgDetailVO vo) {
        if(vo==null)
            return;
        if (vo.getAxis() != null && !TextUtils.isEmpty(vo.getAxis())) {
            List<String> mJaaidOrgMap = getAxis(vo.getAxis());
            mMapJing = mJaaidOrgMap.get(0);
            mMapWei = mJaaidOrgMap.get(1);
        } else {
            mTvOrgmap.setVisibility(View.VISIBLE);
            mIvOrginfomMap.setVisibility(View.GONE);
        }
        String address = DataManage.getInstance().getAddress(vo.getProvince(), vo.getCity(), vo.getCounty(), vo.getAddress());
        mMapTitel = vo.getName();
        mMapAdress = address;
        mMapPhone = vo.getTelephone();
        mTvOrginfomTitle.setText(vo.getName());
        mTvOrginfomAddress.setText(address);
        mTvOrginfomPhoe.setText(vo.getTelephone());
        String mapurl = UrlUtil.getStaticMapImgUrl(getActivity(),mMapJing,mMapWei);
        if(!StringUtil.isEmpty(mapurl))
        {
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(mapurl, mIvOrginfomMap);
        }
        mLiJamedserviceOrg.setVisibility(View.VISIBLE);
        mTvServiceRange.setText(getServiceAddress(vo.getServiceScope()));
        mTvServiceTime.setText(vo.getServiceTime());
        mTvIntroduction.setText(vo.getIntroduction());
    }

    /**
     * 法援机构详情
     */
    private void requestJaaidService() {
        JaaidService service = new JaaidService(getActivity());
        service.setProgressShowContent("加载中...");
        service.setShowProgress(true);
        service.getOrgDetail(mOrgDetailOid, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty() || !(values.get(0) instanceof JaaidOrgDetailVO)) {
                    T.showShort(getActivity(), content);
                    return;
                }
                JaaidOrgDetailVO mJaaidOrgData = (JaaidOrgDetailVO) values.get(0);
                if (mJaaidOrgData == null)
                    return;
                setJaaidOrgData(mJaaidOrgData);
            }

            @Override
            public void onError(String msg, String content) {
                T.showLong(getActivity(), msg);
            }
        });
    }


    /**
     * 法援机构
     *
     * @param vo
     */
    private void setJaaidOrgData(JaaidOrgDetailVO vo) {
        if(vo==null)
            return;
        if (vo.getAxis()!=null&&!TextUtils.isEmpty(vo.getAxis())) {
            List<String> mJaaidOrgMap = getAxis(vo.getAxis());
            mMapJing = mJaaidOrgMap.get(0);
            mMapWei = mJaaidOrgMap.get(1);
        } else {
            mTvOrgmap.setVisibility(View.VISIBLE);
            mIvOrginfomMap.setVisibility(View.GONE);
        }
        String address = DataManage.getInstance().getAddress(vo.getProvince(), vo.getCity(), vo.getCounty(), vo.getCompleteAddress());
        mMapTitel = vo.getName();
        mMapAdress = address;
        mMapPhone = vo.getTelephone();
        mTvOrginfomTitle.setText(vo.getName());
        mTvOrginfomAddress.setText(address);
        mTvOrginfomPhoe.setText(vo.getTelephone());
        String mapurl = UrlUtil.getStaticMapImgUrl(getActivity(),mMapJing,mMapWei);
        if(!StringUtil.isEmpty(mapurl))
        {
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(mapurl, mIvOrginfomMap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_orginfom_map:
                if (!TextUtils.isEmpty(mMapJing) && !TextUtils.isEmpty(mMapWei)) {
                    Intent intent = new Intent(getActivity(), WalkingRouteActivity.class);
                    intent.putExtra(WalkingRouteActivity.LATITUDE, Double.parseDouble(mMapWei));
                    intent.putExtra(WalkingRouteActivity.LONGITUDE, Double.parseDouble(mMapJing));
                    intent.putExtra(WalkingRouteActivity.CSTR_EXTRA_TITLE_STR, mMapTitel);
                    intent.putExtra(WalkingRouteActivity.ADDRESS, mMapAdress);
                    intent.putExtra(WalkingRouteActivity.SERCESCALL, mMapPhone);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 截取经纬度
     *
     * @param map
     * @return
     */
    private List<String> getAxis(String map) {
        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(map, ",");
        while (tokenizer.hasMoreTokens()) {
            String s = tokenizer.nextToken();
            list.add(s);
        }
        return list;
    }

    /**
     * 获取服务范围
     *
     * @param address
     * @return
     */
    private String getServiceAddress(String address) {
        if (TextUtils.isEmpty(address))
            return "";

        List<String> list = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(address, ",");
        while (tokenizer.hasMoreTokens()) {
            String s = tokenizer.nextToken();
            BaseCommonDataVO vo = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getDataDictionarys(), s);
            list.add(vo.getName());
        }
        if (list == null || list.isEmpty()) {
            BaseCommonDataVO vo = BaseCommonDataVO.findDataVOWithOid(ApplicationSet.getInstance().getDataDictionarys(), address);
            if (vo == null)
                return "";
            return vo.getName();
        }
        return listToString(list);
    }

    private String listToString(List<String> lists) {
        if (lists == null)
            return "";
        StringBuilder sb = new StringBuilder();
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            if ((size - 1) == i) {
                sb.append(lists.get(i));
            } else {
                sb.append(lists.get(i) + ",");
            }
        }
        return sb.toString();
    }
}
