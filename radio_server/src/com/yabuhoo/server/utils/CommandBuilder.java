package com.yabuhoo.server.utils;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class CommandBuilder {

	public static String get(String cmd, String... params) {
		StringBuilder sb = new StringBuilder();
		sb.append(cmd.trim());
		sb.append(",");
		for(String param : params) {
			sb.append(param);
			sb.append(",");
		}
		return sb.substring(0, sb.length()-1);
	}
	
}
