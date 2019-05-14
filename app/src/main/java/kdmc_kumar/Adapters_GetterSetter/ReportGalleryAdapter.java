package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ZoomControls;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.ReportGalleryAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;


/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class ReportGalleryAdapter extends Adapter<MyViewHolder> {


    private final ArrayList<CommonDataObjects.RowItem> rowItemr;
    private String Image_Path;
    private final String BUNDLE_PATIENTID;

    public ReportGalleryAdapter(ArrayList<CommonDataObjects.RowItem> rowItem, String PatientId) {
        rowItemr = rowItem;
        this.BUNDLE_PATIENTID = PatientId;
    }

    @NonNull
    @Override
    public final ReportGalleryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.newreportlistrow, parent, false);

        return new ReportGalleryAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull ReportGalleryAdapter.MyViewHolder holder, int position) {
        CommonDataObjects.RowItem rowItem = null;
        try {
            rowItem = this.rowItemr.get(position);

            holder.txtDesc.setText(rowItem.getDesc());
            holder.txtTitle.setText(rowItem.getTitle());

            //Log.e("Report File Path:", rowItem.getFile_Path());

            if (rowItem.getFile_Path() != null) {

                BaseConfig.Glide_LoadImageView(holder.imageView, BaseConfig.AppDBFolderName + "/Uploads/" + rowItem.getFile_Path());

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        CommonDataObjects.RowItem finalRowItem = rowItem;
        CommonDataObjects.RowItem finalRowItem1 = rowItem;
        holder.card_view.setOnClickListener(v -> {
            try {


                BaseConfig.reportname = finalRowItem.getTitle();

                //Image_Path= finalRowItem1.getFile_Path();
                this.Image_Path = BaseConfig.AppDatabaseName + "/Uploads/" + finalRowItem1.getFile_Path();
                LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
                View promptView = layoutInflater.inflate(layout.reportimageview_zoom_dialog, null);
                Builder alertDialogBuilder = new Builder(v.getContext());
                alertDialogBuilder.setView(promptView);

                ImageView reportphoto = promptView.findViewById(id.imageZoom);

                TextView nameagegen1 = promptView.findViewById(id.nameagegen);

                TextView patient_name = promptView.findViewById(id.amt);

                ZoomControls zoom = promptView.findViewById(id.zoomControls1);


                //SelectedGetPatientReports(2, "select ImageUrl,ReportPhoto,ReportType from ReportGallery where ReportType='" + BaseConfig.reportname.toString().trim() + "' and Patid='" + BUNDLE_PATIENTID + "';", v.getContext());

                nameagegen1.setText(String.format("Report Name: %s", BaseConfig.reportname));

                BaseConfig.Glide_LoadImageView( reportphoto, this.Image_Path);



                String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENTID + '\'');


                patient_name.setText(String.format("Patient Name: %s-%s", Patient_Name, this.BUNDLE_PATIENTID));


                zoom.setOnZoomInClickListener(v12 -> {
                    // TODO Auto-generated method stub

                    int w = reportphoto.getWidth();
                    int h = reportphoto.getHeight();

                    LayoutParams params = new LayoutParams(w + 10, h + 10);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);

                    reportphoto.setLayoutParams(params);
                });

                zoom.setOnZoomOutClickListener(v1 -> {
                    // TODO Auto-generated method stub

                    int w = reportphoto.getWidth();
                    int h = reportphoto.getHeight();

                    LayoutParams params =
                            new LayoutParams(w - 10, h - 10);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);

                    reportphoto.setLayoutParams(params);
                });


                alertDialogBuilder.setCancelable(false).setPositiveButton(promptView.getContext().getString(string.ok),
                        (dialog, id) -> {


                        });


                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public final int getItemCount() {
        return this.rowItemr.size();
    }

    private void SelectedGetPatientReports(int id, String Query, Context context) {
        // TODO Auto-generated method stub
        String report_name = "";
        String report64 = "";
        SQLiteDatabase db = BaseConfig.GetDb();//context);


        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    this.Image_Path = c.getString(c.getColumnIndex("ImageUrl"));


                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();

    }

    public static final Bitmap Convert64ToImage(String base64) {
        // BaseConfig.Zoomimg=base64;

        byte[] decodedString = Base64.decode(base64.trim(), Base64.DEFAULT);
        // bimatp factory
        Options options = new Options();

        // downsizing image as it throws OutOfMemory Exception for larger
        // images
        options.inSampleSize = 1;

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    static class MyViewHolder extends ViewHolder {
        final ImageView imageView;
        final LinearLayout rootLayout;

        final TextView txtTitle;
        final TextView txtDesc;
        final CardView card_view;

        MyViewHolder(View itemView) {
            super(itemView);

            this.txtDesc = itemView.findViewById(id.desc);
            this.txtTitle = itemView.findViewById(id.title);
            this.imageView = itemView.findViewById(id.img_icon);
            this.rootLayout = itemView.findViewById(id.list_root);
            this.card_view = itemView.findViewById(id.card_view);
        }
    }


}
