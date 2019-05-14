package kdmc_kumar.Utilities_Others;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

public class RoundedDrawable extends Drawable {

    private static final String TAG = "RoundedDrawable";
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private final RectF mBounds = new RectF();
    private final RectF mDrawableRect = new RectF();
    private final RectF mBitmapRect = new RectF();
    private final BitmapShader mBitmapShader;
    private final Paint mBitmapPaint;
    private final int mBitmapWidth;
    private final int mBitmapHeight;
    private final RectF mBorderRect = new RectF();
    private final Paint mBorderPaint;
    private final Matrix mShaderMatrix = new Matrix();

    private float mCornerRadius;
    private boolean mOval;
    private float mBorderWidth;
    private ColorStateList mBorderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;

    private RoundedDrawable(Bitmap bitmap) {

        this.mBitmapWidth = bitmap.getWidth();
        this.mBitmapHeight = bitmap.getHeight();
        this.mBitmapRect.set((float) 0, (float) 0, (float) this.mBitmapWidth, (float) this.mBitmapHeight);

        this.mBitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);

        this.mBitmapPaint = new Paint();
        this.mBitmapPaint.setStyle(Style.FILL);
        this.mBitmapPaint.setAntiAlias(true);
        this.mBitmapPaint.setShader(this.mBitmapShader);

        this.mBorderPaint = new Paint();
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBorderColor.getColorForState(this.getState(), RoundedDrawable.DEFAULT_BORDER_COLOR));
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
    }

    public static RoundedDrawable fromBitmap(Bitmap bitmap) {
        return bitmap != null ? new RoundedDrawable(bitmap) : null;
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof RoundedDrawable) {
                // just return if it's already a RoundedDrawable
                return drawable;
            } else if (drawable instanceof LayerDrawable) {
                LayerDrawable ld = (LayerDrawable) drawable;
                int num = ld.getNumberOfLayers();

                // loop through layers to and change to RoundedDrawables if possible
                for (int i = 0; i < num; i++) {
                    Drawable d = ld.getDrawable(i);
                    ld.setDrawableByLayerId(ld.getId(i), RoundedDrawable.fromDrawable(d));
                }
                return ld;
            }

            // try to get a bitmap from the drawable and
            Bitmap bm = RoundedDrawable.drawableToBitmap(drawable);
            if (bm != null) {
                return new RoundedDrawable(bm);
            } else {
                Log.w(RoundedDrawable.TAG, "Failed to create bitmap from drawable!");
            }
        }
        return drawable;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        @Nullable Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 1);
        int height = Math.max(drawable.getIntrinsicHeight(), 1);
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
            bitmap = null;
        }

        return bitmap;
    }

    @Override
    public final boolean isStateful() {
        return this.mBorderColor.isStateful();
    }

    @Override
    protected final boolean onStateChange(int[] state) {
        int newColor = this.mBorderColor.getColorForState(state, 0);
        if (this.mBorderPaint.getColor() == newColor) {
            return super.onStateChange(state);
        } else {
            this.mBorderPaint.setColor(newColor);
            return true;
        }
    }

    private void updateShaderMatrix() {
        float scale;
        float dx;
        float dy;

        switch (this.mScaleType) {
            case CENTER:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset((this.mBorderWidth) / 2.0F, (this.mBorderWidth) / 2.0F);

                this.mShaderMatrix.set(null);
                this.mShaderMatrix.setTranslate((float) (int) ((this.mBorderRect.width() - (float) this.mBitmapWidth) * 0.5f + 0.5f),
                        (float) (int) ((this.mBorderRect.height() - (float) this.mBitmapHeight) * 0.5f + 0.5f));
                break;

            case CENTER_CROP:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset((this.mBorderWidth) / 2.0F, (this.mBorderWidth) / 2.0F);

                this.mShaderMatrix.set(null);

                dx = (float) 0;
                dy = (float) 0;

                if ((float) this.mBitmapWidth * this.mBorderRect.height() > this.mBorderRect.width() * (float) this.mBitmapHeight) {
                    scale = this.mBorderRect.height() / this.mBitmapHeight;
                    dx = (this.mBorderRect.width() - (float) this.mBitmapWidth * scale) * 0.5f;
                } else {
                    scale = this.mBorderRect.width() / this.mBitmapWidth;
                    dy = (this.mBorderRect.height() - (float) this.mBitmapHeight * scale) * 0.5f;
                }

                this.mShaderMatrix.setScale(scale, scale);
                this.mShaderMatrix.postTranslate((float) (int) (dx + 0.5f) + this.mBorderWidth,
                        (float) (int) (dy + 0.5f) + this.mBorderWidth);
                break;

            case CENTER_INSIDE:
                this.mShaderMatrix.set(null);

                scale = this.mBitmapWidth <= this.mBounds.width() && this.mBitmapHeight <= this.mBounds.height() ? 1.0f : Math.min(this.mBounds.width() / (float) this.mBitmapWidth,
                        this.mBounds.height() / (float) this.mBitmapHeight);

                dx = (float) (int) ((this.mBounds.width() - (float) this.mBitmapWidth * scale) * 0.5f + 0.5f);
                dy = (float) (int) ((this.mBounds.height() - (float) this.mBitmapHeight * scale) * 0.5f + 0.5f);

                this.mShaderMatrix.setScale(scale, scale);
                this.mShaderMatrix.postTranslate(dx, dy);

                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset((this.mBorderWidth) / 2.0F, (this.mBorderWidth) / 2.0F);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;

            default:
            case FIT_CENTER:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.CENTER);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset((this.mBorderWidth) / 2.0F, (this.mBorderWidth) / 2.0F);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;

            case FIT_END:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.END);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset((this.mBorderWidth) / 2.0F, (this.mBorderWidth) / 2.0F);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;

            case FIT_START:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.START);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset((this.mBorderWidth) / 2.0F, (this.mBorderWidth) / 2.0F);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;

            case FIT_XY:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset((this.mBorderWidth) / 2.0F, (this.mBorderWidth) / 2.0F);
                this.mShaderMatrix.set(null);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
        }

        this.mDrawableRect.set(this.mBorderRect);
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

    @Override
    protected final void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        this.mBounds.set(bounds);

        this.updateShaderMatrix();
    }

    @Override
    public final void draw(@NonNull Canvas canvas) {

        if (this.mOval) {
            if (this.mBorderWidth > (float) 0) {
                canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
                canvas.drawOval(this.mBorderRect, this.mBorderPaint);
            } else {
                canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
            }
        } else {
            if (this.mBorderWidth > (float) 0) {
                canvas.drawRoundRect(this.mDrawableRect, Math.max(this.mCornerRadius, (float) 0),
                        Math.max(this.mCornerRadius, (float) 0), this.mBitmapPaint);
                canvas.drawRoundRect(this.mBorderRect, this.mCornerRadius, this.mCornerRadius, this.mBorderPaint);
            } else {
                canvas.drawRoundRect(this.mDrawableRect, this.mCornerRadius, this.mCornerRadius, this.mBitmapPaint);
            }
        }
    }

    @Override
    public final int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public final void setAlpha(int i) {
        this.mBitmapPaint.setAlpha(i);
        this.invalidateSelf();
    }

    @Override
    public final void setColorFilter(ColorFilter colorFilter) {
        this.mBitmapPaint.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    @Override
    public final void setDither(boolean dither) {
        this.mBitmapPaint.setDither(dither);
        this.invalidateSelf();
    }

    @Override
    public final void setFilterBitmap(boolean filter) {
        this.mBitmapPaint.setFilterBitmap(filter);
        this.invalidateSelf();
    }

    @Override
    public final int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override
    public final int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public final float getCornerRadius() {
        return this.mCornerRadius;
    }

    public final RoundedDrawable setCornerRadius(float radius) {
        this.mCornerRadius = radius;
        return this;
    }

    public final float getBorderWidth() {
        return this.mBorderWidth;
    }

    public final RoundedDrawable setBorderWidth(int width) {
        this.mBorderWidth = (float) width;
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
        return this;
    }

    public final int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
    }

    public final RoundedDrawable setBorderColor(int color) {
        return this.setBorderColors(ColorStateList.valueOf(color));
    }

    public final ColorStateList getBorderColors() {
        return this.mBorderColor;
    }

    public final RoundedDrawable setBorderColors(ColorStateList colors) {
        this.mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
        this.mBorderPaint.setColor(this.mBorderColor.getColorForState(this.getState(), RoundedDrawable.DEFAULT_BORDER_COLOR));
        return this;
    }

    public final boolean isOval() {
        return this.mOval;
    }

    public final RoundedDrawable setOval(boolean oval) {
        this.mOval = oval;
        return this;
    }

    public final ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public final RoundedDrawable setScaleType(ImageView.ScaleType scaleType) {
        ImageView.ScaleType scaleType1 = scaleType;
        if (scaleType1 == null) {
            scaleType1 = ImageView.ScaleType.FIT_CENTER;
        }
        if (this.mScaleType != scaleType1) {
            this.mScaleType = scaleType1;
            this.updateShaderMatrix();
        }
        return this;
    }

    public final Bitmap toBitmap() {
        return RoundedDrawable.drawableToBitmap(this);
    }
}
