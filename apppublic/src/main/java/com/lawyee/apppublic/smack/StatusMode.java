package com.lawyee.apppublic.smack;


/**
 * 0 设置在线 1设置Q我吧，2设置忙碌 3 设置离开 4设置隐身 5设置离线
 */
public enum StatusMode {
	available("在线", 0),
	chat("Q我吧", 1),
	dnd("请勿打扰", 2),
	away("离开", 3),
	xa("隐身", 4),
	offline("离线", 5);

	private final String text;
	private final int drawableId;

	StatusMode(String text, int drawableId) {
		this.text = text;
		this.drawableId = drawableId;
	}

	public String getText() {
		return text;
	}

	public int getDrawableId() {
		return drawableId;
	}

	public String toString() {
		return name();
	}

	public static StatusMode fromString(String status) {
		return StatusMode.valueOf(status);
	}

}
