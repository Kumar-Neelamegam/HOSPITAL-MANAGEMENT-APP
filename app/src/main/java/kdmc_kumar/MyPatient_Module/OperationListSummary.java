package kdmc_kumar.MyPatient_Module;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DiagnosisSummaryAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;

public class OperationListSummary extends AppCompatActivity {

    private ArrayList<CommonDataObjects.DiagnosisItem> diagnosisItems;
    private DiagnosisSummaryAdapter diagnosisSummaryAdapter;

    private String BUNDLE_PATIENT_ID;

    public OperationListSummary() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_operation_list_summary);

       // ActionBar bar = getSupportActionBar();
       // BaseConfig.getActionBar(bar, 1);

        //getSupportActionBar().setTitle(getString(R.string.welcome) + " " + BaseConfig.doctorname);
        //getSupportActionBar().setTitle(getString(R.string.welcome) + " - " + BaseConfig.doctorname);

        Bundle b = this.getIntent().getExtras();

        this.BUNDLE_PATIENT_ID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);

        String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');

        TextView textView = this.findViewById(id.patientname);

        textView.setText(String.format("%s : %s-%s", this.getString(string.operation_details), Patient_Name, this.BUNDLE_PATIENT_ID));

        RecyclerView recyclerView = this.findViewById(id.recyler_view_dignosis);

        this.setReclerViewDetails_Dignosis(recyclerView);

    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem1 = menu.add(0, 1, 1, "Back");
        {
            menuItem1.setIcon(drawable.prev_icon);
            menuItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            menuItem1.setOnMenuItemClickListener(item -> {
                finish();
                return false;
            });

        }
        MenuItem menuItem2 = menu.add(0, 2, 2, "Home");
        {
            menuItem2.setIcon(drawable.home_ico);
            menuItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            menuItem2.setOnMenuItemClickListener(item -> {
                finishAffinity();
                this.startActivity(new Intent(this, Dashboard_NavigationMenu.class));
                return false;
            });

        }
        MenuItem item4 = menu.add(0, 3, 3, string.sign_out);
        {

            item4.setIcon(drawable.signout_icon_orange);
            item4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            item4.setOnMenuItemClickListener(arg0 -> {

                BaseConfig.ExitSweetDialog(this, null);


                return true;
            });
        }
        return true;
    }

    private final void setReclerViewDetails_Dignosis(RecyclerView reclerViewDetails) {
        this.diagnosisItems = new ArrayList<>();

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//OperationListSummary.this);//openOrCreateDatabase(Environment.getExternalStorageDirectory().getPath() + "/.MobydoctorDatabase"+ File.separator + "mobydoc.db",0,null,null);

            int i = 0;
            db.isOpen();
            Cursor c = db.rawQuery("select DoctorName,Oper_Notes,Position,OperationProcedure,Post_Oper_Diagnosis,Post_Oper_Instruction,Closure,Pre_Oper_Diagnosis,Remark_Image from mast_Operation where patid='" + this.BUNDLE_PATIENT_ID + '\'', null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        ++i;
                        String DoctorName = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("DoctorName")));
                        String Oper_Notes = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Oper_Notes")));
                        String Position = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Position")));
                        String OperationProcedure = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("OperationProcedure")));
                        String Post_Oper_diagnosis = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Post_Oper_Diagnosis")));
                        String Post_Oper_Instruction = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Post_Oper_Instruction")));
                        String Closure = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Closure")));
                        String Pre_Oper_Diagnosis = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Pre_Oper_Diagnosis")));
                        String Remark_Image = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("Remark_Image")));

                        //DiagnosisItem(String s_No, String doctor, String OPerationNotes, String position, String operationProcedure, String postOPerativeDiagnosis, String postOpertiveinstruction, String closure, String preOperativeDiagnosis, String remark_Image)
                        this.diagnosisItems.add(new CommonDataObjects.DiagnosisItem(String.valueOf(i), DoctorName, Oper_Notes, Position, OperationProcedure, Post_Oper_diagnosis, Post_Oper_Instruction, Closure, Pre_Oper_Diagnosis, Remark_Image));

                    } while (c.moveToNext());
                }
            }
            c.close();
            this.diagnosisSummaryAdapter = new DiagnosisSummaryAdapter(this.diagnosisItems);
            LayoutManager mLayoutManager = new LinearLayoutManager(this);
            reclerViewDetails.setLayoutManager(mLayoutManager);
            reclerViewDetails.setItemAnimator(new DefaultItemAnimator());
            reclerViewDetails.setAdapter(this.diagnosisSummaryAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public final void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    //End
}
