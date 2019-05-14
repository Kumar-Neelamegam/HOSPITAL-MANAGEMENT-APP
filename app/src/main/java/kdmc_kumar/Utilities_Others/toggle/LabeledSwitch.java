/*
 * Copyright (C) 2018 Angad Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kdmc_kumar.Utilities_Others.toggle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.dimen;
import displ.mobydocmarathi.com.R.styleable;

/**
 * <p>
 * Created by Angad Singh on 27/1/18.
 * </p>
 */

public class LabeledSwitch extends View {
    private int width;
    private int height;
    private int padding;

    private int colorOn;
    private int colorOff;
    private int colorBorder;
    private int colorDisabled;

    private int textSize;

    private int outerRadii;
    private int thumbRadii;

    private boolean isOn;
    private boolean enabled;

    private Paint paint;

    private long startTime;

    private String labelOn;
    private String labelOff;

    private RectF thumbBounds;

    private RectF leftBgArc;
    private RectF rightBgArc;

    private RectF leftFgArc;
    private RectF rightFgArc;

    private Typeface typeface;

    private float thumbOnCenterX;
    private float thumbOffCenterX;

    private OnToggledListener onToggledListener;

    public LabeledSwitch(Context context) {
        super(context);
        this.initView();
    }

    public LabeledSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
        this.initProperties(attrs);
    }

    public LabeledSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
        this.initProperties(attrs);
    }

    private void initView() {
        isOn = false;
        labelOn = "YES";
        labelOff = "NO";

        enabled = true;
        textSize = (int)(12.0f * this.getResources().getDisplayMetrics().scaledDensity);

        this.colorBorder = VERSION.SDK_INT >= VERSION_CODES.M ? (this.colorOn = this.getResources().getColor(color.colorAccent, this.getContext().getTheme())) : (this.colorOn = this.getResources().getColor(color.colorAccent));

        this.paint = new Paint();
        this.paint.setAntiAlias(true);

        this.leftBgArc = new RectF();
        this.rightBgArc = new RectF();

        this.leftFgArc = new RectF();
        this.rightFgArc = new RectF();
        this.thumbBounds = new RectF();

        colorOff = Color.parseColor("#FFFFFF");
        colorDisabled = Color.parseColor("#D3D3D3");
    }

    private void initProperties(AttributeSet attrs) {
        TypedArray tarr = this.getContext().getTheme().obtainStyledAttributes(attrs, styleable.Toggle,0,0);
        int N = tarr.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = tarr.getIndex(i);
            if (attr == styleable.Toggle_on) {
                this.isOn = tarr.getBoolean(styleable.Toggle_on, false);
            } else if (attr == styleable.Toggle_colorOff) {
                this.colorOff = tarr.getColor(styleable.Toggle_colorOff, Color.parseColor("#FFFFFF"));
            } else if (attr == styleable.Toggle_colorBorder) {
                int accentColor;
                accentColor = VERSION.SDK_INT >= VERSION_CODES.M ? this.getResources().getColor(color.colorAccent, this.getContext().getTheme()) : this.getResources().getColor(color.colorAccent);
                this.colorBorder = tarr.getColor(styleable.Toggle_colorBorder, accentColor);
            } else if (attr == styleable.Toggle_colorOn) {
                int accentColor;
                accentColor = VERSION.SDK_INT >= VERSION_CODES.M ? this.getResources().getColor(color.colorAccent, this.getContext().getTheme()) : this.getResources().getColor(color.colorAccent);
                this.colorOn = tarr.getColor(styleable.Toggle_colorOn, accentColor);
            } else if (attr == styleable.Toggle_colorDisabled) {
                this.colorDisabled = tarr.getColor(styleable.Toggle_colorOff, Color.parseColor("#D3D3D3"));
            } else if (attr == styleable.Toggle_textOff) {
                this.labelOff = tarr.getString(styleable.Toggle_textOff);
            } else if (attr == styleable.Toggle_textOn) {
                this.labelOn = tarr.getString(styleable.Toggle_textOn);
            } else if (attr == styleable.Toggle_android_textSize) {
                int defaultTextSize = (int)(12.0f * this.getResources().getDisplayMetrics().scaledDensity);
                this.textSize = tarr.getDimensionPixelSize(styleable.Toggle_android_textSize, defaultTextSize);
            } else if(attr == styleable.Toggle_android_enabled) {
                this.enabled = tarr.getBoolean(styleable.Toggle_android_enabled, false);
            }
        }
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float scaledSizeInPixels = textSize * getResources().getDisplayMetrics().scaledDensity;
        this.paint.setTextSize((float) this.textSize);

//      Drawing Switch background here
        {
            if(this.enabled) {
                this.paint.setColor(this.colorBorder);
            } else {
                this.paint.setColor(this.colorDisabled);
            }
            canvas.drawArc(this.leftBgArc, 90.0F, 180.0F, false, this.paint);
            canvas.drawArc(this.rightBgArc, 90.0F, -180.0F, false, this.paint);
            canvas.drawRect((float) this.outerRadii, (float) 0, (float) (this.width - this.outerRadii), (float) this.height, this.paint);

            this.paint.setColor(this.colorOff);

            canvas.drawArc(this.leftFgArc, 90.0F, 180.0F, false, this.paint);
            canvas.drawArc(this.rightFgArc, 90.0F, -180.0F, false, this.paint);
            canvas.drawRect((float) this.outerRadii, (float) (this.padding / 10), (float) (this.width - this.outerRadii), (float) (this.height - (this.padding / 10)), this.paint);

            int alpha = (int) (((this.thumbBounds.centerX() - this.thumbOffCenterX) / (this.thumbOnCenterX - this.thumbOffCenterX)) * 255.0F);
            int onColor;
            onColor = this.isEnabled() ? Color.argb(alpha, Color.red(this.colorOn), Color.green(this.colorOn), Color.blue(this.colorOn)) : Color.argb(alpha, Color.red(this.colorDisabled), Color.green(this.colorDisabled), Color.blue(this.colorDisabled));
            this.paint.setColor(onColor);

            canvas.drawArc(this.leftBgArc, 90.0F, 180.0F, false, this.paint);
            canvas.drawArc(this.rightBgArc, 90.0F, -180.0F, false, this.paint);
            canvas.drawRect((float) this.outerRadii, (float) 0, (float) (this.width - this.outerRadii), (float) this.height, this.paint);

            int alpha1 = (int) (((this.thumbOnCenterX - this.thumbBounds.centerX()) / (this.thumbOnCenterX - this.thumbOffCenterX)) * 255.0F);
            int offColor = Color.argb(alpha1, Color.red(this.colorOff), Color.green(this.colorOff), Color.blue(this.colorOff));
            this.paint.setColor(offColor);

            canvas.drawArc(this.leftFgArc, 90.0F, 180.0F, false, this.paint);
            canvas.drawArc(this.rightFgArc, 90.0F, -180.0F, false, this.paint);
            canvas.drawRect((float) this.outerRadii, (float) (this.padding / 10), (float) (this.width - this.outerRadii), (float) (this.height - (this.padding / 10)), this.paint);
        }

//      Drawing Switch Labels here
        String MAX_CHAR = "N";
        float textCenter = this.paint.measureText(MAX_CHAR) / 2.0F;
        if(this.isOn) {
            int alpha = (int)(((this.thumbBounds.centerX() - (float) (this.width / 2)) / (this.thumbOnCenterX - (float) (this.width / 2))) * 255.0F);
            int offColor = Color.argb(alpha < 0 ? 0 : alpha, Color.red(this.colorOff), Color.green(this.colorOff), Color.blue(this.colorOff));
            this.paint.setColor(offColor);

            int maxSize = this.width - (2 * this.padding) - (2 * this.thumbRadii);

//            float extraSpace = (maxSize - paint.measureText(labelOn)) / 2;

//            Effective label text area
//            canvas.drawRect(padding, padding, (padding / 2) + maxSize, height - padding, paint);
//            int onColor = Color.argb(alpha < 0 ? 0 : alpha, Color.red(colorOn), Color.green(colorOn), Color.blue(colorOn));
//            paint.setColor(onColor);

            float centerX = (float) ((((this.padding / 2) + maxSize) - this.padding) / 2);
            canvas.drawText(this.labelOn, (float) this.padding + centerX - (this.paint.measureText(this.labelOn) / 2.0F), (float) (this.height / 2) + textCenter, this.paint);
        } else {
            int alpha = (int)((((float) (this.width / 2) - this.thumbBounds.centerX()) / ((float) (this.width / 2) - this.thumbOffCenterX)) * 255.0F);
            int onColor;
            onColor = this.isEnabled() ? Color.argb(alpha < 0 ? 0 : alpha, Color.red(this.colorOn), Color.green(this.colorOn), Color.blue(this.colorOn)) : Color.argb(alpha < 0 ? 0 : alpha, Color.red(this.colorDisabled), Color.green(this.colorDisabled), Color.blue(this.colorDisabled));
            this.paint.setColor(onColor);

//            int maxSize = width - (2 * thumbRadii);
//            float extraSpace = (maxSize - paint.measureText(labelOff)) / 2;

//            Effective label text area
//            canvas.drawRect((padding + (padding / 2)) + (2 * thumbRadii), padding, width - padding, height - padding, paint);
//            int offColor = Color.argb(alpha < 0 ? 0 : alpha, Color.red(colorOff), Color.green(colorOff), Color.blue(colorOff));
//            paint.setColor(offColor);

            float centerX = (float) ((this.width - this.padding - ((this.padding + (this.padding / 2)) + (2 * this.thumbRadii))) / 2);
            canvas.drawText(this.labelOff, (float) (this.padding + (this.padding / 2)) + (float) (2 * this.thumbRadii) + centerX - (this.paint.measureText(this.labelOff) / 2.0F), (float) (this.height / 2) + textCenter, this.paint);
        }

//      Drawing Switch Thumb here
        {
            int alpha = (int) (((this.thumbBounds.centerX() - this.thumbOffCenterX) / (this.thumbOnCenterX - this.thumbOffCenterX)) * 255.0F);
            int offColor = Color.argb(alpha, Color.red(this.colorOff), Color.green(this.colorOff), Color.blue(this.colorOff));
            this.paint.setColor(offColor);

            canvas.drawCircle(this.thumbBounds.centerX(), this.thumbBounds.centerY(), (float) this.thumbRadii, this.paint);
            int alpha1 = (int) (((this.thumbOnCenterX - this.thumbBounds.centerX()) / (this.thumbOnCenterX - this.thumbOffCenterX)) * 255.0F);

            int onColor;
            onColor = this.isEnabled() ? Color.argb(alpha1 < 0 ? 0 : alpha1, Color.red(this.colorOn), Color.green(this.colorOn), Color.blue(this.colorOn)) : Color.argb(alpha1 < 0 ? 0 : alpha1, Color.red(this.colorDisabled), Color.green(this.colorDisabled), Color.blue(this.colorDisabled));
            this.paint.setColor(onColor);
            canvas.drawCircle(this.thumbBounds.centerX(), this.thumbBounds.centerY(), (float) this.thumbRadii, this.paint);
        }
    }

    @Override
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = this.getResources().getDimensionPixelSize(dimen.default_width);
        int desiredHeight = this.getResources().getDimensionPixelSize(dimen.default_height);

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                this.width = widthSize;
                break;
            case View.MeasureSpec.AT_MOST:
                this.width = Math.min(desiredWidth, widthSize);
                break;
            default:
                this.width = desiredWidth;
                break;
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                this.height = heightSize;
                break;
            case View.MeasureSpec.AT_MOST:
                this.height = Math.min(desiredHeight, heightSize);
                break;
            default:
                this.height = desiredHeight;
                break;
        }

        this.setMeasuredDimension(this.width, this.height);

        this.outerRadii = Math.min(this.width, this.height) / 2;
        this.thumbRadii = (int) ((float) Math.min(this.width, this.height) / (2.88f));
        this.padding = (this.height - this.thumbRadii) / 2;

        this.thumbBounds.set((float) (this.width - this.padding - this.thumbRadii), (float) this.padding, (float) (this.width - this.padding), (float) (this.height - this.padding));
        this.thumbOnCenterX = this.thumbBounds.centerX();

        this.thumbBounds.set((float) this.padding, (float) this.padding, (float) (this.padding + this.thumbRadii), (float) (this.height - this.padding));
        this.thumbOffCenterX = this.thumbBounds.centerX();

        if(this.isOn) {
            this.thumbBounds.set((float) (this.width - this.padding - this.thumbRadii), (float) this.padding, (float) (this.width - this.padding), (float) (this.height - this.padding));
        } else {
            this.thumbBounds.set((float) this.padding, (float) this.padding, (float) (this.padding + this.thumbRadii), (float) (this.height - this.padding));
        }

        this.leftBgArc.set((float) 0, (float) 0, (float) (2 * this.outerRadii), (float) this.height);
        this.rightBgArc.set((float) (this.width - (2 * this.outerRadii)), (float) 0, (float) this.width, (float) this.height);

        this.leftFgArc.set((float) (this.padding / 10), (float) (this.padding / 10), (float) ((2 * this.outerRadii) - (this.padding / 10)), (float) (this.height - (this.padding / 10)));
        this.rightFgArc.set((float) (this.width - (2 * this.outerRadii) + (this.padding / 10)), (float) (this.padding / 10), (float) (this.width - (this.padding / 10)), (float) (this.height - (this.padding / 10)));
    }

    @Override
    public final boolean performClick() {
        super.performClick();
        if (this.isOn) {
            ValueAnimator switchColor = ValueAnimator.ofFloat((float) (this.width - this.padding - this.thumbRadii), (float) this.padding);
            switchColor.addUpdateListener(animation -> {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                this.thumbBounds.set(value, this.thumbBounds.top, value + (float) this.thumbRadii, this.thumbBounds.bottom);
                this.invalidate();
            });
            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
            switchColor.setDuration(250L);
            switchColor.start();
        } else {
            ValueAnimator switchColor = ValueAnimator.ofFloat((float) this.padding, (float) (this.width - this.padding - this.thumbRadii));
            switchColor.addUpdateListener(animation -> {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                this.thumbBounds.set(value, this.thumbBounds.top, value + (float) this.thumbRadii, this.thumbBounds.bottom);
                this.invalidate();
            });
            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
            switchColor.setDuration(250L);
            switchColor.start();
        }
        this.isOn =!this.isOn;
        return true;
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {
        if(this.enabled) {
            float x = event.getX();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    this.startTime = System.currentTimeMillis();
                    return true;
                }

                case MotionEvent.ACTION_MOVE: {
                    // MOVE THUMB TO THIS POSITION
                    if (x - (float) (this.thumbRadii / 2) > (float) this.padding && x + (float) (this.thumbRadii / 2) < (float) (this.width - this.padding)) {
//                         Uncommenting this toggle switch back to last state on quick swipe actions.
//                        if (x >= width / 2) {
//                            isOn = true;
//                        } else {
//                            isOn = false;
//                        }
                        this.thumbBounds.set(x - (float) (this.thumbRadii / 2), this.thumbBounds.top, x + (float) (this.thumbRadii / 2), this.thumbBounds.bottom);
                        this.invalidate();
                    }
                    return true;
                }

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    long endTime = System.currentTimeMillis();
                    long span = endTime - this.startTime;
                    if (span < 200L) {
                        // JUST TOGGLE THE CURRENT SELECTION
                        // USING PERFORM CLICK FOR ACCESSIBILITY SUPPORT
                        this.performClick();
                    } else {
                        if (x >= (float) (this.width / 2)) {
                            // MOVE SWITCH TO RIGHT
                            ValueAnimator switchColor = ValueAnimator.ofFloat((x > (float) (this.width - this.padding - this.thumbRadii) ? (float) (this.width - this.padding - this.thumbRadii) : x), (float) (this.width - this.padding - this.thumbRadii));
                            switchColor.addUpdateListener(animation -> {
                                float value = ((Float) animation.getAnimatedValue()).floatValue();
                                this.thumbBounds.set(value, this.thumbBounds.top, value + (float) this.thumbRadii, this.thumbBounds.bottom);
                                this.invalidate();
                            });
                            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
                            switchColor.setDuration(250L);
                            switchColor.start();
                            this.isOn = true;
                        } else {
                            // MOVE SWITCH TO LEFT
                            ValueAnimator switchColor = ValueAnimator.ofFloat((x < (float) this.padding ? (float) this.padding : x), (float) this.padding);
                            switchColor.addUpdateListener(animation -> {
                                float value = ((Float) animation.getAnimatedValue()).floatValue();
                                this.thumbBounds.set(value, this.thumbBounds.top, value + (float) this.thumbRadii, this.thumbBounds.bottom);
                                this.invalidate();
                            });
                            switchColor.setInterpolator(new AccelerateDecelerateInterpolator());
                            switchColor.setDuration(250L);
                            switchColor.start();
                            this.isOn = false;
                        }
                    }
                    this.invalidate();
                    if(this.onToggledListener != null) {
                        this.onToggledListener.onSwitched(this, this.isOn);
                    }
                    return true;
                }

                default: {
                    return super.onTouchEvent(event);
                }
            }
        } else {
            return false;
        }
    }

    public final void setOnToggledListener(OnToggledListener onToggledListener) {
        this.onToggledListener = onToggledListener;
    }



    public final int getColorOn() {
        return this.colorOn;
    }

    public final void setColorOn(int colorOn) {
        this.colorOn = colorOn;
        this.invalidate();
    }

    public final int getColorOff() {
        return this.colorOff;
    }

    public final void setColorOff(int colorOff) {
        this.colorOff = colorOff;
        this.invalidate();
    }

    public final String getLabelOn() {
        return this.labelOn;
    }

    public final void setLabelOn(String labelOn) {
        this.labelOn = labelOn;
        this.invalidate();
    }

    public final String getLabelOff() {
        return this.labelOff;
    }

    public final void setLabelOff(String labelOff) {
        this.labelOff = labelOff;
        this.invalidate();
    }

    public final Typeface getTypeface() {
        return this.typeface;
    }

    public final void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        this.paint.setTypeface(typeface);
        this.invalidate();
    }

    public final boolean isOn() {
        return this.isOn;
    }

    public final void setOn(boolean on) {
        this.isOn = on;
        if(this.isOn) {
            this.thumbBounds.set((float) (this.width - this.padding - this.thumbRadii), (float) this.padding, (float) (this.width - this.padding), (float) (this.height - this.padding));
        } else {
            this.thumbBounds.set((float) this.padding, (float) this.padding, (float) (this.padding + this.thumbRadii), (float) (this.height - this.padding));
        }
        this.invalidate();
    }

    public final int getColorDisabled() {
        return this.colorDisabled;
    }

    public final void setColorDisabled(int colorDisabled) {
        this.colorDisabled = colorDisabled;
        this.invalidate();
    }

    @Override
    public final boolean isEnabled() {
        return this.enabled;
    }
}
