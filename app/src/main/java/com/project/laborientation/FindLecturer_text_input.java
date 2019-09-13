package com.project.laborientation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindLecturer_text_input extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_lecturer_text_input);
        Button search = findViewById(R.id.search);
        Button imageSearch = findViewById(R.id.image_search);
        EditText staff_name = findViewById(R.id.staff_name);

        imageSearch.setOnClickListener((View view) -> {
            Intent modelPlacementIntent = new Intent(getBaseContext(), FindLecturersActivity.class);
            startActivity(modelPlacementIntent);
        });

        search.setOnClickListener((View view) -> openWebsite(staff_name.getText().toString()));
    }

    private void openWebsite(String name){
        setContentView(R.layout.webview);

        String websiteUrl = "https://unidirectory.auckland.ac.nz/search#?search="+name;
        webView = (WebView) findViewById(R.id.webview);
        Toast.makeText(FindLecturer_text_input.this, websiteUrl, Toast.LENGTH_LONG).show();

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(websiteUrl);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
