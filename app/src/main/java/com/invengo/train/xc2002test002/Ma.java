package com.invengo.train.xc2002test002;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.invengo.train.xc2002test002.entity.FlLight;
import com.invengo.train.xc2002test002.entity.Web;
import com.invengo.train.xc2002test002.enums.EmUh;
import com.invengo.train.xc2002test002.enums.EmUrl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.invengo.train.xc2002test002.enums.UtStrMegEm.meg;

// 循环测试
public class Ma extends AppCompatActivity {
	public static final String sdDir = "Invengo/Train/xc2002test002/";	// SD卡路径

	private Web w = new Web();	// 读写器
	private WebView wv;
	private Handler uh = new com.invengo.train.xc2002test002.Ma.UiHandler();

	// 时间
	private SimpleDateFormat timFmtSql = new SimpleDateFormat("yyyyMMddHHmmss");	// 数据库时间格式
	private SimpleDateFormat timFmtView = new SimpleDateFormat("yyyy-M-d HH:mm");	// 界面时间格式

	// 手电筒
	public FlLight fl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD, WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD); // 全屏、不锁屏
		setContentView(R.layout.activity_ma);

		// 时间
		IntentFilter f = new IntentFilter();
		f.addAction("com.invengo.train.xc2002test002.timrcv");

		// 手电筒
		fl = new FlLight(this);

		// 浏览器
		wv = (WebView)findViewById(R.id.wv);
		WebSettings ws = wv.getSettings();
		ws.setDefaultTextEncodingName("UTF-8");
		ws.setJavaScriptEnabled(true);
		wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		wv.addJavascriptInterface(w, "rfdo");

		sendUrl(EmUrl.Read);

		// 读写器
		w.init(this);

	}

	public String getTim () {
		return timFmtSql.format(Calendar.getInstance().getTime());
	}

	@Override
	protected void onResume() {
		w.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		w.close();
		super.onPause();
		this.finish();
	}

	// 页面跳转
	public void sendUrl (EmUrl e, String... args) {
		uh.sendMessage(uh.obtainMessage(EmUh.Url.ordinal(), meg(e.toString(), args)));
	}

	// 发送页面处理消息
	public void sendUh (EmUh e) {
		uh.sendMessage(uh.obtainMessage(e.ordinal()));
	}

	// 页面处理器
	private class UiHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			EmUh e = EmUh.values()[msg.what];
			switch (e) {
				case Url:
					wv.loadUrl((String)msg.obj);
					break;
			}
		}
	}

}
