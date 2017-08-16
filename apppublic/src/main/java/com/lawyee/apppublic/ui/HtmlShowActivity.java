package com.lawyee.apppublic.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.lawyee.apppublic.R;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  标题
 * @Package com.lawyee.apppublic.ui
 * @Description:    显示assets的html文件
 * @author:czq
 * @date:   2017/6/12
 * @version
 * @verdescript   2017/6/12  lzh 初建
 * @Copyright: 2017/6/12 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class HtmlShowActivity extends BaseActivity {
    public final  static String TOHTML="HtmlShowActivity";
    private WebView mWvContent;
    private  String mFileName;
    private final  static String FILEHEAD="file:///android_asset/";
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_html_show);
        initView();

    }

    private void initView() {
        mFileName=getIntent().getStringExtra(TOHTML);
        mWvContent= (WebView) findViewById(R.id.wv_content);
        mWvContent.loadUrl(FILEHEAD+mFileName);
    }

}
