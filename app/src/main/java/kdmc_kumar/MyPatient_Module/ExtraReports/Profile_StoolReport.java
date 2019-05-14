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


public class Profile_StoolReport extends AppCompatActivity {
    private WebView blood_report;
    private Toolbar toolbar;

    private ImageView ic_back;
    private String MTESTID = "";
    private String PTID = "";

    public Profile_StoolReport() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_profile__blood__report);
        this.toolbar = this.findViewById(id.toolbar);

        TextView title = this.toolbar.findViewById(id.txvw_title);
        title.setText(this.getString(string.report_stool));

        Bundle b = this.getIntent().getExtras();

        this.PTID = b.getString("PTID");
        this.MTESTID = b.getString("MTESTID");

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

        this.blood_report.setWebChromeClient(new Profile_StoolReport.MyWebChromeClient());

        this.blood_report.setBackgroundColor(0x00000000);
        this.blood_report.setVerticalScrollBarEnabled(true);
        this.blood_report.setHorizontalScrollBarEnabled(true);

        //MyDynamicToast.informationMessage(this, getString(R.string.loading_blood_report));
        this.blood_report.getSettings().setJavaScriptEnabled(true);
        this.blood_report.getSettings().setAllowContentAccess(true);


        this.blood_report.addJavascriptInterface(new Profile_StoolReport.WebAppInterface(this), "android");
        try {

            this.blood_report.loadDataWithBaseURL("file:///android_asset/", this.LoadInvetigationData(), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String LoadInvetigationData() {

        String values = "";

        String Id = "", Patid = "", Docid = "", Actdate = "", IsActive = "", IsUpdate = "", ServerId = "", Mtestid = "", Testid = "", Subtestid = "", HID = "", IsPaid = "", colour = "", consistency = "", musus = "", blood = "", parasites = "", occult_blood_test = "", cpi_cells = "", pus_cells = "", red_blood_cells = "", macrophages = "", fat = "", starch = "", undigeted_food = "", any_other_finding = "", vegetative_forms = "", protozoa = "", flagellates = "", cyst = "", ova = "", larvae = "", result = "";

        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = "select * from Bind_stool_report where Patid='" + this.PTID + "' and Mtestid='" + this.MTESTID + '\'';
        //String Query = "select * from Bind_examination_blood_test where Patid='PID/2017/188/2870' and Mtestid='MTID/2017/188/DOCID/2017/05/81/4C19/2'";
        //Log.e("Load Invetigation Data: ", Query);

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
                    colour = c.getString(c.getColumnIndex("colour"));
                    consistency = c.getString(c.getColumnIndex("consistency"));
                    musus = c.getString(c.getColumnIndex("musus"));
                    blood = c.getString(c.getColumnIndex("blood"));
                    parasites = c.getString(c.getColumnIndex("parasites"));
                    occult_blood_test = c.getString(c.getColumnIndex("occult_blood_test"));
                    cpi_cells = c.getString(c.getColumnIndex("cpi_cells"));
                    pus_cells = c.getString(c.getColumnIndex("pus_cells"));
                    red_blood_cells = c.getString(c.getColumnIndex("red_blood_cells"));
                    macrophages = c.getString(c.getColumnIndex("macrophages"));
                    fat = c.getString(c.getColumnIndex("fat"));
                    starch = c.getString(c.getColumnIndex("starch"));
                    undigeted_food = c.getString(c.getColumnIndex("undigeted_food"));
                    any_other_finding = c.getString(c.getColumnIndex("any_other_finding"));
                    vegetative_forms = c.getString(c.getColumnIndex("vegetative_forms"));
                    protozoa = c.getString(c.getColumnIndex("protozoa"));
                    flagellates = c.getString(c.getColumnIndex("flagellates"));
                    cyst = c.getString(c.getColumnIndex("cyst"));
                    ova = c.getString(c.getColumnIndex("ova"));
                    larvae = c.getString(c.getColumnIndex("larvae"));
                    result = c.getString(c.getColumnIndex("result"));


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
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-puzzle-piece fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i> " + this.getString(string.profile_stool_physicalexamination) + "</font>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>     " + this.getString(string.profile_stool_colour) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + colour + " </td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_blood) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + blood + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_consistency) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + consistency + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b> " + this.getString(string.profile_stool_parasites) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + parasites + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_mussus) + " </b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + musus + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                "<!---------------------------->\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-flask fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i> " + this.getString(string.profile_stool_chemialexam) + "</font>\n" +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_occultblood) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + occult_blood_test + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "</table>\n" +
                "</div>\n" +
                '\n' +
                "<!---------------------------->\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                '\n' +
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-snowflake-o fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i>  " + this.getString(string.profile_stool_mircrospicexam) + "</font>\n" +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_cpicells) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + cpi_cells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_puscells) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + pus_cells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_vege) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + vegetative_forms + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_redblood) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + red_blood_cells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_protozoa) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + protozoa + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_macrophages) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + macrophages + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_flagellates) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + flagellates + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_fat) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + fat + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_cyst) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + cyst + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_starch) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + starch + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_ova) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + ova + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_undigested) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + undigeted_food + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_larvae) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + larvae + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_anyother) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + any_other_finding + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                '\n' +
                "<!---------------------------->\n" +
                "<div class=\"table-responsive\"> \n" +
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-tasks fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i> Hanging drop method</font>\n" +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.profile_stool_result) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + result + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                "</div>\n" +
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
        // MyDynamicToast.successMessage(this, getString(R.string.Blood_loaded_success));

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
