package kdmc_kumar.Utilities_Others;

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Surface;

import java.io.Closeable;

import kdmc_kumar.Utilities_Others.MonitoredActivity.LifeCycleAdapter;

/**
 * Collection of utility functions used in this package.
 */
public class Util {

    @SuppressWarnings("unused")
    private static final String TAG = "db.Util";

    private Util() {

    }

	/*
     * Compute the sample size as a function of minSideLength and
	 * maxNumOfPixels. minSideLength is used to specify that minimal width or
	 * height of a bitmap. maxNumOfPixels is used to specify the maximal size in
	 * pixels that are tolerable in terms of memory usage.
	 * 
	 * The function returns a sample size based on the constraints. Both size
	 * and minSideLength can be passed in as IImage.UNCONSTRAINED, which
	 * indicates no care of the corresponding constraint. The functions prefers
	 * returning a sample size that generates a smaller bitmap, unless
	 * minSideLength = IImage.UNCONSTRAINED.
	 */

    public static Bitmap transform(@Nullable Matrix scaler, Bitmap source,
                                   int targetWidth, int targetHeight, boolean scaleUp) {

        Matrix scaler1 = scaler;
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
            Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
                    Config.ARGB_8888);
            Canvas c = new Canvas(b2);

            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
                    + Math.min(targetWidth, source.getWidth()), deltaYHalf
                    + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
                    - dstY);
            c.drawBitmap(source, src, dst, null);
            return b2;
        }
        float bitmapWidthF = (float) source.getWidth();
        float bitmapHeightF = (float) source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / (float) targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = (float) targetHeight / bitmapHeightF;
            if (scale < 0.9F || scale > 1.0F) {
                scaler1.setScale(scale, scale);
            } else {
                scaler1 = null;
            }
        } else {
            float scale = (float) targetWidth / bitmapWidthF;
            if (scale < 0.9F || scale > 1.0F) {
                scaler1.setScale(scale, scale);
            } else {
                scaler1 = null;
            }
        }

        Bitmap b1;
        b1 = scaler1 != null ? Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), scaler1, true) : source;

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
                targetHeight);

        if (b1 != source) {
            b1.recycle();
        }

        return b2;
    }

    public static void closeSilently(Closeable c) {

        if (c == null)
            return;
        try {
            c.close();
        } catch (Throwable ignored) {
            // do nothing
        }
    }

    public static void startBackgroundJob(MonitoredActivity activity,
                                          String title, String message, Runnable job, Handler handler) {
        // Make the progress dialog uncancelable, so that we can gurantee
        // the thread will be done before the activity getting destroyed.
        ProgressDialog dialog = ProgressDialog.show(activity, title, message,
                true, false);
        new Thread(new Util.BackgroundJob(activity, job, dialog, handler)).start();
    }

    // Returns Options that set the puregeable flag for Bitmap decode.
    public static Options createNativeAllocOptions() {

        // options.inNativeAlloc = true;
        return new Options();
    }

    // Thong added for rotate
    public static Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    public static int getOrientationInDegree(Activity activity) {

        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        return degrees;
    }

    private static class BackgroundJob extends
            LifeCycleAdapter implements Runnable {

        private final MonitoredActivity mActivity;
        private final ProgressDialog mDialog;
        private final Runnable mJob;
        private final Handler mHandler;
        private final Runnable mCleanupRunner = new Runnable() {
            public void run() {

                Util.BackgroundJob.this.mActivity.removeLifeCycleListener(Util.BackgroundJob.this);
                if (Util.BackgroundJob.this.mDialog.getWindow() != null)
                    Util.BackgroundJob.this.mDialog.dismiss();
            }
        };

        BackgroundJob(MonitoredActivity activity, Runnable job,
                      ProgressDialog dialog, Handler handler) {

            this.mActivity = activity;
            this.mDialog = dialog;
            this.mJob = job;
            this.mActivity.addLifeCycleListener(this);
            this.mHandler = handler;
        }

        public final void run() {

            try {
                this.mJob.run();
            } finally {
                this.mHandler.post(this.mCleanupRunner);
            }
        }

        @Override
        public final void onActivityDestroyed(MonitoredActivity activity) {
            // We get here only when the onDestroyed being called before
            // the mCleanupRunner. So, run it now and remove it from the queue
            this.mCleanupRunner.run();
            this.mHandler.removeCallbacks(this.mCleanupRunner);
        }

        @Override
        public final void onActivityStopped(MonitoredActivity activity) {

            this.mDialog.hide();
        }

        @Override
        public final void onActivityStarted(MonitoredActivity activity) {

            this.mDialog.show();
        }
    }
}
