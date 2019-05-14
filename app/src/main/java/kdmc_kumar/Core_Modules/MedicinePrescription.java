package kdmc_kumar.Core_Modules;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.joda.time.format.PeriodFormatterBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.array;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import displ.mobydocmarathi.com.R.style;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.InjectionGetSet;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MedicineGetSet;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Core_Modules.MedicinePrescription.MedicineRecylerAdapter.MyViewHolder;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.InputFilterMinMax;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.asyn.AsynkTaskCustom;
import kdmc_kumar.Utilities_Others.asyn.onWriteCode;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.controller.api.GeneratePDFNode;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.controller.api.GeneratePDFNodeFactory;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.GetPDFNodeJsRequest;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.JsonValue;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.PDFNodeJsResult;

public class MedicinePrescription extends AppCompatActivity {

    private static int mcYear;
    private static int mcMonth;
    private static int mcDay;
    private static int mYear;
    private static int mMonth;
    private static int mDay;
    private final List<String> Loadlist1 = new ArrayList<>();
    private final List<String> Loadlist2 = new ArrayList<>();
    private final List<String> Loadlist3 = new ArrayList<>();
    private final List<String> Loadlist4 = new ArrayList<>();
    private final String[] Medicine_Type_Array = {"TAB", "SYR", "OINT", "DROPS", "CAP", "INJ"};
    private final String[] Medicine_Intake_Session = {"After Food", "Before Food", "With Food"};
    private final String[] Medicine_Frequency = {"Morning", "Afternoon", "Evening", "Night"};
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final ArrayList<MedicineGetSet> medicineGetSets = new ArrayList<>();//
    private final List<String> list = new ArrayList<>();
    //endregion
    private final List<String> listcnt = new ArrayList<>();
    private final List<String> listtottabcount = new ArrayList<>();
    private final ArrayList<InjectionGetSet> injectionGetSets = new ArrayList<>();//
    private final StringBuilder sbM = new StringBuilder();
    //Declaration
    //Upper Layout
    @BindView(id.medicine_prescription_parent_layout)
    CoordinatorLayout _medicinePrescriptionParentLayout;
    @BindView(id.medicine_prescription_nesetedscrollview)
    NestedScrollView _medicinePrescriptionNesetedscrollview;
    @BindView(id.upperlayout)
    LinearLayout _upperlayout;
    @BindView(id.imgvw_patientphoto)
    CircleImageView _imgvwPatientphoto;
    @BindView(id.tvw_agegender)
    TextView _tvwAgegender;
    @BindView(id.txtvw_title_patientname)
    TextView _txtvwTitlePatientname;
    @BindView(id.autocomplete_patientname)
    AutoCompleteTextView _autocompletePatientname;
    @BindView(id.txtvw_treatmentfor)
    TextView _txtvwTreatmentfor;
    @BindView(id.multiauto_treatmentfor)
    MultiAutoCompleteTextView _multiautoTreatmentfor;
    @BindView(id.txtvw_diagnosis)
    TextView _txtvwDiagnosis;
    @BindView(id.multiauto_diagnosis)
    MultiAutoCompleteTextView _multiautoDiagnosis;
    @BindView(id.layout_prevmedhistory)
    LinearLayout PreviousMedicineLayout;
    //Medicine details
    @BindView(id.card_medicine_details)
    CardView _cardMedicineDetails;
    @BindView(id.txvw_medicine_details_title)
    TextView _txvwMedicineDetailsTitle;
    @BindView(id.imgvw_medicine_options)
    ImageView imgvw_medicine_options;
    @BindView(id.medicine_textinputlayout)
    TextView _medicineTextinputlayout;
    @BindView(id.txtview_visiting_title)
    TextView txtview_visiting_title;
    @BindView(id.autocomplete_medicine_name)
    AutoCompleteTextView _autocompleteMedicineName;
    @BindView(id.txtview_choose_frequency)
    TextView _txtviewChooseFrequency;
    @BindView(id.chkbx_checkall)
    CheckBox _chkbxCheckall;
    @BindView(id.chkbx_morning)
    CheckBox _chkbxMorning;
    @BindView(id.txtvw_morning)
    TextView _txtvwMorning;
    @BindView(id.edttxt_morning_frequency)
    EditText _edttxtMorningFrequency;
    @BindView(id.spinner_morning)
    Spinner _spinnerMorning;
    @BindView(id.chkbx_afternoon)
    CheckBox _chkbxAfternoon;
    @BindView(id.txtvw_afternoon)
    TextView _txtvwAfternoon;
    @BindView(id.edttxt_afternoon_frequency)
    EditText _edttxtAfternoonFrequency;
    @BindView(id.spinner_afternoon)
    Spinner _spinnerAfternoon;
    @BindView(id.chkbx_evening)
    CheckBox _chkbxEvening;
    @BindView(id.txtvw_evening)
    TextView _txtvwEvening;
    @BindView(id.edttxt_evening)
    EditText _edttxtEvening;
    @BindView(id.spinner_evening)
    Spinner _spinnerEvening;
    @BindView(id.chkbx_nite)
    CheckBox _chkbxNite;
    @BindView(id.textvw_nite)
    TextView _textvwNite;
    @BindView(id.edttxt_nite)
    EditText _edttxtNite;
    @BindView(id.spinner_nite)
    Spinner _spinnerNite;
    @BindView(id.layout_choose_intake)
    LinearLayout _layoutChooseIntake;
    @BindView(id.textvw_choose_intake_title)
    TextView _textvwChooseIntakeTitle;
    @BindView(id.radiogrp_intake_options)
    RadioGroup _radiogrpIntakeOptions;
    @BindView(id.radiobtn_after_food)
    RadioButton _radiobtnAfterFood;
    @BindView(id.radiobtn_before_food)
    RadioButton _radiobtnBeforeFood;
    @BindView(id.radiobtn_with_food)
    RadioButton _radiobtnWithFood;
    @BindView(id.layout_choose_duration)
    LinearLayout _layoutChooseDuration;
    @BindView(id.textvw_choose_duration_title)
    TextView _textvwChooseDurationTitle;
    @BindView(id.spinner_duration)
    Spinner _spinnerDuration;
    @BindView(id.button_add_medicine)
    Button _buttonAddMedicine;
    @BindView(id.recycler_medicine_list)
    RecyclerView _recyclerMedicineList;
    //Emergency
    @BindView(id.injection_layout)
    LinearLayout _injectionLayout;
    @BindView(id.autocmpltxt_injection)
    AutoCompleteTextView _autocmpltxtInjection;
    @BindView(id.edt_injecdosage)
    EditText _edtInjecdosage;
    @BindView(id.edt_injquantity)
    EditText _edtInjquantity;
    @BindView(id.btn_addinj)
    Button _btnAddinj;
    @BindView(id.injection_recycler)
    RecyclerView _injectionRecycler;
    @BindView(id.nebulization_layout)
    LinearLayout _nebulizationLayout;
    @BindView(id.chk_nrmsaline)
    CheckBox _chkNrmsaline;
    @BindView(id.chk_asthasaline)
    CheckBox _chkAsthasaline;
    @BindView(id.edt_dosage1)
    EditText _edtDosage1;
    @BindView(id.edt_duration1)
    EditText _edtDuration1;
    @BindView(id.edt_quantity1)
    EditText _edtQuantity1;
    @BindView(id.edt_dosage2)
    EditText _edtDosage2;
    @BindView(id.edt_duration2)
    EditText _edtDuration2;
    @BindView(id.edt_quantity2)
    EditText _edtQuantity2;
    @BindView(id.suturing_layout)
    LinearLayout _suturingLayout;
    @BindView(id.muliautcompltxt_suturing)
    MultiAutoCompleteTextView _muliautcompltxtSuturing;
    @BindView(id.plastering_layout)
    LinearLayout _plasteringLayout;
    @BindView(id.muliautcompltxt_plastering)
    MultiAutoCompleteTextView _muliautcompltxtPlastering;
    @BindView(id.chk_slab)
    CheckBox _chkSlab;
    @BindView(id.chk_cast)
    CheckBox _chkCast;
    @BindView(id.chkbx_injection_layout)
    CheckBox _chkInjection;
    @BindView(id.chkbx_nebu_layout)
    CheckBox _chkNebulization;
    @BindView(id.chkbx_suturing_layout)
    CheckBox _chkSuturing;
    @BindView(id.chkbx_plastering_layout)
    CheckBox _chkPlastering;
    //next visit info
    @BindView(id.edtxtvw_visitafter)
    EditText _edtxtvwVisitafter;
    @BindView(id.textView4)
    TextView _textView4;
    @BindView(id.edtxtvw_visitedon)
    EditText _edtxtvwVisitedon;
    @BindView(id.textView6)
    TextView _textView6;
    @BindView(id.edtxtvw_nextvisit)
    EditText _edtxtvwNextvisit;
    @BindView(id.textView2)
    TextView _textView2;
    @BindView(id.edtxtvw_remarks)
    EditText _edtxtvwRemarks;
    @BindView(id.textView1)
    TextView _textView1;
    //pharmacy details
    @BindView(id.autocomplete_pharmacy_name)
    AutoCompleteTextView _autocompletePharmacyName;
    @BindView(id.textView7)
    TextView _textView7;
    @BindView(id.autocomplete_pharmacy_address)
    AutoCompleteTextView _autocompletePharmacyAddress;
    @BindView(id.button_cancel_prescription)

    //button info
            Button _buttonCancelPrescription;
    @BindView(id.button_submit_prescription)
    Button _buttonSubmitPrescription;
    //Action Bar
    @BindView(id.toolbar)
    Toolbar toolbar;
    @BindView(id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title)
    TextView toolbarTitle;
    @BindView(id.toolbar_exit)
    AppCompatImageView toolbarExit;
    @BindView(id.txtvw_savemedicine_template)
    TextView SaveMedicineTemplate;
    String str_PatientName = "";
    String str_Gender = "";
    String str_Age = "";
    String str_Symptoms = "";
    String str_ProvisionalDiagnosis = "";
    StringBuilder MedicalPrescriped = new StringBuilder();
    //endregion
    List<String> advlist = new ArrayList<>();
    private String session1;
    private String session2;
    private String session3;
    private String session4;
    private String session_set1 = "M";
    private String session_set2 = "A";
    private String session_set3 = "E";
    private String session_set4 = "N";
    private String FLAG_MYPATIENT = "N/A";
    private String COMMON_PATIENT_ID = "-";
    private String COMMON_INVESTIGATIONID = "-";
    private String COMMON_DIAGNOSISID = "-";
    //region MEDICINE PRESCRIPTION - INITIALIZATION
    private SimpleDateFormat mSimpleDateFormat;
    private String Medicine_Type = "";
    private LinearLayoutManager mLinearLayoutManager;
    private MedicinePrescription.MedicineRecylerAdapter medicineRecylerAdapter;
    private MedicinePrescription.InjectionRecyclerAdapter injectionRecyclerAdapter;
    //endregion
    private String concatpersonalhistrystr = "";
    private boolean statusvalue;
    //**********************************************************************************************
    private String SymptomsValue;
    private ImageView NoDataFound;
    private WebView profile_webvw;
    private List<String> mypatientprevmedicinehistory_medid;
    private int pos;
    private String nxtdt = "";
    private String[] visiteddt;
    private ImageView next_btn;
    private ImageView pre_btn;
    private String refdocname = "";


    //endregion
    private TextView PatientNameId;


    //**********************************************************************************************
    //region MEDICINE PRESCRIPTION - CONTROLLISTENERS
    private Calendar now = Calendar.getInstance();
    private List<String> templates_list;

    public MedicinePrescription() {
    }

    //**********************************************************************************************

    private static final void LoadDeleteTempTest() {
        try {
            String CREATE_TABLE_TempTest = "Delete from TempTest;";
            BaseConfig.SaveData(CREATE_TABLE_TempTest);
        } catch (Exception ignored) {

        }
    }

    private static String GetMedicinePrescriptionId() {
        String did = null;
        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select prescnum from presc ;", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    did = "MPID/" + MedicinePrescription.mcYear + '/' + MedicinePrescription.mcDay + MedicinePrescription.mcMonth + '/' + BaseConfig.doctorId + '/' + UUID.randomUUID().toString().split("-")[1] + '/' + c.getString(c.getColumnIndex("prescnum"));

                } while (c.moveToNext());
            }
        }
        Objects.requireNonNull(c).close();
        db.close();
        return did;
    }

    private static final void LoadDrWorkingDays() {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db
                .rawQuery(
                        "select distinct workingdays,prefpharmacy,pharmdetails from drsettings order by workingdays;",
                        null);

        String[] countryname = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    countryname = c.getString(c.getColumnIndex("workingdays"))
                            .split(",");
                    //Log.e("Working Days Count", "" + countryname.length);

                    for (String aCountryname : countryname) {

                        switch (aCountryname) {
                            case "Monday":
                                BaseConfig.workingdays_mon = aCountryname;
                                break;
                            // //Monday,Tuesday,Wednesday,Thursday,Friday
                            case "Tuesday":
                                BaseConfig.workingdays_tue = aCountryname;
                                break;
                            case "Wednesday":
                                BaseConfig.workingdays_wed = aCountryname;
                                break;
                            case "Thursday":
                                BaseConfig.workingdays_thur = aCountryname;
                                break;
                            case "Friday":
                                BaseConfig.workingdays_fri = aCountryname;
                                break;
                            case "Saturday":

                                BaseConfig.workingdays_sat = aCountryname;
                                break;
                            case "Sunday":
                                BaseConfig.workingdays_sun = aCountryname;
                                break;
                            default:
                                break;
                        }

                    }

                } while (c.moveToNext());
            }
        }

        db.close();
        Objects.requireNonNull(c).close();
    }

    /**
     * Muthukumar N
     * Android Developer
     * 20-04-2018
     */
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(layout.kdmc_layout_medicineprescription);

        try {
            this.GETINITIALIZATION();
            this.CONTROLLISTENERS();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //**********************************************************************************************

    private void GETINITIALIZATION() {

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.welcometoast = 0;

        BaseConfig.Glide_LoadDefaultImageView(this._imgvwPatientphoto);

       // BaseConfig.LoadDoctorValues();

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.prescription)));

        this.setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

      //  toolbar.setBackgroundColor(Color.parseColor(MedicinePrescription.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));

        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarBack.setOnClickListener(view -> this.LoadBack());

        String firstt = this.getString(string.symptoms);
        String nextt = "<font color='#EE0000'><b>*</b></font>";
        this._txtvwTreatmentfor.setText(Html.fromHtml(firstt + nextt));

        String firstd = this.getString(string.provisional_diagnosis);
        String nextd = "<font color='#EE0000'><b>*</b></font>";
        this._txtvwDiagnosis.setText(Html.fromHtml(firstd + nextd));

        String first = this.getString(string.pat_name);
        String next = "<font color='#EE0000'><b>*</b></font>";
        this._txtvwTitlePatientname.setText(Html.fromHtml(first + next));

        String first1 = this.getString(string.vist_after_days);
        String next1 = "<font color='#EE0000'><b></b></font>";
        this.txtview_visiting_title.setText(Html.fromHtml(first1 + next1));


        String first3 = this.getString(string.med_details);
        String next3 = "<font color='#EE0000'><b>*</b></font>";
        this._txvwMedicineDetailsTitle.setText(Html.fromHtml(first3 + next3));


        this._autocompletePatientname.setThreshold(1);

        this._multiautoTreatmentfor.setThreshold(1);
        this._multiautoTreatmentfor.setTokenizer(new CommaTokenizer());

        this._multiautoDiagnosis.setThreshold(1);
        this._multiautoDiagnosis.setTokenizer(new CommaTokenizer());

        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        String strdate1 = sdf1.format(c1.getTime());
        this._edtxtvwVisitedon.setText(strdate1);

        this._edtxtvwVisitafter.setText("3");//CR 13

        this.mSimpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        new PeriodFormatterBuilder().appendYears().appendSuffix(" year(s) ").appendMonths().appendSuffix(" month(s) ").appendDays().appendSuffix(" day(s) ").printZeroNever().toFormatter();
        try {
            this.mSimpleDateFormat.parse(this._edtxtvwVisitedon.getText().toString());

        } catch (ParseException e) {

            e.printStackTrace();
        }


        this._autocompletePharmacyName.setThreshold(1);
        this._autocompletePharmacyName.setText(String.valueOf(BaseConfig.HOSPITALNAME));

        String address = BaseConfig.GetValues("select Address as ret_values from Mstr_MultipleHospital where ServerId='" + BaseConfig.HID + '\'');

        if (address.length() > 0) {
            this._autocompletePharmacyAddress.setText(address);
        }

        Calendar cc = Calendar.getInstance();
        MedicinePrescription.mcYear = cc.get(Calendar.YEAR);
        MedicinePrescription.mcMonth = cc.get(Calendar.MONTH) + 1;
        MedicinePrescription.mcDay = cc.get(Calendar.DAY_OF_MONTH);

        this._autocompleteMedicineName.setThreshold(1);// will start working from first character

        Calendar c = Calendar.getInstance();
        MedicinePrescription.mYear = c.get(Calendar.YEAR);
        MedicinePrescription.mMonth = c.get(Calendar.MONTH);
        MedicinePrescription.mDay = c.get(Calendar.DAY_OF_MONTH);

        BaseConfig.LoadValuesSpinner(this._spinnerDuration, getApplicationContext(), "select distinct duration as dvalue from Duration;", this.getString(string.select_duration));

        this.LoadValues(this._multiautoDiagnosis, this, "select distinct diagnosisdata as dvalue from diagonisdetails where (isactive='True' or isactive='true' or isactive='1') order by id desc;", 1);

        this.LoadValues(this._multiautoTreatmentfor, this, "select distinct treatmentfor as dvalue from trmntfor where (isactive='True' or isactive='true' or isactive='1') order by id desc;", 2);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            String STATUS = b.getString("CONTINUE_STATUS");
            String COMMON_TREATMENTFOR = b.getString("PASSING_TREATMENTFOR");
            String COMMON_DIAGNOSIS = b.getString("PASSING_DIAGNOSIS");
            this.COMMON_PATIENT_ID = b.getString("PASSING_PATIENT_ID");
            this.COMMON_INVESTIGATIONID = b.getString("PASSING_INVESTIGATIONID");
            this.COMMON_DIAGNOSISID = b.getString("PASSING_DIAGNOSISID");

            this.FLAG_MYPATIENT = b.getString("FROM");

            assert STATUS != null;
            if (STATUS.equalsIgnoreCase("True")) {

                this.LOAD_PATIENT_DETAILS(this.COMMON_PATIENT_ID, COMMON_TREATMENTFOR, COMMON_DIAGNOSIS);
                String Query = "select id as dstatus from Mprescribed where ptid='" + this._autocompletePatientname.getText().toString().split("-")[1] + "';";

                boolean q1 = BaseConfig.LoadBooleanStatus(Query);
                if (q1) {

                    this.PreviousMedicineLayout.setVisibility(View.VISIBLE);

                }

            }
        } else {
            this.FLAG_MYPATIENT = "";
        }


        this._autocmpltxtInjection.setThreshold(1);
        this._edtDosage1.setEnabled(false);
        this._edtDuration1.setEnabled(false);
        this._edtQuantity1.setEnabled(false);


        this._edtDosage2.setEnabled(false);
        this._edtDuration2.setEnabled(false);
        this._edtQuantity2.setEnabled(false);


    }

    private void CONTROLLISTENERS() {


        this.SaveMedicineTemplate.setOnClickListener(view -> this.ShowSaveTemplateDialog());

        this.imgvw_medicine_options.setOnClickListener(view -> {

            //final CharSequence[] items = {"Medicine Templates", "Favourite Medicines"};
            CharSequence[] items = {"Medicine Templates"};
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle(this.getString(string.options));
            builder.setItems(items, (dialog, item) -> {

                if (items[item].toString().equalsIgnoreCase("Medicine Templates")) {

                    this.LoadMedicineTemplate();

                } else if (items[item].toString().equalsIgnoreCase("Favourite Medicines")) {

                    this.LoadFavourtieMedicines();
                }


            });
            Objects.requireNonNull(builder.create().getWindow()).getAttributes().windowAnimations = style.DialogAnimation;
            builder.create().show();


        });

        this._edtxtvwVisitafter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                MedicinePrescription.this._edtxtvwVisitafter.setFilters(new InputFilter[]{new InputFilterMinMax("1", "180")});

                if (MedicinePrescription.this._edtxtvwVisitafter.getText().length() > 0) {

                    int getcount = Integer.parseInt(MedicinePrescription.this._edtxtvwVisitafter.getText().toString());

                    if (getcount >= 180) {

                        BaseConfig.customtoast(MedicinePrescription.this.getApplicationContext(), MedicinePrescription.this, "Alert: Max 180 days");

                    }


                    MedicinePrescription.this.now = Calendar.getInstance();
                    int noofdays1 = Integer.parseInt(MedicinePrescription.this._edtxtvwVisitafter.getText().toString());
                    MedicinePrescription.this.now.add(Calendar.DATE, noofdays1);

                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                    String strdate1 = sdf1.format(MedicinePrescription.this.now.getTime());

                    SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE",Locale.ENGLISH);
                    String formattedDay = sdf2.format(MedicinePrescription.this.now.getTime());

                    if (formattedDay.equals(BaseConfig.workingdays_mon)
                            || formattedDay.equals(BaseConfig.workingdays_tue)
                            || formattedDay.equals(BaseConfig.workingdays_wed)
                            || formattedDay.equals(BaseConfig.workingdays_thur)
                            || formattedDay.equals(BaseConfig.workingdays_fri)
                            || formattedDay.equals(BaseConfig.workingdays_sat)
                            || formattedDay.equals(BaseConfig.workingdays_sun)) {
                        MedicinePrescription.this._edtxtvwNextvisit.setText(strdate1);
                    } else {
                        MedicinePrescription.this._edtxtvwNextvisit.setText("");
                        LayoutInflater inflator = MedicinePrescription.this.getLayoutInflater();
                        View layout = inflator.inflate(layout.layout_toast_nxtvisit, MedicinePrescription.this.findViewById(id.custom_toast_id));
                        TextView text = layout.findViewById(id.texttoast);
                        text.setText("Choose another day its Holiday");
                        Toast toast = new Toast(MedicinePrescription.this.getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();

                    }

                } else {
                    MedicinePrescription.this._edtxtvwNextvisit.setText("");
                }

            }
        });


        this.PreviousMedicineLayout.setOnClickListener(view -> this.LoadPreviousMedHistory());


        this._edttxtMorningFrequency.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (this._edttxtMorningFrequency.isFocused()) {
                this._edttxtMorningFrequency.setText("");
                this._edttxtMorningFrequency.requestFocus();

            }

        });

        this._edttxtAfternoonFrequency.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (this._edttxtAfternoonFrequency.isFocused()) {
                this._edttxtAfternoonFrequency.setText("");
                this._edttxtAfternoonFrequency.requestFocus();

            }

        });

        this._edttxtEvening.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (this._edttxtEvening.isFocused()) {
                this._edttxtEvening.setText("");
                this._edttxtEvening.requestFocus();

            }

        });

        this._edttxtNite.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (this._edttxtNite.isFocused()) {
                this._edttxtNite.setText("");
                this._edttxtNite.requestFocus();

            }

        });

        this._autocompletePatientname.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {

            String[] patid_nm = this._autocompletePatientname.getText().toString().trim().split("-");
            BaseConfig.patid_mpopup = patid_nm[1].trim();


            String Query = "select id as dstatus from Mprescribed where ptid='" + this._autocompletePatientname.getText().toString().split("-")[1] + "';";

            boolean q1 = BaseConfig.LoadBooleanStatus(Query);

            if (q1) {

                this.PreviousMedicineLayout.setVisibility(View.VISIBLE);

            }


            try {

                this.SelectedGetPatientDetails();


            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        this._buttonSubmitPrescription.setOnClickListener(v -> {

            if (this._autocompletePatientname.getText().length() > 0) {

                String[] Pat = this._autocompletePatientname.getText().toString().split("-");
                if (Pat.length == 2) {

                    boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + "' ");
                    if (q) {
                        this.SAVE_LOCAL();
                    } else {
                        BaseConfig.showSimplePopUp(this.getString(string.not_pat_regist), this.getString(string.info), this, drawable.ic_patienticon, color.red_500);
                    }
                } else if (Pat.length == 1) {
                    boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + '\'');
                    if (q) {


                        this.SAVE_LOCAL();
                    } else {
                        BaseConfig.showSimplePopUp(this.getString(string.not_pat_regist), this.getString(string.info), this, drawable.ic_patienticon, color.red_400);
                    }
                }

            } else {
                BaseConfig.showSimplePopUp(this.getString(string.enter_pat_name), this.getString(string.information), this, drawable.ic_warning_black_24dp, color.orange_500);
                this._autocompletePatientname.requestFocus();
            }

        });


        this._buttonAddMedicine.setOnClickListener(v -> {

            try {
                //changing the layout for prescription
                if (this.Medicine_Type.trim().equalsIgnoreCase("")) {
                    this.ADD_MEDICINE_LIST(1);

                } else {
                    if (this.Medicine_Type.equalsIgnoreCase(this.Medicine_Type_Array[0])) {
                        this.ADD_MEDICINE_LIST(1);
                    } else if (this.Medicine_Type.equalsIgnoreCase(this.Medicine_Type_Array[4])) {
                        this.ADD_MEDICINE_LIST(2);
                    } else if (this.Medicine_Type.equalsIgnoreCase(this.Medicine_Type_Array[1])) {
                        this.ADD_MEDICINE_SYRUP();
                    } else if (this.Medicine_Type.equalsIgnoreCase(this.Medicine_Type_Array[2]) || this.Medicine_Type.equalsIgnoreCase(this.Medicine_Type_Array[3])) {
                        this.ADD_MEDICINE_OINTMENT();
                    } else if (this.Medicine_Type.equalsIgnoreCase(this.Medicine_Type_Array[5])) {
                        this.ADD_MEDICINE_LIST(3);
                    } else {
                        this.ADD_MEDICINE_LIST(1);
                    }

                }

                if (this._recyclerMedicineList.getAdapter()!=null && this._recyclerMedicineList.getAdapter().getItemCount() > 0) {
                    this.SaveMedicineTemplate.setVisibility(View.VISIBLE);
                } else {
                    this.SaveMedicineTemplate.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        this._buttonCancelPrescription.setOnClickListener(new OnClickListener() {

            int count;

            public void onClick(View v) {

                if (this.count == 1) {
                    this.count = 0;


                    finish();
                    Intent intent = new Intent(v.getContext(), Dashboard_NavigationMenu.class);
                    MedicinePrescription.this.startActivity(intent);
                } else {
                    Toast.makeText(MedicinePrescription.this.getApplicationContext(), string.press_again_cancel, Toast.LENGTH_SHORT).show();
                    this.count++;
                }
            }
        });


        this._spinnerMorning.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (MedicinePrescription.this._spinnerAfternoon.getSelectedItemPosition() < 1 || MedicinePrescription.this._spinnerEvening.getSelectedItemPosition() < 1 || MedicinePrescription.this._spinnerNite.getSelectedItemPosition() < 1) {
                    MedicinePrescription.this._spinnerAfternoon.setSelection(MedicinePrescription.this._spinnerMorning.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerEvening.setSelection(MedicinePrescription.this._spinnerMorning.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerNite.setSelection(MedicinePrescription.this._spinnerMorning.getSelectedItemPosition());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        this._spinnerAfternoon.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (MedicinePrescription.this._spinnerMorning.getSelectedItemPosition() < 1 || MedicinePrescription.this._spinnerEvening.getSelectedItemPosition() < 1 || MedicinePrescription.this._spinnerNite.getSelectedItemPosition() < 1) {
                    MedicinePrescription.this._spinnerMorning.setSelection(MedicinePrescription.this._spinnerAfternoon.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerEvening.setSelection(MedicinePrescription.this._spinnerAfternoon.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerNite.setSelection(MedicinePrescription.this._spinnerAfternoon.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        this._spinnerEvening.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (MedicinePrescription.this._spinnerMorning.getSelectedItemPosition() < 1 || MedicinePrescription.this._spinnerAfternoon.getSelectedItemPosition() < 1 || MedicinePrescription.this._spinnerNite.getSelectedItemPosition() < 1) {
                    MedicinePrescription.this._spinnerMorning.setSelection(MedicinePrescription.this._spinnerEvening.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerAfternoon.setSelection(MedicinePrescription.this._spinnerEvening.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerMorning.setSelection(MedicinePrescription.this._spinnerEvening.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });

        this._spinnerNite.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (MedicinePrescription.this._spinnerMorning.getSelectedItemPosition() < 1 && MedicinePrescription.this._spinnerAfternoon.getSelectedItemPosition() < 1 && MedicinePrescription.this._spinnerEvening.getSelectedItemPosition() < 1) {
                    MedicinePrescription.this._spinnerMorning.setSelection(MedicinePrescription.this._spinnerNite.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerAfternoon.setSelection(MedicinePrescription.this._spinnerNite.getSelectedItemPosition());
                    MedicinePrescription.this._spinnerEvening.setSelection(MedicinePrescription.this._spinnerNite.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        this._edtxtvwVisitafter.addTextChangedListener(new MedicinePrescription.MyTextWatcher());


        this._autocompletePharmacyName.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {

            String pharmacy_address = BaseConfig.GetValues("select distinct pharmaddr||'-'||contactnum as ret_values from Pharmacy where pharmacyname='" + this._autocompletePharmacyName.getText() + "';");
            this._autocompletePharmacyAddress.setText(pharmacy_address);

        });


        this._autocompleteMedicineName.setOnItemClickListener((parent, view, position, id) -> {
            try {


                this.GET_MEDICINE_TYPE();

            } catch (Exception ignored) {

            }
        });


        this._multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Investigations.convertListtoStringArray(this.Loadlist2)));

        this._multiautoTreatmentfor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String text;
                text = String.valueOf(s);

                MedicinePrescription.this._multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(MedicinePrescription.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, Investigations.convertListtoStringArray(MedicinePrescription.this.Loadlist2))));

                MedicinePrescription.this._multiautoTreatmentfor.showDropDown();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        this._multiautoDiagnosis.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Investigations.convertListtoStringArray(this.Loadlist1)));

        this._multiautoDiagnosis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String text;
                text = String.valueOf(s);

                MedicinePrescription.this._multiautoDiagnosis.setAdapter(new ArrayAdapter<>(MedicinePrescription.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, Investigations.convertListtoStringArray(MedicinePrescription.this.Loadlist1))));

                MedicinePrescription.this._multiautoDiagnosis.showDropDown();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        this._autocompletePatientname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                Drawable rightDrawable = AppCompatResources.getDrawable(MedicinePrescription.this._autocompletePatientname.getContext(), drawable.ic_clear_button_white);
                if (MedicinePrescription.this._autocompletePatientname.getText().length() > 0) {
                    MedicinePrescription.this._autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    MedicinePrescription.this._autocompletePatientname.setOnTouchListener((v, event) -> {
                        int DRAWABLE_LEFT = 0;
                        int DRAWABLE_TOP = 1;
                        int DRAWABLE_RIGHT = 2;
                        int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (float) (MedicinePrescription.this._autocompletePatientname.getRight() - MedicinePrescription.this._autocompletePatientname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                MedicinePrescription.this.ClearAll();
                                return true;
                            }
                        }
                        return false;
                    });

                } else {
                    MedicinePrescription.this._autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    MedicinePrescription.this._autocompletePatientname.setOnTouchListener(null);

                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (MedicinePrescription.this._autocompletePatientname.getText().toString().length() > 0) {

                    String Query = "select name,Patid from patreg order by name";
                    BaseConfig.SelectedGetPatientDetailsFilter(Query, MedicinePrescription.this, MedicinePrescription.this._autocompletePatientname, s.toString());

                }
                MedicinePrescription.this._imgvwPatientphoto.setImageBitmap(null);
                MedicinePrescription.this._tvwAgegender.setText(null);

            }
        });


        this._autocompleteMedicineName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {



                Drawable rightDrawable = AppCompatResources.getDrawable(MedicinePrescription.this._autocompleteMedicineName.getContext(), drawable.ic_clear_button);
                if (MedicinePrescription.this._autocompleteMedicineName.getText().length() > 0) {
                    MedicinePrescription.this._autocompleteMedicineName.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    MedicinePrescription.this._autocompleteMedicineName.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            int DRAWABLE_LEFT = 0;
                            int DRAWABLE_TOP = 1;
                            int DRAWABLE_RIGHT = 2;
                            int DRAWABLE_BOTTOM = 3;

                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= (MedicinePrescription.this._autocompleteMedicineName.getRight() - MedicinePrescription.this._autocompleteMedicineName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                    MedicinePrescription.this._autocompleteMedicineName.setText("");

                                    return true;
                                }
                            }
                            return false;
                        }
                    });

                } else {
                    MedicinePrescription.this._autocompleteMedicineName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                    MedicinePrescription.this._autocompleteMedicineName.setOnTouchListener(null);

                }



            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (MedicinePrescription.this._autocompleteMedicineName.getText().toString().length() > 0) {
                    String Query = "SELECT (CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims where (Schedule_Category='Schedule 2' or Schedule_Category='Schedule 3')";
                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, MedicinePrescription.this, MedicinePrescription.this._autocompleteMedicineName, s.toString());

                }
            }
        });


        this._imgvwPatientphoto.setOnClickListener(v -> {
            if (this._autocompleteMedicineName.getText().length() > 0
                    && this._tvwAgegender.getText().length() > 0) {

                BaseConfig.Show_Patient_PhotoInDetail(this._autocompleteMedicineName.getText().toString().split("-")[0], this._autocompleteMedicineName.getText().toString().split("-")[1], this._tvwAgegender.getText().toString(), this);
            }
        });


        this._autocompletePharmacyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (MedicinePrescription.this._autocompletePharmacyName.getText().toString().length() > 0) {
                    String Query = "select distinct pharmacyname as dvalue from Pharmacy order by pharmacyname;";
                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, MedicinePrescription.this, MedicinePrescription.this._autocompletePharmacyName, s.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        this._autocmpltxtInjection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (MedicinePrescription.this._autocmpltxtInjection.getText().toString().length() > 0) {

                    String Query = "SELECT (CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG||'-'||DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims where MEDICINEINTAKETYPE='INJ' and " +
                            "Schedule_Category='Schedule 1' and DOSAGE!=''";

                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, MedicinePrescription.this, MedicinePrescription.this._autocmpltxtInjection, s.toString());

                }

                if (MedicinePrescription.this._autocmpltxtInjection.getText().toString().length() == 0) {
                    MedicinePrescription.this._edtInjecdosage.setText("");
                    MedicinePrescription.this._edtInjecdosage.setFocusableInTouchMode(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        this._autocmpltxtInjection.setOnItemClickListener((adapterView, view, i, l) -> {
            if (this._autocmpltxtInjection.getText().toString().length() > 0) {

                String str = this._autocmpltxtInjection.getText().toString();
                if (str.contains("-")) {
                    this._edtInjecdosage.setText(str.split("-")[1]);
                    this._autocmpltxtInjection.setText(str.split("-")[0]);
                    this._edtInjecdosage.setFocusableInTouchMode(false);
                } else {
                    this._edtInjecdosage.requestFocus();
                    Toast.makeText(this, "Dosage not available.. please enter manually", Toast.LENGTH_SHORT).show();
                }

            }
        });


        this._btnAddinj.setOnClickListener(view -> {

            if (this._autocmpltxtInjection.getText().length() > 0) {
                if (this._edtInjecdosage.getText().length() > 0) {
                    if (this._edtInjquantity.getText().length() > 0) {

                        String Query = "SELECT serverid as ret_values FROM cims where MEDICINEINTAKETYPE='INJ' and MARKETNAMEOFDRUG='" + this._autocmpltxtInjection.getText() + "' and Schedule_Category='Schedule 1' and DOSAGE!=''";

                        String InjectionId = BaseConfig.GetValues(Query);


                        InjectionGetSet obj = new InjectionGetSet();

                        obj.setInjectionId(InjectionId);
                        obj.setInjectionName(this._autocmpltxtInjection.getText().toString());
                        obj.setDosage(this._edtInjecdosage.getText().toString());
                        obj.setQuantity(this._edtInjquantity.getText().toString());
                        this.injectionGetSets.add(obj);

                        this.mLinearLayoutManager = new LinearLayoutManager(this);
                        this._injectionRecycler.setLayoutManager(this.mLinearLayoutManager);
                        this.injectionRecyclerAdapter = new MedicinePrescription.InjectionRecyclerAdapter(this.injectionGetSets, this._injectionRecycler);
                        this._injectionRecycler.setAdapter(this.injectionRecyclerAdapter);
                        Toast.makeText(this, "Injection and dosage added to list..", Toast.LENGTH_SHORT).show();

                        this._autocmpltxtInjection.setText("");
                        this._edtInjecdosage.setText("");
                        this._edtInjquantity.setText("");


                    } else {

                        this._edtInjquantity.setError("Required");
                        Toast.makeText(this, "Enter Quantity fields..", Toast.LENGTH_LONG).show();
                    }
                } else {

                    this._edtInjecdosage.setError("Required");
                    Toast.makeText(this, "Enter Dosage..", Toast.LENGTH_LONG).show();
                }

            } else {
                this._autocmpltxtInjection.setError("Required");
                Toast.makeText(this, "Enter Injection Name..", Toast.LENGTH_LONG).show();
            }


        });

        this._chkNrmsaline.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                this._edtDosage1.setEnabled(true);
                this._edtDuration1.setEnabled(true);
                this._edtQuantity1.setEnabled(true);
            } else {
                this._edtDosage1.setEnabled(false);
                this._edtDuration1.setEnabled(false);
                this._edtQuantity1.setEnabled(false);

                this._edtDosage1.setError(null);
                this._edtDuration1.setError(null);
                this._edtQuantity1.setError(null);

            }


        });

        this._chkAsthasaline.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                this._edtDosage2.setEnabled(true);
                this._edtDuration2.setEnabled(true);
                this._edtQuantity2.setEnabled(true);
            } else {
                this._edtDosage2.setEnabled(false);
                this._edtDuration2.setEnabled(false);
                this._edtQuantity2.setEnabled(false);

                this._edtDosage2.setError(null);
                this._edtDuration2.setError(null);
                this._edtQuantity2.setError(null);
            }

        });


        this._chkInjection.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                this._injectionLayout.setVisibility(View.VISIBLE);
            else
                this._injectionLayout.setVisibility(View.GONE);
        });

        this._chkNebulization.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                this._nebulizationLayout.setVisibility(View.VISIBLE);
            else
                this._nebulizationLayout.setVisibility(View.GONE);
        });

        this._chkSuturing.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                this._suturingLayout.setVisibility(View.VISIBLE);
            else
                this._suturingLayout.setVisibility(View.GONE);
            this._muliautcompltxtSuturing.setError(null);
        });

        this._chkPlastering.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                this._plasteringLayout.setVisibility(View.VISIBLE);
            else
                this._plasteringLayout.setVisibility(View.GONE);
            this._muliautcompltxtPlastering.setError(null);
        });



        OnCheckedChangeListener checkedChangeListenerall= (compoundButton, b) -> {
            if (b) {
                this._chkbxMorning.setChecked(true);
                this._chkbxAfternoon.setChecked(true);
                this._chkbxEvening.setChecked(true);
                this._chkbxNite.setChecked(true);
                this._edttxtMorningFrequency.setEnabled(true);
                this._edttxtAfternoonFrequency.setEnabled(true);
                this._edttxtEvening.setEnabled(true);
                this._edttxtNite.setEnabled(true);
                this._spinnerMorning.setEnabled(true);
                this._spinnerAfternoon.setEnabled(true);
                this._spinnerEvening.setEnabled(true);
                this._spinnerNite.setEnabled(true);


            } else {
                this._chkbxMorning.setChecked(false);
                this._chkbxAfternoon.setChecked(false);
                this._chkbxEvening.setChecked(false);
                this._chkbxNite.setChecked(false);
                this._edttxtMorningFrequency.setEnabled(false);
                this._edttxtMorningFrequency.setText("1");
                this._edttxtAfternoonFrequency.setEnabled(false);
                this._edttxtAfternoonFrequency.setText("1");
                this._edttxtEvening.setEnabled(false);
                this._edttxtEvening.setText("1");
                this._edttxtNite.setEnabled(false);
                this._edttxtNite.setText("1");
                this._spinnerMorning.setEnabled(false);
                this._spinnerMorning.setSelection(1);

                this._spinnerAfternoon.setEnabled(false);
                this._spinnerAfternoon.setSelection(1);

                this._spinnerEvening.setEnabled(false);
                this._spinnerEvening.setSelection(1);

                this._spinnerNite.setEnabled(false);
                this._spinnerNite.setSelection(1);

            }
        };

        this._chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);

        this._chkbxMorning.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                this._edttxtMorningFrequency.setEnabled(true);
                this._spinnerMorning.setEnabled(true);


            } else {
                this._chkbxCheckall.setOnCheckedChangeListener(null);
                this._chkbxCheckall.setChecked(false);
                this._chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                this._edttxtMorningFrequency.setEnabled(false);
                this._edttxtMorningFrequency.setText("1");
                this._spinnerMorning.setEnabled(false);
                this._spinnerMorning.setSelection(1);

            }

        });


        this._chkbxAfternoon.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                this._edttxtAfternoonFrequency.setEnabled(true);
                this._spinnerAfternoon.setEnabled(true);


            } else {
                this._chkbxCheckall.setOnCheckedChangeListener(null);
                this._chkbxCheckall.setChecked(false);
                this._chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                this._edttxtAfternoonFrequency.setEnabled(false);
                this._edttxtAfternoonFrequency.setText("1");
                this._spinnerAfternoon.setEnabled(false);
                this._spinnerAfternoon.setSelection(1);
            }

        });


        this._chkbxEvening.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                this._edttxtEvening.setEnabled(true);
                this._spinnerEvening.setEnabled(true);


            } else {
                this._chkbxCheckall.setOnCheckedChangeListener(null);
                this._chkbxCheckall.setChecked(false);
                this._chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                this._edttxtEvening.setEnabled(false);
                this._edttxtEvening.setText("1");
                this._spinnerEvening.setEnabled(false);
                this._spinnerEvening.setSelection(1);
            }
        });


        this._chkbxNite.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                this._edttxtNite.setEnabled(true);
                this._spinnerNite.setEnabled(true);
            } else {
                this._chkbxCheckall.setOnCheckedChangeListener(null);
                this._chkbxCheckall.setChecked(false);
                this._chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                this._edttxtNite.setEnabled(false);
                this._edttxtNite.setText("1");
                this._spinnerNite.setEnabled(false);
                this._spinnerNite.setSelection(1);
            }
        });

        try {


            MedicinePrescription.LoadDrWorkingDays();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//End controlls

    private void LoadFavourtieMedicines() {

        Toast.makeText(this, "Inprocess..", Toast.LENGTH_SHORT).show();
    }

    private void LoadMedicineTemplate() {


        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select distinct TemplateName from TemplateDtls order by id;", null);
        this.templates_list = new ArrayList<>();
        //templates_list.add(getString(R.string.medicine_template_txt));

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String firstName = c.getString(c.getColumnIndex("TemplateName"));
                    this.templates_list.add(firstName);

                } while (c.moveToNext());
            }
        }

        db.close();
        Objects.requireNonNull(c).close();

        if (this.templates_list.size() == 0) {
            //Toast.makeText(MedicinePrescription.this, "No medicine templates available..add it from masters", Toast.LENGTH_LONG).show();
            BaseConfig.SnackBar(this, "No medicine templates available..add it from masters", this._medicinePrescriptionParentLayout, 2);
        }


        if (this.templates_list.size() > 0 && this.templates_list != null) {

            CharSequence colors[] = this.templates_list.toArray(new CharSequence[this.templates_list.size()]);

            Builder builder = new Builder(this);
            builder.setTitle("Choose Medicine Template");
            builder.setItems(colors, (dialog, which) -> this.gettemplate(colors[which].toString()));
            builder.show();

        }

    }

    private final void LOAD_PATIENT_DETAILS(String PatientId, String TreatmentFor, String Diagnosis) {

        try {
            String Str_Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PatientId + '\'');
            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PatientId + '\'');
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + PatientId + '\'');

            this._autocompletePatientname.setText(String.format("%s-%s", Str_Patient_Name, PatientId));
            this._tvwAgegender.setText(Patient_AgeGender);
            this._multiautoTreatmentfor.setText(TreatmentFor);
            this._multiautoDiagnosis.setText(Diagnosis);
            BaseConfig.LoadPatientImage(Str_Patient_Photo, this._imgvwPatientphoto, 100);

            this.SelectedGetPatientDetails();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private final void SelectedGetPatientDetails() {


        try {
            String[] pna = this._autocompletePatientname.getText().toString().split("-");

            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + pna[1] + '\'');
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + pna[1] + '\'');

            BaseConfig.patientEmail = BaseConfig.GetValues("select email as ret_values from Patreg where Patid='" + pna[1] + "'");

            this._tvwAgegender.setText(Patient_AgeGender);
            BaseConfig.Glide_LoadImageView(this._imgvwPatientphoto, Str_Patient_Photo);


        } catch (Exception ignored) {

        }


    }

    private final void LoadValues(AutoCompleteTextView autotxt, Context cntxt, String Query, int id) {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        switch (id) {
                            case 1: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                this.Loadlist1.add(counrtyname);
                                break;
                            }
                            case 2: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                this.Loadlist2.add(counrtyname);
                                break;
                            }
                            case 3: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                this.Loadlist3.add(counrtyname);
                                break;
                            }
                            case 4: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                this.Loadlist4.add(counrtyname);
                                break;
                            }
                        }

                    } while (c.moveToNext());
                }
            }

            Objects.requireNonNull(c).close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private final void ClearAll() {
        this._autocompletePatientname.setText("");
        this._multiautoTreatmentfor.setText("");
        this._multiautoDiagnosis.setText("");

        BaseConfig.Glide_LoadDefaultImageView(this._imgvwPatientphoto);
        this._tvwAgegender.setText("-");
        this._autocompletePatientname.setEnabled(true);
        this.PreviousMedicineLayout.setVisibility(View.GONE);
    }
    //endregion

    //region MEDICINE PRESCRIPTION - SUBMIT CODE
    private boolean checkValidation() {
        boolean ret = true;


        if (!Validation1.hasText(this._autocompletePharmacyName))
            ret = false;


        if (this._chkSuturing.isChecked()) {
            if (this._muliautcompltxtSuturing.getText().toString().length() == 0) {
                ret = false;
                this._muliautcompltxtSuturing.setError("Required");
                Toast.makeText(this, "Enter body part for plastering", Toast.LENGTH_SHORT).show();
            }

        }

        if (this._chkPlastering.isChecked()) {
            if (this._muliautcompltxtPlastering.getText().toString().length() == 0) {
                ret = false;
                this._muliautcompltxtPlastering.setError("Required");
                Toast.makeText(this, "Enter body part for suturing", Toast.LENGTH_SHORT).show();
            }

        }


        if (this._chkNebulization.isChecked()) {
            if (this._chkAsthasaline.isChecked()) {
                if (this._edtDosage2.getText().toString().length() == 0 || this._edtDuration2.getText().toString().length() == 0 || this._edtQuantity2.getText().toString().length() == 0) {
                    ret = false;
                    this._edtDosage2.setError("Required");
                    this._edtDuration2.setError("Required");
                    this._edtQuantity2.setError("Required");
                    Toast.makeText(this, "Fill all fields of Asthaline saline", Toast.LENGTH_SHORT).show();
                }

            }
            if (this._chkNrmsaline.isChecked()) {
                if (this._edtDosage1.getText().toString().length() == 0 || this._edtDuration1.getText().toString().length() == 0 || this._edtQuantity1.getText().toString().length() == 0) {
                    ret = false;
                    this._edtDosage1.setError("Required");
                    this._edtDuration1.setError("Required");
                    this._edtQuantity1.setError("Required");
                    Toast.makeText(this, "Fill all fields of Normal saline", Toast.LENGTH_SHORT).show();
                }

            }
        }


        if (!Validation1.hasText(this._multiautoDiagnosis))
            ret = false;
        if (!Validation1.hasText(this._multiautoTreatmentfor))
            ret = false;
        if (!Validation1.hasText(this._autocompletePatientname))
            ret = false;

        return ret;
    }

    private void SAVE_LOCAL() {

        try {
            if (this.checkValidation())

                this.SUBMIT_FORM();
            else
                Toast.makeText(this, this.getString(string.check_missing), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void SUBMIT_FORM() {
        SQLiteDatabase db = BaseConfig.GetDb();//MedicinePrescription.this);

        ContentValues values;


        String consulationfeee = "0";
        /////////////////////////////////////////////////////////////////////////////////////
        //Treatment master
        String[] tf = this._multiautoTreatmentfor.getText().toString().trim().split(",");
        String[] dg1 = this._multiautoDiagnosis.getText().toString().split(",");

        BaseConfig.INSERT_NEW_TREATMENT_FOR(tf, this);
        BaseConfig.INSERT_NEW_PROVISIONAL_DIAGNOSIS(dg1, this);

        SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.ENGLISH);

        Date date = new Date();



        String MedicinePrescriptionId = MedicinePrescription.GetMedicinePrescriptionId();

        String[] PATIENT_INFO = this._autocompletePatientname.getText().toString().split("-");

        String STR_PATIENT_NAME = PATIENT_INFO[0].trim();
        String STR_PATIENT_ID = PATIENT_INFO[1].trim();

        if (this.list.size() > 0) {

            //String dietrest = concatdietrestriction();

            String bulk_medicinename = this.concatpersonalhistrycnt();

            for (int j = 0; j < this.list.size(); j++) {

                String[] strmed = this.list.get(j).split("_");
                String Str = strmed[0].replace("[", "").replace("]", "").trim();

                String MedicineId = BaseConfig.GetMedicineIdFromName(Str, this);
                String remarkStr = "";
                remarkStr = this._edtxtvwRemarks.getText().length() > 0 ? this._edtxtvwRemarks.getText().toString() : "";


                values = new ContentValues();
                values.put("docid", BaseConfig.doctorId);
                values.put("DiagId", BaseConfig.digid);
                values.put("ptid", STR_PATIENT_ID);
                values.put("pname", STR_PATIENT_NAME);
                values.put("refdocname", BaseConfig.doctorname);
                values.put("clinicname", BaseConfig.clinicname);
                values.put("Medid", MedicinePrescriptionId);
                values.put("dsign", this.listcnt.get(j));
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", Integer.valueOf(1));
                values.put("medicinename", this.list.get(j));
                values.put("pagegen", this._tvwAgegender.getText().toString());
                values.put("diagnosisdtls", this._multiautoDiagnosis.getText().toString());
                values.put("treatmentfor", this._multiautoTreatmentfor.getText().toString());
                values.put("nextvisit", this._edtxtvwNextvisit.getText().toString());
                values.put("fee", consulationfeee);
                values.put("mobnum", BaseConfig.docmobile);
                values.put("diagnosis", this._multiautoDiagnosis.getText().toString());
                values.put("imei", BaseConfig.Imeinum);
                values.put("Isupdate", Integer.valueOf(0));
                values.put("Dose", Str);
                values.put("Freq", "");
                values.put("Duration", this.listtottabcount.get(j));
                values.put("Diabeticdiet", "");
                values.put("Diabeticrenaldiet", "");
                values.put("Lowcholesterol_Cardiacdiet", "");
                values.put("Hypertensivediet", "");
                values.put("Diet_Warfarin", "");
                values.put("prefpharma", this._autocompletePharmacyName.getText().toString());
                values.put("remarks", remarkStr);
                values.put("medicineId", MedicineId);
                values.put("HID", BaseConfig.HID);
                db.insert(BaseConfig.TABLE_PRESCRIPTION, null, values);


            }

            // For sending bulk medicine for first time (Implemented on 4-12-14
            // / table name:SendBulkMedicine)
            values = new ContentValues();
            values.put("ptid", STR_PATIENT_ID);
            values.put("pname", STR_PATIENT_NAME);
            values.put("docid", BaseConfig.doctorId);
            values.put("refdocname", BaseConfig.doctorname);
            values.put("treatmentfor", this._multiautoTreatmentfor.getText().toString());
            values.put("medicine", bulk_medicinename);
            values.put("IsActive", Integer.valueOf(1));
            values.put("Isupdate", Integer.valueOf(0));
            values.put("Medid", MedicinePrescriptionId);
            values.put("Pharmaname", this._autocompletePharmacyName.getText().toString());
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_SEND_BULK_MEDICINE, null, values);

            String Update_Query = "update presc set prescnum=prescnum +1";
            BaseConfig.SaveData(Update_Query);

            MedicinePrescription.LoadDeleteTempTest();

            /**************************************************************************************
             Emergency / Causality
             */
            try {
                if (this._chkInjection.isChecked() || this._chkNebulization.isChecked() || this._chkSuturing.isChecked() || this._chkPlastering.isChecked()) {

                    String Str_InjectionJson = "";
                    String Str_Nebulization_Normal = "", Str_Nebulization_Asthaline = "";
                    String Str_Suturing = "", Str_Plastering = "", Str_Plastering_Slab = "", Str_Plastering_Cast = "";

                    //Injection
                    JSONObject from_db_obj = new JSONObject();
                    JSONArray export_jsonarray = new JSONArray();

                    if (this._chkInjection.isChecked()) {
                        for (int i = 0; i < this.injectionGetSets.size(); i++) {
                            from_db_obj = new JSONObject();
                            from_db_obj.put("MedId", this.injectionGetSets.get(i).getInjectionId());
                            from_db_obj.put("InjectionName", this.injectionGetSets.get(i).getInjectionName());
                            from_db_obj.put("Dosage", this.injectionGetSets.get(i).getDosage());
                            from_db_obj.put("Quantity", this.injectionGetSets.get(i).getQuantity());
                            export_jsonarray.put(from_db_obj);
                        }
                    }


                    //Nebulization**********************************************************************
                    if (this._chkNebulization.isChecked()) {
                        if (this._chkNrmsaline.isChecked()) {
                            Str_Nebulization_Normal = "Normal saline - " + this._edtDosage1.getText() + " - " + this._edtDuration1.getText() + " - " + this._edtQuantity1.getText();
                        }

                        if (this._chkAsthasaline.isChecked()) {
                            Str_Nebulization_Asthaline = "Asthaline saline - " + this._edtDosage2.getText() + " - " + this._edtDuration2.getText() + " - " + this._edtQuantity2.getText();
                        }
                    }

                    //Suturing**************************************************************************
                    if (this._chkSuturing.isChecked()) {
                        Str_Suturing = this._muliautcompltxtSuturing.getText().toString();
                    }


                    //Plastering************************************************************************
                    JSONObject from_db_obj1 = new JSONObject();
                    JSONArray export_jsonarray1 = new JSONArray();
                    if (this._chkPlastering.isChecked()) {

                        String Str_Plastering1 = this._muliautcompltxtPlastering.getText().toString();
                        if (this._chkSlab.isChecked()) {
                            String Str_Plastering_Slab1 = this._chkSlab.getText().toString();
                        }
                        if (this._chkCast.isChecked()) {
                            String Str_Plastering_Cast1 = this._chkCast.getText().toString();
                        }
                        JSONObject from_db_obj11 = new JSONObject();
                        from_db_obj11.put("Plastering", Str_Plastering1);
                        from_db_obj11.put("Plastering_Slab", Str_Plastering_Slab);
                        from_db_obj11.put("Plastering_Cast", Str_Plastering_Cast);
                        export_jsonarray1.put(from_db_obj11);
                    }

                    values = new ContentValues();
                    values.put("Patid", STR_PATIENT_ID);
                    values.put("MPID", MedicinePrescriptionId);
                    values.put("DocId", BaseConfig.doctorId);
                    values.put("Injection", export_jsonarray.toString());
                    values.put("Nebulization_Normal", Str_Nebulization_Normal);
                    values.put("Nebulization_Asthaline", Str_Nebulization_Asthaline);
                    values.put("Suturing", Str_Suturing);
                    values.put("Plastering", export_jsonarray1.toString());
                    values.put("ActDate", BaseConfig.DeviceDate());
                    values.put("IsActive", Integer.valueOf(1));
                    values.put("IsUpdate", Integer.valueOf(0));
                    db.insert("Bind_EmergencyCausality", null, values);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

           // new GeneratePDF_From_Node(MedicinePrescriptionId).execute();

            Toast.makeText(this, this.getString(string.saved_success_prescription), Toast.LENGTH_SHORT).show();
            finish();
            Intent intent11 = new Intent(this, Dashboard_NavigationMenu.class);
            this.startActivity(intent11);


        } else {
            this.showSimplePopUp(this.getString(string.add_medicine_details), 2);
            this._autocompleteMedicineName.requestFocus();
        }


        db.close();
    }

    private final void ShowSweetAlert(String PDFLINK) {

        new CustomKDMCDialog(this)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_success_done)
                .setTitle(getString(string.information))
                .setNegativeButtonVisible(View.GONE)
                .setDescription(this.getString(string.saved_success_prescription))
                .setPossitiveButtonTitle(getString(string.ok))
                .setOnPossitiveListener(() -> {

                    finish();
                    Intent intent11 = new Intent(this, Dashboard_NavigationMenu.class);
                    this.startActivity(intent11);

                    BaseConfig.PDFLINK = PDFLINK;

                });


    }

    private void NoNetworkConnectivity() {

        new CustomKDMCDialog(this)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_cloud_off_black_24dp)
                .setTitle(getString(string.information))
                .setNegativeButtonVisible(View.GONE)
                .setDescription(getString(string.saved_success_prescription_noconnectivity))
                .setPossitiveButtonTitle(getString(string.ok))
                .setOnPossitiveListener(() -> {
                    finish();
                    Intent intent11 = new Intent(this, Dashboard_NavigationMenu.class);
                    this.startActivity(intent11);
                });


    }

    private String concatpersonalhistrycnt() {
        // TODO Auto-generated method stub

        for (int j = 0; j < this.list.size(); j++) {
            this.list.get(j);

            this.concatpersonalhistrystr += this.list.get(j) + '\n';

        }
        return this.concatpersonalhistrystr;

    }

    private final boolean IsCheckBoxChecked() {
        return this._chkbxMorning.isChecked() || this._chkbxAfternoon.isChecked() || this._chkbxEvening.isChecked() || this._chkbxNite.isChecked();
    }

    private final void ADD_MEDICINE_LIST(int id) {

        if (BaseConfig.CheckTextView(this._autocompleteMedicineName)) {

            String Query = "";

            String Query1 = "SELECT MARKETNAMEOFDRUG as dstatus1 FROM cims where MARKETNAMEOFDRUG='" + this._autocompleteMedicineName.getText() + "' or (MARKETNAMEOFDRUG||'-'||DOSAGE)='" + this._autocompleteMedicineName.getText() + '\'';

            String checkavaialblemedicine = Query1;

            Log.e("Medicine Status: ", checkavaialblemedicine);

            boolean b = BaseConfig.LoadReportsBooleanStatus(checkavaialblemedicine);
           // if (b) {
                if (BaseConfig.CheckTextView(this._edttxtMorningFrequency) && BaseConfig.CheckTextView(this._edttxtAfternoonFrequency) && BaseConfig.CheckTextView(this._edttxtEvening) && BaseConfig.CheckTextView(this._edttxtNite)) {
                    if (this.IsCheckBoxChecked()) {

                        if (BaseConfig.CheckSpinner(this._spinnerDuration)) {

                            String remrks = null;

                            if (this._radiobtnAfterFood.isChecked()) {
                                remrks = this.Medicine_Intake_Session[0];
                            } else if (this._radiobtnBeforeFood.isChecked()) {
                                remrks = this.Medicine_Intake_Session[1];
                            } else if (this._radiobtnWithFood.isChecked()) {
                                remrks = this.Medicine_Intake_Session[2];
                            }


                            if ((this._chkbxMorning.isChecked()) && (this._edttxtMorningFrequency.getText().length() >= 0)) {

                                this.session1 = this.Medicine_Frequency[0];
                                this.session_set1 = this.session1.substring(0, 1);

                            } else {

                                this._edttxtMorningFrequency.setText("0");
                            }

                            if (this._chkbxAfternoon.isChecked()) {
                                this.session2 = this.Medicine_Frequency[1];
                                this.session_set2 = this.session2.substring(0, 1);
                            } else {

                                this._edttxtAfternoonFrequency.setText("0");
                            }

                            if (this._chkbxEvening.isChecked()) {
                                this.session3 = this.Medicine_Frequency[2];
                                this.session_set3 = this.session3.substring(0, 1);
                            } else {

                                this._edttxtEvening.setText("0");
                            }
                            if (this._chkbxNite.isChecked()) {
                                this.session4 = this.Medicine_Frequency[3];
                                this.session_set4 = this.session4.substring(0, 1);
                            } else {

                                this._edttxtNite.setText("0");
                            }


                            String mdub = "";
                            String mdub1 = '[' + this._autocompleteMedicineName.getText().toString()
                                    + "]  _  "

                                    + this.session_set1 + '('
                                    + this._edttxtMorningFrequency.getText() + ')'

                                    + this.session_set2 + '('
                                    + this._edttxtAfternoonFrequency.getText() + ')'

                                    + this.session_set3 + '('
                                    + this._edttxtEvening.getText() + ')'

                                    + this.session_set4 + '('
                                    + this._edttxtNite.getText() + ')'

                                    + "  _ " + remrks + "  _   "
                                    + this._spinnerDuration.getSelectedItem();


                            if (this.checklistforduplicate1(mdub1)) {

                                this.list.add('[' + this._autocompleteMedicineName.getText().toString()
                                        + "]  _  "

                                        + this.session_set1 + '('
                                        + this._edttxtMorningFrequency.getText() + ')'

                                        + this.session_set2 + '('
                                        + this._edttxtAfternoonFrequency.getText() + ')'

                                        + this.session_set3 + '('
                                        + this._edttxtEvening.getText() + ')'

                                        + this.session_set4 + '('
                                        + this._edttxtNite.getText() + ')'

                                        + "  _ " + remrks + "  _   "
                                        + this._spinnerDuration.getSelectedItem());


                                String IntakeType = "";
                                switch (id) {
                                    case 1:
                                        IntakeType = this.Medicine_Type_Array[0];
                                        break;
                                    case 2:
                                        IntakeType = this.Medicine_Type_Array[4];
                                        break;
                                    case 3:
                                        IntakeType = this.Medicine_Type_Array[5];
                                        break;
                                }

                                MedicineGetSet obj = new MedicineGetSet();
                                obj.setMedicine_Type(IntakeType);
                                obj.setMedicine_Name(this._autocompleteMedicineName.getText().toString());
                                obj.setMorning_Value(this.session_set1 + '(' + this._edttxtMorningFrequency.getText() + ')');
                                obj.setAfternoon_Value(this.session_set2 + '(' + this._edttxtAfternoonFrequency.getText() + ')');
                                obj.setEvening_Value(this.session_set3 + '(' + this._edttxtEvening.getText() + ')');
                                obj.setNight_Value(this.session_set4 + '(' + this._edttxtNite.getText() + ')');
                                obj.setIntake_Value(remrks);
                                obj.setDays_Value(this._spinnerDuration.getSelectedItem().toString());


                                this.medicineGetSets.add(obj);

                                this.mLinearLayoutManager = new LinearLayoutManager(this);
                                this._recyclerMedicineList.setLayoutManager(this.mLinearLayoutManager);

                                this.medicineRecylerAdapter = new MedicinePrescription.MedicineRecylerAdapter(this.medicineGetSets, this._recyclerMedicineList);
                                this._recyclerMedicineList.setAdapter(this.medicineRecylerAdapter);


                                //for checking whether it is tablet
                                /*if 1 na tablet
                                 * if 2 na syrup
                                 * if 3 na oinment
                                 * */

                                if (id == 1) {
                                    this.listcnt.add("1-TAB");
                                } else if (id == 2) {
                                    this.listcnt.add("4-CAP");
                                }

                                int durcnt = 0;
                                StringBuilder durstr = new StringBuilder();
                                int imonth = 30;
                                int flag = 0;
                                int tottab = 0;


                                Double m1 = Double.valueOf(Double.parseDouble(this._edttxtMorningFrequency.getText().toString()));
                                Double m2 = Double.valueOf(Double.parseDouble(this._edttxtAfternoonFrequency.getText().toString()));
                                Double m3 = Double.valueOf(Double.parseDouble(this._edttxtEvening.getText().toString()));
                                Double m4 = Double.valueOf(Double.parseDouble(this._edttxtNite.getText().toString()));

                                int testval1 = (int) Math.round(m1.doubleValue());
                                int testval2 = (int) Math.round(m2.doubleValue());
                                int testval3 = (int) Math.round(m3.doubleValue());
                                int testval4 = (int) Math.round(m4.doubleValue());

                                int tottab1 = testval1 + testval2 + testval3 + testval4;


                                for (int i = 0; i < this._spinnerDuration.getSelectedItem().toString().length(); i++) {

                                    String tablets = (String.valueOf(this._spinnerDuration.getSelectedItem().toString().charAt(i)));
                                    try {
                                        if (tablets.equals("M")) {
                                            flag = 1;
                                        } else if (tablets.equals("T")) {
                                            flag = 2;
                                        }
                                        durcnt = Integer.parseInt(tablets);

                                        durstr.append(tablets);

                                    } catch (Exception ignored) {
                                        //e.printStackTrace();

                                    }

                                }


                                switch (flag) {
                                    case 1: {
                                        // tottab+=tabletval;
                                        durcnt = Integer.parseInt(durstr.toString());
                                        durcnt = tottab1 * imonth * durcnt;
                                        //int tottab1 = 0;
                                        break;
                                    }
                                    case 2: {
                                        durcnt = 30;
                                        // int tottab1 = 0;
                                        break;
                                    }
                                    default: {
                                        durcnt = Integer.parseInt(durstr.toString());
                                        durcnt = tottab1 * durcnt;
                                        // int tottab1 = 0;

                                        break;
                                    }
                                }


                                this.listtottabcount.add(String.valueOf(durcnt));

                                this._autocompleteMedicineName.setText(null);
                                this._spinnerDuration.setSelection(0);
                                this._edttxtMorningFrequency.setText("1");
                                this._edttxtAfternoonFrequency.setText("1");
                                this._edttxtEvening.setText("1");
                                this._edttxtNite.setText("1");
                                this._edttxtMorningFrequency.setEnabled(false);
                                this._edttxtAfternoonFrequency.setEnabled(false);
                                this._edttxtEvening.setEnabled(false);
                                this._edttxtNite.setEnabled(false);
                                this._chkbxCheckall.setChecked(false);
                                this._chkbxMorning.setChecked(false);
                                this._chkbxAfternoon.setChecked(false);
                                this._chkbxEvening.setChecked(false);
                                this._chkbxNite.setChecked(false);
                                this._radiobtnAfterFood.setChecked(true);
                                this._radiobtnBeforeFood.setChecked(false);
                                this._radiobtnWithFood.setChecked(false);
                                this._spinnerDuration.setSelection(0);
                                this._autocompleteMedicineName.requestFocus();

                            } else {
                                this.showSimplePopUp("Selected Medicine Already Added...", 2);
                            }

                        } else {
                            this.showSimplePopUp("Select Duration", 2);
                            this._spinnerDuration.requestFocus();
                        }
                    } else {
                        this.showSimplePopUp("Select Frequency", 2);

                    }

                } else {
                    this.showSimplePopUp("Check Frequency", 2);
                }
          /*  } else {
                showSimplePopUp("Invalid medicine name\nSelect medicine from dropdown..", 2);
                _autocompleteMedicineName.requestFocus();
            }
*/

        } else {
            this.showSimplePopUp("Enter Medicine Name", 2);
            this._autocompleteMedicineName.requestFocus();
        }

    }

    private final void ADD_MEDICINE_SYRUP() {

        if (BaseConfig.CheckTextView(this._autocompleteMedicineName)) {

            if (BaseConfig.CheckSpinner(this._spinnerMorning) && BaseConfig.CheckSpinner(this._spinnerAfternoon) && BaseConfig.CheckSpinner(this._spinnerEvening) && BaseConfig.CheckSpinner(this._spinnerNite)) {


                if (this.IsCheckBoxChecked()) {

                    if (BaseConfig.CheckSpinner(this._spinnerDuration)) {
                        // if(BaseConfig.CheckSpinner(remarks))
                        // {
                        String remrks = null;
                        if (this._radiobtnAfterFood.isChecked()) {
                            remrks = this.Medicine_Intake_Session[0];

                        } else if (this._radiobtnBeforeFood.isChecked()) {
                            remrks = this.Medicine_Intake_Session[1];
                        } else if (this._radiobtnWithFood.isChecked()) {
                            remrks = this.Medicine_Intake_Session[2];
                        }

                        String session1 = null, session2 = null, session3 = null, session4 = null;

                        String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";


                        String syrmorning = "", syrafternn = "", syreve = "", syrnight = "";

                        if (this._spinnerMorning.getSelectedItemPosition() > 0) {
                            syrmorning = this._spinnerMorning.getSelectedItem().toString();
                        }

                        if (this._spinnerAfternoon.getSelectedItemPosition() > 0) {
                            syrafternn = this._spinnerAfternoon.getSelectedItem().toString();
                        }

                        if (this._spinnerEvening.getSelectedItemPosition() > 0) {
                            syreve = this._spinnerEvening.getSelectedItem().toString();
                        }

                        if (this._spinnerNite.getSelectedItemPosition() > 0) {
                            syrnight = this._spinnerNite.getSelectedItem().toString();
                        }


                        if ((this._chkbxMorning.isChecked()) && (this._spinnerMorning.getSelectedItemPosition() >= 0)) {
                            String session11 = this.Medicine_Frequency[0];
                            session_set1 = session11.substring(0, 1);
                        } else {
                            // session_set1 = "";
                            //et_mrng.setText("0");
                            syrmorning = "0";

                        }

                        if (this._chkbxAfternoon.isChecked()) {
                            String session21 = this.Medicine_Frequency[1];
                            session_set2 = session21.substring(0, 1);
                        } else {
                            // session_set2 = "";
                            //et_aftn.setText("0");
                            syrafternn = "0";
                        }

                        if (this._chkbxEvening.isChecked()) {
                            String session31 = this.Medicine_Frequency[2];
                            session_set3 = session31.substring(0, 1);
                        } else {
                            // session_set3 = "";
                            //et_eve.setText("0");
                            syreve = "0";
                        }
                        if (this._chkbxNite.isChecked()) {
                            String session41 = this.Medicine_Frequency[3];
                            session_set4 = session41.substring(0, 1);
                        } else {
                            // session_set4 = "";
                            //et_night.setText("0");
                            syrnight = "0";
                        }


                        String mdub = "";
                        String mdub1 = '[' + this._autocompleteMedicineName.getText().toString()
                                + "]  _  "

                                + session_set1 + '('
                                + syrmorning + ')'

                                + session_set2 + '('
                                + syrafternn + ')'

                                + session_set3 + '('
                                + syreve + ')'

                                + session_set4 + '('
                                + syrnight + ')'

                                + "  _ " + remrks + "  _   "
                                + this._spinnerDuration.getSelectedItem();

                        if (this.checklistforduplicate1(mdub1)) {

                            this.list.add('[' + this._autocompleteMedicineName.getText().toString()
                                    + "]  _  "

                                    + session_set1 + '('
                                    + syrmorning + ')'

                                    + session_set2 + '('
                                    + syrafternn + ')'

                                    + session_set3 + '('
                                    + syreve + ')'

                                    + session_set4 + '('
                                    + syrnight + ')'

                                    + "  _ " + remrks + "  _   "
                                    + this._spinnerDuration.getSelectedItem());


                            MedicineGetSet obj = new MedicineGetSet();
                            obj.setMedicine_Type("SYR");
                            obj.setMedicine_Name(this._autocompleteMedicineName.getText().toString());
                            obj.setMorning_Value(session_set1 + '(' + syrmorning + ')');
                            obj.setAfternoon_Value(session_set2 + '(' + syrafternn + ')');
                            obj.setEvening_Value(session_set3 + '(' + syreve + ')');
                            obj.setNight_Value(session_set4 + '(' + syrnight + ')');
                            obj.setIntake_Value(remrks);
                            obj.setDays_Value(this._spinnerDuration.getSelectedItem().toString());


                            this.medicineGetSets.add(obj);

                            // use a linear layout manager
                            this.mLinearLayoutManager = new LinearLayoutManager(this);
                            this._recyclerMedicineList.setLayoutManager(this.mLinearLayoutManager);

                            this.medicineRecylerAdapter = new MedicinePrescription.MedicineRecylerAdapter(this.medicineGetSets, this._recyclerMedicineList);

                            this._recyclerMedicineList.setAdapter(this.medicineRecylerAdapter);
                            //for checking whether it is tablet
                            /*if 1 na tablet
                             * if 2 na syrup
                             * if 3 na oinment
                             * */

                            this.listcnt.add("2-SYR");
                            this.listtottabcount.add("1");

                            this._autocompleteMedicineName.setText(null);
                            this._spinnerDuration.setSelection(0);

                            this._spinnerMorning.setSelection(0);
                            this._spinnerAfternoon.setSelection(0);
                            this._spinnerEvening.setSelection(0);
                            this._spinnerNite.setSelection(0);

                            this._edttxtMorningFrequency.setEnabled(false);
                            this._edttxtAfternoonFrequency.setEnabled(false);
                            this._edttxtEvening.setEnabled(false);
                            this._edttxtNite.setEnabled(false);

                            this._chkbxCheckall.setChecked(false);
                            this._chkbxMorning.setChecked(false);
                            this._chkbxAfternoon.setChecked(false);
                            this._chkbxEvening.setChecked(false);
                            this._chkbxNite.setChecked(false);

                            this._edttxtMorningFrequency.setVisibility(View.VISIBLE);
                            this._edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
                            this._edttxtEvening.setVisibility(View.VISIBLE);
                            this._edttxtNite.setVisibility(View.VISIBLE);

                            this._spinnerMorning.setVisibility(View.GONE);
                            this._spinnerAfternoon.setVisibility(View.GONE);
                            this._spinnerEvening.setVisibility(View.GONE);
                            this._spinnerNite.setVisibility(View.GONE);


                            this._radiobtnAfterFood.setChecked(true);
                            this._radiobtnBeforeFood.setChecked(false);
                            this._radiobtnWithFood.setChecked(false);

                            this._autocompleteMedicineName.requestFocus();
                            this._spinnerDuration.setSelection(0);

                        } else {
                            this.showSimplePopUp("Selected Medicine Already Added...", 2);
                        }

                    } else {
                        this.showSimplePopUp("Select Duration", 2);
                        this._spinnerDuration.requestFocus();
                    }
                } else {
                    this.showSimplePopUp("Select Frequency", 2);

                }

            } else {
                this.showSimplePopUp("Check Frequency", 2);
            }

        } else {
            this.showSimplePopUp("Enter Medicine Name", 2);
            this._autocompleteMedicineName.requestFocus();
        }

    }

    private final void ADD_MEDICINE_OINTMENT() {
        if (BaseConfig.CheckTextView(this._autocompleteMedicineName)) {

            if (this.IsCheckBoxChecked()) {

                if (BaseConfig.CheckSpinner(this._spinnerDuration)) {

                    String remrks = "-";

                    String session1 = null, session2 = null, session3 = null, session4 = null;
                    String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";

                    String oint_morning = "-", oint_afternn = "-", oint_eve = "-", oint_night = "-";

                    if (this._spinnerMorning.getSelectedItemPosition() > 0) {
                        oint_morning = this._spinnerMorning.getSelectedItem().toString();
                    }

                    if (this._spinnerAfternoon.getSelectedItemPosition() > 0) {
                        oint_afternn = this._spinnerAfternoon.getSelectedItem().toString();
                    }

                    if (this._spinnerEvening.getSelectedItemPosition() > 0) {
                        oint_eve = this._spinnerEvening.getSelectedItem().toString();
                    }

                    if (this._spinnerNite.getSelectedItemPosition() > 0) {
                        oint_night = this._spinnerNite.getSelectedItem().toString();
                    }


                    if ((this._chkbxMorning.isChecked()) && (this._spinnerMorning.getSelectedItemPosition() >= 0)) {
                        String session11 = this.Medicine_Frequency[0];
                        session_set1 = session11.substring(0, 1);

                    } else {

                        this._edttxtMorningFrequency.setText("0");
                    }

                    if (this._chkbxAfternoon.isChecked()) {
                        String session21 = this.Medicine_Frequency[1];
                        session_set2 = session21.substring(0, 1);

                    } else {

                        this._edttxtAfternoonFrequency.setText("0");
                    }

                    if (this._chkbxEvening.isChecked()) {
                        String session31 = this.Medicine_Frequency[2];
                        session_set3 = session31.substring(0, 1);

                    } else {

                        this._edttxtEvening.setText("0");
                    }
                    if (this._chkbxNite.isChecked()) {
                        String session41 = this.Medicine_Frequency[3];
                        session_set4 = session41.substring(0, 1);

                    } else {
                        this._edttxtNite.setText("0");
                    }

                    String mdub = "";
                    String mdub1 = '[' + this._autocompleteMedicineName.getText().toString()
                            + "]  _  "

                            + session_set1 + '('
                            + oint_morning + ')'

                            + session_set2 + '('
                            + oint_afternn + ')'

                            + session_set3 + '('
                            + oint_eve + ')'

                            + session_set4 + '('
                            + oint_night + ')'

                            + "  _ "
                            + this._spinnerDuration.getSelectedItem();


                    if (this.checklistforduplicate1(mdub1)) {

                        this.list.add('[' + this._autocompleteMedicineName.getText().toString()
                                + "]  _  "

                                + session_set1 + '('
                                + oint_morning + ')'

                                + session_set2 + '('
                                + oint_afternn + ')'

                                + session_set3 + '('
                                + oint_eve + ')'

                                + session_set4 + '('
                                + oint_night + ')'
                                + "  _ "
                                + this._spinnerDuration.getSelectedItem());

                        MedicineGetSet obj = new MedicineGetSet();
                        obj.setMedicine_Type("OINT");
                        obj.setMedicine_Name(this._autocompleteMedicineName.getText().toString());
                        obj.setMorning_Value(session_set1 + '(' + oint_morning + ')');
                        obj.setAfternoon_Value(session_set2 + '(' + oint_afternn + ')');
                        obj.setEvening_Value(session_set3 + '(' + oint_eve + ')');
                        obj.setNight_Value(session_set4 + '(' + oint_night + ')');
                        obj.setIntake_Value(remrks);
                        obj.setDays_Value(this._spinnerDuration.getSelectedItem().toString());


                        this.medicineGetSets.add(obj);

                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        //recyclerview.setHasFixedSize(true);

                        // use a linear layout manager
                        this.mLinearLayoutManager = new LinearLayoutManager(this);
                        this._recyclerMedicineList.setLayoutManager(this.mLinearLayoutManager);

                        this.medicineRecylerAdapter = new MedicinePrescription.MedicineRecylerAdapter(this.medicineGetSets, this._recyclerMedicineList);

                        this._recyclerMedicineList.setAdapter(this.medicineRecylerAdapter);
                        //for checking whether it is tablet
                        /*if 1 na tablet
                         * if 2 na syrup
                         * if 3 na oinment
                         * */

                        this.listcnt.add("3-OINT");
                        this.listtottabcount.add("1");

                        this._autocompleteMedicineName.setText(null);
                        this._spinnerDuration.setSelection(0);

                        this._edttxtMorningFrequency.setText("1");
                        this._edttxtAfternoonFrequency.setText("1");
                        this._edttxtEvening.setText("1");
                        this._edttxtNite.setText("1");

                        this._edttxtMorningFrequency.setEnabled(false);
                        this._edttxtAfternoonFrequency.setEnabled(false);
                        this._edttxtEvening.setEnabled(false);
                        this._edttxtNite.setEnabled(false);

                        this._chkbxCheckall.setChecked(false);
                        this._chkbxMorning.setChecked(false);
                        this._chkbxAfternoon.setChecked(false);
                        this._chkbxEvening.setChecked(false);
                        this._chkbxNite.setChecked(false);

                        this._radiobtnAfterFood.setChecked(true);
                        this._radiobtnBeforeFood.setChecked(false);
                        this._radiobtnWithFood.setChecked(false);

                        this._radiogrpIntakeOptions.setVisibility(View.VISIBLE);
                        this._edttxtMorningFrequency.setVisibility(View.VISIBLE);
                        this._edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
                        this._edttxtEvening.setVisibility(View.VISIBLE);
                        this._edttxtNite.setVisibility(View.VISIBLE);

                        this._autocompleteMedicineName.requestFocus();
                        this._spinnerDuration.setSelection(0);

                    } else {
                        this.showSimplePopUp("Selected Medicine Already Added...", 2);
                    }

                } else {
                    this.showSimplePopUp("Select Duration", 2);
                    this._spinnerDuration.requestFocus();
                }
            } else {
                this.showSimplePopUp("Select Frequency", 2);

            }

        } else {
            this.showSimplePopUp("Enter Medicine Name", 2);
            this._spinnerDuration.requestFocus();
        }

    }

    private final void GET_MEDICINE_TYPE() {


        String Query = "select distinct MEDICINEINTAKETYPE as ret_values from cims where (MARKETNAMEOFDRUG='" + this._autocompleteMedicineName.getText() + "' or (MARKETNAMEOFDRUG||'-'||DOSAGE)='" + this._autocompleteMedicineName.getText() + "') and (Schedule_Category='Schedule 2' or Schedule_Category='Schedule 3')";

        String values = BaseConfig.GetValues(Query);

        this.Medicine_Type = values;

        if (this.Medicine_Type_Array[0].equals(values)) {

            this._spinnerMorning.setVisibility(View.GONE);
            this._spinnerAfternoon.setVisibility(View.GONE);
            this._spinnerEvening.setVisibility(View.GONE);
            this._spinnerNite.setVisibility(View.GONE);


            this._edttxtMorningFrequency.setVisibility(View.VISIBLE);
            this._edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
            this._edttxtEvening.setVisibility(View.VISIBLE);
            this._edttxtNite.setVisibility(View.VISIBLE);

            //CQ 13
            this._chkbxMorning.setChecked(true);
            this._edttxtMorningFrequency.setEnabled(true);

            this._chkbxNite.setChecked(true);
            this._edttxtNite.setEnabled(true);

            this._spinnerDuration.setSelection(3);


        } else if (this.Medicine_Type_Array[1].equals(values)) {//SYRUP ARRAY ADAPTER
            ArrayAdapter<CharSequence> syrup_adapter = ArrayAdapter.createFromResource(this, array.syrup_measure, android.R.layout.simple_spinner_item);
            syrup_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            this._spinnerMorning.setAdapter(syrup_adapter);
            this._spinnerAfternoon.setAdapter(syrup_adapter);
            this._spinnerEvening.setAdapter(syrup_adapter);
            this._spinnerNite.setAdapter(syrup_adapter);

            this._spinnerMorning.setVisibility(View.VISIBLE);
            this._spinnerAfternoon.setVisibility(View.VISIBLE);
            this._spinnerEvening.setVisibility(View.VISIBLE);
            this._spinnerNite.setVisibility(View.VISIBLE);

            this._spinnerMorning.setEnabled(false);
            this._spinnerAfternoon.setEnabled(false);
            this._spinnerEvening.setEnabled(false);
            this._spinnerNite.setEnabled(false);

            this._edttxtMorningFrequency.setVisibility(View.GONE);
            this._edttxtAfternoonFrequency.setVisibility(View.GONE);
            this._edttxtEvening.setVisibility(View.GONE);
            this._edttxtNite.setVisibility(View.GONE);


            //CQ 13
            this._chkbxMorning.setChecked(true);
            this._spinnerMorning.setEnabled(true);

            this._chkbxNite.setChecked(true);
            this._spinnerNite.setEnabled(true);

            this._spinnerDuration.setSelection(3);


        } else if (this.Medicine_Type_Array[2].equals(values)) {//OINTMENT ARRAY ADAPTER
            ArrayAdapter<CharSequence> oint_adapter = ArrayAdapter.createFromResource(this, array.oint_measure, android.R.layout.simple_spinner_item);
            oint_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            this._spinnerMorning.setAdapter(oint_adapter);
            this._spinnerAfternoon.setAdapter(oint_adapter);
            this._spinnerEvening.setAdapter(oint_adapter);
            this._spinnerNite.setAdapter(oint_adapter);

            this._spinnerMorning.setVisibility(View.VISIBLE);
            this._spinnerAfternoon.setVisibility(View.VISIBLE);
            this._spinnerEvening.setVisibility(View.VISIBLE);
            this._spinnerNite.setVisibility(View.VISIBLE);


            this._edttxtMorningFrequency.setVisibility(View.GONE);
            this._edttxtAfternoonFrequency.setVisibility(View.GONE);
            this._edttxtEvening.setVisibility(View.GONE);
            this._edttxtNite.setVisibility(View.GONE);

            this._radiogrpIntakeOptions.setVisibility(View.GONE);

            //CQ 13
            this._chkbxMorning.setChecked(true);
            this._chkbxNite.setChecked(true);
            this._spinnerDuration.setSelection(3);


        } else if (this.Medicine_Type_Array[3].equals(values)) {//DROPS ARRAY ADAPTER
            ArrayAdapter<CharSequence> drops_adapter = ArrayAdapter.createFromResource(this, array.drops_measure, android.R.layout.simple_spinner_item);
            drops_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            this._spinnerMorning.setAdapter(drops_adapter);
            this._spinnerAfternoon.setAdapter(drops_adapter);
            this._spinnerEvening.setAdapter(drops_adapter);
            this._spinnerNite.setAdapter(drops_adapter);

            this._spinnerMorning.setVisibility(View.VISIBLE);
            this._spinnerAfternoon.setVisibility(View.VISIBLE);
            this._spinnerEvening.setVisibility(View.VISIBLE);
            this._spinnerNite.setVisibility(View.VISIBLE);


            this._edttxtMorningFrequency.setVisibility(View.GONE);
            this._edttxtAfternoonFrequency.setVisibility(View.GONE);
            this._edttxtEvening.setVisibility(View.GONE);
            this._edttxtNite.setVisibility(View.GONE);

            this._radiogrpIntakeOptions.setVisibility(View.GONE);

            //CQ 13
            this._chkbxMorning.setChecked(true);
            this._chkbxNite.setChecked(true);
            this._spinnerDuration.setSelection(3);


        } else if (this.Medicine_Type_Array[4].equals(values) || this.Medicine_Type_Array[5].equals(values)) {
            this._spinnerMorning.setVisibility(View.GONE);
            this._spinnerAfternoon.setVisibility(View.GONE);
            this._spinnerEvening.setVisibility(View.GONE);
            this._spinnerNite.setVisibility(View.GONE);


            this._edttxtMorningFrequency.setVisibility(View.VISIBLE);
            this._edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
            this._edttxtEvening.setVisibility(View.VISIBLE);
            this._edttxtNite.setVisibility(View.VISIBLE);

            //CQ 13
            this._chkbxMorning.setChecked(true);
            this._edttxtMorningFrequency.setEnabled(true);

            this._chkbxNite.setChecked(true);
            this._edttxtNite.setEnabled(true);

            this._spinnerDuration.setSelection(3);


        }
    }

    private final void ConfirmDeleteInjection(int position, RecyclerView recyclerView1) {


        new CustomKDMCDialog(this)
                .setLayoutColor(color.red_400)
                .setImage(drawable.ic_delete_forever_black_24dp)
                .setTitle(getResources().getString(string.delete_Confirmation))
                .setDescription(getResources().getString(string.do_you_want_to_delete))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {

                    this.injectionGetSets.remove(position);
                    recyclerView1.getAdapter().notifyDataSetChanged();

                });


    }

    private final void ConfirmDelete(int position) {


        new CustomKDMCDialog(this)
                .setLayoutColor(color.red_400)
                .setImage(drawable.ic_delete_forever_black_24dp)
                .setTitle(getResources().getString(string.delete_Confirmation))
                .setDescription(getResources().getString(string.do_you_want_to_delete))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {

                    this.medicineGetSets.remove(position);

                    this.medicineRecylerAdapter = new MedicinePrescription.MedicineRecylerAdapter(this.medicineGetSets, this._recyclerMedicineList);

                    this._recyclerMedicineList.setAdapter(this.medicineRecylerAdapter);


                    this.list.remove(position);
                    this.listcnt.remove(position);
                    this.listtottabcount.remove(position);


                });


    }

    private void showSimplePopUp(String msg, int Id) {

      /*  AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Information");
        helpBuilder.setMessage(msg);
        helpBuilder.setPositiveButton("Ok",
                (dialog, which) -> {

                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();*/


        int image = drawable.ic_success_done;
        int color = color.green_500;

        if (Id == 1)//Success
        {
            image = drawable.ic_success_done;
            color = color.green_500;
        } else if (Id == 2)//Warning
        {
            image = drawable.ic_warning_black_24dp;
            color = color.orange_500;
        }
        new CustomKDMCDialog(this)
                .setNegativeButtonVisible(View.GONE)
                .setLayoutColor(color)
                .setImage(image)
                .setTitle(getString(string.information))
                .setDescription(msg)
                .setOnPossitiveListener(() -> {

                })
                .setPossitiveButtonTitle(getString(string.ok));


    }

    private final boolean checklistforduplicate1(String Str) {
        int flag = 1;
        if (this.list.size() > 0) {
            for (int i = 0; i < this.list.size(); i++) {

                if (Str.split("_")[0].trim().equalsIgnoreCase(this.list.get(i).split("_")[0].trim())) {
                    flag = 0;
                    break;
                }
            }

        }

        return flag != 0;
    }

    @Override
    public final void onBackPressed() {
        // Do Here what ever you want do on back press;
        this.LoadBack();
        System.gc();
    }

    @Override
    protected final void onDestroy() {

        super.onDestroy();
        System.gc();
    }

    private void LoadBack() {


        try {
            if ("MYPATIENT".equalsIgnoreCase(this.FLAG_MYPATIENT)) {

                BaseConfig.HideKeyboard(this);

                Bundle b=new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, this.COMMON_PATIENT_ID);
                BaseConfig.globalStartIntent(this, MyPatientDrawer.class, b);



            } else if ("INPATIENT".equalsIgnoreCase(this.FLAG_MYPATIENT)) {

                BaseConfig.HideKeyboard(this);
                Bundle b=new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, this.COMMON_PATIENT_ID);
                BaseConfig.globalStartIntent(this, Inpatient_Detailed_View.class, b);



            } else {

                BaseConfig.HideKeyboard(this);
                MedicinePrescription.LoadDeleteTempTest();
                BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);


            }
        } catch (Exception ignored) {

        }

    }

    //region MEDICINE PRESCRIPTION PDF GENERATE
    private final boolean CheckNodeServer() {

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(BaseConfig.AppNodeIP))
                    .openConnection();
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();

            if (status == 200) {
                this.statusvalue = true;
            }
            conn.disconnect();

            return this.statusvalue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    //region LOAD PREVIOUS MEDICINE HISTORY
    private final void LoadPreviousMedHistory() {

        try {
            //Show Dialog
            View rootView = LayoutInflater.from(this).inflate(layout.new_prescription_profile, null);

            this.profile_webvw = rootView.findViewById(id.webvw_prescription_profile);
            this.profile_webvw.getSettings().setJavaScriptEnabled(true);
            this.profile_webvw.setWebChromeClient(new WebChromeClient());

            this.NoDataFound = rootView.findViewById(id.img_nodata);

            this.PatientNameId = rootView.findViewById(id.patientid);

            String Query = "select name||' - '|| Patid as ret_values from Patreg where Patid='" + this._autocompletePatientname.getText().toString().split("-")[1] + '\'';
            this.PatientNameId.setText(BaseConfig.GetValues(Query));

            this.LoadWebview(0);


            this.next_btn = rootView.findViewById(id.next);
            this.pre_btn = rootView.findViewById(id.prev);


            this.next_btn.setOnClickListener(arg0 -> this.Next());

            this.pre_btn.setOnClickListener(arg0 -> this.Previous());

            this.Current();

            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(rootView);
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = style.DialogAnimation;
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //endregion

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private final void LoadWebview(int pos) {
        this.profile_webvw.getSettings().setJavaScriptEnabled(true);
        this.profile_webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        this.profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.profile_webvw.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.profile_webvw.getSettings().setDefaultTextEncodingName("utf-8");

        this.profile_webvw.setWebChromeClient(new MedicinePrescription.MyWebChromeClient());

        this.profile_webvw.setBackgroundColor(0x00000000);
        this.profile_webvw.setVerticalScrollBarEnabled(true);
        this.profile_webvw.setHorizontalScrollBarEnabled(true);


        Toast.makeText(this, "Please wait previous medicine history loading..", Toast.LENGTH_SHORT).show();


        this.profile_webvw.getSettings().setJavaScriptEnabled(true);

        this.profile_webvw.getSettings().setAllowContentAccess(true);


        this.profile_webvw.setOnLongClickListener(v -> true);

        this.profile_webvw.setLongClickable(false);


        this.profile_webvw.addJavascriptInterface(new MedicinePrescription.WebAppInterface(this), "android");
        try {

            this.profile_webvw.loadDataWithBaseURL("file:///android_asset/", this.LoadPrescriptionDetails(pos), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //endregion

    private final void Previous() {
        this.pos += 1;
        if (this.pos < this.mypatientprevmedicinehistory_medid.size()) {
            this.LoadWebview(this.pos);
            this.next_btn.setVisibility(View.VISIBLE);
        }
        if (this.pos == this.mypatientprevmedicinehistory_medid.size() - 1) {
            this.pre_btn.setVisibility(View.GONE);
            this.next_btn.setVisibility(View.VISIBLE);
        }
    }
    //endregion

    private final void Next() {
        this.pos -= 1;
        if (this.pos >= 0) {
            //SelectedGetPrevMedicineHistory(pos);
            this.LoadWebview(this.pos);
            this.pre_btn.setVisibility(View.VISIBLE);
        }
        if (this.pos == 0) {
            this.next_btn.setVisibility(View.GONE);
            this.pre_btn.setVisibility(View.VISIBLE);
        }
    }

    private final void Current() {
        if (this.mypatientprevmedicinehistory_medid.size() > 0) {

            this.LoadWebview(0);

        }
        if (this.mypatientprevmedicinehistory_medid.size() > 1) {
            this.pre_btn.setVisibility(View.VISIBLE);
            this.next_btn.setVisibility(View.GONE);
        }
    }

    private final String LoadPrescriptionDetails(int pos_val) {

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        this.mypatientprevmedicinehistory_medid = new ArrayList<>();

        Cursor c = db
                .rawQuery(
                        "select distinct Medid from Mprescribed where Ptid='" + this._autocompletePatientname.getText().toString().split("-")[1] + "' order by Medid desc;",
                        null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    this.mypatientprevmedicinehistory_medid.add(c.getString(c.getColumnIndex("Medid")));

                } while (c.moveToNext());
            }
        }
        Objects.requireNonNull(c).close();
        //********************************************


        String values;
        String PreviousMedicalHistory = "", HereditaryDiseases = "";
        String AllergicTo = "", Smoking = "", Alcohol = "", Tobacco = "", Others = "", Medication = "", FamilyMedicalHistory = "";
        String Bowel = "", Micturition = "", PresentIllness = "", PastIllness = "", TreatmentforMedicineNamePeriod = "";
        String Obstetric = "", Gynaec = "";

        //ArrayList<PrescriptionGetSet> prescriptionGetSets = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();


        String[] Tabledata;
        String remarksStr, diagnosisStr = null, symStr;


        Cursor c1 = db.rawQuery("select remarks,refdocname,Medid,medicinename,treatmentfor,diagnosis,nextvisit,Actdate from Mprescribed where Ptid='" + this._autocompletePatientname.getText().toString().split("-")[1] + "' and Medid='" + this.mypatientprevmedicinehistory_medid.get(pos_val) + "' order by Medid desc ;", null);

        if (c1.getCount() > 0) {
            this.NoDataFound.setVisibility(View.GONE);
        } else {
            this.NoDataFound.setVisibility(View.VISIBLE);
        }


        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {

                    Tabledata = new String[c1.getCount()];

                    Tabledata = c1.getString(c1.getColumnIndex("medicinename")).split("_");

                    stringBuilder.append("  <tr>\n" + "                  <th><font color=\"#000\">").append(Tabledata[0].replace("[", "").replace("]", "")).append("</font></th>\n \n").append("                    <th><font color=\"#000\">").append(Tabledata[1]).append("</font></th>\n \n").append("               \t <th><font color=\"#000\">").append(Tabledata[2]).append("</font></th>\n \n").append("              \t <th><font color=\"#000\">").append(Tabledata[3]).append("</font></th>\n\n").append("                 </tr>\n");

                    this.sbM.append(c1.getString(c1.getColumnIndex("medicinename")));
                    this.sbM.append('\n');

                    this.nxtdt = c1.getString(c1.getColumnIndex("nextvisit"));

                    if (this.nxtdt.contains("1900"))//If next visit date empty na set agum @Kumar
                    {
                        this.nxtdt = "-";
                    }

                    this.visiteddt = c1.getString(c1.getColumnIndex("Actdate")).split(" ");
                    remarksStr = c1.getString(c1.getColumnIndex("remarks"));

                    this.refdocname = c1.getString(c1.getColumnIndex("refdocname"));
                    diagnosisStr = c1.getString(c1.getColumnIndex("diagnosis"));
                    this.SymptomsValue = c1.getString(c1.getColumnIndex("treatmentfor"));


                } while (c1.moveToNext());
            }

        }
        c1.close();


        values = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                '\n' +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                '\n' +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" +
                '\n' +
                '\n' +
                '\n' +
                '\n' +
                "</head>\n" +
                "<body>  \n" +
                " \n" +
                // "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>Patient Name:</b></td> \n" +
                //  "<td height=\"20\" style=\"color:#000000;\">"+BaseConfig.SelectedPName + "-" + BaseConfig.SelectedPID+"</td>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> Patient Medicine History</font>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<div class=\"table-responsive\">          \n" +
                "<table class=\"table table-bordered\">\n" +
                "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>Refered Doctor Name:</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\">" + this.refdocname + "</td>\n" +
                "  <tr>\n" +
                "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">Medicine Name</font></th>\n" +
                "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">Interval</font></th>\n" +
                "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Frequency</font></th>\n" +
                "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Duration</font></th>\n" +
                "  </tr>\n" +
                " \n" + stringBuilder +
                " \n" +
                "</table>\n" +
                "</div>\n" +
                "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Symptoms</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\">: " + this.SymptomsValue + "</td>\n" +
                '\n' + "</div>\n </br></br>" +
                "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Diagnosis:</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\"> " + diagnosisStr + "</td>\n" +
                "\n\n</br>" +
                "<div class=\"table-responsive\">          \n" +
                "<table class=\"table\">\n" +
                " <tr>\n" +
                "    <th  bgcolor=\"#3d5987\"><font color=\"#fff\">Visited On: </font></th>\n" +
                "    <th  bgcolor=\"#3d5987\"><font color=\"#fff\">Next Visit On:</font></th>\n" +
                "\t </tr>\n" +
                "  <tr>\n" +
                "    <td>" + this.visiteddt[0] + "</td>\n" +
                "    <td>" + this.nxtdt + "</td>\n" +
                "\t  </tr>\n" +
                " \n" +
                "</table>\n" +
                "</div>\n" +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                                                                               ";


        db.close();

        Toast.makeText(this, "Previous Medicine Prescription loaded successfully..", Toast.LENGTH_SHORT).show();


        return values;
    }


    //region MEDICINE TEMPLATE SAVE DIALOG AND INSERT

    private final void gettemplate(String str) {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery(
                    "select MedicineName,TemplateId,TemplateName,Morning,Afternoon from TemplateDtls where TemplateName='"
                            + str.trim()
                            + "';", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        JSONArray jsonArray = new JSONArray(c.getString(c.getColumnIndex("MedicineName")));
                        JSONObject objJson = null;

                        //firstName = c.getString(c.getColumnIndex("MedicineName"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);


                            String medicinename = objJson.get("InjectionName").toString();

                            if (this.checklistforduplicate1(medicinename)) {

                                this.listcnt.add(objJson.get("TabType").toString());
                                this.listtottabcount.add(objJson.get("TabCount").toString());
                                this.list.add(medicinename);

                                String morningvalue, afternoonvalue, eveningvalue, nightvalue;

                                String values[] = medicinename.split("_");

                                String medicine_values = values[1].trim();

                                int mindex = medicine_values.indexOf('M');
                                int aindex = medicine_values.indexOf('A');
                                int eindex = medicine_values.indexOf('E');
                                int nindex = medicine_values.indexOf('N');


                                morningvalue = medicine_values.substring(mindex + 2, aindex - 1);
                                afternoonvalue = medicine_values.substring(aindex + 2, eindex - 1);
                                eveningvalue = medicine_values.substring(eindex + 2, nindex - 1);
                                nightvalue = medicine_values.substring(nindex + 2, medicine_values.length() - 1);


                                MedicineGetSet obj = new MedicineGetSet();
                                obj.setMedicine_Name(values[0]);
                                obj.setMorning_Value(this.session_set1 + '(' + morningvalue + ')');
                                obj.setAfternoon_Value(this.session_set2 + '(' + afternoonvalue + ')');
                                obj.setEvening_Value(this.session_set3 + '(' + eveningvalue + ')');
                                obj.setNight_Value(this.session_set4 + '(' + nightvalue + ')');
                                obj.setIntake_Value(values[2]);
                                obj.setDays_Value(values[3]);


                                this.medicineGetSets.add(obj);

                                // use a linear layout manager
                                this.mLinearLayoutManager = new LinearLayoutManager(this);
                                this._recyclerMedicineList.setLayoutManager(this.mLinearLayoutManager);

                                this.medicineRecylerAdapter = new MedicinePrescription.MedicineRecylerAdapter(this.medicineGetSets, this._recyclerMedicineList);

                                this._recyclerMedicineList.setAdapter(this.medicineRecylerAdapter);

                            } else {
                                this.showSimplePopUp(this.getString(string.select_medicine), 2);
                            }

                        }

                    } while (c.moveToNext());

                }
            }

            db.close();
            Objects.requireNonNull(c).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void ShowSaveTemplateDialog() {
        CardView layoutRoot;
        TextView title;
        TextView templateId;
        TextView txtvwTitle;
        Button buttonOk, buttonCancel;
        EditText edtInputTemplatename;


        if (this.list.size() > 0) {


            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(layout.prompts, null);


            String tid = BaseConfig.GetValues("select tnum as ret_values from TemplateMax");
            Builder alertDialogBuilder = new Builder(this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            layoutRoot = promptsView.findViewById(id.layout_root);
            title = promptsView.findViewById(id.title);
            templateId = promptsView.findViewById(id.template_id);
            txtvwTitle = promptsView.findViewById(id.txtvw_title);
            buttonOk = promptsView.findViewById(id.button_ok);
            buttonCancel = promptsView.findViewById(id.button_cancel);
            edtInputTemplatename = promptsView.findViewById(id.edt_input_templatename);

            templateId.setText(String.format("Template No:%s", tid));

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

            buttonOk.setOnClickListener(arg0 -> {
                // TODO Auto-generated method stub


                if (edtInputTemplatename.getText().length() > 0) {

                    //select distinct TemplateName from TemplateDtls order by id;
                    //Checking duplicate template name
                    boolean q = BaseConfig.LoadReportsBooleanStatus("select TemplateName  as dstatus1 from TemplateDtls where UPPER(TemplateName)='" + edtInputTemplatename.getText().toString().toUpperCase() + "' order by id;");

                    if (q) {
                        Toast.makeText(this, "Template name already exists..\nEnter new template name...", Toast.LENGTH_SHORT).show();
                        edtInputTemplatename.setText("");
                    } else {
                        BaseConfig.popuptemplate_name = edtInputTemplatename.getText().toString();
                        this.inserttemplate(edtInputTemplatename.getText().toString().toUpperCase());
                        alertDialog.cancel();

                    }

                } else {
                    edtInputTemplatename.setError("Required");
                    edtInputTemplatename.requestFocus();
                }


            });

            buttonCancel.setOnClickListener(arg0 -> alertDialog.cancel());

        } else {
            this.showSimplePopUp("Add Medicine Details", 2);
            this._autocompleteMedicineName.requestFocus();
        }
    }

    private final void inserttemplate(String str) {

        SQLiteDatabase db = BaseConfig.GetDb();

        String tid = BaseConfig.GetValues("select tnum as ret_values from TemplateMax");
        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.ENGLISH);
        Date date = new Date();
        String dttm = dateformt.format(date);

        if (this.list.size() > 0) {

            ContentValues contentValue;

            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();


            try {
                for (int j = 0; j < this.list.size(); j++) {
                    from_db_obj = new JSONObject();
                    from_db_obj.put("InjectionName", this.list.get(j));
                    from_db_obj.put("TabType", this.listcnt.get(j));
                    from_db_obj.put("TabCount", this.listtottabcount.get(j));
                    export_jsonarray.put(from_db_obj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            contentValue = new ContentValues();
            contentValue.put("Docid", BaseConfig.doctorId);
            contentValue.put("TemplateId", "Temp" + tid);
            contentValue.put("TemplateName", str);
            contentValue.put("MedicineName", export_jsonarray.toString());
            contentValue.put("dt", dttm);
            contentValue.put("Isupdate", "0");
            db.insert("TemplateDtls", null, contentValue);


            String Update_Query = "update TemplateMax set tnum=tnum+1";
            db.execSQL(Update_Query);

            db.close();

            this.showSimplePopUp(this.getString(string.template_saved), 1);

        } else {
            this.showSimplePopUp(this.getString(string.add_medicines), 2);
            this._autocompleteMedicineName.requestFocus();
        }

    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    private static class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    static class WebAppInterface {

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
        }

    }
    //endregion

    class GeneratePDF_From_Node extends AsyncTask<Void, Void, Void> {

        String medicinePrescriptionId = "";

        GeneratePDF_From_Node(String medicinePrescriptionId) {
            this.medicinePrescriptionId = medicinePrescriptionId;
        }

        protected final Void doInBackground(Void... params) {
            //Your implementation
            try {

                MedicinePrescription.this.CheckNodeServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected final void onPostExecute(Void result) {
            // TODO: do something with the feed


            if (BaseConfig.CheckNetwork(MedicinePrescription.this)) {

                if (MedicinePrescription.this.statusvalue) {

                    //region BACKGROUND SERVICE TO CHECK PDF
                    /*
                      New Method to get pdf from server
                     */
                    AsynkTaskCustom asynkTaskCustom = new AsynkTaskCustom(MedicinePrescription.this, "Please wait...Loading Pdf...");

                    asynkTaskCustom.execute(new onWriteCode<String>() {
                        @Override
                        public String onExecuteCode() throws SchemaException, InterruptedException, ExecutionException {

                            String results = "";


                            GeneratePDFNode generatePDFNode;
                            // Instantiate a controller
                            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(MedicinePrescription.this);
                            GeneratePDFNodeFactory controllerFactory = new GeneratePDFNodeFactory(magnetClient);
                            generatePDFNode = controllerFactory.obtainInstance();

                            String contentType = "application/json";
                            GetPDFNodeJsRequest body = new GetPDFNodeJsRequest();
                            JsonValue json = new JsonValue();

                            String PATIENTID = MedicinePrescription.this._autocompletePatientname.getText().toString().split("-")[1];
                            String PATIENTNAME = MedicinePrescription.this._autocompletePatientname.getText().toString().split("-")[0];

                            String INVESTIGATIONID = MedicinePrescription.this.COMMON_INVESTIGATIONID;
                            String MEDICINEID = GeneratePDF_From_Node.this.medicinePrescriptionId;
                            String DIAGNOSISID = MedicinePrescription.this.COMMON_DIAGNOSISID;

                            String SYMPTOMS = MedicinePrescription.this._multiautoTreatmentfor.getText().toString();
                            String DIAGNOSIS = MedicinePrescription.this._multiautoDiagnosis.getText().toString();

                            //agegen.getText().toString()
                            String AGE = MedicinePrescription.this._tvwAgegender.getText().toString().split("-")[0];
                            String GENDER = MedicinePrescription.this._tvwAgegender.getText().toString().split("-")[1];

                            String DOCTORNAME = BaseConfig.doctorname;
                            String DOCTORID = BaseConfig.doctorId;
                            String DOCTORSPEC = BaseConfig.docspecli;
                            String DATETIME = BaseConfig.DeviceDate();
                            String NEXTVISITDATE = MedicinePrescription.this._edtxtvwVisitafter.getText().toString();
                            String HOSPITALNAME = BaseConfig.HOSPITALNAME;


                            json.setpATIENTID(BaseConfig.CheckDBString(PATIENTID));
                            json.setpATIENTNAME(BaseConfig.CheckDBString(PATIENTNAME));
                            json.setaGE(BaseConfig.CheckDBString(AGE));
                            json.setgENDER(BaseConfig.CheckDBString(GENDER));

                            json.setiNVESTIGATIONID(BaseConfig.CheckDBString(INVESTIGATIONID));
                            json.setmEDICINEID(BaseConfig.CheckDBString(MEDICINEID));
                            json.setdIAGNOSISID(BaseConfig.CheckDBString(DIAGNOSISID));

                            json.setsYMPTOMS(BaseConfig.CheckDBString(SYMPTOMS));
                            json.setdIAGNOSIS(BaseConfig.CheckDBString(DIAGNOSIS));

                            json.setdOCTORNAME(BaseConfig.CheckDBString(DOCTORNAME));
                            json.setdOCTORID(BaseConfig.CheckDBString(DOCTORID));
                            json.setdOCTORSPEC(BaseConfig.CheckDBString(DOCTORSPEC));
                            json.setdATETIME(BaseConfig.CheckDBString(DATETIME));

                            json.setnEXTVISITDATE(BaseConfig.CheckDBString(NEXTVISITDATE));
                            json.sethOSPITALNAME(BaseConfig.CheckDBString(HOSPITALNAME));

                            body.setJsonValue(json);

                            ContentValues pdfvalues = new ContentValues();
                            pdfvalues.put("PATIENTID", PATIENTID);
                            pdfvalues.put("PATIENTNAME", PATIENTNAME);
                            pdfvalues.put("INVESTIGATIONID", INVESTIGATIONID);
                            pdfvalues.put("MEDICINEID", MEDICINEID);
                            pdfvalues.put("DIAGNOSISID", DIAGNOSISID);
                            pdfvalues.put("SYMPTOMS", SYMPTOMS);
                            pdfvalues.put("DIAGNOSIS", DIAGNOSIS);
                            pdfvalues.put("AGE", AGE);
                            pdfvalues.put("GENDER", GENDER);
                            pdfvalues.put("DOCTORNAME", DOCTORNAME);
                            pdfvalues.put("DOCTORID", DOCTORID);
                            pdfvalues.put("DOCTORSPEC", DOCTORSPEC);
                            pdfvalues.put("ACT_DATETIME", DATETIME);
                            pdfvalues.put("NEXTVISITDATE", NEXTVISITDATE);
                            pdfvalues.put("HOSPITALNAME", HOSPITALNAME);
                            SQLiteDatabase db = BaseConfig.GetDb();
                            db.insert("Bind_PDFInfo", null, pdfvalues);


                            Call<PDFNodeJsResult> callObject = generatePDFNode.getPDFNodeJs(contentType, body, null);

                            PDFNodeJsResult result = callObject.get();

                            String resultvalue = result.getResult();
                            String resulturl = result.getURL();


                            results = !resultvalue.toString().equalsIgnoreCase("FAILED") ? result.getURL() : "";

                            return results;
                        }

                        @Override
                        public String onSuccess(String result) {


                            try {
                                if (result != null && !result.equalsIgnoreCase("") ) {


                                    try {
                                        String PDFLink = BaseConfig.AppNodeIP + result;

                                        MedicinePrescription.this.ShowSweetAlert(PDFLink);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {

                                    finish();
                                    Intent intent11 = new Intent(MedicinePrescription.this, Dashboard_NavigationMenu.class);
                                    MedicinePrescription.this.startActivity(intent11);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MedicinePrescription.this, MedicinePrescription.this.getString(string.saved_success_prescription), Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent11 = new Intent(MedicinePrescription.this, Dashboard_NavigationMenu.class);
                                MedicinePrescription.this.startActivity(intent11);
                            }

                            return "";

                        }
                    });
                    //endregion


                } else {
                    MedicinePrescription.this.NoNetworkConnectivity();

                }
            } else {
                MedicinePrescription.this.NoNetworkConnectivity();

            }


        }
    }
    //endregion

    //region EMERGENCY - INJECTION ADAPTER
    public class InjectionRecyclerAdapter extends Adapter<MedicinePrescription.InjectionRecyclerAdapter.MyViewHolder> {

        final RecyclerView recyclerView;
        ArrayList<InjectionGetSet> injectionGetSets = new ArrayList<>();

        InjectionRecyclerAdapter(ArrayList<InjectionGetSet> injectionGetSets, RecyclerView recyclerView) {
            this.injectionGetSets = injectionGetSets;
            this.recyclerView = recyclerView;
        }

        @NonNull
        @Override
        public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layout.listrow_injection, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public final void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


            InjectionGetSet item = this.injectionGetSets.get(position);

            holder.injection_name.setText(item.getInjectionName());
            holder.dosage_value.setText(item.getDosage());
            holder.quantity_count.setText(String.format("Qty: %s", item.getQuantity()));

            holder.card_view.setOnClickListener(v -> {

                //Removing the count of the tablet and total
                MedicinePrescription.this.ConfirmDeleteInjection(position, this.recyclerView);

            });
        }

        @Override
        public final int getItemCount() {
            return this.injectionGetSets.size();
        }

        class MyViewHolder extends ViewHolder {

            final TextView injection_name;
            final TextView dosage_value;
            final TextView quantity_count;

            final LinearLayout card_view;

            MyViewHolder(View itemView) {
                super(itemView);

                this.injection_name = itemView.findViewById(id.injection_name);
                this.dosage_value = itemView.findViewById(id.dosage_value);
                this.quantity_count = itemView.findViewById(id.quantity_value);
                this.card_view = itemView.findViewById(id.injection_layout);

            }
        }

    }

    //region MEDICINE RECYCLER ADAPTER
    public class MedicineRecylerAdapter extends Adapter<MyViewHolder> {
        ArrayList<MedicineGetSet> medicineGetSets = new ArrayList<>();

        MedicineRecylerAdapter(ArrayList<MedicineGetSet> medicineGetSets, RecyclerView recyclerView) {
            this.medicineGetSets = medicineGetSets;
        }

        @NonNull
        @Override
        public final MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layout.listrow_medicine, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public final void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            MedicineGetSet item = this.medicineGetSets.get(position);

            if (MedicinePrescription.this.Medicine_Type_Array[0].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(drawable.tablet);

            } else if (MedicinePrescription.this.Medicine_Type_Array[1].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(drawable.syrup);

            } else if (MedicinePrescription.this.Medicine_Type_Array[2].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(drawable.ointment);

            } else if (MedicinePrescription.this.Medicine_Type_Array[3].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(drawable.tablet);

            } else if (MedicinePrescription.this.Medicine_Type_Array[4].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(drawable.injection);

            }

            holder.medicine_name.setText(item.getMedicine_Name());
            holder.morning.setText(item.getMorning_Value());
            holder.afternoon.setText(item.getAfternoon_Value());
            holder.evening.setText(item.getEvening_Value());
            holder.night.setText(item.getNight_Value());
            holder.intake.setText(item.getIntake_Value());
            holder.duration.setText(item.getDays_Value());
            holder.card_view.setOnClickListener(v -> MedicinePrescription.this.ConfirmDelete(position));
        }

        @Override
        public final int getItemCount() {
            return this.medicineGetSets.size();
        }

        class MyViewHolder extends ViewHolder {
            final TextView medicine_name;
            final TextView morning;
            final TextView afternoon;
            final TextView evening;
            final TextView night;
            final TextView intake;
            final TextView duration;
            final CardView card_view;
            final ImageView medicine_type;

            MyViewHolder(View itemView) {
                super(itemView);

                this.medicine_type = itemView.findViewById(id.medicine_image);
                this.medicine_name = itemView.findViewById(id.medicine_name);
                this.morning = itemView.findViewById(id.medicine_moring);
                this.afternoon = itemView.findViewById(id.medicine_afternoon);
                this.evening = itemView.findViewById(id.medicine_eve);
                this.night = itemView.findViewById(id.medicine_night);
                this.intake = itemView.findViewById(id.medicine_food);
                this.duration = itemView.findViewById(id.medicine_days);
                this.card_view = itemView.findViewById(id.card_view);

            }
        }

    }
    //endregion


}//END
