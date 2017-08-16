package com.lawyee.apppublic.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

import com.lawyee.apppublic.R;
import com.lawyee.apppublic.ui.lawyerService.SessionActivity;
import com.lawyee.apppublic.util.XMPPHelper;

import net.lawyee.mobilelib.utils.L;
import net.lawyee.mobilelib.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseService extends Service {
	private static final String TAG = "BaseService";
	private static final String APP_NAME = "apppublic";
	private static final int MAX_TICKER_MSG_LEN = 50;
	protected static int SERVICE_NOTIFICATION = 1;

	private NotificationManager mNotificationManager;
	private Notification mNotification;
	private Intent mNotificationIntent;
	private Vibrator mVibrator;
	protected WakeLock mWakeLock;

	private Map<String, Integer> mNotificationCount = new HashMap<String, Integer>(
			2);
	private Map<String, Integer> mNotificationId = new HashMap<String, Integer>(
			2);
	private int mLastNotificationId = 2;

	protected Bitmap mNotificationLargeIcon;

	@Override
	public IBinder onBind(Intent arg0) {
		L.i(TAG, "called onBind()");
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		L.i(TAG, "called onUnbind()");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		L.i(TAG, "called onRebind()");
		super.onRebind(intent);
	}

	@Override
	public void onCreate() {
		L.i(TAG, "called onCreate()");
		super.onCreate();
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
				.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, APP_NAME);
		addNotificationMGR();
	}

	@Override
	public void onDestroy() {
		L.i(TAG, "called onDestroy()");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		L.i(TAG, "called onStartCommand()");
		return START_STICKY;
	}

	private void addNotificationMGR() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//聊天窗口
		mNotificationIntent = new Intent(this, SessionActivity.class);
	}

	protected void notifyClient(String fromJid, String fromUserName,
                                String message, boolean showNotification, String businessId, String consultType,
                                String userName, String staffid, String staffname) {
		if (!showNotification) {
			return;
		}
		mWakeLock.acquire();
		setNotification(fromJid, fromUserName, message,businessId,consultType,userName,staffid,staffname);
		setLEDNotification();

		int notifyId = 0;
		if (mNotificationId.containsKey(businessId)) {
			notifyId = mNotificationId.get(businessId);
		} else {
			mLastNotificationId++;
			notifyId = mLastNotificationId;
			mNotificationId.put(businessId, Integer.valueOf(notifyId));
		}

		// If vibration is set to true, add the vibration flag to
		// the notification and let the system decide.
		/*boolean vibraNotify = PreferenceUtils.getPrefBoolean(this,
				PreferenceConstants.VIBRATIONNOTIFY, false);
		if (vibraNotify) {
			mVibrator.vibrate(400);
		}*/
		mNotificationManager.notify(notifyId, mNotification);

		mWakeLock.release();
	}

	private void setNotification(String fromJid, String fromUserId,
                                 String message, String businessID, String consulttype,
                                 String username, String staffid, String staffname) {

		int mNotificationCounter = 0;
		if (mNotificationCount.containsKey(businessID)) {
			mNotificationCounter = mNotificationCount.get(businessID);
		}
		mNotificationCounter++;
		mNotificationCount.put(businessID, mNotificationCounter);
		String author = StringUtil.isEmpty(username)?staffname:username;
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
		/**
		 * Uri userNameUri = Uri.parse(jid);
		 Intent toChatIntent = new Intent(MyMessageActivity.this, OnLineChatActivity.class);
		 toChatIntent.setData(userNameUri);
		 toChatIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_JID, XMPPHelper.splitJidAndServer(jid));
		 toChatIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_USERNAME, StringUtil.isEmpty(username)?staffname:username);
		 toChatIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_BUSINESSID, businessID);
		 toChatIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_CONSULTTYPE, consulttype);
		 */
		Uri userNameUri = Uri.parse(fromJid);
		mNotificationIntent.setData(userNameUri);
		//TODO 需要传递的参数
		/*mNotificationIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_JID, XMPPHelper.splitJidAndServer(fromJid));
		mNotificationIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_USERNAME,
				username);
		mNotificationIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_BUSINESSID,
				businessID);
		mNotificationIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_CONSULTTYPE,
				consulttype);
		mNotificationIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_STAFFID,
				staffid);
		mNotificationIntent.putExtra(OnLineChatActivity.INTENT_EXTRA_STAFFNAME,
				staffname);*/
		mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// need to set flag FLAG_UPDATE_CURRENT to get extras transferred
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		if(mNotificationLargeIcon==null)
			mNotificationLargeIcon = BitmapFactory.decodeResource(getResources(),
					R.mipmap.ic_launcher);
		mNotification = new NotificationCompat.Builder(this)
				.setLargeIcon(mNotificationLargeIcon)
				//.setSmallIcon(R.drawable.icon_lawyer_24)
				.setWhen(System.currentTimeMillis())
				.setTicker(ticker)
				.setContentTitle(title)
				.setContentText(XMPPHelper.generShowMessage(getApplicationContext(),message))
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

	private void setLEDNotification() {
		boolean isLEDNotify = false;
		//PreferenceUtils.getPrefBoolean(this,
				//PreferenceConstants.LEDNOTIFY, true);
		if (isLEDNotify) {
			mNotification.ledARGB = Color.MAGENTA;
			mNotification.ledOnMS = 300;
			mNotification.ledOffMS = 1000;
			mNotification.flags |= Notification.FLAG_SHOW_LIGHTS;
		}
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
