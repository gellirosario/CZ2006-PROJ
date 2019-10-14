package com.cz2006.helloworld.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.cz2006.helloworld.R;

public class News_Web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__web);

        final String url = getIntent().getStringExtra("url");


        WebView webView = findViewById(R.id.news_Vewmore_wv);

        webView.loadUrl(url);

    }
}
