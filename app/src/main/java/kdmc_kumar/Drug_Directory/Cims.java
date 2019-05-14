package kdmc_kumar.Drug_Directory;

import android.R.id;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DrugRecylerAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;

public class Cims extends AppCompatActivity {


    private final Context context = this;
    //declaration
    BaseConfig bcnfg = new BaseConfig();
    String[] values1 = {"1001"};
    String[] values2 = {"1001  Gastrointestinal and hepatobiliary System   		7-LA"};
    private Cims.DownloadFilesIssue1 objbgtsk1 = new Cims.DownloadFilesIssue1();
    private Cims.DownloadFilesIssue2 objbgtsk2 = new Cims.DownloadFilesIssue2();
    private Cims.DownloadFilesIssue3 objbgtsk3 = new Cims.DownloadFilesIssue3();
    private Cims.DownloadFilesIssue4 objbgtsk4 = new Cims.DownloadFilesIssue4();
    private Cims.DownloadFilesIssue5 objbgtsk5 = new Cims.DownloadFilesIssue5();
    private Cims.DownloadFilesIssue6 objbgtsk6 = new Cims.DownloadFilesIssue6();
    ArrayList<HashMap<String, String>> titles_list;
    ArrayAdapter<CommonDataObjects.RowItemVaccination> adapter;
    private AutoCompleteTextView system;
    private AutoCompleteTextView druggeneric;
    private AutoCompleteTextView chemicalname;
    private AutoCompleteTextView brandname;
    private AutoCompleteTextView odm;
    private AutoCompleteTextView composition;
    private TextView counttxt;
    private List<CommonDataObjects.RowItemVaccination> rowItems;
    private final List<String> list = new ArrayList<>();
    private final List<String> listcnt = new ArrayList<>();
    private final ArrayList<CommonDataObjects.DrugItem> drugItemList = new ArrayList<>();
    private Button search;
    private Button clear;
    private RecyclerView medlist;
    //Testing datatable layout
    TableRow tablerw;
    private DrugRecylerAdapter drugRecylerAdapter;
    LinearLayoutManager mLayoutManager;


    private final List<String> list1 = new ArrayList<>();
    private final List<String> list2 = new ArrayList<>();
    private final List<String> list3 = new ArrayList<>();
    private final List<String> list4 = new ArrayList<>();
    private final List<String> list5 = new ArrayList<>();
    private final List<String> list6 = new ArrayList<>();
    private final Typeface tfavv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;
    // #######################################################################################################
    private GridLayoutManager lLayout;

    public Cims() {
    }

    // #######################################################################################################
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_cims);

        try {

            this.GETINITIALIZE();

            this.CONTROLLISTENERS();


        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void LoadBack() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

        BaseConfig.Druglistselindex = "";

    }

    private void GETINITIALIZE() {


        BaseConfig.welcometoast = 0;

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.drug_dir2)));

        this.setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

       // toolbar.setBackgroundColor(Color.parseColor(Cims.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));


        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarBack.setOnClickListener(view -> this.LoadBack());


        this.rowItems = new ArrayList<>();

        this.search = this.findViewById(R.id.button1);
        this.clear = this.findViewById(R.id.button2);
        this.medlist = this.findViewById(R.id.listView1);

        this.lLayout = new GridLayoutManager(this, 2);
        this.medlist.setHasFixedSize(true);
        this.medlist.setLayoutManager(this.lLayout);
        this.medlist.setNestedScrollingEnabled(false);
        this.medlist.setItemAnimator(new DefaultItemAnimator());


        assert this.medlist != null;

        this.counttxt = this.findViewById(R.id.textView7);

        this.system = this.findViewById(R.id.AutoCompleteTextView1);
        this.system.setThreshold(1);
        this.druggeneric = this.findViewById(R.id.AutoCompleteTextView2);
        this.druggeneric.setThreshold(1);
        this.chemicalname = this.findViewById(R.id.AutoCompleteTextView3);
        this.chemicalname.setThreshold(1);
        this.brandname = this.findViewById(R.id.AutoCompleteTextView4);
        this.brandname.setThreshold(1);
        this.odm = this.findViewById(R.id.AutoCompleteTextView5);
        this.odm.setThreshold(1);
        this.composition = this.findViewById(R.id.AutoCompleteTextView6);
        this.composition.setThreshold(1);

        this.counttxt.setText(string.total_records);


        if (!BaseConfig.Druglistselindex.isEmpty()) {
            this.LoadBackData();
        }


        //Loading cims1 data background....
        this.objbgtsk1.execute();
        this.objbgtsk2.execute();
        this.objbgtsk3.execute();
        this.objbgtsk4.execute();
        this.objbgtsk5.execute();
        this.objbgtsk6.execute();

    }

    private final void LoadBackData() {
        this.system.setText(BaseConfig.b.getString("system"));
        this.druggeneric.setText(BaseConfig.b.getString("druggeneric"));
        this.chemicalname.setText(BaseConfig.b.getString("chemicalname"));
        this.brandname.setText(BaseConfig.b.getString("brandname"));
        this.odm.setText(BaseConfig.b.getString("odm"));
        this.composition.setText(BaseConfig.b.getString("composition"));

        this.SelectedDrugDetails();

        this.search.requestFocus();

    }

    // #######################################################################################################
    private void CONTROLLISTENERS() {


        this.search.setOnClickListener(v -> {
            if (this.system.getText().toString().isEmpty() && this.druggeneric.getText().toString().isEmpty() && this.chemicalname.getText().toString().isEmpty() && this.brandname.getText().toString().isEmpty() && this.odm.getText().toString().isEmpty() && this.composition.getText().toString().isEmpty()) {
                BaseConfig.showSimplePopUp("Enter values for drug informations", "Warning", this.context, drawable.ic_warning_black_24dp, color.orange_500);
            } else {
                this.SelectedDrugDetails();
            }

            BaseConfig.HideKeyboard(this);

        });


        this.clear.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            this.system.setText("");
            this.druggeneric.setText("");
            this.chemicalname.setText("");
            this.brandname.setText("");
            this.odm.setText("");
            this.composition.setText("");
            this.counttxt.setText("Total Records :");
            this.list.clear();

            BaseConfig.Druglistselindex = "";
        });


    }

    // =================================
    private final void SelectedDrugDetails() {
        SQLiteDatabase db = BaseConfig.GetDb();//);
        String WhereCondition = "";

        if (!this.system.getText().toString().isEmpty()) {
            WhereCondition = "where SYSTEM='" + this.system.getText() + '\'';


        }

        if (!this.druggeneric.getText().toString().isEmpty()) {
            WhereCondition = !this.system.getText().toString().equals("") ? WhereCondition + "and DRUGGENERICNAME='" + this.druggeneric.getText() + "'" : "where DRUGGENERICNAME='" + this.druggeneric.getText() + "'";
        }

        if (!this.chemicalname.getText().toString().isEmpty()) {
            WhereCondition = !this.system.getText().toString().equals("") || !this.druggeneric.getText().toString().equals("") ? WhereCondition + "and CHEMICALNAME='" + this.chemicalname.getText() + "'" : "where CHEMICALNAME='" + this.chemicalname.getText() + "'";
        }

        if (!this.brandname.getText().toString().isEmpty()) {
            WhereCondition = !this.system.getText().toString().equals("") || !this.druggeneric.getText().toString().equals("") || !this.chemicalname.getText().toString().equals("") ? WhereCondition + "and MARKETNAMEOFDRUG='" + this.brandname.getText() + "'" : "where MARKETNAMEOFDRUG='" + this.brandname.getText() + "'";
        }

        if (!this.odm.getText().toString().isEmpty()) {
            WhereCondition = !this.system.getText().toString().equals("") || !this.druggeneric.getText().toString().equals("") || !this.chemicalname.getText().toString().equals("") || !this.brandname.getText().toString().equals("") ? WhereCondition + "and PHARMACOMPANY='" + this.odm.getText() + "'" : "where PHARMACOMPANY='" + this.odm.getText() + "'";
        }

        if (!this.composition.getText().toString().isEmpty()) {
            WhereCondition = !this.system.getText().toString().equals("") || !this.druggeneric.getText().toString().equals("") || !this.chemicalname.getText().toString().equals("") || !this.brandname.getText().toString().equals("") || !this.odm.getText().toString().equals("") ? WhereCondition + "and COMPOSITION='" + this.composition.getText() + "'" : "where COMPOSITION='" + this.composition.getText() + "'";
        }

        String Query = "select distinct MARKETNAMEOFDRUG,serverid,SYSTEM,PHARMACOMPANY,DOSAGE from cims1 " + WhereCondition + ';';
        Cursor c = db.rawQuery(Query, null);

        int count = 1;
        this.list.clear();
        this.listcnt.clear();
        this.drugItemList.clear();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    CommonDataObjects.DrugItem item = new CommonDataObjects.DrugItem();
                    item.setBrandName(c.getString(c.getColumnIndex("MARKETNAMEOFDRUG")));
                    item.setDosage(c.getString(c.getColumnIndex("DOSAGE")));
                    item.setPharmaCompany(c.getString(c.getColumnIndex("MARKETNAMEOFDRUG")));
                    item.setSystem(c.getString(c.getColumnIndex("SYSTEM")));
                    item.setServerid(c.getString(c.getColumnIndex("serverid")));
                    this.drugItemList.add(item);
                    String pdtls = String.valueOf(count) + ". \t" + c.getString(c.getColumnIndex("SYSTEM")) + " \t- \t" + c.getString(c.getColumnIndex("MARKETNAMEOFDRUG")) + " \t- \t" + c.getString(c.getColumnIndex("PHARMACOMPANY")) + " \t- \t" + c.getString(c.getColumnIndex("DOSAGE"));//+" - "+c.getString(c.getColumnIndex("MARKETNAMEOFDRUG"));
                    String dataindx = c.getString(c.getColumnIndex("serverid"));

                    this.list.add(pdtls);

                    this.listcnt.add(dataindx);

                    count++;

                } while (c.moveToNext());
            }
        }


        ArrayAdapter<String> CountryDataAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_list_item_1, id.text1, this.list);
        CountryDataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        this.drugRecylerAdapter = new DrugRecylerAdapter(this.drugItemList);

        assert this.medlist != null;


        this.medlist.setAdapter(this.drugRecylerAdapter);

        this.counttxt.setText(String.format("%s %s", this.getString(string.total_records_txt), String.valueOf(this.list.size())));
        c.close();
        db.close();

        if (String.valueOf(this.list.size()).equals("0")) {

            Toast.makeText(this, this.getResources().getString(string.no_details), Toast.LENGTH_SHORT).show();
        }

    }

    // #######################################################################################################
    @Override
    public final void onBackPressed() {
        // Do Here what ever you want do on back press;
        this.LoadBack();
    }

    private void LoadData(AutoCompleteTextView autotxt, Context cntxt, String Query, int id) {
        // TODO Auto-generated method stub
        ArrayList<String> spnlist1;

        SQLiteDatabase db = BaseConfig.GetDb();
        spnlist1 = new ArrayList<>();

        Cursor c = db.rawQuery(Query, null);

        if (id == 1) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        this.list1.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 2) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        this.list2.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 3) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        this.list3.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 4) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        this.list4.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 5) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        this.list5.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 6) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        this.list6.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        c.close();
        db.close();

    }

    private final void spinner2meth(Context cntxt, List<String> list, AutoCompleteTextView spinnertxt) {
        // TODO Auto-generated method stub
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(cntxt,/*android.R.layout.simple_spinner_dropdown_item*/layout.simple_list, list) {
            @NonNull
            public View getView(int position, View convertView, @NonNull android.view.ViewGroup parent) {

                TextView v = (TextView) super.getView(position, convertView, parent);

                v.setTextSize(14.0F);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(Cims.this.tfavv);
                //v.setTextColor(Color.BLACK);
                v.setTextSize(14.0F);
                return v;
            }
        };

        adapter.setDropDownViewResource(layout.simple_list);

        spinnertxt.setAdapter(adapter);

    }


    //**********************************************************************************************************
    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue1 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue1() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 1.0");

            Cims.this.spinner2meth(Cims.this, Cims.this.list1, Cims.this.system);
            /*

             */


            Cims.this.objbgtsk1.cancel(true);
            Cims.this.objbgtsk1 = new Cims.DownloadFilesIssue1();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 1.0");

            Cims.this.LoadData(Cims.this.system, Cims.this.context, "select distinct SYSTEM  as dvalue from cims1  where SYSTEM!='' order by SYSTEM;", 1);
            /*LoadData(druggeneric,context,"select distinct DRUGGENERICNAME  as dvalue from cims1  where DRUGGENERICNAME!='' order by DRUGGENERICNAME;",2);


             */

            return null;
        }


    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue2 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue2() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 2.0");

            Cims.this.spinner2meth(Cims.this, Cims.this.list2, Cims.this.druggeneric);

            Cims.this.objbgtsk2.cancel(true);
            Cims.this.objbgtsk2 = new Cims.DownloadFilesIssue2();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 2.0");

            Cims.this.LoadData(Cims.this.druggeneric, Cims.this.context, "select distinct DRUGGENERICNAME  as dvalue from cims1  where DRUGGENERICNAME!='' order by DRUGGENERICNAME;", 2);

            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue3 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue3() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 3.0");
            Cims.this.spinner2meth(Cims.this, Cims.this.list3, Cims.this.chemicalname);
            Cims.this.objbgtsk3.cancel(true);
            Cims.this.objbgtsk3 = new Cims.DownloadFilesIssue3();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 3.0");

            Cims.this.LoadData(Cims.this.chemicalname, Cims.this.context, "select distinct CHEMICALNAME  as dvalue from cims1 where CHEMICALNAME!='' order by CHEMICALNAME;", 3);

            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue4 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue4() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 4.0");

            Cims.this.spinner2meth(Cims.this, Cims.this.list4, Cims.this.brandname);


            Cims.this.objbgtsk4.cancel(true);
            Cims.this.objbgtsk4 = new Cims.DownloadFilesIssue4();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 4.0");

            Cims.this.LoadData(Cims.this.brandname, Cims.this.context, "select distinct MARKETNAMEOFDRUG  as dvalue from cims1 where MARKETNAMEOFDRUG!='' order by MARKETNAMEOFDRUG;", 4);

            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue5 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue5() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 5.0");

            Cims.this.spinner2meth(Cims.this, Cims.this.list5, Cims.this.odm);

            Cims.this.objbgtsk5.cancel(true);
            Cims.this.objbgtsk5 = new Cims.DownloadFilesIssue5();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 5.0");

            Cims.this.LoadData(Cims.this.odm, Cims.this.context, "select distinct PHARMACOMPANY  as dvalue from cims1 where PHARMACOMPANY!='' order by PHARMACOMPANY;", 5);


            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue6 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue6() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 6.0");

            Cims.this.spinner2meth(Cims.this, Cims.this.list6, Cims.this.composition);


            Cims.this.objbgtsk6.cancel(true);
            Cims.this.objbgtsk6 = new Cims.DownloadFilesIssue6();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 6.0");

            Cims.this.LoadData(Cims.this.composition, Cims.this.context, "select distinct COMPOSITION  as dvalue from cims1 where COMPOSITION!='' order by COMPOSITION;", 6);

            return null;
        }

    }

    //**********************************************************************************************************
}
