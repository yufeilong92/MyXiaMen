package com.lawyee.apppublic.smack;


import com.lawyee.apppublic.exception.IMException;

public interface Smack {
	public boolean login(String account, String password) throws IMException;

	public boolean logout();

	public boolean isAuthenticated();

	public void setStatusFromConfig() throws IMException;

	public void sendMessage(String user, String message, String businessId, String consultType,
                            String staffName,
                            String staffId, String userName) throws IMException;

	public String sendFileMessageStart(String user, String filepath, String businessId, String
            consultType, String staffName,
                                       String staffId, String userName, int dstype);

	public void sendFileMessageWithUploadComplte(String id, String fileid) throws IMException;

	public void removeFileMessage(String id);

	public String getNameForJID(String jid);
}
