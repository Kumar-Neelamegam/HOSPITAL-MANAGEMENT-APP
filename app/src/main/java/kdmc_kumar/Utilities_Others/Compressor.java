package kdmc_kumar.Utilities_Others;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by dell on 15/7/17.
 */

class Compressor {

    private static int height, width, inSampleSize;
    private static String encodedfile;

    Compressor() {
    }

    public static File compressImageFile(File imageFile, int reqHeight, int reqWidth,
                                         String filePath, int quality,
                                         CompressFormat compressFormat, int orientation) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File(filePath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(filePath);
            Compressor.decodeBitmapAndCompress(imageFile, reqHeight, reqWidth, orientation)
                    .compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(filePath);
    }

    public static Bitmap decodeBitmapAndCompress(File imageFile, int reqHeight, int reqWidth, int reqOrientation)
            throws IOException {

        Options options = new Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //Calculating Sample Size
        options.inSampleSize = Compressor.calculateSampleSize(options, reqHeight, reqWidth);

        options.inJustDecodeBounds = false;

        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        ExifInterface exifInterface;
        exifInterface = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case 6:
                matrix.postRotate(90);
                break;
            case 3:
                matrix.postRotate(180);
                break;
            case 8:
                matrix.postRotate(270);
                break;
        }
        if (reqOrientation > 0) {
            matrix.postRotate((float) reqOrientation);
        }
        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth()
                , scaledBitmap.getHeight(), matrix, true);
        return scaledBitmap;
    }


    private static int calculateSampleSize(Options options, int reqHeight, int reqWidth) {

        Compressor.height = options.outHeight;
        Compressor.width = options.outWidth;
        Compressor.inSampleSize = 1;

        int halfHeight = Compressor.height / 2;
        int halfWidth = Compressor.width / 2;

        if (Compressor.height > reqHeight || Compressor.width > reqWidth) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / Compressor.inSampleSize) >= reqHeight && (halfWidth / Compressor.inSampleSize) >= reqWidth) {
                Compressor.inSampleSize *= 2;
            }
        }
        return Compressor.inSampleSize;
    }

    public static String getBase64forCompressedImage(File compressFile) {

        FileInputStream fileInputStreamReader = null;
        byte[] bytes = new byte[(int) compressFile.length()];
        try {
            fileInputStreamReader = new FileInputStream(compressFile);
            fileInputStreamReader.read(bytes);
            Compressor.encodedfile = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Compressor.encodedfile;
    }


}
