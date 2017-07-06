package com.example.alessandrycruz.dummypdfviewer.pdf.tasks;

import android.content.Context;
import android.graphics.pdf.PdfRenderer;

import com.example.alessandrycruz.dummypdfviewer.pdf.listeners.Pdf_ConvertPdfPageToBitmap_Listener;
import com.example.alessandrycruz.dummypdfviewer.pdf.listeners.Pdf_CreateFileInputStream_Listener;

/**
 * Created by alessandry.cruz on 7/6/2017.
 */

public class Pdf_Taskset {
    public void executePdf_CreateFileInputStream_Task(Context context, String pdfFileName,
                                                      Pdf_CreateFileInputStream_Listener
                                                              pdf_createFileInputStream_Listener) {
        new Pdf_CreateFileInputStream_Task(context, pdfFileName,
                pdf_createFileInputStream_Listener).execute();
    }

    public void executePdf_ConvertPdfPageToBitmap(Context context, PdfRenderer pdfRenderer,
                                                  int pdfPage, Pdf_ConvertPdfPageToBitmap_Listener
                                                          pdf_convertPdfPageToBitmap_Listener) {
        new Pdf_ConvertPdfPageToBitmap_Task(context, pdfRenderer, pdfPage,
                pdf_convertPdfPageToBitmap_Listener).execute();
    }
}