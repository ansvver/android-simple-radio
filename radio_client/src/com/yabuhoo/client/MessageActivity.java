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
			t.setText(msg.getData().getString("username") + "说:");
			Button button = new Button(MessageActivity.this);
			button.setText("播放");
			final String filepath = msg.getData().getString("filename");
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					MediaPlayer mediaPlayer = new MediaPlayer();
					if (mediaPlayer.isPlaying()) {
						mediaPlayer.reset();// 重置为初始状态
					}
					try {
						mediaPlayer.setDataSource("/sdcard/" + filepath);
						mediaPlayer.prepare();// 缓冲
						mediaPlayer.start();// 开始或恢复播放
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
				Toast.makeText(MessageActivity.this, "播放出错！", 1000).show();
			}
			mediaPlayer.start();
			return null;
		}
		
	}
	
	class SendButtonOnTouchListener implements OnTouchListener {

		String filename = "";
		
		//TODO 这里还存在一点问题，就是SD卡必须要有，没有的话会报start called in an invalid state: 4
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == 0) {
				recorder = new MediaRecorder();
				filename = new Date().getTime() + ".amr";
				recorder.reset();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				// 从麦克风采集声音
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				// 内容输出格式
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				// 音频编码方式

				recorder.setOutputFile("/sdcard/" + filename);// 记住开SD权限
				try {
					recorder.prepare();
				} catch (Exception e) {
					Toast.makeText(MessageActivity.this, "请在试一次!", 1000).show();
				}// 预期准备
				recorder.start(); // 开始刻录
			} else if (event.getAction() == 1) {
				recorder.stop();// 停止刻录
				recorder.reset(); // 重设


				LinearLayout client = new LinearLayout(MessageActivity.this);
				TextView t = new TextView(MessageActivity.this);
				t.setText("我说:");
				Button button = new Button(MessageActivity.this);
				button.setText("播放");
				final String filepath = filename;
				button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						MediaPlayer mediaPlayer = new MediaPlayer();
						if (mediaPlayer.isPlaying()) {
							mediaPlayer.reset();// 重置为初始状态
						}
						try {
							mediaPlayer.setDataSource("/sdcard/" + filepath);
							mediaPlayer.prepare();// 缓冲
							mediaPlayer.start();// 开始或恢复播放
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
			
			Toast.makeText(this, "你有本事再按一次", 1000).show();
			count++;
			return false;
			
		}
		
		return super.onKeyUp(keyCode, event);
	}

	
	
	
}
