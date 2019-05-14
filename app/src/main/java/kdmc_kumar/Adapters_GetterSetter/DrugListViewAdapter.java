package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.DrugItem;


class DrugListViewAdapter extends ArrayAdapter<DrugItem> {

    private final Context context;

    public DrugListViewAdapter(Context context, int resourceId, List<DrugItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    @NonNull
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View convertView1 = convertView;
        DrugListViewAdapter.ViewHolder holder = null;
        DrugItem rowItem = this.getItem(position);

        LayoutInflater mInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView1 == null) {
            convertView1 = mInflater.inflate(layout.drug_listview, null);
            holder = new DrugListViewAdapter.ViewHolder();
            holder.systemView = convertView1.findViewById(id.system);
            holder.CompanyName = convertView1.findViewById(id.company_name);
            holder.BrandName = convertView1.findViewById(id.brand_name);
            holder.Dosage = convertView1.findViewById(id.dosage);
            //holder.mv = (MedallionImageView) convertView.findViewById(R.id.medallionImageView1);
            convertView1.setTag(holder);
        } else

            holder = (DrugListViewAdapter.ViewHolder) convertView1.getTag();

        holder.BrandName.setText(rowItem.getBrandName());
        holder.CompanyName.setText(rowItem.getPharmaCompany());
        holder.Dosage.setText(rowItem.getDosage());
        holder.systemView.setText(rowItem.getSystem());

        return convertView1;
    }

    /* private view holder class */
    private static class ViewHolder {
        ImageView imageView;

        TextView systemView;
        TextView CompanyName;
        TextView BrandName;
        TextView Dosage;

        private ViewHolder() {
        }
    }

}

