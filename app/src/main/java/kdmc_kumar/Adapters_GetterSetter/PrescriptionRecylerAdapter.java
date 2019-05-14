package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.PrescriptionItem;
import kdmc_kumar.Adapters_GetterSetter.PrescriptionRecylerAdapter.MyViewHolder;


/**
 * Created by Ponnusamy M on 4/7/2017.
 */

public class PrescriptionRecylerAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<PrescriptionItem> prescriptionItems;

    public PrescriptionRecylerAdapter(ArrayList<PrescriptionItem> prescriptionItems) {
        this.prescriptionItems = prescriptionItems;
    }

    @NonNull
    @Override
    public final PrescriptionRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.new_prescription_recyler_item, parent, false);

        return new PrescriptionRecylerAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull PrescriptionRecylerAdapter.MyViewHolder holder, int position) {

        PrescriptionItem item = this.prescriptionItems.get(position);

        holder.sno.setText(item.getSno());
        holder.medicinename.setText(item.getMedicine_Name());
        holder.interval.setText(item.getInterval());
        holder.frequency.setText(item.getFrequency());
        holder.Duration.setText(item.getDuration());
        holder.doctorname.setText(item.getDoctorname());
    }

    @Override
    public final int getItemCount() {
        return this.prescriptionItems.size();
    }

    static class MyViewHolder extends ViewHolder {
        final TextView sno;
        final TextView medicinename;
        final TextView interval;
        final TextView frequency;
        final TextView Duration;
        final TextView doctorname;

        MyViewHolder(View itemView) {
            super(itemView);
            this.sno = itemView.findViewById(id.text1);
            this.medicinename = itemView.findViewById(id.text2);
            this.interval = itemView.findViewById(id.text3);
            this.frequency = itemView.findViewById(id.text4);
            this.Duration = itemView.findViewById(id.text5);
            this.doctorname = itemView.findViewById(id.text6);
        }
    }
}
