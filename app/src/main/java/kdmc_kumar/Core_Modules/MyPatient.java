package kdmc_kumar.Core_Modules;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MyPatientGetSet;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Adapters_GetterSetter.MyPatienRecylerAdapter;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.SqliteReader;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;


public class MyPatient extends AppCompatActivity implements TextWatcher {


    protected static final int RESULT_SPEECH = 1;
    private final ArrayList<String> CurrentPatientList = new ArrayList<>();
    public String Load_All_PatientsQuery = "select id,prefix,patientname as name,Patid,age,gender,PC as photo  from Patreg order by patientname, id";
    public String Load_Today_PatientsQuery = "";

    @BindView(R.id.mypatient_parent_layout)
    CoordinatorLayout mypatientParentLayout;
    @BindView(R.id.mypatient_toolbar)
    Toolbar mypatientToolbar;
    @BindView(R.id.mypatient_back)
    AppCompatImageView mypatientBack;
    @BindView(R.id.txvw_title)
    TextView txvwTitle;
    @BindView(R.id.mypatient_btn_home)
    AppCompatImageView mypatientBtnHome;
    @BindView(R.id.imgvw_list_to_grid)
    AppCompatImageView imgvwListToGrid;
    @BindView(R.id.imgvw_menu_option)
    AppCompatImageView imgvwMenuOption;
    @BindView(R.id.imgvw_exit)
    AppCompatImageView imgvwExit;
    @BindView(R.id.cardvw_search_bar)
    CardView cardvwSearchBar;
    @BindView(R.id.edittext_search)
    EditText etSearch;
    @BindView(R.id.button_clear)
    ImageButton btClear;
    @BindView(R.id.button_refresh)
    ImageButton btRefresh;
    @BindView(R.id.textvw_patient_count)
    TextView txtCount;
    @BindView(R.id.imagevw_no_data_available)
    AppCompatImageView imgNoMedia;
    @BindView(R.id.mypatient_fastscrollview)
    FastScrollRecyclerView patient_list;
    ArrayList<HashMap<String, String>> titles_list;
    Handler handler;
    BaseConfig bcnfg = new BaseConfig();
    @BindView(R.id.checkbox_view_all_patients)
    CheckBox ShowAllPatients;
    //**********************************************************************************************
    Dialog builderDialog;
    private SimpleDateFormat dateformt;
    private Date date;
    private String dttm;
    private MyPatienRecylerAdapter recylerAdapter;
    private GridLayoutManager lLayout;
    private Handler timerHandler;
    //**********************************************************************************************
    private Runnable timerRunnable;

    private LinkedList<MyPatientGetSet> rowItems;

    private static final boolean LoadReportsBooleanStatus(String Query) {
        try {
            int havcount = 0;


            SQLiteDatabase db = BaseConfig.GetDb();//context);

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        c.getString(c.getColumnIndex("dstatus1"));

                        havcount++;

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return havcount > 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_my_patients);


        try {

            GETINITIALIZE();

            CONTROLLISTNERS();

            AUTOREFRESHPATIENTLIST();

            new LoadPatientInfo(2, 0).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CONTROLLISTNERS() {

        ShowAllPatients.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // String Query = "select count(*)as ret_values  from Patreg order by patientname, id ";
                //int PresentCount = Integer.valueOf(BaseConfig.GetValues(Query));

                if (isChecked) {

                    //  if (PresentCount > rowItems.size())
                    {
                        new LoadPatientInfo(1, 1).execute();
                    }

                } else {

                    new LoadPatientInfo(2, 0).execute();

                }

                if (etSearch.getText().length() > 0) {
                    etSearch.setText("");
                }


            }
        });


        mypatientBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadBack();
            }
        });

        imgvwExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                BaseConfig.ExitSweetDialog(MyPatient.this, null);


            }
        });

        imgvwMenuOption.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        getString(R.string.today_appointment),
                        getString(R.string.search_patient_online),
                        getString(R.string.admit_patient_request),
                        getString(R.string.close)
                };

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MyPatient.this);
                builder.setTitle(getString(R.string.options));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].toString().equalsIgnoreCase(getString(R.string.today_appointment))) {

                            ShowMyAppointment();


                        } else if (items[item].toString().equalsIgnoreCase(getString(R.string.upcoming_patient))) {

                            UpcomingPatients();


                        } else if (items[item].toString().equalsIgnoreCase(getString(R.string.search_patient_online))) {

                            SearchPatientOnline(dialog);


                        } else if (items[item].toString().equalsIgnoreCase(getString(R.string.admit_patient_request))) {

                            ShowRequestDialog();

                        }


                    }
                });
                android.support.v7.app.AlertDialog alert = builder.create();
                Dialog dialog = alert;
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.show();
            }
        });


        btClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etSearch.getText().toString().length() > 0) {

                    etSearch.setText("");
                    recylerAdapter = new MyPatienRecylerAdapter(rowItems);
                    patient_list.setAdapter(recylerAdapter);

                }

            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateTextRecylerView();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateTextRecylerView();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateTextRecylerView();
            }
        });


        imgvwListToGrid.setOnClickListener(v -> {
            if (BaseConfig.listViewType.equals(ListViewType.ListView)) {

                BaseConfig.listViewType = ListViewType.GrideView;
                imgvwListToGrid.setImageResource(R.drawable.list);

                patient_list.setLayoutManager(new GridLayoutManager(MyPatient.this, 2));
                patient_list.setHasFixedSize(true);
                patient_list.setNestedScrollingEnabled(false);


                recylerAdapter = new MyPatienRecylerAdapter(rowItems);
                patient_list.setItemAnimator(new DefaultItemAnimator());
                patient_list.setAdapter(recylerAdapter);

                String ListCount = String.valueOf(rowItems.size());
                txtCount.setText(getString(R.string.no_of_pat) + ": " + ListCount);

                int visibility = Integer.parseInt(ListCount) == 0 ? View.VISIBLE : View.GONE;
                imgNoMedia.setVisibility(visibility);


            } else {

                BaseConfig.listViewType = ListViewType.ListView;
                imgvwListToGrid.setImageResource(R.drawable.grid);

                patient_list.setLayoutManager(new LinearLayoutManager(this));
                patient_list.setHasFixedSize(true);
                patient_list.setNestedScrollingEnabled(false);

                recylerAdapter = new MyPatienRecylerAdapter(rowItems);
                patient_list.setItemAnimator(new DefaultItemAnimator());
                patient_list.setAdapter(recylerAdapter);

                String ListCount = String.valueOf(rowItems.size());
                txtCount.setText(getString(R.string.no_of_pat) + ": " + ListCount);

                int visibility = Integer.parseInt(ListCount) == 0 ? View.VISIBLE : View.GONE;
                imgNoMedia.setVisibility(visibility);


            }

        });


        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        btRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckPatientsOnline();

                v.startAnimation(animRotate);

                etSearch.setText("");

                try {

                    new LoadPatientInfo(1, 1).execute();

                    Toast.makeText(MyPatient.this, "Please wait refreshing patient list...", Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    public void SearchPatientOnline(DialogInterface dialog) {
        //Search Online Patient Popup
        dialog.dismiss();
        final Dialog alertdialog = new Dialog(MyPatient.this);


        if (BaseConfig.CheckNetwork(MyPatient.this)) {
            MyPatient.this.finish();
            Intent OnlinePatient = new Intent(MyPatient.this, SearchOnlinePatient.class);
            startActivity(OnlinePatient);
        } else {
            Toast.makeText(MyPatient.this, "Data Connection Not Available - Please Enable to search patients online...", Toast.LENGTH_LONG).show();
        }
    }

    public void Refresh() {
        txvwTitle.setText(getString(R.string.mypatient));
        new LoadPatientInfo(1, 1).execute();
    }

    public void UpcomingPatients() {
        txvwTitle.setText(R.string.title_mypatient_appointment);
        new LoadPatientInfo(1, 3).execute();
    }

    //**********************************************************************************************

    public void ShowMyAppointment() {
        try {
            txvwTitle.setText("My Patients - Today Appointment List");
            new LoadPatientInfo(1, 2).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //**********************************************************************************************

    private void updateTextRecylerView() {

        if (etSearch.getText().toString().isEmpty() || etSearch.getText().toString().equalsIgnoreCase("")) {

            recylerAdapter = new MyPatienRecylerAdapter(rowItems);
            patient_list.setItemAnimator(new DefaultItemAnimator());
            patient_list.setAdapter(recylerAdapter);

            String ListCount = String.valueOf(recylerAdapter.getItemCount());
            txtCount.setText(getString(R.string.no_of_pat) + ": " + ListCount);

            int visibility = Integer.parseInt(ListCount) == 0 ? View.VISIBLE : View.GONE;
            imgNoMedia.setVisibility(visibility);


        } else {

            recylerAdapter = new MyPatienRecylerAdapter(filterValue(rowItems, etSearch.getText().toString()));
            patient_list.setItemAnimator(new DefaultItemAnimator());
            patient_list.setAdapter(recylerAdapter);

            String ListCount = String.valueOf(recylerAdapter.getItemCount());
            txtCount.setText(getString(R.string.no_of_pat) + ": " + ListCount);

            int visibility = Integer.parseInt(ListCount) == 0 ? View.VISIBLE : View.GONE;
            imgNoMedia.setVisibility(visibility);


        }


    }

    //**********************************************************************************************

    private void GETINITIALIZE() {

        try {
            ButterKnife.bind(MyPatient.this);
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

            BaseConfig.welcometoast = 0;

            dateformt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            date = new Date();
            dttm = dateformt.format(date);

            rowItems = new LinkedList<>();

            if (BaseConfig.HOSPITALNAME.toString().length() > 0) {
                txvwTitle.setText(String.format("%s  -  %s", BaseConfig.HOSPITALNAME, getString(R.string.patients)));
            } else {
                txvwTitle.setText(getString(R.string.my_patients));
            }


            setSupportActionBar(mypatientToolbar);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ViewCompat.setTransitionName(mypatientToolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
            }

            mypatientToolbar.setBackgroundColor(Color.parseColor(MyPatient.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));


            lLayout = new GridLayoutManager(MyPatient.this, 2);
            patient_list.setHasFixedSize(true);
            patient_list.setLayoutManager(lLayout);
            patient_list.setNestedScrollingEnabled(false);


            if (BaseConfig.listViewType.equals(ListViewType.ListView)) {
                patient_list.setLayoutManager(new LinearLayoutManager(this));
            } else {
                patient_list.setLayoutManager(new GridLayoutManager(MyPatient.this, 2));
            }


            assert patient_list != null;

            imgNoMedia.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //**********************************************************************************************

    private void AUTOREFRESHPATIENTLIST() {


        timerHandler = new Handler();

        timerRunnable = new Runnable() {
            @Override
            public void run() {


                //String Query = "select count(*)as ret_values  from Patreg order by patientname, id ";
                //int PresentCount = Integer.valueOf(BaseConfig.GetValues(Query));

                // if (PresentCount > rowItems.size()) {
                if (ShowAllPatients.isChecked()) {
                    new LoadPatientInfo(1, 1).execute();//ALL PATIENTS
                } else {
                    new LoadPatientInfo(2, 0).execute();//ONL TODAY PATIENT
                }
                //  }

                timerHandler.postDelayed(this, 180000); //run every second

            }
        };

        timerHandler.postDelayed(timerRunnable, 180000); //Start timer after 1 sec

    }

    private void LoadBack() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

    }

    //**********************************************************************************************

    public boolean checkPatientIsExist(String patientid) {


        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select * from  Patreg where Patid='" + patientid + "'", null);
        rowItems.clear();
        CurrentPatientList.clear();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    return true;
                } while (c.moveToNext());
            }
        }
        c.close();
        return false;
    }

    private void ShowRequestDialog() {


        if (BaseConfig.doctorId == null || BaseConfig.doctorId.equalsIgnoreCase("")) {
            //     BaseConfig.LoadDoctorValues();
        }


        View view = getLayoutInflater().inflate(R.layout.inpatient_request_layout, null);
        final AutoCompleteTextView patientAuto = view.findViewById(R.id.patient_detail_values);
        patientAuto.setThreshold(1);
        LoadPatentIDnameForAuto(patientAuto);
        final EditText remarksEdt = view.findViewById(R.id.request_remarks_edt);
        Button submitBtn = view.findViewById(R.id.submit);
        Button cancelBtn = view.findViewById(R.id.cancel);
        Spinner WardCategory = view.findViewById(R.id.ward_category);

        TextView Title_PatientName, Title_WarCategory, Title_Remarks;
        Title_PatientName = view.findViewById(R.id.txtvw_patname);
        Title_WarCategory = view.findViewById(R.id.txtvw_wardcategory);
        Title_Remarks = view.findViewById(R.id.txtvw_remarks);

        String first2 = "Patient Name";
        String next2 = "<font color='#EE0000'><b>*</b></font>";
        Title_PatientName.setText(Html.fromHtml(first2 + next2));

        String first3 = "Choose ward category";
        String next3 = "<font color='#EE0000'><b>*</b></font>";
        Title_WarCategory.setText(Html.fromHtml(first3 + next3));

        String first4 = "Request Remarks";
        String next4 = "<font color='#EE0000'><b>*</b></font>";
        Title_Remarks.setText(Html.fromHtml(first4 + next4));

        BaseConfig.LoadValuesSpinner(WardCategory, MyPatient.this, "select distinct WardCatName as dvalue from Mstr_WardCategory where HID='" + BaseConfig.HID + "' order by ServerId;", "select ward category");

        patientAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (patientAuto.getText().length() > 0) {
                    String patId = patientAuto.getText().toString().split("-")[1].trim();
                    boolean check = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from inpatient_request where patid='" + patId + "'");
                    if (check) {
                        Toast.makeText(MyPatient.this, "Inpatient request already send for this patient..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validation1.hasText(patientAuto)) {
                    if (WardCategory.getSelectedItemPosition() > 0) {
                        if (patientAuto.getText().toString().contains("-")) {
                            if (Validation1.hasText(remarksEdt)) {

                                String patId = patientAuto.getText().toString().split("-")[1].trim();
                                String selectedWard = WardCategory.getSelectedItem().toString();


                                //Check for valid patient id
                                boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + patId + "'");
                                if (chk) {

                                    ContentValues values = new ContentValues();
                                    values.put("patid", patId.trim());
                                    values.put("request_remarks", remarksEdt.getText().toString());
                                    values.put("WardCategory", selectedWard);
                                    values.put("WardCategoryId", BaseConfig.GetValues("select ServerId as ret_values from Mstr_WardCategory where WardCatName='" + selectedWard + "' and HID='" + BaseConfig.HID + "'"));
                                    values.put("IsActive", "1");
                                    values.put("IsUpdate", "0");
                                    values.put("docid", BaseConfig.doctorId);
                                    values.put("ActDate", BaseConfig.DeviceDate());

                                    SQLiteDatabase Db = BaseConfig.GetDb();//);
                                    Db.insert("inpatient_request", null, values);
                                    dialog.dismiss();

                                    ShowSuccessFullyPlacedRequest();

                                } else {
                                    Toast.makeText(MyPatient.this, "Selected Patient Id/Name is not valid!", Toast.LENGTH_LONG).show();
                                }


                            } else {
                                Toast.makeText(MyPatient.this, "Enter remarks", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(MyPatient.this, R.string.valid_patient_details, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MyPatient.this, R.string.choose_ward, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MyPatient.this, R.string.pl_select_patient, Toast.LENGTH_LONG).show();
                }


            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    private void ShowSuccessFullyPlacedRequest() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.title_inpatient));
        alertBuilder.setMessage(R.string.request_placed);
        alertBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertBuilder.show();
    }

    private void LoadPatentIDnameForAuto(AutoCompleteTextView autoSpName) {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select *  from Patreg  where enable_inpatient != '1' order by name;", null);

        List<String> list = new ArrayList<String>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    String pname = c.getString(c.getColumnIndex("name"));
                    String str2 = c.getString(c.getColumnIndex("Patid"));
                    list.add(pname + " - " + str2);


                } while (c.moveToNext());
            }
        }

        BaseConfig.loadSpinner(autoSpName, list);

        db.close();
        c.close();


    }

    private String LoadLabReport(String PATID) {

        String Query0 = "select Id as dstatus1  from Medicaltest where Ptid='" + PATID + "' and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";

        boolean q = LoadReportsBooleanStatus(Query0);

        if (q) {

            String Query1 = "select IsResultAvailable as ret_values from Medicaltest where Ptid='" + PATID + "' and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";
            String IsResultAvailable = BaseConfig.GetValues(Query1);

             if (IsResultAvailable.equalsIgnoreCase("1")) {
                return "1";
            } else if (IsResultAvailable.equalsIgnoreCase("2")) {
                return "2";
            } else {
                return "0";
            }

        } else {
            return "0";
        }

    }

    private List<CommonDataObjects.MyPatientGetSet> filterValue(List<CommonDataObjects.MyPatientGetSet> list, String startwith) {
        List<CommonDataObjects.MyPatientGetSet> items = new ArrayList<>();

        if (!startwith.trim().isEmpty()) {

            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).getPatient_Name().toLowerCase().startsWith(startwith.trim().toLowerCase())) {
                    items.add(list.get(i));
                }
            }
        } else {
            items = items;
        }

        return items;

    }

    private boolean IsOnlinePatient(String patId) {
        for (int i = 0; i <= CurrentPatientList.size() - 1; i++) {
            if (CurrentPatientList.get(i).equalsIgnoreCase(patId)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> CheckPatientsOnline() {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from current_patient_list where docid = '" + BaseConfig.doctorId + "' or (status  = 'true' and HID='" + BaseConfig.HID + "')", null);
            rowItems.clear();
            CurrentPatientList.clear();

            if (c != null) {
                if (c.moveToFirst()) {
                    do

                    {

                        SimpleDateFormat sdf;
                        Date strDate = null;
                        Calendar c1 = null;

                        String date = c.getString(c.getColumnIndex("date"));
                        ////Log.e("Online patient date 1 : ", date.toString());
                        if (date.contains("T")) {

                            date = date.split("T")[0];
                            //"2018-01-19"
                            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            strDate = sdf.parse(date);
                            c1 = Calendar.getInstance(); // today
                        } else {
                            date = date;
                            ////Log.e("Online patient date: ", date.toString());
                            //"2018-01-19"
                            if (date.contains("-")) {
                                if (date.split("-")[0].length() == 4) {
                                    sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                    strDate = sdf.parse(date);
                                    c1 = Calendar.getInstance(); // today
                                } else if (date.split("-")[0].length() == 2) {
                                    sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
                                    strDate = sdf.parse(date);
                                    c1 = Calendar.getInstance(); // today
                                }

                            } else if (date.contains("/")) {

                                sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                                strDate = sdf.parse(date);
                                c1 = Calendar.getInstance(); // today

                            }


                        }


                        Calendar c2 = Calendar.getInstance();


                        c2.setTime(strDate); // your date
                        String closed = c.getString(c.getColumnIndex("closed"));
                        boolean IsCorrectDate = false;
                        int c1Month = c1.get(Calendar.MONTH);
                        int c1Year = c1.get(Calendar.YEAR);
                        int c1Day = c1.get(Calendar.DAY_OF_MONTH);

                        int c2Month = c2.get(Calendar.MONTH);
                        int c2Year = c2.get(Calendar.YEAR);
                        int c2Day = c2.get(Calendar.DAY_OF_MONTH);
                        Date TodayDate = c1.getTime();


                        if (c1Day == c2Day && c1Month == c2Month && c1Year == c2Year) {
                            IsCorrectDate = true;
                        }

                        if (IsCorrectDate && closed.equalsIgnoreCase("0")) {

                            CurrentPatientList.add(c.getString(c.getColumnIndex("patid")));

                        }


                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return CurrentPatientList;
    }

    @Override
    public void onBackPressed() {

        LoadBack();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

    @Override
    public void afterTextChanged(Editable arg0) {

    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

    }

    /**
     * PATIENT LOADIING NEW METHOD
     */

    public void LOAD_ALL_PATIENTS(int PASSING_LOAD_FILTER) {

        try {

            String LOAD_ALL_PATIENTS_QUERY = Load_All_PatientsQuery;// = Load_All_PatientsQuery;

            String pdtls, paggen;
            Bitmap bm;

            rowItems.clear();
            rowItems = new LinkedList<>();

            if (PASSING_LOAD_FILTER == 1)// LOAD ALL PATIENT
            {
                LOAD_ALL_PATIENTS_QUERY = Load_All_PatientsQuery;
            } else if (PASSING_LOAD_FILTER == 2)// LOAD APPOINTMENT
            {
                String Query = "select Patreg.Patid,Patreg.id,Patreg.name,Patreg.age as  age,Patreg.gender  as gender,Patreg.age||Patreg.gender as agegen,' ' as token, Patreg.PC as photo,Patreg.phone as mobnum  from Appoinment INNER JOIN Patreg ON  Patreg.Patid=Appoinment.patid and Appoinment.Appoimentdt='" + dttm + "' and Appoinment.Iscancel='False';";
                Load_All_PatientsQuery = Query;
            } else if (PASSING_LOAD_FILTER == 3) {// LOAD UPCOMING

                SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date date = new Date();
                String dttm = dateformt.format(date);
                String Query = "select distinct Patid,name,age,gender,PC from patreg where substr(Actdate,0,11)='" + dttm + "'";
                Load_All_PatientsQuery = Query;
            }

            SqliteReader.getInstance().setQuery(LOAD_ALL_PATIENTS_QUERY).onDataReceiver(new SqliteReader.Data() {
                @Override
                public void onData(Cursor c) {


                    String PREFIX = c.getString(c.getColumnIndex("prefix"));
                    String NAME = c.getString(c.getColumnIndex("name"));
                    String PATID = c.getString(c.getColumnIndex("Patid"));
                    String AGE = c.getString(c.getColumnIndex("age"));
                    String GENDER = c.getString(c.getColumnIndex("gender"));
                    String PHOTO = c.getString(c.getColumnIndex("photo"));

                    String IsLabReport = LoadLabReport(PATID);

                    MyPatientGetSet item = new MyPatientGetSet(PREFIX, NAME, PATID, AGE, GENDER, PHOTO);

                    item.IsOnlinePatient = false;
                    item.IsLabReport = IsLabReport;
                    rowItems.add(item);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //**********************************************************************************************

    public void LOAD_TODAY_PATIENTS() {

        try {
            String LOAD_ALL_PATIENTS_QUERY = Load_All_PatientsQuery;
            CheckPatientsOnline();
            String pdtls, paggen;
            Bitmap bm;

            rowItems.clear();
            rowItems = new LinkedList<>();
            SqliteReader.getInstance().setQuery(LOAD_ALL_PATIENTS_QUERY).onDataReceiver(new SqliteReader.Data() {
                @Override
                public void onData(Cursor c) {

                    String PREFIX = c.getString(c.getColumnIndex("prefix"));
                    String NAME = c.getString(c.getColumnIndex("name"));
                    String PATID = c.getString(c.getColumnIndex("Patid"));
                    String AGE = c.getString(c.getColumnIndex("age"));
                    String GENDER = c.getString(c.getColumnIndex("gender"));
                    String PHOTO = c.getString(c.getColumnIndex("photo"));


                    String IsLabReport = LoadLabReport(PATID);

                    MyPatientGetSet item = new MyPatientGetSet(PREFIX, NAME, PATID, AGE, GENDER, PHOTO);

                    if (IsOnlinePatient(PATID)) {

                        rowItems.addFirst(item);
                        item.IsOnlinePatient = true;
                        item.IsLabReport = IsLabReport;

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class LoadPatientInfo extends AsyncTask<Void, Void, Void> {

        int PASSING_ID = 1;
        int PASSING_LOAD_FILTER = 0;

        LoadPatientInfo(int Id, int FilterId) {
            this.PASSING_ID = Id;
            this.PASSING_LOAD_FILTER = FilterId;
        }

        @Override
        protected void onPreExecute() {
            builderDialog = BaseConfig.showCustomDialog(getString(R.string.please_wait), "Loading My Patients..", MyPatient.this);
            builderDialog.setCancelable(false);
            builderDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (PASSING_ID == 1)//Get All Paitents
            {
                LOAD_ALL_PATIENTS(PASSING_LOAD_FILTER);
            } else//Get only selected patients
            {
                LOAD_TODAY_PATIENTS();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (builderDialog.isShowing() && builderDialog != null) {
                builderDialog.dismiss();
            }

            recylerAdapter = new MyPatienRecylerAdapter(rowItems);
            patient_list.setItemAnimator(new DefaultItemAnimator());
            patient_list.setAdapter(recylerAdapter);

            String ListCount = String.valueOf(rowItems.size());
            txtCount.setText(getString(R.string.no_of_pat) + ": " + ListCount);

            int visibility = Integer.parseInt(ListCount) == 0 ? View.VISIBLE : View.GONE;
            imgNoMedia.setVisibility(visibility);

            super.onPostExecute(aVoid);
        }
    }

    //**********************************************************************************************

    class PatientMapping extends AsyncTask<String, String, String> {

        boolean result;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MyPatient.this,
                    "Wait",
                    "Please Wait.....");

            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    result = false;
                }
            });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (result) {
                //Patient Mapped Inserted

                new CustomKDMCDialog(MyPatient.this)
                        .setLayoutColor(R.color.green_500)
                        .setImage(R.drawable.ic_success_done)
                        .setTitle("Success").setNegativeButtonVisible(View.GONE)
                        .setDescription("Patient details received successfully.")
                        .setPossitiveButtonTitle(MyPatient.this.getString(R.string.ok))
                        .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                            @Override
                            public void onPossitivePerformed() {

                                try {
                                    CheckPatientsOnline();

                                    new LoadPatientInfo(1, 1).execute();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });


            } else {

                //is not valid Patient

                new CustomKDMCDialog(MyPatient.this)
                        .setLayoutColor(R.color.red_500)
                        .setImage(R.drawable.ic_warning_black_24dp)
                        .setTitle("Error").setNegativeButtonVisible(View.GONE)
                        .setDescription("Invalid Patient Id Please Try again....")
                        .setPossitiveButtonTitle(MyPatient.this.getString(R.string.ok))
                        .setOnPossitiveListener(new CustomKDMCDialog.possitiveOnClick() {
                            @Override
                            public void onPossitivePerformed() {

                                try {
                                    CheckPatientsOnline();

                                    new LoadPatientInfo(1, 1).execute();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });


            }
        }

        @Override
        protected String doInBackground(String... params) {

            return null;
        }
    }


}// END
