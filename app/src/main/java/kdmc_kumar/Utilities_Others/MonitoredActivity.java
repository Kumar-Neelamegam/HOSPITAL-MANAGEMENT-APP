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

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;

public class MonitoredActivity extends Activity {

    private final ArrayList<MonitoredActivity.LifeCycleListener> mListeners =
            new ArrayList<>();

    public MonitoredActivity() {
    }

    public final void addLifeCycleListener(MonitoredActivity.LifeCycleListener listener) {

        if (this.mListeners.contains(listener)) return;
        this.mListeners.add(listener);
    }

    public final void removeLifeCycleListener(MonitoredActivity.LifeCycleListener listener) {

        this.mListeners.remove(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        for (Iterator<MonitoredActivity.LifeCycleListener> iterator = this.mListeners.iterator(); iterator.hasNext(); ) {
            MonitoredActivity.LifeCycleListener listener = iterator.next();
            listener.onActivityCreated(this);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        for (Iterator<MonitoredActivity.LifeCycleListener> iterator = this.mListeners.iterator(); iterator.hasNext(); ) {
            MonitoredActivity.LifeCycleListener listener = iterator.next();
            listener.onActivityDestroyed(this);
        }
    }

    @Override
    protected final void onStart() {

        super.onStart();
        for (Iterator<MonitoredActivity.LifeCycleListener> iterator = this.mListeners.iterator(); iterator.hasNext(); ) {
            MonitoredActivity.LifeCycleListener listener = iterator.next();
            listener.onActivityStarted(this);
        }
    }

    @Override
    protected final void onStop() {

        super.onStop();
        for (Iterator<MonitoredActivity.LifeCycleListener> iterator = this.mListeners.iterator(); iterator.hasNext(); ) {
            MonitoredActivity.LifeCycleListener listener = iterator.next();
            listener.onActivityStopped(this);
        }
    }

    interface LifeCycleListener {

        void onActivityCreated(MonitoredActivity activity);

        void onActivityDestroyed(MonitoredActivity activity);

        void onActivityPaused(MonitoredActivity activity);

        void onActivityResumed(MonitoredActivity activity);

        void onActivityStarted(MonitoredActivity activity);

        void onActivityStopped(MonitoredActivity activity);
    }

    public static class LifeCycleAdapter implements MonitoredActivity.LifeCycleListener {

        public LifeCycleAdapter() {
        }

        public void onActivityCreated(MonitoredActivity activity) {

        }

        public void onActivityDestroyed(MonitoredActivity activity) {

        }

        public void onActivityPaused(MonitoredActivity activity) {

        }

        public void onActivityResumed(MonitoredActivity activity) {

        }

        public void onActivityStarted(MonitoredActivity activity) {

        }

        public void onActivityStopped(MonitoredActivity activity) {

        }
    }
}
