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

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION_CODES;

/**
 * Class to wrap a {@link ValueAnimator}
 * for use with AnimatorCompat
 *
 * @hide
 * @see {@link import kdmc_kumar.seek.internal.compat.AnimatorCompat}
 */
@TargetApi(VERSION_CODES.HONEYCOMB)
public class AnimatorCompatV11 extends  AnimatorCompat {

    ValueAnimator animator;

    public AnimatorCompatV11(float start, float end, AnimatorCompat.AnimationFrameUpdateListener listener) {
        this.animator = ValueAnimator.ofFloat(start, end);
        this.animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                listener.onAnimationFrame((Float) animation.getAnimatedValue());
            }
        });
    }

    @Override
    public void cancel() {
        this.animator.cancel();
    }

    @Override
    public boolean isRunning() {
        return this.animator.isRunning();
    }

    @Override
    public void setDuration(int duration) {
        this.animator.setDuration(duration);
    }

    @Override
    public void start() {
        this.animator.start();
    }
}
