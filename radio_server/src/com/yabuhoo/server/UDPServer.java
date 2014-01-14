package com.yabuhoo.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class UDPServer implements Runnable {

	DatagramPacket data = null;
	
	public UDPServer(DatagramPacket data) {

		this.data = data;
	}

	@Override
	public void run() {
		// 12312312312312
		String s = new String(data.getData()).trim();
System.out.println(s);
		Pool p = (Pool) Server.pools.get(s);
		p.ips.remove(data.getAddress().getHostAddress());
		if(p.handled) {
			Server.pools.remove(s);
		}
	}

	public static void openServer() throws Exception {

		DatagramSocket socket = new DatagramSocket(Globals.UDPSERVER_SOCKET_PORT);
		
		while (true) {
			byte[] b = new byte[1000];
			DatagramPacket data = new DatagramPacket(b, b.length);
			socket.receive(data);
			new Thread(new UDPServer(data)).start();

		}
	}	
	
}
