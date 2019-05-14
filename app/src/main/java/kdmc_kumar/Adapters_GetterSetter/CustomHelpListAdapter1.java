package kdmc_kumar.Adapters_GetterSetter;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;


class CustomHelpListAdapter1 extends BaseAdapter {
    private final String[] web;
    private final int[] Imageid;
    private final Context mContext;


    public CustomHelpListAdapter1(Context c, String[] web, int[] Imageid) {
        this.mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }

    @Override
    public final int getCount() {
        // TODO Auto-generated method stub
        return this.web.length;
    }

    @Nullable
    @Override
    public final Object getItem(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final long getItemId(int i) {
        // TODO Auto-generated method stub
        return 0L;
    }

    @Override
    public final View getView(int i, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub

        View grid;

        if (view == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(layout.task_items, viewGroup, false);
        } else {
            grid = view;
        }
        // grid = new View(mContext);
        //  grid = inflater.inflate(R.layout.task_items, null);
        //  FontHelper.applyFont(mContext, grid.findViewById(R.id.grid_items), BaseConfig.Layout_Font);

        TextView textView = grid.findViewById(id.tv_species);
        ImageView imageView = grid.findViewById(id.img_thumbnail);
        textView.setText(this.web[i]);
        imageView.setImageResource(this.Imageid[i]);

        //Animation
      /*  YoYo.with(Techniques.BounceIn)
                .duration(500)
                .playOn(grid.findViewById(R.id.img_thumbnail));
*/
        return grid;
    }
}