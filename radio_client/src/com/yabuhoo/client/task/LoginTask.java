package com.yabuhoo.client.task;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.SlidingDrawer;
import android.widget.Toast;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class LoginTask extends AsyncTask<String, Integer, String> {

	private Context context;
	
	public LoginTask(Context context) {
		super();
		this.context = context;
	}
	
	String result = "";
	
	/**
	 * 这里的Integer参数对应AsyncTask中的第一个参数 
	 * 这里的String返回值对应AsyncTask的第三个参数
	 * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改
	 * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作
	 */
	
	
	@SuppressLint("ShowToast")
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			Looper.prepare();
			Socket socket  =  new Socket("10.21.17.119", 8081);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write(params[0].getBytes());
			System.out.println(params[0]);
			out.flush();
			byte[] b = new byte[1024];
			in.read(b);
			socket.close();
			result = new String(b);
		} catch (Exception e) {
			result = "exception";
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result; 
	}
	
	/**
	 * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）
	 * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置
	 */
	@Override
	protected void onPostExecute(String result) {
	}


	//该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
	@Override
	protected void onPreExecute() {
	}


	/**
	 * 这里的Intege参数对应AsyncTask中的第二个参数
	 * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行
	 * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		int vlaue = values[0];
		//progressBar.setProgress(vlaue);
	}

}
