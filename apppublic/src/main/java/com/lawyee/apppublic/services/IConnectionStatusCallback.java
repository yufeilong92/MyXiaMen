package com.lawyee.apppublic.services;

public interface IConnectionStatusCallback {
	public void connectionStatusChanged(int connectedState, String reason);
}
