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
	 * �����Integer������ӦAsyncTask�еĵ�һ������ 
	 * �����String����ֵ��ӦAsyncTask�ĵ���������
	 * �÷�������������UI�̵߳��У���Ҫ�����첽�����������ڸ÷����в��ܶ�UI���еĿռ�������ú��޸�
	 * ���ǿ��Ե���publishProgress��������onProgressUpdate��UI���в���
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
	 * �����String������ӦAsyncTask�еĵ�����������Ҳ���ǽ���doInBackground�ķ���ֵ��
	 * ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��� ���Զ�UI�ռ��������
	 */
	@Override
	protected void onPostExecute(String result) {
	}


	//�÷���������UI�̵߳���,����������UI�̵߳��� ���Զ�UI�ռ��������
	@Override
	protected void onPreExecute() {
	}


	/**
	 * �����Intege������ӦAsyncTask�еĵڶ�������
	 * ��doInBackground�������У���ÿ�ε���publishProgress�������ᴥ��onProgressUpdateִ��
	 * onProgressUpdate����UI�߳���ִ�У����п��Զ�UI�ռ���в���
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		int vlaue = values[0];
		//progressBar.setProgress(vlaue);
	}

}
