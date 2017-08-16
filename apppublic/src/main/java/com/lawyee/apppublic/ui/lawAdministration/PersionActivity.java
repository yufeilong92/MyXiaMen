package com.lawyee.apppublic.ui.lawAdministration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.lawAdministration.jamed.JamedServiceListActivity;
import com.lawyee.apppublic.ui.personalcenter.LoginActivity;

public class PersionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvBiao1;
    private RelativeLayout mRlMediation;
    private TextView mTvLawloginOut;

    public static final String CONTENT_PARAMETER_TYPE = "type";
    public static final String CONTENT_PARAMETER_JAMED = "jamed";
    public static final String CONTENT_PARAMETER_MEDIA = "media";
    private String mType;
    private TextView mTvType;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jamed_persion);
        initView();
        initData();
    }

    private void initData() {
        if (mType.equals(CONTENT_PARAMETER_JAMED)){
            mTvType.setText("人民调解");
        }else if (mType.equals(CONTENT_PARAMETER_MEDIA)){
            mTvType.setText("媒体调解");
        }
    }


    private void initView() {
        Intent intent = getIntent();
        mType = intent.getStringExtra(CONTENT_PARAMETER_TYPE);
        mIvBiao1 = (ImageView) findViewById(R.id.iv_biao1);
        mTvType = (TextView) findViewById(R.id.tv_type);
        mRlMediation = (RelativeLayout) findViewById(R.id.rl_mediation);
        mTvLawloginOut = (TextView) findViewById(R.id.tv_Lawlogin_out);
        mTvLawloginOut.setOnClickListener(this);
        mRlMediation.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Lawlogin_out:
                new MaterialDialog.Builder(this)
                        .content("您确定要退出当前帐号吗?")
                        .positiveText(R.string.dl_btn_confirm)
                        .negativeText(R.string.dl_btn_cancel)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ApplicationSet.getInstance().setUserVO(null, true);
                                startActivity(new Intent(PersionActivity.this, LoginActivity.class));
                                PersionActivity.this.finish();
                            }
                        })
                        .show();
                break;
            case R.id.rl_mediation:
                handlerData();
                break;
            default:
                break;
        }
    }
    private void handlerData() {
        if (mType.equals(CONTENT_PARAMETER_JAMED)) {
            Intent jamedIntent = new Intent(PersionActivity.this, JamedServiceListActivity.class);
            jamedIntent.putExtra(JamedServiceListActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.jamed_org));
            jamedIntent.putExtra(JamedServiceListActivity.CONTENT_PARAMETER_TYPE,JamedServiceListActivity.CONTENT_PARAMETER_JAMED);
            startActivity(jamedIntent);
        } else if (mType.equals(CONTENT_PARAMETER_MEDIA)) {
            Intent mediaIntent = new Intent(PersionActivity.this, JamedServiceListActivity.class);
            mediaIntent.putExtra(JamedServiceListActivity.CONTENT_PARAMETER_TYPE,JamedServiceListActivity.CONTENT_PARAMETER_MEDIA);
            mediaIntent.putExtra(JamedServiceListActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.media));
            startActivity(mediaIntent);
        }
    }
}
