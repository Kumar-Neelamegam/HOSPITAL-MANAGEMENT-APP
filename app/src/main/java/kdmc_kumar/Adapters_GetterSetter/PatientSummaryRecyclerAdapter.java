package kdmc_kumar.Adapters_GetterSetter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.OperationItem;
import kdmc_kumar.Adapters_GetterSetter.PatientSummaryRecyclerAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.MyPatient_Module.OperationListSummary;

/**
 * Created by Ponnusamy M on 4/6/2017.
 */

public class PatientSummaryRecyclerAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<OperationItem> operationItems;
    private final String BUNDLE_PATIENT_ID;

    public PatientSummaryRecyclerAdapter(ArrayList<OperationItem> operationItems, String PATIENT_ID) {
        this.operationItems = operationItems;
        this.BUNDLE_PATIENT_ID = PATIENT_ID;
    }

    @NonNull
    @Override
    public final PatientSummaryRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout.new_summary_item, parent, false);

        return new PatientSummaryRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull PatientSummaryRecyclerAdapter.MyViewHolder holder, int position) {

        OperationItem item = this.operationItems.get(position);

        holder.sno.setText(item.getSerialNo());
        holder.operation_name.setText(item.getOperation_name());
        String operationdate[] = item.getOperation_date().split("T");
        String fromdate[] = item.getFrom_time().split("T");
        String todate[] = item.getTo_Time().split("T");
        holder.opreation_date.setText(operationdate[0]);
        holder.from_time.setText(fromdate[1]);
        holder.to_time.setText(todate[1]);
        holder.doc_name.setText(item.getReference_doc());


        holder.tableLayout.setOnClickListener(v -> {

            Intent nxtintent = new Intent(holder.doc_name.getContext(), OperationListSummary.class);
            nxtintent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.BUNDLE_PATIENT_ID);
            holder.from_time.getContext().startActivity(nxtintent);


        });
    }

    @Override
    public final int getItemCount() {
        return this.operationItems.size();
    }

    static class MyViewHolder extends ViewHolder {

        final TextView sno;
        final TextView operation_name;
        final TextView opreation_date;
        final TextView from_time;
        final TextView to_time;
        final TextView doc_name;
        final LinearLayout linearLayout;
        final TableLayout tableLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            this.sno = itemView.findViewById(id.count_1);
            this.operation_name = itemView.findViewById(id.count_2);
            this.opreation_date = itemView.findViewById(id.count_3);
            this.from_time = itemView.findViewById(id.count_4);
            this.to_time = itemView.findViewById(id.count_5);
            this.doc_name = itemView.findViewById(id.count_6);

            this.linearLayout = itemView.findViewById(id.linearlayout);

            this.tableLayout = itemView.findViewById(id.tablelayout);

        }
    }
}
