package kdmc_kumar.Utilities_Others;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;

/**
 * Created by Ponnusamy on 15-03-2018.
 */

public class CustomKDMCDialog extends Dialog implements
        OnClickListener {

    private CustomKDMCDialog.possitiveOnClick possitiveOnClick;
    private CustomKDMCDialog.negativeOnClick negativeOnClick;
    private LinearLayout layout;
    private final Context c;
    private final Dialog d;
    private Button yes;
    private Button no;
    private TextView title;
    private TextView description;
    private AppCompatImageView icon;

    public CustomKDMCDialog(Context a) {
        super(a);
        // TODO Auto-generated constructor stub
        c = a;
        d = this;
        this.show();
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(layout.popup_common_dialog);
        this.yes = this.findViewById(id.bt_close_yes);
        this.no = this.findViewById(id.bt_close_no);
        this.icon = this.findViewById(id.icon);
        this.title = this.findViewById(id.title);
        this.description = this.findViewById(id.content);
        this.layout = this.findViewById(id.panelcolor);
        this.yes.setOnClickListener(this);
        this.no.setOnClickListener(this);
        this.d.setCancelable(false);

    }


    public final CustomKDMCDialog setLayoutColor(int color) {
        layout.setBackgroundColor(this.c.getResources().getColor(color));
        return this;
    }

    public final CustomKDMCDialog setImage(int image) {
        icon.setImageResource(image);



  //     Drawable d = this.icon.getDrawable();
//       ((AnimatedVectorDrawable)d).start();

        return this;
    }

    public final CustomKDMCDialog setTitle(String title) {
        this.title.setText(title);
        return this;
    }

    public final CustomKDMCDialog setTitleColor(int title) {
        this.title.setTextColor(title);
        return this;
    }

    public final CustomKDMCDialog setTitleBackgroundColor(int color) {
        title.setBackgroundColor(color);
        return this;
    }

    public final CustomKDMCDialog setDescription(String title) {
        description.setText(title);
        return this;
    }

    public final CustomKDMCDialog setDescriptionColor(int color) {
        description.setTextColor(color);
        return this;
    }

    public final CustomKDMCDialog setDescriptionBackgroundColor(int color) {
        description.setBackgroundColor(color);
        return this;
    }

    public final CustomKDMCDialog setPossitiveButtonTitle(String color) {
        yes.setText(color);
        return this;
    }

    public final CustomKDMCDialog setNegativeButtonTitle(String color) {
        no.setText(color);
        return this;
    }


    public final CustomKDMCDialog setPassitiveButtonBackground(int color) {
        yes.setBackgroundColor(color);
        return this;
    }

    public final CustomKDMCDialog setNegativeButtonBackground(int color) {
        no.setBackgroundColor(color);
        return this;
    }


    public final CustomKDMCDialog setNegativeButtonVisible(int visible) {
        no.setVisibility(visible);
        return this;
    }


    public final CustomKDMCDialog setOnPossitiveListener(CustomKDMCDialog.possitiveOnClick possitiveListener) {
        possitiveOnClick = possitiveListener;
        return this;
    }

    public final CustomKDMCDialog setOnNegativeListener(CustomKDMCDialog.negativeOnClick negativeListener) {
        negativeOnClick = negativeListener;
        return this;
    }



    @Override
    public final void onClick(View view) {

        switch (view.getId()) {
            case id.bt_close_yes:
                //c.finish();
                try {
                    this.possitiveOnClick.onPossitivePerformed();
                    this.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case id.bt_close_no:
                this.dismiss();
                //negativeOnClick.onNegativePerformed(d);
                break;
            default:
                break;
        }

        this.dismiss();
    }

    public interface possitiveOnClick {
        void onPossitivePerformed();
    }

    interface negativeOnClick {
        void onNegativePerformed();
    }

}