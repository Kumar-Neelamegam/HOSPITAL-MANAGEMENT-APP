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
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Transition;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.TransitionValues;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.AnimatorUtils;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.PointFProperty;

/**
 * This transition tracks changes to the translationX and translationY of
 * target views in the start and end scenes and animates change.
 * <p/>
 * Created by Andrey Kulikov on 13/03/16.
 */
@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class TranslationTransition extends Transition {

    private static final String TRANSLATION_X = "TranslationTransition:translationX";
    private static final String TRANSLATION_Y = "TranslationTransition:translationY";

    private static final PointFProperty<View> TRANSLATION_PROPERTY;

    static {
        if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
            TRANSLATION_PROPERTY = new PointFProperty<View>() {

                @Override
                public void set(View object, PointF value) {
                    object.setTranslationX(value.x);
                    object.setTranslationY(value.y);
                }

                @Override
                public PointF get(View object) {
                    return new PointF(object.getTranslationX(), object.getTranslationY());
                }
            };
        } else {
            TRANSLATION_PROPERTY = null;
        }
    }

    public TranslationTransition() {
    }

    public TranslationTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view != null) {
            transitionValues.values.put(TranslationTransition.TRANSLATION_X, transitionValues.view.getTranslationX());
            transitionValues.values.put(TranslationTransition.TRANSLATION_Y, transitionValues.view.getTranslationY());
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues != null && endValues != null && TranslationTransition.TRANSLATION_PROPERTY != null) {
            float startX = (float) startValues.values.get(TranslationTransition.TRANSLATION_X);
            float startY = (float) startValues.values.get(TranslationTransition.TRANSLATION_Y);
            float endX = (float) endValues.values.get(TranslationTransition.TRANSLATION_X);
            float endY = (float) endValues.values.get(TranslationTransition.TRANSLATION_Y);
            endValues.view.setTranslationX(startX);
            endValues.view.setTranslationY(startY);
            return AnimatorUtils.ofPointF(endValues.view, TranslationTransition.TRANSLATION_PROPERTY, this.getPathMotion(), startX, startY, endX, endY);
        } else {
            return null;
        }
    }

}