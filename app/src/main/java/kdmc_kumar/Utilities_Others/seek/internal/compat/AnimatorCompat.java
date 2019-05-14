/*
 * Copyright (c) Gustavo Claramunt (AnderWeb) 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kdmc_kumar.Utilities_Others.seek.internal.compat;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

/**
 * Currently, there's no {@link android.animation.ValueAnimator} compatibility version
 * and as we didn't want to throw in external dependencies, we made this small class.
 * <p/>
 * <p>
 * This will work like {@link android.support.v4.view.ViewPropertyAnimatorCompat}, that is,
 * not doing anything on API<11 and using the default {@link android.animation.ValueAnimator}
 * on API>=11
 * </p>
 * <p>
 * This class is used to provide animation to the {@link}
 * when navigating with the Keypad
 * </p>
 *
 * @hide
 */
public abstract class AnimatorCompat {
    public interface AnimationFrameUpdateListener {
        void onAnimationFrame(float currentValue);
    }

    AnimatorCompat() {

    }

    public abstract void cancel();

    public abstract boolean isRunning();

    public abstract void setDuration(int progressAnimationDuration);

    public abstract void start();

    public static final AnimatorCompat create(float start, float end, AnimatorCompat.AnimationFrameUpdateListener listener) {
        if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
            return new AnimatorCompatV11(start, end, listener);
        } else {
            return new AnimatorCompat.AnimatorCompatBase(start, end, listener);
        }
    }

    private static class AnimatorCompatBase extends AnimatorCompat {

        private final AnimatorCompat.AnimationFrameUpdateListener mListener;
        private final float mEndValue;

        public AnimatorCompatBase(float start, float end, AnimatorCompat.AnimationFrameUpdateListener listener) {
            this.mListener = listener;
            this.mEndValue = end;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public void setDuration(int progressAnimationDuration) {

        }

        @Override
        public void start() {
            this.mListener.onAnimationFrame(this.mEndValue);
        }
    }
}
