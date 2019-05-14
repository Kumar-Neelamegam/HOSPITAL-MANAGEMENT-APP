package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
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
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.DiagnosisItem;
import kdmc_kumar.Adapters_GetterSetter.DiagnosisSummaryAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;


/**
 * Created by Ponnusamy M on 4/6/2017.
 */

public class DiagnosisSummaryAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<DiagnosisItem> diagnosisItems;

    public DiagnosisSummaryAdapter(ArrayList<DiagnosisItem> diagnosisItems) {
        this.diagnosisItems = diagnosisItems;
    }

    @NonNull
    @Override
    public final DiagnosisSummaryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.new_diagnosis_listrow, parent, false);

        return new DiagnosisSummaryAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull DiagnosisSummaryAdapter.MyViewHolder holder, int position) {

        DiagnosisItem item = this.diagnosisItems.get(position);


        holder.sno.setText(item.getS_No());
        holder.doctor.setText(item.getDoctor());
        holder.operaitonotes.setText(item.getOPerationNotes());
        holder.position.setText(item.getPosition());
        holder.operationprocedure.setText(item.getOperationProcedure());
        holder.postoperativediagnosis.setText(item.getPostOPerativeDiagnosis());
        holder.postoperativeinstruction.setText(item.getPostOpertiveinstruction());
        holder.closure.setText(item.getClosure());
        holder.preoperativediagnosis.setText(item.getPreOperativeDiagnosis());

        BaseConfig.Glide_LoadImageView(holder.remarks, item.getRemark_Image());

        holder.remarks.setOnClickListener(v -> {

            View view = LayoutInflater.from(holder.remarks.getContext()).inflate(layout.pop_view_image, null, false);

            ImageView imageView = view.findViewById(id.imageview);
            TextView doctorname = view.findViewById(id.doctorname);

            doctorname.setText(String.format("%s %s", view.getContext().getString(string.report), item.getDoctor()));

            BaseConfig.Glide_LoadImageView(imageView, item.getRemark_Image());


            Builder builder = new Builder(imageView.getContext());

            builder.setView(view);
            builder.setCancelable(false).setPositiveButton(view.getContext().getString(string.ok), (dialog, which) -> {

            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        });
    }

    @Override
    public final int getItemCount() {
        return this.diagnosisItems.size();
    }

    static class MyViewHolder extends ViewHolder {
        final TextView sno;
        final TextView doctor;
        final TextView operaitonotes;
        final TextView position;
        final TextView operationprocedure;
        final TextView postoperativediagnosis;
        final TextView postoperativeinstruction;
        final TextView closure;
        final TextView preoperativediagnosis;
        final ImageView remarks;


        MyViewHolder(View itemView) {
            super(itemView);
            this.sno = itemView.findViewById(id.text_1);
            this.doctor = itemView.findViewById(id.text_2);
            this.operaitonotes = itemView.findViewById(id.text_3);
            this.position = itemView.findViewById(id.text_4);
            this.operationprocedure = itemView.findViewById(id.text_5);
            this.postoperativediagnosis = itemView.findViewById(id.text_6);
            this.postoperativeinstruction = itemView.findViewById(id.text_7);
            this.closure = itemView.findViewById(id.text_8);
            this.preoperativediagnosis = itemView.findViewById(id.text_9);

            this.remarks = itemView.findViewById(id.text_10);

        }
    }

}
