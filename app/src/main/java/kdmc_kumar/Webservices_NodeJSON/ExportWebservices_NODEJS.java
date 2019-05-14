package kdmc_kumar.Webservices_NodeJSON;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.async.StateChangedListener;
import com.magnet.android.mms.exception.SchemaException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Webservices_NodeJSON.ExportData.controller.api.Export_All_DataFactory;
import kdmc_kumar.Webservices_NodeJSON.ExportData.model.beans.Export_DatasRequest;
import kdmc_kumar.Webservices_NodeJSON.ExportData.model.beans.Export_DatasResult;
import kdmc_kumar.Webservices_NodeJSON.exportCaseNotes.controller.api.ExportCaseNotes;
import kdmc_kumar.Webservices_NodeJSON.exportCaseNotes.controller.api.ExportCaseNotesFactory;
import kdmc_kumar.Webservices_NodeJSON.exportCaseNotes.model.beans.ExportCaseNotesRequest;
import kdmc_kumar.Webservices_NodeJSON.exportCaseNotes.model.beans.ExportCaseNotesResult;


@SuppressWarnings("ALL")
public class ExportWebservices_NODEJS {


    public static int mYear;
    public static int mMonth;
    public static int mDay;
    public static int mcYear;
    public static int mcMonth;
    public static int mcDay;
    public String compareRBSName = "blood sug random";
    public String compareFBSName = "blood sug fasting";
    public String comparePBSName = "blood sug p.p.";
    BaseConfig bc = new BaseConfig();
    Context ctx;
    int serverResponseCode = 0;
    String returnid = "";
    String docid, device_deactivate, device_status;

    public ExportWebservices_NODEJS(Context ctx) {
        this.ctx = ctx;
    }

    public static String postDataExportData(String methodName, String jsonData, Context context) throws SchemaException, InterruptedException, ExecutionException {
        ExportCaseNotes exportCaseNotes;
        // Instantiate a controller
        MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(context);
        ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
        exportCaseNotes = controllerFactory.obtainInstance();

        String contentType = "application/json";
        ExportCaseNotesRequest body = new ExportCaseNotesRequest();
        body.setMethodName(methodName);
        body.setJsonValue(jsonData);

        Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(
                contentType,
                body, null);


        ExportCaseNotesResult result = callObject.get();

        return result.getResults();
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

    public void ExecuteAll() {
        Log.e("Executing Webservices 2", "OnReady Execute All Called 2");

        if (BaseConfig.CheckNetwork(ctx)) {

            try {

                if (CheckNodeServer()) {

                    Log.e("###########", "################");
                    Log.e("MobyDoctor BackGround", "Thread Export Service running 2");
                    Log.e("MobyDoctor BackGround", "Thread Export Service running 2");
                    Log.e("MobyDoctor BackGround", "Thread Export Service running 2");
                    Log.e("###########", "################");

                    //Patient Registration
                    EXPORT_PATIENT_REGISTRATION();

                    //Clinical Information
                    EXPORT_CLINICALINFORMATION();

                    //Vaccination
                    EXPORT_VACCINATIONDTLS();

                    //Casenotes
                    EXPORT_CASENOTES();

                    //Investigation
                    ExportInvestigation();
                    Export_InvestigationDetails();
                    Export_ScanList();
                    Export_XrayList();
                    Export_ECG();
                    Export_EEG();
                    Export_Angiogram();


                    //Medicine Prescription
                    EXPORT_AIO_MEDICINE_PRESCRIPTION();
                    EXPORT_MEDICINE_PRESCRIPTION_DETAILS();
                    Export_Emergency();


                    //Specialist Referrals
                    EXPORT_DOCTOR_REFERRALS();


                    //Inpatient - Details
                    ExportInPatientRequest();
                    //EXPORT_INPATIENT();
                    EXPORT_INPATIENT_MAIN();
                    EXPORT_INPATIENT_DIABETIC();
                    EXPORT_INPATIENT_TEMPERATURE();
                    EXPORT_INPATIENT_SURGERY();
                    EXPORT_INPATIENT_MEDICALCASE_RECORD();
                   // EXPORT_INPATIENT_HOSPITAL_DETAILS();


                    Export_DietEntry();
                    delete_DietEntry();
                    UpdateOperation();
                    ExportInPatientDichargeRequest();


                    //Doctor - detailsl
                    ExportMobyDoctorSettings();
                    //ExportMobyDoctorUpdatedInformation();
                    ExportConsultationStatus();


                    ExportReportGallery_New();
                    //ExportRoomAvalibility();
                    ExportDietDtls();//SP completed//TODO  Not Use
                    ExportNewMedicine();
                    ExportNewDiagnosis();
                    ExportNewTreatment();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public void EXPORT_PATIENT_REGISTRATION() {

        try {
            SQLiteDatabase db = BaseConfig.GetDb();
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            String Query = "select * from Bind_Patient_Registration where IsUpdate=0";
            Cursor c = db.rawQuery(Query, null);

            String Local_Id = "";
            String Local_PatientId = "";


            String str_PatientName="";String str_Age= "";String str_Gender= "";String str_Country= "";String str_State = "";String str_City= "";String str_WillingtoReceive= "";String str_FeeExemption = "";String str_Mobile1= "";String str_FeeExemptionCategory = "";String str_Address1 = "";String str_Bloodgroup = "";String str_Marital= "";String str_RelationShipPrefix= "";String str_FatherName = "";String str_DateOfBirth= "";String str_Caste = "";String str_Income= "";String str_Occupation = "";String str_Address2 = "";String str_Pincode= "";String str_Email = "";String str_MobileNo2= "";String str_Caretaker= "";String str_CaretakerNumber= "";String str_Relationship = "";String str_PreferredNumber= "";String str_WillingtoDonateBlood = "";String str_WillingtoDonateEyes= "";String str_ReferredDoctorName= "";String str_ReferredDoctorMobileNo = "";String str_PolicyName = "";String str_NameOfInsuranceCompany = "";String str_AmountInsured= "";String str_ValidTill= "";String str_AuthorizedHospitals= "";String str_AadhardCard= "";String str_BPLCard= "";String str_IsUpdate = "";String str_IsActive = "";String str_ServerId = "";String str_ActDate= "";String str_Photo_Path = "";String str_PatientId= "";String str_Patient_Prefix = "";

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();
                        Local_Id = c.getString(c.getColumnIndex("Id"));
                        Local_PatientId = c.getString(c.getColumnIndex("PatientId"));

                        from_db_obj.put("str_LocalId",Local_Id);
                        from_db_obj.put("str_PatientName",c.getString(c.getColumnIndex("PatientName")));
                        from_db_obj.put("str_Age",c.getString(c.getColumnIndex("Age")));
                        from_db_obj.put("str_Gender",c.getString(c.getColumnIndex("Gender")));
                        from_db_obj.put("str_Country",c.getString(c.getColumnIndex("Country")));
                        from_db_obj.put("str_State",c.getString(c.getColumnIndex("State")));
                        from_db_obj.put("str_City",c.getString(c.getColumnIndex("City")));
                        from_db_obj.put("str_WillingtoReceive",c.getString(c.getColumnIndex("WillingtoReceive")));
                        from_db_obj.put("str_FeeExemption",c.getString(c.getColumnIndex("FeeExemption")));
                        from_db_obj.put("str_Mobile1",c.getString(c.getColumnIndex("Mobile1")));
                        from_db_obj.put("str_FeeExemptionCategory",c.getString(c.getColumnIndex("FeeExemptionCategory")));
                        from_db_obj.put("str_Address1",c.getString(c.getColumnIndex("Address1")));
                        from_db_obj.put("str_Bloodgroup",c.getString(c.getColumnIndex("Bloodgroup")));
                        from_db_obj.put("str_Marital",c.getString(c.getColumnIndex("Marital")));
                        from_db_obj.put("str_RelationShipPrefix",c.getString(c.getColumnIndex("RelationShipPrefix")));
                        from_db_obj.put("str_FatherName",c.getString(c.getColumnIndex("FatherName")));
                        from_db_obj.put("str_DateOfBirth",c.getString(c.getColumnIndex("DateOfBirth")));
                        from_db_obj.put("str_Caste",c.getString(c.getColumnIndex("Caste")));
                        from_db_obj.put("str_Income",c.getString(c.getColumnIndex("Income")));
                        from_db_obj.put("str_Occupation",c.getString(c.getColumnIndex("Occupation")));
                        from_db_obj.put("str_Address2",c.getString(c.getColumnIndex("Address2")));
                        from_db_obj.put("str_Pincode",c.getString(c.getColumnIndex("Pincode")));
                        from_db_obj.put("str_Email",c.getString(c.getColumnIndex("Email")));
                        from_db_obj.put("str_MobileNo2",c.getString(c.getColumnIndex("MobileNo2")));
                        from_db_obj.put("str_Caretaker",c.getString(c.getColumnIndex("Caretaker")));
                        from_db_obj.put("str_CaretakerNumber",c.getString(c.getColumnIndex("CaretakerNumber")));
                        from_db_obj.put("str_Relationship",c.getString(c.getColumnIndex("Relationship")));
                        from_db_obj.put("str_PreferredNumber",c.getString(c.getColumnIndex("PreferredNumber")));
                        from_db_obj.put("str_WillingtoDonateBlood",c.getString(c.getColumnIndex("WillingtoDonateBlood")));
                        from_db_obj.put("str_WillingtoDonateEyes",c.getString(c.getColumnIndex("WillingtoDonateEyes")));
                        from_db_obj.put("str_ReferredDoctorName",c.getString(c.getColumnIndex("ReferredDoctorName")));
                        from_db_obj.put("str_ReferredDoctorMobileNo",c.getString(c.getColumnIndex("ReferredDoctorMobileNo")));
                        from_db_obj.put("str_PolicyName",c.getString(c.getColumnIndex("PolicyName")));
                        from_db_obj.put("str_NameOfInsuranceCompany",c.getString(c.getColumnIndex("NameOfInsuranceCompany")));
                        from_db_obj.put("str_AmountInsured",c.getString(c.getColumnIndex("AmountInsured")));
                        from_db_obj.put("str_ValidTill",c.getString(c.getColumnIndex("ValidTill")));
                        from_db_obj.put("str_AuthorizedHospitals",c.getString(c.getColumnIndex("AuthorizedHospitals")));
                        from_db_obj.put("str_AadhardCard",c.getString(c.getColumnIndex("AadhardCard")));
                        from_db_obj.put("str_BPLCard",c.getString(c.getColumnIndex("BPLCard")));
                        from_db_obj.put("str_IsUpdate",c.getString(c.getColumnIndex("IsUpdate")));
                        from_db_obj.put("str_IsActive",c.getString(c.getColumnIndex("IsActive")));
                        from_db_obj.put("str_ActDate",c.getString(c.getColumnIndex("ActDate")));

                        String LocalPhotoPath = c.getString(c.getColumnIndex("Photo_Path"));
                        from_db_obj.put("str_Photo_Path", LocalPhotoPath);
                        ExportPatientImage(LocalPhotoPath, Local_PatientId);

                        from_db_obj.put("str_PatientId",c.getString(c.getColumnIndex("PatientId")));
                        from_db_obj.put("str_Patient_Prefix",c.getString(c.getColumnIndex("Patient_Prefix")));
                        from_db_obj.put("str_DoctorId",c.getString(c.getColumnIndex("DoctorId")));
                        from_db_obj.put("str_HID",c.getString(c.getColumnIndex("HID")));
                        from_db_obj.put("str_PinPass",c.getString(c.getColumnIndex("PinPass")));
                        from_db_obj.put("str_SpouseName",c.getString(c.getColumnIndex("SpouseName")));
                        from_db_obj.put("str_NamePrefix",  c.getString(c.getColumnIndex("NamePrefix")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }
            }

            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "Export_PatientInformation";
                String jsonData = export_jsonarray.toString();


                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String LocalId = jsonObject.getString("LocalId");
                    //String ServerId = jsonObject.getString("ServerId");


                    db.delete("Bind_Patient_Registration", " Id='"+Local_Id+"' ",null);


                }


            }


            c.close();
            db.close();
        } catch (SchemaException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void ExportPatientImage(String PhotoFilePath, String PatientId)
    {

        if(PhotoFilePath.isEmpty() || PhotoFilePath.equalsIgnoreCase(""))
        {

            PhotoFilePath = BaseConfig.AppDBFolderName + "/male_avatar.jpg";

        }

        File f = new File(PhotoFilePath);

        Future uploading = Ion.with(ctx)
                .load(BaseConfig.AppNodeIP+"/exportMastersSP/ImageUpload")
                .addHeader("FileName",PatientId)
                .setMultipartFile("File", f)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        try {

                            JSONObject jobj = new JSONObject(result.getResult());

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });



    }

    /**
     * @throws IOException
     * @throws XmlPullParserException
     * @Description New Webservice in Clinical Information
     */
    public void EXPORT_CLINICALINFORMATION() throws IOException,
            XmlPullParserException {

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        JSONObject from_db_obj = new JSONObject();
        JSONArray export_jsonarray = new JSONArray();


        try {
            String Query = "select Antental_val,Antental_others,HID,surgical_notes,id,ptid,pname,docid,Actdate,IsActive,pagegen,Previous_MedicalHistory," +
                    "Hereditary,AllergicTo,Perhis_Smoking,Perhis_Alcohol,Perhis_Tobacco,Perhis_Others,Medication_History,Family_Med_History,Perhis_BowelHabits," +
                    "Perhis_Micturition,present_illness,past_illness,any_medication,obstetric,gynaec from ClinicalInformation where Isupdate='0' limit 10;";

            Cursor c = db.rawQuery(Query, null);

            String Local_Id = "";


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        Local_Id = c.getString(c.getColumnIndex("id"));

                        from_db_obj.put("ptid", BaseConfig.CheckDBString(c.getString(c.getColumnIndex("ptid"))));
                        from_db_obj.put("pname", c.getString(c.getColumnIndex("pname")));
                        from_db_obj.put("docid", c.getString(c.getColumnIndex("docid")));
                        from_db_obj.put("pagegen", c.getString(c.getColumnIndex("pagegen")));
                        from_db_obj.put("Previous_MedicalHistory", c.getString(c.getColumnIndex("Previous_MedicalHistory")));
                        from_db_obj.put("Hereditary", c.getString(c.getColumnIndex("Hereditary")));
                        from_db_obj.put("AllergicTo", c.getString(c.getColumnIndex("AllergicTo")));
                        from_db_obj.put("Perhis_Smoking", c.getString(c.getColumnIndex("Perhis_Smoking")));
                        from_db_obj.put("Perhis_Alcohol", c.getString(c.getColumnIndex("Perhis_Alcohol")));
                        from_db_obj.put("Perhis_Tobacco", c.getString(c.getColumnIndex("Perhis_Tobacco")));
                        from_db_obj.put("Perhis_Others", c.getString(c.getColumnIndex("Perhis_Others")));
                        from_db_obj.put("Medication_History", c.getString(c.getColumnIndex("Medication_History")));
                        from_db_obj.put("Family_Med_History", c.getString(c.getColumnIndex("Family_Med_History")));
                        from_db_obj.put("Actdate", c.getString(c.getColumnIndex("Actdate")));
                        from_db_obj.put("Perhis_BowelHabits", c.getString(c.getColumnIndex("Perhis_BowelHabits")));
                        from_db_obj.put("Perhis_Micturition", c.getString(c.getColumnIndex("Perhis_Micturition")));
                        from_db_obj.put("present_illness", c.getString(c.getColumnIndex("present_illness")));
                        from_db_obj.put("past_illness", c.getString(c.getColumnIndex("past_illness")));
                        from_db_obj.put("any_medication", c.getString(c.getColumnIndex("any_medication")));
                        from_db_obj.put("obstetric", c.getString(c.getColumnIndex("obstetric")));
                        from_db_obj.put("gynaec", c.getString(c.getColumnIndex("gynaec")));
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        from_db_obj.put("surgical_notes", c.getString(c.getColumnIndex("surgical_notes")));
                        from_db_obj.put("HID", c.getString(c.getColumnIndex("HID")));
                        from_db_obj.put("Antental_val", c.getString(c.getColumnIndex("Antental_val")));
                        from_db_obj.put("Antental_others", c.getString(c.getColumnIndex("Antental_others")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }

                c.close();

            }


            if (export_jsonarray != null && export_jsonarray.length() > 0) {


                //====================================================================================
                //====================================================================================

                String methodName = "Export_ClinicalInformation";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String LocalId = jsonObject.getString("LocalId");
                    String ServerId = jsonObject.getString("Id");

                    ContentValues values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("isactive", 2);
                    values.put("ServerId", ServerId);
                    db.update("ClinicalInformation", values, "Id=" + LocalId + "", null);


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
    }


    /**
     * @throws IOException
     * @throws XmlPullParserException
     * @Description Vaccination Dtls
     */
    public void EXPORT_VACCINATIONDTLS() throws IOException,
            XmlPullParserException {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);

            StringBuilder str = new StringBuilder();
            String Query = "select IFNULL(HID,'')as HID,id,patid,pname,agegen,mobnum,drid,vaccinename,schedule,IFNULL(duedt,'')as duedt,(CASE givendt\n" +
                    " WHEN '-' THEN\n" +
                    " ''\n" +
                    " ELSE\n" +
                    " givendt\n" +
                    " END) as givendt,IFNULL(weight,'')as weight,dt,isactive from Vaccination where Isupdate='1' and isactive='1'";


            Cursor c = db.rawQuery(Query, null);

            String Local_Id = "";

            JSONArray export_jsonarray = new JSONArray();
            JSONObject from_db_obj = new JSONObject();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        Local_Id = c.getColumnName(c.getColumnIndex("id"));

                        from_db_obj = new JSONObject();
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        from_db_obj.put("patid", c.getString(c.getColumnIndex("patid")));
                        from_db_obj.put("pname", c.getString(c.getColumnIndex("pname")));
                        from_db_obj.put("agegen", c.getString(c.getColumnIndex("agegen")));
                        from_db_obj.put("mobnum", c.getString(c.getColumnIndex("mobnum")));
                        from_db_obj.put("drid", c.getString(c.getColumnIndex("drid")));
                        from_db_obj.put("vaccinename", c.getString(c.getColumnIndex("vaccinename")));
                        from_db_obj.put("schedule", c.getString(c.getColumnIndex("schedule")));
                        from_db_obj.put("duedt", c.getString(c.getColumnIndex("duedt")));
                        from_db_obj.put("givendt", c.getString(c.getColumnIndex("givendt")));
                        from_db_obj.put("weight", c.getString(c.getColumnIndex("weight")));
                        from_db_obj.put("dt", c.getString(c.getColumnIndex("dt")));
                        from_db_obj.put("isactive", c.getString(c.getColumnIndex("isactive")));
                        from_db_obj.put("HID", c.getString(c.getColumnIndex("HID")));
                        export_jsonarray.put(from_db_obj);


                    } while (c.moveToNext());
                }

                c.close();

            }


            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "Export_Vaccination";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String LocalId = jsonObject.getString("LocalId");
                    String ServerId = jsonObject.getString("Id");


                    ContentValues values = new ContentValues();
                    values.put("Isupdate", 2);
                    values.put("isactive", 2);
                    values.put("ServerId", ServerId);

                    db.update("Vaccination", values, "Id=" + Local_Id + "", null);


                }

            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void EXPORT_CASENOTES() {
        try {
            String Query = "";
            Query = "SELECT A.id AS A_ID, A.Actdate, A.IsActive, A.pagegen, A.Isupdate as A_ISUPDATE, A.Anaemic, A.Icterus, A.Stenosis, A.Clubbing, A.Limbphantom, A.Vericoveins, A.Pedal_edema, A.built, A.extra_generalexam, A.HID, A.IsUpdateMax, A.ServerId, A.skin_infection, A.short_stat, A.pog, A.pallor, A.oedema, A.fundalheight, A.lie, A.fetalmovement, A.fetalheight, A.pv, A.anycompl, A.DiagId, B.id AS B_ID, B.Breathsound, B.Trachea, B.Kyphosis_Scoliosis, B.Crepitation, B.Bronchi, B.Pulserate AS B_Pulserate, B.Note, B.shapeofchest, B.spo2, B.BreathSoundEqual, B.BreathSoundEqualOptions, B.Isupdate AS B_Isupdate, C.id AS C_ID, C.Beat, C.Murmur, C.Pericardial_Rub, C.Pulserate AS C_Pulserate, C.heartrate, C.Isupdate AS C_Isupdate, E.id AS E_ID, E.Abdomen, E.Bowelsound, E.Spleen, E.Liver, E.PalpableMasses, E.Hernial, E.shapeofabdomen, E.Visible_Pulsations, E.Visual_Peristalsis, E.Abdominal_Palpation, E.Splenomegaly, E.Hepatomegaly, E.organomegely, E.freefuild, E.distension, E.tenderness, E.scrotum, E.Isupdate AS E_Isupdate, F.id AS F_ID, F.Pupilsize, F.Speech, F.Carodit, F.Amnesia, F.Apraxia, F.Cognitive_Function, F.Bulk, F.Tone, F.Power_LUL, F.Power_RUL, F.Power_LLL, F.Power_RLL, F.Sensory, F.Reflexes_Corneal, F.Reflexes_Biceps, F.Reflexes_Triceps, F.Reflexes_Supinator, F.Reflexes_Knee, F.Reflexes_Ankle, F.Reflexes_Plantor, F.congentail_abnormality, F.mentalstatus, F.Isupdate AS F_Isupdate, G.id AS G_ID, G.oriented, G.neckrigidity, G.confused, G.exaggerated, G.extensor, G.gsscore, G.incomprehensible, G.depressed, G.flexor, G.coma, G.Isupdate AS G_Isupdate, H.id AS H_ID, H.symmetry, H.smooth_movement, H.arms_swing, H.pelvic_tilt, H.stride_length, H.cervical_lordosis, H.lumbar_lordosis, H.kyphosis, H.scoliosis, H.llswelling, H.lldeformity, H.lllimbshortening, H.llmuscle_wasting, H.llremarks, H.ulswelling, H.uldeformity, H.ullimbshortening, H.ulmuscle_wasting, H.ulremarks, H.Isupdate AS H_Isupdate, I.id AS I_ID, I.Othersystem, I.AdditionalInfo, I.Isupdate AS I_Isupdate, J.id AS J_ID, J.Heamoglobin, J.wbc, J.rbc, J.polymorphs, J.lymphocytes, J.monocytes, J.cl, J.basophils, J.eosinophils, J.esr, J.urea, J.creatinine, J.NA, J.K, J.HCO3, J.UricAcid, J.Sodium, J.Potassium, J.Chloride, J.Bicarbonate, J.TotalCholesterol, J.LDL, J.HDL, J.VLDL, J.Triglycerides, J.Serumbilirubin, J.Direct, J.Indirect, J.Totalprotein, J.Albumin, J.Globulin, J.SGOT, J.SGPT, J.AlkalinePhosphatase, J.FreeT3, J.FreeT4, J.TSH, J.Isupdate AS J_Isupdate, K.id AS K_ID, K.dysuria, K.pyuria, K.haematuria, K.oliguria, K.polyuria, K.anuria, K.nocturia, K.urethraldischarge, K.prostate, K.Isupdate AS K_Isupdate, L.id AS L_ID, L.Endocrine, L.Isupdate AS L_Isupdate, M.id AS M_ID, M.pnc_details, M.ppc_details, M.cob_details, M.Isupdate AS M_Isupdate, N.Id AS N_ID, N.upper_right_jaw, N.upper_left_jaw, N.lower_right_jaw, N.lower_left_jaw, N.dental_sys, N.Isupdate AS N_Isupdate, MAIN.id AS MAIN_ID, MAIN.Docid AS MAIN_DOCID, MAIN.Ptid AS MAIN_PTID, MAIN.DiagId AS MAIN_DIAGID, MAIN.pname, MAIN.gender, MAIN.age, MAIN.mobnum, MAIN.refdocname, MAIN.clinicname, MAIN.Diagnosis, MAIN.BpS, MAIN.BpD, MAIN.FBS, MAIN.PPS, MAIN.RBS, MAIN.PWeight, MAIN.temperature, MAIN.testresult, MAIN.Ecg, MAIN.Ecgfile, MAIN.Scan, MAIN.Scanfile, MAIN.Test, MAIN.Actdate AS MAIN_ACTDATE, MAIN.IsActive AS MAIN_ISACTIVE, MAIN.treatmentfor, MAIN.bloodgroup, MAIN.ecgrate, MAIN.ecgrhyrhm, MAIN.ecgpulserate, MAIN.ecgqrs, MAIN.ecgst, MAIN.ecgt, MAIN.ecgaxis, MAIN.ecgbundlebranch, MAIN.ecgconductiondefects, MAIN.ecgtreadmill, MAIN.imeino, MAIN.docsign, MAIN.Isupdate AS MAIN_ISUPDATE, MAIN.height, MAIN.bmi, MAIN.xrayremrk, MAIN.HID AS MAIN_HID, MAIN.ServerId AS MAIN_SERVERID, MAIN.Signs FROM Diagonis AS MAIN LEFT JOIN CaseNote_GeneralExamination AS A ON MAIN.DiagId = A.DiagId AND (A.Isupdate = '0' OR A.Isupdate = '2') LEFT JOIN CaseNote_RespiratorySystem AS B ON MAIN.DiagId = B.DiagId AND (B.Isupdate = '0' OR B.Isupdate = '2') LEFT JOIN CaseNote_Cardiovascular AS C ON MAIN.DiagId = C.DiagId AND (C.Isupdate = '0' OR C.Isupdate = '2') LEFT JOIN CaseNote_Gastrointestinal AS E ON MAIN.DiagId = E.DiagId AND (E.Isupdate = '0' OR E.Isupdate = '2') LEFT JOIN CaseNote_Neurology AS F ON MAIN.DiagId = F.DiagId AND (F.Isupdate = '0' OR F.Isupdate = '2') LEFT JOIN CaseNote_CNS AS G ON MAIN.DiagId = G.DiagId AND (G.Isupdate = '0' OR G.Isupdate = '2') LEFT JOIN CaseNote_Locomotor AS H ON MAIN.DiagId = H.DiagId AND (H.Isupdate = '0' OR H.Isupdate = '2') LEFT JOIN CaseNote_OtherSystem AS I ON MAIN.DiagId = I.DiagId AND (I.Isupdate = '0' OR I.Isupdate = '2') LEFT JOIN CaseNote_ClinicalData AS J ON MAIN.DiagId = J.DiagId AND (J.Isupdate = '0' OR J.Isupdate = '2') LEFT JOIN CaseNote_Renal AS K ON MAIN.DiagId = K.DiagId AND (K.Isupdate = '0' OR K.Isupdate = '2') LEFT JOIN CaseNote_Endocrine AS L ON MAIN.DiagId = L.DiagId AND (L.Isupdate = '0' OR L.Isupdate = '2') LEFT JOIN CaseNote_PNCSystem AS M ON MAIN.DiagId = M.DiagId AND (M.Isupdate = '0' OR M.Isupdate = '2') LEFT JOIN CaseNote_DentalSystem AS N ON MAIN.DiagId = N.DiagId AND (N.Isupdate = '0' OR N.Isupdate = '2') WHERE (MAIN.Isupdate = '0' OR MAIN.Isupdate = '2') ";

            String DataSet = getResults(Query).toString();

            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                ExportCaseNotes exportCaseNotes;
                MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
                ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
                exportCaseNotes = controllerFactory.obtainInstance();


                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("INSERT_ALL_CASENOTES");
                body.setJsonValue(DataSet);

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(
                        contentType,
                        body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String Diagonis_Id = jsonObject.getString("Diagonis_Id");
                    String Diagonis_LocalId = jsonObject.getString("Diagonis_LocalId");
                    String GENERAL_INFO_Id = jsonObject.getString("GENERAL_INFO_Id");
                    String GENERAL_INFO_LocalId = jsonObject.getString("GENERAL_INFO_LocalId");
                    String CARDIO_Id = jsonObject.getString("CARDIO_Id");
                    String CARDIO_LocalId = jsonObject.getString("CARDIO_LocalId");
                    String RESPIRATORY_Id = jsonObject.getString("RESPIRATORY_Id");
                    String RESPIRATORY_LocalId = jsonObject.getString("RESPIRATORY_LocalId");
                    String GASTRO_Id = jsonObject.getString("GASTRO_Id");
                    String GASTRO_LocalId = jsonObject.getString("GASTRO_LocalId");
                    String NEURO_Id = jsonObject.getString("NEURO_Id");
                    String NEURO_LocalId = jsonObject.getString("NEURO_LocalId");
                    String LOCOMOTOR_Id = jsonObject.getString("LOCOMOTOR_Id");
                    String LOCOMOTOR_LocalId = jsonObject.getString("LOCOMOTOR_LocalId");
                    String RENAL_Id = jsonObject.getString("RENAL_Id");
                    String RENAL_LocalId = jsonObject.getString("RENAL_LocalId");
                    String ENDOCRINE_Id = jsonObject.getString("ENDOCRINE_Id");
                    String ENDOCRINE_LocalId = jsonObject.getString("ENDOCRINE_LocalId");
                    String CLINICAL_Id = jsonObject.getString("CLINICAL_Id");
                    String CLINICAL_LocalId = jsonObject.getString("CLINICAL_LocalId");
                    String DENTAL_Id = jsonObject.getString("DENTAL_Id");
                    String DENTAL_LocalId = jsonObject.getString("DENTAL_LocalId");
                    String OTHER_SYSTEM_Id = jsonObject.getString("OTHER_SYSTEM_Id");
                    String OTHER_SYSTEM_LocalId = jsonObject.getString("OTHER_SYSTEM_LocalId");
                    String PNC_Id = jsonObject.getString("PNC_Id");
                    String PNC_LocalId = jsonObject.getString("PNC_LocalId");
                    String CNS_Id = jsonObject.getString("CNS_Id");
                    String CNS_LocalId = jsonObject.getString("CNS_LocalId");


                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();


                    if (!Diagonis_Id.equalsIgnoreCase("0")) {
                        values.put("Isupdate", 1);
                        values.put("ServerId", Diagonis_Id);
                        db.update("Diagonis", values, "id='" + Diagonis_LocalId + "'", null);


                        values = new ContentValues();
                        if (!GENERAL_INFO_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", GENERAL_INFO_Id);
                            db.update("CaseNote_GeneralExamination", values, "id='" + GENERAL_INFO_LocalId + "'", null);
                        }


                        values = new ContentValues();
                        if (!CARDIO_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", CARDIO_Id);
                            db.update("CaseNote_Cardiovascular", values, "id='" + CARDIO_LocalId + "'", null);
                        }


                        values = new ContentValues();
                        if (!RESPIRATORY_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", RESPIRATORY_Id);
                            db.update("CaseNote_RespiratorySystem", values, "id='" + RESPIRATORY_LocalId + "'", null);
                        }


                        values = new ContentValues();
                        if (!GASTRO_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", GASTRO_Id);
                            db.update("CaseNote_Gastrointestinal", values, "id='" + GASTRO_LocalId + "'", null);
                        }

                        values = new ContentValues();
                        if (!NEURO_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", NEURO_Id);
                            db.update("CaseNote_Neurology", values, "id='" + NEURO_LocalId + "'", null);
                        }


                        values = new ContentValues();
                        if (!RENAL_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", RENAL_Id);
                            db.update("CaseNote_Renal", values, "id='" + RENAL_LocalId + "'", null);
                        }


                        values = new ContentValues();
                        if (!ENDOCRINE_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", ENDOCRINE_Id);
                            db.update("CaseNote_Endocrine", values, "id='" + ENDOCRINE_LocalId + "'", null);
                        }

                        values = new ContentValues();
                        if (!LOCOMOTOR_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", LOCOMOTOR_Id);
                            db.update("CaseNote_Locomotor", values, "id='" + LOCOMOTOR_LocalId + "'", null);
                        }

                        values = new ContentValues();
                        if (!CNS_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", CNS_Id);
                            db.update("CaseNote_CNS", values, "id='" + CNS_LocalId + "'", null);
                        }

                        values = new ContentValues();
                        if (!PNC_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", PNC_Id);
                            db.update("CaseNote_PNCSystem", values, "id='" + PNC_LocalId + "'", null);
                        }


                        values = new ContentValues();
                        if (!OTHER_SYSTEM_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", OTHER_SYSTEM_Id);
                            db.update("CaseNote_OtherSystem", values, "id='" + OTHER_SYSTEM_LocalId + "'", null);
                        }


                        values = new ContentValues();
                        if (!CLINICAL_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", CLINICAL_Id);
                            db.update("CaseNote_ClinicalData", values, "id='" + CLINICAL_LocalId + "'", null);
                        }

                        values = new ContentValues();
                        if (!DENTAL_Id.equalsIgnoreCase("0")) {
                            values.put("Isupdate", 1);
                            values.put("ServerId", DENTAL_Id);
                            db.update("CaseNote_DentalSystem", values, "Id='" + DENTAL_LocalId + "'", null);
                        }
                    }

                    db.close();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void ExportInvestigation() {
        try {
            //String Query = String.valueOf(R.string.Query_Casenotes);
            //Change Query
            //String DataSet = getResults(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();

           // String Query = "SELECT Main.id AS MAIN_ID, Main.Ptid, Main.pname, Main.docname, Main.docid, Main.clinicname, Main.Actdate, Main.IsActive, Main.pagegen, Main.pmobnum, Main.treatmentfor, Main.mtestid, Main.imei, Main.Isupdate, Main.prefdiag, Main.HID, Main.ServerId, Main.Diagnosis, A.testname, A.subtestname, A.alltest, A.testvalue, A.testsummary, A.Isupdate AS A_ISUPDATE, A.IsActive AS A_IsActive, A.result, A.IsNew, A.ServerId AS A_SERVERID, A.IsUpdateMax AS A_ISUPDATEMAX, A.medicineId, A.ReadytoUpdate, A.ValueUpdated, A.xraydtls, A.subtestid, A.bps, A.bpd, A.temperature, A.weight, A.seen, A.testreportimg, A.id AS A_ID, B.id AS B_ID, B.IsActive AS B_ISACTIVE, B.scanname, B.subscanname, B.scanvalue, B.scansummary, B.scanreportimg, B.Isupdate AS B_ISUPDATE, B.ReadytoUpdate AS B_ReadytoUpdate, B.ValueUpdated AS B_ValueUpdated, B.testid, B.ServerId AS B_SERVERID, C.id AS C_ID, C.IsActive AS C_ISACTIVE, C.xray, C.xrayvalue, C.xraysummary, C.xrayimg, C.Isupdate AS C_ISUPDATE, C.ReadytoUpdate AS C_ReadytoUpdate, C.ValueUpdated AS C_ValueUpdated, C.xrayid, C.ServerId AS C_SERVERID, D.id AS D_ID, D.IsActive, D.Isupdate AS D_ISUPDATE, D.Comment AS D_COMMENT, D.Summary, D.EEGFor, D.Valuedata AS D_Valuedata, D.ReadytoUpdate AS D_ReadytoUpdate, D.ValueUpdated AS D_ValueUpdated, D.ServerId AS D_SERVERID, E.id AS E_ID, E.ECGTestFor, E.Comment AS E_COMMENT, E.PRate, E.Ecg, E.Ecgfile, E.Scan, E.Scanfile, E.Treadmill, E.IsActive AS E_ISACTIVE, E.ecgrate, E.ecgqrs, E.ecgst, E.ecgt, E.ecgrhyrhm, E.ecgaxis, E.ecgpulse, E.Isupdate AS E_ISUPDATE, E.Conductiondefects, E.Bundlebranchblock, E.PR, E.ReadytoUpdate AS E_ReadytoUpdate, E.ValueUpdated AS E_ValueUpdated, E.ecgText, E.ecgTreadmillText, E.ServerId AS E_SERVERID, F.id AS F_ID, F.IsActive AS F_ISACTIVE, F.Isupdate AS F_ISUPDATE, F.AngioFor, F.Comment AS F_COMMENT, F.Coronary, F.Brain, F.Upperlimb, F.Lowerlimb, F.Mesenteric, F.ReadytoUpdate AS F_ReadytoUpdate, F.ValueUpdated AS F_ValueUpdated, F.CoronaryText, F.BrainText, F.UpperlimbText, F.LowerlimbText, F.MesentericText, F.ServerId AS F_SERVERID FROM Medicaltest AS Main , Medicaltestdtls AS A , Scantest AS B , XRAY AS C , EEG AS D , ECGTEST AS E , Angiogram AS F WHERE Main.Isupdate = '0' AND A.Isupdate = '0' AND B.Isupdate = '0' AND C.Isupdate = '0' AND D.Isupdate = '0' AND E.Isupdate = '0' AND F.Isupdate = '0'";
            //String DataSet = getResults(Query).toString();

            String Final_Query_Primary_Test = "select mtestid, id, ptid, docid, actdate, isactive,  treatmentfor, isupdate, prefdiag, hid, diagnosis from Medicaltest where Isupdate=0";
            String DataSet = getResults(Final_Query_Primary_Test).toString();


            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("Export_Investigation");
                body.setJsonValue(DataSet.toString());

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String MedicalTest_Id = jsonObject.getString("MedicalTest_Id");

                    String MedicalTestLocal_Id = jsonObject.getString("MedicalTestLocal_Id");

                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", MedicalTest_Id);
                    db.update("medicaltest", values, "id=" + MedicalTestLocal_Id + "", null);

                    db.close();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Export_InvestigationDetails() {
        try {
            //String Query = String.valueOf(R.string.Query_Casenotes);
            //Change Query
            //String DataSet = getResults(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();

            String Final_Query_Secondary_Test = "select    Actdate, HID, mtestid,ptid, docid, testname, subtestname, alltest, testvalue, testsummary, isupdate      AS A_ISUPDATE, isactive      AS A_IsActive, result, isnew, serverid      AS A_SERVERID, isupdatemax   AS A_ISUPDATEMAX, medicineid, readytoupdate, valueupdated, xraydtls, subtestid, bps, bpd, temperature, weight, seen, testreportimg, id            AS A_ID  from Medicaltestdtls where IsUpdate=0";
            String DataSet = getResults(Final_Query_Secondary_Test).toString();


            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("Export_InvestigationDetails");
                body.setJsonValue(DataSet.toString());

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String TestDtls_Id = jsonObject.getString("TestDtls_Id");
                    String TestDtlsLocal_Id = jsonObject.getString("TestDtlsLocal_Id");


                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();
                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", TestDtls_Id);
                    db.update("medicaltestdtls", values, "Id=" + TestDtlsLocal_Id + "", null);

                    db.close();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void Export_ScanList() {
        try {
            //String Query = String.valueOf(R.string.Query_Casenotes);
            //Change Query
            //String DataSet = getResults(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();


            String Final_Query_Scan_Test = "select    Actdate, mtestid,ptid, docid, id AS B_ID, isactive AS B_ISACTIVE, scanname, subscanname, scanvalue, scansummary, scanreportimg, isupdate      AS B_ISUPDATE, readytoupdate AS B_ReadytoUpdate, valueupdated  AS B_ValueUpdated, testid, serverid      AS B_SERVERID from scantest  where IsUpdate=0";
            String DataSet = getResults(Final_Query_Scan_Test).toString();



            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("Export_SCANDTLS");
                body.setJsonValue(DataSet.toString());

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String ScanDtls_Id = jsonObject.getString("ScanDtls_Id");
                    String ScanDtlsLocal_Id = jsonObject.getString("ScanDtlsLocal_Id");
                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();
                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", ScanDtls_Id);
                    db.update("scantest", values, "id=" + ScanDtlsLocal_Id + "", null);


                    db.close();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Export_XrayList() {
        try {
            //String Query = String.valueOf(R.string.Query_Casenotes);
            //Change Query
            //String DataSet = getResults(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();


            String Final_Query_Xray_Test = "select    Actdate, HID,mtestid,ptid, docid, id AS C_ID, isactive AS C_ISACTIVE, xray, xrayvalue, xraysummary, xrayimg, isupdate      AS C_ISUPDATE, readytoupdate AS C_ReadytoUpdate, valueupdated  AS C_ValueUpdated, xrayid, serverid      AS C_SERVERID from xray where IsUpdate=0";
            String DataSet = getResults(Final_Query_Xray_Test).toString();


            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("Export_XRAYDTLS");
                body.setJsonValue(DataSet.toString());

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String XrayDtls_Id = jsonObject.getString("XrayDtls_Id");
                    String XrayDtlsLocal_Id = jsonObject.getString("XrayDtlsLocal_Id");

                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();
                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", XrayDtls_Id);
                    db.update("XRAY", values, "id=" + XrayDtlsLocal_Id + "", null);


                    db.close();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Export_EEG() {
        try {
            //String Query = String.valueOf(R.string.Query_Casenotes);
            //Change Query
            //String DataSet = getResults(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();

            String Final_Query_EEG_Test = "select    Actdate, HID,mtestid,ptid, docid, id AS D_ID, isactive, isupdate AS D_ISUPDATE, comment       AS D_COMMENT, summary, eegfor, valuedata     AS D_Valuedata, readytoupdate AS D_ReadytoUpdate, valueupdated  AS D_ValueUpdated, serverid      AS D_SERVERID from eeg  where IsUpdate=0";
            String DataSet = getResults(Final_Query_EEG_Test).toString();


            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("Export_EEGDTLS");
                body.setJsonValue(DataSet.toString());

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String EEGDtls_Id = jsonObject.getString("EEGDtls_Id");
                    String EEGDtlsLocal_Id = jsonObject.getString("EEGDtlsLocal_Id");

                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();

                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", EEGDtls_Id);
                    db.update("EEG", values, "id=" + EEGDtlsLocal_Id + "", null);


                    db.close();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Export_ECG() {
        try {
            //String Query = String.valueOf(R.string.Query_Casenotes);
            //Change Query
            //String DataSet = getResults(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();


            String Final_Query_EEG_Test = "select   Actdate, HID, mtestid,ptid, docid, id AS E_ID, ecgtestfor, comment AS E_COMMENT, prate, ecg, ecgfile, scan, scanfile, treadmill, isactive      AS E_ISACTIVE, ecgrate, ecgqrs, ecgst, ecgt, ecgrhyrhm, ecgaxis, ecgpulse, isupdate      AS E_ISUPDATE, conductiondefects, bundlebranchblock, pr, readytoupdate AS E_ReadytoUpdate, valueupdated  AS E_ValueUpdated, ecgtext, ecgtreadmilltext, serverid      AS E_SERVERID from ecgtest where IsUpdate=0";
            String DataSet = getResults(Final_Query_EEG_Test).toString();


            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("Export_ECGDTLS");
                body.setJsonValue(DataSet.toString());

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String ECGDtls_Id = jsonObject.getString("ECGDtls_Id");
                    String ECGDtlsLocal_Id = jsonObject.getString("ECGDtlsLocal_Id");


                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();
                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", ECGDtls_Id);
                    db.update("ECGTEST", values, "id=" + ECGDtlsLocal_Id + "", null);


                    db.close();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Export_Angiogram() {
        try {
            //String Query = String.valueOf(R.string.Query_Casenotes);
            //Change Query
            //String DataSet = getResults(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();

            String Final_Query_Angiogram_Test = "select    Actdate, HID,mtestid,ptid, docid, id AS F_ID, isactive      AS F_ISACTIVE, isupdate      AS F_ISUPDATE, angiofor, comment       AS F_COMMENT, coronary, brain, upperlimb, lowerlimb, mesenteric, readytoupdate AS F_ReadytoUpdate, valueupdated  AS F_ValueUpdated, coronarytext, braintext, upperlimbtext, lowerlimbtext, mesenterictext, serverid      AS F_SERVERID  from angiogram  where IsUpdate=0";
            String DataSet = getResults(Final_Query_Angiogram_Test).toString();

            if (DataSet.length()>0 && DataSet != null && !DataSet.toString().equalsIgnoreCase("[]")) {

                String contentType = "application/json";
                ExportCaseNotesRequest body = new ExportCaseNotesRequest();
                body.setMethodName("Export_ANGIOGRAM");
                body.setJsonValue(DataSet.toString());

                Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


                ExportCaseNotesResult result = callObject.get();

                JSONArray jsonArray = new JSONArray(result.getResults());


                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String Angiogram_Id = jsonObject.getString("Angiogram_Id");
                    String AngiogramLocal_Id = jsonObject.getString("AngiogramLocal_Id");


                    SQLiteDatabase db = BaseConfig.GetDb();
                    ContentValues values = new ContentValues();

                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", Angiogram_Id);
                    db.update("Angiogram", values, "id=" + AngiogramLocal_Id + "", null);


                    db.close();
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Create new webservice method to insert all casenotes table at once
     * N. Muthukumar
     * 20-03-2018
     */
    public void EXPORT_INPATIENT()
    {
        try {
            String Query = "";//String.valueOf(context.getString(R.string.Query_Casenotes));
            Query = "";
            String DataSet = getResultsCap(Query).toString();
            //Write code to pass to Node JS Server

            ExportCaseNotes exportCaseNotes;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
            exportCaseNotes = controllerFactory.obtainInstance();

            String contentType = "application/json";
            ExportCaseNotesRequest body = new ExportCaseNotesRequest();
            body.setMethodName("INSERT_ALL_INPATIENT");
            body.setJsonValue(DataSet);

            Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(
                    contentType,
                    body, null);


            ExportCaseNotesResult result = callObject.get();

            JSONArray jsonArray = new JSONArray(result.getResults());


            for (int i = 0; i < jsonArray.length(); i++) {

                //Exectue  Array of Result in one by one
                JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                JSONObject jsonObject = jsonArray1.getJSONObject(0);

                String MainChart_Id = jsonObject.getString("MainChart_Id");
                String DiabeticChart_Id = jsonObject.getString("DiabeticChart_Id");
                String TemperatureChart_Id = jsonObject.getString("TemperatureChart_Id");
                String SurgeryRecord_Id = jsonObject.getString("SurgeryRecord_Id");
                //String MedicalCaseRecords_Id = jsonObject.getString("MedicalCaseRecords_Id");
                String HospitalDetails_Id = jsonObject.getString("HospitalDetails_Id");

                String MainChartLocal_Id = jsonObject.getString("MainChartLocal_Id");
                String DiabeticChartLocal_Id = jsonObject.getString("DiabeticChartLocal_Id");
                String TemperatureChartLocal_Id = jsonObject.getString("TemperatureChartLocal_Id");
                String SurgeryRecordLocal_Id = jsonObject.getString("SurgeryRecordLocal_Id");
                //String MedicalCaseRecordsLocal_Id = jsonObject.getString("MedicalCaseRecordsLocal_Id");
                String HospitalDetailsLocal_Id = jsonObject.getString("HospitalDetailsLocal_Id");


                SQLiteDatabase db = BaseConfig.GetDb();
                ContentValues values = new ContentValues();

                values = new ContentValues();
                values.put("Isupdate", 1);
                db.update("Inpatient_DiabeticChart", values, "id='" + DiabeticChartLocal_Id + "'", null);


                values = new ContentValues();
                values.put("Isupdate", 1);
                db.update("Inpatient_MainChart", values, "id='" + MainChartLocal_Id + "'", null);

                values = new ContentValues();
                values.put("Isupdate", 1);
                db.update("Inpatient_SurgeryRecord", values, "id='" + SurgeryRecordLocal_Id + "'", null);


                values = new ContentValues();
                values.put("Isupdate", 1);
                db.update("Inpatient_TemperatureChart", values, "id='" + TemperatureChartLocal_Id + "'", null);

                //values = new ContentValues();
                //values.put("IsUpdate", 1);
                //db.update("Inpatient_MedicalCaseRecords", values, "Id='" + MedicalCaseRecordsLocal_Id + "'", null);


                values = new ContentValues();
                values.put("Isupdate", 1);
                db.update("InpatientHospitalDetails", values, "id='" + HospitalDetailsLocal_Id + "'", null);

                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


   public void EXPORT_INPATIENT_MAIN(){

       try {
           String Query = "select   MAIN.id as  MAIN_ID, MAIN.patid  as  MAIN_PATID, MAIN.DiagId as  MAIN_DIAGID, MAIN.pname as  MAIN_PNAME, MAIN.pagegen as  MAIN_PAGEGEN, MAIN.docid as  MAIN_DOCID, MAIN.docname as  MAIN_DOCNAME, MAIN.Actdate as  MAIN_ACTDATE, MAIN.IsActive as  MAIN_ISACTIVE, MAIN.Isupdate as  MAIN_ISUPDATE, MAIN.doc_visit_date as  MAIN_DOCVISIT_DATE, MAIN.doc_visit_time as  MAIN_DOCVISIT_TIME, MAIN.bp as  MAIN_BP, MAIN.pulse as  MAIN_PULSE, MAIN.\"temp\" as  MAIN_TEMP, MAIN.resp as  MAIN_RESP, MAIN.spo2 as  MAIN_SPO2, MAIN.drugorder as  MAIN_DRUGORDER, MAIN.nursing_instr as  MAIN_NURSING_INSTR, MAIN.ip_oral as  MAIN_IP_ORAL, MAIN.ip_fluids as  MAIN_IP_FLUIDS, MAIN.op_rvies as  MAIN_OP_RIVES, MAIN.op_motion as  MAIN_OP_MOTION, MAIN.op_urine as  MAIN_OP_URINE, MAIN.dischargedon as  MAIN_DISCHARGE, MAIN.dischrg_dttime as  MAIN_DISCHARGE_DATETIME, MAIN.ServerId as  MAIN_SERVERID, MAIN.IsNew as  MAIN_ISNEW, MAIN.bpd as  MAIN_BPD, MAIN.HID as  MAIN_HID from Inpatient_MainChart MAIN WHERE Isupdate=0";

           String DataSet = getResultsCap(Query).toString();
           //Write code to pass to Node JS Server
           if(!DataSet.equalsIgnoreCase("[]"))
           {
               ExportCaseNotes exportCaseNotes;
               // Instantiate a controller
               MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
               ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
               exportCaseNotes = controllerFactory.obtainInstance();

               String contentType = "application/json";
               ExportCaseNotesRequest body = new ExportCaseNotesRequest();
               body.setMethodName("INSERT_INPATIENT_MAIN");
               body.setJsonValue(DataSet);

               Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(
                       contentType,
                       body, null);


               ExportCaseNotesResult result = callObject.get();

               JSONArray jsonArray = new JSONArray(result.getResults());


               for (int i = 0; i < jsonArray.length(); i++) {

                   //Exectue  Array of Result in one by one
                   JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                   JSONObject jsonObject = jsonArray1.getJSONObject(0);

                   String MainChart_Id = jsonObject.getString("MainChart_Id");
                   String MainChartLocal_Id = jsonObject.getString("MainChartLocal_Id");
                   SQLiteDatabase db = BaseConfig.GetDb();
                   ContentValues values = new ContentValues();

                   values = new ContentValues();
                   values.put("Isupdate", 1);
                   db.update("Inpatient_MainChart", values, "id='" + MainChartLocal_Id + "'", null);

                   db.close();
               }
           }

       } catch (Exception e) {
           e.printStackTrace();
       }

   }


   public void EXPORT_INPATIENT_DIABETIC(){

       try {
           String Query = "select   A.id as  A_ID, A.DiagId as  A_DIAGID, A.patid as  A_PATID, A.pname as  A_PNAME, A.pagegen as  A_PAGEGEN, A.docid as  A_DOCID, A.docname as  A_DOCNAME, A.Actdate as  A_ACTDATE, A.IsActive as  A_ISACTIVE, A.Isupdate as  A_ISUPDATE, A.doc_visit_date as  A_DOC_VISIT_DATE, A.doc_visit_time as  A_DOC_VISIT_TIME, A.spl_instr as  A_SPL_INSTR, A.urine_sugar as  A_URINE_SUGAR, A.lente as  A_LENTE, A.insulin_plain as  A_INSULIN_PLAIN, A.blood_sugar as  A_BLOOD_SUGAR, A.ketone_bodies as  A_KETONE_BODIES, A.room_no as  A_ROOM_NO, A.ServerId as  A_SERVERID, A.IsNew as  A_ISNEW, A.HID as  A_HID from Inpatient_DiabeticChart A where Isupdate=0";
           String DataSet = getResultsCap(Query).toString();

           if(!DataSet.equalsIgnoreCase("[]"))
           {
               //Write code to pass to Node JS Server
               ExportCaseNotes exportCaseNotes;
               // Instantiate a controller
               MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
               ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
               exportCaseNotes = controllerFactory.obtainInstance();

               String contentType = "application/json";
               ExportCaseNotesRequest body = new ExportCaseNotesRequest();
               body.setMethodName("INSERT_INPATIENT_DIABETIC_CHART");
               body.setJsonValue(DataSet);

               Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


               ExportCaseNotesResult result = callObject.get();

               JSONArray jsonArray = new JSONArray(result.getResults());


               for (int i = 0; i < jsonArray.length(); i++) {

                   //Exectue  Array of Result in one by one
                   JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                   JSONObject jsonObject = jsonArray1.getJSONObject(0);

                   String DiabeticChart_Id = jsonObject.getString("DiabeticChart_Id");

                   String DiabeticChartLocal_Id = jsonObject.getString("DiabeticChartLocal_Id");


                   SQLiteDatabase db = BaseConfig.GetDb();
                   ContentValues values = new ContentValues();

                   values = new ContentValues();
                   values.put("Isupdate", 1);
                   db.update("Inpatient_DiabeticChart", values, "id='" + DiabeticChartLocal_Id + "'", null);


                   db.close();
               }
           }

       } catch (Exception e) {
           e.printStackTrace();
       }

   }


   public void EXPORT_INPATIENT_TEMPERATURE(){

       try {
           String Query = "select  B.id as  B_ID, B.DiagId as  B_DIAGID, B.patid as  B_PATID, B.pname as  B_PNAME, B.pagegen as  B_PAGEGEN, B.docid as  B_DOCID, B.docname as  B_DOCNAME, B.Actdate as  B_ACTDATE, B.IsActive as  B_ISACTIVE, B.Isupdate as  B_ISUPDATE, B.doc_visit_date as  B_DOC_VISIT_DATE, B.doc_visit_time as  B_VISIT_TIME, B.temperature as  B_TEMP, B.visitsummary as  B_VISITSUMMARY, B.temptakentime as  B_TEMPTAKEIME, B.dischargedon as  B_DISCHARGE, B.dischrg_dttime as  B_DISCHARGE_DATETIME, B.ServerId as  B_SERVERID, B.IsNew as  B_ISNEW, B.HID as  B_HID from Inpatient_TemperatureChart B where Isupdate=0";
           String DataSet = getResultsCap(Query).toString();
           //Write code to pass to Node JS Server

           if(!DataSet.equalsIgnoreCase("[]"))
           {
               ExportCaseNotes exportCaseNotes;
               // Instantiate a controller
               MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
               ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
               exportCaseNotes = controllerFactory.obtainInstance();

               String contentType = "application/json";
               ExportCaseNotesRequest body = new ExportCaseNotesRequest();
               body.setMethodName("INSERT_INPATIENT_TEMPERATURE_CHART");
               body.setJsonValue(DataSet);

               Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(contentType, body, null);


               ExportCaseNotesResult result = callObject.get();

               JSONArray jsonArray = new JSONArray(result.getResults());


               for (int i = 0; i < jsonArray.length(); i++) {

                   //Exectue  Array of Result in one by one
                   JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                   JSONObject jsonObject = jsonArray1.getJSONObject(0);

                   String TemperatureChart_Id = jsonObject.getString("TemperatureChart_Id");

                   String TemperatureChartLocal_Id = jsonObject.getString("TemperatureChartLocal_Id");


                   SQLiteDatabase db = BaseConfig.GetDb();
                   ContentValues values = new ContentValues();


                   values = new ContentValues();
                   values.put("Isupdate", 1);
                   db.update("Inpatient_TemperatureChart", values, "id='" + TemperatureChartLocal_Id + "'", null);


                   db.close();
               }
           }

       } catch (Exception e) {
           e.printStackTrace();
       }

   }


   public void EXPORT_INPATIENT_SURGERY(){

       try {
           String Query = "select   C.id as  C_ID, C.DiagId as  C_DIAGID, C.patid as  C_PATID, C.pname as  C_PNAME, C.pagegen as  C_PAGEGEN, C.docid as  C_DOCID, C.docname as  C_DOCNAME, C.Actdate as  C_ACTDATE, C.IsActive as  C_ISACTIVE, C.Isupdate as  C_ISUPDATE, C.doc_visit_date as  C_DOC_VISIT_DATE, C.doc_visit_time as  C_DOC_VISIT_TIME, C.pre_operativediag as  C_PRE_OPERATING, C.operative_notes as  C_OPERATIVE_NOTES, C.position as  C_POSITION, C.procedure as  C_PROCEDURE, C.closure as  C_CLOSURE, C.post_operativediag as  C_POST_OPERATIVEDIAG, C.surgeon as  C_SURGEON, C.anaesthetist as  C_ANAESTHETIST, C.asst as  C_ASST, C.bloodloss as  C_BLOODLOSS, C.histopathological as  C_HISTOPATHOLOGICAL, C.post_op_instruct as  C_POSTOP, C.ServerId as  C_SERVERID, C.IsNew as  C_ISNEW, C.HID as C_HID from Inpatient_SurgeryRecord C where Isupdate=0";
           String DataSet = getResultsCap(Query).toString();
           //Write code to pass to Node JS Server
           if(!DataSet.equalsIgnoreCase("[]"))
           {
               ExportCaseNotes exportCaseNotes;
               // Instantiate a controller
               MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
               ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
               exportCaseNotes = controllerFactory.obtainInstance();

               String contentType = "application/json";
               ExportCaseNotesRequest body = new ExportCaseNotesRequest();
               body.setMethodName("INSERT_INPATIENT_SURGERY_CHART");
               body.setJsonValue(DataSet);

               Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(
                       contentType,
                       body, null);


               ExportCaseNotesResult result = callObject.get();

               JSONArray jsonArray = new JSONArray(result.getResults());


               for (int i = 0; i < jsonArray.length(); i++) {

                   //Exectue  Array of Result in one by one
                   JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                   JSONObject jsonObject = jsonArray1.getJSONObject(0);

                   String SurgeryRecord_Id = jsonObject.getString("SurgeryRecord_Id");

                   String SurgeryRecordLocal_Id = jsonObject.getString("SurgeryRecordLocal_Id");


                   SQLiteDatabase db = BaseConfig.GetDb();
                   ContentValues values = new ContentValues();

                   values = new ContentValues();
                   values.put("Isupdate", 1);
                   db.update("Inpatient_SurgeryRecord", values, "id='" + SurgeryRecordLocal_Id + "'", null);


                   db.close();
               }
           }

       } catch (Exception e) {
           e.printStackTrace();
       }

   }


   public void EXPORT_INPATIENT_MEDICALCASE_RECORD(){

       try {
           String Query = "select  D.Id as  D_ID, D.pat_id as  D_PATID, D.doc_id as  D_DOCID, D.result as  D_RESULT, D.next_of_kin as  D_NEXTOFKIN, D.referred_by as  D_REFBY, D.date as  D_DATE, D.clinical_notes as  D_CLINICAL_NOTES, D.treatment_diet as  D_TREATMENT_DIET, D.provisional_diag as  D_PROVISIONAL_DIAG, D.final_diag as  D_FINAL_DIAG, D.IsUpdate as  D_ISUPDATE, D.IsActive as  D_ISACTIVE, D.ActDate as  D_ACTDATE, D.under_care_of as  D_UNDERCARE, D.under_care_name as  D_UNDERCARE_NAME, D.HID as  D_HID from InPatient_MedicalCaseRecords  D where IsUpdate=0";

           String DataSet = getResultsCap(Query).toString();
           //Write code to pass to Node JS Server
           if(!DataSet.equalsIgnoreCase("[]"))
           {
               ExportCaseNotes exportCaseNotes;
               // Instantiate a controller
               MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
               ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
               exportCaseNotes = controllerFactory.obtainInstance();

               String contentType = "application/json";
               ExportCaseNotesRequest body = new ExportCaseNotesRequest();
               body.setMethodName("INSERT_INPATIENT_MEDICALCASE_RECORD_CHART");
               body.setJsonValue(DataSet);

               Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(
                       contentType,
                       body, null);


               ExportCaseNotesResult result = callObject.get();

               JSONArray jsonArray = new JSONArray(result.getResults());


               for (int i = 0; i < jsonArray.length(); i++) {

                   //Exectue  Array of Result in one by one
                   JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                   JSONObject jsonObject = jsonArray1.getJSONObject(0);

                   String MedicalCaseRecords_Id = jsonObject.getString("MedicalCaseRecords_Id");

                   String MedicalCaseRecordsLocal_Id = jsonObject.getString("MedicalCaseRecordsLocal_Id");

                   SQLiteDatabase db = BaseConfig.GetDb();
                   ContentValues values = new ContentValues();


                   values = new ContentValues();
                   values.put("IsUpdate", 1);
                   db.update("Inpatient_MedicalCaseRecords", values, "Id='" + MedicalCaseRecordsLocal_Id + "'", null);


                   db.close();
               }
           }

       } catch (Exception e) {
           e.printStackTrace();
       }

   }


   public void EXPORT_INPATIENT_HOSPITAL_DETAILS(){

       try {
           String Query = "";//String.valueOf(context.getString(R.string.Query_Casenotes));
           Query = "";
           String DataSet = getResultsCap(Query).toString();
           //Write code to pass to Node JS Server

           ExportCaseNotes exportCaseNotes;
           // Instantiate a controller
           MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
           ExportCaseNotesFactory controllerFactory = new ExportCaseNotesFactory(magnetClient);
           exportCaseNotes = controllerFactory.obtainInstance();

           String contentType = "application/json";
           ExportCaseNotesRequest body = new ExportCaseNotesRequest();
           body.setMethodName("");
           body.setJsonValue(DataSet);

           Call<ExportCaseNotesResult> callObject = exportCaseNotes.exportCaseNotes(
                   contentType,
                   body, null);


           ExportCaseNotesResult result = callObject.get();

           JSONArray jsonArray = new JSONArray(result.getResults());


           for (int i = 0; i < jsonArray.length(); i++) {

               //Exectue  Array of Result in one by one
               JSONArray jsonArray1 = jsonArray.getJSONArray(i);

               JSONObject jsonObject = jsonArray1.getJSONObject(0);


               String HospitalDetails_Id = jsonObject.getString("HospitalDetails_Id");

               String HospitalDetailsLocal_Id = jsonObject.getString("HospitalDetailsLocal_Id");


               SQLiteDatabase db = BaseConfig.GetDb();
               ContentValues values = new ContentValues();


               values = new ContentValues();
               values.put("Isupdate", 1);
               db.update("InpatientHospitalDetails", values, "id='" + HospitalDetailsLocal_Id + "'", null);

               db.close();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }

   }





    public void ExportRoomAvalibility() throws IOException, XmlPullParserException {


        String ServerId = "";

        try {

            StringBuilder str = new StringBuilder();
            String Query = "select * from Room_avaliability where Isupdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery(Query, null);
            JSONArray dataArray = new JSONArray();
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        JSONObject obj = new JSONObject();
                        try {


                            obj.put("id", c.getString(c.getColumnIndex("Id")));
                            ServerId = c.getString(c.getColumnIndex("ServerId"));
                            obj.put("Patid", c.getString(c.getColumnIndex("Patid")));
                            obj.put("WardId", c.getString(c.getColumnIndex("WardId")));
                            obj.put("RoomId", c.getString(c.getColumnIndex("RoomId")));
                            obj.put("BedId", c.getString(c.getColumnIndex("BedId")));
                            obj.put("Status", c.getString(c.getColumnIndex("Status")));
                            obj.put("IsActive", c.getString(c.getColumnIndex("IsActive")));
                            obj.put("curDate", c.getString(c.getColumnIndex("curDate")));
                            obj.put("IsUpdate", c.getString(c.getColumnIndex("IsUpdate")));


                            dataArray.put(obj);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;

                    } while (c.moveToNext());
                }

                c.close();

            }

            try {

                if (dataArray != null && dataArray.length() > 0) {
                    ///{ "ReturnValue": [ { "Id": "1", "ServerId": "9", "ServerDate": "2016-09-23 12:22:36" } ] }

                    Pair<String, String> firstValues = new Pair<>("Strval", dataArray.toString());
                    if (ServerId == null) ServerId = "";
                    Pair<String, String> secondValues = new Pair<>("ServerId", ServerId);
                    ArrayList<Pair<String, String>> values = new ArrayList<>();
                    values.add(firstValues);
                    values.add(secondValues);
                    String ReturnValue = "";//= BaseConfig.ExportDataToServerNew("ExportRoomAvailability", values);//Modified for exporting data to server @ MK


                    JSONObject mainJson = new JSONObject(ReturnValue);
                    JSONArray jsonArray = mainJson.getJSONArray("ReturnValue");
                    JSONObject objJson = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        String Id = objJson.getString("Id");
                        String isUpdate = objJson.getString("Isupdate");


                        ContentValues values2 = new ContentValues();
                        values2.put("IsUpdate", isUpdate);
                        db.update("Room_avaliability", values2, "Id='" + Id + "'", null);

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();

            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExportInPatientRequest() throws IOException, XmlPullParserException {


        SQLiteDatabase db = BaseConfig.GetDb();
        try {

            StringBuilder str = new StringBuilder();
            String Query = "select * from inpatient_request where Isupdate='0'";


            Cursor c = db.rawQuery(Query, null);
            JSONObject obj;
            JSONArray dataArray = new JSONArray();
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        try {
                            obj = new JSONObject();

                            obj.put("id", c.getString(c.getColumnIndex("Id")));
                            obj.put("patid", c.getString(c.getColumnIndex("patid")));
                            obj.put("request_remarks", c.getString(c.getColumnIndex("request_remarks")));
                            obj.put("WardCategory", c.getString(c.getColumnIndex("WardCategory")));
                            obj.put("WardCategoryId", c.getString(c.getColumnIndex("WardCategoryId")));
                            obj.put("IsActive", c.getString(c.getColumnIndex("IsActive")));
                            obj.put("docid", c.getString(c.getColumnIndex("docid")));
                            obj.put("ActDate", c.getString(c.getColumnIndex("ActDate")));

                            dataArray.put(obj);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;

                    } while (c.moveToNext());
                }

                c.close();

            }

            try {


                if (dataArray != null && dataArray.length() > 0) {


                    String methodName = "ExportInPatientRequest";
                    String jsonData = dataArray.toString();

                    String results = postDataExportData(methodName, jsonData, ctx);


                    JSONArray jsonArray = new JSONArray(results);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        //Exectue  Array of Result in one by one
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                        JSONObject jsonObject = jsonArray1.getJSONObject(0);

                        String LocalId = jsonObject.getString("LocalId");
                        String ServerId = jsonObject.getString("ServerId");


                        ContentValues values2 = new ContentValues();
                        values2.put("IsUpdate", "1");

                        db.update("inpatient_request", values2, "Id='" + LocalId + "'", null);


                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
    }

    public void ExportMobyDoctorSettings() throws IOException, XmlPullParserException {
        try {


            StringBuilder str = new StringBuilder();
            String Query = "select IFNULL(HID,'')as HID,id,docid,drregno,drname,dremail,dt,enablepin,pin,workingdays,consultationhrs,enableappointment,noofpatients,dailyappointmentviasms,professional_charges_column,referral_report,prefpharmacy,pharmdetails,preflab,labdetails,isactive,Isupdate from drsettings where Isupdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            String Local_id = "";
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        from_db_obj = new JSONObject();

                        Local_id = c.getString(c.getColumnIndex("id"));

                        from_db_obj.put("docid", c.getString(c.getColumnIndex("docid")));
                        from_db_obj.put("drregno", c.getString(c.getColumnIndex("drregno")));
                        from_db_obj.put("drname", c.getString(c.getColumnIndex("drname")));
                        from_db_obj.put("dremail", c.getString(c.getColumnIndex("dremail")));
                        from_db_obj.put("dt", c.getString(c.getColumnIndex("dt")));
                        from_db_obj.put("enablepin", c.getString(c.getColumnIndex("enablepin")));
                        from_db_obj.put("pin", c.getString(c.getColumnIndex("pin")));
                        from_db_obj.put("workingdays", c.getString(c.getColumnIndex("workingdays")));
                        from_db_obj.put("consultationhrs", c.getString(c.getColumnIndex("consultationhrs")));
                        from_db_obj.put("enableappointment", c.getString(c.getColumnIndex("enableappointment")));
                        from_db_obj.put("noofpatients", c.getString(c.getColumnIndex("noofpatients")));
                        from_db_obj.put("dailyappointmentviasms", c.getString(c.getColumnIndex("dailyappointmentviasms")));
                        from_db_obj.put("professional_charges_column", c.getString(c.getColumnIndex("professional_charges_column")));
                        from_db_obj.put("referral_report", c.getString(c.getColumnIndex("referral_report")));
                        from_db_obj.put("prefpharmacy", c.getString(c.getColumnIndex("prefpharmacy")));
                        from_db_obj.put("pharmdetails", c.getString(c.getColumnIndex("pharmdetails")));
                        from_db_obj.put("preflab", c.getString(c.getColumnIndex("preflab")));
                        from_db_obj.put("labdetails", c.getString(c.getColumnIndex("labdetails")));
                        from_db_obj.put("isactive", c.getString(c.getColumnIndex("isactive")));
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));// 20
                        from_db_obj.put("HID", c.getString(c.getColumnIndex("HID")));// 20
                        from_db_obj.put("EnableAutoUpdate", "1");// 20
                        export_jsonarray.put(from_db_obj);


                    } while (c.moveToNext());
                }

                c.close();
            }


            if (export_jsonarray != null && export_jsonarray.length() > 0) {


                String methodName = "insertDrsettings";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String ServerId = jsonObject.getString("ServerId");
                    String ReturnId = jsonObject.getString("LocalId");

                    ContentValues values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", ServerId);
                    db.update("drsettings", values, "Id=" + ReturnId + "", null);


                }

            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void EXPORT_DOCTOR_REFERRALS() throws IOException, XmlPullParserException {
        try {

            StringBuilder str = new StringBuilder();
            String Query = "select ref_from_hospital,ref_to_hospital,HID,id,Ptid,Referby,clinicname,treatmentfor,patname,pagegen,PRem,dSign,Actdate,splin,docname,address,imei,mobnum,IsActive,paddr,docid,pmobnum,diagnosisdtls,ptype,refcertfid from Certificate where Isupdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        from_db_obj = new JSONObject();


                        from_db_obj.put("Ptid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Referby", c.getString(c.getColumnIndex("Referby")));
                        from_db_obj.put("clinicname", c.getString(c.getColumnIndex("clinicname")));
                        from_db_obj.put("treatmentfor", c.getString(c.getColumnIndex("treatmentfor")));
                        from_db_obj.put("patname", c.getString(c.getColumnIndex("patname")));
                        from_db_obj.put("pagegen", c.getString(c.getColumnIndex("pagegen")));
                        from_db_obj.put("PRem", c.getString(c.getColumnIndex("PRem")));
                        from_db_obj.put("dSign", c.getString(c.getColumnIndex("dSign")));
                        from_db_obj.put("Actdate", c.getString(c.getColumnIndex("Actdate")));
                        from_db_obj.put("splin", c.getString(c.getColumnIndex("splin")));
                        from_db_obj.put("docname", c.getString(c.getColumnIndex("docname")).replace("-", ","));
                        from_db_obj.put("address", c.getString(c.getColumnIndex("address")));
                        from_db_obj.put("imei", c.getString(c.getColumnIndex("imei")));
                        from_db_obj.put("mobnum", c.getString(c.getColumnIndex("mobnum")));
                        from_db_obj.put("IsActive", c.getString(c.getColumnIndex("IsActive")));
                        from_db_obj.put("paddr", c.getString(c.getColumnIndex("paddr")));
                        from_db_obj.put("docid", c.getString(c.getColumnIndex("docid")));
                        from_db_obj.put("pmobnum", c.getString(c.getColumnIndex("pmobnum")));
                        from_db_obj.put("diagnosisdtls", c.getString(c.getColumnIndex("diagnosisdtls")));
                        from_db_obj.put("ptype", c.getString(c.getColumnIndex("ptype")));
                        from_db_obj.put("refcertfid", c.getString(c.getColumnIndex("refcertfid")));
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        from_db_obj.put("HID", c.getString(c.getColumnIndex("HID")));
                        from_db_obj.put("ref_from_hospital", c.getString(c.getColumnIndex("ref_from_hospital")));
                        from_db_obj.put("ref_to_hospital", c.getString(c.getColumnIndex("ref_to_hospital")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }

                c.close();

            }

            if (export_jsonarray != null && !export_jsonarray.toString().equalsIgnoreCase("") && export_jsonarray != null && export_jsonarray.length() > 0) {


                String methodName = "insertDoctorReferralCertificate";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String ID = jsonObject.getString("LocalId");
                    String TokenId = jsonObject.getString("TokenId");
                    String DepartID = jsonObject.getString("DepartmentId");
                    String PatientMapId = jsonObject.getString("PatientMapId");
                    String CPatListId = jsonObject.getString("CPatListId");
                    String CertificatId = jsonObject.getString("CertificateId");


                    //Write to Get Reuslt in one by one
                    ContentValues values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", CertificatId);
                    db.update("Certificate", values, "id=" + ID + "", null);


                }


            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void ExportDietDtls() throws IOException, XmlPullParserException {
        try {


            StringBuilder str = new StringBuilder();
            String Query = "select id,mpreid,ptid,refdocname,clinicname,docid,Advice,dt,isactive,imei from DietRestriction where Isupdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();


            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("mpreid", c.getString(c.getColumnIndex("mpreid")));
                        from_db_obj.put("Ptid", c.getString(c.getColumnIndex("ptid")));
                        from_db_obj.put("refdocname", c.getString(c.getColumnIndex("refdocname")));
                        from_db_obj.put("clinicname", c.getString(c.getColumnIndex("clinicname")));
                        from_db_obj.put("docid", c.getString(c.getColumnIndex("docid")));
                        from_db_obj.put("Advice", c.getString(c.getColumnIndex("Advice")));
                        from_db_obj.put("dt", c.getString(c.getColumnIndex("dt")));
                        from_db_obj.put("isactive", c.getString(c.getColumnIndex("isactive")));
                        from_db_obj.put("imei", c.getString(c.getColumnIndex("imei")));
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }

                c.close();
            }


            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "insertDietRestrictionBGP";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String ServerId = jsonObject.getString("ServerId");
                    String LocalId = jsonObject.getString("LocalId");


                    ContentValues values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", ServerId);
                    db.update("DietRestriction", values, "id=" + LocalId + "", null);

                }


            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void EXPORT_AIO_MEDICINE_PRESCRIPTION() throws IOException, XmlPullParserException {
        try {


            String Query = "select id,ptid,pname,docid,refdocname,treatmentfor,medicine,Pharmaname,Medid from SendBulkMedicine where Isupdate='0' limit 1";// and

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Ptid", c.getString(c.getColumnIndex("ptid")));
                        from_db_obj.put("pname", c.getString(c.getColumnIndex("pname")));
                        from_db_obj.put("docid", c.getString(c.getColumnIndex("docid")));
                        from_db_obj.put("refdocname", c.getString(c.getColumnIndex("refdocname")));
                        from_db_obj.put("treatmentfor", c.getString(c.getColumnIndex("treatmentfor")));
                        from_db_obj.put("medicine", c.getString(c.getColumnIndex("medicine")));
                        from_db_obj.put("Pharmaname", c.getString(c.getColumnIndex("Pharmaname")));
                        from_db_obj.put("Medid", c.getString(c.getColumnIndex("Medid")));
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }
                c.close();

            }


            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "insertsmsmedpresall";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String LocalId = jsonObject.getString("LocalId");
                    String ServerId = jsonObject.getString("ServerId");


                    ContentValues values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("ServerId", ServerId);
                    db.update("SendBulkMedicine", values, "id=" + LocalId + "", null);


                }


            }

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void EXPORT_MEDICINE_PRESCRIPTION_DETAILS() throws IOException, XmlPullParserException {


        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        try {

            JSONArray export_jsonarray = new JSONArray();
            JSONObject from_db_obj = new JSONObject();


            StringBuilder str = new StringBuilder();
            String Query = "select * from Mprescribed where Isupdate='0'";


            Cursor c = db.rawQuery(Query, null);

            String Local_Id = "";


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("DiagId", c.getString(c.getColumnIndex("DiagId")));
                        from_db_obj.put("ptid", c.getString(c.getColumnIndex("ptid")));
                        from_db_obj.put("pname", c.getString(c.getColumnIndex("pname")));
                        from_db_obj.put("refdocname", c.getString(c.getColumnIndex("refdocname")));
                        from_db_obj.put("clinicname", c.getString(c.getColumnIndex("clinicname")));
                        from_db_obj.put("Medid", c.getString(c.getColumnIndex("Medid")));
                        from_db_obj.put("Dose", c.getString(c.getColumnIndex("Dose")));
                        from_db_obj.put("Freq", c.getString(c.getColumnIndex("Freq")));
                        from_db_obj.put("Duration", c.getString(c.getColumnIndex("Duration")));
                        from_db_obj.put("remarks", c.getString(c.getColumnIndex("remarks")));
                        from_db_obj.put("docid", c.getString(c.getColumnIndex("docid")));
                        from_db_obj.put("Actdate", c.getString(c.getColumnIndex("Actdate")));
                        from_db_obj.put("IsActive", c.getString(c.getColumnIndex("IsActive")));
                        from_db_obj.put("treatmentfor", c.getString(c.getColumnIndex("treatmentfor")));
                        from_db_obj.put("diagnosis", c.getString(c.getColumnIndex("diagnosis")));
                        from_db_obj.put("medicinename", c.getString(c.getColumnIndex("medicinename")));
                        from_db_obj.put("pagegen", c.getString(c.getColumnIndex("pagegen")));
                        from_db_obj.put("diagnosisdtls", c.getString(c.getColumnIndex("diagnosisdtls")));
                        from_db_obj.put("nextvisit", c.getString(c.getColumnIndex("nextvisit")));
                        from_db_obj.put("fee", c.getString(c.getColumnIndex("fee")));
                        from_db_obj.put("imei", c.getString(c.getColumnIndex("imei")));
                        from_db_obj.put("mobnum", c.getString(c.getColumnIndex("mobnum")));
                        from_db_obj.put("Diabeticdiet", c.getString(c.getColumnIndex("Diabeticdiet")));
                        from_db_obj.put("Diabeticrenaldiet", c.getString(c.getColumnIndex("Diabeticrenaldiet")));
                        from_db_obj.put("Lowcholesterol_Cardiacdiet", c.getString(c.getColumnIndex("Lowcholesterol_Cardiacdiet")));
                        from_db_obj.put("Hypertensivediet", c.getString(c.getColumnIndex("Hypertensivediet")));
                        from_db_obj.put("Diet_Warfarin", c.getString(c.getColumnIndex("Diet_Warfarin")));
                        from_db_obj.put("prefpharma", c.getString(c.getColumnIndex("prefpharma")));
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        from_db_obj.put("medicineId", c.getString(c.getColumnIndex("medicineId")));
                        from_db_obj.put("HID", c.getString(c.getColumnIndex("HID")));
                        export_jsonarray.put(from_db_obj);


                    } while (c.moveToNext());
                }

                c.close();
            }


            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "insertMedicinePrescribed2";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String LocalId = jsonObject.getString("LocalId");
                    String ServerId = jsonObject.getString("ServerId");


                    ContentValues values = new ContentValues();
                    values.put("Isupdate", "1");
                    values.put("ServerId", ServerId);
                    db.update("Mprescribed", values, "id=" + LocalId + "", null);


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
    }

    public void Export_DietEntry() {

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        String Query = "select * from diet_entry where IsUpdate = '0'";

        Cursor c = db.rawQuery(Query, null);
        JSONObject rootObject = new JSONObject();
        JSONArray dataArray = new JSONArray();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    JSONObject obj = new JSONObject();
                    try {

                        obj.put("Id", c.getString(c.getColumnIndex("Id")));
                        obj.put("diet_id", c.getString(c.getColumnIndex("diet_id")));
                        obj.put("doc_id", c.getString(c.getColumnIndex("doc_id")));
                        obj.put("pat_id", c.getString(c.getColumnIndex("pat_id")));
                        obj.put("ActDate", c.getString(c.getColumnIndex("ActDate")));
                        obj.put("diet_session_id", c.getString(c.getColumnIndex("diet_session_id")));
                        obj.put("food_id", c.getString(c.getColumnIndex("food_id")));
                        obj.put("IsDelete", c.getString(c.getColumnIndex("IsDelete")));
                        ///obj.put("RemainingCount",c.getString(c.getColumnIndex("RemainingCount")));
                        obj.put("IsActive", c.getString(c.getColumnIndex("IsActive")));
                        obj.put("IsUpdate", c.getString(c.getColumnIndex("IsUpdate")));
                        obj.put("HID", c.getString(c.getColumnIndex("HID")));
                        //obj.put("ServerDate",c.getString(c.getColumnIndex("ServerDate")));
                        dataArray.put(obj);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } while (c.moveToNext());

            }

        }
        c.close();


        try {

            if (dataArray != null && dataArray.length() > 0) {

                String methodName = "Export_DietEntry";
                String jsonData = dataArray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String ServerId = jsonObject.getString("ServerId");
                    String LocalId = jsonObject.getString("LocalId");

                    ContentValues values = new ContentValues();
                    values.put("IsUpdate", 1);
                    values.put("ServerId", ServerId);
                    db.update("diet_entry", values, "Id='" + LocalId + "'", null);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }

        db.close();


    }

    public void delete_DietEntry() {


        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        String Query = "select * from diet_entry where IsDelete = '1'";

        Cursor c = db.rawQuery(Query, null);
        JSONObject rootObject = new JSONObject();
        JSONArray dataArray = new JSONArray();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    JSONObject obj = new JSONObject();
                    try {

                        obj.put("ServerId", c.getString(c.getColumnIndex("ServerId")));
                        dataArray.put(obj);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } while (c.moveToNext());

            }

        }
        c.close();


        try {

            if (dataArray != null && dataArray.length() > 0) {

                String methodName = "Delete_DietEntry";
                String jsonData = dataArray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String LocalId = jsonObject.getString("LocalId");
                    String ServerId = jsonObject.getString("ServerId");


                    db.delete("diet_entry", "ServerId='" + ServerId + "'", null);

                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }

        db.close();


    }

    public void UpdateOperation() {

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        String Query = "select * from operation_details where IsUpdateLocal='0'";

        Cursor c = db.rawQuery(Query, null);
        JSONObject rootObject = new JSONObject();
        JSONArray dataArray = new JSONArray();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    JSONObject obj = new JSONObject();
                    try {

                        ////// [{ "OperationRemarks": "tRemarks", "OperationId":"1"} ]///

                        obj.put("OperationRemarks", c.getString(c.getColumnIndex("Remarks")));
                        obj.put("OperationId", c.getString(c.getColumnIndex("ServerId")));
                        obj.put("Pre_Oper_Diagnosis", c.getString(c.getColumnIndex("Pre_Oper_Diagnosis")));
                        obj.put("Oper_Notes", c.getString(c.getColumnIndex("Oper_Notes")));
                        obj.put("Position", c.getString(c.getColumnIndex("Position")));
                        obj.put("Procedure", c.getString(c.getColumnIndex("Procedure")));
                        obj.put("Closure", c.getString(c.getColumnIndex("Closure")));
                        obj.put("Post_Oper_Diagnosis", c.getString(c.getColumnIndex("Post_Oper_Diagnosis")));
                        obj.put("Post_Oper_Instruction", c.getString(c.getColumnIndex("Post_Oper_Instruction")));
                        obj.put("Remark_Image", c.getString(c.getColumnIndex("Remark_Image")));
                        obj.put("DocId", c.getString(c.getColumnIndex("DocId")));


                        dataArray.put(obj);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } while (c.moveToNext());

            }

        }

        c.close();


        try {

            if (dataArray != null && dataArray.length() > 0) {

                ///{ "ReturnValue": [ { "Id": "1", "ServerId": "9", "ServerDate": "2016-09-23 12:22:36" } ] }


                String methodName = "UpdateRemarksInOperation";
                String jsonData = dataArray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String ServerId = jsonObject.getString("ServerId");
                    String LocalOperationId = jsonObject.getString("LocalOperationId");

                    ContentValues values = new ContentValues();
                    values.put("IsUpdateLocal", 1);
                    db.update("operation_details", values, "ServerId='" + LocalOperationId + "'", null);


                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (SchemaException e) {
            e.printStackTrace();
        }

        db.close();


    }

    public void ExportInPatientDichargeRequest() throws IOException, XmlPullParserException {


        try {

            StringBuilder str = new StringBuilder();
            String Query = "select * from inpatient_discharge_request where IsUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            Cursor c = db.rawQuery(Query, null);
            JSONArray dataArray = new JSONArray();
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        JSONObject obj = new JSONObject();
                        try {


                            obj.put("id", c.getString(c.getColumnIndex("Id")));
                            obj.put("patid", c.getString(c.getColumnIndex("patid")));
                            obj.put("request_remarks", c.getString(c.getColumnIndex("request_remarks")));
                            obj.put("IsActive", c.getString(c.getColumnIndex("IsActive")));
                            obj.put("docid", c.getString(c.getColumnIndex("docid")));
                            obj.put("ActDate", c.getString(c.getColumnIndex("ActDate")));

                            dataArray.put(obj);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;

                    } while (c.moveToNext());
                }

                c.close();

            }

            try {

                if (dataArray != null && dataArray.length() > 0) {

                    String methodName = "ExportDischargeInPatientRequest";
                    String jsonData = dataArray.toString();

                    String results = postDataExportData(methodName, jsonData, ctx);

                    JSONArray jsonArray = new JSONArray(results);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        //Exectue  Array of Result in one by one
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                        JSONObject jsonObject = jsonArray1.getJSONObject(0);


                        String ServerId = jsonObject.getString("ServerId");
                        String LocalId = jsonObject.getString("LocalId");

                        ContentValues values2 = new ContentValues();
                        values2.put("IsUpdate", "1");
                        db.update("inpatient_discharge_request", values2, "Id='" + LocalId + "'", null);

                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();

            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Export_Emergency() throws IOException, XmlPullParserException {


        try {

            StringBuilder str = new StringBuilder();
            String Query = "select * from Bind_EmergencyCausality where IsUpdate='0'";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            Cursor c = db.rawQuery(Query, null);
            JSONArray dataArray = new JSONArray();
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        JSONObject obj = new JSONObject();
                        try {

                            obj.put("id", c.getString(c.getColumnIndex("Id")));
                            obj.put("patid", c.getString(c.getColumnIndex("Patid")));
                            obj.put("MPID", c.getString(c.getColumnIndex("MPID")));
                            obj.put("DocId", c.getString(c.getColumnIndex("DocId")));
                            obj.put("Injection", c.getString(c.getColumnIndex("Injection")));
                            obj.put("Nebulization_Normal", c.getString(c.getColumnIndex("Nebulization_Normal")));
                            obj.put("Nebulization_Asthaline", c.getString(c.getColumnIndex("Nebulization_Asthaline")));
                            obj.put("Suturing", c.getString(c.getColumnIndex("Suturing")));
                            obj.put("Plastering", c.getString(c.getColumnIndex("Plastering")));
                            obj.put("IsActive", c.getString(c.getColumnIndex("IsActive")));
                            obj.put("ActDate", c.getString(c.getColumnIndex("ActDate")));

                            dataArray.put(obj);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        break;

                    } while (c.moveToNext());
                }

                c.close();

            }

            try {

                if (dataArray != null && dataArray.length() > 0) {


                    String methodName = "Export_Emergency";
                    String jsonData = dataArray.toString();

                    String results = postDataExportData(methodName, jsonData, ctx);

                    JSONArray jsonArray = new JSONArray(results);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        //Exectue  Array of Result in one by one
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                        JSONObject jsonObject = jsonArray1.getJSONObject(0);


                        String LocalId = jsonObject.getString("LocalId");
                        String ServerId = jsonObject.getString("ServerId");


                        ContentValues values2 = new ContentValues();
                        values2.put("IsUpdate", 1);
                        values2.put("ServerId", ServerId);

                        db.update("Bind_EmergencyCausality", values2, "Id='" + LocalId + "'", null);

                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();

            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExportMobyDoctorUpdatedInformation() throws IOException, XmlPullParserException {

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            StringBuilder str = new StringBuilder();
            String Query = "select id,username,drid,IFNULL(imeinum,'')as imeinum,password from users where (status='1' or status='true') and dt='1';";


            String Local_id = "";
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        from_db_obj = new JSONObject();

                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        from_db_obj.put("drid", c.getString(c.getColumnIndex("drid")));
                        from_db_obj.put("username", c.getString(c.getColumnIndex("username")));
                        from_db_obj.put("imeinum", c.getString(c.getColumnIndex("imeinum")));
                        from_db_obj.put("password", c.getString(c.getColumnIndex("password")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }

                c.close();

            }

            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "updatepasswordDr";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);

                    String ServerId=jsonObject.getString("Id");


                    ContentValues values = new ContentValues();

                    values.put("dt", "0");
                    values.put("ServerId", ServerId);
                    db.update("users", values, "ServerId=" + ServerId + "", null);

                }

            }

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void ExportReportGallery_New() throws IOException, XmlPullParserException {

        String FilePath = "";
        try {

            StringBuilder str = new StringBuilder();
            String Query = "select * from ReportGallery where Isupdate='0';";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();


            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Docid", c.getString(c.getColumnIndex("Docid")));
                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Patid")));
                        from_db_obj.put("Name", c.getString(c.getColumnIndex("Name")));
                        from_db_obj.put("agegender", c.getString(c.getColumnIndex("agegender")));
                        from_db_obj.put("GalleryId", c.getString(c.getColumnIndex("GalleryId")));
                        from_db_obj.put("Diagnosisid", c.getString(c.getColumnIndex("Diagnosisid")));
                        from_db_obj.put("dt", c.getString(c.getColumnIndex("dt")));
                        from_db_obj.put("Remarks", c.getString(c.getColumnIndex("Remarks")));
                        from_db_obj.put("patientphoto", c.getString(c.getColumnIndex("patientphoto")));
                        from_db_obj.put("ReportPhoto", c.getString(c.getColumnIndex("ReportPhoto")));
                        from_db_obj.put("ReportType", c.getString(c.getColumnIndex("ReportType")));
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        FilePath = c.getString(c.getColumnIndex("ImageUrl"));
                        from_db_obj.put("ImageUrl", c.getString(c.getColumnIndex("ImageUrl")));
                        from_db_obj.put("FileName", c.getString(c.getColumnIndex("FileName")));
                        from_db_obj.put("FileExtension", c.getString(c.getColumnIndex("FileExtension")));

                        export_jsonarray.put(from_db_obj);

                        int return_status = 0;

                        if (!FilePath.equalsIgnoreCase("")) {
                            //Call http method to upload file to server
                            return_status = UpdateFile(FilePath);

                        }

                        if (return_status == 200) {

                            String methodName = "insertReportGallery";
                            String jsonData = from_db_obj.toString();

                            String results = postDataExportData(methodName, jsonData, ctx);

                            JSONArray jsonArray = new JSONArray(results);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                //Exectue  Array of Result in one by one
                                JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                                JSONObject jsonObject = jsonArray1.getJSONObject(0);


                                String Upload_ServerId = jsonObject.getString("Upload_ServerId");
                                String LocalId = jsonObject.getString("LocalId");
                                String ReportGallery_ServerId = jsonObject.getString("ReportGallery_ServerId");


                                ContentValues values = new ContentValues();
                                values.put("Isupdate", 1);
                                values.put("ServerId", ReportGallery_ServerId);
                                db.update("ReportGallery", values, "id=" + LocalId + "", null);


                            }

                        }
                    } while (c.moveToNext());
                }

                c.close();
            }

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExportNewMedicine() throws IOException, XmlPullParserException {
        try {

            StringBuilder str = new StringBuilder();
            String Query = "select id,medicine from Medicine where IsNew='False'";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        from_db_obj = new JSONObject();
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));

                        from_db_obj.put("medicine", (c.getString(c.getColumnIndex("medicine"))));

                        export_jsonarray.put(from_db_obj);


                    } while (c.moveToNext());
                }

                c.close();
            }

            if (export_jsonarray.toString().equalsIgnoreCase("") && export_jsonarray != null && export_jsonarray.length() > 0) {


                String methodName = "insertMedicineClient";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String ServerId = jsonObject.getString("ServerId");
                    String LocalId = jsonObject.getString("LocalId");

                    ContentValues values;//
                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("IsNew", "True");
                    values.put("isactive", "True");
                    values.put("ServerId", ServerId);
                    db.update("Medicine", values, "id=" + LocalId + "", null);


                }


            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ExportNewDiagnosis() throws IOException, XmlPullParserException {
        try {


            StringBuilder str = new StringBuilder();
            String Query = "select id,diagnosisdata from diagonisdetails where IsNew='False'";


            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();
                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        from_db_obj.put("diagnosisdata", c.getString(c.getColumnIndex("diagnosisdata")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }

                c.close();

            }

            if (export_jsonarray != null && export_jsonarray.length() > 0) {


                String methodName = "insertDiagnosisClient";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String LocalId = jsonObject.getString("LocalId");
                    String ServerId = jsonObject.getString("ServerId");


                    ContentValues values;//
                    values = new ContentValues();
                    //values.put("Isupdate", 1);
                    values.put("IsNew", "True");
                    values.put("isactive", "True");
                    values.put("ServerId", ServerId);
                    db.update("diagonisdetails", values, "id=" + LocalId + "", null);

                }


            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ExportNewTreatment() throws IOException, XmlPullParserException {
        try {


            StringBuilder str = new StringBuilder();
            String Query = "select id,treatmentfor from trmntfor where IsNew='False'";

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            int upid = 0;

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));

                        from_db_obj.put("treatmentfor", (c.getString(c.getColumnIndex("treatmentfor"))));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }

                c.close();
            }

            if (export_jsonarray != null && export_jsonarray.length() > 0) {


                String methodName = "insertTreatmentClient";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String LocalId = jsonObject.getString("LocalId");
                    String ServerId = jsonObject.getString("ServerId");


                    ContentValues values;//
                    values = new ContentValues();
                    values.put("Isupdate", 1);
                    values.put("IsNew", "True");
                    values.put("isactive", "True");
                    values.put("ServerId", ServerId);
                    db.update("trmntfor", values, "id=" + LocalId + "", null);

                }


            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ExportConsultationStatus() throws IOException, XmlPullParserException {
        try {

          String serverid = "";
            String Docreply = "";

            StringBuilder str = new StringBuilder();
            String Query = "select ServerId,DocReply from OnlineConsultancy where IsUpdate='0'";


            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        from_db_obj = new JSONObject();
                        from_db_obj.put("serverid", c.getString(c.getColumnIndex("ServerId")));
                        from_db_obj.put("Docreply", c.getString(c.getColumnIndex("DocReply")));

                        export_jsonarray.put(from_db_obj);


                    } while (c.moveToNext());
                }

                c.close();

            }


            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "UpdateOnlineDrReply";
                String jsonData = export_jsonarray.toString();

                String results = postDataExportData(methodName, jsonData, ctx);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                    String ServerId = jsonObject.getString("ServerId");
                    String LocalId = jsonObject.getString("LocalId");


                    ContentValues values = new ContentValues();
                    values.put("IsUpdate", 1);
                    db.update("OnlineConsultancy", values, "ServerId=" + LocalId + "", null);

                }


            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private JSONArray getResults(String Query) {

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor cursor = db.rawQuery(Query, null);
        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {

                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return resultSet;

    }

    private JSONArray getResultsCap(String Query) {

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor cursor = db.rawQuery(Query, null);
        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {

                            rowObject.put(cursor.getColumnName(i).toUpperCase(), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i).toUpperCase(), "");
                        }
                    } catch (Exception e) {

                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return resultSet;

    }

    public String GetPatId() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final Calendar cc = Calendar.getInstance();
        mcYear = cc.get(Calendar.YEAR);
        mcMonth = cc.get(Calendar.MONTH) + 1;
        mcDay = cc.get(Calendar.DAY_OF_MONTH);
        String pid = null;
        String[] did = BaseConfig.doctorId.split("/");
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c1 = db.rawQuery("select pnum from PMaxvalue;", null);
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    pid = "PID/" + mcYear + "/" + mcDay + mcMonth + "/"
                            + did[3]
                            + c1.getString(c1.getColumnIndex("pnum"));

                } while (c1.moveToNext());
            }
        }
        c1.close();
        db.close();
        if (pid != null) {
            return pid;
        } else {
            return null;
        }

    }

    void UpdateInCaseNotes(String PatId, String mTestId, String alltest) {
        String subtestName = "";
        String subtestValue = "";
        String bps = "";
        String bpd = "";
        String temperature = "";
        String weight = "";

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            Cursor c = db.rawQuery("select * from Medicaltestdtls where Ptid = '" + PatId + "' and mtestid = '" + mTestId + "' and alltest = '" + alltest + "' ", null);


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
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        PatientData data = getPatientName(PatId);

        if (subtestName.equalsIgnoreCase(compareRBSName)) {

            ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);
            values.put("RBS", subtestValue);
            values.put("BpS", bps);
            values.put("BpD", bpd);
            values.put("temperature", temperature);
            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }
        if (subtestName.equalsIgnoreCase(compareFBSName)) {


            ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);
            values.put("FBS", subtestValue);
            values.put("BpS", bps);
            values.put("BpD", bpd);
            values.put("temperature", temperature);
            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }
        if (subtestName.equalsIgnoreCase(comparePBSName)) {


            ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);
            values.put("PPS", subtestValue);
            values.put("BpS", bps);
            values.put("BpD", bpd);
            values.put("temperature", temperature);
            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }

    }


    PatientData getPatientName(String patId) {
        PatientData data = new PatientData();
        data.patId = patId;

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            Cursor c = db.rawQuery("select * from patreg where patid  = '" + patId.trim() + "'", null);


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
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return data;

    }

    public int UpdateFile(String sourceFileUri) {
        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            return 0;
        } else {

            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(BaseConfig.AppImagephpURL);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                if (serverResponseCode == 200) {

                    //File uploaded successfully...


                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

            } catch (Exception e) {


                e.printStackTrace();

            }

            return serverResponseCode;

        } // End else block

    }

    private ResultsValue exportAllDatas(String jsonData, String methodName) throws InterruptedException, ExecutionException {
        ResultsValue returnValue = new ResultsValue();
        try {
            MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(ctx);
            Export_All_DataFactory export_all_dataFactory = new Export_All_DataFactory(magnetMobileClient);
            Export_All_Data export_all_data = (Export_All_Data) export_all_dataFactory.obtainInstance();

            Export_DatasRequest exportDatasRequest = new Export_DatasRequest();
            exportDatasRequest.setJsonValue(jsonData);
            exportDatasRequest.setMethodName(methodName);

            Call<Export_DatasResult> export_datasResult = export_all_data.export_Datas("application/json", exportDatasRequest, null);

            Export_DatasResult vas = export_datasResult.get();

            returnValue.setGetResults(vas.getResults());

            returnValue.setGetLocalIds(vas.getLocalIds());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    public interface Export_All_Data {

        /**
         * Generated from URL POST http://192.168.137.85:1234/exportMasters/postData
         * POST exportMasters/postData
         *
         * @param contentType (original name : Content-Type) style:HEADER
         * @param body        style:PLAIN
         * @param listener
         * @return Export_DatasResult
         */
        Call<Export_DatasResult> export_Datas(
                String contentType,
                Export_DatasRequest body,
                StateChangedListener listener
        );


    }

    public class PatientData {
        String patId = "";
        String pName = "";
        String gender = "";
        String age = "";
        String mobNum = "";


    }

    class ResultsValue {
        public String getResults;
        public String getLocalIds;

        public String getGetResults() {
            return getResults;
        }

        public void setGetResults(String getResults) {
            this.getResults = getResults;
        }

        public String getGetLocalIds() {
            return getLocalIds;
        }

        public void setGetLocalIds(String getLocalIds) {
            this.getLocalIds = getLocalIds;
        }
    }


}//END

