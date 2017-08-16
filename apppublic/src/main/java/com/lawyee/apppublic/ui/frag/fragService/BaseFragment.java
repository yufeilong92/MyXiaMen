package com.lawyee.apppublic.ui.frag.fragService;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.ui.frag.fragService
 * @author: YFL
 * @date: 2017/7/26 16:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class BaseFragment extends Fragment {

    protected String getTextStr(TextView tv) {
        String trim = tv.getText().toString().trim();
        if (!TextUtils.isEmpty(trim))
            return trim;
        return "";
    }

    protected MaterialDialog.Builder getShowDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
        builder
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title("提示")
                .titleGravity(GravityEnum.CENTER)
                .titleColor(Color.RED)
                .content("内容提交后不能修改")
                .contentGravity(GravityEnum.CENTER)
                .positiveText(R.string.dl_btn_ok)
                .negativeText(R.string.dl_btn_cancel)
                .cancelable(false)
                .show();
        return builder;
    }






}
