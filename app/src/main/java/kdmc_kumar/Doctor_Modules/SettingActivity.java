package kdmc_kumar.Doctor_Modules;

import android.R.layout;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.atzcx.appverupdater.AppVerUpdater;
import com.github.atzcx.appverupdater.UpdateErrors;
import com.github.atzcx.appverupdater.callback.Callback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.LocalSharedPref;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;
import kdmc_kumar.Utilities_Others.toggle.OnToggledListener;
import kdmc_kumar.Webservices_NodeJSON.ImportWebservices_NODEJS;

public class SettingActivity extends AppCompatActivity {

    private static ProgressDialog pDialog;
    private final BaseConfig bcnfg = new BaseConfig();
    @BindView(id.language_radio)
    RadioGroup languageGroup;
    @BindView(id.marathiCheck)
    RadioButton marathiCheck;
    @BindView(id.englishCheck)
    RadioButton englishCheck;
    @BindView(id.setttings_parent_layout)
    CoordinatorLayout setttingsParentLayout;
    @BindView(id.toolbar)
    Toolbar settingsToolbar;
    @BindView(id.ic_back)
    AppCompatImageView icBack;
    @BindView(id.txvw_title)
    TextView txvwTitle;
    @BindView(id.ic_changepassword)
    AppCompatImageView icChngpwd;
    @BindView(id.ic_exit)
    AppCompatImageView icExit;
    @BindView(id.settings_nesetedscroll)
    NestedScrollView settingsNesetedscroll;
    @BindView(id.settings_cardlayout1)
    LinearLayout settingsCardlayout1;
    @BindView(id.settings_chkbx_pinnumber)
    CheckBox settingsChkbxPinnumber;
    @BindView(id.settings_edtxt_pinnumber)
    EditText settingsEdtxtPinnumber;
    @BindView(id.checkbox_casenotes_generalexamination)
    CheckBox checkboxCasenotesGeneralexamination;
    @BindView(id.checkbox_casenotes_cardio)
    CheckBox checkboxCasenotesCardio;
    @BindView(id.checkbox_casenotes_respiratory)
    CheckBox checkboxCasenotesRespiratory;
    @BindView(id.checkbox_casenotes_gastrointestinal)
    CheckBox checkboxCasenotesGastrointestinal;
    @BindView(id.checkbox_casenotes_neurology)
    CheckBox checkboxCasenotesNeurology;
    @BindView(id.checkbox_casenotes_locomotor)
    CheckBox checkboxCasenotesLocomotor;
    @BindView(id.checkbox_casenotes_renal)
    CheckBox checkboxCasenotesRenal;
    @BindView(id.checkbox_casenotes_endocrine)
    CheckBox checkboxCasenotesEndocrine;
    @BindView(id.checkbox_casenotes_clinical)
    CheckBox checkboxCasenotesClinical;
    @BindView(id.checkbox_casenotes_postnatal)
    CheckBox checkboxCasenotesPostnatal;
    @BindView(id.checkbox_casenotes_dental)
    CheckBox checkboxCasenotesDental;
    @BindView(id.checkbox_casenotes_other)
    CheckBox checkboxCasenotesOther;
    @BindView(id.settings_txtvw_workingdays)
    TextView settingsTxtvwWorkingdays;
    @BindView(id.editText_workingdays)
    EditText editTextWorkingdays;
    @BindView(id.textview_mrngtime_view)
    EditText textviewMrngtimeView;
    @BindView(id.edittext_evengtime_view)
    EditText edittextEvengtimeView;
    @BindView(id.editText_slot)
    EditText editTextSlot;
    @BindView(id.layout_appointment_noofpatients)
    LinearLayout Appointment_Slot;
    @BindView(id.textView_preferredpharmacy)
    TextView textViewPreferredpharmacy;
    @BindView(id.autocomplete_prefpharmacy)
    AutoCompleteTextView autocompletePrefpharmacy;
    @BindView(id.prefpharmacy_address)
    AutoCompleteTextView prefpharmacyAddress;
    @BindView(id.pref_labs)
    AutoCompleteTextView prefLabs;
    @BindView(id.autocomplete_pref_labs_address)
    AutoCompleteTextView autocompletePrefLabsAddress;
    @BindView(id.edittext_letter_space_head)
    EditText edittextLetterSpaceHead;
    @BindView(id.textvw_pharmacydetails)
    TextView textvw_pharmacyname;
    @BindView(id.textvw_laboratorydetails)
    TextView textvw_labname;
    @BindView(id.button_add)
    Button Add_WorkingDays;
    @BindView(id.button_cancel)
    Button Cancel;
    @BindView(id.button_submit)
    Button Submit;
    @BindView(id.check_app_update)
    TextView CheckAppUpdate;
    @BindView(id.server_info)
    TextView ServerInfo;
    private String STR_GENERALEXAMINATION;// = getResources().getString(R.string.general_examination);
    private String STR_CARDIOSYSTEM;// = getResources().getString(R.string.cardio_vascular_system);
    private String STR_RESPIRATORYSYSTEM;// = getResources().getString(R.string.respiratory_system);
    private String STR_GASTROSYSTEM;// = getResources().getString(R.string.gastrointestinal_system);
    private String STR_NEUROLOGY;// = getResources().getString(R.string.neurology_system);
    private String STR_LOCOMOTORSYSTEM;// = getResources().getString(R.string.locomotor_system);
    private String STR_RENLSYSTEM;// = getResources().getString(R.string.renal_system);
    private String STR_ENDOCRINESYSTEM;// = getResources().getString(R.string.endocrine_system);
    private String STR_CLINICAL;// = getResources().getString(R.string.clinical_informaiton);
    private String STR_OTHERS;// = getResources().getString(R.string.other_systems);
    private String STR_DENTAL;// = getResources().getString(R.string.dental_system);
    private String STR_PNC;// = getResources().getString(R.string.pnc_system);
    private final String smstgltxt;
    //private String consul_feetxt = null;
    //private String appntschdltxt = null;
    //private String collectreprttxt = null;
    //private String labpahrcounttxt = null;
    //private String enableappointmenttxt = null;
    //private String noofpatientcount = null;
    private LabeledSwitch switchAppointments;
    private LabeledSwitch switchDailyAppointmentSMS;
    private LabeledSwitch switchReferralPharmacy;
    private LabeledSwitch switchCheckupdate;

    private final String mLanguageCode_marathi = "mr";
    private final String mLanguageCode_english = "en";

    //**********************************************************************************************
    private AppVerUpdater appVerUpdater;
    //**********************************************************************************************
    private String ApkName;

    public SettingActivity() {
    }

    //**********************************************************************************************

    //*******************************************************************************************
    private static final boolean contains(String[] arr, String item) {

        for (String n : arr) {
            if (item.equalsIgnoreCase(n)) {
                return true;
            }
        }
        return false;
    }

    // //////////////////////////////////////////////////////////////////
    public static final void LoadContime(AutoCompleteTextView mrng1) {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select hrs from contime order by hrs;", null);

        List<String> list = new ArrayList<>();
        // list.add("Select State");

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String hrs = c.getString(c.getColumnIndex("hrs"));
                    list.add(hrs);

                } while (c.moveToNext());
            }
        }


        // BaseConfig.spinner2meth(context, list, mrng1);
        BaseConfig.loadSpinner(mrng1, list);

        db.close();
        c.close();
    }

    private static boolean checkValidation1(AutoCompleteTextView mrngfrom, AutoCompleteTextView mrngto, AutoCompleteTextView evefrom, AutoCompleteTextView eveto) {
        boolean ret = true;


        if (!Validation1.hasText(mrngfrom)) ret = false;

        if (!Validation1.hasText(mrngto)) ret = false;

        if (!Validation1.hasText(evefrom)) ret = false;

        if (!Validation1.hasText(eveto)) ret = false;


        return ret;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.kdmc_layout_settings_activity);

        try {


            this.GETINITIALIZE();

            this.CONTROLLISTENERS();

            this.LOADSETTINGSPIN();

            //      BaseConfig.LoadDoctorValues();


        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    Locale myLocale;


    public void setLocale(String lang)
    {
        BaseConfig.CurrentLangauges=lang;
        Locale myLocale = new Locale(lang);
        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;

        this.onConfigurationChanged(conf);

        res.updateConfiguration(conf, dm);


        Intent refresh = new Intent(this, Dashboard_NavigationMenu.class);
        this.startActivity(refresh);
        finish();
    }



    private void CONTROLLISTENERS() {

        this.CheckAppUpdate.setOnClickListener(v -> {
            if (BaseConfig.server_connectivity_status) {
                new LoginBackgroundProcesses().execute();
            } else {
                BaseConfig.SnackBar(this, "No server connectivity available..\ntry later..", this.setttingsParentLayout, 1);
            }
        });

        this.ServerInfo.setOnClickListener(view -> {

            StringBuilder str = new StringBuilder();
            str.append("Appname: " + BaseConfig.Appname);
            str.append("\n\nAppLogo: " + BaseConfig.AppLogo);
            str.append("\n\nAppLanguage: " + BaseConfig.AppLanguage);
            str.append("\n\nAppWebServiceType: " + BaseConfig.AppWebServiceType);
            str.append("\n\nAppNodeIP: " + BaseConfig.AppNodeIP);
            str.append("\n\nAppImagephpURL: " + BaseConfig.AppImagephpURL);
            str.append("\n\nAppDBFolderName: " + BaseConfig.AppDBFolderName);
            str.append("\n\nAppDatabaseName: " + BaseConfig.AppDatabaseName);
            str.append("\n\nAppDirectoryPictures: " + BaseConfig.AppDirectoryPictures);
            str.append("\n\nAppUpdateJson: " + BaseConfig.AppUpdateJson);


            new CustomKDMCDialog(this)
                    .setLayoutColor(color.green_500)
                    .setImage(drawable.ic_server_info)
                    .setTitle(getString(string.information))
                    .setNegativeButtonVisible(View.GONE)
                    .setOnPossitiveListener(() -> {

                    })
                    .setDescription(str.toString())
                    .setPossitiveButtonTitle(getString(string.ok));


        });


        this.settingsChkbxPinnumber.setOnCheckedChangeListener((arg0, arg1) -> {

            if (this.settingsChkbxPinnumber.isChecked()) {
                String first2 = this.getString(string.nebale_pin_no);
                String next2 = "<font color='#EE0000'><b>*</b></font>";
                this.settingsChkbxPinnumber.setText(Html.fromHtml(first2 + next2));

                this.settingsEdtxtPinnumber.setEnabled(true);
                this.settingsEdtxtPinnumber.requestFocus();

            } else {
                String first2 = this.getString(string.nebale_pin_no);
                String next2 = "<font color='#EE0000'><b></b></font>";
                this.settingsChkbxPinnumber.setText(Html.fromHtml(first2 + next2));

                this.settingsEdtxtPinnumber.setEnabled(false);
            }

        });

        this.Add_WorkingDays.setOnClickListener(this::LoadWorkingDays);

        this.icChngpwd.setOnClickListener(view -> {

            finish();
            Intent intent = new Intent(this.getApplicationContext(), ChangePassword.class);
            this.startActivity(intent);

        });

        this.icExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));


        this.icBack.setOnClickListener(view -> this.LoadBack());


        LocalSharedPref localSharedPref = new LocalSharedPref(this);
        String LanguageCode=localSharedPref.getValue("LocaleLanguage");

        if (!LanguageCode.equalsIgnoreCase("")&&!LanguageCode.isEmpty()) {
            if (localSharedPref.getValue("LocaleLanguage").contentEquals("mr")) {
                this.marathiCheck.setChecked(true);
            }else
            {
                this.englishCheck.setChecked(true);
            }
        }


        
      /*  if (LocaleHelper.getLanguage(this).equalsIgnoreCase("en")) {
            englishCheck.setChecked(true);
            BaseConfig.CurrentLangauges=mLanguageCode_english;
        } else if (LocaleHelper.getLanguage(this).equalsIgnoreCase("mr")) {
            marathiCheck.setChecked(true);
            BaseConfig.CurrentLangauges=mLanguageCode_marathi;
        }

        if (BaseConfig.CurrentLangauges.equalsIgnoreCase("mr")) {

            marathiCheck.setChecked(true);
        } else {
            englishCheck.setChecked(true);
        }*/


        this.languageGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (this.marathiCheck.isChecked()) {

                //LocaleHelper.setLocale(SettingActivity.this, mLanguageCode_marathi);
                BaseConfig.CurrentLangauges= this.mLanguageCode_marathi;
                this.setLocale(this.mLanguageCode_marathi);
                localSharedPref.setValue("LocaleLanguage","mr");
            } else {

                this.setLocale(this.mLanguageCode_english);
               // LocaleHelper.setLocale(SettingActivity.this, mLanguageCode_english);
                BaseConfig.CurrentLangauges= this.mLanguageCode_english;
                localSharedPref.setValue("LocaleLanguage","en");
            }

        });


        // ///////////////////////////////////////////////////////////////////////////////

        this.autocompletePrefpharmacy.setOnItemClickListener((arg0, arg1, arg2, arg3) -> this.SelectedPharmacyAddress());
        this.prefLabs.setOnItemClickListener((arg0, arg1, arg2, arg3) -> this.SelectedLaboratoryAddress());

        this.Submit.setOnClickListener(v -> {

            try {
                this.Savelocal();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        });

        this.Cancel.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(this, Dashboard_NavigationMenu.class);
            this.startActivity(intent);

        });

        this.switchAppointments.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                if (SettingActivity.this.switchAppointments.isOn()) {

                    SettingActivity.this.Appointment_Slot.setVisibility(View.VISIBLE);

                } else {

                    SettingActivity.this.Appointment_Slot.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (BaseConfig.CurrentLangauges != null){

            Locale myLocale = new Locale(BaseConfig.CurrentLangauges);
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            this.getBaseContext().getResources().updateConfiguration(newConfig, this.getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @RequiresApi(api = VERSION_CODES.KITKAT)
    private final void LoadWorkingDays(View v) {
        String[] loadtime = {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};

        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
        View promptView = layoutInflater.inflate(R.layout.workngdays_popup_new, null);
        Builder alertDialogBuilder = new Builder(v.getContext());
        alertDialogBuilder.setView(promptView);


        CheckBox chk1 = promptView.findViewById(id.checkBox_Monday);
        CheckBox chk2 = promptView.findViewById(id.checkBox_Tuesday);
        CheckBox chk3 = promptView.findViewById(id.checkBox_wednesday);
        CheckBox chk4 = promptView.findViewById(id.checkBox_thursday);
        CheckBox chk5 = promptView.findViewById(id.checkBox_friday);
        CheckBox chk6 = promptView.findViewById(id.checkBox_saturday);
        CheckBox chk7 = promptView.findViewById(id.checkBox_sunday);

        Button save = promptView.findViewById(id.button_save);
        Button cancel = promptView.findViewById(id.button_cancel);

        save.setText(this.getResources().getString(string.save));


        AutoCompleteTextView mrngfrom = promptView.findViewById(id.AutoCompleteTextView_MorningFrom);
        mrngfrom.setThreshold(1);
        AutoCompleteTextView mrngto = promptView.findViewById(id.AutoCompleteTextView_MorningTo);
        mrngto.setThreshold(1);
        AutoCompleteTextView evefrom = promptView.findViewById(id.AutoCompleteTextView__EveningFrom);
        evefrom.setThreshold(1);
        AutoCompleteTextView eveto = promptView.findViewById(id.AutoCompleteTextView_EveningTo);
        eveto.setThreshold(1);

        ArrayAdapter<String> listadapter = new ArrayAdapter<>(this, layout.simple_list_item_1, loadtime);
        mrngfrom.setAdapter(listadapter);
        mrngto.setAdapter(listadapter);
        evefrom.setAdapter(listadapter);
        eveto.setAdapter(listadapter);


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


        mrngfrom.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) SettingActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SettingActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });
        mrngto.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) SettingActivity.this.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SettingActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });
        evefrom.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) SettingActivity.this.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SettingActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });
        eveto.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) SettingActivity.this.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(SettingActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        });


        //loading popup values
        this.LoadpopupTime(chk1, chk2, chk3, chk4, chk5, chk6, chk7, mrngfrom, mrngto, evefrom, eveto);


        save.setOnClickListener(v12 -> {
            // TODO Auto-generated method stub
            Integer m1 = null, m2 = null, e1 = null, e2 = null;


            if (SettingActivity.checkValidation1(mrngfrom, mrngto, evefrom, eveto)) {

                Integer m11 = Integer.valueOf(Integer.parseInt(mrngfrom.getText().toString().replace(":", "")));
                Integer m21 = Integer.valueOf(Integer.parseInt(mrngto.getText().toString().replace(":", "")));

                Integer e11 = Integer.valueOf(Integer.parseInt(evefrom.getText().toString().replace(":", "")));
                Integer e21 = Integer.valueOf(Integer.parseInt(eveto.getText().toString().replace(":", "")));

                if (m21.intValue() > m11.intValue()) {
                    if (e21.intValue() > e11.intValue()) {
                        this.editTextWorkingdays.setText(null);
                        if (chk1.isChecked()) {
                            String Mon = chk1.getText()
                                    .toString();
                            if (this.editTextWorkingdays.getText().length() > 0) {
                                this.editTextWorkingdays.setText(',' + Mon);
                            } else {
                                this.editTextWorkingdays.setText(Mon);
                            }

                        }
                        if (chk2.isChecked()) {
                            String Tue = chk2.getText()
                                    .toString();
                            if (this.editTextWorkingdays.getText()
                                    .length() > 0) {
                                this.editTextWorkingdays.append(',' + Tue);
                            } else {
                                this.editTextWorkingdays.append(Tue);
                            }

                        }
                        if (chk3.isChecked()) {
                            String Wed = chk3.getText().toString();
                            if (this.editTextWorkingdays.getText().length() > 0) {
                                this.editTextWorkingdays.append(',' + Wed);
                            } else {
                                this.editTextWorkingdays.append(Wed);
                            }

                        }
                        if (chk4.isChecked()) {
                            String Thur = chk4.getText()
                                    .toString();
                            if (this.editTextWorkingdays.getText()
                                    .length() > 0) {
                                this.editTextWorkingdays
                                        .append(',' + Thur);
                            } else {
                                this.editTextWorkingdays.append(Thur);
                            }

                        }
                        if (chk5.isChecked()) {
                            String Fri = chk5.getText()
                                    .toString();
                            if (this.editTextWorkingdays.getText()
                                    .length() > 0) {
                                this.editTextWorkingdays.append(',' + Fri);
                            } else {
                                this.editTextWorkingdays.append(Fri);
                            }

                        }
                        if (chk6.isChecked()) {
                            String Sat = chk6.getText()
                                    .toString();
                            if (this.editTextWorkingdays.getText()
                                    .length() > 0) {
                                this.editTextWorkingdays.append(',' + Sat);
                            } else {
                                this.editTextWorkingdays.append(Sat);
                            }

                        }
                        if (chk7.isChecked()) {
                            String Sun = chk7.getText()
                                    .toString();
                            if (this.editTextWorkingdays.getText()
                                    .length() > 0) {
                                this.editTextWorkingdays.append(',' + Sun);
                            } else {
                                this.editTextWorkingdays.append(Sun);
                            }

                        }

                        String mrngtime = ("From " + mrngfrom.getText() + "-To " + mrngto.getText());
                        String evetime = ("From " + evefrom.getText() + "-To " + eveto.getText());

                        this.textviewMrngtimeView.setText(mrngtime);
                        this.edittextEvengtimeView.setText(evetime);

                        alertDialog.cancel();


                    } else {
                        Toast toast = Toast.makeText(this, string.selct_valid_timing, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        //Toast.makeText(context, "Select valid timing", Toast.LENGTH_LONG).show();
                        evefrom.setError("Required");
                        eveto.setError("Required");
                        evefrom.setText("");
                        eveto.setText("");

                    }
                } else {
                    Toast toast = Toast.makeText(this, string.selct_valid_timing, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    mrngfrom.setError("Required");
                    mrngto.setError("Required");
                    mrngfrom.setText("");
                    mrngto.setText("");

                }


            } else {
                Toast.makeText(this, string.ched_mis_valid_data, Toast.LENGTH_LONG).show();
            }

        });


        cancel.setOnClickListener(v1 -> {
            // TODO Auto-generated method stub
            alertDialog.cancel();
        });
    }

    private void GETINITIALIZE() {


        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.switchAppointments = this.findViewById(id.switch_appointments);
        this.switchDailyAppointmentSMS = this.findViewById(id.switch_DailyAppointmentSMS);
        this.switchReferralPharmacy = this.findViewById(id.Switch_ReferralPharmacy);
        this.switchCheckupdate = this.findViewById(id.Switch_Checkupdate);


        this.STR_GENERALEXAMINATION = this.getResources().getString(string.general_examination);
        this.STR_CARDIOSYSTEM = this.getResources().getString(string.cardio_vascular_system);
        this.STR_RESPIRATORYSYSTEM = this.getResources().getString(string.respiratory_system);
        this.STR_GASTROSYSTEM = this.getResources().getString(string.gastrointestinal_system);
        this.STR_NEUROLOGY = this.getResources().getString(string.neurology_system);
        this.STR_LOCOMOTORSYSTEM = this.getResources().getString(string.locomotor_system);
        this.STR_RENLSYSTEM = this.getResources().getString(string.renal_system);
        this.STR_ENDOCRINESYSTEM = this.getResources().getString(string.endocrine_system);
        this.STR_CLINICAL = this.getResources().getString(string.clinical_informaiton);
        this.STR_OTHERS = this.getResources().getString(string.other_systems);
        this.STR_DENTAL = this.getResources().getString(string.dental_system);
        this.STR_PNC = this.getResources().getString(string.pnc_system);

        this.txvwTitle.setText(this.getString(string.moby_settings));

        this.setSupportActionBar(this.settingsToolbar);

        String first = this.getString(string.prefred_pharmacies);
        String next = "<font color='#EE0000'><b></b></font>";
        this.textvw_pharmacyname.setText(Html.fromHtml(first + next));


        String first1 = this.getString(string.prefer_diag);
        String next1 = "<font color='#EE0000'><b></b></font>";
        this.textvw_labname.setText(Html.fromHtml(first1 + next1));

        this.settingsEdtxtPinnumber.setEnabled(false);


        this.autocompletePrefpharmacy.setThreshold(1);
        this.prefLabs.setThreshold(1);


        //Settings default name and address
        String address = BaseConfig.GetValues("select Address as ret_values from Mstr_MultipleHospital where ServerId='" + BaseConfig.HID + '\'');



        if(this.autocompletePrefpharmacy.getText().length()==0)
        {
            this.autocompletePrefpharmacy.setText(String.valueOf(BaseConfig.HOSPITALNAME));
            if (address.length() > 0) {
                this.prefpharmacyAddress.setText(address);
            }
        }



        if(this.prefLabs.getText().length()==0)
        {
            this.prefLabs.setText(BaseConfig.HOSPITALNAME + ",LABORATORY");
            if (address.length() > 0) {
                this.autocompletePrefLabsAddress.setText(address);
            }
        }




    }

    //*******************************************************************************************
    private final void LoadBack() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);
    }

    //*******************************************************************************************
    @Override
    public final void onBackPressed() {

        this.LoadBack();

    }

    //*******************************************************************************************
    private final void LOADSETTINGSPIN() {

        boolean status = BaseConfig.LoadReportsBooleanStatus("select count(Id) as dstatus1 from drsettings");
        if (status) {
            this.checkboxCasenotesGeneralexamination.setChecked(false);
            this.checkboxCasenotesClinical.setChecked(false);
            this.checkboxCasenotesOther.setChecked(false);

        }

        String aboveSpace = "";
        SQLiteDatabase db = BaseConfig.GetDb();//);

        String Query = "select EnableAutoUpdate,CaseNotes_Systems,above_space_for_letterhead,enablepin,pin,workingdays,consultationhrs,enableappointment,noofpatients,dailyappointmentviasms,professional_charges_column,referral_report,prefpharmacy,pharmdetails,preflab,labdetails,isactive,Isupdate from drsettings;";

        Cursor c = db
                .rawQuery(
                        Query,
                        null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    this.CheckAllSystems(c.getString(c.getColumnIndex("CaseNotes_Systems")));

                    aboveSpace = c.getString(c.getColumnIndex("above_space_for_letterhead"));


                    if (c.getString(c.getColumnIndex("enableappointment")).equalsIgnoreCase("Yes")) {

                        this.switchAppointments.setOn(true);//setChecked(true);

                        String patientslot = (c.getString(c.getColumnIndex("noofpatients")));

                        this.editTextSlot.setText(patientslot);

                    } else {

                        this.switchAppointments.setOn(false);
                    }

                    if (c.getString(c.getColumnIndex("enablepin")).equalsIgnoreCase("true")) {
                        this.settingsChkbxPinnumber.setChecked(true);


                        String decryptpin = new String(Base64.decode((c.getString(c.getColumnIndex("pin"))).trim(), Base64.DEFAULT));

                        this.settingsEdtxtPinnumber.setText(decryptpin);

                    }

                    if (c.getString(c.getColumnIndex("dailyappointmentviasms")).equalsIgnoreCase("Yes")) {

                        this.switchDailyAppointmentSMS.setOn(true);
                    } else {

                        this.switchDailyAppointmentSMS.setOn(false);
                    }
                    // ///////////////////////////
                    if (c.getString(c.getColumnIndex("EnableAutoUpdate")).equalsIgnoreCase("1")) {

                        this.switchCheckupdate.setOn(true);
                    } else {

                        this.switchCheckupdate.setOn(false);
                    }

                    if (c.getString(c.getColumnIndex("referral_report")).equalsIgnoreCase("Yes")) {

                        this.switchReferralPharmacy.setOn(true);
                    } else {

                        this.switchReferralPharmacy.setOn(false);
                    }


                } while (c.moveToNext());
            }
        }
        c.close();

        Cursor cc = db.rawQuery(
                "select workingdays,morning,evening  from Consultationhrs;",
                null);
        if (cc != null) {
            if (cc.moveToFirst()) {
                do {
                    this.editTextWorkingdays.setText(cc.getString(cc.getColumnIndex("workingdays")));
                    this.textviewMrngtimeView.setText(cc.getString(cc.getColumnIndex("morning")));
                    this.edittextEvengtimeView.setText(cc.getString(cc.getColumnIndex("evening")));

                } while (cc.moveToNext());
            }
        }
        cc.close();
        db.close();

    }

    private final void CheckAllSystems(String str) {

        if (str.length() > 0) {
            String[] arr = str.substring(0, str.length() - 1).split(",");

            for (String s : arr) {

                if (SettingActivity.contains(arr, s)) {
                    switch (s) {
                        case "1":
                            this.checkboxCasenotesGeneralexamination.setChecked(true);
                            break;

                        case "2":
                            this.checkboxCasenotesCardio.setChecked(true);
                            break;

                        case "3":
                            this.checkboxCasenotesRespiratory.setChecked(true);
                            break;

                        case "4":
                            this.checkboxCasenotesGastrointestinal.setChecked(true);
                            break;

                        case "5":
                            this.checkboxCasenotesNeurology.setChecked(true);
                            break;

                        case "6":
                            this.checkboxCasenotesLocomotor.setChecked(true);
                            break;

                        case "7":
                            this.checkboxCasenotesRenal.setChecked(true);
                            break;

                        case "8":
                            this.checkboxCasenotesEndocrine.setChecked(true);
                            break;

                        case "9":
                            this.checkboxCasenotesClinical.setChecked(true);
                            break;

                        case "10":
                            this.checkboxCasenotesOther.setChecked(true);

                            break;

                        case "11":
                            this.checkboxCasenotesDental.setChecked(true);

                            break;

                        case "12":
                            this.checkboxCasenotesPostnatal.setChecked(true);
                            break;

                    }
                }
            }

        }

    }

    //*******************************************************************************************
    @RequiresApi(api = VERSION_CODES.KITKAT)
    private final void LoadpopupTime(CheckBox mon, CheckBox tue, CheckBox wed,
                                     CheckBox thu, CheckBox fri, CheckBox sat, CheckBox sun,
                                     AutoCompleteTextView acmrngfrm, AutoCompleteTextView acmrngto,
                                     AutoCompleteTextView acmrngevng, AutoCompleteTextView acevngto) {
        try {
            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(
                    "select workingdays,morning,evening  from Consultationhrs;",
                    null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String[] wkdays = c.getString(
                                c.getColumnIndex("workingdays")).split(",");
                        for (String wkday : wkdays) {
                            if (wkday.equals("Monday")) {
                                mon.setChecked(true);
                            }
                            if (wkday.equals("Tuesday")) {
                                tue.setChecked(true);
                            }
                            if (wkday.equals("Wednesday")) {
                                wed.setChecked(true);
                            }
                            if (wkday.equals("Thursday")) {
                                thu.setChecked(true);
                            }
                            if (wkday.equals("Friday")) {
                                fri.setChecked(true);
                            }
                            if (wkday.equals("Saturday")) {
                                sat.setChecked(true);
                            }
                            if (wkday.equals("Sunday")) {
                                sun.setChecked(true);
                            }
                        }

                        if (!Objects.equals(c.getString(c.getColumnIndex("morning")), "")) {

                            String[] mrng = this.textviewMrngtimeView.getText().toString().split("-");
                            if (mrng.length > 0) {

                                String[] mrngfrmtime = mrng[0].split(" ");
                                if (mrngfrmtime.length == 2) {

                                    String[] mrngtotime = mrng[1].split(" ");

                                    acmrngfrm.setText(mrngfrmtime[1]);
                                    acmrngto.setText(mrngtotime[1]);
                                }
                            }
                        }
                        if (!Objects.equals(c.getString(c.getColumnIndex("evening")), "")) {

                            String[] evng = this.edittextEvengtimeView.getText().toString().split("-");

                            if (evng.length > 0) {
                                String[] evngfrmtime = evng[0].split(" ");
                                if (evngfrmtime.length == 2) {
                                    String[] evngtotime = evng[1].split(" ");
                                    acmrngevng.setText(evngfrmtime[1]);
                                    acevngto.setText(evngtotime[1]);
                                }
                            }
                        }

                    } while (c.moveToNext());
                }
            }
            c.close();
            db.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final void clear() {

        this.autocompletePrefpharmacy.setText(null);
        this.prefpharmacyAddress.setText(null);
        this.prefLabs.setText(null);
        this.autocompletePrefLabsAddress.setText(null);

    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final void SelectedPharmacyAddress() {
        SQLiteDatabase db = BaseConfig.GetDb();//);

        //String[] pna = prefpharmacy.getText().toString().split(",");

        Cursor c = db.rawQuery(
                "select distinct pharmaddr,contactnum from Pharmacy where pharmacyname='"
                        + this.autocompletePrefpharmacy.getText() + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    this.prefpharmacyAddress.setText(c.getString(c.getColumnIndex("pharmaddr")) + " - " + c.getString(c.getColumnIndex("contactnum")));


                } while (c.moveToNext());
            }
        }

        c.close();
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final void SelectedLaboratoryAddress() {
        SQLiteDatabase db = BaseConfig.GetDb();//);

        //String[] pna = pref_labs.getText().toString().split(",");

        Cursor c = db.rawQuery(
                "select distinct labaddr,contactnum from Laboratory where labname='"
                        + this.prefLabs.getText() + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    this.autocompletePrefLabsAddress.setText(c.getString(c
                            .getColumnIndex("labaddr"))
                            + " - "
                            + c.getString(c.getColumnIndex("contactnum")));

                } while (c.moveToNext());
            }
        }

        c.close();
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // methods autocomplete pharmacy/labs
    // relationship
    public final void LoadPharmacy() {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select distinct pharmacyname from Pharmacy order by pharmacyname;",
                null);

        List<String> list = new ArrayList<>();
        list.add("Select Pharmacy");

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String counrtyname = c.getString(c
                            .getColumnIndex("pharmacyname"));
                    list.add(counrtyname);

                } while (c.moveToNext());
            }
        }

//        BaseConfig.spinner2meth(this, list, prefpharmacy);
        BaseConfig.loadSpinner(this.autocompletePrefpharmacy, list);

        db.close();
        c.close();
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final void ShowSweetAlert(String str) {

        new CustomKDMCDialog(this)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_success_done)
                .setTitle(getString(string.information))
                .setDescription(str)
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle(getString(string.ok))
                .setOnPossitiveListener(() -> {
                    finish();
                    Intent intent = new Intent(this.getApplicationContext(), Dashboard_NavigationMenu.class);
                    this.startActivity(intent);

                });

    }

    /////////////////////////////////////////////////////////////////////////////////
    private final void Savelocal() {

        try {
            if (this.checkValidation())

                this.submitForm();
            else
                Toast.makeText(this, string.check_missing, Toast.LENGTH_LONG).show();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void submitForm() {


        SQLiteDatabase db = BaseConfig.GetDb();//);

        try {
            SimpleDateFormat dateformt = new SimpleDateFormat(
                    "dd/MM/yyyy HH:MM:SS.SSS",Locale.ENGLISH);
            Date date = new Date();
            dateformt.format(date);
            String dttm = dateformt.format(date);

            //String Count = null;
            String smsp = null;

            // ///////////////////////////
            String enablepin = "false";


            if (this.settingsChkbxPinnumber.isChecked()) {
                enablepin = "true";

            }
            // ///////////////////////////


            String Del_Query = "delete from drsettings;";
            // //Log.e("Log test",Insert_Query);
            BaseConfig.SaveData(Del_Query);
            String Del_Query2 = "delete from Consultationhrs;";
            // //Log.e("Log test",Insert_Query);
            BaseConfig.SaveData(Del_Query2);


            String EncPin = (Base64.encodeToString(this.settingsEdtxtPinnumber.getText().toString().getBytes(), Base64.DEFAULT));
            String enableappointmenttxt="";
            String noofpatientcount="";


            if (this.switchAppointments.isOn()) {

                enableappointmenttxt = "Yes";
                this.switchAppointments.setVisibility(View.VISIBLE);
                noofpatientcount = this.editTextSlot.getText().toString();

            } else {

                enableappointmenttxt = "No";
                this.switchAppointments.setVisibility(View.GONE);
                noofpatientcount = "";
            }

            int str_enableautoupdate = 0;
            if (this.switchCheckupdate.isOn()) {
                str_enableautoupdate = 1;
            }



            String str_casenotes_generalinfo = this.enableCaseNoteCheckBox(this.checkboxCasenotesGeneralexamination);//1 default
            String str_casenotes_cardio = this.enableCaseNoteCheckBox(this.checkboxCasenotesCardio);//2
            String str_casenotes_respiratory = this.enableCaseNoteCheckBox(this.checkboxCasenotesRespiratory);//3
            String str_casenotes_gastrointestinal = this.enableCaseNoteCheckBox(this.checkboxCasenotesGastrointestinal);//4
            String str_casenotes_neurology = this.enableCaseNoteCheckBox(this.checkboxCasenotesNeurology);//5
            String str_casenotes_locomotor = this.enableCaseNoteCheckBox(this.checkboxCasenotesLocomotor);//6
            String str_casenotes_renal = this.enableCaseNoteCheckBox(this.checkboxCasenotesRenal);//7
            String str_casenotes_endocrine = this.enableCaseNoteCheckBox(this.checkboxCasenotesEndocrine);//8
            String str_casenotes_clinical = this.enableCaseNoteCheckBox(this.checkboxCasenotesClinical);//9 default
            String str_casenotes_other = this.enableCaseNoteCheckBox(this.checkboxCasenotesOther);//10 default
            String str_casenotes_dental = this.enableCaseNoteCheckBox(this.checkboxCasenotesDental);//11
            String str_casenotes_postnatal = this.enableCaseNoteCheckBox(this.checkboxCasenotesPostnatal);//12


            String CaseNoteSystems = str_casenotes_generalinfo + str_casenotes_cardio + str_casenotes_respiratory + str_casenotes_gastrointestinal + str_casenotes_neurology + str_casenotes_locomotor + str_casenotes_renal + str_casenotes_endocrine + str_casenotes_clinical + str_casenotes_dental + str_casenotes_postnatal + str_casenotes_other;


            ContentValues settings_values = new ContentValues();
            settings_values.put("EnableAutoUpdate", Integer.valueOf(str_enableautoupdate));
            settings_values.put("CaseNotes_Systems", CaseNoteSystems);
            settings_values.put("docid", BaseConfig.doctorId);
            settings_values.put("drregno", BaseConfig.doctorRegId);
            settings_values.put("drname", BaseConfig.doctorname);
            settings_values.put("dremail", BaseConfig.docemail);
            settings_values.put("dt", dttm);
            settings_values.put("enablepin", enablepin);
            settings_values.put("pin", EncPin);
            settings_values.put("workingdays", this.editTextWorkingdays.getText().toString());
            settings_values.put("consultationhrs", (this.textviewMrngtimeView.getText().toString()) + '\n' + (this.edittextEvengtimeView.getText()));
            settings_values.put("enableappointment", enableappointmenttxt);
            settings_values.put("noofpatients", noofpatientcount);
            settings_values.put("dailyappointmentviasms", BaseConfig.GetWidgetOperations(this.switchDailyAppointmentSMS,1));
            settings_values.put("professional_charges_column", "");
            settings_values.put("referral_report", BaseConfig.GetWidgetOperations(this.switchReferralPharmacy,1));
            settings_values.put("prefpharmacy", this.autocompletePrefpharmacy.getText().toString());
            settings_values.put("pharmdetails", this.prefpharmacyAddress.getText().toString());
            settings_values.put("preflab", this.prefLabs.getText().toString());
            settings_values.put("labdetails", this.autocompletePrefLabsAddress.getText().toString());
            settings_values.put("isactive", Integer.valueOf(1));
            settings_values.put("Isupdate", Integer.valueOf(0));
            settings_values.put("above_space_for_letterhead", "5");
            settings_values.put("HID", BaseConfig.HID);

            db.insert(BaseConfig.TABLE_SETTINGS, null, settings_values);


            String Insert_Query1 = "Insert into Consultationhrs(workingdays,morning,evening,isactive)"
                    + " Values('"
                    + this.editTextWorkingdays.getText()
                    + "','"
                    + this.textviewMrngtimeView.getText()
                    + "','"
                    + this.edittextEvengtimeView.getText()
                    + "','0');";
            BaseConfig.SaveData(Insert_Query1);


            this.ShowSweetAlert(this.getString(string.all_settings));

            db.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final String enableCaseNoteCheckBox(CheckBox checkBox) {

        if (checkBox.isChecked()) {

            String str = checkBox.getText().toString();
            String ret = "";


            if (str.equalsIgnoreCase(this.STR_GENERALEXAMINATION)) {
                ret = "1,";


            } else if (str.equalsIgnoreCase(this.STR_CARDIOSYSTEM)) {
                ret = "2,";

            } else if (str.equalsIgnoreCase(this.STR_RESPIRATORYSYSTEM)) {
                ret = "3,";

            } else if (str.equalsIgnoreCase(this.STR_GASTROSYSTEM)) {
                ret = "4,";

            } else if (str.equalsIgnoreCase(this.STR_NEUROLOGY)) {
                ret = "5,";

            } else if (str.equalsIgnoreCase(this.STR_LOCOMOTORSYSTEM)) {
                ret = "6,";

            } else if (str.equalsIgnoreCase(this.STR_RENLSYSTEM)) {
                ret = "7,";

            } else if (str.equalsIgnoreCase(this.STR_ENDOCRINESYSTEM)) {
                ret = "8,";

            } else if (str.equalsIgnoreCase(this.STR_CLINICAL)) {
                ret = "9,";

            } else if (str.equalsIgnoreCase(this.STR_OTHERS)) {
                ret = "10,";

            } else if (str.equalsIgnoreCase(this.STR_DENTAL)) {
                ret = "11,";

            } else if (str.equalsIgnoreCase(this.STR_PNC)) {
                ret = "12,";

            }

            return ret;
        }

        return "";
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean checkValidation() {
        boolean ret = true;

        if (this.settingsChkbxPinnumber.isChecked()) {
            if (!Validation1.isPinNumber(this.settingsEdtxtPinnumber, true)) ret = false;
        } else if (!this.settingsChkbxPinnumber.isChecked()) {
            this.settingsEdtxtPinnumber.setError(null);
        }


        return ret;
    }

    //*******************************************************************************************
    // methods autocomplete pharmacy/labs
    // relationship
    public final void LoadLaboratory() {

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery("select distinct labname from Laboratory order by labname;", null);

        List<String> list = new ArrayList<>();
        list.add("Select Laboratory");

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String counrtyname = c.getString(c
                            .getColumnIndex("labname"));
                    list.add(counrtyname);

                } while (c.moveToNext());
            }
        }

        //BaseConfig.spinner2meth(this, list, pref_labs);
        BaseConfig.loadSpinner(this.prefLabs, list);

        db.close();
        c.close();
    }

    /**
     * Muthukumar N
     */

    private void CHECK_APP_UPDATE() {

        this.appVerUpdater = new AppVerUpdater()
                .setUpdateJSONUrl(BaseConfig.AppUpdateJson)
                .setShowNotUpdated(true)
                .setViewNotes(true)
                .setCallback(new Callback() {
                    @Override
                    public void onFailure(UpdateErrors error) {

                        if (error == UpdateErrors.NETWORK_NOT_AVAILABLE) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"No internet connection.", SettingActivity.this.setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.ERROR_CHECKING_UPDATES) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"An error occurred while checking for updates.", SettingActivity.this.setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.ERROR_DOWNLOADING_UPDATES) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"An error occurred when downloading updates.", SettingActivity.this.setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.JSON_FILE_IS_MISSING) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"File missing", SettingActivity.this.setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.FILE_JSON_NO_DATA) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"The file containing information about the updates are empty.", SettingActivity.this.setttingsParentLayout, 2);
                        }
                    }

                    @Override
                    public void onDownloadSuccess() {
                        // for example, record/reset license
                        SettingActivity.this.UnInstallApplication(SettingActivity.this.getPackageName());
                    }

                    @Override
                    public void onUpdateChecked(boolean downloading) {
                        // Happens after an update check, immediately after if update check was successful and there
                        // were no dialogs, or, when an update dialog is presented and user explicitly dismissed the dialog.
                        // "downloading" is true if user accepted the update
                        // Typically used for resetting next update check time
                    }
                })
                .setAlertDialogCancelable(true)
                .build(this);

    }


    //**********************************************************************************************

    //Uninstall Apk
    private void UnInstallApplication(String packageName) {

        try {
            Uri packageURI = Uri.parse(packageName.toString());
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            this.startActivity(uninstallIntent);

            BaseConfig.SnackBar(this, "Updated version of apk is available in download folder..", this.setttingsParentLayout, 1);


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    //**********************************************************************************************

    //Install Apk
    private void InstallApplication(String AppPackageName) {
        try {
            Uri packageURI = Uri.parse(AppPackageName.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    packageURI);

            intent.setDataAndType(
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                            + "/download/" + this.ApkName)),
                    "application/vnd.android.package-archive");

            this.startActivity(intent);


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public class LoginBackgroundProcesses extends AsyncTask<Void, Void, Void> {

        String docid, ServerId = "";
        String[] db_updatequery;

        protected void onPostExecute(Void v) {

            if (SettingActivity.pDialog.isShowing())
                SettingActivity.pDialog.dismiss();

            if (BaseConfig.CheckNetwork(SettingActivity.this)) {

                SettingActivity.this.CHECK_APP_UPDATE();

            } else {

                BaseConfig.SnackBar(SettingActivity.this, "Data Connection Not Available - Please Enable to check update next time..", SettingActivity.this.setttingsParentLayout, 2);

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            SettingActivity.pDialog = new ProgressDialog(SettingActivity.this);
            SettingActivity.pDialog.setTitle("Checking updates");
            SettingActivity.pDialog.setMessage("Please wait...");
            SettingActivity.pDialog.setCancelable(false);
            SettingActivity.pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {


            try {

                ImportWebservices_NODEJS.CheckDbUpdatesNodeJs(SettingActivity.this);

            } catch (Exception e) {

                e.printStackTrace();
            }


            return null;

        }

    }


}//END
