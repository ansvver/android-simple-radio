package com.yabuhoo.server;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class Laucher {
	public static void main(String[] args) {
		new Thread() {
			public void run() {
				try {
					Server.openServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread() {

			public void run() {

				try {
					UDPServer.openServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};

		}.start();
		
	}
}
