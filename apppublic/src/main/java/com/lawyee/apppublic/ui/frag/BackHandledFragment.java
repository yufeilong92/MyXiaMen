package com.lawyee.apppublic.ui.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.ui.frag
 * @Description:  处理fragment 返回按钮监听
 * @author: YFL
 * @date: 2017/5/24 10:27
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public abstract class BackHandledFragment extends Fragment {
    protected  BackHandlerInterface backHandlerInterface;
    public abstract boolean onBackPressed();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity()instanceof  BackHandlerInterface)){
            throw new ClassCastException("Hosting Activity must implement BackHandlerface");
        }else {
            this.backHandlerInterface= (BackHandlerInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }
}
