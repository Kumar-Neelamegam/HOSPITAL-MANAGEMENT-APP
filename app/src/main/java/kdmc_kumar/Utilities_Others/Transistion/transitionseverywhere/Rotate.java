/*
 * Copyright (C) 2013 The Android Open Source Project
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
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.ViewGroup;

/**
 * This transition captures the rotation property of targets before and after
 * the scene change and animates any changes.
 */
@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class Rotate extends Transition {

    private static final String PROPNAME_ROTATION = "android:rotate:rotation";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(Rotate.PROPNAME_ROTATION, transitionValues.view.getRotation());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(Rotate.PROPNAME_ROTATION, transitionValues.view.getRotation());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        View view = endValues.view;
        float startRotation = (Float) startValues.values.get(Rotate.PROPNAME_ROTATION);
        float endRotation = (Float) endValues.values.get(Rotate.PROPNAME_ROTATION);
        if (startRotation != endRotation) {
            view.setRotation(startRotation);
            return ObjectAnimator.ofFloat(view, View.ROTATION,
                    startRotation, endRotation);
        }
        return null;
    }
}