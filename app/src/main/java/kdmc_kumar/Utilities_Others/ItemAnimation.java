package kdmc_kumar.Utilities_Others;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

class ItemAnimation {

    /* animation type */
    private static final int BOTTOM_UP = 1;
    private static final int FADE_IN = 2;
    private static final int LEFT_RIGHT = 3;
    private static final int RIGHT_LEFT = 4;
    public static final int NONE = 0;

    /* animation duration */
    private static final long DURATION_IN_BOTTOM_UP = 150L;
    private static final long DURATION_IN_FADE_ID = 500L;
    private static final long DURATION_IN_LEFT_RIGHT = 150L;
    private static final long DURATION_IN_RIGHT_LEFT = 150L;

    ItemAnimation() {
    }

    public static void animate(View view, int position, int type) {
        switch (type) {
            case ItemAnimation.BOTTOM_UP:
                ItemAnimation.animateBottomUp(view, position);
                break;

            case ItemAnimation.FADE_IN:
                ItemAnimation.animateFadeIn(view, position);
                break;

            case ItemAnimation.LEFT_RIGHT:
                ItemAnimation.animateLeftRight(view, position);
                break;

            case ItemAnimation.RIGHT_LEFT:
                ItemAnimation.animateRightLeft(view, position);
                break;
        }
    }

    private static void animateBottomUp(View view, int position) {
        int position1 = position;
        boolean not_first_item = position1 == -1;
        position1 += 1;
        view.setTranslationY((float) (not_first_item ? 800 : 500));
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", (float) (not_first_item ? 800 : 500), (float) 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
        animatorTranslateY.setStartDelay(not_first_item ? 0L : ((long) position1 * ItemAnimation.DURATION_IN_BOTTOM_UP));
        animatorTranslateY.setDuration((long) (not_first_item ? 3 : 1) * ItemAnimation.DURATION_IN_BOTTOM_UP);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }

    private static void animateFadeIn(View view, int position) {
        int position1 = position;
        boolean not_first_item = position1 == -1;
        position1 += 1;
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(view, "alpha", 0.0f).start();
        animatorAlpha.setStartDelay(not_first_item ? ItemAnimation.DURATION_IN_FADE_ID / 2L : ((long) position1 * ItemAnimation.DURATION_IN_FADE_ID / 3L));
        animatorAlpha.setDuration(ItemAnimation.DURATION_IN_FADE_ID);
        animatorSet.play(animatorAlpha);
        animatorSet.start();
    }

    private static void animateLeftRight(View view, int position) {
        int position1 = position;
        boolean not_first_item = position1 == -1;
        position1 += 1;
        view.setTranslationX(-400.0f);
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(view, "translationX", -400.0f, (float) 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
        ObjectAnimator.ofFloat(view, "alpha", 0.0f).start();
        animatorTranslateY.setStartDelay(not_first_item ? ItemAnimation.DURATION_IN_LEFT_RIGHT : ((long) position1 * ItemAnimation.DURATION_IN_LEFT_RIGHT));
        animatorTranslateY.setDuration((long) (not_first_item ? 2 : 1) * ItemAnimation.DURATION_IN_LEFT_RIGHT);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }

    private static void animateRightLeft(View view, int position) {
        int position1 = position;
        boolean not_first_item = position1 == -1;
        position1 += 1;
        view.setTranslationX(view.getX() + 400.0F);
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(view, "translationX", view.getX() + 400.0F, (float) 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
        ObjectAnimator.ofFloat(view, "alpha", 0.0f).start();
        animatorTranslateY.setStartDelay(not_first_item ? ItemAnimation.DURATION_IN_RIGHT_LEFT : ((long) position1 * ItemAnimation.DURATION_IN_RIGHT_LEFT));
        animatorTranslateY.setDuration((long) (not_first_item ? 2 : 1) * ItemAnimation.DURATION_IN_RIGHT_LEFT);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }

}
