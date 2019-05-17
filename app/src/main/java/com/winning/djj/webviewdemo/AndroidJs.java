package com.winning.djj.webviewdemo;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by djj on 2019/4/18.
 */

public class AndroidJs {

    private Context mContext;

    public AndroidJs(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void showList() {
        new AlertDialog.Builder(mContext)
                .setTitle("图书列表")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(
                        new String[]{"疯狂java讲义", "疯狂Android讲义",
                                "轻量级java EE开发"}, null)
                .setPositiveButton("确定", null).create().show();
    }

    @JavascriptInterface
    public void showToast() {

        Toast.makeText(mContext, "hello", Toast.LENGTH_LONG).show();
    }

}
