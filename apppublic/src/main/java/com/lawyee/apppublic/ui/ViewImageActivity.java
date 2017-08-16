package com.lawyee.apppublic.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.lawyee.apppublic.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 图片查看
 * 
 * @note 必须传入参数image 图片路径 类型：String
 * @author wuzhu
 * 
 */
public class ViewImageActivity extends BaseActivity{
	/**
	 * 传入参数-图片路径
	 */
	public static final String CSTR_EXTRA_IMAGE_STR = "image";
	public static final String CSTR_EXTRA_IMAGE_STR_LOCAL = "image_local";

	private String mImagePath;
	private String mImageLocalPath;
	private PhotoView mpv_photo;


	@Override
	protected void initContentView(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_viewimage);
		Intent intent = getIntent();
		mImagePath = intent.getStringExtra(CSTR_EXTRA_IMAGE_STR);
		mImageLocalPath=intent.getStringExtra(CSTR_EXTRA_IMAGE_STR_LOCAL);
		mpv_photo = (PhotoView)findViewById(R.id.viewimage_pv);
		ImageLoader loader = ImageLoader.getInstance();
		if(mImageLocalPath!=null&&!mImageLocalPath.equals(""))
		{
			loader.displayImage(Uri.parse("file://" + mImageLocalPath).toString(), mpv_photo);
		}else{
			loader.displayImage(mImagePath, mpv_photo);
		}
	}


}
