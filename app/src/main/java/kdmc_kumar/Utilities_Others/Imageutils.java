package kdmc_kumar.Utilities_Others;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.MediaColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Imageutils {


    private final Context context;
    private final Activity current_activity;

    private final Imageutils.ImageAttachmentListener imageAttachment_callBack;

    @Nullable
    private
    String selected_path = "";
    private Uri imageUri;
    private File path;

    private int from;

    public Imageutils(Activity act) {

        context = act;
        current_activity = act;
        this.imageAttachment_callBack = (Imageutils.ImageAttachmentListener) this.context;
    }

    /**
     * Get file name from path
     *
     * @param path
     * @return
     */

    public static final String getfilename_from_path(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());

    }

    /**
     * Get Image URI from Bitmap
     *
     * @param context
     * @param photo
     * @return
     */

    public static final Uri getImageUri(Context context, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(CompressFormat.PNG, 80, bytes);
        String path = Media.insertImage(context.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    /**
     * Get Path from Image URI
     *
     * @param uri
     * @return
     */

    private final String getPath(Uri uri) {
        String[] projection = {MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if (cursor != null) {
            int column_index1 = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index1);
            cursor.close();
            return path;
        } else
            return uri.getPath();
    }

    /**
     * Bitmap from String
     *
     * @param encodedString
     * @return
     */
    @Nullable
    public static final Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    /**
     * Get String from Bitmap
     *
     * @param bitmap
     * @return
     */

    public static final String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 80, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    /**
     * Check Camera Availability
     *
     * @return
     */

    private final boolean isDeviceSupportCamera() {
        // this device has a camera
// no camera on this device
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }


    /**
     * Compress Imgae
     *
     * @param imageUri
     * @param height
     * @param width
     * @return
     */


    @Nullable
    private final Bitmap compressImage(String imageUri, float height, float width) {

        String filePath = this.getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        Options options = new Options();

        // by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.

        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as 816x612

        float imgRatio = (float) (actualWidth / actualHeight);
        float maxRatio = width / height;

        // width and height values are set maintaining the aspect ratio of the image

        if ((float) actualHeight > height || (float) actualWidth > width) {
            if (imgRatio < maxRatio) {
                float imgRatio1 = height / (float) actualHeight;
                actualWidth = (int) (imgRatio1 * (float) actualWidth);
                actualHeight = (int) height;
            } else if (imgRatio > maxRatio) {
                float imgRatio1 = width / (float) actualWidth;
                actualHeight = (int) (imgRatio1 * (float) actualHeight);
                actualWidth = (int) width;
            } else {
                actualHeight = (int) height;
                actualWidth = (int) width;

            }
        }

        //  setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = Imageutils.calculateInSampleSize(options, actualWidth, actualHeight);

        //  inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        // this options allow android to claim the bitmap memory if it runs low on memory

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            //  load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = (float) actualWidth / (float) options.outWidth;
        float ratioY = (float) actualHeight / (float) options.outHeight;
        float middleX = (float) actualWidth / 2.0f;
        float middleY = (float) actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - (float) (bmp.getWidth() / 2), middleY - (float) (bmp.getHeight() / 2), new Paint(Paint.FILTER_BITMAP_FLAG));

        // check the rotation of the image and display it properly

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case 6:
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + 6);
                    break;
                case 3:
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + 3);
                    break;
                case 8:
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + 8);
                    break;
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);

            return scaledBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get RealPath from Content URI
     *
     * @param contentURI
     * @return
     */
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = this.context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaColumns.DATA);

            return cursor.getString(index);
        }

    }


    /**
     * ImageSize Calculation
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    private static final int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / reqHeight);
            int widthRatio = Math.round((float) width / reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        float totalPixels = (float) (width * height);
        float totalReqPixelsCap = (float) (reqWidth * reqHeight * 2);
        while (totalPixels / (float) (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /**
     * Launch Camera
     *
     * @param from
     */

    private final void launchCamera(int from) {
        this.from = from;

        if (VERSION.SDK_INT >= 23) {
            this.permission_check(1);
        } else {
            this.camera_call();
        }
    }

    /**
     * Launch Gallery
     *
     * @param from
     */

    private final void launchGallery(int from) {

        this.from = from;

        if (VERSION.SDK_INT >= 23) {
            this.permission_check(2);
        } else {
            this.galley_call();
        }
    }

    /**
     * Show AlertDialog with the following options
     * <p>
     * Camera
     * Gallery
     *
     * @param from
     */

    public final void imagepicker(int from) {
        this.from = from;

        CharSequence[] items;

        if (this.isDeviceSupportCamera()) {
            items = new CharSequence[2];
            items[0] = "Camera";
            items[1] = "Gallery";
        } else {
            items = new CharSequence[1];
            items[0] = "Gallery";
        }

        android.app.AlertDialog.Builder alertdialog = new android.app.AlertDialog.Builder(this.current_activity);
        alertdialog.setTitle("Add Image");
        alertdialog.setItems(items, (dialog, item) -> {
            if (items[item].equals("Camera")) {
                this.launchCamera(from);
            } else if (items[item].equals("Gallery")) {
                this.launchGallery(from);
            }
        });
        alertdialog.show();
    }

    /**
     * Check permission
     *
     * @param code
     */

    private final void permission_check(int code) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this.current_activity,
                permission.WRITE_EXTERNAL_STORAGE);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this.current_activity,
                    permission.WRITE_EXTERNAL_STORAGE)) {

                this.showMessageOKCancel(
                        (dialog, which) -> ActivityCompat.requestPermissions(this.current_activity,
                                new String[]{permission.WRITE_EXTERNAL_STORAGE},
                                code));
                return;
            }

            ActivityCompat.requestPermissions(this.current_activity,
                    new String[]{permission.WRITE_EXTERNAL_STORAGE},
                    code);
            return;
        }

        if (code == 1)
            this.camera_call();
        else if (code == 2)
            this.galley_call();
    }


    private void showMessageOKCancel(OnClickListener okListener) {
        new Builder(this.current_activity)
                .setMessage("For adding images , You need to provide permission to access your files")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    /**
     * Capture image from camera
     */

    private final void camera_call() {
        ContentValues values = new ContentValues();
        this.imageUri = this.current_activity.getContentResolver().insert(
                Media.EXTERNAL_CONTENT_URI, values);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri);
        this.current_activity.startActivityForResult(intent1, 0);
    }

    /**
     * pick image from Gallery
     */

    private final void galley_call() {

        Intent intent2 = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
        intent2.setType("image/*");
        this.current_activity.startActivityForResult(intent2, 1);

    }


    /**
     * Activity PermissionResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public final void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.camera_call();
                } else {
                    Toast.makeText(this.current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;

            case 2:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.galley_call();
                } else {

                    Toast.makeText(this.current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    /**
     * Intent ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        String file_name;
        Bitmap bitmap;

        switch (requestCode) {
            case 0:

                if (resultCode == Activity.RESULT_OK) {

                    //Log.e("Camera Selected","Photo");

                    try {
                        this.selected_path = null;
                        this.selected_path = this.getPath(this.imageUri);

                        file_name = this.selected_path.substring(this.selected_path.lastIndexOf('/') + 1);
                        //Log.e("file","name"+file_name);
                        //Log.e("selected","path"+selected_path);
                        bitmap = this.compressImage(this.imageUri.toString(), 816.0F, 612.0F);
                        this.imageAttachment_callBack.image_attachment(this.from, file_name, bitmap, this.imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //BaseConfig.Patient_ImgPath=selected_path;

                }
                break;
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    //Log.e("Gallery","Photo");
                    Uri selectedImage = data.getData();

                    try {
                        this.selected_path = null;
                        this.selected_path = this.getPath(selectedImage);
                        file_name = this.selected_path.substring(this.selected_path.lastIndexOf('/') + 1);
                        bitmap = this.compressImage(selectedImage.toString(), 816.0F, 612.0F);
                        this.imageAttachment_callBack.image_attachment(this.from, file_name, bitmap, selectedImage);
                        //Log.e("file","name"+file_name);
                        //Log.e("selected","path"+selected_path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //BaseConfig.Patient_ImgPath=selected_path;

                }
                break;
        }


    }

    /**
     * Get image from Uri
     *
     * @param uri
     * @param height
     * @param width
     * @return
     */
    public final Bitmap getImage_FromUri(Uri uri, float height, float width) {
        Bitmap bitmap = null;

        try {
            bitmap = this.compressImage(uri.toString(), height, width);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * Get filename from URI
     *
     * @param uri
     * @return
     */
    @Nullable
    public final String getFileName_from_Uri(Uri uri) {
        String path = null, file_name = null;

        try {

            path = this.getRealPathFromURI(uri.getPath());
            file_name = path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return file_name;

    }


    /**
     * Check Image Exist (or) Not
     *
     * @param file_name
     * @param file_path
     * @return
     */

    public final boolean checkimage(String file_name, String file_path) {
        boolean flag;
        this.path = new File(file_path);

        File file = new File(this.path, file_name);
        if (file.exists()) {
            Log.i("file", "exists");
            flag = true;
        } else {
            Log.i("file", "not exist");
            flag = false;
        }

        return flag;
    }


    /**
     * Get Image from the given path
     *
     * @param file_name
     * @param file_path
     * @return
     */

    @Nullable
    public final Bitmap getImage(String file_name, String file_path) {

        this.path = new File(file_path);
        File file = new File(this.path, file_name);

        Options options = new Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inSampleSize = 2;
        options.inTempStorage = new byte[16 * 1024];

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        return bitmap;
    }

    /**
     * Create an image
     *
     * @param bitmap
     * @param file_name
     * @param filepath
     * @param file_replace
     */


    public final void createImage(Bitmap bitmap, String file_name, String filepath, boolean file_replace) {

        try {
            this.path = new File(filepath);

            if (!this.path.exists()) {
                this.path.mkdirs();
            }

            File file = new File(this.path, file_name);

            if (file.exists()) {
                if (file_replace) {
                    file.delete();
                    File file1 = new File(this.path, file_name);
                    Imageutils.store_image(file1, bitmap);
                    Log.i("file", "replaced");
                }
            } else {
                Imageutils.store_image(file, bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @param file
     * @param bmp
     */
    private static final void store_image(File file, Bitmap bmp) {

        //Toast.makeText(context, "Path:"+file , Toast.LENGTH_LONG).show();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(CompressFormat.PNG, 80, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Image Attachment Callback

    public interface ImageAttachmentListener {
        void image_attachment(int from, String filename, Bitmap file, Uri uri);
    }


}
