/**
 * Project:newsreader2
 * File:SettingUtil.java
 * Copyright 2013 WUZHUPC Co., Ltd. All rights reserved.
 */
package com.lawyee.apppublic.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebSettings.TextSize;

/**
 * @author wuzhu
 * @date 2013-4-25 下午8:14:04
 * @version $id$
 */
@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
public class SettingUtil extends net.lawyee.mobilelib.utils.SettingUtil {

	public final static String CSTR_KEY_NEWSFONTSIZE_INT = "NewsFontSize";
	public final static String CSTR_KEY_LASTCHECKPUSHMSGTIME_STR = "LastCheckTime";
	public final static String CSTR_KEY_ALOWPUSH_BOOL = "alowpush";
	public final static String CSTR_KEY_ALOWNIGHTPUSH_BOOL = "alownightpush";
	public final static String CSTR_KEY_IN3G2GNOTPIC_BOOL = "in3g2gnotpic";
	public final static String CSTR_KEY_OFFICECONTACTS_STR = "officecontacts";
	public final static String CSTR_KEY_ISFORBIDNORMALCOMMENT_BOOL = "isforbidnormalcomment";

	/**
	 * 字体列表
	 */
	public final static TextSize[] NewsFontSizes = {
			TextSize.SMALLER, TextSize.NORMAL,
			TextSize.LARGER, TextSize.LARGEST };

	/**
	 * 字体描述列表
	 */
	public final static String[] NewsFontSizesDesc = { "小号字", "中号字", "大号字",
			"特大号字" };

	/**
	 * 获取字体索引
	 * 
	 * @param textSize
	 * @return
	 */
	public static int getIndexFromNewsFontSizes(TextSize textSize) {
		for (int i = 0; i < NewsFontSizes.length; i++)
			if (textSize.equals(NewsFontSizes[i]))
				return i;
		return -1;
	}

	/**
	 * 获取有效的索引值
	 * 
	 * @param index
	 * @return
	 */
	private static int getEffFontSizeIndex(int index) {
		if (index < 0)
			index = 0;
		if (index >= NewsFontSizes.length)
			index = NewsFontSizes.length - 1;
		return index;
	}

	/**
	 * 根据索引获取字体
	 * 
	 * @param index
	 * @return
	 */
	public static TextSize getNewsFontSizeFromIndex(int index) {
		return NewsFontSizes[getEffFontSizeIndex(index)];
	}

	/**
	 * 获取字体对应的描述
	 * 
	 * @param textSize
	 * @return
	 */
	public static String getNewsFontSizeDesc(TextSize textSize) {
		return getNewsFontSizeDesc(getIndexFromNewsFontSizes(textSize));
	}

	/**
	 * 获取字体对应的描述
	 * 
	 * @param index
	 * @return
	 */
	public static String getNewsFontSizeDesc(int index) {
		if (index < 0 || index >= NewsFontSizesDesc.length)
			return "字体不存在";
		return NewsFontSizesDesc[index];
	}

	/**
	 * 获取新闻字体设置
	 * 
	 * @param c
	 * @return
	 */
	public static TextSize getNewsFontSize(Context c) {
		return NewsFontSizes[getNewsFontSizeIndex(c)];
	}

	/**
	 * 获取新闻字体设置
	 * 
	 * @param c
	 * @return
	 */
	public static int getNewsFontSizeIndex(Context c) {
		return getEffFontSizeIndex(getIntSettingValue(c,
				CSTR_KEY_NEWSFONTSIZE_INT, 1));
	}

	/**
	 * 设置字体设置
	 * 
	 * @param c
	 * @param textSize
	 */
	public static void setNewsFontSize(Context c, TextSize textSize) {
		int index = getIndexFromNewsFontSizes(textSize);
		setNewsFontSize(c, index);
	}

	/**
	 * 设置字体设置
	 * 
	 * @param c
	 * @param index
	 */
	public static void setNewsFontSize(Context c, int index) {
		setIntSettingValue(c, CSTR_KEY_NEWSFONTSIZE_INT, index);
	}

}
