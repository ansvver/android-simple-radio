package com.yabuhoo.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class Pool extends Thread {

	public String id;
	public Set<String>	ips = new HashSet<String>();
	public String message = "";
	
	
	public void remove(String ip) {
		ips.remove(ip);
	}
	
	boolean handled = false;
	public void run() {
		
		//lisi,1234.amr,1234343
		try {
			DatagramSocket socket = new DatagramSocket();
			for(int i = 0; i < 3; i++) {
				Iterator<String> it = ips.iterator();
				while(it.hasNext()) {
					String ip = it.next();
System.out.println(message + "##############"+ip+"##############ROUND" + i);
					byte[] b = (message + Globals.CMD_SEPARATOR + id).getBytes();
					DatagramPacket data = new DatagramPacket(b, b.length, InetAddress.getByName(ip), Globals.CLIENT_SOCKET_PORT);
					socket.send(data);
				}
			
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
				}
			}
			handled = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public Pool() {
		id = new Date().getTime()  + "R" 
				+ ((int) (Math.random() * 100000));
	}
}
