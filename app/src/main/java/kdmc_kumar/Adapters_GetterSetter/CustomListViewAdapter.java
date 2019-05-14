package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.RowItem;


public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    private final Context context;

    public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    @NonNull
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View convertView1 = convertView;
        CustomListViewAdapter.ViewHolder holder = null;
        RowItem rowItem = this.getItem(position);

        LayoutInflater mInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView1 == null) {
            convertView1 = mInflater.inflate(layout.list_item, null);
            holder = new CustomListViewAdapter.ViewHolder();
            holder.txtDesc = convertView1.findViewById(id.desc);
            holder.txtTitle = convertView1.findViewById(id.title);
            holder.imageView = convertView1.findViewById(id.icon);
            holder.rootLayout = convertView1.findViewById(id.list_root);
            //holder.mv = (MedallionImageView) convertView.findViewById(R.id.medallionImageView1);
            convertView1.setTag(holder);

        } else

            holder = (CustomListViewAdapter.ViewHolder) convertView1.getTag();
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        // holder.imageView.setImageResource(rowItem.getImageId());
        if (rowItem.getImageId() != null) {
            holder.imageView.setImageBitmap(rowItem.getImageId());
        }
        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        return convertView1;
    }

    /* private view holder class */
    private static class ViewHolder {
        ImageView imageView;
        LinearLayout rootLayout;

        TextView txtTitle;
        TextView txtDesc;

        private ViewHolder() {
        }
    }

}
