package com.atiruz.pdfviewer;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * PDFViewerManager
 */
class PDFViewerManager {

    boolean closed = false;
    PDFView pdfView;
    Activity activity;

    PDFViewerManager(final Activity activity) {
        this.pdfView = new PDFView(activity, null);
        this.activity = activity;
    }

    void openPDF(String path) {
        File file = new File(path);
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();
    }

    void resize(FrameLayout.LayoutParams params) {
        pdfView.setLayoutParams(params);
    }

    void close(MethodCall call, MethodChannel.Result result) {
        if (pdfView != null) {
            ViewGroup vg = (ViewGroup) (pdfView.getParent());
            vg.removeView(pdfView);
        }
        pdfView = null;
        if (result != null) {
            result.success(null);
        }

        closed = true;
        PDFViewerPlugin.channel.invokeMethod("onDestroy", null);
    }

    void close() {
        close(null, null);
    }
}