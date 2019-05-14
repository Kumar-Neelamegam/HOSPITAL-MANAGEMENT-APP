package kdmc_kumar.Utilities_Others;


import android.app.Activity;
import android.content.Context;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;


public class CustomIntent {
    public static String[] types = {
            "",
            "",

    };

    public CustomIntent() {
    }

    public static void customType(Context context, int animtype){
        Activity act = (Activity)context;
        switch (animtype){
            case 1://left-to-right
                act.overridePendingTransition(anim.push_left_in, anim.push_left_out);
                break;
            case 2://"right-to-left":
                act.overridePendingTransition(anim.left_to_right, anim.right_to_left);
                break;
            case 3://"bottom-to-up":
                act.overridePendingTransition(anim.bottom_to_up, anim.up_to_bottom);
                break;
            case 4://"up-to-bottom":
                act.overridePendingTransition(anim.up_to_bottom2, anim.bottom_to_up2);
                break;
            case 5://"fadein-to-fadeout":
                act.overridePendingTransition(anim.fade_in, anim.fade_out);
                break;
            case 6://"rotateout-to-rotatein":
                act.overridePendingTransition(anim.rotatein_out, anim.rotateout_in);
                break;
            default:
                break;

        }
    }
}