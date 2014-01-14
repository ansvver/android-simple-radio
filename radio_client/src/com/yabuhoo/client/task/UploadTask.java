package com.yabuhoo.client.task;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.yabuhoo.client.MainActivity;
import com.yabuhoo.config.Globals;

import android.os.AsyncTask;
import android.os.Looper;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class UploadTask extends AsyncTask<String, Void, Void> {

	@Override
	protected Void doInBackground(String... params) {
		String filename = params[0];
		try {
			Looper.prepare();
			Socket socket = new Socket(Globals.SERVER_IP, Globals.SERVER_SOCKET_PORT);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write((Globals.UPLOAD_TO_SERVER_CMD_PREFIX + Globals.CMD_SEPARATOR
							+ new File("/sdcard/" + filename).length()
							+ Globals.CMD_SEPARATOR + MainActivity.uname).getBytes());
			out.flush();
			byte[] b = new byte[10];
			in.read(b);
			FileInputStream fin = new FileInputStream("/sdcard/"
					+ filename);
			int len = 0;
			byte[] b1 = new byte[1024];
			while ((len = fin.read(b1)) != -1) {
				out.write(b1, 0, len);
				out.flush();
			}
			fin.close();
			socket.close();

		} catch (Exception e) {
			// TODO: handle exception
		}	
		return null;
	}

}
