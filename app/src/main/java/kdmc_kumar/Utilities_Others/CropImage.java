/*
 * Copyright (C) 2007 The Android Open Source Project
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

package kdmc_kumar.Utilities_Others;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.media.FaceDetector;
import android.media.FaceDetector.Face;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Utilities_Others.BitmapManager.ThreadSet;

public class CropImage extends MonitoredActivity {

    public static final String IMAGE_PATH = "image-path";
    public static final String SCALE = "scale";
    private static final String ORIENTATION_IN_DEGREES = "orientation_in_degrees";
    public static final String ASPECT_X = "aspectX";
    public static final String ASPECT_Y = "aspectY";
    public static final String OUTPUT_X = "outputX";
    public static final String OUTPUT_Y = "outputY";
    private static final String SCALE_UP_IF_NEEDED = "scaleUpIfNeeded";
    private static final String CIRCLE_CROP = "circleCrop";
    private static final String RETURN_DATA = "return-data";
    private static final String RETURN_DATA_AS_BITMAP = "data";
    private static final String ACTION_INLINE_DATA = "inline-data";
    private static final int NO_STORAGE_ERROR = -1;
    private static final int CANNOT_STAT_ERROR = -2;
    private static final String TAG = "CropImage";
    private static Bitmap mBitmap;
    private static final int IMAGE_MAX_SIZE = 1024;
    private final Handler mHandler = new Handler();
    private final ThreadSet mDecodingThreads = new ThreadSet();
    boolean mWaitingToPick; // Whether we are wait the user to pick a face.
    boolean mSaving; // Whether the "save" button is already clicked.
    HighlightView mCrop;
    // These are various options can be specified in the intent.
    private final CompressFormat mOutputFormat = CompressFormat.JPEG;
    private Uri mSaveUri;
    private final boolean mDoFaceDetection = true;
    private boolean mCircleCrop;
    private int mAspectX;
    private int mAspectY;
    private int mOutputX;
    private int mOutputY;
    private boolean mScale;
    private CropImageView mImageView;
    private final Runnable mRunFaceDetection = new Runnable() {
        float mScale = 1.0F;
        Matrix mImageMatrix;
        final Face[] mFaces = new Face[3];
        int mNumFaces;

        // For each face, we create a HightlightView for it.
        private void handleFace(Face f) {

            PointF midPoint = new PointF();

            int r = ((int) (f.eyesDistance() * this.mScale)) * 2;
            f.getMidPoint(midPoint);
            midPoint.x *= this.mScale;
            midPoint.y *= this.mScale;

            int midX = (int) midPoint.x;
            int midY = (int) midPoint.y;

            HighlightView hv = new HighlightView(CropImage.this.mImageView);

            int width = CropImage.mBitmap.getWidth();
            int height = CropImage.mBitmap.getHeight();

            Rect imageRect = new Rect(0, 0, width, height);

            RectF faceRect = new RectF((float) midX, (float) midY, (float) midX, (float) midY);
            faceRect.inset((float) -r, (float) -r);
            if (faceRect.left < (float) 0) {
                faceRect.inset(-faceRect.left, -faceRect.left);
            }

            if (faceRect.top < (float) 0) {
                faceRect.inset(-faceRect.top, -faceRect.top);
            }

            if (faceRect.right > (float) imageRect.right) {
                faceRect.inset(faceRect.right - (float) imageRect.right, faceRect.right
                        - (float) imageRect.right);
            }

            if (faceRect.bottom > (float) imageRect.bottom) {
                faceRect.inset(faceRect.bottom - (float) imageRect.bottom,
                        faceRect.bottom - (float) imageRect.bottom);
            }

            hv.setup(this.mImageMatrix, imageRect, faceRect, CropImage.this.mCircleCrop,
                    CropImage.this.mAspectX != 0 && CropImage.this.mAspectY != 0);

            CropImage.this.mImageView.add(hv);
        }

        // Create a default HightlightView if we found no face in the picture.
        private void makeDefault() {

            HighlightView hv = new HighlightView(CropImage.this.mImageView);

            int width = CropImage.mBitmap.getWidth();
            int height = CropImage.mBitmap.getHeight();

            Rect imageRect = new Rect(0, 0, width, height);

            // make the default size about 4/5 of the width or height
            int cropWidth = Math.min(width, height) * 4 / 5;
            int cropHeight = cropWidth;

            if (CropImage.this.mAspectX != 0 && CropImage.this.mAspectY != 0) {

                if (CropImage.this.mAspectX > CropImage.this.mAspectY) {

                    cropHeight = cropWidth * CropImage.this.mAspectY / CropImage.this.mAspectX;
                } else {

                    cropWidth = cropHeight * CropImage.this.mAspectX / CropImage.this.mAspectY;
                }
            }

            int x = (width - cropWidth) / 2;
            int y = (height - cropHeight) / 2;

            RectF cropRect = new RectF((float) x, (float) y, (float) (x + cropWidth), (float) (y + cropHeight));
            hv.setup(this.mImageMatrix, imageRect, cropRect, CropImage.this.mCircleCrop,
                    CropImage.this.mAspectX != 0 && CropImage.this.mAspectY != 0);

            CropImage.this.mImageView.mHighlightViews.clear(); // Thong added for rotate

            CropImage.this.mImageView.add(hv);
        }

        // Scale the image down for faster face detection.
        private Bitmap prepareBitmap() {


            if (CropImage.mBitmap == null) {
                CropImage.mBitmap.recycle();
                return null;
            }

            // 256 pixels wide is enough.
            if (CropImage.mBitmap.getWidth() > 256) {

                this.mScale = 256.0F / (float) CropImage.mBitmap.getWidth();
            }
            Matrix matrix = new Matrix();
            matrix.setScale(this.mScale, this.mScale);


            return Bitmap.createBitmap(CropImage.mBitmap, 0, 0, CropImage.mBitmap.getWidth(),
                    CropImage.mBitmap.getHeight(), matrix, true);
        }


        public void run() {

            this.mImageMatrix = CropImage.this.mImageView.getImageMatrix();
            Bitmap faceBitmap = this.prepareBitmap();

            this.mScale = 1.0F / this.mScale;
            if (faceBitmap != null && CropImage.this.mDoFaceDetection) {
                FaceDetector detector = new FaceDetector(faceBitmap.getWidth(),
                        faceBitmap.getHeight(), this.mFaces.length);
                this.mNumFaces = detector.findFaces(faceBitmap, this.mFaces);
            }

            if (faceBitmap != null && faceBitmap != CropImage.mBitmap) {
                faceBitmap.recycle();
            }

            CropImage.this.mHandler.post(() -> {

                CropImage.this.mWaitingToPick = this.mNumFaces > 1;
                if (this.mNumFaces > 0) {
                    for (int i = 0; i < this.mNumFaces; i++) {
                        this.handleFace(this.mFaces[i]);
                    }
                } else {
                    this.makeDefault();
                }
                CropImage.this.mImageView.invalidate();
                if (CropImage.this.mImageView.mHighlightViews.size() == 1) {
                    CropImage.this.mCrop = CropImage.this.mImageView.mHighlightViews.get(0);
                    CropImage.this.mCrop.setFocus(true);
                }

                if (this.mNumFaces > 1) {
                    Toast.makeText(CropImage.this, "Multi face crop help",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
    private ContentResolver mContentResolver;
    private String mImagePath;
    // These options specifiy the output image size and whether we should
    // scale the output to fit it (or just crop it).
    private boolean mScaleUp = true;

    public CropImage() {
    }

    private static void showStorageToast(Activity activity) {

        CropImage.showStorageToast(activity, CropImage.calculatePicturesRemaining(activity));
    }

    @SuppressLint("ShowToast")
    private static void showStorageToast(Activity activity, int remaining) {

        String noStorageText = null;

        if (remaining == CropImage.NO_STORAGE_ERROR) {

            String state = Environment.getExternalStorageState();
            noStorageText = state.equals(Environment.MEDIA_CHECKING) ? activity.getString(string.preparing_card) : activity.getString(string.no_storage_card);
        } else if (remaining < 1) {

            noStorageText = activity.getString(string.not_enough_space);
        }

        if (noStorageText != null) {

            Toast.makeText(activity, noStorageText, Toast.LENGTH_LONG).show();
        }
    }

    private static int calculatePicturesRemaining(Activity activity) {

        try {
			/*
			 * if (!ImageManager.hasStorage()) { return NO_STORAGE_ERROR; } else
			 * {
			 */
            String storageDirectory = "";
            String state = Environment.getExternalStorageState();
            storageDirectory = Environment.MEDIA_MOUNTED.equals(state) ? Environment.getExternalStorageDirectory()
                    .toString() : activity.getFilesDir().toString();
            StatFs stat = new StatFs(storageDirectory);
            @SuppressWarnings("deprecation")
            float remaining = ((float) stat.getAvailableBlocks() * stat
                    .getBlockSize()) / 400000.0F;
            return (int) remaining;
            // }
        } catch (Exception ignored) {
            // if we can't stat the filesystem then we don't know how many
            // pictures are remaining. it might be zero but just leave it
            // blank since we really don't know.
            return CropImage.CANNOT_STAT_ERROR;
        }
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.mContentResolver = this.getContentResolver();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(layout.cropimage);

        this.mImageView = this.findViewById(id.image);

        CropImage.showStorageToast(this);

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {

            if (extras.getString(CropImage.CIRCLE_CROP) != null) {

                // if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                // mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                // }

                this.mCircleCrop = true;
                this.mAspectX = 1;
                this.mAspectY = 1;
            }

            this.mImagePath = extras.getString(CropImage.IMAGE_PATH);

            this.mSaveUri = CropImage.getImageUri(this.mImagePath);
            CropImage.mBitmap = this.getBitmap(this.mImagePath);

            if (extras.containsKey(CropImage.ASPECT_X)
                    && extras.get(CropImage.ASPECT_X) instanceof Integer) {

                this.mAspectX = extras.getInt(CropImage.ASPECT_X);
            } else {

                throw new IllegalArgumentException("aspect_x must be integer");
            }
            if (extras.containsKey(CropImage.ASPECT_Y)
                    && extras.get(CropImage.ASPECT_Y) instanceof Integer) {

                this.mAspectY = extras.getInt(CropImage.ASPECT_Y);
            } else {

                throw new IllegalArgumentException("aspect_y must be integer");
            }
            this.mOutputX = extras.getInt(CropImage.OUTPUT_X);
            this.mOutputY = extras.getInt(CropImage.OUTPUT_Y);
            this.mScale = extras.getBoolean(CropImage.SCALE, true);
            this.mScaleUp = extras.getBoolean(CropImage.SCALE_UP_IF_NEEDED, true);
        }

        if (CropImage.mBitmap == null) {

            Log.d(CropImage.TAG, "finish!!!");
            this.finish();
            return;
        }

        // Make UI fullscreen.
        this.getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);

        this.findViewById(id.discard).setOnClickListener(
                v -> {

                    this.setResult(Activity.RESULT_CANCELED);
                    this.finish();
                });

        this.findViewById(id.save).setOnClickListener(v -> {

            try {
                this.onSaveClicked();
            } catch (Exception ignored) {
                this.finish();
            }
        });
        this.findViewById(id.rotateLeft).setOnClickListener(
                v -> {

                    CropImage.mBitmap = Util.rotateImage(CropImage.mBitmap, -90.0F);
                    RotateBitmap rotateBitmap = new RotateBitmap(CropImage.mBitmap);
                    this.mImageView.setImageRotateBitmapResetBase(rotateBitmap,
                            true);
                    this.mRunFaceDetection.run();
                });

        this.findViewById(id.rotateRight).setOnClickListener(
                v -> {

                    CropImage.mBitmap = Util.rotateImage(CropImage.mBitmap, 90.0F);
                    RotateBitmap rotateBitmap = new RotateBitmap(CropImage.mBitmap);
                    this.mImageView.setImageRotateBitmapResetBase(rotateBitmap,
                            true);
                    this.mRunFaceDetection.run();
                });
        this.startFaceDetection();
    }

    private static Uri getImageUri(String path) {

        return Uri.fromFile(new File(path));
    }

    private Bitmap getBitmap(String path) {

        Uri uri = CropImage.getImageUri(path);
        InputStream in = null;
        try {
            in = this.mContentResolver.openInputStream(uri);

            // Decode image size
            Options o = new Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > CropImage.IMAGE_MAX_SIZE || o.outWidth > CropImage.IMAGE_MAX_SIZE) {
                scale = (int) StrictMath.pow(
                        2.0,
                        (double) (int) Math.round(StrictMath.log((double) CropImage.IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / StrictMath.log(0.5)));
            }

            Options o2 = new Options();
            o2.inSampleSize = scale;
            in = this.mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } catch (FileNotFoundException ignored) {
            //Log.e(TAG, "file " + path + " not found");
        } catch (IOException ignored) {
            //Log.e(TAG, "file " + path + " not found");
        }
        return null;
    }

    private void startFaceDetection() {

        if (this.isFinishing()) {
            return;
        }

        this.mImageView.setImageBitmapResetBase(CropImage.mBitmap, true);

        Util.startBackgroundJob(this, null, "Please wait\u2026",
                () -> {

                    CountDownLatch latch = new CountDownLatch(1);
                    Bitmap b = CropImage.mBitmap;
                    this.mHandler.post(() -> {

                        if (b != CropImage.mBitmap && b != null) {
                            this.mImageView.setImageBitmapResetBase(b, true);
                            CropImage.mBitmap.recycle();
                            CropImage.mBitmap = b;
                        }
                        if (this.mImageView.getScale() == 1.0F) {
                            this.mImageView.center();
                        }
                        latch.countDown();
                    });
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.mRunFaceDetection.run();
                }, this.mHandler);
    }

    private void onSaveClicked() {
        // TODO this code needs to change to use the decode/crop/encode single
        // step api so that we don't require that the whole (possibly large)
        // bitmap doesn't have to be read into memory
        if (this.mSaving)
            return;

        if (this.mCrop == null) {

            return;
        }

        this.mSaving = true;

        Rect r = this.mCrop.getCropRect();

        int width = r.width();
        int height = r.height();

        // If we are circle cropping, we want alpha channel, which is the
        // third param here.
        Bitmap croppedImage;

        croppedImage = Bitmap.createBitmap(width, height,
                this.mCircleCrop ? Config.ARGB_8888
                        : Config.RGB_565);
        if (croppedImage == null) {

            return;
        }

        {
            Canvas canvas = new Canvas(croppedImage);
            Rect dstRect = new Rect(0, 0, width, height);
            canvas.drawBitmap(CropImage.mBitmap, r, dstRect, null);
        }

        if (this.mCircleCrop) {

            // OK, so what's all this about?
            // Bitmaps are inherently rectangular but we want to return
            // something that's basically a circle. So we fill in the
            // area around the circle with alpha. Note the all important
            // PortDuff.Mode.CLEAR.
            Canvas c = new Canvas(croppedImage);
            Path p = new Path();
            p.addCircle((float) width / 2.0F, (float) height / 2.0F, (float) width / 2.0F, Direction.CW);
            c.clipPath(p, Op.DIFFERENCE);
            c.drawColor(0x00000000, Mode.CLEAR);
        }

		/* If the output is required to a specific size then scale or fill */
        if (this.mOutputX != 0 && this.mOutputY != 0) {

            if (this.mScale) {

				/* Scale the image to the required dimensions */
                Bitmap old = croppedImage;
                croppedImage = Util.transform(new Matrix(), croppedImage,
                        this.mOutputX, this.mOutputY, this.mScaleUp);
                if (old != croppedImage) {

                    old.recycle();
                }
            } else {

				/*
                 * Don't scale the image crop it to the size requested. Create
				 * an new image with the cropped image in the center and the
				 * extra space filled.
				 */

                // Don't scale the image but instead fill it so it's the
                // required dimension
                Bitmap b = Bitmap.createBitmap(this.mOutputX, this.mOutputY,
                        Config.RGB_565);
                Canvas canvas = new Canvas(b);

                Rect srcRect = this.mCrop.getCropRect();
                Rect dstRect = new Rect(0, 0, this.mOutputX, this.mOutputY);

                int dx = (srcRect.width() - dstRect.width()) / 2;
                int dy = (srcRect.height() - dstRect.height()) / 2;

				/* If the srcRect is too big, use the center part of it. */
                srcRect.inset(Math.max(0, dx), Math.max(0, dy));

				/* If the dstRect is too big, use the center part of it. */
                dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

				/* Draw the cropped bitmap in the center */
                canvas.drawBitmap(CropImage.mBitmap, srcRect, dstRect, null);

				/* Set the cropped bitmap as the new bitmap */
                croppedImage.recycle();
                croppedImage = b;
            }
        }

        // Return the cropped image directly or save it to the specified URI.
        Bundle myExtras = this.getIntent().getExtras();
        if (myExtras != null
                && (myExtras.getParcelable("data") != null || myExtras
                .getBoolean(CropImage.RETURN_DATA))) {

            Bundle extras = new Bundle();
            extras.putParcelable(CropImage.RETURN_DATA_AS_BITMAP, croppedImage);
            this.setResult(Activity.RESULT_OK, (new Intent()).setAction(CropImage.ACTION_INLINE_DATA)
                    .putExtras(extras));
            this.finish();
        } else {
            Bitmap b = croppedImage;
            Util.startBackgroundJob(this, null,
                    this.getString(string.saving_image), () -> this.saveOutput(b), this.mHandler);
        }
    }

    private void saveOutput(Bitmap croppedImage) {

        if (this.mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = this.mContentResolver.openOutputStream(this.mSaveUri);
                if (outputStream != null) {
                    croppedImage.compress(this.mOutputFormat, 90, outputStream);
                }
            } catch (IOException ignored) {

                //Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
                this.setResult(Activity.RESULT_CANCELED);
                this.finish();
                return;
            } finally {

                Util.closeSilently(outputStream);
            }

            Bundle extras = new Bundle();
            Intent intent = new Intent(this.mSaveUri.toString());
            intent.putExtras(extras);
            intent.putExtra(CropImage.IMAGE_PATH, this.mImagePath);
            intent.putExtra(CropImage.ORIENTATION_IN_DEGREES,
                    Util.getOrientationInDegree(this));
            this.setResult(Activity.RESULT_OK, intent);
        } else {

            //Log.e(TAG, "not defined image url");
        }
        croppedImage.recycle();
        this.finish();
    }

    @Override
    protected final void onPause() {

        super.onPause();
        BitmapManager.instance().cancelThreadDecoding(this.mDecodingThreads);
    }

    @Override
    protected final void onDestroy() {

        super.onDestroy();

        if (CropImage.mBitmap != null) {

            CropImage.mBitmap.recycle();
        }
    }

}
