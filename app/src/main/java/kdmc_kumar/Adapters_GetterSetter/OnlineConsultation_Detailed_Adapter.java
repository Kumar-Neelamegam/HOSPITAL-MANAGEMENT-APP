package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.OnlineConsultationReport;
import kdmc_kumar.Adapters_GetterSetter.OnlineConsultation_Detailed_Adapter.ViewHolderOnline;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CircleImageView;

/**
 * Created by Android on 4/6/2017.
 */

public class OnlineConsultation_Detailed_Adapter extends Adapter<ViewHolderOnline> {

    private static OnlineConsultation_Detailed_Adapter.OnlineConsultationItemClick onlineConsultationItemClick;
    ArrayList<OnlineConsultationReport> items;
    private final Context context;


    public OnlineConsultation_Detailed_Adapter(@NonNull Context context, ArrayList<OnlineConsultationReport> items) {
        //super(context, resourceId, items);
        this.context = context;
        this.items = items;

    }

    private static void SelectedGetPatientReports_Image(String Query, ImageView imgvw) {

        try {
            String report_name = "";
            String report64 = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        BaseConfig.LoadPatientImage(c.getString(c.getColumnIndex("ReportGallery")), imgvw, 100);

                    } while (c.moveToNext());

                }
            }

            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public OnlineConsultation_Detailed_Adapter.ViewHolderOnline onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnlineConsultation_Detailed_Adapter.ViewHolderOnline(LayoutInflater.from(parent.getContext()).inflate(layout.list_item_onlineconsultation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineConsultation_Detailed_Adapter.ViewHolderOnline holder, int position) {

        OnlineConsultationReport rowItem = this.items.get(position);

        holder.Report_Name.setText(rowItem.getReportName());
        BaseConfig.LoadPatientImage(rowItem.getReportPhoto(), holder.Report_Photo, 50);

        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        holder.rootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OnlineConsultation_Detailed_Adapter.onlineConsultationItemClick.onClickItem(rowItem, holder.Report_Name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void onClickItem(OnlineConsultation_Detailed_Adapter.OnlineConsultationItemClick consultationItemClick) {
        onlineConsultationItemClick = consultationItemClick;
    }

    public interface OnlineConsultationItemClick {
        void onClickItem(OnlineConsultationReport report, View view);
    }

    /*extends ArrayAdapter<CommonDataObjects.OnlineConsultationReport> {

    private final Context context;

    public OnlineConsultation_Detailed_Adapter(@NonNull Context context, int resourceId, List<CommonDataObjects.OnlineConsultationReport> items) {
        super(context, resourceId, items);
        this.context = context;

    }

    @NonNull
    public final View getView(int position, View convertView, @NonNull ViewGroup parent) {

        CommonDataObjects.OnlineConsultationReport rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        convertView.Report_Name.setText(rowItem.getReportName());
        BaseConfig.LoadPatientImage(rowItem.getReportPhoto(), holder.Report_Photo, 50);

        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return convertView1;
    }*/

    static class ViewHolderOnline extends ViewHolder {

        public TextView Report_Name ;
        public CircleImageView Report_Photo ;
        public LinearLayout rootLayout  ;


        public ViewHolderOnline(View itemView) {
            super(itemView);

            this.Report_Name = itemView.findViewById(id.title);
            this.Report_Photo = itemView.findViewById(id.icon);
            this.rootLayout = itemView.findViewById(id.list_root);

        }
    }


}

