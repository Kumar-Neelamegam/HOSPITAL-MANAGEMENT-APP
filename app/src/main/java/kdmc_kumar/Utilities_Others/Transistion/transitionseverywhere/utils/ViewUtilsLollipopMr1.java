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
import android.graphics.PointF;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.util.Property;
import android.view.View;

/**
 * Created by Andrey Kulikov on 10.07.16.
 */
@TargetApi(VERSION_CODES.LOLLIPOP_MR1)
class ViewUtilsLollipopMr1 extends ViewUtilsLollipop {

    private static final Property<View, PointF> POSITION_PROPERTY, BOTTOM_RIGHT_PROPERTY;
    private static final PointF TEMP_POINT_F = new PointF();

    static {
        POSITION_PROPERTY = ViewUtilsLollipopMr1.getChangeBoundsProperty("POSITION_PROPERTY");
        BOTTOM_RIGHT_PROPERTY = ViewUtilsLollipopMr1.getChangeBoundsProperty("BOTTOM_RIGHT_ONLY_PROPERTY");
    }

    @SuppressWarnings("unchecked")
    private static Property<View, PointF> getChangeBoundsProperty(String fieldName) {
        Object fieldValue = ReflectionUtils.getFieldValue(null, null,
            ReflectionUtils.getPrivateField(android.transition.ChangeBounds.class, fieldName));
        if (fieldValue instanceof Property) {
            Property<View, PointF> property = (Property<View, PointF>) fieldValue;
            try {
                property.set(null, new PointF());
            } catch (NullPointerException e) {
                // casted properly. NullPointerException because we've passed null View
            } catch (Exception e) {
                // something wrong with the property. we shouldn't use it.
                property = null;
            }
            return property;
        } else {
            return null;
        }
    }

    @Override
    public void setLeftTopRightBottom(View v, int left, int top, int right, int bottom) {
        if (ViewUtilsLollipopMr1.POSITION_PROPERTY != null && ViewUtilsLollipopMr1.BOTTOM_RIGHT_PROPERTY != null) {
            ViewUtilsLollipopMr1.TEMP_POINT_F.set(left, top);
            ViewUtilsLollipopMr1.POSITION_PROPERTY.set(v, ViewUtilsLollipopMr1.TEMP_POINT_F);
            ViewUtilsLollipopMr1.TEMP_POINT_F.set(right, bottom);
            ViewUtilsLollipopMr1.BOTTOM_RIGHT_PROPERTY.set(v, ViewUtilsLollipopMr1.TEMP_POINT_F);
        } else {
            super.setLeftTopRightBottom(v, left, top, right, bottom);
        }
    }

}
