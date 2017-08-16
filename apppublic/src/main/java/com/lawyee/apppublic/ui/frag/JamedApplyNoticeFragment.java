package com.lawyee.apppublic.ui.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.vo.JaaidApplyEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: Jamed_Notice_Fragment.java
 * @Package com.lawyee.apppublic.ui.frag
 * @Description: 人民调解申请须知页
 * @author: YFL
 * @date: 2017/5/26 21:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/26 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedApplyNoticeFragment extends Fragment {

    private TextView mTvJaemdApplyThree;
    private CheckBox mChbApplySelect;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_jamed_notice, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         initData();
    }


    private void initData() {
   mChbApplySelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
       @Override
       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
           EventBus.getDefault().post(new JaaidApplyEvent(1,isChecked));
       }
   });
    }

    private void initView(View view) {
        mChbApplySelect = (CheckBox) view.findViewById(R.id.chb_apply_select);
    }

}
