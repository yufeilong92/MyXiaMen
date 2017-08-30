package com.lawyee.apppublic.ui.org.japub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.util.UrlUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import static android.view.KeyEvent.KEYCODE_BACK;


public class ImageLookActivity extends BaseActivity {

    private PhotoView mImagePv;
    public static final String CONTNETPARAMETER_URL = "URL";//地址
    private String mUrl;
    private PhotoView mViewimagePv;
    private WebView mWebOrgshow;
    private ProgressBar mLoadingPb;
    private RelativeLayout mRelativeWeb;
    public static final String CONTENT_PARRMTER_TYPE = "type";
    public static final String CONTENT_PARRMTER_IMAGE = "Imgae";//图片查看
    public static final String CONTENT_PARRMTER_WEB = "WEB";//web页查看
    private Context mContext;
    private String mType;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_image_look);
        initView();
        bindViewData();
    }

    private void bindViewData() {
        if (mType.equals(CONTENT_PARRMTER_IMAGE)) {
            mRelativeWeb.setVisibility(View.GONE);
            mImagePv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mUrl)) {
                ImageLoader.getInstance().displayImage(UrlUtil.getImageFileUrl(this, mUrl), mImagePv);
            }
        } else if (mType.equals(CONTENT_PARRMTER_WEB)) {
            mRelativeWeb.setVisibility(View.VISIBLE);
            mImagePv.setVisibility(View.GONE);
            WebSettings settings = mWebOrgshow.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            mWebOrgshow.loadUrl(mUrl);
            mWebOrgshow.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mWebOrgshow.setVisibility(View.GONE);
                    mLoadingPb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mWebOrgshow.setVisibility(View.VISIBLE);
                    mLoadingPb.setVisibility(View.GONE);
                }
            });


        }
    }

    private void initView() {
        mContext = this;
        mImagePv = (PhotoView) findViewById(R.id.viewimage_pv);
        mUrl = getIntent().getStringExtra(CONTNETPARAMETER_URL);
        mWebOrgshow = (WebView) findViewById(R.id.web_Orgshow);
        mLoadingPb = (ProgressBar) findViewById(R.id.loading_pb);
        mRelativeWeb = (RelativeLayout) findViewById(R.id.RelativeWeb);
        Intent intent = getIntent();
        mType = intent.getStringExtra(CONTENT_PARRMTER_TYPE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebOrgshow.canGoBack()) {
            mWebOrgshow.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
