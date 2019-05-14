package kdmc_kumar.Utilities_Others;

import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private final int min;
    private final int max;

    public InputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public InputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Nullable
    @Override
    public final CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        try {
            int input = Integer.parseInt(spanned + charSequence);
            if (InputFilterMinMax.isInRange(this.min, this.max, input))
                return null;
        } catch (NumberFormatException ignored) {
        }
        return "";
    }

    private static boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}