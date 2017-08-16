package com.lawyee.apppublic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Title: 二级activity
 * @Package com.lawyee.apppublic.ui
 * @Description: 二级activity示例
 * @author:wuzhu
 * @date: 2017-04-28
 * @verdescript 1.0 20170428  wuzhu 初建
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class SubActivity extends BaseActivity {

    private TextView mMcEmptyTv;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sub);
        mMcEmptyTv = (TextView) findViewById(R.id.mc_empty_tv);
        mMcEmptyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    public void onToolbarClick(View view) {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title("使用本风格的对话框")
                .content("使用此对话框，简单方便")
                .positiveText("YES")
                .negativeText("No")
                .show();
    }


}
