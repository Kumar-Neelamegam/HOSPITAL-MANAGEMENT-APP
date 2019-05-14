package kdmc_kumar.Core_Modules;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.CaseNotes_Modules.CaseNotes;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

public class Patient_Registration extends AppCompatActivity {

    //Declaration
    @BindView(id.patient_parent_layout)
    CoordinatorLayout patientParentLayout;
    @BindView(id.patientregistration_nesetedscrollview)
    NestedScrollView patientregistrationNesetedscrollview;
    @BindView(id.imgvw_patientphoto)
    ImageView imgvwPatientphoto;
    @BindView(id.imgvw_camera)
    ImageView imgvwCamera;
    @BindView(id.imgvw_browse)
    ImageView imgvwBrowse;
    @BindView(id.upperlayout)
    LinearLayout upperlayout;
    @BindView(id.textvw_patient_name)
    TextView textvwPatientName;
    @BindView(id.edt_patient_name)
    EditText edtPatientName;
    @BindView(id.textvw_age)
    TextView textvwAge;
    @BindView(id.edt_age)
    EditText edtAge;
    @BindView(id.textvw_gender)
    TextView textvwGender;
    @BindView(id.spinner_gender)
    Spinner spinnerGender;
    @BindView(id.textvw_country)
    TextView textvwCountry;
    @BindView(id.autocomplete_country)
    AutoCompleteTextView autocompleteCountry;
    @BindView(id.textvw_state)
    TextView textvwState;
    @BindView(id.autocomplete_state)
    AutoCompleteTextView autocompleteState;
    @BindView(id.textvw_city)
    TextView textvwCity;
    @BindView(id.autocomplete_city)
    AutoCompleteTextView autocompleteCity;
    @BindView(id.textvw_willing_smsalert)
    TextView textvwWillingSmsalert;
    @BindView(id.spinner_willing_receive)
    LabeledSwitch spinnerWillingReceive;
    @BindView(id.textvw_fee_exemption)
    TextView textvwFeeExemption;
    @BindView(id.spinner_fee_exemption)
    LabeledSwitch spinnerFeeExemption;
    @BindView(id.textvw_address1)
    TextView textvwAddress1;
    @BindView(id.edt_address1)
    EditText edtAddress1;
    @BindView(id.spinner_bloodgroup)
    Spinner spinnerBloodgroup;
    @BindView(id.spinner_martial_status)
    Spinner spinnerMartialStatus;
    @BindView(id.spinner_prefix)
    Spinner spinnerPrefix;
    @BindView(id.txtvw_fathername)
    TextView txtvw_FatherName;
    @BindView(id.edt_fathername)
    EditText edtFathername;
    @BindView(id.edt_dateofbirth)
    EditText edtDateofbirth;
    @BindView(id.edt_caste)
    EditText edtCaste;
    @BindView(id.edt_income)
    EditText edtIncome;
    @BindView(id.edt_occupation)
    EditText edtOccupation;
    @BindView(id.edt_address2)
    EditText edtAddress2;
    @BindView(id.edt_pincode)
    EditText edtPincode;
    @BindView(id.edt_email)
    EditText edtEmail;
    @BindView(id.edt_mobilenumber2)
    EditText edtMobilenumber2;
    @BindView(id.edt_caretaker)
    EditText edtCaretaker;
    @BindView(id.edt_caretaker_number)
    EditText edtCaretakerNumber;
    @BindView(id.edt_relationship)
    AutoCompleteTextView edtRelationship;
    @BindView(id.preferrednumber)
    RadioGroup edtPreferredNumber;
    @BindView(id.rbtn_caretaker)
    RadioButton rbtn_caretaker;
    @BindView(id.rbtn_self)
    RadioButton rbtn_self;
    @BindView(id.toggle_donate_blood)
    LabeledSwitch toggleDonateBlood;
    @BindView(id.toggle_donate_eyes)
    LabeledSwitch toggleDonateEyes;
    @BindView(id.edt_refdoc_name)
    EditText edtRefdocName;
    @BindView(id.edt_ref_doctor_mobile_no)
    EditText edtRefDoctorMobileNo;
    @BindView(id.edt_policy_name)
    EditText edtPolicyName;
    @BindView(id.edt_nameof_insurance)
    EditText edtNameofInsurance;
    @BindView(id.edt_amount_insured)
    EditText edtAmountInsured;
    @BindView(id.edt_valid_year)
    EditText edtValidYear;
    @BindView(id.edt_authorized_hospital)
    EditText edtAuthorizedHospital;
    @BindView(id.edt_aadhardcard)
    EditText edtAadhardcard;
    @BindView(id.edt_bpl_card)
    EditText edtBplCard;
    @BindView(id.button_cancel)
    Button buttonCancel;
    @BindView(id.button_submit)
    Button buttonSubmit;


    @BindView(id.layout_mobile1)
    LinearLayout layout_mobile1;

    @BindView(id.layout_fee_exemption)
    LinearLayout layout_fee_exemption_category;


    @BindView(id.textvw_mobile1)
    TextView TxtvwMobile1;
    @BindView(id.textvw_fee_exemption_category)
    TextView TxtvwFeeExemption;

    @BindView(id.edt_mobile1)
    EditText edtMobile1;
    @BindView(id.spinner_fee_category)
    Spinner spinnerFeeExemption_category;

    @BindView(id.toolbar)
    Toolbar toolbar;
    @BindView(id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title)
    TextView toolbarTitle;
    @BindView(id.toolbar_exit)
    AppCompatImageView toolbarExit;

    String QUERY_COUNTRY = "select Country as dvalue FROM Mstr_Country";
    String PATIENT_PHOTO_PATH = "";
    String str_PatientId = "";
    private final int DATE_DIALOG_ID = 123;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.kdmc_patient_registration);

        try {
            this.GETINITIALIZE();

            this.CONTROLLISTENERS();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CONTROLLISTENERS() {

        //---------Autocomplete------
        this.autocompleteCountry.setThreshold(1);
        this.autocompleteState.setThreshold(1);
        this.autocompleteCity.setThreshold(1);
        this.edtRelationship.setThreshold(1);

        //Load Country
        BaseConfig.AutocompleteTextChange(this.autocompleteCountry, this.QUERY_COUNTRY);

        //Load relationship
        String QUERY_RELATIONSHIP = "select Relationship as dvalue from Mstr_Relationship";
        BaseConfig.AutocompleteTextChange(this.edtRelationship, QUERY_RELATIONSHIP);

        //-------Spinners---------
        //exemption category
        String Query_FeeExemption = "select FeeExemption as dvalue from Mstr_FeeExemptions where IsActive=1";
        BaseConfig.LoadValuesSpinner(this.spinnerFeeExemption_category, this, Query_FeeExemption, "Select");

        //Bloodgroup
        String Query_Bloodgroup = "select Bloodgroup as dvalue from Mstr_BloodGroup where IsActive=1";
        BaseConfig.LoadValuesSpinner(this.spinnerBloodgroup, this, Query_Bloodgroup, "Select");


        //Load Gender
        String Query_Gender = "select Gender as dvalue from Mstr_Gender where IsActive=1";
        BaseConfig.LoadValuesSpinner(this.spinnerGender, this, Query_Gender, "Select");

        //Load Martial status
        String Query_MaritalStatus = "select Marital_Status as dvalue from Mstr_MaritalStatus where IsActive=1";
        BaseConfig.LoadValuesSpinner(this.spinnerMartialStatus, this, Query_MaritalStatus, "Select");

        //Load prefix
        String Query_Prefix = "select Prefix as dvalue from Mstr_Prefix where IsActive=1";
        BaseConfig.LoadValuesSpinner(this.spinnerPrefix, this, Query_Prefix, "Select");

        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarBack.setOnClickListener(view -> this.LoadBack());

        this.autocompleteCountry.setOnItemClickListener((parent, view, position, id) -> {

            //Load State
            String Country_ID = BaseConfig.GetValues("select ServerId as ret_values from Mstr_Country where Country='" + this.autocompleteCountry.getText() + "' and IsActive=1");
            String QUERY_STATE = "select State as dvalue from Mstr_State where CountryID='" + Country_ID + "' and IsActive=1";
            BaseConfig.AutocompleteTextChange(this.autocompleteState, QUERY_STATE);

        });

        this.autocompleteState.setOnItemClickListener((parent, view, position, id) -> {

            //Load City
            String Country_ID = BaseConfig.GetValues("select ServerId as ret_values from Mstr_Country where Country='" + this.autocompleteCountry.getText() + "' and IsActive=1");
            String State_ID = BaseConfig.GetValues("select ServerId as ret_values from Mstr_State where State='" + this.autocompleteState.getText() + "' and IsActive=1");
            String QUERY_CITY = "select City as dvalue from Mstr_City where CountryID='" + Country_ID + "' and StateID='" + State_ID + "' and IsActive=1";
            BaseConfig.AutocompleteTextChange(this.autocompleteCity, QUERY_CITY);


        });


        this.imgvwCamera.setOnClickListener(view -> this.pickImageFromSource(Sources.CAMERA));
        this.imgvwBrowse.setOnClickListener(view -> this.pickImageFromSource(Sources.GALLERY));


        this.buttonCancel.setOnClickListener(v -> this.LoadBack());

        this.buttonSubmit.setOnClickListener(v -> this.Savelocal());

        this.spinnerWillingReceive.setOnToggledListener((labeledSwitch, isOn) -> {
            if (isOn) {
                this.layout_mobile1.setVisibility(View.VISIBLE);
            } else {
                if (this.spinnerFeeExemption.isOn())
                    this.layout_mobile1.setVisibility(View.INVISIBLE);
                else
                    this.layout_mobile1.setVisibility(View.GONE);
            }
        });

        this.spinnerFeeExemption.setOnToggledListener((labeledSwitch, isOn) -> {

            if (isOn) {
                this.layout_fee_exemption_category.setVisibility(View.VISIBLE);
            } else {
                if (this.spinnerWillingReceive.isOn())
                    this.layout_fee_exemption_category.setVisibility(View.INVISIBLE);
                else
                    this.layout_fee_exemption_category.setVisibility(View.GONE);
            }

        });

        this.spinnerMartialStatus.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (Patient_Registration.this.spinnerMartialStatus.getSelectedItemPosition() > 0) {
                    String str = Patient_Registration.this.spinnerMartialStatus.getSelectedItem().toString();
                    if (str.equalsIgnoreCase("Married")) {
                        //set title as spouse name
                        Patient_Registration.this.txtvw_FatherName.setText("Spouse Name");
                    } else {
                        //set title as father name
                        Patient_Registration.this.txtvw_FatherName.setText("Father Name");
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        this.edtDateofbirth.setFocusableInTouchMode(false);
        Calendar myCalendar = Calendar.getInstance();


        OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.ENGLISH);
            this.edtDateofbirth.setText(sdf.format(myCalendar.getTime()));


            String AgeStr = this.getAge(year, monthOfYear, dayOfMonth);

            this.edtAge.setText(AgeStr);



        };

        this.edtDateofbirth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Patient_Registration.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


    }




    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    @SuppressLint("CheckResult")
    private void pickImageFromSource(Sources source) {
        RxImagePicker.with(this).requestImage(source)
                .flatMap(uri -> {


                    return RxImageConverters.uriToFile(this, uri, this.createTempFile());

                    //return RxImageConverters.uriToBitmap(Patient_Registration.this, uri);


                })
                .subscribe(this::onImagePicked);//Toast.makeText(Patient_Registration.this, String.format("Error: %s", throwable), Toast.LENGTH_LONG).show());
    }

    private void onImagePicked(Object result) {

        this.PATIENT_PHOTO_PATH = result.toString();

        // Toast.makeText(this, String.format("Result: %s", result), Toast.LENGTH_LONG).show();

        if (result instanceof Bitmap) {
            this.imgvwPatientphoto.setImageBitmap((Bitmap) result);
        } else {
            Glide.with(this)
                    .load(result) // works for File or Uri
                    .into(this.imgvwPatientphoto);
        }

    }

    private File createTempFile() {

        return new File(BaseConfig.Patient_Photo_Location, System.currentTimeMillis() + "_image.jpeg");

    }

    private void Savelocal() {

        if (this.CheckValidation()) {

            this.SUBMIT_FORM();

        } else {
            Toast.makeText(this, string.missing, Toast.LENGTH_LONG).show();

        }

    }

    public void SUBMIT_FORM() {

        try {
            String str_PatientName = "", str_Age = "", str_Gender = "", str_Country = "", str_State = "", str_City = "", str_Willingtosms = "", str_FeeExemption = "", str_Mobile1 = "", str_FeeExemption_Category = "",
                    str_Address1 = "", str_BloodGroup = "", str_MaritalStatus = "", str_Prefix = "", str_FatherName = "", str_DateOfBirth = "", str_Caste = "", str_Income = "", str_Occupation = "",

                    str_Address2 = "", str_Pincode = "", str_Email = "", str_MobileNo2 = "", str_CareTakerName = "", str_ContactNumber = "", str_Relationship = "", str_PreferredNumber = "", str_Willingtodonateblood = "",
                    str_Willingtodonateeyes = "", str_Referreddoctor = "", str_ReferredMobileNo = "", str_PolicyName = "", str_Nameofinsurance = "", str_AmountInsured = "", str_ValidTill = "", str_Authroized = "",
                    str_Aadhardcard = "", str_BPLCardNo = "", str_Patient_Prefix = "";

            String str_PhotoURL = "", str_SpouseName = "";

            String str_PatientPin = "", str_PtientNamePrefix = "";

            str_PatientName = BaseConfig.GetWidgetOperations(this.edtPatientName, 1);
            str_Age = BaseConfig.GetWidgetOperations(this.edtAge, 1);
            str_Gender = BaseConfig.GetWidgetOperations(this.spinnerGender, 1);
            str_Country = BaseConfig.GetWidgetOperations(this.autocompleteCountry, 1);
            str_State = BaseConfig.GetWidgetOperations(this.autocompleteState, 1);
            str_City = BaseConfig.GetWidgetOperations(this.autocompleteCity, 1);
            str_Willingtosms = BaseConfig.GetWidgetOperations(this.spinnerWillingReceive, 1);
            str_FeeExemption = this.spinnerFeeExemption.isOn()? "Yes":"No";//BaseConfig.GetWidgetOperations(spinnerFeeExemption, 1);
            str_FeeExemption = str_FeeExemption.equalsIgnoreCase("Yes") ? "1" : "0";
            str_Mobile1 = BaseConfig.GetWidgetOperations(this.edtMobile1, 1);
            str_FeeExemption_Category = BaseConfig.GetWidgetOperations(this.spinnerFeeExemption_category, 1);
            str_Address1 = BaseConfig.GetWidgetOperations(this.edtAddress1, 1);
            str_BloodGroup = BaseConfig.GetWidgetOperations(this.spinnerBloodgroup, 1);
            str_MaritalStatus = BaseConfig.GetWidgetOperations(this.spinnerMartialStatus, 1);
            str_Prefix = BaseConfig.GetWidgetOperations(this.spinnerPrefix, 1);

            str_DateOfBirth = BaseConfig.GetWidgetOperations(this.edtDateofbirth, 1);
            str_Caste = BaseConfig.GetWidgetOperations(this.edtCaste, 1);
            str_Income = BaseConfig.GetWidgetOperations(this.edtIncome, 1);
            str_Occupation = BaseConfig.GetWidgetOperations(this.edtOccupation, 1);
            str_Address2 = BaseConfig.GetWidgetOperations(this.edtAddress2, 1);
            str_Pincode = BaseConfig.GetWidgetOperations(this.edtPincode, 1);
            str_Email = BaseConfig.GetWidgetOperations(this.edtEmail, 1);
            str_MobileNo2 = BaseConfig.GetWidgetOperations(this.edtMobilenumber2, 1);
            str_CareTakerName = BaseConfig.GetWidgetOperations(this.edtCaretaker, 1);
            str_ContactNumber = BaseConfig.GetWidgetOperations(this.edtCaretakerNumber, 1);
            str_Relationship = BaseConfig.GetWidgetOperations(this.edtRelationship, 1);
            str_PreferredNumber = BaseConfig.GetWidgetOperations(this.edtPreferredNumber, 1);
            str_Willingtodonateblood = BaseConfig.GetWidgetOperations(this.toggleDonateBlood, 1);
            str_Willingtodonateeyes = BaseConfig.GetWidgetOperations(this.toggleDonateEyes, 1);
            str_Referreddoctor = BaseConfig.GetWidgetOperations(this.edtRefdocName, 1);
            str_ReferredMobileNo = BaseConfig.GetWidgetOperations(this.edtRefDoctorMobileNo, 1);
            str_PolicyName = BaseConfig.GetWidgetOperations(this.edtPolicyName, 1);
            str_Nameofinsurance = BaseConfig.GetWidgetOperations(this.edtNameofInsurance, 1);
            str_AmountInsured = BaseConfig.GetWidgetOperations(this.edtAmountInsured, 1);
            str_ValidTill = BaseConfig.GetWidgetOperations(this.edtValidYear, 1);
            str_Authroized = BaseConfig.GetWidgetOperations(this.edtAuthorizedHospital, 1);
            str_Aadhardcard = BaseConfig.GetWidgetOperations(this.edtAadhardcard, 1);
            str_BPLCardNo = BaseConfig.GetWidgetOperations(this.edtBplCard, 1);
            str_PhotoURL = this.PATIENT_PHOTO_PATH;

            String DefaultPhotoPath = BaseConfig.AppDBFolderName + "/male_avatar.jpg";

            str_PhotoURL = str_PhotoURL.equalsIgnoreCase("") ? DefaultPhotoPath : str_PhotoURL;

            str_Patient_Prefix = this.GetGenderPrefix(str_Gender, str_MaritalStatus, str_Patient_Prefix);

            if (str_MaritalStatus.equalsIgnoreCase("Married")) {
                str_SpouseName = BaseConfig.GetWidgetOperations(this.edtFathername, 1);
            } else if (str_MaritalStatus.equalsIgnoreCase("Unmarried")) {
                str_FatherName = BaseConfig.GetWidgetOperations(this.edtFathername, 1);
            }else
            {
                str_FatherName = BaseConfig.GetWidgetOperations(this.edtFathername, 1);
            }

            str_PatientPin = BaseConfig.GeneratePatientLoginPin();

            str_PtientNamePrefix = str_Patient_Prefix+" . "+str_PatientName;

            this.str_PatientId = BaseConfig.GeneratePatientRegistrationID();

            SQLiteDatabase db = BaseConfig.GetDb();
            ContentValues value = new ContentValues();
            value.put("PatientName", str_PtientNamePrefix);
            value.put("Patient_Prefix", str_Patient_Prefix);
            value.put("PatientId", this.str_PatientId);
            value.put("Age", str_Age);
            value.put("Gender", str_Gender);
            value.put("Country", str_Country);
            value.put("State", str_State);
            value.put("City", str_City);
            value.put("WillingtoReceive", str_Willingtosms);
            value.put("FeeExemption", str_FeeExemption);
            value.put("Mobile1", str_Mobile1);
            value.put("FeeExemptionCategory", str_FeeExemption_Category);
            value.put("Address1", str_Address1);
            value.put("Bloodgroup", str_BloodGroup);
            value.put("Marital", str_MaritalStatus);
            value.put("RelationShipPrefix", str_Prefix);
            value.put("FatherName", str_FatherName);
            value.put("DateOfBirth", str_DateOfBirth);
            value.put("Caste", str_Caste);
            value.put("Income", str_Income);
            value.put("Occupation", str_Occupation);
            value.put("Address2", str_Address2);
            value.put("Pincode", str_Pincode);
            value.put("Email", str_Email);
            value.put("MobileNo2", str_MobileNo2);
            value.put("Caretaker", str_CareTakerName);
            value.put("CaretakerNumber", str_ContactNumber);
            value.put("Relationship", str_Relationship);
            value.put("PreferredNumber", str_PreferredNumber);
            value.put("WillingtoDonateBlood", str_Willingtodonateblood);
            value.put("WillingtoDonateEyes", str_Willingtodonateeyes);
            value.put("ReferredDoctorName", str_Referreddoctor);
            value.put("ReferredDoctorMobileNo", str_ReferredMobileNo);
            value.put("PolicyName", str_PolicyName);
            value.put("NameOfInsuranceCompany", str_Nameofinsurance);
            value.put("AmountInsured", str_AmountInsured);
            value.put("ValidTill", str_ValidTill);
            value.put("AuthorizedHospitals", str_Authroized);
            value.put("AadhardCard", str_Aadhardcard);
            value.put("BPLCard", str_BPLCardNo);
            value.put("IsUpdate", 0);
            value.put("IsActive", 1);
            value.put("ServerId", 0);
            value.put("ActDate", BaseConfig.DeviceDate());
            value.put("Photo_Path", str_PhotoURL);
            value.put("SpouseName", str_SpouseName);
            value.put("PinPass", str_PatientPin);
            value.put("HID", BaseConfig.HID);
            value.put("DoctorId", BaseConfig.doctorId);
            value.put("NamePrefix", str_PtientNamePrefix);
            db.insert("Bind_Patient_Registration", null, value);


            str_FeeExemption = this.spinnerFeeExemption.isOn()? "Yes":"No";
            value = new ContentValues();
            value.put("Patid", this.str_PatientId);
            value.put("Docid", BaseConfig.doctorId);
            value.put("name", str_PtientNamePrefix);
            value.put("prefix", str_Patient_Prefix);
            value.put("patientname", str_PatientName);
            value.put("PC", str_PhotoURL);
            value.put("age", str_Age);
            value.put("gender", str_Gender);
            value.put("DOB", str_DateOfBirth);
            value.put("Country", str_Country);
            value.put("State", str_State);
            value.put("District", str_City);
            value.put("Address", str_Address1);
            value.put("pincode", str_Pincode);
            value.put("phone", str_Mobile1);
            value.put("PMH", str_PtientNamePrefix);
            value.put("IsActive", 1);
            value.put("city", str_City);
            value.put("altphone", str_MobileNo2);
            value.put("Actdate", BaseConfig.DeviceDate());
            value.put("Isupdate", 1);
            value.put("inshosp", str_Authroized);
            value.put("Address1", str_Address1+","+str_Address2);
            value.put("email", str_Email);
            value.put("caretaker", str_CareTakerName);
            value.put("crtknum", str_ContactNumber);
            value.put("relationship", str_Relationship);
            value.put("willingblood", str_Willingtodonateblood);
            value.put("willingeye", str_Willingtodonateeyes);
            value.put("PatientPin", str_PatientPin);
            value.put("bloodgroup", str_BloodGroup);
            value.put("policyname", str_PolicyName);
            value.put("insurancecompany", str_Nameofinsurance);
            value.put("amountinsured", str_AmountInsured);
            value.put("validity", str_ValidTill);
            value.put("enable_inpatient", 0);
            value.put("HospitalId", BaseConfig.HID);
            value.put("doc_refer_name", str_Referreddoctor);
            value.put("doc_refer_no", str_ReferredMobileNo);
            value.put("bplCardNo", str_BPLCardNo);
            value.put("AadharCardNo", str_Aadhardcard);
            value.put("caste", str_Caste);
            value.put("income", str_Income);
            value.put("referred_by", str_Referreddoctor);
            value.put("Occupation", str_Occupation);
            value.put("Fathername", str_FatherName);
            value.put("spouse", str_SpouseName);
            value.put("IsFeeExemp", str_FeeExemption);
            value.put("FeeExempCateg", str_FeeExemption_Category);

            db.insert("Patreg", null, value);


            //Insert as current patient list
            value = new ContentValues();
            value.put("date", BaseConfig.Device_OnlyDate2());
            value.put("patid", this.str_PatientId);
            value.put("docid", BaseConfig.doctorId);
            value.put("IsUpdate", 0);
            value.put("ServerId", 0);
            value.put("status", "true");
            value.put("closed", "0");
            value.put("HID", BaseConfig.HID);
            db.insert("current_patient_list", null, value);

            db.close();

            this.GotoClinicalInfo();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String GetGenderPrefix(String str_Gender, String str_MaritalStatus, String str_Patient_Prefix) {
        if (str_Gender.equalsIgnoreCase("Male")) {
            str_Patient_Prefix = "Mr";
        } else if (str_Gender.equalsIgnoreCase("Female")) {
            if (str_MaritalStatus.equalsIgnoreCase("Married")) {
                str_Patient_Prefix = "Mrs";
            } else {
                str_Patient_Prefix = "Ms";
            }
        } else if (str_Gender.equalsIgnoreCase("Baby")) {
            str_Patient_Prefix = "Baby";
        } else if (str_Gender.equalsIgnoreCase("Trans-gender")) {
            str_Patient_Prefix = "Tr";
        }
        return str_Patient_Prefix;
    }

    public void GotoClinicalInfo() {

        try {
            Toast.makeText(this, string.success_message_patient_registration, Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(this.getApplicationContext(), CaseNotes.class);
            intent.putExtra("CONTINUE_STATUS", "True");
            intent.putExtra("PASSING_PATIENT_ID", this.str_PatientId);
            CustomIntent.customType(this, 4);
            this.startActivity(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean CheckValidation() {

        boolean ret = true;

        if (!Validation1.isEmailAddressChk(this.edtEmail, false)) {
            ret = false;
        }

        if (this.edtAddress1.getText().length() == 0) {
            this.edtAddress1.requestFocus();
            this.edtAddress1.setError("Required");
            this.edtAddress1.setHint("Enter patient address");
            ret = false;
        }

        if (this.spinnerFeeExemption.isOn()) {
            if (this.spinnerFeeExemption_category.getSelectedItemPosition() == 0) {
                BaseConfig.CheckSpinner2(this.spinnerFeeExemption_category);
                ret = false;
            }
        }


        if (this.spinnerWillingReceive.isOn()) {
            if (this.edtMobile1.getText().length() == 0) {
                this.edtMobile1.requestFocus();
                this.edtMobile1.setError("Required");
                this.edtMobile1.setHint("Enter mobile no");
                ret = false;
            } else if (!Validation1.isMobileNumber(this.edtMobile1, false)) {
                ret = false;
            }

        }


        if (this.autocompleteCity.getText().length() == 0) {

            this.autocompleteCity.requestFocus();
            this.autocompleteCity.setError("Required");
            this.autocompleteCity.setHint("Enter city name");
            ret = false;
        }

        if (this.autocompleteState.getText().length() == 0) {
            this.autocompleteState.requestFocus();
            this.autocompleteState.setError("Required");
            this.autocompleteState.setHint("Enter state name");
            ret = false;
        }

        if (this.autocompleteCountry.getText().length() == 0) {
            this.autocompleteCountry.requestFocus();
            this.autocompleteCountry.setError("Required");
            this.autocompleteCountry.setHint("Enter country name");
            ret = false;
        }

        if (this.spinnerGender.getSelectedItemPosition() == 0) {
            BaseConfig.CheckSpinner2(this.spinnerGender);
            ret = false;
        }


        if (this.edtAge.getText().length() == 0) {
            this.edtAge.requestFocus();
            this.edtAge.setError("Required");
            this.edtAge.setHint("Enter age");
            ret = false;
        }

        int Age = 0;
        if (this.edtAge.getText().length() > 0) {
            Age = Integer.parseInt(String.valueOf(this.edtAge.getText()));
        }

        if (Integer.parseInt(String.valueOf(Age)) > 110) {
            this.edtAge.requestFocus();
            this.edtAge.setError("Age value should be within 110");
            this.edtAge.setHint("Enter valid age");
            ret = false;
        }

        if (this.edtPatientName.getText().toString().trim().length() == 0 && this.edtPatientName.getText().toString().trim().isEmpty()) {
            this.edtPatientName.requestFocus();
            this.edtPatientName.setError("Required");
            this.edtPatientName.setHint("Enter patient name");
            ret = false;
        }


        return ret;

    }


    private void GETINITIALIZE() {

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        BaseConfig.welcometoast = 0;

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.patientregistration)));

        this.setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

        BaseConfig.HighlightMandatory(this.getString(string.str_patient_name), this.textvwPatientName);
        BaseConfig.HighlightMandatory(this.getString(string.str_age), this.textvwAge);
        BaseConfig.HighlightMandatory(this.getString(string.gender), this.textvwGender);
        BaseConfig.HighlightMandatory(this.getString(string.str_country), this.textvwCountry);
        BaseConfig.HighlightMandatory(this.getString(string.str_state), this.textvwState);
        BaseConfig.HighlightMandatory(this.getString(string.str_city), this.textvwCity);
        BaseConfig.HighlightMandatory(this.getString(string.str_willingsms), this.textvwWillingSmsalert);
        BaseConfig.HighlightMandatory(this.getString(string.str_feeexemp), this.textvwFeeExemption);
        BaseConfig.HighlightMandatory(this.getString(string.str_address1), this.textvwAddress1);

        BaseConfig.HighlightMandatory(this.getString(string.mobile_1), this.TxtvwMobile1);
        BaseConfig.HighlightMandatory(this.getString(string.txt_fee_exemption_category), this.TxtvwFeeExemption);

        BaseConfig.ClearError(this.edtPatientName);
        BaseConfig.ClearError(this.edtAge);
        BaseConfig.ClearError(this.autocompleteCountry);
        BaseConfig.ClearError(this.autocompleteState);
        BaseConfig.ClearError(this.autocompleteCity);
        BaseConfig.ClearError(this.edtAddress1);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.LoadBack();
        System.gc();
    }


    public void LoadBack() {
       BaseConfig.globalStartIntent2(this, Dashboard_NavigationMenu.class, null);
    }


}//END
