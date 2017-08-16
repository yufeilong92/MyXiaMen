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

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.AidStatuTickAdapter;
import com.lawyee.apppublic.adapter.AidStatusAdpater;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;

import java.util.ArrayList;

import static com.lawyee.apppublic.ui.personalcenter.MyLawAidDetailActivity.AID;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: ${todo}(预申请状态页)
 * @author: czq
 * @date: 2017/5/31 15:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class AidStatusFragment extends Fragment {
    private RecyclerView mRvStatu;
    private ArrayList<String> mDatas = new ArrayList<>();
    private Context mContext;
    private AidStatusAdpater mStatusAdpater;
    private GridLayoutManager mLayoutManager;
    private RecyclerView mRvTickling;
    private JaaidApplyDetailVO mjaaidJaaidApplyDetailVO;
    private AidStatuTickAdapter mAidStatuTickAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle bundle = getArguments();
        mjaaidJaaidApplyDetailVO = (JaaidApplyDetailVO) bundle.getSerializable(AID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aid_status, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvStatu = (RecyclerView) view.findViewById(R.id.rv_statu);
        mRvTickling = (RecyclerView) view.findViewById(R.id.rv_tickling);
        mDatas.add(mContext.getString(R.string.submit_aid_internet));
        mDatas.add(mContext.getString(R.string.being_audited));
        if (mjaaidJaaidApplyDetailVO.getAuditStatus() == -1) {
            mDatas.add(mContext.getString(R.string.audited_fail));
            mDatas.add(mContext.getString(R.string.been_finished_2));
        } else {
            mDatas.add(mContext.getString(R.string.audited_pass));
            mDatas.add(mContext.getString(R.string.in_process));
            mDatas.add(mContext.getString(R.string.been_finished_3));
        }

        mStatusAdpater = new AidStatusAdpater(mDatas, mContext, mjaaidJaaidApplyDetailVO);
        mRvStatu.setAdapter(mStatusAdpater);
        mLayoutManager = new GridLayoutManager(mContext, 1);
        mRvStatu.setLayoutManager(mLayoutManager);
        mAidStatuTickAdapter = new AidStatuTickAdapter(mjaaidJaaidApplyDetailVO, mContext);
        mRvTickling.setAdapter(mAidStatuTickAdapter);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(mContext, 1);
        mRvTickling.setLayoutManager(mLayoutManager2);
        mRvTickling.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
