package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView.SectionedAdapter;

import java.util.List;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MyPatientGetSet;
import kdmc_kumar.Adapters_GetterSetter.MyPatienRecylerAdapter.ViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.PatientTestResult;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;

public class MyPatienRecylerAdapter extends Adapter<ViewHolder> implements SectionedAdapter {

    private final List<MyPatientGetSet> mValues;

    public MyPatienRecylerAdapter(List<MyPatientGetSet> mValues) {
        this.mValues = mValues;
    }


    @NonNull
    @Override
    public final MyPatienRecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = BaseConfig.listViewType.equals(ListViewType.ListView) ? LayoutInflater.from(parent.getContext()).inflate(layout.grid_item_patient, parent, false) :
                LayoutInflater.from(parent.getContext()).inflate(layout.list_item_patient, parent, false);


        return new MyPatienRecylerAdapter.ViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull MyPatienRecylerAdapter.ViewHolder holder, int position) {

        try {

            holder.txtDesc.setText(this.mValues.get(position).getPatient_Age());
            holder.gender.setText(this.mValues.get(position).getPatient_Gender());

            holder.txtTitle.setText(String.format("%s.%s", this.mValues.get(position).getPatient_Prefix(), this.mValues.get(position).getPatient_Name()));
            holder.pid.setText(this.mValues.get(position).getPatient_Id());

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        String PatientId1 = this.mValues.get(position).getPatient_Id();

        String status = this.mValues.get(position).IsLabReport;
        if (status.equalsIgnoreCase("1")) {

            holder.labreport.setVisibility(View.VISIBLE);
            holder.labreport_text.setTextColor(Color.RED);
        } else if (status.equalsIgnoreCase("2")) {

            holder.labreport.setVisibility(View.VISIBLE);
            holder.labreport_text.setTextColor(Color.GREEN);
        } else if ((status.equalsIgnoreCase("0"))) {
            holder.labreport.setVisibility(View.GONE);
        }

        holder.labreport.setOnClickListener(v -> {

            ((Activity) v.getContext()).finish();
            Intent in = new Intent(v.getContext(), PatientTestResult.class);
            in.putExtra("PatientId", PatientId1);
            v.getContext().startActivity(in);
        });

        try {
            BaseConfig.LoadPatientImage(this.mValues.get(position).getPatient_Image(), holder.imageView, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (this.mValues.get(position).IsOnlinePatient) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.closeOnlineButton.setVisibility(View.VISIBLE);

        } else if (!this.mValues.get(position).IsOnlinePatient) {
            holder.closeOnlineButton.setVisibility(View.GONE);
            holder.layout.setVisibility(View.GONE);
        }

        holder.closeOnlineButton.setOnClickListener(v -> this.ShowClosePatientDialog(v.getContext(), this.mValues, position));


        holder.card_view.setOnClickListener(v -> {


            String values = this.mValues.get(position).getPatient_Id();
            Intent lib = new Intent(v.getContext(), MyPatientDrawer.class);
            lib.putExtra(BaseConfig.BUNDLE_PATIENT_ID, values);
            v.getContext().startActivity(lib);

        });

    }

    private void ShowClosePatientDialog(Context ctx, List<MyPatientGetSet> mValues, int position) {

        new CustomKDMCDialog(ctx)
                .setLayoutColor(color.orange_500)
                .setImage(drawable.ic_warning_black_24dp)
                .setTitle(ctx.getString(string.close_online))
                .setDescription(ctx.getString(string.close_online_txt))
                .setPossitiveButtonTitle(ctx.getString(string.yes))
                .setNegativeButtonTitle(ctx.getString(string.no))
                .setOnPossitiveListener(() -> {


                    String Query = "Update current_patient_list set closed = '1' where patid = '" + mValues.get(position).getPatient_Id() + '\'';

                    SQLiteDatabase db = BaseConfig.GetDb();
                    db.execSQL(Query);
                    db.close();
                    mValues.get(position).IsOnlinePatient = false;
                    this.notifyDataSetChanged();
                });

    }

    @Override
    public final int getItemCount() {
        return this.mValues.size();
    }

    @NonNull
    @Override
    public final String getSectionName(int position) {

        MyPatientGetSet p = this.mValues.get(position);
        String str = p.getPatient_Name();
        return String.valueOf(str.charAt(0));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView closeOnlineButton;
        final ImageView imageView;

        final TextView txtTitle;
        final LinearLayout layout;
        final TextView txtDesc;
        final TextView gender;
        final TextView pid;

        final TextView labreport_text;
        final LinearLayout labreport;
        final CardView card_view;


        ViewHolder(View view) {
            super(view);
            this.txtDesc = view.findViewById(id.desc);
            this.txtTitle = view.findViewById(id.title);
            this.imageView = view.findViewById(id.icon);

            this.layout = view.findViewById(id.online_patient);
            this.closeOnlineButton = view.findViewById(id.close_online_patient);
            this.gender = view.findViewById(id.gender);
            this.pid = view.findViewById(id.pid);
            this.labreport = view.findViewById(id.labreport);
            this.labreport_text = view.findViewById(id.labreport_text);
            this.card_view = view.findViewById(id.card_view);

        }

    }

}
//**********************************************************************************************

