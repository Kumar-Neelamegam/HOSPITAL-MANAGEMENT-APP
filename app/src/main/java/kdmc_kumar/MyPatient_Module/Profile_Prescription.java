package kdmc_kumar.MyPatient_Module;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Ponnusamy M on 3/31/2017.
 */

public class Profile_Prescription extends Fragment {
    private final TextView medicine_name;
    TextView dose;
    TextView freq;
    TextView duration;
    private final TextView nextvisiton;
    private final TextView visitedon;
    private final TextView remarks;
    private final TextView symptomsValue;
    private final TextView diagnosisValue;
    private StringBuilder sbM = new StringBuilder();
    private String nxtdt = "";
    private String[] visiteddt;
    private ImageView next_btn;
    private ImageView pre_btn;
    private final TextView ref_docname;
    private String refdocname = "";
    private WebView profile_webvw;
    private TextView PatientNameId;
    private String SymptomsValue;

    private ImageView NoDataFound;

    private String BUNDLE_PATIENT_ID;

    ImageView pdfPrint;
    private List<String> mypatientprevmedicinehistory_medid;
    /////////////////////////////////////////////////////////////////////////////////
    private int pos;

    public Profile_Prescription() {
    }

    //#######################################################################################################

    /////////////////////////////////////////////////////////////////////////////////
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {

        View rootView = null;
        try {


            rootView = inflater.inflate(layout.new_prescription_profile, container, false);
            this.profile_webvw = rootView.findViewById(id.webvw_prescription_profile);

            Bundle args = this.getArguments();
            this.BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);


            this.profile_webvw.getSettings().setJavaScriptEnabled(true);
            this.profile_webvw.setWebChromeClient(new WebChromeClient());
            //Controllisteners();
            this.NoDataFound = rootView.findViewById(id.img_nodata);

            this.PatientNameId = rootView.findViewById(id.patientid);

            String Query = "select name||' - '|| Patid as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'';
            this.PatientNameId.setText(BaseConfig.GetValues(Query));

            this.next_btn = rootView.findViewById(id.next);
            this.pre_btn = rootView.findViewById(id.prev);

            this.LoadWebview(0);

            this.Current();


            this.next_btn.setOnClickListener(arg0 -> this.Next());

            this.pre_btn.setOnClickListener(arg0 -> this.Previous());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    //#######################################################################################################
    private final void LoadWebview(int pos) {
        this.profile_webvw.getSettings().setJavaScriptEnabled(true);
        this.profile_webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        this.profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.profile_webvw.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.profile_webvw.getSettings().setDefaultTextEncodingName("utf-8");

        this.profile_webvw.setWebChromeClient(new Profile_Prescription.MyWebChromeClient());

        this.profile_webvw.setBackgroundColor(0x00000000);
        this.profile_webvw.setVerticalScrollBarEnabled(true);
        this.profile_webvw.setHorizontalScrollBarEnabled(true);


        Toast.makeText(this.getActivity(), this.getString(string.prev_med_history_loaded), Toast.LENGTH_SHORT).show();
//        BaseConfig.SnackBar(this,  getString(R.string.prev_med_history_loaded) , parentLayout);


        this.profile_webvw.getSettings().setJavaScriptEnabled(true);

        this.profile_webvw.getSettings().setAllowContentAccess(true);


        this.profile_webvw.setOnLongClickListener(v -> true);

        this.profile_webvw.setLongClickable(false);


        this.profile_webvw.addJavascriptInterface(new Profile_Prescription.WebAppInterface(this.getActivity()), "android");
        try {

            this.profile_webvw.loadDataWithBaseURL("file:///android_asset/", this.LoadPrescriptionDetails(pos), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private final String LoadPrescriptionDetails(int pos_val) {

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        //********************************************
        this.mypatientprevmedicinehistory_medid = new ArrayList<>();

        Cursor c = db
                .rawQuery(
                        "select distinct Medid from Mprescribed where Ptid='" + this.BUNDLE_PATIENT_ID + "' order by Medid desc;",
                        null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    this.mypatientprevmedicinehistory_medid.add(c.getString(c.getColumnIndex("Medid")));

                } while (c.moveToNext());
            }
        }
        c.close();
        //********************************************


        String values;
        String PreviousMedicalHistory = "", HereditaryDiseases = "";
        String AllergicTo = "", Smoking = "", Alcohol = "", Tobacco = "", Others = "", Medication = "", FamilyMedicalHistory = "";
        String Bowel = "", Micturition = "", PresentIllness = "", PastIllness = "", TreatmentforMedicineNamePeriod = "";
        String Obstetric = "", Gynaec = "";

        ArrayList<Profile_Prescription.PrescriptionGetSet> prescriptionGetSets = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder InjectionstringBuilder = new StringBuilder();


        String NebuNormal, NebuAstha;
        String NebuDosag1 = "", NebuDura1 = "", NebuQuantity1 = "";
        String NebuDosag2 = "", NebuDura2 = "", NebuQuantity2 = "";
        String Duration2 = "", Suturing = "", Plastering = "", Plastering_Slab = "", Plastering_Cast = "";


        String[] Tabledata;
        String remarksStr, diagnosisStr = null, symStr;


        Cursor c1 = db.rawQuery("select distinct remarks,refdocname,Medid,medicinename,treatmentfor,diagnosis,nextvisit,Actdate from Mprescribed where Ptid='" + this.BUNDLE_PATIENT_ID + "' and Medid='" + this.mypatientprevmedicinehistory_medid.get(pos_val) + "' order by Medid desc ;", null);

        if (c1.getCount() > 0) {
            this.NoDataFound.setVisibility(View.GONE);
        } else {
            this.NoDataFound.setVisibility(View.VISIBLE);
        }


        /*
          Medicine list
         */
        String tableData="";
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {

                    Tabledata = new String[c1.getCount()];

                    Tabledata = c1.getString(c1.getColumnIndex("medicinename")).split("_");

                    stringBuilder.append("  <tr>\n" + "                  <th><font color=\"#000\">").append(Tabledata[0].replace("[", "").replace("]", "")).append("</font></th>\n \n").append("                    <th><font color=\"#000\">").append(Tabledata[1]).append("</font></th>\n \n").append("               \t <th><font color=\"#000\">").append(Tabledata[2]).append("</font></th>\n \n").append("              \t <th><font color=\"#000\">").append(Tabledata[3]).append("</font></th>\n\n").append("                 </tr>\n");

                    this.sbM.append(c1.getString(c1.getColumnIndex("medicinename")));
                    this.sbM.append('\n');

                    this.nxtdt = c1.getString(c1.getColumnIndex("nextvisit"));

                    if (this.nxtdt.contains("1900") || this.nxtdt.length() == 0)//If next visit date empty na set agum @Kumar
                    {
                        this.nxtdt = "-";
                    }

                    this.visiteddt = c1.getString(c1.getColumnIndex("Actdate")).split(" ");
                    remarksStr = c1.getString(c1.getColumnIndex("remarks"));

                    this.refdocname = c1.getString(c1.getColumnIndex("refdocname"));
                    diagnosisStr = c1.getString(c1.getColumnIndex("diagnosis"));
                    this.SymptomsValue = c1.getString(c1.getColumnIndex("treatmentfor"));


                    tableData=tableData+"<tr>\n" +
                            "<td width=\"50%\">"+Tabledata[0].replace("[", "").replace("]", "")+"</td>\n" +
                            "<td>"+Tabledata[1]+"</td>\n" +
                            "<td>"+Tabledata[2]+"</td>\n" +
                            "<td>"+Tabledata[3]+"</td>\n" +
                            "</tr>\n" ;

                } while (c1.moveToNext());
            }

        }
        c1.close();


        c1=null;
        /*
          Emergecny list
         */
        StringBuilder str = new StringBuilder();
        boolean chk = BaseConfig.LoadReportsBooleanStatus("select distinct Id as dstatus1 from Bind_EmergencyCausality where Patid='" + this.BUNDLE_PATIENT_ID + "' and MPID='" + this.mypatientprevmedicinehistory_medid.get(pos_val) + "' order by Id desc ;");
        if (chk) {
            c1 = db.rawQuery("select distinct Injection,Nebulization_Normal,Nebulization_Asthaline,Suturing,Plastering from Bind_EmergencyCausality where Patid='" + this.BUNDLE_PATIENT_ID + "' and MPID='" + this.mypatientprevmedicinehistory_medid.get(pos_val) + "' order by Id desc ;", null);

            if (c1 != null) {
                if (c1.moveToFirst()) {
                    do {
                        try {


                            String InjectionJsonData = c1.getString(c1.getColumnIndex("Injection"));

                            JSONArray jsonArray = new JSONArray(InjectionJsonData);
                            JSONObject objJson = null;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                objJson = jsonArray.getJSONObject(i);

                                InjectionstringBuilder.append("  <tr>\n" + "<th><font color=\"#000\">").append(objJson.get("InjectionName")).append("</font></th>\n \n").append("<th><font color=\"#000\">").append(objJson.get("Dosage")).append("</font></th>\n \n").append("<th><font color=\"#000\">").append(objJson.get("Quantity")).append("</font></th>\n \n").append("</tr>\n");


                            }

                            NebuNormal = c1.getString(c1.getColumnIndex("Nebulization_Normal"));//.toString().split("-");
                            if (NebuNormal.length() > 0 && !NebuNormal.equalsIgnoreCase("[]")) {
                                String[] NebuNormal1 = NebuNormal.split("-");
                                NebuDosag1 = NebuNormal1[1];
                                NebuDura1 = NebuNormal1[2];
                                NebuQuantity1 = NebuNormal1[3];
                            }


                            NebuAstha = c1.getString(c1.getColumnIndex("Nebulization_Asthaline"));//.toString().split("-");
                            if (NebuAstha.length() > 0 && !NebuNormal.equalsIgnoreCase("[]")) {
                                String[] NebuAstha1 = NebuAstha.split("-");
                                NebuDosag2 = NebuAstha1[1];
                                NebuDura2 = NebuAstha1[2];
                                NebuQuantity2 = NebuAstha1[3];
                            }

                            Suturing = c1.getString(c1.getColumnIndex("Suturing"));

                            String Plastering_Str = c1.getString(c1.getColumnIndex("Plastering"));
                            if (Plastering_Str.length() > 0 && Plastering_Str != null && !Plastering_Str.equalsIgnoreCase("[]")) {
                                JSONArray jsonArray1 = new JSONArray(Plastering_Str);
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    objJson = jsonArray1.getJSONObject(i);
                                    Plastering = objJson.get("Plastering").toString();
                                    Plastering_Slab = objJson.get("Plastering_Slab").toString();
                                    Plastering_Cast = objJson.get("Plastering_Cast").toString();
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } while (c1.moveToNext());
                }

            }
            c1.close();

            str.append("<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> Emergency / Causality </font>\n" +
                    //Injection
                    "<div class=\"table-responsive\">          \n" + "<table class=\"table table-bordered\">\n" + "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Injection </b></td> \n" + "  <tr>\n" + "    <th bgcolor=\"#3d5987\" width=\"50%\"><font color=\"#fff\"> Injection Name </font></th>\n" + "    <th bgcolor=\"#3d5987\" width=\"50%\"><font color=\"#fff\"> Dosage </font></th>\n" + "    <th bgcolor=\"#3d5987\" width=\"50%\"><font color=\"#fff\"> Quantity </font></th>\n" + "  </tr>\n" + " \n").append(InjectionstringBuilder).append(" \n").append("</table>\n").append("</div>\n").append(


                    //Nebulization
                    "<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("<td height=\"20\" bgcolor=\"#3d5987\" style=\"color:#FFF;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Nebulization-Normal Saline </b></td> \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Dosage </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(NebuDosag1).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Duration (In days) </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(NebuDura1).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Quantity </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(NebuQuantity1).append("</td>\n").append("</tr>\n").append("</table>\n").append("</div>\n").append("<table class=\"table table-bordered\">\n").append("<td height=\"20\" bgcolor=\"#3d5987\" style=\"color:#FFF;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Nebulization-Asthaline Saline </b></td> \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Dosage </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(NebuDosag2).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Duration (In days) </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(NebuDura2).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Quantity </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(NebuQuantity2).append("</td>\n").append("</tr>\n").append("</table>\n").append("</div>\n").append(


                    //Suturing
                    "<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("<td height=\"20\" bgcolor=\"#3d5987\" style=\"color:#FFF;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Suturing </b></td> \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> For body part </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Suturing).append("</td>\n").append("</tr>\n").append("</table>\n").append("</div>\n").append(


                    //Plastering
                    "<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("<td height=\"20\" bgcolor=\"#3d5987\" style=\"color:#FFF;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Plastering </b></td> \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> For body part </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Plastering).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Plastering - SLAB </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Plastering_Slab).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Plastering - CAST </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Plastering_Cast).append("</td>\n").append("</table>\n").append("</div>\n");

        }


      /*  values = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                '\n' +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                '\n' +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" +
                '\n' +
                '\n' +
                '\n' +
                '\n' +
                "</head>\n" +
                "<body>  \n" +
                " \n" +


                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> " + getString(R.string.previous_medical_history) + "</font>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<div class=\"table-responsive\">          \n" +
                "<table class=\"table table-bordered\">\n" +
                "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>" + getString(R.string.referred_doctor_name) + "</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\">" + refdocname + "</td>\n" +
                "  <tr>\n" +
                "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.medicinename) + "</font></th>\n" +
                "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.interval) + "</font></th>\n" +
                "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.frequency) + "</font></th>\n" +
                "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.duration) + "</font></th>\n" +
                "  </tr>\n" +
                " \n" + stringBuilder +
                " \n" +
                "</table>\n" +
                "</div>\n" +
                "<td height=\"20\" width=\"50%\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> " + getString(R.string.symptoms) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" width=\"50%\" style=\"color:#000000;\"> " + SymptomsValue + "</td>\n" +
                '\n' + "</div>\n </br></br>" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> " + getString(R.string.diagnosis) + ":</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\"> " + diagnosisStr + "</td>\n" +
                "\n\n</br>" +
                "<div class=\"table-responsive\">          \n" +
                "<table class=\"table\">\n" +
                " <tr>\n" +
                "    <th  bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.visited_on) + ": </font></th>\n" +
                "    <th  bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.nextvisiton) + ":</font></th>\n" +
                "\t </tr>\n" +
                "  <tr>\n" +
                "    <td>" + visiteddt[0] + "</td>\n" +
                "    <td>" + nxtdt + "</td>\n" +
                "\t  </tr>\n" +
                " \n" +
                "</table>\n" +
                "</div>\n"

                + str +

                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                                                                               ";


*/

        values="<!DOCTYPE html>\n" +
                "<!-- saved from url=(0103)file:///C:/Users/Ponnusamy/Desktop/Responsive-Timeline-Generator-With-jQuery-Timeline/Prescription.html -->\n" +
                "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\"> \n" +
                "<link rel=\"stylesheet\" href=\"./Prescription_files/normalize.min.css\">\n" +
                "\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" +
                "\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                "\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" +
                "\n" +
                "<style>\n" +
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
                "</style></head>\n" +
                "\n" +
                "\n" +
                "<body style=\"padding: 20px\">\n" +
                "\n" +
                "<div id=\"vt4\">\n" +
                "\n" +
                "<!-- card 1 -->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>PREVIOUS MEDICAL HISTORY</button></h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tbody><tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.referred_doctor_name)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>"+ this.refdocname +"</td>\n" +
                "</tr>\n" +
                " \n" +
                "</tbody></table>\n" +
                "</div> \n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "<!-- card 2 -->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>MEDICATION Details</button></h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "\n" +
                "<tbody><tr>\n" +
                "<th width=\"25%\">"+ this.getString(string.treatment_for)+"</th>\n" +
                "<th width=\"25%\">"+ this.getString(string.medicine_name)+"</th>\n" +
                "<th width=\"25%\">"+ this.getString(string.period)+"</th>\n" +
                "<th width=\"25%\">"+ this.getString(string.duration)+"</th>\n" +
                "</tr>\n" +
                "\n" +
                "\n" +

                "\n" +
                tableData+
                "\n" +
                "\n" +
                "</tbody></table>\n" +
                "</div> \n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "\n" +


                "<!-- card 6 -->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>Details</button></h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tbody><tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.symptoms)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>"+ this.SymptomsValue +"</td>\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.getString(string.diagnosis)+"</td>\n" +
                "<td>:</td>\n" +
                "<td>"+diagnosisStr+"</td>\n" +
                "</tr>\n" +
                "\n" +
                "</tbody></table>\n" +
                "</div> \n" +
                "</div>\n" +
                "\n" +


                "<!-- card 4 -->\n" +
                "<div class=\"card\">\n" +
                "<h3><button>Date</button></h3>\n" +
                "\n" +
                "<div class=\"table-responsive\">\n" +
                "<table class=\"table\">\t\t \n" +
                "<tbody><tr>\n" +
                "\n" +
                "<td width=\"50%\"><font color=\"black\"><b>"+ this.getString(string.visited_date)+"</b></font></td>\n" +
                "<td width=\"50%\"><font color=\"black\"><b>"+ this.getString(string.nextvisiton)+"</b></font></td>\n" +
                "\n" +
                "</tr>\n" +
                "\n" +
                "<tr>\n" +
                "<td width=\"50%\">"+ this.visiteddt[0]+"</td>\n" +
                "<td width=\"50%\">"+ this.nxtdt +"</td>\n" +
                "</tr>\n" +
                "\n" +
                "</tbody></table>\n" +
                "</div> \n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</div><!-- End vt4 --> \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n"
                + str +

                "</body></html>";

        db.close();

       Toast.makeText(this.getActivity(), this.getString(string.prescription_profile_success), Toast.LENGTH_SHORT).show();

        return values;
    }

    public final void SelectedGetPrevMedicineHistory(int pos) {
        // TODO Auto-generated method stub
        this.sbM = new StringBuilder();
        String remarksStr = "";
        String diagnosisStr = "";
        String symStr = "";

        SQLiteDatabase db = BaseConfig.GetDb();

        String QueryStr = "select remarks,refdocname,Medid,medicinename,treatmentfor,diagnosis,nextvisit,Actdate from Mprescribed where Ptid='" + this.BUNDLE_PATIENT_ID + "' and Medid='" + this.mypatientprevmedicinehistory_medid.get(pos) + "' order by Medid desc ;";
        Cursor c = db
                .rawQuery(QueryStr,
                        null);
        if (c != null) {
            if (c.moveToFirst()) {


                do

                {

                    this.sbM.append(c.getString(c.getColumnIndex("medicinename")));
                    this.sbM.append('\n');

                    this.nxtdt = c.getString(c.getColumnIndex("nextvisit"));
                    this.visiteddt = c.getString(c.getColumnIndex("Actdate")).split(" ");
                    remarksStr = c.getString(c.getColumnIndex("remarks"));

                    this.refdocname = c.getString(c.getColumnIndex("refdocname"));
                    diagnosisStr = c.getString(c.getColumnIndex("diagnosis"));
                    symStr = c.getString(c.getColumnIndex("treatmentfor"));

                } while (c.moveToNext());

            }
        }
        db.close();
        c.close();

        this.medicine_name.setText(this.sbM.toString());
        this.nextvisiton.setText(this.nxtdt);
        this.visitedon.setText(this.visiteddt[0]);
        this.diagnosisValue.setText(diagnosisStr);
        this.symptomsValue.setText(symStr);
        this.ref_docname.setText(this.getString(string.referred_doctor_name) + ": " + this.refdocname);


        try {
            this.remarks.setText(BaseConfig.CheckDBString(remarksStr));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /////////////////////////////////////////////////////////////////////////////////
    private final void Previous() {
        this.pos += 1;
        if (this.pos < this.mypatientprevmedicinehistory_medid.size()) {
            this.LoadWebview(this.pos);
            this.next_btn.setVisibility(View.VISIBLE);
        }
        if (this.pos == this.mypatientprevmedicinehistory_medid.size() - 1) {
            this.pre_btn.setVisibility(View.GONE);
            this.next_btn.setVisibility(View.VISIBLE);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    private final void Next() {
        this.pos -= 1;
        if (this.pos >= 0) {
            //SelectedGetPrevMedicineHistory(pos);
            this.LoadWebview(this.pos);
            this.pre_btn.setVisibility(View.VISIBLE);
        }
        if (this.pos == 0) {
            this.next_btn.setVisibility(View.GONE);
            this.pre_btn.setVisibility(View.VISIBLE);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    private final void Current() {
        if (this.mypatientprevmedicinehistory_medid.size() > 0) {
            //SelectedGetPrevMedicineHistory(0);
            this.LoadWebview(0);

        }
        if (this.mypatientprevmedicinehistory_medid.size() > 1) {
            this.pre_btn.setVisibility(View.VISIBLE);
            this.next_btn.setVisibility(View.GONE);
        }
    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    static class PrescriptionGetSet {
        String medicinename;
        String interval;
        String frequency;
        String duration;

        public PrescriptionGetSet(String medicinename, String interval, String frequency, String duration) {
            this.medicinename = medicinename;
            this.interval = interval;
            this.frequency = frequency;
            this.duration = duration;
        }

        public final String getMedicinename() {
            return this.medicinename;
        }

        public final void setMedicinename(String medicinename) {
            this.medicinename = medicinename;
        }

        public final String getInterval() {
            return this.interval;
        }

        public final void setInterval(String interval) {
            this.interval = interval;
        }

        public final String getFrequency() {
            return this.frequency;
        }

        public final void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public final String getDuration() {
            return this.duration;
        }

        public final void setDuration(String duration) {
            this.duration = duration;
        }
    }

    //#######################################################################################################
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
/////////////////////////////////////////////////////////////////////////////////


}
