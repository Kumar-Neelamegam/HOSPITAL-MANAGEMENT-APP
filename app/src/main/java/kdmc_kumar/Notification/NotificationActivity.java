package kdmc_kumar.Notification;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Core_Modules.BaseConfig;

public class NotificationActivity extends AppCompatActivity implements OnClickListener {

    private final String compareRBSName = "blood sug random";
    private final String compareFBSName = "blood sug fasting";
    private final String comparePBSName = "blood sug pp";
    private RecyclerView recyclerView;
    private ArrayList<CommonDataObjects.NotificationGetSet> notificationGetSets;
    private Button ok;
    private LinearLayoutManager linearLayoutManager;
    private NotificationAdapter notificationAdapter;

    public NotificationActivity() {
    }

    private static final String checkNull(String val) {
        String returns_Val = "";
        try {


            return val.equalsIgnoreCase("null") || val.equalsIgnoreCase(null) || val.equalsIgnoreCase("") ? " " : val;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return returns_Val;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_notifiation);
        this.setTitle(BaseConfig.Appname + " Notification");

        this.init();
        this.ok.setOnClickListener(this);

        this.notificationAdapter = new NotificationAdapter(this.getPatientDetails());
        this.recyclerView.setAdapter(this.notificationAdapter);

    }

    private final ArrayList<CommonDataObjects.NotificationGetSet> getPatientDetails() {
        ArrayList<CommonDataObjects.NotificationGetSet> value = new ArrayList<>();
        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String PATIENT_ID = c.getString(c.getColumnIndex("Ptid"));
                        String MEDICAL_TEST_ID = c.getString(c.getColumnIndex("mtestid"));
                        String PATIENT_NAME = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PATIENT_ID + "'");

                        value.add(new CommonDataObjects.NotificationGetSet(PATIENT_NAME, PATIENT_ID, MEDICAL_TEST_ID, this.getTableData(PATIENT_ID)));

                    }
                    while (c.moveToNext());
                }
            }

            c.close();
            db.close();
        }

        return value;
    }

    private ArrayList<CommonDataObjects.NotificationListGetSet> getTableData(String patientid) {
        ArrayList<CommonDataObjects.NotificationListGetSet> values = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();

        Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') and Ptid='" + patientid + '\'', null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String va = "";

                    if (NotificationActivity.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareRBSName)) {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("temperature")), c.getString(c.getColumnIndex("testsummary"))));


                    } else if (NotificationActivity.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareFBSName)) {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bps")), c.getString(c.getColumnIndex("testsummary"))));

                    } else if (NotificationActivity.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.comparePBSName)) {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bpd")), c.getString(c.getColumnIndex("testsummary"))));

                    } else {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("testvalue")), c.getString(c.getColumnIndex("testsummary"))));
                    }


                } while (c.moveToNext());
            }
        }
        c.close();
        return values;
    }

    private void init() {
        this.recyclerView = this.findViewById(id.recyler_view);
        this.ok = this.findViewById(id.ok);
        this.notificationGetSets = new ArrayList<>();

        this.linearLayoutManager = new LinearLayoutManager(this);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.linearLayoutManager);
    }

    @Override
    public final void onClick(View view) {
        finish();

        SQLiteDatabase db = BaseConfig.GetDb();
        ContentValues values = new ContentValues();
        values.put("seen", "1");
        db.update("Medicaltestdtls", values, "", null);
    }
}
