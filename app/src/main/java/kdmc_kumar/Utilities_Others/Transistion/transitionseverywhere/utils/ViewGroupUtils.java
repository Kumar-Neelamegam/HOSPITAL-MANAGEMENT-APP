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

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ViewGroupUtils {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static class BaseViewGroupUtils {

        private static final int LAYOUT_TRANSITION_CHANGING = 4;

        private static Field sFieldLayoutSuppressed;
        private static LayoutTransition sEmptyLayoutTransition;
        private static Method sMethodLayoutTransitionCancel;

        public void suppressLayout(ViewGroup group, boolean suppress) {
            if (BaseViewGroupUtils.sEmptyLayoutTransition == null) {
                BaseViewGroupUtils.sEmptyLayoutTransition = new LayoutTransition() {
                    @Override
                    public boolean isChangingLayout() {
                        return true;
                    }
                };
                BaseViewGroupUtils.sEmptyLayoutTransition.setAnimator(LayoutTransition.APPEARING, null);
                BaseViewGroupUtils.sEmptyLayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, null);
                BaseViewGroupUtils.sEmptyLayoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, null);
                BaseViewGroupUtils.sEmptyLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, null);
                BaseViewGroupUtils.sEmptyLayoutTransition.setAnimator(BaseViewGroupUtils.LAYOUT_TRANSITION_CHANGING, null);
            }
            if (suppress) {
                this.cancelLayoutTransition(group);
                LayoutTransition layoutTransition = group.getLayoutTransition();
                if (layoutTransition != null && layoutTransition != BaseViewGroupUtils.sEmptyLayoutTransition) {
                    group.setTag(id.group_layouttransition_backup, group.getLayoutTransition());
                }
                group.setLayoutTransition(BaseViewGroupUtils.sEmptyLayoutTransition);
            } else {
                group.setLayoutTransition(null);
                if (BaseViewGroupUtils.sFieldLayoutSuppressed == null) {
                    BaseViewGroupUtils.sFieldLayoutSuppressed = ReflectionUtils.getPrivateField(ViewGroup.class,
                            "mLayoutSuppressed");
                }
                Boolean suppressed = (Boolean) ReflectionUtils.getFieldValue(group,
                        Boolean.FALSE, BaseViewGroupUtils.sFieldLayoutSuppressed);
                if (!Boolean.FALSE.equals(suppressed)) {
                    ReflectionUtils.setFieldValue(group, BaseViewGroupUtils.sFieldLayoutSuppressed, false);
                    group.requestLayout();
                }
                LayoutTransition layoutTransition = (LayoutTransition)
                        group.getTag(id.group_layouttransition_backup);
                if (layoutTransition != null) {
                    group.setTag(id.group_layouttransition_backup, null);
                    group.setLayoutTransition(layoutTransition);
                }
            }
        }

        public boolean cancelLayoutTransition(ViewGroup group) {
            if (group != null) {
                LayoutTransition layoutTransition = group.getLayoutTransition();
                if (layoutTransition != null && layoutTransition.isRunning()) {
                    if (BaseViewGroupUtils.sMethodLayoutTransitionCancel == null) {
                        BaseViewGroupUtils.sMethodLayoutTransitionCancel = ReflectionUtils.getPrivateMethod(LayoutTransition.class, "cancel");
                    }
                    ReflectionUtils.invoke(group.getLayoutTransition(), null, BaseViewGroupUtils.sMethodLayoutTransitionCancel);
                    return true;
                }
            }
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    static class JellyBeanMr2ViewGroupUtils extends ViewGroupUtils.BaseViewGroupUtils {

        private static Method sMethodSuppressLayout;

        @Override
        public void suppressLayout(ViewGroup group, boolean suppress) {
            if (JellyBeanMr2ViewGroupUtils.sMethodSuppressLayout == null) {
                JellyBeanMr2ViewGroupUtils.sMethodSuppressLayout = ReflectionUtils.getMethod(ViewGroup.class, "suppressLayout", boolean.class);
            }
            ReflectionUtils.invoke(group, null, JellyBeanMr2ViewGroupUtils.sMethodSuppressLayout, suppress);
        }
    }

    private static final ViewGroupUtils.BaseViewGroupUtils IMPL;

    static {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            IMPL = new ViewGroupUtils.JellyBeanMr2ViewGroupUtils();
        } else {
            IMPL = new ViewGroupUtils.BaseViewGroupUtils();
        }
    }

    public static void suppressLayout(ViewGroup group, boolean suppress) {
        if (group != null) {
            ViewGroupUtils.IMPL.suppressLayout(group, suppress);
        }
    }

    /**
     * @return is cancel performed
     */
    public static boolean cancelLayoutTransition(ViewGroup group) {
        return ViewGroupUtils.IMPL.cancelLayoutTransition(group);
    }
}
