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

package kdmc_kumar.Utilities_Others.seek;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import java.util.Formatter;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.attr;
import displ.mobydocmarathi.com.R.style;
import displ.mobydocmarathi.com.R.styleable;
import kdmc_kumar.Utilities_Others.seek.internal.PopupIndicator;
import kdmc_kumar.Utilities_Others.seek.internal.compat.AnimatorCompat;
import kdmc_kumar.Utilities_Others.seek.internal.compat.AnimatorCompat.AnimationFrameUpdateListener;
import kdmc_kumar.Utilities_Others.seek.internal.compat.SeekBarCompat;
import kdmc_kumar.Utilities_Others.seek.internal.drawable.MarkerDrawable;
import kdmc_kumar.Utilities_Others.seek.internal.drawable.MarkerDrawable.MarkerAnimationListener;
import kdmc_kumar.Utilities_Others.seek.internal.drawable.ThumbDrawable;
import kdmc_kumar.Utilities_Others.seek.internal.drawable.TrackRectDrawable;

public class DiscreteSeekBar extends View {

    /**
     * Interface to propagate seekbar change event
     */
    public interface OnProgressChangeListener {
        /**
         * When the {@link DiscreteSeekBar} value changes
         *
         * @param seekBar  The DiscreteSeekBar
         * @param value    the new value
         * @param fromUser if the change was made from the user or not (i.e. the developer calling {@link #setProgress(int)}
         */
        void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser);

        void onStartTrackingTouch(DiscreteSeekBar seekBar);

        void onStopTrackingTouch(DiscreteSeekBar seekBar);
    }

    /**
     * Interface to transform the current internal value of this DiscreteSeekBar to anther one for the visualization.
     * <p/>
     * This will be used on the floating bubble to display a different value if needed.
     * <p/>
     * Using this in conjunction with {@link #setIndicatorFormatter(String)} you will be able to manipulate the
     * value seen by the user
     *
     * @see #setIndicatorFormatter(String)
     * @see #setNumericTransformer(DiscreteSeekBar.NumericTransformer)
     */
    public abstract static class NumericTransformer {
        /**
         * Return the desired value to be shown to the user.
         * This value will be formatted using the format specified by {@link #setIndicatorFormatter} before displaying it
         *
         * @param value The value to be transformed
         * @return The transformed int
         */
        public abstract int transform(int value);

        /**
         * Return the desired value to be shown to the user.
         * This value will be displayed 'as is' without further formatting.
         *
         * @param value The value to be transformed
         * @return A formatted string
         */
        public String transformToString(int value) {
            return String.valueOf(value);
        }

        /**
         * Used to indicate which transform will be used. If this method returns true,
         * {@link #transformToString(int)} will be used, otherwise {@link #transform(int)}
         * will be used
         */
        public boolean useStringTransform() {
            return false;
        }
    }


    private static class DefaultNumericTransformer extends DiscreteSeekBar.NumericTransformer {

        @Override
        public int transform(int value) {
            return value;
        }
    }


    private static final boolean isLollipopOrGreater = VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP;
    //We want to always use a formatter so the indicator numbers are "translated" to specific locales.
    private static final String DEFAULT_FORMATTER = "%d";

    private static final int PRESSED_STATE = android.R.attr.state_pressed;
    private static final int FOCUSED_STATE = android.R.attr.state_focused;
    private static final int PROGRESS_ANIMATION_DURATION = 250;
    private static final int INDICATOR_DELAY_FOR_TAPS = 150;
    private static final int DEFAULT_THUMB_COLOR = 0xff009688;
    private static final int SEPARATION_DP = 5;
    private final ThumbDrawable mThumb;
    private final TrackRectDrawable mTrack;
    private final TrackRectDrawable mScrubber;
    private final Drawable mRipple;

    private final int mTrackHeight;
    private final int mScrubberHeight;
    private final int mAddedTouchBounds;

    private int mMax;
    private int mMin;
    private int mValue;
    private int mKeyProgressIncrement = 1;
    private boolean mMirrorForRtl;
    private boolean mAllowTrackClick = true;
    private boolean mIndicatorPopupEnabled = true;
    //We use our own Formatter to avoid creating new instances on every progress change
    Formatter mFormatter;
    private String mIndicatorFormatter;
    private DiscreteSeekBar.NumericTransformer mNumericTransformer;
    private StringBuilder mFormatBuilder;
    private DiscreteSeekBar.OnProgressChangeListener mPublicChangeListener;
    private boolean mIsDragging;
    private int mDragOffset;

    private final Rect mInvalidateRect = new Rect();
    private final Rect mTempRect = new Rect();
    private PopupIndicator mIndicator;
    private AnimatorCompat mPositionAnimator;
    private float mAnimationPosition;
    private int mAnimationTarget;
    private float mDownX;
    private final float mTouchSlop;

    public DiscreteSeekBar(Context context) {
        this(context, null);
    }

    public DiscreteSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, attr.discreteSeekBarStyle);
    }

    public DiscreteSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setFocusable(true);
        this.setWillNotDraw(false);

        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        float density = context.getResources().getDisplayMetrics().density;

        TypedArray a = context.obtainStyledAttributes(attrs, styleable.DiscreteSeekBar,
                defStyleAttr, style.Widget_DiscreteSeekBar);

        int max = 100;
        int min = 0;
        int value = 0;
        this.mMirrorForRtl = a.getBoolean(styleable.DiscreteSeekBar_dsb_mirrorForRtl, this.mMirrorForRtl);
        this.mAllowTrackClick = a.getBoolean(styleable.DiscreteSeekBar_dsb_allowTrackClickToDrag, this.mAllowTrackClick);
        this.mIndicatorPopupEnabled = a.getBoolean(styleable.DiscreteSeekBar_dsb_indicatorPopupEnabled, this.mIndicatorPopupEnabled);
        this.mTrackHeight = a.getDimensionPixelSize(styleable.DiscreteSeekBar_dsb_trackHeight, (int) (1 * density));
        this.mScrubberHeight = a.getDimensionPixelSize(styleable.DiscreteSeekBar_dsb_scrubberHeight, (int) (4 * density));
        int thumbSize = a.getDimensionPixelSize(styleable.DiscreteSeekBar_dsb_thumbSize, (int) (density * ThumbDrawable.DEFAULT_SIZE_DP));
        int separation = a.getDimensionPixelSize(styleable.DiscreteSeekBar_dsb_indicatorSeparation,
                (int) (DiscreteSeekBar.SEPARATION_DP * density));

        //Extra pixels for a minimum touch area of 32dp
        int touchBounds = (int) (density * 32);
        this.mAddedTouchBounds = Math.max(0, (touchBounds - thumbSize) / 2);

        int indexMax = styleable.DiscreteSeekBar_dsb_max;
        int indexMin = styleable.DiscreteSeekBar_dsb_min;
        int indexValue = styleable.DiscreteSeekBar_dsb_value;
        TypedValue out = new TypedValue();
        //Not sure why, but we wanted to be able to use dimensions here...
        if (a.getValue(indexMax, out)) {
            if (out.type == TypedValue.TYPE_DIMENSION) {
                max = a.getDimensionPixelSize(indexMax, max);
            } else {
                max = a.getInteger(indexMax, max);
            }
        }
        if (a.getValue(indexMin, out)) {
            if (out.type == TypedValue.TYPE_DIMENSION) {
                min = a.getDimensionPixelSize(indexMin, min);
            } else {
                min = a.getInteger(indexMin, min);
            }
        }
        if (a.getValue(indexValue, out)) {
            if (out.type == TypedValue.TYPE_DIMENSION) {
                value = a.getDimensionPixelSize(indexValue, value);
            } else {
                value = a.getInteger(indexValue, value);
            }
        }

        this.mMin = min;
        this.mMax = Math.max(min + 1, max);
        this.mValue = Math.max(min, Math.min(max, value));
        this.updateKeyboardRange();

        this.mIndicatorFormatter = a.getString(styleable.DiscreteSeekBar_dsb_indicatorFormatter);

        ColorStateList trackColor = a.getColorStateList(styleable.DiscreteSeekBar_dsb_trackColor);
        ColorStateList progressColor = a.getColorStateList(styleable.DiscreteSeekBar_dsb_progressColor);
        ColorStateList rippleColor = a.getColorStateList(styleable.DiscreteSeekBar_dsb_rippleColor);
        boolean editMode = this.isInEditMode();
        if (editMode || rippleColor == null) {
            rippleColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.DKGRAY});
        }
        if (editMode || trackColor == null) {
            trackColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.GRAY});
        }
        if (editMode || progressColor == null) {
            progressColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{DiscreteSeekBar.DEFAULT_THUMB_COLOR});
        }

        this.mRipple = SeekBarCompat.getRipple(rippleColor);
        if (DiscreteSeekBar.isLollipopOrGreater) {
            SeekBarCompat.setBackground(this, this.mRipple);
        } else {
            this.mRipple.setCallback(this);
        }

        TrackRectDrawable shapeDrawable = new TrackRectDrawable(trackColor);
        this.mTrack = shapeDrawable;
        this.mTrack.setCallback(this);

        shapeDrawable = new TrackRectDrawable(progressColor);
        this.mScrubber = shapeDrawable;
        this.mScrubber.setCallback(this);

        this.mThumb = new ThumbDrawable(progressColor, thumbSize);
        this.mThumb.setCallback(this);
        this.mThumb.setBounds(0, 0, this.mThumb.getIntrinsicWidth(), this.mThumb.getIntrinsicHeight());


        if (!editMode) {
            this.mIndicator = new PopupIndicator(context, attrs, defStyleAttr, this.convertValueToMessage(this.mMax),
                    thumbSize, thumbSize + this.mAddedTouchBounds + separation);
            this.mIndicator.setListener(this.mFloaterListener);
        }
        a.recycle();

        this.setNumericTransformer(new DiscreteSeekBar.DefaultNumericTransformer());

    }

    /**
     * Sets the current Indicator formatter string
     *
     * @param formatter
     * @see String#format(String, Object...)
     * @see #setNumericTransformer(DiscreteSeekBar.NumericTransformer)
     */
    public void setIndicatorFormatter(@Nullable String formatter) {
        this.mIndicatorFormatter = formatter;
        this.updateProgressMessage(this.mValue);
    }

    /**
     * Sets the current {@link DiscreteSeekBar.NumericTransformer}
     *
     * @param transformer
     * @see #getNumericTransformer()
     */
    public void setNumericTransformer(@Nullable DiscreteSeekBar.NumericTransformer transformer) {
        this.mNumericTransformer = transformer != null ? transformer : new DiscreteSeekBar.DefaultNumericTransformer();
        //We need to refresh the PopupIndicator view
        this.updateIndicatorSizes();
        this.updateProgressMessage(this.mValue);
    }

    /**
     * Retrieves the current {@link DiscreteSeekBar.NumericTransformer}
     *
     * @return NumericTransformer
     * @see #setNumericTransformer
     */
    public DiscreteSeekBar.NumericTransformer getNumericTransformer() {
        return this.mNumericTransformer;
    }

    /**
     * Sets the maximum value for this DiscreteSeekBar
     * if the supplied argument is smaller than the Current MIN value,
     * the MIN value will be set to MAX-1
     * <p/>
     * <p>
     * Also if the current progress is out of the new range, it will be set to MIN
     * </p>
     *
     * @param max
     * @see #setMin(int)
     * @see #setProgress(int)
     */
    public void setMax(int max) {
        this.mMax = max;
        if (this.mMax < this.mMin) {
            this.setMin(this.mMax - 1);
        }
        this.updateKeyboardRange();

        if (this.mValue < this.mMin || this.mValue > this.mMax) {
            this.setProgress(this.mMin);
        }
        //We need to refresh the PopupIndicator view
        this.updateIndicatorSizes();
    }

    public int getMax() {
        return this.mMax;
    }

    /**
     * Sets the minimum value for this DiscreteSeekBar
     * if the supplied argument is bigger than the Current MAX value,
     * the MAX value will be set to MIN+1
     * <p>
     * Also if the current progress is out of the new range, it will be set to MIN
     * </p>
     *
     * @param min
     * @see #setMax(int)
     * @see #setProgress(int)
     */
    public void setMin(int min) {
        this.mMin = min;
        if (this.mMin > this.mMax) {
            this.setMax(this.mMin + 1);
        }
        this.updateKeyboardRange();

        if (this.mValue < this.mMin || this.mValue > this.mMax) {
            this.setProgress(this.mMin);
        }
    }

    public int getMin() {
        return this.mMin;
    }

    /**
     * Sets the current progress for this DiscreteSeekBar
     * The supplied argument will be capped to the current MIN-MAX range
     *
     * @param progress
     * @see #setMax(int)
     * @see #setMin(int)
     */
    public void setProgress(int progress) {
        this.setProgress(progress, false);
    }

    private void setProgress(int value, boolean fromUser) {
        value = Math.max(this.mMin, Math.min(this.mMax, value));
        if (this.isAnimationRunning()) {
            this.mPositionAnimator.cancel();
        }

        if (this.mValue != value) {
            this.mValue = value;
            this.notifyProgress(value, fromUser);
            this.updateProgressMessage(value);
            this.updateThumbPosFromCurrentProgress();
        }
    }

    /**
     * Get the current progress
     *
     * @return the current progress :-P
     */
    public int getProgress() {
        return this.mValue;
    }

    /**
     * Sets a listener to receive notifications of changes to the DiscreteSeekBar's progress level. Also
     * provides notifications of when the DiscreteSeekBar shows/hides the bubble indicator.
     *
     * @param listener The seek bar notification listener
     * @see DiscreteSeekBar.OnProgressChangeListener
     */
    public void setOnProgressChangeListener(@Nullable DiscreteSeekBar.OnProgressChangeListener listener) {
        this.mPublicChangeListener = listener;
    }

    /**
     * Sets the color of the seek thumb, as well as the color of the popup indicator.
     *
     * @param thumbColor     The color the seek thumb will be changed to
     * @param indicatorColor The color the popup indicator will be changed to
     *                       The indicator will animate from thumbColor to indicatorColor
     *                       when opening
     */
    public void setThumbColor(int thumbColor, int indicatorColor) {
        this.mThumb.setColorStateList(ColorStateList.valueOf(thumbColor));
        this.mIndicator.setColors(indicatorColor, thumbColor);
    }

    /**
     * Sets the color of the seek thumb, as well as the color of the popup indicator.
     *
     * @param thumbColorStateList The ColorStateList the seek thumb will be changed to
     * @param indicatorColor      The color the popup indicator will be changed to
     *                            The indicator will animate from thumbColorStateList(pressed state) to indicatorColor
     *                            when opening
     */
    public void setThumbColor(@NonNull ColorStateList thumbColorStateList, int indicatorColor) {
        this.mThumb.setColorStateList(thumbColorStateList);
        //we use the "pressed" color to morph the indicator from it to its own color
        int thumbColor = thumbColorStateList.getColorForState(new int[]{DiscreteSeekBar.PRESSED_STATE}, thumbColorStateList.getDefaultColor());
        this.mIndicator.setColors(indicatorColor, thumbColor);
    }

    /**
     * Sets the color of the seekbar scrubber
     *
     * @param color The color the track  scrubber will be changed to
     */
    public void setScrubberColor(int color) {
        this.mScrubber.setColorStateList(ColorStateList.valueOf(color));
    }

    /**
     * Sets the color of the seekbar scrubber
     *
     * @param colorStateList The ColorStateList the track scrubber will be changed to
     */
    public void setScrubberColor(@NonNull ColorStateList colorStateList) {
        this.mScrubber.setColorStateList(colorStateList);
    }

    /**
     * Sets the color of the seekbar ripple
     *
     * @param color The color the track  ripple will be changed to
     */
    public void setRippleColor(int color) {
        this.setRippleColor(new ColorStateList(new int[][]{new int[]{}}, new int[]{color}));
    }

    /**
     * Sets the color of the seekbar ripple
     *
     * @param colorStateList The ColorStateList the track ripple will be changed to
     */
    public void setRippleColor(@NonNull ColorStateList colorStateList) {
        SeekBarCompat.setRippleColor(this.mRipple, colorStateList);
    }

    /**
     * Sets the color of the seekbar scrubber
     *
     * @param color The color the track will be changed to
     */
    public void setTrackColor(int color) {
        this.mTrack.setColorStateList(ColorStateList.valueOf(color));
    }

    /**
     * Sets the color of the seekbar scrubber
     *
     * @param colorStateList The ColorStateList the track will be changed to
     */
    public void setTrackColor(@NonNull ColorStateList colorStateList) {
        this.mTrack.setColorStateList(colorStateList);
    }

    /**
     * If {@code enabled} is false the indicator won't appear. By default popup indicator is
     * enabled.
     */
    public void setIndicatorPopupEnabled(boolean enabled) {
        mIndicatorPopupEnabled = enabled;
    }

    private void updateIndicatorSizes() {
        if (!this.isInEditMode()) {
            if (this.mNumericTransformer.useStringTransform()) {
                this.mIndicator.updateSizes(this.mNumericTransformer.transformToString(this.mMax));
            } else {
                this.mIndicator.updateSizes(this.convertValueToMessage(this.mNumericTransformer.transform(this.mMax)));
            }
        }

    }

    private void notifyProgress(int value, boolean fromUser) {
        if (this.mPublicChangeListener != null) {
            this.mPublicChangeListener.onProgressChanged(this, value, fromUser);
        }
        this.onValueChanged(value);
    }

    private void notifyBubble(boolean open) {
        if (open) {
            this.onShowBubble();
        } else {
            this.onHideBubble();
        }
    }

    /**
     * When the {@link DiscreteSeekBar} enters pressed or focused state
     * the bubble with the value will be shown, and this method called
     * <p>
     * Subclasses may override this to add functionality around this event
     * </p>
     */
    protected void onShowBubble() {
    }

    /**
     * When the {@link DiscreteSeekBar} exits pressed or focused state
     * the bubble with the value will be hidden, and this method called
     * <p>
     * Subclasses may override this to add functionality around this event
     * </p>
     */
    protected void onHideBubble() {
    }

    /**
     * When the {@link DiscreteSeekBar} value changes this method is called
     * <p>
     * Subclasses may override this to add functionality around this event
     * without having to specify a {@link DiscreteSeekBar.OnProgressChangeListener}
     * </p>
     */
    protected void onValueChanged(int value) {
    }

    private void updateKeyboardRange() {
        int range = this.mMax - this.mMin;
        if ((this.mKeyProgressIncrement == 0) || (range / this.mKeyProgressIncrement > 20)) {
            // It will take the user too long to change this via keys, change it
            // to something more reasonable
            this.mKeyProgressIncrement = Math.max(1, Math.round((float) range / 20));
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = this.mThumb.getIntrinsicHeight() + this.getPaddingTop() + this.getPaddingBottom();
        height += (this.mAddedTouchBounds * 2);
        this.setMeasuredDimension(widthSize, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            this.removeCallbacks(this.mShowIndicatorRunnable);
            if (!this.isInEditMode()) {
                this.mIndicator.dismissComplete();
            }
            this.updateFromDrawableState();
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        super.scheduleDrawable(who, what, when);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int thumbWidth = this.mThumb.getIntrinsicWidth();
        int thumbHeight = this.mThumb.getIntrinsicHeight();
        int addedThumb = this.mAddedTouchBounds;
        int halfThumb = thumbWidth / 2;
        int paddingLeft = this.getPaddingLeft() + addedThumb;
        int paddingRight = this.getPaddingRight();
        int bottom = this.getHeight() - this.getPaddingBottom() - addedThumb;
        this.mThumb.setBounds(paddingLeft, bottom - thumbHeight, paddingLeft + thumbWidth, bottom);
        int trackHeight = Math.max(this.mTrackHeight / 2, 1);
        this.mTrack.setBounds(paddingLeft + halfThumb, bottom - halfThumb - trackHeight,
                this.getWidth() - halfThumb - paddingRight - addedThumb, bottom - halfThumb + trackHeight);
        int scrubberHeight = Math.max(this.mScrubberHeight / 2, 2);
        this.mScrubber.setBounds(paddingLeft + halfThumb, bottom - halfThumb - scrubberHeight,
                paddingLeft + halfThumb, bottom - halfThumb + scrubberHeight);

        //Update the thumb position after size changed
        this.updateThumbPosFromCurrentProgress();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (!DiscreteSeekBar.isLollipopOrGreater) {
            this.mRipple.draw(canvas);
        }
        super.onDraw(canvas);
        this.mTrack.draw(canvas);
        this.mScrubber.draw(canvas);
        this.mThumb.draw(canvas);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.updateFromDrawableState();
    }

    private void updateFromDrawableState() {
        int[] state = this.getDrawableState();
        boolean focused = false;
        boolean pressed = false;
        for (int i : state) {
            if (i == DiscreteSeekBar.FOCUSED_STATE) {
                focused = true;
            } else if (i == DiscreteSeekBar.PRESSED_STATE) {
                pressed = true;
            }
        }
        if (this.isEnabled() && (focused || pressed) && this.mIndicatorPopupEnabled) {
            //We want to add a small delay here to avoid
            //poping in/out on simple taps
            this.removeCallbacks(this.mShowIndicatorRunnable);
            this.postDelayed(this.mShowIndicatorRunnable, DiscreteSeekBar.INDICATOR_DELAY_FOR_TAPS);
        } else {
            this.hideFloater();
        }
        this.mThumb.setState(state);
        this.mTrack.setState(state);
        this.mScrubber.setState(state);
        this.mRipple.setState(state);
    }

    private void updateProgressMessage(int value) {
        if (!this.isInEditMode()) {
            if (this.mNumericTransformer.useStringTransform()) {
                this.mIndicator.setValue(this.mNumericTransformer.transformToString(value));
            } else {
                this.mIndicator.setValue(this.convertValueToMessage(this.mNumericTransformer.transform(value)));
            }
        }
    }

    private String convertValueToMessage(int value) {
        String format = this.mIndicatorFormatter != null ? this.mIndicatorFormatter : DiscreteSeekBar.DEFAULT_FORMATTER;
        //We're trying to re-use the Formatter here to avoid too much memory allocations
        //But I'm not completey sure if it's doing anything good... :(
        //Previously, this condition was wrong so the Formatter was always re-created
        //But as I fixed the condition, the formatter started outputting trash characters from previous
        //calls, so I mark the StringBuilder as empty before calling format again.

        //Anyways, I see the memory usage still go up on every call to this method
        //and I have no clue on how to fix that... damn Strings...
        if (this.mFormatter == null || !this.mFormatter.locale().equals(Locale.getDefault())) {
            int bufferSize = format.length() + String.valueOf(this.mMax).length();
            if (this.mFormatBuilder == null) {
                this.mFormatBuilder = new StringBuilder(bufferSize);
            } else {
                this.mFormatBuilder.ensureCapacity(bufferSize);
            }
            this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
        } else {
            this.mFormatBuilder.setLength(0);
        }
        return this.mFormatter.format(format, value).toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isEnabled()) {
            return false;
        }
        int actionMasked = MotionEventCompat.getActionMasked(event);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                this.mDownX = event.getX();
                this.startDragging(event, this.isInScrollingContainer());
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.isDragging()) {
                    this.updateDragging(event);
                } else {
                    float x = event.getX();
                    if (Math.abs(x - this.mDownX) > this.mTouchSlop) {
                        this.startDragging(event, false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!this.isDragging() && this.mAllowTrackClick) {
                    this.startDragging(event, false);
                    this.updateDragging(event);
                }
                this.stopDragging();
                break;
            case MotionEvent.ACTION_CANCEL:
                this.stopDragging();
                break;
        }
        return true;
    }

    private boolean isInScrollingContainer() {
        return SeekBarCompat.isInScrollingContainer(this.getParent());
    }

    private boolean startDragging(MotionEvent ev, boolean ignoreTrackIfInScrollContainer) {
        Rect bounds = this.mTempRect;
        this.mThumb.copyBounds(bounds);
        //Grow the current thumb rect for a bigger touch area
        bounds.inset(-this.mAddedTouchBounds, -this.mAddedTouchBounds);
        this.mIsDragging = (bounds.contains((int) ev.getX(), (int) ev.getY()));
        if (!this.mIsDragging && this.mAllowTrackClick && !ignoreTrackIfInScrollContainer) {
            //If the user clicked outside the thumb, we compute the current position
            //and force an immediate drag to it.
            this.mIsDragging = true;
            this.mDragOffset = (bounds.width() / 2) - this.mAddedTouchBounds;
            this.updateDragging(ev);
            //As the thumb may have moved, get the bounds again
            this.mThumb.copyBounds(bounds);
            bounds.inset(-this.mAddedTouchBounds, -this.mAddedTouchBounds);
        }
        if (this.mIsDragging) {
            this.setPressed(true);
            this.attemptClaimDrag();
            this.setHotspot(ev.getX(), ev.getY());
            this.mDragOffset = (int) (ev.getX() - bounds.left - this.mAddedTouchBounds);
            if (this.mPublicChangeListener != null) {
                this.mPublicChangeListener.onStartTrackingTouch(this);
            }
        }
        return this.mIsDragging;
    }

    private boolean isDragging() {
        return this.mIsDragging;
    }

    private void stopDragging() {
        if (this.mPublicChangeListener != null) {
            this.mPublicChangeListener.onStopTrackingTouch(this);
        }
        this.mIsDragging = false;
        this.setPressed(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //TODO: Should we reverse the keys for RTL? The framework's SeekBar does NOT....
        boolean handled = false;
        if (this.isEnabled()) {
            int progress = this.getAnimatedProgress();
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    handled = true;
                    if (progress <= this.mMin) break;
                    this.animateSetProgress(progress - this.mKeyProgressIncrement);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    handled = true;
                    if (progress >= this.mMax) break;
                    this.animateSetProgress(progress + this.mKeyProgressIncrement);
                    break;
            }
        }

        return handled || super.onKeyDown(keyCode, event);
    }

    private int getAnimatedProgress() {
        return this.isAnimationRunning() ? this.getAnimationTarget() : this.mValue;
    }


    boolean isAnimationRunning() {
        return this.mPositionAnimator != null && this.mPositionAnimator.isRunning();
    }

    void animateSetProgress(int progress) {
        float curProgress = this.isAnimationRunning() ? this.getAnimationPosition() : this.getProgress();

        if (progress < this.mMin) {
            progress = this.mMin;
        } else if (progress > this.mMax) {
            progress = this.mMax;
        }
        //setProgressValueOnly(progress);

        if (this.mPositionAnimator != null) {
            this.mPositionAnimator.cancel();
        }

        this.mAnimationTarget = progress;
        this.mPositionAnimator = AnimatorCompat.create(curProgress,
                progress, new AnimationFrameUpdateListener() {
                    @Override
                    public void onAnimationFrame(float currentValue) {
                        DiscreteSeekBar.this.setAnimationPosition(currentValue);
                    }
                });
        this.mPositionAnimator.setDuration(DiscreteSeekBar.PROGRESS_ANIMATION_DURATION);
        this.mPositionAnimator.start();
    }

    private int getAnimationTarget() {
        return this.mAnimationTarget;
    }

    void setAnimationPosition(float position) {
        this.mAnimationPosition = position;
        float currentScale = (position - this.mMin) / (float) (this.mMax - this.mMin);
        this.updateProgressFromAnimation(currentScale);
    }

    float getAnimationPosition() {
        return this.mAnimationPosition;
    }


    private void updateDragging(MotionEvent ev) {
        this.setHotspot(ev.getX(), ev.getY());
        int x = (int) ev.getX();
        Rect oldBounds = this.mThumb.getBounds();
        int halfThumb = oldBounds.width() / 2;
        int addedThumb = this.mAddedTouchBounds;
        int newX = x - this.mDragOffset + halfThumb;
        int left = this.getPaddingLeft() + halfThumb + addedThumb;
        int right = this.getWidth() - (this.getPaddingRight() + halfThumb + addedThumb);
        if (newX < left) {
            newX = left;
        } else if (newX > right) {
            newX = right;
        }

        int available = right - left;
        float scale = (float) (newX - left) / (float) available;
        if (this.isRtl()) {
            scale = 1f - scale;
        }
        int progress = Math.round((scale * (this.mMax - this.mMin)) + this.mMin);
        this.setProgress(progress, true);
    }

    private void updateProgressFromAnimation(float scale) {
        Rect bounds = this.mThumb.getBounds();
        int halfThumb = bounds.width() / 2;
        int addedThumb = this.mAddedTouchBounds;
        int left = this.getPaddingLeft() + halfThumb + addedThumb;
        int right = this.getWidth() - (this.getPaddingRight() + halfThumb + addedThumb);
        int available = right - left;
        int progress = Math.round((scale * (this.mMax - this.mMin)) + this.mMin);
        //we don't want to just call setProgress here to avoid the animation being cancelled,
        //and this position is not bound to a real progress value but interpolated
        if (progress != this.getProgress()) {
            this.mValue = progress;
            this.notifyProgress(this.mValue, true);
            this.updateProgressMessage(progress);
        }
        int thumbPos = (int) (scale * available + 0.5f);
        this.updateThumbPos(thumbPos);
    }

    private void updateThumbPosFromCurrentProgress() {
        int thumbWidth = this.mThumb.getIntrinsicWidth();
        int addedThumb = this.mAddedTouchBounds;
        int halfThumb = thumbWidth / 2;
        float scaleDraw = (this.mValue - this.mMin) / (float) (this.mMax - this.mMin);

        //This doesn't matter if RTL, as we just need the "avaiable" area
        int left = this.getPaddingLeft() + halfThumb + addedThumb;
        int right = this.getWidth() - (this.getPaddingRight() + halfThumb + addedThumb);
        int available = right - left;

        int thumbPos = (int) (scaleDraw * available + 0.5f);
        this.updateThumbPos(thumbPos);
    }

    private void updateThumbPos(int posX) {
        int thumbWidth = this.mThumb.getIntrinsicWidth();
        int halfThumb = thumbWidth / 2;
        int start;
        if (this.isRtl()) {
            start = this.getWidth() - this.getPaddingRight() - this.mAddedTouchBounds;
            posX = start - posX - thumbWidth;
        } else {
            start = this.getPaddingLeft() + this.mAddedTouchBounds;
            posX = start + posX;
        }
        this.mThumb.copyBounds(this.mInvalidateRect);
        this.mThumb.setBounds(posX, this.mInvalidateRect.top, posX + thumbWidth, this.mInvalidateRect.bottom);
        if (this.isRtl()) {
            this.mScrubber.getBounds().right = start - halfThumb;
            this.mScrubber.getBounds().left = posX + halfThumb;
        } else {
            this.mScrubber.getBounds().left = start + halfThumb;
            this.mScrubber.getBounds().right = posX + halfThumb;
        }
        Rect finalBounds = this.mTempRect;
        this.mThumb.copyBounds(finalBounds);
        if (!this.isInEditMode()) {
            this.mIndicator.move(finalBounds.centerX());
        }

        this.mInvalidateRect.inset(-this.mAddedTouchBounds, -this.mAddedTouchBounds);
        finalBounds.inset(-this.mAddedTouchBounds, -this.mAddedTouchBounds);
        this.mInvalidateRect.union(finalBounds);
        SeekBarCompat.setHotspotBounds(this.mRipple, finalBounds.left, finalBounds.top, finalBounds.right, finalBounds.bottom);
        this.invalidate(this.mInvalidateRect);
    }


    private void setHotspot(float x, float y) {
        DrawableCompat.setHotspot(this.mRipple, x, y);
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == this.mThumb || who == this.mTrack || who == this.mScrubber || who == this.mRipple || super.verifyDrawable(who);
    }

    private void attemptClaimDrag() {
        ViewParent parent = this.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private final Runnable mShowIndicatorRunnable = new Runnable() {
        @Override
        public void run() {
            DiscreteSeekBar.this.showFloater();
        }
    };

    private void showFloater() {
        if (!this.isInEditMode()) {
            this.mThumb.animateToPressed();
            this.mIndicator.showIndicator(this, this.mThumb.getBounds());
            this.notifyBubble(true);
        }
    }

    private void hideFloater() {
        this.removeCallbacks(this.mShowIndicatorRunnable);
        if (!this.isInEditMode()) {
            this.mIndicator.dismiss();
            this.notifyBubble(false);
        }
    }

    private final MarkerAnimationListener mFloaterListener = new MarkerAnimationListener() {
        @Override
        public void onClosingComplete() {
            DiscreteSeekBar.this.mThumb.animateToNormal();
        }

        @Override
        public void onOpeningComplete() {

        }

    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mShowIndicatorRunnable);
        if (!this.isInEditMode()) {
            this.mIndicator.dismissComplete();
        }
    }

    public boolean isRtl() {
        return (ViewCompat.getLayoutDirection(this) == View.LAYOUT_DIRECTION_RTL) && this.mMirrorForRtl;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        DiscreteSeekBar.CustomState state = new DiscreteSeekBar.CustomState(superState);
        state.progress = this.getProgress();
        state.max = this.mMax;
        state.min = this.mMin;
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state == null || !state.getClass().equals(DiscreteSeekBar.CustomState.class)) {
            super.onRestoreInstanceState(state);
            return;
        }

        DiscreteSeekBar.CustomState customState = (DiscreteSeekBar.CustomState) state;
        this.setMin(customState.min);
        this.setMax(customState.max);
        this.setProgress(customState.progress, false);
        super.onRestoreInstanceState(customState.getSuperState());
    }

    static class CustomState extends View.BaseSavedState {
        private int progress;
        private int max;
        private int min;

        public CustomState(Parcel source) {
            super(source);
            this.progress = source.readInt();
            this.max = source.readInt();
            this.min = source.readInt();
        }

        public CustomState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel outcoming, int flags) {
            super.writeToParcel(outcoming, flags);
            outcoming.writeInt(this.progress);
            outcoming.writeInt(this.max);
            outcoming.writeInt(this.min);
        }

        public static final Parcelable.Creator<DiscreteSeekBar.CustomState> CREATOR =
                new Parcelable.Creator<DiscreteSeekBar.CustomState>() {

                    @Override
                    public DiscreteSeekBar.CustomState[] newArray(int size) {
                        return new DiscreteSeekBar.CustomState[size];
                    }

                    @Override
                    public DiscreteSeekBar.CustomState createFromParcel(Parcel incoming) {
                        return new DiscreteSeekBar.CustomState(incoming);
                    }
                };
    }
}
