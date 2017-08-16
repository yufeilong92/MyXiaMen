package com.lawyee.apppublic.ui.infom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.ViewImageActivity;
import com.lawyee.apppublic.util.NewsHtmlParser;
import com.lawyee.apppublic.util.WebViewUtil;
import com.lawyee.apppublic.util.net.DownloadWebImgTask;
import com.lawyee.apppublic.vo.InfomationVO;

import net.lawyee.mobilelib.utils.HtmlParser;
import net.lawyee.mobilelib.utils.ImageUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.ViewUtil;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: DetailsActivity.java
 * @Package com.lawyee.apppublic.ui
 * @Description: 资讯详情页
 * @author: YFL
 * @date: 2017/5/15 17:08
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/15 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class InfomDetailsActivity extends BaseActivity {
    /**
     * 传入参数-资讯VO
     */
    public static final String CSTR_EXTRA_INFORMATIONVO_VO = "infovo";

    /**
     * 资讯VO
     */
    public InfomationVO mInfoVO;

    private int mScreenwidth;

    private ProgressBar mpb_content_loading;
    private WebView mwv_content;
    private NewsHtmlParser mParser;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_details);
        mScreenwidth = ViewUtil.getScreenHeight(InfomDetailsActivity.this);
        Intent intent = getIntent();

        mInfoVO = (InfomationVO) intent.getSerializableExtra(CSTR_EXTRA_INFORMATIONVO_VO);
        if(mInfoVO==null){
            if (mInfoVO == null) {
                Log.e(TAG,
                        "initDataContent CSTR_EXTRA_INFORMATIONVO_VO 为空");
                this.finish();
                return;
            }
        }
        initView();
        loadData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView()
    {
        mpb_content_loading = (ProgressBar) findViewById(R.id.newsdetail_content_loading_pb);
        // WebView相关设置
        mwv_content = (WebView) findViewById(R.id.detail_content_wv);
        mwv_content.getSettings().setJavaScriptEnabled(true);
        mwv_content.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // mwv_content.getSettings().setBlockNetworkImage(true);
        mwv_content.addJavascriptInterface(this,
                HtmlParser.Js2JavaInterfaceName);
        mwv_content.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                mpb_content_loading.setVisibility(View.GONE);
                mwv_content.setVisibility(View.VISIBLE);
                if (mParser != null) {
                    List<String> urlStrs = mParser.getImgUrls();
                    if (urlStrs == null || urlStrs.isEmpty())
                        return;
                    DownloadWebImgTask downloadTask = new DownloadWebImgTask(
                            InfomDetailsActivity.this, mwv_content, false);
                    String urlStrArray[] = new String[urlStrs.size() + 1];
                    urlStrs.toArray(urlStrArray);
                    downloadTask.execute(urlStrArray);
                }
            }

            // 解决点击跳转问题
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    final String url) {
                // 增加处理点击重新加载标签
                if (WebViewUtil.isreloadlink(url)) {
                    loadData();
                    return true;
                }

                // 处理站内跳转等
                WebViewUtil.ProcessSiteLink(InfomDetailsActivity.this, url);
                return true;
            }
        });
    }
    /**
     * 显示图片（JavascriptInterface）
     *
     * @param url
     */
    @JavascriptInterface
    public void showImage(String url) {
        // 显示图片(需要判断是否已经有本地图片，如果没有则要进行加载)
        if (StringUtil.isEmpty(url))
            return;
        if (mParser != null && !ImageUtil.hasLocalFile(InfomDetailsActivity.this,url)) {
            // 未加载图时，加载图
            DownloadWebImgTask downloadTask = new DownloadWebImgTask(
                    InfomDetailsActivity.this, mwv_content, true);
            String urlStrArray[] = { url };
            downloadTask.execute(urlStrArray);
            return;
        }
        Intent intent = new Intent(InfomDetailsActivity.this,
                ViewImageActivity.class);
        intent.putExtra(ViewImageActivity.CSTR_EXTRA_IMAGE_STR, url);
        startActivity(intent);
    }

    /**
     * 读取数据
     */
    private void loadData() {
        mpb_content_loading.setVisibility(View.VISIBLE);
        mwv_content.setVisibility(View.GONE);

        mParser = new NewsHtmlParser(mwv_content, this, mInfoVO, mScreenwidth);
        mParser.startHandle();
    }
}
