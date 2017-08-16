package com.lawyee.apppublic.ui.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.adapter.JameDetailAdpater;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;

import static com.lawyee.apppublic.R.id.rv_content;
import static com.lawyee.apppublic.ui.personalcenter.MyJamedDetailActivity.JAMED;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @author: lzh
 * @date: 2017/6/2 17:54
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class JamedDetailFragment extends Fragment {
    private Context mContext;
    private RecyclerView mRvContent;
    private JameDetailAdpater mAdpater;
    private JamedApplyDetailVO mJamedApplyDetailVO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle bundle = getArguments();
        mJamedApplyDetailVO = (JamedApplyDetailVO) bundle.getSerializable(JAMED);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aid_detail, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mRvContent = (RecyclerView) view.findViewById(rv_content);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,1);
        mRvContent.setLayoutManager(gridLayoutManager);
        initData();
    }

    private void initData() {
        mAdpater=new JameDetailAdpater(mContext,mJamedApplyDetailVO);
        mRvContent.setAdapter(mAdpater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}