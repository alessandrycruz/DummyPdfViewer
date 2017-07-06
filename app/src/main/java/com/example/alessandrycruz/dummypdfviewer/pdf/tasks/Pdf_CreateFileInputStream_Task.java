package com.example.alessandrycruz.dummypdfviewer.pdf.tasks;

import android.content.Context;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;

import com.example.alessandrycruz.dummypdfviewer.pdf.listeners.Pdf_CreateFileInputStream_Listener;
import com.example.alessandrycruz.dummypdfviewer.pdf.utils.PdfRender_Util;

/**
 * Created by alessandry.cruz on 7/6/2017.
 */

public class Pdf_CreateFileInputStream_Task extends AsyncTask<Void, Void, PdfRenderer> {
    private Context mContext;
    private Pdf_CreateFileInputStream_Listener mPdf_createFileInputStream_Listener;
    private PdfRender_Util mPdfRender_Util;
    private String mPdfFileName;

    public Pdf_CreateFileInputStream_Task(Context context, String pdfFileName,
                                          Pdf_CreateFileInputStream_Listener
                                                  pdf_createFileInputStream_Listener) {
        mContext = context;
        mPdf_createFileInputStream_Listener = pdf_createFileInputStream_Listener;
        mPdfFileName = pdfFileName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mPdfRender_Util = new PdfRender_Util();
    }

    @Override
    protected PdfRenderer doInBackground(Void... voids) {
        return mPdfRender_Util.initializePdfFile(mContext, mPdfFileName);
    }

    @Override
    protected void onPostExecute(PdfRenderer pdfRenderer) {
        super.onPostExecute(pdfRenderer);

        if (pdfRenderer != null) {
            mPdf_createFileInputStream_Listener.onSuccess(pdfRenderer);
        } else {
            mPdf_createFileInputStream_Listener.onFail();
        }
    }
}
