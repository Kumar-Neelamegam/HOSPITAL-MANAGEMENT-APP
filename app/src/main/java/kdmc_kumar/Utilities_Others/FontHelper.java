package kdmc_kumar.Utilities_Others;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Helper class to apply custom font from assets to all text views in the specified root
 * view.
 *
 * @author Alexander Naberezhnov
 */
public class FontHelper {
    // -----------------------------------------------------------------------
    //
    // Constants
    //
    // -----------------------------------------------------------------------
    private static final String TAG = FontHelper.class.getSimpleName();

    public FontHelper() {
    }

    // -----------------------------------------------------------------------
    //
    // Properties
    //
    // -----------------------------------------------------------------------

    /**
     * Apply specified font for all text views (including nested ones) in the specified
     * root view.
     *
     * @param context  Context to fetch font asset.
     * @param root     Root view that should have specified font for all it's nested text
     *                 views.
     * @param fontPath Font path related to the assets folder (e.g. "fonts/YourFontName.ttf").
     */
    public static void applyFont(Context context, View root, String fontPath) {
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++)
                    FontHelper.applyFont(context, viewGroup.getChildAt(i), fontPath);
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));

        } catch (Exception e) {
            //Log.e(TAG, String.format("Error occured when trying to apply %s font for %s view", fontPath, root));
            e.printStackTrace();
        }
    }

    /**
     * Apply typeface to all ViewGroup childs
     *
     * @param viewGroup          to typeface typeface
     * @param typefaceCollection typeface collection
     */
    private static void applyTypeface(ViewGroup viewGroup, TypefaceCollection typefaceCollection) {

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup) {
                FontHelper.applyTypeface((ViewGroup) childView, typefaceCollection);
            } else {
                FontHelper.applyForView(childView, typefaceCollection);
            }
        }
    }

    /**
     * Apply typeface to single view
     *
     * @param view               to typeface typeface
     * @param typefaceCollection typeface collection
     */
    private static void applyForView(View view, TypefaceCollection typefaceCollection) {

        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Typeface oldTypeface = textView.getTypeface();
            int style = oldTypeface == null ? Typeface.NORMAL : oldTypeface.getStyle();
            textView.setTypeface(typefaceCollection.getTypeface(style));
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
    }


}
