package com.example.alessandrycruz.dummypdfviewer.pdf.listeners;

import android.graphics.pdf.PdfRenderer;

/**
 * Created by alessandry.cruz on 7/6/2017.
 */

public interface Pdf_CreateFileInputStream_Listener {
    void onSuccess(PdfRenderer pdfRenderer);

    void onFail();
}