package com.project.laborientation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfActivity extends AppCompatActivity {
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfviewer);
        PDFView pdfView = findViewById(R.id.pdfView);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra(CameraActivity.EXTRA_CATEGORY_Name);

        // in the dir "src/main/assets/" put the pdf file and change the name below
        pdfView.fromAsset(categoryName).load();
    }
}
