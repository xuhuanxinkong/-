package com.example.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment {

    WebView webView;
    int position;

    public static WebFragment newInstance(int position) {
        WebFragment fragment = new WebFragment();
        fragment.position=position;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_web,container,false);
        webView= view.findViewById(R.id.id_localWeb1);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        switch(position){
            case 0:webView.loadUrl("file:///android_asset/webview/trys.html");break;
            case 1:webView.loadUrl("file:///android_asset/webview/kaituozhe.html");break;
            case 2:webView.loadUrl("https://www.baidu.com/");break;
            default:webView.loadUrl("file:///android_asset/webview/trys.html");break;
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
//                view.loadUrl("file:///android_asset/webview/trys.html");
                view.loadUrl(request.getUrl().toString());
                return false;
            }

        });
        webView.setWebChromeClient(new WebChromeClient());
        return view; //inflater.inflate(R.layout.fragment_web, container, false);
    }
            @Override
            public void onDestroyView(){
                super.onDestroyView();
                if(webView!=null){
                    webView.clearHistory();
                    ((ViewGroup)webView.getParent()).removeView(webView);
                    webView.destroy();
                    webView=null;
                }
            }
}