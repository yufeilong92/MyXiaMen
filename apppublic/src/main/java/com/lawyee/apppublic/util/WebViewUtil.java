package com.lawyee.apppublic.util;

import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.StringUtil;
import android.content.Context;
import android.content.Intent;

import com.lawyee.apppublic.ui.BaseActivity;
import com.lawyee.apppublic.ui.ViewImageActivity;
import com.lawyee.apppublic.vo.InfomationVO;

/**
 * 处理webview相关的内容
 * 
 * @author wuzhu email:wuzhupc@gmail.com
 * @version 创建时间2012-12-4 下午9:39:33
 */
public class WebViewUtil {
	/**
	 * 重载链接标签
	 */
	public static final String CSTR_RELOADLINK = "http://lawyee.wuzhu/reload";

	public static void ProcessSiteLink(Context c, String url) {
		if (FileUtil.isImageFile(url)) {
			Intent intent = new Intent(c, ViewImageActivity.class);
			intent.putExtra(ViewImageActivity.CSTR_EXTRA_IMAGE_STR, url);
			c.startActivity(intent);
			return;
		}
		// TODO 站内新闻处理
		BaseActivity.runBrowser(url, c);
	}

	public static boolean isreloadlink(String url) {
		if (StringUtil.isEmpty(url))
			return false;
		return url.equals(CSTR_RELOADLINK);
	}

	/**
	 * 返回Html开头
	 * 
	 * @return <html><head><meta http-equiv="Content-Type"
	 *         content="text/html;charset=utf-8"> <script type="text/javascript"
	 *         language="javascript"> function fixImage(i,w,h) { var ow =
	 *         i.width;//图的宽度 var oh = i.height; //图的高度 var rw = w/ow; var rh =
	 *         h/oh; var r = Math.min(rw,rh); if (w ==0 && h == 0) { r = 1;
	 *         }else if (w == 0) { r = rh<1?rh:1; }else if (h == 0) { r =
	 *         rw<1?rw:1; } if (ow!=0 && oh!=0) { i.width = ow * r; i.height =
	 *         oh * r; }else { var __method = this, args = $A(arguments);
	 *         window.setTimeout(function() { fixImage.apply(__method, args); },
	 *         200); } i.onload = function(){} } </script> </head><body>
	 */
	public static String getHtmlHead() {
		// 图像显示屏幕宽度80%
		String autoloadimgscript = "<script type=\"text/javascript\" language=\"javascript\"> "
				+ "function fixImage(i,w,h){  var cw=document.body.clientWidth; if(cw*0.9<w) w=cw*0.9; var ow = i.width;  "
				+ "var oh = i.height;  var rw = w/ow; var rh = h/oh;  var r = Math.min(rw,rh);  "
				+ "if (w ==0 && h == 0){  r = 1;  }else if (w == 0){  r = rh<1?rh:1;  }else if (h == 0){  r = rw<1?rw:1;  } "
				+ "if (ow!=0 && oh!=0){ i.width = ow * r;  i.height = oh * r;  } "
				+ "else{ var __method = this, args = $A(arguments);  window.setTimeout(function() {  "
				+ " fixImage.apply(__method, args); }, 200); }  i.onload = function(){}  }  </script>";
		return "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">"
				+ autoloadimgscript
				+ "</head><body style=\"background-color: #F9F9F9; color: #666666;font-size: 14pt;\">";
	}

	/**
	 * 返回错误提示
	 * 
	 * @param msg
	 *            错误提示信息
	 * @param hasreload
	 *            是否有重载提示
	 * @return
	 */
	public static String getHtmlErrorHit(String msg, boolean hasreload) {
		String result = "<br/>&nbsp;&nbsp;很抱歉，您访问的内容"
				+ (StringUtil.isEmpty(msg) ? "" : "因为" + msg + "的原因而")
				+ "无法正常加载。";
		if (hasreload)
			result = result + "<a href=\"" + CSTR_RELOADLINK + "\">请尝试重新加载</a>";
		return result;
	}

	/**
	 * 返回Html结尾
	 * 
	 * @return
	 */
	public static String getHtmlEnd() {
		return "<br/><br/></body></html>";
	}
}