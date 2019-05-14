package kdmc_kumar.Utilities_Others;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

/*
 * The solution is taken from here: http://stackoverflow.com/questions/10042695/how-to-get-camera-result-as-a-uri-in-data-folder
 */

public class InternalStorageContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri
            .parse("content://com.example.image/");
    private static final HashMap<String, String> MIME_TYPES = new HashMap<>();
    private static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";

    static {
        InternalStorageContentProvider.MIME_TYPES.put(".jpg", "image/jpeg");
        InternalStorageContentProvider.MIME_TYPES.put(".jpeg", "image/jpeg");
    }

    public InternalStorageContentProvider() {
    }

    @Override
    public final boolean onCreate() {
        try {
            File mFile = new File(this.getContext().getFilesDir(), InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
            if (!mFile.exists()) {
                mFile.createNewFile();
                this.getContext().getContentResolver().notifyChange(InternalStorageContentProvider.CONTENT_URI,
                        null);
            }
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    @Override
    public final String getType(@NonNull Uri uri) {
        String path = uri.toString();
        for (Iterator<String> iterator = InternalStorageContentProvider.MIME_TYPES.keySet().iterator(); iterator.hasNext(); ) {
            String extension = iterator.next();
            if (path.endsWith(extension)) {
                return (InternalStorageContentProvider.MIME_TYPES.get(extension));
            }
        }
        return (null);
    }

    @Override
    public final ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode)
            throws FileNotFoundException {
        File f = new File(this.getContext().getFilesDir(),
                InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
        if (f.exists()) {
            return (ParcelFileDescriptor.open(f,
                    ParcelFileDescriptor.MODE_READ_WRITE));
        }
        throw new FileNotFoundException(uri.getPath());
    }

    @Override
    public final int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public final Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    @Nullable
    @Override
    public final Cursor query(@NonNull Uri uri, String[] strings, String s,
                              String[] strings1, String s1) {
        return null;
    }

    @Override
    public final int update(@NonNull Uri uri, ContentValues contentValues, String s,
                            String[] strings) {
        return 0;
    }
}