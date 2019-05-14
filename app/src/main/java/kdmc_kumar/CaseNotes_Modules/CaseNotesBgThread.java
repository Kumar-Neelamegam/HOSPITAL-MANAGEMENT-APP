/*

 */
package kdmc_kumar.CaseNotes_Modules;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

/**
 * @author user4
 *         Developer name: S.Nabeel Ahamed
 *         Email: nabeel
 *         10-Jul-2015 4:27:29 PM
 */


public class CaseNotesBgThread extends AsyncTask<String, String, String> {
    private final String Patient_Id;
    private final Context context;
    private ProgressDialog pDialog;
    private final CaseNotes caseNotes;
    /**
     * progress dialog to show user that the backup is processing.
     */
    private final ProgressDialog dialog;

    public CaseNotesBgThread(Context context, String PatientId) {
        this.context = context;
        this.dialog = new ProgressDialog(context);
        Patient_Id = PatientId;

    }

    @Override
    protected final void onPreExecute() {
        this.pDialog = new ProgressDialog(this.context);
        this.pDialog.setMessage("Loading casenote details...");
        this.pDialog.show();
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected final String doInBackground(String... params) {

        return null;
    }


    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected final void onPostExecute(String result) {
        super.onPostExecute(result);
       // caseNotes.UpdateAllValues();
        this.pDialog.dismiss();
    }


    public static class DiagnosisData {
        String Docid = "";
        String Ptid = "";
        String DiagId = "";
        String pname = "";
        String gender = "";
        String age = "";
        String mobnum = "";
        String refdocname = "";
        String clinicname = "";
        public String Diagnosis = "";
        public String BpS = "";
        public String BpD = "";
        public String FBS = "";
        public String PPS = "";
        public String RBS = "";
        public String PWeight = "";
        public String temperature = "";
        String testresult = "";
        String Ecg = "";
        String Ecgfile = "";
        String Scan = "";
        String Scanfile = "";
        String Test = "";
        String Actdate = "";
        String IsActive = "";
        public String treatmentfor = "";
        String bloodgroup = "";
        String ecgrate = "";
        String ecgrhyrhm = "";
        String ecgpulserate = "";
        String ecgqrs = "";
        String ecgst = "";
        String ecgt = "";
        String ecgaxis = "";
        String ecgbundlebranch = "";
        public String ecgconductiondefects = "";
        String ecgtreadmill = "";
        String imeino = "";
        String docsign = "";
        String Isupdate = "";
        public String height = "";
        public String bmi = "";
        public String xrayremrk = "";


        Cursor c;

        public DiagnosisData() {
        }


        final void SetData() {

            //Log.e("Dataa", "SetData");
            if (this.c == null) {
                //Log.e("c==", "null");
            }

            if (this.c != null) {
                //Log.e("c!=", "null");
                if (this.c.moveToFirst()) {
                    do {
                        //Log.e("c!=", "moved to first");

                        String clinicName = this.c.getString(this.c.getColumnIndex("clinicname"));
                        //Log.e("Clinic Name", clinicName);

                        this.Actdate = this.c.getString(this.c.getColumnIndex("Actdate"));
                        this.age = this.c.getString(this.c.getColumnIndex("age"));
                        // TODO: 3/9/2017 Adding Blood Presser value init
                        this.BpS = this.c.getString(this.c.getColumnIndex("BpS"));
                        this.bloodgroup = this.c.getString(this.c.getColumnIndex("bloodgroup"));
                        this.bmi = this.c.getString(this.c.getColumnIndex("bmi"));
                        this.BpD = this.c.getString(this.c.getColumnIndex("BpD"));
                        this.clinicname = this.c.getString(this.c.getColumnIndex("clinicname"));
                        this.DiagId = this.c.getString(this.c.getColumnIndex("DiagId"));
                        this.Diagnosis = this.c.getString(this.c.getColumnIndex("Diagnosis"));
                        this.Docid = this.c.getString(this.c.getColumnIndex("Docid"));
                        this.docsign = this.c.getString(this.c.getColumnIndex("docsign"));
                        this.Ecg = this.c.getString(this.c.getColumnIndex("Ecg"));
                        this.ecgaxis = this.c.getString(this.c.getColumnIndex("ecgaxis"));
                        this.ecgbundlebranch = this.c.getString(this.c.getColumnIndex("ecgbundlebranch"));
                        this.Ecgfile = this.c.getString(this.c.getColumnIndex("Ecgfile"));
                        this.ecgpulserate = this.c.getString(this.c.getColumnIndex("ecgpulserate"));
                        this.ecgqrs = this.c.getString(this.c.getColumnIndex("ecgqrs"));
                        this.ecgrate = this.c.getString(this.c.getColumnIndex("ecgrate"));
                        this.ecgrhyrhm = this.c.getString(this.c.getColumnIndex("ecgrhyrhm"));
                        this.ecgst = this.c.getString(this.c.getColumnIndex("ecgst"));
                        this.ecgt = this.c.getString(this.c.getColumnIndex("ecgt"));
                        this.ecgtreadmill = this.c.getString(this.c.getColumnIndex("ecgtreadmill"));
                        this.FBS = this.c.getString(this.c.getColumnIndex("FBS"));
                        this.gender = this.c.getString(this.c.getColumnIndex("gender"));
                        this.height = this.c.getString(this.c.getColumnIndex("height"));
                        this.imeino = this.c.getString(this.c.getColumnIndex("imeino"));
                        this.IsActive = this.c.getString(this.c.getColumnIndex("IsActive"));
                        this.Isupdate = this.c.getString(this.c.getColumnIndex("Isupdate"));
                        this.mobnum = this.c.getString(this.c.getColumnIndex("mobnum"));
                        this.PPS = this.c.getString(this.c.getColumnIndex("PPS"));
                        this.pname = this.c.getString(this.c.getColumnIndex("pname"));
                        this.Ptid = this.c.getString(this.c.getColumnIndex("Ptid"));
                        this.PWeight = this.c.getString(this.c.getColumnIndex("PWeight"));
                        this.RBS = this.c.getString(this.c.getColumnIndex("RBS"));
                        this.refdocname = this.c.getString(this.c.getColumnIndex("refdocname"));
                        this.Scan = this.c.getString(this.c.getColumnIndex("Scan"));
                        this.Scanfile = this.c.getString(this.c.getColumnIndex("Scanfile"));
                        this.temperature = this.c.getString(this.c.getColumnIndex("temperature"));
                        this.Test = this.c.getString(this.c.getColumnIndex("Test"));
                        this.testresult = this.c.getString(this.c.getColumnIndex("testresult"));
                        this.treatmentfor = this.c.getString(this.c.getColumnIndex("treatmentfor"));

                    } while (this.c.moveToNext());
                }
            }

        }
    }


    public static class GeneralExaminationData {
        String DiagId = "";
        String ptid = "";
        String pname = "";
        String docid = "";
        String Actdate = "";
        String IsActive = "";
        String pagegen = "";
        String Isupdate = "";
        String Anaemic = "";
        String Icterus = "";
        String Stenosis = "";
        String Clubbing = "";
        String Limbphantom = "";
        String Vericoveins = "";
        String Pedal_edema = "";
        String built = "";
        String extra_generalexam = "";

        String skin_infection = "", short_stat = "", pog = "", pallor = "", oedema = "", fundalheight = "", lie = "", fetalmovement = "", fetalheight = "", pv = "", anycompl = "";

        Cursor c;

        public GeneralExaminationData() {
        }

        final void SetData() {

            //Log.e("GeneralExaminationData Dataa", "SetData");
            if (this.c == null) {
                //Log.e("GeneralExaminationData c==", "null");
            }

            if (this.c != null) {
                //Log.e("GeneralExaminationData c!=", "null");
                if (this.c.moveToFirst()) {

                    do {


                        this.Actdate = this.c.getString(this.c.getColumnIndex("Actdate"));
                        this.Anaemic = this.c.getString(this.c.getColumnIndex("Anaemic"));
                        this.Clubbing = this.c.getString(this.c.getColumnIndex("Clubbing"));
                        this.DiagId = this.c.getString(this.c.getColumnIndex("DiagId"));
                        this.docid = this.c.getString(this.c.getColumnIndex("docid"));
                        this.Icterus = this.c.getString(this.c.getColumnIndex("Icterus"));
                        this.IsActive = this.c.getString(this.c.getColumnIndex("IsActive"));
                        this.Isupdate = this.c.getString(this.c.getColumnIndex("Isupdate"));
                        this.Limbphantom = this.c.getString(this.c.getColumnIndex("Limbphantom"));
                        this.pagegen = this.c.getString(this.c.getColumnIndex("pagegen"));
                        this.Pedal_edema = this.c.getString(this.c.getColumnIndex("Pedal_edema"));
                        this.pname = this.c.getString(this.c.getColumnIndex("pname"));
                        this.ptid = this.c.getString(this.c.getColumnIndex("ptid"));
                        this.Stenosis = this.c.getString(this.c.getColumnIndex("Stenosis"));
                        this.Vericoveins = this.c.getString(this.c.getColumnIndex("Vericoveins"));
                        this.built = this.c.getString(this.c.getColumnIndex("built"));
                        this.extra_generalexam = this.c.getString(this.c.getColumnIndex("extra_generalexam"));

                        this.skin_infection = this.c.getString(this.c.getColumnIndex("skin_infection"));
                        this.short_stat = this.c.getString(this.c.getColumnIndex("short_stat"));
                        this.pog = this.c.getString(this.c.getColumnIndex("pog"));
                        this.pallor = this.c.getString(this.c.getColumnIndex("pallor"));
                        this.oedema = this.c.getString(this.c.getColumnIndex("oedema"));
                        this.fundalheight = this.c.getString(this.c.getColumnIndex("fundalheight"));
                        this.lie = this.c.getString(this.c.getColumnIndex("lie"));
                        this.fetalmovement = this.c.getString(this.c.getColumnIndex("fetalmovement"));
                        this.fetalheight = this.c.getString(this.c.getColumnIndex("fetalheight"));
                        this.pv = this.c.getString(this.c.getColumnIndex("pv"));
                        this.anycompl = this.c.getString(this.c.getColumnIndex("anycompl"));


                    }

                    while (this.c.moveToNext());
                }
            }


        }
    }


    public static class CardioValues {
        String DiagId = "";
        String ptid = "";
        String pname = "";
        String docid = "";
        String Actdate = "";
        String IsActive = "";
        String pagegen = "";
        String Isupdate = "";
        public String Beat = "";
        public String Murmur = "";
        public String Pericardial_Rub = "";
        public String Pulserate = "";
        public String heartrate = "";
        Cursor c;

        public CardioValues() {
        }

        final void SetData() {

            //Log.e("CardioValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("CardioValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("CardioValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {


                        this.Actdate = this.c.getString(this.c.getColumnIndex("Actdate"));
                        this.Beat = this.c.getString(this.c.getColumnIndex("Beat"));
                        this.Murmur = this.c.getString(this.c.getColumnIndex("Murmur"));
                        this.DiagId = this.c.getString(this.c.getColumnIndex("DiagId"));
                        this.docid = this.c.getString(this.c.getColumnIndex("docid"));
                        this.Pericardial_Rub = this.c.getString(this.c.getColumnIndex("Pericardial_Rub"));
                        this.IsActive = this.c.getString(this.c.getColumnIndex("IsActive"));
                        this.Isupdate = this.c.getString(this.c.getColumnIndex("Isupdate"));
                        this.Pulserate = this.c.getString(this.c
                                .getColumnIndex("Pulserate"));
                        this.pagegen = this.c.getString(this.c.getColumnIndex("pagegen"));
                        this.heartrate = this.c.getString(this.c
                                .getColumnIndex("heartrate"));
                        this.pname = this.c.getString(this.c.getColumnIndex("pname"));
                        this.ptid = this.c.getString(this.c.getColumnIndex("ptid"));


                    }

                    while (this.c.moveToNext());
                }
            }


        }
    }


    public static class RespiratoryValues {
        public String Breathsound = "";
        public String Trachea = "";
        public String Kyphosis_Scoliosis = "";
        public String Crepitation = "";
        public String Note = "";
        public String shapeofchest = "";
        public String spo2 = "";
        Cursor c;
        String Bronchi = "";
        String Pulserate = "";

        public RespiratoryValues() {
        }

        final void SetData() {
            //Log.e("RespiratoryValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("RespiratoryValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("RespiratoryValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {
                        this.Breathsound = this.c.getString(this.c.getColumnIndex("Breathsound"));
                        this.Trachea = this.c.getString(this.c.getColumnIndex("Trachea"));
                        this.Kyphosis_Scoliosis = this.c.getString(this.c.getColumnIndex("Kyphosis_Scoliosis"));
                        this.Crepitation = this.c.getString(this.c.getColumnIndex("Crepitation"));
                        this.Bronchi = this.c.getString(this.c.getColumnIndex("Bronchi"));
                        this.Pulserate = this.c.getString(this.c.getColumnIndex("Pulserate"));
                        this.Note = this.c.getString(this.c.getColumnIndex("Note"));
                        this.shapeofchest = this.c.getString(this.c.getColumnIndex("shapeofchest"));
                        this.spo2 = this.c.getString(this.c.getColumnIndex("spo2"));


                    }

                    while (this.c.moveToNext());
                }
            }

        }
    }

    public static class OtherSystemValues {
        public String Othersystem = "";
        public String AdditionalInfo = "";


        Cursor c;

        public OtherSystemValues() {
        }

        final void SetData() {
            //Log.e("OtherSystemValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("OtherSystemValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("OtherSystemValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {
                        this.Othersystem = this.c.getString(this.c.getColumnIndex("Othersystem"));
                        this.AdditionalInfo = this.c.getString(this.c.getColumnIndex("AdditionalInfo"));

                    }

                    while (this.c.moveToNext());
                }
            }

        }
    }

    public static class ClinicalDataValues {
        public String Heamoglobin = "";
        public String wbc = "";
        public String rbc = "";
        public String esr = "";
        public String polymorphs = "";
        public String lymphocytes = "";
        public String monocytes = "";
        public String basophils = "";
        public String eosinophils = "";
        public String urea = "";
        public String creatinine = "";
        public String UricAcid = "";
        public String Sodium = "";
        public String Potassium = "";
        public String Chloride = "";
        public String Bicarbonate = "";
        public String TotalCholesterol = "";
        public String LDL = "";
        public String HDL = "";
        public String VLDL = "";
        public String Triglycerides = "";
        public String Serumbilirubin = "";
        public String Direct = "";
        public String Indirect = "";
        public String Totalprotein = "";
        public String Albumin = "";
        public String Globulin = "";
        public String SGOT = "";
        public String SGPT = "";
        public String AlkalinePhosphatase = "";
        public String FreeT3 = "";
        public String FreeT4 = "";
        public String TSH = "";

        Cursor c;

        public ClinicalDataValues() {
        }

        final void SetData() {
            //Log.e("ClinicalDataValues Data", "SetData");
            if (this.c == null) {
                //Log.e("ClinicalDataValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("ClinicalDataValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {

                        this.Heamoglobin = this.c.getString(this.c.getColumnIndex("Heamoglobin"));
                        this.wbc = this.c.getString(this.c.getColumnIndex("wbc"));
                        this.rbc = this.c.getString(this.c.getColumnIndex("rbc"));
                        this.esr = this.c.getString(this.c.getColumnIndex("esr"));
                        this.polymorphs = this.c.getString(this.c.getColumnIndex("polymorphs"));
                        this.lymphocytes = this.c.getString(this.c.getColumnIndex("lymphocytes"));
                        this.monocytes = this.c.getString(this.c.getColumnIndex("monocytes"));
                        this.basophils = this.c.getString(this.c.getColumnIndex("basophils"));
                        this.eosinophils = this.c.getString(this.c.getColumnIndex("eosinophils"));
                        this.urea = this.c.getString(this.c.getColumnIndex("urea"));
                        this.creatinine = this.c.getString(this.c.getColumnIndex("creatinine"));
                        this.UricAcid = this.c.getString(this.c.getColumnIndex("UricAcid"));

                        this.Sodium = this.c.getString(this.c.getColumnIndex("Sodium"));
                        this.Potassium = this.c.getString(this.c.getColumnIndex("Potassium"));
                        this.Chloride = this.c.getString(this.c.getColumnIndex("Chloride"));
                        this.Bicarbonate = this.c.getString(this.c.getColumnIndex("Bicarbonate"));
                        this.TotalCholesterol = this.c.getString(this.c.getColumnIndex("TotalCholesterol"));
                        this.LDL = this.c.getString(this.c.getColumnIndex("LDL"));
                        this.HDL = this.c.getString(this.c.getColumnIndex("HDL"));
                        this.VLDL = this.c.getString(this.c.getColumnIndex("VLDL"));
                        this.Triglycerides = this.c.getString(this.c.getColumnIndex("Triglycerides"));
                        this.Serumbilirubin = this.c.getString(this.c.getColumnIndex("Serumbilirubin"));
                        this.Direct = this.c.getString(this.c.getColumnIndex("Direct"));
                        this.Indirect = this.c.getString(this.c.getColumnIndex("Indirect"));
                        this.Totalprotein = this.c.getString(this.c.getColumnIndex("Totalprotein"));
                        this.Albumin = this.c.getString(this.c.getColumnIndex("Albumin"));
                        this.Globulin = this.c.getString(this.c.getColumnIndex("Globulin"));
                        this.SGOT = this.c.getString(this.c.getColumnIndex("SGOT"));
                        this.SGPT = this.c.getString(this.c.getColumnIndex("SGPT"));
                        this.AlkalinePhosphatase = this.c.getString(this.c.getColumnIndex("AlkalinePhosphatase"));
                        this.FreeT3 = this.c.getString(this.c.getColumnIndex("FreeT3"));
                        this.FreeT4 = this.c.getString(this.c.getColumnIndex("FreeT4"));
                        this.TSH = this.c.getString(this.c.getColumnIndex("TSH"));

                    }

                    while (this.c.moveToNext());
                }
            }

        }
    }

    public static class EndocrineValues {
        public String Endocrine = "";


        Cursor c;

        public EndocrineValues() {
        }

        final void SetData() {
            //Log.e("EndocrineValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("EndocrineValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("EndocrineValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {
                        this.Endocrine = this.c.getString(this.c.getColumnIndex("Endocrine"));


                    }

                    while (this.c.moveToNext());
                }
            }

        }
    }

    public static class RenalSystemValues {

        public String dysuria = "";
        public String pyuria = "";
        public String haematuria = "";
        public String oliguria = "";
        public String polyuria = "";
        public String anuria = "";
        public String nocturia = "";
        public String urethraldischarge = "";
        public String prostate = "";

        Cursor c;

        public RenalSystemValues() {
        }

        final void SetData() {
            //Log.e("EndocrineValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("EndocrineValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("EndocrineValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {

                        this.dysuria = this.c.getString(this.c.getColumnIndex("dysuria"));
                        this.pyuria = this.c.getString(this.c.getColumnIndex("pyuria"));
                        this.haematuria = this.c.getString(this.c.getColumnIndex("haematuria"));
                        this.oliguria = this.c.getString(this.c.getColumnIndex("oliguria"));
                        this.polyuria = this.c.getString(this.c.getColumnIndex("polyuria"));
                        this.anuria = this.c.getString(this.c.getColumnIndex("anuria"));
                        this.nocturia = this.c.getString(this.c.getColumnIndex("nocturia"));
                        this.urethraldischarge = this.c.getString(this.c.getColumnIndex("urethraldischarge"));
                        this.prostate = this.c.getString(this.c.getColumnIndex("prostate"));

                    }

                    while (this.c.moveToNext());
                }
            }

        }
    }


    public static class LocomotorSystemValues {
        public String symmetry = "";
        public String smooth_movement = "";
        public String arms_swing = "";
        public String pelvic_tilt = "";
        public String stride_length = "";
        public String cervical_lordosis = "";
        public String lumbar_lordosis = "";
        public String kyphosis = "";
        public String scoliosis = "";
        public String llswelling = "";
        String lldeformity = "";
        public String lllimbshortening = "";
        public String llmuscle_wasting = "";
        public String llremarks = "";
        public String ulswelling = "";
        public String uldeformity = "";
        public String ullimbshortening = "";
        String ulmuscle_wasting = "";
        public String ulremarks = "";


        Cursor c;

        public LocomotorSystemValues() {
        }

        final void SetData() {
            //Log.e("LocomotorSystemValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("LocomotorSystemValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("LocomotorSystemValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {
                        this.symmetry = this.c.getString(this.c.getColumnIndex("symmetry"));
                        this.smooth_movement = this.c.getString(this.c.getColumnIndex("smooth_movement"));
                        this.arms_swing = this.c.getString(this.c.getColumnIndex("arms_swing"));
                        this.pelvic_tilt = this.c.getString(this.c.getColumnIndex("pelvic_tilt"));
                        this.stride_length = this.c.getString(this.c.getColumnIndex("stride_length"));
                        this.cervical_lordosis = this.c.getString(this.c.getColumnIndex("cervical_lordosis"));
                        this.lumbar_lordosis = this.c.getString(this.c.getColumnIndex("lumbar_lordosis"));
                        this.kyphosis = this.c.getString(this.c.getColumnIndex("kyphosis"));
                        this.scoliosis = this.c.getString(this.c.getColumnIndex("scoliosis"));
                        this.llswelling = this.c.getString(this.c.getColumnIndex("llswelling"));
                        this.lldeformity = this.c.getString(this.c.getColumnIndex("lldeformity"));
                        this.lllimbshortening = this.c.getString(this.c.getColumnIndex("lllimbshortening"));
                        this.llmuscle_wasting = this.c.getString(this.c.getColumnIndex("llmuscle_wasting"));
                        this.llremarks = this.c.getString(this.c.getColumnIndex("llremarks"));
                        this.ulswelling = this.c.getString(this.c.getColumnIndex("ulswelling"));
                        this.uldeformity = this.c.getString(this.c.getColumnIndex("uldeformity"));
                        this.ullimbshortening = this.c.getString(this.c.getColumnIndex("ullimbshortening"));
                        this.ulmuscle_wasting = this.c.getString(this.c.getColumnIndex("ulmuscle_wasting"));
                        this.ulremarks = this.c.getString(this.c.getColumnIndex("ulremarks"));


                    }

                    while (this.c.moveToNext());
                }
            }

        }
    }


    public static class NeurologySystemValues {


        Cursor c;
        Cursor c1;
        public String Pupilsize = "";
        public String Speech = "";
        public String Carodit = "";
        public String Amnesia = "";

        public String Apraxia = "";
        public String Cognitive_Function = "";
        public String Bulk = "";

        public String Tone = "";
        public String Power_LUL = "";
        public String Power_RUL = "";

        public String Power_LLL = "";
        public String Power_RLL = "";
        public String Sensory = "";
        public String Reflexes_Corneal = "";

        public String Reflexes_Biceps = "";
        public String Reflexes_Triceps = "";
        public String Reflexes_Supinator = "";

        public String Reflexes_Knee = "";
        public String Reflexes_Ankle = "";
        public String Reflexes_Plantor = "";

        public String congentail_abnormality = "";
        public String mentalstatus = "";

        public String oriented = "";
        public String neckrigidity = "";
        public String confused = "";
        public String exaggerated = "";
        public String extensor = "";
        public String gsscore = "";
        public String incomprehensible = "";
        public String depressed = "";
        public String flexor = "";
        public String coma = "";

        public NeurologySystemValues() {
        }


        final void SetData() {
            //Log.e("EndocrineValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("EndocrineValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("EndocrineValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {

                        this.Pupilsize = this.c.getString(this.c.getColumnIndex("Pupilsize"));
                        this.Speech = this.c.getString(this.c.getColumnIndex("Speech"));
                        this.Carodit = this.c.getString(this.c.getColumnIndex("Carodit"));
                        this.Amnesia = this.c.getString(this.c.getColumnIndex("Amnesia"));
                        this.Apraxia = this.c.getString(this.c.getColumnIndex("Apraxia"));
                        this.Cognitive_Function = this.c.getString(this.c.getColumnIndex("Cognitive_Function"));
                        this.Bulk = this.c.getString(this.c.getColumnIndex("Bulk"));
                        this.Tone = this.c.getString(this.c.getColumnIndex("Tone"));
                        this.Power_LUL = this.c.getString(this.c.getColumnIndex("Power_LUL"));
                        this.Power_RUL = this.c.getString(this.c.getColumnIndex("Power_RUL"));
                        this.Power_LLL = this.c.getString(this.c.getColumnIndex("Power_LLL"));
                        this.Power_RLL = this.c.getString(this.c.getColumnIndex("Power_RLL"));
                        this.Sensory = this.c.getString(this.c.getColumnIndex("Sensory"));
                        this.Reflexes_Corneal = this.c.getString(this.c.getColumnIndex("Reflexes_Corneal"));
                        this.Reflexes_Biceps = this.c.getString(this.c.getColumnIndex("Reflexes_Biceps"));
                        this.Reflexes_Triceps = this.c.getString(this.c.getColumnIndex("Reflexes_Triceps"));
                        this.Reflexes_Supinator = this.c.getString(this.c.getColumnIndex("Reflexes_Supinator"));
                        this.Reflexes_Knee = this.c.getString(this.c.getColumnIndex("Reflexes_Knee"));
                        this.Reflexes_Ankle = this.c.getString(this.c.getColumnIndex("Reflexes_Ankle"));
                        this.Reflexes_Plantor = this.c.getString(this.c.getColumnIndex("Reflexes_Plantor"));
                        this.congentail_abnormality = this.c.getString(this.c.getColumnIndex("congentail_abnormality"));
                        this.mentalstatus = this.c.getString(this.c.getColumnIndex("mentalstatus"));

                    }

                    while (this.c.moveToNext());
                }
            }


            //Log.e("CNSValues Dataa", "SetData");
            if (this.c1 == null) {
                //Log.e("CNSValues c1==", "null");
            }

            if (this.c1 != null) {
                //Log.e("CNS Values c1!=", "null");
                if (this.c1.moveToFirst()) {

                    do {

                        this.oriented = this.c1.getString(this.c1.getColumnIndex("oriented"));
                        this.neckrigidity = this.c1.getString(this.c1.getColumnIndex("neckrigidity"));
                        this.confused = this.c1.getString(this.c1.getColumnIndex("confused"));
                        this.exaggerated = this.c1.getString(this.c1.getColumnIndex("exaggerated"));
                        this.extensor = this.c1.getString(this.c1.getColumnIndex("extensor"));
                        this.gsscore = this.c1.getString(this.c1.getColumnIndex("gsscore"));
                        this.incomprehensible = this.c1.getString(this.c1.getColumnIndex("incomprehensible"));
                        this.depressed = this.c1.getString(this.c1.getColumnIndex("depressed"));
                        this.flexor = this.c1.getString(this.c1.getColumnIndex("flexor"));
                        this.coma = this.c1.getString(this.c1.getColumnIndex("coma"));

                    }

                    while (this.c1.moveToNext());
                }
            }

        }
    }


    public static class GastroIntestinalSystem {
        Cursor c;
        public String Abdomen = "";
        public String Bowelsound = "";
        public String Spleen = "";
        public String Liver = "";
        public String PalpableMasses = "";
        public String Hernial = "";
        public String shapeofabdomen = "";
        public String Visible_Pulsations = "";
        public String Visual_Peristalsis = "";
        public String Abdominal_Palpation = "";
        public String Splenomegaly = "";
        public String Hepatomegaly = "";
        public String organomegely = "";
        public String freefuild = "";
        public String distension = "";
        public String tenderness = "";
        public String scrotum = "";

        public GastroIntestinalSystem() {
        }


        final void SetData() {


            //Log.e("LocomotorSystemValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("LocomotorSystemValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("LocomotorSystemValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {
                        this.Abdomen = this.c.getString(this.c.getColumnIndex("Abdomen"));
                        this.Bowelsound = this.c.getString(this.c.getColumnIndex("Bowelsound"));
                        this.Spleen = this.c.getString(this.c.getColumnIndex("Spleen"));
                        this.Liver = this.c.getString(this.c.getColumnIndex("Liver"));
                        this.PalpableMasses = this.c.getString(this.c.getColumnIndex("PalpableMasses"));
                        this.Hernial = this.c.getString(this.c.getColumnIndex("Hernial"));
                        this.shapeofabdomen = this.c.getString(this.c.getColumnIndex("shapeofabdomen"));
                        this.Visible_Pulsations = this.c.getString(this.c.getColumnIndex("Visible_Pulsations"));
                        this.Visual_Peristalsis = this.c.getString(this.c.getColumnIndex("Visual_Peristalsis"));
                        this.Abdominal_Palpation = this.c.getString(this.c.getColumnIndex("Abdominal_Palpation"));
                        this.Splenomegaly = this.c.getString(this.c.getColumnIndex("Splenomegaly"));
                        this.Hepatomegaly = this.c.getString(this.c.getColumnIndex("Hepatomegaly"));
                        this.organomegely = this.c.getString(this.c.getColumnIndex("organomegely"));
                        this.freefuild = this.c.getString(this.c.getColumnIndex("freefuild"));
                        this.distension = this.c.getString(this.c.getColumnIndex("distension"));
                        this.tenderness = this.c.getString(this.c.getColumnIndex("tenderness"));
                        this.scrotum = this.c.getString(this.c.getColumnIndex("scrotum"));


                    }

                    while (this.c.moveToNext());
                }
            }

        }

    }


    //*********************************************************************************************
    public static class PNCValues {

        public String pnc_details = "";
        public String ppc_details = "";
        public String cob_details = "";

        Cursor c;

        public PNCValues() {
        }

        final void SetData() {


            //Log.e("PNCValues Dataa", "SetData");
            if (this.c == null) {
                //Log.e("PNCValues c==", "null");
            }

            if (this.c != null) {
                //Log.e("PNCValues c!=", "null");
                if (this.c.moveToFirst()) {

                    do {

                        this.pnc_details = this.c.getString(this.c.getColumnIndex("pnc_details"));
                        this.ppc_details = this.c.getString(this.c.getColumnIndex("ppc_details"));
                        this.cob_details = this.c.getString(this.c.getColumnIndex("cob_details"));

                    }

                    while (this.c.moveToNext());
                }
            }

        }
    }

    //*********************************************************************************************


}
