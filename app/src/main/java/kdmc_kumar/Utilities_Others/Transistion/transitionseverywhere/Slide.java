/*
 * Copyright (C) 2014 The Android Open Source Project
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
package kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.styleable;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.ViewUtils;

/**
 * This transition tracks changes to the visibility of target views in the
 * start and end scenes and moves views in or out from one of the edges of the
 * scene. Visibility is determined by both the
 * {@link View#setVisibility(int)} state of the view as well as whether it
 * is parented in the current view hierarchy. Disappearing Views are
 * limited as described in {@link Visibility#onDisappear(ViewGroup,
 * TransitionValues, int, TransitionValues, int)}.
 */
@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class Slide extends Visibility {

    protected static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    protected static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    protected Slide.CalculateSlide mSlideCalculator = Slide.sCalculateBottom;
    private @Slide.GravityFlag int mSlideEdge = Gravity.BOTTOM;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Gravity.LEFT, Gravity.TOP, Gravity.RIGHT, Gravity.BOTTOM, Gravity.START, Gravity.END})
    public @interface GravityFlag {}

    protected interface CalculateSlide {

        /** Returns the translation value for view when it goes out of the scene */
        float getGoneX(ViewGroup sceneRoot, View view);

        /** Returns the translation value for view when it goes out of the scene */
        float getGoneY(ViewGroup sceneRoot, View view);
    }

    protected abstract static class CalculateSlideHorizontal implements Slide.CalculateSlide {

        @Override
        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY();
        }
    }

    protected abstract static class CalculateSlideVertical implements Slide.CalculateSlide {

        @Override
        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX();
        }
    }

    private static final Slide.CalculateSlide sCalculateLeft = new Slide.CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() - sceneRoot.getWidth();
        }
    };

    private static final Slide.CalculateSlide sCalculateStart = new Slide.CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean isRtl = ViewUtils.isRtl(sceneRoot);
            float x;
            if (isRtl) {
                x = view.getTranslationX() + sceneRoot.getWidth();
            } else {
                x = view.getTranslationX() - sceneRoot.getWidth();
            }
            return x;
        }
    };

    private static final Slide.CalculateSlide sCalculateTop = new Slide.CalculateSlideVertical() {
        @Override
        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() - sceneRoot.getHeight();
        }
    };

    private static final Slide.CalculateSlide sCalculateRight = new Slide.CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() + sceneRoot.getWidth();
        }
    };

    private static final Slide.CalculateSlide sCalculateEnd = new Slide.CalculateSlideHorizontal() {
        @Override
        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean isRtl = ViewUtils.isRtl(sceneRoot);
            float x;
            if (isRtl) {
                x = view.getTranslationX() - sceneRoot.getWidth();
            } else {
                x = view.getTranslationX() + sceneRoot.getWidth();
            }
            return x;
        }
    };

    private static final Slide.CalculateSlide sCalculateBottom = new Slide.CalculateSlideVertical() {
        @Override
        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() + sceneRoot.getHeight();
        }
    };

    /**
     * Constructor using the default {@link Gravity#BOTTOM}
     * slide edge direction.
     */
    public Slide() {
        this.setSlideEdge(Gravity.BOTTOM);
    }

    /**
     * Constructor using the provided slide edge direction.
     */
    public Slide(@Slide.GravityFlag int slideEdge) {
        this.setSlideEdge(slideEdge);
    }

    public Slide(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, styleable.Slide);
        int edge = a.getInt(styleable.Slide_slideEdge, Gravity.BOTTOM);
        a.recycle();
        this.setSlideEdge(edge);
    }

    /**
     * Change the edge that Views appear and disappear from.
     *
     * @param slideEdge The edge of the scene to use for Views appearing and disappearing. One of
     *                  {@link Gravity#LEFT}, {@link Gravity#TOP},
     *                  {@link Gravity#RIGHT}, {@link Gravity#BOTTOM}.
     * @attr ref android.R.styleable#Slide_slideEdge
     */
    @SuppressLint("RtlHardcoded")
    public void setSlideEdge(@Slide.GravityFlag int slideEdge) {
        switch (slideEdge) {
            case Gravity.LEFT:
                this.mSlideCalculator = Slide.sCalculateLeft;
                break;
            case Gravity.TOP:
                this.mSlideCalculator = Slide.sCalculateTop;
                break;
            case Gravity.RIGHT:
                this.mSlideCalculator = Slide.sCalculateRight;
                break;
            case Gravity.BOTTOM:
                this.mSlideCalculator = Slide.sCalculateBottom;
                break;
            case Gravity.START:
                this.mSlideCalculator = Slide.sCalculateStart;
                break;
            case Gravity.END:
                this.mSlideCalculator = Slide.sCalculateEnd;
                break;
            default:
                throw new IllegalArgumentException("Invalid slide direction");
        }
        this.mSlideEdge = slideEdge;
        SidePropagation propagation = new SidePropagation();
        propagation.setSide(slideEdge);
        this.setPropagation(propagation);
    }

    /**
     * Returns the edge that Views appear and disappear from.
     *
     * @return the edge of the scene to use for Views appearing and disappearing. One of
     *         {@link Gravity#LEFT}, {@link Gravity#TOP},
     *         {@link Gravity#RIGHT}, {@link Gravity#BOTTOM},
     *         {@link Gravity#START}, {@link Gravity#END}.
     * @attr ref android.R.styleable#Slide_slideEdge
     */
    @Slide.GravityFlag
    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view,
                             TransitionValues startValues, TransitionValues endValues) {
        if (endValues == null) {
            return null;
        }
        int[] position = (int[]) endValues.values.get(Visibility.PROPNAME_SCREEN_LOCATION);
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        float startX = this.mSlideCalculator.getGoneX(sceneRoot, view);
        float startY = this.mSlideCalculator.getGoneY(sceneRoot, view);
        return TranslationAnimationCreator
                .createAnimation(view, endValues, position[0], position[1],
                        startX, startY, endX, endY, Slide.sDecelerate, this);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view,
                                TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null) {
            return null;
        }
        int[] position = (int[]) startValues.values.get(Visibility.PROPNAME_SCREEN_LOCATION);
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float endX = this.mSlideCalculator.getGoneX(sceneRoot, view);
        float endY = this.mSlideCalculator.getGoneY(sceneRoot, view);
        return TranslationAnimationCreator
                .createAnimation(view, startValues, position[0], position[1],
                        startX, startY, endX, endY, Slide.sAccelerate, this);
    }
}
