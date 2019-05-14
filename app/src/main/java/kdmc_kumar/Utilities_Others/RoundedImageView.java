package kdmc_kumar.Utilities_Others;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.styleable;


public class RoundedImageView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "RoundedImageView";
    private static final int DEFAULT_RADIUS = 0;
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final ImageView.ScaleType[] SCALE_TYPES = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    private int mCornerRadius = RoundedImageView.DEFAULT_RADIUS;
    private int mBorderWidth = RoundedImageView.DEFAULT_BORDER_WIDTH;
    private ColorStateList mBorderColor =
            ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
    private boolean mOval;
    private boolean mRoundBackground;

    private int mResource;
    private Drawable mDrawable;
    private Drawable mBackgroundDrawable;

    private ImageView.ScaleType mScaleType;

    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, styleable.RoundedImageView, defStyle, 0);

        int index = a.getInt(styleable.RoundedImageView_android_scaleType, -1);
        if (index >= 0) {
            this.setScaleType(RoundedImageView.SCALE_TYPES[index]);
        } else {
            // default scaletype to FIT_CENTER
            this.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

        this.mCornerRadius = a.getDimensionPixelSize(styleable.RoundedImageView_corner_radius, -1);
        this.mBorderWidth = 1;//a.getDimensionPixelSize(R.styleable.RoundedImageView_border_width, -1);

        // don't allow negative values for radius and border
        if (this.mCornerRadius < 0) {
            this.mCornerRadius = RoundedImageView.DEFAULT_RADIUS;
        }
        if (this.mBorderWidth < 0) {
            this.mBorderWidth = RoundedImageView.DEFAULT_BORDER_WIDTH;
        }

        this.mBorderColor = a.getColorStateList(styleable.RoundedImageView_border_color);
        if (this.mBorderColor == null) {
            this.mBorderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        }

        this.mRoundBackground = a.getBoolean(styleable.RoundedImageView_round_background, false);
        this.mOval = a.getBoolean(styleable.RoundedImageView_is_oval, false);

        this.updateDrawableAttrs();
        this.updateBackgroundDrawableAttrs();

        a.recycle();
    }

    @Override
    protected final void drawableStateChanged() {
        super.drawableStateChanged();
        this.invalidate();
    }

    /**
     * Return the current scale type in use by this ImageView.
     *
     * @attr ref android.R.styleable#ImageView_scaleType
     * @see ImageView.ScaleType
     */
    @Override
    public final ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    /**
     * Controls how the image should be resized or moved to match the size
     * of this ImageView.
     *
     * @param scaleType The desired scaling mode.
     * @attr ref android.R.styleable#ImageView_scaleType
     */
    @Override
    public final void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;

            switch (scaleType) {
                case CENTER:
                case CENTER_CROP:
                case CENTER_INSIDE:
                case FIT_CENTER:
                case FIT_START:
                case FIT_END:
                case FIT_XY:
                    super.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }

            this.updateDrawableAttrs();
            this.updateBackgroundDrawableAttrs();
            this.invalidate();
        }
    }

    @Override
    public final void setImageDrawable(Drawable drawable) {
        this.mResource = 0;
        this.mDrawable = RoundedDrawable.fromDrawable(drawable);
        this.updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    @Override
    public final void setImageBitmap(Bitmap bm) {
        this.mResource = 0;
        this.mDrawable = RoundedDrawable.fromBitmap(bm);
        this.updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    @Override
    public final void setImageResource(int resId) {
        if (this.mResource != resId) {
            this.mResource = resId;
            this.mDrawable = this.resolveResource();
            this.updateDrawableAttrs();
            super.setImageDrawable(this.mDrawable);
        }
    }

    @Override
    public final void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.setImageDrawable(this.getDrawable());
    }

    private Drawable resolveResource() {
        Resources rsrc = this.getResources();
        if (rsrc == null) {
            return null;
        }

        Drawable d = null;

        if (this.mResource != 0) {
            try {
                d = rsrc.getDrawable(this.mResource);
            } catch (Exception e) {
                Log.w(RoundedImageView.TAG, "Unable to find resource: " + this.mResource, e);
                // Don't try again.
                this.mResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(d);
    }

//  public void setBackground(Drawable background) {
//    setBackgroundDrawable(background);
//  }

    private void updateDrawableAttrs() {
        this.updateAttrs(this.mDrawable, false);
    }

    private void updateBackgroundDrawableAttrs() {
        this.updateAttrs(this.mBackgroundDrawable, true);
    }

    private void updateAttrs(Drawable drawable, boolean background) {
        if (drawable == null) {
            return;
        }

        if (drawable instanceof RoundedDrawable) {
            ((RoundedDrawable) drawable).setScaleType(this.mScaleType)
                    .setCornerRadius((float) (background && !this.mRoundBackground ? 0 : this.mCornerRadius))
                    .setBorderWidth(background && !this.mRoundBackground ? 0 : this.mBorderWidth)
                    .setBorderColors(this.mBorderColor)
                    .setOval(this.mOval);
        } else if (drawable instanceof LayerDrawable) {
            // loop through layers to and set drawable attrs
            LayerDrawable ld = ((LayerDrawable) drawable);
            int layers = ld.getNumberOfLayers();
            for (int i = 0; i < layers; i++) {
                this.updateAttrs(ld.getDrawable(i), background);
            }
        }
    }

    @Override
    @Deprecated
    public final void setBackgroundDrawable(Drawable background) {
        this.mBackgroundDrawable = RoundedDrawable.fromDrawable(background);
        this.updateBackgroundDrawableAttrs();
        super.setBackgroundDrawable(this.mBackgroundDrawable);
    }

    public final int getCornerRadius() {
        return this.mCornerRadius;
    }

    public final void setCornerRadius(int radius) {
        if (this.mCornerRadius == radius) {
            return;
        }

        this.mCornerRadius = radius;
        this.updateDrawableAttrs();
        this.updateBackgroundDrawableAttrs();
    }

    public final int getBorderWidth() {
        return this.mBorderWidth;
    }

    public final void setBorderWidth(int width) {
        if (this.mBorderWidth == width) {
            return;
        }

        this.mBorderWidth = width;
        this.updateDrawableAttrs();
        this.updateBackgroundDrawableAttrs();
        this.invalidate();
    }

    public final int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
    }

    public final void setBorderColor(int color) {
        this.setBorderColors(ColorStateList.valueOf(color));
    }

    public final ColorStateList getBorderColors() {
        return this.mBorderColor;
    }

    private final void setBorderColors(ColorStateList colors) {
        if (this.mBorderColor.equals(colors)) {
            return;
        }

        this.mBorderColor =
                (colors != null) ? colors : ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        this.updateDrawableAttrs();
        this.updateBackgroundDrawableAttrs();
        if (this.mBorderWidth > 0) {
            this.invalidate();
        }
    }

    public final boolean isOval() {
        return this.mOval;
    }

    public final void setOval(boolean oval) {
        this.mOval = oval;
        this.updateDrawableAttrs();
        this.updateBackgroundDrawableAttrs();
        this.invalidate();
    }

    public final boolean isRoundBackground() {
        return this.mRoundBackground;
    }

    public final void setRoundBackground(boolean roundBackground) {
        if (this.mRoundBackground == roundBackground) {
            return;
        }

        this.mRoundBackground = roundBackground;
        this.updateBackgroundDrawableAttrs();
        this.invalidate();
    }
}
