package com.example.alessandrycruz.dummypdfviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.alessandrycruz.dummypdfviewer.pdf.PdfActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String pdfFileName = getResources().getString(R.string.pdf_help_document);

        startActivity(new Intent(getApplicationContext(),
                PdfActivity.class).putExtra(PdfActivity.PDF_ACTIVITY_EXTRAS_KEY, pdfFileName));
    }
}