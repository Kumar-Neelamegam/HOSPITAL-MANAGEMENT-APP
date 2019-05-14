package kdmc_kumar.MyPatient_Module;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.ReportGalleryAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

public class Patient_Reports extends Fragment {

    public static Bitmap reportimg;
    ArrayList<HashMap<String, String>> titles_list;
    ArrayAdapter<CommonDataObjects.RowItem> adapter;
    private RecyclerView listView;
    private ArrayList<CommonDataObjects.RowItem> rowItems;
    Bitmap reportBitmapView;

    private ReportGalleryAdapter reportGalleryAdapter;
    private String BUNDLE_PATIENT_ID;
    private ImageView no_data;
    private GridLayoutManager lLayout;

    public Patient_Reports() {
    }

    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {

        View rootView = inflater.inflate(layout.fragment_mypatient_reportgalleryfragment, container, false);

        Bundle args = this.getArguments();
        this.BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

        // initialize
        this.rowItems = new ArrayList<>();
        this.listView = rootView.findViewById(id.listView1);

        this.no_data = rootView.findViewById(id.img_nodata);


        //listView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(getActivity());
        //listView.setLayoutManager(mLayoutManager);


        this.lLayout = new GridLayoutManager(this.getActivity(), 2);
        this.listView.setHasFixedSize(true);
        this.listView.setLayoutManager(this.lLayout);
        this.listView.setNestedScrollingEnabled(false);

        assert this.listView != null;


        try {
            this.SelectedGetPatientReports("select * from ReportGallery where Patid='" + this.BUNDLE_PATIENT_ID + "';");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return rootView;
    }


    private void SelectedGetPatientReports(String Query) {
        // TODO Auto-generated method stub
        String report_name = "";
        String report64 = "";
        String Report_Path = "";

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    report_name = c.getString(c.getColumnIndex("ReportType"));
                    //report64 = c.getString(c.getColumnIndex("ReportPhoto"));
                    Report_Path = c.getString(c.getColumnIndex("FileName")) + c.getString(c.getColumnIndex("FileExtension"));

                    CommonDataObjects.RowItem item = new CommonDataObjects.RowItem(null, report_name, "", Report_Path);
                    this.rowItems.add(item);

                } while (c.moveToNext());

            }
        }
        this.listView.setHasFixedSize(true);
        assert this.listView != null;

        this.reportGalleryAdapter = new ReportGalleryAdapter(this.rowItems, this.BUNDLE_PATIENT_ID);

        this.listView.setAdapter(this.reportGalleryAdapter);

        c.close();
        db.close();

        if (this.no_data != null) {
            if (this.rowItems.size() > 0) {
                this.no_data.setVisibility(View.GONE);
            } else if (this.rowItems.size() == 0) {
                this.no_data.setVisibility(View.VISIBLE);
            }
        }

    }

    //End

}
