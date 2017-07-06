package com.example.alessandrycruz.dummypdfviewer.pdf.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.util.Log;

import com.example.alessandrycruz.dummypdfviewer.pdf.listeners.Pdf_ConvertPdfPageToBitmap_Listener;
import com.example.alessandrycruz.dummypdfviewer.pdf.utils.PdfRender_Util;

/**
 * Created by alessandry.cruz on 7/6/2017.
 */

public class Pdf_ConvertPdfPageToBitmap_Task extends AsyncTask<Void, Void, Bitmap> {
    private static final String TAG = Pdf_ConvertPdfPageToBitmap_Task.class.getSimpleName();

    private int mPdfPage;

    private Context mContext;
    private PdfRenderer mPdfRenderer;
    private PdfRender_Util mPdfRender_Util;
    private Pdf_ConvertPdfPageToBitmap_Listener mPdf_convertPdfPageToBitmap_Listener;

    public Pdf_ConvertPdfPageToBitmap_Task(Context context, PdfRenderer pdfRenderer, int pdfPage,
                                           Pdf_ConvertPdfPageToBitmap_Listener
                                                   pdf_convertPdfPageToBitmap_Listener) {
        mPdfPage = pdfPage;
        mContext = context;
        mPdfRenderer = pdfRenderer;
        mPdf_convertPdfPageToBitmap_Listener = pdf_convertPdfPageToBitmap_Listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mPdfRender_Util = new PdfRender_Util();
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            return mPdfRender_Util.pdfToBitmap(mContext, mPdfRenderer, mPdfPage);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null) {
            mPdf_convertPdfPageToBitmap_Listener.onSuccess(bitmap);
        } else {
            mPdf_convertPdfPageToBitmap_Listener.onFail();
        }
    }
}