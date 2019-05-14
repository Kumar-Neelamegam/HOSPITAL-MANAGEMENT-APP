package kdmc_kumar.Utilities_Others;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.styleable;

/**
 * Created by Android on 20-Mar-18.
 */

public class CircleImageView extends AppCompatImageView {

    private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;

    private static final Config BITMAP_CONFIG = Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_BORDER_OVERLAY = false;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    private final Paint mFillPaint = new Paint();

    private int mBorderColor = CircleImageView.DEFAULT_BORDER_COLOR;
    private int mBorderWidth = CircleImageView.DEFAULT_BORDER_WIDTH;
    private int mFillColor = CircleImageView.DEFAULT_FILL_COLOR;

    @Nullable
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mDrawableRadius;
    private float mBorderRadius;

    private ColorFilter mColorFilter;

    private boolean mReady;
    private boolean mSetupPending;
    private boolean mBorderOverlay;
    private boolean mDisableCircularTransformation;

    public CircleImageView(Context context) {
        super(context);

        this.init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, styleable.CircleImageView, defStyle, 0);

        this.mBorderWidth = a.getDimensionPixelSize(styleable.CircleImageView_civ_border_width, CircleImageView.DEFAULT_BORDER_WIDTH);
        this.mBorderColor = a.getColor(styleable.CircleImageView_civ_border_color, CircleImageView.DEFAULT_BORDER_COLOR);
        this.mBorderOverlay = a.getBoolean(styleable.CircleImageView_civ_border_overlay, CircleImageView.DEFAULT_BORDER_OVERLAY);
        this.mFillColor = a.getColor(styleable.CircleImageView_civ_fill_color, CircleImageView.DEFAULT_FILL_COLOR);

        a.recycle();

        this.init();
    }

    private void init() {
        super.setScaleType(CircleImageView.SCALE_TYPE);
        this.mReady = true;

        if (this.mSetupPending) {
            this.setup();
            this.mSetupPending = false;
        }
    }

    @Override
    public final ImageView.ScaleType getScaleType() {
        return CircleImageView.SCALE_TYPE;
    }

    @Override
    public final void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType != CircleImageView.SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public final void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        if (this.mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        }

        if (this.mBitmap == null) {
            return;
        }

        if (this.mFillColor != Color.TRANSPARENT) {
            canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mFillPaint);
        }
        canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mBitmapPaint);
        if (this.mBorderWidth > 0) {
            canvas.drawCircle(this.mBorderRect.centerX(), this.mBorderRect.centerY(), this.mBorderRadius, this.mBorderPaint);
        }
    }

    @Override
    protected final void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.setup();
    }

    @Override
    public final void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        this.setup();
    }

    @Override
    public final void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        this.setup();
    }

    public final int getBorderColor() {
        return this.mBorderColor;
    }

    private final void setBorderColor(@ColorInt int borderColor) {
        if (borderColor == this.mBorderColor) {
            return;
        }

        this.mBorderColor = borderColor;
        this.mBorderPaint.setColor(this.mBorderColor);
        this.invalidate();
    }

    /**
     * @deprecated Use {@link #setBorderColor(int)} instead
     */
    @Deprecated
    public final void setBorderColorResource(@ColorRes int borderColorRes) {
        this.setBorderColor(this.getContext().getResources().getColor(borderColorRes));
    }

    /**
     * Return the color drawn behind the circle-shaped drawable.
     *
     * @return The color drawn behind the drawable
     *
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public final int getFillColor() {
        return this.mFillColor;
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColor The color to be drawn behind the drawable
     *
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public final void setFillColor(@ColorInt int fillColor) {
        if (fillColor == this.mFillColor) {
            return;
        }

        this.mFillColor = fillColor;
        this.mFillPaint.setColor(fillColor);
        this.invalidate();
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColorRes The color resource to be resolved to a color and
     *                     drawn behind the drawable
     *
     * @deprecated Fill color support is going to be removed in the future
     */
    @Deprecated
    public final void setFillColorResource(@ColorRes int fillColorRes) {
        this.setFillColor(this.getContext().getResources().getColor(fillColorRes));
    }

    public final int getBorderWidth() {
        return this.mBorderWidth;
    }

    public final void setBorderWidth(int borderWidth) {
        if (borderWidth == this.mBorderWidth) {
            return;
        }

        this.mBorderWidth = borderWidth;
        this.setup();
    }

    public final boolean isBorderOverlay() {
        return this.mBorderOverlay;
    }

    public final void setBorderOverlay(boolean borderOverlay) {
        if (borderOverlay == this.mBorderOverlay) {
            return;
        }

        this.mBorderOverlay = borderOverlay;
        this.setup();
    }

    public final boolean isDisableCircularTransformation() {
        return this.mDisableCircularTransformation;
    }

    public final void setDisableCircularTransformation(boolean disableCircularTransformation) {
        if (this.mDisableCircularTransformation == disableCircularTransformation) {
            return;
        }

        this.mDisableCircularTransformation = disableCircularTransformation;
        this.initializeBitmap();
    }

    @Override
    public final void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.initializeBitmap();
    }

    @Override
    public final void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        this.initializeBitmap();
    }

    @Override
    public final void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        this.initializeBitmap();
    }

    @Override
    public final void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.initializeBitmap();
    }

    @Override
    public final void setColorFilter(ColorFilter cf) {
        if (cf == this.mColorFilter) {
            return;
        }

        this.mColorFilter = cf;
        this.applyColorFilter();
        this.invalidate();
    }

    @Override
    public final ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    private void applyColorFilter() {
        if (this.mBitmapPaint != null) {
            this.mBitmapPaint.setColorFilter(this.mColorFilter);
        }
    }

    @Nullable
    private static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            bitmap = drawable instanceof ColorDrawable ? Bitmap.createBitmap(CircleImageView.COLORDRAWABLE_DIMENSION, CircleImageView.COLORDRAWABLE_DIMENSION, CircleImageView.BITMAP_CONFIG) : Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), CircleImageView.BITMAP_CONFIG);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initializeBitmap() {
        this.mBitmap = this.mDisableCircularTransformation ? null : CircleImageView.getBitmapFromDrawable(this.getDrawable());
        this.setup();
    }

    private void setup() {
        if (!this.mReady) {
            this.mSetupPending = true;
            return;
        }

        if (this.getWidth() == 0 && this.getHeight() == 0) {
            return;
        }

        if (this.mBitmap == null) {
            this.invalidate();
            return;
        }

        this.mBitmapShader = new BitmapShader(this.mBitmap, TileMode.CLAMP, TileMode.CLAMP);

        this.mBitmapPaint.setAntiAlias(true);
        this.mBitmapPaint.setShader(this.mBitmapShader);

        this.mBorderPaint.setStyle(Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBorderColor);
        this.mBorderPaint.setStrokeWidth((float) this.mBorderWidth);

        this.mFillPaint.setStyle(Style.FILL);
        this.mFillPaint.setAntiAlias(true);
        this.mFillPaint.setColor(this.mFillColor);

        this.mBitmapHeight = this.mBitmap.getHeight();
        this.mBitmapWidth = this.mBitmap.getWidth();

        this.mBorderRect.set(this.calculateBounds());
        this.mBorderRadius = Math.min((this.mBorderRect.height() - (float) this.mBorderWidth) / 2.0f, (this.mBorderRect.width() - (float) this.mBorderWidth) / 2.0f);

        this.mDrawableRect.set(this.mBorderRect);
        if (!this.mBorderOverlay && this.mBorderWidth > 0) {
            this.mDrawableRect.inset((float) this.mBorderWidth - 1.0f, (float) this.mBorderWidth - 1.0f);
        }
        this.mDrawableRadius = Math.min(this.mDrawableRect.height() / 2.0f, this.mDrawableRect.width() / 2.0f);

        this.applyColorFilter();
        this.updateShaderMatrix();
        this.invalidate();
    }

    private RectF calculateBounds() {
        int availableWidth  = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
        int availableHeight = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();

        int sideLength = Math.min(availableWidth, availableHeight);

        float left = (float) this.getPaddingLeft() + (float) (availableWidth - sideLength) / 2.0f;
        float top = (float) this.getPaddingTop() + (float) (availableHeight - sideLength) / 2.0f;

        return new RectF(left, top, left + (float) sideLength, top + (float) sideLength);
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = (float) 0;
        float dy = (float) 0;

        this.mShaderMatrix.set(null);

        if ((float) this.mBitmapWidth * this.mDrawableRect.height() > this.mDrawableRect.width() * (float) this.mBitmapHeight) {
            scale = this.mDrawableRect.height() / this.mBitmapHeight;
            dx = (this.mDrawableRect.width() - (float) this.mBitmapWidth * scale) * 0.5f;
        } else {
            scale = this.mDrawableRect.width() / this.mBitmapWidth;
            dy = (this.mDrawableRect.height() - (float) this.mBitmapHeight * scale) * 0.5f;
        }

        this.mShaderMatrix.setScale(scale, scale);
        this.mShaderMatrix.postTranslate((float) (int) (dx + 0.5f) + this.mDrawableRect.left, (float) (int) (dy + 0.5f) + this.mDrawableRect.top);

        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

}
