package kdmc_kumar.Core_Modules;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.OnlineConsultationReport;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Adapters_GetterSetter.OnlineConsultation_Detailed_Adapter;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Validation1;

public class OnlineConsultation_Details extends AppCompatActivity {

    private static final Bitmap reportimg;
    TextView scrolltitle;
    private TextView Patient_Id;
    private TextView patient_name;
    private TextView Age;
    private TextView Gender;
    private TextView treatmentfor;
    private TextView patient_query;
    private TextView visiteddate;
    private EditText Docreply;
    private ImageView Patient_photo;
    private Button reject;
    private Button cancel;
    private Button reply;
    ArrayList<HashMap<String, String>> titles_list;
    private OnlineConsultation_Detailed_Adapter adapter;
    private RecyclerView listView;
    private ArrayList<OnlineConsultationReport> rowItems;
    private Bundle b;
    private String ServerId;
    private String PatientId;
    private String MedId;

    private WebView profile_webvw;
    /**********************************************************************************************/


    /*
      Code rewriten by Muthukumar
      06/04/2017
      @param savedInstanceState
     */
    /***********************************************************************************************/
    private Toolbar toolbar;
    private ImageView home;
    private ImageView exit;
    private ImageView back;

    public OnlineConsultation_Details() {
    }


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stubsuper.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        this.setContentView(layout.kdmc_layout_onlineconsultation_details);


        //getSupportActionBar().setTitle(getString(R.string.welcome) + " - " + BaseConfig.doctorname);
        BaseConfig.welcometoast = 0;

        this.toolbar = this.findViewById(id.toolbar_mypatient);


        this.home = this.toolbar.findViewById(id.home_mp);
        this.exit = this.toolbar.findViewById(id.exit_mp);
        this.back = this.toolbar.findViewById(id.back_mp);


        this.home.setOnClickListener(v -> {

            finish();
            Intent intent = new Intent(this, Dashboard_NavigationMenu.class);
            this.startActivityForResult(intent, 500);
            this.overridePendingTransition(anim.abc_slide_in_top,
                    anim.abc_slide_in_top);

        });

        this.exit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.back.setOnClickListener(view -> this.LoadBack());


        try {
            try {

                this.b = this.getIntent().getExtras();

                if (this.b == null) {
                    this.ServerId = "";
                    this.PatientId = "";
                    this.MedId = "";
                } else {
                    this.ServerId = this.b.getString("ServerId");
                    this.PatientId = this.b.getString("PatientId");
                    this.MedId = this.b.getString("MedId");

                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            this.getinitialize();
            this.SelectedGetPatientDetails();
            this.LoadWebview();
            String Query="select ReportGallery,FileName from OnlineConsultancyDtls where ServerId='" + this.ServerId + "' and pid='" + this.PatientId + "';";
            this.SelectedGetPatientReports(Query);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    //#######################################################################################################
    private final void LoadWebview() {

        this.profile_webvw.getSettings().setJavaScriptEnabled(true);
        this.profile_webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        this.profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.profile_webvw.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.profile_webvw.getSettings().setDefaultTextEncodingName("utf-8");

        this.profile_webvw.setWebChromeClient(new OnlineConsultation_Details.MyWebChromeClient());

        this.profile_webvw.setBackgroundColor(0x00000000);
        this.profile_webvw.setVerticalScrollBarEnabled(true);
        this.profile_webvw.setHorizontalScrollBarEnabled(true);


        this.profile_webvw.getSettings().setJavaScriptEnabled(true);

        this.profile_webvw.getSettings().setAllowContentAccess(true);


        this.profile_webvw.setOnLongClickListener(v -> true);

        this.profile_webvw.setLongClickable(false);


        this.profile_webvw.addJavascriptInterface(new OnlineConsultation_Details.WebAppInterface(this), "android");
        try {

            this.profile_webvw.loadDataWithBaseURL("file:///android_asset/", this.LoadPrescriptionDetails(), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    private final String LoadPrescriptionDetails() {
        String values = "";
        String[] Tabledata;
        StringBuilder stringBuilder = new StringBuilder();

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        String Query = "select distinct Medicinename from OnlineSharedMedicine where pid='" + this.PatientId + "'  and status='" + this.MedId + "';";
        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    Tabledata = c.getString(c.getColumnIndex("Medicinename")).split("_");

                    stringBuilder.append("  <tr>\n" + "                  <th><font color=\"#000\">").append(Tabledata[0].replace("[", "").replace("]", "")).append("</font></th>\n \n").append("                    <th><font color=\"#000\">").append(Tabledata[1]).append("</font></th>\n \n").append("               \t <th><font color=\"#000\">").append(Tabledata[2]).append("</font></th>\n \n").append("              \t <th><font color=\"#000\">").append(Tabledata[3]).append("</font></th>\n\n").append("                 </tr>\n+");

                } while (c.moveToNext());

                //medicine_name.setText(mednm.toString());
            }
        }
        String values1 = "<!DOCTYPE html>\n" +
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
                "<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.patientmedicinehistory) + "</font>\n" +
                "<div class=\"table-responsive\"> <table class=\"table table-bordered\"><tr>\n" +
                "  <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.medicinename) + "</font></th>\n" +
                "  <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.interval) + "</font></th>\n" +
                "\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.frequency) + "</font></th>\n" +
                "\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">" + this.getString(string.duration) + "</font></th>\n" +
                "  </tr>\n" +
                " \n" + stringBuilder +
                " \n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html> ";


        c.close();
        db.close();


        return values1;
    }

    /***********************************************************************************************/


    private void SelectedGetPatientDetails() {

        try {
            String pimg64 = "";
            String name = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery("select * from OnlineSharedMedicine where pid='" + this.PatientId + "'  and status='" + this.MedId + "';", null);
            StringBuilder mednm = new StringBuilder();
            String photoo = "";

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        //mednm.append(c.getString(c.getColumnIndex("Medicinename")) + "\n");
                        this.treatmentfor.setText(c.getString(c.getColumnIndex("TreatmentFor")));

                    } while (c.moveToNext());

                    //medicine_name.setText(mednm.toString());
                }
            }


            c.close();

            Cursor c1 = db.rawQuery("select * from OnlineConsultancy where ServerId='" + this.ServerId + "' and pid='" + this.PatientId + "';", null);
            if (c1 != null) {
                if (c1.moveToFirst()) {
                    do {

                        this.patient_name.setText(c1.getString(c1.getColumnIndex("pname")));

                        this.patient_query.setText(c1.getString(c1.getColumnIndex("healthsummary")));
                        BaseConfig.LoadPatientImage(BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='"+c1.getString(c1.getColumnIndex("pid"))+"'"), this.Patient_photo, 100);
                        this.Age.setText(c1.getString(c1.getColumnIndex("age")));
                        this.Gender.setText(c1.getString(c1.getColumnIndex("gender")));
                        this.visiteddate.setText(c1.getString(c1.getColumnIndex("Actdt")));
                        this.Patient_Id.setText(c1.getString(c1.getColumnIndex("pid")));
                        break;
                    } while (c1.moveToNext());
                }
            }

            db.close();
            c1.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    //#######################################################################################################

    /***********************************************************************************************/


    private void SelectedGetPatientReports(String Query) {

        try {
            String report_name = "";
            String report64 = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                            report_name = c.getString(c.getColumnIndex("FileName"));
                            report64 = c.getString(c.getColumnIndex("ReportGallery"));

                            OnlineConsultationReport item = new OnlineConsultationReport(report_name, report64);

                        this.rowItems.add(item);

                    } while (c.moveToNext());

                }
            }

            this.adapter = new OnlineConsultation_Detailed_Adapter(this, this.rowItems);
            this.listView.setLayoutManager(new LinearLayoutManager(this));
            this.listView.setAdapter(this.adapter);

            this.setRecylerViewListener();

            c.close();
            db.close();
        } catch (RuntimeException e) {

            e.printStackTrace();

        }
    }

    /***********************************************************************************************/


    private static void SelectedGetPatientReports_Image(String Query, ImageView imgvw) {

        try {
            String report_name = "";
            String report64 = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        BaseConfig.LoadPatientImage(c.getString(c.getColumnIndex("ReportGallery")), imgvw, 100);

                    } while (c.moveToNext());

                }
            }

            c.close();
            db.close();
        } catch (Exception e) {
e.printStackTrace();
        }
    }

    /***********************************************************************************************/


    private void getinitialize() {

        this.Patient_Id = this.findViewById(id.pid_txt);
        this.patient_name = this.findViewById(id.textView2);
        this.Age = this.findViewById(id.textView4);
        this.Gender = this.findViewById(id.textView6);
        this.treatmentfor = this.findViewById(id.textView9);
        this.patient_query = this.findViewById(id.textView15);
        this.visiteddate = this.findViewById(id.textView7);

        this.Docreply = this.findViewById(id.editText1);


        this.Patient_photo = this.findViewById(id.icon);

        this.reject = this.findViewById(id.Button01);
        this.cancel = this.findViewById(id.Button03);
        this.reply = this.findViewById(id.Button02);


        // initialize
        this.rowItems = new ArrayList<>();
        this.listView = this.findViewById(id.listView1);
        this.profile_webvw = this.findViewById(id.webvw_prescription_profile);
        this.profile_webvw.getSettings().setJavaScriptEnabled(true);
        this.profile_webvw.setWebChromeClient(new WebChromeClient());


        //***************************************************************************************
        //controllisteners
        //***************************************************************************************


        // //////////////////////////////////////////////////////////////////
        this.Docreply.setOnTouchListener((v, event) -> {
            if (this.Docreply.length() > 350) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });

        // //////////////////////////////////////////////////////////////////


        this.reply.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            try {

                if (this.checkValidation())

                    this.savelocal();
                else

                   Toast.makeText(this, this.getString(string.check_missing_valid), Toast.LENGTH_LONG).show();
              //  BaseConfig.SnackBar(this,  getString(R.string.check_missing_valid) , parentLayout,2);

            } catch (RuntimeException e) {
                e.printStackTrace();

            }
        });

        this.cancel.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            finish();
            Intent intentnew = new Intent(this.getApplicationContext(), OnlineConsultation.class);
            this.startActivity(intentnew);

        });


        this.reject.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            //SaveLocal(2);
            this.showSimplePopUpExit(this.getString(string.reject_consultation));
        });


        ///////////////////////////////////////////////////////////////////////////////


        Context context = this;


    }

    private void setRecylerViewListener() {
        this.adapter.onClickItem((report, view) -> {

            String ReportName = report.getReportName();


            LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());

            View promptView = layoutInflater.inflate(layout.reportimageview_zoom_dialog2, null);

            Builder alertDialogBuilder = new Builder(view.getContext());

            alertDialogBuilder.setView(promptView);


            Bitmap rotatedBitmap;

            ImageView reportphoto = promptView.findViewById(id.imageZoom);

            String Query_Image="select ReportGallery,FileName from OnlineConsultancyDtls where ServerId='" + this.ServerId + "' and FileName='" + ReportName + "' and pid='" + this.PatientId + "';";
            OnlineConsultation_Details.SelectedGetPatientReports_Image(Query_Image, reportphoto);

            TextView nameagegen1 = promptView.findViewById(id.nameagegen);

            TextView patient_name = promptView.findViewById(id.amt);


            nameagegen1.setText(this.getString(string.report_name) +": "+ ReportName);
            reportphoto.setImageBitmap(OnlineConsultation_Details.reportimg);


            alertDialogBuilder.setCancelable(false).setPositiveButton(this.getString(string.ok),
                    (dialog, id1) -> {


                    });


            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();


        });
    }

    private final boolean checkValidation() {
        boolean ret = true;

        if (!Validation1.hasText(this.Docreply))
            ret = false;


        return ret;
    }

    private final void savelocal() {

        try {
            String Insert_Query = "update OnlineConsultancy set DocReply='" + this.Docreply.getText() + "',IsUpdate='0',IsActive='False' where ServerId='" + this.ServerId + "' ";

            BaseConfig.SaveData(Insert_Query);
            //Log.e("Insert_Query", Insert_Query);

            this.showSimplePopUpExit(this.getString(string.response_send));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void showSimplePopUpExit(String msg) {

        new CustomKDMCDialog(this)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_success_done)
                .setTitle(getString(string.information))
                .setNegativeButtonVisible(View.GONE)
                .setDescription(msg)
                .setPossitiveButtonTitle(getString(string.ok))
                .setOnPossitiveListener(() -> {
                    String Insert_Query = "update OnlineConsultancy set IsUpdate='1',IsActive='False' where ServerId='" + this.ServerId + "' ";

                    BaseConfig.SaveData(Insert_Query);
                    //Log.e("Insert_Query", Insert_Query);


                    finish();
                    Intent intent = new Intent(this.getApplicationContext(), OnlineConsultation.class);
                    this.startActivity(intent);
                });


    }

    //#######################################################################################################
    @Override
    public final void onBackPressed() {

        this.LoadBack();

    }

    private final void LoadBack() {
        BaseConfig.globalStartIntent(this, OnlineConsultation.class, null);

    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    //#######################################################################################################
    static class WebAppInterface {
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
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }

    //#######################################################################################################

}