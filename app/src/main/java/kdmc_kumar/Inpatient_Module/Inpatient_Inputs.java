package kdmc_kumar.Inpatient_Module;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MedicalCaseRecords;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.KDMCRecyclerAdapter;
import kdmc_kumar.Utilities_Others.KDMCRecyclerAdapter.AdapterView;
import kdmc_kumar.Utilities_Others.ViewAnimation;


public class Inpatient_Inputs extends AppCompatActivity {

    private String patienname = "";
    private String patientagegen = "";

    public Inpatient_Inputs() {
    }

    // #######################################################################################################
    @BindView(id.clinicalinformation_parent_layout)
    CoordinatorLayout clinicalinformationParentLayout;
    @BindView(id.inpatiententry_nesetedscrollview)
    NestedScrollView inpatiententryNesetedscrollview;
    @BindView(id.upperlayout)
    LinearLayout upperlayout;
    @BindView(id.imgvw_patientphoto)
    CircleImageView imgvwPatientphoto;
    @BindView(id.tvw_agegender)
    TextView tvwAgegender;
    @BindView(id.txt_patid)
    TextView txtPatid;
    @BindView(id.textView12)
    TextView textView12;
    @BindView(id.icome_text)
    TextView icomeText;
    @BindView(id.caste_text)
    TextView casteText;
    @BindView(id.caretaker_text)
    TextView caretakerText;
    @BindView(id.ward_no)
    TextView wardNo;
    @BindView(id.room_no)
    TextView roomNo;
    @BindView(id.bed_no)
    TextView bedNo;
    @BindView(id.textview_ward)
    TextView textviewWard;
    @BindView(id.textview_roomnumber)
    TextView textviewRoomnumber;
    @BindView(id.textview_bednumber)
    TextView textviewBednumber;
    @BindView(id.textview_admitteddate)
    TextView textviewAdmitteddate;
    @BindView(id.edittext_inaptientchart_date)
    EditText edittextInaptientchartDate;
    @BindView(id.edittext_inaptientchart_time)
    EditText edittextInaptientchartTime;
    @BindView(id.edittext_inaptientchart_bpsytolic)
    EditText edittextInaptientchartBpsytolic;
    @BindView(id.edittext_inaptientchart_bpdiastolic)
    EditText edittextInaptientchartBpdiastolic;
    @BindView(id.edittext_inaptientchart_pulseperminute)
    EditText edittextInaptientchartPulseperminute;
    @BindView(id.spinner_inaptientchart_temperature)
    Spinner spinnerInaptientchartTemperature;
    @BindView(id.edittext_inaptientchart_resp)
    EditText edittextInaptientchartResp;
    @BindView(id.edittext_inaptientchart_spo2)
    EditText edittextInaptientchartSpo2;
    @BindView(id.edittext_inaptientchart_drugorder)
    EditText edittextInaptientchartDrugorder;
    @BindView(id.edittext_inaptientchart_nursing_instruction)
    EditText edittextInaptientchartNursingInstruction;
    @BindView(id.edittext_inaptientchart_oral)
    EditText edittextInaptientchartOral;
    @BindView(id.edittext_inaptientchart_fluids)
    EditText edittextInaptientchartFluids;
    @BindView(id.edittext_inaptientchart_rvles)
    EditText edittextInaptientchartRvles;
    @BindView(id.edittext_inaptientchart_motion)
    EditText edittextInaptientchartMotion;
    @BindView(id.edittext_inaptientchart_urine)
    EditText edittextInaptientchartUrine;
    @BindView(id.edittext_diabetichart_date)
    EditText edittextDiabetichartDate;
    @BindView(id.edittext_diabetichart_time)
    EditText edittextDiabetichartTime;
    @BindView(id.edittext_diabetichart_speciali_instruction)
    EditText edittextDiabetichartSpecialiInstruction;
    @BindView(id.autoCompleteTextView_diabetichart_urine_sugar)
    EditText autoCompleteTextViewDiabetichartUrineSugar;
    @BindView(id.edittext_diabetichart_lente)
    EditText edittextDiabetichartLente;
    @BindView(id.edittext_diabetichart_insulin_plain)
    EditText edittextDiabetichartInsulinPlain;
    @BindView(id.edittext_diabetichart_blood_sugar)
    EditText edittextDiabetichartBloodSugar;
    @BindView(id.edittext_diabetichart_ketone_bodies)
    EditText edittextDiabetichartKetoneBodies;
    @BindView(id.edittext_temperaturechart_date)
    EditText edittextTemperaturechartDate;
    @BindView(id.edittext_temperaturechart_time)
    EditText edittextTemperaturechartTime;
    @BindView(id.spinner_temperaturechart_selecttemperature)
    Spinner spinnerTemperaturechartSelecttemperature;
    @BindView(id.edittext_temperaturechart_visit_summary)
    EditText edittextTemperaturechartVisitSummary;
    @BindView(id.edittext_surgery_date)
    EditText edittextSurgeryDate;
    @BindView(id.edittext_surgery_preoperative_diagnosis)
    EditText edittextSurgeryPreoperativeDiagnosis;
    @BindView(id.editext_surgery_operativenotes)
    EditText editextSurgeryOperativenotes;
    @BindView(id.edittext_surgery_position)
    EditText edittextSurgeryPosition;
    @BindView(id.edittext_surgery_procedure)
    EditText edittextSurgeryProcedure;
    @BindView(id.edittext_surgery_closure)
    EditText edittextSurgeryClosure;
    @BindView(id.edittext_surgery_postoperative_diagnosis)
    EditText edittextSurgeryPostoperativeDiagnosis;
    @BindView(id.edittext_surgery_surgeon)
    EditText edittextSurgerySurgeon;
    @BindView(id.edittext_surgery_anaesthetist)
    EditText edittextSurgeryAnaesthetist;
    @BindView(id.edittext_surgery_asst)
    EditText edittextSurgeryAsst;
    @BindView(id.edittext_surgery_blood_loss)
    EditText edittextSurgeryBloodLoss;
    @BindView(id.edittext_surgery_histo_pathological)
    EditText edittextSurgeryHistoPathological;
    @BindView(id.edittext_surgery_post_op_instruction)
    EditText edittextSurgeryPostOpInstruction;
    @BindView(id.Spinner_medicalcase_record_undercareof)
    Spinner spinnerMedicalcaseRecordUndercareof;
    @BindView(id.edittext_medicalcase_record_student)
    EditText edittextMedicalcaseRecordStudent;
    @BindView(id.autocomplete_medicalcase_provisional_diagnosis)
    AutoCompleteTextView autocompleteMedicalcaseProvisionalDiagnosis;
    @BindView(id.edittext_medicalcase_final_diagnosis)
    EditText edittextMedicalcaseFinalDiagnosis;
    @BindView(id.spinner_medicalcase_select_result)
    Spinner spinnerMedicalcaseSelectResult;
    @BindView(id.edittext_medicalcase_date)
    EditText edittextMedicalcaseDate;
    @BindView(id.edittext_medicalcase_time)
    EditText edittextMedicalcaseTime;
    @BindView(id.edittext_medicalcase_clinicalnotes)
    EditText edittextMedicalcaseClinicalnotes;
    @BindView(id.edittext_medicalcase_treatement_and_diet)
    EditText edittextMedicalcaseTreatementAndDiet;


    @BindView(id.inpatientchart_primary)
    LinearLayout inpatientchart_primary;


    @BindView(id.diabetic_primary)
    LinearLayout diabetic_primary;

    @BindView(id.temperature_primary)
    LinearLayout temperature_primary;

    @BindView(id.surgery_primary)
    LinearLayout surgery_primary;

    @BindView(id.medicalcase_primary)
    LinearLayout medicalcase_primary;

    @BindView(id.recyler_View)
    RecyclerView recyclerView;

    @BindView(id.toolbar)
    Toolbar toolbar;
    @BindView(id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title)
    TextView toolbarTitle;
    @BindView(id.toolbar_exit)
    AppCompatImageView toolbarExit;

    // #######################################################################################################
    private String PATIENT_NAME;
    private String PATIENT_AGEGENDER;
    private String PATIENT_INCOME;
    private String PATIENT_CASTE;
    private String PATIENT_CARETAKER;
    private String PATIENT_ID;
    // #######################################################################################################
    ArrayList<MedicalCaseRecords> medicalCaseRecords = new ArrayList<>();
    KDMCRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.kdmc_layout_inpatiententry);
        ButterKnife.bind(this);


        try {
            this.loadInpatientDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void loadInpatientDetails() {

        Bundle b = this.getIntent().getExtras();
        this.PATIENT_ID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);
        this.PATIENT_NAME = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.PATIENT_ID + '\'');
        this.PATIENT_AGEGENDER = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + this.PATIENT_ID + '\'');
        this.PATIENT_INCOME = BaseConfig.GetValues("select income as ret_values from Patreg where Patid='" + this.PATIENT_ID + '\'');
        this.PATIENT_CASTE = BaseConfig.GetValues("select caste as ret_values from Patreg where Patid='" + this.PATIENT_ID + '\'');
        this.PATIENT_CARETAKER = BaseConfig.GetValues("select caretaker as ret_values from Patreg where Patid='" + this.PATIENT_ID + '\'');

        String ward_values = BaseConfig.CheckDBString(BaseConfig.getwardNameFromId(BaseConfig.GetValues("select wardno as ret_values from Patreg where Patid='" + this.PATIENT_ID + "'"), this));
        String room_values = BaseConfig.CheckDBString(BaseConfig.getRoomNameFromId(BaseConfig.GetValues("select roomno as ret_values from Patreg where Patid='" + this.PATIENT_ID + "'"), this));
        String bed_values = BaseConfig.CheckDBString(BaseConfig.getBedNameFromId(BaseConfig.GetValues("select bedno as ret_values from Patreg where Patid='" + this.PATIENT_ID + "'"), this));


        String admitDate = BaseConfig.GetValues("select admitdt as ret_values from Patreg where Patid='" + this.PATIENT_ID + '\'');


        try {
            if (admitDate.contains("T")) {
                admitDate = admitDate.split("T")[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.txtPatid.setText(this.PATIENT_ID);
        this.textView12.setText(this.PATIENT_NAME);
        this.tvwAgegender.setText(this.PATIENT_AGEGENDER);
        this.icomeText.setText(this.PATIENT_INCOME);
        this.casteText.setText(this.PATIENT_CASTE);
        this.caretakerText.setText(this.PATIENT_CARETAKER);
        this.wardNo.setText(ward_values);
        this.roomNo.setText(room_values);
        this.bedNo.setText(bed_values);
        this.textviewAdmitteddate.setText(admitDate);


        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String arrs1[] = {"Select Under Care of", "Medical Officer", "Student"};
        String arrs[] = {"Select Result", "Cured", "Relieved", "Unrelieved", "Absconded", "Died"};
        ArrayAdapter cc = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrs1);
        this.spinnerMedicalcaseRecordUndercareof.setAdapter(cc);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrs);
        this.spinnerMedicalcaseSelectResult.setAdapter(aa);


        this.getUserDetails(this.tvwAgegender, this.imgvwPatientphoto);


        //Load Pickers
        this.edittextInaptientchartDate.setOnClickListener(view -> Inpatient_Inputs.openDatePickerDialog(this.edittextInaptientchartDate));
        this.edittextInaptientchartTime.setOnClickListener(view -> this.openTimePickerDialog(this.edittextInaptientchartTime));
        this.edittextInaptientchartDate.setText(this.getDate());
        this.edittextInaptientchartTime.setText(this.getTime());

        this.edittextDiabetichartTime.setOnClickListener(view -> this.openTimePickerDialog(this.edittextDiabetichartTime));
        this.edittextDiabetichartDate.setOnClickListener(view -> Inpatient_Inputs.openDatePickerDialog(this.edittextDiabetichartDate));
        this.edittextDiabetichartDate.setText(this.getDate());
        this.edittextDiabetichartTime.setText(this.getTime());

        this.edittextTemperaturechartTime.setOnClickListener(view -> this.openTimePickerDialog(this.edittextTemperaturechartTime));
        this.edittextTemperaturechartDate.setOnClickListener(view -> Inpatient_Inputs.openDatePickerDialog(this.edittextTemperaturechartDate));
        this.edittextTemperaturechartDate.setText(this.getDate());
        this.edittextTemperaturechartTime.setText(this.getTime());

        this.edittextSurgeryDate.setOnClickListener(view -> Inpatient_Inputs.openDatePickerDialog(this.edittextSurgeryDate));
        this.edittextSurgeryDate.setText(this.getDate());

        this.edittextMedicalcaseDate.setOnClickListener(view -> Inpatient_Inputs.openDatePickerDialog(this.edittextMedicalcaseDate));
        this.edittextMedicalcaseTime.setOnClickListener(view -> this.openTimePickerDialog(this.edittextMedicalcaseTime));
        this.edittextMedicalcaseDate.setText(this.getDate());
        this.edittextMedicalcaseTime.setText(this.getTime());

        this.toolbarBack.setOnClickListener(view -> finish());
        this.toolbarExit.setOnClickListener(view -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarTitle.setText("Inpatient Entry");

    }

    public class MedicalCaseViewHolder extends ViewHolder {

        @BindView(id.date_mc)
        TextView dateMc;
        @BindView(id.clinical_notes_value)
        TextView clinicalNotesValue;
        @BindView(id.treatment_diet_value)
        TextView treatmentDietValue;

        @BindView(id.ic_delete)
        ImageView delete;

        public MedicalCaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void openTimePickerDialog(EditText editText) {



        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(editText.getContext(), new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                // Set a variable to hold the current time AM PM Status
                // Initially we set the variable value to AM
                String status = "AM";

                if(hourOfDay > 11)
                {
                    // If the hour is greater than or equal to 12
                    // Then the current AM PM status is PM
                    status = "PM";
                }

                // Initialize a new variable to hold 12 hour format hour value
                int hour_of_12_hour_format;

                if(hourOfDay > 11){

                    // If the hour is greater than or equal to 12
                    // Then we subtract 12 from the hour to make it 12 hour format time
                    hour_of_12_hour_format = hourOfDay - 12;
                }
                else {
                    hour_of_12_hour_format = hourOfDay;
                }

                // Display the 12 hour format time in app interface
                editText.setText(hour_of_12_hour_format + " : " + minute + " : " + status);


            }
        }, hour, minute, false);

        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }


    public static void openDatePickerDialog(EditText editText) {

        Calendar myCalendar = Calendar.getInstance();
        @SuppressLint("DefaultLocale")
        OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            editText.setText(String.format("%d/%d/%d", myCalendar.get(Calendar.DAY_OF_MONTH), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.YEAR)));

        };

        new DatePickerDialog(editText.getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private final void getUserDetails(TextView patientname, ImageView PatientImage) {
        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select * from patreg where Patid='"+ this.PATIENT_ID + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    patientname.setText(String.format("%s-%s", c.getString(c.getColumnIndex("age")), c.getString(c.getColumnIndex("gender"))));

                    String Photo_Path = c.getString(c.getColumnIndex("PC"));

                    BaseConfig.LoadPatientImage(Photo_Path, PatientImage, 100);
                    patienname = c.getString(c.getColumnIndex("name"));
                    patientagegen = c.getString(c.getColumnIndex("age")) + '-' + c.getString(c.getColumnIndex("gender"));

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
    }


    private void toggleSectionInput(View view, View PrimaryLayout) {
        boolean show = this.toggleArrow(view);
        if (show) {
           // ViewAnimation.expand(PrimaryLayout, () -> Tools.nestedScrollTo(inpatiententryNesetedscrollview, PrimaryLayout));
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


    //#####################################################################//
    //#####################################################################//

    @OnClick(id.arrow_inpatient_chart)
    void onArrowInpatientChartClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_inpatient_chart), this.inpatientchart_primary);
    }

    @OnClick(id.arrow_diabetic_chart)
    void onArrowDiabeticChartClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_diabetic_chart), this.diabetic_primary);
    }

    @OnClick(id.arrow_temperature_chart)
    void onArrowTemperatureChartClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_temperature_chart), this.temperature_primary);

    }

    @OnClick(id.arrow_surgery_record)
    void onArrowSurgeryRecordClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_surgery_record), this.surgery_primary);
    }

    @OnClick(id.arrow_medicalcase_record)
    void onArrowMedicalcaseRecordClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_medicalcase_record), this.medicalcase_primary);
    }


    //#####################################################################//
    //#####################################################################//

    public static int mYear;
    public static int mMonth;
    public static int mDay;
    public static int mcYear;
    public static int mcMonth;
    public static int mcDay;

    public String getDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public String getTime() {
        Date c = Calendar.getInstance().getTime();
        String time = c.getHours() + ":" + c.getMinutes();
        return time;
    }

    protected void SaveLocal(int id) {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);

            Calendar c = Calendar.getInstance();
            Inpatient_Inputs.mYear = c.get(Calendar.YEAR);
            Inpatient_Inputs.mMonth = c.get(Calendar.MONTH);
            Inpatient_Inputs.mDay = c.get(Calendar.DAY_OF_MONTH);

            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dttm = dateformt.format(date);

            String InpatientChartid = BaseConfig.Patent_Id;


            if (id == 1) {

                String bp_txt1, bp_txt2, pulse_txt, temp_txt, resp_txt, spo2_txt, nursing_txt, drugorder_txt, iporal_txt,
                        ipfluid_txt, oprives_txt, opmotion_txt, opurine_txt, ipseltime = "-";


                bp_txt1 = this.CheckEdittextIsEmpty(this.edittextInaptientchartBpsytolic);
                bp_txt2 = this.CheckEdittextIsEmpty(this.edittextInaptientchartBpdiastolic);
                pulse_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartPulseperminute);
                temp_txt = this.CheckEdittextIsEmpty(this.spinnerInaptientchartTemperature);
                resp_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartResp);
                spo2_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartSpo2);
                nursing_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartNursingInstruction);
                drugorder_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartDrugorder);
                iporal_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartOral);
                ipfluid_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartFluids);
                oprives_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartRvles);
                opmotion_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartMotion);
                opurine_txt = this.CheckEdittextIsEmpty(this.edittextInaptientchartUrine);
                ipseltime = this.CheckEdittextIsEmpty(this.edittextInaptientchartTime);


                ContentValues cv = new ContentValues();
                cv.put("DiagId", InpatientChartid);
                cv.put("patid", this.PATIENT_ID);
                cv.put("pname", this.PATIENT_NAME);
                cv.put("pagegen", this.textView12.getText().toString());
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", this.edittextInaptientchartDate.getText().toString());
                cv.put("doc_visit_time", ipseltime);
                cv.put("bp", bp_txt1);
                cv.put("bpd", bp_txt2);
                cv.put("pulse", pulse_txt);
                cv.put("temp", temp_txt.split(" ")[0].trim());
                cv.put("resp", resp_txt);
                cv.put("spo2", spo2_txt);
                cv.put("drugorder", drugorder_txt);
                cv.put("nursing_instr", nursing_txt);
                cv.put("ip_oral", iporal_txt);
                cv.put("ip_fluids", ipfluid_txt);
                cv.put("op_rvies", oprives_txt);
                cv.put("op_motion", opmotion_txt);
                cv.put("op_urine", opurine_txt);
                cv.put("dischargedon", "");
                cv.put("dischrg_dttime", "");
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_MainChart", null, cv);

                this.showSuccess();


                try {
                    this.edittextInaptientchartBpsytolic.setText("");
                    this.edittextInaptientchartDate.setText("");
                    this.edittextInaptientchartBpdiastolic.setText("");
                    this.edittextInaptientchartPulseperminute.setText("");
                    this.spinnerInaptientchartTemperature.setSelection(0);
                    this.edittextInaptientchartResp.setText("");
                    this.edittextInaptientchartSpo2.setText("");
                    this.edittextInaptientchartNursingInstruction.setText("");
                    this.edittextInaptientchartDrugorder.setText("");
                    this.edittextInaptientchartOral.setText("");
                    this.edittextInaptientchartFluids.setText("");
                    this.edittextInaptientchartRvles.setText("");
                    this.edittextInaptientchartMotion.setText("");
                    this.edittextInaptientchartUrine.setText("");
                    this.edittextInaptientchartTime.setText("");

                    this.toggleSectionInput(this.findViewById(id.arrow_inpatient_chart), this.inpatientchart_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (id == 2) {/////////////////////////////////////////////////////////////////////

                String urinesgr_txt, lente_txt, insulinplain_txt, bloodsgr_txt, ketone_txt, ipdiab_time = "-";


                urinesgr_txt = this.CheckEdittextIsEmpty(this.autoCompleteTextViewDiabetichartUrineSugar);
                lente_txt = this.CheckEdittextIsEmpty(this.edittextDiabetichartLente);
                insulinplain_txt = this.CheckEdittextIsEmpty(this.edittextDiabetichartInsulinPlain);
                bloodsgr_txt = this.CheckEdittextIsEmpty(this.edittextDiabetichartBloodSugar);
                ketone_txt = this.CheckEdittextIsEmpty(this.edittextDiabetichartKetoneBodies);
                ipdiab_time = this.CheckEdittextIsEmpty(this.edittextDiabetichartTime);


                ContentValues cv = new ContentValues();

                cv.put("DiagId", InpatientChartid);
                cv.put("patid", this.PATIENT_ID);
                cv.put("pname", this.PATIENT_NAME);
                cv.put("pagegen", this.tvwAgegender.getText().toString());
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", this.edittextDiabetichartDate.getText().toString());
                cv.put("doc_visit_time", ipdiab_time);
                cv.put("spl_instr", this.edittextDiabetichartSpecialiInstruction.getText().toString());
                cv.put("urine_sugar", urinesgr_txt);
                cv.put("lente", lente_txt);
                cv.put("insulin_plain", insulinplain_txt);
                cv.put("blood_sugar", bloodsgr_txt);
                cv.put("ketone_bodies", ketone_txt);
                cv.put("room_no", "");
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_DiabeticChart", null, cv);

                //final String Update_Query = "update InpatientChartmvalue set minpcnum=minpcnum +1";
                //BaseConfig.SaveData(Update_Query);

                this.showSuccess();


                try {
                    this.edittextDiabetichartLente.setText("");
                    this.edittextDiabetichartInsulinPlain.setText("");
                    this.edittextDiabetichartBloodSugar.setText("");
                    this.edittextDiabetichartKetoneBodies.setText("");
                    this.edittextDiabetichartDate.setText("");
                    this.edittextDiabetichartTime.setText("");
                    this.edittextDiabetichartSpecialiInstruction.setText("");
                    this.autoCompleteTextViewDiabetichartUrineSugar.setText("");
                    this.toggleSectionInput(this.findViewById(id.arrow_diabetic_chart), this.diabetic_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (id == 3) {
                String temperature_values = "0", temperature_time = "";

                if (this.spinnerTemperaturechartSelecttemperature.getSelectedItemPosition() > 0) {
                    temperature_values = this.spinnerTemperaturechartSelecttemperature.getSelectedItem().toString();
                }


                ContentValues cv = new ContentValues();

                cv.put("DiagId", InpatientChartid);
                cv.put("patid", this.PATIENT_ID);
                cv.put("pname", this.PATIENT_NAME);
                cv.put("pagegen", this.PATIENT_AGEGENDER);
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", this.edittextTemperaturechartDate.getText().toString());
                cv.put("doc_visit_time", this.edittextTemperaturechartTime.getText().toString());
                cv.put("temperature", temperature_values);
                cv.put("visitsummary", this.edittextTemperaturechartVisitSummary.getText().toString());
                cv.put("temptakentime", temperature_time);
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_TemperatureChart", null, cv);
                //#######################################################


                this.showSuccess();


                try {
                    this.edittextTemperaturechartDate.setText("");
                    this.edittextTemperaturechartTime.setText("");
                    this.edittextTemperaturechartVisitSummary.setText("");
                    this.spinnerTemperaturechartSelecttemperature.setSelection(0);
                    this.toggleSectionInput(this.findViewById(id.arrow_temperature_chart), this.temperature_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (id == 4) {

                //#######################################################
                ContentValues cv = new ContentValues();

                cv.put("DiagId", InpatientChartid);
                cv.put("patid", this.PATIENT_ID);
                cv.put("pname", this.PATIENT_NAME);
                cv.put("pagegen", this.PATIENT_AGEGENDER);
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", this.edittextSurgeryDate.getText().toString());
                cv.put("doc_visit_time", BaseConfig.GetCurrentTime());
                cv.put("pre_operativediag", this.edittextSurgeryPreoperativeDiagnosis.getText().toString());
                cv.put("operative_notes", this.editextSurgeryOperativenotes.getText().toString());
                cv.put("position", this.edittextSurgeryPosition.getText().toString());
                cv.put("procedure", this.edittextSurgeryProcedure.getText().toString());
                cv.put("closure", this.edittextSurgeryClosure.getText().toString());
                cv.put("post_operativediag", this.edittextSurgeryPostoperativeDiagnosis.getText().toString());
                cv.put("surgeon", this.edittextSurgerySurgeon.getText().toString());
                cv.put("anaesthetist", this.edittextSurgeryAnaesthetist.getText().toString());
                cv.put("asst", this.edittextSurgeryAsst.getText().toString());
                cv.put("bloodloss", this.edittextSurgeryBloodLoss.getText().toString());
                cv.put("histopathological", this.edittextSurgeryHistoPathological.getText().toString());
                cv.put("post_op_instruct", this.edittextSurgeryPostOpInstruction.getText().toString());
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_SurgeryRecord", null, cv);

                this.showSuccess();

                try {
                    this.edittextSurgeryDate.setText("");
                    this.edittextSurgeryPreoperativeDiagnosis.setText("");
                    this.editextSurgeryOperativenotes.setText("");
                    this.edittextSurgeryPosition.setText("");
                    this.edittextSurgeryClosure.setText("");
                    this.edittextSurgeryPostoperativeDiagnosis.setText("");
                    this.edittextSurgerySurgeon.setText("");
                    this.edittextSurgeryAnaesthetist.setText("");
                    this.edittextSurgeryAsst.setText("");
                    this.edittextSurgeryBloodLoss.setText("");
                    this.edittextSurgeryHistoPathological.setText("");
                    this.edittextSurgeryPostOpInstruction.setText("");

                    this.edittextSurgeryProcedure.setText("");
                    this.toggleSectionInput(this.findViewById(id.arrow_surgery_record), this.surgery_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            db.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    boolean CheckInPatientValidation() {
        boolean ret = true;


        if (this.edittextInaptientchartTime.getText().toString().length() == 0) {
            Toast.makeText(this, "please select time", Toast.LENGTH_LONG).show();
            ret = false;
        }
        if (this.edittextInaptientchartBpsytolic.getText().length() == 0 && this.edittextInaptientchartBpdiastolic.getText().length() == 0
                && this.edittextInaptientchartPulseperminute.getText().toString().length() == 0 && this.edittextInaptientchartResp.getText().toString().length() == 0) {
            Toast.makeText(this, "At least input one numeric value", Toast.LENGTH_LONG).show();
            ret = false;
        }
        if (this.spinnerInaptientchartTemperature.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Temperature", Toast.LENGTH_LONG).show();
            ret = false;
        }


        return ret;
    }


    //region InpatientChart
    @OnClick(id.button_inaptientchart_clear)
    void onButtonInaptientchartClearClick() {
        this.edittextInaptientchartBpsytolic.setText("");
        this.edittextInaptientchartBpdiastolic.setText("");
        this.edittextInaptientchartPulseperminute.setText("");
        this.spinnerInaptientchartTemperature.setSelection(0);
        this.edittextInaptientchartResp.setText("");
        this.edittextInaptientchartSpo2.setText("");
        this.edittextInaptientchartNursingInstruction.setText("");
        this.edittextInaptientchartDrugorder.setText("");
        this.edittextInaptientchartOral.setText("");
        this.edittextInaptientchartFluids.setText("");
        this.edittextInaptientchartRvles.setText("");
        this.edittextInaptientchartMotion.setText("");
        this.edittextInaptientchartUrine.setText("");
        this.edittextInaptientchartTime.setText("");
        this.edittextInaptientchartDate.setText("");
    }


    @OnClick(id.button_inaptientchart_save)
    void onButtonInaptientchartSaveClick() {
        if (this.CheckInPatientValidation()) {


            this.SaveLocal(1);


        }

    }


    @OnClick(id.button_inaptientchart_hide)
    void onButtonInaptientchartHideClick() {
        try {
            this.toggleSectionInput(this.findViewById(id.arrow_inpatient_chart), this.inpatientchart_primary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion


    //region Diabetic Chart
    @OnClick(id.button_diabetichart_hide)
    void onButtonDiabetichartHideClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_diabetic_chart), this.diabetic_primary);
    }

    @OnClick(id.button_diabetichart_clear)
    void onButtonDiabetichartClearClick() {
        try {
            this.edittextDiabetichartBloodSugar.setText("");
            this.edittextDiabetichartLente.setText("");
            this.edittextDiabetichartInsulinPlain.setText("");
            this.edittextDiabetichartBloodSugar.setText("");
            this.edittextDiabetichartKetoneBodies.setText("");
            this.edittextDiabetichartDate.setText("");
            this.edittextDiabetichartTime.setText("");
            this.edittextDiabetichartSpecialiInstruction.setText("");
            this.autoCompleteTextViewDiabetichartUrineSugar.setText("");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(id.button_diabetichart_save)
    void onButtonDiabetichartSaveClick() {
        if (this.CheckDiabeticChartValidation()) {
            this.SaveLocal(2);
        }
    }

    private boolean CheckDiabeticChartValidation() {
        boolean ret = true;
        if (this.edittextDiabetichartTime.getText().length() == 0) {
            Toast.makeText(this, "please select time", Toast.LENGTH_LONG).show();
            ret = false;
        }

        if (this.autoCompleteTextViewDiabetichartUrineSugar.getText().toString().length() == 0 && this.edittextDiabetichartLente.getText().toString().length() == 0 && this.edittextDiabetichartInsulinPlain.getText().toString().length() == 0
                && this.edittextDiabetichartBloodSugar.getText().toString().length() == 0 && this.edittextDiabetichartKetoneBodies.getText().toString().length() == 0) {

            Toast.makeText(this, "At least input one numeric value", Toast.LENGTH_LONG).show();
            ret = false;

        }

        return ret;

    }
    //endregion


    //region Temperature Chart
    @OnClick(id.button_temperaturechart_hide)
    void onButtonTemperaturechartHideClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_temperature_chart), this.temperature_primary);
    }


    @OnClick(id.button_temperaturechart_clear)
    void onButtonTemperaturechartClearClick() {

        try {
            this.edittextTemperaturechartDate.setText("");
            this.edittextTemperaturechartTime.setText("");
            this.edittextTemperaturechartVisitSummary.setText("");
            this.spinnerTemperaturechartSelecttemperature.setSelection(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(id.button_temperaturechart_save)
    void onButtonTemperaturechartSaveClick() {
        if (this.CheckTemperatureChartValidation()) {
            this.SaveLocal(3);
        }
    }

    private boolean CheckTemperatureChartValidation() {
        boolean ret = true;
        if (this.edittextTemperaturechartTime.getText().length() == 0) {
            Toast.makeText(this, "please select time", Toast.LENGTH_LONG).show();
            ret = false;
        }
        if (this.spinnerTemperaturechartSelecttemperature.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "please select temperature", Toast.LENGTH_LONG).show();
            ret = false;
        }
        return ret;
    }
    //endregion


    //region Surgery
    @OnClick(id.button_surgery_hide)
    void onButtonSurgeryHideClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_surgery_record), this.surgery_primary);
    }

    @OnClick(id.button_surgery_clear)
    void onButtonSurgeryClearClick() {
        try {
            this.edittextSurgeryDate.setText("");
            this.edittextSurgeryPreoperativeDiagnosis.setText("");
            this.editextSurgeryOperativenotes.setText("");
            this.edittextSurgeryPosition.setText("");
            this.edittextSurgeryClosure.setText("");
            this.edittextSurgeryPostoperativeDiagnosis.setText("");
            this.edittextSurgerySurgeon.setText("");
            this.edittextSurgeryAnaesthetist.setText("");
            this.edittextSurgeryAsst.setText("");
            this.edittextSurgeryBloodLoss.setText("");
            this.edittextSurgeryHistoPathological.setText("");
            this.edittextSurgeryPostOpInstruction.setText("");

            this.edittextSurgeryProcedure.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(id.button_surgery_save)
    void onButtonSurgerySaveClick() {
        this.SaveLocal(4);
    }
    //endregion


    //region Medical Case Record
    @OnClick(id.button_medicalcase_add)
    void onButtonMedicalcaseAddClick() {


        if (this.edittextMedicalcaseDate.getText().toString().length() == 0) {
            this.edittextMedicalcaseDate.setFocusable(true);
            this.edittextMedicalcaseDate.setError("Enter");
        } else if (this.edittextMedicalcaseTime.getText().toString().length() == 0) {
            this.edittextMedicalcaseTime.setFocusable(true);
            this.edittextMedicalcaseTime.setError("Enter");

        } else if (this.edittextMedicalcaseClinicalnotes.getText().toString().length() == 0) {
            this.edittextMedicalcaseClinicalnotes.setFocusable(true);
            this.edittextMedicalcaseClinicalnotes.setError("Enter");
        } else if (this.edittextMedicalcaseTreatementAndDiet.getText().toString().length() == 0) {
            this.edittextMedicalcaseTreatementAndDiet.setFocusable(true);
            this.edittextMedicalcaseTreatementAndDiet.setError("Enter");
        } else {

            this.medicalCaseRecords.add(new MedicalCaseRecords(this.edittextMedicalcaseDate.getText().toString(), this.edittextMedicalcaseTime.getText().toString(), this.edittextMedicalcaseClinicalnotes.getText().toString(), this.edittextMedicalcaseTreatementAndDiet.getText().toString()));

            this.edittextMedicalcaseDate.setText("");
            this.edittextMedicalcaseClinicalnotes.setText("");
            this.edittextMedicalcaseTreatementAndDiet.setText("");
            this.edittextMedicalcaseTime.setText("");

            this.adapter = new KDMCRecyclerAdapter<>(this.medicalCaseRecords, layout.each_medical_records)
                    .setRowItemView(new AdapterView() {
                        @Override
                        public Object setAdapterView(ViewGroup parent, int viewType, int layoutId) {
                            return new Inpatient_Inputs.MedicalCaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
                        }

                        @Override
                        public void onBindView(Object holder, int position, Object data, List<Object> dataList) {

                            Inpatient_Inputs.MedicalCaseViewHolder viewholder = (Inpatient_Inputs.MedicalCaseViewHolder) holder;
                            MedicalCaseRecords value = (MedicalCaseRecords) data;

                            viewholder.clinicalNotesValue.setText(value.getClinical_notes());
                            viewholder.dateMc.setText(value.getDate());
                            viewholder.treatmentDietValue.setText(value.getTreatment_diet());

                            viewholder.delete.setOnClickListener(view ->
                                    new CustomKDMCDialog(view.getContext())
                                            .setLayoutColor(color.red_400)
                                            .setImage(drawable.ic_delete_forever_black_24dp)
                                            .setTitle("Remove")
                                            .setDescription("Do you want to Remove?")
                                            .setPossitiveButtonTitle("YES")
                                            .setNegativeButtonTitle("NO")
                                            .setOnPossitiveListener(() -> {
                                                Inpatient_Inputs.this.adapter.delete(position);
                                                Inpatient_Inputs.this.medicalCaseRecords.remove(position);
                                            }));
                        }
                    });

            this.recyclerView.setAdapter(this.adapter);
        }

    }


    @OnClick(id.button_medicalcase_hide)
    void onButtonMedicalcaseHideClick() {
        this.toggleSectionInput(this.findViewById(id.arrow_medicalcase_record), this.medicalcase_primary);
    }


    @OnClick(id.button_medicalcase_clear)
    void onButtonMedicalcaseClearClick() {
        this.edittextMedicalcaseDate.setText("");
        this.edittextMedicalcaseClinicalnotes.setText("");
        this.edittextMedicalcaseTreatementAndDiet.setText("");
        this.edittextMedicalcaseTime.setText("");
        this.spinnerMedicalcaseRecordUndercareof.setSelection(0);
        this.edittextMedicalcaseRecordStudent.setText("");
        this.autocompleteMedicalcaseProvisionalDiagnosis.setText("");
        this.edittextMedicalcaseFinalDiagnosis.setText("");
        this.spinnerMedicalcaseSelectResult.setSelection(0);

        try {
            //first clear the recycler view so items are not populated twice
            for (int i1 = 0; i1 < this.adapter.getSize(); i1++) {
                this.adapter.delete(i1);
            }
            this.adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick(id.button_medicalcase_save)
    void onButtonMedicalcaseSaveClick() {

        SQLiteDatabase db = BaseConfig.GetDb();//);

        if (this.spinnerMedicalcaseRecordUndercareof.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please enter Please enter Under care of....", Toast.LENGTH_LONG).show();
            this.spinnerMedicalcaseRecordUndercareof.setFocusable(true);
        } else if (this.edittextMedicalcaseRecordStudent.getText().toString().isEmpty()) {
            this.edittextMedicalcaseRecordStudent.setError("Please enter Student name....");
            this.edittextMedicalcaseRecordStudent.setFocusable(true);
        } else if (this.autocompleteMedicalcaseProvisionalDiagnosis.getText().toString().isEmpty()) {
            this.autocompleteMedicalcaseProvisionalDiagnosis.setError("Please enter  Provisional Diagnosis....");
            this.autocompleteMedicalcaseProvisionalDiagnosis.setFocusable(true);
        } else if (this.edittextMedicalcaseFinalDiagnosis.getText().toString().isEmpty()) {
            this.edittextMedicalcaseFinalDiagnosis.setError("Please enter  Final Diagnosis....");
            this.edittextMedicalcaseFinalDiagnosis.setFocusable(true);
        } else if (this.medicalCaseRecords.size() == 0) {

            Toast.makeText(this, "Please add medical case record item using Plus(+) button", Toast.LENGTH_LONG).show();

        } else {
            String Datetime;

            for (int i = 0; i <= this.medicalCaseRecords.size() - 1; i++) {

                String dateMc = this.medicalCaseRecords.get(i).getDate();
                String clinicalNotes = this.medicalCaseRecords.get(i).getClinical_notes();
                String treatmentDiet = this.medicalCaseRecords.get(i).getTreatment_diet();


                // TODO: 3/14/2017 Tochange DropDown Value Remove AM/PM

                Datetime = this.medicalCaseRecords.get(i).getTime();


                ContentValues cv = new ContentValues();

                cv.put("ActDate", BaseConfig.DeviceDate());
                cv.put("pat_id", this.PATIENT_ID);
                cv.put("doc_id", BaseConfig.doctorId);
                cv.put("result", this.spinnerMedicalcaseSelectResult.getSelectedItem().toString());
                cv.put("under_care_of", this.spinnerMedicalcaseRecordUndercareof.getSelectedItem().toString());
                cv.put("under_care_name", this.edittextMedicalcaseRecordStudent.getText().toString());
                cv.put("date", Datetime);
                cv.put("referred_by", BaseConfig.doctorname);
                cv.put("clinical_notes", clinicalNotes);
                cv.put("treatment_diet", treatmentDiet);
                cv.put("provisional_diag", this.autocompleteMedicalcaseProvisionalDiagnosis.getText().toString());
                cv.put("final_diag", this.edittextMedicalcaseFinalDiagnosis.getText().toString());
                cv.put("IsUpdate", "0");
                cv.put("IsActive", "1");
                cv.put("HID", BaseConfig.HID);
                db.insert("InPatient_MedicalCaseRecords", null, cv);


                this.showSuccess();

                try {
                    this.edittextMedicalcaseDate.setText("");
                    this.edittextMedicalcaseClinicalnotes.setText("");
                    this.edittextMedicalcaseTreatementAndDiet.setText("");
                    this.edittextMedicalcaseTime.setText("");
                    this.spinnerMedicalcaseRecordUndercareof.setSelection(0);
                    this.edittextMedicalcaseRecordStudent.setText("");
                    this.autocompleteMedicalcaseProvisionalDiagnosis.setText("");
                    this.edittextMedicalcaseFinalDiagnosis.setText("");
                    this.spinnerMedicalcaseSelectResult.setSelection(0);

                    try {
                        this.medicalCaseRecords = new ArrayList<>();
                        //first clear the recycler view so items are not populated twice
                        for (int i1 = 0; i1 < this.adapter.getSize(); i1++) {
                            this.adapter.delete(i1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    this.toggleSectionInput(this.findViewById(id.arrow_medicalcase_record), this.medicalcase_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
        db.close();
    }

    private void showSuccess() {
        new CustomKDMCDialog(this)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_success_done)
                .setTitle(getResources().getString(string.information))
                .setDescription("Successfully Inserted")
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle("OK");
    }
    //endregion


    // #######################################################################################################
    public String CheckEdittextIsEmpty(AutoCompleteTextView autotxt) {
        String chartvaluse = "0";

        if (!autotxt.getText().toString().equalsIgnoreCase("")) {

            chartvaluse = autotxt.getText().toString();

        }
        return chartvaluse;
    }

    public String CheckEdittextIsEmpty(EditText edtxt) {
        String chartvaluse = "0";

        if (!edtxt.getText().toString().equalsIgnoreCase("")) {

            chartvaluse = edtxt.getText().toString();

        }

        return chartvaluse;
    }

    public String CheckEdittextIsEmpty(Spinner edtxt) {
        String chartvaluse = "0";

        if (edtxt.getSelectedItemPosition() != 0) {

            chartvaluse = edtxt.getSelectedItem().toString();

        }

        return chartvaluse;
    }
    // #######################################################################################################
}