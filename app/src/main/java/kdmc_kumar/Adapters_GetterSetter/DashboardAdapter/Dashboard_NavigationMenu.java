package kdmc_kumar.Adapters_GetterSetter.DashboardAdapter;

import android.R.color;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.raw;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.ObjectDrawerItem;
import kdmc_kumar.Adapters_GetterSetter.DrawerItemCustomAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.CloudData;
import kdmc_kumar.Doctor_Modules.Doctor_Profile;
import kdmc_kumar.Doctor_Modules.Login;
import kdmc_kumar.Doctor_Modules.SettingActivity;
import kdmc_kumar.Inpatient_Module.OperationTheater;
import kdmc_kumar.Notification.NotificationActivity;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.NotificationBadge;


public class Dashboard_NavigationMenu extends AppCompatActivity {


    public static int flag;
    private final String dt = BaseConfig.timedate;
    private final ArrayList<Dashboard_NavigationMenu.NotificationClass> notificationList = new ArrayList<>();
    private final ArrayList<Dashboard_NavigationMenu.NotificationClass> notificationListTest = new ArrayList<>();
    public String Text = "";
    int COUNT_PATIENT_REGISTRATION;
    int COUNT_CLINICAL_INFORMATION;
    int COUNT_CASENOTES;
    int COUNT_INVESTIGATION;
    int COUNT_PRESCRIPTION;
    @BindView(id.dashboard_drawer_layout)
    DrawerLayout dashboardDrawerLayout;
    @BindView(id.dashboard_toolbar)
    Toolbar dashboardToolbar;
    @BindView(id.dashboard_toolbar_txvw_title)
    TextView dashboardToolbarTxvwTitle;
    @BindView(id.dashboard_toolbar_ic_cloud)
    AppCompatImageView dashboardToolbarIcCloud;
    @BindView(id.dashboard_toolbar_cloud_notificationbatch)
    NotificationBadge dashboardToolbarCloudNotificationbatch;
    @BindView(id.dashboard_toolbar_ic_notification)
    AppCompatImageView dashboardToolbarIcNotification;
    @BindView(id.dashboard_toolbar_operations_notificationbatch)
    NotificationBadge dashboardToolbarOperationsNotificationbatch;
    @BindView(id.dashboard_toolbar_ic_settings)
    AppCompatImageView dashboardToolbarIcSettings;
    @BindView(id.dashboard_toolbar_ic_exit)
    AppCompatImageView dashboardToolbarIcExit;

    @BindView(id.dashboard_left_drawer)
    ListView dashboardLeftDrawer;

    FrameLayout dashboardContentFrame;

    private Dashboard_NavigationMenu.CheckTrail Expiry = new Dashboard_NavigationMenu.CheckTrail();
    private Dashboard_NavigationMenu.CheckApplcationExpiry AppExpiry = new Dashboard_NavigationMenu.CheckApplcationExpiry();
    private ActionBarDrawerToggle mDrawerToggle;
    private Handler timerHandler;
    private Runnable timerRunnable;


    public Dashboard_NavigationMenu() {
    }

    @Override
    public final void onResume() {

        BaseConfig.IsFromInPatient = false;

        BaseConfig.LoadDoctorValues();

        if (BaseConfig.PDFLINK.length() > 0 && BaseConfig.PDFLINK != null && !BaseConfig.PDFLINK_FLAG) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BaseConfig.PDFLINK));
            this.startActivity(browserIntent);
            BaseConfig.PDFLINK_FLAG = true;
        }

        super.onResume();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.kdmc_main_navigation_layout);


        try {


            this.GETINITIALIZE(savedInstanceState);

            this.CONTROLLISTENERS();

            this.AUTO_REFRESH_CLOUD_COUNT();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.timerHandler != null) {
            this.timerHandler.removeCallbacks(this.timerRunnable);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.timerHandler != null) {
            this.timerHandler.removeCallbacks(this.timerRunnable);
        }
    }

    private void AUTO_REFRESH_CLOUD_COUNT() {


        this.timerHandler = new Handler();

        this.timerRunnable = new Runnable() {
            @Override
            public void run() {

                new CheckPendingData().execute();

                Dashboard_NavigationMenu.this.timerHandler.postDelayed(this, 1000); //run every second

            }
        };

        this.timerHandler.postDelayed(this.timerRunnable, 1000); //Start timer after 1 sec

    }


    public void CONTROLLISTENERS() {

        this.dashboardToolbarIcCloud.setOnClickListener(v -> {

            finish();
            Intent setting = new Intent(this.getApplicationContext(), CloudData.class);
            this.startActivity(setting);

        });

        this.dashboardToolbarIcExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.dashboardToolbarIcSettings.setOnClickListener(view -> {

            finish();
            Intent setting = new Intent(this.getApplicationContext(), SettingActivity.class);
            this.startActivity(setting);


        });

        this.dashboardToolbarIcNotification.setOnClickListener(view -> this.prepareListNotificationList());

    }


    public void GETINITIALIZE(Bundle savedInstanceState) {

        ButterKnife.bind(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.HideKeyboard(this);


        this.dashboardContentFrame = this.findViewById(id.dashboard_content_frame);


        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];

        drawerItem[0] = new ObjectDrawerItem(0, "");
        drawerItem[1] = new ObjectDrawerItem(drawable.ic_drawer_calendar, this.dt);
        drawerItem[2] = new ObjectDrawerItem(drawable.ic_drawer_profile, this.getString(string.view_profile));
        drawerItem[3] = new ObjectDrawerItem(drawable.ic_drawer_settings, this.getString(string.title_activity_setting));
        drawerItem[4] = new ObjectDrawerItem(drawable.ic_drawer_signout, this.getString(string.sign_out));

        this.getSupportActionBar();

        this.setSupportActionBar(this.dashboardToolbar);

        this.dashboardToolbarTxvwTitle.setText(String.format("%s - %s", this.getResources().getString(string.welcome), BaseConfig.doctorname));

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, layout.navigation_drawer_list_item, drawerItem);

        this.dashboardLeftDrawer.setAdapter(adapter);

        this.dashboardLeftDrawer.setOnItemClickListener(new DrawerItemClickListener());

        this.dashboardDrawerLayout.setDrawerShadow(drawable.drawer_shadow, GravityCompat.START);


        this.mDrawerToggle = new ActionBarDrawerToggle(this, Dashboard_NavigationMenu.this.dashboardDrawerLayout, string.navigation_drawer_open, string.navigation_drawer_close) {

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Objects.requireNonNull(Dashboard_NavigationMenu.this.getSupportActionBar()).setTitle(String.format("%s %s", Dashboard_NavigationMenu.this.getResources().getString(string.welcome), BaseConfig.doctorname));
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Objects.requireNonNull(Dashboard_NavigationMenu.this.getSupportActionBar()).setTitle(Dashboard_NavigationMenu.this.getResources().getString(string.welcome) + ' ' + BaseConfig.doctorname);
            }
        };

        this.dashboardDrawerLayout.setDrawerListener(this.mDrawerToggle);

        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            this.selectItem(0);
        }


        if (BaseConfig.welcometoast == 1) {

            BaseConfig.SnackBar(this, this.getResources().getString(string.welcome) + ' ' + BaseConfig.doctorname + " - Choose Your Task", this.dashboardDrawerLayout, 1);

        }


        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            this.startActivity(new Intent(this, NotificationActivity.class));
        }


    }

    @Override
    public final void onBackPressed() {

        System.gc();
        BaseConfig.ExitSweetDialog(this, Dashboard_NavigationMenu.class);

    }

    public final void prepareListNotificationList() {

        this.notificationList.clear();

        if (BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from operation_details where (IsNotified = 'false'  or IsNotified IS NULL)")) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from operation_details where  doctors like '%" + BaseConfig.doctorname + ",%' and (IsNotified = 'false' or IsNotified IS NULL)", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String operationDateStr = c.getString(c.getColumnIndex("operation_date"));
                        String operationFromTimeStr = c.getString(c.getColumnIndex("from_time"));
                        String operationToTimeStr = c.getString(c.getColumnIndex("to_time"));


                        if (operationFromTimeStr.contains("T")) {
                            operationFromTimeStr = operationFromTimeStr.split("T")[1];

                        }
                        if (operationToTimeStr.contains("T")) {
                            operationToTimeStr = operationToTimeStr.split("T")[1];
                        }
                        if (operationDateStr.contains("T")) {
                            operationDateStr = operationDateStr.split("T")[0];
                        }

                        StringBuilder msgString = new StringBuilder();

                        msgString.append(this.getString(string.patient_name)).append(c.getString(c.getColumnIndex("patient_name"))).append('\n');
                        msgString.append(this.getString(string.operationdate)).append(": ").append(operationDateStr).append(' ').append(operationFromTimeStr).append(" to ").append(operationToTimeStr).append('\n');
                        String doctors = c.getString(c.getColumnIndex("doctors"));
                        if (doctors.contains(",")) {
                            String drnames[] = doctors.split(",");
                            StringBuilder DrNames = new StringBuilder();
                            DrNames.append(this.getString(string.doctorss));
                            StringBuilder nurseName = new StringBuilder();
                            nurseName.append(this.getString(string.assistants));
                            for (int i = 0; i <= drnames.length - 1; i++) {
                                if (drnames[i].contains("Dr")) {

                                    DrNames.append(drnames[i]).append(",");

                                } else {
                                    nurseName.append(drnames[i]);
                                }

                            }

                            String wholeString = DrNames + "\n" + nurseName;
                            msgString.append(wholeString);


                        } else {
                            msgString.append(doctors);

                        }


                        Dashboard_NavigationMenu.NotificationClass objClass = new Dashboard_NavigationMenu.NotificationClass();
                        objClass.TestName = msgString.toString();

                        objClass.TestValues = String.valueOf(c.getString(c.getColumnIndex("operation_name")));
                        objClass.destinationClass = OperationTheater.class.getSimpleName();
                        try {

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            Date date1 = sdf.parse(operationDateStr + " 00:00:00");

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String date = df.format(Calendar.getInstance().getTime());
                            Date date2 = sdf.parse(date + " 00:00:00");

                            if (!date1.before(date2)) {
                                System.out.println("Date1 is not before Date2");
                            }

                            if (!date1.before(date2)) {
                                this.notificationList.add(objClass);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    while (c.moveToNext());
                }
            }

            Objects.requireNonNull(c).close();
            db.close();


        }

        this.showNotification();


    }

    public final void prepareMedicalTestListNotificationList() {

        // TODO: 3/24/2017 Checking M.POnnusamy in Medical Test Notification
        this.notificationListTest.clear();

        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        Dashboard_NavigationMenu.NotificationClass objClass = new Dashboard_NavigationMenu.NotificationClass();
                        objClass.TestName = "Patient Name " + c.getString(c.getColumnIndex("pname")) + ' ' + c.getString(c.getColumnIndex("Ptid")) + "\nTestName - " + c.getString(c.getColumnIndex("subtestname")) + "\nTest Value - " + c.getString(c.getColumnIndex("testvalue"));
                        objClass.TestValues = String.valueOf(c.getString(c.getColumnIndex("alltest")));
                        objClass.destinationClass = OperationTheater.class.getSimpleName();
                        objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                        objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));
                        this.notificationListTest.add(objClass);
                    }
                    while (c.moveToNext());
                }
            }

            Objects.requireNonNull(c).close();
            db.close();


        }

        this.showNotificationForTestValues();


    }

    public final void showNotificationForTestValues() {

        Dialog dialog = new Dialog(this);
        View rootLayoutParent = getLayoutInflater().inflate(layout.parent_dialog_root, null);
        LinearLayout parentLayout = rootLayoutParent.findViewById(id.root_layout);
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflator = getLayoutInflater();


        for (int i = 0; i <= this.notificationListTest.size() - 1; i++) {


            LinearLayout layout = (LinearLayout) inflator.inflate(layout.dialog_each_notify, null);
            LinearLayout viewRoot = layout.findViewById(id.each_layout_parent);
            TextView messageTitle = layout.findViewById(id.message_title);
            TextView messageStrView = layout.findViewById(id.message_title_explanation);
            Dashboard_NavigationMenu.NotificationClass notifyObj = this.notificationListTest.get(i);
            messageStrView.setText(notifyObj.TestName);
            messageTitle.setText(notifyObj.TestValues);


            viewRoot.setOnClickListener(view -> {

            });


            rootLayout.addView(layout);


        }


        if (this.notificationListTest.size() > 0) {
            parentLayout.addView(rootLayout);
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Button okButon = rootLayoutParent.findViewById(id.btn_ok);
            okButon.setOnClickListener(view -> {

                SQLiteDatabase db = BaseConfig.GetDb();//);
                ContentValues values = new ContentValues();
                values.put("seen", "1");
                db.update("Medicaltestdtls", values, "", null);

                dialog.dismiss();
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(rootLayoutParent);
            Objects.requireNonNull(dialog.getWindow()).setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

            dialog.show();
        }


    }

    public final void showNotification() {

        Dialog dialog = new Dialog(this);
        View rootLayoutParent = getLayoutInflater().inflate(layout.parent_dialog_root, null);
        LinearLayout parentLayout = rootLayoutParent.findViewById(id.root_layout);
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflator = getLayoutInflater();


        for (int i = 0; i <= this.notificationList.size() - 1; i++) {


            LinearLayout layout = (LinearLayout) inflator.inflate(layout.dialog_each_notify, null);
            LinearLayout viewRoot = layout.findViewById(id.each_layout_parent);

            TextView messageTitle = layout.findViewById(id.message_title);
            TextView messageStrView = layout.findViewById(id.message_title_explanation);

            Dashboard_NavigationMenu.NotificationClass notifyObj = this.notificationList.get(i);
            messageStrView.setText(notifyObj.TestName);
            messageTitle.setText(notifyObj.TestValues);


            messageTitle.setOnClickListener(view -> {


                /*String testvalues=messageTitle.getText().toString();

                if(testvalues.toString().equalsIgnoreCase("More"))
                {
                    startActivity(new Intent(Dashboard_NavigationMenu.this, Profile_Blood_Report.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                }*/


            });


            viewRoot.setOnClickListener(view -> {
                Intent iStart = null;
                try {


                    iStart = new Intent(this, OperationTheater.class);
                    ///iStart = new Intent();

                    this.startActivity(iStart);
                    this.finish();

                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            });


            rootLayout.addView(layout);


        }


        if (this.notificationList.size() > 0) {
            parentLayout.addView(rootLayout);
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Button okButon = rootLayoutParent.findViewById(id.btn_ok);
            okButon.setOnClickListener(view -> dialog.dismiss());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(rootLayoutParent);
            Objects.requireNonNull(dialog.getWindow()).setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

            dialog.show();
        } else {

            Toast.makeText(this, "No operations available at this time", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (this.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;

    }

    public final void selectItem(int position) {

        Fragment fragment = null;


        switch (position) {
            case 0:

                fragment = new TaskSelection_Final();

                break;

            case 1:

                break;

            case 2:
                BaseConfig.temp_updateflag = "true";
                BaseConfig.globalStartIntent(this, Doctor_Profile.class, null);
                break;

            case 3:

                BaseConfig.globalStartIntent(this, SettingActivity.class, null);

                break;

            case 4:

                new CustomKDMCDialog(this)
                        .setLayoutColor(R.color.green_500)
                        .setImage(drawable.ic_exit_to_app_black_24dp)
                        .setTitle(getResources().getString(string.message_title))
                        .setDescription(this.getString(string.str_signout_msg))
                        .setPossitiveButtonTitle("YES")
                        .setNegativeButtonTitle("NO")
                        .setOnPossitiveListener(() -> {

                            BaseConfig.StopwebService_Import();
                            BaseConfig.StopwebService_Export();
                            Log.e("Webservices", "************Stopped***********");
                            finish();
                            Intent intent = new Intent(this.getApplicationContext(), Login.class);
                            this.startActivity(intent);

                        });

                break;


        }


        if (fragment != null) {
            FragmentManager fragmentManager = this.getFragmentManager();
            fragmentManager.beginTransaction().replace(id.dashboard_content_frame, fragment).commit();
            this.dashboardLeftDrawer.setItemChecked(position, true);
            this.dashboardLeftDrawer.setSelection(position);
            this.dashboardDrawerLayout.closeDrawer(this.dashboardLeftDrawer);

        }

    }

    @Override
    public final void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mDrawerToggle.syncState();
    }

    public void CheckPending() {

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


    public static class NotificationClass {
        public String TestName = "";
        public String TestValues = "";
        public String destinationClass = "";
        public String MtestId = "";
        public String Patid = "";

        public NotificationClass() {
        }
    }


    public class DrawerItemClickListener implements OnItemClickListener {

        private DrawerItemClickListener() {

        }

        @Override
        public final void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Dashboard_NavigationMenu.this.selectItem(i);

        }

    }


    public class CheckTrail extends AsyncTask<Void, Void, Void> {

        CheckTrail() {
        }

        protected final void onPostExecute(Void result) {


            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor cc = db.rawQuery("select isactive from TrailCheck where isupdate='1'", null);
            if (cc != null) {
                if (cc.moveToFirst()) {
                    do {

                        if (cc.getString(cc.getColumnIndex("isactive")).equals("0")) {


                            //sound
                            MediaPlayer mp = null;
                            if (mp != null) {
                                mp.release();
                            }
                            MediaPlayer mp1 = MediaPlayer.create(Dashboard_NavigationMenu.this.getApplicationContext(), raw.notify);
                            mp1.start();


                            View v1 = null;

                            View v11 = Dashboard_NavigationMenu.this.getWindow().getDecorView();
                            v11.setBackgroundResource(color.transparent);
                            LayoutInflater li = LayoutInflater.from(Dashboard_NavigationMenu.this);
                            View promptsView = li.inflate(layout.blockscreen, null);

                            Builder alertDialogBuilder = new Builder(Dashboard_NavigationMenu.this).setCancelable(false);


                            alertDialogBuilder.setView(promptsView);


                            TextView status_txt = promptsView.findViewById(id.textView1);
                            Button exit_btn = promptsView.findViewById(id.button1);

                            status_txt.setText(string.trail_label);


                            // create alert dialog

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = anim.myanimation;
                            alertDialog.show();

                            exit_btn.setOnClickListener(v2 -> finish());
                        }


                    } while (cc.moveToNext());
                }
            }
            Objects.requireNonNull(cc).close();

            db.close();


            Dashboard_NavigationMenu.this.Expiry.cancel(true);
            Dashboard_NavigationMenu.this.Expiry = new Dashboard_NavigationMenu.CheckTrail();


        }


        @Override
        protected final Void doInBackground(Void... params) {


            return null;
        }
    }

    class CheckApplcationExpiry extends AsyncTask<Void, Void, Void> {

        CheckApplcationExpiry() {
        }


        protected final void onPostExecute(Void result) {

            try {

                SQLiteDatabase db = BaseConfig.GetDb();
                Cursor cc = db.rawQuery("select * from AppValidityCheck where isupdate='1'", null);
                if (cc != null) {
                    if (cc.moveToFirst()) {
                        do {

                            if (cc.getString(cc.getColumnIndex("isactive")).equals("0")) {


                                //sound
                                MediaPlayer mp = null;
                                if (mp != null) {
                                    mp.release();
                                }
                                MediaPlayer mp1 = MediaPlayer.create(Dashboard_NavigationMenu.this.getApplicationContext(), raw.notify);
                                mp1.start();


                                View v1 = null;

                                View v11 = Dashboard_NavigationMenu.this.getWindow().getDecorView();
                                v11.setBackgroundResource(color.transparent);
                                LayoutInflater li = LayoutInflater.from(Dashboard_NavigationMenu.this);
                                View promptsView = li.inflate(layout.blockscreen, null);

                                Builder alertDialogBuilder = new Builder(Dashboard_NavigationMenu.this).setCancelable(false);


                                alertDialogBuilder.setView(promptsView);

                                TextView status_txt = promptsView.findViewById(id.textView1);
                                Button exit_btn = promptsView.findViewById(id.button1);

                                status_txt.setText(String.format("%s%s\nExpiry date:%s\nTo continue, please contact mobydoctor.\nWebsite: http://www.mobydoctor.com/ \nEmail: help@mobydoctor.com", Dashboard_NavigationMenu.this.getString(string.app_expired), cc.getString(cc.getColumnIndex("installed_date")), cc.getString(cc.getColumnIndex("expiry_date"))));
                                status_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0F);
                                status_txt.setGravity(Gravity.LEFT);
                                status_txt.setPadding(10, 10, 10, 10);
                                status_txt.setLineSpacing(1.0F, 1.0F);

                                // create alert dialog
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = anim.myanimation;
                                alertDialog.show();

                                exit_btn.setOnClickListener(v2 -> finish());
                            }


                        } while (cc.moveToNext());
                    }
                }
                Objects.requireNonNull(cc).close();

                db.close();


                Dashboard_NavigationMenu.this.AppExpiry.cancel(true);
                Dashboard_NavigationMenu.this.AppExpiry = new Dashboard_NavigationMenu.CheckApplcationExpiry();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }

    public class CheckPendingData extends AsyncTask<Void, Void, Void> {

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);


            try {

                int total = Dashboard_NavigationMenu.this.COUNT_PATIENT_REGISTRATION + Dashboard_NavigationMenu.this.COUNT_CLINICAL_INFORMATION + Dashboard_NavigationMenu.this.COUNT_PRESCRIPTION + Dashboard_NavigationMenu.this.COUNT_CASENOTES + Dashboard_NavigationMenu.this.COUNT_INVESTIGATION;

                if (total > 0) {

                    Dashboard_NavigationMenu.this.dashboardToolbarIcCloud.setVisibility(View.VISIBLE);
                    Dashboard_NavigationMenu.this.dashboardToolbarCloudNotificationbatch.setVisibility(View.VISIBLE);
                    Dashboard_NavigationMenu.this.dashboardToolbarCloudNotificationbatch.setText(String.valueOf(total));

                } else {

                    Dashboard_NavigationMenu.this.dashboardToolbarIcCloud.setVisibility(View.GONE);
                    Dashboard_NavigationMenu.this.dashboardToolbarCloudNotificationbatch.setVisibility(View.GONE);

                }

                // notify the adapter
                total = 0;


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Dashboard_NavigationMenu.this.CheckPending();
            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }

    }


}//END

