package com.winning.djj.webviewdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述: 主界面
 * 作者|时间: djj on 2019/4/17 17:27
 * 博客地址: http://www.jianshu.com/u/dfbde65a03fc
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mWebView.loadUrl("file:///android_asset/javascript.html");//加载本地asset下面的js_java_interaction.html文件
        //mWebView.loadUrl("https://www.baidu.com/");//加载本地assets下面的js_java_interaction.html文件

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//打开js支持

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        /**
         * 打开js接口給H5调用，参数1为本地类名，参数2为别名；h5用window.别名.类名里的方法名才能调用方法里面的内容，例如：window.android.back();
         * */
//        mWebView.addJavascriptInterface(new JsInteration(), "android");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    /**
     * 自己写一个类，里面是提供给H5访问的方法
     */
//    public class JsInteration {
//
//        @JavascriptInterface//一定要写，不然H5调不到这个方法
//        public String back() {
//            return "我是java里的方法返回值";
//        }
//    }

    final int version = Build.VERSION.SDK_INT;

    @OnClick(R.id.btn_load)
    public void onViewClicked() {
        //通过handle发送消息
        mWebView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                //        mWebView.loadUrl("JavaScript:show()");//直接访问H5里不带返回值的方法，show()为H5里的方法

                if (version < 18) {
                    //传固定字符串可以直接用单引号括起来
                    mWebView.loadUrl("javascript:alertMessage('哈哈')");//访问H5里带参数的方法，alertMessage(message)为H5里的方法

                    //当出入变量名时，需要用转义符隔开
                    String content = "9880";
                    mWebView.loadUrl("javascript:alertMessage(\"" + content + "\")");
                } else {
                    //Android调用有返回值js方法，安卓4.4以上才能用这个方法
                    mWebView.evaluateJavascript("alertMessage('哈哈')", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
                        }
                    });
                    mWebView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Toast.makeText(MainActivity.this, "js返回的结果为=" + value, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }


}
