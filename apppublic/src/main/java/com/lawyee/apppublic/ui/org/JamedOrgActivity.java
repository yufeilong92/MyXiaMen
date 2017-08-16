package com.lawyee.apppublic.ui.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.infom.InformationActivity;
import com.lawyee.apppublic.ui.org.apply.JamedOrgApplyActivity;
import com.lawyee.apppublic.util.ToLoginDialogUtil;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.ScreenUtils;
import net.lawyee.mobilelib.utils.StringUtil;

import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertTiptoLogin;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertToPer;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.isFull;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: JamedOrgActivity.java
 * @Package com.lawyee.apppublic.ui.org
 * @Description: 人民调解页
 * @author: YFL
 * @date: 2017/5/24 15:10
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/24 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class JamedOrgActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLiJamedOrg;
    private LinearLayout mLiJamedGuide;
    private LinearLayout mLiJamedApply;
    private Context mContext;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jamed_org);
        mContext=this;
        int screenHeight = ScreenUtils.getScreenHeight(this);
        initView();
        LinearLayout.LayoutParams OrgParams = (LinearLayout.LayoutParams) mLiJamedOrg.getLayoutParams();
        OrgParams.height= (int) (screenHeight*0.28);
        mLiJamedOrg.setLayoutParams(OrgParams);
        LinearLayout.LayoutParams InformationParams = (LinearLayout.LayoutParams) mLiJamedGuide.getLayoutParams();
        InformationParams.height= (int) (screenHeight*0.28);
        mLiJamedGuide.setLayoutParams(InformationParams);
        LinearLayout.LayoutParams Applyparams = (LinearLayout.LayoutParams) mLiJamedApply.getLayoutParams();
        Applyparams.height= (int) (screenHeight*0.22);
        mLiJamedApply.setLayoutParams(Applyparams);
    }
    private void initView() {
        mLiJamedOrg = (LinearLayout) findViewById(R.id.li_jamed_org);
        mLiJamedGuide = (LinearLayout) findViewById(R.id.li_jamed_guide);
        mLiJamedApply = (LinearLayout) findViewById(R.id.li_jamed_apply);
        mLiJamedApply.setOnClickListener(this);
        mLiJamedGuide.setOnClickListener(this);
        mLiJamedOrg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_jamed_apply:
                UserVO userVO = ApplicationSet.getInstance().getUserVO();
                if(userVO==null|| StringUtil.isEmpty(userVO.getLoginId())) {
                    ToLoginDialogUtil.alertToLogin(mContext);
                }else {
                    if(!userVO.isPublicUser()){
                        alertTiptoLogin(mContext);
                    }else{
                        if(isFull(userVO.getIdCard(),userVO.getRealName())) {
                            Intent intent2 = new Intent(JamedOrgActivity.this, JamedOrgApplyActivity.class);
                            intent2.putExtra(CSTR_EXTRA_TITLE_STR, getString(R.string.jamed_apply));
                            startActivity(intent2);
                        }else {
                            alertToPer(mContext);
                        }
                    }

                }

                break;
            case R.id.li_jamed_guide:
                Intent intent = new Intent(JamedOrgActivity.this, InformationActivity.class);
                intent.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPENAME_STRARRAY,getResources().getStringArray(R.array.RMDJInfoName));
                intent.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPEID_STRARRAY,getResources().getStringArray(R.array.RMDJInfoID));
                intent.putExtra(InformationActivity.CSTR_EXTRA_TITLE_STR,getString(R.string.jamed_guide));
                startActivity(intent);
                break;
            case R.id.li_jamed_org:
                Intent intent1 = new Intent(JamedOrgActivity.this, JamedOrgListActivity.class);
                intent1.putExtra(JamedOrgListActivity.CSTR_EXTRA_TITLE_STR,getString(R.string.perple_org));
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

}
