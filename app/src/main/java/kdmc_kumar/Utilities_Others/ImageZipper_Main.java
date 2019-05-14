package kdmc_kumar.Utilities_Others;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.File;
import java.io.IOException;

/**
 * Created by dell on 15/7/17.
 */

public class ImageZipper_Main {

    private final String destinationDirectory;
    private int maxWidth = 612;
    private int maxHeight = 816;
    private int quality = 50;
    private int orientation;
    private CompressFormat compressFormat = CompressFormat.JPEG;

    public ImageZipper_Main(Context context) {
        this.destinationDirectory = context.getCacheDir().getPath() + File.separator + "images";
    }

    public static String getBase64forImage(File compressFile) {
        return Compressor.getBase64forCompressedImage(compressFile);
    }

    public static Bitmap decodeBase64(String base64) {
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public final ImageZipper_Main setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public final ImageZipper_Main setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public final ImageZipper_Main setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public final ImageZipper_Main setOrientation(int orientation) {
        this.orientation = orientation;
        return this;
    }

    public final ImageZipper_Main setCompressFormat(CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public final Bitmap compressToBitmap(File imageFile) throws IOException {
        return Compressor.decodeBitmapAndCompress(imageFile, this.maxHeight, this.maxWidth, this.orientation);
    }

    public final File compressToFile(File imageFile) throws IOException {
        return this.compressToFile(imageFile, imageFile.getName(), this.orientation);
    }

    private File compressToFile(File imageFile, String fileName, int orientation) throws IOException {
        return Compressor.compressImageFile(imageFile, this.maxHeight, this.maxWidth,
                this.destinationDirectory + File.separator + fileName, this.quality, this.compressFormat, orientation);
    }

}
