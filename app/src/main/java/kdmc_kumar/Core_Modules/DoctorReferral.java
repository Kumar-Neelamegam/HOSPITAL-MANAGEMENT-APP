package kdmc_kumar.Core_Modules;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.ConnectionDetector;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Validation1;

import static kdmc_kumar.Core_Modules.BaseConfig.GetDb;
import static kdmc_kumar.Core_Modules.Investigations.convertListtoStringArray;

public class DoctorReferral extends AppCompatActivity implements TextWatcher {


    private static final String DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final int SIGNATURE_ACTIVITY = 1;
    private static final int DATE_DIALOG_ID = 1;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CODE = 6384;
    private static final String TAG = "FileChooserExampleActivity";
    private static int mcYear;
    private static int mcMonth;
    private static int mcDay;
    private static int mYear;
    private static int mMonth;
    private static int mDay;
    public static int putsign = 0;
    private static String tempDir;
    private final Context context = this;
    public SQLiteDatabase db;
    public int count = 1;
    public String current = null;
    protected ArrayList<CharSequence> selectedItems = new ArrayList<CharSequence>();
    protected ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    protected ArrayList<CharSequence> selectedoffense = new ArrayList<CharSequence>();
    @BindView(R.id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(R.id.nesetedscrollview_doctorreferral)
    NestedScrollView nesetedscrollviewDoctorreferral;
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
    @BindView(R.id.primary_referral_doctor)
    LinearLayout primaryReferralDoctor;
    @BindView(R.id.textview_doctorname)
    TextView textviewDoctorname;
    @BindView(R.id.autocomplete_doctorname)
    AutoCompleteTextView autocompleteDoctorname;
    @BindView(R.id.textview_specialization)
    TextView textviewSpecialization;
    @BindView(R.id.spinner_specialist)
    Spinner spinnerSpecialist;
    @BindView(R.id.textview_specialist)
    TextView textviewSpecialist;
    @BindView(R.id.textview_referredfrom_hospital)
    TextView textviewReferredfromHospital;
    @BindView(R.id.edttext_referredfrom_hospital)
    EditText edttextReferredfromHospital;
    @BindView(R.id.textview_referredto_hospital)
    TextView textviewReferredtoHospital;
    @BindView(R.id.autocomplete_referredto_hospital)
    AutoCompleteTextView autocompleteReferredtoHospital;
    @BindView(R.id.textview_clinicalname)
    TextView textviewClinicalname;
    @BindView(R.id.edittext_clinicalname)
    EditText edittextClinicalname;
    @BindView(R.id.textview_address)
    TextView textviewAddress;
    @BindView(R.id.edittext_address)
    EditText edittextAddress;
    @BindView(R.id.textview_emailid)
    TextView textviewEmailid;
    @BindView(R.id.edittext_emailid)
    EditText edittextEmailid;
    @BindView(R.id.textview_mobilenumber)
    TextView textviewMobilenumber;
    @BindView(R.id.edittext_mobilenumber)
    EditText edittextMobilenumber;
    @BindView(R.id.textview_remarks)
    TextView textviewRemarks;
    @BindView(R.id.edittext_remarks)
    EditText edittextRemarks;
    @BindView(R.id.textview_remarkscontent)
    TextView textviewRemarkscontent;
    @BindView(R.id.multiautocomplete_remarks_content)
    MultiAutoCompleteTextView multiautocompleteRemarksContent;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
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


    String base64;
    String simg;
    File mypath;
    CharSequence[] items;
    private final List<String> Loadlist0 = new ArrayList<>();
    private final List<String> Loadlist1 = new ArrayList<>();
    private final List<String> Loadlist2 = new ArrayList<>();
    Typeface tfavv;

    String addresss, subject, message, file_path;
    ConnectionDetector cd;

    private Uri URI = null;
    int columnindex;
    String patient_type;
    String str_DoctorName = "", str_MCIRegnno = "", str_PatientName = "", str_Gender = "", str_Age = "", str_Symptoms = "", str_provosionaldiagnosis = "", str_specialistdoctorreferal = "";
    String patid = "";
    String str_preferreddiagnosis, str_address, str_hospitalclinic = "";
    private TextView mDateDisplay;


    private ProgressDialog pDialog;
    @SuppressWarnings("unused")
    private String uniqueId;

    private String[] patientname;

    private final DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, 1);
    }

    @SuppressLint("UseValueOf")
    private static int calculateAge() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatterr = new SimpleDateFormat("dd", Locale.ENGLISH);
        String daynum = formatterr.format(currentDate.getTime());

        SimpleDateFormat formatterm = new SimpleDateFormat("yyyy",Locale.ENGLISH);
        String yrnum = formatterm.format(currentDate.getTime());

        android.text.format.DateFormat.format("EEEE", currentDate);
        String Monthname = (String) android.text.format.DateFormat.format("mm",
                currentDate);

        Integer currentYear = new Integer(yrnum);
        Integer currentMonth = new Integer(Monthname);
        Integer currentDay = new Integer(daynum);

        Integer dobMonth = mMonth;
        Integer dobDay = mDay;

        int age = currentYear - mYear;

        if ((dobMonth > currentMonth)
                || (currentMonth == dobMonth && dobDay > currentDay))
            age--;

        return age;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            setContentView(R.layout.kdmc_layout_doctorreferral);


            GETINITIALIZE();

            CONTROLLISTENERS();




        } catch (Exception e) {
            e.printStackTrace();
        }


    /*    clear_refdoc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                clinicname.setText("");
                dmobnum.setText("");
                daddr.setText("");
                specialist_text.setText("");
                refdocmail.setText("");

                refdoctorname.setText("");


                clinicname.setEnabled(true);
                dmobnum.setEnabled(true);
                daddr.setEnabled(true);
                refdocmail.setEnabled(true);

                specialist_text.setVisibility(View.GONE);


                splin.setVisibility(View.VISIBLE);


            }
        });
    }*/
    }


    private void GETINITIALIZE() {
        ButterKnife.bind(DoctorReferral.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);

        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

     //   toolbar.setBackgroundColor(Color.parseColor(DoctorReferral.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));


        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.title_doctor_referral)));


        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(DoctorReferral.this, null));


        toolbarBack.setOnClickListener(view -> LoadBack());


        BaseConfig.welcometoast = 0;

        BaseConfig.sndmailflag = 1;


        edttextReferredfromHospital.setText(BaseConfig.HOSPITALNAME);

        autocompleteReferredtoHospital.setThreshold(1);


        String first = getString(R.string.pat_name);
        String next = "<font color='#EE0000'><b>*</b></font>";
        txtvwTitlePatientname.setText(Html.fromHtml(first + next));


        String first1 = getString(R.string.odc_name);
        textviewDoctorname.setText(Html.fromHtml(first1));

        String first2t = getString(R.string.symptoms);
        String next2t = "<font color='#EE0000'><b>*</b></font>";
        txtvwTreatmentfor.setText(Html.fromHtml(first2t + next2t));

        String first2d = getString(R.string.prov_diag);
        String next2d = "<font color='#EE0000'><b>*</b></font>";
        txtvwDiagnosis.setText(Html.fromHtml(first2d + next2d));


        String first3d = getString(R.string.referred_from_hospital);
        String next3d = "<font color='#EE0000'><b>*</b></font>";
        textviewReferredfromHospital.setText(Html.fromHtml(first3d + next3d));

        String first4d = getString(R.string.referred_to_hospital);
        String next4d = "<font color='#EE0000'><b>*</b></font>";
        textviewReferredtoHospital.setText(Html.fromHtml(first4d + next4d));

        final Calendar cc = Calendar.getInstance();
        mcYear = cc.get(Calendar.YEAR);
        mcMonth = cc.get(Calendar.MONTH);
        mcDay = cc.get(Calendar.DAY_OF_MONTH);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        multiautoDiagnosis.setThreshold(1);
        multiautoDiagnosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        multiautoTreatmentfor.setThreshold(1);
        multiautoTreatmentfor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        autocompletePatientname.setThreshold(1);
        autocompletePatientname.addTextChangedListener(this);


        autocompleteDoctorname.setThreshold(1);

        edittextAddress.setText(getResources().getString(R.string.addrs));
        edittextRemarks.setText(getResources().getString(R.string.drrefremarks));


        Bundle b = getIntent().getExtras();
        if (b != null) {
            String STATUS = b.getString("CONTINUE_STATUS");//To check whether the activity from clinical information;
            String COMMON_PATIENT_ID = b.getString("PASSING_PATIENT_ID");


            if (STATUS.equalsIgnoreCase("True")) {
                LoadPDtls(COMMON_PATIENT_ID);
            }
        }

    }

    private void CONTROLLISTENERS() {


        autocompleteDoctorname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (autocompleteDoctorname.getText().toString().length() > 0) {
                    String Query = "select  name,Docid  from Mstr_DoctorsList where DocId!='" + BaseConfig.doctorId + "' order by name;";
                    List<String> values = getDoctorsDetails(Query, autocompleteDoctorname.getContext(), autocompleteDoctorname, charSequence.toString());

                    //spinner2meth(autocompleteDoctorname.getContext(), values, autocompleteDoctorname);
                    BaseConfig.loadSpinner(autocompleteDoctorname, values);

                    autocompleteDoctorname.showDropDown();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        imgvwPatientphoto.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (autocompletePatientname.getText().length() > 0 && tvwAgegender.getText().length() > 0) {

                    BaseConfig.Show_Patient_PhotoInDetail(autocompletePatientname.getText().toString().split("-")[0], autocompletePatientname.getText().toString().split("-")[1],
                            tvwAgegender.getText().toString(), DoctorReferral.this);
                }
            }
        });


        multiautoTreatmentfor.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                String patient_type;

               /* if (r1.isChecked()) {
                    patient_type = r1.getText().toString();
                } else {
                    patient_type = r2.getText().toString();
                }*/


                // TODO Auto-generated method stub
                // if (patient_type.equalsIgnoreCase("General")) {

                multiautocompleteRemarksContent.setText("");
                String[] name = autocompletePatientname.getText().toString().split("-");

                String secondln = ("</b></font>" + getString(R.string.content_treatment));
                multiautocompleteRemarksContent.setText(Html.fromHtml("" + secondln));
                //  }

            }
        });

        autocompleteDoctorname.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                SelectedGetDocDetails();

            }
        });

        autocompletePatientname.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                // Patient_Name.setEnabled(false);
                try {
                    SelectedGetPatientDetails();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });


        buttonCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                DoctorReferral.this.finish();
                Intent intent = new Intent(v.getContext(), Dashboard_NavigationMenu.class);
                startActivity(intent);

            }
        });


        buttonSubmit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (autocompletePatientname.getText().length() > 0) {


                    String[] Pat = autocompletePatientname.getText().toString().split("-");
                   if (Pat.length == 2) {

                        boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + "'");
                        if (q) {
                            SaveLocal();
                        } else {
                            BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), context, R.drawable.ic_patienticon, R.color.orange_400);
                        }
                    } else if (Pat.length == 1) {
                        boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + "'");
                        if (q) {
                            SaveLocal();
                        } else {
                            BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), context, R.drawable.ic_patienticon, R.color.red_400);
                        }
                    }


                } else {
                    BaseConfig.showSimplePopUp(getString(R.string.enter_pat_name),
                            getString(R.string.information), context, R.drawable.ic_patienticon, R.color.red_400);
                    autocompletePatientname.requestFocus();
                }
            }
        });



        LoadValues(autocompleteReferredtoHospital, DoctorReferral.this, "select distinct Hospital_Name as dvalue from Mstr_MultipleHospital where (IsActive='true' or IsActive='1') order by Id desc;", 0);

        BaseConfig.loadSpinner(autocompleteReferredtoHospital, Loadlist0);

        LoadPatentIDname();

        LoadValues(multiautoDiagnosis, DoctorReferral.this, "select distinct diagnosisdata as dvalue from diagonisdetails where (isactive='true' or isactive='1') order by id desc;", 1);

        LoadValues(multiautoTreatmentfor, DoctorReferral.this, "select distinct treatmentfor as dvalue from trmntfor where (isactive='true' or isactive='1') order by id desc;", 2);

        BaseConfig.loadSpinner(multiautoDiagnosis, Loadlist1);
        BaseConfig.loadSpinner(multiautoTreatmentfor, Loadlist2);

        LoadSpecialistIn();


        multiautoDiagnosis.setAdapter(new ArrayAdapter<>(DoctorReferral.this, android.R.layout.simple_dropdown_item_1line, convertListtoStringArray(Loadlist2)));

        multiautoDiagnosis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String text;
                text = String.valueOf(charSequence);

                multiautoDiagnosis.setAdapter(new ArrayAdapter<>(DoctorReferral.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, convertListtoStringArray(Loadlist2))));

                multiautoDiagnosis.showDropDown();

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(DoctorReferral.this, android.R.layout.simple_dropdown_item_1line, convertListtoStringArray(Loadlist1)));

        multiautoTreatmentfor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String text;
                text = String.valueOf(charSequence);

                //Log.e("MainActivity", text);

                multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(DoctorReferral.this,
                        android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, convertListtoStringArray(Loadlist1))));

                multiautoTreatmentfor.showDropDown();

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });



    }




    private void LoadValues(AutoCompleteTextView autotxt, Context cntxt, String Query, int id) {


        try {

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        if (id == 0) {
                            String countryname = c.getString(c.getColumnIndex("dvalue"));
                            Loadlist0.add(countryname);
                        } else if (id == 1) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void LoadBack() {

        BaseConfig.HideKeyboard(DoctorReferral.this);

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

    }
    // ///////////////////////////////////////////////////////////////////////////////////////////////

//PDF
  /*  public void CreatePDF(String[] PATIENT_INFO) {

        String FILE = Environment.getExternalStorageDirectory().toString() + "/PDF/" + "MedicalCertificate.pdf";

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/PDF");
        myDir.mkdirs();


        if (BaseConfig.CurrentLangauges.equalsIgnoreCase("mr")) {


            try {
                createDoctorReferralPdfMarathi(FILE);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (CssResolverException e) {
                e.printStackTrace();
            }

        } else {


            //CreateHTMLtoPdf(PATIENT_INFO,FILE);

          Document document = new Document(PageSize.A4, 36, 36, 36, 130);
            try {

                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
                writer.setPageEvent(new HeaderFooterPageEvent());

                document.open();
                addMetaData1(document);
                addTitlePage1(document, PATIENT_INFO);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.close();

        }





    }


    public class HeaderFooterPageEvent extends PdfPageEventHelper
    {
        public void onStartPage(PdfWriter writer, Document document) {


        }

        public void onEndPage(PdfWriter writer, Document document) {
            try {

                Image img1 = Image.getInstance(BaseConfig.AppDBFolderName + "/Patient_Photos/" + BaseConfig.doctorId.replace("/", "") + "_sign.png");

                img1.setAbsolutePosition((document.right() - 100), document.bottom() - 30);
                img1.scaleAbsolute(50f, 50f);
                img1.scalePercent(30);
                document.add(img1);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap1 = BitmapFactory.decodeResource(getBaseContext()
                        .getResources(), R.drawable.mobydoctor_pdflogo);
                bitmap1.compress(CompressFormat.PNG, 100, stream);
                Image myImg = Image.getInstance(stream.toByteArray());
                myImg.setAlignment(Image.MIDDLE);
                myImg.scaleAbsolute(120, 70);
                myImg.scaleAbsoluteHeight(60);
                myImg.setAbsolutePosition(250, 50);
                // add image to document
                document.add(myImg);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void CreateHTMLtoPdf(String[] PATIENT_INFO,String FILE)
    {
        try {

            String Str_DoctorName="",Str_Specialization="",Str_MCIRegNo="",Str_HospitalName="",Str_DateTime="",Str_Address="",Str_Mobile="";
            String Str_PatientName="",Str_Gender="",Str_Age="",Str_Symptoms="",Str_Diagnosis="",Str_ReferredDoctor="",Str_ClinicName="";

            SQLiteDatabase db=BaseConfig.GetDb();


            /*/

    /**************************************************************************************

     String Query="select name,MCI,Academic,cliname,Address,Address2,Specialization,Mobile,docsign from Drreg";
     Cursor c=db.rawQuery(Query,null);
     if(c!=null)
     {
     if(c.moveToFirst())
     {
     do{

     Str_DoctorName=c.getString(c.getColumnIndex("name"))+"-"+c.getString(c.getColumnIndex("Academic"));
     Str_Specialization=c.getString(c.getColumnIndex("Specialization"));
     Str_MCIRegNo=c.getString(c.getColumnIndex("MCI"));
     Str_HospitalName=c.getString(c.getColumnIndex("cliname"));
     Str_Address=c.getString(c.getColumnIndex("Address"))+"-"+c.getString(c.getColumnIndex("Address2"));
     Str_Mobile=c.getString(c.getColumnIndex("Mobile"));

     }while(c.moveToNext());
     }
     }
     c.close();




     /*/

    private void LoadPDtls(String PatientId) {

        String Str_Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PatientId + "'");
        String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PatientId + "'");
        String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + PatientId + "'");

        autocompletePatientname.setText(Str_Patient_Name + "-" + PatientId);
        tvwAgegender.setText(Patient_AgeGender);
        BaseConfig.LoadPatientImage(Str_Patient_Photo, imgvwPatientphoto, 100);

    }



    private void ClearAll() {
        autocompletePatientname.setText("");
        BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);

        tvwAgegender.setText("-");
        multiautoTreatmentfor.setText("");
        multiautoDiagnosis.setText("");
        autocompletePatientname.setEnabled(true);
        LoadSpecialistIn();
    }


    //########################################################################################################################
    //Doctor Referal Letter General
    // ///////////////////////////////////////////////////////////////////////////////////////////
    // /////*******************************************************************************************************************/////////

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        LoadBack();
    }


    // /////*******************************************************************************************************************

  /*  public void addTitlePage1(Document document, String[] PATIENT_INFO) throws DocumentException, IOException {


        SQLiteDatabase db = GetDb();//);
        document.open();


        // Font Itext_Style for Document
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD | Font.UNDERLINE, BaseColor.GRAY);
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        // Start New Paragraph
        Paragraph prHead = new Paragraph();

        // Set Font in this Paragraph
        prHead.setFont(titleFont);
        // Add item into Paragraph
        prHead.add("");
        //getting patient id
        String[] arr = Patient_Name.getText().toString().split("-");



				*//*	Cursor c15 = db.rawQuery("select dsign from mprescribed where ptid='"+arr[1].toString().trim()+"' and id=(select max(id) from mprescribed where ptid='"+arr[1].toString().trim()+"')", null);

					if (c15 != null) {
						if (c15.moveToFirst()) {


						}
						}
					*//*

        Font BlackFont = FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new BaseColor(61, 88, 135));

        //Inserting doctor details
        //!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        PdfPTable table1 = new PdfPTable(3); // 3 columns.
        table1.setWidthPercentage(100); //Width 100%
        table1.setSpacingBefore(10f); //Space before table
        table1.setSpacingAfter(10f); //Space after table

        //Set Column widths
        float[] columnWidths = {1f, 1f, 1f};
        table1.setWidths(columnWidths);


        PdfPCell cell1 = new PdfPCell(new Paragraph(""));
        //cell1.setBorderColor(BaseColor.BLUE);
        cell1.setBorder(0);
        //cell1.setPaddingLeft(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Cursor c2 = db.rawQuery("select name,MCI,Academic,cliname,Address,Address2,Specialization,Mobile,docsign from Drreg", null);

        if (c2 != null) {
            if (c2.moveToFirst()) {

                simg = c2.getString(c2.getColumnIndex("docsign"));

                //Convert64ToImagePdf(simg, 2);


                //space
                PdfPCell cell3 = new PdfPCell(new Paragraph(""));
                //cell3.setBorderColor(BaseColor.RED);
                cell3.setBorder(0);
                //	cell3.setPaddingLeft(10);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);


                PdfPCell cell2 = new PdfPCell(new Paragraph(c2.getString(c2.getColumnIndex("name")) + " " + (c2.getString(c2.getColumnIndex("Academic"))), catFont));
                //cell2.setBorderColor(BaseColor.GREEN);
                cell2.setBorder(0);
                //cell2.setPaddingLeft(10);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell5 = new PdfPCell(new Paragraph(c2.getString(c2.getColumnIndex("Specialization")), normal));
                //cell2.setBorderColor(BaseColor.GREEN);
                cell5.setBorder(0);
                //cell5.setPaddingLeft(10);
                cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);


                PdfPCell cell6 = new PdfPCell(new Paragraph("MCI Regn. No: " + c2.getString(c2.getColumnIndex("MCI"))));
                //cell2.setBorderColor(BaseColor.GREEN);
                cell6.setBorder(0);
                //	cell6.setPaddingLeft(10);
                cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                //To avoid having the cell border and the content overlap, if you are having thick cell borders
                //cell1.setUserBorderPadding(true);
                //cell2.setUserBorderPadding(true);
                //cell3.setUserBorderPadding(true);
                String clinicName = c2.getString(c2.getColumnIndex("cliname"));


                if (clinicName.contains(",")) {
                    clinicName = clinicName.replace(",", "\n");
                }


                PdfPCell cell7 = new PdfPCell(new Paragraph("\n" + clinicName, catFont));
                //cell2.setBorderColor(BaseColor.GREEN);
                cell7.setBorder(0);
                //cell7.setPaddingLeft(10);
                cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell7A = new PdfPCell(new Paragraph("Address: " + c2.getString(c2.getColumnIndex("Address")) + ", " + c2.getString(c2.getColumnIndex("Address2"))));
                //cell2.setBorderColor(BaseColor.GREEN);
                cell7A.setBorder(0);
                //cell7A.setPaddingLeft(10);
                cell7A.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell7A.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //date
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
                String strDate = sdf.format(c.getTime());
                SimpleDateFormat stime = new SimpleDateFormat("h:mm:ss a");
                String currentDateandTime = stime.format(new Date());
                PdfPCell cell8 = new PdfPCell(new Paragraph(strDate + " \t " + currentDateandTime));
                //cell2.setBorderColor(BaseColor.GREEN);
                cell8.setBorder(0);
                cell8.setPaddingLeft(10);
                cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //dr name
                table1.addCell(cell1);
                table1.addCell(cell2);
                table1.addCell(cell3);

                //general medicine
                table1.addCell(cell1);
                table1.addCell(cell5);
                table1.addCell(cell3);
                //mci
                table1.addCell(cell1);
                table1.addCell(cell6);
                table1.addCell(cell3);

                //empty
                table1.addCell(cell1);
                table1.addCell(cell1);
                table1.addCell(cell3);

                //clicni name
                table1.addCell(cell1);
                table1.addCell(cell7);
                table1.addCell(cell3);

                //clicni address
                table1.addCell(cell1);
                //table1.addCell(cell7A);
                table1.addCell(cell3);

                //date time
                table1.addCell(cell1);
                table1.addCell(cell1);
                table1.addCell(cell8);

                //empty
                table1.addCell(cell1);
                table1.addCell(cell1);
                table1.addCell(cell3);


                document.add(table1);

            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // Start underline//
        PdfPTable myTable = new PdfPTable(1);
        // 100.0f mean width of table is same as Document size
        myTable.setWidthPercentage(100.0f);
        // Create New Cell into Table
        PdfPCell myCell = new PdfPCell(new Paragraph(" "));
        myCell.setBorder(Rectangle.TOP);
        myTable.addCell(myCell);
        prHead.setFont(catFont);
        //prHead.add("\n");
        prHead.setAlignment(Element.ALIGN_CENTER);
        document.add(prHead);
        document.add(myTable);
        // End underline//
//////////////////////////////////////////////////////////////////////////////////////////////////////
        PdfPTable table2 = new PdfPTable(3); // 3 columns.
        table2.setWidthPercentage(100); //Width 100%
        table2.setSpacingBefore(10f); //Space before table
        table2.setSpacingAfter(10f); //Space after table

        //Set Column widths
        float[] columnWidths2 = {0.7f, 1f, 1f};
        table2.setWidths(columnWidths2);

        //Paient Details
        /////////////////
        Cursor c4 = db.rawQuery("select name,age,gender,Actdate,phone from Patreg where Patid='" + arr[1].trim() + "'", null);

        //Log.e("Patient Id", arr[1].toString().trim());


        if (c4 != null) {
            if (c4.moveToFirst()) {

                PdfPTable breakln = new PdfPTable(2);
                document.add(breakln);


                PdfPCell cell9 = new PdfPCell(new Paragraph("Patient Name"));
                cell9.setBorder(0);
                cell9.setPaddingLeft(10);
                cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell10 = new PdfPCell(new Paragraph("Gender"));
                cell10.setBorder(0);
                cell10.setPaddingLeft(10);
                cell10.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell11 = new PdfPCell(new Paragraph("Age"));
                cell11.setBorder(0);
                cell11.setPaddingLeft(10);
                cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell12 = new PdfPCell(new Paragraph("Symptoms"));
                cell12.setBorder(0);
                cell12.setPaddingLeft(10);
                cell12.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell13 = new PdfPCell(new Paragraph("Provisional Diagnosis"));
                cell13.setBorder(0);
                cell13.setPaddingLeft(10);
                cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);

                PdfPCell cell17 = new PdfPCell(new Paragraph("Specialist Doctor Referred"));
                cell17.setBorder(0);
                cell17.setPaddingLeft(10);
                cell17.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell17.setVerticalAlignment(Element.ALIGN_MIDDLE);


                PdfPCell cell18 = new PdfPCell(new Paragraph("Hospital/Clinic"));
                cell18.setBorder(0);
                cell18.setPaddingLeft(10);
                cell18.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell18.setVerticalAlignment(Element.ALIGN_MIDDLE);


                PdfPCell cell19 = new PdfPCell(new Paragraph("Referred From Hospital"));
                cell19.setBorder(0);
                cell19.setPaddingLeft(10);
                cell19.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell19.setVerticalAlignment(Element.ALIGN_MIDDLE);


                PdfPCell cell110 = new PdfPCell(new Paragraph("Referred To Hospital"));
                cell110.setBorder(0);
                cell110.setPaddingLeft(10);
                cell110.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell110.setVerticalAlignment(Element.ALIGN_MIDDLE);



                //empty
                table1.addCell(cell1);
                table1.addCell(cell1);
                table1.addCell(cell1);



                PdfPCell cell14 = new PdfPCell(new Paragraph(": " + c4.getString(c4.getColumnIndex("name"))));
                cell14.setBorder(0);
                cell14.setPaddingLeft(10);
                cell14.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell14.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //Title patient name
                table2.addCell(cell9);
                table2.addCell(cell14);
                table2.addCell(cell1);


                //empty
                table2.addCell(cell1);
                table2.addCell(cell1);
                table2.addCell(cell1);

                PdfPCell cell15 = new PdfPCell(new Paragraph(": " + c4.getString(c4.getColumnIndex("gender"))));
                cell15.setBorder(0);
                cell15.setPaddingLeft(10);
                cell15.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell15.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //Title gender
                table2.addCell(cell10);
                table2.addCell(cell15);
                table2.addCell(cell1);

                //empty
                table2.addCell(cell1);
                table2.addCell(cell1);
                table2.addCell(cell1);

                PdfPCell cell16 = new PdfPCell(new Paragraph(": " + c4.getString(c4.getColumnIndex("age"))));
                cell16.setBorder(0);
                cell16.setPaddingLeft(10);
                cell16.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell16.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //Title age
                table2.addCell(cell11);
                table2.addCell(cell16);
                table2.addCell(cell1);

                //empty
                table2.addCell(cell1);
                table2.addCell(cell1);
                table2.addCell(cell1);


                PdfPCell cell171 = new PdfPCell(new Paragraph(": " + treatment.getText().toString()));
                cell171.setBorder(0);
                cell171.setPaddingLeft(10);
                cell171.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell171.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //Title symptoms
                table2.addCell(cell12);
                table2.addCell(cell171);
                table2.addCell(cell1);

                //empty
                table2.addCell(cell1);
                table2.addCell(cell1);
                table2.addCell(cell1);


                PdfPCell cell181 = new PdfPCell(new Paragraph(": " + diagdetail.getText().toString()));
                cell181.setBorder(0);
                cell181.setPaddingLeft(10);
                cell181.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell181.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //Title diagnosis
                table2.addCell(cell13);
                table2.addCell(cell181);
                table2.addCell(cell1);


                PdfPCell cell1811 = new PdfPCell(new Paragraph(": " + refdoctorname.getText().toString()));
                cell1811.setBorder(0);
                cell1811.setPaddingLeft(10);
                cell1811.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell1811.setVerticalAlignment(Element.ALIGN_MIDDLE);

                //Title diagnosis
                table2.addCell(cell17);
                table2.addCell(cell1811);
                table2.addCell(cell1);

                PdfPCell cell18111 = new PdfPCell(new Paragraph(": " + clinicname.getText().toString()));
                cell18111.setBorder(0);
                cell18111.setPaddingLeft(10);
                cell18111.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell18111.setVerticalAlignment(Element.ALIGN_MIDDLE);


                //Title diagnosis
                table2.addCell(cell18);
                table2.addCell(cell18111);
                table2.addCell(cell1);

                PdfPCell cell181111 = new PdfPCell(new Paragraph(": " + ref_from_hospital.getText().toString()));
                cell181111.setBorder(0);
                cell181111.setPaddingLeft(10);
                cell181111.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell181111.setVerticalAlignment(Element.ALIGN_MIDDLE);

                table2.addCell(cell19);
                table2.addCell(cell181111);
                table2.addCell(cell1);




                PdfPCell cell1811111 = new PdfPCell(new Paragraph(": " + ref_to_hospital.getText().toString()));
                cell1811111.setBorder(0);
                cell1811111.setPaddingLeft(10);
                cell1811111.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell1811111.setVerticalAlignment(Element.ALIGN_MIDDLE);

                table2.addCell(cell110);
                table2.addCell(cell1811111);
                table2.addCell(cell1);



                document.add(table2);
            }
        }

        //########################################################################################################################
        // Font Itext_Style for Document


        //paragraph

        Cursor c1 = db.rawQuery("select Ptid,Referby,clinicname,treatmentfor,patname,pagegen,PRem,dSign,Actdate,splin,docname,address,imei," +
                "mobnum,IsActive,paddr,docid,pmobnum,diagnosisdtls,ptype,refcertfid,Isupdate from Certificate where " +
                "Ptid='" + arr[1] + "'" +
                " and  Actdate=(select MAX(Actdate) from Certificate where Ptid='" + arr[1] + "')", null);
        if (c1 != null) {
            if (c1.moveToFirst()) {

                //Toast.makeText(getApplicationContext(),"test", Toast.LENGTH_LONG).show();
                Paragraph preface = new Paragraph();


                preface.add(new Paragraph("Dear " + c1.getString(c1.getColumnIndex("docname")) + ", ", smallBold));

                preface.add(new Paragraph("                   ", smallBold));

                preface.add(new Paragraph(contentref.getText().toString()));

                preface.add(new Paragraph("                   ", smallBold));
                preface.add(new Paragraph("                   ", smallBold));
                preface.add(new Paragraph("Sincerely", smallBold));
                preface.add(new Paragraph(BaseConfig.doctorname + ", ", smallBold));
                preface.add(new Paragraph(BaseConfig.docmobile + ", ", smallBold));
                document.add(preface);

            }
        }                // Start a new page
        //document.newPage();


        PdfPTable tblsig1 = new PdfPTable(4);
        tblsig1.setWidthPercentage(100.0f);
        tblsig1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        // sign

      *//*  Image img11 = Image.getInstance("/sdcard/sign.jpg");
        img11.scaleAbsolute(120f, 60f);// image width,height
        img11.setAbsolutePosition(420, 230);
        img11.scalePercent(30);*//*

        tblsig1.addCell("");
        tblsig1.addCell("");
        tblsig1.addCell("");
        tblsig1.addCell("\n");

        tblsig1.addCell("");
        tblsig1.addCell("");
        tblsig1.addCell("");
        tblsig1.addCell("");
        //tblsig1.addCell(img11);

        tblsig1.addCell("");
        tblsig1.addCell("");
        tblsig1.addCell("");
        tblsig1.addCell("");
        tblsig1.addCell("Thanks and with best regards");

        document.add(tblsig1);
        //End Sign//


        // Start footer//
        PdfPTable tblfoot = new PdfPTable(3);
        tblfoot.setWidthPercentage(100.0f);
        tblfoot.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        //tblfoot.addCell("@ powered by MobyDoctor");
        tblfoot.addCell("");
        tblfoot.addCell("");
        tblfoot.addCell("");
        //BaseConfig.SelectedPID = "";
        // Image image=decodeImage(imgsmp)

        document.add(tblfoot);


        document.add(tblfoot);
        //BaseConfig.SelectedPID = "";
        //End footer//
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.mobydoctor_pdflogo);
        bitmap1.compress(CompressFormat.JPEG, 100, stream);
        Image myImg = Image.getInstance(stream.toByteArray());
        myImg.setAlignment(Image.MIDDLE);
        myImg.scaleAbsolute(120, 70);
        myImg.scaleAbsoluteHeight(60);
        myImg.setAbsolutePosition(250, 50);
        //add image to document
        document.add(myImg);

        document.newPage();
    }

*/
    // /////***General****************************************************************************************************************/////////

    // Set PDF document Properties
  /*  public void addMetaData1(Document document)
    {
        document.addTitle("RESUME");
        document.addSubject("Person Info");
        document.addKeywords("Personal,	Education, Skills");
        document.addAuthor("TAG");
        document.addCreator("TAG");
    }


    private void addEmptyLine(Paragraph preface, int i) {
        // TODO Auto-generated method stub

    }
*/

    //########################################################################################################################
    //Doctor Referal Letter Personal

    //*******************************************************************************************************************/////////
    private void SaveLocal() {


        try {
            if (checkValidation())

                submitForm();
            else
                Toast.makeText(DoctorReferral.this, getString(R.string.check_missing_valid),
                        Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private boolean checkValidation() {

        boolean ret = true;

        if (!Validation1.hasText(autocompletePatientname))
            ret = false;


        if (!Validation1.isMobileNumber(edittextMobilenumber, true))
            ret = false;
        if (!Validation1.hasText(multiautoTreatmentfor))
            ret = false;
        if (!Validation1.hasText(multiautoDiagnosis))
            ret = false;

        if (!Validation1.hasText(edttextReferredfromHospital))
            ret = false;

        if (!Validation1.hasText(autocompleteReferredtoHospital))
            ret = false;

        if (!Validation1.isEmailAddressChk(edittextEmailid, true))
            ret = false;

        return ret;
    }

    private void submitForm() {

        SQLiteDatabase db = GetDb();//);

        ContentValues values;

        /////////////////////////////////////////////////////////////////////////////////////
        //Treatment master
        String[] tf = multiautoTreatmentfor.getText().toString().trim().split(",");
        String[] dg1 = multiautoDiagnosis.getText().toString().split(",");

        BaseConfig.INSERT_NEW_TREATMENT_FOR(tf, this);
        BaseConfig.INSERT_NEW_PROVISIONAL_DIAGNOSIS(dg1, this);

        /////////////////////////////////////////////////////////////////////////////////////


        SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.ENGLISH);
        Date date = new Date();
        String dttm = dateformt.format(date);

        String refcertfId = GetrefcertfId();
        String[] arr = autocompletePatientname.getText().toString().split("-");
        String[] agegensel = tvwAgegender.getText().toString().split("-");

        String refddocspln = "";


        String PATIENT_ADDRESS = BaseConfig.GetValues("select Address as ret_values from Patreg where Patid='" + arr[1] + "'");
        String PATIENT_MOBILE = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + arr[1] + "'");

        values = new ContentValues();
        values.put("HID", BaseConfig.HID);
        values.put("Ptid", arr[1].trim());
        values.put("Referby", BaseConfig.doctorname);
        values.put("clinicname", edittextClinicalname.getText().toString());
        values.put("treatmentfor", multiautoTreatmentfor.getText().toString());
        values.put("patname", arr[0].trim());
        values.put("pagegen", tvwAgegender.getText().toString());
        values.put("paddr", PATIENT_ADDRESS);
        values.put("dSign", "");
        values.put("Actdate", BaseConfig.DeviceDate());
        values.put("splin", refddocspln);
        values.put("docname", "Dr." + textviewDoctorname.getText().toString());
        values.put("address", edittextAddress.getText().toString());
        values.put("imei", BaseConfig.Imeinum);
        values.put("mobnum", edittextMobilenumber.getText().toString());
        values.put("IsActive", 1);
        values.put("docid", BaseConfig.doctorId);
        values.put("pmobnum", PATIENT_MOBILE);
        values.put("diagnosisdtls", multiautoDiagnosis.getText().toString());
        values.put("ptype", "");
        values.put("refcertfid", refcertfId);
        values.put("PRem", edittextRemarks.getText().toString());
        values.put("Isupdate", 0);
        values.put("refdocmailid", edittextEmailid.getText().toString());
        values.put("ref_from_hospital", edttextReferredfromHospital.getText().toString());
        values.put("ref_to_hospital", autocompleteReferredtoHospital.getText().toString());
        db.insert(BaseConfig.TABLE_CERTIFICATE, null, values);

        //for clearing sign
        BaseConfig.drsign = 0;

        final String Update_Query = "update Certificatemvalue set certificatenum=certificatenum +1";
        BaseConfig.SaveData(Update_Query);

        db.close();

        showSuccessDialog();


    }

    private void showSuccessDialog() {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getString(R.string.information))
                .setNegativeButtonVisible(View.GONE)
                .setDescription("Doctor referral successfully submitted..")
                .setPossitiveButtonTitle(this.getString(R.string.ok))
                .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                    @Override
                    public void onPossitivePerformed() {

                        //  dialog.dismiss();
                        DoctorReferral.this.finish();
                        Intent intent11 = new Intent(DoctorReferral.this, Dashboard_NavigationMenu.class);
                        startActivity(intent11);

                    }
                });


    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////
    private String GetrefcertfId() {
        String did = null;
        SQLiteDatabase db = GetDb();//);
        Cursor c = db.rawQuery(
                "select certificatenum from Certificatemvalue ;", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    did = "RID/" + mcYear + "/" + mcDay + mcMonth + "/"
                            + c.getString(c.getColumnIndex("certificatenum"));

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        if (did != null) {
            return did;
        } else {
            return null;
        }

    }


    // /////*******************************************************************************************************************/////////

    /**************************************************************************************
     //patient info
     String Query1="select name,age,gender,Actdate,phone from Patreg where Patid='"+ PATIENT_INFO[1].trim() +"'";
     c=null;
     c=db.rawQuery(Query1,null);
     if(c!=null)
     {
     if(c.moveToFirst())
     {
     do
     {

     Str_PatientName=c.getString(c.getColumnIndex("name"));
     Str_Gender=c.getString(c.getColumnIndex("gender"));
     Str_Age=c.getString(c.getColumnIndex("age"));


     }
     while(c.moveToNext());

     }

     }
     c.close();

     Str_Symptoms=treatment.getText().toString();
     Str_Diagnosis=diagdetail.getText().toString();
     Str_ReferredDoctor=refdoctorname.getText().toString().split("-")[0];
     Str_ClinicName=clinicname.getText().toString();


     db.close();


     com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.LETTER);
     PdfWriter.getInstance(document, new FileOutputStream(FILE));
     document.open();
     document.addCreationDate();

     HTMLWorker htmlWorker = new HTMLWorker(document);

     String str;

     str="<h3 style=\"text-align: center;\"><strong>"+Str_DoctorName+"</strong></h3>\n" +
     "<h4 style=\"text-align: center;\">"+Str_Specialization+"</h4>\n" +
     "<h4 style=\"text-align: center;\">"+Str_MCIRegNo+"</h4>\n" +
     "<p>&nbsp;</p>\n" +
     "<h4 style=\"text-align: center;\"><strong>"+Str_HospitalName+"<br>"+Str_Address+"<br>"+Str_Mobile+"</strong></h4>\n" +
     "<p style=\"text-align: center;\"><strong>"+BaseConfig.DeviceDate()+"</strong></p>\n" +
     "<p style=\"text-align: justify;\"><strong>Patient Name &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;:</strong> &nbsp;"+Str_PatientName+"</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Gender &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;:</strong> &nbsp;"+Str_Gender+"</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Age &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;:</strong> &nbsp;"+Str_Age+"</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Symptoms &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; :</strong> &nbsp;"+Str_Symptoms+"</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Provisional Diagnosis &nbsp; &nbsp; &nbsp; &nbsp; :</strong> &nbsp;"+Str_Diagnosis+"</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Specialist Doctor&nbsp;Referred &nbsp;:</strong> &nbsp;"+Str_ReferredDoctor+"</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Hospital / Clinic &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;:</strong> &nbsp;"+Str_ClinicName+"</p>\n" +
     "<p style=\"text-align: justify;\">&nbsp;</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Dear "+ Str_ReferredDoctor +",</strong></p>\n" +
     "<p style=\"text-align: justify;\">"+contentref.getText().toString()+"</p>\n" +
     "<p style=\"text-align: justify;\">&nbsp;</p>\n" +
     "<p style=\"text-align: justify;\"><strong>Sincerely,</strong></p>\n" +
     "<p style=\"text-align: justify;\"><strong>"+BaseConfig.doctorname+",</strong></p>\n" +
     "<p style=\"text-align: justify;\"><strong>"+BaseConfig.docmobile+"</strong></p>";

     htmlWorker.parse(new StringReader(str));



     document.close();

     } catch (DocumentException e) {
     e.printStackTrace();
     } catch (Exception e) {
     e.printStackTrace();
     }

     }
     */
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    public void LoadPDFGeneral() {
        String FileFinalpath = DATABASE_FILE_PATH + File.separator
                + "/PDF/MedicalCertificate.pdf";
        File file = new File(FileFinalpath);
        if (file.exists()) {
            Uri filepath = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(filepath, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //startActivity(intent);
            clear();
            //DoctorReferral.this.finish();
            Intent intent1 = Intent.createChooser(intent, getString(R.string.open_referral_pdf));

            try {

                startActivity(intent1);

            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
                Toast.makeText(this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();

            }


        } else {

        }
    }

    public void LoadPDFPersonal() {
        String FileFinalpath = DATABASE_FILE_PATH + File.separator
                + "/PDF/MedicalCertificatePersonal.pdf";
        File file = new File(FileFinalpath);
        if (file.exists()) {
            Uri filepath = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(filepath, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //startActivity(intent);
            DoctorReferral.this.finish();
            Intent intent1 = Intent.createChooser(intent, getString(R.string.open_referral_pdf));

            try {
                startActivity(intent1);

            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
                Toast.makeText(this, "No PDF Viewer Installed", Toast.LENGTH_LONG).show();

            }

        } else {

        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void Convert64ToImagePdf(String base64, int id) {

        try {
            byte[] decodedString = Base64.decode(base64.trim(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                    0, decodedString.length);

            FileOutputStream fileOutputStream;
            if (id == 1) {
                fileOutputStream = new FileOutputStream(DATABASE_FILE_PATH
                        + File.separator + "logo.jpg");
            } else {
                fileOutputStream = new FileOutputStream(DATABASE_FILE_PATH
                        + File.separator + "sign.jpg");
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    fileOutputStream);
            decodedByte.compress(CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            // logos.setImageBitmap(decodedByte);
        } catch (IOException e) {

            e.printStackTrace();
        }

        // setting the decodedByte to ImageView

    }


    private void clear() {
        autocompletePatientname.setText(null);
        multiautoTreatmentfor.setText(null);

        spinnerSpecialist.setSelection(0);

        edittextRemarks.setText(null);
        autocompleteDoctorname.setText(null);
        edittextAddress.setText(null);
        edittextMobilenumber.setText(null);

        imgvwPatientphoto.setImageBitmap(null);
        tvwAgegender.setText(null);

        multiautocompleteRemarksContent.setText("");
        multiautoDiagnosis.setText("");

    }

    private void LoadSpecialistIn() {

        SQLiteDatabase db = GetDb();//);
        Cursor c = db
                .rawQuery(
                        "select distinct specialzation from Specialzation order by specialzation;",
                        null);

        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.selec_specliz));

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String counrtyname = c.getString(c
                            .getColumnIndex("specialzation"));
                    list.add(counrtyname);

                } while (c.moveToNext());
            }
        }



        BaseConfig.loadSpinner(spinnerSpecialist, list);
        db.close();
        c.close();
    }

    private void SelectedGetPatientDetails() {

        String[] pna = autocompletePatientname.getText().toString().split("-");

        String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + pna[1] + "'");
        String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + pna[1] + "'");

        tvwAgegender.setText(Patient_AgeGender);

        BaseConfig.Glide_LoadImageView( imgvwPatientphoto, Str_Patient_Photo );
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case SIGNATURE_ACTIVITY:
                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    String status = bundle.getString("status");
                    if (status.equalsIgnoreCase("done")) {
                        Toast toast = Toast.makeText(this,
                                "Signature capture successfull!",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 40, 105);
                        toast.show();
                    }

                }
                break;


            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {

                        try {

                            URI = Uri.parse(Environment.getExternalStorageDirectory() + "/PDF/MedicalCertificate.pdf");

                        } catch (Exception e) {

                        }
                    }

                }
        }


    }

    private void SelectedGetDocDetails() {
        SQLiteDatabase db = GetDb();//);

        String[] pna = autocompleteDoctorname.getText().toString().split("-");
        String docsin64 = "";
        Cursor c = db.rawQuery(
                "select name,clinicname,Address,Mobile,mail,Specialization_text,Country,State,District from Mstr_DoctorsList where Docid='"
                        + pna[1].trim() + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    edittextClinicalname.setText(c.getString(c.getColumnIndex("clinicname")));
                    edittextMobilenumber.setText(c.getString(c.getColumnIndex("Mobile")));
                    edittextAddress.setText(c.getString(c.getColumnIndex("Address")));
                    textviewSpecialization.setText(c.getString(c.getColumnIndex("Specialization_text")));
                    edittextEmailid.setText(c.getString(c.getColumnIndex("mail")));

                    spinnerSpecialist.setVisibility(View.GONE);

                    String[] values = c.getString(c.getColumnIndex("Specialization_text")).split(",");

                    LoadSpecialistIn(values);

                    edittextClinicalname.setEnabled(false);
                    edittextMobilenumber.setEnabled(false);
                    edittextAddress.setEnabled(false);
                    edittextEmailid.setEnabled(false);

                    edittextClinicalname.setTextColor(Color.BLACK);
                    edittextMobilenumber.setTextColor(Color.BLACK);
                    edittextAddress.setTextColor(Color.BLACK);
                    edittextEmailid.setTextColor(Color.BLACK);

                } while (c.moveToNext());
            }
        }

        db.close();
        c.close();

    }


    private void LoadPatentIDname() {

        autocompletePatientname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (autocompletePatientname.getText().toString().length() > 0) {

                    String LoadPatientQuery = "select name,Patid from patreg order by name";
                    String Query = LoadPatientQuery;
                    BaseConfig.SelectedGetPatientDetailsFilter(Query, DoctorReferral.this, autocompletePatientname, charSequence.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                                    return true;
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
        });

    }

    public void LoadDoctorname() {

        SQLiteDatabase db = GetDb();//);
        Cursor c = db.rawQuery(
                "select name,Academic from Drreg order by name;", null);

        List<String> list = new ArrayList<String>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String pname = c.getString(c.getColumnIndex("name"));
                    String str2 = c.getString(c.getColumnIndex("Academic"));
                    list.add(pname + "-" + str2);

                } while (c.moveToNext());
            }
        }

        ArrayAdapter<String> StateDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list);

        autocompleteDoctorname.setAdapter(StateDataAdapter);

        db.close();
        c.close();

    }

    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }

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

    @SuppressLint("ShowToast")
    private boolean prepareDirectory() {
        try {
            return makedirs();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(
                    this,
                    "Could not initiate File System.. Is Sdcard mounted properly?",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean makedirs() {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory()) {
            File[] files = tempdir.listFiles();
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete " + file);
                }
            }
        }
        return (tempdir.isDirectory());
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {

            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    private void updateDisplay() {
        mDateDisplay.setText("DOB : " + new StringBuilder()
                // Month is 0 based so add 1
                .append(mDay).append("-").append(mMonth + 1).append("-")

                .append(mYear).append(" "));
        int ag = calculateAge();
        tvwAgegender.setText(String.valueOf(ag));
    }

    // ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void afterTextChanged(Editable s) {

    }

    // ////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        imgvwPatientphoto.setImageBitmap(null);
        tvwAgegender.setText(null);
    }

    private List<String> getDoctorsDetails(String query, Context ctx, AutoCompleteTextView refdrname, String s) {

        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = GetDb();


        Cursor c = db.rawQuery(query, null);
        list.clear();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex("name"));
                    String Docid = c.getString(c.getColumnIndex("Docid"));
                    String namev = name.split("\\.")[1].toLowerCase().trim();

                    if (namev.startsWith(s.toLowerCase())) {
                        list.add(namev + "-" + Docid);

                    }

                } while (c.moveToNext());
            }
        }

        c.close();
        db.close();

        return list;


    }

    public StringBuilder getImageBase64(String ImagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        StringBuilder imgString = new StringBuilder();
        imgString.append(Base64.encodeToString(byteFormat, Base64.NO_WRAP));
        return imgString;
    }

/*
    private void createDoctorReferralPdfMarathi(String file)  throws IOException, DocumentException, CssResolverException {



        StringBuilder doctsign=new StringBuilder();



        SQLiteDatabase db = BaseConfig.GetDb();//);

        //doctor
        Cursor c2 = db
                .rawQuery(
                        "select name,MCI,Academic,cliname,Address,Address2,Specialization,Mobile,docsign from Drreg ",
                        null);

        if (c2 != null) {
            if (c2.moveToFirst()) {

                do {

                    str_DoctorName=c2.getString(c2.getColumnIndex("name"));
                    str_MCIRegnno=c2.getString(c2.getColumnIndex("MCI"));
                    //doctsign.append(c2.getString(c2.getColumnIndex("docsign")));
                    doctsign.append(getImageBase64(c2.getString(c2.getColumnIndex("docsign"))));

                }while(c2.moveToNext());
            }}

        c2.close();



        //patient
        c2=null;
        c2 = db
                .rawQuery(
                        "select name,age,gender from Patreg",
                        null);

        if (c2 != null) {
            if (c2.moveToFirst()) {

                do {

                    str_PatientName=c2.getString(c2.getColumnIndex("name"));
                    str_Age=c2.getString(c2.getColumnIndex("age"));
                    str_Gender=c2.getString(c2.getColumnIndex("gender"));
//                    patid=c2.getString(c2.getColumnIndex(""));



                }while(c2.moveToNext());
            }}

        c2.close();
        String str_referenceDocname="",str_Clinicname="",str_contentref="";

        str_Symptoms= treatment.getText().toString();
        str_provosionaldiagnosis=diagdetail.getText().toString();
        str_referenceDocname= refdoctorname.getText().toString();
        str_Clinicname= clinicname.getText().toString();



        str_contentref=contentref.getText().toString();


        String str = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\t<style>\n" +
                "hr { background-color: #c3c3c3; height: 2px; border: 0; }\n" +
                "p.small {\n" +
                "    line-height: 100%;\n padding-bottom: 0px;" +
                "}\n" +
                "p.mediam {\n" +
                "    line-height: 130%;\n" +
                "}\n" +
                "br {\n" +
                "    display: block;\n" +
                "    margin-bottom: 2px;\n" +
                "    font-size:2px;\n" +
                "    line-height: 2px;\n" +
                "}\n" +
                "img{\n" +
                "    padding-top: 20px;\n" +
                "}"+
                "table, td, th {\n" +
                "    border: 1px solid black;\n" +
                "\theight: 50px;\n" +
                "}\n" +
                "\n" +
                "table {\n" +
                "    border-collapse: collapse;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                "td {\n" +
                "    height: 50px;\n" +
                "}\n" +
                "th {\n" +
                "    height: 50px;\n" +
                "}\n" +
                "body {\n" +
                "    padding-top: 40px;\n" +
                "    padding-right: 40px;\n" +
                "    padding-bottom: 40px;\n" +
                "    padding-left: 40px;\n" +
                "}\n" +
                "\n" +
                "</style>\n" +
                "\t<body>\n" +
                "\t\t<br></br>\n" +
                "\t\t<p align=\"center\" class=\"small\">\n" +
                "\t\t\t<strong>"+str_DoctorName+"</strong>\n" +
                "\t\t</p>\n" +
                "\t\t<p align=\"center\" class=\"small\"><img src=\"" + getResources().getString(R.string.pdf_generalmedicine) + "\" /></p>\n" +
                "\t\t<p align=\"center\" class=\"small\">\n" +
                "\t\t\t<B>MCI Regn. No: "+str_MCIRegnno+"</B>\n" +
                "\t\t</p>\n" +
                "\t\t<br></br>\n" +
                "\t\t<p align=\"center\" class=\"small\">\n" +
                "\t\t\t<strong>BAL RUKMINIBAL HOSPITAL</strong>\n" +
                "\t\t</p>\n" +
                "\t\t<p align=\"center\" class=\"small\">\n" +
                "\t\t\t<strong>KALYAN</strong>\n" +
                "\t\t</p>\n" +
                "\t\t<p align=\"center\" class=\"small\">"+BaseConfig.DeviceDate()+"</p>\n" +
                "\t\t<hr class=\"small\"></hr>\n" +

                "<br></br>\n" +
                "<p align=\"left\" class=\"small\"><img  style=\"padding-top: 20px;\" src=\"" + getResources().getString(R.string.pdf_patientname) + "\"  align=\"center\"/>"+space+"<br></br> "+str_PatientName+"</p>\n" +
                "\t\t<p align=\"left\" class=\"small\"><img  style=\"padding-top: 20px;\" src=\"" + getResources().getString(R.string.pdf_gender_b64) + "\"  align=\"center\" /> "+space+"<br></br>"+str_Gender+"</p>\n" +
                "\t\t<p align=\"left\" class=\"small\"><img  style=\"padding-top: 20px;\" src=\"" + getResources().getString(R.string.pdf_age_b64) + "\"  align=\"center\" />"+space+"<br></br> "+str_Age+"</p>\n" +
                "\t\t<p align=\"left\" class=\"small\"><img  style=\"padding-top: 20px;\" src=\"" + getResources().getString(R.string.pdf_symptoms_b64) + "\"  align=\"center\" /> "+space+"<br></br> Body pain</p>\n" +
                "\t\t<p align=\"left\" class=\"small\"><img  style=\"padding-top: 20px;\" src=\"" + getResources().getString(R.string.pdf_provosionaldiagnosis) + "\"  align=\"center\" />"+space+" <br></br>"+str_provosionaldiagnosis+"</p>\n" +
             "\t\t<p align=\"left\" class=\"small\"><img style=\"padding-top: 20px;\"  src=\"" + getResources().getString(R.string.pdf_specialistdoctorreferal) + "\"  align=\"center\"/>"+space+"<br></br>"+str_specialistdoctorreferal+"</p>\n" +
                "\t\t<p align=\"left\" class=\"small\"><img style=\"padding-top: 20px;\"  src=\"" + getResources().getString(R.string.pdf_hospitalclinic) + "\"  align=\"center\"/>"+space+"<br></br>"+str_hospitalclinic+"</p>\n" +
                "\t\t<br></br>"+




                // "\t\t<hr class=\"small\"></hr>\n" +
                // "\t\t<br></br>\n" +
                "\t\t<p align=\"left\" class=\"small\"><img style=\"padding-top: 20px;\"  src=\"" + getResources().getString(R.string.pdf_dear) + "\"  align=\"center\"/> "+str_DoctorName+",</p>\n" +
                "\t\t\n" +
                "\t\t<p align=\"left\" class=\"small\"> "+str_contentref+"</p>\n" +
                "\t\t\n<br></br>" +
                "\t\t<p align=\"left\" class=\"small\"><img src=\"" + getResources().getString(R.string.pdf_sincerely) + "\"  align=\"center\"/> <br></br> </p>\n" +
                "\t\t<p align=\"left\" class=\"small\">"+space+"&nbsp;&nbsp;&nbsp;&nbsp;"+BaseConfig.doctorname+"</p>\n" +
                "\t\t<p align=\"left\" class=\"small\">"+space+""+BaseConfig.docmobile +"</p>\n" +

                "\t\t<p align=\"right\" class=\"small\"><img width=\"72\" height=\"72\" src=\"data:image/png;base64," + doctsign + "\"  align=\"right\"/></p>\n" +
                "\t\t<p align=\"right\" class=\"small\">Signature</p>\n" +
                "\t\t<p align=\"center\" class=\"small\"><img width=\"190\" height=\"150\" src=\"" + getResources().getString(R.string.pdf_hospitallogo) + "\"  align=\"center\"/></p>\n" +"\t\t<!----------------------------------------------------------------->\n" +
                "\t\t<br></br>\n" +
                "\t\t<!----------------------------------------------------------------->\n" +
                "\t\t<!----------------------------------------------------------------->\n" +
                "\t</body>\n" +
                "</html>  ";


        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4

        // CSS
        CSSResolver cssResolver =
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);


        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new Base64ImageProvider());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);



        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(str.getBytes()));

        // step 5
        document.close();
    }


    class Base64ImageProvider extends AbstractImageProvider {

        @Override
        public com.itextpdf.text.Image retrieve(String src) {
            int pos = src.indexOf("base64,");
            try {
                if (src.startsWith("data") && pos > 0) {
                    byte[] img = com.itextpdf.text.pdf.codec.Base64.decode(src.substring(pos + 7));//
                    return com.itextpdf.text.Image.getInstance(img);
                } else {
                    return com.itextpdf.text.Image.getInstance(src);
                }
            } catch (BadElementException ex) {
                return null;
            } catch (IOException ex) {
                return null;
            }
        }

        @Override
        public String getImageRootPath() {
            return null;
        }
    }
*/

    private void LoadSpecialistIn(String[] values) {

        SQLiteDatabase db = GetDb();//);
        Cursor c = db
                .rawQuery(
                        "select distinct specialzation from Specialzation order by specialzation;",
                        null);
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.selec_specliz));


        for (int i = 0; i < values.length; i++) {


            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String counrtyname = c.getString(c
                                .getColumnIndex("specialzation"));

                        //Checking is equal to add List
                        if (values[i].toLowerCase().trim().startsWith(counrtyname.toLowerCase().trim())) {
                            list.add(counrtyname);
                        }

                    } while (c.moveToNext());
                }
            }


        }



        BaseConfig.loadSpinner(spinnerSpecialist, list);
        db.close();
        c.close();
    }

   /* private class SendMail extends AsyncTask<Void, Void, Void> {

        // //////////////////////////////////////////////
        protected void onPostExecute(Void v) {
            if (pDialog.isShowing())
                pDialog.dismiss();

            BaseConfig.sndmailflag = 0;

            sndmail.cancel(true);

            Toast.makeText(context, getString(R.string.email_sent), Toast.LENGTH_LONG).show();

            showSimplePopUp1(getString(R.string.email_sent), null);


        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(DoctorReferral.this);
            pDialog.setTitle(getString(R.string.sen_email));
            pDialog.setMessage(getString(R.string.eait_email));
            pDialog.setCancelable(false);
            pDialog.show();


        }


        @Override
        protected Void doInBackground(Void... params) {

            try {

                sendMail();
                sendMailPatient();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        private void sendMail() throws MessagingException, IOException {

            //New method for email sending
            patientname = Patient_Name.getText().toString().split("-");


            //Godaddy mailserver
            final String SMTP_OUT_SERVER = "smtpout.secureserver.net";
            final String USER = "info@mobydoctor.com"; // godaddy domain
            final String PASSWORD = "1962maha";


            String subj1 = "<b>Dear Dr." + refdoctorname.getText().toString() + ",</b><br><br>";
            subj1 += (getString(R.string.referral_content_txt) + patientname[0] + getString(R.string.referral_content_txtt) + diagdetail.getText().toString() + ".</b></font>"
                    + getString(R.string.referral_content_txttt) +
                    getString(R.string.referral_content_txtttt));
            subj1 += ("<b>Regards,</b><br>");
            subj1 += ("<b>" + BaseConfig.doctorname + "</b><br>");
            subj1 += ("<b>" + BaseConfig.docmobile + "</b><br>");
            subj1 += ("<b>" + BaseConfig.clinicname + "</b><br>");

            //Mail m = null;

            Properties props = System.getProperties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", SMTP_OUT_SERVER);

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "80");
            props.setProperty("mail.user", USER);
            props.setProperty("mail.password", PASSWORD);

            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport("smtp");
            MimeMessage message = new MimeMessage(mailSession);
            message.setSentDate(new Date());
            message.setSubject(getString(R.string.referralletter));
            message.setFrom(new InternetAddress(USER));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(refdocmail.getText().toString()));
            MimeMultipart multipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(subj1, "text/html");
            // messageBodyPart.setContent(messagecnt, "text/html");

            MimeBodyPart attachPart = new MimeBodyPart();
            File F = new File(Environment.getExternalStorageDirectory() + "/PDF/MedicalCertificate.pdf");
            attachPart.attachFile(F);
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);


            transport.connect(SMTP_OUT_SERVER, USER, PASSWORD);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();

            //Log.e("MailApp", "Referral Doctor Email was sent successfully.");

        }


        private void sendMailPatient() throws MessagingException, IOException {
            try {
                patientname = Patient_Name.getText().toString().split("-");

                //New method for email sending

                //Godaddy mailserver
                final String SMTP_OUT_SERVER = "smtpout.secureserver.net";
                final String USER = "info@mobydoctor.com"; // godaddy domain
                final String PASSWORD = "1962maha";


                String subj1 = "<b>Hello " + patientname[0] + ",</b><br><br>";
					*//*subj1 += ("I had diagnosed <b>"+patientname[0].toString()+"</b> and inferred that the patient is affected by "+diagdetail.getText().toString()+".</b></font>"
							+ "<br>Hence I referred you for further diagnosis and consultations.<br><br>" +
							"Please find the herein below attached referral letter. Kindly do the needful.<br><br>");
					*//*
                //We herewith attached a copy of referral letter mailed to "+ "<font color='#EE0000'><b>"
                //+ BaseConfig.doctorname.toString()+ "</b></font>"+" for expert opinion and management.


                subj1 += (getString(R.string.content_doctor_referral) + BaseConfig.doctorname + getString(R.string.content_doctor_referral_txt));
                subj1 += ("<b>Regards,</b><br>");
                subj1 += ("<b>" + BaseConfig.doctorname + "</b><br>");
                subj1 += ("<b>" + BaseConfig.docmobile + "</b><br>");
                subj1 += ("<b>" + BaseConfig.clinicname + "</b><br>");

                //Mail m = null;

                Properties props = System.getProperties();
                props.setProperty("mail.transport.protocol", "smtp");
                props.setProperty("mail.host", SMTP_OUT_SERVER);

                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "80");
                props.setProperty("mail.user", USER);
                props.setProperty("mail.password", PASSWORD);

                Session mailSession = Session.getDefaultInstance(props, null);
                mailSession.setDebug(true);
                Transport transport = mailSession.getTransport("smtp");
                MimeMessage message = new MimeMessage(mailSession);
                message.setSentDate(new Date());
                message.setSubject(getString(R.string.referralletter));
                message.setFrom(new InternetAddress(USER));

                message.setRecipient(Message.RecipientType.TO, new InternetAddress(BaseConfig.patientEmail));
                MimeMultipart multipart = new MimeMultipart("related");

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(subj1, "text/html");
                // messageBodyPart.setContent(messagecnt, "text/html");

                MimeBodyPart attachPart = new MimeBodyPart();
                File F = new File(Environment.getExternalStorageDirectory() + "/PDF/MedicalCertificate.pdf");
                attachPart.attachFile(F);
                multipart.addBodyPart(messageBodyPart);
                multipart.addBodyPart(attachPart);
                message.setContent(multipart);


                transport.connect(SMTP_OUT_SERVER, USER, PASSWORD);
                transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                transport.close();

                //Log.e("MailApp", "Referral Patient Email was sent successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                //Log.e("Email - Referral Letter", "No Mail Id for patient");
            }


        }
    }
*/
    //End
}
