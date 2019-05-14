package kdmc_kumar.Core_Modules;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MyPatientGetSet;
import kdmc_kumar.Adapters_GetterSetter.SearchMyPatienRecylerAdapter;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.DoctorIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.PostDoctorIdRequest;

public class SearchOnlinePatient extends AppCompatActivity {

    private static String Patient_List;
    private CoordinatorLayout parentLayout;
    private Toolbar toolbar;
    private ImageView home;
    private ImageView search;
    private TextView txvwTitle;
    ImageView home1;
    private RelativeLayout RelativeLayout1;
    private TextView TextView04;
    private TextView txtCount;
    private RecyclerView list;
    private GridLayoutManager lLayout;

    private Button GetDetails;
    private LinkedList<MyPatientGetSet> rowItems;

    private final ArrayList<String> CurrentPatientList = new ArrayList<>();

    private SearchMyPatienRecylerAdapter recylerAdapter;

    public SearchOnlinePatient() {
    }

    public static boolean LoadReportsBooleanStatus(String Query) {
        try {
            int havcount = 0;

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);

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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_searchonline_patient);


        try {

            this.GetInit();
            this.Contrll();
            this.LoadSearchDialog();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void GetInit() {

        this.parentLayout = this.findViewById(id.parent_layout);
        this.toolbar = this.findViewById(id.toolbar);
        this.home = this.findViewById(id.home);
        this.search = this.findViewById(id.search_ico);

        this.txvwTitle = this.findViewById(id.txvw_title);
        this.RelativeLayout1 = this.findViewById(id.RelativeLayout1);
        this.TextView04 = this.findViewById(id.TextView04);

        this.txtCount = this.findViewById(id.txt_count);


        this.list = this.findViewById(id.list);
        this.lLayout = new GridLayoutManager(this, 2);
        this.list.setHasFixedSize(true);
        this.list.setLayoutManager(this.lLayout);
        this.list.setNestedScrollingEnabled(false);
        this.list.setLayoutManager(new GridLayoutManager(this, 2));

        this.rowItems = new LinkedList<>();

        this.GetDetails = this.findViewById(id.getpatient);


    }

    // /////////////////////////////////////////////////////////////////////////////////////////////

    private final void LoadSearchDialog() {
        try {

            Dialog alertdialog = new Dialog(this);

            LayoutInflater inflater = this.getLayoutInflater();

            View view = inflater.inflate(layout.popup_search_patient, null);


            RadioButton Male_Rbtn, Female_Rbtn;
            Spinner AgeLimit;
            Button cancel;
            Button get;
            EditText patientname;
            EditText patientid, patientpin, patientmobile;

            Male_Rbtn = view.findViewById(id.rbtn_male);
            Female_Rbtn = view.findViewById(id.rbtn_female);
            AgeLimit = view.findViewById(id.age_limit);
            cancel = view.findViewById(id.cancel);
            get = view.findViewById(id.get);
            patientname = view.findViewById(id.search_name);
            patientid = view.findViewById(id.search_patid);
            patientpin = view.findViewById(id.patient_pin);
            patientmobile = view.findViewById(id.mobile_number);


            Male_Rbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {
                    Female_Rbtn.setChecked(false);
                }

            });

            Female_Rbtn.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {
                    Male_Rbtn.setChecked(false);
                }

            });


            get.setOnClickListener(v -> {


                if (patientname.getText().toString().length() == 0 && !Male_Rbtn.isChecked() && !Female_Rbtn.isChecked() && patientid.getText().toString().isEmpty() &&
                        patientid.getText().toString().equalsIgnoreCase("PID/") && AgeLimit.getSelectedItemPosition() > 0
                        && patientmobile.getText().length() == 0 && patientpin.getText().length() == 0) {
                    //         Toast.makeText(SearchOnlinePatient.this, R.string.entervalue, Toast.LENGTH_LONG).show();
                    BaseConfig.SnackBar(this, this.getResources().getString(string.entervalue), this.parentLayout, 1);


                    return;

                }

                if (patientname.getText().length() == 0) {
                    //Toast.makeText(SearchOnlinePatient.this, "Enter patient name..", Toast.LENGTH_LONG).show();
                    BaseConfig.SnackBar(this, "Enter patient name..", this.parentLayout, 1);

                    return;
                }


                if (this.checkPatientIsExist(patientid.getText().toString())) {

                    //Toast.makeText(SearchOnlinePatient.this, , Toast.LENGTH_SHORT).show();
                    BaseConfig.SnackBar(this, String.valueOf(string.already_patient_available), this.parentLayout, 2);

                } else {


                    String Gender;

                    if (Male_Rbtn.isChecked()) {
                        Gender = "Male";
                    } else if (Female_Rbtn.isChecked()) {
                        Gender = "Female";
                    } else {
                        Gender = "-";
                    }

                    String agelimit_1 = "";
                    String agelimit_2 = "";


                    if (AgeLimit.getSelectedItemPosition() == 0) {
                        agelimit_1 = "0";
                        agelimit_2 = "0";
                    } else if (AgeLimit.getSelectedItemPosition() > 0) {

                        agelimit_1 = AgeLimit.getSelectedItem().toString().split("-")[0];
                        agelimit_2 = AgeLimit.getSelectedItem().toString().split("-")[1];
                    }

                    alertdialog.dismiss();

                    new PatientMapping().execute(patientid.getText().toString().trim(), patientname.getText().toString(), agelimit_1, agelimit_2, Gender, patientpin.getText().toString(), patientmobile.getText().toString());

                }


            });

            cancel.setOnClickListener(v -> alertdialog.dismiss());


            alertdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertdialog.setContentView(view);
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            alertdialog.getWindow().setLayout((6 * width) / 7, LayoutParams.WRAP_CONTENT);
            alertdialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // /////////////////////////////////////////////////////////////////////////////////////////////

    private final void Contrll() {

        this.search.setOnClickListener(v -> this.LoadSearchDialog());

        this.home.setOnClickListener(v -> this.LoadBack());


    }

    private final boolean checkPatientIsExist(String patientid) {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select * from  Patreg where Patid='" + patientid + '\'', null);
        this.rowItems.clear();
        this.CurrentPatientList.clear();

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

    private final void LoadBack() {


        BaseConfig.HideKeyboard(this);

        BaseConfig.globalStartIntent(this, MyPatient.class, null);

    }

    @Override
    public final void onBackPressed() {

        this.LoadBack();
    }

    private final void LoadSearchedPatients(String resultsRequestSOAP) {

        try {

            this.recylerAdapter = new SearchMyPatienRecylerAdapter(this.rowItems, this.GetDetails, resultsRequestSOAP, this);
            this.list.setItemAnimator(new DefaultItemAnimator());
            this.list.setAdapter(this.recylerAdapter);

            String ListCount = String.valueOf(this.rowItems.size());
            this.txtCount.setText(this.getString(string.no_of_pat) + ListCount);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private String getDoctorIdRESTCALL(String doctorID, String methodName) throws com.magnet.android.mms.exception.SchemaException, InterruptedException, ExecutionException {
        MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this);
        PostDoctorIdFactory postDoctorIdFactory = new PostDoctorIdFactory(magnetMobileClient);
        PostDoctorId postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        PostDoctorIdRequest body = new PostDoctorIdRequest();
        body.setDoctorID(doctorID);
        body.setMethodName(methodName);

        Call<DoctorIdResult> resultCall = postDoctorId.postDoctorId(body, null);

        return resultCall.get().getResults();
    }


    private final boolean InsertPatientMapping(String Patid, String name, String age1, String age2, String gender, String patientpin, String patientmobile) throws InterruptedException, ExecutionException, SchemaException {


        String str = "";

        boolean result = false;


        SQLiteDatabase db = BaseConfig.GetDb();//ctx);



        String data="[{\"PATID\":\""+Patid+"\",\n" +
                "\"NAME\":\"%"+name+"%\",\n" +
                "\"AGE_LIMIT1\":\""+age1+"\",\n" +
                "\"AGE_LIMIT2\":\""+age2+"\",\n" +
                "\"GENDER\":\""+gender+"\",\n" +
                "\"PINNO\":\""+patientpin+"\",\n" +
                "\"PATIENTMOBILE\":\""+patientmobile+"\"}]\n";

        String MethodName = "Import_PatientMapping_Insert";
        String resultsRequestSOAP = this.getDoctorIdRESTCALL(data, MethodName);



        ContentValues values1;

        try {

            SearchOnlinePatient.Patient_List = resultsRequestSOAP.toString();

            if (!resultsRequestSOAP.toString().equalsIgnoreCase("[]")) {

                JSONArray jsonArray=new JSONArray(resultsRequestSOAP);
                JSONObject objJson = null;


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    String id_data = String.valueOf(objJson.getString("id"));
                    String Patid_data = String.valueOf(objJson.getString("Patid"));
                    String Docid_data = String.valueOf(objJson.getString("Docid"));
                    String reffered_data = String.valueOf(objJson.getString("reffered"));
                    String name_data = String.valueOf(objJson.getString("name"));

                    String Patientname="";
                    String Prefix="";

                    if (name_data.contains(".")) {
                        String prefixData[] = name_data.split("\\.");

                        Prefix = prefixData[0];

                        Patientname = prefixData[1];
                    }

                    String age_data = String.valueOf(objJson.getString("age"));
                    String gender_data = String.valueOf(objJson.getString("gender"));

                    String PatientPhoto = "";
                    try {

                        Bitmap theBitmap = BaseConfig.Glide_GetBitmap(this, objJson.getString("PC"));
                        PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, Patid_data.replace("/", "-"));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                        MyPatientGetSet item = new MyPatientGetSet(Prefix, Patientname,
                                Patid_data,
                                age_data,
                                gender_data,
                                PatientPhoto);
                    this.rowItems.addFirst(item);

                }


                result = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();

        return result;

    }

    final boolean IsOnlinePatient(String patId) {
        for (int i = 0; i <= this.CurrentPatientList.size() - 1; i++) {
            if (this.CurrentPatientList.get(i).equalsIgnoreCase(patId)) {
                return true;
            }
        }
        return false;
    }

    final ArrayList<String> CheckPatientsOnline() {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select * from current_patient_list where docid = '" + BaseConfig.doctorId + "' and status  = 'true'", null);
            this.rowItems.clear();
            this.CurrentPatientList.clear();

            if (c != null) {
                if (c.moveToFirst()) {
                    do

                    {
                        String date = c.getString(c.getColumnIndex("date"));
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                        Date strDate = sdf.parse(date);
                        Calendar c1 = Calendar.getInstance(); // today


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

                            this.CurrentPatientList.add(c.getString(c.getColumnIndex("patid")));

                        }


                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.CurrentPatientList;
    }

    @Override
    protected final void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }

    class PatientMapping extends AsyncTask<String, String, String> {

        boolean result;
        ProgressDialog progressDialog;

        PatientMapping() {
        }

        @Override
        protected final void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = ProgressDialog.show(SearchOnlinePatient.this, "Searching Patient Online", "Please Wait.....");
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setOnCancelListener(dialog -> this.result = false);
        }


        @Override
        protected final void onPostExecute(String result) {
            super.onPostExecute(result);

            this.progressDialog.dismiss();

            //  Toast.makeText(SearchOnlinePatient.this, "Result" + result, Toast.LENGTH_LONG).show();

            if (this.result) {

                new CustomKDMCDialog(SearchOnlinePatient.this)
                        .setLayoutColor(color.green_500)
                        .setImage(drawable.ic_success_done)
                        .setTitle("Success")
                        .setNegativeButtonVisible(View.GONE)
                        .setDescription("Patient details received successfully.")
                        .setPossitiveButtonTitle(getString(string.ok))
                        .setOnPossitiveListener(() -> {

                            try {
                                // CheckPatientsOnline();

                                //Load patients list
                                SearchOnlinePatient.this.LoadSearchedPatients(SearchOnlinePatient.Patient_List);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });


                //Patient Mapped Inserted


            } else {
                //is not valid Patient
                new CustomKDMCDialog(SearchOnlinePatient.this)
                        .setLayoutColor(color.red_500)
                        .setImage(drawable.ic_close_black_24dp)
                        .setTitle("Error")
                        .setDescription("Patient details not found..\nPlease Try again....")
                        .setPossitiveButtonTitle(getString(string.ok))
                        .setOnPossitiveListener(() -> {

                            try {
                                // CheckPatientsOnline();

                                //Load patients list
                                SearchOnlinePatient.this.LoadSearchedPatients(SearchOnlinePatient.Patient_List);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });


            }
        }

        @Nullable
        @Override
        protected final String doInBackground(String... params) {

            try {
                this.result = SearchOnlinePatient.this.InsertPatientMapping(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (SchemaException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////


    //End
}
