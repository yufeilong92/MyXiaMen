package com.lawyee.apppublic.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.personalcenter.LoginActivity;
import com.lawyee.apppublic.ui.personalcenter.PerInfoActivity;

import static com.lawyee.apppublic.ui.personalcenter.LoginActivity.OTHER;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V1.0.xxxxxxxx
 * @Package com.lawyee.apppublic.util
 * @Description:  提示对话框工具类
 * @author: lzh
 * @date: 2017/7/24 16:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: ${year} www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

public class ToLoginDialogUtil {
    public static void alertToLogin(final Context context) {
        new MaterialDialog.Builder(context)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.dl_titile_pleaseLogin)
                .positiveText(R.string.dl_btn_ok)
                .negativeText(R.string.dl_btn_cancel)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra(OTHER, true);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    //切换登陆提示对话框
    public static void alertTiptoLogin(Context context){
        new MaterialDialog.Builder(context)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.dl_titile_please_change_Login)
                .positiveText(R.string.dl_btn_ok)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    public static void alertToPer(final Context context) {
        new MaterialDialog.Builder(context)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.dl_titile_pleasemodifyinfo)
                .positiveText(R.string.dl_btn_ok)
                .negativeText(R.string.dl_btn_cancel)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(context, PerInfoActivity.class);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static boolean isFull(String id,String realName) {
        if(id!=null&&!id.equals("")&&realName!=null&&!realName.equals("")){
            return true;
        }else {
            return false;
        }
    }

}