package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.TemplateGetSet;
import kdmc_kumar.Adapters_GetterSetter.TemplateRecylerAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Masters_Modules.templates_addnew;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;

public class TemplateRecylerAdapter extends Adapter<MyViewHolder> {
    ArrayList<TemplateGetSet> templateGetSets = new ArrayList<>();

    public TemplateRecylerAdapter(ArrayList<TemplateGetSet> templateGetSets) {
        this.templateGetSets = templateGetSets;
    }

    @NonNull
    @Override
    public final TemplateRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.template_list_row, parent, false);

        return new TemplateRecylerAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull TemplateRecylerAdapter.MyViewHolder holder, int position) {

        TemplateGetSet item = this.templateGetSets.get(position);

        holder.sno.setText(item.getSno());
        //holder.template_name.setText("" + item.getName().split(":")[1]);
        holder.template_name.setText(item.getName());

        holder.card_view.setOnClickListener(v -> {
            BaseConfig.selectedtemplate = item.getName();
            BaseConfig.temp_flag = "false";
            //((Activity)holder.card_view.getContext()).finish();
            Intent lib = new Intent(v.getContext(), templates_addnew.class);
            holder.card_view.getContext().startActivity(lib);
        });

        holder.Delete.setOnClickListener(view -> {


            // Toast.makeText(templates_list.this, "Delete clicked" + item.getSno(), Toast.LENGTH_SHORT).show();


            new CustomKDMCDialog(view.getContext())
                    .setLayoutColor(color.orange_500)
                    .setImage(drawable.ic_warning_black_24dp)
                    .setTitle(view.getContext().getString(string.info))
                    .setDescription("Are you sure want to delete?")
                    .setPossitiveButtonTitle(view.getContext().getString(string.yes))
                    .setNegativeButtonTitle(view.getContext().getString(string.no))
                    .setOnPossitiveListener(() -> {

                        try {
                            SQLiteDatabase db = BaseConfig.GetDb();
                            db.execSQL("delete from TemplateDtls where TemplateName='" + item.getName() + '\'');
                            db.close();

                          //  SelectedGetPatientDetails();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        new CustomKDMCDialog(view.getContext()).setNegativeButtonVisible(View.GONE)
                                .setImage(drawable.ic_success_done)
                                .setTitle("Deleted").setNegativeButtonVisible(View.GONE)
                                .setPossitiveButtonTitle("OK");


                    });


        });


    }

    @Override
    public final int getItemCount() {
        return this.templateGetSets.size();
    }

    public class MyViewHolder extends ViewHolder {
        final TextView sno;
        final TextView template_name;
        final CardView card_view;
        final ImageView Delete;

        MyViewHolder(View itemView) {
            super(itemView);
            this.sno = itemView.findViewById(id.serial_no);
            this.template_name = itemView.findViewById(id.template_name);
            this.card_view = itemView.findViewById(id.card_view);
            this.Delete = itemView.findViewById(id.ic_delete);
        }
    }
}
