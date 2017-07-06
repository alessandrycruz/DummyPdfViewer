package com.example.alessandrycruz.dummypdfviewer.pdf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.alessandrycruz.dummypdfviewer.R;
import com.example.alessandrycruz.dummypdfviewer.pdf.listeners.Pdf_ConvertPdfPageToBitmap_Listener;
import com.example.alessandrycruz.dummypdfviewer.pdf.listeners.Pdf_CreateFileInputStream_Listener;
import com.example.alessandrycruz.dummypdfviewer.pdf.tasks.Pdf_Taskset;
import com.example.alessandrycruz.dummypdfviewer.pdf.utils.Pdf_Util;

/**
 * Created by alessandry.cruz on 7/5/2017.
 */

public class PdfActivity extends Activity {
    public static final String PDF_ACTIVITY_EXTRAS_KEY = "pdf_activity_extras_key";

    private static int ZERO = 0;
    private static int ONE = 1;
    private static int TWO = 2;

    private int mPdfPageCount;
    private int mCurrentPdfPage;

    private Button mButtonPrevious;
    private Button mButtonNext;
    private Context mContext;
    private ImageView mImageViewPdf;
    private Pdf_Util mPdf_Util;
    private PdfRenderer mPdfRenderer;
    private String mPdfFileName;
    private Pdf_Taskset mPdf_Taskset;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        initializeGlobalMembers();
        getIntentExtras();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initializePdf();
    }

    @Override
    protected void onPause() {
        super.onPause();

        destroyPdf();
    }

    public void onClickPrevious(View view) {
        goToPreviousPage();
    }

    public void onClickNext(View view) {
        goToNextPage();
    }

    private void initializeGlobalMembers() {
        mButtonPrevious = findViewById(R.id.button_previous_pdf);
        mButtonNext = findViewById(R.id.button_next_pdf);
        mContext = getApplicationContext();
        mPdf_Util = new Pdf_Util();
        mImageViewPdf = findViewById(R.id.image_view_pdf);
        mPdf_Taskset = new Pdf_Taskset();
        mProgressBar = findViewById(R.id.progress_bar_pdf);
    }

    private void getIntentExtras() {
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(PDF_ACTIVITY_EXTRAS_KEY)) {
            mPdfFileName = intent.getStringExtra(PDF_ACTIVITY_EXTRAS_KEY);
        }
    }

    private void goToPreviousPage() {
        if (mCurrentPdfPage <= ZERO) {
            mCurrentPdfPage = ZERO;
        } else {
            mCurrentPdfPage--;

            if (mCurrentPdfPage == ZERO) {
                mButtonPrevious.setTextColor(getResources().getColor(android.R.color.darker_gray));
                mButtonNext.setTextColor(getResources().getColor(android.R.color.black));
            } else if (mCurrentPdfPage == ONE) {
                mButtonPrevious.setTextColor(getResources().getColor(android.R.color.black));
                mButtonNext.setTextColor(getResources().getColor(android.R.color.black));
            } else if (mCurrentPdfPage == (mPdfPageCount - TWO)) {
                mButtonPrevious.setTextColor(getResources().getColor(android.R.color.black));
                mButtonNext.setTextColor(getResources().getColor(android.R.color.black));
            }

            loadPdfPage();
        }
    }

    private void goToNextPage() {
        if (mCurrentPdfPage >= (mPdfPageCount - ONE)) {
            mCurrentPdfPage = mPdfPageCount - ONE;
        } else {
            mCurrentPdfPage++;

            if (mCurrentPdfPage == 1) {
                mButtonPrevious.setTextColor(getResources().getColor(android.R.color.black));
                mButtonNext.setTextColor(getResources().getColor(android.R.color.black));
            } else if (mCurrentPdfPage == (mPdfPageCount - ONE)) {
                mButtonPrevious.setTextColor(getResources().getColor(android.R.color.black));
                mButtonNext.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }

            loadPdfPage();
        }
    }

    private void initializePdf() {
        mProgressBar.setVisibility(View.VISIBLE);

        mPdf_Taskset.executePdf_CreateFileInputStream_Task(mContext, mPdfFileName,
                new Pdf_CreateFileInputStream_Listener() {
                    @Override
                    public void onSuccess(PdfRenderer pdfRenderer) {
                        mProgressBar.setVisibility(View.GONE);

                        mPdfRenderer = pdfRenderer;

                        mPdfPageCount = mPdf_Util.getPdfPageCount(mPdfRenderer);

                        loadPdfPage();
                    }

                    @Override
                    public void onFail() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void loadPdfPage() {
        mProgressBar.setVisibility(View.VISIBLE);

        mPdf_Taskset.executePdf_ConvertPdfPageToBitmap(mContext, mPdfRenderer,
                mCurrentPdfPage, new Pdf_ConvertPdfPageToBitmap_Listener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        mProgressBar.setVisibility(View.GONE);

                        mImageViewPdf.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFail() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void destroyPdf() {
        mPdf_Util.closePdfRenderer(mPdfRenderer);
    }
}