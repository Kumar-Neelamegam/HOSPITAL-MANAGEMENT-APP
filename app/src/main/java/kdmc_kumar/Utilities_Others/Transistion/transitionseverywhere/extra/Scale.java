/*
 * Copyright (C) 2016 Andrey Kulikov (andkulikov@gmail.com)
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
package kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.extra;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.styleable;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Transition;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.TransitionUtils;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.TransitionValues;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Visibility;

@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class Scale extends Visibility {

    static final String PROPNAME_SCALE_X = "scale:scaleX";
    static final String PROPNAME_SCALE_Y = "scale:scaleY";

    private float mDisappearedScale;

    public Scale() {
    }

    /**
     * @param disappearedScale Value of scale on start of appearing or in finish of disappearing.
     *                         Default value is 0. Can be useful for mixing some Visibility
     *                         transitions, for example Scale and Fade
     */
    public Scale(float disappearedScale) {
        this.setDisappearedScale(disappearedScale);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        if (transitionValues.view != null) {
            transitionValues.values.put(Scale.PROPNAME_SCALE_X, transitionValues.view.getScaleX());
            transitionValues.values.put(Scale.PROPNAME_SCALE_Y, transitionValues.view.getScaleY());
        }
    }

    /**
     * @param disappearedScale Value of scale on start of appearing or in finish of disappearing.
     *                         Default value is 0. Can be useful for mixing some Visibility
     *                         transitions, for example Scale and Fade
     * @return This Scale object.
     */
    public Scale setDisappearedScale(float disappearedScale) {
        if (disappearedScale < 0f) {
            throw new IllegalArgumentException("disappearedScale cannot be negative!");
        }
        this.mDisappearedScale = disappearedScale;
        return this;
    }

    public Scale(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, styleable.Scale);
        this.setDisappearedScale(a.getFloat(styleable.Scale_disappearedScale, this.mDisappearedScale));
        a.recycle();
    }

    @Nullable
    private Animator createAnimation(View view, float startScale, float endScale, TransitionValues values) {
        float initialScaleX = view.getScaleX();
        float initialScaleY = view.getScaleY();
        float startScaleX = initialScaleX * startScale;
        float endScaleX = initialScaleX * endScale;
        float startScaleY = initialScaleY * startScale;
        float endScaleY = initialScaleY * endScale;

        if (values != null) {
            Float savedScaleX = (Float) values.values.get(Scale.PROPNAME_SCALE_X);
            Float savedScaleY = (Float) values.values.get(Scale.PROPNAME_SCALE_Y);
            // if saved value is not equal initial value it means that previous
            // transition was interrupted and in the onTransitionEnd
            // we've applied endScale. we should apply proper value to
            // continue animation from the interrupted state
            if (savedScaleX != null && savedScaleX != initialScaleX) {
                startScaleX = savedScaleX;
            }
            if (savedScaleY != null && savedScaleY != initialScaleY) {
                startScaleY = savedScaleY;
            }
        }

        view.setScaleX(startScaleX);
        view.setScaleY(startScaleY);

        Animator animator = TransitionUtils.mergeAnimators(
            ObjectAnimator.ofFloat(view, View.SCALE_X, startScaleX, endScaleX),
            ObjectAnimator.ofFloat(view, View.SCALE_Y, startScaleY, endScaleY));
        this.addListener(new Transition.TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                view.setScaleX(initialScaleX);
                view.setScaleY(initialScaleY);
                transition.removeListener(this);
            }
        });
        return animator;
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues,
                             TransitionValues endValues) {
        return this.createAnimation(view, this.mDisappearedScale, 1f, startValues);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues,
                                TransitionValues endValues) {
        return this.createAnimation(view, 1f, this.mDisappearedScale, startValues);
    }

}