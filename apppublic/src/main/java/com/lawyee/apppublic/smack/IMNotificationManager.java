package com.lawyee.apppublic.smack;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.lawyerService.SessionActivity;
import com.lawyee.apppublic.util.XMPPHelper;

import net.lawyee.mobilelib.app.AppContext;
import net.lawyee.mobilelib.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

public class IMNotificationManager {
	private static final String TAG = "IMNotificationManager";
	private static final String APP_NAME = "apppublic";
	private static final int MAX_TICKER_MSG_LEN = 50;
	protected static int SERVICE_NOTIFICATION = 1;

	private static volatile IMNotificationManager mManager;

	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private Intent mNotificationIntent;

	private Map<String, Integer> mNotificationCount = new HashMap<String, Integer>(
			2);
	private Map<String, Integer> mNotificationId = new HashMap<String, Integer>(
			2);
	private int mLastNotificationId = 2;

	protected Bitmap mNotificationLargeIcon;

	private IMNotificationManager()
	{
		addNotificationMGR();
	}
	public static IMNotificationManager getInstance()
	{
		if(mManager==null)
		{
			synchronized (IMNotificationManager.class)
			{
				if(mManager==null)
					mManager=new IMNotificationManager();
			}
		}
		return mManager;
	}

	private void addNotificationMGR() {
		mNotificationManager = (NotificationManager) AppContext.context().getSystemService(NOTIFICATION_SERVICE);
		//聊天窗口
		mNotificationIntent = new Intent(AppContext.context(), SessionActivity.class);
	}

	public void notifyClient(String fromJid, String fromUserName,
                                String message,String businessId)
	{
		setNotification(fromJid, fromUserName, message,businessId);

		int notifyId = 0;
		if (mNotificationId.containsKey(businessId)) {
			notifyId = mNotificationId.get(businessId);
		} else {
			mLastNotificationId++;
			notifyId = mLastNotificationId;
			mNotificationId.put(businessId, Integer.valueOf(notifyId));
		}
		mNotificationManager.notify(notifyId, mNotification);
	}

	private void setNotification(String fromJid, String username,
                                 String message, String businessID) {

		int mNotificationCounter = 0;
		if (mNotificationCount.containsKey(businessID)) {
			mNotificationCounter = mNotificationCount.get(businessID);
		}
		mNotificationCounter++;
		mNotificationCount.put(businessID, mNotificationCounter);
		String author = username;
		author = StringUtil.isEmpty(author)?fromJid:author;

		String title = author;
		String ticker;
		boolean isTicker = true;
				/*PreferenceUtils.getPrefBoolean(this,
				PreferenceConstants.TICKER, true);*/
		if (isTicker) {
			int newline = message.indexOf('\n');
			int limit = 0;
			String messageSummary = message;
			if (newline >= 0)
				limit = newline;
			if (limit > MAX_TICKER_MSG_LEN
					|| message.length() > MAX_TICKER_MSG_LEN)
				limit = MAX_TICKER_MSG_LEN;
			if (limit > 0)
				messageSummary = message.substring(0, limit) + " [...]";
			ticker = title + ":\n" + messageSummary;
		} else
			ticker = author;
		//需要传递的参数
		mNotificationIntent.putExtra(SessionActivity.CSTR_EXTRA_TITLE_STR,username);
		mNotificationIntent.putExtra(SessionActivity.CSTR_EXTRA_SESSION_STR,fromJid);
		mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// need to set flag FLAG_UPDATE_CURRENT to get extras transferred
		PendingIntent pendingIntent = PendingIntent.getActivity(AppContext.context(), 0,
				mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		if(mNotificationLargeIcon==null)
			mNotificationLargeIcon = BitmapFactory.decodeResource(AppContext.context().getResources(),
					R.mipmap.ic_launcher);
		mNotification = new NotificationCompat.Builder(AppContext.context())
				.setLargeIcon(mNotificationLargeIcon)
				.setSmallIcon(R.drawable.ic_launcher_24)
				.setWhen(System.currentTimeMillis())
				.setTicker(ticker)
				.setContentTitle(title)
				.setContentText(XMPPHelper.generShowMessage(AppContext.context(),message))
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_ALL)
				.build();

		/*mNotification = new Notification(R.mipmap.ic_launcher, ticker,
				System.currentTimeMillis());
		mNotification.setLatestEventInfo(this, title, message, pendingIntent);*/
		if (mNotificationCounter > 1)
			mNotification.number = mNotificationCounter;
	}

	public void resetNotificationCounter(String userJid) {
		mNotificationCount.remove(userJid);
	}

	public void clearNotification(String Jid) {
		int notifyId = 0;
		if (mNotificationId.containsKey(Jid)) {
			notifyId = mNotificationId.get(Jid);
			mNotificationManager.cancel(notifyId);
		}
	}

	public void clearAllNotification()
	{
		if(mNotificationId.isEmpty())
			return;
		for(String key:mNotificationId.keySet())
		{
			clearNotification(key);
			resetNotificationCounter(key);
		}
		mNotificationId.clear();
	}

}
