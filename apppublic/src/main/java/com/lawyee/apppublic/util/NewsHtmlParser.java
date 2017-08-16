package com.lawyee.apppublic.util;

import android.content.Context;
import android.webkit.WebView;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.dal.BaseJsonService;
import com.lawyee.apppublic.dal.InfoService;
import com.lawyee.apppublic.vo.InfomationVO;
import com.lawyee.apppublic.vo.InfomationDetailVO;

import net.lawyee.mobilelib.utils.FileUtil;
import net.lawyee.mobilelib.utils.HtmlParser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 资讯详情处理
 * 
 * @author wuzhu
 * @note 初始化后调用startHandle获取数据
 */
public class NewsHtmlParser extends HtmlParser {
	private InfomationVO mDataVO;

	private NewsHtmlParser(WebView wevView, Context context, String htmltext,
                           int screenwidth) {
		super(wevView, context, htmltext, false, screenwidth);
	}

	public NewsHtmlParser(WebView webView, Context context, InfomationVO datavo,
                          int screenwidth) {
		this(webView, context, "", screenwidth);
		mDataVO = datavo;
		// 不要读取缓存
		String cachehtml = FileUtil.readContent(mDataVO.getDataFileName(context));
		setHandledHtmltext(cachehtml);
	}

	public void startHandle() {
		// 如果已经有已经处理过的内容
		if (isHandledContent()) {
			execute((Void) null);
			return;
		}
		// 如果已经是详情，则直接处理
		if (mDataVO instanceof InfomationDetailVO) {
			setNoHandledHtmltext(((InfomationDetailVO) mDataVO).getContent());
			execute((Void) null);
			return;
		}
		// 未处理，则要调用处理
		InfoService service = new InfoService(getContext());
		service.getDetail(mDataVO.getOid(), new BaseJsonService.IResultInfoListener() {

					@Override
					public void onError(String msg, String content) {
						setHandledHtmltext(setErrorInfo(msg));
						execute((Void) null);
					}

					@Override
					public void onComplete(ArrayList<Object> values,
							String content) {
						if (values == null || values.isEmpty()) {
							setHandledHtmltext(setErrorInfo("无效的数据内容"));
							execute((Void) null);
							return;
						}
						Object o = values.get(0);
						if (!(o instanceof InfomationDetailVO)) {
							setHandledHtmltext(setErrorInfo("无效的数据内容"));
							execute((Void) null);
							return;
						}
						mDataVO = (InfomationDetailVO) o;
						setNoHandledHtmltext(((InfomationDetailVO) mDataVO)
								.getContent());
						execute((Void) null);
					}
				});
	}

	public InfomationVO getDataVO() {
		return mDataVO;
	}

	public void setDataVO(InfomationVO dataVO) {
		this.mDataVO = dataVO;
	}

	/**
	 * 显示错误信息
	 * 
	 * @param msg
	 */
	private String setErrorInfo(String msg) {
		return WebViewUtil.getHtmlHead() + mDataVO.getHtmlTitle()
				+ mDataVO.getHtmlSubTitle()
				+ WebViewUtil.getHtmlErrorHit(msg, true)
				+ WebViewUtil.getHtmlEnd();
	}

	/**
	 * 显示新闻信息
	 */
	private String setNewsInfo(String content) {
		return WebViewUtil.getHtmlHead() + mDataVO.getHtmlTitle()
				+ mDataVO.getHtmlSubTitle() + content
				+ WebViewUtil.getHtmlEnd();
	}

	@Override
	protected Element handleDocument(Document doc) {
		if (doc == null) {
			return null;
		}
		Elements es = doc.getElementsByTag("a");
		/*
		 * for (Element e : es) { if (e.getElementsByTag("img") != null)//
		 * 包含图片的链接时 { e.removeAttr("href"); } }
		 */
		for (Element e : es) {
			Elements imges = e.getElementsByTag("img");
			if (imges != null && imges.size() > 0)// 包含图片的链接时
			{
				e.removeAttr("href");
			}
		}
		return doc;
	}

	@Override
	protected String getDocument(Element e) {
		if (e == null) {
			return setErrorInfo(getContext().getResources().getString(
					R.string.newsdetail_news_content_geterror));
		}
		String content = e.html().replace("<html>\n" +
				" <head></head>\n" +
				" <body>","").replace(" </body>\n" +
				"</html>","");
		String html = setNewsInfo(content);
		// 缓存
		FileUtil.saveContent(mDataVO.getDataFileName(getContext()), html);
		return html;
	}

}
