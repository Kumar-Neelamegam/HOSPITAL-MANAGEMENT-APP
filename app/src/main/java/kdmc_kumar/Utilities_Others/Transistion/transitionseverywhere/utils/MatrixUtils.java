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

import android.animation.TypeEvaluator;
import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by Andrey Kulikov on 19.10.14.
 */
public class MatrixUtils {

    public static Matrix IDENTITY_MATRIX = new Matrix() {
        void oops() {
            throw new IllegalStateException("Matrix can not be modified");
        }

        @Override
        public void set(Matrix src) {
            this.oops();
        }

        @Override
        public void reset() {
            this.oops();
        }

        @Override
        public void setTranslate(float dx, float dy) {
            this.oops();
        }

        @Override
        public void setScale(float sx, float sy, float px, float py) {
            this.oops();
        }

        @Override
        public void setScale(float sx, float sy) {
            this.oops();
        }

        @Override
        public void setRotate(float degrees, float px, float py) {
            this.oops();
        }

        @Override
        public void setRotate(float degrees) {
            this.oops();
        }

        @Override
        public void setSinCos(float sinValue, float cosValue, float px, float py) {
            this.oops();
        }

        @Override
        public void setSinCos(float sinValue, float cosValue) {
            this.oops();
        }

        @Override
        public void setSkew(float kx, float ky, float px, float py) {
            this.oops();
        }

        @Override
        public void setSkew(float kx, float ky) {
            this.oops();
        }

        @Override
        public boolean setConcat(Matrix a, Matrix b) {
            this.oops();
            return false;
        }

        @Override
        public boolean preTranslate(float dx, float dy) {
            this.oops();
            return false;
        }

        @Override
        public boolean preScale(float sx, float sy, float px, float py) {
            this.oops();
            return false;
        }

        @Override
        public boolean preScale(float sx, float sy) {
            this.oops();
            return false;
        }

        @Override
        public boolean preRotate(float degrees, float px, float py) {
            this.oops();
            return false;
        }

        @Override
        public boolean preRotate(float degrees) {
            this.oops();
            return false;
        }

        @Override
        public boolean preSkew(float kx, float ky, float px, float py) {
            this.oops();
            return false;
        }

        @Override
        public boolean preSkew(float kx, float ky) {
            this.oops();
            return false;
        }

        @Override
        public boolean preConcat(Matrix other) {
            this.oops();
            return false;
        }

        @Override
        public boolean postTranslate(float dx, float dy) {
            this.oops();
            return false;
        }

        @Override
        public boolean postScale(float sx, float sy, float px, float py) {
            this.oops();
            return false;
        }

        @Override
        public boolean postScale(float sx, float sy) {
            this.oops();
            return false;
        }

        @Override
        public boolean postRotate(float degrees, float px, float py) {
            this.oops();
            return false;
        }

        @Override
        public boolean postRotate(float degrees) {
            this.oops();
            return false;
        }

        @Override
        public boolean postSkew(float kx, float ky, float px, float py) {
            this.oops();
            return false;
        }

        @Override
        public boolean postSkew(float kx, float ky) {
            this.oops();
            return false;
        }

        @Override
        public boolean postConcat(Matrix other) {
            this.oops();
            return false;
        }

        @Override
        public boolean setRectToRect(RectF src, RectF dst, Matrix.ScaleToFit stf) {
            this.oops();
            return false;
        }

        @Override
        public boolean setPolyToPoly(float[] src, int srcIndex, float[] dst, int dstIndex,
                                     int pointCount) {
            this.oops();
            return false;
        }

        @Override
        public void setValues(float[] values) {
            this.oops();
        }
    };

    private static final Field FIELD_DRAW_MATRIX = ReflectionUtils.getPrivateField(ImageView.class, "mDrawMatrix");

    public static void animateTransform(ImageView imageView, Matrix matrix) {
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) {
            return;
        }
        if (matrix == null || drawable.getIntrinsicWidth() == -1
                || drawable.getIntrinsicHeight() == -1) {
            drawable.setBounds(0, 0, imageView.getWidth(), imageView.getHeight());
        } else {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            Matrix drawMatrix = imageView.getImageMatrix();
            if (drawMatrix.isIdentity()) {
                drawMatrix = new Matrix();
                ReflectionUtils.setFieldValue(imageView, MatrixUtils.FIELD_DRAW_MATRIX, drawMatrix);
            }
            drawMatrix.set(matrix);
        }
        imageView.invalidate();
    }

    @TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
    public static class MatrixEvaluator implements TypeEvaluator<Matrix> {

        float[] mTempStartValues = new float[9];

        float[] mTempEndValues = new float[9];

        Matrix mTempMatrix = new Matrix();

        @Override
        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            startValue.getValues(this.mTempStartValues);
            endValue.getValues(this.mTempEndValues);
            for (int i = 0; i < 9; i++) {
                float diff = this.mTempEndValues[i] - this.mTempStartValues[i];
                this.mTempEndValues[i] = this.mTempStartValues[i] + (fraction * diff);
            }
            this.mTempMatrix.setValues(this.mTempEndValues);
            return this.mTempMatrix;
        }
    }

    @TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
    public static class NullMatrixEvaluator implements TypeEvaluator<Matrix> {
        @Override
        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            return null;
        }
    }

}
