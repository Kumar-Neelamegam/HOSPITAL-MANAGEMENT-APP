package kdmc_kumar.MyPatient_Module;

import android.R.color;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.PDFReportItems;
import kdmc_kumar.Adapters_GetterSetter.PdfReportAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

import static displ.mobydocmarathi.com.R.id.swipeRefreshLayout;

/**
 * Created by Android on 12-Mar-18.
 */

public class PDFReport_Profile extends android.support.v4.app.Fragment {


//**********************************************************************************************

    private RecyclerView listView;
    private PdfReportAdapter adapter;

    private final ArrayList<PDFReportItems> rowItems = new ArrayList<>();

    private String BUNDLE_PATIENT_ID;
    private ImageView no_data;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GridLayoutManager lLayout;

    public PDFReport_Profile() {
    }

    //**********************************************************************************************
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(layout.fragment_mypatient_reportgalleryfragment, container, false);

        Bundle args = this.getArguments();
        this.BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

        this.listView = rootView.findViewById(id.listView1);
        this.mSwipeRefreshLayout = rootView.findViewById(swipeRefreshLayout);

        this.lLayout = new GridLayoutManager(this.getActivity(), 1);
        this.listView.setHasFixedSize(true);
        this.listView.setLayoutManager(this.lLayout);
        this.listView.setNestedScrollingEnabled(false);

        this.no_data = rootView.findViewById(id.img_nodata);

        try {

            this.SelectedGetPatientPDFReports("select * from Bind_PDFInfo where PATIENTID='" + this.BUNDLE_PATIENT_ID + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }


        this.mSwipeRefreshLayout = rootView.findViewById(swipeRefreshLayout);
        this.mSwipeRefreshLayout.setColorSchemeResources(color.holo_green_dark, color.holo_red_dark, color.holo_blue_bright);
        this.mSwipeRefreshLayout.setOnRefreshListener(() -> {

            try {

                this.SelectedGetPatientPDFReports("select * from Bind_PDFInfo where PATIENTID='" + this.BUNDLE_PATIENT_ID + "';");

            } catch (Exception e) {
                e.printStackTrace();
            }


        });


        return rootView;
    }

    //**********************************************************************************************
    private void SelectedGetPatientPDFReports(String Query) {


        try {
            SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        int Id = Integer.parseInt(c.getString(c.getColumnIndex("Id")));
                        String PATIENTNAME = c.getString(c.getColumnIndex("PATIENTNAME"));
                        String PATIENTID = c.getString(c.getColumnIndex("PATIENTID"));
                        String SYMP = c.getString(c.getColumnIndex("SYMPTOMS"));
                        String DIAGNOSIS = c.getString(c.getColumnIndex("DIAGNOSIS"));
                        String VISITEDON = c.getString(c.getColumnIndex("ACT_DATETIME"));


                        PDFReportItems item = new PDFReportItems(Id, PATIENTID, PATIENTNAME, SYMP, DIAGNOSIS, VISITEDON);
                        this.rowItems.add(item);


                    } while (c.moveToNext());

                }
            }

            this.listView.setHasFixedSize(true);
            assert this.listView != null;

            this.adapter = new PdfReportAdapter(this.rowItems, this.BUNDLE_PATIENT_ID, this.getActivity());

            this.listView.setAdapter(this.adapter);

            c.close();
            db.close();

            if (this.no_data != null) {
                if (this.rowItems.size() > 0) {
                    this.no_data.setVisibility(View.GONE);
                } else if (this.rowItems.size() == 0) {
                    this.no_data.setVisibility(View.VISIBLE);
                }
            }

            // Stop refresh animation
            this.mSwipeRefreshLayout.setRefreshing(false);


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //**********************************************************************************************
}//END
