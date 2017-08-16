package com.lawyee.apppublic.ui.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawyee.apppublic.R;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  侧拉示例
 * @Package com.lawyee.apppublic.ui.frag
 * @Description:    注释
 * @author:wuzhu
 * @date:   2017/5/3
 * @version
 * @verdescript   2017/5/3  wuzhu 初建
 * @Copyright: 2017/5/3 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class FilterFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;
    private RelativeLayout rl_department;
    private ImageView iv_back;
    private TextView department_selected;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patrol_filter, container,false);
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        rl_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNext();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(mDrawerContent);
            }
        });
    }

    private void initView(View view) {
        String departmentName = getArguments().getString("departmentName");
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.subdrawerlayout_layout);
        mDrawerContent = (FrameLayout) getActivity().findViewById(R.id.subdrawerlayout_drawercontent);
        rl_department = (RelativeLayout) view.findViewById(R.id.rl_department);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        department_selected = (TextView) view.findViewById(R.id.department_selected);
        if(!TextUtils.isEmpty(departmentName)){
            department_selected.setText(departmentName);
            department_selected.setTextColor(getResources().getColor(R.color.blue_text));
        }
    }


    private void showNext() {
        Fragment fragment = new FilterFragmentTwo();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        fragmentTransaction.replace(R.id.subdrawerlayout_drawercontent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
