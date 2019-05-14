package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.OnlineConsultationRecylerAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.OnlineConsultation_Details;
import kdmc_kumar.Utilities_Others.CircleImageView;

/**
 * Created by Ponnusamy M on 4/2/2017.
 */

public class OnlineConsultationRecylerAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<CommonDataObjects.OnlineConsultation_DataObjects> items;
    private final Context context;

    public OnlineConsultationRecylerAdapter(ArrayList<CommonDataObjects.OnlineConsultation_DataObjects> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public final OnlineConsultationRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.newlistrowonlineconsultation, parent, false);
        return new OnlineConsultationRecylerAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull OnlineConsultationRecylerAdapter.MyViewHolder holder, int position) {

        CommonDataObjects.OnlineConsultation_DataObjects rowItem = this.items.get(position);

        holder.PatientName.setText(rowItem.getPatient_Name());
        holder.PatientId.setText(rowItem.getPatient_Id());
        holder.Age.setText(rowItem.getPatient_Age());
        holder.Gender.setText(rowItem.getPatient_Gender());
        holder.ConsultationId.setText(rowItem.getConsultationId());
        //Log.e("Photo Path: ", rowItem.getPatient_Photo());
        BaseConfig.LoadPatientImage(rowItem.getPatient_Photo(), holder.image, 50);

        holder.list_root.setOnClickListener(v -> {
                    try {


                        ((Activity) this.context).finish();
                        Intent lib = new Intent(this.context, OnlineConsultation_Details.class);
                        lib.putExtra("ServerId", rowItem.getConsultationId());
                        lib.putExtra("PatientId", rowItem.getPatient_Id());
                        lib.putExtra("MedId", rowItem.getMedId());
                        this.context.startActivity(lib);

                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
        );


    }

    @Override
    public final int getItemCount() {
        return this.items.size();
    }

    static class MyViewHolder extends ViewHolder {
        final CircleImageView image;

        final LinearLayout list_root;

        final TextView PatientName;
        final TextView PatientId;
        final TextView Age;
        final TextView Gender;
        final TextView ConsultationId;

        MyViewHolder(View itemView) {
            super(itemView);

            this.image = itemView.findViewById(id.icon);
            this.PatientName = itemView.findViewById(id.txtvw_patient_name);
            this.PatientId = itemView.findViewById(id.txtvw_patient_id);
            this.Age = itemView.findViewById(id.txtvw_age);
            this.Gender = itemView.findViewById(id.txtvw_gender);
            this.ConsultationId = itemView.findViewById(id.txtvw_consultation);
            this.list_root = itemView.findViewById(id.list_root);
        }
    }
}
