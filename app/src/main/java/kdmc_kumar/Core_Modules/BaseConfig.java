package kdmc_kumar.Core_Modules;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.LocalSharedPref;
import kdmc_kumar.Utilities_Others.RandomUtils;
import kdmc_kumar.Utilities_Others.TimeUtils;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;
import kdmc_kumar.Webservices_NodeJSON.ExportWebservices_NODEJS;
import kdmc_kumar.Webservices_NodeJSON.ImportWebservices_NODEJS;
import kdmc_kumar.Webservices_NodeJSON.MasterWebservices_NODEJS;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class BaseConfig {


    public static final String adminentry = "ADMINKDMC";// "AdmiN";
    public static final Bundle b = new Bundle();
    public static final ArrayList<CharSequence> selectedColours9 = new ArrayList<CharSequence>();
    public static final String Patent_Id = "";
    public static final String TreatmentFor_Dental = "Dentistry";
    public static final String bundlebranch = "";
    public static final String conductiondefects = "";
    public static final String treadmill = "";
    public static final String TABLE_PATIENT_VACCINATION = "Vaccination";
    public static final String TABLE_CLINICAL_INFORMATION = "ClinicalInformation";
    public static final String TABLE_CLINICAL_MHIS = "mhis";
    public static final String TABLE_CLINICAL_ALLERGY = "Allergy";
    public static final String TABLE_CLINICAL_HEREDITARYDIEASES = "hereditaryDisease";
    public static final String TABLE_DIAGONIS = "Diagonis";
    public static final String TABLE_CASENOTES_GENERALEXAMINATION = "CaseNote_GeneralExamination";
    public static final String TABLE_CASENOTES_CARDIOVASCULAR = "CaseNote_Cardiovascular";
    public static final String TABLE_CASENOTES_RESPIRATORYSYSTEMS = "CaseNote_RespiratorySystem";
    public static final String TABLE_CASENOTES_GASTROINTESTINAL = "CaseNote_Gastrointestinal";
    public static final String TABLE_CASENOTES_NEUROLOGY = "CaseNote_Neurology";
    public static final String TABLE_CASENOTES_CNS = "CaseNote_CNS";
    public static final String TABLE_CASENOTES_LOCOMOTOR = "CaseNote_Locomotor";
    public static final String TABLE_CASENOTES_OTHERSSYSTEMS = "CaseNote_OtherSystem";
    public static final String TABLE_CASENOTES_CLINICALDATA = "CaseNote_ClinicalData";
    public static final String TABLE_CASENOTES_RENAL = "CaseNote_Renal";
    public static final String TABLE_CASENOTES_ENDOCRINE = "CaseNote_Endocrine";
    public static final String TABLE_CASENOTES_PNCSYSTEM = "CaseNote_PNCSystem";
    public static final String TABLE_CASENOTES_DENTALSYSTEM = "CaseNote_DentalSystem";
    public static final String TABLE_ANGIOGRAM = "Angiogram";
    public static final String TABLE_EEG = "EEG";
    public static final String TABLE_ECGTEST = "ECGTEST";
    public static final String TABLE_XRAY = "XRAY";
    public static final String TABLE_SCANTEST = "Scantest";
    public static final String TABLE_MEDICALTESTDTLS = "Medicaltestdtls";
    public static final String TABLE_PRESCRIPTION = "Mprescribed";
    public static final String TABLE_MEDICINE = "Medicine";
    public static final String TABLE_CERTIFICATE = "Certificate";
    public static final String TABLE_SEND_BULK_MEDICINE = "SendBulkMedicine";
    public static final String TABLE_DIET_RESTRICTION = "DietRestriction";
    public static final String TABLE_MEDICAL_TEST = "Medicaltest";
    public static final String TABLE_APPOINTMENTS = "Appoinment";
    public static final String TABLE_REPORTGALLERY = "ReportGallery";
    public static final String TABLE_SETTINGS = "drsettings";
    public static final String BUNDLE_PATIENT_ID = "PATIENT_ID";
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final int MEDIA_TYPE_VIDEO = 2;
    private static final ArrayList<CharSequence> selectedColours6 = new ArrayList<CharSequence>();
    private static final ArrayList<CharSequence> selectedColours7 = new ArrayList<CharSequence>();
    private static final ArrayList<CharSequence> selectedColours8 = new ArrayList<CharSequence>();
    private static final ArrayList<CharSequence> selectedColours10 = new ArrayList<CharSequence>();
    private static final ArrayList<CharSequence> selectedColours11 = new ArrayList<CharSequence>();
    private static final ArrayList<CharSequence> selectedColours12 = new ArrayList<CharSequence>();
    private static final String Font_Location = "fonts/fontawesome-webfont.ttf";//Fontawesome
    private static final String TABLE_TREATMENTFOR = "trmntfor";
    private static final String TABLE_SIGNS = "Mstr_Signs";
    private static final String TABLE_DIAGONISDETAILS = "diagonisdetails";
    private static final Calendar currentDate = Calendar.getInstance();
    private static final SimpleDateFormat formatterr = new SimpleDateFormat("dd",Locale.ENGLISH);
    private static final String daynum = formatterr.format(currentDate.getTime());
    private static final SimpleDateFormat formatterm = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    private static final String yrnum = formatterm.format(currentDate.getTime());
    private static final String dayname = (String) android.text.format.DateFormat.format("EEEE", currentDate);
    private static final String Monthname = (String) android.text.format.DateFormat.format("MMMM", currentDate);
    private static final String finaldt = dayname + ", " + Monthname + " " + daynum + ", " + yrnum;
    public static String Appname = "";
    public static String AppLogo = "";
    public static String AppLanguage = "";
    public static String AppWebServiceType = "";
    public static String AppNodeIP = "";
    public static String AppImagephpURL = "";
    public static String AppDBFolderName = "";
    public static String AppDatabaseName = "";
    public static String AppDirectoryPictures = Appname;
    public static String AppUpdateJson = BaseConfig.AppNodeIP + "/app_files/update.json";
    public static String Patient_Photo_Location = "";
    public static File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Appname), AppDirectoryPictures);
    public static String PDFLINK = "";
    public static boolean PDFLINK_FLAG = false;
    // public static String myInpatientselected;
    public static String CurrentLangauges = "en";
    public static String patientimage;
    public static Bitmap SelectedBitmap;
    public static int patient_nameflag;
    public static String patient_updateflag;
    public static String Patient_ImgPath = "";
    public static ListViewType listViewType = ListViewType.GrideView;
    public static ArrayList<CharSequence> selectedColours2 = new ArrayList<CharSequence>();
    public static String ReportyGal;
    public static String patientEmail = "";

    public static boolean IsFromInPatient = false;
    public static String Druglistselindex = "";
    public static String Assistant_Task = "";
    //Report
    public static int reports_val = 0;
    public static String fircolumn = "";
    public static String seccolumn = "";
    public static String thirdcolumn = "";
    public static String totcolumn = "";
    public static String patid_mpopup = "";
    public static int Isnewpatreg = 0;
    public static int Adult_childAge = 12;


    public static String Imeinum = "";
    public static String MacId = "";
    public static String username = "";
    public static String reportname;
    public static String onlineconsulreportname;
    public static String docemail = "";
    public static String doctorname = "";
    public static String doctorId = "";
    public static String HID = "";
    public static String HOSPITALNAME = "";
    public static String doctorRegId = "";
    public static String docregcntry;
    public static String docregcity;
    public static String docspecli;
    public static String docacademic;
    public static String clinicname;
    public static String docaddress;
    public static String docmobile;
    public static String docpincode;
    public static String docstate;
    public static String template_name;
    public static String popuptemplate_name;
    public static String popupmedicine_name;
    public static String popupmedicineupdat_name;
    public static String popuptreatment_name;
    public static String popuptreatmentupdat_name;
    public static String popupdiagnosis_name;
    public static String popupdiagnosisupdat_name;
    public static String popuptest_name;
    public static String popuptestupdat_name;
    public static String popupsubtest_name;
    public static String popupsubtestupdat_name;
    public static String signature = "";
    public static String workingdays_mon = "";
    public static String workingdays_tue = "";
    public static String workingdays_wed = "";
    public static String workingdays_thur = "";
    public static String workingdays_fri = "";
    public static String workingdays_sat = "";
    public static String workingdays_sun = "";
    public static String doctorimage = null;
    public static String docsign = "";
    public static String doc_signature = null;
    public static int welcometoast = 0;
    public static int sndmailflag;

    public static int expandlstflag = 0;
    public static int drsign = 0;
    public static int bkproc = 0;
    public static String digid = "";
    public static String medid = "";
    public static String rem = "";
    public static String prstate = "";
    public static String prdis = "";
    public static String Zoomimg = "";
    public static Bitmap imgzom = null;
    public static String rate = "";
    public static String pulse = "";
    public static String pulserate = "";
    public static String qrs = "";
    public static String st = "";
    public static String t = "";
    public static String rhyrhm = "";
    public static String axis = "";
    public static String temp_flag = "";
    public static String temp_updateflag = "false";
    public static List<String> scansummarydtls = new ArrayList<String>();
    public static List<String> testsummarydtls = new ArrayList<String>();
    public static String selectedtemplate = "";
    public static Context context;
    public static String Login_Docid = "";
    public static String TABLE_FORREPORT = "ForReport";
    public static String timedate = finaldt;
    public static String SetActionBarColour = String.valueOf(R.color.colorPrimary);
    public static boolean server_connectivity_status = false;
    static CharSequence[] items5;
    // This function call when click on attachment button of actionbar and display reveal animation layout
    static boolean hidden = true;
    private static int mYear;
    private static int mMonth;
    private static int mDay;
    private static int mcYear;
    private static int mcMonth;
    private static int mcDay;
    private static ScheduledExecutorService Import_scheduler;
    private static ScheduledExecutorService Masters_Import_scheduler;
    private static ScheduledExecutorService Export_scheduler;
    private static List<String> mypatientdetails = new ArrayList<String>();
    private static List<String> mypatientprevmedicalhistory2 = new ArrayList<String>();
    private static List<String> mypatientprevmedicalhistory2_dgid = new ArrayList<String>();
    private static List<String> mypatientprevmedicalhistory2_mscanid = new ArrayList<String>();
    private static List<String> mypatientprevmedicalhistory2_mtestid = new ArrayList<String>();
    private static List<String> mypatientprevmedicalhistory3 = new ArrayList<String>();
    private static List<String> mypatientprevmedicalhistory4 = new ArrayList<String>();
    private static List<String> mypatientprevmedicinehistory = new ArrayList<String>();
    private static String scanreportdt = "";
    private static String testreportdt = "";
    private static CharSequence[] items;
    private static CharSequence[] items1;
    private static CharSequence[] items2;
    private static CharSequence[] items3;
    private static CharSequence[] items4;
    private static CharSequence[] items6;
    private static CharSequence[] items7;
    /* Patient FamilyRelation*/
    private static StringBuilder stringBuilder6 = new StringBuilder();
    private static StringBuilder stringBuilder7 = new StringBuilder();
    private static StringBuilder stringBuilder8 = new StringBuilder();
    private static StringBuilder stringBuilder9 = new StringBuilder();
    private static StringBuilder stringBuilder10 = new StringBuilder();
    private static StringBuilder stringBuilder11 = new StringBuilder();
    private static StringBuilder stringBuilder12 = new StringBuilder();
    private static Locale myLocale;
    private static BaseConfig baseConfig = null;
    public ArrayList<CharSequence> selectedColours = new ArrayList<CharSequence>();
    public ArrayList<CharSequence> selectedColours1 = new ArrayList<CharSequence>();
    public ArrayList<CharSequence> selectedColours3 = new ArrayList<CharSequence>();
    public ArrayList<CharSequence> selectedColours4 = new ArrayList<CharSequence>();
    public ArrayList<CharSequence> selectedColours5 = new ArrayList<CharSequence>();
    public String PinStatus = "";
    public String DecryptPin = "";
    Typeface tfavv1;

    private ArrayAdapter<String> ListDataAdapter;
    private Bitmap rotatedBitmap;

    public BaseConfig() {

        getinitialize();

    }

    public static SQLiteDatabase GetDb() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(AppDatabaseName, null, null);
        return db;
    }

    public static void ClearStorage() {


        mypatientdetails = new ArrayList<String>();
        mypatientprevmedicalhistory2 = new ArrayList<String>();
        mypatientprevmedicalhistory2_dgid = new ArrayList<String>();
        mypatientprevmedicalhistory2_mscanid = new ArrayList<String>();
        mypatientprevmedicalhistory2_mtestid = new ArrayList<String>();

        mypatientprevmedicalhistory3 = new ArrayList<String>();
        mypatientprevmedicalhistory4 = new ArrayList<String>();

        mypatientprevmedicinehistory = new ArrayList<String>();

        scanreportdt = "";
        testreportdt = "";
    }

    public static boolean CheckTextView(TextView tv) {
        return tv.getText().length() != 0;
    }

    public static boolean CheckSpinner(Spinner sp) {
        return sp.getSelectedItemPosition() > 0;
    }

    public static boolean CheckBox(Checkable sp) {
        return sp.isChecked();
    }

    public static boolean EditTextInput(EditText edt) {
        return !edt.getText().toString().startsWith(".") && !edt.getText().toString().endsWith(".");
    }

    //To check EditTextInputLength
    public static boolean EditTextInputLength(EditText edt, int maxLength, Context cntx, String msg) {
        edt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        if (!edt.getText().toString().equals("") && edt.length() >= maxLength) {
            return true;
        } else {
            edt.setError(msg);
            edt.requestFocus();
            return false;
        }
    }

    public static void LoadValues(AutoCompleteTextView autotxt, Context cntxt, String Query) {
        try {

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list.add(counrtyname);

                    } while (c.moveToNext());
                }
            }

            loadSpinner(autotxt, list);
            c.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void LoadValues(MultiAutoCompleteTextView autotxt, Context cntxt, String Query) {
        try {

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list.add(counrtyname);

                    } while (c.moveToNext());
                }
            }

            loadSpinner(autotxt, list);
            c.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> LoadMultiAutoValues(MultiAutoCompleteTextView autotxt, Context cntxt, String Query) {
        try {

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list.add(counrtyname);

                    } while (c.moveToNext());
                }
            }

            //loadSpinner(autotxt, list);
            c.close();
            db.close();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void LoadValuesListView(ListView autotxt, Context cntxt, String Query) {
        try {

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list.add(counrtyname);

                    } while (c.moveToNext());
                }
            }

            ArrayAdapter<String> CountryDataAdapter = new ArrayAdapter<String>(cntxt, android.R.layout.simple_list_item_single_choice, list);
            CountryDataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

            autotxt.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            autotxt.setAdapter(CountryDataAdapter);

            c.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void LoadValuesForMedicine(Context cntxt, String Query) {

        try {

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));

                        boolean q = LoadBooleanStatusCount("select count(medicine) as dstatus from NewMedicine where medicine='" + counrtyname.toString() + "'", cntxt);
                        if (q) {
                            final String Insert_Query_Myfav = "Insert into NewMedicine(medicine,Isupdate)" + " Values('" + counrtyname.toString() + "','0');";

                            BaseConfig.SaveData(Insert_Query_Myfav);

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

    public static void SaveData(String Query) {

        SQLiteDatabase db = GetDb();

        db.execSQL(Query);

        db.close();

    }

    public static boolean LoadBooleanStatus(String Query) {
        try {
            int havcount = 0;

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        //PinStatus = c.getString(c.getColumnIndex("dstatus"));
                        havcount++;

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

            return havcount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean LoadReportsBooleanStatus(String Query) {
        try {
            int havcount = 0;

            SQLiteDatabase db = GetDb();
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean LoadBooleanStatusCount(String Query, Context context) {
        try {

            String PinStatus = "";
            int havcount = 0;

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        PinStatus = c.getString(c.getColumnIndex("dstatus"));
                        if (PinStatus.trim().equalsIgnoreCase("0")) {
                            havcount++;
                        }

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

            return havcount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getHospitalName(String hid) {
        String hospitalName = "";
        try {
            SQLiteDatabase db = GetDb();
            String Query = "SELECT * from Mstr_MultipleHospital where ServerId = '" + hid + "'";
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        hospitalName = c.getString(c
                                .getColumnIndex("Hospital_Name"));

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hospitalName;
    }

    public static void LoadDoctorValues() {

        try {

            String Query = "";
            SQLiteDatabase db = GetDb();
            Query = "SELECT HID,Docid,drname,name,Imei,RegId,emailid,cliname,Academic,Specialization,Country,State,District,Address,pincode,Mobile,docimage,docsign from Drreg";
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        BaseConfig.HID = c.getString(c.getColumnIndex("HID"));
                        BaseConfig.HOSPITALNAME = getHospitalName(BaseConfig.HID);
                        BaseConfig.doctorId = c.getString(c.getColumnIndex("Docid"));
                        BaseConfig.doctorname = c.getString(c.getColumnIndex("name"));
                        BaseConfig.doctorRegId = c.getString(c.getColumnIndex("RegId"));
                        BaseConfig.docemail = c.getString(c.getColumnIndex("emailid"));
                        BaseConfig.clinicname = c.getString(c.getColumnIndex("cliname"));
                        BaseConfig.docacademic = c.getString(c.getColumnIndex("Academic"));
                        BaseConfig.docspecli = c.getString(c.getColumnIndex("Specialization"));
                        BaseConfig.docregcntry = c.getString(c.getColumnIndex("Country"));
                        BaseConfig.docstate = c.getString(c.getColumnIndex("State"));
                        BaseConfig.docregcity = c.getString(c.getColumnIndex("District"));
                        BaseConfig.docaddress = c.getString(c.getColumnIndex("Address"));
                        BaseConfig.docpincode = c.getString(c.getColumnIndex("pincode"));
                        BaseConfig.docmobile = c.getString(c.getColumnIndex("Mobile"));
                        BaseConfig.doctorimage = c.getString(c.getColumnIndex("docimage"));
                        BaseConfig.Imeinum = c.getString(c.getColumnIndex("Imei"));

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

            BaseConfig.Patient_Photo_Location = BaseConfig.AppDBFolderName + "/Patient_Photos/";


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static void LoadValuesSpinner(Spinner spinner_ctrl, Context cntxt, String Query, String lstadd) {


        try {
            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            list.add(lstadd);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list.add(counrtyname);

                    } while (c.moveToNext());
                }
            }

            //spinner2meth(cntxt, list, spinnertxt);

            loadSpinner(spinner_ctrl, list);

            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void LoadPopupValues(Context cntxt, String Query, String strval) {
        try {
            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));

                        list.add(counrtyname);

                    } while (c.moveToNext());
                }
            }

            new ArrayAdapter<String>(cntxt, R.layout.simple_list, list);

            if (strval.equalsIgnoreCase("1")) {
                items = list.toArray(new String[list.size()]);
            } else if (strval.equalsIgnoreCase("2")) {
                items1 = list.toArray(new String[list.size()]);
            } else if (strval.equalsIgnoreCase("3")) {
                items2 = list.toArray(new String[list.size()]);
            } else if (strval.equalsIgnoreCase("4")) {
                items3 = list.toArray(new String[list.size()]);
            } else if (strval.equalsIgnoreCase("5")) {
                items4 = list.toArray(new String[list.size()]);
            } else if (strval.equalsIgnoreCase("6")) {
                items6 = list.toArray(new String[list.size()]);
            } else if (strval.equalsIgnoreCase("7")) {
                items7 = list.toArray(new String[list.size()]);
            }

            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSelectColoursDialogHereDisease(Context cntxt,
                                                          final EditText etxt, final String stricon) {

        boolean[] checkedColours = new boolean[items2.length];
        int count = items2.length;

        int i = count - 1;
        while (i >= 0) {
            checkedColours[i] = selectedColours2.contains(items2[i]);
            i--;
        }

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which,
                                boolean isChecked) {
                if (isChecked) {
                    selectedColours2.add(items2[which]);

                } else {
                    selectedColours2.remove(items2[which]);
                }

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);

        if (stricon == "Medical") {
            builder.setTitle(R.string.popup_medical);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Alergy") {
            builder.setTitle(R.string.popup_allergic);//.setIcon(R.drawable.allergic_icon);
        } else if (stricon == "Hereditary") {
            builder.setTitle(R.string.popup_hereditary);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Academic") {
            builder.setTitle(R.string.popup_academic);

        } else if (stricon == "Specialization") {
            builder.setTitle(R.string.popup_specialization);

        } else if (stricon == "Endo") {
            builder.setTitle(R.string.str_endocrine);

        }

        builder.setMultiChoiceItems(items2, checkedColours,
                coloursDialogListener);
        builder.setPositiveButton(cntxt.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();

                for (CharSequence colour : selectedColours2) {
                    stringBuilder.append(colour + "\n");

                }

                etxt.setText(stringBuilder.toString());

            }
        });
        builder.setNegativeButton(cntxt.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new StringBuilder();

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static void showSelectColoursDialogFamilyRelation(Context cntxt, final EditText etxt, final String stricon, final String dieases) {

        boolean[] checkedColours = new boolean[items6.length];
        int count = items6.length;

        if (dieases.equals("Asthma")) {
            for (int i = 0; i < count; i++)
                checkedColours[i] = selectedColours6.contains(items6[i]);
        } else if (dieases.equals("Diabetes")) {
            for (int i = 0; i < count; i++)
                checkedColours[i] = selectedColours10.contains(items6[i]);
        } else if (dieases.equals("HyperTension")) {
            for (int i = 0; i < count; i++)
                checkedColours[i] = selectedColours11.contains(items6[i]);
        } else if (dieases.equals("Hypercholesterolemia")) {
            for (int i = 0; i < count; i++)
                checkedColours[i] = selectedColours7.contains(items6[i]);
        } else if (dieases.equals("Stroke/TIA")) {
            for (int i = 0; i < count; i++)
                checkedColours[i] = selectedColours8.contains(items6[i]);
        } else if (dieases.equals("Migraine")) {
            for (int i = 0; i < count; i++)
                checkedColours[i] = selectedColours9.contains(items6[i]);
        } else if (dieases.equals("Others")) {
            for (int i = 0; i < count; i++)
                checkedColours[i] = selectedColours12.contains(items6[i]);
        }

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which,
                                boolean isChecked) {

                if (dieases.equals("Asthma")) {
                    if (isChecked) {
                        selectedColours6.add(items6[which]);

                    } else {
                        selectedColours6.remove(items6[which]);
                    }
                } else if (dieases.equals("Hypercholesterolemia")) {
                    if (isChecked) {
                        selectedColours7.add(items6[which]);

                    } else {
                        selectedColours7.remove(items6[which]);
                    }
                } else if (dieases.equals("Stroke/TIA")) {
                    if (isChecked) {
                        selectedColours8.add(items6[which]);

                    } else {
                        selectedColours8.remove(items6[which]);
                    }
                } else if (dieases.equals("Migraine")) {
                    if (isChecked) {
                        selectedColours9.add(items6[which]);

                    } else {
                        selectedColours9.remove(items6[which]);
                    }
                } else if (dieases.equals("Diabetes")) {
                    if (isChecked) {
                        selectedColours10.add(items6[which]);

                    } else {
                        selectedColours10.remove(items6[which]);
                    }
                } else if (dieases.equals("HyperTension")) {
                    if (isChecked) {
                        selectedColours11.add(items6[which]);

                    } else {
                        selectedColours11.remove(items6[which]);
                    }
                } else if (dieases.equals("Others")) {
                    if (isChecked) {
                        selectedColours12.add(items6[which]);

                    } else {
                        selectedColours12.remove(items6[which]);
                    }
                }

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);

        if (stricon == "Medical") {
            builder.setTitle(R.string.popup_medical);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Allergy") {
            builder.setTitle(R.string.popup_allergic);//.setIcon(R.drawable.allergic_icon);
        } else if (stricon == "Hereditary") {
            builder.setTitle(R.string.popup_hereditary);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Academic") {
            builder.setTitle(R.string.popup_academic);

        } else if (stricon == "Specialization") {
            builder.setTitle(R.string.popup_specialization);

        } else if (stricon == "Endo") {
            builder.setTitle(R.string.str_endocrine);

        } else if (stricon == "Family") {
            builder.setTitle(dieases);

        }

        builder.setMultiChoiceItems(items6, checkedColours, coloursDialogListener);

        builder.setPositiveButton(cntxt.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {


                if (dieases.equals("Asthma")) {
                    stringBuilder6 = new StringBuilder();
                    stringBuilder6.append(dieases + "\n");
                    for (CharSequence colour : selectedColours6) {
                        stringBuilder6.append(colour + "\n");
                        // selectedoffense.add(colour);
                    }
                } else if (dieases.equals("Hypercholesterolemia")) {
                    stringBuilder7 = new StringBuilder();
                    stringBuilder7.append(dieases + "\n");
                    for (CharSequence colour : selectedColours7) {
                        stringBuilder7.append(colour + "\n");
                        // selectedoffense.add(colour);
                    }
                } else if (dieases.equals("Stroke/TIA")) {
                    stringBuilder8 = new StringBuilder();
                    stringBuilder8.append(dieases + "\n");
                    for (CharSequence colour : selectedColours8) {
                        stringBuilder8.append(colour + "\n");
                        // selectedoffense.add(colour);
                    }
                } else if (dieases.equals("Migraine")) {
                    stringBuilder9 = new StringBuilder();
                    stringBuilder9.append(dieases + "\n");
                    for (CharSequence colour : selectedColours9) {
                        stringBuilder9.append(colour + "\n");
                        // selectedoffense.add(colour);
                    }
                } else if (dieases.equals("Diabetes")) {
                    stringBuilder11 = new StringBuilder();
                    stringBuilder11.append(dieases + "\n");
                    for (CharSequence colour : selectedColours10) {
                        stringBuilder11.append(colour + "\n");
                        // selectedoffense.add(colour);
                    }
                } else if (dieases.equals("HyperTension")) {
                    stringBuilder12 = new StringBuilder();
                    stringBuilder12.append(dieases + "\n");
                    for (CharSequence colour : selectedColours11) {
                        stringBuilder12.append(colour + "\n");
                        // selectedoffense.add(colour);
                    }
                } else if (dieases.equals("Others")) {
                    stringBuilder10 = new StringBuilder();
                    stringBuilder10.append(dieases + "\n");
                    for (CharSequence colour : selectedColours12) {
                        stringBuilder10.append(colour + "\n");
                        // selectedoffense.add(colour);
                    }
                }

                etxt.setText(stringBuilder6.toString() + stringBuilder7.toString() + stringBuilder8.toString() + stringBuilder9.toString() + stringBuilder10.toString() + stringBuilder11.toString() + stringBuilder12.toString());


            }
        });
        builder.setNegativeButton(cntxt.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new StringBuilder();

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), AppDirectoryPictures);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(AppDirectoryPictures, "Oops! Failed create "
                        + AppDirectoryPictures + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.ENGLISH).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "patient.jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public static void LoadPatientImage(String FilePath, ImageView imgvw, int Quality) {


        if (FilePath.length() > 0) {

            BaseConfig.Glide_LoadImageView(imgvw, FilePath);

        } else {

            BaseConfig.Glide_LoadDefaultImageView(imgvw);

        }


    }

    public static void LoadPatientImage(String FilePath, AppCompatImageView imgvw, int Quality) {


        if (FilePath.length() > 0) {

            BaseConfig.Glide_LoadImageView(imgvw, FilePath);

        } else {

            BaseConfig.Glide_LoadDefaultImageView(imgvw);

        }


    }


    public static void showSimplePopUp(String msg, String head, Context cntxt, int drawable, int panelcolor) {

        //  new SweetAlertDialog(cntxt).setTitleText(head).setContentText(msg).show();

        new CustomKDMCDialog(cntxt)
                .setLayoutColor(panelcolor)
                .setImage(drawable)
                .setTitle(head)
                .setDescription(msg)
                .setNegativeButtonVisible(View.GONE)
                .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                    @Override
                    public void onPossitivePerformed() {

                    }
                })
                .setPossitiveButtonTitle(cntxt.getString(R.string.ok));


    }

    public static void Is_Valid_Email(EditText patientname)
            throws NumberFormatException {
        if (patientname.getText().toString().length() <= 0) {

        } else if (!patientname
                .getText()
                .toString()
                .matches(
                        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            patientname.setError("Invalid Email");
        }

    }

    public static void StartWebservice_Import(final Context ctx, int id) {

        //IF ID == 1 : DOTNET SERVICES
        //ELSE ID == 2 : NODEJS SERVICES
        //===============================
        Masters_Import_scheduler = Executors.newSingleThreadScheduledExecutor();

        Masters_Import_scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {


                    MasterWebservices_NODEJS service2 = new MasterWebservices_NODEJS(ctx);
                    service2.ExecuteAll();
                    Log.e("WEBSERVICES", "*** NODEJS MASTER SERVICES STARTED ***");
                    Log.e("WEBSERVICES", "*** NODEJS MASTER SERVICES STARTED ***");


                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.gc();
            }
        }, 0, 20, TimeUnit.SECONDS);


        //===============================
        Import_scheduler = Executors.newSingleThreadScheduledExecutor();

        Import_scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {

                try {

                    ImportWebservices_NODEJS service1 = new ImportWebservices_NODEJS(ctx);
                    service1.ExecuteAll();
                    Log.e("WEBSERVICES", "*** NODEJS IMPORT SERVICES STARTED ***");
                    Log.e("WEBSERVICES", "*** NODEJS IMPORT SERVICES STARTED ***");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.gc();
            }
        }, 0, 15, TimeUnit.SECONDS);


    }


    public static void StartWebservice_Export(final Context ctx, int id) {

        Export_scheduler = Executors.newSingleThreadScheduledExecutor();

        Export_scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {


                    ExportWebservices_NODEJS service2 = new ExportWebservices_NODEJS(ctx);
                    service2.ExecuteAll();
                    Log.e("WEBSERVICES", "*** NODEJS EXPORT SERVICES STARTED ***");
                    Log.e("WEBSERVICES", "*** NODEJS EXPORT SERVICES STARTED ***");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.gc();
            }
        }, 0, 10, TimeUnit.SECONDS);

    }

    public static void StopwebService_Import() {
        if (Import_scheduler != null) {
            try {
                Log.e("SERVICES", "IMPORT WEBSERVICES : STOPPED");
                Log.e("SERVICES", "IMPORT WEBSERVICES : STOPPED");
                Import_scheduler.shutdownNow();
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Masters_Import_scheduler != null) {
            try {
                Log.e("SERVICES", "MASTERS WEBSERVICES : STOPPED");
                Log.e("SERVICES", "MASTERS WEBSERVICES : STOPPED");
                Masters_Import_scheduler.shutdownNow();
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void StopwebService_Export() {
        if (Export_scheduler != null) {
            try {
                Log.e("SERVICES", "EXPORT WEBSERVICES : STOPPED");
                Log.e("SERVICES", "EXPORT WEBSERVICES : STOPPED");
                Export_scheduler.shutdownNow();
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void customtoast(Context cnt, Activity activity, String textval) {

        // Custom Toast
        LayoutInflater inflator = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflator.inflate(R.layout.layout_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_id));

        TextView text = (TextView) layout.findViewById(R.id.texttoast);

        text.setText(textval);

        text.setTextColor(Color.rgb(204, 0, 0));
        text.setBackgroundDrawable(cnt.getResources().getDrawable(R.drawable.bk));
        text.setPadding(10, 10, 10, 10);
        Toast toast = new Toast(cnt.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public static void animation1(ImageView mLogo) {

        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();


    }

    public static void KDMC_COMMON_DILOAGS(int Id, Context ctx, String Title, String Message1, String Message2) {
        MediaPlayer mp = MediaPlayer.create(ctx, R.raw.cloud_tone1);

        switch (Id) {


            //A basic message
            case 1:

              /*  new SweetAlertDialog(ctx)
                        .setTitleText(Title)
                        .show();*/

                new CustomKDMCDialog(ctx)
                        .setImage(R.drawable.ic_success_done)
                        .setTitle(Title).setNegativeButtonVisible(View.GONE)
                        .setPossitiveButtonTitle("OK");

                mp.start();

                break;

            //A title with a text under
            case 2:
                new CustomKDMCDialog(ctx)
                        .setImage(R.drawable.ic_info_black_24dp)
                        .setTitle(Title)
                        .setDescription(Message1)
                        .setNegativeButtonVisible(View.GONE)
                        .setPossitiveButtonTitle("OK")
                        .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                            @Override
                            public void onPossitivePerformed() {

                            }
                        });


                mp.start();
                break;

            //A error message
            case 3:
                new CustomKDMCDialog(ctx)
                        .setLayoutColor(R.color.red_500)
                        .setImage(R.drawable.ic_warning_black_24dp)
                        .setTitle(Title)
                        .setDescription(Message1)
                        .setNegativeButtonVisible(View.GONE)
                        .setPossitiveButtonTitle("OK")
                        .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                            @Override
                            public void onPossitivePerformed() {

                            }
                        });

                mp.start();
                break;
            //A warning message
            case 4:
                new CustomKDMCDialog(ctx)
                        .setLayoutColor(R.color.orange_500)
                        .setImage(R.drawable.ic_warning_black_24dp)
                        .setTitle(Title)
                        .setNegativeButtonVisible(View.GONE)
                        .setDescription(Message1)
                        .setPossitiveButtonTitle("OK")
                        .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                            @Override
                            public void onPossitivePerformed() {

                            }
                        });

                mp.start();
                break;


        }


    }

    public static void SnackBar(Context ctx, String Message, View parentLayout, int id) {

        Snackbar mSnackBar = Snackbar.make(parentLayout, Message, Snackbar.LENGTH_LONG);
        View view = mSnackBar.getView();
        view.setPadding(5, 5, 5, 5);

        if (id == 1)//Positive
        {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.colorPrimary));
        } else if (id == 2)//Negative
        {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.orange_300));
        } else//Negative
        {
            view.setBackgroundColor(ctx.getResources().getColor(R.color.red_400));
        }


        TextView mainTextView = (TextView) (view).findViewById(android.support.design.R.id.snackbar_text);
        mainTextView.setAllCaps(true);
        mainTextView.setTextSize(16);
        mainTextView.setTextColor(ctx.getResources().getColor(R.color.white));
        mSnackBar.setDuration(4000);
        mSnackBar.show();


    }

    public static void ExitSweetDialog(final Context ctx, final Class<?> className) {

        new CustomKDMCDialog(ctx)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_exit_to_app_black_24dp)
                .setTitle(ctx.getResources().getString(R.string.message_title))
                .setDescription(ctx.getResources().getString(R.string.message_exit))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                    @Override
                    public void onPossitivePerformed() {

                        BaseConfig.StopwebService_Import();
                        BaseConfig.StopwebService_Export();
                        ((Activity) ctx).finishAffinity();
                        Log.e("Webservices", "************Stopped***********");
                        Log.e("Webservices", "************Stopped***********");
                    }
                });


    }

    public static void getActionBar(android.support.v7.app.ActionBar bar, int Id) {

        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3d5987")));
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>" + R.string.app_name + "</font>"));

        if (Id == 1) {
            bar.setDisplayUseLogoEnabled(true);
            bar.setIcon(R.drawable.mobydr_launch_icon1);
            bar.setDisplayHomeAsUpEnabled(false);
            bar.setHomeButtonEnabled(false);
            bar.show();
        } else if (Id == 2) {
            bar.setDisplayUseLogoEnabled(true);
            bar.setIcon(R.drawable.mobydr_launch_icon1);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.show();
        }

    }

    public static Typeface GetFontFamily(Context ctx) {
        Typeface fontFamily = Typeface.createFromAsset(ctx.getAssets(), Font_Location);

        return fontFamily;
    }

    public static boolean CheckSpinner2(Spinner spinner) {
        if (spinner.getSelectedItemPosition() > 0) {
            return true;
        } else {
            TextView errorText = (TextView) spinner.getSelectedView();
            errorText.setError("*");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Required");//changes the selected item text to this
            return false;
        }

    }

    public static String getwardNameFromId(String id, Context ctx) {
        String wardId = "";
        try {


            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from mast_ward where ServerId = '" + id.trim() + "'", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        wardId = c.getString(c.getColumnIndex("WardName_NO"));

                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wardId == null) {
            return "";
        }
        return wardId;

    }

    public static String getRoomNameFromId(String name, Context ctx) {
        String roomIdStr = "";
        try {


            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from mast_room where ServerId = '" + name.trim() + "'", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        roomIdStr = c.getString(c.getColumnIndex("Roomno"));

                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (roomIdStr == null) {
            return "";
        }
        return roomIdStr;

    }

    public static String getBedNameFromId(String name, Context ctx) {
        String bedIdStr = "";
        try {


            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from mast_bed where ServerId = '" + name.trim() + "'", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        bedIdStr = c.getString(c.getColumnIndex("Bed"));

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

    public static Integer parseStringForInt(String g) {
        int iNumber = 0;
        try {
            iNumber = Integer.parseInt(g);

        } catch (Exception e) {
            iNumber = 0;

        }

        return iNumber;
    }

    public static String CheckDBString(String str) {
        if (str == null) {
            return "";
        } else if (str.trim().equalsIgnoreCase("null") || str.equalsIgnoreCase("null")) {
            return "";
        } else if (str.length() > 0) {
            return str;
        } else {
            return "-";
        }

    }

    public static String CheckCaseNotesString(String str) {
        if (str == null) {
            return "0";
        } else if (str.trim().equalsIgnoreCase("null") || str.equalsIgnoreCase("null")) {
            return "0";
        } else if (str.length() > 0) {
            return str;
        } else {
            return "0";
        }

    }

    public static void setLocale(String lang, Context ctx, final Class<?> className) {
        switch (AppLanguage) {

            case "English":

                lang = "en";
                break;


            case "Marathi"://Marathi   - मराठी

                lang = "mr";
                break;


        }

   /*     myLocale = new Locale(lang);
        Resources res = ctx.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);*/

        Locale locale = new Locale(lang);
        Configuration config = ctx.getResources().getConfiguration();
        config.locale = locale;
        ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());


    }



    public static void setLocale_new(String lang,Context ctx) {

        try {

            BaseConfig.AppLanguage = lang;
            Locale myLocale = new Locale(lang);
            Resources res = ctx.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void SaveLaguagePreference(String LanguageCode, Context ctx) {
        String language_code = LanguageCode.toString();

        savePreferences(language_code.toString(), ctx);

    }

    private static void savePreferences(String value, Context ctx) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("LANGUAGE_CODE", value);
        edit.apply();
    }

    public static String GetPatientNameFromId(String patId, Context ctx) {


        String bedIdStr = "";
        try {


            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from patreg where patid  = '" + patId.trim() + "'", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        bedIdStr = c.getString(c.getColumnIndex("name"));

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

    public static String GetMedicineIdFromName(String name, Context ctx) {


        String bedIdStr = "";
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            String query = "select distinct * from cims where (CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) ='" + name.toString().trim() + "'";

            Cursor c = db.rawQuery(query, null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        bedIdStr = c.getString(c.getColumnIndex("serverid"));


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

    public static void SetSpinnerValue(Spinner spinner, String value) {

        if (value != null && spinner != null) {
            if (spinner.getAdapter() != null) {
                for (int i = 0; i <= spinner.getAdapter().getCount() - 1; i++) {
                    String compareValue = spinner.getAdapter().getItem(i).toString();

                    if (compareValue.equalsIgnoreCase(value)) {
                        spinner.setSelection(i);
                        return;
                    }
                }
            }

        }

    }

    public static String FormatActDateForGraph(String ActDate) {
        String formatStr = "";
        try {
            if (ActDate.contains(" ")) {
                String actDateArr[] = ActDate.split(" ");
                if (actDateArr[1].contains(":")) {
                    String timeFormatStr[] = actDateArr[1].split(":");
                    String timeExample = String.valueOf(timeFormatStr[0] + ":" + timeFormatStr[1]);
                    formatStr = String.valueOf(actDateArr[0] + "\n" + timeExample + "\n");
                    return formatStr;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return formatStr;
    }

    public static void INSERT_NEW_TREATMENT_FOR(String[] text, Context ctx) {
        try {
            SQLiteDatabase db = GetDb();
            ContentValues values;

            for (int i = 0; i < text.length; i++) {
                boolean q = LoadBooleanStatusCount("select count(treatmentfor) as dstatus from trmntfor where upper(treatmentfor)=upper('" + text[i].toString() + "')", ctx);

                if (q) {

                    values = new ContentValues();
                    values.put("treatmentfor", text[i].toString().trim());
                    values.put("IsNew", "False");
                    values.put("isactive", "True");
                    db.insert(TABLE_TREATMENTFOR, null, values);

                }
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void INSERT_NEW_PROVISIONAL_DIAGNOSIS(String[] text, Context ctx) {
        try {
            SQLiteDatabase db = GetDb();
            ContentValues values;

            for (int i = 0; i < text.length; i++) {
                boolean q = LoadBooleanStatusCount("select count(diagnosisdata) as dstatus from diagonisdetails where upper(diagnosisdata)=upper('" + text[i].toString() + "')", ctx);

                if (q) {

                    values = new ContentValues();
                    values.put("diagnosisdata", text[i].toString().trim());
                    values.put("IsNew", "False");
                    values.put("isactive", "True");
                    db.insert(TABLE_DIAGONISDETAILS, null, values);

                }
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void INSERT_NEW_SIGNS(String[] text, Context ctx) {
        try {
            SQLiteDatabase db = GetDb();
            ContentValues values;

            for (int i = 0; i < text.length; i++) {
                boolean q = LoadBooleanStatusCount("select count(signs) as dstatus from Mstr_Signs where upper(signs)=upper('" + text[i].toString() + "')", ctx);

                if (q) {

                    values = new ContentValues();
                    values.put("signs", text[i].toString().trim());
                    values.put("IsNew", "False");
                    values.put("isactive", "True");
                    db.insert(TABLE_SIGNS, null, values);

                }
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void InsertDatabaseValues(String TableName, ContentValues values, Context ctx) {
        try {
            SQLiteDatabase db = GetDb();
            db.insert(TableName, null, values);
            db.close();
            //Log.e("Inserted Table:" + TableName, values.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetValues(String Query) {
        String str = "";
        SQLiteDatabase db = GetDb();
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    str = c.getString(c.getColumnIndex("ret_values"));

                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();
        return str;
    }

    public static String saveImageToSDCard(String Image, String patientId) {
        try {
            byte[] imageBytes = Base64.decode(Image, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(imageBytes);
            Bitmap image = BitmapFactory.decodeStream(is);

            String mBaseFolderPath = BaseConfig.AppDatabaseName + "/Operation_Remarks";

            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }

            String mFilePath = mBaseFolderPath + "/" + patientId + ".jpg";

            File file = new File(mFilePath);

            FileOutputStream stream = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            is.close();
            image.recycle();

            stream.flush();
            stream.close();

            return mFilePath;
        } catch (Exception e) {
            //Log.e("SaveFile", "" + e);
        }

        return "";
    }

    public static String SaveImageToSDCard(String Image, String patientId) {
        try {
            byte[] imageBytes = Base64.decode(Image, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(imageBytes);
            Bitmap image = BitmapFactory.decodeStream(is);

            String mBaseFolderPath = BaseConfig.AppDatabaseName + "/Patient_Images";

            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }

            String mFilePath = mBaseFolderPath + "/" + patientId + ".jpg";

            File file = new File(mFilePath);

            FileOutputStream stream = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            is.close();
            image.recycle();

            stream.flush();
            stream.close();

            return mFilePath;
        } catch (Exception e) {
            //Log.e("SaveFile", "" + e);
        }

        return "";
    }

    public static void SelectedGetPatientDetailsFilter(String Query, Context ctx, AutoCompleteTextView patientname, String filtervalue) {


        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = GetDb();


        Cursor c = db.rawQuery(Query, null);
        list.clear();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String PATIENT_NAME = c.getString(c.getColumnIndex("name"));
                    String PATIENT_ID = c.getString(c.getColumnIndex("Patid"));

                    String names[] = PATIENT_NAME.toString().split("\\.");

                    String name = names[1].trim();

                    if (name.toLowerCase().startsWith(filtervalue))
                    {
                        list.add(PATIENT_NAME + "-" + PATIENT_ID);
                    }

                } while (c.moveToNext());
            }
        }

        loadSpinner(patientname, list);

        c.close();
        db.close();


    }

    public static void SelectedGetPatientDetailsFilterOthers(String Query, Context ctx, AutoCompleteTextView patientname, String filtervalue) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = GetDb();


        Cursor c = db.rawQuery(Query, null);
        list.clear();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String values = c.getString(c.getColumnIndex("dvalue"));

                    if (values.toLowerCase().startsWith(filtervalue)) {
                        list.add(values);

                    }

                } while (c.moveToNext());
            }
        }

        // spinner2meth(ctx, list, patientname);
        loadSpinner(patientname, list);
        c.close();
        db.close();


    }

    public static void SelectedGetPatientDetailsFilterOthers(String Query, Context ctx, MultiAutoCompleteTextView patientname, String filtervalue) {
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = GetDb();

        String values = "";

        Cursor c = db.rawQuery(Query, null);
        list.clear();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    values = c.getString(c.getColumnIndex("dvalue"));


                    filtervalue = filtervalue.replaceAll(".+,", "");
                    System.out.println(filtervalue);

                    if (values.toString().toLowerCase().startsWith(filtervalue.toString())) {
                        list.add(values);

                    }

                } while (c.moveToNext());
            }
        }

        //Log.e("List: ", values);

        //     spinner2meth(ctx, list, patientname);
        loadSpinner(patientname, list);
        c.close();
        db.close();


    }

    public static String saveImageToSDCard(String base64image, String imagename, String pathname) {
        try {
            byte[] imageBytes = Base64.decode(base64image, Base64.DEFAULT);
            InputStream is = new ByteArrayInputStream(imageBytes);
            Bitmap image = BitmapFactory.decodeStream(is);

            String mBaseFolderPath = BaseConfig.AppDatabaseName + "/" + pathname + "";

            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }

            String mFilePath = mBaseFolderPath + "/" + imagename + ".jpg";

            File file = new File(mFilePath);

            FileOutputStream stream = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            is.close();
            image.recycle();

            stream.flush();
            stream.close();

            return mFilePath;
        } catch (Exception e) {
            //Log.e("SaveFile", "" + e);
        }

        return "";
    }

    public static String saveURLImageToSDCard(Bitmap img, String imagename) {
        try {

            Bitmap image = img;

            String mBaseFolderPath = BaseConfig.AppDatabaseName + "/Patient_Photos/";

            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }

            String mFilePath = mBaseFolderPath + "/" + imagename + ".jpg";

            File file = new File(mFilePath);

            FileOutputStream stream = new FileOutputStream(file);

            if (!file.exists()) {
                file.createNewFile();
            }

            image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            image.recycle();

            stream.flush();
            stream.close();

            return mFilePath;

        } catch (Exception e) {
            //Log.e("SaveFile", "" + e);
        }

        return "";
    }

    public static void Show_Patient_PhotoInDetail(String patientname, String patientId, String agegender, Context ctx) {
        View promptView = LayoutInflater.from(ctx).inflate(R.layout.imageview_zoom_dialog, null);
        final ImageView PatientPhoto_imgvw = (ImageView) promptView.findViewById(R.id.imvw_patientphoto);
        final TextView PatientName = (TextView) promptView.findViewById(R.id.tv_patient_name);
        final TextView PatientAgeGender = (TextView) promptView.findViewById(R.id.tv_patient_agegender);
        final TextView PatientId = (TextView) promptView.findViewById(R.id.tv_patient_id);
        final Button Close = (Button) promptView.findViewById(R.id.cancel);

        PatientName.setText(patientname.toString());
        PatientId.setText(patientId.toString());
        PatientAgeGender.setText(agegender.toString());

        String Query = "select PC as ret_values from Patreg where Patid='" + patientId.toString() + "'";
        String PhotoPath = BaseConfig.GetValues(Query);
        BaseConfig.LoadPatientImage(PhotoPath, PatientPhoto_imgvw, 100);

        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(promptView);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();

        Close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

    }

    public static String DateFormatter(String ServerDate) {
        String return_date = "";

        //9/4/2017 6:16:09 PM
        SimpleDateFormat dateformt = new SimpleDateFormat("MMM-dd-yyyy / hh:mm a",Locale.ENGLISH);
        return_date = dateformt.format(Date.parse(ServerDate));

        return return_date;

    }

    public static String DateFormatter2(String ServerDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss",Locale.ENGLISH);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(ServerDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        String dates = spf.format(convertedDate);


        return dates;
    }

    public static String Imported_DateFormatter2(String ServerDate) {

        SimpleDateFormat dateFormat;//= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa",Locale.ENGLISH);
        if (ServerDate.toString().contains("-")) {
            //"16-11-2017 06:48:21"
            dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss",Locale.ENGLISH);
        } else {
            //"11-16-2017 06:48:21"
            dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa",Locale.ENGLISH);
        }

        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(ServerDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        String dates = spf.format(convertedDate);


        return dates;
    }

    public static String DateFormatter3(String ServerDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS",Locale.ENGLISH);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(ServerDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        String dates = spf.format(convertedDate);


        return dates;
    }

    public static String DeviceDate() {
        String date = "";

        //2017/04/09 19:51:10
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.ENGLISH);
        date = dateformt.format(Date.parse(currentDateTimeString));

        return date;
    }

    public static String Device_OnlyDate() {
        String date = "";

        try {
            //2017/04/09 19:51:10
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
            date = dateformt.format(Date.parse(currentDateTimeString));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String Device_OnlyDateWithHypon() {
        String date = "";

        try {
            //2017/04/09 19:51:10
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
            date = dateformt.format(Date.parse(currentDateTimeString));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String Device_OnlyDate2() {
        String date = "";

        try {
            //2017/04/09 19:51:10
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            SimpleDateFormat dateformt = new SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH);
            date = dateformt.format(Date.parse(currentDateTimeString));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String Device_OnlyDate2WithHypon() {
        String date = "";

        try {
            //2017/04/09 19:51:10
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            SimpleDateFormat dateformt = new SimpleDateFormat("MM-dd-yyyy",Locale.ENGLISH);
            date = dateformt.format(Date.parse(currentDateTimeString));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String CompareWithDeviceDate() {
        String date = "";

        try {
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            SimpleDateFormat dateformt = new SimpleDateFormat("MMM-dd-yyyy",Locale.ENGLISH);
            date = dateformt.format(Date.parse(currentDateTimeString));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;

    }

    public static String CompareWithDeviceDate(String strdate) throws ParseException {

       /* //Log.e("CompareWithDeviceDate: 1",strdate );


        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy/mm/dd");
        SimpleDateFormat writeFormat = new SimpleDateFormat("MMM-dd-yyyy");

        java.util.Date date;

        date = readFormat.parse(strdate);

        Log.v("Changed date",""+writeFormat.format(date));
        Log.v("Changed date",""+writeFormat.format(date));*/

        String str = convertDate(strdate);

        return str;

    }

    private static String convertDate(String va) {

        String str = "";

        DateTime dateTime = null;
        try {
            if (va.contains("/")) {
                str = "yyyy/MM/dd";
            } else {
                str = "yyyy-MM-dd";
            }

            DateTimeFormatter dtf_inp = DateTimeFormat.forPattern(str);


            dateTime = dtf_inp.withZone(DateTimeZone.forID("Asia/Kolkata"))
                    .parseDateTime(va);


            System.out.println(dateTime.toString("MMM-dd-yyyy"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateTime.toString("MMM-dd-yyyy");
    }

    public static String ChangeDateFormat(String date) {
        String date_return = "";

        try {
            SimpleDateFormat dateformt = new SimpleDateFormat("MMM-dd-yyyy",Locale.ENGLISH);
            date_return = dateformt.format(Date.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date_return;
    }

    public static Dialog showCustomDialog(String title, String message, Context ctx) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View inflatedLayout = inflater.inflate(R.layout.popup_layout, null, false);
        Dialog builderDialog = new Dialog(ctx);
        builderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        TextView messageView = (TextView) inflatedLayout.findViewById(R.id.message);
        TextView titleView = (TextView) inflatedLayout.findViewById(R.id.title);
        ImageView image = (ImageView) inflatedLayout.findViewById(R.id.load_image);
        // Create an animation instance
        Animation an;// = new RotateAnimation(0.0f, 360.0f, 1, 1);
        an = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // Set the animation's parameters
        an.setDuration(300);               // duration in ms
        an.setRepeatCount(Animation.INFINITE);                // -1 = infinite repeated
        //an.setRepeatMode(Animation.RESTART); // reverses each repeat
        an.setFillAfter(true);               // keep rotation after animation

        // Aply animation to image view
        image.setAnimation(an);

        messageView.setText(message);
        titleView.setText(title);
        builderDialog.setContentView(inflatedLayout);
        builderDialog.setCancelable(false);
        builderDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation2;
        //builderDialog.show();
        return builderDialog;
    }

    public static String saveURLImagetoSDcard(Bitmap bitmap, String imagename) {


        if (!new File(Patient_Photo_Location).exists()) {
            new File(Patient_Photo_Location).mkdir();
        }

        String mFilePath = Patient_Photo_Location + imagename + ".png";


        File mypath = new File(mFilePath);

        if (mypath.exists()) {
            mypath.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mFilePath;

    }

    public static String saveURLImagetoSDcard1(Bitmap bitmap, String imagename) {
        String mBaseFolderPath = BaseConfig.AppDBFolderName + "/Test_Images/";

        if (!new File(mBaseFolderPath).exists()) {
            new File(mBaseFolderPath).mkdir();
        }

        String mFilePath = mBaseFolderPath + imagename + ".png";

        File mypath = new File(mFilePath);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mFilePath;

    }

    public static String saveURLImagetoSDcard2(Bitmap bitmap, String imagename) {
        String mBaseFolderPath = BaseConfig.AppDatabaseName + "/OnlineshareReports/";

        if (!new File(mBaseFolderPath).exists()) {
            new File(mBaseFolderPath).mkdir();
        }

        String mFilePath = mBaseFolderPath + imagename + ".png";

        File mypath = new File(mFilePath);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mFilePath;

    }

    public static String[] filterValues(String filtervalue, String[] filter) {
        String[] nevalue;


        String OrigionalFilterValue = null;


        int starts = filtervalue.lastIndexOf(",") + 1;

        System.out.println(filtervalue);


        //Origional FilterValue
        OrigionalFilterValue = filtervalue.substring(starts, filtervalue.length()).trim();


        //Log.e("Origional", OrigionalFilterValue);


        //Filter Value is not Empty
        if (!OrigionalFilterValue.isEmpty() || OrigionalFilterValue != "") {

            int j = 0;

            for (int i = 0; i < filter.length; i++) {


                if (filter[i].toLowerCase().trim().startsWith(OrigionalFilterValue)) {

                    ++j;
                }

            }

            nevalue = new String[j];
            j = 0;

            for (int i = 0; i < filter.length; i++) {


                if (filter[i].toLowerCase().trim().startsWith(OrigionalFilterValue)) {

                    nevalue[j] = filter[i];
                    //Log.e("Filtered Values", filter[i]);

                    ++j;
                }

            }


            return nevalue;

        }
        return filter;
    }

    public static boolean CheckNetwork(Context ctx) {
        ConnectivityManager cn = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {

            return true;
        } else {

            return false;
        }
    }

    public static void setEdittextDrawable(EditText edt) {
        Drawable rightDrawable = AppCompatResources.getDrawable(edt.getContext(), R.drawable.ic_clear_button);
        if (edt.getText().length() > 0) {
            edt.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

            edt.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (edt.getRight() - edt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action here

                            return true;
                        }
                    }
                    return false;
                }
            });

        } else {
            edt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            edt.setOnTouchListener(null);

        }

    }

    public static void loadSpinner(Spinner spinner, List<String> list) {
        try {
            spinner.setAdapter(new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_list_item_1, list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSpinner(AutoCompleteTextView spinner, List<String> list) {
        try {
            spinner.setAdapter(new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_list_item_1, list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSpinner(MultiAutoCompleteTextView spinner, List<String> list) {
        try {
            spinner.setAdapter(new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_list_item_1, list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSpinner(Spinner spinner, String[] list) {
        try {
            spinner.setAdapter(new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_list_item_1, list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Glide_LoadImageView(ImageView imgvw, String location) {
        try {

            Glide.with(imgvw.getContext()).load(new File(location)).into(imgvw);//.onLoadFailed(imgvw.getResources().getDrawable(R.drawable.no_media));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Glide_LoadImageView(AppCompatImageView imgvw, String location) {
        try {

            Glide.with(imgvw.getContext()).load(new File(location)).into(imgvw);//.onLoadFailed(imgvw.getResources().getDrawable(R.drawable.no_media));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Glide_LoadDefaultImageView(ImageView imgvw) {
        Glide.with(imgvw.getContext()).load(Uri.parse("file:///android_asset/image.png")).into(imgvw);//.onLoadFailed(imgvw.getResources().getDrawable(R.drawable.no_media));
    }

    public static Bitmap Glide_GetBitmap(Context ctx, String path) {
        try {
            Bitmap ret_bitmap = null;

            ret_bitmap = Glide.with(ctx).asBitmap().load(path).into(150, 150).get();

            return ret_bitmap;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void HideKeyboard(Context ctx) {
        if (((Activity) ctx).getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) ((Activity) ctx).getSystemService(INPUT_METHOD_SERVICE);
            ;
            inputMethodManager.hideSoftInputFromWindow(((Activity) ctx).getCurrentFocus().getWindowToken(), 0);
        }
        ((Activity) ctx).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public static String[] convertListtoStringArray(List<String> liststring) {

        String[] ValueArr = new String[liststring.size()];
        ValueArr = liststring.toArray(ValueArr);

        return ValueArr;
    }

    public static String GeneratePatientRegistrationID() {
        String str = "";

        str = "PID/" + GetCurrentYear() + "/" + GetCurrentMonth() + GetCurrentDay() + "/" + RandomUtils.getRandomNumbers(3);

        Log.e("GeneratePatientID: ", str);
        if (str != null) {

            //  05-03 19:44:20.465 17525-17525/displ.mobydocmarathi.com E/GenerateCaseNotesID:: DID/15253568604523934/1
            return str;
        } else {
            return "";
        }

    }

    public static String GeneratePatientLoginPin() {
        String str = "";
        str = RandomUtils.getRandomNumbers(4);
        return str;
    }


    public static String GenerateClinicalID() {
        String str = "";

        str = "CID/" + TimeUtils.getCurrentTimeInLong() + RandomUtils.getRandomNumbers(4);

        Log.e("GenerateClinicalID: ", str);
        if (str != null) {

            //  05-03 19:44:20.465 17525-17525/displ.mobydocmarathi.com E/GenerateCaseNotesID:: DID/15253568604523934/1
            return str;
        } else {
            return "";
        }

    }

    public static String GenerateCaseNotesID() {
        String str = "";

        str = "DID/" + TimeUtils.getCurrentTimeInLong() + RandomUtils.getRandomNumbers(4) + "/" + BaseConfig.GetValues("select mdiagnum as ret_values from Diagnosismvalue;");

        Log.e("GenerateCaseNotesID: ", str);
        if (str != null) {

            //  05-03 19:44:20.465 17525-17525/displ.mobydocmarathi.com E/GenerateCaseNotesID:: DID/15253568604523934/1
            return str;
        } else {
            return "";
        }

    }

    public static String GenerateInvestigationID() {
        String str = "";

        str = "MTID/" + TimeUtils.getCurrentTimeInLong() + RandomUtils.getRandomNumbers(4) + "/" + BaseConfig.GetValues("select mdiagnum as ret_values from Diagnosismvalue;");

        Log.e("GenerateInvestigationID: ", str);
        if (str != null) {

            //  05-03 19:44:20.465 17525-17525/displ.mobydocmarathi.com E/GenerateCaseNotesID:: MTID/15253568604523934/1
            return str;
        } else {
            return "";
        }

    }

    public static int GetCurrentYear() {
        // get current year、month and day
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        return year;
    }

    public static int GetCurrentMonth() {
        // get current year、month and day
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        return month;
    }

    public static int GetCurrentDay() {
        // get current year、month and day
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);

        return day;
    }


    /**
     * All control basic operations
     * Muthukumar N
     * 03-05-2018
     */
    public static String GetWidgetOperations(View control, int id) {
        // android.support.v7.widget.AppCompatAutoCompleteTextView
        // 05-03 19:44:20.435 17525-17525/displ.mobydocmarathi.com E/check 2:: class android.support.v7.widget.AppCompatAutoCompleteTextView
        //Log.e("check 1: ",control.getClass().getName().toString());
        // Log.e("check 2: ",control.getClass().toString());
        String str = "";

        if (control instanceof EditText) {

            EditText edt = (EditText) control;
            str = EditTextOperations(edt, id, "", "");

        }  else if (control instanceof AutoCompleteTextView) {
            AutoCompleteTextView edt = (AutoCompleteTextView) control;
            str = AutoCompleteTextViewOperations(edt, id, "", "");

        } else if (control instanceof MultiAutoCompleteTextView) {
            MultiAutoCompleteTextView edt = (MultiAutoCompleteTextView) control;
            str = MultiAutoCompleteTextViewOperations(edt, id, "", "");

        } else if (control instanceof ToggleButton) {
            ToggleButton tbg = (ToggleButton) control;
            str = ToggleButtonOperations(tbg, id, "");

        } else if (control instanceof LabeledSwitch) {
            LabeledSwitch tbg = (LabeledSwitch) control;
            str = LabeledSwitchOperations(tbg, id, "");

        } else if (control instanceof CheckBox) {
            CheckBox chk = (CheckBox) control;
            str = CheckBoxButtonOperations(chk, id, "");

        } else if (control instanceof RadioButton) {
            RadioButton rbtn = (RadioButton) control;
            str = RadioButtonOperations(rbtn, id, "");

        } else if (control instanceof RadioGroup) {
            RadioGroup rgrp = (RadioGroup) control;
            str = RadioGroupOperations(rgrp, id, "");

        } else if (control instanceof Switch) {
            Switch swt = (Switch) control;
            str = SwitchButtonOperations(swt, id, "");

        } else if (control instanceof Spinner) {
            Spinner spn = (Spinner) control;

            str = SpinnerOperations(spn, id);

        } else if(control instanceof  SeekBar)
        {
            SeekBar seekbar = (SeekBar) control;
            str = SeekBarOperations(seekbar,id,"" ,"");

        } else if (control instanceof TextView) {
            TextView txtvw = (TextView) control;
            str = TextViewOperations(txtvw, id, "", "");

        }

        return str;

    }

    public static String SeekBarOperations(SeekBar seekbar, int id, String setdata, String errormsg)
    {
        String str="";
        switch(id)
        {
            case 1://Get text
                int p = seekbar.getProgress();
                str=String.valueOf(p);
                break;

        }

        return str;

    }

    /**
     * All control basic operations - EDIT_TEXT
     * Muthukumar N
     * 04-05-2018
     */
    public static String EditTextOperations(EditText edt, int id, String setdata, String errormsg) {
        String str = "";
        switch (id) {
            case 1://Get text
                str = edt.getText().toString();
                break;

            case 2://Set text
                edt.setText(setdata);
                break;

            case 3://set enable - true
                edt.setEnabled(true);
                break;

            case 4://set enable - false
                edt.setEnabled(false);
                break;

            case 5://get text length
                if (edt.getText().length() > 0) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;

            case 6://set error
                edt.setError(errormsg);
                break;
        }


        return str;

    }

    public static String TextViewOperations(TextView edt, int id, String setdata, String errormsg) {
        String str = "";
        switch (id) {
            case 1://Get text
                str = edt.getText().toString();
                break;

            case 2://Set text
                edt.setText(setdata);
                break;

            case 3://set enable - true
                edt.setEnabled(true);
                break;

            case 4://set enable - false
                edt.setEnabled(false);
                break;

            case 5://get text length
                if (edt.getText().length() > 0) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;

            case 6://set error
                edt.setError(errormsg);
                break;
        }


        return str;

    }

    public static String AutoCompleteTextViewOperations(AutoCompleteTextView edt, int id, String setdata, String errormsg) {
        String str = "";
        switch (id) {
            case 1://Get text
                str = edt.getText().toString();
                break;

            case 2://Set text
                edt.setText(setdata);
                break;

            case 3://set enable - true
                edt.setEnabled(true);
                break;

            case 4://set enable - false
                edt.setEnabled(false);
                break;

            case 5://get text length
                if (edt.getText().length() > 0) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;

            case 6://set error
                edt.setError(errormsg);
                break;

            case 7:
                edt.setThreshold(1);
                break;

        }


        return str;

    }

    public static String MultiAutoCompleteTextViewOperations(MultiAutoCompleteTextView edt, int id, String setdata, String errormsg) {
        String str = "";
        switch (id) {
            case 1://Get text
                str = edt.getText().toString();
                break;

            case 2://Set text
                edt.setText(setdata);
                break;

            case 3://set enable - true
                edt.setEnabled(true);
                break;

            case 4://set enable - false
                edt.setEnabled(false);
                break;

            case 5://get text length
                if (edt.getText().length() > 0) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;

            case 6://set error
                edt.setError(errormsg);
                break;

            case 7:
                edt.setThreshold(1);
                break;

        }
        return str;
    }

    public static String LabeledSwitchOperations(LabeledSwitch toggleButton, int id, String setdata) {
        String str = "";
        switch (id) {

            case 1:
                if (toggleButton.isOn()) {
                    str = toggleButton.getLabelOn();
                } else {
                    str = toggleButton.getLabelOff();
                }
                break;
        }

        return str;
    }

    public static String ToggleButtonOperations(ToggleButton toggleButton, int id, String setdata) {
        String str = "";
        switch (id) {
            case 1://get text
                str = toggleButton.getTextOn().toString();
                break;

            case 2://set on text
                toggleButton.setTextOn(setdata);
                break;

            case 3://set off text
                toggleButton.setTextOff(setdata);
                break;

            case 4:
                if (toggleButton.isChecked()) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;
        }

        return str;
    }

    public static String SpinnerOperations(Spinner spn, int id) {
        String str = "";
        switch (id) {
            case 1:
                if (spn.getSelectedItemPosition() > 0) {
                    str = spn.getSelectedItem().toString();
                }
                break;


        }

        return str;

    }

    public static String SwitchButtonOperations(Switch toggleButton, int id, String setdata) {
        String str = "";
        switch (id) {
            case 1://get text
                str = toggleButton.getTextOn().toString();
                break;

            case 2://set on text
                toggleButton.setTextOn(setdata);
                break;

            case 3://set off text
                toggleButton.setTextOff(setdata);
                break;

            case 4:
                if (toggleButton.isChecked()) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;
        }

        return str;
    }
    // Common methods to get all control values

    public static String RadioGroupOperations(RadioGroup rdgrp, int id, String setdata) {
        String str = "";
        switch (id) {
            case 1:
                // Get the checked Radio Button ID from Radio Group
                int selectedRadioButtonID = rdgrp.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = (RadioButton) ((Activity) rdgrp.getContext()).findViewById(selectedRadioButtonID);
                    String selectedRadioButtonText = selectedRadioButton.getText().toString();
                    str = selectedRadioButtonText;
                } else {
                    str = "-";
                }
                break;

            case 2:
                // Get the checked Radio Button ID from Radio Group
                selectedRadioButtonID = rdgrp.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID == -1) {
                    str = "false";
                } else {
                    str = "true";
                }

                break;

        }

        return str;
    }

    public static String RadioButtonOperations(RadioButton rbtn, int id, String setdata) {
        String str = "";
        switch (id) {
            case 1:
                if (rbtn.isChecked()) {
                    str = rbtn.getText().toString();
                }else {
                    str="";
                }

                break;

            case 2:
                rbtn.setText(setdata);
                break;

            case 3:
                if (rbtn.isChecked()) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;
        }

        return str;
    }

    public static String CheckBoxButtonOperations(CheckBox rbtn, int id, String setdata) {
        String str = "";
        switch (id) {
            case 1:
                if (rbtn.isChecked()) {
                    str = rbtn.getText().toString();
                }else {
                    str="";
                }

                break;

            case 2:
                rbtn.setText(setdata);
                break;

            case 3:
                if (rbtn.isChecked()) {
                    str = "true";
                } else {
                    str = "false";
                }
                break;
        }

        return str;
    }

    /**
     * Android app configuration
     * 07-05-2018
     */
    public static void LoadAppConfiguration(Context ctx) {


        Appname = new LocalSharedPref(ctx).getValue("Appname");
        AppLanguage = new LocalSharedPref(ctx).getValue("AppLanguage");
        AppWebServiceType = new LocalSharedPref(ctx).getValue("AppWebServiceType");
        AppNodeIP = new LocalSharedPref(ctx).getValue("AppNodeIP");
        AppImagephpURL = new LocalSharedPref(ctx).getValue("AppImagephpURL");
        AppDBFolderName = new LocalSharedPref(ctx).getValue("AppDBFolderName");
        AppDatabaseName = new LocalSharedPref(ctx).getValue("AppDatabaseName");
        AppDirectoryPictures = new LocalSharedPref(ctx).getValue("AppDirectoryPictures");
        AppLogo = new LocalSharedPref(ctx).getValue("AppLogo");


    }

    public static void checkNodeServer(ImageView imgview) throws InterruptedException {

        new Thread() {

            Handler messageHandler = new Handler() {
                public void handleMessage(Message msg) {

                    super.handleMessage(msg);
                    if (server_connectivity_status) {
                        server_connectivity_status = true;
                        if (imgview != null) {
                            imgview.setImageResource(R.drawable.ic_status_ready);
                        }
                    } else {
                        server_connectivity_status = false;

                        if (imgview != null) {
                            imgview.setImageResource(R.drawable.ic_status_busy);
                        }
                    }
                }
            };

            public void run() {

                CheckServerConnection();
                messageHandler.sendEmptyMessage(0);
            }


        }.start();


    }

    public static void CheckServerConnection() {
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(BaseConfig.AppNodeIP)).openConnection();
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();

            if (status == 200) {
                server_connectivity_status = true;
            } else {
                server_connectivity_status = false;
            }

            conn.disconnect();

        } catch (Exception e) {
            Log.e("doHttpGetRequest", e.toString());
        }
    }

    /**
     * Global start intent
     *
     * @param context
     * @param classs
     * @param bundle
     */
    public static void globalStartIntent(Context context, Class classes, Bundle bundle) {
        ((Activity) context).finish();
        Intent intent = new Intent(context, classes);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        CustomIntent.customType(context, 4);
        context.startActivity(intent);

    }

    public static void globalStartIntent2(Context context, Class classes, Bundle bundle) {
        ((Activity) context).finish();
        Intent intent = new Intent(context, classes);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        CustomIntent.customType(context, 1);
        context.startActivity(intent);

    }

    public static void AutocompleteTextChange(AutoCompleteTextView autoCompleteTextView, String Query) {
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (autoCompleteTextView.getText().toString().length() > 0) {

                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, autoCompleteTextView.getContext(), autoCompleteTextView, s.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static void HighlightMandatory(String LabelName, TextView textview) {
        String starsymbol = "<font color='#EE0000'><b>*</b></font>";
        textview.setText(Html.fromHtml(LabelName + starsymbol));
    }

    public static void ClearError(EditText edt_text) {

        edt_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (edt_text.getError() != null) {
                    edt_text.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public BaseConfig getInstance() {
        if (baseConfig == null) {
            baseConfig = new BaseConfig();
            return baseConfig;
        }

        return baseConfig;
    }

    private void getinitialize() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        final Calendar cc = Calendar.getInstance();
        mcYear = cc.get(Calendar.YEAR);
        mcMonth = cc.get(Calendar.MONTH) + 1;
        mcDay = cc.get(Calendar.DAY_OF_MONTH);

    }

    public void LoadMasterValues(ListView lstView, Context cntxt, String Query, int scanld) {
        try {

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);

            List<String> list = new ArrayList<String>();

            ListDataAdapter = new ArrayAdapter<String>(cntxt, R.layout.simple_list, list);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        if (scanld == 1) {    //trim added to check null in diagnosis data
                            String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                            list.add(counrtyname);
                        } else if (scanld == 2) {
                            String counrtyname = c.getString(c.getColumnIndex("dvalue1")) + "-" + c.getString(c.getColumnIndex("dvalue2"));
                            list.add(counrtyname);
                        } else {
                            String counrtyname = c.getString(c.getColumnIndex("dvalue1")) + "," + c.getString(c.getColumnIndex("dvalue2"));
                            list.add(counrtyname);
                        }

                    } while (c.moveToNext());
                }
            }

            ArrayAdapter<String> CountryDataAdapter = new ArrayAdapter<String>(cntxt, R.layout.simple_list, list);
            CountryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ListDataAdapter = CountryDataAdapter;

            lstView.setAdapter(CountryDataAdapter);

            c.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public boolean LoadBooleanStatusPin(String Query) {
        try {
            int havcount = 0;

            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        DecryptPin = new String(Base64.decode(c.getString(c.getColumnIndex("dstatus")), Base64.DEFAULT));
                        havcount++;
                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

            return havcount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void LoadSeekBar(final TextView lultextView, SeekBar lulseekBar) {
        lultextView.setText(lulseekBar.getProgress() + "/" + lulseekBar.getMax());
        lulseekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                lultextView.setText(progress + "/" + seekBar.getMax());
            }
        });

    }

    public void LoadUserValues() {
        try {

            String Query = "";
            SQLiteDatabase db = GetDb();
            Query = "select username,password,doctorname,clinicname,status,dremail,drid from users";
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        BaseConfig.username = c.getString(c
                                .getColumnIndex("username"));
                        BaseConfig.doctorname = c.getString(c
                                .getColumnIndex("doctorname"));
                        BaseConfig.clinicname = c.getString(c
                                .getColumnIndex("clinicname"));
                        BaseConfig.doctorId = c.getString(c
                                .getColumnIndex("drid"));

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void showSelectColoursDialogPrevMedHistory(Context cntxt,
                                                      final EditText etxt, final String stricon) {

        boolean[] checkedColours = new boolean[items1.length];
        int count = items1.length;

        for (int i = 0; i < count; i++)
            checkedColours[i] = selectedColours1.contains(items1[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialog, int which,
                                boolean isChecked) {
                if (isChecked) {
                    selectedColours1.add(items1[which]);

                } else {
                    selectedColours1.remove(items1[which]);
                }

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);

        if (stricon == "Medical") {
            builder.setTitle(R.string.popup_medical);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Alergy") {
            builder.setTitle(R.string.popup_allergic);//.setIcon(R.drawable.allergic_icon);
        } else if (stricon == "Hereditary") {
            builder.setTitle(R.string.popup_hereditary);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Academic") {
            builder.setTitle(R.string.popup_academic);

        } else if (stricon == "Specialization") {
            builder.setTitle(R.string.popup_specialization);

        }

        builder.setMultiChoiceItems(items1, checkedColours,
                coloursDialogListener);
        builder.setPositiveButton(cntxt.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();

                for (CharSequence colour : selectedColours1) {
                    stringBuilder.append(colour + "\n");
                }

                etxt.setText(stringBuilder.toString());

            }
        });
        builder.setNegativeButton(cntxt.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new StringBuilder();

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void showSelectColoursDialogendocrinesys(Context cntxt,
                                                    final EditText etxt, final String stricon) {

        boolean[] checkedColours = new boolean[items7.length];
        int count = items7.length;

        for (int i = 0; i < count; i++)
            checkedColours[i] = selectedColours9.contains(items7[i]);

        DialogInterface.OnMultiChoiceClickListener coloursDialogListener = (dialog, which, isChecked) -> {
            if (isChecked) {
                selectedColours9.add(items7[which]);

            } else {
                selectedColours9.remove(items7[which]);
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);

        if (stricon == "Medical") {
            builder.setTitle(R.string.popup_medical);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Alergy") {
            builder.setTitle(R.string.popup_allergic);//.setIcon(R.drawable.allergic_icon);
        } else if (stricon == "Hereditary") {
            builder.setTitle(R.string.popup_hereditary);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Academic") {
            builder.setTitle(R.string.popup_academic);

        } else if (stricon == "Specialization") {
            builder.setTitle(R.string.popup_specialization);

        } else if (stricon == "Endo") {
            builder.setTitle(R.string.str_endocrine);

        }

        builder.setMultiChoiceItems(items7, checkedColours,
                coloursDialogListener);
        builder.setPositiveButton(cntxt.getResources().getString(R.string.ok), (dialog, which) -> {
            StringBuilder stringBuilder = new StringBuilder();

            for (CharSequence colour : selectedColours9) {
                stringBuilder.append(colour + ",\n");

            }

            etxt.setText(stringBuilder.toString());

        });
        builder.setNegativeButton(cntxt.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new StringBuilder();

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void showSelectColoursDialogAlergy(Context cntxt,
                                              final EditText etxt, final String stricon) {

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
                    selectedColours.remove(items[which]);
                }

            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);

        if (stricon == "Medical") {
            builder.setTitle(R.string.popup_medical);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Allergy") {
            builder.setTitle(R.string.popup_allergic);//.setIcon(R.drawable.allergic_icon);
        } else if (stricon == "Hereditary") {
            builder.setTitle(R.string.popup_hereditary);//.setIcon(R.drawable.medical_history_icon);
        } else if (stricon == "Academic") {
            builder.setTitle(R.string.popup_academic);

        } else if (stricon == "Specialization") {
            builder.setTitle(R.string.popup_specialization);

        } else if (stricon == "Endo") {
            builder.setTitle(R.string.str_endocrine);

        }

        builder.setMultiChoiceItems(items, checkedColours,
                coloursDialogListener);
        builder.setPositiveButton(cntxt.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();

                for (CharSequence colour : selectedColours) {
                    stringBuilder.append(colour + "\n");

                }

                etxt.setText(stringBuilder.toString());

            }
        });
        builder.setNegativeButton(cntxt.getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new StringBuilder();

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void LoadPatientDetailswithtreatmentfor(String Query,
                                                   TextView txttreatment) {
        try {
            SQLiteDatabase db = GetDb();
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        txttreatment.setText(c.getString(c
                                .getColumnIndex("treatmentfor")));

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ThreecolumnReport(String query) {

        fircolumn = "";
        seccolumn = "";
        thirdcolumn = "";
        totcolumn = "";

        StringBuilder pat_name = new StringBuilder();
        StringBuilder date_time1 = new StringBuilder();
        StringBuilder Docname = new StringBuilder();


        int i = 0;

        SQLiteDatabase db = GetDb();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    pat_name.append(c.getString(c.getColumnIndex("patname")));
                    pat_name.append("\n\n");
                    date_time1.append(c.getString(c.getColumnIndex("actdate")));
                    date_time1.append("\n\n");
                    Docname.append(c.getString(c.getColumnIndex("docname")));
                    Docname.append("\n\n");
                    i++;

                } while (c.moveToNext());

                totcolumn = String.valueOf(i);
            }

            fircolumn = pat_name.toString();
            seccolumn = Docname.toString();
            thirdcolumn = date_time1.toString();
        }

        c.close();
        //database.close();
        db.close();
    }

    public void showSimplePopUpExit(final Context cntxt, final Class<?> cls,
                                    String message, String msgtitle, String btn1, String btn2, int id) {

        /********
         * Alert Dialog
         **********/


        AlertDialog.Builder builder = new AlertDialog.Builder(cntxt);

        builder.setTitle(msgtitle).setMessage("Message").setCancelable(false);

        // single button
        if (id == 1) {
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton(btn1,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            ((Activity) cntxt).finish();
                            Intent intent = new Intent();
                            intent.setClass(cntxt, cls);
                            cntxt.startActivity(intent);

                        }
                    });
        } else if (id == 2) {
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton(btn1,
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            ((Activity) cntxt).finish();

                        }
                    });

        }

        AlertDialog alert = builder.create();
        alert.show();


    }

    public void previewCapturedImage(final ImageView img, ImageButton btn_left,
                                     ImageButton btn_right, ImageButton btn_reset, String strimg) {
        try {
            Bitmap bitmap = null;
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            // showIssuePopUp(mediaStorageDir.getPath()) ;
            if (strimg.equals("doctor")) {
                bitmap = BitmapFactory.decodeFile(mediaStorageDir.getPath() + File.separator + "doctor.jpg", options);
            } else if (strimg.equals("patient")) {
                bitmap = BitmapFactory.decodeFile(mediaStorageDir.getPath()
                        + File.separator + "patient.jpg", options);
            } else if (strimg.equals("assistant")) {
                bitmap = BitmapFactory.decodeFile(mediaStorageDir.getPath()
                        + File.separator + "assist.jpg", options);
            }

            img.setImageBitmap(bitmap);

            Matrix matrix = new Matrix();

            matrix.postRotate(90);

            final Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

            /*final Bitmap */
            rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

            img.setImageBitmap(rotatedBitmap);


			/*	bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
            img.setImageBitmap(bitmap);
			 *//**
             * Rotate a bitmap clock
             *//*
            btn_clock = (Button) findViewById(R.id.btn_clockWise);
			btn_clock.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Matrix mMatrix = new Matrix();
					Matrix mat=img.getImageMatrix();
					mMatrix.set(mat);
					mMatrix.setRotate(90);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
							bitmap.getHeight(), mMatrix, false);
					img.setImageBitmap(bitmap);
				}
			});
			  */

			/*btn_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Matrix matrix = new Matrix();
					Matrix mat=img.getImageMatrix();
					matrix.set(mat);
					matrix.setRotate(90);
					rotatedBitmap = Bitmap.createBitmap(rotatedBitmap, 0,0, rotatedBitmap.getWidth(),rotatedBitmap.getHeight(), matrix, true);
					img.setImageBitmap(rotatedBitmap);
				}

			});

			btn_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Matrix matrix = new Matrix();
					matrix.postRotate(180);
					Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0,
							0, scaledBitmap.getWidth(),
							scaledBitmap.getHeight(), matrix, true);
					img.setImageBitmap(rotatedBitmap);
				}
			});
			btn_reset.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0,
							0, scaledBitmap.getWidth(),
							scaledBitmap.getHeight(), matrix, true);
					img.setImageBitmap(rotatedBitmap);
				}
			});
			 */
            btn_left.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                    Matrix matrix = new Matrix();
                    Matrix mat = img.getImageMatrix();
                    matrix.set(mat);
                    matrix.setRotate(-90);
                    rotatedBitmap = Bitmap.createBitmap(rotatedBitmap, 0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), matrix, true);
                    img.setImageBitmap(rotatedBitmap);
                }

            });


            btn_right.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                    Matrix matrix = new Matrix();
                    Matrix mat = img.getImageMatrix();
                    matrix.set(mat);
                    matrix.setRotate(90);
                    rotatedBitmap = Bitmap.createBitmap(rotatedBitmap, 0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), matrix, true);
                    img.setImageBitmap(rotatedBitmap);
                }
            });
            btn_reset.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    img.setImageBitmap(rotatedBitmap);
                }
            });

            BaseConfig.SelectedBitmap = rotatedBitmap;
            BaseConfig.imgzom = rotatedBitmap;

        } catch (NullPointerException e) {

            e.printStackTrace();
        }
    }

    public void Convert64ToImage(String base64, ImageView img) {
        // BaseConfig.Zoomimg=base64;

        byte[] decodedString = Base64.decode(base64.trim(), Base64.DEFAULT);
        // bimatp factory
        BitmapFactory.Options options = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 4;

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options);

        // setting the decodedByte to ImageView
        img.setImageBitmap(decodedByte);

        BaseConfig.SelectedBitmap = decodedByte;


        // bimatp factory
        BitmapFactory.Options options1 = new BitmapFactory.Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options1.inSampleSize = 1;

        Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        BaseConfig.imgzom = decodedByte1;

    }

    public void InsertData_To_Local_Database(String Query) {

        SQLiteDatabase db = GetDb();
        db.beginTransactionNonExclusive();
        SQLiteStatement stmt = db.compileStatement(Query);
        stmt.execute();
        stmt.clearBindings();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

    }


    public static String GetCurrentTime()
    {
        String str= null;
        try {

            str = "";
            SimpleDateFormat sdf4 = new SimpleDateFormat("h:mm a");
            String currentDateandTime = sdf4.format(new Date());     //8:29 PM
            str= currentDateandTime;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

//**************************************************************************************************
}//END
