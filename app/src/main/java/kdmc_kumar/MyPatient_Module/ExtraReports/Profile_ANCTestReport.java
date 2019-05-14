package kdmc_kumar.MyPatient_Module.ExtraReports;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Android on 8/21/2017.
 */

public class Profile_ANCTestReport extends AppCompatActivity {

    private WebView blood_report;
    private Toolbar toolbar;

    private ImageView ic_back;
    private String MTESTID = "";
    private String PTID = "";

    public Profile_ANCTestReport() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_profile__blood__report);

        Bundle b = this.getIntent().getExtras();

        this.PTID = b.getString("PTID");
        this.MTESTID = b.getString("MTESTID");


        this.toolbar = this.findViewById(id.toolbar);
        TextView title = this.toolbar.findViewById(id.txvw_title);
        title.setText(this.getString(string.report_anc));

        this.ic_back = this.toolbar.findViewById(id.ic_back);

        this.ic_back.setOnClickListener(view -> finish());

        this.blood_report = this.findViewById(id.blood_report);

        this.blood_report.getSettings().setJavaScriptEnabled(true);
        this.blood_report.setWebChromeClient(new WebChromeClient());
        this.blood_report.getSettings().setJavaScriptEnabled(true);
        this.blood_report.setLayerType(View.LAYER_TYPE_NONE, null);
        this.blood_report.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.blood_report.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.blood_report.getSettings().setDefaultTextEncodingName("utf-8");

        this.blood_report.setWebChromeClient(new Profile_ANCTestReport.MyWebChromeClient());

        this.blood_report.setBackgroundColor(0x00000000);
        this.blood_report.setVerticalScrollBarEnabled(true);
        this.blood_report.setHorizontalScrollBarEnabled(true);

        //MyDynamicToast.informationMessage(this, getString(R.string.anc_test_loading));
        this.blood_report.getSettings().setJavaScriptEnabled(true);
        this.blood_report.getSettings().setAllowContentAccess(true);


        this.blood_report.addJavascriptInterface(new Profile_ANCTestReport.WebAppInterface(this), "android");
        try {

            this.blood_report.loadDataWithBaseURL("file:///android_asset/", this.LoadInvetigationData(), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String LoadInvetigationData() {

        String values = "";

        String Id = "", Patid = "", Docid = "", Actdate = "", IsActive = "", IsUpdate = "", ServerId = "", Mtestid = "", Testid = "", Subtestid = "", HID = "", IsPaid = "", haemogiobin = "", bloodgroup = "", vdpl = "", colour = "", apperance = "", albumin = "", sugar = "", bsbp = "", epithelialcells = "", puscells = "", redcells = "", yeastcells = "", bacteria = "", amarphousmatenal = "", trichomonas = "", casts = "", crystals = "", australia_antigen = "", upt = "", tc = "", dcn = "", dcl = "", dce = "", dcm = "";

        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = "select * from Bind_anc_fp_test where Patid='" + this.PTID + "' and Mtestid='" + this.MTESTID + '\'';
        //String Query = "select * from Bind_examination_blood_test where Patid='PID/2017/188/2870' and Mtestid='MTID/2017/188/DOCID/2017/05/81/4C19/2'";
        //Log.e("Load ANCTEST Data: ", Query);

        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Id = c.getString(c.getColumnIndex("Id"));
                    Patid = c.getString(c.getColumnIndex("Patid"));
                    Docid = c.getString(c.getColumnIndex("Docid"));
                    Actdate = c.getString(c.getColumnIndex("Actdate"));
                    IsActive = c.getString(c.getColumnIndex("IsActive"));
                    IsUpdate = c.getString(c.getColumnIndex("IsUpdate"));
                    ServerId = c.getString(c.getColumnIndex("ServerId"));
                    Mtestid = c.getString(c.getColumnIndex("Mtestid"));
                    Testid = c.getString(c.getColumnIndex("Testid"));
                    Subtestid = c.getString(c.getColumnIndex("Subtestid"));
                    HID = c.getString(c.getColumnIndex("HID"));
                    IsPaid = c.getString(c.getColumnIndex("IsPaid"));

                    haemogiobin = c.getString(c.getColumnIndex("haemogiobin"));
                    bloodgroup = c.getString(c.getColumnIndex("bloodgroup"));
                    vdpl = c.getString(c.getColumnIndex("vdpl"));
                    colour = c.getString(c.getColumnIndex("colour"));
                    apperance = c.getString(c.getColumnIndex("apperance"));
                    albumin = c.getString(c.getColumnIndex("albumin"));
                    sugar = c.getString(c.getColumnIndex("sugar"));
                    bsbp = c.getString(c.getColumnIndex("bsbp"));
                    epithelialcells = c.getString(c.getColumnIndex("epithelialcells"));
                    puscells = c.getString(c.getColumnIndex("puscells"));
                    redcells = c.getString(c.getColumnIndex("redcells"));
                    yeastcells = c.getString(c.getColumnIndex("yeastcells"));
                    bacteria = c.getString(c.getColumnIndex("bacteria"));
                    amarphousmatenal = c.getString(c.getColumnIndex("amarphousmatenal"));
                    trichomonas = c.getString(c.getColumnIndex("trichomonas"));
                    casts = c.getString(c.getColumnIndex("casts"));
                    crystals = c.getString(c.getColumnIndex("crystals"));
                    australia_antigen = c.getString(c.getColumnIndex("australia_antigen"));
                    upt = c.getString(c.getColumnIndex("upt"));
                    tc = c.getString(c.getColumnIndex("tc"));
                    dcn = c.getString(c.getColumnIndex("dcn"));
                    dcl = c.getString(c.getColumnIndex("dcl"));
                    dce = c.getString(c.getColumnIndex("dce"));
                    dcm = c.getString(c.getColumnIndex("dcm"));


                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();

        String values1 = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                '\n' +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\"/>\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\"/>\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\"/>\n" +
                '\n' +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\"></script>\n" +
                '\n' +
                "</head>\n" +
                "<body>  \n" +
                '\n' +
                " \n" +
                "<div class=\"table-responsive\">          \n" +
                '\n' +
                "<h4><b><align><center>\n" +
                " ANC / FP NO.</b></align></h4>\n" +
                " \n" +
                '\n' +
                " <table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>     " + this.getString(string.test_report_haemoglobin) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + haemogiobin + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_blood_group) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + bloodgroup + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  VDPL</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + vdpl + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                "<!---------------------------->\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                "<font class=\"sub\">\n" +
                ' ' + this.getString(string.test_report_urine_examination) + "</font>\n" +
                '\n' +
                '\n' +
                " <p><b>" + this.getString(string.test_report_physical) + " </b>&emsp;&emsp;&emsp;&emsp;\t\t<b>" + this.getString(string.test_report_chemical) + "</b></p> \n" +
                " \n" +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_colour) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + colour + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_albumin) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + albumin + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_appearance) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + apperance + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_sugar) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + sugar + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  BSBP</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + bsbp + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "</table>\n" +
                "</div>\n" +
                '\n' +
                "<!---------------------------->\n" +
                '\n' +
                '\n' +
                "<p><b>" + this.getString(string.test_report_microscope) + " </b>\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_epithelial_cells) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + epithelialcells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_amarphous_matenal) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + amarphousmatenal + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_pus_cells) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + puscells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_trichomonas_vaginalls) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + trichomonas + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_red_cells) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + redcells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_casts) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + casts + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_yeast_cells) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + yeastcells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_crystals) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + crystals + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_bacteria) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + bacteria + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                "<ul>\n" +
                "<li>\n" +
                "<p><b>" + this.getString(string.test_report_australia_antigen) + "</b>  " + australia_antigen + "</p></li>\n" +
                '\n' +
                '\n' +
                "<li><p><b>" + this.getString(string.test_report_upt) + "</b> " + upt + "</p></li>\n" +
                '\n' +
                '\n' +
                "<li><p><b>TC :</b> " + tc + " <b>cells/mm<sup>3<sup></b></p>\n" +
                "</li>\n" +
                '\n' +
                "<li><p><b>DC : </b>\n" +
                "<b>N = </b> " + dcn + " <b>%</b>\n" +
                "<b>L = </b> " + dcl + " <b>%</b>\n" +
                "<b>E = </b> " + dce + " <b>%</b>\n" +
                "<b>M = </b> " + dcm + " <b>%</b>\n" +
                '\n' +
                "</p>\n" +
                "</li>\n" +
                '\n' +
                '\n' +
                '\n' +
                "</ul>\n" +
                '\n' +
                "<!---------------------------->\n" +
                '\n' +
                '\n' +
                "<!---------------------------->\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<br/>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html> ";

        //Toast.makeText(this, "Doctor profile loaded successfully..", Toast.LENGTH_SHORT).show();
        //MyDynamicToast.successMessage(this, getString(R.string.Blood_loaded_success));

        return values1;
    }

    @Override
    public final void onBackPressed() {
        //super.onBackPressed();
        finish();

    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        this.onBackPressed();
        return true;
    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

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
        public final void showToast(String toast) {
            Toast.makeText(this.mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }
}
