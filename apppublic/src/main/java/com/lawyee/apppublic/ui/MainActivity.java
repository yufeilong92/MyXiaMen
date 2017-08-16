package com.lawyee.apppublic.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.services.JaaidApplyService;
import com.lawyee.apppublic.vo.JaaidApplyDetailVO;

import net.lawyee.mobilelib.Constants;
import net.lawyee.mobilelib.utils.ActivityUtil;
import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.T;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  主Activity
 * @Package com.lawyee.apppublic.ui
 * @Description:    主activity
 * @author:wuzhu
 * @date:   2017-04-28
 * @version V1.0.xxxxxxxx
 * @verdescript  1.0 20170428  wuzhu 初建
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initView();

        checkStorage();
    }

    public void initView() {
    }


    /**
     * 检查是否有访问存储卡的权限
     */
    public void checkStorage() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,};
        performCodeWithPermission(getString(R.string.rationale_storage), RC_STORAGE_PERM, perms, new PermissionCallback() {
            @Override
            public void hasPermission(List<String> allPerms) {
                //创建缓存文件夹
                FileUtil.isExistFile(Constants.getDataStoreDir(getApplicationContext()));
            }

            @Override
            public void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied) {
                if (hasPermanentlyDenied) {
                    alertAppSetPermission(getString(R.string.rationale_ask_again), RC_SETTINGS_SCREEN);
                }
            }
        });
    }

    public void onSubClick(View v)
    {
        Intent intent=new Intent();
        intent.setClass(MainActivity.this, SubActivity.class);
        startActivity(intent);
    }

    public void onSubDLClick(View v)
    {
        if(ActivityUtil.isServiceRunning(this,JaaidApplyService.class.getName()))
        {
            T.showLong(this,"服务运行中");
            return;
        }
        /*Intent intent=new Intent();
        intent.setClass(MainActivity.this, SubDrawerlayoutActivity.class);
        startActivity(intent);*/
        Intent intent = new Intent(this,JaaidApplyService.class);
        JaaidApplyDetailVO detailVO =getJaaidApplyDatailVO();
        intent.putExtra(JaaidApplyService.CSTR_EXTRA_JAAIDAPPLYDETAIL_VO,detailVO);
        startService(intent);
    }
    private JaaidApplyDetailVO getJaaidApplyDatailVO()
    {
        return (JaaidApplyDetailVO) JaaidApplyDetailVO.loadVO(JaaidApplyDetailVO.dataFileName(getApplicationContext(),"apply"));
    }

}
