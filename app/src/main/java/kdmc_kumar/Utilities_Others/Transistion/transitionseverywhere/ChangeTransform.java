/*
 * Copyright (C) 2014 The Android Open Source Project
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
package kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.styleable;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Transition.TransitionListenerAdapter;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.MatrixUtils;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.MatrixUtils.MatrixEvaluator;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.utils.ViewUtils;

/**
 * This Transition captures scale and rotation for Views before and after the
 * scene change and animates those changes during the transition.
 *
 * A change in parent is handled as well by capturing the transforms from
 * the parent before and after the scene change and animating those during the
 * transition.
 */
@TargetApi(VERSION_CODES.LOLLIPOP)
public class ChangeTransform extends Transition {

    private static final String TAG = "ChangeTransform";

    private static final String PROPNAME_MATRIX = "android:changeTransform:matrix";
    private static final String PROPNAME_TRANSFORMS = "android:changeTransform:transforms";
    private static final String PROPNAME_PARENT = "android:changeTransform:parent";
    private static final String PROPNAME_PARENT_MATRIX = "android:changeTransform:parentMatrix";
    private static final String PROPNAME_INTERMEDIATE_PARENT_MATRIX =
            "android:changeTransform:intermediateParentMatrix";
    private static final String PROPNAME_INTERMEDIATE_MATRIX =
            "android:changeTransform:intermediateMatrix";

    private static final String[] sTransitionProperties = {
            ChangeTransform.PROPNAME_MATRIX,
            ChangeTransform.PROPNAME_TRANSFORMS,
            ChangeTransform.PROPNAME_PARENT_MATRIX,
    };

    private static final Property<View, Matrix> ANIMATION_MATRIX_PROPERTY;

    static {
        if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
            ANIMATION_MATRIX_PROPERTY = new Property<View, Matrix>(Matrix.class, "animationMatrix") {
                @Override
                public Matrix get(View object) {
                    return null;
                }

                @Override
                public void set(View object, Matrix value) {
                    ViewUtils.setAnimationMatrix(object, value);
                }
            };
        } else {
            ANIMATION_MATRIX_PROPERTY = null;
        }
    }

    private boolean mUseOverlay = true;
    private boolean mReparent = true;
    private final Matrix mTempMatrix = new Matrix();

    public ChangeTransform() {}

    public ChangeTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, styleable.ChangeTransform);
        this.mUseOverlay = a.getBoolean(styleable.ChangeTransform_reparentWithOverlay, true);
        this.mReparent = a.getBoolean(styleable.ChangeTransform_reparent, true);
        a.recycle();
    }

    /**
     * Returns whether changes to parent should use an overlay or not. When the parent
     * change doesn't use an overlay, it affects the transforms of the child. The
     * default value is <code>true</code>.
     *
     * <p>Note: when Overlays are not used when a parent changes, a view can be clipped when
     * it moves outside the bounds of its parent. Setting
     * {@link ViewGroup#setClipChildren(boolean)} and
     * {@link ViewGroup#setClipToPadding(boolean)} can help. Also, when
     * Overlays are not used and the parent is animating its location, the position of the
     * child view will be relative to its parent's final position, so it may appear to "jump"
     * at the beginning.</p>
     *
     * @return <code>true</code> when a changed parent should execute the transition
     * inside the scene root's overlay or <code>false</code> if a parent change only
     * affects the transform of the transitioning view.
     * @attr ref android.R.styleable#ChangeTransform_reparentWithOverlay
     */
    public boolean getReparentWithOverlay() {
        return this.mUseOverlay;
    }

    /**
     * Sets whether changes to parent should use an overlay or not. When the parent
     * change doesn't use an overlay, it affects the transforms of the child. The
     * default value is <code>true</code>.
     *
     * <p>Note: when Overlays are not used when a parent changes, a view can be clipped when
     * it moves outside the bounds of its parent. Setting
     * {@link ViewGroup#setClipChildren(boolean)} and
     * {@link ViewGroup#setClipToPadding(boolean)} can help. Also, when
     * Overlays are not used and the parent is animating its location, the position of the
     * child view will be relative to its parent's final position, so it may appear to "jump"
     * at the beginning.</p>
     *
     * @return <code>true</code> when a changed parent should execute the transition
     * inside the scene root's overlay or <code>false</code> if a parent change only
     * affects the transform of the transitioning view.
     * @attr ref android.R.styleable#ChangeTransform_reparentWithOverlay
     */
    public void setReparentWithOverlay(boolean reparentWithOverlay) {
        this.mUseOverlay = reparentWithOverlay;
    }

    /**
     * Returns whether parent changes will be tracked by the ChangeTransform. If parent
     * changes are tracked, then the transform will adjust to the transforms of the
     * different parents. If they aren't tracked, only the transforms of the transitioning
     * view will be tracked. Default is true.
     *
     * @return whether parent changes will be tracked by the ChangeTransform.
     * @attr ref android.R.styleable#ChangeTransform_reparent
     */
    public boolean getReparent() {
        return this.mReparent;
    }

    /**
     * Sets whether parent changes will be tracked by the ChangeTransform. If parent
     * changes are tracked, then the transform will adjust to the transforms of the
     * different parents. If they aren't tracked, only the transforms of the transitioning
     * view will be tracked. Default is true.
     *
     * @param reparent Set to true to track parent changes or false to only track changes
     *                 of the transitioning view without considering the parent change.
     * @attr ref android.R.styleable#ChangeTransform_reparent
     */
    public void setReparent(boolean reparent) {
        this.mReparent = reparent;
    }

    @Override
    public String[] getTransitionProperties() {
        return ChangeTransform.sTransitionProperties;
    }

    private void captureValues(TransitionValues transitionValues) {
        if (VERSION.SDK_INT < VERSION_CODES.LOLLIPOP) {
            return;
        }
        View view = transitionValues.view;
        if (view.getVisibility() == View.GONE) {
            return;
        }
        transitionValues.values.put(ChangeTransform.PROPNAME_PARENT, view.getParent());
        ChangeTransform.Transforms transforms = new ChangeTransform.Transforms(view);
        transitionValues.values.put(ChangeTransform.PROPNAME_TRANSFORMS, transforms);
        Matrix matrix = view.getMatrix();
        if (matrix == null || matrix.isIdentity()) {
            matrix = null;
        } else {
            matrix = new Matrix(matrix);
        }
        transitionValues.values.put(ChangeTransform.PROPNAME_MATRIX, matrix);
        if (this.mReparent) {
            Matrix parentMatrix = new Matrix();
            ViewGroup parent = (ViewGroup) view.getParent();
            ViewUtils.transformMatrixToGlobal(parent, parentMatrix);
            parentMatrix.preTranslate(-parent.getScrollX(), -parent.getScrollY());
            transitionValues.values.put(ChangeTransform.PROPNAME_PARENT_MATRIX, parentMatrix);
            transitionValues.values.put(ChangeTransform.PROPNAME_INTERMEDIATE_MATRIX,
                    view.getTag(id.transitionTransform));
            transitionValues.values.put(ChangeTransform.PROPNAME_INTERMEDIATE_PARENT_MATRIX,
                    view.getTag(id.parentMatrix));
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                   TransitionValues endValues) {
        if (startValues == null || endValues == null ||
                VERSION.SDK_INT < VERSION_CODES.LOLLIPOP ||
                !startValues.values.containsKey(ChangeTransform.PROPNAME_PARENT) ||
                !endValues.values.containsKey(ChangeTransform.PROPNAME_PARENT)) {
            return null;
        }

        ViewGroup startParent = (ViewGroup) startValues.values.get(ChangeTransform.PROPNAME_PARENT);
        ViewGroup endParent = (ViewGroup) endValues.values.get(ChangeTransform.PROPNAME_PARENT);
        boolean handleParentChange = this.mReparent && !this.parentsMatch(startParent, endParent);

        Matrix startMatrix = (Matrix) startValues.values.get(ChangeTransform.PROPNAME_INTERMEDIATE_MATRIX);
        if (startMatrix != null) {
            startValues.values.put(ChangeTransform.PROPNAME_MATRIX, startMatrix);
        }

        Matrix startParentMatrix = (Matrix)
                startValues.values.get(ChangeTransform.PROPNAME_INTERMEDIATE_PARENT_MATRIX);
        if (startParentMatrix != null) {
            startValues.values.put(ChangeTransform.PROPNAME_PARENT_MATRIX, startParentMatrix);
        }

        // First handle the parent change:
        if (handleParentChange) {
            this.setMatricesForParent(startValues, endValues);
        }

        // Next handle the normal matrix transform:
        ObjectAnimator transformAnimator = this.createTransformAnimator(startValues, endValues,
                handleParentChange);

        if (handleParentChange && transformAnimator != null && this.mUseOverlay) {
            this.createGhostView(sceneRoot, startValues, endValues);
        }

        return transformAnimator;
    }

    private ObjectAnimator createTransformAnimator(TransitionValues startValues,
                                                   TransitionValues endValues, boolean handleParentChange) {
        Matrix startMatrix = (Matrix) startValues.values.get(ChangeTransform.PROPNAME_MATRIX);
        Matrix endMatrix = (Matrix) endValues.values.get(ChangeTransform.PROPNAME_MATRIX);

        if (startMatrix == null) {
            startMatrix = MatrixUtils.IDENTITY_MATRIX;
        }

        if (endMatrix == null) {
            endMatrix = MatrixUtils.IDENTITY_MATRIX;
        }

        if (startMatrix.equals(endMatrix)) {
            return null;
        }

        ChangeTransform.Transforms transforms = (ChangeTransform.Transforms) endValues.values.get(ChangeTransform.PROPNAME_TRANSFORMS);

        // clear the transform properties so that we can use the animation matrix instead
        View view = endValues.view;
        ChangeTransform.setIdentityTransforms(view);

        ObjectAnimator animator = ObjectAnimator.ofObject(view, ChangeTransform.ANIMATION_MATRIX_PROPERTY,
                new MatrixEvaluator(), startMatrix, endMatrix);

        Matrix finalEndMatrix = endMatrix;

        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            private boolean mIsCanceled;
            private final Matrix mTempMatrix = new Matrix();

            @Override
            public void onAnimationCancel(Animator animation) {
                this.mIsCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!this.mIsCanceled) {
                    if (handleParentChange && ChangeTransform.this.mUseOverlay) {
                        this.setCurrentMatrix(finalEndMatrix);
                    } else {
                        view.setTag(id.transitionTransform, null);
                        view.setTag(id.parentMatrix, null);
                    }
                }
                ChangeTransform.ANIMATION_MATRIX_PROPERTY.set(view, null);
                transforms.restore(view);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                ValueAnimator animator = (ValueAnimator) animation;
                Matrix currentMatrix = (Matrix) animator.getAnimatedValue();
                this.setCurrentMatrix(currentMatrix);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                ChangeTransform.setIdentityTransforms(view);
            }

            private void setCurrentMatrix(Matrix currentMatrix) {
                this.mTempMatrix.set(currentMatrix);
                view.setTag(id.transitionTransform, this.mTempMatrix);
                transforms.restore(view);
            }
        };

        animator.addListener(listener);
        animator.addPauseListener(listener);
        return animator;
    }

    private boolean parentsMatch(ViewGroup startParent, ViewGroup endParent) {
        boolean parentsMatch = false;
        if (!this.isValidTarget(startParent) || !this.isValidTarget(endParent)) {
            parentsMatch = startParent == endParent;
        } else {
            TransitionValues endValues = this.getMatchedTransitionValues(startParent, true);
            if (endValues != null) {
                parentsMatch = endParent == endValues.view;
            }
        }
        return parentsMatch;
    }

    private void createGhostView(ViewGroup sceneRoot, TransitionValues startValues,
                                 TransitionValues endValues) {
        View view = endValues.view;

        Matrix endMatrix = (Matrix) endValues.values.get(ChangeTransform.PROPNAME_PARENT_MATRIX);
        Matrix localEndMatrix = new Matrix(endMatrix);
        ViewUtils.transformMatrixToLocal(sceneRoot, localEndMatrix);

        Transition outerTransition = this;
        while (outerTransition.mParent != null) {
            outerTransition = outerTransition.mParent;
        }

        View ghostView = ViewUtils.addGhostView(view, sceneRoot, localEndMatrix);
        if (ghostView != null) {
            ChangeTransform.GhostListener listener = new ChangeTransform.GhostListener(view, ghostView, endMatrix);
            outerTransition.addListener(listener);
        }

        if (startValues.view != endValues.view) {
            view.setAlpha(0);
        }
        view.setAlpha(1);
    }

    private void setMatricesForParent(TransitionValues startValues, TransitionValues endValues) {
        Matrix endParentMatrix = (Matrix) endValues.values.get(ChangeTransform.PROPNAME_PARENT_MATRIX);
        endValues.view.setTag(id.parentMatrix, endParentMatrix);

        Matrix toLocal = this.mTempMatrix;
        toLocal.reset();
        endParentMatrix.invert(toLocal);

        Matrix startLocal = (Matrix) startValues.values.get(ChangeTransform.PROPNAME_MATRIX);
        if (startLocal == null) {
            startLocal = new Matrix();
            startValues.values.put(ChangeTransform.PROPNAME_MATRIX, startLocal);
        }

        Matrix startParentMatrix = (Matrix) startValues.values.get(ChangeTransform.PROPNAME_PARENT_MATRIX);
        startLocal.postConcat(startParentMatrix);
        startLocal.postConcat(toLocal);
    }

    private static void setIdentityTransforms(View view) {
        ChangeTransform.setTransforms(view, 0, 0, 0, 1, 1, 0, 0, 0);
    }

    private static void setTransforms(View view, float translationX, float translationY,
                                      float translationZ, float scaleX, float scaleY, float rotationX,
                                      float rotationY, float rotationZ) {
        view.setTranslationX(translationX);
        view.setTranslationY(translationY);
        ViewUtils.setTranslationZ(view, translationZ);
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
        view.setRotationX(rotationX);
        view.setRotationY(rotationY);
        view.setRotation(rotationZ);
    }

    private static class Transforms {
        public final float translationX;
        public final float translationY;
        public final float translationZ;
        public final float scaleX;
        public final float scaleY;
        public final float rotationX;
        public final float rotationY;
        public final float rotationZ;

        public Transforms(View view) {
            this.translationX = view.getTranslationX();
            this.translationY = view.getTranslationY();
            this.translationZ = ViewUtils.getTranslationZ(view);
            this.scaleX = view.getScaleX();
            this.scaleY = view.getScaleY();
            this.rotationX = view.getRotationX();
            this.rotationY = view.getRotationY();
            this.rotationZ = view.getRotation();
        }

        public void restore(View view) {
            ChangeTransform.setTransforms(view, this.translationX, this.translationY, this.translationZ, this.scaleX, this.scaleY,
                    this.rotationX, this.rotationY, this.rotationZ);
        }

        @Override
        public boolean equals(Object that) {
            if (!(that instanceof ChangeTransform.Transforms)) {
                return false;
            }
            ChangeTransform.Transforms thatTransform = (ChangeTransform.Transforms) that;
            return thatTransform.translationX == this.translationX &&
                    thatTransform.translationY == this.translationY &&
                    thatTransform.translationZ == this.translationZ &&
                    thatTransform.scaleX == this.scaleX &&
                    thatTransform.scaleY == this.scaleY &&
                    thatTransform.rotationX == this.rotationX &&
                    thatTransform.rotationY == this.rotationY &&
                    thatTransform.rotationZ == this.rotationZ;
        }
    }

    private static class GhostListener extends TransitionListenerAdapter {
        private final View mView;
        private final View mGhostView;
        private final Matrix mEndMatrix;

        public GhostListener(View view, View ghostView, Matrix endMatrix) {
            this.mView = view;
            this.mGhostView = ghostView;
            this.mEndMatrix = endMatrix;
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
            ViewUtils.removeGhostView(this.mView);
            this.mView.setTag(id.transitionTransform, null);
            this.mView.setTag(id.parentMatrix, null);
        }

        @Override
        public void onTransitionPause(Transition transition) {
            this.mGhostView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTransitionResume(Transition transition) {
            this.mGhostView.setVisibility(View.VISIBLE);
        }
    }
}
