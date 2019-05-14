package kdmc_kumar.Adapters_GetterSetter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.DrugItem;
import kdmc_kumar.Adapters_GetterSetter.DrugRecylerAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Drug_Directory.Cims_Display;

/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class DrugRecylerAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<DrugItem> drugItems;

    public DrugRecylerAdapter(ArrayList<DrugItem> drugItems) {
        this.drugItems = drugItems;
    }

    @NonNull
    @Override
    public final DrugRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout.newdruglistrow, parent, false);

        return new DrugRecylerAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull DrugRecylerAdapter.MyViewHolder holder, int position) {

        DrugItem rowItem = this.drugItems.get(position);

        holder.BrandName.setText(rowItem.getBrandName());
        holder.CompanyName.setText(rowItem.getPharmaCompany());
        holder.Dosage.setText(rowItem.getDosage());
        holder.systemView.setText(rowItem.getSystem());
        holder.card_view.setOnClickListener(v -> {
            BaseConfig.Druglistselindex = rowItem.getServerid();
            Intent lib = new Intent(v.getContext(), Cims_Display.class);
            v.getContext().startActivity(lib);
        });

    }

    @Override
    public final int getItemCount() {
        return this.drugItems.size();
    }

    static class MyViewHolder extends ViewHolder {
        ImageView imageView;

        final TextView systemView;
        final TextView CompanyName;
        final TextView BrandName;
        final TextView Dosage;
        final CardView card_view;

        MyViewHolder(View itemView) {
            super(itemView);

            this.systemView = itemView.findViewById(id.system);
            this.CompanyName = itemView.findViewById(id.company_name);
            this.BrandName = itemView.findViewById(id.brand_name);
            this.Dosage = itemView.findViewById(id.dosage);
            this.card_view = itemView.findViewById(id.card_view);
        }
    }
}
