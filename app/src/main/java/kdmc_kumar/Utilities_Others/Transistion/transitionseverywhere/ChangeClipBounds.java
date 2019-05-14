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
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.RectEvaluator;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.ViewUtils;

/**
 * ChangeClipBounds captures the {@link View#getClipBounds()} before and after the
 * scene change and animates those changes during the transition.
 */
@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class ChangeClipBounds extends Transition {

    private static final String TAG = "ChangeTransform";

    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";

    private static final String[] sTransitionProperties = {
            ChangeClipBounds.PROPNAME_CLIP,
    };

    public static final Property<View, Rect> VIEW_CLIP_BOUNDS;

    static {
        if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
            VIEW_CLIP_BOUNDS = new Property<View, Rect>(Rect.class, "clipBounds") {

                @Override
                public void set(View object, Rect value) {
                    ViewUtils.setClipBounds(object, value);
                }

                @Override
                public Rect get(View object) {
                    return ViewUtils.getClipBounds(object);
                }

            };
        } else {
            VIEW_CLIP_BOUNDS = null;
        }
    }

    public ChangeClipBounds() {}

    public ChangeClipBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        return ChangeClipBounds.sTransitionProperties;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (view.getVisibility() == View.GONE) {
            return;
        }

        Rect clip = ViewUtils.getClipBounds(view);
        values.values.put(ChangeClipBounds.PROPNAME_CLIP, clip);
        if (clip == null) {
            Rect bounds = new Rect(0, 0, view.getWidth(), view.getHeight());
            values.values.put(ChangeClipBounds.PROPNAME_BOUNDS, bounds);
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
        if (startValues == null || endValues == null
                || !startValues.values.containsKey(ChangeClipBounds.PROPNAME_CLIP)
                || !endValues.values.containsKey(ChangeClipBounds.PROPNAME_CLIP)) {
            return null;
        }
        Rect start = (Rect) startValues.values.get(ChangeClipBounds.PROPNAME_CLIP);
        Rect end = (Rect) endValues.values.get(ChangeClipBounds.PROPNAME_CLIP);
        if (start == null && end == null) {
            return null; // No animation required since there is no clip.
        }

        if (start == null) {
            start = (Rect) startValues.values.get(ChangeClipBounds.PROPNAME_BOUNDS);
        } else if (end == null) {
            end = (Rect) endValues.values.get(ChangeClipBounds.PROPNAME_BOUNDS);
        }
        if (start.equals(end)) {
            return null;
        }

        ViewUtils.setClipBounds(endValues.view, start);
        RectEvaluator evaluator = new RectEvaluator(new Rect());
        return ObjectAnimator.ofObject(endValues.view, ChangeClipBounds.VIEW_CLIP_BOUNDS, evaluator, start, end);
    }

}
