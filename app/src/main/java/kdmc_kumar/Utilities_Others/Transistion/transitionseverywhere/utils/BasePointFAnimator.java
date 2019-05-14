/*
 * Copyright (C) 2015 Andrey Kulikov (andkulikov@gmail.com)
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
package kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.graphics.PointF;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import java.lang.ref.WeakReference;

/**
 * Created by Andrey Kulikov on 17.08.15.
 */
@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public abstract class BasePointFAnimator extends ValueAnimator implements AnimatorUpdateListener {

        /**
         * A weak reference to the mTarget object on which the property exists, set
         * in the constructor. We'll cancel the animation if this goes away.
         */
        private final WeakReference mTarget;

        private final PointFProperty mPointFProperty;

        private final PointF mTempPointF = new PointF();

        protected BasePointFAnimator(Object target, PointFProperty pointFProperty) {
            this.mTarget = new WeakReference<>(target);
            this.mPointFProperty = pointFProperty;
            this.setFloatValues(0f, 1f);
            this.addUpdateListener(this);
        }

        protected abstract void applyAnimatedFraction(PointF holder, float fraction);

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Object target = this.mTarget.get();
            if (target == null) {
                // We lost the target reference, cancel.
                this.cancel();
                return;
            }
            this.applyAnimatedFraction(this.mTempPointF, animation.getAnimatedFraction());
            this.mPointFProperty.set(target, this.mTempPointF);
        }
    }