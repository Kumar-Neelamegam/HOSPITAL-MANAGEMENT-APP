package kdmc_kumar.MyPatient_Module;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.PatientSummaryRecyclerAdapter;
import kdmc_kumar.Adapters_GetterSetter.PrescriptionRecylerAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Ponnusamy M on 4/5/2017.
 */

public class InpatientSummary_Profile extends Fragment {

    private ArrayList<CommonDataObjects.OperationItem> operationItems;

    private PatientSummaryRecyclerAdapter patientSummaryRecyclerAdapter;

    private PrescriptionRecylerAdapter prescriptionRecylerAdapter;

    private ArrayList<CommonDataObjects.PrescriptionItem> prescriptionItems;
    private RecyclerView recyler_view_prescription;

    private String BUNDLE_PATIENT_ID;

    public InpatientSummary_Profile() {
    }


    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(layout.newinpatientsummary_profile, container, false);

        Bundle args = this.getArguments();
        this.BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

        String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');

        TextView patienname_id = view.findViewById(id.patienname_id);
        patienname_id.setText(Patient_Name + " - " + this.BUNDLE_PATIENT_ID);


        RecyclerView recyler_view = view.findViewById(id.recyler_view);

        this.recyler_view_prescription = view.findViewById(id.recyler_view_prescription);

        LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyler_view.setLayoutManager(mLayoutManager);
        recyler_view.setItemAnimator(new DefaultItemAnimator());


        // TODO: 4/6/2017 Set Recyler View Data
        this.setReclerViewDetails(recyler_view);

        return view;
    }


    private final void setReclerViewDetails(RecyclerView reclerViewDetails) {

        this.operationItems = new ArrayList<>();
        ArrayList wherelikedata = new ArrayList();

        try {

            SQLiteDatabase db = BaseConfig.GetDb();

            int i = 0;
            Cursor c = db.rawQuery("select * from mast_Operation where patid='" + this.BUNDLE_PATIENT_ID + "' group by OperationNo", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        ++i;
                        String va;
                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(1322018752992L);


                            int mYear = calendar.get(Calendar.YEAR);
                            int mMonth = calendar.get(Calendar.MONTH);
                            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                            int hour = calendar.get(Calendar.HOUR);
                            int min = calendar.get(Calendar.MINUTE);
                            int sec = calendar.get(Calendar.SECOND);

                            va = c.getString(c.getColumnIndex("ScheduleDate"));
                            DateTimeFormatter formatter = DateTimeFormat.forPattern("yy/MM/ss HH:mm:ss");
                            DateTimeFormatter formatter1 = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
                            DateTime dt = formatter.parseDateTime(va);


                            va = dt.toString();


                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }


                        this.operationItems.add(new CommonDataObjects.OperationItem(String.valueOf(i), c.getString(c.getColumnIndex("OperationName")), c.getString(c.getColumnIndex("ScheduleDate")), c.getString(c.getColumnIndex("Fromtime")), c.getString(c.getColumnIndex("Totime")), c.getString(c.getColumnIndex("DoctorName"))));

                        wherelikedata.add(c.getString(c.getColumnIndex("ScheduleDate")));


                    } while (c.moveToNext());
                }
            }
            c.close();
            this.patientSummaryRecyclerAdapter = new PatientSummaryRecyclerAdapter(this.operationItems, this.BUNDLE_PATIENT_ID);
            LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
            reclerViewDetails.setLayoutManager(mLayoutManager);
            reclerViewDetails.setItemAnimator(new DefaultItemAnimator());
            reclerViewDetails.setAdapter(this.patientSummaryRecyclerAdapter);

            this.setReclerViewDetails_Dignosis(this.recyler_view_prescription, wherelikedata);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private final void setReclerViewDetails_Dignosis(RecyclerView reclerViewDetails, ArrayList datevalues) {
        this.prescriptionItems = new ArrayList<>();


        try {
            for (int ij = 0; ij < datevalues.size(); ij++) {

                SQLiteDatabase db = BaseConfig.GetDb();//getContext().openOrCreateDatabase(Environment.getExternalStorageDirectory().getPath() + "/.MobydoctorDatabase"+ File.separator + "mobydoc.db",0,null,null);

                String Tabledata[];

                int i = 0;
                db.isOpen();
                Cursor c = db.rawQuery("select remarks,refdocname,Medid,medicinename,treatmentfor,diagnosis,nextvisit,Actdate from Mprescribed where Ptid='" + this.BUNDLE_PATIENT_ID + "' and (substr(Actdate,0,12)='" + datevalues.get(ij) + "' or substr(Actdate,0,12)='" + datevalues.get(ij) + "') order by Medid desc ;", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            ++i;
                            Tabledata = new String[c.getCount()];

                            Tabledata = c.getString(c.getColumnIndex("medicinename")).split("_");

                            String nxtdt = c.getString(c.getColumnIndex("nextvisit"));
                            String visiteddt[] = c.getString(c.getColumnIndex("Actdate")).split(" ");
                            String remarksStr = c.getString(c.getColumnIndex("remarks"));

                            String refdocname = c.getString(c.getColumnIndex("refdocname"));
                            String diagnosisStr = c.getString(c.getColumnIndex("diagnosis"));
                            String symStr = c.getString(c.getColumnIndex("treatmentfor"));

                            //PrescriptionItem(String sno,String medicine_Name, String interval, String frequency, String duration, String doctorname)
                            this.prescriptionItems.add(new CommonDataObjects.PrescriptionItem(String.valueOf(i), Tabledata[0], Tabledata[1], Tabledata[2], Tabledata[3], refdocname));

                        } while (c.moveToNext());
                    }
                }

                c.close();
            }

            this.prescriptionRecylerAdapter = new PrescriptionRecylerAdapter(this.prescriptionItems);
            LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
            reclerViewDetails.setLayoutManager(mLayoutManager);
            reclerViewDetails.setItemAnimator(new DefaultItemAnimator());
            reclerViewDetails.setAdapter(this.prescriptionRecylerAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    //End
}
