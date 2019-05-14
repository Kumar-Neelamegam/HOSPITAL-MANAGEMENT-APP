/*
 * Copyright (C) 2007 The Android Open Source Project
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

package kdmc_kumar.Utilities_Others;


import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.view.View;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;

// This class is used by CropImage to display a highlighted cropping rectangle
// overlayed with the image. There are two coordinate spaces in use. One is
// image, another is screen. computeLayout() uses mMatrix to map from image
// space to screen space.
class HighlightView {

    public static final int GROW_NONE = (1);
    private static final int GROW_LEFT_EDGE = (1 << 1);
    private static final int GROW_RIGHT_EDGE = (1 << 2);
    private static final int GROW_TOP_EDGE = (1 << 3);
    private static final int GROW_BOTTOM_EDGE = (1 << 4);
    public static final int MOVE = (1 << 5);
    @SuppressWarnings("unused")
    private static final String TAG = "HighlightView";
    private final Paint mFocusPaint = new Paint();
    private final Paint mNoFocusPaint = new Paint();
    private final Paint mOutlinePaint = new Paint();
    private final View mContext; // The View displaying the image.
    boolean mIsFocused;
    private boolean mHidden;
    Rect mDrawRect; // in screen space
    RectF mCropRect; // in image space
    Matrix mMatrix;
    private HighlightView.ModifyMode mMode = HighlightView.ModifyMode.None;
    private RectF mImageRect; // in image space
    private boolean mMaintainAspectRatio;
    private float mInitialAspectRatio;
    private boolean mCircle;
    private Drawable mResizeDrawableWidth;
    private Drawable mResizeDrawableHeight;
    private Drawable mResizeDrawableDiagonal;

    public HighlightView(View ctx) {

        this.mContext = ctx;
    }

    private void init() {

        android.content.res.Resources resources = this.mContext.getResources();
        this.mResizeDrawableWidth = resources
                .getDrawable(drawable.camera_crop_width);
        this.mResizeDrawableHeight = resources
                .getDrawable(drawable.camera_crop_height);
        this.mResizeDrawableDiagonal = resources
                .getDrawable(drawable.indicator_autocrop);
    }

    public final boolean hasFocus() {

        return this.mIsFocused;
    }

    public final void setFocus(boolean f) {

        this.mIsFocused = f;
    }

    public final void setHidden(boolean hidden) {

        this.mHidden = hidden;
    }

    final void draw(Canvas canvas) {

        if (this.mHidden) {
            return;
        }

        Path path = new Path();
        if (this.hasFocus()) {
            Rect viewDrawingRect = new Rect();
            this.mContext.getDrawingRect(viewDrawingRect);
            if (this.mCircle) {

                canvas.save();

                float width = this.mDrawRect.width();
                float height = this.mDrawRect.height();
                path.addCircle(this.mDrawRect.left + (width / 2), this.mDrawRect.top
                        + (height / 2), width / 2, Direction.CW);
                this.mOutlinePaint.setColor(0xFFEF04D6);

                canvas.clipPath(path, Op.DIFFERENCE);
                canvas.drawRect(viewDrawingRect, this.hasFocus() ? this.mFocusPaint
                        : this.mNoFocusPaint);

                canvas.restore();

            } else {

                Rect topRect = new Rect(viewDrawingRect.left,
                        viewDrawingRect.top, viewDrawingRect.right,
                        this.mDrawRect.top);
                if (topRect.width() > 0 && topRect.height() > 0) {
                    canvas.drawRect(topRect, this.hasFocus() ? this.mFocusPaint
                            : this.mNoFocusPaint);
                }
                Rect bottomRect = new Rect(viewDrawingRect.left,
                        this.mDrawRect.bottom, viewDrawingRect.right,
                        viewDrawingRect.bottom);
                if (bottomRect.width() > 0 && bottomRect.height() > 0) {
                    canvas.drawRect(bottomRect, this.hasFocus() ? this.mFocusPaint
                            : this.mNoFocusPaint);
                }
                Rect leftRect = new Rect(viewDrawingRect.left, topRect.bottom,
                        this.mDrawRect.left, bottomRect.top);
                if (leftRect.width() > 0 && leftRect.height() > 0) {
                    canvas.drawRect(leftRect, this.hasFocus() ? this.mFocusPaint
                            : this.mNoFocusPaint);
                }
                Rect rightRect = new Rect(this.mDrawRect.right, topRect.bottom,
                        viewDrawingRect.right, bottomRect.top);
                if (rightRect.width() > 0 && rightRect.height() > 0) {
                    canvas.drawRect(rightRect, this.hasFocus() ? this.mFocusPaint
                            : this.mNoFocusPaint);
                }

                path.addRect(new RectF(this.mDrawRect), Direction.CW);

                this.mOutlinePaint.setColor(0xFFFF8A00);

            }

            canvas.drawPath(path, this.mOutlinePaint);

            if (this.mMode == HighlightView.ModifyMode.Grow) {
                if (this.mCircle) {
                    int width = this.mResizeDrawableDiagonal.getIntrinsicWidth();
                    int height = this.mResizeDrawableDiagonal.getIntrinsicHeight();

                    int d = (int) Math.round(Math.cos(/* 45deg */Math.PI / 4D)
                            * (this.mDrawRect.width() / 2D));
                    int x = this.mDrawRect.left + (this.mDrawRect.width() / 2) + d
                            - width / 2;
                    int y = this.mDrawRect.top + (this.mDrawRect.height() / 2) - d
                            - height / 2;
                    this.mResizeDrawableDiagonal.setBounds(x, y, x
                            + this.mResizeDrawableDiagonal.getIntrinsicWidth(), y
                            + this.mResizeDrawableDiagonal.getIntrinsicHeight());
                    this.mResizeDrawableDiagonal.draw(canvas);
                } else {
                    int left = this.mDrawRect.left + 1;
                    int right = this.mDrawRect.right + 1;
                    int top = this.mDrawRect.top + 4;
                    int bottom = this.mDrawRect.bottom + 3;

                    int widthWidth = this.mResizeDrawableWidth.getIntrinsicWidth() / 2;
                    int widthHeight = this.mResizeDrawableWidth.getIntrinsicHeight() / 2;
                    int heightHeight = this.mResizeDrawableHeight
                            .getIntrinsicHeight() / 2;
                    int heightWidth = this.mResizeDrawableHeight.getIntrinsicWidth() / 2;

                    int xMiddle = this.mDrawRect.left
                            + ((this.mDrawRect.right - this.mDrawRect.left) / 2);
                    int yMiddle = this.mDrawRect.top
                            + ((this.mDrawRect.bottom - this.mDrawRect.top) / 2);

                    this.mResizeDrawableWidth.setBounds(left - widthWidth, yMiddle
                            - widthHeight, left + widthWidth, yMiddle
                            + widthHeight);
                    this.mResizeDrawableWidth.draw(canvas);

                    this.mResizeDrawableWidth.setBounds(right - widthWidth, yMiddle
                            - widthHeight, right + widthWidth, yMiddle
                            + widthHeight);
                    this.mResizeDrawableWidth.draw(canvas);

                    this.mResizeDrawableHeight.setBounds(xMiddle - heightWidth, top
                            - heightHeight, xMiddle + heightWidth, top
                            + heightHeight);
                    this.mResizeDrawableHeight.draw(canvas);

                    this.mResizeDrawableHeight.setBounds(xMiddle - heightWidth,
                            bottom - heightHeight, xMiddle + heightWidth,
                            bottom + heightHeight);
                    this.mResizeDrawableHeight.draw(canvas);
                }
            }
        } else {
            this.mOutlinePaint.setColor(0xFF000000);
            canvas.drawRect(this.mDrawRect, this.mOutlinePaint);
        }
    }

    public final HighlightView.ModifyMode getMode() {

        return this.mMode;
    }

    public final void setMode(HighlightView.ModifyMode mode) {

        if (mode != this.mMode) {
            this.mMode = mode;
            this.mContext.invalidate();
        }
    }

    // Determines which edges are hit by touching at (x, y).
    public final int getHit(float x, float y) {

        Rect r = this.computeLayout();
        float hysteresis = 20.0F;
        int retval = HighlightView.GROW_NONE;

        if (this.mCircle) {
            float distX = x - (float) r.centerX();
            float distY = y - (float) r.centerY();
            int distanceFromCenter = (int) Math.sqrt((double) (distX * distX + distY
                    * distY));
            int radius = this.mDrawRect.width() / 2;
            int delta = distanceFromCenter - radius;
            if ((float) Math.abs(delta) <= hysteresis) {
                if (Math.abs(distY) > Math.abs(distX)) {
                    retval = distY < 0 ? HighlightView.GROW_TOP_EDGE : HighlightView.GROW_BOTTOM_EDGE;
                } else {
                    retval = distX < 0 ? HighlightView.GROW_LEFT_EDGE : HighlightView.GROW_RIGHT_EDGE;
                }
            } else if (distanceFromCenter < radius) {
                retval = HighlightView.MOVE;
            } else {
                retval = HighlightView.GROW_NONE;
            }
        } else {
            // verticalCheck makes sure the position is between the top and
            // the bottom edge (with some tolerance). Similar for horizCheck.
            boolean verticalCheck = (y >= (float) r.top - hysteresis)
                    && (y < (float) r.bottom + hysteresis);
            boolean horizCheck = (x >= (float) r.left - hysteresis)
                    && (x < (float) r.right + hysteresis);

            // Check whether the position is near some edge(s).
            if ((Math.abs((float) r.left - x) < hysteresis) && verticalCheck) {
                retval |= HighlightView.GROW_LEFT_EDGE;
            }
            if ((Math.abs((float) r.right - x) < hysteresis) && verticalCheck) {
                retval |= HighlightView.GROW_RIGHT_EDGE;
            }
            if ((Math.abs((float) r.top - y) < hysteresis) && horizCheck) {
                retval |= HighlightView.GROW_TOP_EDGE;
            }
            if ((Math.abs((float) r.bottom - y) < hysteresis) && horizCheck) {
                retval |= HighlightView.GROW_BOTTOM_EDGE;
            }

            // Not near any edge but inside the rectangle: move.
            if (retval == HighlightView.GROW_NONE && r.contains((int) x, (int) y)) {
                retval = HighlightView.MOVE;
            }
        }
        return retval;
    }

    // Handles motion (dx, dy) in screen space.
    // The "edge" parameter specifies which edges the user is dragging.
    final void handleMotion(int edge, float dx, float dy) {

        float dx1 = dx;
        float dy1 = dy;
        Rect r = this.computeLayout();
        switch (edge) {
            case HighlightView.GROW_NONE:
                break;
            case HighlightView.MOVE:
                // Convert to image space before sending to moveBy().
                this.moveBy(dx1 * (this.mCropRect.width() / r.width()),
                        dy1 * (this.mCropRect.height() / r.height()));
                break;
            default:
                if (((HighlightView.GROW_LEFT_EDGE | HighlightView.GROW_RIGHT_EDGE) & edge) == 0) {
                    dx1 = 0;
                }

                if (((HighlightView.GROW_TOP_EDGE | HighlightView.GROW_BOTTOM_EDGE) & edge) == 0) {
                    dy1 = 0;
                }

                // Convert to image space before sending to growBy().
                float xDelta = dx1 * (this.mCropRect.width() / r.width());
                float yDelta = dy1 * (this.mCropRect.height() / r.height());
                this.growBy((((edge & HighlightView.GROW_LEFT_EDGE) == 0) ? 1 : -1) * xDelta,
                        (((edge & HighlightView.GROW_TOP_EDGE) == 0) ? 1 : -1) * yDelta);
                break;
        }
    }

    // Grows the cropping rectange by (dx, dy) in image space.
    private final void moveBy(float dx, float dy) {

        Rect invalRect = new Rect(this.mDrawRect);

        this.mCropRect.offset(dx, dy);

        // Put the cropping rectangle inside image rectangle.
        this.mCropRect.offset(Math.max((float) 0, this.mImageRect.left - this.mCropRect.left),
                Math.max((float) 0, this.mImageRect.top - this.mCropRect.top));

        this.mCropRect.offset(Math.min((float) 0, this.mImageRect.right - this.mCropRect.right),
                Math.min((float) 0, this.mImageRect.bottom - this.mCropRect.bottom));

        this.mDrawRect = this.computeLayout();
        invalRect.union(this.mDrawRect);
        invalRect.inset(-10, -10);
        this.mContext.invalidate(invalRect);
    }

    // Grows the cropping rectange by (dx, dy) in image space.
    private final void growBy(float dx, float dy) {

        float dy1 = dy;
        float dx1 = dx;
        if (this.mMaintainAspectRatio) {
            if (dx1 != (float) 0) {
                dy1 = dx1 / this.mInitialAspectRatio;
            } else if (dy1 != (float) 0) {
                dx1 = dy1 * this.mInitialAspectRatio;
            }
        }

        // Don't let the cropping rectangle grow too fast.
        // Grow at most half of the difference between the image rectangle and
        // the cropping rectangle.
        RectF r = new RectF(this.mCropRect);
        if (dx1 > 0.0F && r.width() + 2.0F * dx1 > this.mImageRect.width()) {
            dx1 = (this.mImageRect.width() - r.width()) / 2F;
            if (this.mMaintainAspectRatio) {
                dy1 = dx1 / this.mInitialAspectRatio;
            }
        }
        if (dy1 > 0.0F && r.height() + 2.0F * dy1 > this.mImageRect.height()) {
            dy1 = (this.mImageRect.height() - r.height()) / 2F;
            if (this.mMaintainAspectRatio) {
                dx1 = dy1 * this.mInitialAspectRatio;
            }
        }

        r.inset(-dx1, -dy1);

        // Don't let the cropping rectangle shrink too fast.
        float widthCap = 25.0F;
        if (r.width() < widthCap) {
            r.inset(-(widthCap - r.width()) / 2.0F, 0.0F);
        }
        float heightCap = this.mMaintainAspectRatio ? (widthCap / this.mInitialAspectRatio)
                : widthCap;
        if (r.height() < heightCap) {
            r.inset(0.0F, -(heightCap - r.height()) / 2.0F);
        }

        // Put the cropping rectangle inside the image rectangle.
        if (r.left < this.mImageRect.left) {
            r.offset(this.mImageRect.left - r.left, 0.0F);
        } else if (r.right > this.mImageRect.right) {
            r.offset(-(r.right - this.mImageRect.right), (float) 0);
        }
        if (r.top < this.mImageRect.top) {
            r.offset(0.0F, this.mImageRect.top - r.top);
        } else if (r.bottom > this.mImageRect.bottom) {
            r.offset(0.0F, -(r.bottom - this.mImageRect.bottom));
        }

        this.mCropRect.set(r);
        this.mDrawRect = this.computeLayout();
        this.mContext.invalidate();
    }

    // Returns the cropping rectangle in image space.
    public final Rect getCropRect() {

        return new Rect((int) this.mCropRect.left, (int) this.mCropRect.top,
                (int) this.mCropRect.right, (int) this.mCropRect.bottom);
    }

    // Maps the cropping rectangle from image space to screen space.
    private Rect computeLayout() {

        RectF r = new RectF(this.mCropRect.left, this.mCropRect.top, this.mCropRect.right,
                this.mCropRect.bottom);
        this.mMatrix.mapRect(r);
        return new Rect(Math.round(r.left), Math.round(r.top),
                Math.round(r.right), Math.round(r.bottom));
    }

    public final void invalidate() {

        this.mDrawRect = this.computeLayout();
    }

    public final void setup(Matrix m, Rect imageRect, RectF cropRect, boolean circle,
                            boolean maintainAspectRatio) {

        boolean maintainAspectRatio1 = maintainAspectRatio;
        if (circle) {
            maintainAspectRatio1 = true;
        }
        this.mMatrix = new Matrix(m);

        this.mCropRect = cropRect;
        this.mImageRect = new RectF(imageRect);
        this.mMaintainAspectRatio = maintainAspectRatio1;
        this.mCircle = circle;

        this.mInitialAspectRatio = this.mCropRect.width() / this.mCropRect.height();
        this.mDrawRect = this.computeLayout();

        this.mFocusPaint.setARGB(125, 50, 50, 50);
        this.mNoFocusPaint.setARGB(125, 50, 50, 50);
        this.mOutlinePaint.setStrokeWidth(3.0F);
        this.mOutlinePaint.setStyle(Style.STROKE);
        this.mOutlinePaint.setAntiAlias(true);

        this.mMode = HighlightView.ModifyMode.None;
        this.init();
    }
    enum ModifyMode {
        None, Move, Grow
    }
}
