package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.PdfReportAdapter.MyViewHolder1;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.asyn.AsynkTaskCustom;
import kdmc_kumar.Utilities_Others.asyn.onWriteCode;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.controller.api.GeneratePDFNode;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.controller.api.GeneratePDFNodeFactory;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.GetPDFNodeJsRequest;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.JsonValue;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.PDFNodeJsResult;

/**
 * Created by Android on 13-Mar-18.
 */


public class PdfReportAdapter extends Adapter<MyViewHolder1> {

    //**********************************************************************************************
    private final ArrayList<PDFReportItems> rowItem;
    private final String BUNDLE_PATIENTID;
    private final Context ctx;

    public PdfReportAdapter(ArrayList<PDFReportItems> rowItem, String PatientId, Context ctxnew) {

        this.rowItem = rowItem;
        this.BUNDLE_PATIENTID = PatientId;
        ctx = ctxnew;

    }

    //**********************************************************************************************
    @NonNull
    @Override
    public final PdfReportAdapter.MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.newpdfreportlistrow, parent, false);

        return new PdfReportAdapter.MyViewHolder1(view);
    }

    //**********************************************************************************************
    @Override
    public final void onBindViewHolder(@NonNull PdfReportAdapter.MyViewHolder1 holder, int position) {

        try {
            PDFReportItems rowItemNew = null;
            PDFReportItems rowItemNew1 = this.rowItem.get(position);

            holder.txtpatnameid.setText(String.format("%s-%s", rowItemNew1.getPATIENTNAME(), rowItemNew1.getPATIENTID()));
            holder.txtsymptoms.setText(rowItemNew1.getSYMPTOMS());
            holder.txtdiagnosis.setText(rowItemNew1.getDIAGNOSIS());
            holder.txtvisitedon.setText(rowItemNew1.getVISITEDON());

            int ID = rowItemNew1.getId();

            holder.textCount.setText(String.valueOf(position + 1));

            holder.rootLayout.setOnClickListener(view -> new PdfReportAdapter.GeneratePDF_From_Node(ID, this.ctx).execute());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //**********************************************************************************************
    @Override
    public final int getItemCount() {
        return this.rowItem.size();
    }


    static class MyViewHolder1 extends ViewHolder {

        final LinearLayout rootLayout;
        final TextView txtpatnameid;
        final TextView txtsymptoms;
        final TextView txtdiagnosis;
        final TextView txtvisitedon;
        final TextView textCount;

        MyViewHolder1(View itemView) {
            super(itemView);

            this.rootLayout = itemView.findViewById(id.list_root);
            this.txtpatnameid = itemView.findViewById(id.patnameid);
            this.txtsymptoms = itemView.findViewById(id.patsymptoms);
            this.txtdiagnosis = itemView.findViewById(id.patdiagnosis);
            this.txtvisitedon = itemView.findViewById(id.patvisiteddate);
            this.textCount = itemView.findViewById(id.txtcount);


        }
    }


    static class GeneratePDF_From_Node extends AsyncTask<Void, Void, Void> {


        int id;

        boolean statusvalue;

        final Context ctx1;


        GeneratePDF_From_Node(int ID, Context ctx) {
            id = ID;
            this.ctx1 = ctx;
        }

        protected final Void doInBackground(Void... params) {
            //Your implementation
            try {

                this.CheckNodeServer(this.ctx1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        protected final void onPostExecute(Void result) {
            // TODO: do something with the feed


            if (BaseConfig.CheckNetwork(this.ctx1)) {

                if (this.statusvalue) {

                    //region BACKGROUND SERVICE TO CHECK PDF
                    /*
                      New Method to get pdf from server
                     */
                    AsynkTaskCustom asynkTaskCustom = new AsynkTaskCustom(this.ctx1, "Please wait...Loading Pdf...");

                    asynkTaskCustom.execute(new onWriteCode<String>() {
                        @Override
                        public String onExecuteCode() throws com.magnet.android.mms.exception.SchemaException, InterruptedException, java.util.concurrent.ExecutionException {

                            String results = "";


                            GeneratePDFNode generatePDFNode;
                            // Instantiate a controller
                            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(PdfReportAdapter.GeneratePDF_From_Node.this.ctx1);
                            GeneratePDFNodeFactory controllerFactory = new GeneratePDFNodeFactory(magnetClient);
                            generatePDFNode = controllerFactory.obtainInstance();

                            String contentType = "application/json";
                            GetPDFNodeJsRequest body = new GetPDFNodeJsRequest();


                            JsonValue json = new JsonValue();

                            String PATIENTID = "";
                            String PATIENTNAME = "";
                            String INVESTIGATIONID = "";
                            String MEDICINEID = "";
                            String DIAGNOSISID = "";
                            String SYMPTOMS = "";
                            String DIAGNOSIS = "";
                            String AGE = "";
                            String GENDER = "";
                            String DOCTORNAME = "";
                            String DOCTORID = "";
                            String DOCTORSPEC = "";
                            String DATETIME = "";
                            String NEXTVISITDATE = "";
                            String HOSPITALNAME = "";

                            SQLiteDatabase db = BaseConfig.GetDb();
                            String Query = "select * from Bind_PDFInfo where Id='" + PdfReportAdapter.GeneratePDF_From_Node.this.id + '\'';
                            Cursor c = db.rawQuery(Query, null);
                            if (c != null) {
                                if (c.moveToFirst()) {
                                    do {

                                        PATIENTID = c.getString(c.getColumnIndex("PATIENTID"));
                                        PATIENTNAME = c.getString(c.getColumnIndex("PATIENTNAME"));
                                        INVESTIGATIONID = c.getString(c.getColumnIndex("INVESTIGATIONID"));
                                        MEDICINEID = c.getString(c.getColumnIndex("MEDICINEID"));
                                        DIAGNOSISID = c.getString(c.getColumnIndex("DIAGNOSISID"));
                                        SYMPTOMS = c.getString(c.getColumnIndex("SYMPTOMS"));
                                        DIAGNOSIS = c.getString(c.getColumnIndex("DIAGNOSIS"));
                                        AGE = c.getString(c.getColumnIndex("AGE"));
                                        GENDER = c.getString(c.getColumnIndex("GENDER"));
                                        DOCTORNAME = c.getString(c.getColumnIndex("DOCTORNAME"));
                                        DOCTORID = c.getString(c.getColumnIndex("DOCTORID"));
                                        DOCTORSPEC = c.getString(c.getColumnIndex("DOCTORSPEC"));
                                        DATETIME = c.getString(c.getColumnIndex("ACT_DATETIME"));
                                        NEXTVISITDATE = c.getString(c.getColumnIndex("NEXTVISITDATE"));
                                        HOSPITALNAME = c.getString(c.getColumnIndex("HOSPITALNAME"));

                                    } while (c.moveToNext());
                                }
                            }
                            c.close();
                            db.close();


                            json.setpATIENTID(BaseConfig.CheckDBString(PATIENTID));
                            json.setpATIENTNAME(BaseConfig.CheckDBString(PATIENTNAME));
                            json.setaGE(BaseConfig.CheckDBString(AGE));
                            json.setgENDER(BaseConfig.CheckDBString(GENDER));

                            json.setiNVESTIGATIONID(BaseConfig.CheckDBString(INVESTIGATIONID));
                            json.setmEDICINEID(BaseConfig.CheckDBString(MEDICINEID));
                            json.setdIAGNOSISID(BaseConfig.CheckDBString(DIAGNOSISID));

                            json.setsYMPTOMS(BaseConfig.CheckDBString(SYMPTOMS));
                            json.setdIAGNOSIS(BaseConfig.CheckDBString(DIAGNOSIS));

                            json.setdOCTORNAME(BaseConfig.CheckDBString(DOCTORNAME));
                            json.setdOCTORID(BaseConfig.CheckDBString(DOCTORID));
                            json.setdOCTORSPEC(BaseConfig.CheckDBString(DOCTORSPEC));
                            json.setdATETIME(BaseConfig.CheckDBString(DATETIME));

                            json.setnEXTVISITDATE(BaseConfig.CheckDBString(NEXTVISITDATE));
                            json.sethOSPITALNAME(BaseConfig.CheckDBString(HOSPITALNAME));

                            body.setJsonValue(json);


                            Call<PDFNodeJsResult> callObject = generatePDFNode.getPDFNodeJs(contentType, body, null);

                            PDFNodeJsResult result = callObject.get();

                            String resultvalue = result.getResult();
                            String resulturl = result.getURL();


                            results = !resultvalue.toString().equalsIgnoreCase("FAILED") ? result.getURL() : "";

                            return results;
                        }

                        @Override
                        public String onSuccess(String result) throws NotFoundException {


                            if (result.toString().equalsIgnoreCase("")) {

                                new CustomKDMCDialog(PdfReportAdapter.GeneratePDF_From_Node.this.ctx1)
                                        .setLayoutColor(color.orange_500).setNegativeButtonVisible(View.GONE)
                                        .setImage(drawable.ic_warning_black_24dp)
                                        .setTitle(PdfReportAdapter.GeneratePDF_From_Node.this.ctx1.getResources().getString(string.information))
                                        .setDescription("Unable to connect with internet/server..try later..")
                                        .setPossitiveButtonTitle("OK");


                            } else {


                                try {
                                    String PDFLink = BaseConfig.AppNodeIP + result;

                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PDFLink));
                                    PdfReportAdapter.GeneratePDF_From_Node.this.ctx1.startActivity(browserIntent);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                            return "";

                        }
                    });
                    //endregion


                } else {
                    this.NoNetworkConnectivity(this.ctx1);

                }
            } else {
                this.NoNetworkConnectivity(this.ctx1);

            }


        }

//**********************************************************************************************

        final boolean CheckNodeServer(Context ctx) {

            try {
                HttpURLConnection conn = (HttpURLConnection) (new URL(BaseConfig.AppNodeIP))
                        .openConnection();
                conn.setUseCaches(false);
                conn.connect();
                int status = conn.getResponseCode();

                if (status == 200) {
                    this.statusvalue = true;
                }
                conn.disconnect();

                return this.statusvalue;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;

        }



        private void NoNetworkConnectivity(Context ctx) {

            new CustomKDMCDialog(this.ctx1)
                    .setLayoutColor(color.green_500)
                    .setImage(drawable.ic_success_done)
                    .setTitle(this.ctx1.getResources().getString(string.information)).setNegativeButtonVisible(View.GONE)
                    .setDescription("Unable to connect with internet/server..try later..")
                    .setPossitiveButtonTitle("OK");


        }


    }


}


//**********************************************************************************************