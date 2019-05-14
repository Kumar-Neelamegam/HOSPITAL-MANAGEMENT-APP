package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.Fav_MedicineList;
import kdmc_kumar.Adapters_GetterSetter.FavMedicineRecylerAdapter.MyViewHolder;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;


/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class FavMedicineRecylerAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<Fav_MedicineList> favmedicineItems;
    //**********************************************************************************************

    public FavMedicineRecylerAdapter(ArrayList<Fav_MedicineList> drugItems) {

        favmedicineItems = drugItems;
    }
    //**********************************************************************************************

    @NonNull
    @Override
    public final FavMedicineRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout.activity_premed_adap, parent, false);

        return new FavMedicineRecylerAdapter.MyViewHolder(view);
    }
    //**********************************************************************************************

    @Override
    public final void onBindViewHolder(@NonNull FavMedicineRecylerAdapter.MyViewHolder holder, int position) {

        Fav_MedicineList rowItem = this.favmedicineItems.get(position);

        holder.Medicine.setText(rowItem.getMedicine());

        holder.Medicine.setOnClickListener(view -> {

            new CustomKDMCDialog(view.getContext())
                    .setLayoutColor(color.red_500)
                    .setImage(drawable.ic_delete_forever_black_24dp)
                    .setTitle(view.getContext().getString(string.information))
                    .setDescription("Are you sure want to delete\nthis Prefered Medicine?")
                    .setPossitiveButtonTitle("Yes, delete it")
                    .setNegativeButtonTitle("No")
                    .setOnPossitiveListener(() -> {
                        this.favmedicineItems.remove(position);
                        this.notifyDataSetChanged();

                    });

        });
    }
    //**********************************************************************************************

    @Override
    public final int getItemCount() {
        return this.favmedicineItems.size();
    }

    //**********************************************************************************************

    static class MyViewHolder extends ViewHolder {

        final TextView Medicine;
        final LinearLayout ParentView;

        MyViewHolder(View itemView) {
            super(itemView);

            this.Medicine = itemView.findViewById(id.desc);

            this.ParentView = itemView.findViewById(id.list_root);


        }
    }
    //**********************************************************************************************

}
