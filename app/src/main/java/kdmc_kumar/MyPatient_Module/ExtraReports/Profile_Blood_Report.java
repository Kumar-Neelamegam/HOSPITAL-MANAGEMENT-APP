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


public class Profile_Blood_Report extends AppCompatActivity {

    private WebView blood_report;
    private Toolbar toolbar;

    private ImageView ic_back;

    private String MTESTID = "";
    private String PTID = "";

    public Profile_Blood_Report() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_profile__blood__report);

        try {
            this.toolbar = this.findViewById(id.toolbar);
            TextView title = this.toolbar.findViewById(id.txvw_title);
            title.setText(this.getString(string.report_blood));

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

            this.blood_report.setWebChromeClient(new Profile_Blood_Report.MyWebChromeClient());

            this.blood_report.setBackgroundColor(0x00000000);
            this.blood_report.setVerticalScrollBarEnabled(true);
            this.blood_report.setHorizontalScrollBarEnabled(true);

            //MyDynamicToast.informationMessage(this, getString(R.string.loading_blood_report));
            this.blood_report.getSettings().setJavaScriptEnabled(true);
            this.blood_report.getSettings().setAllowContentAccess(true);


            this.blood_report.addJavascriptInterface(new Profile_Blood_Report.WebAppInterface(this), "android");
            try {

                this.blood_report.loadDataWithBaseURL("file:///android_asset/", this.LoadInvetigationData(), "text/html", "utf-8", null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set max and min values
     *
     * @param
     * @return
     */


    private String LoadInvetigationData() {

        String values = "";

        String Patid = "", Docid = "", Actdate = "", IsActive = "", IsUpdate = "", ServerId = "", Mtestid = "", Testid = "", Subtestid = "", HID = "", IsPaid = "",
                maemogiobin = "", packed_cell_volume = "", total_count = "", rbc_count = "", polymorphs = "", lymphocytes = "", eosinophilis = "", monocytes = "",
                basophils = "", band_from = "", platelets_count = "", psmp = "", esr = "", STyphi_O = "", STyphi_H = "", SParatyphi_AH = "", SParatyphi_BH = "";


        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = "select * from Bind_examination_blood_test where Patid='" + this.PTID + "' and Mtestid='" + this.MTESTID + '\'';
        //String Query = "select * from Bind_examination_blood_test where Patid='PID/2017/188/2870' and Mtestid='MTID/2017/188/DOCID/2017/05/81/4C19/2'";
        //Log.e("Load Invetigation Data: ", Query);

        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

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

                    maemogiobin = c.getString(c.getColumnIndex("maemogiobin"));
                    packed_cell_volume = c.getString(c.getColumnIndex("packed_cell_volume"));
                    total_count = c.getString(c.getColumnIndex("total_count"));
                    rbc_count = c.getString(c.getColumnIndex("rbc_count"));
                    polymorphs = c.getString(c.getColumnIndex("polymorphs"));
                    lymphocytes = c.getString(c.getColumnIndex("lymphocytes"));
                    eosinophilis = c.getString(c.getColumnIndex("eosinophilis"));
                    monocytes = c.getString(c.getColumnIndex("monocytes"));
                    basophils = c.getString(c.getColumnIndex("basophils"));
                    band_from = c.getString(c.getColumnIndex("band_from"));
                    platelets_count = c.getString(c.getColumnIndex("platelets_count"));
                    psmp = c.getString(c.getColumnIndex("psmp"));
                    esr = c.getString(c.getColumnIndex("esr"));
                    STyphi_O = c.getString(c.getColumnIndex("STyphi_O"));
                    STyphi_H = c.getString(c.getColumnIndex("STyphi_H"));
                    SParatyphi_AH = c.getString(c.getColumnIndex("SParatyphi_AH"));
                    SParatyphi_BH = c.getString(c.getColumnIndex("SParatyphi_BH"));


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
                "<i class=\"fa fa-flask fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i> " + this.getString(string.test_report_complete_blood_count) + "</font>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"20%\" style=\"color:#3d5987;\"></i><b>     " + this.getString(string.test_report_hemoglobin) + "</b><br>(M:14-17 gm%,F:12-16 gm%)</td> \n" +
                Profile_Blood_Report.GetMaxMin1(maemogiobin, Patid) +
                '\n' +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_packed_cell_volume) + "</b><br> (M:40-54, F:37-47)</td> \n" +
                Profile_Blood_Report.GetMaxMin2(packed_cell_volume, Patid) +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_total_count) + " </b><br>(4000-10000 cells/mm)</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + total_count + " cell/mm</td>\n" +
                Profile_Blood_Report.GetMaxMin3(total_count, Patid, 10000, 4000, "cell/mm") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_rbc_count) + " </b><br>(4.5-6.0 x 10<sup>6</sup> cells/mm<sup>3</sup>) </td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + rbc_count + " cell/mm<sup>3</sup></td>\n" +
                Profile_Blood_Report.GetMaxMin4(rbc_count, Patid, Double.valueOf(6.0), Double.valueOf(4.5), "cell/mm<sup>3</sup>") +
                "</tr>\n" +
                '\n' +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                "<!---------------------------->\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-compress fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i> " + this.getString(string.test_report_differential_count) + "</font>\n" +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"20%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_polymorphs) + "</b><br>(40 to 70)%</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + polymorphs + " %</td>\n" +
                Profile_Blood_Report.GetMaxMin3(polymorphs, Patid, 70, 40, "%") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_lymphocytes) + " </b><br>(20 to 40)%</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + lymphocytes + " %</td>\n" +
                Profile_Blood_Report.GetMaxMin3(lymphocytes, Patid, 40, 20, "%") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_eosinophilis) + " </b><br>(01 to 06)%</td> \n" +
                // "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + eosinophilis + " %</td>\n" +
                Profile_Blood_Report.GetMaxMin3(eosinophilis, Patid, 6, 1, "%") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_monocytes) + " </b> <br> (02 to 08)%</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + monocytes + " %</td>\n" +
                Profile_Blood_Report.GetMaxMin3(monocytes, Patid, 0x08, 2, "%") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_basophils) + "</b> <br> (00 to 01)%</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + basophils + " %</td>\n" +
                Profile_Blood_Report.GetMaxMin3(basophils, Patid, 1, 0, "%") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_band_form) + "</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\">" + band_from + " %</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + this.getString(string.test_report_platelets_count) + " </b><br>(1.5-3.5 lakhs/mm<sup>3</sup>)</td> \n" +
                // "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + platelets_count + "</td>\n" +
                Profile_Blood_Report.GetMaxMin4(platelets_count, Patid, Double.valueOf(3.5), Double.valueOf(1.5), "") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  P.S. for M.P. </b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + psmp + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  ESR </b> <br> (0-15 MM/HR)(" + this.getString(string.test_report_method_westergen) + ")</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + esr + "</td>\n" +
                Profile_Blood_Report.GetMaxMin3(esr, Patid, 15, 0, "(Method: Westergen)") +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                "</div>\n" +
                '\n' +
                "<!---------------------------->\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                '\n' +
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-list fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i>  " + this.getString(string.test_report_widal_test) + "</font>\n" +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  S. Typhi 'O'</b> <br>(Titre(NR)- 1:80)</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + STyphi_O + "</td>\n" +
                Profile_Blood_Report.GetMaxMin3(STyphi_O, Patid, 80, 1, "%") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  S. Typhi 'H'</b><br> (Titre(NR)- 1:160)</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + STyphi_H + "</td>\n" +
                Profile_Blood_Report.GetMaxMin3(STyphi_H, Patid, 160, 1, "%") +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  S. Paratyphi 'AH'</b><br> (Titre(NR)- 1:80)</td> \n" +
                // "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + SParatyphi_AH + "</td>\n" +
                Profile_Blood_Report.GetMaxMin3(SParatyphi_AH, Patid, 80, 1, "%") +
                '\n' +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  S. Paratyphi 'BH'</b> <br> (Titre(NR)- 1:80)</td> \n" +
                //"<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + SParatyphi_BH + "</td>\n" +
                Profile_Blood_Report.GetMaxMin3(SParatyphi_BH, Patid, 80, 1, "%") +
                '\n' +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                '\n' +
                "<!---------------------------->\n" +
                '\n' +
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


    //**********************************************************************************************

    private static final String GetMaxMin1(String val, String patid) {
        boolean status = false;
        int current_value = Integer.parseInt(val);

        String str = "";

        String PatientGender = BaseConfig.GetValues("select Gender as ret_values from Patreg where Patid='" + patid + '\'');

        if (PatientGender.equalsIgnoreCase("Male"))//Male
        {

            int Min = 14;
            int Max = 17;

            if (current_value < Min || current_value > Max) {
                status = true;
            }


        } else if (PatientGender.equalsIgnoreCase("Female"))//Female
        {
            int Min = 12;
            int Max = 16;

            if (current_value < Min || current_value > Max) {
                status = true;
            }

        }

        if (status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#F00;\">" + val + " gm%</td>\n";
        } else if (!status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + val + " gm%</td>\n";
        }

        return str;
    }


    private static final String GetMaxMin2(String val, String patid) {
        boolean status = false;
        int current_value = Integer.parseInt(val);

        String str = "";

        String PatientGender = BaseConfig.GetValues("select Gender as ret_values from Patreg where Patid='" + patid + '\'');

        if (PatientGender.equalsIgnoreCase("Male"))//Male
        {

            int Min = 40;
            int Max = 54;

            if (current_value < Min || current_value > Max) {
                status = true;
            }


        } else if (PatientGender.equalsIgnoreCase("Female"))//Female
        {
            int Min = 37;
            int Max = 47;

            if (current_value < Min || current_value > Max) {
                status = true;
            }

        }

        if (status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#F00;\">" + val + " %</td>\n";
        } else if (!status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + val + " %</td>\n";
        }

        return str;
    }


    private static final String GetMaxMin3(String val, String patid, int Max, int Min, String Unit) {
        boolean status = false;
        int current_value = Integer.parseInt(val);

        String str = "";


        if (current_value < Min || current_value > Max) {
            status = true;
        }

        if (status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#F00;\">" + val + ' ' + Unit + "</td>\n";
        } else if (!status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + val + ' ' + Unit + "</td>\n";
        }

        return str;
    }


    private static final String GetMaxMin4(String val, String patid, Double Max, Double Min, String Unit) {
        boolean status = false;
        double current_value = Double.parseDouble(val);

        String str = "";


        if (current_value < Min.doubleValue() || current_value > Max.doubleValue()) {
            status = true;
        }

        if (status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#F00;\">" + val + ' ' + Unit + "</td>\n";
        } else if (!status) {
            str = "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">" + val + ' ' + Unit + "</td>\n";
        }

        return str;
    }

    //**********************************************************************************************

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
