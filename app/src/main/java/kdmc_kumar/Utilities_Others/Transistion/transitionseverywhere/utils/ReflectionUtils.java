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

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {

    private static final String TAG = ReflectionUtils.class.getSimpleName();

    private ReflectionUtils() {
        // This utility class is not publicly instantiable.
    }

    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Method getMethod(Class<?> targetClass, String name,
                                   Class<?>... parameterTypes) {
        if (targetClass == null || TextUtils.isEmpty(name)) return null;
        try {
            return targetClass.getMethod(name, parameterTypes);
        } catch (SecurityException e) {
            // ignore
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return null;
    }

    public static Method getPrivateMethod(Class<?> targetClass, String name,
                                          Class<?>... parameterTypes) {
        if (targetClass == null || TextUtils.isEmpty(name)) return null;
        try {
            Method method = targetClass.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (SecurityException e) {
            // ignore
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return null;
    }

    public static Object invoke(Object receiver, Object defaultValue,
                                Method method, Object... args) {
        if (method == null) return defaultValue;
        try {
            return method.invoke(receiver, args);
        } catch (Exception e) {
            Log.e(ReflectionUtils.TAG, "Exception in invoke", e);
        }
        return defaultValue;
    }

    public static Field getPrivateField(Class<?> targetClass, String name) {
        if (targetClass == null || TextUtils.isEmpty(name)) return null;
        try {
            Field field = targetClass.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (SecurityException e) {
            e.printStackTrace();
            // ignore
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // ignore
        }
        return null;
    }

    public static void setFieldValue(Object receiver, Field field, Object value) {
        if (field == null) return;
        try {
            field.set(receiver, value);
        } catch (Exception e) {
            Log.e(ReflectionUtils.TAG, "Exception in setFieldValue", e);
        }
    }

    public static Object getFieldValue(Object receiver, Object defaultValue,
                                       Field field) {
        if (field == null) return defaultValue;
        try {
            return field.get(receiver);
        } catch (Exception e) {
            Log.e(ReflectionUtils.TAG, "Exception in getFieldValue", e);
        }
        return defaultValue;
    }

    // Optimizations to avoid creating new objects array in every call of method.invoke(...)
    // caused by "Object... args" arguments definition

    private static final Object[] EMPTY_ARRAY = new Object[0];
    private static final Object[] ONE_OBJECT_ARRAY = new Object[1];
    private static final Object[] TWO_OBJECTS_ARRAY = new Object[2];
    private static final Object[] THREE_OBJECTS_ARRAY = new Object[3];
    private static final Object[] FOUR_OBJECTS_ARRAY = new Object[4];

    public static Object invoke(Object receiver, Object defaultValue, Method method) {
        return ReflectionUtils.invoke(receiver, defaultValue, method, ReflectionUtils.EMPTY_ARRAY);
    }

    public static Object invoke(Object receiver, Object defaultValue, Method method, Object firstArg) {
        ReflectionUtils.ONE_OBJECT_ARRAY[0] = firstArg;
        Object result = ReflectionUtils.invoke(receiver, defaultValue, method, ReflectionUtils.ONE_OBJECT_ARRAY);
        ReflectionUtils.ONE_OBJECT_ARRAY[0] = null;
        return result;
    }

    public static Object invoke(Object receiver, Object defaultValue, Method method,
                                Object firstArg, Object secondArg) {
        ReflectionUtils.TWO_OBJECTS_ARRAY[0] = firstArg;
        ReflectionUtils.TWO_OBJECTS_ARRAY[1] = secondArg;
        Object result = ReflectionUtils.invoke(receiver, defaultValue, method, ReflectionUtils.TWO_OBJECTS_ARRAY);
        ReflectionUtils.TWO_OBJECTS_ARRAY[0] = null;
        ReflectionUtils.TWO_OBJECTS_ARRAY[1] = null;
        return result;
    }


    public static Object invoke(Object receiver, Object defaultValue, Method method,
                                Object firstArg, Object secondArg, Object thirdArg) {
        ReflectionUtils.THREE_OBJECTS_ARRAY[0] = firstArg;
        ReflectionUtils.THREE_OBJECTS_ARRAY[1] = secondArg;
        ReflectionUtils.THREE_OBJECTS_ARRAY[2] = thirdArg;
        Object result = ReflectionUtils.invoke(receiver, defaultValue, method, ReflectionUtils.THREE_OBJECTS_ARRAY);
        ReflectionUtils.THREE_OBJECTS_ARRAY[0] = null;
        ReflectionUtils.THREE_OBJECTS_ARRAY[1] = null;
        ReflectionUtils.THREE_OBJECTS_ARRAY[2] = null;
        return result;
    }

    public static Object invoke(Object receiver, Object defaultValue, Method method,
                                Object firstArg, Object secondArg,
                                Object thirdArg, Object fourthArg) {
        ReflectionUtils.FOUR_OBJECTS_ARRAY[0] = firstArg;
        ReflectionUtils.FOUR_OBJECTS_ARRAY[1] = secondArg;
        ReflectionUtils.FOUR_OBJECTS_ARRAY[2] = thirdArg;
        ReflectionUtils.FOUR_OBJECTS_ARRAY[3] = fourthArg;
        Object result = ReflectionUtils.invoke(receiver, defaultValue, method, ReflectionUtils.FOUR_OBJECTS_ARRAY);
        ReflectionUtils.FOUR_OBJECTS_ARRAY[0] = null;
        ReflectionUtils.FOUR_OBJECTS_ARRAY[1] = null;
        ReflectionUtils.FOUR_OBJECTS_ARRAY[2] = null;
        ReflectionUtils.FOUR_OBJECTS_ARRAY[3] = null;
        return result;
    }

}
