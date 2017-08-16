package com.lawyee.apppublic.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.CommonService;
import com.lawyee.apppublic.dal.UserService;
import com.lawyee.apppublic.services.JaaidApplyService;
import com.lawyee.apppublic.vo.DataDictionaryVO;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;
import com.lawyee.apppublic.vo.UserVO;

import net.lawyee.mobilelib.Constants;
import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.StringUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.ui
 * @Description: 欢迎页面
 * @author: czq
 * @date: 2017/5/15 10:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 *                     2017/5/26 10:04 wuzhu 添加数据字典及区域初始化
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class WelcomeActivity extends  BaseActivity{
    private Context mContext;
    private Thread mThread;
    private boolean mLoadDataDictionary;
    private boolean mLoadAreas;

    private JaaidApplyDetailVO mJaaidApplyDetailVO;
    private String mJaaidApplyMessage;
    private int mJaaidApplyResult;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(!mLoadAreas||!mLoadDataDictionary)
                return;
            if(mJaaidApplyResult== JaaidApplyService.JAAIDAPPLYRESULT.error.getValue()&&
                    mJaaidApplyDetailVO!=null)
            {
                new MaterialDialog.Builder(WelcomeActivity.this)
                        .limitIconToDefaultSize()
                        .title("法援预申请信息提交失败")
                        .content(mJaaidApplyMessage+",是否重新进行提交?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .cancelable(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                T.showLong(WelcomeActivity.this,"预申请信息已经重新开始提交，请注意状态条信息提示");
                                Intent intent = new Intent(WelcomeActivity.this,JaaidApplyService.class);
                                JaaidApplyDetailVO detailVO =mJaaidApplyDetailVO;
                                intent.putExtra(JaaidApplyService.CSTR_EXTRA_JAAIDAPPLYDETAIL_VO,detailVO);
                                startService(intent);
                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
                            }
                        })
                        .show();
            }else {
                startHome();
            }
        }
    };

    private void startHome()
    {
        startActivity(new Intent(mContext, HomeActivity.class));
        //startActivity(new Intent(mContext,MainActivity.class));
        finish();
    }
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        //接收法援预申请服务反馈过来的一些信息
        Intent intent = getIntent();
        Object o = intent.getSerializableExtra(JaaidApplyService.CSTR_EXTRA_JAAIDAPPLYDETAIL_VO);
        if(o instanceof JaaidApplyDetailVO)
        {
            mJaaidApplyDetailVO=(JaaidApplyDetailVO)o;
        }
        mJaaidApplyMessage = intent.getStringExtra(JaaidApplyService.CSTR_EXTRA_JAAIDAPPLYMESSAGE_STR);
        mJaaidApplyResult = intent.getIntExtra(JaaidApplyService.CSTR_EXTRA_JAAIDAPPLYRESULT_INT,0);
        setContentView(R.layout.activity_welcome);
        mContext=this;
        mLoadAreas=false;
        mLoadDataDictionary = false;
        checkStorage();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //获取数据字典
            CommonService service = new CommonService(WelcomeActivity.this);
            service.getDataDictionary(new BaseJsonService.IResultInfoListener() {
                @Override
                public void onComplete(ArrayList<Object> values, String content) {
                    if(values!=null&&!values.isEmpty()
                            &&values.get(0)!=null)
                    {
                        ApplicationSet.getInstance().setDataDictionarys((List<DataDictionaryVO>) values.get(0));
                        //缓存
                        DataDictionaryVO.saveVOList((ArrayList<?>) values.get(0),DataDictionaryVO.dataListFileName(WelcomeActivity.this));
                    }
                    loadDataDictionaryComplete();
                }

                @Override
                public void onError(String msg, String content) {
                    loadDataDictionaryComplete();
                }
            });
            //用户自动登录
            userAutoLogin();
            ApplicationSet.initAreas();
            mLoadAreas = true;
            //testData();
            mHandler.sendEmptyMessage(0);
        }
    };

    /**
     * 用户自动登录
     */
    private void userAutoLogin()
    {
        final UserVO userVO = (UserVO) UserVO.loadVO(UserVO.dataFileName(WelcomeActivity.this));
        if(userVO==null||StringUtil.isEmpty(userVO.getLoginId())||!userVO.isRememblePwd())
            return;
        UserService service = new UserService(WelcomeActivity.this);
        service.userLogin(userVO.getLoginId(), userVO.getPassword(),userVO.getRole(), new BaseJsonService.IResultInfoListener() {
            @Override
            public void onComplete(ArrayList<Object> values, String content) {
                if(values==null||values.isEmpty()||!(values.get(0) instanceof UserVO))
                {
                    T.showLong(getApplicationContext(),getString(R.string.login_error_noeffectdata));
                    return;
                }
                UserVO newUserVO = (UserVO)values.get(0);
                newUserVO.setPassword(userVO.getPassword());
                newUserVO.setRememblePwd(true);
                ApplicationSet.getInstance().setUserVO(newUserVO,true);
                T.showLong(getApplicationContext(),getString(R.string.auto_login_sucess));
            }

            @Override
            public void onError(String msg, String content) {
                L.v(TAG,"UserLogin onError:"+content);
                //T.showLong(WelcomeActivity.this,msg);

            }
        });
    }

    /**
     * 数据字典读取完成
     */
    private void loadDataDictionaryComplete()
    {
        if(ApplicationSet.getInstance().getDataDictionarys(false)==null||
                ApplicationSet.getInstance().getDataDictionarys(false).isEmpty()) {
            ApplicationSet.initDataDictionary();
        }
        mLoadDataDictionary = true;
        mHandler.sendEmptyMessage(0);
    }

    /**
     * 检查是否有访问存储卡的权限
     */
    public void checkStorage() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};
        performCodeWithPermission(getString(R.string.rationale_storage), RC_STORAGE_PERM, perms, new PermissionCallback() {
            @Override
            public void hasPermission(List<String> allPerms) {
                //创建缓存文件夹
                FileUtil.isExistFolder(Constants.getDataStoreDir(getApplicationContext()));
                String caseDir = Constants.getDataStoreDir(WelcomeActivity.this)
                        + net.lawyee.mobilelib.Constants.CSTR_IMAGECACHEDIR;
                FileUtil.isExistFolder(caseDir);
                //已经初始化过就不重新初始化
                if(ApplicationSet.getInstance().getDataDictionarys(false)==null||ApplicationSet.getInstance().getAreas(false)==null) {
                    mThread = new Thread(runnable);
                    mThread.start();
                }else
                {
                    mLoadAreas = true;
                    mLoadDataDictionary = true;
                    mHandler.sendEmptyMessage(0);
                }
            }

            @Override
            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
                if (hasPermanentlyDenied) {
                    alertAppSetPermission(getString(R.string.rationale_ask_again), RC_SETTINGS_SCREEN);
                }
            }
        });
    }
}
