package kdmc_kumar.CaseNotes_Modules;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_BloodPressure;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_BloodSugar;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_Weight;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_BloodPressure;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_BloodSugar;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_Weight;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.ClinicalInformation;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.Inpatient_Module.Inpatient_Inputs;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Helper;
import kdmc_kumar.Utilities_Others.SqliteReader;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;
import kdmc_kumar.Utilities_Others.seek.DiscreteSeekBar;
import kdmc_kumar.Utilities_Others.seek.DiscreteSeekBar.OnProgressChangeListener;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

/**
 * Muthukumar N
 * 27-4-2018
 */
public class CaseNotes extends AppCompatActivity {


    @BindView(id.appbar_layout)
    AppBarLayout appbar_layout;
    @BindView(id.casenotes_parent_layout)
    CoordinatorLayout casenotesParentLayout;
    @BindView(id.casenotes_nestedscrollview)
    NestedScrollView casenotesNestedscrollview;
    @BindView(id.upperlayout)
    LinearLayout upperlayout;

    @BindView(id.imgvw_patientphoto)
    CircleImageView imgvwPatientphoto;

    @BindView(id.tvw_agegender)
    TextView tvwAgegender;

    @BindView(id.txtvw_title_patientname)
    TextView txtvwTitlePatientname;

    @BindView(id.autocomplete_patientname)
    AutoCompleteTextView autocompletePatientname;

    @BindView(id.txtvw_treatmentfor)
    TextView txtvwTreatmentfor;

    @BindView(id.multiauto_treatmentfor)
    MultiAutoCompleteTextView multiautoTreatmentfor;

    @BindView(id.txtvw_signs)
    TextView txtvwSigns;

    @BindView(id.multiauto_signs)
    MultiAutoCompleteTextView multiautoSigns;

    @BindView(id.txtvw_diagnosis)
    TextView txtvwDiagnosis;

    @BindView(id.multiauto_diagnosis)
    MultiAutoCompleteTextView multiautoDiagnosis;

    @BindView(id.upper_parent)
    LinearLayout upperParent;
    @BindView(id.autocomplt_weight)
    AutoCompleteTextView autocompltWeight;


    @BindView(id.autocomplt_height)
    AutoCompleteTextView autocompltHeight;

    @BindView(id.autocomplt_bmi)
    AutoCompleteTextView autocompltBmi;
    @BindView(id.autocomplt_temperature)
    AutoCompleteTextView autocompltTemperature;

    @BindView(id.autocomplt_bps)
    AutoCompleteTextView autocompltBps;

    @BindView(id.autocomplt_bpd)
    AutoCompleteTextView autocompltBpd;

    @BindView(id.autocomplt_fbs)
    AutoCompleteTextView autocompltFbs;

    @BindView(id.autocomplt_ppbs)
    AutoCompleteTextView autocompltPpbs;

    @BindView(id.autocomplt_rbs)
    AutoCompleteTextView autocompltRbs;


    @BindView(id.all_casenotes_layouts)
    LinearLayout allCasenotesLayouts;


    @BindView(id.contentLayout)
    LinearLayout contentLayout;


    @BindView(id.imgvw_bloodsugar_bar)
    AppCompatImageView imgvwBloodsugarBar;

    @BindView(id.imgvw_bloodsugar_line)
    AppCompatImageView imgvwBloodsugarLine;

    @BindView(id.imgvw_bloodpressure_bar)
    AppCompatImageView imgvwBloodpressureBar;

    @BindView(id.imgvw_bloodpressure_line)
    AppCompatImageView imgvwBloodpressureLine;

    @BindView(id.imgvw_bloodweight_bar)
    AppCompatImageView imgvwBloodweightBar;

    @BindView(id.imgvw_bloodweight_line)
    AppCompatImageView imgvwBloodweightLine;

    @BindView(id.button_cancel)
    Button buttonCancel;

    @BindView(id.button_report_upload)
    Button buttonReportUpload;

    @BindView(id.button_submit)
    Button buttonSubmit;


    @BindView(id.toolbar)
    Toolbar toolbar;
    @BindView(id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title)
    TextView toolbarTitle;
    @BindView(id.toolbar_exit)
    AppCompatImageView toolbarExit;
    String[] values = {"Select", "Good", "Fair", "Bad"};
    String[] values1;
    String[] shapeofchest;
    String[] abdomenshape;
    String[] abdomenpalpation;
    String[] reflexes;
    String[] haematuriaarry;
    String Diagnosis_Query = "select distinct diagnosisdata as dvalue from diagonisdetails where (isactive='true' or isactive='1') order by id desc;";
    String Treatment_Query = "select distinct treatmentfor as dvalue from trmntfor where (isactive='true' or isactive='1') order by id desc;";
    String Signs_Query = "select distinct signs as dvalue from Mstr_Signs where (IsActive='true' or IsActive='1') order by id desc;";
    String LoadPatientQuery = "select name,Patid from patreg order by name";
    List<String> list_Treatment;
    List<String> list_Signs;
    List<String> list_Diagnosis;
    String FLAG_MYPATIENT = "N/A";
    String COMMON_PATIENT_ID;
    CaseNotes.LOAD_GENERAL_INFORMATION general;
    CaseNotes.LOAD_CARDIO_VASCULAR cardio;
    CaseNotes.LOAD_RESPIRATORY respiratory;
    CaseNotes.LOAD_GASTRO gastro;
    CaseNotes.LOAD_NEUROLOGY neurology;
    CaseNotes.LOAD_MOTOR locomotor;
    CaseNotes.LOAD_RENAL renal;
    CaseNotes.LOAD_ENDOSYS endosys;
    CaseNotes.LOAD_CLINICAL clinical;
    CaseNotes.LOAD_OTHERSYS othersys;
    CaseNotes.LOAD_DENTAL dental;
    CaseNotes.LOAD_PNC pnc;
    String ReturnValues = "";
    CharSequence[] items_abdomen;
    ArrayList<CharSequence> selectedAbdomen = new ArrayList<CharSequence>();
    ArrayList<CharSequence> selectedEndocrine = new ArrayList<CharSequence>();
    List<String> abdomen_list = new ArrayList<String>();
    List<String> endocrine_list = new ArrayList<String>();

    private static final boolean toggleArrow(View view) {
        if (view.getRotation() == (float) 0) {
            view.animate().setDuration(200L).rotation(180.0F);
            return true;
        } else {
            view.animate().setDuration(200L).rotation((float) 0);
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.kdmc_layout_casenotes_main_activity);

        try {

            this.GETINITIALIZE();

            this.CONTROLLISTENERS();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//END ON-CREATE

    private void CONTROLLISTENERS() {


        try {
            this.LOAD_CASENOTES_SYSTEMS_DYNAMICALLY();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.casenotesParentLayout.setFocusableInTouchMode(true);

        this.casenotesParentLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);


        this.autocompltWeight.setOnClickListener(view -> this.showPopup_PatientWeight());


        this.autocompltHeight.setOnClickListener(view -> this.showPopup_PatientHeight());


        this.multiautoTreatmentfor.setOnItemClickListener((adapterView, view, i, l) -> {

            if (this.multiautoTreatmentfor.getText().length() > 0) {
                //Write code to hide and view dentail layout
                if (this.multiautoTreatmentfor.getText().toString().contains(BaseConfig.TreatmentFor_Dental)) {

                    this.dental = new CaseNotes.LOAD_DENTAL();
                }

            }
        });

        this.multiautoTreatmentfor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String text;
                text = String.valueOf(charSequence);

                CaseNotes.this.multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(CaseNotes.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, BaseConfig.convertListtoStringArray(CaseNotes.this.list_Treatment))));


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        this.multiautoDiagnosis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String text;
                text = String.valueOf(charSequence);

                CaseNotes.this.multiautoDiagnosis.setAdapter(new ArrayAdapter<>(CaseNotes.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, BaseConfig.convertListtoStringArray(CaseNotes.this.list_Diagnosis))));


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        this.autocompletePatientname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (CaseNotes.this.autocompletePatientname.getText().toString().length() > 0) {

                    String Query = CaseNotes.this.LoadPatientQuery;
                    BaseConfig.SelectedGetPatientDetailsFilter(Query, CaseNotes.this, CaseNotes.this.autocompletePatientname, charSequence.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Drawable rightDrawable = AppCompatResources.getDrawable(CaseNotes.this.autocompletePatientname.getContext(), drawable.ic_clear_button_white);
                if (CaseNotes.this.autocompletePatientname.getText().length() > 0) {
                    CaseNotes.this.autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    CaseNotes.this.autocompletePatientname.setOnTouchListener((v, event) -> {
                        int DRAWABLE_LEFT = 0;
                        int DRAWABLE_TOP = 1;
                        int DRAWABLE_RIGHT = 2;
                        int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (CaseNotes.this.autocompletePatientname.getRight() - CaseNotes.this.autocompletePatientname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                CaseNotes.this.ClearAll();
                                return true;
                            }
                        }
                        return false;
                    });

                } else {
                    CaseNotes.this.autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    CaseNotes.this.autocompletePatientname.setOnTouchListener(null);

                }


            }
        });


        this.autocompltHeight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                try {
                    if (CaseNotes.this.autocompltHeight.getText().length() > 0) {
                        if (CaseNotes.this.autocompltWeight.getText().length() > 0) {
                            float weight1 = Float.parseFloat(CaseNotes.this.autocompltWeight.getText().toString());


                            float height1 = Float.parseFloat(CaseNotes.this.autocompltHeight.getText().toString());

                            /*if (String.valueOf(height1)!="") {
                             */
                            // calculate the bmi value
                            float bmiValue = CaseNotes.this.calculateBMI(weight1, height1);

                            // interpret the meaning of the bmi value
                            String bmiInterpretation = CaseNotes.this.interpretBMI(bmiValue);

                            if (bmiInterpretation.equalsIgnoreCase("Normal")) {
                                CaseNotes.this.autocompltBmi.setTextColor(Color.GREEN);
                                CaseNotes.this.autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                            } else {
                                CaseNotes.this.autocompltBmi.setTextColor(Color.RED);
                                CaseNotes.this.autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                            }

                            // now set the value in the result text
                            CaseNotes.this.autocompltBmi.setText(String.valueOf(bmiValue));

                        }
                    } else if (CaseNotes.this.autocompltHeight.getText().length() <= 0) {
                        CaseNotes.this.autocompltBmi.setText("0");
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        this.autocompltWeight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                try {
                    if (CaseNotes.this.autocompltWeight.getText().length() > 0) {
                        float weight1 = Float.parseFloat(CaseNotes.this.autocompltWeight.getText().toString());


                        float height1 = Float.parseFloat(CaseNotes.this.autocompltHeight.getText().toString());

                        /*if (String.valueOf(height1)!="") {
                         */
                        // calculate the bmi value
                        float bmiValue = CaseNotes.this.calculateBMI(weight1, height1);

                        // interpret the meaning of the bmi value
                        String bmiInterpretation = CaseNotes.this.interpretBMI(bmiValue);

                        if (bmiInterpretation.equalsIgnoreCase("Normal")) {
                            CaseNotes.this.autocompltBmi.setTextColor(Color.GREEN);
                            CaseNotes.this.autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                        } else {
                            CaseNotes.this.autocompltBmi.setTextColor(Color.RED);
                            CaseNotes.this.autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                        }

                        // now set the value in the result text
                        CaseNotes.this.autocompltBmi.setText(String.valueOf(bmiValue));
                    } else if (CaseNotes.this.autocompltWeight.getText().length() <= 0) {
                        CaseNotes.this.autocompltBmi.setText("0");
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });


        this.autocompltHeight.setOnFocusChangeListener((v, hasFocus) -> {

            if (this.autocompltHeight.isFocused()) {
                this.autocompltHeight.setText(null);
                this.autocompltHeight.requestFocus();

            }

        });


        this.autocompltFbs.setOnFocusChangeListener((v, hasFocus) -> {

            if (this.autocompltFbs.isFocused()) {
                this.autocompltFbs.setText(null);
                this.autocompltFbs.requestFocus();

            }
        });


        this.autocompltPpbs.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (this.autocompltPpbs.isFocused()) {
                this.autocompltPpbs.setText(null);
                this.autocompltPpbs.requestFocus();

            }
        });


        this.autocompltRbs.setOnFocusChangeListener((v, hasFocus) -> {

            if (this.autocompltRbs.isFocused()) {
                this.autocompltRbs.setText(null);
                this.autocompltRbs.requestFocus();
            }
        });


        this.buttonCancel.setOnClickListener(new OnClickListener() {
            int count;

            public void onClick(View v) {

                if (this.count == 1) {
                    this.count = 0;

                    finish();
                    Intent intent = new Intent(v.getContext(), Dashboard_NavigationMenu.class);
                    CaseNotes.this.startActivity(intent);
                } else {
                    //Toast.makeText(getApplicationContext(), getString(R.string.press_again_cancel), Toast.LENGTH_SHORT).show();
                    BaseConfig.SnackBar(CaseNotes.this, CaseNotes.this.getString(string.press_again_cancel), CaseNotes.this.upperParent, 1);
                    this.count++;
                }
            }
        });


        this.buttonSubmit.setOnClickListener(view -> this.SAVE_DATA());


        this.autocompletePatientname.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {

            try {

                this.SelectedGetPatientDetails();

            } catch (Exception e) {
                e.printStackTrace();

            }
        });


        this.imgvwPatientphoto.setOnClickListener(v -> {
            if (this.autocompletePatientname.getText().length() > 0
                    && this.tvwAgegender.getText().length() > 0) {

                BaseConfig.Show_Patient_PhotoInDetail(this.autocompletePatientname.getText().toString().split("-")[0], this.autocompletePatientname.getText().toString().split("-")[1],
                        this.tvwAgegender.getText().toString(), this);


            }
        });


        this.imgvwBloodsugarBar.setOnClickListener(view -> {
            if (this.autocompletePatientname.getText().length() > 0
                    && this.tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "true";

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + this.autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(this, Chart_Bar_BloodSugar.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", this.autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", this.tvwAgegender.getText().toString());
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.select_patientname), this.getString(string.ok));
                this.autocompletePatientname.requestFocus();
            }

        });


        this.imgvwBloodsugarLine.setOnClickListener(view -> {
            if (this.autocompletePatientname.getText().length() > 0
                    && this.tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "false";

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + this.autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(this, Chart_Line_BloodSugar.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", this.autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", this.tvwAgegender.getText().toString());
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.select_patientname), this.getString(string.ok));
                this.autocompletePatientname.requestFocus();
            }

        });


        this.imgvwBloodpressureBar.setOnClickListener(view -> {
            if (this.autocompletePatientname.getText().length() > 0
                    && this.tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "true";
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + this.autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(this, Chart_Bar_BloodPressure.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", this.autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", this.tvwAgegender.getText().toString());
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.select_patientname), this.getString(string.ok));
                this.autocompletePatientname.requestFocus();
            }

        });


        this.imgvwBloodpressureLine.setOnClickListener(view -> {
            if (this.autocompletePatientname.getText().length() > 0
                    && this.tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "false";
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + this.autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(this, Chart_Line_BloodPressure.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", this.autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", this.tvwAgegender.getText().toString());
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.select_patientname), this.getString(string.ok));
                this.autocompletePatientname.requestFocus();
            }

        });


        this.imgvwBloodweightBar.setOnClickListener(view -> {
            if (this.autocompletePatientname.getText().length() > 0
                    && this.tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "true";
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + this.autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(this, Chart_Bar_Weight.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", this.autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", this.tvwAgegender.getText().toString());
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.select_patientname), this.getString(string.ok));
                this.autocompletePatientname.requestFocus();
            }

        });


        this.imgvwBloodweightLine.setOnClickListener(view -> {
            if (this.autocompletePatientname.getText().length() > 0
                    && this.tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "false";

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + this.autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(this, Chart_Line_Weight.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, this.autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", this.autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", this.tvwAgegender.getText().toString());
                    this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.no_data), this.getString(string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, this, getResources().getString(string.information), this.getString(string.select_patientname), this.getString(string.ok));
                this.autocompletePatientname.requestFocus();
            }

        });


    }//Controllisteners End



/*
    private void DENTAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (dental!=null && dental.arrowDentalSystem.getVisibility() == View.VISIBLE) {
            if (dental!=null && dental.toothChildLayout.getVisibility() == View.VISIBLE) {
                //Upper jaw line right/left
                String str_cchkbxUr8 = CheckDentalSystem(dental.cchkbxUr8,1);
                String str_cchkbxUr7 = CheckDentalSystem(dental.cchkbxUr7, 1);
                String str_cchkbxUr6 = CheckDentalSystem(dental.cchkbxUr6, 1);
                String str_cchkbxUr5 = CheckDentalSystem(dental.cchkbxUr5, 1);
                String str_cchkbxUr4 = CheckDentalSystem(dental.cchkbxUr4, 1);

                String str_cchkbxUl1 = CheckDentalSystem(dental.chkbxUl1, 2);
                String str_cchkbxUl2 = CheckDentalSystem(dental.chkbxUl2, 2);
                String str_cchkbxUl3 = CheckDentalSystem(dental.chkbxUl3, 2);
                String str_cchkbxUl4 = CheckDentalSystem(dental.chkbxUl4, 2);
                String str_cchkbxUl5 = CheckDentalSystem(dental.chkbxUl5, 2);


                //Lower Jaw line right/left
                String str_cchkbxLr1 = CheckDentalSystem(dental.cchkbxLr1, 3);
                String str_cchkbxLr2 = CheckDentalSystem(dental.cchkbxLr2, 3);
                String str_cchkbxLr3 = CheckDentalSystem(dental.cchkbxLr3, 3);
                String str_cchkbxLr4 = CheckDentalSystem(dental.cchkbxLr4, 3);
                String str_cchkbxLr5 = CheckDentalSystem(dental.cchkbxLr5, 3);


                String str_cchkbxLl1 = CheckDentalSystem(dental.cchkbxLl1, 4);
                String str_cchkbxLl2 = CheckDentalSystem(dental.cchkbxLl2, 4);
                String str_cchkbxLl3 = CheckDentalSystem(dental.cchkbxLl3, 4);
                String str_cchkbxLl4 = CheckDentalSystem(dental.cchkbxLl4, 4);
                String str_cchkbxLl5 = CheckDentalSystem(dental.cchkbxLl5, 4);


                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("upper_right_jaw", str_cchkbxUr8 + str_cchkbxUr7 + str_cchkbxUr6 + str_cchkbxUr5 + str_cchkbxUr4);
                values.put("upper_left_jaw", str_cchkbxUl1 + str_cchkbxUl2 + str_cchkbxUl3 + str_cchkbxUl4 + str_cchkbxUl5);
                values.put("lower_right_jaw", str_cchkbxLr1 + str_cchkbxLr2 + str_cchkbxLr3 + str_cchkbxLr4 + str_cchkbxLr5);
                values.put("lower_left_jaw", str_cchkbxLl1 + str_cchkbxLl2 + str_cchkbxLl3 + str_cchkbxLl4 + str_cchkbxLl5);
                values.put("HID", BaseConfig.HID);
                values.put("dental_sys","Child");
                db.insert(BaseConfig.TABLE_CASENOTES_DENTALSYSTEM, null, values);
                //Log.e("Insert Dental Child : ", values.toString());
            }

            if (dental.toothAdultLayout.getVisibility() == View.VISIBLE) {

                //Upper jaw line right/left
                String str_chkbxUr8 = CheckDentalSystem(dental.chkbxUr8, 1);
                String str_chkbxUr7 = CheckDentalSystem(dental.chkbxUr7, 1);
                String str_chkbxUr6 = CheckDentalSystem(dental.chkbxUr6, 1);
                String str_chkbxUr5 = CheckDentalSystem(dental.chkbxUr5, 1);
                String str_chkbxUr4 = CheckDentalSystem(dental.chkbxUr4, 1);
                String str_chkbxUr3 = CheckDentalSystem(dental.chkbxUr3, 1);
                String str_chkbxUr2 = CheckDentalSystem(dental.chkbxUr2, 1);
                String str_chkbxUr1 = CheckDentalSystem(dental.chkbxUr1, 1);


                String str_chkbxUl1 = CheckDentalSystem(dental.chkbxUl1, 2);
                String str_chkbxUl2 = CheckDentalSystem(dental.chkbxUl2, 2);
                String str_chkbxUl3 = CheckDentalSystem(dental.chkbxUl3, 2);
                String str_chkbxUl4 = CheckDentalSystem(dental.chkbxUl4, 2);
                String str_chkbxUl5 = CheckDentalSystem(dental.chkbxUl5, 2);
                String str_chkbxUl6 = CheckDentalSystem(dental.chkbxUl6, 2);
                String str_chkbxUl7 = CheckDentalSystem(dental.chkbxUl7, 2);
                String str_chkbxUl8 = CheckDentalSystem(dental.chkbxUl8, 2);

                //Lower jaw line right/left
                String str_chkbxLr1 = CheckDentalSystem(dental.chkbxLr1, 3);
                String str_chkbxLr2 = CheckDentalSystem(dental.chkbxLr2, 3);
                String str_chkbxLr3 = CheckDentalSystem(dental.chkbxLr3, 3);
                String str_chkbxLr4 = CheckDentalSystem(dental.chkbxLr4, 3);
                String str_chkbxLr5 = CheckDentalSystem(dental.chkbxLr5, 3);
                String str_chkbxLr6 = CheckDentalSystem(dental.chkbxLr6, 3);
                String str_chkbxLr7 = CheckDentalSystem(dental.chkbxLr7, 3);
                String str_chkbxLr8 = CheckDentalSystem(dental.chkbxLr8, 3);


                String str_chkbxLl1 = CheckDentalSystem(dental.chkbxLl1, 4);
                String str_chkbxLl2 = CheckDentalSystem(dental.chkbxLl2, 4);
                String str_chkbxLl3 = CheckDentalSystem(dental.chkbxLl3, 4);
                String str_chkbxLl4 = CheckDentalSystem(dental.chkbxLl4, 4);
                String str_chkbxLl5 = CheckDentalSystem(dental.chkbxLl5, 4);
                String str_chkbxLl6 = CheckDentalSystem(dental.chkbxLl6, 4);
                String str_chkbxLl7 = CheckDentalSystem(dental.chkbxLl7, 4);
                String str_chkbxLl8 = CheckDentalSystem(dental.chkbxLl8, 4);


                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("upper_right_jaw", str_chkbxUr1 + str_chkbxUr2 + str_chkbxUr3 + str_chkbxUr4 + str_chkbxUr5 + str_chkbxUr6 + str_chkbxUr7 + str_chkbxUr8);//-
                values.put("upper_left_jaw", str_chkbxUl1 + str_chkbxUl2 + str_chkbxUl3 + str_chkbxUl4 + str_chkbxUl5 + str_chkbxUl6 + str_chkbxUl7 + str_chkbxUl8);
                values.put("lower_right_jaw", str_chkbxLr1 + str_chkbxLr2 + str_chkbxLr3 + str_chkbxLr4 + str_chkbxLr5 + str_chkbxLr6 + str_chkbxLr7 + str_chkbxLr8);
                values.put("lower_left_jaw", str_chkbxLl1 + str_chkbxLl2 + str_chkbxLl3 + str_chkbxLl4 + str_chkbxLl5 + str_chkbxLl6 + str_chkbxLl7 + str_chkbxLl8);//-
                values.put("HID", BaseConfig.HID);
                values.put("dental_sys","Adult");
                db.insert(BaseConfig.TABLE_CASENOTES_DENTALSYSTEM, null, values);

            }
        }
    }
*/

    public void SAVE_DATA() {

        if (this.autocompletePatientname.getText().length() > 0) {


            String[] Pat = this.autocompletePatientname.getText().toString().split("-");
            if (Pat.length == 2) {



                boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + '\'');
                if (q) {


                    if (this.buttonSubmit.getText().toString().equalsIgnoreCase(this.getString(string.submit))) {
                        this.SaveLocal();
                    } else if (this.buttonSubmit.getText().toString().equalsIgnoreCase(this.getString(string.update))) {

                        try {
                            if (this.checkValidation()) {
                                // UpdateData(Pat[1]);
                            } else {
                                Toast.makeText(this,
                                        this.getString(string.check_missing_valid), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.print(e);

                        }

                    }

                } else {
                    BaseConfig.showSimplePopUp(this.getString(string.not_pat_regist), this.getString(string.info), this, drawable.ic_warning_black_24dp, color.red_400);
                }


            }
            else if (Pat.length == 1) {

                boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + '\'');
                if (q) {


                    if (this.buttonSubmit.getText().toString().equalsIgnoreCase(this.getString(string.submit))) {
                        this.SaveLocal();
                    } else if (this.buttonSubmit.getText().toString().equalsIgnoreCase(this.getString(string.update))) {

                        try {
                            if (this.checkValidation()) {
                                // UpdateData(Pat[1]);
                            } else {
                                Toast.makeText(this,
                                        this.getString(string.check_missing_valid), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.print(e);

                        }

                    }

                } else {
                    BaseConfig.showSimplePopUp(this.getString(string.not_pat_regist), this.getString(string.info), this, drawable.ic_warning_black_24dp, color.red_400);
                }


            }


        } else {
            BaseConfig.showSimplePopUp(this.getString(string.enter_pat_name), this.getString(string.information), this, drawable.ic_warning_black_24dp, color.orange_400);
            this.autocompletePatientname.requestFocus();
        }


    }

    public void SaveLocal() {

        try {
            if (this.checkValidation()) {

                this.SUBMITFORM();

            } else {
                Toast.makeText(this, getResources().getString(string.missing), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(e);

        }
    }

    private void SUBMITFORM() {


        try {
            SQLiteDatabase db = BaseConfig.GetDb();//);

            ContentValues values;

            //Treatment master
            String[] tf = this.multiautoTreatmentfor.getText().toString().trim().split(",");
            String[] dg1 = this.multiautoDiagnosis.getText().toString().split(",");

            BaseConfig.INSERT_NEW_TREATMENT_FOR(tf, this);
            BaseConfig.INSERT_NEW_PROVISIONAL_DIAGNOSIS(dg1, this);

            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dttm = dateformt.format(date);
            String UNIQUE_DIAGNOSIS_ID = BaseConfig.GenerateCaseNotesID();

            BaseConfig.digid = UNIQUE_DIAGNOSIS_ID;

            String[] PatientInfo = this.autocompletePatientname.getText().toString().split("-");
            String PatientName = PatientInfo[0];
            String PatientID = PatientInfo[1];

            String[] PatientAgeGender = this.tvwAgegender.getText().toString().split("-");
            String PatientAge = PatientAgeGender[0];
            String PatientGender = PatientAgeGender[1];

            String Str_FBS = "0", Str_PPBS = "0", Str_RBS = "0";
            String Str_Weight = "0", Str_Height = "0", Str_BMI = "0", Str_Temperature = "0", Str_BPS = "0", Str_BPD = "0";
            String Str_Signs="";

            Str_Weight = BaseConfig.CheckCaseNotesString(this.autocompltWeight.getText().toString());
            Str_Height = BaseConfig.CheckCaseNotesString(this.autocompltHeight.getText().toString());
            Str_BMI = BaseConfig.CheckCaseNotesString(this.autocompltBmi.getText().toString());
            Str_Temperature = BaseConfig.CheckCaseNotesString(this.autocompltTemperature.getText().toString());
            Str_BPS = BaseConfig.CheckCaseNotesString(this.autocompltBps.getText().toString());
            Str_BPD = BaseConfig.CheckCaseNotesString(this.autocompltBpd.getText().toString());

            Str_FBS     =  BaseConfig.CheckCaseNotesString(this.autocompltFbs.getText().toString());
            Str_PPBS    =  BaseConfig.CheckCaseNotesString(this.autocompltPpbs.getText().toString());
            Str_RBS     =  BaseConfig.CheckCaseNotesString(this.autocompltRbs.getText().toString());

            Str_Signs = BaseConfig.CheckCaseNotesString(this.multiautoSigns.getText().toString());

            values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatientID);
            values.put("DiagId", UNIQUE_DIAGNOSIS_ID);
            values.put("pname", PatientName);
            values.put("gender", PatientGender);
            values.put("age", PatientAge);
            values.put("mobnum", BaseConfig.docmobile);
            values.put("refdocname", BaseConfig.doctorname);
            values.put("clinicname", BaseConfig.clinicname);
            values.put("Diagnosis", this.multiautoDiagnosis.getText().toString());
            values.put("BpS", Str_BPS);
            values.put("BpD", Str_BPD);
            values.put("FBS", Str_FBS);
            values.put("PPS", Str_PPBS);
            values.put("RBS", Str_RBS);
            values.put("PWeight", Str_Weight);
            values.put("temperature", Str_Temperature);
            values.put("Ecgfile", "");
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("treatmentfor", this.multiautoTreatmentfor.getText().toString());
            values.put("bloodgroup", "");
            values.put("imeino", BaseConfig.Imeinum);
            values.put("docsign", "");
            values.put("Isupdate", 0);
            values.put("height", Str_Height);
            values.put("bmi", Str_BMI);
            values.put("HID", BaseConfig.HID);
            values.put("Signs", Str_Signs);
            db.insert(BaseConfig.TABLE_DIAGONIS, null, values);


            String Update_Query = "update Diagnosismvalue set mdiagnum=mdiagnum +1";
            BaseConfig.SaveData(Update_Query);


            //1.)//CaseNote_GeneralExamination
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.GENERAL_INFORMATION_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //2.)Insert_Query_CaseNote_Cardiovascular
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.CARDIOVASCULAR_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //3.)Insert_Query_CaseNote_RespiratorySystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.RESPIRATORY_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //4.)Insert_Query_CaseNote_Gastrointestinal
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.GASTRO_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //5.)Insert_Query_CaseNote_Neurology
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.NEURO_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            //6.)Insert_Query_CaseNote_Motor
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.LOCOMOTOR_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            //7.)Insert_Query_CaseNote_OtherSystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.OTHERS_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            //8.)Insert_Query_CaseNote_ClinicalData
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.CLINICAL_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //9.)Insert_Query_CaseNote_RenalSystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.RENAL_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //10.)Insert_Query_CaseNote_EndocrineSystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.ENDOCRINE_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //11.)Insert_Query_CaseNote_PNC
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.PNC_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //12.)Insert_Query_CaseNote_Dental
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            this.DENTAL_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            this.ShowSweetAlert(this.getString(string.casenotes_saved), UNIQUE_DIAGNOSIS_ID);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DENTAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.dental != null && this.dental.arrowDentalSystem.getVisibility() == View.VISIBLE) {
            if (this.dental != null && this.dental.toothChildLayout.getVisibility() == View.VISIBLE) {

                JSONArray jsonArraynew = null;
                jsonArraynew = new JSONArray();

                try {
                    //Upper jaw line right/left
                    String str_cchkbxUr8 = this.CheckDentalSystem(this.dental.cchkbxUr8, 1);
                    String str_cchkbxUr7 = this.CheckDentalSystem(this.dental.cchkbxUr7, 1);
                    String str_cchkbxUr6 = this.CheckDentalSystem(this.dental.cchkbxUr6, 1);
                    String str_cchkbxUr5 = this.CheckDentalSystem(this.dental.cchkbxUr5, 1);
                    String str_cchkbxUr4 = this.CheckDentalSystem(this.dental.cchkbxUr4, 1);

                    String str_cchkbxUl1 = this.CheckDentalSystem(this.dental.cchkbxUl1, 2);
                    String str_cchkbxUl2 = this.CheckDentalSystem(this.dental.cchkbxUl2, 2);
                    String str_cchkbxUl3 = this.CheckDentalSystem(this.dental.cchkbxUl3, 2);
                    String str_cchkbxUl4 = this.CheckDentalSystem(this.dental.cchkbxUl4, 2);
                    String str_cchkbxUl5 = this.CheckDentalSystem(this.dental.cchkbxUl5, 2);


                    //Lower Jaw line right/left
                    String str_cchkbxLr1 = this.CheckDentalSystem(this.dental.cchkbxLr1, 3);
                    String str_cchkbxLr2 = this.CheckDentalSystem(this.dental.cchkbxLr2, 3);
                    String str_cchkbxLr3 = this.CheckDentalSystem(this.dental.cchkbxLr3, 3);
                    String str_cchkbxLr4 = this.CheckDentalSystem(this.dental.cchkbxLr4, 3);
                    String str_cchkbxLr5 = this.CheckDentalSystem(this.dental.cchkbxLr5, 3);


                    String str_cchkbxLl1 = this.CheckDentalSystem(this.dental.cchkbxLl1, 4);
                    String str_cchkbxLl2 = this.CheckDentalSystem(this.dental.cchkbxLl2, 4);
                    String str_cchkbxLl3 = this.CheckDentalSystem(this.dental.cchkbxLl3, 4);
                    String str_cchkbxLl4 = this.CheckDentalSystem(this.dental.cchkbxLl4, 4);
                    String str_cchkbxLl5 = this.CheckDentalSystem(this.dental.cchkbxLl5, 4);


                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(new JSONObject().put("upper_right_jaw", str_cchkbxUr8 + str_cchkbxUr7 + str_cchkbxUr6 + str_cchkbxUr5 + str_cchkbxUr4));
                    jsonArray.put(new JSONObject().put("upper_left_jaw", str_cchkbxUl1 + str_cchkbxUl2 + str_cchkbxUl3 + str_cchkbxUl4 + str_cchkbxUl5));
                    jsonArray.put(new JSONObject().put("lower_right_jaw", str_cchkbxLr1 + str_cchkbxLr2 + str_cchkbxLr3 + str_cchkbxLr4 + str_cchkbxLr5));
                    jsonArray.put(new JSONObject().put("lower_left_jaw", str_cchkbxLl1 + str_cchkbxLl2 + str_cchkbxLl3 + str_cchkbxLl4 + str_cchkbxLl5));
                    jsonArraynew.put(new JSONObject().put("Child", jsonArray.toString()));

                    String str_chkbxUr8 = this.CheckDentalSystem(this.dental.chkbxUr8, 1);
                    String str_chkbxUr7 = this.CheckDentalSystem(this.dental.chkbxUr7, 1);
                    String str_chkbxUr6 = this.CheckDentalSystem(this.dental.chkbxUr6, 1);
                    String str_chkbxUr5 = this.CheckDentalSystem(this.dental.chkbxUr5, 1);
                    String str_chkbxUr4 = this.CheckDentalSystem(this.dental.chkbxUr4, 1);
                    String str_chkbxUr3 = this.CheckDentalSystem(this.dental.chkbxUr3, 1);
                    String str_chkbxUr2 = this.CheckDentalSystem(this.dental.chkbxUr2, 1);
                    String str_chkbxUr1 = this.CheckDentalSystem(this.dental.chkbxUr1, 1);


                    String str_chkbxUl1 = this.CheckDentalSystem(this.dental.chkbxUl1, 2);
                    String str_chkbxUl2 = this.CheckDentalSystem(this.dental.chkbxUl2, 2);
                    String str_chkbxUl3 = this.CheckDentalSystem(this.dental.chkbxUl3, 2);
                    String str_chkbxUl4 = this.CheckDentalSystem(this.dental.chkbxUl4, 2);
                    String str_chkbxUl5 = this.CheckDentalSystem(this.dental.chkbxUl5, 2);
                    String str_chkbxUl6 = this.CheckDentalSystem(this.dental.chkbxUl6, 2);
                    String str_chkbxUl7 = this.CheckDentalSystem(this.dental.chkbxUl7, 2);
                    String str_chkbxUl8 = this.CheckDentalSystem(this.dental.chkbxUl8, 2);

                    //Lower jaw line right/left
                    String str_chkbxLr1 = this.CheckDentalSystem(this.dental.chkbxLr1, 3);
                    String str_chkbxLr2 = this.CheckDentalSystem(this.dental.chkbxLr2, 3);
                    String str_chkbxLr3 = this.CheckDentalSystem(this.dental.chkbxLr3, 3);
                    String str_chkbxLr4 = this.CheckDentalSystem(this.dental.chkbxLr4, 3);
                    String str_chkbxLr5 = this.CheckDentalSystem(this.dental.chkbxLr5, 3);
                    String str_chkbxLr6 = this.CheckDentalSystem(this.dental.chkbxLr6, 3);
                    String str_chkbxLr7 = this.CheckDentalSystem(this.dental.chkbxLr7, 3);
                    String str_chkbxLr8 = this.CheckDentalSystem(this.dental.chkbxLr8, 3);


                    String str_chkbxLl1 = this.CheckDentalSystem(this.dental.chkbxLl1, 4);
                    String str_chkbxLl2 = this.CheckDentalSystem(this.dental.chkbxLl2, 4);
                    String str_chkbxLl3 = this.CheckDentalSystem(this.dental.chkbxLl3, 4);
                    String str_chkbxLl4 = this.CheckDentalSystem(this.dental.chkbxLl4, 4);
                    String str_chkbxLl5 = this.CheckDentalSystem(this.dental.chkbxLl5, 4);
                    String str_chkbxLl6 = this.CheckDentalSystem(this.dental.chkbxLl6, 4);
                    String str_chkbxLl7 = this.CheckDentalSystem(this.dental.chkbxLl7, 4);
                    String str_chkbxLl8 = this.CheckDentalSystem(this.dental.chkbxLl8, 4);


                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(new JSONObject().put("upper_right_jaw", str_chkbxUr1 + str_chkbxUr2 + str_chkbxUr3 + str_chkbxUr4 + str_chkbxUr5 + str_chkbxUr6 + str_chkbxUr7 + str_chkbxUr8));
                    jsonArray1.put(new JSONObject().put("upper_left_jaw", str_chkbxUl1 + str_chkbxUl2 + str_chkbxUl3 + str_chkbxUl4 + str_chkbxUl5 + str_chkbxUl6 + str_chkbxUl7 + str_chkbxUl8));
                    jsonArray1.put(new JSONObject().put("lower_right_jaw", str_chkbxLr1 + str_chkbxLr2 + str_chkbxLr3 + str_chkbxLr4 + str_chkbxLr5 + str_chkbxLr6 + str_chkbxLr7 + str_chkbxLr8));
                    jsonArray1.put(new JSONObject().put("lower_left_jaw", str_chkbxLl1 + str_chkbxLl2 + str_chkbxLl3 + str_chkbxLl4 + str_chkbxLl5 + str_chkbxLl6 + str_chkbxLl7 + str_chkbxLl8));

                    jsonArraynew.put(new JSONObject().put("Adult", jsonArray1.toString()));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("HID", BaseConfig.HID);
                values.put("dental_sys", jsonArraynew.toString());

                db.insert(BaseConfig.TABLE_CASENOTES_DENTALSYSTEM, null, values);
                //Log.e("Insert Dental Child : ", values.toString());
            }


        }
    }

    private void PNC_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.pnc != null && this.pnc.arrowPnc.getVisibility() == View.VISIBLE) {
            try {
                String PNC_Json = "", PPC_Json = "", COB_Json = "";
                JSONArray jsonary1 = new JSONArray();
                JSONArray jsonary2 = new JSONArray();
                JSONArray jsonary3 = new JSONArray();
                JSONObject obj = new JSONObject();

                //PNC **************************************************************************************

                String str_dateofdelivery = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.editTextDateOfDelivery, 1));
                String str_placeofdelivery = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextPlaceOfDelivery, 1));
                String str_terms = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextTermPreterm, 1));
                String str_postdelivery = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextPostDelivery, 1));
                String str_complications = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextComplications, 1));
                String str_genderofchild = BaseConfig.GetWidgetOperations(this.pnc.radGrpSexofbaby, 1);
                String str_weight_baby_kg = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextKg, 1));
                String str_weight_baby_gms = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextGms, 1));
                String str_criedafter_birth = BaseConfig.GetWidgetOperations(this.pnc.radGrpCried, 1);
                String str_initi_breastfeeding = BaseConfig.GetWidgetOperations(this.pnc.radGrpFeed, 1);

                obj.put("str_dateofdelivery", str_dateofdelivery);
                obj.put("str_placeofdelivery", str_placeofdelivery);
                obj.put("str_tod_n", BaseConfig.GetWidgetOperations(this.pnc.chkNormal, 1));
                obj.put("str_tod_instr", BaseConfig.GetWidgetOperations(this.pnc.chkInst, 1));
                obj.put("str_tod_cs", BaseConfig.GetWidgetOperations(this.pnc.chkCs, 1));
                obj.put("str_terms", str_terms);
                obj.put("str_postdelivery", str_postdelivery);
                obj.put("str_complications", str_complications);
                obj.put("str_genderofchild", str_genderofchild);
                obj.put("str_weight_baby_kg", str_weight_baby_kg);
                obj.put("str_weight_baby_gms", str_weight_baby_gms);
                obj.put("str_criedafter_birth", str_criedafter_birth);
                obj.put("str_initi_breastfeeding", str_initi_breastfeeding);
                jsonary1.put(obj);

                //PPC **************************************************************************************
                String str_any_comapl = "", str_edt_ppc_pallor = "", str_edt_pulse = "", str_edt_bloodpres = "", str_edt_temperature = "", str_edt_familyplanning = "", str_edt_anyothers = "";

                String str_rg_breast = "", str_rg_nipples = "", str_rg_uterus = "", str_rg_bleednig = "", str_rg_lochia = "", str_rg_epi = "";

                str_any_comapl = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextAnyComplaints, 1));
                str_edt_ppc_pallor = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextPallor, 1));
                str_edt_pulse = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextPulseRate, 1));
                str_edt_bloodpres = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextBloodpressure, 1));
                str_edt_temperature = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextTemperature, 1));
                str_edt_familyplanning = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.editTextFamilyPlanningCounselling, 1));
                str_edt_anyothers = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextAnyOtherComplications, 1));


                str_rg_breast = BaseConfig.GetWidgetOperations(this.pnc.radGrpBreast, 1);

                str_rg_nipples = BaseConfig.GetWidgetOperations(this.pnc.radGrpNipple, 1);

                str_rg_uterus = BaseConfig.GetWidgetOperations(this.pnc.radGrpUterus, 1);

                str_rg_bleednig = BaseConfig.GetWidgetOperations(this.pnc.radGrpBleed, 1);

                str_rg_lochia = BaseConfig.GetWidgetOperations(this.pnc.radGrpLochia, 1);

                str_rg_epi = BaseConfig.GetWidgetOperations(this.pnc.radGrpTear, 1);

                obj = new JSONObject();
                obj.put("str_any_comapl", str_any_comapl);
                obj.put("str_edt_ppc_pallor", str_edt_ppc_pallor);
                obj.put("str_edt_pulse", str_edt_pulse);
                obj.put("str_edt_bloodpres", str_edt_bloodpres);
                obj.put("str_edt_temperature", str_edt_temperature);
                obj.put("str_edt_familyplanning", str_edt_familyplanning);
                obj.put("str_edt_anyothers", str_edt_anyothers);
                obj.put("str_rg_breast", str_rg_breast);
                obj.put("str_rg_nipples", str_rg_nipples);
                obj.put("str_rg_uterus", str_rg_uterus);
                obj.put("str_rg_bleednig", str_rg_bleednig);
                obj.put("str_rg_lochia", str_rg_lochia);
                obj.put("str_rg_epi", str_rg_epi);
                jsonary2.put(obj);


                //COB **************************************************************************************
                String str_edt_urinepassed = "", str_edt_stoolpassed = "", str_edt_diarrhea = "", str_edt_vomiting = "", str_edt_convulions = "", str_edt_cb_temp = "", str_edt_cb_jaundice = "", str_edt_anyothercompli = "";
                String str_rg_activity = "", str_eg_sucking = "", str_rg_breathing = "", str_rg_chest = "", str_rg_skinpus = "";
                String str_umbilical = "";

                str_edt_urinepassed = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edtUrine, 1));
                str_edt_stoolpassed = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edtStool, 1));
                str_edt_diarrhea = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edtDiarrhea, 1));
                str_edt_vomiting = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edtVomit, 1));
                str_edt_convulions = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edtConv, 1));
                str_edt_cb_temp = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextTemper, 1));
                str_edt_cb_jaundice = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextJaundice, 1));
                str_umbilical = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextStump, 1));
                str_edt_anyothercompli = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.pnc.edittextAnyOtherComplications, 1));


                str_rg_activity = BaseConfig.GetWidgetOperations(this.pnc.radGrpActivity, 1);//getRadioChecked(rg_activity);

                str_eg_sucking = BaseConfig.GetWidgetOperations(this.pnc.radGrpSucking, 1);//getRadioChecked(eg_sucking);

                str_rg_breathing = BaseConfig.GetWidgetOperations(this.pnc.radGrpBreathing, 1);//getRadioChecked(rg_breathing);

                str_rg_chest = BaseConfig.GetWidgetOperations(this.pnc.radGrpChest, 1);//getRadioChecked(rg_chest);

                str_rg_skinpus = BaseConfig.GetWidgetOperations(this.pnc.radGrpSkin, 1);//getRadioChecked(rg_skinpus);

                obj = new JSONObject();
                obj.put("str_edt_urinepassed", str_edt_urinepassed);
                obj.put("str_edt_stoolpassed", str_edt_stoolpassed);
                obj.put("str_edt_diarrhea", str_edt_diarrhea);
                obj.put("str_edt_vomiting", str_edt_vomiting);
                obj.put("str_edt_convulions", str_edt_convulions);
                obj.put("str_edt_cb_temp", str_edt_cb_temp);
                obj.put("str_edt_cb_jaundice", str_edt_cb_jaundice);
                obj.put("str_umbilical", str_umbilical);
                obj.put("str_edt_anyothercompli", str_edt_anyothercompli);
                obj.put("str_rg_activity", str_rg_activity);
                obj.put("str_eg_sucking", str_eg_sucking);
                obj.put("str_rg_breathing", str_rg_breathing);
                obj.put("str_rg_chest", str_rg_chest);
                obj.put("str_rg_skinpus", str_rg_skinpus);
                jsonary3.put(obj);


                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("pnc_details", jsonary1.toString());
                values.put("ppc_details", jsonary2.toString());
                values.put("cob_details", jsonary3.toString());
                values.put("HID", BaseConfig.HID);
                db.insert(BaseConfig.TABLE_CASENOTES_PNCSYSTEM, null, values);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void ENDOCRINE_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.endosys != null && this.endosys.arrowEndocrineSystem.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Endocrine", BaseConfig.GetWidgetOperations(this.endosys.edtEndocrine, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_ENDOCRINE, null, values);


        }
    }

    private void RENAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.renal != null && this.renal.arrowRenalSystem.getVisibility() == View.VISIBLE) {

            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("dysuria", BaseConfig.GetWidgetOperations(this.renal.switchDysuria1, 1));
            values.put("pyuria", BaseConfig.GetWidgetOperations(this.renal.switchPyuria1, 1));
            values.put("haematuria", BaseConfig.GetWidgetOperations(this.renal.spinnerHaematuria, 1));
            values.put("oliguria", BaseConfig.GetWidgetOperations(this.renal.switchOliguria1, 1));
            values.put("polyuria", BaseConfig.GetWidgetOperations(this.renal.switchPolyuria1, 1));
            values.put("anuria", BaseConfig.GetWidgetOperations(this.renal.switchAnuria1, 1));
            values.put("nocturia", BaseConfig.GetWidgetOperations(this.renal.switchNocturia1, 1));
            values.put("urethraldischarge", BaseConfig.GetWidgetOperations(this.renal.switchUrethraldischarge1, 1));
            values.put("prostate", BaseConfig.GetWidgetOperations(this.renal.switchProstate, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_RENAL, null, values);


        }
    }

    private void CLINICAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.clinical != null && this.clinical.arrowClinicalData.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Heamoglobin", BaseConfig.GetWidgetOperations(this.clinical.editTextHaemoglobinGDl, 1));
            values.put("wbc", BaseConfig.GetWidgetOperations(this.clinical.editTextWBC, 1));
            values.put("rbc", BaseConfig.GetWidgetOperations(this.clinical.editTextRbc, 1));
            values.put("esr", BaseConfig.GetWidgetOperations(this.clinical.editTextEsr, 1));
            values.put("polymorphs", BaseConfig.GetWidgetOperations(this.clinical.editTextPolymorphs, 1));
            values.put("lymphocytes", BaseConfig.GetWidgetOperations(this.clinical.editTextLymphocytes, 1));
            values.put("monocytes", BaseConfig.GetWidgetOperations(this.clinical.editTextMonocytes, 1));
            values.put("basophils", BaseConfig.GetWidgetOperations(this.clinical.editTextBasophils, 1));
            values.put("eosinophils", BaseConfig.GetWidgetOperations(this.clinical.editTextEosinophils, 1));
            values.put("urea", BaseConfig.GetWidgetOperations(this.clinical.editTextUrea, 1));
            values.put("creatinine", BaseConfig.GetWidgetOperations(this.clinical.editTextCreatinine, 1));
            values.put("UricAcid", BaseConfig.GetWidgetOperations(this.clinical.editTextUricAcid, 1));
            values.put("Sodium", BaseConfig.GetWidgetOperations(this.clinical.editTextSodium, 1));
            values.put("Potassium", BaseConfig.GetWidgetOperations(this.clinical.editTextPotassium, 1));
            values.put("Chloride", BaseConfig.GetWidgetOperations(this.clinical.editTextChloride, 1));
            values.put("Bicarbonate", BaseConfig.GetWidgetOperations(this.clinical.editTextBicarbonate, 1));
            values.put("TotalCholesterol", BaseConfig.GetWidgetOperations(this.clinical.editTextTotalCholesterol, 1));
            values.put("LDL", BaseConfig.GetWidgetOperations(this.clinical.editTextLdl, 1));
            values.put("HDL", BaseConfig.GetWidgetOperations(this.clinical.editTextHdl, 1));
            values.put("VLDL", BaseConfig.GetWidgetOperations(this.clinical.editTextVldl, 1));
            values.put("Triglycerides", BaseConfig.GetWidgetOperations(this.clinical.editTextTriglycerides, 1));
            values.put("Serumbilirubin", BaseConfig.GetWidgetOperations(this.clinical.editTextSerumBilirubinTotal, 1));
            values.put("Direct", BaseConfig.GetWidgetOperations(this.clinical.editTextDirect, 1));
            values.put("Indirect", BaseConfig.GetWidgetOperations(this.clinical.editTextIndirect, 1));
            values.put("Totalprotein", BaseConfig.GetWidgetOperations(this.clinical.editTextTotalProtein, 1));
            values.put("Albumin", BaseConfig.GetWidgetOperations(this.clinical.editTextAlbumin, 1));
            values.put("Globulin", BaseConfig.GetWidgetOperations(this.clinical.editTextGlobulin, 1));
            values.put("SGOT", BaseConfig.GetWidgetOperations(this.clinical.editTextSgot, 1));
            values.put("SGPT", BaseConfig.GetWidgetOperations(this.clinical.editTextSgpt, 1));
            values.put("AlkalinePhosphatase", BaseConfig.GetWidgetOperations(this.clinical.editTextAlkalinePhosphatase, 1));
            values.put("FreeT3", BaseConfig.GetWidgetOperations(this.clinical.editTextFreeT3, 1));
            values.put("FreeT4", BaseConfig.GetWidgetOperations(this.clinical.editTextFreeT4, 1));
            values.put("TSH", BaseConfig.GetWidgetOperations(this.clinical.editTextTsh, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_CLINICALDATA, null, values);

        }
    }

    private void OTHERS_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.othersys != null && this.othersys.arrowOtherSystems.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Othersystem", BaseConfig.GetWidgetOperations(this.othersys.edtOtherSystems, 1));
            values.put("AdditionalInfo", BaseConfig.GetWidgetOperations(this.othersys.edtAdditionalInformation, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_OTHERSSYSTEMS, null, values);


        }
    }

    private void LOCOMOTOR_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.locomotor != null && this.locomotor.arrowLocomotorSystem.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("symmetry", BaseConfig.GetWidgetOperations(this.locomotor.switchSymmetry1, 1));
            values.put("smooth_movement", BaseConfig.GetWidgetOperations(this.locomotor.switchSmoothMovement1, 1));
            values.put("arms_swing", BaseConfig.GetWidgetOperations(this.locomotor.switchArmsSwing1, 1));
            values.put("pelvic_tilt", BaseConfig.GetWidgetOperations(this.locomotor.switchPelvicTilt1, 1));
            values.put("stride_length", BaseConfig.GetWidgetOperations(this.locomotor.strideLength1, 1));
            values.put("cervical_lordosis", BaseConfig.GetWidgetOperations(this.locomotor.switchCervicalLordosis1, 1));
            values.put("lumbar_lordosis", BaseConfig.GetWidgetOperations(this.locomotor.switchLumbarLordosis1, 1));
            values.put("kyphosis", BaseConfig.GetWidgetOperations(this.locomotor.switchKyphosis1, 1));
            values.put("scoliosis", BaseConfig.GetWidgetOperations(this.locomotor.switchScoliosis1, 1));
            values.put("llswelling", BaseConfig.GetWidgetOperations(this.locomotor.switchLlSwelling1, 1));
            values.put("lldeformity", BaseConfig.GetWidgetOperations(this.locomotor.switchLlDeformity1, 1));
            values.put("lllimbshortening", BaseConfig.GetWidgetOperations(this.locomotor.switchLlLimbShortening1, 1));
            values.put("llmuscle_wasting", BaseConfig.GetWidgetOperations(this.locomotor.switchLlMuscleWasting1, 1));
            values.put("llremarks", BaseConfig.GetWidgetOperations(this.locomotor.editTextRemarks, 1));
            values.put("ulswelling", BaseConfig.GetWidgetOperations(this.locomotor.switchUpperLimbSwelling, 1));
            values.put("uldeformity", BaseConfig.GetWidgetOperations(this.locomotor.switchUpperLimbDeformity, 1));
            values.put("ullimbshortening", BaseConfig.GetWidgetOperations(this.locomotor.switchUpperLimbShortening, 1));
            values.put("ulmuscle_wasting", BaseConfig.GetWidgetOperations(this.locomotor.switchUpperLimbWasting1, 1));
            values.put("ulremarks", BaseConfig.GetWidgetOperations(this.locomotor.editTextRemarks1, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_LOCOMOTOR, null, values);

        }
    }

    private void NEURO_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.neurology != null && this.neurology.arrowNeurologySystem.getVisibility() == View.VISIBLE) {
            //***Cognitive Function
            String sensoryspnner = "", cornealytxt = "", bicepsntxt = "", tricepsntxt = "", supinatorytxt = "", kneeytxt = "", ankleytxt = "", plantarntxt = "";

//hi ram install apk to rucha
            //Insert CaseNote_Neurology
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Pupilsize", BaseConfig.GetWidgetOperations(this.neurology.radiogroupPupilsize, 1));
            values.put("Speech", BaseConfig.GetWidgetOperations(this.neurology.radiogroupSpeech, 1));
            values.put("Carodit", BaseConfig.GetWidgetOperations(this.neurology.radiogroupCarotid, 1));
            values.put("Amnesia", BaseConfig.GetWidgetOperations(this.neurology.switchAmnesiay, 1));
            values.put("Apraxia", BaseConfig.GetWidgetOperations(this.neurology.switchApraxiay, 1));
            values.put("Cognitive_Function", BaseConfig.GetWidgetOperations(this.neurology.radiogroupCognitiveFunction, 1));
            values.put("Bulk", BaseConfig.GetWidgetOperations(this.neurology.radiogroupBulk, 1));
            values.put("Tone", BaseConfig.GetWidgetOperations(this.neurology.radiogroupTones, 1));
            values.put("Power_LUL", BaseConfig.GetWidgetOperations(this.neurology.seekBarLeftUpperLimb, 1));
            values.put("Power_RUL", BaseConfig.GetWidgetOperations(this.neurology.seekBarRightUpperLimb, 1));
            values.put("Power_LLL", BaseConfig.GetWidgetOperations(this.neurology.seekBarLeftLowerLimb, 1));
            values.put("Power_RLL", BaseConfig.GetWidgetOperations(this.neurology.seekBarRightLowerLimb, 1));
            values.put("Sensory", BaseConfig.GetWidgetOperations(this.neurology.spinnerSensory, 1));
            values.put("Reflexes_Corneal", BaseConfig.GetWidgetOperations(this.neurology.spinnerCorneal, 1));
            values.put("Reflexes_Biceps", BaseConfig.GetWidgetOperations(this.neurology.spinnerBiceps, 1));
            values.put("Reflexes_Triceps", BaseConfig.GetWidgetOperations(this.neurology.spinnerTriceps, 1));
            values.put("Reflexes_Supinator", BaseConfig.GetWidgetOperations(this.neurology.spinnerSupinator, 1));
            values.put("Reflexes_Knee", BaseConfig.GetWidgetOperations(this.neurology.spinnerKnee, 1));
            values.put("Reflexes_Ankle", BaseConfig.GetWidgetOperations(this.neurology.spinnerAnkle, 1));
            values.put("Reflexes_Plantor", BaseConfig.GetWidgetOperations(this.neurology.spinnerPlantar, 1));
            values.put("congentail_abnormality", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewCongentialAbnormality, 1));
            values.put("mentalstatus", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewMentalStatus, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_NEUROLOGY, null, values);

            //Insert CaseNote_CNS
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("oriented", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewOriented, 1));
            values.put("neckrigidity", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewNeckRigidity, 1));
            values.put("confused", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewConfused, 1));
            values.put("exaggerated", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewExaggerated, 1));
            values.put("extensor", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewExtenstor, 1));
            values.put("gsscore", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewGsScore, 1));
            values.put("incomprehensible", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewIncomprehensible, 1));
            values.put("depressed", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewDepressed, 1));
            values.put("flexor", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewFlexor, 1));
            values.put("coma", BaseConfig.GetWidgetOperations(this.neurology.autoCompleteTextViewComa, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_CNS, null, values);


        }
    }

    //All systems end

    private void GASTRO_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.gastro != null && this.gastro.arrowGastrointestinalSystem.getVisibility() == View.VISIBLE) {

            String Gastrointestinalcnt = this.concatpersonalhistrycnt(this.abdomen_list).toString();
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("shapeofabdomen", BaseConfig.GetWidgetOperations(this.gastro.spinnerShapeOfTheAbdomen, 1));
            values.put("Abdomen", Gastrointestinalcnt);
            values.put("Visible_Pulsations", BaseConfig.GetWidgetOperations(this.gastro.switchVisiblePulsation1, 1));
            values.put("Visual_Peristalsis", BaseConfig.GetWidgetOperations(this.gastro.switchVisiblePerist1, 1));
            values.put("Abdominal_Palpation", BaseConfig.GetWidgetOperations(this.gastro.spinnerAbdominalPalpation, 1));
            values.put("Splenomegaly", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewSplenomegaly, 1));
            values.put("Hepatomegaly", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewHepatomegaly, 1));
            values.put("PalpableMasses", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewPalpableMasses, 1));
            values.put("Bowelsound", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewBowelSounds, 1));
            values.put("Hernial", BaseConfig.GetWidgetOperations(this.gastro.checkBoxOrifices, 1) + BaseConfig.GetWidgetOperations(this.gastro.checkBoxFree, 1));
            values.put("Spleen", BaseConfig.GetWidgetOperations(this.gastro.switchSpleen, 1));
            values.put("Liver", BaseConfig.GetWidgetOperations(this.gastro.switchLiverNormal, 1));
            values.put("organomegely", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewOrganomegely, 1));
            values.put("freefuild", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewFreeFluidInTheAbdomen, 1));
            values.put("distension", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewDistension, 1));
            values.put("tenderness", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewTenderness, 1));
            values.put("scrotum	", BaseConfig.GetWidgetOperations(this.gastro.autoCompleteTextViewScrotum, 1));
            values.put("HID", BaseConfig.HID);

            db.insert(BaseConfig.TABLE_CASENOTES_GASTROINTESTINAL, null, values);

        }
    }

    private void RESPIRATORY_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.respiratory != null && this.respiratory.arrowRespiratorySystem.getVisibility() == View.VISIBLE) {

            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("BreathSoundEqual", BaseConfig.GetWidgetOperations(this.respiratory.radiogroupBreathsoundequal, 1));
            values.put("BreathSoundEqualOptions", BaseConfig.GetWidgetOperations(this.respiratory.radiogroupBreathsoundequalOptns, 1));
            values.put("Breathsound", BaseConfig.GetWidgetOperations(this.respiratory.spinnerBreathSound, 1));
            values.put("Trachea", BaseConfig.GetWidgetOperations(this.respiratory.spinner_Trachea, 1));
            values.put("Kyphosis_Scoliosis", BaseConfig.GetWidgetOperations(this.respiratory.checkBoxKyphosis, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.respiratory.checkBoxScoliosis, 1));
            values.put("Crepitation", BaseConfig.GetWidgetOperations(this.respiratory.checkBoxLeft, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.respiratory.checkBoxRight, 1));
            values.put("Bronchi", BaseConfig.GetWidgetOperations(this.respiratory.checkBoxRhonchiLeft, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.respiratory.checkBoxRhonchiRight, 1));
            values.put("Pulserate", "");
            values.put("Note", BaseConfig.GetWidgetOperations(this.respiratory.editTextRespiratoryRate, 1));
            values.put("shapeofchest", BaseConfig.GetWidgetOperations(this.respiratory.spinnerShapeOfTheChest, 1));
            values.put("spo2", BaseConfig.GetWidgetOperations(this.respiratory.autoCompleteTextViewSpo2, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_RESPIRATORYSYSTEMS, null, values);

        }
    }

    private void CARDIOVASCULAR_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.cardio != null && this.cardio.arrowCardioVascularSystem.getVisibility() == View.VISIBLE) {
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Beat", BaseConfig.GetWidgetOperations(this.cardio.checkBoxS1, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.cardio.checkBoxS2, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.cardio.checkBoxS3, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.cardio.checkBoxS4, 1));
            values.put("Murmur", BaseConfig.GetWidgetOperations(this.cardio.checkBoxSystolic, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.cardio.checkBoxDiastolic, 1) +"\n"+ BaseConfig.GetWidgetOperations(this.cardio.checkBoxOthers, 1));
            values.put("Pericardial_Rub", BaseConfig.GetWidgetOperations(this.cardio.switchPericardialrub, 1));
            values.put("Pulserate", BaseConfig.GetWidgetOperations(this.cardio.switchPulserate, 1));
            values.put("heartrate", BaseConfig.GetWidgetOperations(this.cardio.editTextHeartrate, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_CARDIOVASCULAR, null, values);

        }
    }

    public void GENERAL_INFORMATION_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (this.general != null && this.general.arrowGeneralExamination.getVisibility() == View.VISIBLE) {
            //***Clubbing,Pedal_edema
            String tempstring = BaseConfig.GetWidgetOperations(this.general.checkBoxPallorness, 1) +
                    BaseConfig.GetWidgetOperations(this.general.checkBoxThyroid, 1) +
                    BaseConfig.GetWidgetOperations(this.general.checkBoxFacePuffiness, 1) +
                    BaseConfig.GetWidgetOperations(this.general.checkBoxJaundice, 1);

            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Anaemic", BaseConfig.GetWidgetOperations(this.general.switchAnaemic, 1));
            values.put("Icterus", BaseConfig.GetWidgetOperations(this.general.switchIcterus, 1));
            values.put("Stenosis", BaseConfig.GetWidgetOperations(this.general.switchCyanosis, 1));
            values.put("Clubbing", BaseConfig.GetWidgetOperations(this.general.switchClubbing, 1));
            values.put("Limbphantom", BaseConfig.GetWidgetOperations(this.general.switchLymphadenopathy, 1));
            values.put("Vericoveins", BaseConfig.GetWidgetOperations(this.general.switchVaricoseVeins, 1));
            values.put("Pedal_edema", BaseConfig.GetWidgetOperations(this.general.switchPedalEdema, 1));
            values.put("skin_infection", BaseConfig.GetWidgetOperations(this.general.switchSkininfection, 1));
            values.put("short_stat", BaseConfig.GetWidgetOperations(this.general.switchShortStatured, 1));
            values.put("built", BaseConfig.GetWidgetOperations(this.general.radiogroupBuilt, 1));
            values.put("extra_generalexam", tempstring);
            values.put("pog", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtPog, 1)));
            values.put("pallor", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtPallor, 1)));
            values.put("oedema", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtOedema, 1)));
            values.put("fundalheight", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtHeight, 1)));
            values.put("lie", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtLiePresentation, 1)));
            values.put("fetalmovement", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtFetalmovements, 1)));
            values.put("fetalheight", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtFetalheartrate, 1)));
            values.put("pv", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtPv, 1)));
            values.put("anycompl", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(this.general.edtAnycomplaints, 1)));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_GENERALEXAMINATION, null, values);

        }
    }

    public String CheckDentalSystem(CheckBox chkbx, int position) {
        String rt = "";

        if (position == 1)//Upper right jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText() + ",";
            } else {
                rt = "";
            }

        } else if (position == 2)//Upper left jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText() + ",";
            } else {
                rt = "";
            }

        } else if (position == 3)//Lower right jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText() + ",";
            } else {
                rt = "";
            }

        } else if (position == 4)//Lower left jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText() + ",";
            } else {
                rt = "";
            }
        }

        return rt;
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private StringBuilder concatpersonalhistrycnt(List<String> personalarylist2) {

        StringBuilder concatpersonalhistrystr = new StringBuilder();

        for (int j = 0; j < personalarylist2.size(); j++) {
            personalarylist2.get(j);

            //noinspection StringConcatenationInLoop
            concatpersonalhistrystr.append(personalarylist2.get(j) + "\n");

        }
        return concatpersonalhistrystr;
    }

    public void LOAD_CASENOTES_SYSTEMS_DYNAMICALLY() throws Exception {

        String Query = "select * from drsettings";
        new SqliteReader().setQuery(Query).onDataReceiver(c -> this.ReturnValues = c.getString(c.getColumnIndex("CaseNotes_Systems")));

        Log.e("Settigns_Systems: ", this.ReturnValues);

        if (this.ReturnValues.length() > 0) {

            String[] arr = this.ReturnValues.substring(0, this.ReturnValues.length() - 1).split(",");

            for (String s : arr) {

                if (this.contains(arr, s)) {
                    switch (s) {
                        case "1":

                            this.general = new CaseNotes.LOAD_GENERAL_INFORMATION();

                            break;

                        case "2":
                            this.cardio = new CaseNotes.LOAD_CARDIO_VASCULAR();

                            break;

                        case "3":
                            this.respiratory = new CaseNotes.LOAD_RESPIRATORY();

                            break;

                        case "4":
                            this.gastro = new CaseNotes.LOAD_GASTRO();

                            break;

                        case "5":
                            this.neurology = new CaseNotes.LOAD_NEUROLOGY();

                            break;

                        case "6":
                            this.locomotor = new CaseNotes.LOAD_MOTOR();

                            break;

                        case "7":
                            this.renal = new CaseNotes.LOAD_RENAL();

                            break;

                        case "8":
                            this.endosys = new CaseNotes.LOAD_ENDOSYS();

                            break;

                        case "9":
                            this.clinical = new CaseNotes.LOAD_CLINICAL();

                            break;

                        case "10":
                            this.othersys = new CaseNotes.LOAD_OTHERSYS();
                            break;

                        case "11"://Dental
                            this.dental = new CaseNotes.LOAD_DENTAL();
                            break;

                        case "12"://PNC
                            this.pnc = new CaseNotes.LOAD_PNC();
                            break;

                    }
                }
            }

        }

    }

    public boolean contains(String[] arr, String item) {

        for (String n : arr) {
            if (item.equalsIgnoreCase(n)) {
                return true;
            }
        }
        return false;
    }

    private void toggleSectionInput(View view, View PrimaryLayout) {
        boolean show = CaseNotes.toggleArrow(view);
        if (show) {

            ViewAnimation.expand(PrimaryLayout);
        } else {
            ViewAnimation.collapse(PrimaryLayout);
        }
    }

    private void GETINITIALIZE() {

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.Glide_LoadDefaultImageView(this.imgvwPatientphoto);

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.lbl_Casenotes)));


        this.setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

        //   toolbar.setBackgroundColor(Color.parseColor(ClinicalInformation.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));

        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarBack.setOnClickListener(view -> this.LoadBack());

        if (BaseConfig.CurrentLangauges.equalsIgnoreCase("mr")) {
            this.values1 = new String[]{"निवडा", "Normal", "Hypoesthesia", "Hyperesthesia"};
            this.shapeofchest = new String[]{"निवडा", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            this.abdomenshape = new String[]{"निवडा", "Normal", "Scaphoid", "Sunken", "Distended"};
            this.abdomenpalpation = new String[]{"निवडा", "Soft", "Guarding", "Rigidity"};
            this.reflexes = new String[]{"निवडा", "Normal", "Reduced", "Increased", "Absent"};
            this.haematuriaarry = new String[]{"निवडा", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

        } else {
            this.values1 = new String[]{"Select", "Normal", "Hypoesthesia", "Hyperesthesia"};
            this.shapeofchest = new String[]{"Select", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            this.abdomenshape = new String[]{"Select", "Normal", "Scaphoid", "Sunken", "Distended"};
            this.abdomenpalpation = new String[]{"Select", "Soft", "Guarding", "Rigidity"};
            this.reflexes = new String[]{"Select", "Normal", "Reduced", "Increased", "Absent"};
            this.haematuriaarry = new String[]{"Select", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

        }


        String first = this.getString(string.patient_name);
        String next = "<font color='#EE0000'><b>*</b></font>";
        this.txtvwTitlePatientname.setText(Html.fromHtml(first + next));

        String first1 = this.getString(string.symptoms);
        String next1 = "<font color='#EE0000'><b>*</b></font>";
        this.txtvwTreatmentfor.setText(Html.fromHtml(first1 + next1));

        String first2 = this.getString(string.provisional_diagnosis);
        String next2 = "<font color='#EE0000'><b>*</b></font>";
        this.txtvwDiagnosis.setText(Html.fromHtml(first2 + next2));


        this.autocompletePatientname.setThreshold(1);

        this.multiautoDiagnosis.setText("");
        this.multiautoDiagnosis.setThreshold(1);
        this.multiautoDiagnosis.setTokenizer(new CommaTokenizer());

        this.multiautoSigns.setText("");
        this.multiautoSigns.setThreshold(1);
        this.multiautoSigns.setTokenizer(new CommaTokenizer());


        this.multiautoTreatmentfor.setText("");
        this.multiautoTreatmentfor.setThreshold(1);
        this.multiautoTreatmentfor.setTokenizer(new CommaTokenizer());


        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            String STATUS = b.getString("CONTINUE_STATUS");
            this.COMMON_PATIENT_ID = b.getString("PASSING_PATIENT_ID");

            this.FLAG_MYPATIENT = b.getString("FROM");

            assert STATUS != null;
            if (STATUS.equalsIgnoreCase("True")) {
                this.Load_Patient_Details(this.COMMON_PATIENT_ID);
            }
        } else {
            this.FLAG_MYPATIENT = "";
        }


        this.list_Diagnosis = BaseConfig.LoadMultiAutoValues(this.multiautoDiagnosis, this, this.Diagnosis_Query);
        BaseConfig.loadSpinner(this.multiautoDiagnosis, this.list_Treatment);

        this.list_Treatment = BaseConfig.LoadMultiAutoValues(this.multiautoTreatmentfor, this, this.Treatment_Query);
        BaseConfig.loadSpinner(this.multiautoTreatmentfor, this.list_Diagnosis);

        this.list_Signs = BaseConfig.LoadMultiAutoValues(this.multiautoSigns, this, this.Signs_Query);
        BaseConfig.loadSpinner(this.multiautoSigns, this.list_Signs);


    }

    private void showPopup_PatientWeight() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View customView = layoutInflater.inflate(layout.kdmc_layout_weight, null);

        Builder alertDialogBuilder = new Builder(this).setCancelable(false);
        alertDialogBuilder.setView(customView);


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

        kdmc_kumar.Utilities_Others.seek.DiscreteSeekBar seekbar_weight = customView.findViewById(id.seek_bar);

        TextView setWeight = customView.findViewById(id.txtvw_weight);
        TextView setWeightHint = customView.findViewById(id.txtvw_weight_hint);
        LinearLayout panel = customView.findViewById(id.panelcolor);

        seekbar_weight.setOnProgressChangeListener(new OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

                setWeight.setText(String.valueOf(value));


                if (value <= 53) {
                    setWeight.setTextColor(CaseNotes.this.getResources().getColor(color.blue_300));
                    setWeightHint.setText("Underweight");
                    setWeightHint.setTextColor(CaseNotes.this.getResources().getColor(color.blue_300));
                    seekbar_weight.setTrackColor(CaseNotes.this.getResources().getColor(color.blue_300));
                    panel.setBackgroundColor(CaseNotes.this.getResources().getColor(color.blue_300));
                } else if (value >= 53 && value <= 72) {
                    setWeight.setTextColor(CaseNotes.this.getResources().getColor(color.green_400));
                    setWeightHint.setText("Normal");
                    setWeightHint.setTextColor(CaseNotes.this.getResources().getColor(color.green_400));
                    seekbar_weight.setTrackColor(CaseNotes.this.getResources().getColor(color.green_400));
                    panel.setBackgroundColor(CaseNotes.this.getResources().getColor(color.green_400));
                } else if (value >= 72 && value <= 80) {
                    setWeight.setTextColor(CaseNotes.this.getResources().getColor(color.purple_600));
                    setWeightHint.setText("Overweight");
                    setWeightHint.setTextColor(CaseNotes.this.getResources().getColor(color.purple_600));
                    seekbar_weight.setTrackColor(CaseNotes.this.getResources().getColor(color.purple_600));
                    panel.setBackgroundColor(CaseNotes.this.getResources().getColor(color.purple_600));
                } else if (value >= 80 && value <= 92) {
                    setWeight.setTextColor(CaseNotes.this.getResources().getColor(color.orange_500));
                    setWeightHint.setText("Obese I");
                    setWeightHint.setTextColor(CaseNotes.this.getResources().getColor(color.orange_500));
                    seekbar_weight.setTrackColor(CaseNotes.this.getResources().getColor(color.orange_500));
                    panel.setBackgroundColor(CaseNotes.this.getResources().getColor(color.orange_500));
                } else if (value >= 92) {
                    setWeight.setTextColor(CaseNotes.this.getResources().getColor(color.red_500));
                    setWeightHint.setText("Obese II");
                    setWeightHint.setTextColor(CaseNotes.this.getResources().getColor(color.red_500));
                    seekbar_weight.setTrackColor(CaseNotes.this.getResources().getColor(color.red_500));
                    panel.setBackgroundColor(CaseNotes.this.getResources().getColor(color.red_500));
                }


            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });


        AppCompatButton button_ok = customView.findViewById(id.bt_close_yes);
        AppCompatButton button_cancel = customView.findViewById(id.bt_close_no);

        button_ok.setOnClickListener(v -> {

            int seekValue = seekbar_weight.getProgress();

            // Toast.makeText(v.getContext(), String.valueOf(seekValue), Toast.LENGTH_SHORT).show();

            this.autocompltWeight.setText(String.valueOf(seekValue));

            alertDialog.dismiss();


        });

        button_cancel.setOnClickListener(view -> alertDialog.dismiss());

    }

    private void showPopup_PatientHeight() {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View customView = layoutInflater.inflate(layout.kdmc_layout_height, null);

        Builder alertDialogBuilder = new Builder(this).setCancelable(false);
        alertDialogBuilder.setView(customView);


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

        kdmc_kumar.Utilities_Others.seek.DiscreteSeekBar seekbar_height = customView.findViewById(id.seek_bar);

        TextView setHeight = customView.findViewById(id.txtvw_height);

        AppCompatButton button_ok = customView.findViewById(id.bt_close_yes);
        AppCompatButton button_cancel = customView.findViewById(id.bt_close_no);

        seekbar_height.setOnProgressChangeListener(new OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

                setHeight.setText(String.valueOf(value));

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        button_ok.setOnClickListener(v -> {

            int seekValue = seekbar_height.getProgress();
            // Toast.makeText(v.getContext(), String.valueOf(seekValue), Toast.LENGTH_SHORT).show();
            this.autocompltHeight.setText(String.valueOf(seekValue));
            alertDialog.dismiss();


        });

        button_cancel.setOnClickListener(v -> {

            alertDialog.dismiss();


        });

    }

    private void LoadBack() {

        if ("MYPATIENT".equalsIgnoreCase(this.FLAG_MYPATIENT)) {

            BaseConfig.HideKeyboard(this);
            Bundle b = new Bundle();
            b.putString(BaseConfig.BUNDLE_PATIENT_ID, this.COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, MyPatientDrawer.class, b);


        } else if ("INPATIENT".equalsIgnoreCase(this.FLAG_MYPATIENT)) {

            BaseConfig.HideKeyboard(this);
            Bundle b = new Bundle();
            b.putString(BaseConfig.BUNDLE_PATIENT_ID, this.COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, Inpatient_Detailed_View.class, b);

        } else {

            BaseConfig.HideKeyboard(this);
            this.multiautoTreatmentfor.setText("");
            BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

        }


    }

    public void SelectedGetPatientDetails() {


        try {
            String[] pna = this.autocompletePatientname.getText().toString().split("-");

            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + pna[1] + "'");
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + pna[1] + "'");


            this.tvwAgegender.setText(Patient_AgeGender);

            BaseConfig.Glide_LoadImageView(this.imgvwPatientphoto, Str_Patient_Photo);


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    public void ClearAll() {
        this.autocompletePatientname.setText("");
        BaseConfig.Glide_LoadDefaultImageView(this.imgvwPatientphoto);
        this.tvwAgegender.setText("-");
        this.multiautoTreatmentfor.setText("");
        this.multiautoDiagnosis.setText("");
        this.multiautoSigns.setText("");
        this.autocompletePatientname.setEnabled(true);
    }

    private float calculateBMI(float weight, float height) {
        String h1 = String.valueOf(height);
        String w1 = String.valueOf(height);

        if (h1.equals("0.0") && w1.equals("0.0")) {
            return 0;
        } else {
            return (weight / (height * height)) * 10000;
        }

    }
    //All systems end

    private String interpretBMI(float bmiValue) {
        if (bmiValue < 16) {
            return "Abnormal";
        } else if (bmiValue < 18.5) {
            return "Underweight";
        } else if (bmiValue < 25) {
            return "Normal";
        } else if (bmiValue < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation1.hasText(this.multiautoDiagnosis))
            ret = false;

        if (!Validation1.hasText(this.multiautoTreatmentfor))
            ret = false;

        if (!Validation1.hasText(this.autocompletePatientname))
            ret = false;

        return ret;
    }
    //******************************************************************************************

    public void ShowSweetAlert(String message, String DIAGNOSIS_ID) {


        new CustomKDMCDialog(this)
                .setNegativeButtonVisible(View.GONE)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_success_done)
                .setTitle(getString(string.information))
                .setDescription(message)
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle(getString(string.ok))
                .setOnPossitiveListener(() -> {
                  /*  CaseNotes.this.finish();
                    Intent intent = new Intent(getApplicationContext(), Investigations.class);
                    intent.putExtra("CONTINUE_STATUS", "True");
                    intent.putExtra("PASSING_TREATMENTFOR", multiautoTreatmentfor.getText().toString());
                    intent.putExtra("PASSING_DIAGNOSIS", multiautoDiagnosis.getText().toString());
                    intent.putExtra("PASSING_PATIENT_ID", autocompletePatientname.getText().toString().split("-")[1]);
                    startActivity(intent);*/

                    finish();
                    Intent intent = new Intent(this.getApplicationContext(), ClinicalInformation.class);
                    intent.putExtra("CONTINUE_STATUS", "True");
                    intent.putExtra("PASSING_DIAGNOSISID", DIAGNOSIS_ID);
                    intent.putExtra("PASSING_PATIENT_ID", this.autocompletePatientname.getText().toString().split("-")[1]);
                    intent.putExtra("PASSING_TREATMENTFOR", this.multiautoTreatmentfor.getText().toString());
                    intent.putExtra("PASSING_DIAGNOSIS", this.multiautoDiagnosis.getText().toString());

                    this.startActivity(intent);

                    this.clear();
                });


    }

    //******************************************************************************************

    public void clear() {
        this.autocompletePatientname.setText(null);
        this.imgvwPatientphoto.setImageBitmap(null);
        this.tvwAgegender.setText(null);
        this.multiautoDiagnosis.setText(null);
        this.autocompltWeight.setText(null);
        this.autocompltTemperature.setText(null);
        this.autocompltBps.setText(null);
        this.autocompltBpd.setText(null);
        this.autocompltFbs.setText(null);
        this.autocompltPpbs.setText(null);
        this.autocompltRbs.setText(null);
    }

    public void Load_Patient_Details(String PatientId) {

        try {
            String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PatientId + "'");
            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PatientId + "'");
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + PatientId + "'");

            this.autocompletePatientname.setText(String.format("%s-%s", Patient_Name, PatientId));
            this.tvwAgegender.setText(Patient_AgeGender);
            BaseConfig.LoadPatientImage(Str_Patient_Photo, this.imgvwPatientphoto, 100);
            this.autocompletePatientname.setEnabled(false);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {


        try {
            this.LoadBack();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //******************************************************************************************

    public class LOAD_GENERAL_INFORMATION {
        ImageButton arrowGeneralExamination;
        LinearLayout primaryGeneralExaminationLayout;
        Button hideGeneralInfo;
        LinearLayout parentGeneralExaminationLayout;


        @BindView(id.textView_anaemic)
        TextView textViewAnaemic;
        @BindView(id.switch_anaemic)
        LabeledSwitch switchAnaemic;
        @BindView(id.TextView_icterus)
        TextView textViewIcterus;
        @BindView(id.switch_icterus)
        LabeledSwitch switchIcterus;
        @BindView(id.TextView_cyanosis)
        TextView textViewCyanosis;
        @BindView(id.switch_cyanosis)
        LabeledSwitch switchCyanosis;
        @BindView(id.textView_clubbing)
        TextView textViewClubbing;
        @BindView(id.switch_clubbing)
        LabeledSwitch switchClubbing;
        @BindView(id.textView_lymphadenopathy)
        TextView textViewLymphadenopathy;
        @BindView(id.switch_lymphadenopathy)
        LabeledSwitch switchLymphadenopathy;
        @BindView(id.textView_varicose_veins)
        TextView textViewVaricoseVeins;
        @BindView(id.switch_varicose_veins)
        LabeledSwitch switchVaricoseVeins;
        @BindView(id.textView_pedal_edema)
        TextView textViewPedalEdema;
        @BindView(id.switch_pedal_edema)
        LabeledSwitch switchPedalEdema;
        @BindView(id.textview_skininfection)
        TextView textviewSkininfection;
        @BindView(id.switch_skininfection)
        LabeledSwitch switchSkininfection;
        @BindView(id.textView_ShortStatured)
        TextView textViewShortStatured;
        @BindView(id.switch_ShortStatured)
        LabeledSwitch switchShortStatured;
        @BindView(id.textView_Built)
        TextView textViewBuilt;
        @BindView(id.radiogroup_built)
        RadioGroup radiogroupBuilt;
        @BindView(id.radioButton_lean)
        RadioButton radioButtonLean;
        @BindView(id.radioButton_normal)
        RadioButton radioButtonNormal;
        @BindView(id.radioButton_obese)
        RadioButton radioButtonObese;
        @BindView(id.checkBox_pallorness)
        CheckBox checkBoxPallorness;
        @BindView(id.checkBox_thyroid)
        CheckBox checkBoxThyroid;
        @BindView(id.checkBox_face_puffiness)
        CheckBox checkBoxFacePuffiness;
        @BindView(id.checkBox_jaundice)
        CheckBox checkBoxJaundice;
        @BindView(id.textView_pog)
        TextView textViewPog;
        @BindView(id.edt_pog)
        EditText edtPog;
        @BindView(id.textView_pallor)
        TextView textViewPallor;
        @BindView(id.edt_pallor)
        EditText edtPallor;
        @BindView(id.textView_oedema)
        TextView textViewOedema;
        @BindView(id.edt_oedema)
        EditText edtOedema;
        @BindView(id.textView_fundalHeight)
        TextView textViewFundalHeight;
        @BindView(id.edt_height)
        EditText edtHeight;
        @BindView(id.textView_LiePresentation)
        TextView textViewLiePresentation;
        @BindView(id.edt_LiePresentation)
        EditText edtLiePresentation;
        @BindView(id.textView_Fetalmovements)
        TextView textViewFetalmovements;
        @BindView(id.edt_Fetalmovements)
        EditText edtFetalmovements;
        @BindView(id.textView_Fetalheartrate)
        TextView textViewFetalheartrate;
        @BindView(id.edt_Fetalheartrate)
        EditText edtFetalheartrate;
        @BindView(id.textView_pv)
        TextView textViewPv;
        @BindView(id.edt_pv)
        EditText edtPv;
        @BindView(id.textView_anycomplaints)
        TextView textViewAnycomplaints;
        @BindView(id.edt_anycomplaints)
        EditText edtAnycomplaints;


        LOAD_GENERAL_INFORMATION() {
            View general_information = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_general_info, null);
            //1. GENERAL INFORMATION
            ButterKnife.bind(this, general_information);

            this.parentGeneralExaminationLayout = general_information.findViewById(id.sub_cn_generalinfo);
            this.arrowGeneralExamination = general_information.findViewById(id.arrow_general_examination);
            this.primaryGeneralExaminationLayout = general_information.findViewById(id.primary_general_examination_layout);
            this.hideGeneralInfo = general_information.findViewById(id.hide_generalinfo);


            this.arrowGeneralExamination.setOnClickListener(v -> {

                CaseNotes.this.toggleSectionInput(this.arrowGeneralExamination, this.primaryGeneralExaminationLayout);

            });

            this.hideGeneralInfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowGeneralExamination, this.primaryGeneralExaminationLayout));


            CaseNotes.this.allCasenotesLayouts.addView(general_information);



        }


    }

    public class LOAD_CARDIO_VASCULAR {


        @BindView(id.textView_beat)
        TextView textViewBeat;
        @BindView(id.checkBox_s1)
        CheckBox checkBoxS1;
        @BindView(id.checkBox_s2)
        CheckBox checkBoxS2;
        @BindView(id.checkBox_s3)
        CheckBox checkBoxS3;
        @BindView(id.checkBox_s4)
        CheckBox checkBoxS4;
        @BindView(id.textView_murmur)
        TextView textViewMurmur;
        @BindView(id.checkBox_systolic)
        CheckBox checkBoxSystolic;
        @BindView(id.checkBox_diastolic)
        CheckBox checkBoxDiastolic;
        @BindView(id.checkBox_others)
        CheckBox checkBoxOthers;
        @BindView(id.textView_pericardialrub)
        TextView textViewPericardialrub;
        @BindView(id.switch_pericardialrub)
        LabeledSwitch switchPericardialrub;
        @BindView(id.textView_pulserate)
        TextView textViewPulserate;
        @BindView(id.switch_pulserate)
        LabeledSwitch switchPulserate;
        @BindView(id.textView_heartrate)
        TextView textViewHeartrate;
        @BindView(id.editText_heartrate)
        EditText editTextHeartrate;

        ImageButton arrowCardioVascularSystem;
        LinearLayout primaryCardioVascularSystemLayout;
        Button hideCardioInfo;

        LOAD_CARDIO_VASCULAR() {
            View cardio_information = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_cardiovascular, null);
            //2.CARDIO VASCULAR


            this.arrowCardioVascularSystem = cardio_information.findViewById(id.arrow_cardio_vascular_system);
            this.primaryCardioVascularSystemLayout = cardio_information.findViewById(id.primary_cardio_vascular_system_layout);
            this.hideCardioInfo = cardio_information.findViewById(id.hide_cardioinfo);

            this.arrowCardioVascularSystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowCardioVascularSystem, this.primaryCardioVascularSystemLayout));

            this.hideCardioInfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowCardioVascularSystem, this.primaryCardioVascularSystemLayout));

            CaseNotes.this.allCasenotesLayouts.addView(cardio_information);


            ButterKnife.bind(this, cardio_information);

        }

    }

    public class LOAD_RESPIRATORY {


        @BindView(id.textView_breathsound)
        TextView textViewBreathsound;
        @BindView(id.radiogroup_breathsoundequal)
        RadioGroup radiogroupBreathsoundequal;
        @BindView(id.rbtn_yes)
        RadioButton rbtnYes;
        @BindView(id.rbtn_no)
        RadioButton rbtnNo;
        @BindView(id.radiogroup_breathsoundequal_optns)
        RadioGroup radiogroupBreathsoundequalOptns;
        @BindView(id.rrbtn_DecreaseonLeft)
        RadioButton rrbtnDecreaseonLeft;
        @BindView(id.rrbtn_Decreaseonright)
        RadioButton rrbtnDecreaseonright;
        @BindView(id.textView_breath_sound)
        TextView textViewBreathSound;
        @BindView(id.spinner_breath_sound)
        Spinner spinnerBreathSound;
        @BindView(id.textView_trachea)
        TextView textViewTrachea;
        @BindView(id.spinner2_trachea)
        Spinner spinner_Trachea;
        @BindView(id.checkBox_kyphosis)
        CheckBox checkBoxKyphosis;
        @BindView(id.checkBox_scoliosis)
        CheckBox checkBoxScoliosis;
        @BindView(id.textView_crepitation)
        TextView textViewCrepitation;
        @BindView(id.checkBox_left)
        CheckBox checkBoxLeft;
        @BindView(id.checkBox_right)
        CheckBox checkBoxRight;
        @BindView(id.textView_rhonchi)
        TextView textViewRhonchi;
        @BindView(id.checkBox_rhonchi_left)
        CheckBox checkBoxRhonchiLeft;
        @BindView(id.checkBox__rhonchi_right)
        CheckBox checkBoxRhonchiRight;
        @BindView(id.TextView_shape_of_the_chest)
        TextView textViewShapeOfTheChest;
        @BindView(id.spinner_shape_of_the_chest)
        Spinner spinnerShapeOfTheChest;
        @BindView(id.textView_respiratory_rate)
        TextView textViewRespiratoryRate;
        @BindView(id.editText_respiratory_rate)
        EditText editTextRespiratoryRate;
        @BindView(id.textView_spo2)
        TextView textViewSpo2;
        @BindView(id.autoCompleteTextView_spo2)
        AutoCompleteTextView autoCompleteTextViewSpo2;
        ImageButton arrowRespiratorySystem;
        LinearLayout primaryRespiratorySystemLayout;
        Button hideRespiratoryinfo;


        LOAD_RESPIRATORY() {
            //3.RESPIRATORY SYSTEM
            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_respiratory, null);


            this.arrowRespiratorySystem = current_view.findViewById(id.arrow_respiratory_system);
            this.primaryRespiratorySystemLayout = current_view.findViewById(id.primary_respiratory_system_layout);
            this.hideRespiratoryinfo = current_view.findViewById(id.hide_respiratoryinfo);

            this.arrowRespiratorySystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowRespiratorySystem, this.primaryRespiratorySystemLayout));

            this.hideRespiratoryinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowRespiratorySystem, this.primaryRespiratorySystemLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);


            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {
                CaseNotes.this.shapeofchest = new String[]{"निवडा", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            } else {

                CaseNotes.this.shapeofchest = new String[]{"Select", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            }


            //Load spinners
            BaseConfig.LoadValuesSpinner(this.spinnerBreathSound, CaseNotes.this, "select distinct Breath as dvalue from Mstr_Breath where IsActive=1 and Breath!='' order by Breath;", CaseNotes.this.getString(string.select));

            BaseConfig.LoadValuesSpinner(this.spinner_Trachea, CaseNotes.this, "select distinct Trachea as dvalue from Mstr_Trachea where IsActive=1 and Trachea!='' order by Trachea;", CaseNotes.this.getString(string.select));

            BaseConfig.loadSpinner(this.spinnerShapeOfTheChest, CaseNotes.this.shapeofchest);


            this.radiogroupBreathsoundequal.setOnCheckedChangeListener((radioGroup, i) -> {
                if (radioGroup.getCheckedRadioButtonId() == id.rbtn_yes) {
                    this.radiogroupBreathsoundequalOptns.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == id.rbtn_no) {
                    this.radiogroupBreathsoundequalOptns.setVisibility(View.VISIBLE);
                    Toast.makeText(CaseNotes.this, "Select below options if no breath sound found...", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    public class LOAD_GASTRO {

        @BindView(id.textView_shape_of_the_abdomen)
        TextView textViewShapeOfTheAbdomen;
        @BindView(id.spinner_shape_of_the_abdomen)
        Spinner spinnerShapeOfTheAbdomen;
        @BindView(id.textView_abdomen)
        TextView textViewAbdomen;
        @BindView(id.abdon_select)
        Button buttonAbdomen;
        @BindView(id.listView1)
        ListView listView1;
        @BindView(id.textView_visible_pulsation1)
        TextView textViewVisiblePulsation1;
        @BindView(id.switch_visible_pulsation1)
        LabeledSwitch switchVisiblePulsation1;
        @BindView(id.textView_switch_visible_perist1)
        TextView textViewSwitchVisiblePerist1;
        @BindView(id.switch_visible_perist1)
        LabeledSwitch switchVisiblePerist1;
        @BindView(id.textView_abdominal_palpation)
        TextView textViewAbdominalPalpation;
        @BindView(id.spinner_abdominal_palpation)
        Spinner spinnerAbdominalPalpation;
        @BindView(id.textView_splenomegaly)
        TextView textViewSplenomegaly;
        @BindView(id.autoCompleteTextView_splenomegaly)
        AutoCompleteTextView autoCompleteTextViewSplenomegaly;
        @BindView(id.textView_hepatomegaly)
        TextView textViewHepatomegaly;
        @BindView(id.autoCompleteTextView_hepatomegaly)
        AutoCompleteTextView autoCompleteTextViewHepatomegaly;
        @BindView(id.textView_palpable_masses)
        TextView textViewPalpableMasses;
        @BindView(id.autoCompleteTextView_palpable_masses)
        AutoCompleteTextView autoCompleteTextViewPalpableMasses;
        @BindView(id.textView_bowel_sounds)
        TextView textViewBowelSounds;
        @BindView(id.autoCompleteTextView_bowel_sounds)
        AutoCompleteTextView autoCompleteTextViewBowelSounds;
        @BindView(id.textView_hernial)
        TextView textViewHernial;
        @BindView(id.checkBox_orifices)
        CheckBox checkBoxOrifices;
        @BindView(id.checkBox_free)
        CheckBox checkBoxFree;
        @BindView(id.textView)
        TextView textView;
        @BindView(id.spinner3)
        Spinner spinner3;
        @BindView(id.textView_spleen)
        TextView textViewSpleen;
        @BindView(id.switch_spleen)
        LabeledSwitch switchSpleen;
        @BindView(id.textView16)
        TextView textView16;
        @BindView(id.switch_liver_normal)
        LabeledSwitch switchLiverNormal;
        @BindView(id.textView_organomegely)
        TextView textViewOrganomegely;
        @BindView(id.AutoCompleteTextView_organomegely)
        AutoCompleteTextView autoCompleteTextViewOrganomegely;
        @BindView(id.textView_free_fluid_in_the_abdomen)
        TextView textViewFreeFluidInTheAbdomen;
        @BindView(id.AutoCompleteTextView_free_fluid_in_the_abdomen)
        AutoCompleteTextView autoCompleteTextViewFreeFluidInTheAbdomen;
        @BindView(id.textView_distension)
        TextView textViewDistension;
        @BindView(id.autoCompleteTextView_distension)
        AutoCompleteTextView autoCompleteTextViewDistension;
        @BindView(id.textView_tenderness)
        TextView textViewTenderness;
        @BindView(id.autoCompleteTextView_tenderness)
        AutoCompleteTextView autoCompleteTextViewTenderness;
        @BindView(id.textView_scrotum)
        TextView textViewScrotum;
        @BindView(id.AutoCompleteTextView_scrotum)
        AutoCompleteTextView autoCompleteTextViewScrotum;

        ImageButton arrowGastrointestinalSystem;
        LinearLayout primaryGastrointestinalSystemLayout;
        Button hideGastroinfo;

        LOAD_GASTRO() {
            //4.GASTRO SYSTEM
            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_gastro, null);

            this.arrowGastrointestinalSystem = current_view.findViewById(id.arrow_gastrointestinal_system);
            this.primaryGastrointestinalSystemLayout = current_view.findViewById(id.primary_gastrointestinal_system_layout);
            this.hideGastroinfo = current_view.findViewById(id.hide_gastroinfo);

            this.arrowGastrointestinalSystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowGastrointestinalSystem, this.primaryGastrointestinalSystemLayout));

            this.hideGastroinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowGastrointestinalSystem, this.primaryGastrointestinalSystemLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);

            //load abdomen

            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {
                CaseNotes.this.abdomenshape = new String[]{"निवडा", "Normal", "Scaphoid", "Sunken", "Distended"};
                CaseNotes.this.abdomenpalpation = new String[]{"निवडा", "Soft", "Guarding", "Rigidity"};
            } else {
                CaseNotes.this.abdomenshape = new String[]{"Select", "Normal", "Scaphoid", "Sunken", "Distended"};
                CaseNotes.this.abdomenpalpation = new String[]{"Select", "Soft", "Guarding", "Rigidity"};
            }

            BaseConfig.loadSpinner(this.spinnerShapeOfTheAbdomen, CaseNotes.this.abdomenshape);

            BaseConfig.loadSpinner(this.spinnerAbdominalPalpation, CaseNotes.this.abdomenpalpation);

            this.buttonAbdomen.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    try {
                        this.LoadAbdomen();
                        this.showSMSalertDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                private void LoadAbdomen() {

                    SQLiteDatabase db = BaseConfig.GetDb();//);
                    Cursor c = db.rawQuery("select distinct Abdomen from mstr_Abdomen order by Abdomen;", null);

                    List<String> list1 = new ArrayList<String>();

                    if (c != null) {
                        if (c.moveToFirst()) {
                            do {

                                String pname = c.getString(c.getColumnIndex("Abdomen"));

                                list1.add(pname);

                            } while (c.moveToNext());
                        }
                    }

                    new ArrayAdapter<String>(CaseNotes.this, android.R.layout.simple_list_item_1, list1);

                    CaseNotes.this.items_abdomen = list1.toArray(new String[list1.size()]);

                    db.close();
                    c.close();
                }

                private void showSMSalertDialog() {

                    boolean[] checkedColours = new boolean[CaseNotes.this.items_abdomen.length];
                    int count = CaseNotes.this.items_abdomen.length;

                    for (int i = 0; i < count; i++)
                        checkedColours[i] = CaseNotes.this.selectedAbdomen.contains(CaseNotes.this.items_abdomen[i]);

                    OnMultiChoiceClickListener coloursDialogListener = (dialog, which, isChecked) -> {

                        if (isChecked) {
                            CaseNotes.this.selectedAbdomen.add(CaseNotes.this.items_abdomen[which]);

                        } else {
                            CaseNotes.this.selectedAbdomen.remove(CaseNotes.this.items_abdomen[which]);
                        }

                    };
                    Builder builder = new Builder(CaseNotes.this);
                    builder.setTitle("Select Abdomen");
                    builder.setMultiChoiceItems(CaseNotes.this.items_abdomen, checkedColours, coloursDialogListener);
                    builder.setPositiveButton(CaseNotes.this.getResources().getString(string.ok), (dialog, which) -> {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (CharSequence colour : CaseNotes.this.selectedAbdomen) {
                            stringBuilder.append(colour + "\n");
                        }
                        String[] splt = stringBuilder.toString().split("\n");
                        CaseNotes.this.abdomen_list = new ArrayList<String>();
                        CaseNotes.this.abdomen_list.clear();

                        if (splt.length > 0) {

                            CaseNotes.this.abdomen_list.addAll(Arrays.asList(splt));

                            if (CaseNotes.this.abdomen_list.size() > 0) {
                                ArrayAdapter<String> listadapter = new ArrayAdapter<String>(CaseNotes.this, android.R.layout.simple_list_item_1, CaseNotes.this.abdomen_list);
                                CaseNotes.LOAD_GASTRO.this.listView1.setAdapter(listadapter);
                                Helper.getListViewSize(CaseNotes.LOAD_GASTRO.this.listView1);
                            }

                        }

                    });
                    builder.setNegativeButton(CaseNotes.this.getResources().getString(string.cancel),
                            (dialog, which) -> new StringBuilder());

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


            });


        }

    }

    public class LOAD_NEUROLOGY {

        @BindView(id.textView_pupil_size)
        TextView textViewPupilSize;
        @BindView(id.radiogroup_pupilsize)
        RadioGroup radiogroupPupilsize;
        @BindView(id.radioButton_normal)
        RadioButton radioButtonNormal;
        @BindView(id.radioButton_decrease)
        RadioButton radioButtonDecrease;
        @BindView(id.radioButton_increase)
        RadioButton radioButtonIncrease;
        @BindView(id.textView20)
        TextView textView20;
        @BindView(id.radiogroup_speech)
        RadioGroup radiogroupSpeech;
        @BindView(id.radioButton_speech_normal)
        RadioButton radioButtonSpeechNormal;
        @BindView(id.radioButton_dysphasia)
        RadioButton radioButtonDysphasia;
        @BindView(id.radioButton_aphasia)
        RadioButton radioButtonAphasia;
        @BindView(id.textView_carotid)
        TextView textViewCarotid;
        @BindView(id.radiogroup_carotid)
        RadioGroup radiogroupCarotid;
        @BindView(id.radioButton_bruit_present)
        RadioButton radioButtonBruitPresent;
        @BindView(id.radioButton_bruit_absent)
        RadioButton radioButtonBruitAbsent;
        @BindView(id.textView23)
        TextView textView23;
        @BindView(id.switch_amnesiay)
        LabeledSwitch switchAmnesiay;
        @BindView(id.textView24)
        TextView textView24;
        @BindView(id.switch_apraxiay)
        LabeledSwitch switchApraxiay;
        @BindView(id.textView_cognitive_function)
        TextView textViewCognitiveFunction;
        @BindView(id.radiogroup_cognitive_function)
        RadioGroup radiogroupCognitiveFunction;
        @BindView(id.radioButton_cognitive_function_normal)
        RadioButton radioButtonCognitiveFunctionNormal;
        @BindView(id.radioButton_impaired)
        RadioButton radioButtonImpaired;
        @BindView(id.textView_bulk)
        TextView textViewBulk;
        @BindView(id.radiogroup_bulk)
        RadioGroup radiogroupBulk;
        @BindView(id.radioButton_bulk_normal)
        RadioButton radioButtonBulkNormal;
        @BindView(id.radioButton_bulk_reduced)
        RadioButton radioButtonBulkReduced;
        @BindView(id.radioButton_bulk_increase)
        RadioButton radioButtonBulkIncrease;
        @BindView(id.textView_tone)
        TextView textViewTone;
        @BindView(id.rdgrp_tones)
        RadioGroup radiogroupTones;
        @BindView(id.radioButton_tone_normal)
        RadioButton radioButtonToneNormal;
        @BindView(id.radioButton_tone_reduced)
        RadioButton radioButtonToneReduced;
        @BindView(id.radioButton_tone_increased)
        RadioButton radioButtonToneIncreased;
        @BindView(id.textView_power)
        TextView textViewPower;
        @BindView(id.textView_left_upper_limb)
        TextView textViewLeftUpperLimb;
        @BindView(id.seekBar_left_upper_limb)
        SeekBar seekBarLeftUpperLimb;
        @BindView(id.textView_left_upper_limb_5_5)
        TextView textViewLeftUpperLimb55;
        @BindView(id.textView_right_upper_limb)
        TextView textViewRightUpperLimb;
        @BindView(id.seekBar_right_upper_limb)
        SeekBar seekBarRightUpperLimb;
        @BindView(id.textView_right_upper_limb_5_5)
        TextView textViewRightUpperLimb55;
        @BindView(id.textView_left_lower_limb)
        TextView textViewLeftLowerLimb;
        @BindView(id.seekBar__left_lower_limb)
        SeekBar seekBarLeftLowerLimb;
        @BindView(id.textView__left_lower_limb_5_5)
        TextView textViewLeftLowerLimb55;
        @BindView(id.textView_right_lower_limb)
        TextView textViewRightLowerLimb;
        @BindView(id.seekBar_right_lower_limb)
        SeekBar seekBarRightLowerLimb;
        @BindView(id.textView_right_lower_limb_5_5)
        TextView textViewRightLowerLimb55;
        @BindView(id.textView_sensory)
        TextView textViewSensory;
        @BindView(id.spinner_sensory)
        Spinner spinnerSensory;
        @BindView(id.textView_corneal)
        TextView textViewCorneal;
        @BindView(id.spinner_corneal)
        Spinner spinnerCorneal;
        @BindView(id.textView_biceps)
        TextView textViewBiceps;
        @BindView(id.spinner_biceps)
        Spinner spinnerBiceps;
        @BindView(id.textView_triceps)
        TextView textViewTriceps;
        @BindView(id.spinner_triceps)
        Spinner spinnerTriceps;
        @BindView(id.textView_supinator)
        TextView textViewSupinator;
        @BindView(id.spinner_supinator)
        Spinner spinnerSupinator;
        @BindView(id.textView_knee)
        TextView textViewKnee;
        @BindView(id.spinner_knee)
        Spinner spinnerKnee;
        @BindView(id.textView_ankle)
        TextView textViewAnkle;
        @BindView(id.spinner_ankle)
        Spinner spinnerAnkle;
        @BindView(id.textView_plantar)
        TextView textViewPlantar;
        @BindView(id.spinner_plantar)
        Spinner spinnerPlantar;
        @BindView(id.textView_pcentral_nervous_system)
        TextView textViewPcentralNervousSystem;
        @BindView(id.textView_oriented)
        TextView textViewOriented;
        @BindView(id.AutoCompleteTextView_oriented)
        AutoCompleteTextView autoCompleteTextViewOriented;
        @BindView(id.textView_neck_rigidity)
        TextView textViewNeckRigidity;
        @BindView(id.AutoCompleteTextView_neck_rigidity)
        AutoCompleteTextView autoCompleteTextViewNeckRigidity;
        @BindView(id.textView_confused)
        TextView textViewConfused;
        @BindView(id.AutoCompleteTextView_confused)
        AutoCompleteTextView autoCompleteTextViewConfused;
        @BindView(id.textView_exaggerated)
        TextView textViewExaggerated;
        @BindView(id.AutoCompleteTextView_exaggerated)
        AutoCompleteTextView autoCompleteTextViewExaggerated;
        @BindView(id.textView_extenstor)
        TextView textViewExtenstor;
        @BindView(id.AutoCompleteTextView_extenstor)
        AutoCompleteTextView autoCompleteTextViewExtenstor;
        @BindView(id.textView_gs_score)
        TextView textViewGsScore;
        @BindView(id.AutoCompleteTextView_gs_score)
        AutoCompleteTextView autoCompleteTextViewGsScore;
        @BindView(id.textView_incomprehensible)
        TextView textViewIncomprehensible;
        @BindView(id.AutoCompleteTextView_incomprehensible)
        AutoCompleteTextView autoCompleteTextViewIncomprehensible;
        @BindView(id.textView_depressed)
        TextView textViewDepressed;
        @BindView(id.AutoCompleteTextView_depressed)
        AutoCompleteTextView autoCompleteTextViewDepressed;
        @BindView(id.textView_flexor)
        TextView textViewFlexor;
        @BindView(id.AutoCompleteTextView_flexor)
        AutoCompleteTextView autoCompleteTextViewFlexor;
        @BindView(id.TextView_coma)
        TextView textViewComa;
        @BindView(id.AutoCompleteTextView_coma)
        AutoCompleteTextView autoCompleteTextViewComa;
        @BindView(id.textView_musclo_skeletal_systems)
        TextView textViewMuscloSkeletalSystems;
        @BindView(id.textView_congential_abnormality)
        TextView textViewCongentialAbnormality;
        @BindView(id.AutoCompleteTextView_congential_abnormality)
        AutoCompleteTextView autoCompleteTextViewCongentialAbnormality;
        @BindView(id.textView_mental_status)
        TextView textViewMentalStatus;
        @BindView(id.autoCompleteTextView_mental_status)
        AutoCompleteTextView autoCompleteTextViewMentalStatus;

        ImageButton arrowNeurologySystem;
        LinearLayout primaryNeurologySystemLayout;
        Button hideNeurologyinfo;

        LOAD_NEUROLOGY() {
            //5.NEUROLOGY
            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_neurology, null);


            this.arrowNeurologySystem = current_view.findViewById(id.arrow_neurology_system);
            this.primaryNeurologySystemLayout = current_view.findViewById(id.primary_neurology_system_layout);
            this.hideNeurologyinfo = current_view.findViewById(id.hide_neurologyinfo);

            this.arrowNeurologySystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowNeurologySystem, this.primaryNeurologySystemLayout));

            this.hideNeurologyinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowNeurologySystem, this.primaryNeurologySystemLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);

            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {
                CaseNotes.this.reflexes = new String[]{"निवडा", "Normal", "Reduced", "Increased", "Absent"};
            } else {
                CaseNotes.this.reflexes = new String[]{"Select", "Normal", "Reduced", "Increased", "Absent"};
            }

            BaseConfig.loadSpinner(this.spinnerSensory, CaseNotes.this.reflexes);
            BaseConfig.loadSpinner(this.spinnerCorneal, CaseNotes.this.reflexes);
            BaseConfig.loadSpinner(this.spinnerBiceps, CaseNotes.this.reflexes);
            BaseConfig.loadSpinner(this.spinnerTriceps, CaseNotes.this.reflexes);
            BaseConfig.loadSpinner(this.spinnerSupinator, CaseNotes.this.reflexes);
            BaseConfig.loadSpinner(this.spinnerKnee, CaseNotes.this.reflexes);
            BaseConfig.loadSpinner(this.spinnerAnkle, CaseNotes.this.reflexes);
            BaseConfig.loadSpinner(this.spinnerPlantar, CaseNotes.this.reflexes);

            this.seekBarLeftUpperLimb.setMax(5);
            this.seekBarRightUpperLimb.setMax(5);
            this.seekBarLeftLowerLimb.setMax(5);
            this.seekBarRightLowerLimb.setMax(5);

            this.seekBarLeftUpperLimb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   CaseNotes.LOAD_NEUROLOGY.this.textViewLeftUpperLimb55.setText(progress + "/5");

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });

            this.seekBarRightUpperLimb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   CaseNotes.LOAD_NEUROLOGY.this.textViewRightUpperLimb55.setText(progress + "/5");

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });
            this.seekBarLeftLowerLimb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   CaseNotes.LOAD_NEUROLOGY.this.textViewLeftLowerLimb55.setText(progress + "/5");

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });
            this.seekBarRightLowerLimb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   CaseNotes.LOAD_NEUROLOGY.this.textViewRightLowerLimb55.setText(progress + "/5");

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });


        }

    }

    public class LOAD_MOTOR {

        @BindView(id.Motor)
        LinearLayout motor;
        @BindView(id.TextView36)
        TextView textView36;
        @BindView(id.textView_symmetry)
        TextView textViewSymmetry;
        @BindView(id.switch_Symmetry1)
        LabeledSwitch switchSymmetry1;
        @BindView(id.textView_Smooth_movement1)
        TextView textViewSmoothMovement1;
        @BindView(id.switch_Smooth_movement1)
        LabeledSwitch switchSmoothMovement1;
        @BindView(id.textView_Arms_swing1)
        TextView textViewArmsSwing1;
        @BindView(id.switch_Arms_swing1)
        LabeledSwitch switchArmsSwing1;
        @BindView(id.textView_Pelvic_tilt1)
        TextView textViewPelvicTilt1;
        @BindView(id.switch_Pelvic_tilt1)
        LabeledSwitch switchPelvicTilt1;
        @BindView(id.textView__length1)
        TextView textViewLength1;
        @BindView(id.Stride_length1)
        LabeledSwitch strideLength1;
        @BindView(id.textView_spine)
        TextView textViewSpine;
        @BindView(id.textView_cervical_lordosis)
        TextView textViewCervicalLordosis;
        @BindView(id.switch_Cervical_Lordosis1)
        LabeledSwitch switchCervicalLordosis1;
        @BindView(id.textView_lumbar_lordosis)
        TextView textViewLumbarLordosis;
        @BindView(id.switch_Lumbar_Lordosis1)
        LabeledSwitch switchLumbarLordosis1;
        @BindView(id.textView_kyphosis)
        TextView textViewKyphosis;
        @BindView(id.switch_Kyphosis1)
        LabeledSwitch switchKyphosis1;
        @BindView(id.textView_Scoliosis1)
        TextView textViewScoliosis1;
        @BindView(id.switch_Scoliosis1)
        LabeledSwitch switchScoliosis1;
        @BindView(id.textView_lower_limb)
        TextView textViewLowerLimb;
        @BindView(id.textView_swelling)
        TextView textViewSwelling;
        @BindView(id.switch_llSwelling1)
        LabeledSwitch switchLlSwelling1;
        @BindView(id.textView_deformity)
        TextView textViewDeformity;
        @BindView(id.switch_llDeformity1)
        LabeledSwitch switchLlDeformity1;
        @BindView(id.textView_limb_shortening)
        TextView textViewLimbShortening;
        @BindView(id.switch_llLimb_shortening1)
        LabeledSwitch switchLlLimbShortening1;
        @BindView(id.textView_muscle_wasting)
        TextView textViewMuscleWasting;
        @BindView(id.switch_llMuscle_wasting1)
        LabeledSwitch switchLlMuscleWasting1;
        @BindView(id.textView_remarks)
        TextView textViewRemarks;
        @BindView(id.editText_remarks)
        EditText editTextRemarks;
        @BindView(id.textView_upper_limb)
        TextView textViewUpperLimb;
        @BindView(id.textView_upper_limb_swelling)
        TextView textViewUpperLimbSwelling;
        @BindView(id.switch_upper_limb_swelling)
        LabeledSwitch switchUpperLimbSwelling;
        @BindView(id.textView_upper_limb_deformity)
        TextView textViewUpperLimbDeformity;
        @BindView(id.switch__upper_limb_deformity)
        LabeledSwitch switchUpperLimbDeformity;
        @BindView(id.textView_upper_limb_shortening)
        TextView textViewUpperLimbShortening;
        @BindView(id.switch_upper_limb_shortening)
        LabeledSwitch switchUpperLimbShortening;
        @BindView(id.textView_upper_limb_muscle_wasting)
        TextView textViewUpperLimbMuscleWasting;
        @BindView(id.switch_upper_limb_wasting1)
        LabeledSwitch switchUpperLimbWasting1;
        @BindView(id.textView_remarks1)
        TextView textViewRemarks1;
        @BindView(id.editText_remarks1)
        EditText editTextRemarks1;

        ImageButton arrowLocomotorSystem;
        LinearLayout primaryLocomotorSystemLayout;
        Button hideLocomotorinfo;

        LOAD_MOTOR() {
            //6.LOCOMOTOR

            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_locomotor, null);

            this.arrowLocomotorSystem = current_view.findViewById(id.arrow_locomotor_system);
            this.primaryLocomotorSystemLayout = current_view.findViewById(id.primary_locomotor_system_layout);
            this.hideLocomotorinfo = current_view.findViewById(id.hide_locomotorinfo);

            this.arrowLocomotorSystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowLocomotorSystem, this.primaryLocomotorSystemLayout));

            this.hideLocomotorinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowLocomotorSystem, this.primaryLocomotorSystemLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);

        }

    }

    public class LOAD_RENAL {

        @BindView(id.textView_dysuria)
        TextView textViewDysuria;
        @BindView(id.switch_dysuria1)
        LabeledSwitch switchDysuria1;
        @BindView(id.textView_pyuria)
        TextView textViewPyuria;
        @BindView(id.switch_pyuria1)
        LabeledSwitch switchPyuria1;
        @BindView(id.textView_oliguria)
        TextView textViewOliguria;
        @BindView(id.switch_oliguria1)
        LabeledSwitch switchOliguria1;
        @BindView(id.textView_polyuria)
        TextView textViewPolyuria;
        @BindView(id.switch_polyuria1)
        LabeledSwitch switchPolyuria1;
        @BindView(id.textView_anuria)
        TextView textViewAnuria;
        @BindView(id.switch_anuria1)
        LabeledSwitch switchAnuria1;
        @BindView(id.textView_nocturia)
        TextView textViewNocturia;
        @BindView(id.switch_nocturia1)
        LabeledSwitch switchNocturia1;
        @BindView(id.textView_urethral_discharge)
        TextView textViewUrethralDischarge;
        @BindView(id.switch_urethraldischarge1)
        LabeledSwitch switchUrethraldischarge1;
        @BindView(id.textView_prostate)
        TextView textViewProstate;
        @BindView(id.switch_prostate)
        LabeledSwitch switchProstate;
        @BindView(id.textView_haematuria)
        TextView textViewHaematuria;
        @BindView(id.spinner_haematuria)
        Spinner spinnerHaematuria;


        ImageButton arrowRenalSystem;
        LinearLayout primaryRenalSystemLayout;
        Button hideRenalinfo;

        LOAD_RENAL() {
            //7.RENAL SYSTEM
            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_renal_system, null);

            this.arrowRenalSystem = current_view.findViewById(id.arrow_renal_system);
            this.primaryRenalSystemLayout = current_view.findViewById(id.primary_renal_system_layout);
            this.hideRenalinfo = current_view.findViewById(id.hide_renalinfo);

            this.arrowRenalSystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowRenalSystem, this.primaryRenalSystemLayout));

            this.hideRenalinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowRenalSystem, this.primaryRenalSystemLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {

                CaseNotes.this.haematuriaarry = new String[]{"निवडा", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

            } else {

                CaseNotes.this.haematuriaarry = new String[]{"Select", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

            }

            BaseConfig.loadSpinner(this.spinnerHaematuria, CaseNotes.this.haematuriaarry);



        }

    }

    public class LOAD_ENDOSYS {
        ImageButton arrowEndocrineSystem;
        LinearLayout primaryEndocrineSystemLayout;
        Button buttonEndocrineAdd;
        EditText edtEndocrine;
        Button hideEndocrineinfo;

        LOAD_ENDOSYS() {
            //8.ENDOCRINE
            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_endrocine, null);


            this.arrowEndocrineSystem = current_view.findViewById(id.arrow_endocrine_system);
            this.primaryEndocrineSystemLayout = current_view.findViewById(id.primary_endocrine_system_layout);
            this.buttonEndocrineAdd = current_view.findViewById(id.button_endocrine_add);
            this.edtEndocrine = current_view.findViewById(id.edt_endocrine);
            this.hideEndocrineinfo = current_view.findViewById(id.hide_endocrineinfo);


            this.arrowEndocrineSystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowEndocrineSystem, this.primaryEndocrineSystemLayout));

            this.hideEndocrineinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowEndocrineSystem, this.primaryEndocrineSystemLayout));


            CaseNotes.this.allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);


            this.buttonEndocrineAdd.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    try {
                        this.LoadAbdomen();
                        this.showSMSalertDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                CharSequence[]   items_endocrine;
                private void LoadAbdomen() {

                    SQLiteDatabase db = BaseConfig.GetDb();//);
                    Cursor c = db.rawQuery("select distinct Endocrine from Mstr_Endocrine where IsActive=1 order by Endocrine;", null);

                    List<String> list1 = new ArrayList<String>();

                    if (c != null) {
                        if (c.moveToFirst()) {
                            do {

                                String pname = c.getString(c.getColumnIndex("Endocrine"));

                                list1.add(pname);

                            } while (c.moveToNext());
                        }
                    }

                    new ArrayAdapter<String>(CaseNotes.this, android.R.layout.simple_list_item_1, list1);

                    this.items_endocrine = list1.toArray(new String[list1.size()]);

                    db.close();
                    c.close();
                }

                private void showSMSalertDialog() {

                    boolean[] checkedColours = new boolean[this.items_endocrine.length];
                    int count = this.items_endocrine.length;

                    for (int i = 0; i < count; i++)
                        checkedColours[i] = CaseNotes.this.selectedEndocrine.contains(this.items_endocrine[i]);

                    OnMultiChoiceClickListener coloursDialogListener = (dialog, which, isChecked) -> {

                        if (isChecked) {
                            CaseNotes.this.selectedEndocrine.add(this.items_endocrine[which]);

                        } else {
                            CaseNotes.this.selectedEndocrine.remove(this.items_endocrine[which]);
                        }

                    };
                    Builder builder = new Builder(CaseNotes.this);
                    builder.setTitle("Select Endocrine");
                    builder.setMultiChoiceItems(this.items_endocrine, checkedColours, coloursDialogListener);
                    builder.setPositiveButton(CaseNotes.this.getResources().getString(string.ok), (dialog, which) -> {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (CharSequence colour : CaseNotes.this.selectedEndocrine) {
                            stringBuilder.append(colour + "\n");
                        }
                        String[] splt = stringBuilder.toString().split("\n");

                        CaseNotes.LOAD_ENDOSYS.this.edtEndocrine.setText(stringBuilder.toString());

                    });
                    builder.setNegativeButton(CaseNotes.this.getResources().getString(string.cancel),
                            (dialog, which) -> new StringBuilder());

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


            });


        }

    }

    public class LOAD_CLINICAL {

        @BindView(id.Clinical)
        LinearLayout clinical;
        @BindView(id.textView_haemogram)
        TextView textViewHaemogram;
        @BindView(id.textView_haemoglobin_g_dl)
        TextView textViewHaemoglobinGDl;
        @BindView(id.editText_haemoglobin_g_dl)
        EditText editTextHaemoglobinGDl;
        @BindView(id.editText_wbc)
        EditText editTextWBC;
        @BindView(id.textView_monocytes)
        TextView textViewMonocytes;
        @BindView(id.editText_monocytes)
        EditText editTextMonocytes;
        @BindView(id.textView_basophils)
        TextView textViewBasophils;
        @BindView(id.editText_basophils)
        EditText editTextBasophils;
        @BindView(id.textView_rbc)
        TextView textViewRbc;
        @BindView(id.editText_rbc)
        EditText editTextRbc;
        @BindView(id.textView_esr)
        TextView textViewEsr;
        @BindView(id.editText_esr)
        EditText editTextEsr;
        @BindView(id.textView_polymorphs)
        TextView textViewPolymorphs;
        @BindView(id.editText_polymorphs)
        EditText editTextPolymorphs;
        @BindView(id.textView_lymphocytes)
        TextView textViewLymphocytes;
        @BindView(id.editText_lymphocytes)
        EditText editTextLymphocytes;
        @BindView(id.TextView_renal_markers)
        TextView textViewRenalMarkers;
        @BindView(id.textView_eosinophils)
        TextView textViewEosinophils;
        @BindView(id.editText_eosinophils)
        EditText editTextEosinophils;
        @BindView(id.textView_urea)
        TextView textViewUrea;
        @BindView(id.editText_urea)
        EditText editTextUrea;
        @BindView(id.textView_creatinine)
        TextView textViewCreatinine;
        @BindView(id.editText_creatinine)
        EditText editTextCreatinine;
        @BindView(id.textView_uric_acid)
        TextView textViewUricAcid;
        @BindView(id.editText_uric_acid)
        EditText editTextUricAcid;
        @BindView(id.TextView_electrolytes)
        TextView textViewElectrolytes;
        @BindView(id.TextView_sodium)
        TextView textViewSodium;
        @BindView(id.EditText_sodium)
        EditText editTextSodium;
        @BindView(id.TextView_potassium)
        TextView textViewPotassium;
        @BindView(id.EditText_potassium)
        EditText editTextPotassium;
        @BindView(id.TextView_chloride)
        TextView textViewChloride;
        @BindView(id.EditText_chloride)
        EditText editTextChloride;
        @BindView(id.TextView_bicarbonate)
        TextView textViewBicarbonate;
        @BindView(id.EditText_bicarbonate)
        EditText editTextBicarbonate;
        @BindView(id.TextView_lipo_profile)
        TextView textViewLipoProfile;
        @BindView(id.TextView_total_cholesterol)
        TextView textViewTotalCholesterol;
        @BindView(id.EditText_total_cholesterol)
        EditText editTextTotalCholesterol;
        @BindView(id.TextView_ldl)
        TextView textViewLdl;
        @BindView(id.EditText_ldl)
        EditText editTextLdl;
        @BindView(id.TextView_hdl)
        TextView textViewHdl;
        @BindView(id.EditText_hdl)
        EditText editTextHdl;
        @BindView(id.TextView_vldl)
        TextView textViewVldl;
        @BindView(id.EditText_vldl)
        EditText editTextVldl;
        @BindView(id.TextView_triglycerides)
        TextView textViewTriglycerides;
        @BindView(id.EditText_triglycerides)
        EditText editTextTriglycerides;
        @BindView(id.TextView_liver_function_test)
        TextView textViewLiverFunctionTest;
        @BindView(id.TextView_serum_bilirubin_total)
        TextView textViewSerumBilirubinTotal;
        @BindView(id.EditText_serum_bilirubin_total)
        EditText editTextSerumBilirubinTotal;
        @BindView(id.TextView_direct)
        TextView textViewDirect;
        @BindView(id.EditText_direct)
        EditText editTextDirect;
        @BindView(id.TextView_indirect)
        TextView textViewIndirect;
        @BindView(id.EditText_indirect)
        EditText editTextIndirect;
        @BindView(id.TextView_total_protein)
        TextView textViewTotalProtein;
        @BindView(id.EditText_total_protein)
        EditText editTextTotalProtein;
        @BindView(id.TextView_albumin)
        TextView textViewAlbumin;
        @BindView(id.EditText_albumin)
        EditText editTextAlbumin;
        @BindView(id.textView_globulin)
        TextView textViewGlobulin;
        @BindView(id.editText_globulin)
        EditText editTextGlobulin;
        @BindView(id.textView_sgot)
        TextView textViewSgot;
        @BindView(id.editText_sgot)
        EditText editTextSgot;
        @BindView(id.textView_sgpt)
        TextView textViewSgpt;
        @BindView(id.editText_sgpt)
        EditText editTextSgpt;
        @BindView(id.textView_alkaline_phosphatase)
        TextView textViewAlkalinePhosphatase;
        @BindView(id.editText_alkaline_phosphatase)
        EditText editTextAlkalinePhosphatase;
        @BindView(id.TextView_thyroid_profile)
        TextView textViewThyroidProfile;
        @BindView(id.TextView_free_t3)
        TextView textViewFreeT3;
        @BindView(id.EditText_free_t3)
        EditText editTextFreeT3;
        @BindView(id.TextView_free_t4)
        TextView textViewFreeT4;
        @BindView(id.EditText_free_t4)
        EditText editTextFreeT4;
        @BindView(id.TextView_tsh)
        TextView textViewTsh;
        @BindView(id.EditText_tsh)
        EditText editTextTsh;
        ImageButton arrowClinicalData;
        LinearLayout primaryClinicalDataLayout;
        Button hideClinicalinfo;

        LOAD_CLINICAL() {
            //10.CLINICAL DATA
            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_clinicaldata, null);


            this.arrowClinicalData = current_view.findViewById(id.arrow_clinical_data);
            this.primaryClinicalDataLayout = current_view.findViewById(id.primary_clinical_data_layout);
            this.hideClinicalinfo = current_view.findViewById(id.hide_clinicalinfo);

            this.arrowClinicalData.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowClinicalData, this.primaryClinicalDataLayout));

            this.hideClinicalinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowClinicalData, this.primaryClinicalDataLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


        }

    }

    public class LOAD_OTHERSYS {
        ImageButton arrowOtherSystems;
        LinearLayout primaryOtherSystemsLayout;
        EditText edtOtherSystems;
        EditText edtAdditionalInformation;
        Button hideOthersinfo;

        LOAD_OTHERSYS() {
            //13.OTHER SYSTEMS
            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_other_system, null);


            this.arrowOtherSystems = current_view.findViewById(id.arrow_other_systems);
            this.primaryOtherSystemsLayout = current_view.findViewById(id.primary_other_systems_layout);
            this.edtOtherSystems = current_view.findViewById(id.edt_other_systems);
            this.edtAdditionalInformation = current_view.findViewById(id.edt_additional_information);
            this.hideOthersinfo = current_view.findViewById(id.hide_othersinfo);

            this.arrowOtherSystems.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowOtherSystems, this.primaryOtherSystemsLayout));

            this.hideOthersinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowOtherSystems, this.primaryOtherSystemsLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);


        }

    }

    public class LOAD_DENTAL {

        @BindView(id.tooth_child_layout)
        LinearLayout toothChildLayout;
        @BindView(id.cchkbx_ur8)
        CheckBox cchkbxUr8;
        @BindView(id.cchkbx_ur7)
        CheckBox cchkbxUr7;
        @BindView(id.cchkbx_ur6)
        CheckBox cchkbxUr6;
        @BindView(id.cchkbx_ur5)
        CheckBox cchkbxUr5;
        @BindView(id.cchkbx_ur4)
        CheckBox cchkbxUr4;
        @BindView(id.cchkbx_ul1)
        CheckBox cchkbxUl1;
        @BindView(id.cchkbx_ul2)
        CheckBox cchkbxUl2;
        @BindView(id.cchkbx_ul3)
        CheckBox cchkbxUl3;
        @BindView(id.cchkbx_ul4)
        CheckBox cchkbxUl4;
        @BindView(id.cchkbx_ul5)
        CheckBox cchkbxUl5;
        @BindView(id.cchkbx_lr5)
        CheckBox cchkbxLr5;
        @BindView(id.cchkbx_lr4)
        CheckBox cchkbxLr4;
        @BindView(id.cchkbx_lr3)
        CheckBox cchkbxLr3;
        @BindView(id.cchkbx_lr2)
        CheckBox cchkbxLr2;
        @BindView(id.cchkbx_lr1)
        CheckBox cchkbxLr1;
        @BindView(id.cchkbx_ll1)
        CheckBox cchkbxLl1;
        @BindView(id.cchkbx_ll2)
        CheckBox cchkbxLl2;
        @BindView(id.cchkbx_ll3)
        CheckBox cchkbxLl3;
        @BindView(id.cchkbx_ll4)
        CheckBox cchkbxLl4;
        @BindView(id.cchkbx_ll5)
        CheckBox cchkbxLl5;

        @BindView(id.tooth_adult_layout)
        LinearLayout toothAdultLayout;
        @BindView(id.chkbx_ur8)
        CheckBox chkbxUr8;
        @BindView(id.chkbx_ur7)
        CheckBox chkbxUr7;
        @BindView(id.chkbx_ur6)
        CheckBox chkbxUr6;
        @BindView(id.chkbx_ur5)
        CheckBox chkbxUr5;
        @BindView(id.chkbx_ur4)
        CheckBox chkbxUr4;
        @BindView(id.chkbx_ur3)
        CheckBox chkbxUr3;
        @BindView(id.chkbx_ur2)
        CheckBox chkbxUr2;
        @BindView(id.chkbx_ur1)
        CheckBox chkbxUr1;
        @BindView(id.chkbx_ul1)
        CheckBox chkbxUl1;
        @BindView(id.chkbx_ul2)
        CheckBox chkbxUl2;
        @BindView(id.chkbx_ul3)
        CheckBox chkbxUl3;
        @BindView(id.chkbx_ul4)
        CheckBox chkbxUl4;
        @BindView(id.chkbx_ul5)
        CheckBox chkbxUl5;
        @BindView(id.chkbx_ul6)
        CheckBox chkbxUl6;
        @BindView(id.chkbx_ul7)
        CheckBox chkbxUl7;
        @BindView(id.chkbx_ul8)
        CheckBox chkbxUl8;
        @BindView(id.chkbx_lr8)
        CheckBox chkbxLr8;
        @BindView(id.chkbx_lr7)
        CheckBox chkbxLr7;
        @BindView(id.chkbx_lr6)
        CheckBox chkbxLr6;
        @BindView(id.chkbx_lr5)
        CheckBox chkbxLr5;
        @BindView(id.chkbx_lr4)
        CheckBox chkbxLr4;
        @BindView(id.chkbx_lr3)
        CheckBox chkbxLr3;
        @BindView(id.chkbx_lr2)
        CheckBox chkbxLr2;
        @BindView(id.chkbx_lr1)
        CheckBox chkbxLr1;
        @BindView(id.chkbx_ll1)
        CheckBox chkbxLl1;
        @BindView(id.chkbx_ll2)
        CheckBox chkbxLl2;
        @BindView(id.chkbx_ll3)
        CheckBox chkbxLl3;
        @BindView(id.chkbx_ll4)
        CheckBox chkbxLl4;
        @BindView(id.chkbx_ll5)
        CheckBox chkbxLl5;
        @BindView(id.chkbx_ll6)
        CheckBox chkbxLl6;
        @BindView(id.chkbx_ll7)
        CheckBox chkbxLl7;
        @BindView(id.chkbx_ll8)
        CheckBox chkbxLl8;

        ImageButton arrowDentalSystem;
        LinearLayout primaryDentalSystemLayout;
        Button hideDentalinfo;

        LOAD_DENTAL() {
            //12.DENTAL SYSTEM

            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_dental_system, null);

            this.arrowDentalSystem = current_view.findViewById(id.arrow_dental_system);
            this.primaryDentalSystemLayout = current_view.findViewById(id.primary_dental_system_layout);
            this.hideDentalinfo = current_view.findViewById(id.hide_dentalinfo);

            this.arrowDentalSystem.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowDentalSystem, this.primaryDentalSystemLayout));

            this.hideDentalinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowDentalSystem, this.primaryDentalSystemLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


            this.DentalCheckChangeListerners(this.cchkbxUr8);
            this.DentalCheckChangeListerners(this.cchkbxUr7);
            this.DentalCheckChangeListerners(this.cchkbxUr6);
            this.DentalCheckChangeListerners(this.cchkbxUr5);
            this.DentalCheckChangeListerners(this.cchkbxUr4);
            this.DentalCheckChangeListerners(this.cchkbxUl1);
            this.DentalCheckChangeListerners(this.cchkbxUl2);
            this.DentalCheckChangeListerners(this.cchkbxUl3);
            this.DentalCheckChangeListerners(this.cchkbxUl4);
            this.DentalCheckChangeListerners(this.cchkbxUl5);
            this.DentalCheckChangeListerners(this.cchkbxLr5);
            this.DentalCheckChangeListerners(this.cchkbxLr4);
            this.DentalCheckChangeListerners(this.cchkbxLr3);
            this.DentalCheckChangeListerners(this.cchkbxLr2);
            this.DentalCheckChangeListerners(this.cchkbxLr1);
            this.DentalCheckChangeListerners(this.cchkbxLl1);
            this.DentalCheckChangeListerners(this.cchkbxLl2);
            this.DentalCheckChangeListerners(this.cchkbxLl3);
            this.DentalCheckChangeListerners(this.cchkbxLl4);
            this.DentalCheckChangeListerners(this.cchkbxLl5);


            this.DentalCheckChangeListerners(this.chkbxUr8);
            this.DentalCheckChangeListerners(this.chkbxUr7);
            this.DentalCheckChangeListerners(this.chkbxUr6);
            this.DentalCheckChangeListerners(this.chkbxUr5);
            this.DentalCheckChangeListerners(this.chkbxUr4);
            this.DentalCheckChangeListerners(this.chkbxUr3);
            this.DentalCheckChangeListerners(this.chkbxUr2);
            this.DentalCheckChangeListerners(this.chkbxUr1);
            this.DentalCheckChangeListerners(this.chkbxUl1);
            this.DentalCheckChangeListerners(this.chkbxUl2);
            this.DentalCheckChangeListerners(this.chkbxUl3);
            this.DentalCheckChangeListerners(this.chkbxUl4);
            this.DentalCheckChangeListerners(this.chkbxUl5);
            this.DentalCheckChangeListerners(this.chkbxUl6);
            this.DentalCheckChangeListerners(this.chkbxUl7);
            this.DentalCheckChangeListerners(this.chkbxUl8);
            this.DentalCheckChangeListerners(this.chkbxLr8);
            this.DentalCheckChangeListerners(this.chkbxLr7);
            this.DentalCheckChangeListerners(this.chkbxLr6);
            this.DentalCheckChangeListerners(this.chkbxLr5);
            this.DentalCheckChangeListerners(this.chkbxLr4);
            this.DentalCheckChangeListerners(this.chkbxLr3);
            this.DentalCheckChangeListerners(this.chkbxLr2);
            this.DentalCheckChangeListerners(this.chkbxLr1);
            this.DentalCheckChangeListerners(this.chkbxLl1);
            this.DentalCheckChangeListerners(this.chkbxLl2);
            this.DentalCheckChangeListerners(this.chkbxLl3);
            this.DentalCheckChangeListerners(this.chkbxLl4);
            this.DentalCheckChangeListerners(this.chkbxLl5);
            this.DentalCheckChangeListerners(this.chkbxLl6);
            this.DentalCheckChangeListerners(this.chkbxLl7);
            this.DentalCheckChangeListerners(this.chkbxLl8);


        }


        public void DentalCheckChangeListerners(CheckBox chkbx) {
            try {

                chkbx.setOnCheckedChangeListener((compoundButton, b) -> {

                    if (compoundButton.isChecked()) {

                        compoundButton.setTextColor(getResources().getColor(color.white));
                        compoundButton.setBackgroundColor(getResources().getColor(color.red));

                    } else {
                        compoundButton.setTextColor(getResources().getColor(color.ourcolour));
                        compoundButton.setBackgroundColor(getResources().getColor(color.white));
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public class LOAD_PNC {

        //care of baby
        @BindView(id.edt_urine)
        EditText edtUrine;
        @BindView(id.edt_stool)
        EditText edtStool;
        @BindView(id.edt_diarrhea)
        EditText edtDiarrhea;
        @BindView(id.edt_vomit)
        EditText edtVomit;
        @BindView(id.edt_conv)
        EditText edtConv;
        @BindView(id.rad_grp_activity)
        RadioGroup radGrpActivity;
        @BindView(id.rad_btn_activity1)
        RadioButton radBtnActivity1;
        @BindView(id.rad_btn_activity2)
        RadioButton radBtnActivity2;
        @BindView(id.rad_grp_sucking)
        RadioGroup radGrpSucking;
        @BindView(id.rad_btn_sucking1)
        RadioButton radBtnSucking1;
        @BindView(id.rad_btn_sucking2)
        RadioButton radBtnSucking2;
        @BindView(id.rad_grp_breathing)
        RadioGroup radGrpBreathing;
        @BindView(id.rad_btn_breathing1)
        RadioButton radBtnBreathing1;
        @BindView(id.rad_btn_breathing2)
        RadioButton radBtnBreathing2;
        @BindView(id.rad_grp_chest)
        RadioGroup radGrpChest;
        @BindView(id.rad_btn_chest1)
        RadioButton radBtnChest1;
        @BindView(id.rad_btn_chest2)
        RadioButton radBtnChest2;
        @BindView(id.textView_temper)
        TextView textViewTemper;
        @BindView(id.edittext_temper)
        EditText edittextTemper;
        @BindView(id.textView_Jaundice)
        TextView textViewJaundice;
        @BindView(id.edittext_jaundice)
        EditText edittextJaundice;
        @BindView(id.textView_stump)
        TextView textViewStump;
        @BindView(id.edittext_stump)
        EditText edittextStump;
        @BindView(id.textView_Skin)
        TextView textViewSkin;
        @BindView(id.rad_grp_skin)
        RadioGroup radGrpSkin;
        @BindView(id.rad_btn_skin1)
        RadioButton radBtnSkin1;
        @BindView(id.rad_btn_skin2)
        RadioButton radBtnSkin2;
        @BindView(id.textView_Any_other_complications)
        TextView textViewAnyOtherComplications;
        @BindView(id.edittext__Any_other_complications)
        EditText edittextAnyOtherComplications;
        @BindView(id.textView_AnyComplaints)
        TextView textViewAnyComplaints;

        //post portum care
        @BindView(id.edittext_Any_Complaints)
        EditText edittextAnyComplaints;

        @BindView(id.textView_Pallor)
        TextView textViewPallor;
        @BindView(id.edittext_pallor)
        EditText edittextPallor;
        @BindView(id.textView_Pulse_rate)
        TextView textViewPulseRate;
        @BindView(id.edittext_pulse_rate)
        EditText edittextPulseRate;
        @BindView(id.textView_Bloodpressure)
        TextView textViewBloodpressure;
        @BindView(id.edittext_Bloodpressure)
        EditText edittextBloodpressure;
        @BindView(id.textView_Temperature)
        TextView textViewTemperature;
        @BindView(id.edittext_Temperature)
        EditText edittextTemperature;
        @BindView(id.textView_Breasts)
        TextView textViewBreasts;
        @BindView(id.rad_grp_breast)
        RadioGroup radGrpBreast;
        @BindView(id.rad_btn_breast1)
        RadioButton radBtnBreast1;
        @BindView(id.rad_btn_breast2)
        RadioButton radBtnBreast2;
        @BindView(id.textView_Nipples)
        TextView textViewNipples;
        @BindView(id.rad_grp_nipple)
        RadioGroup radGrpNipple;
        @BindView(id.rad_btn_nipple1)
        RadioButton radBtnNipple1;
        @BindView(id.rad_btn_nipple2)
        RadioButton radBtnNipple2;
        @BindView(id.textView_Uterus_Tenderness)
        TextView textViewUterusTenderness;
        @BindView(id.rad_grp_uterus)
        RadioGroup radGrpUterus;
        @BindView(id.rad_btn_uterus1)
        RadioButton radBtnUterus1;
        @BindView(id.rad_btn_uterus2)
        RadioButton radBtnUterus2;
        @BindView(id.textView_BleedingPV)
        TextView textViewBleedingPV;
        @BindView(id.rad_grp_bleed)
        RadioGroup radGrpBleed;
        @BindView(id.rad_btn_bleed1)
        RadioButton radBtnBleed1;
        @BindView(id.rad_btn_bleed2)
        RadioButton radBtnBleed2;
        @BindView(id.textView_Lochia)
        TextView textViewLochia;
        @BindView(id.rad_grp_lochia)
        RadioGroup radGrpLochia;
        @BindView(id.rad_btn_lochia1)
        RadioButton radBtnLochia1;
        @BindView(id.rad_btn_lochia2)
        RadioButton radBtnLochia2;
        @BindView(id.textView_Episiotomy_Tear)
        TextView textViewEpisiotomyTear;
        @BindView(id.rad_grp_tear)
        RadioGroup radGrpTear;
        @BindView(id.rad_btn_tear1)
        RadioButton radBtnTear1;
        @BindView(id.rad_btn_tear2)
        RadioButton radBtnTear2;
        @BindView(id.textView_Family_planning_counselling)
        TextView textViewFamilyPlanningCounselling;
        @BindView(id.editText_Family_planning_counselling)
        EditText editTextFamilyPlanningCounselling;
        @BindView(id.textView_other_complications)
        TextView textViewOtherComplications;
        @BindView(id.edittext_other_complications)
        EditText edittextOtherComplications;
        @BindView(id.textView_Date_of_delivery)
        TextView textViewDateOfDelivery;
        @BindView(id.editText__Date_of_delivery)
        EditText editTextDateOfDelivery;
        @BindView(id.textView_Place_of_delivery)
        TextView textViewPlaceOfDelivery;
        @BindView(id.edittext__Place_of_delivery)
        EditText edittextPlaceOfDelivery;
        @BindView(id.textView_Type_of_delivery)
        TextView textViewTypeOfDelivery;
        @BindView(id.chk_normal)
        CheckBox chkNormal;
        @BindView(id.chk_inst)
        CheckBox chkInst;
        @BindView(id.chk_cs)
        CheckBox chkCs;
        @BindView(id.textView_Term_Preterm)
        TextView textViewTermPreterm;
        @BindView(id.edittext_Term_Preterm)
        EditText edittextTermPreterm;
        @BindView(id.textView_postdelivery)
        TextView textViewPostdelivery;
        @BindView(id.edittext_post_delivery)
        EditText edittextPostDelivery;
        @BindView(id.textView_complications)
        TextView textViewComplications;
        @BindView(id.edittext_complications)
        EditText edittextComplications;
        @BindView(id.textView_sexofbaby)
        TextView textViewSexofbaby;
        @BindView(id.rad_grp__sexofbaby)
        RadioGroup radGrpSexofbaby;
        @BindView(id.rad_btn_male)
        RadioButton radBtnMale;
        @BindView(id.rad_btn_female)
        RadioButton radBtnFemale;
        @BindView(id.textView_Weight_of_Baby)
        TextView textViewWeightOfBaby;
        @BindView(id.edittext_kg)
        EditText edittextKg;
        @BindView(id.textView_kg)
        TextView textViewKg;
        @BindView(id.edittext_gms)
        EditText edittextGms;
        @BindView(id.textView_cried)
        TextView textViewCried;
        @BindView(id.rad_grp_cried)
        RadioGroup radGrpCried;
        @BindView(id.rad_cry_yes)
        RadioButton radCryYes;
        @BindView(id.rad_cry_no)
        RadioButton radCryNo;
        @BindView(id.textView_breast_feeding)
        TextView textViewBreastFeeding;
        @BindView(id.rad_grp_feed)
        RadioGroup radGrpFeed;
        @BindView(id.rad_feed_yes)
        RadioButton radFeedYes;
        @BindView(id.rad_feed_no)
        RadioButton radFeedNo;

        ImageButton arrowPnc;
        LinearLayout primaryPncLayout;
        Button hideGeneralinfo;

        LOAD_PNC() {
            //11.PNC SYSTEM

            View current_view = CaseNotes.this.getLayoutInflater().inflate(layout.kdmc_layout_casenotes_pnc_system, null);

            this.arrowPnc = current_view.findViewById(id.arrow_pnc);
            this.primaryPncLayout = current_view.findViewById(id.primary_pnc_layout);
            this.hideGeneralinfo = current_view.findViewById(id.hide_generalinfo);

            this.arrowPnc.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowPnc, this.primaryPncLayout));

            this.hideGeneralinfo.setOnClickListener(view -> CaseNotes.this.toggleSectionInput(this.arrowPnc, this.primaryPncLayout));

            CaseNotes.this.allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


            this.editTextDateOfDelivery.setOnClickListener(view -> Inpatient_Inputs.openDatePickerDialog(this.editTextDateOfDelivery));


        }

    }


//**************************************************************************************************
}//END
