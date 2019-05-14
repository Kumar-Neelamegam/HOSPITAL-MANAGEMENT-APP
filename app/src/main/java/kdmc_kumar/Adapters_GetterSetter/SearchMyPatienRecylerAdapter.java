package kdmc_kumar.Adapters_GetterSetter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MyPatientGetSet;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu.NotificationClass;
import kdmc_kumar.Adapters_GetterSetter.SearchMyPatienRecylerAdapter.ViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Core_Modules.MyPatient;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Webservices_NodeJSON.ImportWebservices_NODEJS;

/**
 * Created by Ponnusamy M on 4/2/2017.
 */

public class SearchMyPatienRecylerAdapter extends Adapter<ViewHolder> {

    private static final int WRAP_CONTENT = 34;
    ArrayList<MyPatientGetSet> filteredList = new ArrayList<>();
    ArrayList<NotificationClass> notificationListTest = new ArrayList<>();
    private final LinkedHashMap<Integer, String> hm = new LinkedHashMap<>();
    private final Button getDetails;
    private final String ResponseJson;
    private final Context ctxx;
    private final List<MyPatientGetSet> mValues;
    private final List<MyPatientGetSet> dictionaryWords;


    public SearchMyPatienRecylerAdapter(List<MyPatientGetSet> mValues, Button getDetails, String ResponseJson, Context ctxx) {
        this.mValues = mValues;
        this.getDetails = getDetails;
        this.ResponseJson = ResponseJson;
        this.ctxx = ctxx;
    }

    @NonNull
    @Override
    public final SearchMyPatienRecylerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        // if (BaseConfig.listViewType.equals(ListViewType.ListView)) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(layout.grid_item_searchpatient, parent, false);

        return new SearchMyPatienRecylerAdapter.ViewHolder(view1);
    }


    @Override
    public final void onBindViewHolder(@NonNull SearchMyPatienRecylerAdapter.ViewHolder holder, int position) {

        String[] parts;
        String part1, part2 = null;
        try {

            // TODO: 3/20/2017 adding My patient infos

            holder.txtDesc.setText(this.mValues.get(position).getPatient_Age());
            holder.gender.setText(this.mValues.get(position).getPatient_Gender());

            holder.txtTitle.setText(this.mValues.get(position).getPatient_Name());
            holder.pid.setText(this.mValues.get(position).getPatient_Id());

            holder.checkPatient.setOnCheckedChangeListener((buttonView, isChecked) -> {


                String values = this.mValues.get(position).getPatient_Id();


                if (isChecked) {
                    this.hm.put(Integer.valueOf(position), values);
                    //Toast.makeText(buttonView.getContext(), "Added Selected Position: " + values, Toast.LENGTH_LONG).show();
                } else {
                    this.hm.remove(Integer.valueOf(position));
                    //Toast.makeText(buttonView.getContext(), "Removed Selected Position: " + values, Toast.LENGTH_LONG).show();
                }


            });


            this.getDetails.setOnClickListener(v -> {

                try {
                    if (this.hm != null && this.hm.size() > 0) {
                        new PatientMapping(this.ResponseJson, v).execute();
                    } else {
                        Toast.makeText(v.getContext(), "Select any one patient to get information...", Toast.LENGTH_SHORT).show();

//                        BaseConfig.SnackBar(this,  "Select any one patient to get information...", parentLayout);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        String PatientId = "";

        String PatientId1 = this.mValues.get(position).getPatient_Id();

        String finalPatientId = PatientId1;


        //Log.e("Image File Path: ", mValues.get(position).getPatient_Image());

        try {
            BaseConfig.LoadPatientImage(this.mValues.get(position).getPatient_Image(), holder.imageView, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.rootLayout.setLayoutParams(new AbsListView.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

/*
        if (mValues.get(position).IsOnlinePatient) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.closeOnlineButton.setVisibility(View.VISIBLE);

        } else if (!mValues.get(position).IsOnlinePatient) {
            holder.closeOnlineButton.setVisibility(View.GONE);
            holder.layout.setVisibility(View.GONE);
        }


        holder.closeOnlineButton.setOnClickListener(v -> ShowClosePatientDialog(v.getContext(), mValues, position));
*/


    }


//**********************************************************************************************

    private final void InsertPatient(String resultsRequestSOAP, View v) {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();

            if (!resultsRequestSOAP.equalsIgnoreCase("[]")) {

                JSONObject mainJson = new JSONObject(resultsRequestSOAP);
                JSONArray jsonArray = mainJson.getJSONArray("FoodItems");
                JSONObject objJson = null;


                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);

                    String id_data = String.valueOf(objJson.getString("id"));
                    String Patid_data = String.valueOf(objJson.getString("Patid"));


                    String Docid_data = String.valueOf(objJson.getString("Docid"));
                    String reffered_data = String.valueOf(objJson.getString("reffered"));
                    String name_data = String.valueOf(objJson.getString("name"));
                    String age_data = String.valueOf(objJson.getString("age"));
                    String gender_data = String.valueOf(objJson.getString("gender"));
                    String DOB_data = String.valueOf(objJson.getString("DOB"));
                    String weight_data = String.valueOf(objJson.getString("weight"));
                    String Country_data = String.valueOf(objJson.getString("Country"));
                    String State_data = String.valueOf(objJson.getString("State"));
                    String District_data = String.valueOf(objJson.getString("District"));
                    String Address_data = String.valueOf(objJson.getString("Address"));
                    String pincode_data = String.valueOf(objJson.getString("pincode"));
                    String phone_data = String.valueOf(objJson.getString("phone"));
                    String PMH_data = String.valueOf(objJson.getString("PMH"));
                    String Allergy_data = String.valueOf(objJson.getString("Allergy"));
                    String IsActive_data = String.valueOf(objJson.getString("IsActive"));
                    String imei_data = String.valueOf(objJson.getString("imei"));
                    String city_data = String.valueOf(objJson.getString("city"));
                    String altphone_data = String.valueOf(objJson.getString("altphone"));
                    String Actdate_data = String.valueOf(objJson.getString("Actdate"));
                    String Isupdate_data = String.valueOf(objJson.getString("Isupdate"));
                    String Address1_data = String.valueOf(objJson.getString("Address1"));
                    String email_data = String.valueOf(objJson.getString("email"));
                    String caretaker_data = String.valueOf(objJson.getString("caretaker"));
                    String crtknum_data = String.valueOf(objJson.getString("crtknum"));
                    String relationship_data = String.valueOf(objJson.getString("relationship"));
                    String willingsms_data = String.valueOf(objJson.getString("willingsms"));
                    String smsto_data = String.valueOf(objJson.getString("smsto"));
                    String smsfor_data = String.valueOf(objJson.getString("smsfor"));
                    String willingblood_data = String.valueOf(objJson.getString("willingblood"));
                    String willingeye_data = String.valueOf(objJson.getString("willingeye"));
                    String hereditary_data = String.valueOf(objJson.getString("hereditary"));
                    String mbid_data = String.valueOf(objJson.getString("mbid"));
                    String pinno_data = String.valueOf(objJson.getString("pinno"));
                    String issms_data = String.valueOf(objJson.getString("issms"));
                    String bloodgroup_data = String.valueOf(objJson.getString("bloodgroup"));
                    String Policyname_data = String.valueOf(objJson.getString("Policyname"));
                    String Inscompany_data = String.valueOf(objJson.getString("Inscompany"));
                    String Insamount_data = String.valueOf(objJson.getString("Insamount"));
                    String Insvalidity_data = String.valueOf(objJson.getString("Insvalidity"));
                    String Authorhospital_data = String.valueOf(objJson.getString("Authorhospital"));
                    String Isprfupdate_data = String.valueOf(objJson.getString("Isprfupdate"));
                    String Pages_data = String.valueOf(objJson.getString("Pages"));
                    String docReferName_data = String.valueOf(objJson.getString("docReferName"));
                    String docReferNo_data = String.valueOf(objJson.getString("docReferNo"));
                    String enable_inpatient_data = String.valueOf(objJson.getString("enable_inpatient"));
                    if (enable_inpatient_data.equalsIgnoreCase("true")) {
                        enable_inpatient_data = "1";
                    } else if (enable_inpatient_data.equalsIgnoreCase("false")) {
                        enable_inpatient_data = "0";
                    }
                    String admitdt_data = String.valueOf(objJson.getString("admitdt"));
                    String admittime_data = String.valueOf(objJson.getString("admittime"));
                    String Ward_data = String.valueOf(objJson.getString("Ward"));
                    String Bed_data = String.valueOf(objJson.getString("Bed"));
                    String roomno_data = String.valueOf(objJson.getString("roomno"));
                    String dischargedt_data = String.valueOf(objJson.getString("dischargedt"));
                    String HospitalId_data = String.valueOf(objJson.getString("HospitalId"));
                    String Presentwithco_data = String.valueOf(objJson.getString("Presentwithco"));
                    String doc_refer_name_data = String.valueOf(objJson.getString("doc_refer_name"));
                    String doc_refer_no_data = String.valueOf(objJson.getString("doc_refer_no"));
                    String IsFeeExemp_data = String.valueOf(objJson.getString("IsFeeExemp"));
                    String FeeExempCateg_data = String.valueOf(objJson.getString("FeeExempCateg"));
                    String bplCardNo_data = String.valueOf(objJson.getString("bplCardNo"));
                    String AadharCardNo_data = String.valueOf(objJson.getString("AadharCardNo"));
                    String FeeExempReason_data = String.valueOf(objJson.getString("FeeExempReason"));
                    String caste_data = String.valueOf(objJson.getString("caste"));
                    String income_data = String.valueOf(objJson.getString("income"));
                    String under_care_of_data = String.valueOf(objJson.getString("under_care_of"));
                    String referred_by_data = String.valueOf(objJson.getString("referred_by"));
                    String Occupation_data = String.valueOf(objJson.getString("Occupation"));
                    String distime = String.valueOf(objJson.getString("distime"));
                    String disdate = String.valueOf(objJson.getString("disdate"));
                    String Fathername_data = String.valueOf(objJson.getString("Fathername"));
                    String spouse = String.valueOf(objJson.getString("spouse"));

                    ContentValues values1;

                    values1 = new ContentValues();
                    values1.put("Patid", Patid_data);
                    values1.put("Docid", Docid_data);
                    values1.put("referred_by", reffered_data);
                    values1.put("name", name_data);
                    try {
                        if (name_data.contains(".")) {
                            String prefixData[] = name_data.split("\\.");
                            values1.put("prefix", prefixData[0]);   values1.put("patientname", prefixData[1].trim());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // TODO: 5/9/2017 Online URL to Write External Storage
                    /*
                      @Muthukumar N
                     * 27-4-2017
                     */
                    String PatientPhoto = "";
                    try {
                        //Log.e("URL: ", objJson.getString("PC"));
                        Bitmap theBitmap = BaseConfig.Glide_GetBitmap(v.getContext(),objJson.getString("PC"));

                        PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, Patid_data.replace("/", "-"));
                        //Log.e("IMG URL TO PATH: ", PatientPhoto);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    values1.put("age", age_data);
                    values1.put("gender", gender_data);
                    values1.put("DOB", DOB_data);
                    values1.put("weight", weight_data);
                    values1.put("Country", Country_data);
                    values1.put("State", State_data);
                    values1.put("District", District_data);
                    values1.put("Address", Address_data);
                    values1.put("pincode", pincode_data);
                    values1.put("phone", phone_data);
                    values1.put("PMH", PMH_data);
                    values1.put("PC", PatientPhoto);
                    values1.put("Allergy", Allergy_data);
                    values1.put("IsActive", IsActive_data);
                    values1.put("imei", imei_data);
                    values1.put("city", city_data);
                    values1.put("altphone", altphone_data);
                    values1.put("Actdate", Actdate_data);
                    values1.put("Isupdate", Isupdate_data);
                    values1.put("Address1", Address1_data);
                    values1.put("email", email_data);
                    values1.put("caretaker", caretaker_data);
                    values1.put("crtknum", crtknum_data);
                    values1.put("relationship", relationship_data);
                    values1.put("willingsms", willingsms_data);
                    values1.put("smsto", smsto_data);
                    values1.put("smsfor", smsfor_data);
                    values1.put("willingblood", willingblood_data);
                    values1.put("willingeye", willingeye_data);
                    values1.put("hereditary", hereditary_data);
                    values1.put("PatientPin", pinno_data);
                    values1.put("bloodgroup", bloodgroup_data);
                    values1.put("policyname", Policyname_data);
                    values1.put("insurancecompany", Inscompany_data);
                    values1.put("amountinsured", Insamount_data);
                    values1.put("validity", Insvalidity_data);
                    values1.put("inshosp", Authorhospital_data);
                    values1.put("enable_inpatient", enable_inpatient_data);
                    values1.put("admitdt", admitdt_data);
                    values1.put("admit_time", admittime_data);
                    values1.put("wardno", Ward_data);
                    values1.put("bedno", Bed_data);
                    values1.put("roomno", roomno_data);
                    values1.put("discharge_date", disdate);
                    values1.put("discharge_time", distime);
                    values1.put("HospitalId", HospitalId_data);
                    values1.put("Presentwithco", Presentwithco_data);
                    values1.put("doc_refer_name", doc_refer_name_data);
                    values1.put("doc_refer_no", doc_refer_no_data);
                    values1.put("IsFeeExemp", IsFeeExemp_data);
                    values1.put("FeeExempCateg", FeeExempCateg_data);
                    values1.put("bplCardNo", bplCardNo_data);
                    values1.put("AadharCardNo", AadharCardNo_data);
                    values1.put("FeeExempReason", FeeExempReason_data);
                    values1.put("caste", caste_data);
                    values1.put("income", income_data);
                    values1.put("under_care_of", under_care_of_data);
                    values1.put("referred_by", doc_refer_name_data);
                    values1.put("Occupation", Occupation_data);
                    values1.put("Fathername", Fathername_data);
                    values1.put("spouse", spouse);


                    for (Iterator<Entry<Integer, String>> iterator = this.hm.entrySet().iterator(); iterator.hasNext(); ) {
                        Entry m = iterator.next();
                        //Log.e("Details: ", m.getKey() + " " + m.getValue());

                        if (Patid_data.equalsIgnoreCase(m.getValue().toString())) {

                            boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + Patid_data + '\'');

                            if (GetStatus) {


                                db.update("Patreg", values1, "Patid ='" + Patid_data + "'", null);
                                //Log.e("Update PatitentMapping Webservice: ", String.valueOf(values1));
                            } else {

                                db.insert("Patreg", null, values1);
                                //Log.e("Insert PatitentMapping Webservice: ", String.valueOf(values1));

                                ImportWebservices_NODEJS.LoadForVaccination(Patid_data, name_data, age_data + "-" + gender_data, phone_data);

                            }

                            //Current Online Patient
                            values1 = new ContentValues();
                            values1.put("date", BaseConfig.Device_OnlyDate2());
                            values1.put("patid", Patid_data);
                            values1.put("docid", BaseConfig.doctorId);
                            values1.put("status", "true");
                            values1.put("closed", "0");
                            db.insert("current_patient_list", null, values1);
                            //Log.e("InsertPatientMapping Current Online: ", values1.toString());


                        }
                    }


                    this.UpdatePatientMapping(this.hm);


                }
            }

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void UpdatePatientMapping(LinkedHashMap<Integer, String> hm) {
        try {

            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            for (Iterator<Entry<Integer, String>> iterator = this.hm.entrySet().iterator(); iterator.hasNext(); ) {
                Entry m = iterator.next();

                from_db_obj = new JSONObject();

                //Log.e("Details: ", m.getKey() + " " + m.getValue());

                from_db_obj.put("Ptid", m.getValue().toString());
                from_db_obj.put("HID", BaseConfig.HID);
                from_db_obj.put("DocId", BaseConfig.doctorId);
                export_jsonarray.put(from_db_obj);

            }

            if (export_jsonarray != null && export_jsonarray.length() > 0) {

               // String ReturnValue = BaseConfig.ExportDataToServer("insertMappingPatient", export_jsonarray.toString());//Modified for exporting data to server @ MK

                //Log.e("InsertMappingPatient: ", export_jsonarray.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//**********************************************************************************************

    private final void ShowClosePatientDialog(Context ctx, List<MyPatientGetSet> mValues, int position) {


        new CustomKDMCDialog(ctx)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_close_black_24dp)
                .setTitle(ctx.getResources().getString(string.close_online))
                .setDescription(ctx.getResources().getString(string.close_online_txt))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {

                    String Query = "Update current_patient_list set closed = '1' where patid = '" + mValues.get(position).getPatient_Id() + '\'';

                    SQLiteDatabase db = BaseConfig.GetDb();
                    db.execSQL(Query);
                    db.close();
                    mValues.get(position).IsOnlinePatient = false;
                    this.notifyDataSetChanged();

                });



    }


//**********************************************************************************************

    public static final boolean LoadReportsBooleanStatus(String Query, Context context) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


//**********************************************************************************************

    @Override
    public final int getItemCount() {
        return this.mValues.size();
    }


//**********************************************************************************************

    public static final boolean LoadReportsBooleanStatus(String Query) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


//**********************************************************************************************

    class PatientMapping extends AsyncTask<String, String, String> {


        boolean result;
        ProgressDialog progressDialog;

        final String responseJson;
        final View v;

        PatientMapping(String responseJson, View v) {
            this.responseJson = responseJson;
            this.v = v;
        }

        @Override
        protected final void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = ProgressDialog.show(this.v.getContext(),
                    "Please wait",
                    "Getting all selected patient details...");

            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setOnCancelListener(dialog -> this.result = false);
        }

        @Override
        protected final void onPostExecute(String result) {
            super.onPostExecute(result);
            this.progressDialog.dismiss();

            Toast.makeText(this.v.getContext(), "Patient details imported successfully...", Toast.LENGTH_SHORT).show();
           // BaseConfig.SnackBar(this,  "Patient details imported successfully..." , parentLayout);

            new CustomKDMCDialog(this.v.getContext())
                    .setLayoutColor(color.green_500)
                    .setImage(drawable.ic_success_done)
                    .setTitle(this.v.getContext().getString(string.information)).setNegativeButtonVisible(View.GONE)
                    .setDescription("Patient details received successfully.")
                    .setPossitiveButtonTitle(this.v.getContext().getString(string.ok))
                    .setOnPossitiveListener(() -> {
                        ((Activity) SearchMyPatienRecylerAdapter.this.ctxx).finish();
                        Intent OnlinePatient = new Intent(SearchMyPatienRecylerAdapter.this.ctxx, MyPatient.class);
                        SearchMyPatienRecylerAdapter.this.ctxx.startActivity(OnlinePatient);
                    });






        }

        @Nullable
        @Override
        protected final String doInBackground(String... params) {

            try {
                SearchMyPatienRecylerAdapter.this.InsertPatient(this.responseJson, this.v);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


//**********************************************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {

      //  final Button closeOnlineButton;
        final ImageView imageView;
        final LinearLayout rootLayout;

        final TextView txtTitle;
        final LinearLayout layout;
        final TextView txtDesc;
        final TextView gender;
        final TextView pid;

        TextView labreport_text;
        LinearLayout labreport;
        final CardView card_view;
        final CheckBox checkPatient;


        ViewHolder(View view) {
            super(view);
            this.txtDesc = view.findViewById(id.desc);
            this.txtTitle = view.findViewById(id.title);
            this.imageView = view.findViewById(id.icon);
            this.rootLayout = view.findViewById(id.list_root);
            this.layout = view.findViewById(id.online_patient);
           // closeOnlineButton = view.findViewById(R.id.close_online_patient);
            this.gender = view.findViewById(id.gender);
            this.pid = view.findViewById(id.pid);
            this.card_view = view.findViewById(id.card_view);
            this.checkPatient = view.findViewById(id.chk_bx);

        }

    }


//**********************************************************************************************

}
//**********************************************************************************************

