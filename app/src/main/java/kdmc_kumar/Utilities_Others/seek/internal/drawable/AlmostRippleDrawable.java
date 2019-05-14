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

package kdmc_kumar.Utilities_Others.seek.internal.drawable;

import android.R.attr;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

public class AlmostRippleDrawable extends   StateDrawable implements Animatable {
    private static final long FRAME_DURATION = 1000 / 60;
    private static final int ANIMATION_DURATION = 250;

    private static final float INACTIVE_SCALE = 0f;
    private static final float ACTIVE_SCALE = 1f;
    private float mCurrentScale = AlmostRippleDrawable.INACTIVE_SCALE;
    private final Interpolator mInterpolator;
    private long mStartTime;
    private boolean mReverse;
    private boolean mRunning;
    private int mDuration = AlmostRippleDrawable.ANIMATION_DURATION;
    private float mAnimationInitialValue;
    //We don't use colors just with our drawable state because of animations
    private int mPressedColor;
    private int mFocusedColor;
    private int mDisabledColor;
    private int mRippleColor;
    private int mRippleBgColor;

    public AlmostRippleDrawable(@NonNull ColorStateList tintStateList) {
        super(tintStateList);
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        this.setColor(tintStateList);
    }

    public void setColor(@NonNull ColorStateList tintStateList) {
        int defaultColor = tintStateList.getDefaultColor();
        this.mFocusedColor = tintStateList.getColorForState(new int[]{attr.state_enabled, attr.state_focused}, defaultColor);
        this.mPressedColor = tintStateList.getColorForState(new int[]{attr.state_enabled, attr.state_pressed}, defaultColor);
        this.mDisabledColor = tintStateList.getColorForState(new int[]{-attr.state_enabled}, defaultColor);

        //The ripple should be partially transparent
        this.mFocusedColor = AlmostRippleDrawable.getModulatedAlphaColor(130, this.mFocusedColor);
        this.mPressedColor = AlmostRippleDrawable.getModulatedAlphaColor(130, this.mPressedColor);
        this.mDisabledColor = AlmostRippleDrawable.getModulatedAlphaColor(130, this.mDisabledColor);
    }

    private static int getModulatedAlphaColor(int alphaValue, int originalColor) {
        int alpha = Color.alpha(originalColor);
        int scale = alphaValue + (alphaValue >> 7);
        alpha = alpha * scale >> 8;
        return Color.argb(alpha, Color.red(originalColor), Color.green(originalColor), Color.blue(originalColor));
    }

    @Override
    public void doDraw(Canvas canvas, Paint paint) {
        Rect bounds = this.getBounds();
        int size = Math.min(bounds.width(), bounds.height());
        float scale = this.mCurrentScale;
        int rippleColor = this.mRippleColor;
        int bgColor = this.mRippleBgColor;
        float radius = (size / 2);
        float radiusAnimated = radius * scale;
        if (scale > AlmostRippleDrawable.INACTIVE_SCALE) {
            if (bgColor != 0) {
                paint.setColor(bgColor);
                paint.setAlpha(this.decreasedAlpha(Color.alpha(bgColor)));
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), radius, paint);
            }
            if (rippleColor != 0) {
                paint.setColor(rippleColor);
                paint.setAlpha(this.modulateAlpha(Color.alpha(rippleColor)));
                canvas.drawCircle(bounds.centerX(), bounds.centerY(), radiusAnimated, paint);
            }
        }
    }

    private int decreasedAlpha(int alpha) {
        int scale = 100 + (100 >> 7);
        return alpha * scale >> 8;
    }

    @Override
    public boolean setState(int[] stateSet) {
        int[] oldState = this.getState();
        boolean oldPressed = false;
        for (int i : oldState) {
            if (i == attr.state_pressed) {
                oldPressed = true;
            }
        }
        super.setState(stateSet);
        boolean focused = false;
        boolean pressed = false;
        boolean disabled = true;
        for (int i : stateSet) {
            if (i == attr.state_focused) {
                focused = true;
            } else if (i == attr.state_pressed) {
                pressed = true;
            } else if (i == attr.state_enabled) {
                disabled = false;
            }
        }

        if (disabled) {
            this.unscheduleSelf(this.mUpdater);
            this.mRippleColor = this.mDisabledColor;
            this.mRippleBgColor = 0;
            this.mCurrentScale = AlmostRippleDrawable.ACTIVE_SCALE / 2;
            this.invalidateSelf();
        } else {
            if (pressed) {
                this.animateToPressed();
                this.mRippleColor = this.mRippleBgColor = this.mPressedColor;
            } else if (oldPressed) {
                this.mRippleColor = this.mRippleBgColor = this.mPressedColor;
                this.animateToNormal();
            } else if (focused) {
                this.mRippleColor = this.mFocusedColor;
                this.mRippleBgColor = 0;
                this.mCurrentScale = AlmostRippleDrawable.ACTIVE_SCALE;
                this.invalidateSelf();
            } else {
                this.mRippleColor = 0;
                this.mRippleBgColor = 0;
                this.mCurrentScale = AlmostRippleDrawable.INACTIVE_SCALE;
                this.invalidateSelf();
            }
        }
        return true;
    }

    public void animateToPressed() {
        this.unscheduleSelf(this.mUpdater);
        if (this.mCurrentScale < AlmostRippleDrawable.ACTIVE_SCALE) {
            this.mReverse = false;
            this.mRunning = true;
            this.mAnimationInitialValue = this.mCurrentScale;
            float durationFactor = 1f - ((this.mAnimationInitialValue - AlmostRippleDrawable.INACTIVE_SCALE) / (AlmostRippleDrawable.ACTIVE_SCALE - AlmostRippleDrawable.INACTIVE_SCALE));
            this.mDuration = (int) (AlmostRippleDrawable.ANIMATION_DURATION * durationFactor);
            this.mStartTime = SystemClock.uptimeMillis();
            this.scheduleSelf(this.mUpdater, this.mStartTime + AlmostRippleDrawable.FRAME_DURATION);
        }
    }

    public void animateToNormal() {
        this.unscheduleSelf(this.mUpdater);
        if (this.mCurrentScale > AlmostRippleDrawable.INACTIVE_SCALE) {
            this.mReverse = true;
            this.mRunning = true;
            this.mAnimationInitialValue = this.mCurrentScale;
            float durationFactor = 1f - ((this.mAnimationInitialValue - AlmostRippleDrawable.ACTIVE_SCALE) / (AlmostRippleDrawable.INACTIVE_SCALE - AlmostRippleDrawable.ACTIVE_SCALE));
            this.mDuration = (int) (AlmostRippleDrawable.ANIMATION_DURATION * durationFactor);
            this.mStartTime = SystemClock.uptimeMillis();
            this.scheduleSelf(this.mUpdater, this.mStartTime + AlmostRippleDrawable.FRAME_DURATION);
        }
    }

    private void updateAnimation(float factor) {
        float initial = this.mAnimationInitialValue;
        float destination = this.mReverse ? AlmostRippleDrawable.INACTIVE_SCALE : AlmostRippleDrawable.ACTIVE_SCALE;
        this.mCurrentScale = initial + (destination - initial) * factor;
        this.invalidateSelf();
    }

    private final Runnable mUpdater = new Runnable() {

        @Override
        public void run() {

            long currentTime = SystemClock.uptimeMillis();
            long diff = currentTime - AlmostRippleDrawable.this.mStartTime;
            if (diff < AlmostRippleDrawable.this.mDuration) {
                float interpolation = AlmostRippleDrawable.this.mInterpolator.getInterpolation((float) diff / (float) AlmostRippleDrawable.this.mDuration);
                AlmostRippleDrawable.this.scheduleSelf(AlmostRippleDrawable.this.mUpdater, currentTime + AlmostRippleDrawable.FRAME_DURATION);
                AlmostRippleDrawable.this.updateAnimation(interpolation);
            } else {
                AlmostRippleDrawable.this.unscheduleSelf(AlmostRippleDrawable.this.mUpdater);
                AlmostRippleDrawable.this.mRunning = false;
                AlmostRippleDrawable.this.updateAnimation(1f);
            }
        }
    };

    @Override
    public void start() {
        //No-Op. We control our own animation
    }

    @Override
    public void stop() {
        //No-Op. We control our own animation
    }

    @Override
    public boolean isRunning() {
        return this.mRunning;
    }
}
