package kdmc_kumar.Utilities_Others;

import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.SparseArray;

/**
 * Represents collection of typefaces. This includes 4 variants (styles):
 * <ul>
 * <li>{@link android.graphics.Typeface#NORMAL}</li>
 * <li>{@link android.graphics.Typeface#BOLD}</li>
 * <li>{@link android.graphics.Typeface#ITALIC}</li>
 * <li>{@link android.graphics.Typeface#BOLD_ITALIC}</li>
 * </ul>
 *
 * @author Jakub Skierbiszewski
 */
public class TypefaceCollection {

    private final SparseArray<Typeface> mTypefaces = new SparseArray<>(4);

    private TypefaceCollection() {
    }

    /**
     * Creates default typeface collection, containing system fonts:
     * <ul>
     * <li>{@link android.graphics.Typeface#DEFAULT}</li>
     * <li>{@link android.graphics.Typeface#DEFAULT_BOLD}</li>
     * </ul>
     *
     * @return typeface collection
     */
    public static TypefaceCollection createSystemDefault() {
        return new TypefaceCollection.Builder().set(Typeface.NORMAL, Typeface.DEFAULT).set(Typeface.BOLD, Typeface.DEFAULT_BOLD)
                .set(Typeface.ITALIC, Typeface.DEFAULT)
                .set(Typeface.BOLD_ITALIC, Typeface.DEFAULT_BOLD)
                .create();
    }

    private static void validateTypefaceStyle(int typefaceStyle) {
        switch (typefaceStyle) {
            case Typeface.NORMAL:
            case Typeface.BOLD:
            case Typeface.ITALIC:
            case Typeface.BOLD_ITALIC:
                break;
            default:
                throw new IllegalArgumentException("Invalid typeface style! Have to be one of " +
                        "Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC or Typeface.BOLD_ITALIC");
        }
    }

    /**
     * @param typefaceStyle <ul>
     *                      <li>{@link android.graphics.Typeface#NORMAL}</li>
     *                      <li>{@link android.graphics.Typeface#BOLD}</li>
     *                      <li>{@link android.graphics.Typeface#ITALIC}</li>
     *                      <li>{@link android.graphics.Typeface#BOLD_ITALIC}</li>
     *                      </ul>
     * @return selected Typeface
     * @throws IllegalArgumentException when invalid typefaceStyle is passed
     */
    final Typeface getTypeface(int typefaceStyle) {
        TypefaceCollection.validateTypefaceStyle(typefaceStyle);
        return this.getmTypefaces().get(typefaceStyle);
    }

    public SparseArray<Typeface> getmTypefaces() {
        return this.mTypefaces;
    }

    /**
     * Builder interface
     */
    public static class Builder {

        /**
         * This typeface is set in fist {@link #set(int, android.graphics.Typeface)}
         * call and is used for all unset styles in {@link #create()} call
         */
        private Typeface mDefaultTypeface;
        @Nullable
        private TypefaceCollection mCollection = new TypefaceCollection();

        public Builder() {
        }

        /**
         * Sets typeface for certain style.
         *
         * @param typefaceStyle one of:
         *                      <ul>
         *                      <li>{@link android.graphics.Typeface#NORMAL}</li>
         *                      <li>{@link android.graphics.Typeface#BOLD}</li>
         *                      <li>{@link android.graphics.Typeface#ITALIC}</li>
         *                      <li>{@link android.graphics.Typeface#BOLD_ITALIC}</li>
         *                      </ul>
         * @return self
         * @throws IllegalArgumentException when invalid typefaceStyle is passed
         */
        final TypefaceCollection.Builder set(int typefaceStyle, Typeface typeface) {
            TypefaceCollection.validateTypefaceStyle(typefaceStyle);
            if (this.mDefaultTypeface == null) {
                this.mDefaultTypeface = typeface;
            }
            this.mCollection.getmTypefaces().put(typefaceStyle, typeface);
            return this;
        }

        /**
         * Creates typeface collection.
         * If not all styles are set, uses fist typeface that has been set
         * for all unset styles.
         *
         * @return TypefaceCollection
         * @throws IllegalStateException when no {@link android.graphics.Typeface}
         *                               has been set via {@link #set(int, android.graphics.Typeface)}
         */
        final TypefaceCollection create() {
            if (this.mDefaultTypeface == null) {
                throw new IllegalStateException("At least one typeface style have to be set!");
            }

            if (this.mCollection.getmTypefaces().get(Typeface.NORMAL) == null) {
                this.mCollection.getmTypefaces().put(Typeface.NORMAL, this.mDefaultTypeface);
            }

            if (this.mCollection.getmTypefaces().get(Typeface.BOLD) == null) {
                this.mCollection.getmTypefaces().put(Typeface.BOLD, this.mDefaultTypeface);
            }

            if (this.mCollection.getmTypefaces().get(Typeface.ITALIC) == null) {
                this.mCollection.getmTypefaces().put(Typeface.ITALIC, this.mDefaultTypeface);
            }

            if (this.mCollection.getmTypefaces().get(Typeface.BOLD_ITALIC) == null) {
                this.mCollection.getmTypefaces().put(Typeface.BOLD_ITALIC, this.mDefaultTypeface);
            }

            TypefaceCollection collection = this.mCollection;
            this.mCollection = null;
            return collection;
        }
    }
}
