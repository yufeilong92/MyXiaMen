package com.lawyee.apppublic.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lawyee.apppublic.R;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.apppublic.util
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/7/20 10:20
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ShowOrHide {

    public static void showOrHide(Object o, ImageView iv) {
        if (o != null && iv != null) {
            if (o instanceof RelativeLayout) {
                RelativeLayout layout;
                layout = (RelativeLayout) o;
                if (layout.getVisibility() == View.GONE) {
                    layout.setVisibility(View.VISIBLE);
                    iv.setImageResource(R.mipmap.icon_lawvote_down);
                } else if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                    iv.setImageResource(R.mipmap.icon_lawvote_packup);
                }

            } else if (o instanceof LinearLayout) {
                LinearLayout layout;
                layout = (LinearLayout) o;
                if (layout.getVisibility() == View.GONE) {
                    layout.setVisibility(View.VISIBLE);
                    iv.setImageResource(R.mipmap.icon_lawvote_down);
                } else if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                    iv.setImageResource(R.mipmap.icon_lawvote_packup);
                }
            } else if (o instanceof RecyclerView) {
                RecyclerView rlv;
                rlv = (RecyclerView) o;
                if (rlv.getVisibility() == View.GONE) {
                    rlv.setVisibility(View.VISIBLE);
                    iv.setImageResource(R.mipmap.icon_lawvote_down);
                } else if (rlv.getVisibility() == View.VISIBLE) {
                    rlv.setVisibility(View.GONE);
                    iv.setImageResource(R.mipmap.icon_lawvote_packup);
                }
            } else if (o instanceof ImageView) {
                ImageView image;
                image = (ImageView) o;
                if (image.getVisibility() == View.GONE) {
                    image.setVisibility(View.VISIBLE);
                    iv.setImageResource(R.mipmap.icon_lawvote_down);
                } else if (image.getVisibility() == View.VISIBLE) {
                    image.setVisibility(View.GONE);
                    iv.setImageResource(R.mipmap.icon_lawvote_packup);
                }

            }
        }
    }

    /**
     * @param rlv  法治人物
     * @param rlv1 法治事件
     * @param btn  提交按钮
     */
    public static void showOrHideBtn(RecyclerView rlv, RecyclerView rlv1, Button btn) {
        if (rlv == null || rlv1 == null || btn == null)
            return;
        if (rlv.getVisibility() == View.VISIBLE || rlv1.getVisibility() == View.VISIBLE) {
            btn.setVisibility(View.VISIBLE);
        }
        if (rlv.getVisibility()==View.GONE&&rlv1.getVisibility()==View.GONE){
            btn.setVisibility(View.GONE);
        }

    }
    public static void showDataDialog(Context context,final TextView tv) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_datepicker, null);
        new MaterialDialog.Builder(context)
                .customView(view, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        DatePicker mDate = (DatePicker) view.findViewById(R.id.datePicker);
                        int year = mDate.getYear();
                        int month = mDate.getMonth() + 1;
                        int dayOfMonth = mDate.getDayOfMonth();
                        tv.setText(year + "-" + month + "-" + dayOfMonth);
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



}
