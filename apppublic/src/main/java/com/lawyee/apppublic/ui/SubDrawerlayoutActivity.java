package com.lawyee.apppublic.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.frag.FilterFragment;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  二级activity--带左侧侧拉
 * @Package com.lawyee.apppublic.ui
 * @Description:    二级activity--带左侧侧拉
 * @author:wuzhu
 * @date:   2017-05-03
 * @version V1.0.xxxxxxxx
 * @verdescript  1.0 20170503  wuzhu 初建
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SubDrawerlayoutActivity extends BaseActivity {


    private ImageView mFilter;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mDrawerContent;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_subdrawerlayout);

        mFilter = (ImageView)findViewById(R.id.subdrawerlayout_filter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.subdrawerlayout_layout);
        mDrawerContent = (FrameLayout) findViewById(R.id.subdrawerlayout_drawercontent);

        Fragment fragment = new FilterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("departmentName","");
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.subdrawerlayout_drawercontent, fragment)
                .commit();


        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mDrawerContent);
            }
        });
    }

}
