package kdmc_kumar.Core_Modules;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu.NotificationClass;
import kdmc_kumar.Inpatient_Module.OperationTheater;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_ANCTestReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_Blood_Report;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_StoolReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_UrineReport;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;


public class PatientTestResult extends AppCompatActivity {
    private static final int WRAP_CONTENT = 21;
    private final ArrayList<NotificationClass> notificationListTest = new ArrayList<>();
    private ImageView lab_patitent_image;
    private String value;
    private TextView patientname;
    private TextView patientid;
    private TextView patientage;
    private TextView patientgender;


    private Toolbar toolbar;
    private ImageView back;
    private ImageView exit;
    private TextView Title;

    private Button more_details;
    String strPatid = "", strMtestId = "";
    private final String compareExamination = "All examination of blood";
    private final String compareStoolReport = "All stool test";
    private final String compareUrineReport = "All urine test";
    private final String compareAncfpReport = "All anc fp test";
    private final String compareHIVReport = "All hiv test";

    public PatientTestResult() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_patient_test_result);


        // #######################################################################################################

        this.toolbar = this.findViewById(id.toolbar);
        this.exit = this.toolbar.findViewById(id.ic_exit);
        this.back = this.toolbar.findViewById(id.ic_back);
        this.Title = this.toolbar.findViewById(id.txvw_title);
        this.Title.setText(this.getString(string.app_name) + " - " + "Lab Reports");

        this.setSupportActionBar(this.toolbar);

        this.exit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));


        this.back.setOnClickListener(view -> this.LoadBack());
        // #######################################################################################################


        this.value = this.getIntent().getExtras().getString("PatientId");

        this.lab_patitent_image = this.findViewById(id.lab_patitent_image);
        this.patientname = this.findViewById(id.lab_patient_name);
        this.patientid = this.findViewById(id.lab_patient_id);
        this.patientage = this.findViewById(id.lab_patient_age);
        this.patientgender = this.findViewById(id.lab_patient_gender);

        this.more_details = this.findViewById(id.more_info);

        this.more_details.setOnClickListener(v -> {

            ((Activity) v.getContext()).finish();
            Intent lib = new Intent(v.getContext(), MyPatientDrawer.class);
            lib.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.value);
            lib.putExtra("TEST_REPORT", "True");
            v.getContext().startActivity(lib);

        });

        LinearLayout parentLayout = this.findViewById(id.insert_rows);

        // TODO: 3/24/2017 set Patient Details
        PatientTestResult.setPatientDetails(this.value, this.lab_patitent_image, this.patientname, this.patientid, this.patientage, this.patientgender);


        // TODO: 3/24/2017 Checked M.Ponnusamy in Medical Test Notification
        this.notificationListTest.clear();

        boolean status_value;


        String compareRBSName = "blood sug random";
        String compareFBSName = "blood sug fasting";
        String comparePBSName = "blood sug pp";


        String Query = "select Id as dstatus1  from Medicaltestdtls where Ptid='" + this.value + "' and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";

        //Log.e("Test result query: ", Query);
        //Log.e("Test result query: ", Query);
        //Log.e("Test result query: ", Query);

        status_value = PatientTestResult.LoadReportsBooleanStatus(Query);


        if (status_value) {
            SQLiteDatabase db = BaseConfig.GetDb();//PatientTestResult.this);

            Cursor c = db.rawQuery("select *  from Medicaltestdtls where Ptid='" + this.value + "' and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        NotificationClass objClass = new NotificationClass();


                        if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareRBSName)) {
                            objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                            objClass.TestValues = String.valueOf(PatientTestResult.checkNull(c.getString(c.getColumnIndex("temperature"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();


                        } else if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareFBSName)) {
                            objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                            objClass.TestValues = String.valueOf(PatientTestResult.checkNull(c.getString(c.getColumnIndex("bps"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();


                        } else if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(comparePBSName)) {
                            objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                            objClass.TestValues = String.valueOf(PatientTestResult.checkNull(c.getString(c.getColumnIndex("bpd"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();


                        } else if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareExamination)) //EXAMINATION OF BLOOD - All examination of blood
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_examination_blood_test where Patid='" + this.value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareStoolReport)) //STOOL REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_stool_report where Patid='" + this.value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareUrineReport)) //URINE REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_urine_test where Patid='" + this.value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareAncfpReport)) //ANC FP REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_anc_fp_test where Patid='" + this.value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareHIVReport)) //HIV REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_HIV_Report where Patid='" + this.value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else {
                            objClass.TestName = c.getString(c.getColumnIndex("testname")) + " - " + PatientTestResult.checkNull(c.getString(c.getColumnIndex("subtestname")));
                            objClass.TestValues = String.valueOf(PatientTestResult.checkNull(c.getString(c.getColumnIndex("testvalue"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();

                        }


                        this.notificationListTest.add(objClass);
                    }
                    while (c.moveToNext());
                }
            }

            c.close();
            db.close();


        }

        try {
            this.showNotificationForTestValues(parentLayout);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static final void setPatientDetails(String patientid, ImageView image, TextView name, TextView id, TextView age, TextView gender) {

        try {
            String str = "";


            String Query = "select name,Patid,gender,age,PC from Patreg where Patid='" + patientid + '\'';

            SQLiteDatabase db = BaseConfig.GetDb();//PatientTestResult.this);

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        name.setText(c.getString(c.getColumnIndex("name")));
                        id.setText(c.getString(c.getColumnIndex("Patid")));
                        gender.setText(c.getString(c.getColumnIndex("gender")));
                        age.setText(c.getString(c.getColumnIndex("age")));


                        BaseConfig.Glide_LoadImageView( image,  c.getString(c.getColumnIndex("PC")));




                    } while (c.moveToNext());

                }
            }
            c.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private final void showNotificationForTestValues(LinearLayout parentLayout) {

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflator = getLayoutInflater();


        for (int i = 0; i <= this.notificationListTest.size() - 1; i++) {


            LinearLayout layout = (LinearLayout) inflator.inflate(layout.patient_test_result_row, null);
            LinearLayout viewRoot = layout.findViewById(id.row_lay);
            TextView TestName = layout.findViewById(id.lab_testname);
            TextView TestValue = layout.findViewById(id.lab_testvalue);
            NotificationClass notifyObj = this.notificationListTest.get(i);

            TestName.setText(notifyObj.TestName);
            TestValue.setText(notifyObj.TestValues);


            TestValue.setOnClickListener(view -> {

                String[] testname = TestName.getText().toString().split("-");

                String str_testname = testname[1];
                String testvalues = TestValue.getText().toString();

                if (str_testname.equalsIgnoreCase(this.compareExamination))//blood test
                {
                    if (testvalues.equalsIgnoreCase("More")) {
                        this.startActivity(new Intent(this, Profile_Blood_Report.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }
                } else if (str_testname.equalsIgnoreCase(this.compareStoolReport))//stool test
                {
                    if (testvalues.equalsIgnoreCase("More")) {
                        this.startActivity(new Intent(this, Profile_StoolReport.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }
                } else if (str_testname.equalsIgnoreCase(this.compareUrineReport))//urine test
                {
                    if (testvalues.equalsIgnoreCase("More")) {
                        this.startActivity(new Intent(this, Profile_UrineReport.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }
                } else if (str_testname.equalsIgnoreCase(this.compareAncfpReport)) {
                    if (testvalues.equalsIgnoreCase("More")) {
                        this.startActivity(new Intent(this, Profile_ANCTestReport.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }

                }

            });


            viewRoot.setOnClickListener(view -> {
                Intent iStart = null;


            });


            rootLayout.addView(layout);


        }


        if (this.notificationListTest.size() > 0) {
            parentLayout.addView(rootLayout);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

        }


    }

    private static final boolean LoadReportsBooleanStatus(String Query) {
        try {
            int havcount = 0;

            SQLiteDatabase db = BaseConfig.GetDb();//PatientTestResult.this);

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        c.getString(c.getColumnIndex("dstatus1"));

                        havcount++;

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return havcount > 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public final void onBackPressed() {
        super.onBackPressed();

        this.LoadBack();

    }

    private final void LoadBack() {

        BaseConfig.globalStartIntent(this, MyPatient.class, null);

    }

    private static final String checkNull(String val) {
        String returns_Val = "";
        try {


            return val.equalsIgnoreCase("null") || val.equalsIgnoreCase(null) || val.equalsIgnoreCase("") ? " " : val;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return returns_Val;
    }


}//end
