package kdmc_kumar.Inpatient_Module;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Adapters_GetterSetter.InpatientRecyclerAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;

public class Inpatient_List extends AppCompatActivity {


    private static final int LoadBack_ACTIVITY = 2;
    private static final int RESULT_SPEECH = 1;
    private static final int DATE_DIALOG_ID_1 = 1;
    public static int mcYear;
    public static int mcMonth;
    public static int mcDay;
    private static int mYear;
    private static int mMonth;
    private static int mDay;

    private final String QueryInPatient = "select id,prefix,patientname as name,Patid,age,gender,PC as photo  from Patreg  where enable_inpatient=1 order by patientname, id ";
    private final OnDateSetListener mDateSetListener1 = (datePicker, i, i1, i2) -> {
        Inpatient_List.mYear = i;
        Inpatient_List.mMonth = i1;
        Inpatient_List.mDay = i2;
        this.updateDisplay();
    };
    @BindView(id.inpatient_parent_layout)
    CoordinatorLayout inpatientParentLayout;
    @BindView(id.inpatient_toolbar)
    Toolbar inpatientToolbar;
    @BindView(id.inpatient_back)
    AppCompatImageView inpatientBack;
    @BindView(id.txvw_title)
    TextView txvwTitle;
    @BindView(id.listgrid)
    AppCompatImageView listgrid;
    @BindView(id.admin_request)
    AppCompatImageView adminRequest;
    @BindView(id.operation_theater)
    AppCompatImageView operationTheater;
    @BindView(id.imgvw_exit)
    AppCompatImageView imgvwExit;
    @BindView(id.cardvw_search_bar)
    CardView cardvwSearchBar;
    @BindView(id.edittext_search)
    EditText edittextSearch;
    @BindView(id.button_clear)
    ImageButton btClear;
    @BindView(id.button_refresh)
    ImageButton btRefresh;
    @BindView(id.textvw_patient_count)
    TextView textvwPatientCount;
    @BindView(id.imagevw_no_data_available)
    AppCompatImageView imagevwNoDataAvailable;
    @BindView(id.inpatient_fastscrollview)
    FastScrollRecyclerView inpatientFastscrollview;
    //**********************************************************************************************
    Dialog builderDialog;
    private ArrayList<CommonDataObjects.MyPatientGetSet> rowItems;
    private InpatientRecyclerAdapter inpatientRecyclerAdapter;

    public Inpatient_List() {
    }

    private static void LoadPatentIDnameForAuto(AutoCompleteTextView autoSpName) {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select *  from Patreg  where enable_inpatient != '1' order by name;", null);

        List<String> list = new ArrayList<>();

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

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.setContentView(layout.kdmc_layout_inpatient_list);


        try {
            this.GETINITIALIZE();
            this.CONTROLLISTENERS();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void CONTROLLISTENERS() {


        this.edittextSearch.addTextChangedListener(new TextWatcher() {

            @RequiresApi(api = VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1,
                                      int i2) {


                if (Inpatient_List.this.edittextSearch.getText().toString().length() > 0) {
                    boolean istext = false;
                    try {
                        Integer.parseInt(charSequence.toString());
                        istext = false;

                    } catch (NumberFormatException ignored) {
                        istext = true;
                    }
                    Inpatient_List.this.SelectedGetPatientDetailsFilter(Inpatient_List.this.QueryInPatient, charSequence.toString(), istext);
                } else {
                    Inpatient_List.this.textvwPatientCount.setText(String.format("%s: %d", Inpatient_List.this.getString(string.no_of_pat), Inpatient_List.this.rowItems.size()));
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Inpatient_List.this.edittextSearch.getText().toString().length() > 0) {


                } else {
                    Inpatient_List.this.SelectedGetPatientDetails(Inpatient_List.this.QueryInPatient);
                    Inpatient_List.this.textvwPatientCount.setText(String.format("%s: %d", Inpatient_List.this.getString(string.no_of_pat), Inpatient_List.this.rowItems.size()));
                }
            }

            @RequiresApi(api = VERSION_CODES.N)
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {

                boolean istext = false;

                try {
                    Integer.parseInt(charSequence.toString());
                    istext = false;

                } catch (NumberFormatException ignored) {
                    // e.printStackTrace();
                    istext = true;
                }


                Inpatient_List.this.SelectedGetPatientDetailsFilter(Inpatient_List.this.QueryInPatient, charSequence.toString(), istext);

            }
        });

        this.btClear.setOnClickListener(view -> {

            if (this.edittextSearch.getText().toString().length() > 0) {

                this.edittextSearch.setText("");
                this.inpatientRecyclerAdapter = new InpatientRecyclerAdapter(this.rowItems);
                this.inpatientFastscrollview.setAdapter(this.inpatientRecyclerAdapter);
                this.textvwPatientCount.setText(String.format("%s:%s", this.getString(string.noofpatient), String.valueOf(this.rowItems.size())));


            }

        });


        this.inpatientBack.setOnClickListener(v -> {

            BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

        });

        this.adminRequest.setOnClickListener(v -> this.ShowRequestDialog());

        this.operationTheater.setOnClickListener(v -> {

            BaseConfig.globalStartIntent(this, OperationTheater.class, null);

        });


        this.imgvwExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));


        this.listgrid.setOnClickListener(v -> {
            if (BaseConfig.listViewType.equals(ListViewType.ListView)) {
                BaseConfig.listViewType = ListViewType.GrideView;
                this.inpatientFastscrollview.setLayoutManager(new GridLayoutManager(this, 2));
                this.inpatientFastscrollview.setHasFixedSize(true);
                this.inpatientFastscrollview.setNestedScrollingEnabled(false);
                this.SelectedGetPatientDetails(this.QueryInPatient);
                this.listgrid.setImageResource(drawable.list);
            } else {
                BaseConfig.listViewType = ListViewType.ListView;
                this.listgrid.setImageResource(drawable.grid);
                this.inpatientFastscrollview.setLayoutManager(new LinearLayoutManager(this));
                this.inpatientFastscrollview.setHasFixedSize(true);
                this.inpatientFastscrollview.setNestedScrollingEnabled(false);
                this.SelectedGetPatientDetails(this.QueryInPatient);
            }
        });


        assert this.inpatientFastscrollview != null;


        Animation animRotate = AnimationUtils.loadAnimation(this, anim.anim_rotate);

        this.btRefresh.setOnClickListener(v -> {

            try {
                this.edittextSearch.setText("");
                v.startAnimation(animRotate);
                this.SelectedGetPatientDetails(this.QueryInPatient);

                Toast.makeText(this, string.refresh_txt, Toast.LENGTH_LONG).show();


            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    private void GETINITIALIZE() {


        ButterKnife.bind(this);


        this.setSupportActionBar(this.inpatientToolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.inpatientToolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.welcometoast = 0;
        if (BaseConfig.HOSPITALNAME.length() > 0) {
            this.txvwTitle.setText(String.format("%s  -  %s", BaseConfig.HOSPITALNAME, this.getString(string.title_inpatient)));
        } else {
            this.txvwTitle.setText(this.getString(string.title_inpatient));
        }


        this.rowItems = new ArrayList<>();

        GridLayoutManager lLayout = new GridLayoutManager(this, 2);
        this.inpatientFastscrollview.setHasFixedSize(true);
        this.inpatientFastscrollview.setLayoutManager(lLayout);
        this.inpatientFastscrollview.setNestedScrollingEnabled(false);


        if (BaseConfig.listViewType.equals(ListViewType.ListView)) {
            this.inpatientFastscrollview.setLayoutManager(new LinearLayoutManager(this));
        } else {
            this.inpatientFastscrollview.setLayoutManager(new GridLayoutManager(this, 2));
        }


        this.imagevwNoDataAvailable.setVisibility(View.GONE);


        new LoadPatientInfo(this.QueryInPatient, 1).execute();


    }

    private void SelectedGetPatientDetails(String Query) {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(Query, null);
            this.rowItems.clear();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String PREFIX = c.getString(c.getColumnIndex("prefix"));
                        String NAME = c.getString(c.getColumnIndex("name"));
                        String PATID = c.getString(c.getColumnIndex("Patid"));
                        String AGE = c.getString(c.getColumnIndex("age"));
                        String GENDER = c.getString(c.getColumnIndex("gender"));
                        String PHOTO = c.getString(c.getColumnIndex("photo"));


                        CommonDataObjects.MyPatientGetSet item = new CommonDataObjects.MyPatientGetSet(PREFIX, PREFIX+"."+NAME, PATID, AGE, GENDER, PHOTO);
                        item.IsOnlinePatient = false;
                        this.rowItems.add(item);


                    } while (c.moveToNext());
                }
            }


            c.close();
            db.close();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    //**********************************************************************************************

    private void SelectedGetPatientDetailsFilter(String Query, String filtervalue, boolean isName) {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(Query, null);
            this.rowItems.clear();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String PREFIX = c.getString(c.getColumnIndex("prefix"));
                        String NAME = c.getString(c.getColumnIndex("name"));
                        String PATID = c.getString(c.getColumnIndex("Patid"));
                        String AGE = c.getString(c.getColumnIndex("age"));
                        String GENDER = c.getString(c.getColumnIndex("gender"));
                        String PHOTO = c.getString(c.getColumnIndex("photo"));


                        CommonDataObjects.MyPatientGetSet item = new CommonDataObjects.MyPatientGetSet(PREFIX, PREFIX+"."+NAME, PATID, AGE, GENDER, PHOTO);
                        String val = c.getString(c.getColumnIndex("name"));
                        String name[] = val.split("\\.");
                        item.IsOnlinePatient = false;
                        String val1=null;
                        try {
                            val1 = name[1].trim();
                        } catch (Exception e) {
                            val1 = name[0].trim();
                            // e.printStackTrace();
                        }
                        if (isName) {
                            if (val1.toLowerCase().startsWith(filtervalue)) {
                                this.rowItems.add(item);
                            }

                        } else {

                            if (c.getString(c.getColumnIndex("Patid")).split("/")[3].startsWith(filtervalue)) {
                                this.rowItems.add(item);
                            }
                        }


                    } while (c.moveToNext());
                }
            }


            c.close();
            db.close();

            this.inpatientRecyclerAdapter = new InpatientRecyclerAdapter(this.rowItems);

            this.inpatientFastscrollview.setAdapter(this.inpatientRecyclerAdapter);

            this.textvwPatientCount.setText(String.format("%s: %s", this.getString(string.no_of_pat), String.valueOf(this.rowItems.size())));

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public final void onBackPressed() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

    }

    @Nullable
    @Override
    protected final Dialog onCreateDialog(int id) {
        switch (id) {

            case Inpatient_List.DATE_DIALOG_ID_1:

                return new DatePickerDialog(this, this.mDateSetListener1, Inpatient_List.mYear, Inpatient_List.mMonth, Inpatient_List.mDay);


        }
        return null;
    }

    private void updateDisplay() {

        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String strdate1 = sdf1.format(c1.getTime());
        Date got;
        try {
            got = dateFormat.parse(Inpatient_List.mDay + "/" + (Inpatient_List.mMonth + 1) + '/' + Inpatient_List.mYear);
            int result = dateFormat.parse(strdate1).compareTo(got);

            if (result < 0) {

                BaseConfig.showSimplePopUp("Invalid Selection. Please select correct date", "Warning!", this, drawable.ic_warning_black_24dp, color.orange_500);

            }
        } catch (Exception e) {

        }

    }

    private void ShowSuccessFullyPlacedRequest() {
        Builder alertBuilder = new Builder(this);
        alertBuilder.setTitle(this.getString(string.title_inpatient));
        alertBuilder.setMessage(string.request_placed);
        alertBuilder.setPositiveButton(this.getString(string.ok), (dialog, which) -> {

        });
        alertBuilder.show();
    }

    private void ShowRequestDialog() {

        View view = this.getLayoutInflater().inflate(layout.inpatient_request_layout, null);
        AutoCompleteTextView patientAuto = view.findViewById(id.patient_detail_values);
        patientAuto.setThreshold(1);
        Inpatient_List.LoadPatentIDnameForAuto(patientAuto);
        EditText remarksEdt = view.findViewById(id.request_remarks_edt);
        Button submitBtn = view.findViewById(id.submit);
        Button cancelBtn = view.findViewById(id.cancel);
        Spinner WardCategory = view.findViewById(id.ward_category);
        TextView Title_PatientName, Title_WarCategory, Title_Remarks;
        Title_PatientName = view.findViewById(id.txtvw_patname);
        Title_WarCategory = view.findViewById(id.txtvw_wardcategory);
        Title_Remarks = view.findViewById(id.txtvw_remarks);

        String first2 = "Patient Name";
        String next2 = "<font color='#EE0000'><b>*</b></font>";
        Title_PatientName.setText(Html.fromHtml(first2 + next2));

        String first3 = "Choose ward category";
        String next3 = "<font color='#EE0000'><b>*</b></font>";
        Title_WarCategory.setText(Html.fromHtml(first3 + next3));

        String first4 = "Request Remarks";
        String next4 = "<font color='#EE0000'><b>*</b></font>";
        Title_Remarks.setText(Html.fromHtml(first4 + next4));

        BaseConfig.LoadValuesSpinner(WardCategory, this, "select distinct WardCatName as dvalue from Mstr_WardCategory where HID='" + BaseConfig.HID + "' order by ServerId;", "select ward category");

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        submitBtn.setOnClickListener(v -> {


            if (Validation1.hasText(patientAuto)) {
                if (WardCategory.getSelectedItemPosition() > 0) {
                    if (patientAuto.getText().toString().contains("-")) {

                        if (Validation1.hasText(remarksEdt)) {

                            String patId = patientAuto.getText().toString().split("-")[1].trim();
                            String selectedWard = WardCategory.getSelectedItem().toString();


                            //Check for valid patient id
                            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + patId + '\'');
                            if (chk) {

                                ContentValues values = new ContentValues();
                                values.put("patid", patId);
                                values.put("request_remarks", remarksEdt.getText().toString());
                                values.put("WardCategory", selectedWard);
                                values.put("WardCategoryId", BaseConfig.GetValues("select ServerId as ret_values from Mstr_WardCategory where WardCatName='" + selectedWard + "' and HID='" + BaseConfig.HID + '\''));
                                values.put("IsActive", "1");
                                values.put("IsUpdate", "0");
                                values.put("docid", BaseConfig.doctorId);
                                values.put("ActDate", BaseConfig.DeviceDate());

                                SQLiteDatabase Db = BaseConfig.GetDb();//);
                                Db.insert("inpatient_request", null, values);
                                dialog.dismiss();

                                this.ShowSuccessFullyPlacedRequest();

                            } else {
//                                Toast.makeText(Inpatient_List.this, "Selected Patient Id/Name is not valid!", Toast.LENGTH_LONG).show();
                                BaseConfig.SnackBar(this, "Selected Patient Id/Name is not valid!", view, 2);

                            }


                        } else {
                            //Toast.makeText(Inpatient_List.this, "Enter remarks", Toast.LENGTH_LONG).show();
                            BaseConfig.SnackBar(this, "Enter remarks", view, 2);


                        }

                    } else {
                        Toast.makeText(this, string.valid_patient_details, Toast.LENGTH_LONG).show();
                        BaseConfig.SnackBar(this, this.getResources().getString(string.valid_patient_details), view, 2);

                    }

                } else {

                    Toast.makeText(this, string.choose_ward, Toast.LENGTH_LONG).show();
                    BaseConfig.SnackBar(this, this.getResources().getString(string.choose_ward), view, 2);

                }

            } else {
                Toast.makeText(this, string.pl_select_patient, Toast.LENGTH_LONG).show();
                BaseConfig.SnackBar(this, this.getResources().getString(string.pl_select_patient), view, 2);

            }

        });
        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    public class LoadPatientInfo extends AsyncTask<Void, Void, Void> {

        String PASSING_QUERY = "";
        int PASSING_ID = 1;

        LoadPatientInfo(String select_MyPatient_Query, int Id) {
            PASSING_ID = Id;
            PASSING_QUERY = select_MyPatient_Query;
        }

        @Override
        protected void onPreExecute() {
            Inpatient_List.this.builderDialog = BaseConfig.showCustomDialog(Inpatient_List.this.getString(string.please_wait), "Loading My Patients..", Inpatient_List.this);
            Inpatient_List.this.builderDialog.setCancelable(false);
            Inpatient_List.this.builderDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String Query = "select count(*)as ret_values  from Patreg where enable_inpatient=1 order by patientname, id ";
            int PresentCount = Integer.valueOf(BaseConfig.GetValues(Query));

            Log.e("Present Count: ", String.valueOf(PresentCount));

            if (PresentCount > Inpatient_List.this.rowItems.size()) {
                Inpatient_List.this.SelectedGetPatientDetails(this.PASSING_QUERY);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (Inpatient_List.this.builderDialog.isShowing() && Inpatient_List.this.builderDialog != null) {
                Inpatient_List.this.builderDialog.dismiss();
            }


            Inpatient_List.this.inpatientRecyclerAdapter = new InpatientRecyclerAdapter(Inpatient_List.this.rowItems);

            Inpatient_List.this.inpatientFastscrollview.setAdapter(Inpatient_List.this.inpatientRecyclerAdapter);

            Inpatient_List.this.textvwPatientCount.setText(String.format("%s: %s", Inpatient_List.this.getString(string.noofpatient), String.valueOf(Inpatient_List.this.rowItems.size())));


            super.onPostExecute(aVoid);
        }
    }


}//END
