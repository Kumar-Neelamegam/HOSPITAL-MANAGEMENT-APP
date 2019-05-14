package kdmc_kumar.Adapters_GetterSetter;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.style;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.TestItem;
import kdmc_kumar.Adapters_GetterSetter.TestRecylerAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;


/**
 * Created by Ponnusamy M on 4/8/2017.
 */

public class TestRecylerAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<TestItem> testItems;

    public TestRecylerAdapter(ArrayList<TestItem> testItems) {
        this.testItems = testItems;
    }

    @NonNull
    @Override
    public final TestRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.new_testreport_listitem, parent, false);
        return new TestRecylerAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull TestRecylerAdapter.MyViewHolder holder, int position) {

        TestItem item = this.testItems.get(position);
        holder.repname.setText(item.getReport_Name());
        holder.reptype.setText(item.getReport_Type());
        holder.repdate.setText(item.getReport_Date().split("T")[0]);
        holder.reportsummary.setText(item.getReport_Summary());

        BaseConfig.Glide_LoadImageView(holder.imageView ,  item.getReport_Image());

        holder.imageView.setOnClickListener(view -> {

            View promptView = LayoutInflater.from(holder.imageView.getContext()).inflate(layout.imageview_zoom_dialog_report, null);
            ImageView Photo_imgvw = promptView.findViewById(id.imvw_patientphoto);
            TextView Report_Name = promptView.findViewById(id.tv_report_name);
            TextView Report_Type = promptView.findViewById(id.tv_report_type);
            TextView Report_Date = promptView.findViewById(id.tv_report_date);
            TextView Report_Summary = promptView.findViewById(id.tv_report_summary);
            Button Close = promptView.findViewById(id.cancel);

            Report_Name.setText(item.getReport_Name());
            Report_Type.setText(item.getReport_Type());
            try {
                Report_Date.setText(item.getReport_Date().split("T")[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Report_Summary.setText(item.getReport_Summary());

            BaseConfig.Glide_LoadImageView( Photo_imgvw, item.getReport_Image() );


            Dialog dialog = new Dialog(holder.imageView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(promptView);
            dialog.getWindow().getAttributes().windowAnimations = style.DialogAnimation;
            dialog.show();

            Close.setOnClickListener(view1 -> dialog.dismiss());


        });


    }

    @Override
    public final int getItemCount() {
        return this.testItems.size();
    }

    static class MyViewHolder extends ViewHolder {

        final ImageView imageView;
        final TextView repname;
        final TextView reptype;
        final TextView repdate;
        final TextView reportsummary;

        MyViewHolder(View itemView) {
            super(itemView);
            this.repname = itemView.findViewById(id.reportname);
            this.reptype = itemView.findViewById(id.report_type);
            this.repdate = itemView.findViewById(id.report_date);
            this.reportsummary = itemView.findViewById(id.report_summary);
            this.imageView = itemView.findViewById(id.imageview);

        }
    }
}
