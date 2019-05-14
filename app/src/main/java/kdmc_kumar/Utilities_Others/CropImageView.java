package kdmc_kumar.Utilities_Others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;

import kdmc_kumar.Utilities_Others.HighlightView.ModifyMode;

class CropImageView extends ImageViewTouchBase {

    final ArrayList<HighlightView> mHighlightViews = new ArrayList<>();
    @Nullable
    private
    HighlightView mMotionHighlightView;
    private float mLastX;
    private float mLastY;
    private int mMotionEdge;

    private final Context mContext;

    public CropImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
        mContext = context;
    }

    @Override
    protected final void onLayout(boolean changed, int left, int top,
                                  int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        if (this.mBitmapDisplayed.getBitmap() != null) {
            for (Iterator<HighlightView> iterator = this.mHighlightViews.iterator(); iterator.hasNext(); ) {
                HighlightView hv = iterator.next();
                hv.mMatrix.set(this.getImageMatrix());
                hv.invalidate();
                if (hv.mIsFocused) {
                    this.centerBasedOnHighlightView(hv);
                }
            }
        }
    }

    @Override
    public final void zoomTo(float scale, float centerX, float centerY) {

        super.zoomTo(scale, centerX, centerY);
        for (Iterator<HighlightView> iterator = this.mHighlightViews.iterator(); iterator.hasNext(); ) {
            HighlightView hv = iterator.next();
            hv.mMatrix.set(this.getImageMatrix());
            hv.invalidate();
        }
    }

    protected final void zoomIn() {

        for (Iterator<HighlightView> iterator = this.mHighlightViews.iterator(); iterator.hasNext(); ) {
            HighlightView hv = iterator.next();
            hv.mMatrix.set(this.getImageMatrix());
            hv.invalidate();
        }
    }

    protected final void zoomOut() {

        for (Iterator<HighlightView> iterator = this.mHighlightViews.iterator(); iterator.hasNext(); ) {
            HighlightView hv = iterator.next();
            hv.mMatrix.set(this.getImageMatrix());
            hv.invalidate();
        }
    }

    @Override
    protected final void postTranslate(float dx, float dy) {

        super.postTranslate(dx, dy);
        for (int i = 0; i < this.mHighlightViews.size(); i++) {
            HighlightView hv = this.mHighlightViews.get(i);
            hv.mMatrix.postTranslate(dx, dy);
            hv.invalidate();
        }
    }

    // According to the event's position, change the focus to the first
    // hitting cropping rectangle.
    private void recomputeFocus(MotionEvent event) {

        for (int i = 0; i < this.mHighlightViews.size(); i++) {
            HighlightView hv = this.mHighlightViews.get(i);
            hv.setFocus(false);
            hv.invalidate();
        }

        for (int i = 0; i < this.mHighlightViews.size(); i++) {
            HighlightView hv = this.mHighlightViews.get(i);
            int edge = hv.getHit(event.getX(), event.getY());
            if (edge != HighlightView.GROW_NONE) {
                if (!hv.hasFocus()) {
                    hv.setFocus(true);
                    hv.invalidate();
                }
                break;
            }
        }
        this.invalidate();
    }

    @Override
    public final boolean onTouchEvent(MotionEvent event) {

        CropImage cropImage = (CropImage) this.mContext;
        if (cropImage.mSaving) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (cropImage.mWaitingToPick) {
                    this.recomputeFocus(event);
                } else {
                    for (int i = 0; i < this.mHighlightViews.size(); i++) {
                        HighlightView hv = this.mHighlightViews.get(i);
                        int edge = hv.getHit(event.getX(), event.getY());
                        if (edge != HighlightView.GROW_NONE) {
                            this.mMotionEdge = edge;
                            this.mMotionHighlightView = hv;
                            this.mLastX = event.getX();
                            this.mLastY = event.getY();
                            this.mMotionHighlightView.setMode(
                                    (edge == HighlightView.MOVE)
                                            ? ModifyMode.Move
                                            : ModifyMode.Grow);
                            break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (cropImage.mWaitingToPick) {
                    for (int i = 0; i < this.mHighlightViews.size(); i++) {
                        HighlightView hv = this.mHighlightViews.get(i);
                        if (hv.hasFocus()) {
                            cropImage.mCrop = hv;
                            for (int j = 0; j < this.mHighlightViews.size(); j++) {
                                if (j == i) {
                                    continue;
                                }
                                this.mHighlightViews.get(j).setHidden(true);
                            }
                            this.centerBasedOnHighlightView(hv);
                            ((CropImage) this.mContext).mWaitingToPick = false;
                            return true;
                        }
                    }
                } else if (this.mMotionHighlightView != null) {
                    this.centerBasedOnHighlightView(this.mMotionHighlightView);
                    this.mMotionHighlightView.setMode(
                            ModifyMode.None);
                }
                this.mMotionHighlightView = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (cropImage.mWaitingToPick) {
                    this.recomputeFocus(event);
                } else if (this.mMotionHighlightView != null) {
                    this.mMotionHighlightView.handleMotion(this.mMotionEdge,
                            event.getX() - this.mLastX,
                            event.getY() - this.mLastY);
                    this.mLastX = event.getX();
                    this.mLastY = event.getY();

                    // This section of code is optional. It has some user
                    // benefit in that moving the crop rectangle against
                    // the edge of the screen causes scrolling but it means
                    // that the crop rectangle is no longer fixed under
                    // the user's finger.
                    this.ensureVisible(this.mMotionHighlightView);
                }
                break;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                this.center();
                break;
            case MotionEvent.ACTION_MOVE:
                // if we're not zoomed then there's no point in even allowing
                // the user to move the image around.  This call to center puts
                // it back to the normalized location (with false meaning don't
                // animate).
                if (this.getScale() == 1.0F) {
                    this.center();
                }
                break;
        }

        return true;
    }

    // Pan the displayed image to make sure the cropping rectangle is visible.
    private void ensureVisible(HighlightView hv) {

        Rect r = hv.mDrawRect;

        int panDeltaX1 = Math.max(0, this.mLeft - r.left);
        int panDeltaX2 = Math.min(0, this.mRight - r.right);

        int panDeltaY1 = Math.max(0, this.mTop - r.top);
        int panDeltaY2 = Math.min(0, this.mBottom - r.bottom);

        int panDeltaX = panDeltaX1 == 0 ? panDeltaX2 : panDeltaX1;
        int panDeltaY = panDeltaY1 == 0 ? panDeltaY2 : panDeltaY1;

        if (panDeltaX != 0 || panDeltaY != 0) {
            this.panBy((float) panDeltaX, (float) panDeltaY);
        }
    }

    // If the cropping rectangle's size changed significantly, change the
    // view's center and scale according to the cropping rectangle.
    private void centerBasedOnHighlightView(HighlightView hv) {

        Rect drawRect = hv.mDrawRect;

        float width = (float) drawRect.width();
        float height = (float) drawRect.height();

        float thisWidth = (float) this.getWidth();
        float thisHeight = (float) this.getHeight();

        float z1 = thisWidth / width * 0.6F;
        float z2 = thisHeight / height * 0.6F;

        float zoom = Math.min(z1, z2);
        zoom *= getScale();
        zoom = Math.max(1.0F, zoom);
        if ((double) (Math.abs(zoom - this.getScale()) / zoom) > 0.1) {
            float[] coordinates = {hv.mCropRect.centerX(),
                    hv.mCropRect.centerY()};
            this.getImageMatrix().mapPoints(coordinates);
            this.zoomTo(zoom, coordinates[0], coordinates[1]);
        }

        this.ensureVisible(hv);
    }

    @Override
    protected final void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        for (int i = 0; i < this.mHighlightViews.size(); i++) {
            this.mHighlightViews.get(i).draw(canvas);
        }
    }

    public final void add(HighlightView hv) {

        this.mHighlightViews.add(hv);
        this.invalidate();
    }
}