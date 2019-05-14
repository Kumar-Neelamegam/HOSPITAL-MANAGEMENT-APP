package kdmc_kumar.Core_Modules;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.KDMCRecyclerAdapter;
import kdmc_kumar.Utilities_Others.Tools;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;


public class ClinicalInformation extends AppCompatActivity {

    private final List<String> personalarylist = new ArrayList<>();

    private static int mYear = 0;
    private static int mMonth = 0;
    private static int mDay = 0;
    private static int mcYear = 0;
    private static int mcMonth = 0;
    private static int mcDay = 0;
    private static List<String> list = new ArrayList<>();
    /**
     * CONSTANTS
     * ALLERGY, MEDICAL HISTORY, FAMILY HISTORY, HEREDITARY DISEASES
     */
    private final String QUERY_ALLERGY = "select DataVal from Mstr_Others where MasterId=1 and (IsActive=1 or IsActive='true') order by DataVal";
    private final String QUERY_MEDICAL_HISTORY = "select DataVal from Mstr_Others where MasterId=2 and (IsActive=1 or IsActive='true') order by DataVal";
    private final String QUERY_FAMILY_HISTORY = "select DataVal from Mstr_Others where MasterId=3 and (IsActive=1 or IsActive='true')";
    private final String QUERY_HEREDITARY_DISEASES = "select DataVal from Mstr_Others where MasterId=4 and (IsActive=1 or IsActive='true') order by DataVal";

   // private final List<String> personalarylist = new ArrayList<>();
    private final ArrayList<Object> antental_list = new ArrayList<>();
    private final List<String> Loadlist1 = new ArrayList<>();
    private final List<String> Loadlist2 = new ArrayList<>();
    private final ArrayList<Integer> mUserItems1 = new ArrayList<>();
    private final ArrayList<Integer> mUserItems2 = new ArrayList<>();
    private final ArrayList<Integer> mUserItems3 = new ArrayList<>();
    // String[] listItems;
    private final ArrayList<String> listItems1 = new ArrayList<>();
    private final ArrayList<String> listItems2 = new ArrayList<>();
    private final ArrayList<String> listItems3 = new ArrayList<>();

    @BindView(R.id.clinical_parent_layout)
    android.support.design.widget.CoordinatorLayout ParentLayout;

    @BindView(R.id.clinicalinformation_nesetedscrollview)
    NestedScrollView clinicalinformationNesetedscrollview;
    @BindView(R.id.imgvw_patientphoto)
    CircleImageView imgvwPatientphoto;
    @BindView(R.id.tvw_agegender)
    TextView tvwAgegender;
    @BindView(R.id.txtvw_title_patientname)
    TextView txtvwTitlePatientname;
    @BindView(R.id.autocomplete_patientname)
    AutoCompleteTextView autocompletePatientname;
    @BindView(R.id.arrow_prevmedhistory)
    ImageButton arrowPrevmedhistory;
    @BindView(R.id.primary_pms_layout)
    LinearLayout primaryPmsLayout;
    @BindView(R.id.imgbutotn_prevmedhistory)
    Button imgbutotnPrevmedhistory;
    @BindView(R.id.edt_prevmedhistory)
    EditText edtPrevmedhistory;
    @BindView(R.id.edt_prevmedhistory_others)
    EditText edtPrevmedhistoryOthers;
    @BindView(R.id.hide_prevmedhistory)
    Button hidePrevmedhistory;
    @BindView(R.id.arrow_hereditary_diseases)
    ImageButton arrowHereditaryDiseases;
    @BindView(R.id.primary_hd_layout)
    LinearLayout primaryHdLayout;
    @BindView(R.id.button_hereditary_diseases)
    Button buttonHereditaryDiseases;
    @BindView(R.id.edt_hereditary_diseases)
    EditText edtHereditaryDiseases;
    @BindView(R.id.edt_hereditary_diseases_others)
    EditText edtHereditaryDiseasesOthers;
    @BindView(R.id.hide_hereditary_diseases)
    Button hideHereditaryDiseases;
    @BindView(R.id.arrow_personal_history)
    ImageButton arrowPersonalHistory;
    @BindView(R.id.primary_ph_layout)
    LinearLayout primaryPhLayout;
    @BindView(R.id.spinner_ph_smoking)
    Spinner spinnerPhSmoking;
    @BindView(R.id.spinner_ph_alcohol)
    Spinner spinnerPhAlcohol;
    @BindView(R.id.spinner_ph_tobacco)
    Spinner spinnerPhTobacco;
    @BindView(R.id.rbtn_bowel_normal)
    RadioButton rbtnBowelNormal;
    @BindView(R.id.rbtn_bowel_altered)
    RadioButton rbtnBowelAltered;
    @BindView(R.id.rbtn_mic_normal)
    RadioButton rbtnMicNormal;
    @BindView(R.id.rbtn_mic_altered)
    RadioButton rbtnMicAltered;
    @BindView(R.id.edt_ph_others)
    AutoCompleteTextView edtPhOthers;
    @BindView(R.id.hide_personal_history)
    Button hidePersonalHistory;
    @BindView(R.id.arrow_family_medicial_history)
    ImageButton arrowFamilyMedicialHistory;
    @BindView(R.id.primary_fmh_layout)
    LinearLayout primaryFmhLayout;
    @BindView(R.id.spinner_fmh_dieases)
    Spinner spinnerFmhDieases;
    @BindView(R.id.button_fmh_selectrelationship)
    Button buttonFmhSelectrelationship;
    @BindView(R.id.edt_fmh_selectedtext)
    EditText edtFmhSelectedtext;
    @BindView(R.id.edt_fmh_others)
    EditText edtFmhOthers;
    @BindView(R.id.hide_family_medical_history)
    Button hideFamilyMedicalHistory;
    @BindView(R.id.arrow_medication_history)
    ImageButton arrowMedicationHistory;
    @BindView(R.id.primary_mh_layout)
    LinearLayout primaryMhLayout;
    @BindView(R.id.edt_mh_treatmentfor)
    MultiAutoCompleteTextView edtMhTreatmentfor;
    @BindView(R.id.edt_mh_medicine_name)
    AutoCompleteTextView edtMhMedicineName;
    @BindView(R.id.spinner_mh_period)
    Spinner spinner_mh_period;
    @BindView(R.id.button_mh_add)
    Button buttonMhAdd;

    //@BindView(R.id.listview_mh)
    //ListView listviewMh;

    @BindView(R.id.hide_medication_history)
    Button hideMedicationHistory;
    @BindView(R.id.arrow_allergy)
    ImageButton arrowAllergy;
    @BindView(R.id.primary_allergy_layout)
    LinearLayout primaryAllergyLayout;
    @BindView(R.id.button_allergy_add)
    Button buttonAllergyAdd;
    @BindView(R.id.edt_allergy_text)
    EditText edtAllergyText;
    @BindView(R.id.edt_allergy_others)
    EditText edtAllergyOthers;
    @BindView(R.id.hide_allergy)
    Button hideAllergy;
    @BindView(R.id.arrow_immunization)
    ImageButton arrowImmunization;
    @BindView(R.id.primary_immunization_layout)
    LinearLayout primaryImmunizationLayout;
    @BindView(R.id.button_immunization)
    Button buttonImmunization;
    //@BindView(R.id.listview_immunization)
    //ListView listviewImmunization;
    @BindView(R.id.hide_immunization)
    Button hideImmunization;
    @BindView(R.id.card_eighth_anchistory)
    CardView cardEighthAnchistory;
    @BindView(R.id.arrow_anc_history)
    ImageButton arrowAncHistory;
    @BindView(R.id.primary_anc_layout)
    LinearLayout primaryAncLayout;
    @BindView(R.id.chkbx_aph)
    CheckBox chkbxAph;
    @BindView(R.id.chkbx_eclampsia)
    CheckBox chkbxEclampsia;
    @BindView(R.id.chkbx_pih)
    CheckBox chkbxPih;
    @BindView(R.id.chkbx_anaemia)
    CheckBox chkbxAnaemia;
    @BindView(R.id.chkbx_obla)
    CheckBox chkbxObla;
    @BindView(R.id.chkbx_pph)
    CheckBox chkbxPph;
    @BindView(R.id.chkbx_lscs)
    CheckBox chkbxLscs;
    @BindView(R.id.chkbx_caib)
    CheckBox chkbxCaib;
    @BindView(R.id.anc_others)
    EditText ancOthers;
    @BindView(R.id.hide_anc_history)
    Button hideAncHistory;
    @BindView(R.id.arrow_other_medical_history)
    ImageButton arrowOtherMedicalHistory;
    @BindView(R.id.primary_others_layout)
    LinearLayout primaryOthersLayout;
    @BindView(R.id.psntilns)
    AutoCompleteTextView psntilns;
    @BindView(R.id.pastilnes)
    AutoCompleteTextView pastilnes;
    @BindView(R.id.any_medicatin)
    AutoCompleteTextView anyMedicatin;
    @BindView(R.id.auto_obset)
    AutoCompleteTextView autoObset;
    @BindView(R.id.auto_gyna)
    AutoCompleteTextView autoGyna;
    @BindView(R.id.edt_surgical_remarks)
    AutoCompleteTextView edtSurgicalRemarks;
    @BindView(R.id.hide_other_medical_history)
    Button hideOtherMedicalHistory;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    @BindView(R.id.button_skip)
    Button buttonSkip;
    @BindView(R.id.button_submit)
    Button buttonSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;

    @BindView(R.id.recyclerView_mh)
    RecyclerView recyclerViewMh;
    List<CommonDataObjects.Clinical_MHData> clinical_mhDataList=new ArrayList<>();

    @BindView(R.id.recylerView_immunization)
    RecyclerView recylerViewImmunization;
    List<CommonDataObjects.TwoData> clinicalImmunizationList=new ArrayList<>();

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Typeface tfavv = null;
    private String FLAG_MYPATIENT = "N/A";
    private String COMMON_PATIENT_ID = null;
    String COMMON_DIAGNOSIS;
    String COMMON_SYMPTOMS;
    String COMMON_DIAGNOSISID = "";

    private String bowelhbt_txt = "";
    private String micturin_txt = "";
    private ArrayList newarray = null;
    private String[] values = null;
    private ArrayList<CharSequence> selectedColours = new ArrayList<>();
    private boolean[] checkedItems1 = null;
    private boolean[] checkedItems2 = null;
    private boolean[] checkedItems3 = null;

    public ClinicalInformation() {
    }

    private static String concatpersonalhistrycnt(List<String> personalarylist2) {
        // TODO Auto-generated method stub
        StringBuilder concatpersonalhistrystr = new StringBuilder();

        for (int j = 0; j < personalarylist2.size(); j++) {
            personalarylist2.get(j);

            concatpersonalhistrystr.append(personalarylist2.get(j)).append('#');

        }
        return concatpersonalhistrystr.toString();
    }

    private static final boolean toggleArrow(View view) {
        if (view.getRotation() == (float) 0) {
            view.animate().setDuration(200L).rotation(180.0F);
            return true;
        } else {
            view.animate().setDuration(200L).rotation((float) 0);
            return false;
        }
    }
    //endregion

    /***
     * MUTHUKUMAR N
     * 29-03-2018
     * @param savedInstanceState
     */

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_clinicalinformation);

        try {

            GETINITIALIZATION();

            CONTROLLISTERNERS();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }
    //endregion

    //region GETINITIALIZATION
    private void GETINITIALIZATION() {

        ButterKnife.bind(ClinicalInformation.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);


        BaseConfig.welcometoast = 0;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final Calendar cc = Calendar.getInstance();
        mcYear = cc.get(Calendar.YEAR);
        mcMonth = cc.get(Calendar.MONTH) + 1;
        mcDay = cc.get(Calendar.DAY_OF_MONTH);

        values = BaseConfig.CurrentLangauges.equalsIgnoreCase("mr") ? new String[]{"निवडा", "Asthma", "Diabetes", "HyperTension", "Hypercholesterolemia", "Stroke/TIA", "Migraine", "TB", "TB Contact", "Others"} : new String[]{"Select", "Asthma", "Diabetes", "HyperTension", "Hypercholesterolemia", "Stroke/TIA", "Migraine", "TB", "TB Contact", "Others"};

        //BaseConfig.LoadDoctorValues();

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.clinical_informaiton)));


        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

     //   toolbar.setBackgroundColor(Color.parseColor(ClinicalInformation.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));

        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(ClinicalInformation.this, null));

        toolbarBack.setOnClickListener(view -> LoadBack());

        Bundle b = getIntent().getExtras();
        if (b != null) {

            String STATUS = b.getString("CONTINUE_STATUS");//To check whether the activity from clinical information;
            COMMON_PATIENT_ID = b.getString("PASSING_PATIENT_ID");
            COMMON_SYMPTOMS = b.getString("PASSING_TREATMENTFOR");
            COMMON_DIAGNOSIS = b.getString("PASSING_DIAGNOSIS");
            FLAG_MYPATIENT = b.getString("FROM");

            if (STATUS != null && STATUS.equalsIgnoreCase("True")) {
                LoadPatientDetails(COMMON_PATIENT_ID);
            }
        } else {
            FLAG_MYPATIENT = "";
        }

        autocompletePatientname.setThreshold(1);
        edtMhTreatmentfor.setThreshold(1);
        edtMhTreatmentfor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        edtMhMedicineName.setThreshold(1);

        String first = getString(R.string.pat_name);
        String next = "<font color='#EE0000'><b>*</b></font>";
        txtvwTitlePatientname.setText(Html.fromHtml(first + next));


        if (BaseConfig.CurrentLangauges.equalsIgnoreCase("mr")) {
            BaseConfig.LoadValuesSpinner(spinnerPhSmoking, this, "select distinct smoking as dvalue from smoking;", "निवडा");
            BaseConfig.LoadValuesSpinner(spinnerPhAlcohol, this, "select distinct replace(smoking,'Pack','') as dvalue from smoking;", "निवडा");
            BaseConfig.LoadValuesSpinner(spinnerPhTobacco, this, "select distinct smoking as dvalue from smoking;", "निवडा");
            BaseConfig.LoadValuesSpinner(spinner_mh_period, this, "select distinct period as dvalue from period;", "निवडा");
            LoadValues(edtMhTreatmentfor, this, "select distinct treatmentfor as dvalue from trmntfor where (isactive='True' or isactive='true' or isactive='1') order by treatmentfor;", 1);

        } else {
            BaseConfig.LoadValuesSpinner(spinnerPhSmoking, this, "select distinct smoking as dvalue from smoking;", "Select");
            BaseConfig.LoadValuesSpinner(spinnerPhAlcohol, this, "select distinct replace(smoking,'Pack','') as dvalue from smoking;", "Select");
            BaseConfig.LoadValuesSpinner(spinnerPhTobacco, this, "select distinct smoking as dvalue from smoking;", "Select");
            BaseConfig.LoadValuesSpinner(spinner_mh_period, this, "select distinct period as dvalue from period;", "Select");
            LoadValues(edtMhTreatmentfor, this, "select distinct treatmentfor as dvalue from trmntfor where (isactive='True' or isactive='true' or isactive='1') order by treatmentfor;", 1);

        }

        BaseConfig.loadSpinner(edtMhTreatmentfor, Loadlist1);
        //BaseConfig.loadSpinner(edtMhMedicineName, Loadlist2);


        LoadAntental();
        LoadAllergicTo();
        LoadMedicalHistoryPOPUP();
        LoadHereditaryDiseases();


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.simple_list, GetArrayList(QUERY_FAMILY_HISTORY));
        spinnerFmhDieases.setAdapter(adapter2);

    }
    //endregion

    //region CONTROLLISTERNERS
    private void CONTROLLISTERNERS() {




        //Set Medical History RecylerView
        recyclerViewMh.setLayoutManager(new LinearLayoutManager(ClinicalInformation.this));
        final KDMCRecyclerAdapter KDMCRecyclerAdapter =new KDMCRecyclerAdapter(clinical_mhDataList,R.layout.kdmc_row_medicalhistory_layout)
                .setRowItemView(new KDMCRecyclerAdapter.AdapterView() {
                    @Override
                    public Object setAdapterView(ViewGroup parent, int viewType, int layoutId) {
                        return new MedicalViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
                    }

                    @Override
                    public void onBindView(Object holder, final int position, Object data, final List<Object> dataList) {

                        MedicalViewHolder myViewHolders= (MedicalViewHolder) holder;
                        final CommonDataObjects.Clinical_MHData value= (CommonDataObjects.Clinical_MHData) data;

                        try {
                            myViewHolders.medicineName.setText(value.getMedicine_Name());
                            myViewHolders.treatmentfor.setText(value.getTreatmentFor());
                            myViewHolders.period.setText(value.getPeriod());

                            myViewHolders.medicineName.setOnClickListener(v -> removeMedicalHistory(position,dataList));
                            myViewHolders.treatmentfor.setOnClickListener(v -> removeMedicalHistory(position,dataList));
                            myViewHolders.period.setOnClickListener(v -> removeMedicalHistory(position,dataList));



                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });
        recyclerViewMh.setAdapter(KDMCRecyclerAdapter);


        //Set Immunization RecylerView
        recylerViewImmunization.setLayoutManager(new LinearLayoutManager(ClinicalInformation.this));
        final KDMCRecyclerAdapter KDMCRecyclerAdapter1 =new KDMCRecyclerAdapter(clinicalImmunizationList,R.layout.kdmc_row_iimunization_layout)
                .setRowItemView(new KDMCRecyclerAdapter.AdapterView() {
                    @Override
                    public Object setAdapterView(ViewGroup parent, int viewType, int layoutId) {
                        return new ImmunizationViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
                    }

                    @Override
                    public void onBindView(Object holder, final int position, Object data, final List<Object> dataList) {

                        ImmunizationViewHolder myViewHolders= (ImmunizationViewHolder) holder;
                        final CommonDataObjects.TwoData value= (CommonDataObjects.TwoData) data;

                        try {
                            myViewHolders.text1.setText(value.getData1());
                            myViewHolders.text2.setText(value.getData2());


                            myViewHolders.text1.setOnClickListener(v -> removeImmunization(position,dataList));
                            myViewHolders.text2.setOnClickListener(v -> removeImmunization(position,dataList));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });
        recylerViewImmunization.setAdapter(KDMCRecyclerAdapter1);

        arrowPrevmedhistory.setOnClickListener(view -> toggleSectionInput(arrowPrevmedhistory, primaryPmsLayout));

        hidePrevmedhistory.setOnClickListener(view -> toggleSectionInput(arrowPrevmedhistory, primaryPmsLayout));

        arrowHereditaryDiseases.setOnClickListener(view -> toggleSectionInput(arrowHereditaryDiseases, primaryHdLayout));

        hideHereditaryDiseases.setOnClickListener(view -> toggleSectionInput(arrowHereditaryDiseases, primaryHdLayout));

        arrowPersonalHistory.setOnClickListener(view -> toggleSectionInput(arrowPersonalHistory, primaryPhLayout));

        hidePersonalHistory.setOnClickListener(view -> toggleSectionInput(arrowPersonalHistory, primaryPhLayout));

        arrowFamilyMedicialHistory.setOnClickListener(view -> toggleSectionInput(arrowFamilyMedicialHistory, primaryFmhLayout));

        hideFamilyMedicalHistory.setOnClickListener(view -> toggleSectionInput(arrowFamilyMedicialHistory, primaryFmhLayout));

        arrowMedicationHistory.setOnClickListener(view -> toggleSectionInput(arrowMedicationHistory, primaryMhLayout));

        hideMedicationHistory.setOnClickListener(view -> toggleSectionInput(arrowMedicationHistory, primaryMhLayout));

        arrowAllergy.setOnClickListener(view -> toggleSectionInput(arrowAllergy, primaryAllergyLayout));

        hideAllergy.setOnClickListener(view -> toggleSectionInput(arrowAllergy, primaryAllergyLayout));

        arrowImmunization.setOnClickListener(view -> toggleSectionInput(arrowImmunization, primaryImmunizationLayout));

        hideImmunization.setOnClickListener(view -> toggleSectionInput(arrowImmunization, primaryImmunizationLayout));

        arrowAncHistory.setOnClickListener(view -> toggleSectionInput(arrowAncHistory, primaryAncLayout));

        hideAncHistory.setOnClickListener(view -> toggleSectionInput(arrowAncHistory, primaryAncLayout));

        arrowOtherMedicalHistory.setOnClickListener(view -> toggleSectionInput(arrowOtherMedicalHistory, primaryOthersLayout));

        hideOtherMedicalHistory.setOnClickListener(view -> toggleSectionInput(arrowOtherMedicalHistory, primaryOthersLayout));


        edtMhMedicineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (edtMhMedicineName.getText().toString().length() > 0) {

                    String Query = "SELECT (CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims where (Schedule_Category='Schedule 2' or Schedule_Category='Schedule 3')";
                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, ClinicalInformation.this, edtMhMedicineName, charSequence.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        buttonSkip.setOnClickListener(v -> showskip());

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            public void onClick(View view) {

                if (count == 1) {
                    count = 0;

                    ClinicalInformation.this.finish();
                    Intent intent = new Intent(getApplicationContext(), Dashboard_NavigationMenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.press_again, Toast.LENGTH_SHORT).show();
                    count++;
                }
            }
        });


        //listviewMh.setOnItemClickListener((parent, view, position, id) -> ConfirmDeletemedicationD(position, personalarylist, listviewMh));


        //listviewImmunization.setOnItemClickListener((parent, view, position, id) -> ConfirmDeletemedicationD(position, list, listviewImmunization));


        buttonMhAdd.setOnClickListener(view -> {

            if (edtMhTreatmentfor.getText().length() > 0 && edtMhMedicineName.getText().length() > 0 && spinner_mh_period.getSelectedItemPosition()>0) {
                AddListItem();
            } else {
                BaseConfig.showSimplePopUp(getString(R.string.select_value), getString(R.string.warning), ClinicalInformation.this, R.drawable.ic_warning_black_24dp, R.color.orange_400);
            }

        });


        buttonFmhSelectrelationship.setOnClickListener(v -> {
            try {
                if (spinnerFmhDieases.getSelectedItemPosition() > 0) {
                    BaseConfig.LoadPopupValues(getApplicationContext(), "select members as dvalue from FamilyRelationship order by members;", "6");
                    BaseConfig.showSelectColoursDialogFamilyRelation(ClinicalInformation.this, edtFmhSelectedtext, "Family", spinnerFmhDieases.getSelectedItem().toString());
                } else {
                    Toast.makeText(ClinicalInformation.this, R.string.select_disease, Toast.LENGTH_LONG).show();

                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //Immunizations
        buttonImmunization.setOnClickListener(new View.OnClickListener() {

            CharSequence[] items = null;

            @Override
            public void onClick(View view) {
                BaseConfig.HideKeyboard(buttonImmunization.getContext());
                LoadImmunization();
                showSelectColoursDialog();
            }

            private void LoadImmunization() {

                SQLiteDatabase db = BaseConfig.GetDb();//);
                Cursor c = db.rawQuery("select distinct ifnull(vaccinename,'')as vaccinename,ifnull(schedule,'')as schedule  from listofvaccine where isactive='1' order by vaccinename;", null);   List<String> list = new ArrayList<>();
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            String vaccinename="",schedule="";

                            vaccinename=c.getString(c.getColumnIndex("vaccinename"));
                            schedule=c.getString(c.getColumnIndex("schedule"));

                            String pname;
                            if (vaccinename.length()> 0 && schedule.length()>0 ) {
                                pname = c.getString(c.getColumnIndex("vaccinename")) + "->" + c.getString(c.getColumnIndex("schedule"));

                            }
                            else
                            {
                                pname = c.getString(c.getColumnIndex("vaccinename"));
                            }

                            list.add(pname);

                        } while (c.moveToNext());
                    }
                }

                new ArrayAdapter<>(ClinicalInformation.this, android.R.layout.simple_list_item_1, list);

                items = list.toArray(new String[list.size()]);

                db.close();
                c.close();
            }

            private void showSelectColoursDialog() {

                boolean[] checkedColours = new boolean[items.length];
                int count = items.length;
                // stringBuilder=new StringBuilder();
                //selectedColours.clear();
                int i = 0;
                while (i < count) {
                    checkedColours[i] = selectedColours.contains(items[i]);
                    i++;
                }

                DialogInterface.OnMultiChoiceClickListener coloursDialogListener = (dialog, which, isChecked) -> {

                    if (isChecked) {
                        selectedColours.add(items[which]);

                    } else {
                        //Toast.makeText(getApplicationContext(), "removed", Toast.LENGTH_LONG).show();
                        selectedColours.remove(items[which]);

                    }

                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ClinicalInformation.this);
                builder.setTitle(R.string.immune_list);

                builder.setMultiChoiceItems(items, checkedColours,
                        coloursDialogListener);
                builder.setPositiveButton(R.string.ok, (dialog, which) -> {

                    StringBuilder stringBuilder = new StringBuilder();

                    for (Iterator<CharSequence> iterator = selectedColours.iterator(); iterator.hasNext(); ) {
                        CharSequence colour = iterator.next();

                        stringBuilder.append(colour).append("\n");
                    }


                    String[] splt = stringBuilder.toString().split("\n");

                    list.clear();

                    if (splt.length > 0) {

                        for (String aSplt : splt) {

                            list.add(aSplt);

                            String[] Datas = null;
                            String Data1 = "", Data2 = "";

                            if (aSplt.contains("->")) {

                                Datas = aSplt.split("->");

                                try
                                {
                                    Data1 = Datas[0];
                                    Data2 = Datas[1];
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                            } else {

                                    Data1 = aSplt;
                                    Data2 = "-";

                            }

                            clinicalImmunizationList.add(new CommonDataObjects.TwoData(Data1, Data2));

                        }

                        recylerViewImmunization.getAdapter().notifyDataSetChanged();

                    }

                });
                builder.setNegativeButton(R.string.cancel,
                        (dialog, which) -> new StringBuilder());
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        imgvwPatientphoto.setOnClickListener(v -> {


            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {


                BaseConfig.Show_Patient_PhotoInDetail(autocompletePatientname.getText().toString().split("-")[0], autocompletePatientname.getText().toString().split("-")[1], tvwAgegender.getText().toString(), ClinicalInformation.this);


            }
        });


        autocompletePatientname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (autocompletePatientname.getText().toString().length() > 0) {

                    BaseConfig.SelectedGetPatientDetailsFilter("select name,Patid from patreg order by name", ClinicalInformation.this, autocompletePatientname, charSequence.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Drawable rightDrawable = AppCompatResources.getDrawable(autocompletePatientname.getContext(), R.drawable.ic_clear_button_white);
                if (autocompletePatientname.getText().length() > 0) {
                    autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    autocompletePatientname.setOnTouchListener((v, event) -> {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (float) (autocompletePatientname.getRight() - autocompletePatientname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {


                                ClearAll();

                                return true;
                            }
                        }
                        return false;
                    });

                } else {
                    autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                    autocompletePatientname.setOnTouchListener(null);

                }


            }
        });


        autocompletePatientname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i,
                                    long l) {

                try {


                    SelectedGetPatientDetails();


                 GetClinicalInfo_PreviousData();


                } catch (RuntimeException ignored) {
                    //e.printStackTrace();

                }
            }


            ////////////////////////////////////////////////////////////////////////////////////////////////////


            void SelectedGetPatientDetails() {

                String[] pna = autocompletePatientname.getText().toString().split("-");

                String Gender = BaseConfig.GetValues("select gender as ret_values from Patreg where Patid='" + pna[1] + '\'');
                if (Gender.equalsIgnoreCase("Female")) {
                    cardEighthAnchistory.setVisibility(View.VISIBLE);
                } else {
                    cardEighthAnchistory.setVisibility(View.GONE);
                }

                String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + pna[1] + '\'');
                String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + pna[1] + '\'');


                tvwAgegender.setText(Patient_AgeGender);

                BaseConfig.Glide_LoadImageView(imgvwPatientphoto, Str_Patient_Photo);


            }


            //////////////////////////////////////////////////////////////////////////////////////////////////
            private void GetClinicalInfo_PreviousData() {
                String[] patientidinfo = autocompletePatientname.getText().toString().split("-");

                String Antental_Values = "";
                String Antental_Others = "";

                try {
                    SQLiteDatabase db = BaseConfig.GetDb();//);
                    String Query = "select substr(Actdate,0,11) as actdate,* from clinicalInformation  where ptid='" + patientidinfo[1].trim() + "' order by id desc limit 1;";
                    Cursor c = db.rawQuery(Query, null);
                    if (c != null) {
                        if (c.moveToFirst()) {

                            do {


                                edtPrevmedhistory.setText(c.getString(c.getColumnIndex("Previous_MedicalHistory")));

                                String[] arr = c.getString(c.getColumnIndex("Previous_MedicalHistory")).split("\n");
                                if (arr.length > 0) {
                                    for (String anArr : arr) {
                                        BaseConfig bc = new BaseConfig();
                                        bc.selectedColours1.add(anArr);
                                    }
                                }


                                edtHereditaryDiseases.setText(c.getString(c.getColumnIndex("Hereditary")));

                                String[] arr1 = c.getString(c.getColumnIndex("Hereditary")).split("\n");
                                if (arr1.length > 0) {
                                    Collections.addAll(BaseConfig.selectedColours2, arr1);
                                }


                                edtAllergyText.setText(c.getString(c.getColumnIndex("AllergicTo")));


                                String[] arr2 = c.getString(c.getColumnIndex("AllergicTo")).split("\n");
                                if (arr2.length > 0) {
                                    for (String anArr2 : arr2) {
                                        BaseConfig bc = new BaseConfig();
                                        bc.selectedColours.add(anArr2);
                                    }
                                }

                                edtFmhSelectedtext.setText(c.getString(c.getColumnIndex("Family_Med_History")));


                                for (int i = 0; i < spinnerPhSmoking.getCount(); i++) {
                                    if (c.getString(c.getColumnIndex("Perhis_Smoking")).equals(spinnerPhSmoking.getItemAtPosition(i))) {
                                        spinnerPhSmoking.setSelection(i);
                                        break;
                                    } else {
                                        spinnerPhSmoking.setSelection(0);
                                    }

                                }


                                for (int i = 0; i < spinnerPhAlcohol.getCount(); i++) {
                                    if (c.getString(c.getColumnIndex("Perhis_Alcohol")).equals(spinnerPhAlcohol.getItemAtPosition(i))) {
                                        spinnerPhAlcohol.setSelection(i);
                                        break;
                                    } else {
                                        spinnerPhAlcohol.setSelection(0);
                                    }

                                }

                                for (int i = 0; i < spinnerPhTobacco.getCount(); i++) {
                                    if (c.getString(c.getColumnIndex("Perhis_Tobacco")).equals(spinnerPhTobacco.getItemAtPosition(i))) {
                                        spinnerPhTobacco.setSelection(i);
                                        break;
                                    } else {
                                        spinnerPhTobacco.setSelection(0);
                                    }

                                }

                                edtPhOthers.setText(c.getString(c.getColumnIndex("Perhis_Others")));


                                if (c.getString(c.getColumnIndex("Perhis_BowelHabits")).equalsIgnoreCase("Normal")) {
                                    rbtnBowelNormal.setChecked(true);
                                } else if (c.getString(c.getColumnIndex("Perhis_BowelHabits")).equalsIgnoreCase("Altered")) {
                                    rbtnBowelAltered.setChecked(true);
                                }


                                if (c.getString(c.getColumnIndex("Perhis_Micturition")).equalsIgnoreCase("Normal")) {
                                    rbtnMicNormal.setChecked(true);
                                } else if (c.getString(c.getColumnIndex("Perhis_Micturition")).equalsIgnoreCase("Altered")) {
                                    rbtnMicAltered.setChecked(true);
                                }


                                // Updated # for splitting values
                                String[] arr3 = c.getString(c.getColumnIndex("Medication_History")).split("#");

                                  //  clinical_mhDataList.add(new CommonDataObjects.Clinical_MHData(edtMhTreatmentfor.getText().toString(),edtMhMedicineName.getText().toString(),spinner_mh_period.getSelectedItem().toString()));


                                psntilns.setText(c.getString(c.getColumnIndex("present_illness")));
                                pastilnes.setText(c.getString(c.getColumnIndex("past_illness")));
                                anyMedicatin.setText(c.getString(c.getColumnIndex("any_medication")));
                                autoObset.setText(c.getString(c.getColumnIndex("obstetric")));
                                autoGyna.setText(c.getString(c.getColumnIndex("gynaec")));
                                edtSurgicalRemarks.setText(c.getString(c.getColumnIndex("surgical_notes")));


                                //Load Antental values
                                Antental_Values = c.getString(c.getColumnIndex("Antental_val"));
                                Antental_Others = c.getString(c.getColumnIndex("Antental_others"));

                                if (Antental_Values.contains(",")) {
                                    String[] Antentalvalues_arr = Antental_Values.split(",");
                                    for (String aAntentalvalues_arr : Antentalvalues_arr) {
                                        if (aAntentalvalues_arr.equalsIgnoreCase("APH")) {
                                            chkbxAph.setChecked(true);
                                        }

                                        if (aAntentalvalues_arr.equalsIgnoreCase("Eclampsia")) {
                                            chkbxEclampsia.setChecked(true);
                                        }

                                        if (aAntentalvalues_arr.equalsIgnoreCase("PIH")) {
                                            chkbxPih.setChecked(true);
                                        }

                                        if (aAntentalvalues_arr.equalsIgnoreCase("Anaemia")) {
                                            chkbxAnaemia.setChecked(true);
                                        }

                                        if (aAntentalvalues_arr.equalsIgnoreCase("Obstructed labor")) {
                                            chkbxObla.setChecked(true);
                                        }

                                        if (aAntentalvalues_arr.equalsIgnoreCase("PPH")) {
                                            chkbxPph.setChecked(true);
                                        }

                                        if (aAntentalvalues_arr.equalsIgnoreCase("LSCS")) {
                                            chkbxLscs.setChecked(true);
                                        }

                                        if (aAntentalvalues_arr.equalsIgnoreCase("Congenital anomaly in baby")) {
                                            chkbxCaib.setChecked(true);
                                        }

                                    }
                                }


                            } while (c.moveToNext());


                        }
                    }
                    c.close();


                    ancOthers.setText(Antental_Others);


                    list = new ArrayList<>();
                    Cursor c1 = db.rawQuery("select (vaccinename||'->'||schedule)as dvalue,vaccinename, schedule from Vaccination where patid='" + patientidinfo[1].trim() + "' and givendt!='';", null);
                    if (c1 != null) {
                        if (c1.moveToFirst()) {
                            do {

                                String counrtyname = c1.getString(c1.getColumnIndex("dvalue"));
                                list.add(counrtyname);
                                selectedColours.add(counrtyname);

                                String VaccineName = c1.getString(c1.getColumnIndex("vaccinename"));
                                String Vaccine_Schedule = c1.getString(c1.getColumnIndex("schedule"));
                                clinicalImmunizationList.add(new CommonDataObjects.TwoData(VaccineName, Vaccine_Schedule));


                            } while (c1.moveToNext());
                        }
                    }

                    //To load medical history
                    Objects.requireNonNull(recyclerViewMh.getAdapter()).notifyDataSetChanged();

                    //To load immunization history
                    recylerViewImmunization.getAdapter().notifyDataSetChanged();


                    c1.close();

                    db.close();
                } catch (RuntimeException ignored) {
                    //e.printStackTrace();
                }
            }

        });


        buttonSubmit.setOnClickListener(v -> {


            if (autocompletePatientname.getText().length() > 0) {

                String[] Pat = autocompletePatientname.getText().toString().split("-");
                if (Pat.length == 2) {

                    boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + '\'');
                    if (q) {
                        SAVELOCAL();
                    } else {
                        BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), ClinicalInformation.this, R.drawable.ic_patienticon, R.color.orange_500);
                    }


                } else if (Pat.length == 1) {

                    boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + '\'');
                    if (q) {
                        SAVELOCAL();
                    } else {
                        BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), ClinicalInformation.this, R.drawable.ic_patienticon, R.color.red_300);
                    }

                }


            } else {
                BaseConfig.showSimplePopUp(getString(R.string.enteR_pat_name), getString(R.string.info), ClinicalInformation.this, R.drawable.ic_patienticon, R.color.red_300);
                autocompletePatientname.requestFocus();
            }
        });

        buttonAllergyAdd.setOnClickListener(v -> {
            try {

                BaseConfig.HideKeyboard(buttonMhAdd.getContext());

                POPUP_Allergy();


            } catch (RuntimeException ignored) {

            }
        });

        imgbutotnPrevmedhistory.setOnClickListener(v -> {
            try {

                BaseConfig.HideKeyboard(imgbutotnPrevmedhistory.getContext());

                POPUP_MedicalHistory();


            } catch (RuntimeException ignored) {

            }
        });

        buttonHereditaryDiseases.setOnClickListener(v -> {
            try {

                BaseConfig.HideKeyboard(buttonHereditaryDiseases.getContext());

                POPUP_Hereditary();


            } catch (RuntimeException ignored) {


            }
        });


    }//End Controllisteners

    //region LoadPatientDetails
    private final void LoadPatientDetails(String PatientId) {

        try {
            String Str_Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PatientId + '\'');
            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PatientId + '\'');
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + PatientId + '\'');

            autocompletePatientname.setText(String.format("%s-%s", Str_Patient_Name, PatientId));
            tvwAgegender.setText(Patient_AgeGender);
            BaseConfig.LoadPatientImage(Str_Patient_Photo, imgvwPatientphoto, 100);
            autocompletePatientname.setEnabled(false);

            String Gender = BaseConfig.GetValues("select gender as ret_values from Patreg where Patid='" + PatientId + "'");
            if (Gender.equalsIgnoreCase("Female")) {
                cardEighthAnchistory.setVisibility(View.VISIBLE);
            } else {
                cardEighthAnchistory.setVisibility(View.GONE);
            }


        } catch (Exception ignored) {
            //  e.printStackTrace();
        }

    }


    private final void ClearAll() {

        autocompletePatientname.setText("");

        //
        BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);
        tvwAgegender.setText("-");
        autocompletePatientname.setEnabled(true);

        edtPrevmedhistory.setText("");
        edtHereditaryDiseases.setText("");
        edtAllergyText.setText("");
        edtFmhSelectedtext.setText("");
        spinnerPhSmoking.setSelection(0);
        spinnerPhAlcohol.setSelection(0);
        spinnerPhTobacco.setSelection(0);
        list.clear();

        BaseConfig bc = new BaseConfig();
        bc.selectedColours = new ArrayList<>();
        bc.selectedColours1 = new ArrayList<>();
        BaseConfig.selectedColours2 = new ArrayList<>();
        selectedColours = new ArrayList<>();
    }

    private void AddListItem() {

        String mdub = "";
        String mdub1 = edtMhTreatmentfor.getText().toString() + " _ " + edtMhMedicineName.getText().toString() + " _ " + spinner_mh_period.getSelectedItem().toString();

        if (checklistforduplicate1(mdub1)) {

            clinical_mhDataList.add(new CommonDataObjects.Clinical_MHData(edtMhTreatmentfor.getText().toString(),edtMhMedicineName.getText().toString(),spinner_mh_period.getSelectedItem().toString()));
            Objects.requireNonNull(recyclerViewMh.getAdapter()).notifyDataSetChanged();

            personalarylist.add(mdub1);
/*



            ArrayAdapter<String> listadapter = new ArrayAdapter<>(ClinicalInformation.this, android.R.layout.simple_list_item_1, personalarylist);
            listviewMh.setAdapter(listadapter);
*/

            edtMhTreatmentfor.setText("");
            edtMhMedicineName.setText("");
            spinner_mh_period.setSelection(0);
            edtMhTreatmentfor.requestFocus();

        } else {
            BaseConfig.showSimplePopUp(getString(R.string.select_list), getString(R.string.warning), ClinicalInformation.this, R.drawable.ic_warning_black_24dp, R.color.orange_400);
        }

    }

    private boolean checklistforduplicate1(String Str) {
        int flag = 1;
        if (personalarylist.size() > 0) {
            for (int i = 0; i < personalarylist.size(); i++) {

                if (personalarylist.get(i).trim().equalsIgnoreCase(Str)) {
                    flag = 0;
                    break;
                }
            }

        }

        return flag != 0;
    }

    private void ConfirmDeletemedicationD(final int position, final List<String> personalarylist2, final ListView medihistryaddlist2) {

        new CustomKDMCDialog(ClinicalInformation.this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_delete_forever_black_24dp)
                .setTitle(ClinicalInformation.this.getResources().getString(R.string.delete_confirmation))
                .setDescription(ClinicalInformation.this.getResources().getString(R.string.do_you_want_to_delete))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {

                    personalarylist2.remove(position);
                    ArrayAdapter<String> listadapter = new ArrayAdapter<>(ClinicalInformation.this, android.R.layout.simple_list_item_1, personalarylist2);
                    medihistryaddlist2.setAdapter(listadapter);

                });

    }

    private final void showskip() {


        new CustomKDMCDialog(ClinicalInformation.this)
                .setLayoutColor(R.color.blue_300)
                .setImage(R.drawable.ic_skip_next_black_24dp)
                .setTitle(ClinicalInformation.this.getResources().getString(R.string.str_information))
                .setDescription(ClinicalInformation.this.getResources().getString(R.string.skip_message))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {

                    Toast.makeText(getApplicationContext(), R.string.skipped_toinvestigation, Toast.LENGTH_SHORT).show();

                    ClinicalInformation.this.finish();
                    Intent intent = new Intent(getApplicationContext(), Investigations.class);
                    if (autocompletePatientname.getText().length() > 0) {
                        intent.putExtra("CONTINUE_STATUS", "True");
                        intent.putExtra("PASSING_DIAGNOSISID", COMMON_DIAGNOSISID);
                        intent.putExtra("PASSING_TREATMENTFOR", COMMON_SYMPTOMS);
                        intent.putExtra("PASSING_DIAGNOSIS", COMMON_DIAGNOSIS);
                        intent.putExtra("PASSING_PATIENT_ID", autocompletePatientname.getText().toString().split("-")[1]);
                    }
                    startActivity(intent);

                });

    }

    private final void SAVELOCAL() {


        try {

            if (checkValidation())


                SUBMIT_FORM();
            else
                Toast.makeText(ClinicalInformation.this,
                        R.string.missing, Toast.LENGTH_LONG)
                        .show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Updated Insert Query to ContentValues @ Muthukumar & Vidhya
     * 25-03-2017
     */
    private void SUBMIT_FORM() {


        try {
            SQLiteDatabase db = BaseConfig.GetDb();//);

            String tobacco_value = "";

            if (spinnerPhTobacco.getSelectedItemPosition() > 0) {
                tobacco_value = spinnerPhTobacco.getSelectedItem().toString();
            }


            String[] pna = autocompletePatientname.getText().toString().split("-");
            String[] pag = tvwAgegender.getText().toString().split("-");

            String personalhistrycnt = concatpersonalhistrycnt(personalarylist);


            String smoke = "", alcohol = "";
            if (spinnerPhSmoking.getSelectedItemPosition() > 0) {
                smoke = spinnerPhSmoking.getSelectedItem().toString();
            }
            if (spinnerPhAlcohol.getSelectedItemPosition() > 0) {
                alcohol = spinnerPhAlcohol.getSelectedItem().toString();
            }

            String surgicalNotes = edtSurgicalRemarks.getText().toString();
            if (surgicalNotes.length()==0) {
                surgicalNotes = "";
            }

            String result = android.text.TextUtils.join(",", antental_list);


            long lStartTime = System.nanoTime();
            ContentValues values = new ContentValues();
            values.put("Clinical_ID", BaseConfig.GenerateClinicalID());
            values.put("ptid", pna[1].trim());
            values.put("pname", pna[0].trim());
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", Integer.valueOf(1));
            values.put("pagegen", tvwAgegender.getText().toString());
            values.put("Isupdate", Integer.valueOf(0));
            values.put("Previous_MedicalHistory", edtPrevmedhistory.getText().toString() + ',' + edtPrevmedhistoryOthers.getText().toString());
            values.put("Hereditary", edtHereditaryDiseases.getText().toString() + ',' + edtHereditaryDiseasesOthers.getText().toString());
            values.put("AllergicTo", edtAllergyText.getText().toString() + ',' + edtAllergyOthers.getText().toString());
            values.put("Perhis_Smoking", smoke);
            values.put("Perhis_Alcohol", alcohol);
            values.put("Perhis_Tobacco", tobacco_value);
            values.put("Perhis_Others", edtPhOthers.getText().toString());
            values.put("Perhis_BowelHabits", bowelhbt_txt);
            values.put("Perhis_Micturition", micturin_txt);
            values.put("Medication_History", personalhistrycnt);
            values.put("Family_Med_History", edtFmhSelectedtext.getText().toString() + ',' + edtFmhOthers.getText().toString());
            values.put("present_illness", psntilns.getText().toString());
            values.put("past_illness", pastilnes.getText().toString());
            values.put("any_medication", anyMedicatin.getText().toString());
            values.put("obstetric", autoObset.getText().toString());
            values.put("gynaec", autoGyna.getText().toString());
            values.put("surgical_notes", surgicalNotes);
            values.put("HID", BaseConfig.HID);
            values.put("Antental_val", result);
            values.put("Antental_others", ancOthers.getText().toString());
            db.insert(BaseConfig.TABLE_CLINICAL_INFORMATION, null, values);


            // execution finised
            long lEndTime = System.nanoTime();
            // display execution time
            long timeElapsed = lEndTime - lStartTime;
            Log.e("Time Taken: ", String.valueOf(timeElapsed / 1000000L));

            if (!edtPrevmedhistoryOthers.getText().toString().isEmpty()) {
                ContentValues values1 = new ContentValues();
                values1.put("prvmedhis", edtPrevmedhistoryOthers.getText().toString());
                db.insert(BaseConfig.TABLE_CLINICAL_MHIS, null, values1);

            }
            if (!edtAllergyOthers.getText().toString().isEmpty()) {

                ContentValues values1 = new ContentValues();
                values1.put("allergy", edtAllergyOthers.getText().toString());
                db.insert(BaseConfig.TABLE_CLINICAL_ALLERGY, null, values1);

            }
            if (!edtHereditaryDiseasesOthers.getText().toString().isEmpty()) {
                ContentValues values1 = new ContentValues();
                values1.put("Disease", edtHereditaryDiseasesOthers.getText().toString());
                db.insert(BaseConfig.TABLE_CLINICAL_HEREDITARYDIEASES, null, values1);

            }


            //Update Vaccination Given date"-"
            UpdateVaccination(pna[1]);

            Toast.makeText(this, R.string.success_message_clinical, Toast.LENGTH_SHORT).show();

            ClinicalInformation.this.finish();
            Intent intent = new Intent(getApplicationContext(), Investigations.class);
            intent.putExtra("CONTINUE_STATUS", "True");
            intent.putExtra("PASSING_DIAGNOSISID", COMMON_DIAGNOSISID);
            intent.putExtra("PASSING_TREATMENTFOR", COMMON_SYMPTOMS);
            intent.putExtra("PASSING_DIAGNOSIS", COMMON_DIAGNOSIS);
            intent.putExtra("PASSING_PATIENT_ID", autocompletePatientname.getText().toString().split("-")[1]);
            CustomIntent.customType(ClinicalInformation.this, 4);
            startActivity(intent);

            db.close();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception ignored) {
            //e.printStackTrace();
        }
    }

    /**
     * New Method to generate sqlite transaction
     * Muthu & Ponnu
     */

    private void NewInsert(SQLiteDatabase db, String tobacco_value, String[] pna, String personalhistrycnt, String smoke, String alcohol, String surgicalNotes, String result) throws JSONException {

        // keep track of execution time
        long lStartTime = System.nanoTime();
        JSONObject values = new JSONObject();
        values.put("ptid", pna[1].trim());
        values.put("pname", pna[0].trim());
        values.put("docid", BaseConfig.doctorId);
        values.put("Actdate", BaseConfig.DeviceDate());
        values.put("IsActive", 1);
        values.put("pagegen", tvwAgegender.getText().toString());
        values.put("Isupdate", 0);
        values.put("Previous_MedicalHistory", edtPrevmedhistory.getText().toString() + '\n' + edtPrevmedhistoryOthers.getText().toString());
        values.put("Hereditary", edtHereditaryDiseases.getText().toString() + '\n' + edtHereditaryDiseasesOthers.getText().toString());
        values.put("AllergicTo", edtAllergyText.getText().toString() + '\n' + edtAllergyOthers.getText().toString());
        values.put("Perhis_Smoking", smoke);
        values.put("Perhis_Alcohol", alcohol);
        values.put("Perhis_Tobacco", tobacco_value);
        values.put("Perhis_Others", edtPhOthers.getText().toString());
        values.put("Perhis_BowelHabits", bowelhbt_txt);
        values.put("Perhis_Micturition", micturin_txt);
        values.put("Medication_History", personalhistrycnt);
        values.put("Family_Med_History", edtFmhSelectedtext.getText().toString() + '\n' + edtFmhOthers.getText().toString());
        values.put("present_illness", psntilns.getText().toString());
        values.put("past_illness", pastilnes.getText().toString());
        values.put("any_medication", anyMedicatin.getText().toString());
        values.put("obstetric", autoObset.getText().toString());
        values.put("gynaec", autoGyna.getText().toString());
        values.put("surgical_notes", surgicalNotes);
        values.put("HID", BaseConfig.HID);
        values.put("Antental_val", result);
        values.put("Antental_others", ancOthers.getText().toString());

        StringBuilder columnNames = new StringBuilder(" (");
        StringBuilder valuesQuestion = new StringBuilder(" (");
        Iterator<String> iter = values.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            columnNames.append(key).append(',');
            valuesQuestion.append("?,");

        }

        columnNames = new StringBuilder(columnNames.substring(0, columnNames.length() - 1));
        valuesQuestion = new StringBuilder(valuesQuestion.substring(0, valuesQuestion.length() - 1));

        columnNames.append(") ");
        valuesQuestion.append(") ");

        String sql = "INSERT OR REPLACE INTO " + BaseConfig.TABLE_CLINICAL_INFORMATION + ' ' + columnNames + " values " + valuesQuestion;

        db.beginTransactionNonExclusive();


        SQLiteStatement stmt = db.compileStatement(sql);
        int position = 1;
        Iterator<String> iter1 = values.keys();
        while (iter1.hasNext()) {
            String key = iter1.next();

            stmt.bindString(position, values.getString(key));

            position++;

        }


        stmt.execute();
        stmt.clearBindings();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        // execution finised
        long lEndTime = System.nanoTime();
        // display execution time
        long timeElapsed = lEndTime - lStartTime;
        Log.e("Time Taken: ", String.valueOf(timeElapsed / 1000000L));

    }

    private final void UpdateVaccination(String patientRegId) {
        SQLiteDatabase db = BaseConfig.GetDb();//);
        ContentValues values;

        SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        String dttm = dateformt.format(date);

        String[] pna = autocompletePatientname.getText().toString().split("-");
        for (int j = 0; j < recylerViewImmunization.getAdapter().getItemCount(); j++) {


           // String[] arrins = clinicalImmunizationList.get(j).getData1().toString().split("->");

            if (clinicalImmunizationList.get(j).getData1().length()>1) {
                values = new ContentValues();
                values.put("givendt", "-");
                values.put("weight", "");
                values.put("duedt", "00/00/0000 00:00:00");
                values.put("isactive", Integer.valueOf(1));
                values.put("Isupdate", Integer.valueOf(1));
                values.put("dt", dttm);
                db.update(BaseConfig.TABLE_PATIENT_VACCINATION, values, "vaccinename='" + clinicalImmunizationList.get(j).getData1() + "' and patid='" + pna[1].trim() + "' and " +
                        "isactive='0' and Isupdate='0'", null);


            } else {
                values = new ContentValues();
                values.put("givendt", "-");
                values.put("weight", "");
                values.put("duedt", "00/00/0000 00:00:00");
                values.put("isactive", Integer.valueOf(1));
                values.put("Isupdate", Integer.valueOf(1));
                values.put("dt", dttm);
                db.update(BaseConfig.TABLE_PATIENT_VACCINATION, values, "vaccinename='" + clinicalImmunizationList.get(j).getData1() + "' and schedule='" + clinicalImmunizationList.get(j).getData2() + "' and patid='" + pna[1].trim() + "' and " +
                        "isactive='0' and Isupdate='0'", null);


            }
        }

        db.close();

    }

    private boolean checkValidation() {

        boolean ret = true;

        if (!Validation1.hasText(autocompletePatientname))
            ret = false;


        return ret;
    }

    private void toggleSectionInput(View view, View PrimaryLayout) {
        boolean show = toggleArrow(view);
        if (show) {
           // ViewAnimation.expand(PrimaryLayout, () -> Tools.nestedScrollTo(clinicalinformationNesetedscrollview, PrimaryLayout));
            ViewAnimation.expand(PrimaryLayout);
        } else {
            ViewAnimation.collapse(PrimaryLayout);
        }
    }

    private final void LoadAntental() {

        LoadCheckBox(chkbxAph);
        LoadCheckBox(chkbxPih);
        LoadCheckBox(chkbxObla);
        LoadCheckBox(chkbxLscs);
        LoadCheckBox(chkbxEclampsia);
        LoadCheckBox(chkbxAnaemia);
        LoadCheckBox(chkbxPph);
        LoadCheckBox(chkbxCaib);

    }

    private final void LoadCheckBox(CheckBox chk) {


        chk.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                chk.setChecked(true);
                antental_list.add(compoundButton.getText().toString());
            } else {
                chk.setChecked(false);
                antental_list.remove(compoundButton.getText().toString());
            }

        });

    }

    /* Loading Autocomplete values */
    private final void LoadValues(AutoCompleteTextView autotxt, Context cntxt, String Query, int id) {


        try {

            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        if (id == 1) {
                            String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                            Loadlist1.add(counrtyname);
                        } else if (id == 2) {
                            String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                            Loadlist2.add(counrtyname);
                        }

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    public final void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbtn_bowel_normal:

                if (checked)
                    bowelhbt_txt = "Normal";
                rbtnBowelAltered.setChecked(false);

                break;
            case R.id.rbtn_bowel_altered:
                if (checked)
                    bowelhbt_txt = "Altered";
                rbtnBowelNormal.setChecked(false);

                break;
            case R.id.rbtn_mic_normal:

                if (checked)
                    micturin_txt = "Normal";
                rbtnMicAltered.setChecked(false);

                break;
            case R.id.rbtn_mic_altered:
                if (checked)
                    micturin_txt = "Altered";
                rbtnMicNormal.setChecked(false);

                break;

        }
    }

    private final void LoadBack() {

        if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("MYPATIENT")) {

            BaseConfig.HideKeyboard(ClinicalInformation.this);
            Bundle b;
            b=new Bundle();
            b.putString(BaseConfig.BUNDLE_PATIENT_ID,COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, MyPatientDrawer.class, b);

        } else if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("INPATIENT")) {

            BaseConfig.HideKeyboard(ClinicalInformation.this);
            Bundle b;
            b=new Bundle();
            b.putString(BaseConfig.BUNDLE_PATIENT_ID,COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, Inpatient_Detailed_View.class, b);

        } else {
            BaseConfig.HideKeyboard(ClinicalInformation.this);

            BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);
        }
    }

    private final void LoadAllergicTo()//ID==1
    {

        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = QUERY_ALLERGY;
        int i = 0;
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    listItems1.add(c.getString(c.getColumnIndex("DataVal")));

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();


        checkedItems1 = new boolean[listItems1.size()];


    }

    private final void LoadMedicalHistoryPOPUP()//ID==2
    {

        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = QUERY_MEDICAL_HISTORY;
        int i = 0;
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    listItems2.add(c.getString(c.getColumnIndex("DataVal")));

                    i++;

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();


        checkedItems2 = new boolean[listItems2.size()];


    }

    private final void LoadHereditaryDiseases()//ID==4
    {

        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = QUERY_HEREDITARY_DISEASES;
        int i = 0;
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    listItems3.add(c.getString(c.getColumnIndex("DataVal")));

                    i++;
                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();

        checkedItems3 = new boolean[listItems3.size()];


    }

    //To load personal history
    private final ArrayList GetArrayList(String Query) {

        newarray = new ArrayList();
        SQLiteDatabase db = BaseConfig.GetDb();

        int i = 0;
        // newarray[0]="Select";
        newarray.add(0, "Select");
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    newarray.add(i + 1, c.getString(c.getColumnIndex("DataVal")));
                    i++;
                }
                while (c.moveToNext());
            }
        }
        c.close();
        db.close();


        return newarray;
    }


    private void POPUP_Hereditary() {

        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ClinicalInformation.this);
        mBuilder.setTitle(R.string.popup_hereditary);
        mBuilder.setMultiChoiceItems(listItems3.toArray(new String[listItems3.size()]), checkedItems3, (dialogInterface, position, isChecked) -> {


            if (isChecked) {
                mUserItems3.add(Integer.valueOf(position));
            } else {
                mUserItems3.remove((Integer.valueOf(position)));
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok, (dialogInterface, which) -> {
            String item = "";
            for (int i1 = 0; i1 < mUserItems3.size(); i1++) {
                item += listItems3.get(mUserItems3.get(i1).intValue());
                if (i1 != mUserItems3.size() - 1) {
                    item += ", ";
                }
            }
            edtHereditaryDiseases.setText(item);
        });

        mBuilder.setNegativeButton(R.string.close, (dialogInterface, i12) -> dialogInterface.dismiss());

        mBuilder.setNeutralButton(R.string.clear, (dialogInterface, which) -> {
            for (int i13 = 0; i13 < checkedItems3.length; i13++) {
                checkedItems3[i13] = false;
                mUserItems3.clear();
                edtHereditaryDiseases.setText("");
            }
        });

        android.support.v7.app.AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void POPUP_MedicalHistory() {


        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ClinicalInformation.this);
        mBuilder.setTitle(R.string.popup_medical);
        mBuilder.setMultiChoiceItems(listItems2.toArray(new String[listItems2.size()]), checkedItems2, (dialogInterface, position, isChecked) -> {


            if (isChecked) {
                mUserItems2.add(Integer.valueOf(position));
            } else {
                mUserItems2.remove((Integer.valueOf(position)));
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok, (dialogInterface, which) -> {
            String item = "";
            for (int i = 0; i < mUserItems2.size(); i++) {
                item += listItems2.get(mUserItems2.get(i).intValue());
                if (i != mUserItems2.size() - 1) {
                    item += ", ";
                }
            }
            edtPrevmedhistory.setText(item);
        });

        mBuilder.setNegativeButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss());

        mBuilder.setNeutralButton(R.string.clear, (dialogInterface, which) -> {
            for (int i = 0; i < checkedItems2.length; i++) {
                checkedItems2[i] = false;
                mUserItems2.clear();
                edtPrevmedhistory.setText("");
            }
        });

        android.support.v7.app.AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void POPUP_Allergy() {


        android.support.v7.app.AlertDialog.Builder mBuilder = new android.support.v7.app.AlertDialog.Builder(ClinicalInformation.this);
        mBuilder.setTitle(R.string.popup_allergic);
        mBuilder.setMultiChoiceItems(listItems1.toArray(new String[listItems1.size()]), checkedItems1, (dialogInterface, position, isChecked) -> {


            if (isChecked) {
                mUserItems1.add(Integer.valueOf(position));
            } else {
                mUserItems1.remove((Integer.valueOf(position)));
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok, (dialogInterface, which) -> {
            String item = "";
            for (int i = 0; i < mUserItems1.size(); i++) {
                item += listItems1.get(mUserItems1.get(i).intValue());
                if (i != mUserItems1.size() - 1) {
                    item += ", ";
                }
            }
            edtAllergyText.setText(item);
        });

        mBuilder.setNegativeButton(R.string.close, (dialogInterface, i) -> dialogInterface.dismiss());

        mBuilder.setNeutralButton(R.string.clear, (dialogInterface, which) -> {
            for (int i = 0; i < checkedItems1.length; i++) {
                checkedItems1[i] = false;
                mUserItems1.clear();
                edtAllergyText.setText("");
            }
        });

        android.support.v7.app.AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }



    public void removeMedicalHistory(int position, final List<Object> dataList)
    {

        new CustomKDMCDialog(ClinicalInformation.this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_delete_forever_black_24dp)
                .setTitle(ClinicalInformation.this.getResources().getString(R.string.delete_confirmation))
                .setDescription(ClinicalInformation.this.getResources().getString(R.string.do_you_want_to_delete))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {
                    dataList.remove(position);
                    recyclerViewMh.getAdapter().notifyDataSetChanged();


                });
    }


    public void removeImmunization(int position, final List<Object> dataList)
    {
        new CustomKDMCDialog(ClinicalInformation.this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_delete_forever_black_24dp)
                .setTitle(ClinicalInformation.this.getResources().getString(R.string.delete_confirmation))
                .setDescription(ClinicalInformation.this.getResources().getString(R.string.do_you_want_to_delete))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {
                    dataList.remove(position);
                    recylerViewImmunization.getAdapter().notifyDataSetChanged();
                });
    }

    public static class MedicalViewHolder extends RecyclerView.ViewHolder{
        private TextView treatmentfor;
        private TextView medicineName;
        private TextView period;

        public MedicalViewHolder(View view) {
            super(view);
            treatmentfor = view.findViewById(R.id.treatmentfor);
            medicineName = view.findViewById(R.id.medicine_name);
            period = view.findViewById(R.id.period);
        }
    }

    public static class ImmunizationViewHolder extends RecyclerView.ViewHolder{
        private TextView text1;
        private TextView text2;

        public ImmunizationViewHolder(View view) {
            super(view);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
        }
    }


    @Override
    protected final void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public final void onBackPressed() {
        LoadBack();
        System.gc();
    }

    @Override
    protected final void onPause() {
        super.onPause();
        System.gc();
    }

    @Override
    protected final void onResume() {
        super.onResume();
        System.gc();
    }


}//END
