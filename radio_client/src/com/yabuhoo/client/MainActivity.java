package com.yabuhoo.client;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import com.yabuhoo.client.R;
import com.yabuhoo.client.task.LoginTask;
import com.yabuhoo.config.Globals;
import com.yabuhoo.utils.CommandBuilder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * 
 * @author Jeffy Wong
 *
 */
@SuppressLint("ShowToast")
public class MainActivity extends Activity {

	Button loginButton = null;
	Button exitButton = null;
	EditText usernameEditText = null;
	EditText passwordEditText = null;
	
	public static String uname ="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginButton = (Button) findViewById(R.id.login_button);
		exitButton = (Button) findViewById(R.id.exit_button);
		usernameEditText = (EditText) findViewById(R.id.username_input);
		passwordEditText = (EditText) findViewById(R.id.password_input);
		
	}

	
	public void onExitClick(View view) {
		System.exit(0);
	}
	
	public void onLoginClick(View view) {
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		uname = username;
		
		String cmd = CommandBuilder.get(Globals.LOGIN_CMD_PREFIX, username, password);
		//String cmd  = Globals.LOGIN_CMD_PREFIX + Globals.CMD_SEPARATOR + username + "," + password;
		String result = null;
		try {
			result = new LoginTask(this).execute(cmd).get();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		if(new String(result).trim().equalsIgnoreCase("ok")) {
			Toast.makeText(this, "µÇÂ¼³É¹¦£¡", 1000).show();
			Intent intent = new Intent();
			intent.setClass(this, MessageActivity.class);
			startActivity(intent);
			
		}  else if(result.trim().equalsIgnoreCase("exception")){
			Toast.makeText(this, "ÍøÂçÒì³££¡", 1000).show();
		}  else {
			Toast.makeText(this, "ÓÃ»§Ãû»òÃÜÂë´íÎó£¡", 1000).show();
		}

		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

/*	
	@SuppressLint("ShowToast")
	private class LoginTask extends AsyncTask<String, Integer, String> {

		String result = "";
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
				if(new String(b).trim().equalsIgnoreCase("ok")) {
					Toast.makeText(getBaseContext(), "µÇÂ¼³É¹¦£¡", 1000).show();
					System.out.println( "µÇÂ¼³É¹¦£¡");
					
					Intent intent = new Intent();
					intent.setClass(getBaseContext(), MessageActivity.class);
					
					startActivity(intent);
					
					result = "µÇÂ¼³É¹¦£¡";
				} else {
					Toast.makeText(getBaseContext(), "ÓÃ»§Ãû»òÃÜÂë´íÎó£¡", 1000).show();
					System.out.println( "ÓÃ»§Ãû»òÃÜÂë´íÎó£¡");
					result = "ÓÃ»§Ãû»òÃÜÂë´íÎó£¡";
				}
				socket.close();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), "ÍøÂçÒì³££¡", 1000).show();
				System.out.println( "ÍøÂçÒì³££¡");
				result =  "ÍøÂçÒì³££¡";
				e.printStackTrace();
			}
			return result; 
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			Toast.makeText(getBaseContext(), result, 1000).show();
			super.onPostExecute(result);
		}
			
	}
	*/
}
