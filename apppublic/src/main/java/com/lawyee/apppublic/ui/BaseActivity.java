/**
 * Project:newsreader2
 * File:BaseActivity.java
 * Copyright 2013 WUZHUPC Co., Ltd. All rights reserved.
 */
package com.lawyee.apppublic.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.lawyee.apppublic.R;
import com.lawyee.apppublic.config.ApplicationSet;
import com.lawyee.apppublic.util.AndroidBug5497Workaround;

import net.lawyee.mobilelib.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  基础Activity
 * @Package com.lawyee.apppublic.ui
 * @Description:    应用中所有的activity继承于此，特殊除外
 * @author:wuzhu
 * @date:   2017-04-28
 * @version V1.0.xxxxxxxx
 * @verdescript  1.0 20170428  wuzhu 初建
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

	protected static final String TAG = BaseActivity.class.getSimpleName();
	/**
	 * 传入参数-标题
	 */
	public static final String CSTR_EXTRA_TITLE_STR = "title";


	/**
	 * 权限申请回调常量,权限相关回调常量请在此处定义
	 * 注意：Can only use lower 8 bits for requestCode
	 */
	protected static final int RC_SETTINGS_SCREEN = 100;
	protected static final int RC_STORAGE_PERM = 101;
	protected static final int RC_STORAGE_READ_PERM = 102;
	protected static final int RC_STORAGE_WRITE_PERM = 103;
	protected static final int RC_CAMERA_PERM = 104;
	protected static final int RC_LOCATION_PERM = 105;

	private Map<Integer, PermissionCallback> mPermissonCallbacks = null;
	private Map<Integer, String[]> mPermissions = null;

	public static ArrayList<BackPressHandler> mListeners = new ArrayList<BackPressHandler>();

	/**
	 * 数据是否处理中，用于服务端请求数据时标识，防止重复申请
	 */
	private Boolean mInProgess= false;

	private String mBaseTitle;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ApplicationSet.getInstance().addActivity(this);
		Intent intent = getIntent();
		mBaseTitle = intent.getStringExtra(CSTR_EXTRA_TITLE_STR);
		initContentView(savedInstanceState);
		if(!StringUtil.isEmpty(mBaseTitle))
			setTitle(mBaseTitle);
		//透明状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			// Translucent status bar
			window.setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			/**
			 * 解决设置透明时输入法导致输入框不可移动问题
			 */
			AndroidBug5497Workaround.assistActivity(this);
		}
	}

	protected abstract  void initContentView(Bundle savedInstanceState);

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ApplicationSet.getInstance().removeActivity(this);
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (mListeners.size() > 0)
			for (BackPressHandler handler : mListeners) {
				handler.activityOnResume();
			}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mListeners.size() > 0)
			for (BackPressHandler handler : mListeners) {
				handler.activityOnPause();
			}
	}

	/**
	 * 返回点击事件
	 * @param v
	 */
	public void onHomeClick(View v)
	{
		this.finish();
	}

	public void setTitle(String title)
	{
		TextView textView = (TextView)findViewById(R.id.activity_title_text);
		if(textView!=null)
			textView.setText(title);
	}

	public String getBaseTitle() {
		return mBaseTitle;
	}

	public void setBaseTitle(String title) {
		this.mBaseTitle = title;
	}

	public static abstract interface BackPressHandler {

		public abstract void activityOnResume();

		public abstract void activityOnPause();

	}

	public Boolean getInProgess()
	{
		return mInProgess;
	}

	public void setInProgess(Boolean inProgess)
	{
		this.mInProgess = inProgess;
	}

	/**
	 * 启动浏览器
	 *
	 * @param url
	 *            地址
	 */
	public void runBrowser(String url) {
		runBrowser(url, this);
	}

	/**
	 * 启动浏览器
	 *
	 * @param url
	 *            地址
	 */
	public static void runBrowser(String url, Context c) {
		if (StringUtil.isEmpty(url))
			return;
		try {
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			c.startActivity(intent);
		} catch (Exception e) {

		}
	}
	/********************** 以下内容跟权限相关 *********************************/
	protected interface PermissionCallback {
		/**
		 * has all permission
		 * @param allPerms all permissions
		 */
		void hasPermission(List<String> allPerms);

		/**
		 * denied some permission
		 * @param deniedPerms denied permission
		 * @param grantedPerms granted permission
		 * @param hasPermanentlyDenied has permission denied permanently
		 */
		void noPermission(List<String> deniedPerms, List<String> grantedPerms, Boolean hasPermanentlyDenied);
	}


	/**
	 * request permission
	 * @param rationale if denied first, next request rationale
	 * @param requestCode requestCode
	 * @param perms permissions
	 * @param callback callback
	 */
	protected void performCodeWithPermission(@NonNull String rationale,
											 final int requestCode, @NonNull String[] perms, @NonNull PermissionCallback callback) {
		if (EasyPermissions.hasPermissions(this, perms)) {
			callback.hasPermission(Arrays.asList(perms));
		} else {
			if(mPermissonCallbacks == null) {
				mPermissonCallbacks = new HashMap<>();
			}
			mPermissonCallbacks.put(requestCode, callback);

			if(mPermissions == null) {
				mPermissions = new HashMap<>();
			}
			mPermissions.put(requestCode, perms);

			EasyPermissions.requestPermissions(this, rationale, requestCode, perms);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		if(mPermissonCallbacks == null || !mPermissonCallbacks.containsKey(requestCode)) {
			return;
		}
		if(mPermissions == null || !mPermissions.containsKey(requestCode)) {
			return;
		}

		// 100% granted permissions
		if(mPermissions.get(requestCode).length == perms.size()) {
			mPermissonCallbacks.get(requestCode).hasPermission(Arrays.asList(mPermissions.get(requestCode)));
		}
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		if(mPermissonCallbacks == null || !mPermissonCallbacks.containsKey(requestCode)) {
			return;
		}
		if(mPermissions == null || !mPermissions.containsKey(requestCode)) {
			return;
		}

		//granted permission
		List<String> grantedPerms = new ArrayList<>();
		for(String perm : mPermissions.get(requestCode)) {
			if(!perms.contains(perm)) {
				grantedPerms.add(perm);
			}
		}

		//check has permission denied permanently
		if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
			mPermissonCallbacks.get(requestCode).noPermission(perms, grantedPerms, true);
		} else {
			mPermissonCallbacks.get(requestCode).noPermission(perms, grantedPerms, false);
		}
	}

	/**
	 * alert AppSet Permission
	 * @param rationale alert setting rationale
	 */
	protected void alertAppSetPermission(String rationale) {
		new AppSettingsDialog.Builder(this, rationale)
				.setTitle(getString(R.string.rationale_title_settings_dialog))
				.setPositiveButton(getString(R.string.rationale_setting))
				.setNegativeButton(getString(R.string.rationale_cancel), null)
				.build()
				.show();
	}

	/**
	 * alert AppSet Permission
	 * @param rationale alert setting rationale
	 * @param requestCode onActivityResult requestCode
	 */
	protected void alertAppSetPermission(String rationale, int requestCode) {
		new AppSettingsDialog.Builder(this, rationale)
				.setTitle(getString(R.string.title_settings_dialog))
				.setPositiveButton(getString(R.string.rationale_setting))
				.setNegativeButton(getString(R.string.rationale_cancel), null)
				.setRequestCode(requestCode)
				.build()
				.show();
	}

	/********************** 以上内容跟权限相关 *********************************/
	public String getmBaseTitle() {
		return mBaseTitle;
	}

	public void setmBaseTitle(String mBaseTitle) {
		this.mBaseTitle = mBaseTitle;
	}
	public void setBadgView(BadgeView badgeView,int num){
		if (num == 0) {
			badgeView.setVisibility(View.GONE);
		} else {
			badgeView.setVisibility(View.VISIBLE);
			if(num>99){
				badgeView.setText(R.string.num_99);
			}else {
				badgeView.setBadgeCount(num);
			}
		}
	}
}
