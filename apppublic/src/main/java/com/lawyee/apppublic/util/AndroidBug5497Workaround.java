package com.lawyee.apppublic.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * 解决设置透明时输入法导致输入框不可移动问题
 * Created by wuzhu on 16/3/25.
 * http://stackoverflow.com/questions/7417123/android-how-to-adjust-layout-in-full-screen-mode-when-softkeyboard-is-visible/19494006#19494006
 */
public class AndroidBug5497Workaround {
    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity (Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private Activity mActivity;
    /**
     * 设置透明后，可用区域要包括状态条，所以在设置可用区域时要增加上
     */
    private int mStatusBarHeight=0;

    private AndroidBug5497Workaround(Activity activity) {
        mActivity = activity;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            /*int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            L.d("AndroidBug5497Workaround","usableHeightNow:"+usableHeightNow+" usableHeightSansKeyboard:"+usableHeightSansKeyboard);
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                if(usableHeightPrevious>0) {
                    if(mStatusBarHeight==0)
                        mStatusBarHeight = getStatusBarHeight();
                    frameLayoutParams.height = usableHeightNow + mStatusBarHeight;
                }
                else
                    frameLayoutParams.height = usableHeightNow;
            } else {
                // keyboard probably just became hidden
                if(mStatusBarHeight==0)
                    mStatusBarHeight = getStatusBarHeight();
                frameLayoutParams.height = usableHeightNow + mStatusBarHeight;
            }*/
            if(mStatusBarHeight==0)
                mStatusBarHeight = getStatusBarHeight();
            frameLayoutParams.height = usableHeightNow + mStatusBarHeight;
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    private int getStatusBarHeight()
    {
        Rect frame = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }
}
