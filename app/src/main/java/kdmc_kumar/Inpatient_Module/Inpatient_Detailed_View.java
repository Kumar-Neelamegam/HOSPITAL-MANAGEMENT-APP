package kdmc_kumar.Inpatient_Module;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import displ.mobydocmarathi.com.R.style;
import kdmc_kumar.CaseNotes_Modules.CaseNotes;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.ClinicalInformation;
import kdmc_kumar.Core_Modules.Investigations;
import kdmc_kumar.Core_Modules.MedicinePrescription;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Bar_DiabeticChart;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Bar_Inpatient;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Bar_InputChart;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Bar_OutputChart;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Bar_TemperatureChart;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Line_DiabeticChart;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Line_Inpatient;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Line_InputChart;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Line_OutputChart;
import kdmc_kumar.Inpatient_Module.Inpatient_Charts.Chart_Line_TemperatureChart;

/**
 * Created by Vidhya-Android on 4/11/2017.
 */

public class Inpatient_Detailed_View extends AppCompatActivity {


    @BindView(id.inpatient_parent_layout) CoordinatorLayout inpatientParentLayout;
    @BindView(id.inpatient_imgvw_patient_photo) ImageView inpatientImgvwPatientPhoto;
    @BindView(id.inpatient_webvw_profile) WebView inpatientWebvwProfile;
    @BindView(id.inpatient_fab) FloatingActionButton inpatientFab;

    @BindView(id.toolbar) Toolbar toolbar;
    @BindView(id.toolbar_back) AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title) TextView toolbarTitle;
    @BindView(id.toolbar_exit) AppCompatImageView toolbarExit;

    String COMMON_PATIENTID;


    private static final int LoadBack_ACTIVITY = 1;
    private static final int DATE_DIALOG_ID = 1;
    /**
     * @Vidhya 11-4-2017
     * @param savedInstanceState
     */


    private static int mYear;
    private static int mMonth;
    private static int mDay;
    public static int mcYear;
    public static int mcMonth;
    public static int mcDay;

    private String Bundle_Patient_Id;
    private String Bundle_Patient_Name;
    private String Bundle_Patient_Agegender;


    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    private final OnDateSetListener mDateSetListener = (datePicker, i, i1, i2) -> {
        Inpatient_Detailed_View.mYear = i;
        Inpatient_Detailed_View.mMonth = i1;
        Inpatient_Detailed_View.mDay = i2;
        this.updateDisplay();
    };

    public Inpatient_Detailed_View() {
    }



    // #######################################################################################################
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.new_layout_inpatient_view);


        try {

            this.GETINITIALIZE();

            this.CONTROLLISTENERS();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GETINITIALIZE() {

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.setSupportActionBar(this.toolbar);

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.inpat)));

        this.inpatientWebvwProfile.getSettings().setJavaScriptEnabled(true);
        this.inpatientWebvwProfile.setWebChromeClient(new WebChromeClient());
        this.inpatientWebvwProfile.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.inpatientWebvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.inpatientWebvwProfile.getSettings().setRenderPriority(RenderPriority.HIGH);


        Calendar c = Calendar.getInstance();
        Inpatient_Detailed_View.mYear = c.get(Calendar.YEAR);
        Inpatient_Detailed_View.mMonth = c.get(Calendar.MONTH);
        Inpatient_Detailed_View.mDay = c.get(Calendar.DAY_OF_MONTH);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {

            this.COMMON_PATIENTID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);

        }



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        this.dateFormatter = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        try {
            this.LoadWebview(sdf.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void CONTROLLISTENERS() {


        this.inpatientFab.setOnClickListener(view -> this.ShowDialog());

        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarBack.setOnClickListener(view -> this.LoadBack());

        this.inpatientImgvwPatientPhoto.setOnClickListener(v -> {

            String Query = "select name as ret_values from Patreg where Patid='" + this.Bundle_Patient_Id + '\'';

            this.Bundle_Patient_Name = BaseConfig.GetValues(Query);

            String Query1 = "select age||'-'||gender as ret_values from Patreg where Patid='" + this.Bundle_Patient_Id + '\'';
            this.Bundle_Patient_Agegender = BaseConfig.GetValues(Query1);

            BaseConfig.Show_Patient_PhotoInDetail(this.Bundle_Patient_Name, this.Bundle_Patient_Id, this.Bundle_Patient_Agegender, this);

        });

    }

    //#######################################################################################################
    private final void LoadBack() {

        BaseConfig.globalStartIntent(this, Inpatient_List.class,null);
    }

    /**
     * Common method to get all chart values
     */
    private final void LoadingChart(Button btn, int Id) {

        boolean status;

        switch (Id) {
            case 1:


                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_MainChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Bar_Inpatient.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.nodata), this.getString(string.ok));
                }

                break;


            case 2:


                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_DiabeticChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Bar_DiabeticChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }

                break;


            case 3:

                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_TemperatureChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Bar_TemperatureChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


                break;


            case 4:


                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_MainChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Bar_InputChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


                break;


            case 5:


                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_MainChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Bar_OutputChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


                break;


            case 6:

                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_MainChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Line_Inpatient.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


                break;


            case 7:


                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_DiabeticChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Line_DiabeticChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


                break;


            case 8:


                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_TemperatureChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Line_TemperatureChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


                break;

            case 9:

                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_MainChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Line_InputChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


                break;


            case 10:

                status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Inpatient_MainChart where patid='" + this.Bundle_Patient_Id + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(this, Chart_Line_OutputChart.class);
                    intent.putExtra("ID", "1");
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, this.getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }

                break;

        }


    }



    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.overridePendingTransition(anim.slide_in_left, anim.slide_out_right);

        if (requestCode == Inpatient_Detailed_View.LoadBack_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                if (status.equalsIgnoreCase("done")) {

                    try {

                        this.LoadWebview("");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private final void LoadWebview(String DateValue) {
        try {

            this.inpatientWebvwProfile.getSettings().setJavaScriptEnabled(true);
            this.inpatientWebvwProfile.setLayerType(View.LAYER_TYPE_NONE, null);
            this.inpatientWebvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            this.inpatientWebvwProfile.getSettings().setRenderPriority(RenderPriority.HIGH);

            this.inpatientWebvwProfile.setWebChromeClient(new Inpatient_Detailed_View.MyWebChromeClient());

            this.inpatientWebvwProfile.setBackgroundColor(0x00000000);

            this.inpatientWebvwProfile.addJavascriptInterface(new WebAppInterface(this), "android");
            try {

                this.inpatientWebvwProfile.loadDataWithBaseURL("file:///android_asset/", this.LoadInPatientProfile(DateValue), "text/html", "utf-8", null);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String LoadInPatientProfile(String DateValue) {

        try {
            String value = "";

            String PatientName = "-", PatientId = "-", BloodGroup = "-", Address = "-", Address1 = "-", District = "-", city = "-", caretaker = "-", crtknum = "-", phone = "-", gender = "-", age = "-";
            String ward_values = "-", room_values = "-", bed_values = "-", admitDate = "-", admitTime = "-";
            String Photo = "-";

            //Patient Info
            SQLiteDatabase db = BaseConfig.GetDb();//Inpatient_Detailed_View.this);

            Cursor c = db.rawQuery("select wardno,roomno,bedno,admitdt,admit_time,Patid,name,bloodgroup,Address,Address1,District,city,caretaker,crtknum,phone,age,gender,PC from Patreg where patid='" + this.COMMON_PATIENTID + "'  order by id desc;", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        ward_values = BaseConfig.CheckDBString(BaseConfig.getwardNameFromId(c.getString(c.getColumnIndex("wardno")), this));
                        room_values = BaseConfig.CheckDBString(BaseConfig.getRoomNameFromId(c.getString(c.getColumnIndex("roomno")), this));
                        bed_values = BaseConfig.CheckDBString(BaseConfig.getBedNameFromId(c.getString(c.getColumnIndex("bedno")), this));


                        try {

                            admitDate = c.getString(c.getColumnIndex("admitdt"));
                            admitTime = c.getString(c.getColumnIndex("admit_time"));
                            if (admitDate.contains(" ") || admitDate.contains("T")) {
                                admitDate = admitDate.contains(" ")? admitDate.split(" ")[0] : admitDate.split("T")[0];
                            }

                            if (admitTime.contains(" ") || admitTime.contains("T")) {
                                admitTime = admitTime.contains(" ")? admitTime.split(" ")[1] : admitTime.split("T")[1];
                            }

                                admitTime = admitTime.contains("Z")? admitTime.replace("Z","") : admitTime;


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        PatientName = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("name")));
                        PatientId = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Patid")));

                        this.Bundle_Patient_Id = PatientId;

                        BloodGroup = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("bloodgroup")));
                        Address = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Address")));
                        Address1 = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Address1")));
                        District = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("District")));
                        city = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("city")));
                        caretaker = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("caretaker")));
                        crtknum = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("crtknum")));
                        phone = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("phone")));
                        age = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("age")));
                        gender = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("gender")));

                        Photo = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("PC")));

                        BaseConfig.LoadPatientImage(Photo, this.inpatientImgvwPatientPhoto, 100);


                    } while (c.moveToNext());
                }
            }

            c.close();

            String Str1 = "<div >\n" +
                    '\n' +
                    '\n' +
                    "<table style=\"width:100%\" class=\"table\">\n" +
                    "   \n" +
                    "<tbody>\n" +
                    '\n' +
                    "<tr >\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-id-card-o\" aria-hidden=\"true\"></i><b>  " + this.getString(string.patient_id) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + PatientId + "</td> \n" +
                    "</tr>\n" +
                    '\n' +
                    "<tr >\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-user-circle-o\" aria-hidden=\"true\"></i><b>  " + this.getString(string.name) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + PatientName + "</td> \n" +
                    "</tr>\n" +
                    '\n' +
                    " \n" +
                    '\n' +
                    "<tr > \n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-calendar-check-o\" aria-hidden=\"true\"></i><b>  " + this.getString(string.age) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:  " + age + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    "<tr >\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-mars-stroke\" aria-hidden=\"true\"></i><b>  " + this.getString(string.gender) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + gender + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    " \n" +
                    '\n' +
                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.blood_group) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + BloodGroup + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.address1) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + Address + "</td>\n" +
                    "</tr>\n" +
                    " \n" +
                    " \n" +
                    " <tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.address2) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + Address1 + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    '\n' +
                    '\n' +

                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.city) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + city + "</td>\n" +
                    "</tr>\n" +
                    '\n' +

                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.district_title) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + District + "</td>\n" +
                    "</tr>\n" +
                    '\n' +

                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.phone) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + phone + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    " <tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.caretaker) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + caretaker + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    " <tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + this.getString(string.caretaker_number) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + crtknum + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    "</tbody>\n" +
                    "</table>\n" +
                    '\n' +
                    "</div>";


            String Str2 = "<div class=\"bs-example\">\n" +
                    '\n' +
                    '\n' +
                    "<table style=\"width:100%\" class=\"table\">\n" +
                    "   \n" +
                    "<tbody>\n" +
                    '\n' +
                    '\n' +
                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-address-card-o\" aria-hidden=\"true\"></i><b> " + this.getString(string.ward) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + ward_values + "</td> \n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    '\n' +
                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-address-card\" aria-hidden=\"true\"></i><b>   " + this.getString(string.room_no) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + room_values + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-globe\" aria-hidden=\"true\"></i><b>  " + this.getString(string.bed_no) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + bed_values + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-globe\" aria-hidden=\"true\"></i><b>  " + this.getString(string.admitted_date) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + admitDate + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    '\n' +
                    "<tr>\n" +
                    "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-globe\" aria-hidden=\"true\"></i><b>  " + this.getString(string.admitted_time) + "</b></td> \n" +
                    "<td height=\"20\" style=\"color:#000000;\">:   " + admitTime + "</td>\n" +
                    "</tr>\n" +
                    '\n' +
                    " \n" +
                    " \n" +
                    '\n' +
                    "</tbody>\n" +
                    "</table>\n" +


                    '\n' +
                    "</div>";


            String ip_date = "-", ip_time = "-", ip_visiteddoctor = "-", ip_bps = "-", ip_bpd = "-", ip_pulse = "-", ip_temp = "-", ip_resp = "-", ip_spo2 = "-", ip_drugorder = "-",
                    ip_nursing = "-", ip_oral = "-", ip_fluid = "-", ip_rvles = "-", ip_motion = "-", ip_urin = "-";


            StringBuilder Str3 = new StringBuilder();

            //IP CHART
            Cursor c6 = db.rawQuery("select * from Inpatient_MainChart where substr(Actdate,1,10)='" + DateValue + "' and patid='" + this.COMMON_PATIENTID + "'  order by id desc;", null);

            if (c6 != null) {
                if (c6.moveToFirst()) {
                    do {


                        ip_date = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("doc_visit_date")));
                        ip_time = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("doc_visit_time")));
                        ip_visiteddoctor = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("docname")));
                        ip_bps = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("bp")));
                        ip_bpd = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("bpd")));
                        ip_pulse = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("pulse")));
                        ip_temp = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("temp")));
                        ip_resp = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("resp")));
                        ip_spo2 = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("spo2")));
                        ip_drugorder = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("drugorder")));
                        ip_nursing = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("nursing_instr")));
                        ip_oral = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("ip_oral")));
                        ip_fluid = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("ip_fluids")));
                        ip_rvles = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("op_rvies")));
                        ip_motion = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("op_motion")));
                        ip_urin = BaseConfig.CheckDBString(c6.getString(c6.getColumnIndex("op_urine")));


                        Str3.append(" <tr>\n" + "  <td>").append(ip_date).append("</td>\n").append("\t\t<td>").append(ip_time).append("</td>\n").append("\t\t<td>").append(ip_visiteddoctor).append("</td>\n").append("\t\t<td>").append(ip_bps).append("</td>\n").append("\t\t<td>").append(ip_bpd).append("</td>\n").append("\t\t<td>").append(ip_pulse).append("</td>\n").append("\t\t<td>").append(ip_temp).append("</td>\n").append("\t\t<td>").append(ip_resp).append("</td>\n").append("\t\t<td>").append(ip_spo2).append("</td>\n").append("\t\t<td>").append(ip_drugorder).append("</td>\n").append("\t\t<td>").append(ip_nursing).append("</td>\n").append("\t\t<td>").append(ip_oral).append("</td>\n").append("\t\t<td>").append(ip_fluid).append("</td>\n").append("\t\t<td>").append(ip_rvles).append("</td>\n").append("\t\t<td>").append(ip_motion).append("</td>\n").append("\t\t<td>").append(ip_urin).append("</td>\n").append("  </tr>\n").append(' ');


                    } while (c6.moveToNext());
                }
            }

            c6.close();


//-------------------------------


            StringBuilder Str4 = new StringBuilder();

            String diab_date = "-", diab_time = "-", diab_visiteddoctor = "-", diab_splinstr = "-", diab_urinesugar = "-",
                    diab_lente = "-", diab_insulin = "-", diab_bldsugar = "-", diab_ketone = "-";

            //DIABETIC
            Cursor c5 = db.rawQuery("select * from Inpatient_DiabeticChart where substr(Actdate,1,10)='" + DateValue + "' and patid='" + this.COMMON_PATIENTID + "'  order by id desc;", null);

            if (c5 != null) {
                if (c5.moveToFirst()) {
                    do {

                        diab_date = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("doc_visit_date")));
                        diab_time = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("doc_visit_time")));
                        diab_visiteddoctor = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("docname")));
                        diab_splinstr = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("spl_instr")));
                        diab_urinesugar = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("urine_sugar")));
                        diab_lente = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("lente")));
                        diab_insulin = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("insulin_plain")));
                        diab_bldsugar = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("blood_sugar")));
                        diab_ketone = BaseConfig.CheckDBString(c5.getString(c5.getColumnIndex("ketone_bodies")));

                        Str4.append("<tr>\n" + "\t\t<td>").append(diab_date).append("</td>\n").append("\t\t<td>").append(diab_time).append("</td>\n").append("\t\t<td>").append(diab_visiteddoctor).append("</td>\n").append("\t\t<td>").append(diab_splinstr).append("</td>\n").append("\t\t<td>").append(diab_urinesugar).append("</td>\n").append("\t\t<td>").append(diab_lente).append("</td>\n").append("\t\t<td>").append(diab_insulin).append("</td>\n").append("\t\t<td>").append(diab_bldsugar).append("</td>\n").append("\t\t<td>").append(diab_ketone).append("</td>\n").append("  </tr>");

                    } while (c5.moveToNext());
                }
            }

            c5.close();


//-------------------------------


            StringBuilder Str5 = new StringBuilder();

            String temperature_date = "-", temperature_time = "-", temperature_visitdoc = "-", temp_range = "-", temp_visit = "-", temp_takentime = "-";

            //TEMP
            Cursor c4 = db.rawQuery("select * from Inpatient_TemperatureChart where substr(Actdate,1,10)='" + DateValue + "' and patid='" + this.COMMON_PATIENTID + "'  order by id desc;", null);

            if (c4 != null) {
                if (c4.moveToFirst()) {
                    do {

                        temperature_date = BaseConfig.CheckDBString(c4.getString(c4.getColumnIndex("doc_visit_date")));
                        temperature_time = BaseConfig.CheckDBString(c4.getString(c4.getColumnIndex("doc_visit_time")));
                        temperature_visitdoc = BaseConfig.CheckDBString(c4.getString(c4.getColumnIndex("docname")));
                        temp_range = BaseConfig.CheckDBString(c4.getString(c4.getColumnIndex("temperature")));
                        temp_visit = BaseConfig.CheckDBString(c4.getString(c4.getColumnIndex("visitsummary")));
                        temp_takentime = BaseConfig.CheckDBString(c4.getString(c4.getColumnIndex("temptakentime")));


                        Str5.append("<tr>\n" + "\t\t<td>").append(temperature_date).append("</td>\n").append("\t\t<td>").append(temperature_time).append("</td>\n").append("\t\t<td>").append(temperature_visitdoc).append("</td>\n").append("\t\t<td>").append(temp_range).append("</td>\n").append("\t\t<td>").append(temp_visit).append("</td>\n").append("\t\t<td>").append(temp_takentime).append("</td>\n").append("  </tr>");

                    } while (c4.moveToNext());
                }
            }

            c4.close();


//-------------------------------

            StringBuilder Str6 = new StringBuilder();
            String mc_temp_date, mc_temp_time, mc_temp_visitdoctor, mc_clinicalnotes_tv, mc_treatmentdiet_tv;
            String value1[];
            //
            Cursor c3 = db.rawQuery("select * from InPatient_MedicalCaseRecords where  pat_id='" + this.COMMON_PATIENTID + "' and substr(ActDate,1,10)='" + DateValue + "'   order by id desc;", null);

            if (c3 != null) {
                if (c3.moveToFirst()) {
                    do {


                        String valueStr1 = "";
                        String valueStr2 = "";

                        String vl = c3.getString(c3.getColumnIndex("date")).trim();


                        if (vl.contains(" ")) {


                            try {

                                value1 = vl.split(" ");
                                valueStr1 = value1[0];
                                valueStr2 = value1[1];

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }

                        mc_temp_date = BaseConfig.CheckDBString(valueStr1);
                        mc_temp_time = BaseConfig.CheckDBString(valueStr2);
                        mc_temp_visitdoctor = BaseConfig.CheckDBString(c3.getString(c3.getColumnIndex("referred_by")));
                        mc_clinicalnotes_tv = BaseConfig.CheckDBString(c3.getString(c3.getColumnIndex("clinical_notes")));
                        mc_treatmentdiet_tv = BaseConfig.CheckDBString(c3.getString(c3.getColumnIndex("treatment_diet")));

                        Str6.append("<tr>\n" + "\t\t<td>").append(mc_temp_date).append("</td>\n").append("\t\t<td>").append(mc_temp_time).append("</td>\n").append("\t\t<td>").append(mc_temp_visitdoctor).append("</td> \n").append("\t\t\n").append("\t\t<td>").append(mc_clinicalnotes_tv).append("</td>\n").append("\t\t<td>").append(mc_treatmentdiet_tv).append("</td>\n").append("  </tr>");


                    } while (c3.moveToNext());
                }
            }

            c3.close();


//-------------------------------


            String[] Tabledata;
            StringBuilder Medicine_List = new StringBuilder();

            String Prescription_Date = DateValue.replace("/","-");

            String Prescription_Query ="select * from Mprescribed where  Ptid='" + this.COMMON_PATIENTID + "' and substr(Actdate,1,10)='"+Prescription_Date+"' order by Medid desc;";

            Cursor c2 = db.rawQuery(Prescription_Query, null);

            if (c2 != null) {
                if (c2.moveToFirst()) {
                    do {

                        Tabledata = c2.getString(c2.getColumnIndex("medicinename")).split("_");

                        Medicine_List.append("  <tr>\n" + "                  <th><font color=\"#000\">").append(Tabledata[0]).append("</font></th>\n \n").append("                    <th><font color=\"#000\">").append(Tabledata[1]).append("</font></th>\n \n").append("               \t <th><font color=\"#000\">").append(Tabledata[2]).append("</font></th>\n \n").append("              \t <th><font color=\"#000\">").append(Tabledata[3]).append("</font></th>\n\n").append("                 </tr>\n");

                    } while (c2.moveToNext());
                }
            }

            c2.close();


//-------------------------------


            String surgery_date = "", pre_operativediag = "-", operative_notes = "-", position = "-", procedure = "-", closure = "-", post_operativediag = "-", surgeon = "-", anaesthetist = "-", asst = "-", bloodloss = "-", histopathological = "-", post_op_instruct = "-";

            //Medical Case record
            Cursor c1 = db.rawQuery("select * from Inpatient_SurgeryRecord where substr(Actdate,1,10)='" + DateValue + "' and patid='" + this.COMMON_PATIENTID + "' order by id desc  limit 1 ;", null);

            if (c1 != null) {
                if (c1.moveToFirst()) {
                    do {

                        surgery_date = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("Actdate"))).split(" ")[0];
                        pre_operativediag = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("pre_operativediag")));
                        operative_notes = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("operative_notes")));
                        position = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("position")));
                        procedure = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("procedure")));
                        closure = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("closure")));
                        post_operativediag = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("post_operativediag")));
                        surgeon = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("surgeon")));
                        anaesthetist = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("anaesthetist")));
                        asst = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("asst")));
                        bloodloss = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("bloodloss")));
                        histopathological = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("histopathological")));
                        post_op_instruct = BaseConfig.CheckDBString(c1.getString(c1.getColumnIndex("post_op_instruct")));

                    } while (c1.moveToNext());
                }
            }

            c1.close();


//-------------------------------


            //Update html full code below
            String value2 = "<!DOCTYPE html>\n" + "\n" + "<html lang=\"en\">\n" + "<head>\n" + "\n" + "<script type=\"text/javascript\">\n" + "    function showAndroidToast(toast) {\n" + "        android.showToast(toast);\n" + "    }\n" + "</script>\n" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" + "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" + "\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" + "\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" + "\n" + "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" + "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" + "\n" + "\n" + "\n" + "\n" + "</head>\n" + "<body>  \n" + " \n" + "  \n" + "<font class=\"sub\">  \n" + "\n" + "<i class=\"fa fa-user-circle-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.patientdetails) + "</font>\n" + "\n" + "" + Str1 + "\n" + "\n" + "\n" + "<br/>\n" + "<!--------------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.admission_details) + "</font>\n" + "\n" + "\n" + Str2 + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "<br/>\n" + "<!----------------------------------------------------------------->\n" + " <!--------------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.i_pchart) + "</font>\n" + "\n" + "\n" + "\n" + "\n" + "<div class=\"table-responsive\">  \n" + "\n" + "        \n" + "<table class=\"table table-bordered\">\n" + "  <tr>\n" + "  \n" + "  \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.date) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.time) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.visited_doctor) + "</font></th>\n" + "\t \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">BPS</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">BPD</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Pulse</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Temp</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Resp</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">SPO2</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.drug_order) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.nursing_instrutions) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Input Oral</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Input Fluids</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Output Rvles</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Output Motion</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Output Urine</font></th>\n" + "\t \n" + "  </tr>\n" + "  \n" + " " + Str3 + "\n" + "</table>\n" + "</div>\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "<br/>\n" + "<!----------------------------------------------------------------->\n" + " <!--------------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.diabetic_chart) + "</font>\n" + "\n" + "\n" + "\n" + "\n" + "<div class=\"table-responsive\">          \n" + "<table class=\"table table-bordered\">\n" + "  <tr>\n" + "  \n" + "  \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.date) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.time) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.visited_doctor) + "</font></th>\n" + "\t   \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.special_instruction) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.urine_sugar) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.lente) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.insulin_plain) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.blood_sugar) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.ketone_bodies) + "</font></th> \n" + "  </tr>\n" + "  \n" + "  \n" + "  \n" + "  " + Str4 + "\n" + " \n" + "</table>\n" + "</div>\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "<br/>\n" + "<!----------------------------------------------------------------->\n" + " <!--------------------------------------------------------------------->\n" + "\n" + " \n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.temperature_chart) + "</font>\n" + "\n" + "\n" + "\n" + "<div class=\"table-responsive\">          \n" + "<table class=\"table table-bordered\">\n" + "  <tr>\n" + "  \n" + "  \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.date) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.time) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.visited_doctor) + "</font></th>\n" + "\t \n" + "\t \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.temperature_range) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.visitsummary) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.taken_time) + "</font></th> \n" + "  </tr>\n" + "  \n" + "  " + Str5 + "\n" + " \n" + "</table>\n" + "</div>\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "<br/>\n" + "<!----------------------------------------------------------------->\n" + " <!--------------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.medical_case_record) + "</font>\n" + "\n" + "\n" + "\n" + "<div class=\"table-responsive\">          \n" + "<table class=\"table table-bordered\">\n" + "  <tr>\n" + "  \n" + "  \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.date) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.time) + "</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.visited_doctor) + "</font></th>\n" + "\t \n" + "  \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.clinical_notes) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.treatment_and_diet) + "</font></th>\n" + "\t \n" + "  </tr>\n" + "  \n" + "  " + Str6 + "\n" + " \n" + "</table>\n" + "</div>\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "<br/>\n" + "<!----------------------------------------------------------------->\n" + " <!--------------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.medical_prescription) + "</font>\n" + "\n" + "\n" + "<div class=\"bs-example\"> \n" + "\n" + "\n" + "<div class=\"table-responsive\">          \n" + "<table class=\"table table-bordered\">\n" + "  <tr>\n" + "  \n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.medicinename) + "</font></th>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.interval) + "</font></th>\n" + "\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.frequency) + "</font></th>\n" + "\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.duration) + "</font></th>\n" + "\t \n" + "  </tr>\n" + "  \n" + "  " + Medicine_List + "\n" + " \n" + "</table>\n" + "</div>\n" + "\n" + "</div>\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "<br/>\n" + "<!----------------------------------------------------------------->\n" + " <!--------------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.surgery_record) + "</font>\n" + "\n" + "\n" + "<div class=\"bs-example\"> \n" + " \n" + "\n" + "\n" + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + "\n" + "<td height=\"20\" style=\"color:#fff; background:#777575\"> <b>  " + this.getString(string.notes) + "</b></td>\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"><b> " + this.getString(string.date) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + surgery_date + "</td> \n" + "</tr>\n" + "\n" + "\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"><b>   " + this.getString(string.pre_operative_diagnosis) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + pre_operativediag + "</td>\n" + "</tr>\n" + "\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"><b>  " + this.getString(string.operativenotes) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + operative_notes + "</td>\n" + "</tr>\n" + "\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"><b>  " + this.getString(string.position) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + position + "</td>\n" + "</tr>\n" + "\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"><b>  " + this.getString(string.procedure) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + procedure + "</td>\n" + "</tr>\n" + "\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"><b>  " + this.getString(string.closure) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + closure + "</td>\n" + "</tr>\n" + "\n" + " \n" + " <tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"> <b>  " + this.getString(string.post_operative_diagnosis) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + post_operativediag + "</td>\n" + "</tr>\n" + " \n" + " \n" + "<td height=\"20\" style=\"color:#fff; background:#777575\"> <b>  " + this.getString(string.treatment) + "</b></td>\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"> <b>  " + this.getString(string.surgeon) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + surgeon + "</td>\n" + "</tr>\n" + "\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"> <b>  " + this.getString(string.anaesthetist) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + anaesthetist + "</td>\n" + "</tr>\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"> <b>  Asst</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + asst + "</td>\n" + "</tr>\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"> <b>  " + this.getString(string.bloodloss) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + bloodloss + "</td>\n" + "</tr>\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"> <b>  " + this.getString(string.histo_pathological_diagnosis) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + histopathological + "</td>\n" + "</tr>\n" + "\n" + "<tr>\n" + "<td height=\"20\" style=\"color:#3d5987;\"> <b>  " + this.getString(string.post_operative_instruction) + "</b></td> \n" + "<td height=\"20\" style=\"color:#000000;\">:   " + post_op_instruct + "</td>\n" + "</tr>\n" + "</tbody>\n" + "</table>\n" + "\n" + "</div>\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "<!----------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-bar-chart fa-2x\" aria-hidden=\"true\"></i> Bar chart</font>\n" + "<div class=\"table-responsive\">  \n" + "<table class=\"table table-bordered\">\n" + "  <tr>\n" + "  \n" + "  \n" + "    <th onClick=\"showAndroidToast('1')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.inpatient_chart) + "</font></th>\n" + "    <th onClick=\"showAndroidToast('2')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.diabetic_chart) + "</font></th>\n" + "\t </tr>\n" + "\t \n" + "\t <tr>\n" + "\t <th onClick=\"showAndroidToast('3')\"  bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.temperature_chart) + "</font></th>\n" + "\t <th onClick=\"showAndroidToast('4')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.input_chart) + "</font></th> \n" + "\t</tr>\n" + "\t\n" + "\t<tr>\n" + "\t <th onClick=\"showAndroidToast('5')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.output_chart) + "</font></th> \n" + "\t</tr>\n" + "\t\n" + "</tbody>\n" + "</table>\n" + "</div>\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-line-chart fa-2x\" aria-hidden=\"true\"></i> Line chart</font>\n" + "<div class=\"table-responsive\">  \n" + "<table class=\"table table-bordered\">\n" + "  <tr>\n" + "  \n" + "  \n" + "    <th onClick=\"showAndroidToast('6')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.inpatient_chart) + "</font></th>\n" + "    <th onClick=\"showAndroidToast('7')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.diabetic_chart) + "</font></th>\n" + "\t </tr>\n" + "\t \n" + "\t <tr>\n" + "\t <th onClick=\"showAndroidToast('8')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.temperature_chart) + "</font></th>\n" + "\t <th onClick=\"showAndroidToast('9')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.input_chart) + "</font></th> \n" + "\t</tr>\n" + "\t\n" + "\t<tr>\n" + "\t <th onClick=\"showAndroidToast('10')\" bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.output_chart) + "</font></th> \n" + "\t</tr>\n" + "\t\n" + "</tbody>\n" + "</table>\n" + "</div>" + "<!----------------------------------------------------------------->\n" + "</body>\n" + "</html>";


            db.close();


            return value2;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public final void onBackPressed() {
        this.LoadBack();
    }

    //#######################################################################################################
    public final void selectDate(View view) {
        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(this.getFragmentManager(), "DatePicker");

    }

    private final void populateSetDate(int year, int month, int day) {

        //SeletedDate.setText(month + "/" + day + '/' + year);

    }

    @Nullable
    @Override
    protected final Dialog onCreateDialog(int id) {
        switch (id) {

            case Inpatient_Detailed_View.DATE_DIALOG_ID:
                return new DatePickerDialog(this, this.mDateSetListener, Inpatient_Detailed_View.mYear, Inpatient_Detailed_View.mMonth, Inpatient_Detailed_View.mDay);
        }
        return null;
    }

    protected final void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {

            case Inpatient_Detailed_View.DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(Inpatient_Detailed_View.mYear, Inpatient_Detailed_View.mMonth, Inpatient_Detailed_View.mDay);

                break;
        }
    }

    private void updateDisplay() {

        Calendar c1 = Calendar.getInstance();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);

        String strdate1 = sdf1.format(c1.getTime());

        Date got;

        try {
            got = dateFormat.parse(Inpatient_Detailed_View.mDay + "/" + (Inpatient_Detailed_View.mMonth + 1) + '/' + Inpatient_Detailed_View.mYear);

            int result = dateFormat.parse(strdate1).compareTo(got);

           /* SeletedDate.setText(new StringBuilder()
                    .append(mDay).append('/').append(mMonth + 1)
                    .append('/').append(mYear).append(""));
*/



        } catch (Exception e) {

        }

    }

    // #######################################################################################################

    private final void ShowDialog() {


        CharSequence[] items =
                {
                        this.getString(string.search),
                        this.getString(string.add_inpatient_details_txt),
                        this.getString(string.add_diet_details),
                        this.getString(string.clinical_informaiton),
                        this.getString(string.casenotes),
                        this.getString(string.investigation),
                        this.getString(string.prescription),
                        this.getString(string.close)
                };

        Builder builder = new Builder(this, style.MyAlertDialogStyle);
        builder.setTitle(this.getString(string.options));
        builder.setItems(items, (dialog, item) -> {


            if (items[item].toString().equalsIgnoreCase(this.getString(string.clinical_informaiton))) {

                Intent i = new Intent(this, ClinicalInformation.class);
                i.putExtra("FROM", "INPATIENT");
                i.putExtra("CONTINUE_STATUS", "True");
                i.putExtra("PASSING_PATIENT_ID", this.COMMON_PATIENTID);
                this.startActivity(i);
                this.finish();

            } else if (items[item].toString().equalsIgnoreCase(this.getString(string.casenotes))) {
                Intent i = new Intent(this, CaseNotes.class);
                i.putExtra("FROM", "INPATIENT");
                i.putExtra("CONTINUE_STATUS", "True");
                i.putExtra("PASSING_TREATMENTFOR", "");
                i.putExtra("PASSING_DIAGNOSIS", "");
                i.putExtra("PASSING_PATIENT_ID", this.COMMON_PATIENTID);
                this.startActivity(i);
                this.finish();

            } else if (items[item].toString().equalsIgnoreCase(this.getString(string.investigation))) {

                Intent i = new Intent(this, Investigations.class);
                i.putExtra("FROM", "INPATIENT");
                i.putExtra("CONTINUE_STATUS", "True");
                i.putExtra("PASSING_TREATMENTFOR", "");
                i.putExtra("PASSING_DIAGNOSIS", "");
                i.putExtra("PASSING_PATIENT_ID", this.COMMON_PATIENTID);
                this.startActivity(i);
                this.finish();

            } else if (items[item].toString().equalsIgnoreCase(this.getString(string.prescription))) {

                Intent i = new Intent(this, MedicinePrescription.class);
                i.putExtra("FROM", "INPATIENT");
                i.putExtra("CONTINUE_STATUS", "True");
                i.putExtra("PASSING_TREATMENTFOR", "");
                i.putExtra("PASSING_DIAGNOSIS", "");
                i.putExtra("PASSING_PATIENT_ID", this.COMMON_PATIENTID);
                this.startActivity(i);
                this.finish();

            }
            else if (items[item].toString().equalsIgnoreCase(this.getString(string.add_inpatient_details_txt))) {


                Intent intent = new Intent(this, Inpatient_Inputs.class);
                Bundle b = new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                intent.putExtras(b);
                this.startActivityForResult(intent, Inpatient_Detailed_View.LoadBack_ACTIVITY);


            } else if (items[item].toString().equalsIgnoreCase(this.getString(string.add_diet_details))) {

                Intent i = new Intent(this, AddDietActivity.class);
                Bundle b = new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, this.Bundle_Patient_Id);
                i.putExtras(b);
                this.startActivity(i);

            }else if (items[item].toString().equalsIgnoreCase(this.getString(string.search))) {

                this.LoadSearch();

            }else if (items[item].toString().equalsIgnoreCase(this.getString(string.close))) {


            }


        });
        builder.create().getWindow().getAttributes().windowAnimations = style.DialogAnimation;
        builder.create().show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
            this.dateFormatter = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
            try {
                this.LoadWebview(sdf.format(new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadSearch() {

        Calendar newCalendar = Calendar.getInstance();
        this.fromDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            //Toast.makeText(this, ""+ dateFormatter.format(newDate.getTime()), Toast.LENGTH_SHORT).show();

            String Date = this.dateFormatter.format(newDate.getTime());
            this.LoadWebview(Date);


        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        this.fromDatePickerDialog.show();

    }

    private final OnDateChangedListener dateChangedListener =
            new OnDateChangedListener(){
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    Toast.makeText(Inpatient_Detailed_View.this, "picked date is " + datePicker.getDayOfMonth() +
                            " / " + (datePicker.getMonth()+1) +
                            " / " + datePicker.getYear(), Toast.LENGTH_SHORT).show();
                }
            };

    private static class MyWebChromeClient extends WebChromeClient {
    }

    class WebAppInterface {
        final Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            this.mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public final void showToast(String id) {//Blood report

            Inpatient_Detailed_View.this.LoadingChart(null, Integer.parseInt(id));

        }


    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("ValidFragment")
    public class SelectDateFragment extends DialogFragment implements OnDateSetListener {
        public SelectDateFragment() {
        }

        @Override
        public final Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(this.getActivity(), this, yy, mm, dd);
        }

        public final void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            Inpatient_Detailed_View.this.populateSetDate(i, i1 + 1, i2);

        }


    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /*
     <3<3        <3<3    <3<3         <3<3       <3<3                 <3<3         <3<3<3<3          <3<3<3<3<3<3<
     <3<3       <3<3     <3<3         <3<3       <3<3<3              3<3<3        <3<3<3<3<3         <3<3<3<3<3<3<3
     <3<3      <3<3      <3<3         <3<3       <3<3<3<3           <3<3<3       <3<3    <3<3        <3<3       <3<3
     <3<3    <3<3        <3<3         <3<3       <3<3  <3<3       <3<3<3<3      <3<3      <3<3       <3<3       <3<3
     <3<3<3<3<3          <3<3         <3<3       <3<3    <3<3   <3<3  <3<3     <3<3        <3<3      <3<3<3<3<3<3
     <3<3    <3<3        <3<3         <3<3       <3<3      3<3<3<3    <3<3    <3<3<3<3<3<3<3<3<3     <3<3<3<3<3<3<3
     <3<3      <3<3      <3<3         <3<3       <3<3        <3<3     <3<3    <3<3          <3<3     <3<3        <3<3
     <3<3       <3<3     <3<3         <3<3       <3<3        <3<3     <3<3    <3<3          <3<3     <3<3         <3<3
     <3<3        <3<3       <3<3<3<3<3<3         <3<3        <3<3     <3<3    <3<3          <3<3     <3<3         <3<3
<3*/
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}