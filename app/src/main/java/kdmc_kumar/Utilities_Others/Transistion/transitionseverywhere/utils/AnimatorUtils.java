/*
 * Copyright (C) 2014 Andrey Kulikov (andkulikov@gmail.com)
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
package kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Property;
import android.view.View;

import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.PathMotion;

public class AnimatorUtils {

    static class BaseAnimatorCompat {

        public void addPauseListener(Animator animator, Animator.AnimatorPauseListener listener) {
        }

        public void pause(Animator animator) {
        }

        public void resume(Animator animator) {
        }

        public <T> Animator ofPointF(T target, PointFProperty<T> property, float startLeft,
                                     float startTop, float endLeft, float endTop) {
            return null;
        }

        public <T> Animator ofPointF(T target, PointFProperty<T> property, Path path) {
            return null;
        }

        public boolean isAnimatorStarted(Animator anim) {
            return false;
        }

        public boolean hasOverlappingRendering(View view) {
            return true;
        }

        public ObjectAnimator ofFloat(View view, Property<View, Float> property,
                                      float startFraction, float endFraction) {
            return null;
        }

        public ObjectAnimator ofInt(View view, Property<View, Integer> property,
                                    float startFraction, float endFraction) {
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    static class IceCreamSandwichAnimatorCompat extends AnimatorUtils.BaseAnimatorCompat {

        @Override
        public void pause(Animator animator) {
            animator.cancel();
        }

        @Override
        public <T> Animator ofPointF(T target, PointFProperty<T> property, float startLeft,
                                     float startTop, float endLeft, float endTop) {
            return PointFAnimator.ofPointF(target, property, startLeft, startTop, endLeft, endTop);
        }

        @Override
        public <T> Animator ofPointF(T target, PointFProperty<T> property, Path path) {
            return PathAnimatorCompat.ofPointF(target, property, path);
        }

        @Override
        public boolean isAnimatorStarted(Animator anim) {
            return anim.isStarted();
        }

        @Override
        public ObjectAnimator ofFloat(View view, Property<View, Float> property,
                                      float startFraction, float endFraction) {
            float start = property.get(view) * startFraction;
            float end = property.get(view) * endFraction;
            if (start == end) {
                return null;
            }
            property.set(view, start);
            return ObjectAnimator.ofFloat(view, property, end);
        }

        @Override
        public ObjectAnimator ofInt(View view, Property<View, Integer> property,
                                    float startFraction, float endFraction) {
            int start = (int) (property.get(view) * startFraction);
            int end = (int) (property.get(view) * endFraction);
            if (start == end) {
                return null;
            }
            property.set(view, start);
            return ObjectAnimator.ofInt(view, property, end);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static class JellyBeanCompat extends AnimatorUtils.IceCreamSandwichAnimatorCompat {
        @Override
        public boolean hasOverlappingRendering(View view) {
            return view.hasOverlappingRendering();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    static class KitKatAnimatorCompat extends AnimatorUtils.JellyBeanCompat {
        @Override
        public void addPauseListener(Animator animator, Animator.AnimatorPauseListener listener) {
            animator.addPauseListener(listener);
        }

        @Override
        public void pause(Animator animator) {
            animator.pause();
        }

        @Override
        public void resume(Animator animator) {
            animator.resume();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static class LollipopAnimatorCompat extends AnimatorUtils.KitKatAnimatorCompat {

        @Override
        public <T> Animator ofPointF(T target, PointFProperty<T> property, Path path) {
            return ObjectAnimator.ofObject(target, property, null, path);
        }

    }

    private static final AnimatorUtils.BaseAnimatorCompat IMPL;

    static {
        int version = VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.LOLLIPOP) {
            IMPL = new AnimatorUtils.LollipopAnimatorCompat();
        } else if (version >= Build.VERSION_CODES.KITKAT) {
            IMPL = new AnimatorUtils.KitKatAnimatorCompat();
        } else if (version >= Build.VERSION_CODES.JELLY_BEAN) {
            IMPL = new AnimatorUtils.JellyBeanCompat();
        } else if (version >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            IMPL = new AnimatorUtils.IceCreamSandwichAnimatorCompat();
        } else {
            IMPL = new AnimatorUtils.BaseAnimatorCompat();
        }
    }

    public static void addPauseListener(Animator animator, Animator.AnimatorPauseListener listener) {
        AnimatorUtils.IMPL.addPauseListener(animator, listener);
    }

    public static void pause(Animator animator) {
        AnimatorUtils.IMPL.pause(animator);
    }

    public static void resume(Animator animator) {
        AnimatorUtils.IMPL.resume(animator);
    }

    public static <T> Animator ofPointF(T target, PointFProperty<T> property,
                                        float startLeft, float startTop,
                                        float endLeft, float endTop) {
        return AnimatorUtils.IMPL.ofPointF(target, property, startLeft, startTop, endLeft, endTop);
    }

    public static <T> Animator ofPointF(T target, PointFProperty<T> property, Path path) {
        if (path != null) {
            return AnimatorUtils.IMPL.ofPointF(target, property, path);
        } else {
            return null;
        }
    }

    public static <T> Animator ofPointF(T target, PointFProperty<T> property, PathMotion pathMotion,
                                        float startLeft, float startTop, float endLeft, float endTop) {
        if (startLeft != endLeft || startTop != endTop) {
            if (pathMotion == null || pathMotion.equals(PathMotion.STRAIGHT_PATH_MOTION)) {
                return AnimatorUtils.ofPointF(target, property, startLeft, startTop, endLeft, endTop);
            } else {
                return AnimatorUtils.ofPointF(target, property, pathMotion.getPath(startLeft, startTop,
                        endLeft, endTop));
            }
        } else {
            return null;
        }
    }

    public static boolean isAnimatorStarted(Animator anim) {
        return AnimatorUtils.IMPL.isAnimatorStarted(anim);
    }

    public static boolean hasOverlappingRendering(View view) {
        return AnimatorUtils.IMPL.hasOverlappingRendering(view);
    }

    public static ObjectAnimator ofFloat(View view, Property<View, Float> property,
                                         float startFraction, float endFraction) {
        return AnimatorUtils.IMPL.ofFloat(view, property, startFraction, endFraction);
    }

    public static ObjectAnimator ofInt(View view, Property<View, Integer> property,
                                       float startFraction, float endFraction) {
        return AnimatorUtils.IMPL.ofInt(view, property, startFraction, endFraction);
    }

}
