package kdmc_kumar.MyPatient_Module;

import android.R.anim;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.CaseNotes_Modules.CaseNotes;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.ClinicalInformation;
import kdmc_kumar.Core_Modules.Investigations;
import kdmc_kumar.Core_Modules.MedicinePrescription;
import kdmc_kumar.Core_Modules.MyPatient;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;

public class MyPatientDrawer extends AppCompatActivity implements OnNavigationItemSelectedListener {


    //Main layout
    @BindView(id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(id.toolbar_mypatient)
    Toolbar toolbarMypatient;
    @BindView(id.txvw_title)
    TextView txvwTitle;
    @BindView(id.back_mp)
    AppCompatImageView backMp;
    @BindView(id.home_mp)
    AppCompatImageView homeMp;
    @BindView(id.exit_mp)
    AppCompatImageView exitMp;
    @BindView(id.nav_view)
    NavigationView navView;

    //frame-layout
    @BindView(id.frame)
    FrameLayout frame;
    @BindView(id.back_drop)
    View backDrop;
    @BindView(id.lyt_inpatient)
    LinearLayout lytInpatient;
    @BindView(id.fab_admitinpatient)
    FloatingActionButton fabAdmitinpatient;
    @BindView(id.lyt_clinicalinfo)
    LinearLayout lytClinicalinfo;
    @BindView(id.fab_clinicalinfo)
    FloatingActionButton fabClinicalinfo;
    @BindView(id.lyt_casenotes)
    LinearLayout lytCasenotes;
    @BindView(id.fab_casenote)
    FloatingActionButton fabCasenote;
    @BindView(id.lyt_investigation)
    LinearLayout lytInvestigation;
    @BindView(id.fab_investigation)
    FloatingActionButton fabInvestigation;
    @BindView(id.lyt_Prescription)
    LinearLayout lytPrescription;
    @BindView(id.fab_Prescription)
    FloatingActionButton fabPrescription;
    @BindView(id.fab_add)
    FloatingActionButton fabAdd;


    //Drawer header
    ImageView profileImage;
    TextView patientName;
    TextView patientDetails;

    private Bundle b;
    private String BUNDLE_PATIENT_ID = "";
    private String FROM_TEST_REPORT = "False";
    private final CharSequence[] items;

    private boolean rotate;

    public MyPatientDrawer() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_my_patient_drawer);


        this.GETINITIALIZE();

        this.CONTROLLISTENERS();



    }


    public void GETINITIALIZE() {

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        this.b = this.getIntent().getExtras();

        if (this.b != null) {

            this.BUNDLE_PATIENT_ID = this.b.getString(BaseConfig.BUNDLE_PATIENT_ID);
            this.FROM_TEST_REPORT = this.b.getString("TEST_REPORT");
        }

        ViewAnimation.initShowOut(this.lytInpatient);
        ViewAnimation.initShowOut(this.lytClinicalinfo);
        ViewAnimation.initShowOut(this.lytCasenotes);
        ViewAnimation.initShowOut(this.lytInvestigation);
        ViewAnimation.initShowOut(this.lytPrescription);

        this.backDrop.setVisibility(View.GONE);



        View headerview= this.navView.getHeaderView(0);

        this.setPatientDetails(headerview);

        if ("True".equalsIgnoreCase(this.FROM_TEST_REPORT)) {

            Fragment fragment = new Profile_Investigation();
            Bundle args = new Bundle();
            args.putString(BaseConfig.BUNDLE_PATIENT_ID, this.BUNDLE_PATIENT_ID);
            fragment.setArguments(args);

            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(anim.fade_in, anim.fade_out);
            fragmentTransaction.replace(id.frame, fragment, "1");
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            Fragment fragment = new Profile_Patient();
            Bundle args = new Bundle();
            args.putString(BaseConfig.BUNDLE_PATIENT_ID, this.BUNDLE_PATIENT_ID);
            fragment.setArguments(args);

            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(anim.fade_in, anim.fade_out);
            fragmentTransaction.replace(id.frame, fragment, "1");
            fragmentTransaction.commitAllowingStateLoss();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbarMypatient, string.navigation_drawer_open, string.navigation_drawer_close);
        this.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        this.navView.setItemIconTintList(null);
        this.navView.setNavigationItemSelectedListener(this);

        boolean checkadmitreqstatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where enable_inpatient= '1' and Patid='" + this.BUNDLE_PATIENT_ID.trim() + '\'');

        if (checkadmitreqstatus) {
            this.lytInpatient.setVisibility(View.GONE);
        } else {
            this.lytInpatient.setVisibility(View.VISIBLE);
        }


    }


    public void CONTROLLISTENERS() {


        this.backDrop.setOnClickListener(v -> this.toggleFabMode(this.fabAdd));

        this.homeMp.setOnClickListener(v -> {

            finish();
            Intent intent1 = new Intent(this, Dashboard_NavigationMenu.class);
            this.startActivity(intent1);
        });

        this.exitMp.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.backMp.setOnClickListener(view -> this.LoadBack());

        this.fabAdd.setOnClickListener(this::toggleFabMode);

        this.fabAdmitinpatient.setOnClickListener(view -> this.ShowRequestDialog());

        this.fabClinicalinfo.setOnClickListener(view -> {

            Intent i = new Intent(this, ClinicalInformation.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_PATIENT_ID", this.BUNDLE_PATIENT_ID);
            this.startActivity(i);
            this.finish();
        });

        this.fabCasenote.setOnClickListener(view -> {
            Intent i = new Intent(this, CaseNotes.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_TREATMENTFOR", "");
            i.putExtra("PASSING_DIAGNOSIS", "");
            i.putExtra("PASSING_PATIENT_ID", this.BUNDLE_PATIENT_ID);
            this.startActivity(i);
            this.finish();

        });

        this.fabInvestigation.setOnClickListener(view -> {

            Intent i = new Intent(this, Investigations.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_TREATMENTFOR", "");
            i.putExtra("PASSING_DIAGNOSIS", "");
            i.putExtra("PASSING_PATIENT_ID", this.BUNDLE_PATIENT_ID);
            this.startActivity(i);
            this.finish();
        });

        this.fabPrescription.setOnClickListener(view -> {
            Intent i = new Intent(this, MedicinePrescription.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_TREATMENTFOR", "");
            i.putExtra("PASSING_DIAGNOSIS", "");
            i.putExtra("PASSING_PATIENT_ID", this.BUNDLE_PATIENT_ID);
            this.startActivity(i);
            this.finish();
        });


    }


    private final void LoadBack() {

        if ("True".equalsIgnoreCase(this.FROM_TEST_REPORT)) {
            BaseConfig.globalStartIntent(this, MyPatient.class, null);
        }else
        {
            finish();
        }

    }


    public final void onBackPressed() {
        DrawerLayout drawer = this.findViewById(id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        this.LoadBack();
    }


    private void toggleFabMode(View v) {
        this.rotate = ViewAnimation.rotateFab(v, !this.rotate);
        if (this.rotate) {
            ViewAnimation.showIn(this.lytInpatient);
            ViewAnimation.showIn(this.lytClinicalinfo);
            ViewAnimation.showIn(this.lytCasenotes);
            ViewAnimation.showIn(this.lytInvestigation);
            ViewAnimation.showIn(this.lytPrescription);
            this.backDrop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(this.lytInpatient);
            ViewAnimation.showOut(this.lytClinicalinfo);
            ViewAnimation.showOut(this.lytCasenotes);
            ViewAnimation.showOut(this.lytInvestigation);
            ViewAnimation.showOut(this.lytPrescription);
            this.backDrop.setVisibility(View.GONE);
        }
    }


    @Override
    public final boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Bundle args;

        switch (id) {

            case id.patient_profile:
                // TODO: 4/1/2017 change Patient  Profile Muthukumar Sir

                fragment = new Profile_Patient();

                break;

            case id.clinical_information:

                // TODO: 3/31/2017 Change New Web Page Ponnusamy M
                fragment = new Profile_ClinicalInformation();

                break;

            case id.casenotes:

                // TODO: 4/1/2017 Change  New Web page Mutu Kumar sir
//            fragment = new CasenotesWeb();
                fragment = new Profile_Casenotes();

                break;

            case id.investigation:
                fragment = new Profile_Investigation();

                break;
            case id.previous_precription:

                fragment = new Profile_Prescription();

                break;

            case id.reports:

                fragment = new Patient_Reports();

                break;

            case id.immunization:

                fragment = new Patient_Immunization();


                break;
            case id.inpatient_summary:
                fragment = new InpatientSummary_Profile();


                break;


            case id.pdf_summary:
                fragment = new PDFReport_Profile();
                break;


        }


        args = new Bundle();
        args.putString(BaseConfig.BUNDLE_PATIENT_ID, this.BUNDLE_PATIENT_ID);
        fragment.setArguments(args);

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(anim.fade_in, anim.fade_out);
        fragmentTransaction.replace(id.frame, fragment, "1");
        fragmentTransaction.commitAllowingStateLoss();
        DrawerLayout drawer = this.findViewById(id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    private final void setPatientDetails(View view) {

        this.profileImage = view.findViewById(id.profile_image_header);
        this.patientName = view.findViewById(id.patient_name_header);
        this.patientDetails = view.findViewById(id.patient_details_header);


        String Patient_Name1 = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');
        String Patient_AgeGender1 = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');
        String Patient_Photo1 = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');

        if (Patient_Photo1.length() > 1) {
            BaseConfig.Glide_LoadImageView(this.profileImage, Patient_Photo1);


        } else {

            BaseConfig.Glide_LoadDefaultImageView(this.profileImage);
        }


        this.patientName.setText(Patient_Name1 + '-' + this.BUNDLE_PATIENT_ID);
        this.patientDetails.setText(String.valueOf(Patient_AgeGender1));

    }

    private final void ShowRequestDialog() {


        try {
            if (BaseConfig.doctorId == null || BaseConfig.doctorId.equalsIgnoreCase("")) {
            //    BaseConfig.LoadDoctorValues();
            }


            View view = this.getLayoutInflater().inflate(layout.inpatient_request_layout, null);
            AutoCompleteTextView patientAuto = view.findViewById(id.patient_detail_values);
            patientAuto.setThreshold(1);

            patientAuto.setText(this.BUNDLE_PATIENT_ID.trim());
            patientAuto.setFocusableInTouchMode(false);

            EditText remarksEdt = view.findViewById(id.request_remarks_edt);
            Button submitBtn = view.findViewById(id.submit);
            Button cancelBtn = view.findViewById(id.cancel);
            Spinner WardCategory = view.findViewById(id.ward_category);

            TextView Title_PatientName, Title_WarCategory, Title_Remarks;
            Title_PatientName = view.findViewById(id.txtvw_patname);
            Title_WarCategory = view.findViewById(id.txtvw_wardcategory);
            Title_Remarks = view.findViewById(id.txtvw_remarks);

            String first2 = "Patient Id";
            String next2 = "<font color='#EE0000'><b>*</b></font>";
            Title_PatientName.setText(Html.fromHtml(first2 + next2));

            String first3 = "Choose ward category";
            String next3 = "<font color='#EE0000'><b>*</b></font>";
            Title_WarCategory.setText(Html.fromHtml(first3 + next3));

            String first4 = "Request Remarks";
            String next4 = "<font color='#EE0000'><b>*</b></font>";
            Title_Remarks.setText(Html.fromHtml(first4 + next4));

            BaseConfig.LoadValuesSpinner(WardCategory, this, "select distinct WardCatName as dvalue from Mstr_WardCategory where HID='" + BaseConfig.HID + "' order by ServerId;", "select ward category");


            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view);
            submitBtn.setOnClickListener(v -> {

                if (Validation1.hasText(patientAuto)) {
                    if (WardCategory.getSelectedItemPosition() > 0) {
                        if (this.BUNDLE_PATIENT_ID.equalsIgnoreCase("")) {
                            Toast.makeText(this, string.valid_patient_details, Toast.LENGTH_LONG).show();
                        } else {

                            if (Validation1.hasText(remarksEdt)) {

                                //String patId = patientAuto.getText().toString().split("-")[1];
                                String selectedWard = WardCategory.getSelectedItem().toString();

                                ContentValues values = new ContentValues();
                                values.put("patid", this.BUNDLE_PATIENT_ID.trim());
                                values.put("request_remarks", remarksEdt.getText().toString());
                                values.put("WardCategory", selectedWard);
                                values.put("WardCategoryId", BaseConfig.GetValues("select ServerId as ret_values from Mstr_WardCategory where WardCatName='" + selectedWard + "' and HID='" + BaseConfig.HID + "'"));
                                values.put("IsActive", "1");
                                values.put("IsUpdate", "0");
                                values.put("docid", BaseConfig.doctorId);
                                values.put("ActDate", BaseConfig.DeviceDate());

                                SQLiteDatabase Db = BaseConfig.GetDb();//);
                                Db.insert("inpatient_request", null, values);
                                dialog.dismiss();

                                this.ShowSuccessFullyPlacedRequest(submitBtn);

                            } else {
                                BaseConfig.SnackBar(submitBtn.getContext(), "Enter remarks", submitBtn, 2);
                            }

                        }
                    } else {
                        BaseConfig.SnackBar(submitBtn.getContext(), this.getString(string.choose_ward), submitBtn, 2);
                    }
                } else {
                    BaseConfig.SnackBar(submitBtn.getContext(), this.getString(string.pl_select_patient), submitBtn, 2);
                }


            });
            cancelBtn.setOnClickListener(v -> dialog.dismiss());

            //dialog.getWindow().setLayout(350, 500);
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            dialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

            dialog.show();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    private final void ShowSuccessFullyPlacedRequest(View v) {

        new CustomKDMCDialog(v.getContext())
                .setLayoutColor(color.green_500).setNegativeButtonVisible(View.GONE)
                .setImage(drawable.ic_success_done)
                .setTitle(v.getContext().getString(string.title_inpatient))
                .setDescription(v.getContext().getString(string.request_placed))
                .setPossitiveButtonTitle(v.getContext().getString(string.ok));


    }


}//End