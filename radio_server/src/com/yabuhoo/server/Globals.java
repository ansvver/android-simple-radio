package com.yabuhoo.server;

import java.io.File;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class Globals {

	public static final String SUCCESS = "ok";
	public static final String FAILURE = "error";	
	
	
	public static final int SERVER_SOCKET_PORT = 8081;
	public static final int UDPSERVER_SOCKET_PORT = 8082;
	public static final int CLIENT_SOCKET_PORT = 8083;
	
	public static final String CMD_SEPARATOR = ",";
	public static final String LOGIN_CMD_PREFIX = "login";
	public static final String GET_FROM_SERVER_CMD_PREFIX = "get";
	public static final String UPLOAD_TO_SERVER_CMD_PREFIX = "upload";
	
	public static final String USER_INFO_XML_FILE = "src"+ File.separator +"user_info.xml";
	public static final String AMR_FILE_FOLDER = "D:\\amr";
	
}
