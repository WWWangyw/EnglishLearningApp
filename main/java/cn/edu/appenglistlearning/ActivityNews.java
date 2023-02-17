package cn.edu.appenglistlearning;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.Unbinder;

public class ActivityNews extends AppCompatActivity {


    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        unbinder = ButterKnife.bind(this);

        progressbar.setVisibility(View.VISIBLE);
        webview.loadUrl("http://global.chinadaily.com.cn/");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        updateBarHandler.post(updateThread);
    }

    int i = 0;
    Runnable updateThread = new Runnable() {
        @Override
        public void run() {
            i = i + 10;
            Message msg = updateBarHandler.obtainMessage();
            msg.arg1 = i;
            updateBarHandler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler updateBarHandler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.arg1 <= 100) {
                progressbar.setProgress(msg.arg1);
                updateBarHandler.post(updateThread);
            } else {
                i = 0;
                progressbar.setProgress(i);
                progressbar.setVisibility(View.INVISIBLE);
                updateBarHandler.removeMessages(msg.what);
            }
        }
    };

    @SuppressLint("SetJavaScriptEnabled")

    protected void onDestory() {
        super.onDestroy();
        unbinder.unbind();
    }
}
