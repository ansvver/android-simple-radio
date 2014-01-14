package com.yabuhoo.client;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import com.yabuhoo.config.Globals;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class UDPServer extends Thread {

	DatagramPacket data = null;
	
	public UDPServer(DatagramPacket data) {
		this.data = data;
	}
	
	public static MessageActivity messageActivity = null;
	
	@SuppressLint("SdCardPath")
	public void run() {
		// 1234132424132
		//lisi,1234.amr,23134234234
		String s = new String(data.getData()).trim();
		try {
			Socket socket = new Socket(Globals.SERVER_IP, Globals.SERVER_SOCKET_PORT);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write((Globals.GET_FROM_SERVER_CMD_PREFIX + Globals.CMD_SEPARATOR + s.split(Globals.CMD_SEPARATOR)[1]).getBytes());
			out.flush();
			byte[] b = new byte[1000];
			in.read(b);
			//ok,64723
			long size = Long.parseLong(new String(b).trim().split(Globals.CMD_SEPARATOR)[1]);
			out.write(Globals.SUCCESS.getBytes());
			out.flush();
			
			FileOutputStream fout = new FileOutputStream(new File("/sdcard/", s.split(Globals.CMD_SEPARATOR)[1]));
			int len = 0;
			long length = 0;
			byte[] b1 = new byte[1024];
			while((len = in.read(b1)) != -1) {
				fout.write(b1, 0, len);
				length += len;
				if(length >= size) {
					break;
				}
			}
			fout.close();
			socket.close();
			
			DatagramSocket socket2 = new DatagramSocket();
			byte[] bb = s.split(Globals.CMD_SEPARATOR)[2].getBytes();
			DatagramPacket data = new DatagramPacket(bb, bb.length, InetAddress.getByName(Globals.SERVER_IP), Globals.UDPSERVER_SOCKET_PORT);
			socket2.send(data);
			
			Message msg = new Message();
			Bundle b3 =new Bundle();
			b3.putString("username", s.split(Globals.CMD_SEPARATOR)[0]);
			b3.putString("filename", s.split(Globals.CMD_SEPARATOR)[1]);
			msg.setData(b3);
			messageActivity.han.sendMessage(msg);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void openServer() throws Exception {
		DatagramSocket socket = new DatagramSocket(Globals.CLIENT_SOCKET_PORT);
		
		while(true) {
			byte[] b = new byte[1000];
			DatagramPacket data = new DatagramPacket(b, b.length);
			socket.receive(data);
			new UDPServer(data).start();
		}
	}
}
