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
import com.lawyee.apppublic.adapter.AidDetailAdpater;
import com.lawyee.apppublic.vo.AttachmentVO;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;

import java.util.ArrayList;
import java.util.List;

import static com.lawyee.apppublic.ui.personalcenter.MyLawAidDetailActivity.AID;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: ${todo}(个人中心法援预申请详情)
 * @author: czq
 * @date: 2017/6/1 16:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class AidDetailFragment extends Fragment {

    private Context mContext;
    private RecyclerView rv_content;
    private JaaidApplyDetailVO mJaaidJaaidApplyDetailVO;
    private AidDetailAdpater mAdpater;
    private List<AttachmentVO> attachments=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Bundle bundle = getArguments();
        mJaaidJaaidApplyDetailVO = (JaaidApplyDetailVO) bundle.getSerializable(AID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aid_detail, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(mContext,1);
        rv_content.setLayoutManager(gridLayoutManager);
        mAdpater=new AidDetailAdpater(mContext,mJaaidJaaidApplyDetailVO);
        rv_content.setAdapter(mAdpater);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
