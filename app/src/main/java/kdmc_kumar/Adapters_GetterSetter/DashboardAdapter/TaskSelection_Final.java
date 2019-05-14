package kdmc_kumar.Adapters_GetterSetter.DashboardAdapter;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;

public class TaskSelection_Final extends Fragment {


    public static final String DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().getPath();
    public static RecyclerView grid;
    public static int flag = 0;
    private static int pcount = 0;
    private static String pdata = "0";
    private static String clinicdata = "0";
    private static String Mprescdata = "0";
    private static String cnotesize = "0";
    private static String invstsize = "0";
    private static int total = 0;
    private String PATIENT_INFO_LABEL = "";
    private final String[] prgmNameList = new String[12];
    private final int[] prgmImages =
            {
                    R.drawable.dashboard_ic_patient_registration,
                    R.drawable.dashboard_ic_case_book,
                    R.drawable.dashboard_ic_diagnosis_details,
                    R.drawable.dashboard_ic_medical_test,
                    R.drawable.dashboard_ic_prescription,
                    R.drawable.dashboard_ic_specialist_referral,
                    R.drawable.dashboard_ic_my_patients,
                    R.drawable.dashboard_ic_drug,
                    R.drawable.dashboard_ic_online_consultation,
                    R.drawable.dashboard_ic_inpatient,
                    R.drawable.dashboard_ic_masters,
                    R.drawable.dashboard_ic_cloud
            };
    // *************************************************************************************************
    // Declarations
    BaseConfig bc = new BaseConfig();
    private ImageView Logo;
    private ImageView TopBanner;
    private ImageView BottomBanner;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.layout_taskselection, container, false);


        prgmNameList[0] = getString(R.string.dashboard_patientregistration);
        prgmNameList[1] = getString(R.string.dashboard_casenotes);
        prgmNameList[2] = getString(R.string.dashboard_clinicalinformation);
        prgmNameList[3] = getString(R.string.dashboard_investigation);
        prgmNameList[4] = getString(R.string.dashboard_prescription);
        prgmNameList[5] = getString(R.string.dashboard_specialist_referral);
        prgmNameList[6] = getString(R.string.dashboard_mypatients);
        prgmNameList[7] = getString(R.string.dashboard_drugdirectory);
        prgmNameList[8] = getString(R.string.dashboard_onlineconsultation);
        prgmNameList[9] = getString(R.string.dashboard_inpatient);
        prgmNameList[10] = getString(R.string.dashboard_masters);
        prgmNameList[11] = getString(R.string.dashboard_clouds);

        try {

            // Initialize Controlls
            GetInitialize(rootView);


            LoadGridView(rootView);

            Default_settings();


            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatterr = new SimpleDateFormat("dd",Locale.ENGLISH);
            String daynum = formatterr.format(currentDate.getTime());
            SimpleDateFormat formatterm = new SimpleDateFormat("yyyy", Locale.ENGLISH);
            String yrnum = formatterm.format(currentDate.getTime());
            String dayname = (String) android.text.format.DateFormat.format("EEEE", currentDate);
            String Monthname = (String) android.text.format.DateFormat.format("MMMM", currentDate);
            String finaldt = dayname + ", " + Monthname + " " + daynum + ", " + yrnum;// +"  , "+tm;

            BaseConfig.timedate = finaldt;

            BaseConfig.LoadDoctorValues();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return rootView;
    }

    private void GetInitialize(View rootview) {


        TopBanner = rootview.findViewById(R.id.top_banner1);

        BottomBanner = rootview.findViewById(R.id.bottom_banner2);

        grid = rootview.findViewById(R.id.task_grid);

        Logo = rootview.findViewById(R.id.logo);

        if (BaseConfig.welcometoast == 1) {

            Animation AnimationBottom = AnimationUtils.loadAnimation(rootview.getContext(), R.anim.slide_in_top);
            TopBanner.startAnimation(AnimationBottom);

            Animation AnimationTop = AnimationUtils.loadAnimation(rootview.getContext(), R.anim.slid_up);
            BottomBanner.startAnimation(AnimationTop);

            int resId = R.anim.layout_animation_from_bottom;
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(rootview.getContext(), resId);
            grid.setLayoutAnimation(animation);

        }



    }

    // *************************************************************************************************
    void ShowLogOutPopup() {


        BaseConfig.ExitSweetDialog(getActivity(), TaskSelection_Final.class);

    }

    private void LoadGridView(View rootview) {


        DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(getActivity(), prgmNameList, prgmImages);
        grid.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        grid.setAdapter(dashBoardAdapter);


    }

    // *************************************************************************************

    public void onStop() {
        Log.i("~~~~", "~~~onStop ~~~ ");

        super.onStop();


    }

    // *************************************************************************************

    public void onDestroy() {
        Log.i("~~~~", "~~~onDestroy ~~~ ");
        super.onDestroy();

    }

    private void Default_settings() {
        try {
            int count = 0;

            try {
                String Query = "select count(*) as Count_Value from drsettings";

                SQLiteDatabase db = BaseConfig.GetDb();//);

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            if (c.getString(c.getColumnIndex("Count_Value")) != null) {
                                count = Integer.parseInt(c.getString(c.getColumnIndex("Count_Value")));
                            } else {
                                count = 0;
                            }

                        } while (c.moveToNext());

                    }
                }
    c.close();
                if (count == 0) {

                    SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.ENGLISH);
                    Date date = new Date();
                    dateformt.format(date);
                    String dttm = dateformt.format(date);

                    //1. KDMC
                    String Query1 = "insert into   drsettings (EnableAutoUpdate,CaseNotes_Systems,docid,drname,dremail," +
                            "dt,enablepin,workingdays,consultationhrs,enableappointment,noofpatients,dailyappointmentviasms,professional_charges_column,referral_report,prefpharmacy,pharmdetails,preflab,labdetails,IsUpdate,IsActive,HID)" +
                            " values(1,'1,9,10,', '" + BaseConfig.doctorId + "','" + BaseConfig.doctorname + "','" + BaseConfig.docemail + " ','" + dttm + " ','false','Monday,Tuesday,Wednesday,Thursday,Friday,Saturday','From 10:00-To 13:00" +
                            "From 18:00-To 21:00','No','','No','No','No','BAI RUKMINIBAI HOSPITAL DISPENSARY,KALYAN','BAI RUKIMINIBAI HOSPITAL,KALYAN (WEST)\\n Phone No-2316324 - 2320202','BAI RUKIMINIBAI HOSPITAL,LABORATORY','BAI RUKIMINIBAI HOSPITAL,KALYAN (WEST)\\n Phone No-2316324 - 2320202','0','1','" + BaseConfig.HID + "')";
                    db.execSQL(Query1);

         /*     //2. MD - Mobydoctor
                   String Query1 = "insert into   drsettings (docid,drname,dremail," +
                            "dt,enablepin,workingdays,consultationhrs,enableappointment,noofpatients,dailyappointmentviasms,professional_charges_column,referral_report,prefpharmacy,pharmdetails,preflab,labdetails,IsUpdate,IsActive,HID)" +
                            " values('" + BaseConfig.doctorId + "','" + BaseConfig.doctorname + "','" + BaseConfig.docemail + " ','" + dttm + " ','false','Monday,Tuesday,Wednesday,Thursday,Friday,Saturday','From 10:00-To 13:00" +
                            "From 18:00-To 21:00','No','','No','No','No','Mahan Pharmacy','Anna nagar, Chennai,\nPhone No-2316324 - 2320202','Mahan LABORATORY','Mahan HOSPITAL, Chennai\\n Phone No-2316324 - 2320202','0','1','"+BaseConfig.HID+"')";

    */
                    //String Query = "UPDATE diet_entry SET IsDelete = 1 WHERE  pat_id='" + Patient_ids + "' ";

                }


                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // *************************************************************************************


}//end
