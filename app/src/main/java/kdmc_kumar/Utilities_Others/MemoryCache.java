package kdmc_kumar.Utilities_Others;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class MemoryCache {
    private final Map<String, SoftReference<Bitmap>> cache = Collections
            .synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());

    MemoryCache() {
    }

    @Nullable
    public final Bitmap get(String id) {
        if (!this.cache.containsKey(id))
            return null;
        SoftReference<Bitmap> ref = this.cache.get(id);
        return ref.get();
    }

    public final void put(String id, Bitmap bitmap) {
        this.cache.put(id, new SoftReference<>(bitmap));
    }

    public final void clear() {
        this.cache.clear();
    }
}