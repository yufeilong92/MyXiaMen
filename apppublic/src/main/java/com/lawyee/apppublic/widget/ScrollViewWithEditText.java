package com.lawyee.apppublic.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: LawerApp
 * @Package com.lawyee.appservice.widget
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/7/4 17:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class ScrollViewWithEditText extends ScrollView {
    public ScrollViewWithEditText(Context context) {
        super(context);
    }

    public ScrollViewWithEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewWithEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {

        if (child instanceof EditText)
            return true;
        return false;
    }
}
