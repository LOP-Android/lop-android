package com.example.karen.lop_android;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.preference.DialogPreference;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by David L. Ramirez on 3/5/2015.
 */
public class WebManager {
    private WebView webView;
    private WebSettings webSettings;
    private Button back;
    private Button forward;
    private Button refresh;
    private Drawable buttonStateDraw;
    private String fullPath;
    private Context baseContext;

    private LinearLayout overallLayout;
    private LinearLayout buttonLayout;
    private LinearLayout webLayout;

    private StringBuffer score;


    public WebManager(String path, Context context){
        fullPath = path;
        baseContext = context;
        initComponents();
        initSettings();
        Log.e("WEBVIEW","Web View PASSED");

        //setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initComponents(){
        webView =  new WebView(baseContext);
        back = new Button(baseContext);
        forward = new Button(baseContext);
        refresh = new Button(baseContext);
        score = new StringBuffer();
        webSettings = webView.getSettings();

        // Button Layout
        back.setText("<<");

        forward.setText(">>");
        refresh.setText("REFRESH");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goForward(v);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage(v);
            }
        });

        buttonLayout = new LinearLayout(baseContext);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        back.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.00f));
        forward.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.00f));
        refresh.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.00f));

        buttonLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        buttonLayout.addView(back);
        buttonLayout.addView(refresh);
        buttonLayout.addView(forward);

        // Overall Layout
        overallLayout = new LinearLayout(baseContext);
        overallLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        overallLayout.setOrientation(LinearLayout.VERTICAL);
        overallLayout.addView(buttonLayout);

        webLayout = new LinearLayout(baseContext);
        webLayout.addView(webView);
        webLayout.setOrientation(LinearLayout.HORIZONTAL);
        overallLayout.addView(webLayout);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setButtonStates(Drawable buttonStatesRes1, Drawable buttonStatesRes2, Drawable buttonStatesRes3){
        back.setBackground(buttonStatesRes1);
        refresh.setBackground(buttonStatesRes2);
        forward.setBackground(buttonStatesRes3);
    }

    public WebView getWebView(){
        return webView;
    }

    public LinearLayout getInstance(){
        LinearLayout layout = new LinearLayout(baseContext);
        layout.addView(overallLayout);
        return layout;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) baseContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void goBack(View view){
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            Toast.makeText(baseContext.getApplicationContext(),"Can't go back!",Toast.LENGTH_SHORT).show();
        }

    }

    public void goForward(View view){
        if(webView.canGoForward()){
            webView.goForward();
        }else{
            Toast.makeText(baseContext.getApplicationContext(),"Can't go forward!",Toast.LENGTH_SHORT).show();
        }

    }

    private void initSettings(){
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollbarFadingEnabled(false);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(baseContext.getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                initComponents();

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(baseContext, "hoy", Toast.LENGTH_SHORT).show();
               if (url.contains("score.txt/")) {
                   StringBuffer sb = new StringBuffer();

                   int i = url.indexOf("score.txt/");
                   while(url.charAt(i++) != '/');

                   while(i < url.length()) {
                       sb.append(url.charAt(i));
                        i++;
                   }

                   try {
                       File file = new File(fullPath);
                       String path = file.getParent();
                       int start = path.indexOf(Environment.getExternalStorageDirectory().toString());
                       StringBuffer validPath = new StringBuffer();

                       for(int c = start;c < path.length();c++)
                           validPath.append(path.charAt(c));

                       Log.e("WEBVIEW","Web View shouldUrlOverload PASSED");
                       FileWriter write = new FileWriter(validPath.toString()+"/assets/js/quizScore.txt");

                       write.write(sb.toString());
                       write.close();

                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                }

                // if there is a link to anything else then load the URL in the webview
                else view.loadUrl(url);
                return true;
            }
        });

        try {
            webView.loadUrl(fullPath);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private String getPackageName() {
        return baseContext.getPackageName();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void refreshPage(View view){
        webView.reload();
    }

}
