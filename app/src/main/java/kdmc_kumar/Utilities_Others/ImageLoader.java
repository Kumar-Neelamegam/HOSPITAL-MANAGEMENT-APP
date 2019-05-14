package kdmc_kumar.Utilities_Others;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;

class ImageLoader {

    private final int stub_id = drawable.no_media;
    private final MemoryCache memoryCache = new MemoryCache();
    private final FileCache fileCache;
    private final ExecutorService executorService;
    private final Map<ImageView, String> imageViews = Collections
            .synchronizedMap(new WeakHashMap<ImageView, String>());

    public ImageLoader(Context context) {
        this.fileCache = new FileCache(context);
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public final void DisplayImage(String url, ImageView imageView) {
        this.imageViews.put(imageView, url);
        Bitmap bitmap = this.memoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            this.queuePhoto(url, imageView);
            imageView.setImageResource(this.stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView) {
        ImageLoader.PhotoToLoad p = new ImageLoader.PhotoToLoad(url, imageView);
        this.executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url) {
        File f = this.fileCache.getFile(url);

        // from SD cache
        Bitmap b = ImageLoader.decodeFile(f);
        if (b != null)
            return b;

        // from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl
                    .openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            Bitmap bitmap1 = ImageLoader.decodeFile(f);
            return bitmap1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // decodes image and scales it to reduce memory consumption
    private static Bitmap decodeFile(File f) {
        try {
            // decode image size
            Options o = new Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (width_tmp / 2 >= REQUIRED_SIZE
                    && height_tmp / 2 >= REQUIRED_SIZE) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            Options o2 = new Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException ignored) {
        }
        return null;
    }

    private final boolean imageViewReused(ImageLoader.PhotoToLoad photoToLoad) {
        String tag = this.imageViews.get(photoToLoad.imageView);
        return tag == null || !tag.equals(photoToLoad.url);
    }

    public final void clearCache() {
        this.memoryCache.clear();
        this.fileCache.clear();
    }

    // Task for the queue
    private static class PhotoToLoad {
        final String url;
        final ImageView imageView;

        PhotoToLoad(String u, ImageView i) {
            this.url = u;
            this.imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        final ImageLoader.PhotoToLoad photoToLoad;

        PhotosLoader(ImageLoader.PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public final void run() {
            if (ImageLoader.this.imageViewReused(this.photoToLoad))
                return;
            Bitmap bmp = ImageLoader.this.getBitmap(this.photoToLoad.url);
            ImageLoader.this.memoryCache.put(this.photoToLoad.url, bmp);
            if (ImageLoader.this.imageViewReused(this.photoToLoad))
                return;
            ImageLoader.BitmapDisplayer bd = new ImageLoader.BitmapDisplayer(bmp, this.photoToLoad);
            Activity a = (Activity) this.photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        final Bitmap bitmap;
        final ImageLoader.PhotoToLoad photoToLoad;

        BitmapDisplayer(Bitmap b, ImageLoader.PhotoToLoad p) {
            this.bitmap = b;
            this.photoToLoad = p;
        }

        public final void run() {
            if (ImageLoader.this.imageViewReused(this.photoToLoad))
                return;
            if (this.bitmap != null)
                this.photoToLoad.imageView.setImageBitmap(this.bitmap);
            else
                this.photoToLoad.imageView.setImageResource(ImageLoader.this.stub_id);
        }
    }

}
