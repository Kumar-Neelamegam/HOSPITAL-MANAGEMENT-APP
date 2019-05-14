package kdmc_kumar.Core_Modules;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Doctor_Modules.Splash_Screen;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.KDMCRecyclerAdapter;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;
import kdmc_kumar.Webservices_NodeJSON.Investigation_TestList.Investigation_ExpandableTestList;

@SuppressWarnings("ALL")
public class Investigations extends AppCompatActivity implements TextWatcher {

    // ****************************************************************
    public static final int SIGNATURE_ACTIVITY = 1;
    public static int mYear;
    public static int mMonth;
    public static int mDay;
    public static int putsign = 0;
    public static String tempDir;
    public static int mcYear;
    public static int mcMonth;
    public static int mcDay;
    // #######################################################################################################
    public static List<String> list = new ArrayList<String>();
    public static String space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    final Context context = this;
    public SQLiteDatabase db;
    public String current = null;
    //ScrollView parentscroll;
    public int numoftest = 0;
    protected ArrayList<CharSequence> selectedItems = new ArrayList<CharSequence>();
    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    protected ArrayList<CharSequence> selectedoffense = new ArrayList<CharSequence>();
    @BindView(R.id.investigation_parent_layout)
    CoordinatorLayout investigationParentLayout;
    @BindView(R.id.investigation_nesetedscrollview)
    NestedScrollView investigationNesetedscrollview;
    @BindView(R.id.upperlayout)
    LinearLayout upperlayout;
    @BindView(R.id.imgvw_patientphoto)
    CircleImageView imgvwPatientphoto;
    @BindView(R.id.tvw_agegender)
    TextView tvwAgegender;
    @BindView(R.id.txtvw_title_patientname)
    TextView txtvwTitlePatientname;
    @BindView(R.id.autocomplete_patientname)
    AutoCompleteTextView autocompletePatientname;
    @BindView(R.id.txtvw_treatmentfor)
    TextView txtvwTreatmentfor;
    @BindView(R.id.multiauto_treatmentfor)
    MultiAutoCompleteTextView multiautoTreatmentfor;
    @BindView(R.id.txtvw_diagnosis)
    TextView txtvwDiagnosis;
    @BindView(R.id.multiauto_diagnosis)
    MultiAutoCompleteTextView multiautoDiagnosis;
    @BindView(R.id.card_test)
    CardView cardTest;
    @BindView(R.id.arrow_test)
    ImageButton arrowTest;
    @BindView(R.id.primary_investigation_test)
    LinearLayout primaryInvestigationTest;
    @BindView(R.id.button_my_preferred_testlist)
    Button buttonMyPreferredTestlist;
    @BindView(R.id.button_all_test)
    Button buttonAllTest;
    @BindView(R.id.txtvw_totaltestcount)
    TextView txtvwTotaltestcount;
    @BindView(R.id.hide_investigation_test)
    Button hideInvestigationTest;
    @BindView(R.id.arrow_investigation_scan)
    ImageButton arrowInvestigationScan;
    @BindView(R.id.primary_investigation_scan)
    LinearLayout primaryInvestigationScan;
    @BindView(R.id.spinner_scan)
    Spinner spinnerScan;
    @BindView(R.id.scanfor_edt)
    MultiAutoCompleteTextView scanforEdt;
    @BindView(R.id.button_scanforadd)
    Button buttonScanforadd;
    @BindView(R.id.hide_investigation_scan)
    Button hideInvestigationScan;
    @BindView(R.id.arrow_investigation_xray)
    ImageButton arrowInvestigationXray;
    @BindView(R.id.primary_investigation_xray)
    LinearLayout primaryInvestigationXray;
    @BindView(R.id.multiauto_xray)
    AutoCompleteTextView multiautoXray;
    @BindView(R.id.button_xrayadd)
    Button buttonXrayadd;
    @BindView(R.id.listview_investigation_xray)
    ListView listviewInvestigationXray;
    @BindView(R.id.hide_investigation_xray)
    Button hideInvestigationXray;
    @BindView(R.id.arrow_investigation_ecg)
    ImageButton arrowInvestigationEcg;
    @BindView(R.id.primary_investigation_ecg)
    LinearLayout primaryInvestigationEcg;
    @BindView(R.id.checkbox_ecg_option1)
    CheckBox checkboxEcgOption1;
    @BindView(R.id.checkbox_ecg_option2)
    CheckBox checkboxEcgOption2;
    @BindView(R.id.edt_ecg_comments)
    EditText edtEcgComments;
    @BindView(R.id.arrow_investigation_eeg)
    ImageButton arrowInvestigationEeg;
    @BindView(R.id.primary_investigation_eeg)
    LinearLayout primaryInvestigationEeg;
    @BindView(R.id.checkbox_eeg_option1)
    CheckBox checkboxEegOption1;
    @BindView(R.id.edt_eeg_comment)
    EditText edtEegComment;
    @BindView(R.id.hide_investigation_eeg)
    Button hideInvestigationEeg;
    @BindView(R.id.hide_investigation_ecg)
    Button hideInvestigationEcg;
    @BindView(R.id.arrow_investigation_angiogram)
    ImageButton arrowInvestigationAngiogram;
    @BindView(R.id.primary_investigation_angio)
    LinearLayout primaryInvestigationAngio;
    @BindView(R.id.primary_preflayout)
    LinearLayout primaryInvestigationPreferred;
    @BindView(R.id.checkbox_angio_coronary)
    CheckBox checkboxAngioCoronary;
    @BindView(R.id.checkbox_angio_brain)
    CheckBox checkboxAngioBrain;
    @BindView(R.id.checkbox_angio_lowerlimb)
    CheckBox checkboxAngioLowerlimb;
    @BindView(R.id.checkbox_angio_mesenteric)
    CheckBox checkboxAngioMesenteric;
    @BindView(R.id.checkbox_angio_upperlimb)
    CheckBox checkboxAngioUpperlimb;
    @BindView(R.id.edt_angio_comments)
    EditText edtAngioComments;
    @BindView(R.id.hide_investigation_angio)
    Button hideInvestigationAngio;
    @BindView(R.id.arrow_investigation_prefadddiagcentre)
    ImageButton arrowInvestigationPrefadddiagcentre;
    @BindView(R.id.autocomplete_diagcenter_name)
    AutoCompleteTextView autocompleteDiagcenterName;
    @BindView(R.id.autocomplete_diagcenter_address)
    AutoCompleteTextView autocompleteDiagcenterAddress;
    @BindView(R.id.setasdefault)
    CheckBox setasdefault;
    @BindView(R.id.hide_prefdiagcenterinfo)
    Button hidePrefdiagcenterinfo;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    @BindView(R.id.button_skiptoprescription)
    Button buttonSkiptoprescription;
    @BindView(R.id.button_submit)
    Button buttonSubmit;



    @BindView(R.id.recylerView_test)
    RecyclerView recylerView_test;
    List<CommonDataObjects.TwoData> testList=new ArrayList<>();

    @BindView(R.id.recylerView_scan)
    RecyclerView recylerView_scan;
    List<CommonDataObjects.TwoData> scanList=new ArrayList<>();





    CharSequence[] items;
    String base64;
    String simg;

    File mypath;

    String COMMON_DIAGNOSIS;
    String COMMON_DIAGNOSISID = "";

    String FLAG_MYPATIENT = "N/A";
    String COMMON_PATIENT_ID;
    StringBuilder sb = new StringBuilder();
    int TestCount = 0;
    //MultiAutoCompleteTextView diagnosis;
    List<String> xrayitemarry = new ArrayList<String>();
    List<String> advlist = new ArrayList<String>();
    // Custom Method to handle checkbox click events
    String ecgtxt = "", ecg_treadmil_txt = "";
    String eegtxt = "";
    String angitxt1 = "", angitxt2 = "", angitxt3 = "", angitxt4 = "",
            angitxt5 = "", angitxt6 = "";

    String MTestID;
    StringBuilder Validation_str;
    StringBuilder sbt = new StringBuilder();

    Typeface tfavv;
    List<String> Loadlist1 = new ArrayList<String>();
    List<String> Loadlist2 = new ArrayList<String>();
    List<String> Loadlist3 = new ArrayList<String>();
    List<String> Loadlist4 = new ArrayList<String>();


    int i = 0;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;
    @SuppressWarnings("unused")
    private String uniqueId;
    private ProgressDialog pDialog;
    private String[] patientname;

    public static String[] convertListtoStringArray(List<String> liststring) {

        String[] ValueArr = new String[liststring.size()];
        ValueArr = liststring.toArray(ValueArr);

        return ValueArr;
    }

    // #######################################################################################################

    /***
     * MUTHUKUMAR N
     * 29-03-2018
     * @param savedInstanceState
     */
//kdmc_layout_clinicalinformation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_investigations);

        // sendmail btn flag
        BaseConfig.sndmailflag = 1;
        BaseConfig.welcometoast = 0;


        try {

            GETINITIALIZE();

            CONTROLLISTENERS();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void LoadBack() {

        if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("MYPATIENT")) {

            BaseConfig.HideKeyboard(Investigations.this);

            Bundle b;
            b=new Bundle();
            b.putString(BaseConfig.BUNDLE_PATIENT_ID, COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, MyPatientDrawer.class, b);

        } else if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("INPATIENT")) {

            BaseConfig.HideKeyboard(Investigations.this);
            Bundle bundle=new Bundle();
            bundle.putString(BaseConfig.BUNDLE_PATIENT_ID, COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, Inpatient_Detailed_View.class, bundle);

        } else {

            BaseConfig.HideKeyboard(Investigations.this);

            LoadDeleteTempTest();

            BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

        }

    }

    //#######################################################################################################

    public void LoadPDtls(String PatientId, String TreatmentFor, String Diagnosis) {

        String Str_Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PatientId + "'");
        String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PatientId + "'");
        String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + PatientId + "'");

        autocompletePatientname.setText(Str_Patient_Name + "-" + PatientId);
        tvwAgegender.setText(Patient_AgeGender);
        multiautoTreatmentfor.setText(TreatmentFor);
        if (Diagnosis != null && Diagnosis.length() > 0) {
            multiautoDiagnosis.setText(Diagnosis);
        }

        BaseConfig.LoadPatientImage(Str_Patient_Photo, imgvwPatientphoto, 100);

    }

    // #######################################################################################################
    @Override
    public void onBackPressed() {


        LoadBack();
    }

    public void AddMoreListItem(String str) {

        if (list.size() > 0) {
            int flg = 1;
            for (int h = 0; h < list.size(); h++) {
                if (list.get(h).equalsIgnoreCase(str)) {
                    flg = 0;
                    break;
                }

            }
            if (flg == 1) {
                list.add(str);
               /* ArrayAdapter<String> listadapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1,
                        list);*/
                //listviewTests.setAdapter(listadapter);
                numoftest = numoftest + 1;
                txtvwTotaltestcount.setText(getString(R.string.total_test)
                        + String.valueOf(list.size()));
            }
        } else {
            list.add(str);
         /*   ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, list);
            listviewTests.setAdapter(listadapter);*/
            numoftest = numoftest + 1;
            txtvwTotaltestcount.setText(getString(R.string.total_test)
                    + String.valueOf(list.size()));
        }

    }


    public void LoadTestSubTest() {

        String Test = "";
        String SubTest = "";

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select Test,SubTest  from TempTest order by Test;", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    Test = c.getString(c.getColumnIndex("Test"));//.split("\\|")[1];


                    SubTest = c.getString(c.getColumnIndex("SubTest"));



                    String Data1="",Data2="";

                    try {
                        Data1=Test;
                        Data2=SubTest;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    testList.add(new CommonDataObjects.TwoData(Data1,Data2));



                    if (checklistforduplicatetest(Test + "/" + SubTest)) {
                        list.add(Test + "/" + SubTest);

                   /*     ArrayAdapter<String> StateDataAdapter = new ArrayAdapter<String>(
                                Investigations.this,
                                android.R.layout.simple_list_item_1,
                                list);
                        listviewTests.setAdapter(StateDataAdapter);*/
                    } else {

                        Toast.makeText(context, getResources().getString(R.string.already_exist), Toast.LENGTH_SHORT).show();

                    }

                    TestCount++;

                } while (c.moveToNext());

            }

        }

        db.close();
        c.close();
        recylerView_test.getAdapter().notifyDataSetChanged();
    }


    private boolean checklistforduplicatetest(String Str) {
        // TODO Auto-generated method stub
        int flag = 1;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).equalsIgnoreCase(Str)) {
                    flag = 0;
                    break;
                }
            }

        }

        return flag != 0;
    }


    public void LoadDeleteTempTest() {
        final String CREATE_TABLE_TempTest = "Delete from TempTest;";
        BaseConfig.SaveData(CREATE_TABLE_TempTest);
    }


    public void LoadPatentIDname() {

        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery("select *  from Patreg order by name;", null);

        List<String> list = new ArrayList<String>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String pname = c.getString(c.getColumnIndex("name"));
                    String str2 = c.getString(c.getColumnIndex("Patid"));

                    list.add(pname + " - " + str2);

                } while (c.moveToNext());
            }
        }

        BaseConfig.loadSpinner(autocompletePatientname, list);
        c.close();
        db.close();

    }


    public void GETINITIALIZE() {


        try {

            ButterKnife.bind(Investigations.this);
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

           // BaseConfig.LoadDoctorValues();

            BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);

            toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.investigation)));

            setSupportActionBar(toolbar);
      //      toolbar.setBackgroundColor(Color.parseColor(Investigations.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));


            // #######################################################################################################
            // Setting title colour
            String first = getString(R.string.patint_name);
            String next = "<font color='#EE0000'><b>*</b></font>";
            txtvwTitlePatientname.setText(Html.fromHtml(first + next));

            String firstt = getString(R.string.symptoms);
            String nextt = "<font color='#EE0000'><b>*</b></font>";
            txtvwTreatmentfor.setText(Html.fromHtml(firstt + nextt));

            String first2 = getString(R.string.provisional_diagnosis);
            String next2 = "<font color='#EE0000'><b>*</b></font>";
            txtvwDiagnosis.setText(Html.fromHtml(first2 + next2));


            autocompletePatientname.setThreshold(1);// will start working from first character
            multiautoTreatmentfor.setThreshold(1);
            multiautoTreatmentfor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


            multiautoDiagnosis.setText("");
            multiautoDiagnosis.setThreshold(1);
            multiautoDiagnosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


            LoadValues(multiautoDiagnosis, Investigations.this, "select distinct diagnosisdata as dvalue from diagonisdetails where (isactive='true' or isactive='1') order by id desc;", 1);


            if (BaseConfig.Assistant_Task.equalsIgnoreCase("True")) {
                buttonSkiptoprescription.setVisibility(View.INVISIBLE);
            } else {
                buttonSkiptoprescription.setVisibility(View.VISIBLE);
            }

            Bundle b = getIntent().getExtras();
            if (b != null) {

                String STATUS = b.getString("CONTINUE_STATUS");//To check whether the activity from clinical information;
                COMMON_PATIENT_ID = b.getString("PASSING_PATIENT_ID");
                String COMMON_TREATMENTFOR = b.getString("PASSING_TREATMENTFOR");
                COMMON_DIAGNOSIS = b.getString("PASSING_DIAGNOSIS");
                COMMON_DIAGNOSISID = b.getString("PASSING_DIAGNOSISID");
                FLAG_MYPATIENT = b.getString("FROM");


                if (STATUS.equalsIgnoreCase("True")) {
                    LoadPDtls(COMMON_PATIENT_ID, COMMON_TREATMENTFOR, COMMON_DIAGNOSIS);
                }
            } else {

                FLAG_MYPATIENT = "";

            }

            multiautoDiagnosis.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, convertListtoStringArray(Loadlist1)));

            autocompleteDiagcenterName.setThreshold(1);


            list.clear();

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            final Calendar cc = Calendar.getInstance();
            mcYear = cc.get(Calendar.YEAR);
            mcMonth = cc.get(Calendar.MONTH) + 1;
            mcDay = cc.get(Calendar.DAY_OF_MONTH);

            txtvwTotaltestcount.setText(Investigations.this.getResources().getString(R.string.total_no) + String.valueOf(list.size()));


            try {
                LoadTestSubtest();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

            e.printStackTrace();

        }


        LoadValues(
                multiautoTreatmentfor,
                Investigations.this,
                "select distinct treatmentfor as dvalue from trmntfor where (isactive='True' or isactive='true' or isactive='1') order by id desc;",
                1);

        BaseConfig.LoadValuesSpinner(
                spinnerScan,
                Investigations.this,
                "select distinct scantype as dvalue from scan where IsActive='1' and scantype!='' order by scantype;",
                getString(R.string.select_scan_type));


        try {
            BaseConfig.loadSpinner(multiautoTreatmentfor, Loadlist1);
            BaseConfig.loadSpinner(multiautoXray, Loadlist3);
            multiautoXray.setThreshold(1);
            BaseConfig.loadSpinner(autocompleteDiagcenterName, Loadlist4);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (autocompleteDiagcenterName != null) {
                autocompleteDiagcenterName.setText(String.valueOf(BaseConfig.HOSPITALNAME + ",LABORATORY"));
            }

            String address = BaseConfig.GetValues("select Address as ret_values from Mstr_MultipleHospital where ServerId='" + BaseConfig.HID + "'");

            if (address.length() > 0) {
                autocompleteDiagcenterAddress.setText(address);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        arrowTest.setOnClickListener(view -> toggleSectionInput(arrowTest, primaryInvestigationTest));

        hideInvestigationTest.setOnClickListener(view -> toggleSectionInput(arrowTest, primaryInvestigationTest));


        arrowInvestigationScan.setOnClickListener(view -> toggleSectionInput(arrowInvestigationScan, primaryInvestigationScan));

        hideInvestigationScan.setOnClickListener(view -> toggleSectionInput(arrowInvestigationScan, primaryInvestigationScan));


        arrowInvestigationXray.setOnClickListener(view -> toggleSectionInput(arrowInvestigationXray, primaryInvestigationXray));

        hideInvestigationXray.setOnClickListener(view -> toggleSectionInput(arrowInvestigationXray, primaryInvestigationXray));


        arrowInvestigationEcg.setOnClickListener(view -> toggleSectionInput(arrowInvestigationEcg, primaryInvestigationEcg));

        hideInvestigationEcg.setOnClickListener(view -> toggleSectionInput(arrowInvestigationEcg, primaryInvestigationEcg));


        arrowInvestigationEeg.setOnClickListener(view -> toggleSectionInput(arrowInvestigationEeg, primaryInvestigationEeg));

        hideInvestigationEeg.setOnClickListener(view -> toggleSectionInput(arrowInvestigationEeg, primaryInvestigationEeg));


        arrowInvestigationAngiogram.setOnClickListener(view -> toggleSectionInput(arrowInvestigationAngiogram, primaryInvestigationAngio));

        hideInvestigationAngio.setOnClickListener(view -> toggleSectionInput(arrowInvestigationAngiogram, primaryInvestigationAngio));


        arrowInvestigationPrefadddiagcentre.setOnClickListener(view -> toggleSectionInput(arrowInvestigationPrefadddiagcentre, primaryInvestigationPreferred));

        hidePrefdiagcenterinfo.setOnClickListener(view -> toggleSectionInput(arrowInvestigationPrefadddiagcentre, primaryInvestigationPreferred));


    }


    private void toggleSectionInput(View view, View PrimaryLayout) {
        boolean show = toggleArrow(view);
        if (show) {
            //ViewAnimation.expand(PrimaryLayout, () -> Tools.nestedScrollTo(investigationNesetedscrollview, PrimaryLayout));
            ViewAnimation.expand(PrimaryLayout);
        } else {
            ViewAnimation.collapse(PrimaryLayout);
        }
    }


    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }


    public void CONTROLLISTENERS() {



        try {




            //Set Test RecylerView
            recylerView_test.setLayoutManager(new LinearLayoutManager(Investigations.this));
            final KDMCRecyclerAdapter KDMCRecyclerAdapter1 =new KDMCRecyclerAdapter(testList,R.layout.kdmc_row_iimunization_layout)
                    .setRowItemView(new KDMCRecyclerAdapter.AdapterView() {
                        @Override
                        public Object setAdapterView(ViewGroup parent, int viewType, int layoutId) {
                            return new TwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
                        }

                        @Override
                        public void onBindView(Object holder, final int position, Object data, final List<Object> dataList) {

                            TwoViewHolder myViewHolders= (TwoViewHolder) holder;
                            final CommonDataObjects.TwoData value= (CommonDataObjects.TwoData) data;

                            try {
                                myViewHolders.text1.setText(value.getData1());
                                myViewHolders.text2.setText(value.getData2());


                                myViewHolders.text1.setOnClickListener(v -> ConfirmDelete(position));
                                myViewHolders.text2.setOnClickListener(v -> ConfirmDelete(position));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });
            recylerView_test.setAdapter(KDMCRecyclerAdapter1);

            //Set Scan RecylerView
            recylerView_scan.setLayoutManager(new LinearLayoutManager(Investigations.this));
            final KDMCRecyclerAdapter KDMCRecyclerAdapter2 =new KDMCRecyclerAdapter(scanList,R.layout.kdmc_row_iimunization_layout)
                    .setRowItemView(new KDMCRecyclerAdapter.AdapterView() {
                        @Override
                        public Object setAdapterView(ViewGroup parent, int viewType, int layoutId) {
                            return new TwoViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
                        }

                        @Override
                        public void onBindView(Object holder, final int position, Object data, final List<Object> dataList) {

                            TwoViewHolder myViewHolders= (TwoViewHolder) holder;
                            final CommonDataObjects.TwoData value= (CommonDataObjects.TwoData) data;

                            try {
                                myViewHolders.text1.setText(value.getData1());
                                myViewHolders.text2.setText(value.getData2());


                                myViewHolders.text1.setOnClickListener(v -> ConfirmDeletediet(position));
                                myViewHolders.text2.setOnClickListener(v ->  ConfirmDeletediet(position));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });
            recylerView_scan.setAdapter(KDMCRecyclerAdapter2);







            multiautoDiagnosis.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    String text;
                    text = String.valueOf(charSequence);

                    multiautoDiagnosis.setAdapter(new ArrayAdapter<>(Investigations.this,
                            android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, convertListtoStringArray(Loadlist1))));


                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });




            toolbarExit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    BaseConfig.ExitSweetDialog(Investigations.this, null);

                }
            });


            toolbarBack.setOnClickListener(view -> {

                    LoadBack();

            });


            autocompletePatientname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    try {
                        if (autocompletePatientname.getText().toString().length() > 0) {

                            String LoadPatientQuery = "select name,Patid from patreg order by name";
                            String Query = LoadPatientQuery;
                            BaseConfig.SelectedGetPatientDetailsFilter(Query, Investigations.this, autocompletePatientname, charSequence.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });


            multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(Investigations.this, android.R.layout.simple_list_item_1, convertListtoStringArray(Loadlist1)));

            multiautoTreatmentfor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    String text;
                    text = String.valueOf(charSequence);

                    try {
                        multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(Investigations.this,
                                android.R.layout.simple_list_item_1, BaseConfig.filterValues(text, convertListtoStringArray(Loadlist1))));

                        multiautoTreatmentfor.showDropDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });

            multiautoXray.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (multiautoXray.getText().toString().length() > 0) {
                        String Query = "select distinct xraydetail as dvalue from xray_mstr where (IsActive='True' or IsActive='true' or IsActive='1') order by xraydetail;";
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, Investigations.this, multiautoXray, charSequence.toString());

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });

            autocompleteDiagcenterName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (autocompleteDiagcenterName.getText().toString().length() > 0) {
                        String Query = "select distinct labname as dvalue from Laboratory  order by labname;";
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, Investigations.this, autocompleteDiagcenterName, charSequence.toString());

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    Drawable rightDrawable = AppCompatResources.getDrawable(autocompleteDiagcenterName.getContext(), R.drawable.ic_clear_button);
                    if (autocompleteDiagcenterName.getText().length() > 0) {
                        autocompleteDiagcenterName.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                        autocompleteDiagcenterName.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                final int DRAWABLE_LEFT = 0;
                                final int DRAWABLE_TOP = 1;
                                final int DRAWABLE_RIGHT = 2;
                                final int DRAWABLE_BOTTOM = 3;

                                if (event.getAction() == MotionEvent.ACTION_UP) {
                                    if (event.getRawX() >= (autocompleteDiagcenterName.getRight() - autocompleteDiagcenterName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                        // your action here
                                        autocompleteDiagcenterName.setText("");
                                        autocompleteDiagcenterAddress.setText("");
                                        autocompleteDiagcenterName.setEnabled(true);
                                        return true;
                                    }
                                }
                                return false;
                            }
                        });

                    } else {
                        autocompleteDiagcenterName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                        autocompleteDiagcenterName.setOnTouchListener(null);

                    }


                }
            });


            autocompleteDiagcenterName.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    SelectedLaboratoryAddress(autocompleteDiagcenterName.getText().toString());

                }
            });

            buttonXrayadd.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (multiautoXray.getText().length() != 0) {
                        if (checklistforduplicateDet(multiautoXray.getText().toString())) {
                            xrayitemarry.add(multiautoXray.getText().toString());
                            ArrayAdapter<String> listadapter = new ArrayAdapter<String>(
                                    Investigations.this,
                                    android.R.layout.simple_list_item_1,
                                    xrayitemarry);
                            listviewInvestigationXray.setAdapter(listadapter);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.already_exist), Toast.LENGTH_LONG).show();

                        }

                        multiautoXray.setText("");
                    } else {
                        multiautoXray.requestFocus();
                        multiautoXray.setError(Investigations.this.getResources().getString(R.string.required));
                    }
                }

                private boolean checklistforduplicateDet(String Str) {
                    // TODO Auto-generated method stub
                    int flag = 1;
                    if (xrayitemarry.size() > 0) {
                        for (int i = 0; i < xrayitemarry.size(); i++) {

                            if (xrayitemarry.get(i)
                                    .equalsIgnoreCase(Str)) {
                                flag = 0;
                                break;
                            }
                        }

                    }

                    return flag != 0;
                }

            });

            listviewInvestigationXray.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    ConfirmDeletexray(position);
                }

                private void ConfirmDeletexray(final int position) {
                    // TODO Auto-generated method stub
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.delete_Confirmation);
                    builder.setMessage(R.string.delete_ques);

                    builder.setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    xrayitemarry.remove(position);
                                    ArrayAdapter<String> listadapter = new ArrayAdapter<String>(
                                            Investigations.this,
                                            android.R.layout.simple_list_item_1,
                                            xrayitemarry);
                                    listviewInvestigationXray.setAdapter(listadapter);
                                }
                            });
                    builder.setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();


                }

            });

            // //////////////////////////////////////////////////////////////////
            // Scan for
            buttonScanforadd.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (spinnerScan.getSelectedItemPosition() > 0) {

                        AddAdviceList(spinnerScan.getSelectedItem().toString().trim());
                        spinnerScan.setSelection(0);

                    } else {
                        Toast.makeText(getApplicationContext(), Investigations.this.getResources().getString(R.string.select_scan_type), Toast.LENGTH_LONG).show();

                    }

                }
            });

            // //////////////////////////////////////////////////////////////////

            buttonSkiptoprescription.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    showskip();
                }

            });

            // //////////////////////////////////////////////////////////////////
            investigationNesetedscrollview.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });

            // //////////////////////////////////////////////////////////////////
            listviewInvestigationXray.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    if (xrayitemarry.size() > 4) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }
            });


            buttonAllTest.setOnClickListener(new OnClickListener() {
                ArrayList<String> groupItem = new ArrayList<String>();
                ArrayList<Object> childItem = new ArrayList<Object>();

                public void onClick(View v) {

                    BaseConfig.expandlstflag = 1;
                    // for deleting temptest table
                    LoadDeleteTempTest();
                    Intent intent = new Intent(Investigations.this, Investigation_ExpandableTestList.class);
                    startActivityForResult(intent, SIGNATURE_ACTIVITY);

                }
            });
            // //////////////////////////////////////////////////////////////////
            autocompletePatientname.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    // Validation.haspwd(Patient_Name);

                    Drawable rightDrawable = AppCompatResources.getDrawable(autocompletePatientname.getContext(), R.drawable.ic_clear_button_white);
                    if (autocompletePatientname.getText().length() > 0) {
                        autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                        autocompletePatientname.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                final int DRAWABLE_LEFT = 0;
                                final int DRAWABLE_TOP = 1;
                                final int DRAWABLE_RIGHT = 2;
                                final int DRAWABLE_BOTTOM = 3;

                                if (event.getAction() == MotionEvent.ACTION_UP) {
                                    if (event.getRawX() >= (autocompletePatientname.getRight() - autocompletePatientname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                        ClearAll();
                                       // return true;
                                    }
                                }
                                return false;
                            }
                        });

                    } else {
                        autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                        autocompletePatientname.setOnTouchListener(null);

                    }


                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {

                    imgvwPatientphoto.setImageBitmap(null);
                    tvwAgegender.setText(null);
                }
            });

            // //////////////////////////////////////////////////////////////////
            // zoom imageview
            imgvwPatientphoto.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    if (autocompletePatientname.getText().length() > 0
                            && tvwAgegender.getText().length() > 0) {
                        BaseConfig.Show_Patient_PhotoInDetail(autocompletePatientname.getText().toString().split("-")[0], autocompletePatientname.getText().toString().split("-")[1], tvwAgegender.getText().toString(), Investigations.this);
                    }
                }
            });
            // //////////////////////////////////////////////////////////////////
            buttonMyPreferredTestlist.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    // select distinct count(Testname) as testn from myfavTest order
                    // by Testname;
                    String pname = "";
                    SQLiteDatabase db = BaseConfig.GetDb();//);
                    Cursor c = db
                            .rawQuery(
                                    "select  count(Testname) as dstatus from myfavTest order by Testname;",
                                    null);
                    if (c != null) {
                        if (c.moveToFirst()) {
                            do {

                                pname = c.getString(c.getColumnIndex("dstatus"));

                            } while (c.moveToNext());
                        }

                        if (!pname.equalsIgnoreCase("0")) {
                            BaseConfig.expandlstflag = 2;
                            LoadDeleteTempTest();
                            Intent intent = new Intent(Investigations.this, Investigation_ExpandableTestList.class);
                            intent.putExtra("MYFAVTEST", "true");
                            startActivityForResult(intent, SIGNATURE_ACTIVITY);
                        } else {
                            // Toast.makeText(context,
                            // "No test available - add preferred test from master",
                            // Toast.LENGTH_LONG).show();
                            BaseConfig.showSimplePopUp(
                                    getString(R.string.no_test_popuyp),
                                    getString(R.string.info), context, R.drawable.ic_warning_black_24dp, R.color.orange_500);
                        }
                    }

                    db.close();
                    c.close();

                }
            });
            // //////////////////////////////////////////////////////////////////
            autocompletePatientname.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    //    Patient_Name.setEnabled(false);
                    try {
                        SelectedGetPatientDetails();

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
            // //////////////////////////////////////////////////////////////////
            buttonSubmit.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    if (autocompletePatientname.getText().length() > 0) {

                        String[] Pat = autocompletePatientname.getText().toString().split("-");
                        if (Pat.length == 2) {

                            boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + "'");
                            if (q) {
                                SAVELOCAL();
                            } else {
                                BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), context, R.drawable.ic_close_black_24dp, R.color.orange_300);
                            }
                        } else if (Pat.length == 1) {
                            boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + "'");
                            if (q) {
                                SAVELOCAL();
                            } else {
                                BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), context, R.drawable.ic_patienticon, R.color.orange_500);
                            }
                        }


                    } else {
                        BaseConfig.showSimplePopUp(getString(R.string.enter_pat_name),
                                getString(R.string.info), context, R.drawable.ic_patienticon, R.color.orange_500);
                        autocompletePatientname.requestFocus();
                    }
                    LoadDeleteTempTest();
                }
            });
            // //////////////////////////////////////////////////////////////////
            buttonCancel.setOnClickListener(new OnClickListener() {
                int count = 0;

                public void onClick(View v) {

                    if (count == 1) {
                        count = 0;

                        LoadDeleteTempTest();
                        Investigations.this.finish();
                        Intent intent = new Intent(v.getContext(),
                                Dashboard_NavigationMenu.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.press_again), Toast.LENGTH_SHORT)
                                .show();
                        count++;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    // ///////////////////////////////////////////////////////////////////////////////////////////////
    public void LoadDrWorkingDays() {

        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery(
                "select preflab,labdetails from drsettings;", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                } while (c.moveToNext());
            }
        }

        c.close();
        db.close();

    }


    public void ClearAll() {
        autocompletePatientname.setText("");
        multiautoTreatmentfor.setText("");
        BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);

        autocompletePatientname.setEnabled(true);
        multiautoDiagnosis.setText("");
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void SelectedLaboratoryAddress(String str) {
        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery(
                "select distinct labaddr,contactnum from Laboratory where labname='"
                        + str + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    autocompleteDiagcenterAddress.setText(c.getString(c
                            .getColumnIndex("labaddr"))
                            + " - "
                            + c.getString(c.getColumnIndex("contactnum")));

                } while (c.moveToNext());
            }
        }

        c.close();
        db.close();
    }

    public void LoadTestSubtest() {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db
                .rawQuery(
                        "select distinct Testname,SubTest from Testname where IsActive='1' order by Testname;",
                        null);

        List<String> list = new ArrayList<String>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String pname = c.getString(c.getColumnIndex("Testname"))
                            + " / " + c.getString(c.getColumnIndex("SubTest"));

                    list.add(pname);

                } while (c.moveToNext());
            }
        }

        new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);

        items = list.toArray(new String[list.size()]);

        db.close();
        c.close();

    }

    // #######################################################################################################//////
    // Show Button
    protected void showSelectColoursDialog() {

        boolean[] checkedColours = new boolean[items.length];
        int count = items.length;
        for (int i = 0; i < count; i++)

            checkedColours[i] = selectedColours.contains(items[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {

            public void onClick(DialogInterface dialog, int which,
                                boolean isChecked) {
                if (isChecked) {
                    selectedColours.add(items[which]);

                } else {
                    Toast.makeText(getApplicationContext(), R.string.removed,
                            Toast.LENGTH_LONG).show();
                    selectedColours.remove(items[which]);

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.testname);

        builder.setMultiChoiceItems(items, checkedColours,
                coloursDialogListener);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                StringBuilder stringBuilder = new StringBuilder();

                for (CharSequence colour : selectedColours) {
                    stringBuilder.append(colour).append("\n");
                    // selectedoffense.add(colour);
                }
                String[] splt = stringBuilder.toString().split("\n");
                if (splt.length > 0) {
                    for (int j = 0; j < splt.length; j++) {
                        AddMoreListItem(splt[j].trim());
                    }
                }

            }
        });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new StringBuilder();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // #######################################################################################################//////
    private String getTodaysDate() {

        final Calendar c = Calendar.getInstance();
        int todaysDate = (c.get(Calendar.YEAR) * 10000)
                + ((c.get(Calendar.MONTH) + 1) * 100)
                + (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:", String.valueOf(todaysDate));
        return (String.valueOf(todaysDate));

    }

    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)
                + (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
        Log.w("TIME:", String.valueOf(currentTime));
        return (String.valueOf(currentTime));

    }

    public void AddAdviceList(String dres) {
        if (checklistforduplicate(dres)) {

            advlist.add(dres);



            String[] Datas=dres.split("/");

            String Data1="",Data2="";

            try {
                Data1=Datas[0];
                Data2=Datas[1];
            } catch (Exception e) {
                e.printStackTrace();
            }

            scanList.add(new CommonDataObjects.TwoData(Data1,Data2));
            recylerView_scan.getAdapter().notifyDataSetChanged();

        } else {

            new CustomKDMCDialog(this)
                    .setLayoutColor(R.color.orange_500)
                    .setImage(R.drawable.ic_duplicate_content)
                    .setTitle(this.getResources().getString(R.string.information))
                    .setDescription(getString(R.string.selected_item) + dres + getString(R.string.already_added))
                    .setPossitiveButtonTitle("OK")
                    .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                        @Override
                        public void onPossitivePerformed() {
                        }
                    });

        }
    }

    public boolean checklistforduplicate(String Str) {

        int flag = 1;
        if (advlist.size() > 0) {
            for (int i = 0; i < advlist.size(); i++) {

                if (advlist.get(i).equals(Str)) {

                    flag = 0;
                    break;
                }
            }

        }

        return flag != 0;
    }

    public void showskip() {


        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_skip_next_black_24dp)
                .setTitle(this.getResources().getString(R.string.information))
                .setDescription(this.getResources().getString(R.string.skip_pre))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                    @Override
                    public void onPossitivePerformed() {

                        Toast.makeText(getApplicationContext(),
                                getString(R.string.skip_to_pres), Toast.LENGTH_SHORT).show();

                        LoadDeleteTempTest();
                        Investigations.this.finish();
                        Intent intent = new Intent(getApplicationContext(), MedicinePrescription.class);

                        if (autocompletePatientname.getText().length() > 0) {
                            COMMON_DIAGNOSIS= multiautoDiagnosis.getText().toString();
                            intent.putExtra("CONTINUE_STATUS", "True");
                            intent.putExtra("INVESTIGATIONID", "");
                            intent.putExtra("PASSING_DIAGNOSISID", COMMON_DIAGNOSISID);
                            intent.putExtra("PASSING_TREATMENTFOR", multiautoTreatmentfor.getText().toString());
                            intent.putExtra("PASSING_DIAGNOSIS", COMMON_DIAGNOSIS);
                            intent.putExtra("PASSING_PATIENT_ID", autocompletePatientname.getText().toString().split("-")[1]);
                        }

                        startActivity(intent);

                    }
                });


    }

    protected void ConfirmDeletediet(final int position) {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_delete_forever_black_24dp)
                .setTitle(this.getResources().getString(R.string.del_confirmaiton))
                .setDescription(this.getResources().getString(R.string.deletE_question))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                    @Override
                    public void onPossitivePerformed() {
                        //  dialog.dismiss();
                        advlist.remove(position);

                        scanList.remove(position);
                        recylerView_scan.getAdapter().notifyDataSetChanged();



                    }
                });


    }

    public void clear() {

        autocompletePatientname.setText("");
        tvwAgegender.setText("");
        tvwAgegender.setText("");
        multiautoDiagnosis.setText("");

        putsign = 0;

        list.clear();

        txtvwTotaltestcount.setText(getString(R.string.total_no) + String.valueOf(list.size()));
    }

    public void onCheckBoxClicked(View v) {

        if (v.getId() == R.id.checkbox_ecg_option1) {
            if (checkboxEcgOption1.isChecked()) {
                ecgtxt = "ECG,";
            } else {
                ecgtxt = "";
            }
        }

        if (v.getId() == R.id.checkbox_ecg_option2) {
            if (checkboxEcgOption2.isChecked()) {
                ecg_treadmil_txt = checkboxEcgOption2.getText().toString();
            } else {
                ecg_treadmil_txt = "";
            }
        }
        // //////////////////////////////
        if (v.getId() == R.id.checkbox_eeg_option1) {
            if (checkboxEegOption1.isChecked()) {
                eegtxt = checkboxEegOption1.getText().toString();
            } else {
                eegtxt = "";
            }
        }
        // //////////////////////////////
        if (v.getId() == R.id.checkbox_angio_coronary) {
            if (checkboxAngioCoronary.isChecked()) {
                angitxt1 = checkboxAngioCoronary.getText().toString() + ",";
            } else {
                eegtxt = "";
            }
        }

        if (v.getId() == R.id.checkbox_angio_brain) {
            if (checkboxAngioBrain.isChecked()) {
                angitxt2 = checkboxAngioBrain.getText().toString() + ",";
            } else {
                angitxt2 = "";
            }
        }

        if (v.getId() == R.id.checkbox_angio_lowerlimb) {
            if (checkboxAngioLowerlimb.isChecked()) {
                angitxt3 = checkboxAngioLowerlimb.getText().toString() + ",";
            } else {
                angitxt3 = "";
            }
        }

        if (v.getId() == R.id.checkbox_angio_mesenteric) {
            if (checkboxAngioMesenteric.isChecked()) {
                angitxt4 = checkboxAngioMesenteric.getText().toString() + ",";
            } else {
                angitxt4 = "";
            }
        }

        if (v.getId() == R.id.checkbox_angio_upperlimb) {
            if (checkboxAngioUpperlimb.isChecked()) {
                angitxt5 = checkboxAngioUpperlimb.getText().toString();
            } else {
                angitxt5 = "";
            }
        }

    }

    public void SAVELOCAL() {

        try {

            if (checkValidation())

                SUBMIT_FORM();
            else
                Toast.makeText(Investigations.this,
                        R.string.check_missing_valid, Toast.LENGTH_LONG)
                        .show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void SUBMIT_FORM() {

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//);
            Splash_Screen splashScreenActivity = new Splash_Screen();

            String xraycnt = concatxraycnt();
            // ///////////////////////////////////////////////////////////////////////////////////
            // Treatment master

            String[] tf = multiautoTreatmentfor.getText().toString().trim().split(",");

            BaseConfig.INSERT_NEW_TREATMENT_FOR(tf, this);

            String[] dg1 = multiautoDiagnosis.getText().toString().split(",");

            BaseConfig.INSERT_NEW_PROVISIONAL_DIAGNOSIS(dg1, Investigations.this);

            // ///////////////////////////////////////////////////////////////////////////////////


            String[] pna = autocompletePatientname.getText().toString().split("-");

            String Patient_MobileNo = BaseConfig.GetValues("select phone as ret_values from Patreg where Patid='" + pna[1].trim() + "'");

            MTestID = BaseConfig.GenerateInvestigationID();

            int reportins = 0;

            ContentValues values;

            reportins = insertMedicalTest(db, xraycnt, pna, reportins);

            reportins = insertScanDetails(db, pna, Patient_MobileNo, reportins);

            reportins = insertXrayDetails(db, pna, Patient_MobileNo, reportins);

            reportins = insertECGDetails(db, pna, Patient_MobileNo, reportins);

            reportins = insertEEGDetails(db, pna, reportins);

            reportins = insertAngiogramDetails(db, pna, reportins);

            insertMedicalPrimaryTable(db, pna, Patient_MobileNo, reportins);


            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertMedicalPrimaryTable(SQLiteDatabase db, String[] pna, String patient_MobileNo, int reportins) {
        ContentValues values;
        if (reportins == 1) {

            /**
             * Investigation Insert Query
             */
            values = new ContentValues();
            values.put("Ptid", pna[1].trim());
            values.put("pname", pna[0].trim());
            values.put("docname", BaseConfig.doctorname);
            values.put("docid", BaseConfig.doctorId);
            values.put("clinicname", BaseConfig.clinicname);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", tvwAgegender.getText().toString());
            values.put("pmobnum", patient_MobileNo);
            values.put("dsign", "");
            values.put("treatmentfor", multiautoTreatmentfor.getText().toString());
            values.put("Diagnosis", multiautoDiagnosis.getText().toString());
            values.put("mtestid", MTestID);
            values.put("imei", BaseConfig.Imeinum);
            values.put("prefdiag", autocompleteDiagcenterName.getText().toString());
            values.put("Isupdate", 0);
            values.put("IsResultAvailable", 1);
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_MEDICAL_TEST, null, values);

            final String Update_Query = "update mtest set mtestnum =mtestnum +1";
            BaseConfig.SaveData(Update_Query);

            Toast.makeText(this, "Investigation Details - Submitted Successfully..", Toast.LENGTH_SHORT).show();

            COMMON_DIAGNOSIS= multiautoDiagnosis.getText().toString();
            Investigations.this.finish();
            Intent intent = new Intent(getApplicationContext(), MedicinePrescription.class);
            intent.putExtra("CONTINUE_STATUS", "True");
            intent.putExtra("PASSING_INVESTIGATIONID", MTestID);
            intent.putExtra("PASSING_DIAGNOSISID", COMMON_DIAGNOSISID);
            intent.putExtra("PASSING_TREATMENTFOR", multiautoTreatmentfor.getText().toString());
            intent.putExtra("PASSING_DIAGNOSIS", COMMON_DIAGNOSIS);
            intent.putExtra("PASSING_PATIENT_ID", autocompletePatientname.getText().toString().split("-")[1]);
            startActivity(intent);

        } else {
            BaseConfig.showSimplePopUp(getString(R.string.test_skip), Investigations.this.getResources().getString(R.string.information), context, R.drawable.ic_warning_black_24dp, R.color.orange_400);
        }
    }

    private int insertAngiogramDetails(SQLiteDatabase db, String[] pna, int reportins) {
        ContentValues values;//Angiogram
        if (checkboxAngioCoronary.isChecked() || checkboxAngioBrain.isChecked() || checkboxAngioLowerlimb.isChecked() || checkboxAngioMesenteric.isChecked() || checkboxAngioUpperlimb.isChecked() || edtAngioComments.getText().length() > 0) {

            String coronoryStr = "";
            String brainStr = "";
            String upperLimbStr = "";
            String lowerLimbStr = "";
            String mesentericStr = "";

            if (checkboxAngioCoronary.isChecked())
                coronoryStr = checkboxAngioCoronary.getText().toString();
            if (checkboxAngioBrain.isChecked()) brainStr = checkboxAngioBrain.getText().toString();
            if (checkboxAngioUpperlimb.isChecked())
                upperLimbStr = checkboxAngioUpperlimb.getText().toString();
            if (checkboxAngioLowerlimb.isChecked())
                lowerLimbStr = checkboxAngioLowerlimb.getText().toString();
            if (checkboxAngioMesenteric.isChecked())
                mesentericStr = checkboxAngioMesenteric.getText().toString();


            values = new ContentValues();
            values.put("mtestid", MTestID);
            values.put("ptid", pna[1].trim());
            //values.put("pname", pna[0].trim());
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            //values.put("pagegen", tvwAgegender.getText().toString());
            values.put("Isupdate", 0);
            values.put("AngioFor", angitxt1 + angitxt2 + angitxt3 + angitxt4 + angitxt5);
            values.put("Comment", edtAngioComments.getText().toString());
            values.put("ReadytoUpdate", 0);
            values.put("CoronaryText", coronoryStr);
            values.put("BrainText", brainStr);
            values.put("UpperlimbText", upperLimbStr);
            values.put("LowerlimbText", lowerLimbStr);
            values.put("MesentericText", mesentericStr);
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_ANGIOGRAM, null, values);

            reportins = 1;
        }
        return reportins;
    }

    private int insertEEGDetails(SQLiteDatabase db, String[] pna, int reportins) {
        ContentValues values;//EEG Insert
        if (checkboxEegOption1.isChecked() || edtEegComment.getText().length() > 0) {
            values = new ContentValues();
            values.put("mtestid", MTestID);
            values.put("ptid", pna[1].trim());
            // values.put("pname", pna[0].trim());
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            //  values.put("pagegen", tvwAgegender.getText().toString());
            values.put("Isupdate", 0);
            values.put("EEGFor", eegtxt);
            values.put("Comment", edtEegComment.getText().toString());
            values.put("ReadytoUpdate", 0);
            values.put("Summary", "");
            values.put("Valuedata", "");
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_EEG, null, values);

            reportins = 1;
        }
        return reportins;
    }

    private int insertECGDetails(SQLiteDatabase db, String[] pna, String patient_MobileNo, int reportins) {
        ContentValues values;//ECG insert
        String[] patage_gen = tvwAgegender.getText().toString().split("-");


        if (checkboxEcgOption1.isChecked() || checkboxEcgOption2.isChecked() || edtEcgComments.getText().length() > 0) {

            String ecgStr = "";
            String ecgTreadMillStr = "";
            if (checkboxEcgOption1.isChecked()) ecgStr = checkboxEcgOption1.getText().toString();
            if (checkboxEcgOption2.isChecked())
                ecgTreadMillStr = checkboxEcgOption2.getText().toString();


            values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", pna[1].trim());
            // values.put("pname", pna[0].trim());
            values.put("mtestid", MTestID);
            // values.put("gender", patage_gen[1]);
            //values.put("age", patage_gen[0]);
            values.put("mobnum", patient_MobileNo);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("treatmentfor", multiautoTreatmentfor.getText().toString());
            values.put("Isupdate", 0);
            values.put("ECGTestFor", ecgtxt + ecg_treadmil_txt);
            values.put("Comment", edtEcgComments.getText().toString());
            values.put("clinicname", BaseConfig.clinicname);
            values.put("imeino", BaseConfig.Imeinum);
            values.put("ReadytoUpdate", 0);
            values.put("ecgText", ecgStr);
            values.put("ecgTreadmillText", ecgTreadMillStr);
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_ECGTEST, null, values);

            reportins = 1;

        }
        return reportins;
    }

    private int insertXrayDetails(SQLiteDatabase db, String[] pna, String patient_MobileNo, int reportins) {
        ContentValues values;// ##########################
        //xray insert
        if (xrayitemarry.size() > 0) {

            for (int j = 0; j < xrayitemarry.size(); j++) {

                String xrayId = getXrayId(xrayitemarry.get(j).trim());


                values = new ContentValues();
                values.put("Ptid", pna[1].trim());
                // values.put("pname", pna[0].trim());
                // values.put("docname", BaseConfig.doctorname);
                values.put("docid", BaseConfig.doctorId);
                // values.put("clinicname", BaseConfig.clinicname);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                // values.put("pagegen", tvwAgegender.getText().toString());
                //values.put("pmobnum", patient_MobileNo);
                values.put("mtestid", MTestID);
                values.put("imei", BaseConfig.Imeinum);
                values.put("xray", xrayitemarry.get(j).trim());
                values.put("Isupdate", 0);
                values.put("ReadytoUpdate", 0);
                values.put("xrayvalue", "");
                values.put("xraysummary", "");
                values.put("xrayimg", "");
                values.put("xrayid", xrayId);
                values.put("HID", BaseConfig.HID);
                db.insert(BaseConfig.TABLE_XRAY, null, values);


            }
            reportins = 1;
        }
        return reportins;
    }

    private int insertScanDetails(SQLiteDatabase db, String[] pna, String patient_MobileNo, int reportins) {
        ContentValues values;//Scan insert
        if (advlist.size() > 0) {
            for (int j = 0; j < advlist.size(); j++) {

                String testidStr = "";
                try {

                    testidStr = getScanTestId(advlist.get(j).trim());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                values = new ContentValues();
                values.put("Ptid", pna[1].trim());
                //  values.put("pname", pna[0].trim());
                // values.put("docname", BaseConfig.doctorname);
                values.put("docid", BaseConfig.doctorId);
                // values.put("clinicname", BaseConfig.clinicname);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                //   values.put("pagegen", tvwAgegender.getText().toString());
                //  values.put("pmobnum", patient_MobileNo);
                values.put("mtestid", MTestID);
                values.put("imei", BaseConfig.Imeinum);
                values.put("scanname", advlist.get(j).trim());
                values.put("Isupdate", 0);
                values.put("ReadytoUpdate", 0);
                values.put("subscanname", "");
                values.put("scanvalue", "");
                values.put("scansummary", "");
                values.put("scanreportimg", "");
                values.put("testid", testidStr);
                values.put("HID", BaseConfig.HID);
                db.insert(BaseConfig.TABLE_SCANTEST, null, values);
            }
            reportins = 1;
        }
        return reportins;
    }

    private int insertMedicalTest(SQLiteDatabase db, String xraycnt, String[] pna, int reportins) {
        ContentValues values;//Medical test and its details
        if (list.size() > 0) {


            for (int j = 0; j < list.size(); j++) {


                String[] arrins = list.get(j).split("/");
                if (arrins.length == 1) {

                    values = new ContentValues();
                    values.put("Ptid", pna[1].trim());
                    // values.put("pname", pna[0].trim());
                    // values.put("docname", BaseConfig.doctorname);
                    values.put("docid", BaseConfig.doctorId);
                    //  values.put("clinicname", BaseConfig.clinicname);
                    values.put("mtestid", MTestID);
                    values.put("testname", arrins[0]);
                    values.put("subtestname", arrins[1]);
                    values.put("alltest", list.get(j));
                    values.put("treatmentfor", multiautoTreatmentfor.getText().toString());
                    values.put("Isupdate", 0);
                    values.put("Actdate", BaseConfig.DeviceDate());
                    values.put("IsActive", 1);
                    values.put("xraydtls", xraycnt);
                    values.put("ReadytoUpdate", 0);
                    values.put("testvalue", "");
                    values.put("testsummary", "");
                    values.put("HID", BaseConfig.HID);
                    db.insert(BaseConfig.TABLE_MEDICALTESTDTLS, null, values);

                } else {

                    String subTestnameId = getSubtestNameId(arrins[1]);

                    values = new ContentValues();
                    values.put("Ptid", pna[1].trim());
                    //values.put("pname", pna[0].trim());
                    //  values.put("docname", BaseConfig.doctorname);
                    values.put("docid", BaseConfig.doctorId);
                    // values.put("clinicname", BaseConfig.clinicname);
                    values.put("mtestid", MTestID);
                    values.put("testname", arrins[0]);
                    values.put("subtestname", arrins[1]);
                    values.put("alltest", list.get(j));
                    //  values.put("treatmentfor", treatmentfor1.getText().toString());
                    values.put("Isupdate", 0);
                    values.put("Actdate", BaseConfig.DeviceDate());
                    values.put("IsActive", 1);
                    values.put("xraydtls", xraycnt);
                    values.put("ReadytoUpdate", 0);
                    values.put("testvalue", "");
                    values.put("testsummary", "");
                    values.put("subtestid", subTestnameId);
                    values.put("HID", BaseConfig.HID);
                    db.insert(BaseConfig.TABLE_MEDICALTESTDTLS, null, values);

                }

            }


            final String Delete_Query = "Delete from TempTest";
            BaseConfig.SaveData(Delete_Query);

            reportins = 1;
        }
        return reportins;
    }

    public String getXrayId(String name) {


        String bedIdStr = "";
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from xray_mstr where xraydetail  = '" + name.trim() + "'", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        bedIdStr = c.getString(c.getColumnIndex("ServerId"));


                    }

                    while (c.moveToNext());
                }
            }

            db.close();
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bedIdStr == null) {
            return "";
        }
        return bedIdStr;

    }

    public String getScanTestId(String name) {


        String bedIdStr = "";
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from scan where scantype  = '" + name.trim() + "'", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        bedIdStr = c.getString(c.getColumnIndex("ServerId"));


                    }

                    while (c.moveToNext());
                }
            }

            db.close();
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bedIdStr == null) {
            return "";
        }
        return bedIdStr;

    }

    public String getSubtestNameId(String name) {


        String bedIdStr = "";
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from Testname where IsActive='1' and SubTest  = '" + name.trim() + "'", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        bedIdStr = c.getString(c.getColumnIndex("ServerId"));

                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bedIdStr == null) {
            return "";
        }
        return bedIdStr;
    }

    private String concatxraycnt() {
        // TODO Auto-generated method stub
        String concatdietstr = "";

        for (int j = 0; j < xrayitemarry.size(); j++) {
            xrayitemarry.get(j);


            concatdietstr += xrayitemarry.get(j);

        }
        return concatdietstr;
    }


    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation1.hasText(multiautoDiagnosis))
            ret = false;

        if (!Validation1.hasText(multiautoTreatmentfor))
            ret = false;

        if (!Validation1.hasText(autocompletePatientname))
            ret = false;

        return ret;
    }

    protected void ConfirmDelete(final int position) {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_delete_forever_black_24dp)
                .setTitle(this.getResources().getString(R.string.delet_conf))
                .setDescription(this.getResources().getString(R.string.delete_quesr))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                    @Override
                    public void onPossitivePerformed() {
                        // dialog.dismiss();

                        list.remove(position);

                        numoftest = numoftest - 1;
                        txtvwTotaltestcount.setText(Investigations.this.getResources().getString(R.string.total_no) + String.valueOf(list.size()));


                        testList.remove(position);
                        recylerView_test.getAdapter().notifyDataSetChanged();

                    }
                });


    }

    public void SelectedGetPatientDetails() {

        try {
            String[] pna = autocompletePatientname.getText().toString().split("-");

            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + pna[1] + "'");
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + pna[1] + "'");
            String Str_Patient_Mail = BaseConfig.GetValues("select email as ret_values from Patreg where Patid='" + pna[1] + "'");

            BaseConfig.patientEmail = Str_Patient_Mail;
            tvwAgegender.setText(Patient_AgeGender);
            BaseConfig.Glide_LoadImageView( imgvwPatientphoto,  Str_Patient_Photo);



        } catch (Exception e) {

        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        numoftest = 0;
        switch (requestCode) {
            case SIGNATURE_ACTIVITY:
                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    String status = bundle.getString("status");
                    if (status.equalsIgnoreCase("done")) {

                        LoadTestSubTest();

                        txtvwTotaltestcount.setText(Investigations.this.getResources().getString(R.string.total_no) + String.valueOf(list.size()));
                    }

                }
                break;
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /* Loading Autocomplete values */
    public void LoadValues(AutoCompleteTextView autotxt, Context cntxt,
                           String Query, int id) {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        if (id == 1) {
                            String counrtyname = c.getString(c
                                    .getColumnIndex("dvalue"));
                            Loadlist1.add(counrtyname);
                        } else if (id == 2) {
                            String counrtyname = c.getString(c
                                    .getColumnIndex("dvalue"));
                            Loadlist2.add(counrtyname);
                        } else if (id == 3) {
                            String counrtyname = c.getString(c
                                    .getColumnIndex("dvalue"));
                            Loadlist3.add(counrtyname);
                        } else if (id == 4) {
                            String counrtyname = c.getString(c
                                    .getColumnIndex("dvalue"));
                            Loadlist4.add(counrtyname);
                        }

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static class TwoViewHolder extends RecyclerView.ViewHolder{
        private TextView text1;
        private TextView text2;

        public TwoViewHolder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.text1);
            text2 = (TextView) view.findViewById(R.id.text2);
        }
    }




}//END
