package kdmc_kumar.Masters_Modules;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;

public class Masters_New extends AppCompatActivity {

    //**********************************************************************************************
    public boolean rotate;
    /**
     * Muthukumar N
     * 08-05-2018
     */
    //**********************************************************************************************
    //Declaration
    @BindView(id.content)
    RelativeLayout content;
    @BindView(id.tab_layout)
    TabLayout tabLayout;
    @BindView(id.simpleFrameLayout)
    FrameLayout simpleFrameLayout;
    @BindView(id.back_drop)
    View backDrop;
    @BindView(id.lyt_1)
    LinearLayout lyt1;
    @BindView(id.fab_1)
    FloatingActionButton fab1;
    @BindView(id.lyt_2)
    LinearLayout lyt2;
    @BindView(id.fab_2)
    FloatingActionButton fab2;
    @BindView(id.lyt_3)
    LinearLayout lyt3;
    @BindView(id.fab_3)
    FloatingActionButton fab3;
    @BindView(id.fab_add)
    FloatingActionButton fabAdd;

    @BindView(id.toolbar)
    Toolbar toolbar;
    @BindView(id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title)
    TextView toolbarTitle;
    @BindView(id.toolbar_exit)
    AppCompatImageView toolbarExit;
    //**********************************************************************************************

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.kdmc_layout_masters);


        try {
            this.GETINITIALIZE();
            this.CONTROLLISTENERS();
        } catch (Exception e) {
            //e.printStackTrace();
        }


    }
   public static Tab Category,Brand;
    //**********************************************************************************************
    private void CONTROLLISTENERS() {


        Tab Items = this.tabLayout.newTab();
        Items.setText("Prescription Templates");
        Items.setIcon(drawable.dashboard_ic_prescription);
        this.tabLayout.addTab(Items);

        Masters_New.Category = this.tabLayout.newTab();
        Masters_New.Category.setText("My Preferred Medicine List");
        Masters_New.Category.setIcon(drawable.dashboard_ic_case_book);
        this.tabLayout.addTab(Masters_New.Category);

        Masters_New.Brand = this.tabLayout.newTab();
        Masters_New.Brand.setText("My Preferred Test List");
        Masters_New.Brand.setIcon(drawable.dashboard_ic_medical_test);
        this.tabLayout.addTab(Masters_New.Brand);


        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        this.tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, Mode.SRC_IN);
        this.tabLayout.getTabAt(1).getIcon().setColorFilter(this.getResources().getColor(color.grey_20), Mode.SRC_IN);
        this.tabLayout.getTabAt(2).getIcon().setColorFilter(this.getResources().getColor(color.grey_20), Mode.SRC_IN);

        this.tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

                tab.getIcon().setColorFilter(Color.WHITE, Mode.SRC_IN);
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {

                    case 0:
                        fragment = new Fragment_Prescription_Templates();
                        break;

                    case 1:
                        fragment = new Fragment_My_Preferred_Medicine_List();
                        break;

                    case 2:
                        fragment = new Fragment_My_Preferred_Test_List();
                        break;


                }

                FragmentManager fm = Masters_New.this.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }

            @Override
            public void onTabUnselected(Tab tab) {
                tab.getIcon().setColorFilter(Masters_New.this.getResources().getColor(color.grey_20), Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(Tab tab) {

                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {

                    case 0:
                        fragment = new Fragment_Prescription_Templates();
                        break;

                    case 1:
                        fragment = new Fragment_My_Preferred_Medicine_List();
                        break;

                    case 2:
                        fragment = new Fragment_My_Preferred_Test_List();
                        break;


                }

                FragmentManager fm = Masters_New.this.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });


        Fragment fragment = new Fragment_Prescription_Templates();
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


        ViewAnimation.initShowOut(this.lyt1);
        ViewAnimation.initShowOut(this.lyt2);
        ViewAnimation.initShowOut(this.lyt3);

        this.backDrop.setVisibility(View.GONE);

        this.fabAdd.setOnClickListener(v -> this.toggleFabMode(v));

        this.backDrop.setOnClickListener(v -> this.toggleFabMode(this.fabAdd));

        this.fab1.setOnClickListener(v -> {
            this.toggleFabMode(v);
            this.startActivity(new Intent(this, templates_addnew.class));
            BaseConfig.temp_flag="true";
        });

        this.fab2.setOnClickListener(v -> {
            this.toggleFabMode(v);
            this.addMedicineNamePopupDialog((Activity) this.fab1.getContext());
        });

        this.fab3.setOnClickListener(v -> {

            this.toggleFabMode(v);

            this.addTestPopupDialog((Activity) this.fab3.getContext());


        });


    }

    public void addMedicineNamePopupDialog(Activity activity) {
        try {
            AlertDialog alertDialog =null;
            LayoutInflater li = LayoutInflater.from(this.getApplicationContext());
            View prompt = li.inflate(layout.popup_medicine_layout, null);
            Builder alertDialogBuilder = new Builder(this);
            AutoCompleteTextView medicine_name = prompt.findViewById(id.medcineName);
            Button add = prompt.findViewById(id.add);
            alertDialogBuilder.setView(prompt);

            medicine_name.setThreshold(1);

            medicine_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (medicine_name.getText().toString().length() > 0) {
                        String Query = "select distinct dvalue from (SELECT id,(CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims  order by id) as temp order by dvalue";
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, activity, medicine_name, charSequence.toString());

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });


            alertDialog=alertDialogBuilder.show();

            AlertDialog finalAlertDialog = alertDialog;
            add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Masters_New.this.checkValidation(medicine_name)) {

                        String Get_Medicine = medicine_name.getText().toString();

                        boolean b = BaseConfig.LoadBooleanStatus("select medicine from NewMedicine where medicine='" + Get_Medicine + '\'');

                        if (b) {

                            medicine_name.setText("");
                            medicine_name.setFocusable(true);
                            BaseConfig.KDMC_COMMON_DILOAGS(3, activity, "Information", "Already selected medicine added in mypreferred list..", Masters_New.this.getString(string.ok));

                        } else {
                            Masters_New.this.InsertTODB(medicine_name);
                            finalAlertDialog.cancel();
                        }


                    } else {
                        Toast.makeText(activity,
                                "check for missing & valid Data", Toast.LENGTH_LONG)
                                .show();
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addTestPopupDialog(Activity activity) {
        try {

            AlertDialog alertDialog =null;

                    LayoutInflater li = LayoutInflater.from(this.getApplicationContext());
            View prompt = li.inflate(layout.popup_test_layout, null);
            Builder alertDialogBuilder = new Builder(this);
            AutoCompleteTextView auto_test = prompt.findViewById(id.auto_test);
            AutoCompleteTextView auto_subtest = prompt.findViewById(id.auto_subtest);
            EditText templatename = prompt.findViewById(id.edt_templatename);
            Button add = prompt.findViewById(id.ic_add);
            alertDialogBuilder.setView(prompt);
            alertDialog=alertDialogBuilder.show();

            auto_subtest.setThreshold(1);
            auto_test.setThreshold(1);

            auto_test.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (auto_test.getText().toString().length() > 0) {
                        String Query = "select distinct Testname as dvalue  from Testname where IsActive='1'";
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, activity, auto_test, charSequence.toString());

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });

            auto_subtest.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (auto_subtest.getText().toString().length() > 0) {
                        String Query = "select SubTest as dvalue from Testname where IsActive=1 and Testname='" + auto_test.getText() + '\'';
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query,activity, auto_subtest, charSequence.toString());
                        //LoadValues(SubTest, prefered_test.this, Query, 1);
                        //spinner2meth(prefered_test.this, Loadlist1, SubTest);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });


            AlertDialog finalAlertDialog = alertDialog;
            add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Masters_New.this.checkValidation(auto_test)) {
                        String Get_TemplateName = templatename.getText().toString();
                        String Get_TestName = auto_test.getText().toString();
                        String Get_SubTestName = auto_subtest.getText().toString();

                        boolean b = BaseConfig.LoadBooleanStatus("select Testname from MyFavTest where TemplateName='" + Get_TemplateName + "' and Testname='" + Get_TestName + "' and SubTest='" + Get_SubTestName + '\'');

                        if (b) {

                            auto_test.setText("");
                            auto_subtest.setText("");
                            auto_test.setFocusable(true);
                            BaseConfig.KDMC_COMMON_DILOAGS(3, activity, "Information", "Already selected testname and subtest added in mypreferred list..", Masters_New.this.getString(string.ok));

                        } else {
                            SQLiteDatabase db = BaseConfig.GetDb();
                             Get_TemplateName = templatename.getText().toString();
                             Get_TestName = auto_test.getText().toString();
                             Get_SubTestName = auto_subtest.getText().toString();
                            ContentValues values = new ContentValues();
                            values.put("TemplateName", Get_TemplateName);
                            values.put("Testname", Get_TestName);
                            values.put("SubTest", Get_SubTestName);
                            values.put("Isupdate", Integer.valueOf(1));
                            db.insert("MyFavTest", null, values);
                            db.close();

                            finalAlertDialog.cancel();

                            new CustomKDMCDialog(activity)
                                    .setLayoutColor(color.green_500)
                                    .setImage(drawable.ic_success_done)
                                    .setNegativeButtonVisible(View.GONE)
                                    .setTitle(activity.getString(string.information))
                                    .setOnPossitiveListener(() -> {

                                    })
                                    .setDescription("Preferred Test & Subtest - Saved Successfully")
                                    .setPossitiveButtonTitle(activity.getString(string.ok));

                            Masters_New.Brand.select();
                        }


                    } else {
                        Toast.makeText(activity,
                                "check for missing & valid Data", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final void InsertTODB(AutoCompleteTextView medicinename) {

        SQLiteDatabase db = BaseConfig.GetDb();


        String Get_MedicineName = medicinename.getText().toString();

        ContentValues values = new ContentValues();
        values.put("medicine", Get_MedicineName);
        values.put("Isupdate", Integer.valueOf(1));
        db.insert("NewMedicine", null, values);

        db.close();

        new CustomKDMCDialog(this)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_success_done)
                .setTitle(getString(string.information))
                .setOnPossitiveListener(() -> {

                })
                .setNegativeButtonVisible(View.GONE)
                .setDescription("Preferred Medicine - Saved Successfully")
                .setPossitiveButtonTitle(getString(string.ok));

        Masters_New.Category.select();

    }

    private boolean checkValidation(AutoCompleteTextView medicinename) {

        boolean ret = true;

        if (!Validation1.hasText(medicinename))
            ret = false;


        return ret;
    }

    private void toggleFabMode(View v) {
        this.rotate = ViewAnimation.rotateFab(v, !this.rotate);
        if (this.rotate) {
            ViewAnimation.showIn(this.lyt1);
            ViewAnimation.showIn(this.lyt2);
            ViewAnimation.showIn(this.lyt3);
            this.backDrop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(this.lyt1);
            ViewAnimation.showOut(this.lyt2);
            ViewAnimation.showOut(this.lyt3);
            this.backDrop.setVisibility(View.GONE);
        }
    }


    //**********************************************************************************************
    private void GETINITIALIZE() {
        BaseConfig.welcometoast = 0;

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.master)));

        this.setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }


        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarBack.setOnClickListener(view -> this.LoadBack());



    }

    //**********************************************************************************************

    private void LoadBack()
    {

        finish();
        Intent intent = new Intent(this,Dashboard_NavigationMenu.class);
        this.startActivity(intent);
        BaseConfig.Druglistselindex = "";

    }

    //**********************************************************************************************


    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(this, Dashboard_NavigationMenu.class);
        this.startActivity(intent);
        CustomIntent.customType(this, 4);

    }
    //**********************************************************************************************


}//END
