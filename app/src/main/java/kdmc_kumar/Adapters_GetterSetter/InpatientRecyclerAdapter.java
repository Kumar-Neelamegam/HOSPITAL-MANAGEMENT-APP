package kdmc_kumar.Adapters_GetterSetter;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView.SectionedAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MyPatientGetSet;
import kdmc_kumar.Adapters_GetterSetter.InpatientRecyclerAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;

/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class InpatientRecyclerAdapter extends Adapter<MyViewHolder> implements SectionedAdapter {

    private final ArrayList<MyPatientGetSet> rowItems;

    public InpatientRecyclerAdapter(ArrayList<MyPatientGetSet> rowItems) {
        this.rowItems = rowItems;
    }

    private static final void showDichargeRequestDialog(Context context, String pat_id, String patientid) {

        View view = LayoutInflater.from(context).inflate(layout.inpatient_dicharge_request, null);
        TextView patientAuto = view.findViewById(id.patient_detail_values);
        EditText remarksEdt = view.findViewById(id.request_remarks_edt);
        Button submitBtn = view.findViewById(id.submit);
        Button cancelBtn = view.findViewById(id.cancel);

        patientAuto.setText(String.format("%s-%s", pat_id, patientid));

        SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.ENGLISH);
        Date date = new Date();

        String dttm = dateformt.format(date);

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        submitBtn.setOnClickListener(v -> {


            ContentValues values = new ContentValues();
            values.put("patid", patientid.trim());
            values.put("request_remarks", remarksEdt.getText().toString());
            values.put("IsActive", "1");
            values.put("IsUpdate", "0");
            values.put("docid", BaseConfig.doctorId);
            values.put("ActDate", dttm);

            SQLiteDatabase Db = BaseConfig.GetDb();//v.getContext());
            Db.insert("inpatient_discharge_request", null, values);
            dialog.dismiss();


            new CustomKDMCDialog(v.getContext())
                    .setLayoutColor(color.green_500)
                    .setImage(drawable.ic_success_done)
                    .setTitle(v.getContext().getString(string.information))
                    .setNegativeButtonVisible(View.GONE)
                    .setDescription(context.getString(string.requeste_sent_discharge))
                    .setPossitiveButtonTitle(v.getContext().getString(string.ok));


        });
        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        //dialog.getWindow().setLayout(350, 500);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    @NonNull
    @Override
    public final InpatientRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = BaseConfig.listViewType.equals(ListViewType.ListView) ? LayoutInflater.from(parent.getContext()).inflate(layout.grid_item_inpatient, parent, false) :
                LayoutInflater.from(parent.getContext()).inflate(layout.list_item_inpatient, parent, false);


        return new InpatientRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull InpatientRecyclerAdapter.MyViewHolder holder, int position) {

        MyPatientGetSet rowItem = this.rowItems.get(position);


        try {
            holder.txtDesc.setText(this.rowItems.get(position).getPatient_Age());
            holder.gender.setText(this.rowItems.get(position).getPatient_Gender());

            holder.txtTitle.setText(this.rowItems.get(position).getPatient_Name());
            holder.pid.setText(this.rowItems.get(position).getPatient_Id());

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        BaseConfig.LoadPatientImage(this.rowItems.get(position).getPatient_Image(), holder.imageView, 50);


        holder.cardView.setOnClickListener(v -> {
            try {

                Bundle b=new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, rowItem.getPatient_Id());
                BaseConfig.globalStartIntent2(v.getContext(), Inpatient_Detailed_View.class, b);

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        });

        holder.closeOnlineButton.setOnClickListener(v -> InpatientRecyclerAdapter.showDichargeRequestDialog(holder.cardView.getContext(), rowItem.getPatient_Name(), rowItem.getPatient_Id()));
    }

    @Override
    public final int getItemCount() {
        return this.rowItems.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        MyPatientGetSet p = this.rowItems.get(position);
        String str = p.getPatient_Name();
        return String.valueOf(str.charAt(0));
    }

    static class MyViewHolder extends ViewHolder {
        final Button closeOnlineButton;
        final CircleImageView imageView;
        final TextView txtTitle;
        final LinearLayout layout;
        final TextView txtDesc;
        final TextView gender;
        final TextView pid;

        final CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);
            this.txtDesc = itemView.findViewById(id.desc);
            this.txtTitle = itemView.findViewById(id.title);
            this.imageView = itemView.findViewById(id.icon);

            this.layout = itemView.findViewById(id.online_patient);
            this.closeOnlineButton = itemView.findViewById(id.close_inpatient_patient);
            this.gender = itemView.findViewById(id.gender);
            this.pid = itemView.findViewById(id.pid);
            this.cardView = itemView.findViewById(id.card_view);
        }
    }

}
