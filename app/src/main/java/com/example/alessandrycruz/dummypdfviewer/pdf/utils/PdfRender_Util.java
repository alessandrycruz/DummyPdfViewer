package com.example.alessandrycruz.dummypdfviewer.pdf.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by alessandry.cruz on 7/5/2017.
 */

public class PdfRender_Util {
    private static final int BITMAP_TARGET_RESOLUTION_IN_DPI = 72;
    private static final int BIT_MAP_SCALE = 2;
    private static final int ONE_MB = 1024;
    private static final int ZERO = 0;

    private static final String TAG = PdfRender_Util.class.getSimpleName();
    private static final String ASSETS_HELP_FOLDER = "help";
    private static final String SLASH = "/";

    public PdfRenderer initializePdfFile(Context context, String pdfFileName) {
        try {
            InputStream inputStream = context.getAssets().open(ASSETS_HELP_FOLDER + SLASH +
                    pdfFileName);
            File pdfFile = createFileFromInputStream(context, pdfFileName, inputStream);

            return getPdfRendererFromFile(pdfFile);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }

        return null;
    }

    public int getPdfPageCount(PdfRenderer pdfRenderer) {
        return pdfRenderer != null ? pdfRenderer.getPageCount() : ZERO;
    }

    public Bitmap pdfToBitmap(Context context, PdfRenderer pdfRenderer, int pdfPage) {
        try {
            Bitmap bitmap;

            PdfRenderer.Page page = pdfRenderer.openPage(pdfPage);

            int width = context.getResources().getDisplayMetrics().
                    densityDpi / BITMAP_TARGET_RESOLUTION_IN_DPI *
                    (page.getWidth() / BIT_MAP_SCALE);
            int height = context.getResources().getDisplayMetrics().
                    densityDpi / BITMAP_TARGET_RESOLUTION_IN_DPI *
                    (page.getHeight() / BIT_MAP_SCALE);

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            // Close the page
            page.close();

            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);
        }

        return null;
    }

    public void closePdfRenderer(PdfRenderer pdfRenderer) {
        if (pdfRenderer != null) {
            pdfRenderer.close();
        }
    }

    private File createFileFromInputStream(Context context, String fileName,
                                           InputStream inputStream) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            OutputStream outputStream = new FileOutputStream(file);

            byte buffer[] = new byte[ONE_MB];
            int length;

            while ((length = inputStream.read(buffer)) > ZERO) {
                outputStream.write(buffer, ZERO, length);
            }

            outputStream.close();
            inputStream.close();

            return file;
        } catch (IOException e) {
            Log.e(TAG, "Exception: " + e);
        }

        return null;
    }

    private PdfRenderer getPdfRendererFromFile(File pdfFile) {
        try {
            return new PdfRenderer(ParcelFileDescriptor.open(pdfFile,
                    ParcelFileDescriptor.MODE_READ_ONLY));
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e);

            return null;
        }
    }
}