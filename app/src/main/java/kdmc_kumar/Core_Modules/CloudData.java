package kdmc_kumar.Core_Modules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;


public class CloudData extends AppCompatActivity {


    @BindView(id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(id.imagevw_serverconnectivity)
    ImageView imagevwServerconnectivity;
    @BindView(id.textview_servertitle)
    TextView textviewServertitle;
    @BindView(id.imagevw_internet)
    ImageView imagevwInternet;
    @BindView(id.textvw_internet)
    TextView textvwInternet;
    @BindView(id.webvw_profile)
    WebView webvwProfile;

    @BindView(id.toolbar)
    Toolbar toolbar;
    @BindView(id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title)
    TextView toolbarTitle;
    @BindView(id.toolbar_exit)
    AppCompatImageView toolbarExit;
    BaseConfig bc = new BaseConfig();
    //#######################################################################################################
    int COUNT_PATIENT_REGISTRATION;
    int COUNT_CLINICAL_INFORMATION;
    int COUNT_CASENOTES;
    int COUNT_INVESTIGATION;
    int COUNT_PRESCRIPTION;

    private Handler timerHandler;
    private Runnable timerRunnable;

    public CloudData() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            this.setContentView(layout.layout_cloud);

            this.GETINITIALIZATION();

            this.CONTROLLISTNERS();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void GETINITIALIZATION() {
        BaseConfig.welcometoast = 0;

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.cloud)));

        this.setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

        //  toolbar.setBackgroundColor(Color.parseColor(CloudData.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));


    }

    private void CONTROLLISTNERS() {
        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));


        this.toolbarBack.setOnClickListener(v -> this.LoadBack());


        this.CheckServerConnectivity();

        this.AUTO_REFRESH_CLOUD_COUNT();

    }

    private void AUTO_REFRESH_CLOUD_COUNT() {


        this.timerHandler = new Handler();

        this.timerRunnable = new Runnable() {
            @Override
            public void run() {

                new CheckPendingData().execute();

                CloudData.this.timerHandler.postDelayed(this, 1000); //run every second

            }
        };

        this.timerHandler.postDelayed(this.timerRunnable, 1000); //Start timer after 1 sec

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (this.timerHandler != null) {
            this.timerHandler.removeCallbacks(this.timerRunnable);
        }

    }

    private void CheckServerConnectivity() {
        if (BaseConfig.CheckNetwork(this)) {
            this.imagevwInternet.setImageBitmap(null);
            this.imagevwInternet.setImageResource(drawable.ic_status_ready);
        } else {
            this.imagevwInternet.setImageResource(drawable.ic_status_busy);
        }

        try {
            BaseConfig.checkNodeServer(this.imagevwServerconnectivity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final void LoadBack() {
        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

    }

    //#######################################################################################################
    @Override
    public final void onBackPressed() {

        this.LoadBack();

    }

    private final void CheckPending() {

        try {
            this.COUNT_PATIENT_REGISTRATION = this.CheckCount("select count(*) as ret_values  from Bind_Patient_Registration where Isupdate='0' group by PatientId");
            this.COUNT_CLINICAL_INFORMATION = this.CheckCount("select count(*) as ret_values from ClinicalInformation where Isupdate='0' group by ptid");
            this.COUNT_CASENOTES = this.CheckCount("select count(*) as ret_values from Diagonis where Isupdate='0' group by DiagId");
            this.COUNT_INVESTIGATION = this.CheckCount("select count(*)  ret_values from Medicaltest where Isupdate='0' group by mtestid");
            this.COUNT_PRESCRIPTION = this.CheckCount("select count(*) as ret_values from Mprescribed where Isupdate='0' group by Medid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int CheckCount(String query)
    {

        int str = 0;
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    str = Integer.parseInt(c.getString(c.getColumnIndex("ret_values")));

                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();
        return str;

    }

    @SuppressLint("AddJavascriptInterface")
    private final void LoadWebview() {

        this.webvwProfile = this.findViewById(id.webvw_profile);
        this.webvwProfile.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.webvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webvwProfile.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.webvwProfile.setOnLongClickListener(v -> true);
        this.webvwProfile.setLongClickable(false);
        this.webvwProfile.setLayerType(View.LAYER_TYPE_NONE, null);
        this.webvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webvwProfile.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.webvwProfile.getSettings().setDefaultTextEncodingName("utf-8");
        this.webvwProfile.setWebChromeClient(new CloudData.MyWebChromeClient());
        this.webvwProfile.setBackgroundColor(0x00000000);
        this.webvwProfile.setVerticalScrollBarEnabled(true);
        this.webvwProfile.setHorizontalScrollBarEnabled(true);
        this.webvwProfile.getSettings().setJavaScriptEnabled(true);
        this.webvwProfile.getSettings().setAllowContentAccess(true);
        this.webvwProfile.addJavascriptInterface(new CloudData.WebAppInterface(this), "android");

        try {

            this.webvwProfile.loadDataWithBaseURL("file:///android_asset/", this.LoadCloudDetails(), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    //#######################################################################################################

    private String LoadCloudDetails() {


        return "<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +
                "\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" +
                "\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                "\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "<body>  \n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"table-responsive\"> \n" +
                "       \n" +
                "<table class=\"table table-bordered\">\n" +
                "  <tr>\n" +
                "  \n" +
                "  \n" +
                "    <th class=\"fixed\" bgcolor=\"#3d5987\"  ><font color=\"#fff\">" + this.getString(string.pending_data) + "</font></th>\n" +
                "    <th class=\"fixed\" bgcolor=\"#3d5987\" style=\"text-align:center\"><font color=\"#fff\">" + this.getString(string.count) + "</font></th> \n" +
                " \n" +
                "  </tr>\n" +
                "  \n" +

                "\t<tr>\n" +
                "\t<td><b>" + this.getString(string.patientregistration) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + this.COUNT_PATIENT_REGISTRATION + "</td> \n" +
                "\t</tr>  \n" +
                "\t\n" +

                "\t<tr>\n" +
                "\t<td><b>" + this.getString(string.clinical_informaiton) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + this.COUNT_CLINICAL_INFORMATION + "</td> \n" +
                "\t</tr>  \n" +

                "\t\n" +
                "\t<tr>\n" +
                "\t<td><b>" + this.getString(string.case_notes) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + this.COUNT_CASENOTES + "</td> \n" +
                "\t</tr>  \n" +
                "\t\n" +
                "\t<tr>\n" +
                "\t<td><b>" + this.getString(string.investigation) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + this.COUNT_INVESTIGATION + "</td> \n" +
                "\t</tr>  \n" +
                "\t\n" +
                "\t\t<tr>\n" +
                "\t<td><b>" + this.getString(string.prescription) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + this.COUNT_PRESCRIPTION + "</td> \n" +
                "\t</tr> \n" +
                " \n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "  \n" +
                "<h4 style=\"padding:5px\";>\n" +
                "<b>Note:</b><br>" + this.getString(string.content_cloud) + "\n" +
                "</h4>\n" +
                "\n" +
                " \n" +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                             ";
    }

    //#######################################################################################################


    public final void onDestroy() {
        super.onDestroy();

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
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }

    private class CheckPendingData extends AsyncTask<Void, Void, Void> {

        private CheckPendingData() {
        }

        protected final void onPostExecute(Void result) {

            try {
                CloudData.this.LoadWebview();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected final Void doInBackground(Void... params) {

            CloudData.this.CheckPending();

            return null;
        }

    }


}//END
