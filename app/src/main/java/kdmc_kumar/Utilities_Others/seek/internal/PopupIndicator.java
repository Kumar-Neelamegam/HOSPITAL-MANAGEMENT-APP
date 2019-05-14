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

package kdmc_kumar.Utilities_Others.seek.internal;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

import kdmc_kumar.Utilities_Others.seek.internal.compat.SeekBarCompat;
import kdmc_kumar.Utilities_Others.seek.internal.drawable.MarkerDrawable;
import kdmc_kumar.Utilities_Others.seek.internal.drawable.MarkerDrawable.MarkerAnimationListener;

/**
 * Class to manage the floating bubble thing, similar (but quite worse tested than {@link android.widget.PopupWindow}
 * <p/>
 * <p>
 * This will attach a View to the Window (full-width, measured-height, positioned just under the thumb)
 * </p>
 *
 * @hide
 * @see #showIndicator(View, Rect)
 * @see #dismiss()
 * @see #dismissComplete()
 * @see 
 */
public class PopupIndicator {

    private final WindowManager mWindowManager;
    private boolean mShowing;
    private final PopupIndicator.Floater mPopupView;
    //Outside listener for the DiscreteSeekBar to get MarkerDrawable animation events.
    //The whole chain of events goes this way:
    //MarkerDrawable->Marker->Floater->mListener->DiscreteSeekBar....
    //... phew!
    private MarkerAnimationListener mListener;
    private final int[] mDrawingLocation = new int[2];
    Point screenSize = new Point();

    public PopupIndicator(Context context, AttributeSet attrs, int defStyleAttr, String maxValue, int thumbSize, int separation) {
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mPopupView = new PopupIndicator.Floater(context, attrs, defStyleAttr, maxValue, thumbSize, separation);
    }

    public void updateSizes(String maxValue) {
        this.dismissComplete();
        if (this.mPopupView != null) {
            this.mPopupView.mMarker.resetSizes(maxValue);
        }
    }

    public void setListener(MarkerAnimationListener listener) {
        this.mListener = listener;
    }

    /**
     * We want the Floater to be full-width because the contents will be moved from side to side.
     * We may/should change this in the future to use just the PARENT View width and/or pass it in the constructor
     */
    private void measureFloater() {
        int specWidth = MeasureSpec.makeMeasureSpec(this.screenSize.x, MeasureSpec.EXACTLY);
        int specHeight = MeasureSpec.makeMeasureSpec(this.screenSize.y, MeasureSpec.AT_MOST);
        this.mPopupView.measure(specWidth, specHeight);
    }

    public void setValue(CharSequence value) {
        this.mPopupView.mMarker.setValue(value);
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public void showIndicator(View parent, Rect touchBounds) {
        if (this.isShowing()) {
            this.mPopupView.mMarker.animateOpen();
            return;
        }

        IBinder windowToken = parent.getWindowToken();
        if (windowToken != null) {
            LayoutParams p = this.createPopupLayout(windowToken);

            p.gravity = Gravity.TOP | GravityCompat.START;
            this.updateLayoutParamsForPosiion(parent, p, touchBounds.bottom);
            this.mShowing = true;

            this.translateViewIntoPosition(touchBounds.centerX());
            this.invokePopup(p);
        }
    }

    public void move(int x) {
        if (!this.isShowing()) {
            return;
        }
        this.translateViewIntoPosition(x);
    }

    public void setColors(int startColor, int endColor) {
        this.mPopupView.setColors(startColor, endColor);
    }

    /**
     * This will start the closing animation of the Marker and call onClosingComplete when finished
     */
    public void dismiss() {
        this.mPopupView.mMarker.animateClose();
    }

    /**
     * FORCE the popup window to be removed.
     * You typically calls this when the parent view is being removed from the window to avoid a Window Leak
     */
    public void dismissComplete() {
        if (this.isShowing()) {
            this.mShowing = false;
            try {
                this.mWindowManager.removeViewImmediate(this.mPopupView);
            } finally {
            }
        }
    }

    private void updateLayoutParamsForPosiion(View anchor, LayoutParams p, int yOffset) {
        DisplayMetrics displayMetrics = anchor.getResources().getDisplayMetrics();
        this.screenSize.set(displayMetrics.widthPixels, displayMetrics.heightPixels);

        this.measureFloater();
        int measuredHeight = this.mPopupView.getMeasuredHeight();
        int paddingBottom = this.mPopupView.mMarker.getPaddingBottom();
        anchor.getLocationInWindow(this.mDrawingLocation);
        p.x = 0;
        p.y = this.mDrawingLocation[1] - measuredHeight + yOffset + paddingBottom;
        p.width = this.screenSize.x;
        p.height = measuredHeight;
    }

    private void translateViewIntoPosition(int x) {
        this.mPopupView.setFloatOffset(x + this.mDrawingLocation[0]);
    }

    private void invokePopup(LayoutParams p) {
        this.mWindowManager.addView(this.mPopupView, p);
        this.mPopupView.mMarker.animateOpen();
    }

    private LayoutParams createPopupLayout(IBinder token) {
        LayoutParams p = new LayoutParams();
        p.gravity = Gravity.START | Gravity.TOP;
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        p.format = PixelFormat.TRANSLUCENT;
        p.flags = this.computeFlags(p.flags);
        p.type = LayoutParams.TYPE_APPLICATION_PANEL;
        p.token = token;
        p.softInputMode = LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        p.setTitle("DiscreteSeekBar Indicator:" + Integer.toHexString(this.hashCode()));

        return p;
    }

    /**
     * I'm NOT completely sure how all this bitwise things work...
     *
     * @param curFlags
     * @return
     */
    private int computeFlags(int curFlags) {
        curFlags &= ~(
                LayoutParams.FLAG_IGNORE_CHEEK_PRESSES |
                        LayoutParams.FLAG_NOT_FOCUSABLE |
                        LayoutParams.FLAG_NOT_TOUCHABLE |
                        LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        curFlags |= LayoutParams.FLAG_IGNORE_CHEEK_PRESSES;
        curFlags |= LayoutParams.FLAG_NOT_FOCUSABLE;
        curFlags |= LayoutParams.FLAG_NOT_TOUCHABLE;
        curFlags |= LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        return curFlags;
    }

    /**
     * Small FrameLayout class to hold and move the bubble around when requested
     * I wanted to use the {@link } directly
     * but doing so would make some things harder to implement
     * (like moving the marker around, having the Marker's outline to work, etc)
     */
    private class Floater extends FrameLayout implements MarkerAnimationListener {
        private final Marker mMarker;
        private int mOffset;

        public Floater(Context context, AttributeSet attrs, int defStyleAttr, String maxValue, int thumbSize, int separation) {
            super(context);
            this.mMarker = new  Marker(context, attrs, defStyleAttr, maxValue, thumbSize, separation);
            this.addView(this.mMarker, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            this.measureChildren(widthMeasureSpec, heightMeasureSpec);
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSie = this.mMarker.getMeasuredHeight();
            this.setMeasuredDimension(widthSize, heightSie);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            int centerDiffX = this.mMarker.getMeasuredWidth() / 2;
            int offset = (this.mOffset - centerDiffX);
            this.mMarker.layout(offset, 0, offset + this.mMarker.getMeasuredWidth(), this.mMarker.getMeasuredHeight());
        }

        public void setFloatOffset(int x) {
            this.mOffset = x;
            int centerDiffX = this.mMarker.getMeasuredWidth() / 2;
            int offset = (x - centerDiffX);
            this.mMarker.offsetLeftAndRight(offset - this.mMarker.getLeft());
            //Without hardware acceleration (or API levels<11), offsetting a view seems to NOT invalidate the proper area.
            //We should calc the proper invalidate Rect but this will be for now...
            if (!SeekBarCompat.isHardwareAccelerated(this)) {
                this.invalidate();
            }
        }

        @Override
        public void onClosingComplete() {
            if (PopupIndicator.this.mListener != null) {
                PopupIndicator.this.mListener.onClosingComplete();
            }
            PopupIndicator.this.dismissComplete();
        }

        @Override
        public void onOpeningComplete() {
            if (PopupIndicator.this.mListener != null) {
                PopupIndicator.this.mListener.onOpeningComplete();
            }
        }

        public void setColors(int startColor, int endColor) {
            this.mMarker.setColors(startColor, endColor);
        }
    }

}
