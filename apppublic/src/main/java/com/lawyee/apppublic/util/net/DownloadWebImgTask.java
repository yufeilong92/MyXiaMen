package com.lawyee.apppublic.util.net;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

import com.lawyee.apppublic.util.SettingUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.ImageUtil;
import net.lawyee.mobilelib.utils.NetUtil;
import net.lawyee.mobilelib.utils.StringUtil;

import java.io.File;

public class DownloadWebImgTask extends AsyncTask<String, String, Boolean> {
	private static final String TAG = "DownloadWebImgTask";
	private Context mContext;
	private WebView mWebView;

	private String[] mImageUrls;
	private Boolean[] mHasDatas;
	private boolean mdown;

	/**
	 * 
	 * @param downin3g2g
	 *            3g/2g网络情况是否下载图片
	 */
	public DownloadWebImgTask(Context c, WebView wv, boolean downin3g2g) {
		mContext = c;
		mWebView = wv;
		boolean notdown = SettingUtil.getBooleanSettingValue(c,
				SettingUtil.CSTR_KEY_IN3G2GNOTPIC_BOOL, true);
		// 几种情况下可以下载：设置里不限制不能下载和当前网络是wifi
		mdown = downin3g2g || !notdown
				|| (NetUtil.getNetWorkType(c) == NetUtil.CINT_CONNECTTYPE_WIFI);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		if (values == null || values.length == 0
				|| !ImageUtil.hasLocalFile(mContext,values[0]))
			return;
		mWebView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++)  " + "{"
				+ "    var imgSrc = objs[i].getAttribute(\"src_link\"); "
				+ "    var imgOriSrc = objs[i].getAttribute(\"ori_link\"); "
				+ " if(imgOriSrc == \"" + values[0] + "\"){ "
				+ "    objs[i].setAttribute(\"src\",imgSrc);}" + "}" + "})()");
	}

	@Override
	protected void onPostExecute(Boolean hasdata) {
		if (!hasdata || mImageUrls == null || mImageUrls.length == 0)
			return;

		for (int i = 0; i < mImageUrls.length; i++) {
			if (!mHasDatas[i])
				continue;
			String imageurl = mImageUrls[i];
			mWebView.loadUrl("javascript:(function(){"
					+ "var objs = document.getElementsByTagName(\"img\"); "
					+ "for(var i=0;i<objs.length;i++)  "
					+ "{"
					+ "    var imgSrc = objs[i].getAttribute(\"src_link\"); "
					+ "    var imgOriSrc = objs[i].getAttribute(\"ori_link\"); "
					+ " if(imgOriSrc == \"" + imageurl + "\"){ "
					+ "    objs[i].setAttribute(\"src\",imgSrc);}" + "}"
					+ "})()");
		}
	}

	@Override
	protected Boolean doInBackground(String... params) {

		if (params == null || params.length == 0)
			return false;
		mImageUrls = params;
		mHasDatas = new Boolean[mImageUrls.length];
		for(int i=0;i<params.length;i++)
		{
			mHasDatas[i]=false;
			String urlStr = params[i];
			if(StringUtil.isEmpty(urlStr))
				continue;
			//判断文件是否已经存在
			if(ImageUtil.hasLocalFile(mContext,urlStr))
			{
				mHasDatas[i]=true;
				publishProgress(urlStr);
				continue;
			}
			//下载文件
			ImageLoader.getInstance().loadImageSync(urlStr);
			File localpath = ImageLoader.getInstance().getDiskCache().get(urlStr);
			if(localpath.exists())
			{
				//文件存在就修改成符合条件的名称
				localpath.renameTo(new File(ImageUtil.getDefaultImageFileFullPath(mContext,urlStr)));
				mHasDatas[i]=true;
				publishProgress(urlStr);
			}
		}
		return true;
	}

}
