package com.lawyee.apppublic.ui.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.services.JaaidApplyService;
import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.infom.InformationActivity;
import com.lawyee.apppublic.ui.org.apply.JaaidApplyActivity;
import com.lawyee.apppublic.util.ToLoginDialogUtil;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.utils.ActivityUtil;
import net.lawyee.mobilelib.utils.ScreenUtils;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertTiptoLogin;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.alertToPer;
import static com.lawyee.apppublic.util.ToLoginDialogUtil.isFull;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LegalAidActivity.java
 * @Package com.lawyee.apppublic.ui
 * @Description: 法律服务
 * @author: YFL
 * @date: 2017/5/24 17:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/5/24 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class JaaidActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLiLegalaidOrg;
    private LinearLayout mLiLegalaidInformation;
    private LinearLayout mLiLegalaidApply;
    private Context mContext;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_legalaid);
        mContext=this;
        int screenHeight = ScreenUtils.getScreenHeight(this);
        initView();
        handlerView(screenHeight);
    }

    /**
     * 动态添加布局
     * @param screenHeight
     */
    private void handlerView(int screenHeight) {
        LinearLayout.LayoutParams OrgParams = (LinearLayout.LayoutParams) mLiLegalaidOrg.getLayoutParams();
        OrgParams.height= (int) (screenHeight*0.28);
        mLiLegalaidOrg.setLayoutParams(OrgParams);
        LinearLayout.LayoutParams InformationParams = (LinearLayout.LayoutParams) mLiLegalaidInformation.getLayoutParams();
        InformationParams.height= (int) (screenHeight*0.28);
        mLiLegalaidInformation.setLayoutParams(InformationParams);
        LinearLayout.LayoutParams Applyparams = (LinearLayout.LayoutParams) mLiLegalaidApply.getLayoutParams();
        Applyparams.height= (int) (screenHeight*0.22);
        mLiLegalaidApply.setLayoutParams(Applyparams);
    }

    private void initView() {
        mLiLegalaidOrg = (LinearLayout) findViewById(R.id.li_legalaid_org);
        mLiLegalaidInformation = (LinearLayout) findViewById(R.id.li_legalaid_information);
        mLiLegalaidApply = (LinearLayout) findViewById(R.id.li_legalaid_apply);
        mLiLegalaidApply.setOnClickListener(this);
        mLiLegalaidInformation.setOnClickListener(this);
        mLiLegalaidOrg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.li_legalaid_apply :
            if(ActivityUtil.isServiceRunning(this,JaaidApplyService.class.getName()))
            {
                T.showLong(this,"您有法援预申请信息还在提交中，请在提交完成后再发起新的申请");
                return;
            }
            UserVO userVO = ApplicationSet.getInstance().getUserVO();
            if(userVO==null|| StringUtil.isEmpty(userVO.getLoginId())) {
                ToLoginDialogUtil.alertToLogin(mContext);
            }else {
                if (!userVO.isPublicUser()) {
                    alertTiptoLogin(mContext);
                } else {
                    if(isFull(userVO.getIdCard(),userVO.getRealName())) {
                        startActivity(new Intent(JaaidActivity.this, JaaidApplyActivity.class));
                    }else{
                        alertToPer(mContext);
                    }
                }
            }
              break;
        case  R.id.li_legalaid_information:
            Intent intent = new Intent(JaaidActivity.this, InformationActivity.class);
                intent.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPENAME_STRARRAY,getResources().getStringArray(R.array.FLYZInfoName));
                intent.putExtra(InformationActivity.CSTR_EXTRA_INFOTYPEID_STRARRAY,getResources().getStringArray(R.array.FLYZInfoID));
                intent.putExtra(InformationActivity.CSTR_EXTRA_TITLE_STR,getString(R.string.legal_aidInfom));
                startActivity(intent);
           break;
        case  R.id.li_legalaid_org:
            Intent intent1 = new Intent(JaaidActivity.this, JaaidOrgActivity.class);
            intent1.putExtra(JaaidOrgActivity.CSTR_EXTRA_TITLE_STR,getString(R.string.legalaid_org));
            startActivity(intent1);
           break;
          default:
              break;
        }

    }


}
