package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.ObjectDrawerItem;
import kdmc_kumar.Core_Modules.BaseConfig;

public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {

    private final Context mContext;
    private final int layoutResourceId;
    private ObjectDrawerItem[] data;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, ObjectDrawerItem[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    // /////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {

        //View listItem = convertView;
        View listItem = null;

        if (convertView == null) {
            // inflate the listview_item_row.xml parent
            LayoutInflater inflater = ((Activity) this.mContext).getLayoutInflater();
            listItem = inflater.inflate(this.layoutResourceId, parent, false);
        } else
            listItem = convertView;
        DrawerItemCustomAdapter.ViewHolder viewHolder = new DrawerItemCustomAdapter.ViewHolder();


        viewHolder.menuName = listItem.findViewById(id.menuName);
        viewHolder.menuImage = listItem.findViewById(id.menuImage);
        viewHolder.imgLayout = listItem.findViewById(id.imgLayout);
        viewHolder.buttonLayout = listItem.findViewById(id.buttonLayout);
        viewHolder.img_banner = listItem.findViewById(id.img_banner);
        viewHolder.DoctorName = listItem.findViewById(id.title_doctorname);


        viewHolder.DoctorName.setText(String.format("%s\n%s", BaseConfig.doctorname, BaseConfig.HOSPITALNAME));


        String getDoctorImage = BaseConfig.GetValues("select docimage as ret_values from  Drreg where Docid='" + BaseConfig.doctorId + '\'');

        //Toast.makeText(mContext, "doctor Image: "+ getDoctorImage, Toast.LENGTH_SHORT).show();
        if (getDoctorImage != null && getDoctorImage.length() > 0) {


            BaseConfig.Glide_LoadImageView(viewHolder.img_banner, getDoctorImage);

        } else {

            Bitmap bitmp = BitmapFactory.decodeResource(this.getContext().getResources(), drawable.image);
            viewHolder.img_banner.setImageBitmap(bitmp);

        }


        ObjectDrawerItem folder = this.data[position];


        if (position == 0) {

            viewHolder.img_banner.setVisibility(View.VISIBLE);
            viewHolder.buttonLayout.setVisibility(View.GONE);

        } else {
            viewHolder.menuImage.setImageResource(folder.icon);
            viewHolder.menuName.setText(folder.name);
            viewHolder.img_banner.setVisibility(View.GONE);
            //circular image
            viewHolder.imgLayout.setVisibility(View.GONE);
            viewHolder.buttonLayout.setVisibility(View.VISIBLE);
        }

        listItem.setTag(viewHolder);

        return listItem;
    }

    // /////////////////////////////////////////////////////////////////////////////
    static class ViewHolder {
        TextView menuName;
        ImageView menuImage;
        LinearLayout buttonLayout;
        LinearLayout imgLayout;
        ImageView img_banner;
        TextView DoctorName;

        ViewHolder() {
        }
    }
    // /////////////////////////////////////////////////////////////////////////////

}