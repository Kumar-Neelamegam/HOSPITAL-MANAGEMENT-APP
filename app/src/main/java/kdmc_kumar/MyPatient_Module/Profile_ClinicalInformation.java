package kdmc_kumar.MyPatient_Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DateTimelineRowitemAdapter;
import kdmc_kumar.Adapters_GetterSetter.Timeline_Objects;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Ponnusamy M on 3/31/2017.
 */

public class Profile_ClinicalInformation extends Fragment {


    /**
     * Muthukumar N
     * 10-5-2018
     * ENHANCEMENT V2
     */
    //**********************************************************************************************
    //Declaration
    @BindView(id.clinical_profile_parent_layout)
    LinearLayout clinicalProfileParentLayout;
    @BindView(id.clinical_profile_patientid)
    TextView clinicalProfilePatientid;
    @BindView(id.recycler)
    RecyclerView recycler;
    @BindView(id.clinical_profile_img_nodata)
    AppCompatImageView clinicalProfileImgNodata;
    @BindView(id.clinical_profile_nodatatext)
    TextView clinicalProfileNodatatext;
    @BindView(id.clinical_profile_clinicalinfo)
    WebView clinicalProfileClinicalinfo;

    DateTimelineRowitemAdapter adapter;
    ArrayList<Timeline_Objects> datetime_arrayList;
    private StringBuilder medicationtablerow = new StringBuilder();
    //**********************************************************************************************
    private String BUNDLE_PATIENT_ID;

    public Profile_ClinicalInformation() {

    }
    //**********************************************************************************************

    private static final String checkNullEmpty(String value) {
        return value == null || value.isEmpty() || value.equals("null") || value.equalsIgnoreCase("null") ? " - " : value;
    }
    //**********************************************************************************************

    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(layout.new_clinicalnote_profile, container, false);

        try {

            this.GETINITILIZATION(rootView);

            this.CONTROLLISTENERS(rootView);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return rootView;
    }
    //**********************************************************************************************

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    //**********************************************************************************************

    private void CONTROLLISTENERS(View rootView) {

        this.adapter.setOnItemClickListener((view, position, items) -> this.LoadWebview(items.get(position).getId()));


    }
    //**********************************************************************************************

    private void GETINITILIZATION(View rootView) {

        ButterKnife.bind(this, rootView);
        Bundle args = this.getArguments();
        this.BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

        String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');
        this.clinicalProfilePatientid.setText(String.format("%s - %s", Patient_Name, this.BUNDLE_PATIENT_ID));


        this.recycler.setHasFixedSize(true);
        this.recycler.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));


        ArrayList<Timeline_Objects> datetime_arrayList = this.LoadDateTimeLine();
        if (datetime_arrayList.size() > 0) {
            this.clinicalProfileImgNodata.setVisibility(View.GONE);
            this.clinicalProfileNodatatext.setVisibility(View.GONE);
            this.clinicalProfileClinicalinfo.setVisibility(View.VISIBLE);
            this.recycler.setVisibility(View.VISIBLE);
        } else {
            this.clinicalProfileImgNodata.setVisibility(View.VISIBLE);
            this.clinicalProfileNodatatext.setVisibility(View.VISIBLE);
            this.clinicalProfileClinicalinfo.setVisibility(View.GONE);
            this.recycler.setVisibility(View.GONE);
        }


        this.adapter = new DateTimelineRowitemAdapter(this.getActivity(), datetime_arrayList);
        this.recycler.setAdapter(this.adapter);// set adapter on recyclerview
        this.adapter.notifyDataSetChanged();// Notify the adapter



    }

    private ArrayList<Timeline_Objects> LoadDateTimeLine() {

        this.datetime_arrayList = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery("select distinct Clinical_ID,id,Actdate as acdate from clinicalInformation where ptid='" + this.BUNDLE_PATIENT_ID + "' order by id desc;", null);

        if (c.moveToFirst()) {
            do {


                String ID = c.getString(c.getColumnIndex("id"));
                String DATE = c.getString(c.getColumnIndex("acdate"));
                DATE = DATE.contains("/") ? DATE.replace("/","-") : DATE;
                String CLINICAL_ID = c.getString(c.getColumnIndex("Clinical_ID"));


                this.datetime_arrayList.add(new Timeline_Objects(ID, DATE, CLINICAL_ID));

            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return this.datetime_arrayList;
    }

    //**********************************************************************************************

    private final void LoadWebview(String id) {

        this.clinicalProfileClinicalinfo.setLayerType(View.LAYER_TYPE_NONE, null);
        this.clinicalProfileClinicalinfo.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.clinicalProfileClinicalinfo.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.clinicalProfileClinicalinfo.getSettings().setDefaultTextEncodingName("utf-8");
        this.clinicalProfileClinicalinfo.setWebChromeClient(new Profile_ClinicalInformation.MyWebChromeClient());
        this.clinicalProfileClinicalinfo.setBackgroundColor(0x00000000);
        this.clinicalProfileClinicalinfo.setVerticalScrollBarEnabled(true);
        this.clinicalProfileClinicalinfo.setHorizontalScrollBarEnabled(true);
        this.clinicalProfileClinicalinfo.getSettings().setJavaScriptEnabled(true);
        this.clinicalProfileClinicalinfo.getSettings().setAllowContentAccess(true);
        this.clinicalProfileClinicalinfo.setOnLongClickListener(v -> true);
        this.clinicalProfileClinicalinfo.setWebChromeClient(new WebChromeClient());
        this.clinicalProfileClinicalinfo.setLongClickable(false);


        this.clinicalProfileClinicalinfo.addJavascriptInterface(new Profile_ClinicalInformation.WebAppInterface(this.getActivity()), "android");
        try {

            this.clinicalProfileClinicalinfo.loadDataWithBaseURL("file:///android_asset/", this.LoadClinicalNoteDetails(id), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
    //**********************************************************************************************

    private final String LoadClinicalNoteDetails(String id) {

        String values;
        String PreviousMedicalHistory = "", HereditaryDiseases = "";
        String AllergicTo = "", Smoking = "", Alcohol = "", Tobacco = "", Others = "", Medication = "", FamilyMedicalHistory = "";
        String Bowel = "", Micturition = "", PresentIllness = "", AnyMedication = "", PastIllness = "", TreatmentforMedicineNamePeriod = "";
        String Obstetric = "", Gynaec = "", SurgicalNotes = "", Antental_Values = "", Antental_Others = "";


        SQLiteDatabase db = BaseConfig.GetDb();//);

        db.isOpen();

        String Query = "select Antental_val,Antental_others,surgical_notes,Previous_MedicalHistory,Hereditary,AllergicTo,any_medication,Perhis_Smoking,Perhis_Alcohol," +
                "Perhis_Tobacco,Perhis_Others,Medication_History,Family_Med_History,Perhis_BowelHabits,Perhis_Micturition,present_illness,past_illness,any_medication," +
                "obstetric,gynaec from clinicalInformation where id='" + id.trim() + "' and ptid='" + this.BUNDLE_PATIENT_ID + "' ;";

        // Log.e("Query: ",Query);
       // Toast.makeText(getActivity(), "Selected Id: "+ id, Toast.LENGTH_SHORT).show();


        Cursor c = db.rawQuery(Query, null);


        this.medicationtablerow = new StringBuilder();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    if (c.getString(c.getColumnIndex("Medication_History")).contains("#")) {
                        String[] valuenew1 = c.getString(c.getColumnIndex("Medication_History")).split("#");


                        for (int i = 0; i <= valuenew1.length - 1; i++) {

                            String[] valuenew2 = valuenew1[i].trim().split("_");

                            this.medicationtablerow.append("  <tr>\n" + "    <td>").append(valuenew2[0].replace(",", "")).append("</td>\n").append("    <td>").append(valuenew2[1]).append("</td>\n").append("\t<td>").append(valuenew2[2]).append("</td>\n").append("  </tr>\n");


                        }

                    } else {

                        if (c.getString(c.getColumnIndex("Medication_History")).length() > 0) {

                            String[] valuenew3 = c.getString(c.getColumnIndex("Medication_History")).split("_");

                            this.medicationtablerow.append("  <tr>\n" + "    <td>").append(valuenew3[0].replace(",", "")).append("</td>\n").append("    <td>").append(valuenew3[1]).append("</td>\n").append("\t<td>").append(valuenew3[2]).append("</td>\n").append("  </tr>\n");
                        }

                    }

                 /*   familymedicalhistory.append("  <tr>\n" +
                            //  "    <td>Disease Name</td>\n" +
                            "    <td>").append(c.getString(c.getColumnIndex("Family_Med_History"))).append("</td>\n").append("  </tr>\n");
*/
                    PreviousMedicalHistory = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Previous_MedicalHistory"))));
                    HereditaryDiseases = (Profile_ClinicalInformation.checkNullEmpty(String.format("%-20s", c.getString(c.getColumnIndex("Hereditary")))));
                    AllergicTo = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("AllergicTo"))));
                    Smoking = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Perhis_Smoking"))));
                    Alcohol = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Perhis_Alcohol"))));
                    Tobacco = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Perhis_Tobacco"))));
                    Others = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Perhis_Others"))));
                    Medication = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Medication_History"))));
                    FamilyMedicalHistory = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Family_Med_History"))));
                    Bowel = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Perhis_BowelHabits"))));
                    Micturition = (Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Perhis_Micturition"))));
                    PresentIllness = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("present_illness")));
                    AnyMedication = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("any_medication")));
                    PastIllness = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("past_illness")));
                    SurgicalNotes = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("surgical_notes")));
                    TreatmentforMedicineNamePeriod = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("any_medication")));
                    Obstetric = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("obstetric")));
                    Gynaec = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("gynaec")));

                    Antental_Values = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Antental_val")));
                    Antental_Others = Profile_ClinicalInformation.checkNullEmpty(c.getString(c.getColumnIndex("Antental_others")));


                } while (c.moveToNext());
            }

        }
        c.close();

        //region Description
        values = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> \n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.3/normalize.min.css\" />\n" +
                "\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\">\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\"></script>\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<style>\n" +
                "\n" +
                "\n" +
                "\n" +
                ".vtimeline {\n" +
                "position: relative;\n" +
                "padding: 30px;\n" +
                "overflow: hidden; }\n" +
                ".vtimeline:before {\n" +
                "content: '';\n" +
                "position: absolute;\n" +
                "height: 100%;\n" +
                "width: 4px;\n" +
                "background: #3d5987;\n" +
                "top: 0;\n" +
                "bottom: 0;\n" +
                "left: 0;\n" +
                "right: 0;\n" +
                "margin: 0 auto; }\n" +
                "\n" +
                ".vtimeline-point {\n" +
                "position: relative;\n" +
                "width: 100%;\n" +
                "margin-bottom: 20px; }\n" +
                "\n" +
                ".vtimeline-icon {\n" +
                "position: absolute;\n" +
                "top: 10px;\n" +
                "height: 25px;\n" +
                "width: 25px;\n" +
                "left: 0;\n" +
                "right: 0;\n" +
                "margin: 0 auto;\n" +
                "border-radius: 50%;\n" +
                "background: #3d5987; }\n" +
                "\n" +
                ".vtimeline-block {\n" +
                "width: 45%;\n" +
                "margin: 0;\n" +
                "transition: 0.5s ease all; }\n" +
                ".vtimeline-block:after {\n" +
                "content: '';\n" +
                "position: absolute;\n" +
                "top: 13px;\n" +
                "left: 45%;\n" +
                "width: 0;\n" +
                "height: 0;\n" +
                "border-top: 10px solid transparent;\n" +
                "border-bottom: 10px solid transparent;\n" +
                "border-left: 10px solid #eee;\n" +
                "border-right: none; }\n" +
                "\n" +
                ".vtimeline-right {\n" +
                "margin-left: 55%; }\n" +
                ".vtimeline-right.vt-animate-slide .vtimeline-content {\n" +
                "-webkit-transform: translateX(100%);\n" +
                "transform: translateX(100%); }\n" +
                ".vtimeline-right .vtimeline-date {\n" +
                "left: auto;\n" +
                "right: 55%; }\n" +
                ".vtimeline-right:after {\n" +
                "left: auto;\n" +
                "right: 45%;\n" +
                "border-top: 10px solid transparent;\n" +
                "border-bottom: 10px solid transparent;\n" +
                "border-left: none;\n" +
                "border-right: 10px solid #eee; }\n" +
                "\n" +
                ".vtimeline-date {\n" +
                "position: absolute;\n" +
                "left: 55%;\n" +
                "top: 15px; }\n" +
                "\n" +
                ".vtimeline-content {\n" +
                "padding: 0px;\n" +
                "padding-bottom: 5px\n" +
                "background: #fff;\n" +
                "transition: 0.5s ease all; }\n" +
                "\n" +
                ".vt-animate-fade {\n" +
                "opacity: 0; }\n" +
                "\n" +
                ".vt-animate-slide {\n" +
                "opacity: 0; }\n" +
                ".vt-animate-slide .vtimeline-content {\n" +
                "-webkit-transform: translateX(-100%);\n" +
                "transform: translateX(-100%); }\n" +
                "\n" +
                ".vt-noarrows:after {\n" +
                "content: none; }\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "@media only screen and (max-width: 992px) {\n" +
                ".vtimeline:before {\n" +
                "left: 30px;\n" +
                "right: auto;\n" +
                "margin: 0; }\n" +
                ".vtimeline-icon {\n" +
                "left: -10px;\n" +
                "right: auto;\n" +
                "margin: 0; }\n" +
                ".vtimeline-block {\n" +
                "width: 95%;\n" +
                "margin-left: 5%; }\n" +
                ".vtimeline-block:after {\n" +
                "left: auto;\n" +
                "right: 85%;\n" +
                "border-top: 10px solid transparent;\n" +
                "border-bottom: 10px solid transparent;\n" +
                "border-left: none;\n" +
                "border-right: 10px solid #eee; }\n" +
                ".vtimeline-date {\n" +
                "position: relative;\n" +
                "display: block;\n" +
                "padding: 10px;\n" +
                "top: 0;\n" +
                "left: 0;\n" +
                "right: auto;\n" +
                "background: #eee; }\n" +
                ".vtimeline-right .vtimeline-date {\n" +
                "right: auto; }\n" +
                ".vtimeline.basic .vtimeline-date {\n" +
                "padding: 10px 10px 0 10px; }\n" +
                ".vt-animate-slide .vtimeline-content {\n" +
                "-webkit-transform: translateX(100%);\n" +
                "transform: translateX(100%); } }\n" +
                "\n" +
                "\n" +
                "h1, h2 {\n" +
                "text-align: center;\n" +
                "}\n" +
                "\n" +
                "h3 { \n" +
                "margin: 0;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                ".card {\n" +
                "box-shadow: 0 5px 5px 0 rgba(0, 0, 0, 0.2);\n" +
                "max-width: 100%;\n" +
                "margin: auto;\n" +
                "width: 100%;\n" +
                "text-align: left;\n" +
                "font-family: arial;\n" +
                "}\n" +
                "\n" +
                ".title {\n" +
                "color: grey;\n" +
                "font-size: 18px;\n" +
                "}\n" +
                "\n" +
                "button {\n" +
                "border: none;\n" +
                "outline: 0;\n" +
                "display: inline-block;\n" +
                "padding: 8px;\n" +
                "color: white;\n" +
                "background-color: #3d5987;\n" +
                "text-align: left;\n" +
                "cursor: pointer;\n" +
                "width: 100%;\n" +
                "font-size: 18px;\n" +
                "}\n" +
                "\n" +
                "a {\n" +
                "text-decoration: none;\n" +
                "font-size: 22px;\n" +
                "color: black;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</style>\n" +
                "<body>\n" +
                "\n" +
                "<div id=\"vt4\">\n" +
                "\n" +


                "<!-- card 1 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.prev_med_history)+"</button></h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.str_history)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + PreviousMedicalHistory + "</td>\n" +
                "</tr>\n" +
                "\n" +
               /* "<tr>\n" +
                "<td width=\"50%\">"+getString(R.string.others)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>-</td>\n" +
                "</tr>\t\t \n" +*/
                "</table>\n" +
                "</div> \n" +
                "\n" +
                "</div>\n" +


                "<!-- card 2 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.hereditary_diseases)+"</h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.diseases)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + HereditaryDiseases + "</td>\n" +
                "</tr>\n" +
                "\n" +
            /*    "<tr>\n" +
                "<td width=\"50%\">"+getString(R.string.others)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>-</td>\n" +
                "</tr>\t\t \n" +*/
                "</table>\n" +
                "</div> \n" +
                "\n" +
                "</div>\n" +
                "\n" +


                "<!-- card 3 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.personal_history)+"</h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.smoking_habit)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Smoking + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.alcohol_habit)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Alcohol + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.tobacco_habit)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Tobacco + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.bowel_habits)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Bowel + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.micturition)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Micturition + "</td>\n" +
                "</tr>\n" +
                "\n" +
               /* "<tr>\n" +
                "<td width=\"50%\">"+getString(R.string.others)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>-</td>\n" +
                "</tr>\t\t \n" +*/
                "</table>\n" +
                "</div> \n" +
                "\n" +
                "</div>\n" +
                "\n" +


                "<!-- card 4 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.family_medical_history)+"</h3>\n" +
                "\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.diseases)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + FamilyMedicalHistory + "</td>\n" +
                "</tr>\n" +
                "\n" +
              /*  "<tr>\n" +
                "<td width=\"50%\">"+getString(R.string.others)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>-</td>\n" +
                "</tr>\n" +*/
                "\n" +
                "</table>\n" +
                "</div> \n" +
                "\n" +
                "</div>\n" +
                "\n" +


                "<!-- card 5 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.medication_history)+"</h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "\n" +
                "<tr>\n" +
                "<th width=\"auto\">"+ this.getString(string.treatment_for)+"</th>\n" +
                "<th width=\"auto\">"+ this.getString(string.medicine_name)+"</th>\n" +
                "<th width=\"auto\">"+ this.getString(string.period)+"</th>\n" +
                "</tr>\n" +
                "\n" +
                "\n" +


                this.medicationtablerow
                +
                "\n" +
                "\n" +
                "</table>\n" +
                "</div> \n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "\n" +


                "<!-- card 6 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.allergies)+"</h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.allergies)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + AllergicTo + "</td>\n" +
                "</tr>\n" +
                "\n" +
             /*   "<tr>\n" +
                "<td width=\"50%\">"+getString(R.string.others)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>-</td>\n" +
                "</tr>\n" +*/
                "\n" +
                "</table>\n" +
                "</div> \n" +
                "\n" +
                "</div>\n" +
                "\n" +


                "<!-- card 7 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.anc)+"</h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.obstetric)+"<br/>"+ this.getString(string.previous_pregenancy)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Antental_Values + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.others)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Antental_Others + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "</table>\n" +
                "</div> \n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "\n" +


                "<!-- card 8 ---------------------------------------------------------------------->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>"+ this.getString(string.other_medical_history)+"</h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.present_illness)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + PresentIllness + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.past_illness)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + PastIllness + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.any_medication)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + AnyMedication + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.previous_surgery_details)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Obstetric + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.gynaec)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + Gynaec + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.other_surgical_remarks)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>" + SurgicalNotes + "</td>\n" +
                "</tr>\n" +
                "\n" +
                "</table>\n" +
                "</div> \n" +
                "</div>\n" +
                "\n" +
                "</div><!-- End vt4 --> \n" +
                "\n" +


                "<script src=\"file:///android_asset/Doctor_Profile/css/vertical-timeline.js\"></script>\n" +
                "<script>\n" +
                "\n" +
                "$('#vt4').verticalTimeline({\n" +
                "startLeft: false,\n" +
                "arrows: false,\n" +
                "alternate: false\n" +
                "});\n" +
                "\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";
        //endregion

        db.close();

        return values;
    }

    //**********************************************************************************************

    private static class MyWebChromeClient extends WebChromeClient {
    }
    //**********************************************************************************************

    static class WebAppInterface {
        final Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            this.mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }

    //**********************************************************************************************

}
