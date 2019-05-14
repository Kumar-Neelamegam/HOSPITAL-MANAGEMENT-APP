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

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ViewUtils {

    static class BaseViewUtils {

        private static final Field FIELD_VIEW_FLAGS =
                ReflectionUtils.getPrivateField(View.class, "mViewFlags");

        private static final Field FIELD_LAYOUT_PARAMS =
                ReflectionUtils.getPrivateField(View.class, "mLayoutParams");

        private static final int VIEW_VISIBILITY_MASK = 0x0000000C;

        private static final Method METHOD_SET_FRAME =
                ReflectionUtils.getPrivateMethod(View.class, "setFrame", int.class, int.class,
                        int.class, int.class);

        public float getTransitionAlpha(View v) {
            return v.getAlpha();
        }

        public boolean isLaidOut(View v, boolean defaultValue) {
            return defaultValue;
        }

        public void setClipBounds(View v, Rect clipBounds) {
            // TODO: Implement support behavior
        }

        public Rect getClipBounds(View v) {
            // TODO: Implement support behavior
            return null;
        }

        public void setTransitionName(View v, String name) {
            v.setTag(id.transitionName, name);
        }

        public String getTransitionName(View v) {
            return (String) v.getTag(id.transitionName);
        }

        public void setTranslationZ(View view, float z) {
            // do nothing
        }

        public float getTranslationZ(View view) {
            return 0;
        }

        public View addGhostView(View view, ViewGroup viewGroup, Matrix matrix) {
            return null;
        }

        public void removeGhostView(View view) {
            // do nothing
        }

        public void transformMatrixToGlobal(View view, Matrix matrix) {
            // TODO: Implement support behavior
        }

        public void transformMatrixToLocal(View v, Matrix matrix) {
            // TODO: Implement support behavior
        }

        public void setAnimationMatrix(View view, Matrix matrix) {
            // TODO: Implement support behavior
        }

        public Object getWindowId(View view) {
            return view.getWindowToken();
        }

        public boolean isRtl(View view) {
            return false;
        }

        public void setHasTransientState(View view, boolean hasTransientState) {
            // do nothing; API doesn't exist
        }

        public boolean hasTransientState(View view) {
            return false;
        }

        public void setTransitionVisibility(View v, int visibility) {
            int value = (Integer) ReflectionUtils.getFieldValue(v, 0, BaseViewUtils.FIELD_VIEW_FLAGS);
            value = (value & ~BaseViewUtils.VIEW_VISIBILITY_MASK) | visibility;
            ReflectionUtils.setFieldValue(v, BaseViewUtils.FIELD_VIEW_FLAGS, value);
        }

        public void setLeftTopRightBottom(View v, int left, int top, int right, int bottom) {
            ReflectionUtils.invoke(v, null, BaseViewUtils.METHOD_SET_FRAME, left, top, right, bottom);
        }

        public void setLayoutParamsSilently(View view, LayoutParams layoutParams) {
            ReflectionUtils.setFieldValue(view, BaseViewUtils.FIELD_LAYOUT_PARAMS, layoutParams);
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static class ViewUtilsJellyBean extends ViewUtils.BaseViewUtils {
        @Override
        public void setHasTransientState(View view, boolean hasTransientState) {
            view.setHasTransientState(hasTransientState);
        }

        @Override
        public boolean hasTransientState(View view) {
            return view.hasTransientState();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    static class ViewUtilsJellyBeanMR1 extends ViewUtils.ViewUtilsJellyBean {
        @Override
        public boolean isRtl(View view) {
            return view != null && view.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    static class ViewUtilsJellyBeanMR2 extends ViewUtils.ViewUtilsJellyBeanMR1 {
        @Override
        public void setClipBounds(View v, Rect clipBounds) {
            v.setClipBounds(clipBounds);
        }

        @Override
        public Rect getClipBounds(View v) {
            return v.getClipBounds();
        }

        @Override
        public Object getWindowId(View view) {
            return view.getWindowId();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    static class ViewUtilsKitKat extends ViewUtils.ViewUtilsJellyBeanMR2 {
        @Override
        public boolean isLaidOut(View v, boolean defaultValue) {
            return v.isLaidOut();
        }
    }

    private static final ViewUtils.BaseViewUtils IMPL;

    static {
        int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            IMPL = new ViewUtilsLollipopMr1();
        } else if (version >= Build.VERSION_CODES.LOLLIPOP) {
            IMPL = new ViewUtilsLollipop();
        } else if (version >= Build.VERSION_CODES.KITKAT) {
            IMPL = new ViewUtils.ViewUtilsKitKat();
        } else if (version >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            IMPL = new ViewUtils.ViewUtilsJellyBeanMR2();
        } else if (version >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            IMPL = new ViewUtils.ViewUtilsJellyBeanMR1();
        } else if (version >= Build.VERSION_CODES.JELLY_BEAN) {
            IMPL = new ViewUtils.ViewUtilsJellyBean();
        } else {
            IMPL = new ViewUtils.BaseViewUtils();
        }
    }

    public static float getTransitionAlpha(View v) {
        return ViewUtils.IMPL.getTransitionAlpha(v);
    }

    public static boolean isLaidOut(View v, boolean defaultValue) {
        return ViewUtils.IMPL.isLaidOut(v, defaultValue);
    }

    public static void setClipBounds(View v, Rect clipBounds) {
        ViewUtils.IMPL.setClipBounds(v, clipBounds);
    }

    public static Rect getClipBounds(View v) {
        return ViewUtils.IMPL.getClipBounds(v);
    }

    public static void setTransitionName(View v, String name) {
        ViewUtils.IMPL.setTransitionName(v, name);
    }

    public static String getTransitionName(View v) {
        return ViewUtils.IMPL.getTransitionName(v);
    }

    public static float getTranslationZ(View view) {
        return ViewUtils.IMPL.getTranslationZ(view);
    }

    public static void setTranslationZ(View view, float z) {
        ViewUtils.IMPL.setTranslationZ(view, z);
    }

    public static void transformMatrixToGlobal(View view, Matrix matrix) {
        ViewUtils.IMPL.transformMatrixToGlobal(view, matrix);
    }

    public static void transformMatrixToLocal(View view, Matrix matrix) {
        ViewUtils.IMPL.transformMatrixToLocal(view, matrix);
    }

    public static void setAnimationMatrix(View view, Matrix matrix) {
        ViewUtils.IMPL.setAnimationMatrix(view, matrix);
    }

    public static View addGhostView(View view, ViewGroup viewGroup, Matrix matrix) {
        return ViewUtils.IMPL.addGhostView(view, viewGroup, matrix);
    }

    public static void removeGhostView(View view) {
        ViewUtils.IMPL.removeGhostView(view);
    }

    public static Object getWindowId(View view) {
        return ViewUtils.IMPL.getWindowId(view);
    }

    public static boolean isRtl(View view) {
        return ViewUtils.IMPL.isRtl(view);
    }

    public static boolean hasTransientState(View view) {
        return ViewUtils.IMPL.hasTransientState(view);
    }

    public static void setHasTransientState(View view, boolean hasTransientState) {
        ViewUtils.IMPL.setHasTransientState(view, hasTransientState);
    }

    /**
     * Change the visibility of the View without triggering any other changes. This is
     * important for transitions, where visibility changes should not adjust focus or
     * trigger a new layout. This is only used when the visibility has already been changed
     * and we need a transient value during an animation. When the animation completes,
     * the original visibility value is always restored.
     *
     * @param visibility One of View.VISIBLE, View.INVISIBLE, or View.GONE.
     */
    public static void setTransitionVisibility(View view, int visibility) {
        ViewUtils.IMPL.setTransitionVisibility(view, visibility);
    }

    public static void setLeftTopRightBottom(View view, int left, int top, int right, int bottom) {
        if (view != null) {
            ViewUtils.IMPL.setLeftTopRightBottom(view, left, top, right, bottom);
        }
    }

    public static void setLayoutParamsSilently(View view, LayoutParams layoutParams) {
        ViewUtils.IMPL.setLayoutParamsSilently(view, layoutParams);
    }
}
