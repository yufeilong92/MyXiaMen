package com.lawyee.apppublic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.adapter
 * @Description: Viewpage适配器
 * @author: YFL
 * @date: 2017/5/15 14:11
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class InformationViewPageAdapter extends FragmentPagerAdapter {

    List<String> mTitel;
    List<Fragment> mDate;

    public InformationViewPageAdapter(FragmentManager fm, List<String> data, List<Fragment> date) {
        super(fm);
        this.mTitel=data;
        this.mDate=date;
    }

    @Override
    public Fragment getItem(int position) {
        return mDate.get(position) ;
    }

    @Override
    public int getCount() {
        return mDate.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitel.get(position);
    }

}
