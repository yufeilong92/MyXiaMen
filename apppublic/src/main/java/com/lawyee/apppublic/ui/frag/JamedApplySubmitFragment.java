package com.lawyee.apppublic.ui.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.JamedApplyDetailVO;
import com.lawyee.apppublic.vo.JamedApplyFourIsNextEven;
import com.lawyee.apppublic.vo.JamedApplyFourSubmitEven;

import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  JamedApplySubmitFragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description:    人民调解申请提交页
 * @author: YFL
 * @date:   2017/6/12 15:22
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/6/12 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedApplySubmitFragment extends Fragment {

    private EditText mEtJamedApplyDisputecase;
    private EditText mJamedApplyParty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(JamedApplyFourIsNextEven even) {
        JamedApplyDetailVO vo = even.getJamedApplyDataVo();
        if (even.isNext()) {
            submit(vo);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jamed_submit, container, false);
        initView(view);
        //initCacheData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        mEtJamedApplyDisputecase = (EditText) view.findViewById(R.id.et_jamed_apply_disputecase);
        mJamedApplyParty = (EditText) view.findViewById(R.id.jamed_apply_party);
    }
    /**
     * 从缓存中读取上次填写的数据
     */
    private void initCacheData() {
        //TODO 下次可以删除
        JamedApplyDetailVO cachedata = (JamedApplyDetailVO) JamedApplyDetailVO.loadVO(JamedApplyDetailVO.cacheDataFileName(getActivity()));
        if (cachedata == null)
            return;
        if (!StringUtil.isEmpty(cachedata.getIntroduction())) {
            mEtJamedApplyDisputecase.setText(cachedata.getIntroduction());
        }
        if (!StringUtil.isEmpty(cachedata.getMatter())) {
            mJamedApplyParty.setText(cachedata.getMatter());
        }
    }

    private void submit(JamedApplyDetailVO vo) {
        // validate
        String disputecase = mEtJamedApplyDisputecase.getText().toString().trim();
        if (TextUtils.isEmpty(disputecase)) {
            T.showShort(getContext(),getString(R.string.jamed_disputeisempty));
            return;
        }
         vo.setIntroduction(disputecase);
        String party = mJamedApplyParty.getText().toString().trim();
        if (TextUtils.isEmpty(party)) {
            T.showShort(getContext(),getString(R.string.jamed_partyisempty));
            return;
        }
         vo.setMatter(party);
        EventBus.getDefault().post(new JamedApplyFourSubmitEven(vo,true));

    }
}
