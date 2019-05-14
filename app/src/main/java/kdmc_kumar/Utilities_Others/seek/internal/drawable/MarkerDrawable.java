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
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Implementation of {@link import kdmc_kumar.seek.internal.drawable.StateDrawable} to draw a morphing marker symbol.
 * <p>
 * It's basically an implementation of an {@link Animatable} Drawable with the following details:
 * </p>
 * <ul>
 * <li>Animates from a circle shape to a "marker" shape just using a RoundRect</li>
 * <li>Animates color change from the normal state color to the pressed state color</li>
 * <li>Uses a {@link Path} to also serve as Outline for API>=21</li>
 * </ul>
 *
 * @hide
 */
public class MarkerDrawable extends StateDrawable implements Animatable {
    private static final long FRAME_DURATION = 1000 / 60;
    private static final int ANIMATION_DURATION = 250;

    private float mCurrentScale;
    private final Interpolator mInterpolator;
    private long mStartTime;
    private boolean mReverse;
    private boolean mRunning;
    private int mDuration = MarkerDrawable.ANIMATION_DURATION;
    //size of the actual thumb drawable to use as circle state size
    private final float mClosedStateSize;
    //value to store que current scale when starting an animation and interpolate from it
    private float mAnimationInitialValue;
    //extra offset directed from the View to account
    //for its internal padding between circle state and marker state
    private int mExternalOffset;
    //colors for interpolation
    private int mStartColor;//Color when the Marker is OPEN
    private int mEndColor;//Color when the arker is CLOSED

    Path mPath = new Path();
    RectF mRect = new RectF();
    Matrix mMatrix = new Matrix();
    private MarkerDrawable.MarkerAnimationListener mMarkerListener;

    public MarkerDrawable(@NonNull ColorStateList tintList, int closedSize) {
        super(tintList);
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        this.mClosedStateSize = closedSize;
        this.mStartColor = tintList.getColorForState(new int[]{attr.state_enabled, attr.state_pressed}, tintList.getDefaultColor());
        this.mEndColor = tintList.getDefaultColor();

    }

    public void setExternalOffset(int offset) {
        this.mExternalOffset = offset;
    }

    /**
     * The two colors that will be used for the seek thumb.
     *
     * @param startColor Color used for the seek thumb
     * @param endColor   Color used for popup indicator
     */
    public void setColors(int startColor, int endColor) {
        this.mStartColor = startColor;
        this.mEndColor = endColor;
    }

    @Override
    void doDraw(Canvas canvas, Paint paint) {
        if (!this.mPath.isEmpty()) {
            paint.setStyle(Style.FILL);
            int color = MarkerDrawable.blendColors(this.mStartColor, this.mEndColor, this.mCurrentScale);
            paint.setColor(color);
            canvas.drawPath(this.mPath, paint);
        }
    }

    public Path getPath() {
        return this.mPath;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.computePath(bounds);
    }

    private void computePath(Rect bounds) {
        float currentScale = this.mCurrentScale;
        Path path = this.mPath;
        RectF rect = this.mRect;
        Matrix matrix = this.mMatrix;

        path.reset();
        int totalSize = Math.min(bounds.width(), bounds.height());

        float initial = this.mClosedStateSize;
        float destination = totalSize;
        float currentSize = initial + (destination - initial) * currentScale;

        float halfSize = currentSize / 2f;
        float inverseScale = 1f - currentScale;
        float cornerSize = halfSize * inverseScale;
        float[] corners = {halfSize, halfSize, halfSize, halfSize, halfSize, halfSize, cornerSize, cornerSize};
        rect.set(bounds.left, bounds.top, bounds.left + currentSize, bounds.top + currentSize);
        path.addRoundRect(rect, corners, Direction.CCW);
        matrix.reset();
        matrix.postRotate(-45, bounds.left + halfSize, bounds.top + halfSize);
        matrix.postTranslate((bounds.width() - currentSize) / 2, 0);
        float hDiff = (bounds.bottom - currentSize - this.mExternalOffset) * inverseScale;
        matrix.postTranslate(0, hDiff);
        path.transform(matrix);
    }

    private void updateAnimation(float factor) {
        float initial = this.mAnimationInitialValue;
        float destination = this.mReverse ? 0f : 1f;
        this.mCurrentScale = initial + (destination - initial) * factor;
        this.computePath(this.getBounds());
        this.invalidateSelf();
    }

    public void animateToPressed() {
        this.unscheduleSelf(this.mUpdater);
        this.mReverse = false;
        if (this.mCurrentScale < 1) {
            this.mRunning = true;
            this.mAnimationInitialValue = this.mCurrentScale;
            float durationFactor = 1f - this.mCurrentScale;
            this.mDuration = (int) (MarkerDrawable.ANIMATION_DURATION * durationFactor);
            this.mStartTime = SystemClock.uptimeMillis();
            this.scheduleSelf(this.mUpdater, this.mStartTime + MarkerDrawable.FRAME_DURATION);
        } else {
            this.notifyFinishedToListener();
        }
    }

    public void animateToNormal() {
        this.mReverse = true;
        this.unscheduleSelf(this.mUpdater);
        if (this.mCurrentScale > 0) {
            this.mRunning = true;
            this.mAnimationInitialValue = this.mCurrentScale;
            float durationFactor = 1f - this.mCurrentScale;
            this.mDuration = MarkerDrawable.ANIMATION_DURATION - (int) (MarkerDrawable.ANIMATION_DURATION * durationFactor);
            this.mStartTime = SystemClock.uptimeMillis();
            this.scheduleSelf(this.mUpdater, this.mStartTime + MarkerDrawable.FRAME_DURATION);
        } else {
            this.notifyFinishedToListener();
        }
    }

    private final Runnable mUpdater = new Runnable() {

        @Override
        public void run() {

            long currentTime = SystemClock.uptimeMillis();
            long diff = currentTime - MarkerDrawable.this.mStartTime;
            if (diff < MarkerDrawable.this.mDuration) {
                float interpolation = MarkerDrawable.this.mInterpolator.getInterpolation((float) diff / (float) MarkerDrawable.this.mDuration);
                MarkerDrawable.this.scheduleSelf(MarkerDrawable.this.mUpdater, currentTime + MarkerDrawable.FRAME_DURATION);
                MarkerDrawable.this.updateAnimation(interpolation);
            } else {
                MarkerDrawable.this.unscheduleSelf(MarkerDrawable.this.mUpdater);
                MarkerDrawable.this.mRunning = false;
                MarkerDrawable.this.updateAnimation(1f);
                MarkerDrawable.this.notifyFinishedToListener();
            }
        }
    };

    public void setMarkerListener(MarkerDrawable.MarkerAnimationListener listener) {
        this.mMarkerListener = listener;
    }

    private void notifyFinishedToListener() {
        if (this.mMarkerListener != null) {
            if (this.mReverse) {
                this.mMarkerListener.onClosingComplete();
            } else {
                this.mMarkerListener.onOpeningComplete();
            }
        }
    }

    @Override
    public void start() {
        //No-Op. We control our own animation
    }

    @Override
    public void stop() {
        this.unscheduleSelf(this.mUpdater);
    }

    @Override
    public boolean isRunning() {
        return this.mRunning;
    }

    private static int blendColors(int color1, int color2, float factor) {
        float inverseFactor = 1f - factor;
        float a = (Color.alpha(color1) * factor) + (Color.alpha(color2) * inverseFactor);
        float r = (Color.red(color1) * factor) + (Color.red(color2) * inverseFactor);
        float g = (Color.green(color1) * factor) + (Color.green(color2) * inverseFactor);
        float b = (Color.blue(color1) * factor) + (Color.blue(color2) * inverseFactor);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }


    /**
     * A listener interface to porpagate animation events
     * This is the "poor's man" AnimatorListener for this Drawable
     */
    public interface MarkerAnimationListener {
        void onClosingComplete();

        void onOpeningComplete();
    }
}
