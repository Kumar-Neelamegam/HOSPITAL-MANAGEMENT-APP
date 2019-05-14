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

package kdmc_kumar.Utilities_Others;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/**
 * This class provides several utilities to cancel bitmap decoding.
 * <p/>
 * The function decodeFileDescriptor() is used to decode a bitmap. During
 * decoding if another thread wants to cancel it, it calls the function
 * cancelThreadDecoding() specifying the Thread which is in decoding.
 * <p/>
 * cancelThreadDecoding() is sticky until allowThreadDecoding() is called.
 * <p/>
 * You can also cancel decoding for a set of threads using ThreadSet as
 * the parameter for cancelThreadDecoding. To put a thread into a ThreadSet,
 * use the add() method. A ThreadSet holds (weak) references to the threads,
 * so you don't need to remove Thread from it if some thread dies.
 */
public class BitmapManager {

    private static final String TAG = "BitmapManager";
    private static BitmapManager sManager;
    private final WeakHashMap<Thread, BitmapManager.ThreadStatus> mThreadStatus =
            new WeakHashMap<>();

    private BitmapManager() {

    }

    public static BitmapManager instance() {
        synchronized (BitmapManager.class) {

            if (BitmapManager.sManager == null) {
                BitmapManager.sManager = new BitmapManager();
            }
            return BitmapManager.sManager;
        }
    }

    /**
     * Get thread status and create one if specified.
     */
    private BitmapManager.ThreadStatus getOrCreateThreadStatus(Thread t) {
        synchronized (this) {

            BitmapManager.ThreadStatus status = this.mThreadStatus.get(t);
            if (status == null) {
                status = new BitmapManager.ThreadStatus();
                this.mThreadStatus.put(t, status);
            }
            return status;
        }
    }

    /**
     * The following three methods are used to keep track of
     * BitmapFaction.Options used for decoding and cancelling.
     */
    private void setDecodingOptions(Thread t,
                                    Options options) {
        synchronized (this) {

            this.getOrCreateThreadStatus(t).mOptions = options;
        }
    }

    @Nullable
    final Options getDecodingOptions(Thread t) {
        synchronized (this) {

            BitmapManager.ThreadStatus status = this.mThreadStatus.get(t);
            return status != null ? status.mOptions : null;
        }
    }

    private final void removeDecodingOptions(Thread t) {
        synchronized (this) {

            BitmapManager.ThreadStatus status = this.mThreadStatus.get(t);
            status.mOptions = null;
        }
    }

    /**
     * The following two methods are used to allow/cancel a set of threads
     * for bitmap decoding.
     */
    public final void allowThreadDecoding(BitmapManager.ThreadSet threads) {
        synchronized (this) {

            for (Iterator<Thread> iterator = threads.iterator(); iterator.hasNext(); ) {
                Thread t = iterator.next();
                this.allowThreadDecoding(t);
            }
        }
    }

    public final void cancelThreadDecoding(BitmapManager.ThreadSet threads) {
        synchronized (this) {

            for (Iterator<Thread> iterator = threads.iterator(); iterator.hasNext(); ) {
                Thread t = iterator.next();
                this.cancelThreadDecoding(t);
            }
        }
    }

    /**
     * The following three methods are used to keep track of which thread
     * is being disabled for bitmap decoding.
     */
    private final boolean canThreadDecoding(Thread t) {
        synchronized (this) {

            BitmapManager.ThreadStatus status = this.mThreadStatus.get(t);
            if (status == null) {
                // allow decoding by default
                return true;
            }

            return (status.mState != BitmapManager.State.CANCEL);
        }
    }

    private final void allowThreadDecoding(Thread t) {
        synchronized (this) {

            this.getOrCreateThreadStatus(t).mState = BitmapManager.State.ALLOW;
        }
    }

    private final void cancelThreadDecoding(Thread t) {
        synchronized (this) {

            BitmapManager.ThreadStatus status = this.getOrCreateThreadStatus(t);
            status.mState = BitmapManager.State.CANCEL;
            if (status.mOptions != null) {
                status.mOptions.requestCancelDecode();
            }

            // Wake up threads in waiting list
            this.notifyAll();
        }
    }

    /**
     * A debugging routine.
     */
    public final void dump() {
        synchronized (this) {

            for (Entry<Thread, BitmapManager.ThreadStatus> entry : this.mThreadStatus.entrySet()) {
                Log.v(BitmapManager.TAG, "[Dump] Thread " + entry.getKey() + " ("
                        + entry.getKey().getId()
                        + ")'s status is " + entry.getValue());
            }
        }
    }

    /**
     * The real place to delegate bitmap decoding to BitmapFactory.
     */
    @Nullable
    public final Bitmap decodeFileDescriptor(FileDescriptor fd,
                                             Options options) {

        if (options.mCancel) {
            return null;
        }

        Thread thread = Thread.currentThread();
        if (!this.canThreadDecoding(thread)) {
            // Log.d(TAG, "Thread " + thread + " is not allowed to decode.");
            return null;
        }

        this.setDecodingOptions(thread, options);
        Bitmap b = BitmapFactory.decodeFileDescriptor(fd, null, options);

        this.removeDecodingOptions(thread);
        return b;
    }

    private enum State {CANCEL, ALLOW}

    private static class ThreadStatus {

        BitmapManager.State mState = BitmapManager.State.ALLOW;
        @Nullable
        Options mOptions;

        private ThreadStatus() {
        }

        @Override
        public final String toString() {

            String s;
            switch (this.mState) {
                case CANCEL:
                    s = "Cancel";
                    break;
                case ALLOW:
                    s = "Allow";
                    break;
                default:
                    s = "?";
                    break;
            }
            s = "thread state = " + s + ", options = " + this.mOptions;
            return s;
        }
    }

    public static class ThreadSet implements Iterable<Thread> {

        private final WeakHashMap<Thread, Object> mWeakCollection =
                new WeakHashMap<>();

        public ThreadSet() {
        }

        public final void add(Thread t) {

            this.mWeakCollection.put(t, null);
        }

        public final void remove(Thread t) {

            this.mWeakCollection.remove(t);
        }

        @NonNull
        public final Iterator<Thread> iterator() {

            return this.mWeakCollection.keySet().iterator();
        }
    }
}
