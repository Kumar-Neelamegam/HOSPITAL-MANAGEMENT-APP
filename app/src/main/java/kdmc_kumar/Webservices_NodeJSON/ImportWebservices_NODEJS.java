package kdmc_kumar.Webservices_NodeJSON;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.NotificationGetSet;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.NotificationListGetSet;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.controller.api.PatientMapping;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.controller.api.PatientMappingFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.model.beans.GetPatientMappingRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.model.beans.PatientMappingResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.controller.api.UpdateDB;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.controller.api.UpdateDBFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.model.beans.GetUpdateDBRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.model.beans.UpdateDBResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.api.PosPatIdDocIdMtestIdNew;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.api.PosPatIdDocIdMtestIdNewFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.controller.api.PostPatId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.controller.api.PostPatIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.model.beans.PosPatIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.model.beans.PosPatIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.DoctorIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.PostDoctorIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.controller.api.PostImmunizationInfo;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.controller.api.PostImmunizationInfoFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.model.beans.PosImmunizationInfoRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.model.beans.PosImmunizationInfoResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.controller.api.PostIsUpdateMax;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.controller.api.PostIsUpdateMaxFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.IsUpdateMaxResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.PostIsUpdateMaxRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.controller.api.PostIsUpdateMaxDocId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.controller.api.PostIsUpdateMaxDocIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.model.beans.IsUpdateMaxDocIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.model.beans.PostIsUpdateMaxDocIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.controller.api.PostPatidMtestid;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.controller.api.PostPatidMtestidFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.model.beans.PatidMtestidResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.model.beans.PostPatidMtestidRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.controller.api.PostPatientIdList;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.controller.api.PostPatientIdListFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PatientIdListResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PostPatientIdListRequest;

import static kdmc_kumar.Core_Modules.BaseConfig.LoadDoctorValues;

public class ImportWebservices_NODEJS {

    private static Context ctx;
    /**
     * Ponnu
     * Notification - kdmc for investigation
     */

    //============================================================================================================
    //============================================================================================================

    private final String compareRBSName = "blood sug random";
    private final String compareFBSName = "blood sug fasting";
    private final String comparePBSName = "blood sug pp";
    BaseConfig bc = new BaseConfig();
    private String sndserverid = "";
    private String patient_Id = "";
    private String MedId = "";

    public ImportWebservices_NODEJS(Context ctx) {
        ImportWebservices_NODEJS.ctx = ctx;
    }

    public static void LoadForVaccination(String pid, String pname, String agegen, String mob) {
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        int upid = 0;

        Cursor c = db
                .rawQuery(
                        "select distinct vaccinename,schedule from listofvaccine",
                        null);

        if (null != c) {
            if (c.moveToFirst()) {
                do {

                    String InsertQuery = "Insert into Vaccination(patid,pname,agegen,mobnum,drid,vaccinename,schedule,Isupdate,isactive) values"
                            + "('"
                            + pid
                            + "','"
                            + pname
                            + "','"
                            + agegen
                            + "','"
                            + mob
                            + "','"
                            + ""
                            + "','"
                            + c.getString(c.getColumnIndex("vaccinename"))
                            + "','"
                            + c.getString(c.getColumnIndex("schedule"))
                            + "','0','0');";
                    db.execSQL(InsertQuery);

                } while (c.moveToNext());
            }

            c.close();
            db.close();
        }
    }

    private static String getPatientList() throws JSONException {
        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();
        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (null != c) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }
        // JSONObject parentData = new JSONObject();
        //  parentData.put("PatientList", patientIdList);
        Objects.requireNonNull(c).close();
        db.close();

        return patientIdList.toString();
    }

    private static String GetDocid() {
        String str = "";

        try {
            SQLiteDatabase db = BaseConfig.GetDb();

            // database = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery("Select distinct Docid from Drreg", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        /*
                         * str.append(c.getString(c.getColumnIndex("token")));
                         * str.append("^");
                         */
                        str = c.getString(c.getColumnIndex("Docid"));

                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return str;
    }

    private static String GetCurrentSeenValue(String patId, String mTestId, String allTest) {


        String bedIdStr = "";
        try {

            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select * from Medicaltestdtls where Ptid ='" + patId.trim() + "' and alltest = '" + allTest + "' and mTestId = '" + mTestId + '\'', null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        bedIdStr = c.getString(c.getColumnIndex("seen"));

                    } while (c.moveToNext());
                }
            }

            db.close();
            Objects.requireNonNull(c).close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bedIdStr == null) {
            return "0";
        }
        return bedIdStr;
    }

    static boolean CheckNodeServer() {

        boolean statusvalue = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(BaseConfig.AppNodeIP)).openConnection();
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();

            if (status == 200) {
                statusvalue = true;
                BaseConfig.server_connectivity_status = true;
            } else {
                BaseConfig.server_connectivity_status = false;
            }
            conn.disconnect();
            return statusvalue;
        } catch (Exception e) {


        }
        return statusvalue;
    }

    public static void CheckDbUpdatesNodeJs(Context context) {
        try {

            UpdateDB updateDB;
            String DoctorID = GetDocid();


            SQLiteDatabase db = BaseConfig.GetDb();

            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(context);
            UpdateDBFactory controllerFactory = new UpdateDBFactory(magnetClient);
            updateDB = controllerFactory.obtainInstance();


            // FIXME : set proper value for the parameters
            String contentType = "application/json";
            GetUpdateDBRequest body = new GetUpdateDBRequest();
            body.setDocid(DoctorID);
            body.setImei(BaseConfig.Imeinum);
            body.setMac(BaseConfig.MacId);

            Call<UpdateDBResult> callObject = updateDB.getUpdateDB(
                    contentType,
                    body, null);

            UpdateDBResult result = callObject.get();

            String res = result.getResults();


            JSONArray jsonArray = new JSONArray(res);


            for (int i = 0; i < jsonArray.length(); i++) {
                String Insert_TABLE_ps = ((JSONObject) jsonArray.get(i)).getString("query");
                db.execSQL(Insert_TABLE_ps);

            }


            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @SuppressLint("LongLogTag")
    public final void ExecuteAll() {

        if (BaseConfig.CheckNetwork(ctx)) {

            try {

                if (ImportWebservices_NODEJS.CheckNodeServer()) {


                    Log.e("###########", "################");
                    Log.e("MobyDoctor BackGround", "Thread Import Service running 1");
                    Log.e("MobyDoctor BackGround", "Thread Import Service running 1");
                    Log.e("MobyDoctor BackGround", "Thread Import Service running 1");
                    Log.e("###########", "################");


                    CheckDbUpdatesNodeJs(ImportWebservices_NODEJS.ctx);  // TODO: 1/24/2018 Completed

                    LoadDoctorValues();// TODO: 1/24/2018 Note Used

                    //HID IsUpdateMax
                    InsertPatientMapping(); // TODO: 1/24/2018 Completed


                    //Docid
                    ImportDoctorInfo();         // TODO: 1/24/2018 Completed
                    ImportReportGallery();      // TODO: 1/24/2018 Completed
                   // this.ImportPatientInfo();        // TODO: 1/24/2018 Completed
                    ImportAppointment();        // TODO: 1/24/2018 Completed
                    ImportOnlineConsultation(); // TODO: 1/24/2018 Completed


                    //IsUpdateMax
                    ImportScanMaster();           // TODO: 1/24/2018 Completed
                    ImportDoctorsList();          // TODO: 1/24/2018 Completed
                    ImportTest();                 // TODO: 1/24/2018 Completed
                    ImportPharmacyDtls();         // TODO: 1/24/2018 Completed -- waste
                    ImportDiagnosticCentreDtls(); // TODO: 1/24/2018 Completed -- waste

                    //IsUpdateMax DocId
                    ImportOperationSchedule();          // TODO: 1/24/2018 Completed

                    //PatientIdList
                    ImportBind_ClinicalInformation();   // TODO: 1/24/2018 Completed


                    //Investigation
                    ImportBind_MedicalTestDtls();       // TODO: 1/24/2018 Completed
                    ImportBind_MedicalTest();           // TODO: 1/24/2018 Completed
                    ImportScanDtls();                   // TODO: 1/24/2018 Completed
                    ImportXrayDtls();                   // TODO: 1/24/2018 Completed
                    ImportEEGDtls();                    // TODO: 1/24/2018 Completed
                    ImportECGDtls();                    // TODO: 1/24/2018 Completed
                    ImportAngiogram();                  // TODO: 1/24/2018 Completed


                    ImportMprescribed();                // TODO: 1/24/2018 Completed


                    //Casenotes
                    ImportBind_Diagnosis();             // TODO: 1/24/2018 Completed
                    ImportBind_GeneralExamination();    // TODO: 1/24/2018 Completed
                    ImportBind_Cardiovascular();        // TODO: 1/24/2018 Completed
                    ImportBind_RespiratorySystem();     // TODO: 1/24/2018 Completed
                    ImportBind_Gastrointestinal();      // TODO: 1/24/2018 Completed
                    ImportBind_Neurology();             // TODO: 1/24/2018 Completed
                    ImportCaseNote_Renal();             // TODO: 1/24/2018 Completed
                    ImportCaseNote_Endocrine();         // TODO: 1/24/2018 Completed
                    ImportCaseNote_ClinicalData();      // TODO: 1/24/2018 Completed
                    ImportCaseNote_Locomotor();         // TODO: 1/24/2018 Completed
                    ImportBind_CaseNote_OtherSystem();  // TODO: 1/24/2018 Completed
                    ImportCaseNote_Dental();  // TODO: 1/24/2018 Completed

                    //Docid Ptid
                    InsertImmunizationInformation();  // TODO: 1/24/2018 Completed

                    //Docid Patid Mtest id list
                    ImportupdatedTestDtls();  // TODO: 1/24/2018 Completed
                    ImportupdatedScanDtls();  // TODO: 1/24/2018 Completed
                    ImportupdatedXrayDtls();  // TODO: 1/24/2018 Completed
                    ImportupdatedEEGDtls();   // TODO: 1/24/2018 Completed //err
                    ImportupdatedECGDtls();   // TODO: 1/24/2018 Completed
                    ImportupdatedAngiogram(); // TODO: 1/24/2018 Completed


                    //Patid
                    Importmast_Operations(); // TODO: 1/24/2018 Completed


                    //Patid Mtestid
                    Importmast_Upload();            // TODO: 1/24/2018 Completed
                    Import_ExaminationBlood_Test(); // TODO: 1/24/2018 Completed
                    Import_ExaminationStool_Test(); // TODO: 1/24/2018 Completed
                    Import_ExaminationUrine_Test(); // TODO: 1/24/2018 Completed
                    Import_ExaminationANC_Test();   // TODO: 1/24/2018 Completed
                    Import_ExaminationHIV_Test();   // TODO: 1/24/2018 Completed


                    this.LoadInvestigation_Notification(); // Investigation Notification





                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void InsertPatientMapping() {


        SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;

        try {


            MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
            PatientMappingFactory patientMappingFactory = new PatientMappingFactory(magnetMobileClient);
            PatientMapping patientMapping = patientMappingFactory.obtainInstance();

            GetPatientMappingRequest getPatientMappingRequest = new GetPatientMappingRequest();
            getPatientMappingRequest.sethID(BaseConfig.doctorId);
            getPatientMappingRequest.setIsUpdateMax("0");

            Call<PatientMappingResult> resultCall = patientMapping.getPatientMapping(getPatientMappingRequest, null);

            String resultsRequestSOAP = resultCall.get().getResults();

            if (!"[]".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

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
                    //String PC_data = String.valueOf(objJson.getString("PC"));//Commented because to save base64 into images

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
                    if ("true".equalsIgnoreCase(enable_inpatient_data)) {
                        enable_inpatient_data = "1";
                    } else if ("false".equalsIgnoreCase(enable_inpatient_data)) {
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
                    String Occupation_data = String.valueOf(objJson.getString("Occupation"));
                    String distime = String.valueOf(objJson.getString("distime"));
                    String disdate = String.valueOf(objJson.getString("disdate"));
                    String Fathername_data = String.valueOf(objJson.getString("Fathername"));
                    String spouse = String.valueOf(objJson.getString("spouse"));

                    values1 = new ContentValues();

                    values1.put("Patid", Patid_data);
                    values1.put("Docid", Docid_data);
                    values1.put("referred_by", reffered_data);
                    values1.put("name", name_data);
                    try {
                        if (name_data.contains(".")) {
                            String[] prefixData = name_data.split("\\.");
                            values1.put("prefix", prefixData[0]);
                            values1.put("patientname", prefixData[1].trim());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String PatientPhoto = "";
                    try {
                        Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ImportWebservices_NODEJS.ctx, String.valueOf(objJson.getString("PC")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("PC"))).asBitmap().into(-1, -1).get();

                        PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, Patid_data.replace("/", "-"));

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
                    values1.put("IsFeeExemp", IsFeeExemp_data.toString().equalsIgnoreCase("true")? "Yes" : "No");
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

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + Patid_data + '\'');

                    if (!GetStatus) {

                        db.insert("Patreg", null, values1);
                        LoadForVaccination(Patid_data, name_data, age_data + "-" + gender_data, phone_data);

                    } else {

                        db.update("Patreg", values1, "Patid ='" + Patid_data + '\'', null);

                        String Update_Vaccination = "update  Vaccination set pname='"+name_data+ "',agegen='"+age_data+ '-'+ gender_data+ "',mobnum='"+phone_data+ "' where patid='"+Patid_data+ "';";
                        BaseConfig.SaveData(Update_Vaccination);

                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();

    }

    private void ImportDoctorInfo() {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();
            String DoctorID = GetDocid();
            String MethodName = "infodrupdateJSON";
            try {
                String resultsRequestSOAP = getDoctorIdRESTCALL(DoctorID, MethodName);

                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;


                    for (int i = 0; i < jsonArray.length(); i++) {


                        objJson = jsonArray.getJSONObject(i);
                        String Id = String.valueOf(objJson.getString("Id"));
                        String Docid = String.valueOf(objJson.getString("Docid"));
                        String name = String.valueOf(objJson.getString("name"));
                        String age = String.valueOf(objJson.getString("age"));
                        String gender = String.valueOf(objJson.getString("gender"));
                        String DOB = String.valueOf(objJson.getString("DOB"));
                        String RegId = String.valueOf(objJson.getString("RegId"));
                        String TaxNo = String.valueOf(objJson.getString("TaxNo"));
                        String Academic = String.valueOf(objJson.getString("Academic"));
                        String Academic_text = String.valueOf(objJson.getString("Academic_text"));
                        String Specialization = String.valueOf(objJson.getString("Specialization"));
                        String Specialization_text = String.valueOf(objJson.getString("Specialization_text"));
                        String Country = String.valueOf(objJson.getString("Country"));
                        String State = String.valueOf(objJson.getString("State"));
                        String District = String.valueOf(objJson.getString("District"));
                        String Username = String.valueOf(objJson.getString("Username"));
                        String Password = String.valueOf(objJson.getString("Password"));
                        String Address = String.valueOf(objJson.getString("Address"));
                        String pincode = String.valueOf(objJson.getString("pincode"));
                        String Lanline = String.valueOf(objJson.getString("Lanline"));
                        String Mobile = String.valueOf(objJson.getString("Mobile"));
                        String Time = String.valueOf(objJson.getString("Time"));
                        String consultation = String.valueOf(objJson.getString("consultation"));
                        String clinicname = String.valueOf(objJson.getString("clinicname"));
                        String photo = String.valueOf(objJson.getString("photo"));
                        String mail = String.valueOf(objJson.getString("mail"));
                        String Ismail = String.valueOf(objJson.getString("Ismail"));
                        String homephone = String.valueOf(objJson.getString("homephone"));
                        String IsActive = String.valueOf(objJson.getString("IsActive"));
                        String Actdate = String.valueOf(objJson.getString("InsertedDt"));
                        String Imei = String.valueOf(objJson.getString("Imei"));
                        String docsign = String.valueOf(objJson.getString("docsign"));
                        String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                        String Issms = String.valueOf(objJson.getString("Issms"));
                        String paymenttype = String.valueOf(objJson.getString("paymenttype"));
                        String pay = String.valueOf(objJson.getString("pay"));
                        String paymentdate = String.valueOf(objJson.getString("paymentdate"));
                        String IsPaid = String.valueOf(objJson.getString("IsPaid"));
                        String MCI_No = String.valueOf(objJson.getString("MCI_No"));
                        String panno = String.valueOf(objJson.getString("panno"));
                        String Dr_serviceno = String.valueOf(objJson.getString("Dr_serviceno"));
                        String promotion = String.valueOf(objJson.getString("promotion"));
                        String editdate = String.valueOf(objJson.getString("editdate"));
                        String MAC = String.valueOf(objJson.getString("MAC"));
                        String AppLicenseCode = String.valueOf(objJson.getString("AppLicenseCode"));
                        String OnlineFee = String.valueOf(objJson.getString("OnlineFee"));
                        String OnlineFeeOthers = String.valueOf(objJson.getString("OnlineFeeOthers"));
                        String Isprfupdate = String.valueOf(objJson.getString("Isprfupdate"));
                        String HID = String.valueOf(objJson.getString("HID"));
                        String InsertedDt = String.valueOf(objJson.getString("InsertedDt"));
                        String Insertedby = String.valueOf(objJson.getString("Insertedby"));


                        Bitmap theBitmap = null;
                        try {
                            theBitmap = Glide.
                                    with(ctx).asBitmap().
                                    load(photo).
                                    into(150, 150). // Width and height
                                    get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ContentValues cv = new ContentValues();

                        //cv.put("id",);
                        cv.put("Docid", Docid);
                        cv.put("prefix", "");
                        cv.put("drname", name);
                        cv.put("name", name);
                        cv.put("cliname", clinicname);
                        cv.put("age", age);
                        cv.put("gender", gender);
                        cv.put("DOB", DOB);
                        cv.put("PAN", panno);
                        cv.put("MCI", MCI_No);
                        cv.put("RegId", RegId);
                        cv.put("ClinicTaxNo", TaxNo);
                        cv.put("TaxNo", TaxNo);
                        cv.put("Academic", Academic_text);
                        cv.put("Specialization", Specialization_text);
                        cv.put("Country", Country);
                        cv.put("State", State);
                        cv.put("District", District);
                        cv.put("Address", Address);
                        cv.put("Address2", Address);
                        cv.put("pincode", pincode);
                        cv.put("Lanline", Lanline);
                        cv.put("Mobile", Mobile);
                        cv.put("Homephone", homephone);
                        cv.put("Actdate", Actdate);
                        cv.put("Imei", Imei);
                        cv.put("docimage", BaseConfig.saveURLImagetoSDcard(theBitmap,
                                Docid.replace("/", "")));      //
                        cv.put("IsActive", "1");
                        cv.put("emailid", mail);


                        try {

                            Bitmap theBitmap1 = BaseConfig.Glide_GetBitmap(ImportWebservices_NODEJS.ctx, docsign);

                            docsign = BaseConfig.saveURLImagetoSDcard(theBitmap1, Docid.replace("/", "") + "_sign");

                        } catch (Exception e) {

                        }

                        cv.put("docsign", docsign);      //docsign


                        cv.put("IsRegistered", "1");
                        cv.put("IsSmsSent", Issms);
                        cv.put("IsEmailSent", Ismail);
                        cv.put("Paymentpaid", pay);
                        cv.put("Isupdate", "1");
                        cv.put("IsEdited", Isupdate);
                        cv.put("EditedDT", Isupdate);
                        cv.put("promotion", promotion);
                        cv.put("MAC", MAC);
                        cv.put("AppLicenseCode", AppLicenseCode);
                        cv.put("OnlineFee", OnlineFee);
                        cv.put("OnlineFeeOthers", OnlineFeeOthers);
                        cv.put("device_deactivate", IsActive);
                        cv.put("device_status", IsActive);
                        cv.put("hospital_id", HID);
                        cv.put("dept", "");
                        cv.put("HID", HID);

                        // BaseConfig.GetDb().insert("Drreg", null , cv);

                        boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Drreg where Docid='" + Docid + '\'');

                        if (!GetStatus) {
                            db.insert("Drreg", null, cv);
                        } else {
                            db.update("Drreg", cv, "Docid='" + Docid + '\'', null);
                        }

                        String Update_Query1 = "update users set doctorname='"
                                + name + '\'';
                        BaseConfig.SaveData(Update_Query1);

                        String Update_Query2 = "update drsettings set drname='"
                                + name + '\'';
                        BaseConfig.SaveData(Update_Query2);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportReportGallery() {
        try {

            String DoctorID = GetDocid();

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);


            String MethodName = "infoBindUpload";
            try {
                String resultsRequestSOAP = getDoctorIdRESTCALL(DoctorID, MethodName);

                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {

                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objJson = jsonArray.getJSONObject(i);
                        String returnid = String.valueOf(objJson.getString("id"));

                        ContentValues values;

                        String PatientPhoto = null;
                        try {
                            Log.e("Report Image URL: ", objJson.getString("Upload"));
                            Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ImportWebservices_NODEJS.ctx, String.valueOf(objJson.getString("Upload")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("Upload"))).asBitmap().into(-1, -1).get();
                            PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, String.valueOf(objJson.getString("Patid")).replace("/", ""));

                        } catch (Exception e) {

                        }


                        values = new ContentValues();
                        values.put("Docid", String.valueOf(objJson.getString("Docid")));
                        values.put("Patid", String.valueOf(objJson.getString("Patid")));
                        values.put("Name", "");
                        values.put("agegender", "");
                        values.put("Diagnosisid", "");
                        values.put("dt", "");
                        values.put("Remarks", "");
                        values.put("IsUpdate", 1);
                        values.put("patientphoto", "");
                        values.put("ReportPhoto", PatientPhoto);
                        values.put("ReportType", String.valueOf(objJson.getString("Fname")));
                        values.put("ServerId", returnid);
                        // db.insert(BaseConfig.TABLE_REPORTGALLERY, null, values);

                        String Query = "select Docid as dstatus1 from ReportGallery where ServerId='" + returnid + '\'';
                        boolean q = BaseConfig.LoadReportsBooleanStatus(Query);

                        if (!q) {
                            db.insert(BaseConfig.TABLE_REPORTGALLERY, null, values);
                        } else {
                            db.update(BaseConfig.TABLE_REPORTGALLERY, values, "ServerId='" + returnid + '\'', null);

                        }
                    }


                }

                db.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportPatientInfo() {

        try {


            String DoctorID = GetDocid();

            SQLiteDatabase db = BaseConfig.GetDb();
            try {

                String MethodName = "infopatupdate";
                String resultsRequestSOAP = getDoctorIdRESTCALL(DoctorID, MethodName);

                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {

                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objJson = jsonArray.getJSONObject(i);

                        String Update_Query = "update patreg set name='"
                                + String.valueOf(objJson.getString("name")) + "',age='"
                                + String.valueOf(objJson.getString("age"))
                                + "',gender='"
                                + String.valueOf(objJson.getString("gender")) + "',DOB='"
                                + String.valueOf(objJson.getString("dob"))
                                + "',Country='"
                                + String.valueOf(objJson.getString("country"))
                                + "',Address='"
                                + String.valueOf(objJson.getString("address"))
                                + "',pincode='"
                                + String.valueOf(objJson.getString("pincode")) + "',PC='"
                                + String.valueOf(objJson.getString("pc"))
                                + "',city='"
                                + String.valueOf(objJson.getString("city"))
                                + "',phone='"
                                + String.valueOf(objJson.getString("phone"))
                                + "',altphone='"
                                + String.valueOf(objJson.getString("altphone"))
                                + "',Address1='"
                                + String.valueOf(objJson.getString("address1"))
                                + "',email='"
                                + String.valueOf(objJson.getString("email"))
                                + "' where Patid='"
                                + String.valueOf(objJson.getString("Patid")) + "';";

                        BaseConfig.SaveData(Update_Query);


                        String Update_Vaccination = "update  Vaccination set pname='"
                                + String.valueOf(objJson.getString("name"))
                                + "',agegen='"
                                + String.valueOf(objJson.getString("age"))
                                + '-'
                                + String.valueOf(objJson.getString("gender"))
                                + "',mobnum='"
                                + String.valueOf(objJson.getString("phone"))
                                + "' where patid='"
                                + String.valueOf(objJson.getString("Patid")) + "';";
                        BaseConfig.SaveData(Update_Vaccination);

                    }

                    db.close();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportAppointment() {

        String DoctorID = GetDocid();

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        try {

            ContentValues values;

            String MethodName = "infobookappoinment";
            String resultsRequestSOAP = getDoctorIdRESTCALL(DoctorID, MethodName);

            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {

                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objJson = jsonArray.getJSONObject(i);
                    values = new ContentValues();
                    values.put("ServerId", String.valueOf(objJson.getString("Id")));
                    values.put("patid", String.valueOf(objJson.getString("PatId")));
                    values.put("name", String.valueOf(objJson.getString("Patname")));
                    values.put("mobnum", String.valueOf(objJson.getString("Phone")));
                    values.put("agegen", String.valueOf(objJson.getString("Agegen")));
                    values.put("docid", String.valueOf(objJson.getString("DocId")));
                    values.put("Actdt", String.valueOf(objJson.getString("Bookingdate")));
                    values.put("Appoimentdt", String.valueOf(objJson.getString("Appdate")));
                    values.put("token", String.valueOf(objJson.getString("Tokenno")));
                    values.put("Isupdate", 1);

                    Bitmap theBitmap = null;
                    try {
                        theBitmap = BaseConfig.Glide_GetBitmap(ImportWebservices_NODEJS.ctx, String.valueOf(objJson.getString("Patphoto")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("Patphoto"))).asBitmap().into(-1, -1).get();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, String.valueOf(objJson.getString("PatId")).replace("/", ""));//+System.currentTimeMillis());

                    values.put("photo", PatientPhoto);
                    values.put("Iscancel", "False");

                    String Query = "select Id as dstatus1 from Appoinment where ServerId='" + String.valueOf(objJson.getString("Id")) + "' " +
                            "and patid='" + String.valueOf(objJson.getString("PatId")) + "' " +
                            "and Appoimentdt='" + String.valueOf(objJson.getString("Appdate")) + '\'';

                    boolean q = BaseConfig.LoadReportsBooleanStatus(Query);

                    if (!q) {
                        db.insert(BaseConfig.TABLE_APPOINTMENTS, null, values);
                    } else {
                        db.update(BaseConfig.TABLE_APPOINTMENTS, values, "patid='" + String.valueOf(objJson.getString("PatId")) + "' and Appoimentdt='" + String.valueOf(objJson.getString("Appdate")) + '\'', null);

                    }


                }

            }
            db.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }
    }

    private void ImportOnlineConsultation() {

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;

        int flag;
        try {

            String MethodName = "GetOnlinesharePatdetails";
            String resultsRequestSOAP = getDoctorIdRESTCALL(BaseConfig.doctorId, MethodName);

            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                flag = 1;
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject objJson = jsonArray.getJSONObject(i);

                    sndserverid = String.valueOf(objJson.getString("Id"));
                    patient_Id = String.valueOf(objJson.getString("Patid"));
                    MedId = String.valueOf(objJson.getString("medid"));


                    Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ImportWebservices_NODEJS.ctx, String.valueOf(objJson.getString("PC")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("PC"))).asBitmap().into(-1, -1).get();
                    String PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, String.valueOf(objJson.getString("Patid")).replace("/", ""));//+System.currentTimeMillis());


                    values1 = new ContentValues();
                    values1.put("pid", String.valueOf(objJson.getString("Patid")));
                    values1.put("age", String.valueOf(objJson.getString("age")));
                    values1.put("gender", String.valueOf(objJson.getString("gender")));
                    values1.put("pname", String.valueOf(objJson.getString("name")));
                    values1.put("pphoto", PatientPhoto);
                    values1.put("docid", String.valueOf(objJson.getString("docid")));
                    values1.put("healthsummary", String.valueOf(objJson.getString("ptsummary")));
                    values1.put("ServerId", String.valueOf(objJson.getString("Id")));
                    values1.put("Actdt", String.valueOf(objJson.getString("dates")));
                    values1.put("IsActive", "True");
                    values1.put("Medid", String.valueOf(objJson.getString("medid")));
                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from OnlineConsultancy where ServerId='" + String.valueOf(objJson.getString("Id")) + '\'');

                    if (!GetStatus) {
                        db.insert("OnlineConsultancy", null, values1);
                    } else {
                        db.update("OnlineConsultancy", values1, "ServerId='" + String.valueOf(objJson.getString("Id")) + "' and IsUpdate!='0' and IsActive!='False'", null);
                    }


                }
            } else {
                flag = 0;
            }


            //sending serverid
            if (1 == flag) {

                //New Method is UPdated
                String IsUpdateMax = sndserverid;
               // String updateOnlinesharePatdetails = "UpdateOnlinesharePatdetails";
               // this.postIsUpdateMaxRESTMethod(IsUpdateMax, updateOnlinesharePatdetails);


                try {


                    String updateOnlinesharePatdetails = "GetOnlineshareMprescribed";
                    String PatId = patient_Id;
                    String MtestId = MedId;
                    resultsRequestSOAP = postPatidMtestId(PatId, MtestId, updateOnlinesharePatdetails);


                    if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                        JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                        JSONObject objJson;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);
                            String ServerId = objJson.getString("Id");

                            values1 = new ContentValues();


                            values1.put("pid", String.valueOf(objJson.getString("ptid")));
                            values1.put("pname", String.valueOf(objJson.getString("pname")));
                            values1.put("status", String.valueOf(objJson.getString("Medid")));
                            values1.put("TreatmentFor", String.valueOf(objJson.getString("treatmentfor")));
                            values1.put("Medicinename", String.valueOf(objJson.getString("medicinename")));
                            values1.put("Actdt", String.valueOf(objJson.getString("Actdate")));
                            values1.put("pphoto", sndserverid);

                            boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from OnlineSharedMedicine where pphoto='" + String.valueOf(objJson.getString("Id")) +"'");

                            if (GetStatus) {

                                //Update Details
                                db.update("OnlineSharedMedicine", values1,"pphoto='"+ServerId+"'",null);

                            }else
                            {
                                db.insert("OnlineSharedMedicine", null, values1);
                            }
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }


                //inserting report from server

                String getOnlineshareReports = "GetOnlineshareReports";
                String DoctorID = patient_Id + "##" + BaseConfig.doctorId + "##" + sndserverid;
                String doctorIdRESTCALL = getDoctorIdRESTCALL(DoctorID, getOnlineshareReports);

                if (!"[]".equalsIgnoreCase(doctorIdRESTCALL) && !"".equalsIgnoreCase(doctorIdRESTCALL)) {

                    JSONArray jsonArray = new JSONArray(doctorIdRESTCALL);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objJson = jsonArray.getJSONObject(i);

                        values1 = new ContentValues();

                        Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ImportWebservices_NODEJS.ctx, String.valueOf(objJson.getString("Upload")));//Glide.with(this.ctx).load(objJson.getString("Upload")).asBitmap().into(-1, -1).get();
                        String ReportPhoto = BaseConfig.saveURLImagetoSDcard1(theBitmap, UUID.randomUUID() + objJson.getString("ptid").replace("/", ""));//+System.currentTimeMillis();

                        values1.put("ServerId", objJson.getString("shid"));
                        values1.put("pid", objJson.getString("ptid"));
                        values1.put("docid", objJson.getString("docid"));
                        values1.put("Actdt", objJson.getString("dates"));
                        values1.put("ReportGallery", ReportPhoto);
                        values1.put("FileName", objJson.getString("Fname"));
                        values1.put("FileType", objJson.getString("Filetype"));

                        boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from OnlineConsultancyDtls where ServerId='" + String.valueOf(objJson.getString("shid")) +"'");

                        if (GetStatus) {

                            //Update Details
                            db.update("OnlineConsultancyDtls",  values1,"ServerId='" + String.valueOf(objJson.getString("shid")) +"'",null);

                        }else
                        {
                            db.insert("OnlineConsultancyDtls", null, values1);
                        }
                    }
                }


            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void ImportScanMaster() {

        String IsActive;
        String str = "";

        String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from scan";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        Cursor c = db.rawQuery(Query, null);

        if (null != c) {
            if (c.moveToFirst()) {
                do {

                    if (null != c.getString(c.getColumnIndex("IsUpdateMax"))) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }

        String IsUpdateMax = str;
        String MethodName = "ImportMstrScan";


        ContentValues values1;

        try {


            String resultsRequestSOAP = postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);
                    ///{ "Id": "1", "ScanFor": "Head", "IsActive": "True", "IsUpdate": "1" }
                    String ServerId = String.valueOf(objJson.getString("Id"));
                    String ScanFor = String.valueOf(objJson.getString("Scan"));
                    String IsUpdate = String.valueOf(objJson.getString("IsUpdate"));

                    String e = objJson.getString("IsActive");

                    if ("true".equalsIgnoreCase(e)) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("scantype", ScanFor);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from scan where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("scan", null, values1);
                    } else {
                        db.update("scan", values1, "ServerId='" + ServerId + '\'', null);
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportDoctorsList() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_DoctorsList";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    if (c.getString(c.getColumnIndex("IsUpdateMax")) != null) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }


        ContentValues values1;

        String IsUpdateMax = str;
        String MethodName = "ImportMstrDoctorsList";

        try {


            String resultsRequestSOAP = postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);

                    //Id,Docid,name,Academic_text,Specialization_text,Country,[State],District,Address,pincode,Mobile,clinicname,mail,HID
                    String ServerId = String.valueOf(objJson.getString("Id"));
                    String Docid = String.valueOf(objJson.getString("Docid"));
                    String name = String.valueOf(objJson.getString("name"));
                    String Academic_text = String.valueOf(objJson.getString("Academic_text"));
                    String Specialization_text = String.valueOf(objJson.getString("Specialization_text"));
                    String Country = String.valueOf(objJson.getString("Country"));
                    String State = String.valueOf(objJson.getString("State"));
                    String District = String.valueOf(objJson.getString("District"));
                    String Address = String.valueOf(objJson.getString("Address"));
                    String pincode = String.valueOf(objJson.getString("pincode"));
                    String Mobile = String.valueOf(objJson.getString("Mobile"));
                    String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String mail = String.valueOf(objJson.getString("mail"));
                    String HID = String.valueOf(objJson.getString("HID"));

                    String IsActive;

                    String isActive = objJson.getString("IsActive");

                    if ("true".equalsIgnoreCase(isActive)) {
                        isActive = "1";
                    } else {
                        isActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Docid", Docid);
                    values1.put("name", name);
                    values1.put("Academic_text", Academic_text);
                    values1.put("Specialization_text", Specialization_text);
                    values1.put("Country", Country);
                    values1.put("State", State);
                    values1.put("District", District);
                    values1.put("Address", Address);
                    values1.put("pincode", pincode);
                    values1.put("Mobile", Mobile);
                    values1.put("clinicname", clinicname);
                    values1.put("mail", mail);
                    values1.put("HID", HID);
                    values1.put("IsActive", isActive);
                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_DoctorsList where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("Mstr_DoctorsList", null, values1);
                    } else {
                        db.update("Mstr_DoctorsList", values1, "ServerId='" + ServerId + '\'', null);
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportTest() {


        String IsActive;
        String str = "";

        String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from testname";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    if (c.getString(c.getColumnIndex("IsUpdateMax")) != null) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }


        ContentValues values1;

        String IsUpdateMax = str;
        String MethodName = "ImportMstrTest";

        try {


            String resultsRequestSOAP = postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);
                    ///{ "IsActive": "True", "IsUpdate": "1", "SubTest": "HiB 3D", "Id": "1", "TestName": "HiB", "TestFor": "Hand" }
                    String ServerId = String.valueOf(objJson.getString("Id"));
                    String SubTest = String.valueOf(objJson.getString("SubTest"));
                    //String TestFor = String.valueOf(objJson.getString("TestFor"));
                    String testName = String.valueOf(objJson.getString("TestName"));
                    String IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    String e = objJson.getString("IsActive");
                    String Unit = objJson.getString("Unit");
                    String NormalRange = objJson.getString("NormalRange");
                    String TemplateName = objJson.getString("TemplateName");

                    if ("true".equalsIgnoreCase(e)) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("SubTest", SubTest);
                    /// values1.put("TestFor", TestFor);
                    values1.put("testName", testName);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);
                    values1.put("Unit", Unit);
                    values1.put("NormalRange", NormalRange);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Testname where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("Testname", null, values1);
                    } else {
                        db.update("Testname", values1, "ServerId='" + ServerId + '\'', null);
                    }

                    // TODO: 8/23/2017  Insert Fav Subtest names in MyFavTable
                    String IsFav = objJson.getString("IsFav");


                    if ("True".equalsIgnoreCase(IsFav) || "1".equalsIgnoreCase(IsFav)) {
                        //Insert My Fav Table Details
                        ContentValues values22 = new ContentValues();
                        values22.put("Testname", testName);
                        values22.put("SubTest", SubTest);
                        values22.put("IsUpdate", IsUpdate);
                        values22.put("TemplateName", TemplateName);
                        db.insert("MyFavTest", null, values22);

                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportPharmacyDtls() {
        try {

            StringBuilder str = new StringBuilder();
            String Query = "select IFNULL(max(ServerId),'0') as ServerId from Pharmacy";

            SQLiteDatabase db = BaseConfig.GetDb();
            int upid = 0;
            // database = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        if (c.getString(c.getColumnIndex("ServerId")) != null) {

                            str.append(c.getString(c.getColumnIndex("ServerId")));

                        } else {
                            str.append('0');

                        }

                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }
            if (!"".equals(str.toString())) {

                try {

                    String IsUpdateMax = str.toString();
                    String MethodName = "pharmachyname_select";

                    String resultsRequestSOAP = postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


                    if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                        JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                        JSONObject objJson;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);

                            ContentValues values;
                            values = new ContentValues();
                            values.put("ServerId", String.valueOf(objJson.getString("Id")));
                            values.put("pharmacyname", String.valueOf(objJson.getString("pharmacyname")));
                            values.put("pharmaddr", String.valueOf(objJson.getString("address")));
                            values.put("contactnum", String.valueOf(objJson.getString("contactnum")));
                            db.insert("Pharmacy", null, values);

                        }

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (SchemaException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportDiagnosticCentreDtls() {
        try {

            StringBuilder str = new StringBuilder();
            String Query = "select IFNULL(max(ServerId),'0') as ServerId from Laboratory";

            SQLiteDatabase db = BaseConfig.GetDb();
            int upid = 0;
            // database = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        // upid=Integer.parseInt(c.getString(c.getColumnIndex("id")));
                        if (c.getString(c.getColumnIndex("ServerId")) != null) {
                            str.append(c.getString(c.getColumnIndex("ServerId")));
                        } else {
                            str.append('0');
                        }
                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }
            if (!"".equals(str.toString())) {

                try {

                    String IsUpdateMax = str.toString();
                    String MethodName = "labname_select";

                    String resultsRequestSOAP = postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


                    if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                        JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                        JSONObject objJson;
                        ContentValues values;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);

                            //Id,labname,contactnum,address
                            values = new ContentValues();
                            values.put("ServerId", objJson.getString("Id"));
                            values.put("labname", objJson.getString("labname"));
                            values.put("labaddr", objJson.getString("address"));
                            values.put("contactnum", objJson.getString("contactnum"));
                            db.insert("Laboratory", null, values);

                        }

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (SchemaException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportOperationSchedule() {

	/*	Id
                ServerId
		SessionName
				IsActive
		IsUpdate*/


        String str = "";

        String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from operation_details";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    if (c.getString(c.getColumnIndex("IsUpdateMax")) != null) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }

        ContentValues values1;

        try {


            MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
            PostIsUpdateMaxDocIdFactory postDoctorIdFactory = new PostIsUpdateMaxDocIdFactory(magnetMobileClient);
            PostIsUpdateMaxDocId postDoctorId = postDoctorIdFactory.obtainInstance();

            //Set Body Data
            PostIsUpdateMaxDocIdRequest body = new PostIsUpdateMaxDocIdRequest();
            body.setDocId(BaseConfig.doctorId);
            body.setIsUpdateMax(str);

            Call<IsUpdateMaxDocIdResult> resultCall = postDoctorId.postIsUpdateMaxDocId(body, null);

            String resultsRequestSOAP = resultCall.get().getResults();


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    //{ "FoodItems": [ { "Expr1": 1, "Docid": "23", "operationId": 1, "OperationNo": "234", "OperationName": "Appendicitis", "patid": "PID/2014/1511/13353", "PatientName": "Kiran", "Gender": "Male", "ScheduleDate": "2016-09-19T19:15:23.557", "Fromtime": null, "Totime": null, "Rate": null, "IsActive": true, "curdate": "2016-09-19T19:15:23.557", "IsUpdate": 1, "Status": "Completed" } ] }
                    objJson = jsonArray.getJSONObject(i);

                    String ServerId = String.valueOf(objJson.getString("operationId"));
                    String DocList = String.valueOf(objJson.getString("Doctors"));
                    String DocId = String.valueOf(objJson.getString("docref"));
                    String OperationNo = objJson.getString("OperationNo");
                    String OperationName = objJson.getString("OperationName");
                    String patid = objJson.getString("patid");
                    String PatientName = objJson.getString("PatientName");
                    String ScheduleDate = objJson.getString("ScheduleDate");
                    String Fromtime = objJson.getString("Fromtime");
                    String rate = objJson.getString("Rate");
                    String Totime = objJson.getString("Totime");
                    String Status = objJson.getString("Status");
                    String IsActive = objJson.getString("IsActive");
                    String IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("operation_name", OperationName);
                    values1.put("operation_no", OperationNo);
                    values1.put("patient_name", patid);
                    values1.put("operation_date", ScheduleDate);
                    values1.put("from_time", Fromtime);
                    values1.put("to_time", Totime);
                    values1.put("doctors", DocList);
                    values1.put("DocId", DocId);
                    values1.put("Rate", rate);
                    values1.put("Status", Status);
                    values1.put("patient_name", PatientName);



                    /*if already serverid irunthal update
                        else
                    serverid ilana insert*/

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from operation_details where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("operation_details", null, values1);
                    } else {
                        db.update("operation_details", values1, "ServerId='" + ServerId + '\'', null);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportBind_ClinicalInformation() throws JSONException {


        String IsActive;
        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();

        Cursor c = db.rawQuery(Query, null);


        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }


        ContentValues values1;

        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_ClinicalInformation";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("Id"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Previous_MedicalHistory = String.valueOf(objJson.getString("Previous_MedicalHistory"));
                    String Hereditary = String.valueOf(objJson.getString("Hereditary"));
                    String AllergicTo = String.valueOf(objJson.getString("AllergicTo"));
                    String Perhis_Smoking = String.valueOf(objJson.getString("Perhis_Smoking"));
                    String Perhis_Alcohol = String.valueOf(objJson.getString("Perhis_Alcohol"));
                    String Perhis_Tobacco = String.valueOf(objJson.getString("Perhis_Tobacco"));
                    String Perhis_Others = String.valueOf(objJson.getString("Perhis_Others"));
                    String Medication_History = String.valueOf(objJson.getString("Medication_History"));
                    String Family_Med_History = String.valueOf(objJson.getString("Family_Med_History"));
                    String Actdate = String.valueOf(objJson.getString("Actdate"));
                    try {

                        Actdate = BaseConfig.DateFormatter3(Actdate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String Upid = String.valueOf(objJson.getString("Upid"));
                    String Perhis_BowelHabits = String.valueOf(objJson.getString("Perhis_BowelHabits"));
                    String Perhis_Micturition = String.valueOf(objJson.getString("Perhis_Micturition"));
                    String present_illness = String.valueOf(objJson.getString("present_illness"));
                    String past_illness = String.valueOf(objJson.getString("past_illness"));
                    String any_medication = String.valueOf(objJson.getString("any_medication"));
                    String obstetric = String.valueOf(objJson.getString("obstetric"));
                    String gynaec = String.valueOf(objJson.getString("gynaec"));
                    String surgical_notes = String.valueOf(objJson.getString("surgical_notes"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    values1.put("ServerId", Id);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Previous_MedicalHistory", Previous_MedicalHistory);
                    values1.put("Hereditary", Hereditary);
                    values1.put("AllergicTo", AllergicTo);
                    values1.put("Perhis_Smoking", Perhis_Smoking);
                    values1.put("Perhis_Alcohol", Perhis_Alcohol);
                    values1.put("Perhis_Tobacco", Perhis_Tobacco);
                    values1.put("Perhis_Others", Perhis_Others);
                    values1.put("Medication_History", Medication_History);
                    values1.put("Family_Med_History", Family_Med_History);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);

                    values1.put("Perhis_BowelHabits", Perhis_BowelHabits);
                    values1.put("Perhis_Micturition", Perhis_Micturition);
                    values1.put("present_illness", present_illness);
                    values1.put("past_illness", past_illness);
                    values1.put("any_medication", any_medication);
                    values1.put("obstetric", obstetric);
                    values1.put("gynaec", gynaec);
                    values1.put("surgical_notes", surgical_notes);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);

                    values1.put("ServerId", Id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from ClinicalInformation where ServerId=" + Id);

                    if (GetStatus) {
                        db.update("ClinicalInformation", values1, "ServerId=" + Id, null);

                    } else {
                        db.insert("ClinicalInformation", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportBind_MedicalTestDtls() throws JSONException {


        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();
        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_MedicalTestDtls";

        ContentValues values1;
        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("id"));
                    String Ptid = String.valueOf(objJson.getString("Ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docname = String.valueOf(objJson.getString("docname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String alltest = String.valueOf(objJson.getString("alltest"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String diagid = String.valueOf(objJson.getString("diagid"));
                    String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    String dsign = String.valueOf(objJson.getString("dsign"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String imei = String.valueOf(objJson.getString("imei"));
                    String testname = String.valueOf(objJson.getString("testname"));
                    String subtestname = String.valueOf(objJson.getString("subtestname"));
                    String testvalue = String.valueOf(objJson.getString("testvalue"));
                    String testsummary = String.valueOf(objJson.getString("testsummary"));
                    String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    String testreportimg = String.valueOf(objJson.getString("testreportimg"));
                    String scanname = String.valueOf(objJson.getString("scanname"));
                    String subscanname = String.valueOf(objJson.getString("subscanname"));
                    String scanvalue = String.valueOf(objJson.getString("scanvalue"));
                    String scansummary = String.valueOf(objJson.getString("scansummary"));
                    String scanreportimg = String.valueOf(objJson.getString("scanreportimg"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String WUP = String.valueOf(objJson.getString("WUP"));
                    String subtestid = String.valueOf(objJson.getString("subtestid"));
                    String bps = String.valueOf(objJson.getString("bps"));
                    String bpd = String.valueOf(objJson.getString("bpd"));
                    String temperature = String.valueOf(objJson.getString("temperature"));
                    String weight = String.valueOf(objJson.getString("weight"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsPaid = String.valueOf(objJson.getString("IsPaid"));


                    String ResultValue = "0";
                    if ("0".equalsIgnoreCase(String.valueOf(objJson.getString("result")))) {
                        ResultValue = "Nil";
                    } else if ("1".equalsIgnoreCase(String.valueOf(objJson.getString("result")))) {
                        ResultValue = "Positive";

                    } else if ("2".equalsIgnoreCase(String.valueOf(objJson.getString("result")))) {
                        ResultValue = "Negative";
                    }

                    values1 = new ContentValues();

                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("alltest", alltest);
                    values1.put("mtestid", mtestid);
                    values1.put("testname", testname);
                    values1.put("subtestname", subtestname);
                    values1.put("testvalue", testvalue);
                    values1.put("testsummary", testsummary);
                    values1.put("treatmentfor", treatmentfor);
                    values1.put("testreportimg", testreportimg);
                    values1.put("subtestid", subtestid);
                    values1.put("bps", bps);
                    values1.put("bpd", bpd);
                    values1.put("temperature", temperature);
                    values1.put("weight", weight);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("HID", HID);
                    values1.put("Isupdate", "1");
                    values1.put("ServerId", Id);
                    values1.put("result", ResultValue);


                    // TODO: 3/28/2017 Adding New Column in IsNew
                    values1.put("IsNew", 1);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Medicaltestdtls where ServerId=" + Id);

                    if (GetStatus) {

                        if (BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Medicaltestdtls where ServerId=" + Id + " and seen='1'")) {


                            values1.put("ReadytoUpdate", 2);
                            values1.put("seen", "1");
                        } else {
                            if (!"".equals(testvalue) && !"".equalsIgnoreCase(testvalue) && !"null".equalsIgnoreCase(testvalue)) {
                                values1.put("ReadytoUpdate", 2);
                                values1.put("seen", "0");
                            } else {
                                values1.put("ReadytoUpdate", 0);
                            }

                        }


                        db.update("Medicaltestdtls", values1, "ServerId=" + Id + ' ', null);

                    } else {

                        if (!"".equals(testvalue) && !"".equalsIgnoreCase(testvalue) && !"null".equalsIgnoreCase(testvalue)) {
                            values1.put("ReadytoUpdate", 2);
                            values1.put("seen", "0");
                        } else {
                            values1.put("ReadytoUpdate", 0);
                        }

                        db.insert("Medicaltestdtls", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportBind_MedicalTest() throws JSONException {


        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();

        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_MedicalTest";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("id"));
                    String Ptid = String.valueOf(objJson.getString("Ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docname = String.valueOf(objJson.getString("docname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String diagid = String.valueOf(objJson.getString("diagid"));
                    String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    String dsign = String.valueOf(objJson.getString("dsign"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String imei = String.valueOf(objJson.getString("imei"));
                    String testname = String.valueOf(objJson.getString("testname"));
                    String subtestname = String.valueOf(objJson.getString("subtestname"));
                    String testvalue = String.valueOf(objJson.getString("testvalue"));
                    String testsummary = String.valueOf(objJson.getString("testsummar" +
                            'y'));
                    // String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    String testreportimg = String.valueOf(objJson.getString("testreportimg"));
                    String scanname = String.valueOf(objJson.getString("scanname"));
                    String subscanname = String.valueOf(objJson.getString("subscanname"));
                    String scanvalue = String.valueOf(objJson.getString("scanvalue"));
                    String scansummary = String.valueOf(objJson.getString("scansummary"));
                    String scanreportimg = String.valueOf(objJson.getString("scanreportimg"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String Dig_Center = String.valueOf(objJson.getString("Dig_Center"));
                    String HID = String.valueOf(objJson.getString("HID"));

                    String Diagnosis = String.valueOf(objJson.getString("Diagnosis"));
                    String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));


                    values1 = new ContentValues();
                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("pmobnum", pmobnum);
                    values1.put("dsign", dsign);
                    values1.put("mtestid", mtestid);
                    values1.put("imei", imei);
                    values1.put("treatmentfor", treatmentfor);
                    values1.put("Diagnosis", Diagnosis);
                    values1.put("HID", HID);
                    values1.put("Isupdate", "1");


                              /*  if (!testvalue.equals("") && !testvalue.equalsIgnoreCase("") && !testvalue.equalsIgnoreCase("null")) {
                                    values1.put("ReadytoUpdate", 2);
                                } else {
                                    values1.put("ReadytoUpdate", 0);
                                }*/

                    values1.put("ServerId", Id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Medicaltest where (ServerId=" + Id + " or ServerId!=null) and Ptid='" + Ptid + "' and mtestid='" + mtestid + '\'');

                    if (GetStatus) {
                        db.update("Medicaltest", values1, "ServerId=" + Id + ' ', null);

                    } else {
                        db.insert("Medicaltest", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportScanDtls() throws JSONException {


        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();

        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_ScanDetailsJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Ptid = String.valueOf(objJson.getString("Ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docname = String.valueOf(objJson.getString("docname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    String dsign = String.valueOf(objJson.getString("dsign"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String imei = String.valueOf(objJson.getString("imei"));
                    String scanname = String.valueOf(objJson.getString("scanname"));
                    String subscanname = String.valueOf(objJson.getString("subscanname"));
                    String scanvalue = String.valueOf(objJson.getString("scanvalue"));
                    String scansummary = String.valueOf(objJson.getString("scansummary"));
                    String scanreportimg = String.valueOf(objJson.getString("scanreportimg"));
                    //String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    //String ReadytoUpdate = String.valueOf(objJson.getString("ReadytoUpdate"));
                    //String ValueUpdated = String.valueOf(objJson.getString("ValueUpdated"));
                    String testid = String.valueOf(objJson.getString("testid"));
                    String ServerId = String.valueOf(objJson.getString("id"));

                    values1 = new ContentValues();
                    // values1.put("id", id);
                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("pmobnum", pmobnum);
                    values1.put("dsign", dsign);
                    values1.put("mtestid", mtestid);
                    values1.put("imei", imei);
                    values1.put("scanname", scanname);
                    values1.put("subscanname", subscanname);
                    values1.put("scanvalue", scanvalue);
                    values1.put("scansummary", scansummary);
                    values1.put("scanreportimg", scanreportimg);
                    values1.put("Isupdate", 1);

                    if (!scanvalue.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("testid", testid);
                    values1.put("ServerId", ServerId);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Scantest where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("Scantest", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("Scantest", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportXrayDtls() throws JSONException {


        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }


        ContentValues values1;

        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_XrayDetailsJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docname = String.valueOf(objJson.getString("docname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    String dsign = String.valueOf(objJson.getString("dsign"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String imei = String.valueOf(objJson.getString("imei"));

                    String xray = String.valueOf(objJson.getString("xray"));
                    String xrayvalue = String.valueOf(objJson.getString("xrayvalue"));
                    String xraysummary = String.valueOf(objJson.getString("xraysummary"));
                    String xrayimg = String.valueOf(objJson.getString("xrayimg"));
                    String xrayid = String.valueOf(objJson.getString("xrayid"));
                    String ServerId = String.valueOf(objJson.getString("Id"));

                    values1 = new ContentValues();

                    // values1.put("id", id);
                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("pmobnum", pmobnum);
                    values1.put("dsign", dsign);
                    values1.put("mtestid", mtestid);
                    values1.put("imei", imei);
                    values1.put("xray", xray);
                    values1.put("xrayvalue", xrayvalue);
                    values1.put("xraysummary", xraysummary);
                    values1.put("xrayimg", xrayimg);
                    values1.put("Isupdate", 1);
                    values1.put("xrayid", xrayid);
                    values1.put("ServerId", ServerId);

                    if (!xrayvalue.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1   from XRAY where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("XRAY", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("XRAY", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportEEGDtls() throws JSONException {


        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_EEGDetailsJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
//                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String ECGfor = String.valueOf(objJson.getString("ECGfor"));
                    String Comment = String.valueOf(objJson.getString("Comment"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String Summary = String.valueOf(objJson.getString("Summary"));
                    String Valuedate = String.valueOf(objJson.getString("Valuedate"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String ServerId = String.valueOf(objJson.getString("id"));

                    values1 = new ContentValues();


                    values1.put("mtestid", mtestid);
                    values1.put("ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //        values1.put("pagegen", pagegen);
                    values1.put("Isupdate", 1);
                    values1.put("EEGFor", ECGfor);
                    values1.put("Comment", Comment);
                    values1.put("Summary", Summary);
                    values1.put("Valuedata", Valuedate);
                    values1.put("HID", HID);
                    values1.put("ServerId", ServerId);


                    if (!Summary.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from EEG where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("EEG", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("EEG", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportECGDtls() throws JSONException {


        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_ECGDetailsJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String ServerId = String.valueOf(objJson.getString("id"));
                    String Docid = String.valueOf(objJson.getString("Docid"));
                    String Ptid = String.valueOf(objJson.getString("Ptid"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String gender = String.valueOf(objJson.getString("gender"));
                    String age = String.valueOf(objJson.getString("age"));
                    String mobnum = String.valueOf(objJson.getString("mobnum"));
                    String ECGTestFor = String.valueOf(objJson.getString("ECGTestFor"));
                    String Comment = String.valueOf(objJson.getString("Comment"));
                    String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String PRate = String.valueOf(objJson.getString("PRate"));
                    String Ecg = String.valueOf(objJson.getString("Ecg"));
                    String Ecgfile = String.valueOf(objJson.getString("Ecgfile"));
                    String Scan = String.valueOf(objJson.getString("Scan"));
                    String Scanfile = String.valueOf(objJson.getString("Scanfile"));
                    String Treadmill = String.valueOf(objJson.getString("Treadmill"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    String bloodgroup = String.valueOf(objJson.getString("bloodgroup"));
                    String ecgrate = String.valueOf(objJson.getString("ecgrate"));
                    String ecgqrs = String.valueOf(objJson.getString("ecgqrs"));
                    String ecgst = String.valueOf(objJson.getString("ecgst"));
                    String ecgt = String.valueOf(objJson.getString("ecgt"));
                    String ecgrhyrhm = String.valueOf(objJson.getString("ecgrhyrhm"));
                    String ecgaxis = String.valueOf(objJson.getString("ecgaxis"));
                    String ecgpulse = String.valueOf(objJson.getString("ecgpulse"));
                    String imeino = String.valueOf(objJson.getString("imeino"));
                    String docsign = String.valueOf(objJson.getString("docsign"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Bundlebranchblock = String.valueOf(objJson.getString("Bundlebranchblock"));
                    String Conductiondefects = String.valueOf(objJson.getString("Conductiondefects"));
                    String PR = String.valueOf(objJson.getString("PR"));
                    String uid = String.valueOf(objJson.getString("uid"));
                    String WUP = String.valueOf(objJson.getString("WUP"));
                    String ecgText = String.valueOf(objJson.getString("ecgText"));
                    String ecgTreadmillText = String.valueOf(objJson.getString("ecgTreadmillText"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsPaid = String.valueOf(objJson.getString("IsPaid"));

                    values1 = new ContentValues();


                    // values1.put("id", id);
                    values1.put("Docid", Docid);
                    values1.put("Ptid", Ptid);
                    values1.put("mtestid", mtestid);
                    values1.put("pname", pname);
                    values1.put("gender", gender);
                    values1.put("age", age);
                    values1.put("mobnum", mobnum);
                    values1.put("ECGTestFor", ECGTestFor);
                    values1.put("Comment", Comment);
                    values1.put("clinicname", clinicname);
                    values1.put("PRate", PRate);
                    values1.put("Ecg", Ecg);
                    values1.put("Ecgfile", Ecgfile);
                    values1.put("Scan", Scan);
                    values1.put("Scanfile", Scanfile);
                    values1.put("Treadmill", Treadmill);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("treatmentfor", treatmentfor);
                    values1.put("bloodgroup", bloodgroup);
                    values1.put("ecgrate", ecgrate);
                    values1.put("ecgqrs", ecgqrs);
                    values1.put("ecgst", ecgst);
                    values1.put("ecgt", ecgt);
                    values1.put("ecgrhyrhm", ecgrhyrhm);
                    values1.put("ecgaxis", ecgaxis);
                    values1.put("ecgpulse", ecgpulse);
                    values1.put("imeino", imeino);
                    values1.put("docsign", docsign);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Bundlebranchblock", Bundlebranchblock);
                    values1.put("Conductiondefects", Conductiondefects);
                    values1.put("PR", PR);
                    // values1.put("ReadytoUpdate", ReadytoUpdate);
                    // values1.put("ValueUpdated", ValueUpdated);
                    values1.put("ecgText", ecgText);
                    values1.put("ecgTreadmillText", ecgTreadmillText);
                    values1.put("ServerId", ServerId);


                    if (!Treadmill.isEmpty() || !ecgrate.isEmpty() || !ecgqrs.isEmpty() || !ecgst.isEmpty() || !ecgt.isEmpty() || !ecgrhyrhm.isEmpty() || !ecgaxis.isEmpty() || !Bundlebranchblock.isEmpty() || !Conductiondefects.isEmpty() || !PR.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1   from ECGTEST where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("ECGTEST", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("ECGTEST", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportAngiogram() throws JSONException {


        String str = "";

        String Query = "select  Patid from  Patreg ";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        Cursor c = db.rawQuery(Query, null);
        JSONArray patientIdList = new JSONArray();
        JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        String MethodName = "ImportBind_AngiogramDetailsJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);

                    String ServerId = String.valueOf(objJson.getString("id"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String AngioFor = String.valueOf(objJson.getString("AngioFor"));
                    String Comment = String.valueOf(objJson.getString("Comment"));
                    String Coronary = String.valueOf(objJson.getString("Coronary"));
                    String Brain = String.valueOf(objJson.getString("Brain"));
                    String Upperlimb = String.valueOf(objJson.getString("Upperlimb"));
                    String Lowerlimb = String.valueOf(objJson.getString("Lowerlimb"));
                    String Mesenteric = String.valueOf(objJson.getString("Mesenteric"));
                    String uid = String.valueOf(objJson.getString("uid"));
                    String WUP = String.valueOf(objJson.getString("WUP"));
                    String CoronaryText = String.valueOf(objJson.getString("CoronaryText"));
                    String BrainText = String.valueOf(objJson.getString("BrainText"));
                    String UpperlimbText = String.valueOf(objJson.getString("UpperlimbText"));
                    String LowerlimbText = String.valueOf(objJson.getString("LowerlimbText"));
                    String MesentericText = String.valueOf(objJson.getString("MesentericText"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsPaid = String.valueOf(objJson.getString("IsPaid"));


                    values1 = new ContentValues();


                    values1.put("mtestid", mtestid);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("AngioFor", AngioFor);
                    values1.put("Comment", Comment);
                    values1.put("Coronary", Coronary);
                    values1.put("Brain", Brain);
                    values1.put("Upperlimb", Upperlimb);
                    values1.put("Lowerlimb", Lowerlimb);
                    values1.put("Mesenteric", Mesenteric);
                    // values1.put("ReadytoUpdate", ReadytoUpdate);
                    // values1.put("ValueUpdated", ValueUpdated);
                    values1.put("CoronaryText", CoronaryText);
                    values1.put("BrainText", BrainText);
                    values1.put("UpperlimbText", UpperlimbText);
                    values1.put("LowerlimbText", LowerlimbText);
                    values1.put("MesentericText", MesentericText);
                    values1.put("HID", HID);
                    values1.put("ServerId", ServerId);


                    if (!Brain.isEmpty() || !Upperlimb.isEmpty() || !Lowerlimb.isEmpty() || !Mesenteric.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1   from Angiogram where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("Angiogram", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("Angiogram", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportMprescribed() throws JSONException {


        String IsActive;
        String str = "";

        String Query = "select  Patid from  Patreg";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();//.replaceAll("\"","\\\"");
        String MethodName = "ImportMprescribedJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);
                    String id = String.valueOf(objJson.getString("id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String refdocname = String.valueOf(objJson.getString("refdocname"));
                    String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Medid = String.valueOf(objJson.getString("Medid"));
                    String Dose = String.valueOf(objJson.getString("Dose"));
                    String Freq = String.valueOf(objJson.getString("Freq"));
                    String Duration = String.valueOf(objJson.getString("Duration"));
                    String remarks = String.valueOf(objJson.getString("remarks"));
                    String dsign = String.valueOf(objJson.getString("dsign"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String medicinename = String.valueOf(objJson.getString("medicinename"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String diagnosis = String.valueOf(objJson.getString("diagnosis"));
                    String diagnosisdtls = String.valueOf(objJson.getString("diagnosisdtls"));
                    String nextvisit = String.valueOf(objJson.getString("nextvisit"));
                    String fee = String.valueOf(objJson.getString("fee"));
                    String mobnum = String.valueOf(objJson.getString("mobnum"));
                    String imei = String.valueOf(objJson.getString("imei"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String NVsms = String.valueOf(objJson.getString("NVsms"));
                    String MPsms = String.valueOf(objJson.getString("MPsms"));
                    String Diabeticdiet = String.valueOf(objJson.getString("Diabeticdiet"));
                    String Diabeticrenaldiet = String.valueOf(objJson.getString("Diabeticrenaldiet"));
                    String Lowcholesterol_Cardiacdiet = String.valueOf(objJson.getString("Lowcholesterol_Cardiacdiet"));
                    String Hypertensivediet = String.valueOf(objJson.getString("Hypertensivediet"));
                    String Diet_Warfarin = String.valueOf(objJson.getString("Diet_Warfarin"));
                    String prepharma = String.valueOf(objJson.getString("prepharma"));
                    String medicineId = String.valueOf(objJson.getString("medicineId"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsIssued = String.valueOf(objJson.getString("IsIssued"));

                    values1 = new ContentValues();
                    // values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("refdocname", refdocname);
                    values1.put("clinicname", clinicname);
                    values1.put("Medid", Medid);
                    values1.put("Dose", Dose);
                    values1.put("Freq", Freq);
                    values1.put("Duration", Duration);
                    values1.put("remarks", remarks);
                    values1.put("dsign", dsign);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("medicinename", medicinename);
                    values1.put("pagegen", pagegen);
                    values1.put("diagnosis", diagnosis);
                    values1.put("diagnosisdtls", diagnosisdtls);
                    values1.put("nextvisit", nextvisit);
                    values1.put("fee", fee);
                    values1.put("mobnum", mobnum);
                    values1.put("imei", imei);
                    //values1.put("Isupdate", Isupdate);
                    values1.put("treatmentfor", treatmentfor);
                    //values1.put("upid", upid);
                    //values1.put("NVsms", NVsms);
                    //values1.put("MPsms", MPsms);

                    values1.put("Diabeticdiet", Diabeticdiet);
                    values1.put("Diabeticrenaldiet", Diabeticrenaldiet);
                    values1.put("Lowcholesterol_Cardiacdiet", Lowcholesterol_Cardiacdiet);
                    values1.put("Hypertensivediet", Hypertensivediet);
                    values1.put("Diet_Warfarin", Diet_Warfarin);
                    values1.put("prefpharma", prepharma);
                    values1.put("medicineId", medicineId);
                    values1.put("HID", HID);
                    values1.put("ServerId", id);

                    // TODO: 3/4/2017   IsUpadateMax Value insert

                    values1.put("IsUpdateMax", Isupdate);


                    // values1.put("IsIssued", IsIssued);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mprescribed where ServerId=" + id);

                    if (GetStatus) {
                        db.update("Mprescribed", values1, "ServerId=" + id, null);

                    } else {
                        db.insert("Mprescribed", null, values1);
                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_Diagnosis() {
        try {

            String Query = "select  Patid from  Patreg";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);

            ContentValues values1;
            String PatientIdLIst = getPatientList();
            String MethodName = "ImportBind_DiagnosisJSON";

            try {


                String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {


                        objJson = jsonArray.getJSONObject(i);


                        String Id = String.valueOf(objJson.getString("id"));
                        String Ptid = String.valueOf(objJson.getString("Ptid"));
                        String DiagId = String.valueOf(objJson.getString("DiagId"));
                        String pname = String.valueOf(objJson.getString("pname"));
                        String gender = String.valueOf(objJson.getString("gender"));
                        String age = String.valueOf(objJson.getString("age"));
                        String mobnum = String.valueOf(objJson.getString("mobnum"));
                        String refdocname = String.valueOf(objJson.getString("refdocname"));
                        String clinicname = String.valueOf(objJson.getString("clinicname"));
                        String Diagnosis = String.valueOf(objJson.getString("Diagnosis"));
                        String Bp = String.valueOf(objJson.getString("Bp"));
                        String FBS = String.valueOf(objJson.getString("FBS"));
                        String PPS = String.valueOf(objJson.getString("PPS"));
                        String RBS = String.valueOf(objJson.getString("RBS"));
                        String PWeight = String.valueOf(objJson.getString("PWeight"));
                        String temperature = String.valueOf(objJson.getString("temperature"));
                        String Actdate = String.valueOf(objJson.getString("Actdate"));
                        String IsActive = String.valueOf(objJson.getString("IsActive"));
                        String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                        String Docid = String.valueOf(objJson.getString("Docid"));
                        String BPD = String.valueOf(objJson.getString("BPD"));
                        String bmi = String.valueOf(objJson.getString("bmi"));
                        String height = String.valueOf(objJson.getString("height"));
                        String HID = String.valueOf(objJson.getString("HID"));
                        String Signs = String.valueOf(objJson.getString("Signs"));


                        values1 = new ContentValues();
                        values1.put("ServerId", Id);
                        values1.put("Docid", Docid);
                        values1.put("Ptid", Ptid);
                        values1.put("DiagId", DiagId);
                        values1.put("pname", pname);
                        values1.put("gender", gender);
                        values1.put("age", age);
                        values1.put("mobnum", mobnum);
                        values1.put("refdocname", refdocname);
                        values1.put("clinicname", clinicname);
                        values1.put("Diagnosis", Diagnosis);
                        values1.put("BpS", Bp);
                        values1.put("BpD", BPD);
                        values1.put("FBS", FBS);
                        values1.put("PPS", PPS);
                        values1.put("RBS", RBS);
                        values1.put("PWeight", PWeight);
                        values1.put("temperature", temperature);
                        values1.put("Actdate", BaseConfig.DateFormatter3(Actdate));
                        values1.put("IsActive", IsActive);
                        values1.put("treatmentfor", treatmentfor);
                        values1.put("Isupdate", "1");
                        values1.put("height", height);
                        values1.put("bmi", bmi);
                        values1.put("HID", HID);
                        values1.put("Signs", Signs);

                        boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Diagonis where  Ptid='" + Ptid + "' and DiagId='" + DiagId + "' and ServerId='" + Id + '\'');//(ServerId=" + Id + " or ServerId!=null)

                        if (GetStatus == false) {

                            db.insert("Diagonis", null, values1);

                        } else {
                            db.update("Diagonis", values1, "ServerId=" + Id, null);

                        }

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            db.close();

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private void ImportBind_GeneralExamination() throws JSONException {


        String IsActive;
        String str = "";

        String Query = "select  Patid from  Patreg";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportBind_GeneralExaminationJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("Id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Anaemic = String.valueOf(objJson.getString("Anaemic"));
                    String Icterus = String.valueOf(objJson.getString("Icterus"));
                    String Stenosis = String.valueOf(objJson.getString("Stenosis"));
                    String Clubbing = String.valueOf(objJson.getString("Clubbing"));
                    String Limbphantom = String.valueOf(objJson.getString("Limbphantom"));
                    String Vericoveins = String.valueOf(objJson.getString("Vericoveins"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String Upid = String.valueOf(objJson.getString("Upid"));
                    String Pedal_edema = String.valueOf(objJson.getString("Pedal_edema"));
                    String built = String.valueOf(objJson.getString("built"));
                    String extra_generalexam = String.valueOf(objJson.getString("extra_generalexam"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    //values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Anaemic", Anaemic);
                    values1.put("Icterus", Icterus);
                    values1.put("Stenosis", Stenosis);
                    values1.put("Clubbing", Clubbing);
                    values1.put("Limbphantom", Limbphantom);
                    values1.put("Vericoveins", Vericoveins);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("Pedal_edema", Pedal_edema);
                    values1.put("built", built);
                    values1.put("extra_generalexam", extra_generalexam);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_GeneralExamination where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_GeneralExamination", null, values1);

                    } else {
                        db.update("CaseNote_GeneralExamination", values1, "ServerId=" + Id, null);

                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();

    }

    private void ImportBind_Cardiovascular() throws JSONException {


        String IsActive;

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportBind_CardiovascularJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("Id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Beat = String.valueOf(objJson.getString("Beat"));
                    String Murmur = String.valueOf(objJson.getString("Murmur"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String Upid = String.valueOf(objJson.getString("Upid"));
                    String Pericardial_Rub = String.valueOf(objJson.getString("Pericardial_Rub"));
                    String Pulserate = String.valueOf(objJson.getString("Pulserate"));
                    String heartrate = String.valueOf(objJson.getString("heartrate"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    ///values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Beat", Beat);
                    values1.put("Murmur", Murmur);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("Pericardial_Rub", Pericardial_Rub);
                    values1.put("Pulserate", Pulserate);
                    values1.put("heartrate", heartrate);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Cardiovascular where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Cardiovascular", null, values1);

                    } else {
                        db.update("CaseNote_Cardiovascular", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_RespiratorySystem() throws JSONException {


        String IsActive;
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportBind_RespiratorySystemJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("Id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Breathsound = String.valueOf(objJson.getString("Breathsound"));
                    String Trachea = String.valueOf(objJson.getString("Trachea"));
                    String Kyphosis = String.valueOf(objJson.getString("Kyphosis"));
                    String Crepitation = String.valueOf(objJson.getString("Crepitation"));
                    String Bronchi = String.valueOf(objJson.getString("Bronchi"));
                    String Pulserate = String.valueOf(objJson.getString("Pulserate"));
                    String Note = String.valueOf(objJson.getString("Note"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String Upid = String.valueOf(objJson.getString("Upid"));
                    String shapeofchest = String.valueOf(objJson.getString("shapeofchest"));
                    String spo2 = String.valueOf(objJson.getString("spo2"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    // values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Breathsound", Breathsound);
                    values1.put("Trachea", Trachea);
                    values1.put("Kyphosis_Scoliosis", Kyphosis);
                    values1.put("Crepitation", Crepitation);
                    values1.put("Bronchi", Bronchi);
                    values1.put("Pulserate", Pulserate);
                    values1.put("Note", Note);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("shapeofchest", shapeofchest);
                    values1.put("spo2", spo2);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_RespiratorySystem where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_RespiratorySystem", null, values1);

                    } else {
                        db.update("CaseNote_RespiratorySystem", values1, "ServerId=" + Id, null);

                    }


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_Gastrointestinal() throws JSONException {


        String IsActive;

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportBind_GastrointestinalJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("Id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Abdomen = String.valueOf(objJson.getString("Abdomen"));
                    String Bowelsound = String.valueOf(objJson.getString("Bowelsound"));
                    String Spleen = String.valueOf(objJson.getString("Spleen"));
                    String Liver = String.valueOf(objJson.getString("Liver"));
                    String PalpableMasses = String.valueOf(objJson.getString("PalpableMasses"));
                    String Hernial = String.valueOf(objJson.getString("Hernial"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String Upid = String.valueOf(objJson.getString("Upid"));
                    String shapeofabdomen = String.valueOf(objJson.getString("shapeofabdomen"));
                    String Visible_Pulsations = String.valueOf(objJson.getString("Visible_Pulsations"));
                    String Visual_Peristalsis = String.valueOf(objJson.getString("Visual_Peristalsis"));
                    String Abdominal_Palpation = String.valueOf(objJson.getString("Abdominal_Palpation"));
                    String Splenomegaly = String.valueOf(objJson.getString("Splenomegaly"));
                    String Hepatomegaly = String.valueOf(objJson.getString("Hepatomegaly"));
                    String organomegely = String.valueOf(objJson.getString("organomegely"));
                    String freefuild = String.valueOf(objJson.getString("freefuild"));
                    String distension = String.valueOf(objJson.getString("distension"));
                    String tenderness = String.valueOf(objJson.getString("tenderness"));
                    String scrotum = String.valueOf(objJson.getString("scrotum"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    // values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Abdomen", Abdomen);
                    values1.put("Bowelsound", Bowelsound);
                    values1.put("Spleen", Spleen);
                    values1.put("Liver", Liver);
                    values1.put("PalpableMasses", PalpableMasses);
                    values1.put("Hernial", Hernial);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("shapeofabdomen", shapeofabdomen);
                    values1.put("Visible_Pulsations", Visible_Pulsations);
                    values1.put("Visual_Peristalsis", Visual_Peristalsis);
                    values1.put("Abdominal_Palpation", Abdominal_Palpation);
                    values1.put("Splenomegaly", Splenomegaly);
                    values1.put("Hepatomegaly", Hepatomegaly);
                    values1.put("organomegely", organomegely);
                    values1.put("freefuild", freefuild);
                    values1.put("distension", distension);
                    values1.put("tenderness", tenderness);
                    values1.put("scrotum", scrotum);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Gastrointestinal where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Gastrointestinal", null, values1);

                    } else {
                        db.update("CaseNote_Gastrointestinal", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_Neurology() throws JSONException {


        String IsActive;
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportBind_NeurologyJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("Id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Pupilsize = String.valueOf(objJson.getString("Pupilsize"));
                    String Speech = String.valueOf(objJson.getString("Speech"));
                    String Carodit = String.valueOf(objJson.getString("Carodit"));
                    String Amnesia = String.valueOf(objJson.getString("Amnesia"));
                    String Apraxia = String.valueOf(objJson.getString("Apraxia"));
                    String Cognitive_Function = String.valueOf(objJson.getString("Cognitive_Function"));
                    String Bulkdata = String.valueOf(objJson.getString("Bulkdata"));
                    String Tone = String.valueOf(objJson.getString("Tone"));
                    String Power_LUL = String.valueOf(objJson.getString("Power_LUL"));
                    String Power_RUL = String.valueOf(objJson.getString("Power_RUL"));
                    String Power_LLL = String.valueOf(objJson.getString("Power_LLL"));
                    String Power_RLL = String.valueOf(objJson.getString("Power_RLL"));
                    String Sensory = String.valueOf(objJson.getString("Sensory"));
                    String Reflexes_Corneal = String.valueOf(objJson.getString("Reflexes_Corneal"));
                    String Reflexes_Biceps = String.valueOf(objJson.getString("Reflexes_Biceps"));
                    String Reflexes_Triceps = String.valueOf(objJson.getString("Reflexes_Triceps"));
                    String Reflexes_Supinator = String.valueOf(objJson.getString("Reflexes_Supinator"));
                    String Reflexes_Knee = String.valueOf(objJson.getString("Reflexes_Knee"));
                    String Reflexes_Ankle = String.valueOf(objJson.getString("Reflexes_Ankle"));
                    String Reflexes_Plantor = String.valueOf(objJson.getString("Reflexes_Plantor"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String Upid = String.valueOf(objJson.getString("Upid"));
                    String congentail_abnormality = String.valueOf(objJson.getString("congentail_abnormality"));
                    String mentalstatus = String.valueOf(objJson.getString("mentalstatus"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    //values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Pupilsize", Pupilsize);
                    values1.put("Speech", Speech);
                    values1.put("Carodit", Carodit);
                    values1.put("Amnesia", Amnesia);
                    values1.put("Apraxia", Apraxia);
                    values1.put("Cognitive_Function", Cognitive_Function);
                    values1.put("Bulk", Bulkdata);
                    values1.put("Tone", Tone);
                    values1.put("Power_LUL", Power_LUL);
                    values1.put("Power_RUL", Power_RUL);
                    values1.put("Power_LLL", Power_LLL);
                    values1.put("Power_RLL", Power_RLL);
                    values1.put("Sensory", Sensory);
                    values1.put("Reflexes_Corneal", Reflexes_Corneal);
                    values1.put("Reflexes_Biceps", Reflexes_Biceps);
                    values1.put("Reflexes_Triceps", Reflexes_Triceps);
                    values1.put("Reflexes_Supinator", Reflexes_Supinator);
                    values1.put("Reflexes_Knee", Reflexes_Knee);
                    values1.put("Reflexes_Ankle", Reflexes_Ankle);
                    values1.put("Reflexes_Plantor", Reflexes_Plantor);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("congentail_abnormality", congentail_abnormality);
                    values1.put("mentalstatus", mentalstatus);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Neurology where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Neurology", null, values1);

                    } else {
                        db.update("CaseNote_Neurology", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Renal() throws JSONException {


        String IsActive;
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportCaseNote_RenalJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String id = String.valueOf(objJson.getString("id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String dysuria = String.valueOf(objJson.getString("dysuria"));
                    String pyuria = String.valueOf(objJson.getString("pyuria"));
                    String haematuria = String.valueOf(objJson.getString("haematuria"));
                    String oliguria = String.valueOf(objJson.getString("oliguria"));
                    String polyuria = String.valueOf(objJson.getString("polyuria"));
                    String anuria = String.valueOf(objJson.getString("anuria"));
                    String nocturia = String.valueOf(objJson.getString("nocturia"));
                    String urethraldischarge = String.valueOf(objJson.getString("urethraldischarge"));
                    String prostate = String.valueOf(objJson.getString("prostate"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    values1 = new ContentValues();
                    //values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("dysuria", dysuria);
                    values1.put("pyuria", pyuria);
                    values1.put("haematuria", haematuria);
                    values1.put("oliguria", oliguria);
                    values1.put("polyuria", polyuria);
                    values1.put("anuria", anuria);
                    values1.put("nocturia", nocturia);
                    values1.put("urethraldischarge", urethraldischarge);
                    values1.put("prostate", prostate);
                    //values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Renal where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Renal", null, values1);

                    } else {
                        db.update("CaseNote_Renal", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Endocrine() throws JSONException {


        String IsActive;
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportCaseNote_EndocrineJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String id = String.valueOf(objJson.getString("id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Endocrine = String.valueOf(objJson.getString("Endocrine"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    // values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Endocrine", Endocrine);
                    //values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Endocrine where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Endocrine", null, values1);

                    } else {
                        db.update("CaseNote_Endocrine", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_ClinicalData() throws JSONException {


        String IsActive;
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportCaseNote_ClinicalDataJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String id = String.valueOf(objJson.getString("id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Heamoglobin = String.valueOf(objJson.getString("Heamoglobin"));
                    String wbc = String.valueOf(objJson.getString("wbc"));
                    String rbc = String.valueOf(objJson.getString("rbc"));
                    String esr = String.valueOf(objJson.getString("esr"));
                    String polymorphs = String.valueOf(objJson.getString("polymorphs"));
                    String lymphocytes = String.valueOf(objJson.getString("lymphocytes"));
                    String monocytes = String.valueOf(objJson.getString("monocytes"));
                    String basophils = String.valueOf(objJson.getString("basophils"));
                    String eosinophils = String.valueOf(objJson.getString("eosinophils"));
                    String urea = String.valueOf(objJson.getString("urea"));
                    String creatinine = String.valueOf(objJson.getString("creatinine"));
                    String UricAcid = String.valueOf(objJson.getString("UricAcid"));
                    String Sodium = String.valueOf(objJson.getString("Sodium"));
                    String Potassium = String.valueOf(objJson.getString("Potassium"));
                    String Chloride = String.valueOf(objJson.getString("Chloride"));
                    String Bicarbonate = String.valueOf(objJson.getString("Bicarbonate"));
                    String TotalCholesterol = String.valueOf(objJson.getString("TotalCholesterol"));
                    String LDL = String.valueOf(objJson.getString("LDL"));
                    String HDL = String.valueOf(objJson.getString("HDL"));
                    String VLDL = String.valueOf(objJson.getString("VLDL"));
                    String Triglycerides = String.valueOf(objJson.getString("Triglycerides"));
                    String Serumbilirubin = String.valueOf(objJson.getString("Serumbilirubin"));
                    String Direct = String.valueOf(objJson.getString("Direct"));
                    String Indirect = String.valueOf(objJson.getString("Indirect"));
                    String Totalprotein = String.valueOf(objJson.getString("Totalprotein"));
                    String Albumin = String.valueOf(objJson.getString("Albumin"));
                    String Globulin = String.valueOf(objJson.getString("Globulin"));
                    String SGOT = String.valueOf(objJson.getString("SGOT"));
                    String SGPT = String.valueOf(objJson.getString("SGPT"));
                    String AlkalinePhosphatase = String.valueOf(objJson.getString("AlkalinePhosphatase"));
                    String FreeT3 = String.valueOf(objJson.getString("FreeT3"));
                    String FreeT4 = String.valueOf(objJson.getString("FreeT4"));
                    String TSH = String.valueOf(objJson.getString("TSH"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    //values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Heamoglobin", Heamoglobin);
                    values1.put("wbc", wbc);
                    values1.put("rbc", rbc);
                    values1.put("esr", esr);
                    values1.put("polymorphs", polymorphs);
                    values1.put("lymphocytes", lymphocytes);
                    values1.put("monocytes", monocytes);
                    values1.put("basophils", basophils);
                    values1.put("eosinophils", eosinophils);
                    values1.put("urea", urea);
                    values1.put("creatinine", creatinine);
                    values1.put("UricAcid", UricAcid);
                    values1.put("Sodium", Sodium);
                    values1.put("Potassium", Potassium);
                    values1.put("Chloride", Chloride);
                    values1.put("Bicarbonate", Bicarbonate);
                    values1.put("TotalCholesterol", TotalCholesterol);
                    values1.put("LDL", LDL);
                    values1.put("HDL", HDL);
                    values1.put("VLDL", VLDL);
                    values1.put("Triglycerides", Triglycerides);
                    values1.put("Serumbilirubin", Serumbilirubin);
                    values1.put("Direct", Direct);
                    values1.put("Indirect", Indirect);
                    values1.put("Totalprotein", Totalprotein);
                    values1.put("Albumin", Albumin);
                    values1.put("Globulin", Globulin);
                    values1.put("SGOT", SGOT);
                    values1.put("SGPT", SGPT);
                    values1.put("AlkalinePhosphatase", AlkalinePhosphatase);
                    values1.put("FreeT3", FreeT3);
                    values1.put("FreeT4", FreeT4);
                    values1.put("TSH", TSH);
                    // values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_ClinicalData where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_ClinicalData", null, values1);

                    } else {
                        db.update("CaseNote_ClinicalData", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Locomotor() throws JSONException {

        String IsActive;
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportCaseNote_LocomotorJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String id = String.valueOf(objJson.getString("id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String symmetry = String.valueOf(objJson.getString("symmetry"));
                    String smooth_movement = String.valueOf(objJson.getString("smooth_movement"));
                    String arms_swing = String.valueOf(objJson.getString("arms_swing"));
                    String pelvic_tilt = String.valueOf(objJson.getString("pelvic_tilt"));
                    String stride_length = String.valueOf(objJson.getString("stride_length"));
                    String cervical_lordosis = String.valueOf(objJson.getString("cervical_lordosis"));
                    String lumbar_lordosis = String.valueOf(objJson.getString("lumbar_lordosis"));
                    String kyphosis = String.valueOf(objJson.getString("kyphosis"));
                    String scoliosis = String.valueOf(objJson.getString("scoliosis"));
                    String llswelling = String.valueOf(objJson.getString("llswelling"));
                    String lldeformity = String.valueOf(objJson.getString("lldeformity"));
                    String lllimbshortening = String.valueOf(objJson.getString("lllimbshortening"));
                    String llmuscle_wasting = String.valueOf(objJson.getString("llmuscle_wasting"));
                    String llremarks = String.valueOf(objJson.getString("llremarks"));
                    String ulswelling = String.valueOf(objJson.getString("ulswelling"));
                    String uldeformity = String.valueOf(objJson.getString("uldeformity"));
                    String ullimbshortening = String.valueOf(objJson.getString("ullimbshortening"));
                    String ulmuscle_wasting = String.valueOf(objJson.getString("ulmuscle_wasting"));
                    String ulremarks = String.valueOf(objJson.getString("ulremarks"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    //values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("symmetry", symmetry);
                    values1.put("smooth_movement", smooth_movement);
                    values1.put("arms_swing", arms_swing);
                    values1.put("pelvic_tilt", pelvic_tilt);
                    values1.put("stride_length", stride_length);
                    values1.put("cervical_lordosis", cervical_lordosis);
                    values1.put("lumbar_lordosis", lumbar_lordosis);
                    values1.put("kyphosis", kyphosis);
                    values1.put("scoliosis", scoliosis);
                    values1.put("llswelling", llswelling);
                    values1.put("lldeformity", lldeformity);
                    values1.put("lllimbshortening", lllimbshortening);
                    values1.put("llmuscle_wasting", llmuscle_wasting);
                    values1.put("llremarks", llremarks);
                    values1.put("ulswelling", ulswelling);
                    values1.put("uldeformity", uldeformity);
                    values1.put("ullimbshortening", ullimbshortening);
                    values1.put("ulmuscle_wasting", ulmuscle_wasting);
                    values1.put("ulremarks", ulremarks);
                    //values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Locomotor where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Locomotor", null, values1);

                    } else {
                        db.update("CaseNote_Locomotor", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_CaseNote_OtherSystem() throws JSONException {


        String IsActive;
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportBind_CaseNote_OtherSystemJSON";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    String Id = String.valueOf(objJson.getString("Id"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String Othersystem = String.valueOf(objJson.getString("Othersystem"));
                    String AdditionalInfo = String.valueOf(objJson.getString("AdditionalInfo"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String Upid = String.valueOf(objJson.getString("Upid"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    //values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Othersystem", Othersystem);
                    values1.put("AdditionalInfo", AdditionalInfo);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_OtherSystem where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_OtherSystem", null, values1);

                    } else {
                        db.update("CaseNote_OtherSystem", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Dental() throws JSONException {


        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        String PatientIdLIst = getPatientList();
        String MethodName = "ImportCaseNote_Dental";

        try {


            String resultsRequestSOAP = postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);

                    String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    String upper_right_jaw = String.valueOf(objJson.getString("upper_right_jaw"));
                    String upper_left_jaw = String.valueOf(objJson.getString("upper_left_jaw"));
                    String upid = String.valueOf(objJson.getString("upid"));
                    String pagegen = String.valueOf(objJson.getString("pagegen"));
                    String DiagId = String.valueOf(objJson.getString("DiagId"));

                    String HID = String.valueOf(objJson.getString("HID"));
                    String lower_left_jaw = String.valueOf(objJson.getString("lower_left_jaw"));
                    String id = String.valueOf(objJson.getString("id"));
                    String docid = String.valueOf(objJson.getString("docid"));
                    String ptid = String.valueOf(objJson.getString("ptid"));
                    // String Actdate = String.valueOf(objJson.getString("Actdate"));
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String pname = String.valueOf(objJson.getString("pname"));
                    String lower_right_jaw = String.valueOf(objJson.getString("lower_right_jaw"));
                    String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", IsUpdateMax);
                    values1.put("upper_right_jaw", upper_right_jaw);
                    values1.put("upper_left_jaw", upper_left_jaw);
                    //values1.put("upid", upid);
                    values1.put("pagegen", pagegen);
                    values1.put("DiagId", DiagId);
                    values1.put("HID", HID);
                    values1.put("lower_left_jaw", lower_left_jaw);
                    //values1.put("id", id);
                    values1.put("docid", docid);
                    values1.put("ptid", ptid);
                    values1.put("IsActive", IsActive);
                    values1.put("pname", pname);
                    values1.put("lower_right_jaw", lower_right_jaw);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);

                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_OtherSystem where ServerId=" + IsUpdateMax);

                    if (!GetStatus) {

                        db.insert("CaseNote_DentalSystem", null, values1);

                    } else {
                        db.update("CaseNote_DentalSystem", values1, "ServerId=" + IsUpdateMax, null);

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void InsertImmunizationInformation() {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);

            StringBuilder str = new StringBuilder();

            String Query = "select ptid,docid from ClinicalInformation where vaccine_update='1'";

            String DocId = "";
            String PatId = "";

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        str.append(c.getString(c.getColumnIndex("docid")));
                        str.append('^');
                        str.append(c.getString(c.getColumnIndex("ptid")));

                        DocId = c.getString(c.getColumnIndex("docid"));
                        PatId = c.getString(c.getColumnIndex("ptid"));

                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }


            String resultsRequestSOAP = null;
            try {
                MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
                PostImmunizationInfoFactory postDoctorIdFactory = new PostImmunizationInfoFactory(magnetMobileClient);
                PostImmunizationInfo postDoctorId = postDoctorIdFactory.obtainInstance();

                //Set Body Data
                PosImmunizationInfoRequest body = new PosImmunizationInfoRequest();
                body.setDocId(DocId);
                body.setPatId(PatId);

                Call<PosImmunizationInfoResult> resultCall = postDoctorId.posImmunizationInfo(body, null);

                resultsRequestSOAP = resultCall.get().getResults();
            } catch (SchemaException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);

                    //Id,patid,pname,agegen,mobnum,drid,vaccinename,schedule,duedt,givendt,weight,dt

                    String Insert_Query_TestDtls = "update Vaccination set givendt='"
                            + objJson.getString("givendt")
                            + "',isactive='1',Isupdate='1',dt='"
                            + objJson.getString("dt")
                            + "' where vaccinename='"
                            + objJson.getString("vaccinename")
                            + "' and schedule='"
                            + objJson.getString("givendt")
                            + "' and patid='"
                            + objJson.getString("patid")
                            + "' and isactive='0' and Isupdate='0'";

                    BaseConfig.SaveData(Insert_Query_TestDtls);


                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedTestDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {

            StringBuilder str = new StringBuilder();

            String Query = "select mtestid,docid,Ptid from medicaltestdtls where ReadytoUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();

            int upid = 0;

            Cursor c = db.rawQuery(Query, null);
            String MethodName;

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));

                        MethodName = "fromlab_Medicaltestdts";

                        if (!"".equals(mtestid)) {

                            String resultsRequestSOAP = "";

                            resultsRequestSOAP = postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);


                            try {


                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //result,weight,temperature,bpd,bps,id,testvalue,testsummary,alltest


                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update
                                        int seenInt = 0;

                                        try {

                                            seenInt = Integer.parseInt(GetCurrentSeenValue(ptid, mtestid, objJson.getString("alltest")));
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                            //Log.e("SEEN VALUE EXCEPTION WARNING", e.getMessage());
                                        }

                                        String ResultValue = "0";
                                        if ("0".equalsIgnoreCase(objJson.getString("result"))) {
                                            ResultValue = "Nil";
                                        } else if ("1".equalsIgnoreCase(objJson.getString("result"))) {
                                            ResultValue = "Positive";

                                        } else if ("2".equalsIgnoreCase(objJson.getString("result"))) {
                                            ResultValue = "Negative";
                                        }


                                        ContentValues values = new ContentValues();
                                        values.put("testvalue", objJson.getString("testvalue"));
                                        values.put("testsummary", objJson.getString("testsummary"));
                                        values.put("IsActive", "0");
                                        values.put("seen", seenInt);
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        values.put("bps", objJson.getString("bps"));
                                        values.put("bpd", objJson.getString("bpd"));
                                        values.put("temperature", objJson.getString("temperature"));
                                        values.put("weight", objJson.getString("weight"));
                                        values.put("result", ResultValue);
                                        db.update("Medicaltestdtls", values, "Ptid='" + ptid + "' and alltest='" + objJson.getString("alltest") + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                        UpdateInCaseNotes(ptid, mtestid, objJson.getString("alltest"));



                                    }

                                }

                                //Log.e("Testing", String.valueOf(upid));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedScanDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedScanDtls");

            String Query = "select mtestid,docid,Ptid  from Scantest where ReadytoUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();
            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));

                        if (!"".equals(mtestid)) {

                            String MethodName = "fromlab_Scantest";


                            try {

                                String resultsRequestSOAP = "";

                                resultsRequestSOAP = postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        // id,scanvalue,scanname,scansummary


                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update
                                        ContentValues values = new ContentValues();
                                        values.put("scanvalue", objJson.getString("scanvalue"));
                                        values.put("scansummary", objJson.getString("scansummary"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("Scantest", values, "where Ptid='" + ptid + "' and scanname='" + objJson.getString("scanname") + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");


                                    }
                                }

                                //Log.e("Testing", String.valueOf(upid));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedXrayDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedXrayDtls");

            String Query = "select mtestid,docid,Ptid  from XRAY where ReadytoUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();
            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));

                        if (!"".equals(mtestid)) {

                            String MethodName = "fromlab_XRAY";

                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,xrayvalue,xraysummary,xray

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';

                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update


                                        ContentValues values = new ContentValues();
                                        values.put("xrayvalue", objJson.getString("xrayvalue"));
                                        values.put("xraysummary", objJson.getString("xraysummary"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("XRAY", values, "where Ptid='" + ptid + "' and xray='" + objJson.getString("xray") + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                    }

                                }


                                //Log.e("Testing", String.valueOf(upid));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportupdatedEEGDtls() {
// TODO: 3/26/2017 checking EEG Details
        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedEEGDtls");

            String Query = "select mtestid,docid,ptid  from EEG where ReadytoUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();
            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("ptid"));

                        if (!"".equals(mtestid)) {

                            String MethodName = "fromlab_EEG";

                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,Summary

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update
                                        ContentValues values = new ContentValues();
                                        values.put("Summary", objJson.getString("Summary"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("EEG", values, "Ptid='" + ptid + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                    }

                                }


                                //Log.e("Testing", String.valueOf(upid));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportupdatedECGDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";
// TODO: 3/26/2017 checking ECG Details
        try {
            //Log.e("Data Sent", "Entered ImportupdatedECGDtls");

            String Query = "select mtestid,Docid,Ptid  from ECGTEST where ReadytoUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();
            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("Docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));


                        if (!"".equals(mtestid)) {


                            String MethodName = "fromlab_ECGTEST";


                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,Ecg,Treadmill,ecgrate,ecgqrs,ecgrate,ecgst,ecgt,ecgrhyrhm,ecgaxis,Bundlebranchblock,Conductiondefects,PR

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update

                                        // Ecg,Treadmill,ecgrate, ecgqrs,ecgst,ecgt,
                                        // ecgrhyrhm,ecgaxis,
                                        // Bundlebranchblock,Conductiondefects,PR
                                        ContentValues values = new ContentValues();
                                        values.put("Ecg", objJson.getString("Ecg"));
                                        values.put("Treadmill", objJson.getString("Treadmill"));
                                        values.put("ecgrate", objJson.getString("ecgrate"));
                                        values.put("ecgqrs", objJson.getString("ecgqrs"));
                                        values.put("ecgst", objJson.getString("ecgst"));
                                        values.put("ecgt", objJson.getString("ecgt"));
                                        values.put("ecgrhyrhm", objJson.getString("ecgrhyrhm"));
                                        values.put("ecgaxis", objJson.getString("ecgaxis"));
                                        values.put("Bundlebranchblock", objJson.getString("Bundlebranchblock"));
                                        values.put("Conductiondefects", objJson.getString("Conductiondefects"));
                                        values.put("PR", objJson.getString("PR"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("ECGTEST", values, " Ptid='" + ptid + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                    }

                                }

                                //Log.e("Testing", String.valueOf(upid));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // break;

                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedAngiogram() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedAngiogram");

            String Query = "select mtestid,docid,ptid from Angiogram where ReadytoUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();
            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("ptid"));

                        if (!"".equals(mtestid)) {

                            String MethodName = "fromlab_Angiogram";


                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,Coronary,Brain,Upperlimb,Lowerlimb,Mesenteric

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update

                                        // Ecg,Treadmill,ecgrate, ecgqrs,ecgst,ecgt,
                                        // ecgrhyrhm,ecgaxis,
                                        // Bundlebranchblock,Conductiondefects,PR
                                        ContentValues values = new ContentValues();
                                        values.put("Coronary", objJson.getString("Coronary"));
                                        values.put("Brain", objJson.getString("Brain"));
                                        values.put("Upperlimb", objJson.getString("Upperlimb"));
                                        values.put("Lowerlimb", objJson.getString("Lowerlimb"));
                                        values.put("Mesenteric", objJson.getString("Mesenteric"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("Angiogram", values, "where Ptid='" + ptid + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");


                                    }

                                }


                                //Log.e("Testing", String.valueOf(upid));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void Importmast_Operations() {


        String str = "";

        String Query = "select  Patid from  Patreg";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String PatientId = c.getString(c.getColumnIndex("Patid"));

                    getOperationDetails(PatientId);


                } while (c.moveToNext());

            }
        }

        Objects.requireNonNull(c).close();

        db.close();

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private void Importmast_Upload() {


        String str = "";

        String Query = "select Ptid,mtestid from Medicaltest  group by mtestid";

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String Ptid = c.getString(c.getColumnIndex("Ptid"));
                    String mtestid = c.getString(c.getColumnIndex("mtestid"));

                    getBindUpload(Ptid, mtestid);


                } while (c.moveToNext());

            }
        }

        Objects.requireNonNull(c).close();

        db.close();

    }

    private void Import_ExaminationBlood_Test() throws JSONException {
        String PatId = "";
        String MtestId = "";
        String MethodName = "Import_ExaminationBlood_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            //Query = "select Patid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);


                        break;
                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                String resultsRequestSOAP = postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        String ServerId = objJson.getString("Id");
                        String Patid = objJson.getString("Patid");
                        String Docid = objJson.getString("Docid");
                        String Actdate = objJson.getString("Actdate");
                        String IsActive = objJson.getString("IsActive");
                        String IsUpdate = objJson.getString("IsUpdate");
                        String Mtestid = objJson.getString("Mtestid");
                        String Testid = objJson.getString("Testid");
                        String Subtestid = objJson.getString("Subtestid");
                        String HID = objJson.getString("HID");
                        String IsPaid = objJson.getString("IsPaid");
                        String maemogiobin = objJson.getString("Haemogiobin");
                        String packed_cell_volume = objJson.getString("packed_cell_volume");
                        String total_count = objJson.getString("total_count");
                        String rbc_count = objJson.getString("rbc_count");
                        String polymorphs = objJson.getString("polymorphs");
                        String lymphocytes = objJson.getString("lymphocytes");
                        String eosinophilis = objJson.getString("eosinophilis");
                        String monocytes = objJson.getString("monocytes");
                        String basophils = objJson.getString("basophils");
                        String band_from = objJson.getString("band_from");
                        String platelets_count = objJson.getString("platelets_count");
                        String psmp = objJson.getString("psmp");
                        String esr = objJson.getString("esr");
                        String STyphi_O = objJson.getString("STyphi_O");
                        String STyphi_H = objJson.getString("STyphi_H");
                        String SParatyphi_AH = objJson.getString("SParatyphi_AH");
                        String SParatyphi_BH = objJson.getString("SParatyphi_BH");
                        String Result_Status = objJson.getString("Blood_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);
                        values.put("maemogiobin", maemogiobin);
                        values.put("packed_cell_volume", packed_cell_volume);
                        values.put("total_count", total_count);
                        values.put("rbc_count", rbc_count);
                        values.put("polymorphs", polymorphs);
                        values.put("lymphocytes", lymphocytes);
                        values.put("eosinophilis", eosinophilis);
                        values.put("monocytes", monocytes);
                        values.put("basophils", basophils);
                        values.put("band_from", band_from);
                        values.put("platelets_count", platelets_count);
                        values.put("psmp", psmp);
                        values.put("esr", esr);
                        values.put("STyphi_O", STyphi_O);
                        values.put("STyphi_H", STyphi_H);
                        values.put("SParatyphi_AH", SParatyphi_AH);
                        values.put("SParatyphi_BH", SParatyphi_BH);
                        values.put("Result_Status", Result_Status);

                        //data iruntha update else insert
                        boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_examination_blood_test where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_examination_blood_test", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_examination_blood_test", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }

    }

    private void Import_ExaminationStool_Test() {
        String PatId = "";
        String MtestId = "";
        String MethodName = "Import_ExaminationStool_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));

                        export_jsonarray.put(from_db_obj);

                        break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                String resultsRequestSOAP = postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);


                        String Id = objJson.getString("Id");
                        String Patid = objJson.getString("Patid");
                        String Docid = objJson.getString("Docid");
                        String Actdate = objJson.getString("Actdate");
                        String IsActive = objJson.getString("IsActive");
                        String IsUpdate = objJson.getString("IsUpdate");
                        String ServerId = objJson.getString("Id");
                        String Mtestid = objJson.getString("Mtestid");
                        String Testid = objJson.getString("Mtestid");
                        String Subtestid = objJson.getString("Subtestid");
                        String HID = objJson.getString("HID");
                        String IsPaid = objJson.getString("IsPaid");
                        String colour = objJson.getString("colour");
                        String consistency = objJson.getString("consistency");
                        String musus = objJson.getString("musus");
                        String blood = objJson.getString("blood");
                        String parasites = objJson.getString("parasites");
                        String occult_blood_test = objJson.getString("occult_blood_test");
                        String cpi_cells = objJson.getString("cpi_cells");
                        String pus_cells = objJson.getString("pus_cells");
                        String red_blood_cells = objJson.getString("red_blood_cells");
                        String macrophages = objJson.getString("macrophages");
                        String fat = objJson.getString("fat");
                        String starch = objJson.getString("starch");
                        String undigeted_food = objJson.getString("undigeted_food_matter");
                        String any_other_finding = objJson.getString("any_other_finding");
                        String vegetative_forms = objJson.getString("vegetative_forms");
                        String protozoa = objJson.getString("protozoa");
                        String flagellates = objJson.getString("flagellates");
                        String cyst = objJson.getString("cyst");
                        String ova = objJson.getString("ova");
                        String larvae = objJson.getString("larvae");
                        String result = objJson.getString("result");
                        String Result_Status = objJson.getString("Stool_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        ContentValues values = new ContentValues();
                        values.put("Id", Id);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("ServerId", ServerId);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Mtestid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);
                        values.put("colour", colour);
                        values.put("consistency", consistency);
                        values.put("musus", musus);
                        values.put("blood", blood);
                        values.put("parasites", parasites);
                        values.put("occult_blood_test", occult_blood_test);
                        values.put("cpi_cells", cpi_cells);
                        values.put("pus_cells", pus_cells);
                        values.put("red_blood_cells", red_blood_cells);
                        values.put("macrophages", macrophages);
                        values.put("fat", fat);
                        values.put("starch", starch);
                        values.put("undigeted_food", undigeted_food);
                        values.put("any_other_finding", any_other_finding);
                        values.put("vegetative_forms", vegetative_forms);
                        values.put("protozoa", protozoa);
                        values.put("flagellates", flagellates);
                        values.put("cyst", cyst);
                        values.put("ova", ova);
                        values.put("larvae", larvae);
                        values.put("result", result);
                        values.put("Result_Status", Result_Status);

                        //data iruntha update else insert
                        boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_stool_report where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_stool_report", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_stool_report", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }
    }

    private void Import_ExaminationUrine_Test() {
        String PatId = "";
        String MtestId = "";
        String MethodName = "Import_ExaminationUrine_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);


                        break;
                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {

                String resultsRequestSOAP = postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        String ServerId = objJson.getString("Id");
                        String Patid = objJson.getString("Patid");
                        String Docid = objJson.getString("Docid");
                        String Actdate = objJson.getString("Actdate");
                        String IsActive = objJson.getString("IsActive");
                        String IsUpdate = objJson.getString("IsUpdate");
                        String Mtestid = objJson.getString("Mtestid");
                        String Testid = objJson.getString("Testid");
                        String Subtestid = objJson.getString("Subtestid");
                        String HID = objJson.getString("HID");
                        String IsPaid = objJson.getString("IsPaid");

                        String quantity = objJson.getString("Quantity");
                        String colour = objJson.getString("colour");
                        String appearance = objJson.getString("appearance");
                        String sediment = objJson.getString("sediment");
                        String specific_gravity = objJson.getString("specific_gravity");
                        String ph_reaction = objJson.getString("ph_reaction");
                        String proteins = objJson.getString("proteins");
                        String sugar = objJson.getString("sugar");
                        String occult_blood = objJson.getString("occult_blood");
                        String acetone = objJson.getString("acetone");
                        String bite_salf = objJson.getString("bite_salf");
                        String bile_pigment = objJson.getString("bile_pigment");
                        String epithelial_cells = objJson.getString("epithelial_cells");
                        String pus_cells = objJson.getString("pus_cells");
                        String red_blood_cells = objJson.getString("red_blood_cells");
                        String any_other_finding = objJson.getString("any_other_finding");
                        String amorphous_material = objJson.getString("amorphous_material");
                        String trichomans_vaginalis = objJson.getString("trichomans_vaginalis");
                        String casts = objJson.getString("casts");
                        String Result_Status = objJson.getString("Urine_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);
                        values.put("quantity", quantity);
                        values.put("colour", colour);
                        values.put("appearance", appearance);
                        values.put("sediment", sediment);
                        values.put("specific_gravity", specific_gravity);
                        values.put("ph_reaction", ph_reaction);
                        values.put("proteins", proteins);
                        values.put("sugar", sugar);
                        values.put("occult_blood", occult_blood);
                        values.put("acetone", acetone);
                        values.put("bite_salf", bite_salf);
                        values.put("bile_pigment", bile_pigment);
                        values.put("epithelial_cells", epithelial_cells);
                        values.put("pus_cells", pus_cells);
                        values.put("red_blood_cells", red_blood_cells);
                        values.put("any_other_finding", any_other_finding);
                        values.put("amorphous_material", amorphous_material);
                        values.put("trichomans_vaginalis", trichomans_vaginalis);
                        values.put("casts", casts);
                        values.put("Result_Status", Result_Status);


                        //data iruntha update else insert
                        boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_urine_test where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_urine_test", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_urine_test", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }
    }

    private void Import_ExaminationANC_Test() {
        String PatId = "";
        String MtestId = "";
        String MethodName = "Import_ExaminationANC_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);

                        break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                String resultsRequestSOAP = postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        String ServerId = objJson.getString("Id");
                        String Patid = objJson.getString("Patid");
                        String Docid = objJson.getString("Docid");
                        String Actdate = objJson.getString("Actdate");
                        String IsActive = objJson.getString("IsActive");
                        String IsUpdate = objJson.getString("IsUpdate");
                        String Mtestid = objJson.getString("Mtestid");
                        String Testid = objJson.getString("Testid");
                        String Subtestid = objJson.getString("Subtestid");
                        String HID = objJson.getString("HID");
                        String IsPaid = objJson.getString("IsPaid");

                        String haemogiobin = objJson.getString("Haemogiobin");
                        String bloodgroup = objJson.getString("bloodgroup");
                        String vdpl = objJson.getString("vdpl");
                        String colour = objJson.getString("colour");
                        String apperance = objJson.getString("apperance");
                        String albumin = objJson.getString("albumin");
                        String sugar = objJson.getString("sugar");
                        String bsbp = objJson.getString("bsbp");
                        String epithelialcells = objJson.getString("epithelialcells");
                        String puscells = objJson.getString("puscells");
                        String redcells = objJson.getString("redcells");
                        String yeastcells = objJson.getString("redcells");
                        String bacteria = objJson.getString("bacteria");
                        String amarphousmatenal = objJson.getString("amarphousmatenal");
                        String trichomonas = objJson.getString("trichomonas");
                        String casts = objJson.getString("casts");
                        String crystals = objJson.getString("crystals");
                        String australia_antigen = objJson.getString("australia_antigen");
                        String upt = objJson.getString("upt");
                        String tc = objJson.getString("tc");
                        String dcn = objJson.getString("dcn");
                        String dcl = objJson.getString("dcl");
                        String dce = objJson.getString("dce");
                        String dcm = objJson.getString("dcm");
                        String Result_Status = objJson.getString("ANC_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);

                        values.put("haemogiobin", haemogiobin);
                        values.put("bloodgroup", bloodgroup);
                        values.put("vdpl", vdpl);
                        values.put("colour", colour);
                        values.put("apperance", apperance);
                        values.put("albumin", albumin);
                        values.put("sugar", sugar);
                        values.put("bsbp", bsbp);
                        values.put("epithelialcells", epithelialcells);
                        values.put("puscells", puscells);
                        values.put("redcells", redcells);
                        values.put("yeastcells", redcells);
                        values.put("bacteria", bacteria);
                        values.put("amarphousmatenal", amarphousmatenal);
                        values.put("trichomonas", trichomonas);
                        values.put("casts", casts);
                        values.put("crystals", crystals);
                        values.put("australia_antigen", australia_antigen);
                        values.put("upt", upt);
                        values.put("tc", tc);
                        values.put("dcn", dcn);
                        values.put("dcl", dcl);
                        values.put("dce", dce);
                        values.put("dcm", dcm);
                        values.put("Result_Status", Result_Status);


                        //data iruntha update else insert
                        boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_anc_fp_test where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_anc_fp_test", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_anc_fp_test", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }
    }

    private void Import_ExaminationHIV_Test() {
        String PatId = "";
        String MtestId = "";
        String MethodName = "Import_ExaminationHIV_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));
                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);

                        break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                String resultsRequestSOAP = postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        String ServerId = objJson.getString("Id");
                        String Patid = objJson.getString("Patid");
                        String Docid = objJson.getString("Docid");
                        String Actdate = objJson.getString("Actdate");
                        String IsActive = objJson.getString("IsActive");
                        String IsUpdate = objJson.getString("IsUpdate");
                        String Mtestid = objJson.getString("Mtestid");
                        String Testid = objJson.getString("Testid");
                        String Subtestid = objJson.getString("Subtestid");
                        String HID = objJson.getString("HID");
                        String IsPaid = objJson.getString("IsPaid");


                        //String date_time= objJson.getString("date_time");
                        String LabId = objJson.getString("LabId");
                        String blood_datetime = objJson.getString("blood_datetime");

                        String NameHIVTestKit1 = objJson.getString("NameHIVTestKit1");
                        String BatchNo1 = objJson.getString("BatchNo1");
                        String ExpDate1 = objJson.getString("ExpDate1");
                        String Antibodies11 = objJson.getString("Antibodies11");
                        String Antibodies21 = objJson.getString("Antibodies21");
                        String Antibodies31 = objJson.getString("Antibodies31");

                        String NameHIVTestKit2 = objJson.getString("NameHIVTestKit2");
                        String BatchNo2 = objJson.getString("BatchNo2");
                        String ExpDate2 = objJson.getString("ExpDate2");
                        String Antibodies12 = objJson.getString("Antibodies12");
                        String Antibodies22 = objJson.getString("Antibodies22");
                        String Antibodies32 = objJson.getString("Antibodies32");

                        String NameHIVTestKit3 = objJson.getString("NameHIVTestKit3");
                        String BatchNo3 = objJson.getString("BatchNo3");
                        String ExpDate3 = objJson.getString("ExpDate3");
                        String Antibodies13 = objJson.getString("Antibodies13");
                        String Antibodies23 = objJson.getString("Antibodies23");
                        String Antibodies33 = objJson.getString("Antibodies33");

                        String negative_HIV = objJson.getString("negative_HIV");
                        String positive_HIV_1 = objJson.getString("positive_HIV_1");
                        String positive_HIV = objJson.getString("positive_HIV");
                        String HIV_Anitibodies = objJson.getString("HIV_Anitibodies");

                        String Result_Status = objJson.getString("HIV_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);

                        values.put("date_time", Actdate);
                        values.put("LabId", LabId);
                        values.put("blood_datetime", blood_datetime);

                        values.put("NameHIVTestKit1", NameHIVTestKit1);
                        values.put("BatchNo1", BatchNo1);
                        values.put("ExpDate1", ExpDate1);
                        values.put("Antibodies11", Antibodies11);
                        values.put("Antibodies21", Antibodies21);
                        values.put("Antibodies31", Antibodies31);

                        values.put("NameHIVTestKit2", NameHIVTestKit2);
                        values.put("BatchNo2", BatchNo2);
                        values.put("ExpDate2", ExpDate2);
                        values.put("Antibodies12", Antibodies12);
                        values.put("Antibodies22", Antibodies22);
                        values.put("Antibodies32", Antibodies32);

                        values.put("NameHIVTestKit3", NameHIVTestKit3);
                        values.put("BatchNo3", BatchNo3);
                        values.put("ExpDate3", ExpDate3);
                        values.put("Antibodies13", Antibodies13);
                        values.put("Antibodies23", Antibodies23);
                        values.put("Antibodies33", Antibodies33);

                        values.put("negative_HIV", negative_HIV);
                        values.put("positive_HIV_1", positive_HIV_1);
                        values.put("positive_HIV", positive_HIV);
                        values.put("HIV_Anitibodies", HIV_Anitibodies);
                        values.put("Result_Status", Result_Status);


                        //data iruntha update else insert
                        boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_HIV_Report where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_HIV_Report", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_HIV_Report", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }

    }

    private String postIsUpdateMaxRESTMethod(String isUpdateMax, String methodName) throws SchemaException, InterruptedException, ExecutionException {
        MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
        PostIsUpdateMaxFactory postDoctorIdFactory = new PostIsUpdateMaxFactory(magnetMobileClient);
        PostIsUpdateMax postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        PostIsUpdateMaxRequest body = new PostIsUpdateMaxRequest();
        body.setIsUpdateMax(isUpdateMax);
        body.setMethodName(methodName);

        Call<IsUpdateMaxResult> resultCall = postDoctorId.postIsUpdateMax(body, null);

        return resultCall.get().getResults();
    }

    private String postPatientIdListRESTMethod(String patientIdLIst, String methodName) throws SchemaException, InterruptedException, ExecutionException {
        MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
        PostPatientIdListFactory postDoctorIdFactory = new PostPatientIdListFactory(magnetMobileClient);
        PostPatientIdList postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        PostPatientIdListRequest body = new PostPatientIdListRequest();
        body.setPatientList(patientIdLIst);
        body.setMethodName(methodName);

        Call<PatientIdListResult> resultCall = postDoctorId.postPatientIdList(body, null);

        return resultCall.get().getResults();
    }

    private String getDoctorIdRESTCALL(String doctorID, String methodName) throws SchemaException, InterruptedException, ExecutionException {
        MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
        PostDoctorIdFactory postDoctorIdFactory = new PostDoctorIdFactory(magnetMobileClient);
        PostDoctorId postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        PostDoctorIdRequest body = new PostDoctorIdRequest();
        body.setDoctorID(doctorID);
        body.setMethodName(methodName);

        Call<DoctorIdResult> resultCall = postDoctorId.postDoctorId(body, null);

        return resultCall.get().getResults();
    }

    private String postMtestIdDidPId(String mtestid, String docid, String ptid, String methodName, String resultsRequestSOAP) {
        try {
            PosPatIdDocIdMtestIdNew posPatIdDocIdMtestIdNew;
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            PosPatIdDocIdMtestIdNewFactory controllerFactory = new PosPatIdDocIdMtestIdNewFactory(magnetClient);
            posPatIdDocIdMtestIdNew = controllerFactory.obtainInstance();
            String contentType = "application/json";
            //Set Body Data
            kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.beans.PosPatIdDocIdMtestIdRequest body = new kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.beans.PosPatIdDocIdMtestIdRequest();
            body.setDocId(docid);
            body.setMethodName(methodName);
            body.setPatId(ptid);
            body.setMtestId(mtestid);

            Call<kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.beans.PosPatIdDocIdMtestIdResult> resultCall = posPatIdDocIdMtestIdNew.posPatIdDocIdMtestId(
                    contentType,
                    body, null);

            resultsRequestSOAP = resultCall.get().getResults();
        } catch (SchemaException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resultsRequestSOAP;
    }

    private void UpdateInCaseNotes(String PatId, String mTestId, String alltest) {
        String subtestName = "";
        String subtestValue = "";
        String bps = "0";
        String bpd = "0";
        String temperature = "0";
        String weight = "0";

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            Cursor c = db.rawQuery("select subtestname,IFNULL(testvalue,'0')as testvalue,IFNULL(bps,'0')as bps,IFNULL(bpd,'0')as bpd,IFNULL(temperature,'0')as temperature,weight from Medicaltestdtls where Ptid = '" + PatId + "' and mtestid = '" + mTestId + "' and alltest = '" + alltest + "' ", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        subtestName = c.getString(c.getColumnIndex("subtestname"));
                        subtestValue = c.getString(c.getColumnIndex("testvalue"));

                        bps = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("bps")));
                        bpd = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("bpd")));
                        temperature = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("temperature")));

                        weight = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("weight")));


                    } while (c.moveToNext());
                }
            }

            db.close();
            Objects.requireNonNull(c).close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        PatientData data = getPatientName(PatId);

        String[] split_subtest;

        split_subtest = alltest.split("/");

        String compareRBSName = "blood sug random";
        if (split_subtest[1].trim().equalsIgnoreCase(compareRBSName)) {

            ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);

            values.put("FBS", 0);//fbs->bps
            values.put("PPS", 0);//pps->bpd
            values.put("RBS", temperature);//rbs->temperature

            values.put("BpS", 0);
            values.put("BpD", 0);
            values.put("temperature", 0);

            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }
        String compareFBSName = "blood sug fasting";
        if (split_subtest[1].trim().equalsIgnoreCase(compareFBSName)) {


            ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);

            values.put("FBS", bps);
            values.put("PPS", 0);
            values.put("RBS", 0);

            values.put("BpS", 0);
            values.put("BpD", 0);
            values.put("temperature", 0);

            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }
        String comparePBSName = "blood sug pp";
        if (split_subtest[1].trim().equalsIgnoreCase(comparePBSName)) {


            ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);

            values.put("FBS", 0);
            values.put("PPS", bpd);
            values.put("RBS", 0);

            values.put("BpS", 0);
            values.put("BpD", 0);
            values.put("temperature", 0);

            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }

    }

    private PatientData getPatientName(String patId) {
        PatientData data = new ImportWebservices_NODEJS.PatientData();


        try {

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            Cursor c = db.rawQuery("select * from patreg where patid  = '" + patId.trim() + '\'', null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        data.pName = c.getString(c.getColumnIndex("patientname"));
                        data.age = c.getString(c.getColumnIndex("age"));
                        data.gender = c.getString(c.getColumnIndex("gender"));
                        data.mobNum = c.getString(c.getColumnIndex("phone"));


                    } while (c.moveToNext());
                }
            }

            db.close();
            Objects.requireNonNull(c).close();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return data;

    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private void getOperationDetails(String patids) {
        //String url = BaseConfig.URL + "/GetPatientOperationInfo?PatientId=" + patid;
        //Log.e("Mast Operation url", url);

        // TODO: with response

        ContentValues values1;
        try {
            MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
            PostPatIdFactory postDoctorIdFactory = new PostPatIdFactory(magnetMobileClient);
            PostPatId postDoctorId = postDoctorIdFactory.obtainInstance();

            //Set Body Data
            PosPatIdRequest body = new PosPatIdRequest();
            body.setPatientId(patids);


            Call<PosPatIdResult> resultCall = postDoctorId.posPatId(body, null);

            String resultsRequestSOAP = resultCall.get().getResults();


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);
                    String Remark_Image;
                    String ServerId = String.valueOf(objJson.getString("operationId"));
                    String OperationNo = String.valueOf(objJson.getString("OperationNo"));
                    String OperationName = String.valueOf(objJson.getString("OperationName"));
                    String patid = String.valueOf(objJson.getString("patid"));
                    String PatientName = String.valueOf(objJson.getString("PatientName"));
                    String Gender = String.valueOf(objJson.getString("Gender"));
                    String ScheduleDate = String.valueOf(objJson.getString("ScheduleDate"));
                    String Fromtime = String.valueOf(objJson.getString("Fromtime"));
                    String Totime = String.valueOf(objJson.getString("Totime"));
                    String Rate = String.valueOf(objJson.getString("Rate"));
                    String IsActive = String.valueOf(objJson.getString("IsActive"));
                    String curdate = String.valueOf(objJson.getString("curdate"));
                    String IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    String IsCancel = String.valueOf(objJson.getString("IsCancel"));
                    String Remarks_Cancel = String.valueOf(objJson.getString("Remarks_Cancel"));
                    String Status = String.valueOf(objJson.getString("Status"));

                    String Oper_Notes = String.valueOf(objJson.getString("Oper_Notes"));
                    String Position = String.valueOf(objJson.getString("Position"));
                    String OperationProcedure = String.valueOf(objJson.getString("OperationProcedure"));
                    String Post_Oper_Diagnosis = String.valueOf(objJson.getString("Post_Oper_Diagnosis"));
                    String Post_Oper_Instruction = String.valueOf(objJson.getString("Post_Oper_Instruction"));
                    String Closure = String.valueOf(objJson.getString("Closure"));
                    String Pre_Oper_Diagnosis = String.valueOf(objJson.getString("Pre_Oper_Diagnosis"));


                    String Doctor_Name = String.valueOf(objJson.getString("doctorname"));
                    Remark_Image = String.valueOf(objJson.getString("Remark_Image"));

                    Remark_Image = BaseConfig.saveImageToSDCard(Remark_Image, ServerId);

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("OperationNo", OperationNo);
                    values1.put("OperationName", OperationName);
                    values1.put("patid", patid);
                    values1.put("PatientName", PatientName);
                    values1.put("Gender", Gender);
                    values1.put("ScheduleDate", ScheduleDate);
                    values1.put("Fromtime", Fromtime);
                    values1.put("Totime", Totime);
                    values1.put("Rate", Rate);
                    values1.put("IsActive", IsActive);
                    values1.put("curdate", curdate);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsCancel", IsCancel);
                    values1.put("Remarks_Cancel", Remarks_Cancel);
                    values1.put("Status", Status);
                    values1.put("DoctorName", Doctor_Name);
                    values1.put("Oper_Notes", Oper_Notes);
                    values1.put("Position", Position);
                    values1.put("OperationProcedure", OperationProcedure);
                    values1.put("Post_Oper_Diagnosis", Post_Oper_Diagnosis);
                    values1.put("Post_Oper_Instruction", Post_Oper_Instruction);
                    values1.put("Closure", Closure);
                    values1.put("Pre_Oper_Diagnosis", Pre_Oper_Diagnosis);
                    values1.put("Remark_Image", Remark_Image);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from mast_Operation where ServerId=" + ServerId);

                    SQLiteDatabase db = BaseConfig.GetDb();//ctx);
                    if (GetStatus) {
                        db.update("mast_Operation", values1, "ServerId=" + ServerId, null);

                    } else {
                        db.insert("mast_Operation", null, values1);
                    }


                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getBindUpload(String patid, String mtestid1) {


        SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;

        try {

            String MethodName = "Import_Upload";
            String resultsRequestSOAP = postPatidMtestId(patid, mtestid1, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);
                    String Remark_Image;
                    String ServerId = String.valueOf(objJson.getString("id"));
                    String Patid = String.valueOf(objJson.getString("Patid"));
                    String Fname = String.valueOf(objJson.getString("Fname"));
                    String Filetype = String.valueOf(objJson.getString("Filetype"));
                    String Type = String.valueOf(objJson.getString("Type"));
                    String Cdtime = String.valueOf(objJson.getString("Cdtime"));
                    String Docid = String.valueOf(objJson.getString("Docid"));
                    String Upload_Base64 = String.valueOf(objJson.getString("Upload_Base64"));
                    String mtestid = String.valueOf(objJson.getString("mtestid"));
                    String Report_Remarks = String.valueOf(objJson.getString("image_remarks"));


                    //Log.e("getBindUpload: Upload_Base64: ", Upload_Base64);

                    Bitmap theBitmap = null;

                    theBitmap = BaseConfig.Glide_GetBitmap(ImportWebservices_NODEJS.ctx, Upload_Base64);//Glide.with(this.ctx).load(Upload_Base64).asBitmap().into(-1, -1).get();

                    String ReportPhoto = null;
                    try {
                        ReportPhoto = BaseConfig.saveURLImagetoSDcard1(theBitmap, ServerId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Patid", Patid);
                    values1.put("Fname", Fname);
                    values1.put("Filetype", Filetype);
                    values1.put("Type", Type);
                    values1.put("Cdtime", Cdtime);
                    values1.put("Docid", Docid);
                    values1.put("Remarks", Report_Remarks);
                    values1.put("Upload_Base64", ReportPhoto);
                    values1.put("mtestid", mtestid);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Bind_Upload where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("Bind_Upload", values1, "ServerId=" + ServerId, null);

                    } else {
                        db.insert("Bind_Upload", null, values1);
                    }

                }

                db.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String postPatidMtestId(String patId, String mtestId, String methodName) throws SchemaException, InterruptedException, ExecutionException {
        MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
        PostPatidMtestidFactory postDoctorIdFactory = new PostPatidMtestidFactory(magnetMobileClient);
        PostPatidMtestid postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        PostPatidMtestidRequest body = new PostPatidMtestidRequest();
        body.setMethodName(methodName);
        body.setMtestId(mtestId);
        body.setPatId(patId);

        Call<PatidMtestidResult> resultCall = postDoctorId.postPatidMtestid(body, null);

        return resultCall.get().getResults();
    }

    private void LoadInvestigation_Notification() {

        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {

            this.getPatientDetails();
        }

    }

    private final ArrayList<NotificationGetSet> getPatientDetails() {
        ArrayList<NotificationGetSet> value = new ArrayList<>();
        //Original Query
        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";
        //Testing Purpose
        //String Query = "select Id as dstatus1  from Medicaltestdtls ";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid", null);
            //Cursor c = db.rawQuery("select *  from Medicaltestdtls limit 6", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        int ID = Integer.parseInt(c.getString(c.getColumnIndex("id")));
                        String PATIENT_ID = c.getString(c.getColumnIndex("Ptid"));
                        String MEDICAL_TEST_ID = c.getString(c.getColumnIndex("mtestid"));
                        String PATIENT_NAME = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PATIENT_ID + "'");

                        this.CreateNewNotification(PATIENT_ID, PATIENT_NAME +"-"+ PATIENT_ID, MEDICAL_TEST_ID, ID);


                    }
                    while (c.moveToNext());
                }
            }

            c.close();
            db.close();
        }

        return value;
    }

    private void CreateNewNotification(String patienid, String patid_name, String medid, int ids) {
        Notification foregroundNote;
        RemoteViews bigView = new RemoteViews(ImportWebservices_NODEJS.ctx.getPackageName(), layout.notification_layout_big);

        bigView.setImageViewBitmap(id.patient_image, this.getPatientBitmpaImage(patienid));
        bigView.setTextViewText(id.patientnotification_id, medid);
        bigView.setTextViewText(id.patient_name, patid_name);


        Intent showFullQuoteIntent = new Intent(ImportWebservices_NODEJS.ctx, MyPatientDrawer.class);
        showFullQuoteIntent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, patienid);

        // both of these approaches now work: FLAG_CANCEL, FLAG_UPDATE; the uniqueInt may be the real solution.
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(ImportWebservices_NODEJS.ctx, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // bigView.setOnClickPendingIntent() etc..
        Builder mNotifyBuilder = new Builder(ImportWebservices_NODEJS.ctx);
        foregroundNote = mNotifyBuilder.setContentTitle("Investigation Results - Expand to view")
                .setContentText(patid_name)
                .setSmallIcon(drawable.logo_high_pix)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        foregroundNote.bigContentView = bigView;

        // now show notification..
        NotificationManager mNotifyManager = (NotificationManager) ImportWebservices_NODEJS.ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        // tapping notification will open MainActivity


        mNotifyManager.notify(ids, foregroundNote);
    }

    public Bitmap getPatientBitmpaImage(String patient_Id) {

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery("select PC from Patreg where Patid='" + patient_Id + "'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    try {
                        File sd = Environment.getExternalStorageDirectory();
                        File image = new File(c.getString(c.getColumnIndex("PC")));
                        Options bmOptions = new Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
                        return bitmap;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } while (c.moveToNext());
            }
        }
        return null;
    }


    private ArrayList<NotificationListGetSet> getTableData(String patientid) {
        ArrayList<NotificationListGetSet> values = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();

        Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') and Ptid='" + patientid + '\'', null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String va = "";

                    if (ImportWebservices_NODEJS.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareRBSName)) {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("temperature")), c.getString(c.getColumnIndex("testsummary"))));


                    } else if (ImportWebservices_NODEJS.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareFBSName)) {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bps")), c.getString(c.getColumnIndex("testsummary"))));

                    } else if (ImportWebservices_NODEJS.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.comparePBSName)) {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bpd")), c.getString(c.getColumnIndex("testsummary"))));

                    } else {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("testvalue")), c.getString(c.getColumnIndex("testsummary"))));
                    }


                } while (c.moveToNext());
            }
        }
        c.close();
        return values;
    }

    static class PatientData {
        // --Commented out by Inspection (25-Apr-18 4:36 PM):String patId = "";
        String pName = "";
        String gender = "";
        String age = "";
        String mobNum = "";


        PatientData() {
        }

    }

    //============================================================================================================
    //============================================================================================================


}//END
