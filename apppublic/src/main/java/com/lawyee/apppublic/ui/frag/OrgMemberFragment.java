package com.lawyee.apppublic.ui.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.NarratorAdapter;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.JaauthService;
import com.lawyee.apppublic.dal.JamedService;
import com.lawyee.apppublic.dal.JanotaService;
import com.lawyee.apppublic.vo.JamedStaffVO;
import com.lawyee.apppublic.widget.RecycleViewDivider;

import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JamedOrgFragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 机构员信息页
 * @author: YFL
 * @date: 2017/5/24 15:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/24 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class OrgMemberFragment extends Fragment {
    /**
     * 传入机构oid
     */
    public static final String CSTR_EXTRA_INFOTYPEID_OID = "orgdetailoid";

    /**
     * 传入参数-类型
     */
    public static final String CSTR_EXTRA_TYPE_TYPE = "type";
    /**
     * 传入参数-公证员
     */
    public static final String CSTR_EXTRA_TYPE_JANOTASTAFF = "janotastaff";
    /**
     * 传入参数-调解员
     */
    public static final String CSTR_EXTRA_TYPE_JAMEDSTAFF = "jamedstaff";
    /**
     * 传入参数-鉴定员
     */
    public static final String CSTR_EXTRA_TYPE_JAAUTHSTAFF = "jaauthstaff";
    private RecyclerView mRlvNarrator;
    private List<JamedStaffVO> lists = new ArrayList<>();
    private String mOrgType;
    private String mOrgId;
    private TextView mOrgmenberContentEmptyTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrgId = getArguments().getString(CSTR_EXTRA_INFOTYPEID_OID);
            mOrgType = getArguments().getString(CSTR_EXTRA_TYPE_TYPE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jamed_org, null);
        initView(view);
        handlerRequestService();
        return view;
    }

    /**
     * 处理请求数据
     */
    private void handlerRequestService() {
        if (mOrgType.equals(CSTR_EXTRA_TYPE_JAAUTHSTAFF))//鉴定员
        {
            requestJaauthService();
        } else if (mOrgType.equals(CSTR_EXTRA_TYPE_JAMEDSTAFF)) {//调解员
            requestJamedService();
        } else if (mOrgType.equals(CSTR_EXTRA_TYPE_JANOTASTAFF)) {//公证员
            requestJanotaService();
        }
    }

    /**
     * 公证员
     */
    private void requestJanotaService() {
        JanotaService janotaService = new JanotaService(getActivity());
        janotaService.getStaffList(mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(getActivity(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                if(list==null||list.isEmpty()){
                    mRlvNarrator.setVisibility(View.GONE);
                    mOrgmenberContentEmptyTv.setText(R.string.wujanotastaff);
                    mOrgmenberContentEmptyTv.setVisibility(View.VISIBLE);
                    return;
                }
                setAdapterData(list, NarratorAdapter.JANOTASTAFF);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(getContext(), msg);
            }
        });
    }

    /**
     * 调解员网络请求
     */
    private void requestJamedService() {
        JamedService jamedService = new JamedService(getActivity());
        jamedService.getStaffList(mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(getActivity(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                if(list==null||list.isEmpty()){
                    mRlvNarrator.setVisibility(View.GONE);
                    mOrgmenberContentEmptyTv.setText(R.string.wujamestaff);
                    mOrgmenberContentEmptyTv.setVisibility(View.VISIBLE);
                    return;
                }
                setAdapterData(list, NarratorAdapter.JAMEDSTAFF);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(getContext(), msg);
            }
        });
    }

    /**
     * 鉴定员网络请求
     */
    private void requestJaauthService() {
        JaauthService jaauthService = new JaauthService(getActivity());
        jaauthService.getStaffList(mOrgId, new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if (values == null || values.isEmpty()) {
                    T.showShort(getActivity(), content);
                    return;
                }
                ArrayList list = (ArrayList) values.get(0);
                if(list==null||list.isEmpty()){
                    mRlvNarrator.setVisibility(View.GONE);
                    mOrgmenberContentEmptyTv.setText(R.string.wujaauthstaff);
                    mOrgmenberContentEmptyTv.setVisibility(View.VISIBLE);
                    return;
                }
                setAdapterData(list, NarratorAdapter.JAAUTHSTAFF);
            }

            @Override
            public void onError(String msg, String content) {
                T.showShort(getActivity(), msg);
            }
        });
    }

    private void setAdapterData(ArrayList list, String type) {
        NarratorAdapter narratorAdapter = new NarratorAdapter(list, getActivity(), type);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRlvNarrator.addItemDecoration(new RecycleViewDivider(getActivity(), GridLayoutManager.VERTICAL, R.drawable.bg_rlv_diving));
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRlvNarrator.setLayoutManager(gridLayoutManager);
        mRlvNarrator.setAdapter(narratorAdapter);

    }

    private void initView(View view) {
        mRlvNarrator = (RecyclerView) view.findViewById(R.id.rlv_narrator);
        mOrgmenberContentEmptyTv = (TextView) view.findViewById(R.id.orgmenber_content_empty_tv);
    }
}
