package com.yabuhoo.client;

import java.util.Date;

import com.yabuhoo.client.R;
import com.yabuhoo.client.task.UploadTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Jeffy Wong
 *
 */
public class MessageActivity extends Activity {
	
	LinearLayout line  = null;
	MediaRecorder recorder;

	Handler han = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			

			LinearLayout client = new LinearLayout(MessageActivity.this);
			TextView t = new TextView(MessageActivity.this);
			t.setText(msg.getData().getString("username") + "˵:");
			Button button = new Button(MessageActivity.this);
			button.setText("����");
			final String filepath = msg.getData().getString("filename");
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MediaPlayer mediaPlayer = new MediaPlayer();
					if (mediaPlayer.isPlaying()) {
						mediaPlayer.reset();// ����Ϊ��ʼ״̬
					}
					try {
						mediaPlayer.setDataSource("/sdcard/" + filepath);
						mediaPlayer.prepare();// ����
						mediaPlayer.start();// ��ʼ��ָ�����
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			});
			client.setOrientation(LinearLayout.HORIZONTAL);
			client.addView(t);
			client.addView(button);

			line.addView(client);
			
			super.handleMessage(msg);
		}
		
	};
	
	class PlayTask extends AsyncTask<String, Integer, String> {
		@SuppressLint({ "ShowToast", "SdCardPath" })
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			MediaPlayer mediaPlayer = new MediaPlayer();
			if(mediaPlayer.isPlaying()){
				mediaPlayer.reset();
				
			}
			try {
				mediaPlayer.setDataSource("/sdcard/" + params[0]);
				mediaPlayer.prepare();							
			} catch (Exception e) {
				Toast.makeText(MessageActivity.this, "���ų���", 1000).show();
			}
			mediaPlayer.start();
			return null;
		}
		
	}
	
	class SendButtonOnTouchListener implements OnTouchListener {

		String filename = "";
		
		//TODO ���ﻹ����һ�����⣬����SD������Ҫ�У�û�еĻ��ᱨstart called in an invalid state: 4
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == 0) {
				recorder = new MediaRecorder();
				filename = new Date().getTime() + ".amr";
				recorder.reset();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				// ����˷�ɼ�����
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				// ���������ʽ
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				// ��Ƶ���뷽ʽ

				recorder.setOutputFile("/sdcard/" + filename);// ��ס��SDȨ��
				try {
					recorder.prepare();
				} catch (Exception e) {
					Toast.makeText(MessageActivity.this, "������һ��!", 1000).show();
				}// Ԥ��׼��
				recorder.start(); // ��ʼ��¼
			} else if (event.getAction() == 1) {
				recorder.stop();// ֹͣ��¼
				recorder.reset(); // ����


				LinearLayout client = new LinearLayout(MessageActivity.this);
				TextView t = new TextView(MessageActivity.this);
				t.setText("��˵:");
				Button button = new Button(MessageActivity.this);
				button.setText("����");
				final String filepath = filename;
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						MediaPlayer mediaPlayer = new MediaPlayer();
						if (mediaPlayer.isPlaying()) {
							mediaPlayer.reset();// ����Ϊ��ʼ״̬
						}
						try {
							mediaPlayer.setDataSource("/sdcard/" + filepath);
							mediaPlayer.prepare();// ����
							mediaPlayer.start();// ��ʼ��ָ�����
						} catch (Exception e) {
							// TODO: handle exception
						}

					}
				});
				client.setOrientation(LinearLayout.HORIZONTAL);
				client.addView(t);
				client.addView(button);

				line.addView(client);
				new UploadTask().execute(filename);
			}
			return false;
		}
		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		Button sendButton = (Button) findViewById(R.id.sendButton);
		sendButton.setOnTouchListener(new SendButtonOnTouchListener());
		line = (LinearLayout) findViewById(R.id.list_linearLayout);
		
		new Thread() {
			public void run() {
				
				UDPServer.messageActivity = MessageActivity.this;
				try {
					UDPServer.openServer();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}.start();
	}

	int count  = 0;
	@SuppressWarnings("static-access")
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(count > 0)
			System.exit(0);
		
		if(event.KEYCODE_BACK == keyCode) {
			
			Toast.makeText(this, "���б����ٰ�һ��", 1000).show();
			count++;
			return false;
			
		}
		
		return super.onKeyUp(keyCode, event);
	}

	
	
	
}
