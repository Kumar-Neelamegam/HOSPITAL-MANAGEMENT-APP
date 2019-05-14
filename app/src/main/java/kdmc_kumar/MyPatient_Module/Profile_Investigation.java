package kdmc_kumar.MyPatient_Module;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
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
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DateTimelineRowitemAdapter;
import kdmc_kumar.Adapters_GetterSetter.TestRecylerAdapter;
import kdmc_kumar.Adapters_GetterSetter.Timeline_Objects;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_ANCTestReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_Blood_Report;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_HIV_Report;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_StoolReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_UrineReport;

/**
 * Created by Ponnusamy M on 3/31/2017.
 */

public class Profile_Investigation extends Fragment {


    @BindView(id.investigation_parent_layout) LinearLayout investigationParentLayout;
    @BindView(id.investigation_profile_patientid) TextView investigationProfilePatientid;
    @BindView(id.investigation_profile_recycler) RecyclerView investigationProfileRecycler;
    @BindView(id.investigation_profile_img_nodata) AppCompatImageView investigationProfileImgNodata;
    @BindView(id.investigation_profile_nodatatext) TextView investigationProfileNodatatext;
    @BindView(id.webvw_investigation_profile) WebView webvwInvestigationProfile;
    @BindView(id.investigation_reports_layout) LinearLayout investigationReportsLayout;
    @BindView(id.investigation_profile_reports) RecyclerView investigationProfileReports;


    private String Ecg = "";
    private String Rate = "-";
    private String Rhythm = "-";
    private String PRInterval = "-";
    private String QRS = "-";
    private String ST = "-";
    private String TWave = "-";
    private String AxisDeviation = "-";
    private String BundleBranch = "-";
    private String ConductionDefects = "-";
    private String TreadMill = "-";
    private String EEGFor = "-";
    private String EEGSummary = "-";
    private StringBuilder testtablerows;
    private StringBuilder testscantablerows;
    private StringBuilder testxrayrows;


    private ArrayList<CommonDataObjects.TestItem> testItems;
    private TestRecylerAdapter testRecylerAdapter;
    private GridLayoutManager lLayout;
    private String BUNDLE_PATIENT_ID;

    // ///////////////////////////////////////////////////////////////////////////////////////////////
    //Investigations
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    //Load Investigation
    private StringBuilder str1;
    //Load ANGIOGRAM
    private String Coronary = "-";
    private String Brain = "-";
    private String Lower_limb = "-";
    private String Upper_limb = "-";
    private String Mesenteric = "-";
    private StringBuilder strTestDetails = new StringBuilder();
    /**
     * Static string values to get the subtest
     */
    private final String compareRBSName = "blood sug random";
    private final String compareFBSName = "blood sug fasting";
    private final String comparePBSName = "blood sug pp";
    private final String compareExamination = "All examination of blood";
    private final String compareStoolReport = "All stool test";
    private final String compareUrineReport = "All urine test";
    private final String compareAncfpReport = "All anc fp test";

    //Load SCAN
    //List<String> list1 = new ArrayList<String>();
    private final String compareHIVReport = "All hiv test";

    //Load TEST
    //List<String> list2 = new ArrayList<String>();
    //Load XRAY
    private StringBuilder strXrayDetails = new StringBuilder();
    private StringBuilder strScanDetails = new StringBuilder();
    private StringBuilder strAngiogramDetails = new StringBuilder();
    //Load ECG
    private StringBuilder strECGDetails = new StringBuilder();
    //Load EEG
    private StringBuilder strEEGDetails = new StringBuilder();
    private String XraySummary;

    public Profile_Investigation() {
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layout.new_investigation_profile, container, false);


        try {
            this.GETINITIALIZATION(rootView);

            this.CONTROLLISTENERS(rootView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }




    private void GETINITIALIZATION(View rootView) {


        ButterKnife.bind(this, rootView);

        Bundle args = this.getArguments();
        this.BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);


        this.webvwInvestigationProfile.getSettings().setJavaScriptEnabled(true);
        this.webvwInvestigationProfile.setWebChromeClient(new WebChromeClient());


        this.lLayout = new GridLayoutManager(this.getActivity(), 2);
        this.investigationProfileReports.setHasFixedSize(true);
        this.investigationProfileReports.setLayoutManager(this.lLayout);
        this.investigationProfileReports.setNestedScrollingEnabled(false);


        String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');

        this.investigationProfilePatientid.setText(String.format("%s - %s", Patient_Name, this.BUNDLE_PATIENT_ID));



        assert this.investigationProfileReports != null;

        this.setRecylerViewDetails(this.investigationProfileReports);


        this.investigationProfileRecycler.setHasFixedSize(true);
        this.investigationProfileRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));


        ArrayList<Timeline_Objects> datetime_arrayList = this.LoadDateTimeLine();


        if (datetime_arrayList.size() > 0) {
            this.investigationProfileImgNodata.setVisibility(View.GONE);
            this.investigationProfileNodatatext.setVisibility(View.GONE);
            this.webvwInvestigationProfile.setVisibility(View.VISIBLE);
            this.investigationProfileRecycler.setVisibility(View.VISIBLE);

        } else {
            this.investigationProfileRecycler.setVisibility(View.GONE);
            this.webvwInvestigationProfile.setVisibility(View.GONE);
            this.investigationProfileImgNodata.setVisibility(View.VISIBLE);
            this.investigationProfileNodatatext.setVisibility(View.VISIBLE);

        }


        this.adapter = new DateTimelineRowitemAdapter(this.getActivity(), datetime_arrayList);
        this.investigationProfileRecycler.setAdapter(this.adapter);// set adapter on recyclerview
        this.adapter.notifyDataSetChanged();// Notify the adapter



    }



    DateTimelineRowitemAdapter adapter;
    ArrayList<Timeline_Objects> datetime_arrayList;

    private ArrayList<Timeline_Objects> LoadDateTimeLine() {

        this.datetime_arrayList = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery("select   id,Actdate as date,mtestid from medicaltest where ptid='" + this.BUNDLE_PATIENT_ID + "' order by id desc;", null);

        if (c.moveToFirst()) {
            do {

                String ID = c.getString(c.getColumnIndex("id"));
                String DATE = c.getString(c.getColumnIndex("date"));
                DATE = DATE.contains("/") ? DATE.replace("/","-") : DATE;
                String INVESTIGATIONID = c.getString(c.getColumnIndex("mtestid"));

                this.datetime_arrayList.add(new Timeline_Objects(ID, DATE, INVESTIGATIONID));

            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return this.datetime_arrayList;
    }


    private void CONTROLLISTENERS(View rootView) {

        this.adapter.setOnItemClickListener((view, position, items) -> {

            try {
                this.LoadInvestigationInfo(items.get(position).getUnique_Id());

                this.LoadECG(items.get(position).getUnique_Id());

                this.LoadEEG(items.get(position).getUnique_Id());

                this.LoadAngiogram(items.get(position).getUnique_Id());

                this.LoadScanDtls(items.get(position).getUnique_Id());

                this.LoadTestDtls(items.get(position).getUnique_Id());

                this.LoadXrayDtls(items.get(position).getUnique_Id());

                this.LoadWebview();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });



    }

    private final void setRecylerViewDetails(RecyclerView re) {

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        this.testItems = new ArrayList<>();

        Cursor c = db.rawQuery("select * from Bind_Upload where Patid='" + this.BUNDLE_PATIENT_ID + '\'', null);
        try {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String Fname = c.getString(c.getColumnIndex("Fname"));
                        String Type = c.getString(c.getColumnIndex("Type"));
                        String Cdtime = c.getString(c.getColumnIndex("Cdtime"));
                        String Upload_Base64 = c.getString(c.getColumnIndex("Upload_Base64"));
                        String Remark_Summary = c.getString(c.getColumnIndex("Remarks"));


                        this.testItems.add(new CommonDataObjects.TestItem(Fname, Type, Cdtime, Upload_Base64, Remark_Summary));


                    } while (c.moveToNext());
                }
            }

            this.testRecylerAdapter = new TestRecylerAdapter(this.testItems);
            re.setAdapter(this.testRecylerAdapter);
            db.close();
            c.close();

            if (this.testRecylerAdapter.getItemCount() > 0) {
                this.investigationReportsLayout.setVisibility(View.VISIBLE);
            } else {
                this.investigationReportsLayout.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final void LoadInvestigationInfo(String mtest) {

        this.str1 = new StringBuilder();
        StringBuilder values = new StringBuilder();

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        Cursor c = null;
        try {


            String Query = "select * from Medicaltest where mtestid='" + mtest.trim() + "' and Ptid='" + this.BUNDLE_PATIENT_ID + "'";

            c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String Symptoms = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("treatmentfor")));
                        String ProvisionalDiagnosis = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Diagnosis")));

                        this.str1.append("<div >\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr >\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(this.getString(string.symptoms)).append("</b></td> \n").append("<td height=\"20\" " +
                                "width=\"50%\" style=\"color:#000000;\">:   ").append(Symptoms).append("</td> \n").append("</tr>\n").append('\n').append("<tr >\n").append("<td height=\"20\"" +
                                "  width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(this.getString(string.provisional_diagnosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(ProvisionalDiagnosis).append("</td> \n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div>");


                    } while (c.moveToNext());
                }
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void LoadECG( String dateval2) {

        try {
            this.strECGDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from ECGTEST where Ptid='" + this.BUNDLE_PATIENT_ID + "' " +
                    "and mtestid='" + dateval2.trim() + "' ");
            if (chk) {

                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select Ecg,PR,ecgrate,ecgrhyrhm,ecgpulse,ecgqrs,ecgst,ecgt,ecgaxis,Bundlebranchblock,Conductiondefects,Treadmill from ECGTEST where Ptid='" + this.BUNDLE_PATIENT_ID + "' " +
                        "and mtestid='" + dateval2.trim() + "' ";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            this.Ecg = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Ecg")));
                            this.Rate = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("ecgrate")));
                            this.Rhythm = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("ecgrhyrhm")));
                            this.PRInterval = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("PR")));
                            this.QRS = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("ecgqrs")));
                            this.ST = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("ecgst")));
                            this.TWave = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("ecgt")));
                            this.AxisDeviation = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("ecgaxis")));
                            this.BundleBranch = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Bundlebranchblock")));
                            this.ConductionDefects = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Conductiondefects")));
                            this.TreadMill = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Treadmill")));

                        } while (c.moveToNext());
                    }
                }
                c.close();
                db.close();

                this.strECGDetails.append("<font class=\"sub\">  \n" + "<i class=\"fa fa-user-circle-o fa-2x\" aria-hidden=\"true\"></i> ").append(this.getString(string.ecg_diagnostic_summary)).append("</font>\n").append('\n').append("</br>\n").append('\n').append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.ecg_summary_details)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.Ecg).append("</td> \n").append("</tr>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.rate)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.Rate).append("</td> \n").append("</tr>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>QRS</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.QRS).append("</td>\n").append("</tr>\n").append('\n').append(" <tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.axis_deviation)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.AxisDeviation).append("</td>\n").append("</tr> \n").append('\n').append('\n').append("<tr > \n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.treadmill)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.TreadMill).append("</td>\n").append("</tr>\n").append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.rhythm)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.Rhythm).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>ST</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.ST).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.bundle_branch)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.BundleBranch).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.pr_interval_secs_t_t)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.PRInterval).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>T wave:</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.TWave).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.conduction_defects)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.ConductionDefects).append("</td>\n").append("</tr>\n").append('\n').append("</tbody>\n").append("</table>\n");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void LoadEEG(String dateval2) {


        try {
            this.strEEGDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from EEG where Ptid='" + this.BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ");
            if (chk) {
                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select Actdate,IFNULL(EEGFor,'-')as EEGFor,IFNULL(Comment,'-')as Comment,IFNULL(Summary,'-')as Summary,IFNULL(Valuedata,'-')as Valuedata from EEG where Ptid='" + this.BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            if (c.getString(c.getColumnIndex("EEGFor")).equalsIgnoreCase("")) {

                                this.EEGFor = "-";
                                this.EEGSummary = "-";


                            } else {
                                this.EEGFor = (Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("EEGFor"))));
                                this.EEGSummary = (Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Summary"))));

                            }

                        } while (c.moveToNext());
                    }
                }
                c.close();
                db.close();


                this.strEEGDetails.append('\n' + "<font class=\"sub\">  \n" + "<i class=\"fa fa-user-circle-o fa-2x\" aria-hidden=\"true\"></i> ").append(this.getString(string.eeg_summary)).append("</font>\n").append('\n').append('\n').append('\n').append("<div class=\"table-responsive\">\n").append('\n').append('\n').append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> ").append(this.getString(string.eeg_for)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\"> : ").append(this.EEGFor).append("</td> \n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> ").append(this.getString(string.eeg_summary)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\"> : ").append(this.EEGSummary).append("</td> \n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div>\n").append('\n');
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private final void LoadAngiogram(String dateval2) {
        try {
            this.strAngiogramDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Angiogram where Ptid='" + this.BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ");
            if (chk) {


                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select Actdate,IFNULL(AngioFor,'')as AngioFor,Comment,IFNULL(Coronary,'')as Coronary,IFNULL(Brain,'')as Brain,IFNULL(Upperlimb,'')as Upperlimb,IFNULL(Lowerlimb,'')as Lowerlimb,IFNULL(Mesenteric,'')as Mesenteric from Angiogram where Ptid='" + this.BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ";
                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            this.Coronary = c.getString(c.getColumnIndex("Coronary"));
                            this.Brain = c.getString(c.getColumnIndex("Brain"));
                            this.Lower_limb = c.getString(c.getColumnIndex("Upperlimb"));
                            this.Upper_limb = c.getString(c.getColumnIndex("Lowerlimb"));
                            this.Mesenteric = c.getString(c.getColumnIndex("Mesenteric"));


                        } while (c.moveToNext());
                    }
                }

                db.close();
                c.close();

                this.strAngiogramDetails.append("<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> ").append(this.getString(string.angiogram)).append("</font>\n").append('\n').append('\n').append("<div class=\"table-responsive\">          \n").append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.coronary)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  ").append(this.Coronary).append("</td> \n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.brain)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.Brain).append("</td>\n").append("</tr>\n").append('\n').append(" <tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.lower_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.Lower_limb).append("</td>\n").append("</tr> \n").append('\n').append("<tr > \n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.upper_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.Upper_limb).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(this.getString(string.mesenteric)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(this.Mesenteric).append("</td>\n").append("</tr>\n").append('\n').append("</table>\n").append("   \n").append("</tbody>\n").append("</div>\n");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private final void LoadScanDtls(String dateval2) {

        try {
            this.strScanDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Scantest where Ptid='" + this.BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "' ");
            if (chk) {

                StringBuilder scanDtlsStr = new StringBuilder();


                this.testscantablerows = new StringBuilder();


                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                //ArrayList<Item> items = new ArrayList<Item>();

                String Query = "select Actdate,scanvalue,scanname,scansummary from Scantest where Ptid='" + this.BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "'";

                //Log.e("Get Scan Query: ", Query);
                //Log.e("Get Scan Query: ", Query);

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            String value[] = c.getString(c.getColumnIndex("scanname")).split("/");


                            this.testscantablerows.append("  <tr>\n" + "    <td>").append(value[0]).append("</td>\n").append("    <td>").append(value[1]).append("</td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("scanvalue")))).append("</td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("scansummary")))).append("</td>\n").append("  </tr>\n");


                        } while (c.moveToNext());
                    }
                }
                c.close();
                db.close();


                //ScanSummary = scanDtlsStr.toString();

                this.strScanDetails.append('\n' + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> ").append(this.getString(string.scan)).append("</font>\n").append('\n').append("<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("  <tr>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.scan_name)).append("</font></th>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.scan_for)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.resultvalue)).append("</font></th>\n").append("\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.remarks)).append("</font></th>\n").append('\n').append("  </tr>\n").append(this.testscantablerows);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //#######################################################################################################

    private final void LoadTestDtls(String dateval2) {

        this.strTestDetails = new StringBuilder();
        boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Medicaltestdtls where Ptid='" + this.BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "';");
        if (chk) {

            StringBuilder TestDtlsStr = new StringBuilder();
            try {


                this.testtablerows = new StringBuilder();


                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select * from Medicaltestdtls where Ptid='" + this.BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "';";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            String allTest = Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("alltest")));

                            String[] values = allTest.split("/");


                            if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareRBSName)) {
                                this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("temperature")))).append("</td>\n").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");

                            } else if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareFBSName)) {
                                this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append(
                                        // "\t<td onClick=\"showAndroidToast('"+checkNullEmpty(c.getString(c.getColumnIndex("bps"))).toString()+"')\">"+checkNullEmpty(c.getString(c.getColumnIndex("bps"))).toString()+"</td>\n" +
                                        "\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("bps")))).append("</td>\n").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");
                            } else if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.comparePBSName)) {
                                this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("bpd")))).append("</td>\n").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");
                            } else if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareExamination)) //EXAMINATION OF BLOOD - All examination of blood
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_examination_blood_test where Patid='" + this.BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast('").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareStoolReport)) //STOOL REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_stool_report where Patid='" + this.BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast1('").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareUrineReport)) //URINE REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_urine_test where Patid='" + this.BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast2('").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareAncfpReport)) //ANC FP REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_anc_fp_test where Patid='" + this.BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast3('").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (Profile_Investigation.checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(this.compareHIVReport)) //HIV REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_HIV_Report where Patid='" + this.BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("\t<td> - </td>\n").append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast4('").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else//other tests
                            {
                                this.testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testvalue")))).append("</td>\n").append(Profile_Investigation.GetResultValue(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(Profile_Investigation.GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(Profile_Investigation.GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");

                            }

                            String testDetails = this.getString(string.test_name) + "<b>" + allTest + "</b>" + " ,  " + this.getString(string.test_values) + ' ' + "<b>" + Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testvalue"))) + "</b>" + " , Test Remarks :<b> " + BaseConfig.CheckDBString(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("testsummary"))) + "</b>");

                            //  TestDtlsStr.append(String.valueOf("<br><b>" + testDetails + "</b></br>"));


                        } while (c.moveToNext());
                    }
                }

                db.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            // TestSummary = TestDtlsStr.toString();

            this.strTestDetails.append("<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> ").append(this.getString(string.test)).append("</font>\n").append('\n').append("<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("  <tr>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.test_name)).append("</font></th>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.subtest_name)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.test_values)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.test_result)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.test_units)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.test_normalrange)).append("</font></th>\n").append("\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.test_remark)).append("</font></th>\n").append('\n').append("  </tr>\n").append(this.testtablerows).append('\n').append("</table>\n").append('\n').append('\n').append("</div>\n");
        }

    }

    private final void LoadXrayDtls(String dateval2) {

        try {
            this.strXrayDetails = new StringBuilder();

            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from XRAY where Ptid='" + this.BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "' ");
            if (chk) {

                StringBuilder stringBuilderXray = new StringBuilder();


                this.testxrayrows = new StringBuilder();

                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                db.isOpen();

                String Query = "select Actdate,IFNULL(xray,'')as xray,IFNULL(xrayvalue,'')as xrayvalue,IFNULL(xraysummary,'')as xraysummary  from XRAY where Ptid='" + this.BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "' ";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            this.testxrayrows.append("  <tr>\n" + "    <td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("xray")))).append("</td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("xrayvalue")).split("-")[1])).append("</td>\n").append("\t<td>").append(Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("xrayvalue")).split("-")[0])).append("</td>\n").append("  </tr>\n");

                            this.XraySummary = (": " + Profile_Investigation.checkNullEmpty(c.getString(c.getColumnIndex("xraysummary"))));


                        } while (c.moveToNext());
                    }
                }

                db.close();
                c.close();


                this.strXrayDetails.append("</table>\n" + "</div>\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> ").append(this.getString(string.x_ray)).append("</font>\n").append('\n').append("<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("  <tr>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.xray_for)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.resultvalue)).append("</font></th>\n").append("\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(this.getString(string.remarks)).append("</font></th>\n").append('\n').append("  </tr>\n").append(this.testxrayrows).append('\n').append("</table>\n").append("</div>\n").append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //#######################################################################################################

    private static final String GetResultValue(String chkstr) {
        String str = "";

        if (chkstr.equalsIgnoreCase("Negative")) {
            str = "\t<td style=\"color:#F00;\"> Negative </td>\n";
        } else if (chkstr.equalsIgnoreCase("Positive")) {
            str = "\t<td style=\"color:#0F0;\"> Positive </td>\n";
        }

        return str;
    }

    private static String GetNormalRange(String value, String value1) {
        String str = "";
        String str2 = BaseConfig.GetValues("select NormalRange as ret_values from Testname where Testname='" + value + "' and SubTest='" + value1 + '\'');
        return str2;
    }

    private static String GetUnit(String value, String value1) {
        String str = "";
        String str2 = BaseConfig.GetValues("select Unit as ret_values from Testname where Testname='" + value + "' and SubTest='" + value1 + '\'');
        return str2;
    }

    private static final String checkNull(String val) {
        String returns_Val = "";
        try {


            return val.equalsIgnoreCase("null") || val.equalsIgnoreCase(null) || val.equalsIgnoreCase("") ? " " : val;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returns_Val;
    }

    private static final String checkNullEmpty(String val) {
        return val == null || val.isEmpty() || val.equalsIgnoreCase("null") ? " - " : val;
    }

    //#######################################################################################################
    private final void LoadWebview() {


        this.webvwInvestigationProfile.getSettings().setJavaScriptEnabled(true);
        this.webvwInvestigationProfile.setLayerType(View.LAYER_TYPE_NONE, null);
        this.webvwInvestigationProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.webvwInvestigationProfile.getSettings().setRenderPriority(RenderPriority.HIGH);
        this.webvwInvestigationProfile.getSettings().setDefaultTextEncodingName("utf-8");
        this.webvwInvestigationProfile.setWebChromeClient(new Profile_Investigation.MyWebChromeClient());
        this.webvwInvestigationProfile.setBackgroundColor(0x00000000);
        this.webvwInvestigationProfile.setVerticalScrollBarEnabled(true);
        this.webvwInvestigationProfile.setHorizontalScrollBarEnabled(true);
        this.webvwInvestigationProfile.getSettings().setJavaScriptEnabled(true);
        this.webvwInvestigationProfile.getSettings().setAllowContentAccess(true);
        this.webvwInvestigationProfile.setOnLongClickListener(v -> true);
        this.webvwInvestigationProfile.setLongClickable(false);

        this.webvwInvestigationProfile.addJavascriptInterface(new WebAppInterface(this.getActivity()), "android");
        try {

            this.webvwInvestigationProfile.loadDataWithBaseURL("file:///android_asset/", this.LoadInvetigationData(), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //#######################################################################################################

    private final String LoadInvetigationData() {

        String values;


        values = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +

                "<script type=\"text/javascript\">\n" +

                "    function showAndroidToast(toast,toast1) {\n" +
                "        android.showToast(toast,toast1);\n" +
                "    }\n" +

                "    function showAndroidToast1(toast,toast1) {\n" +
                "        android.showToast1(toast,toast1);\n" +
                "    }\n" +

                "    function showAndroidToast2(toast,toast1) {\n" +
                "        android.showToast2(toast,toast1);\n" +
                "    }\n" +

                "    function showAndroidToast3(toast,toast1) {\n" +
                "        android.showToast3(toast,toast1);\n" +
                "    }\n" +


                "    function showAndroidToast4(toast,toast1) {\n" +
                "        android.showToast4(toast,toast1);\n" +
                "    }\n" +


                "</script>\n" +

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
                "</head>\n" +
                "<body>  \n" +
                " \n" +
                '\n' + "<font class=\"sub\">  \n" +
                '\n' +
                "<i class=\"fa fa-stethoscope fa-2x\" aria-hidden=\"true\"></i> " + this.getString(string.diagnosis_details) + "</font>\n" +
                '\n' +
                this.str1 +


                this.strTestDetails +
                "</br>\n" +

                this.strScanDetails +
                "</br>\n" +

                this.strXrayDetails +
                "</br>\n" +

                this.strECGDetails +
                '\n' +
                "</br>\n" +


                this.strEEGDetails +
                "</br>\n" +

                this.strAngiogramDetails +
                "<br/>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                                      ";


        return values;
    }

    private static class MyWebChromeClient extends WebChromeClient {
    }


    class WebAppInterface {
        final Context mContext;


        WebAppInterface(Context c) {
            this.mContext = c;
        }


        @JavascriptInterface
        public final void showToast(String Ptid, String Mtestid) {//Blood report

            Profile_Investigation.this.startActivity(new Intent(this.mContext, Profile_Blood_Report.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast1(String Ptid, String Mtestid) {//Stool report

            Profile_Investigation.this.startActivity(new Intent(this.mContext, Profile_StoolReport.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast2(String Ptid, String Mtestid) {//Urine report

            Profile_Investigation.this.startActivity(new Intent(this.mContext, Profile_UrineReport.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast3(String Ptid, String Mtestid) {//ANCFP report

            Profile_Investigation.this.startActivity(new Intent(this.mContext, Profile_ANCTestReport.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast4(String Ptid, String Mtestid) {//HIV

            Profile_Investigation.this.startActivity(new Intent(this.mContext, Profile_HIV_Report.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

    }


}//END
