package com.example.alessandrycruz.dummypdfviewer.pdf.listeners;

import android.graphics.Bitmap;

/**
 * Created by alessandry.cruz on 7/6/2017.
 */

public interface Pdf_ConvertPdfPageToBitmap_Listener {
    void onSuccess(Bitmap bitmap);

    void onFail();
}