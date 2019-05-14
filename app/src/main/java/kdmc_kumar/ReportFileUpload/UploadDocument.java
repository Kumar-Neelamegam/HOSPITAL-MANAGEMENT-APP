package kdmc_kumar.ReportFileUpload;

import android.R.layout;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CropImage;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.InternalStorageContentProvider;


public class UploadDocument extends AppCompatActivity implements OnClickListener {

    public static final int REQUEST_CODE_GALLERY = 0x4;
    private static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    private static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private static final String TEMP_PHOTO_FILE_NAME = "/temp_photo.jpg";
    private static final int REQUEST_CODE = 123;
    private static int Image_ID;
    private TextView patientid;
    private TextView patientname;
    private TextView patientagegen;
    private ImageView patientimage;
    private ImageView ic_back;
    private Spinner report_type;
    private EditText report_details;
    private EditText filepath;
    private Button browse;
    private Button add_item;
    private RecyclerView recyler_view;
    private TextView cancel;
    private TextView submit;
    private Toolbar toolbar;
    private final String[] Report_Type = {"Select Report", "Scan Report", "Test Report", "EEG Report", "ECG Report", "Angiogram Report", "Others"};
    private File mFileTemp;
    private FileAdapter fileAdapter;
    private ArrayList<FileGetSet> fileGetSets;
    private String BUNDLE_PATIENT_ID;
    private TextView txt_report_type;
    private TextView txt_report_details;
    private TextView txt_file_path1;
    private TextView txt_file_path2;
    private final String Str_FileName = "";
    private final String Str_FileExtension = "";
    private GridLayoutManager lLayout;
    private final int REQUEST_CAMERA;
    private final int SELECT_FILE = 1;

    public UploadDocument() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_upload_document);
        this.toolbar = this.findViewById(id.toolbar);

        Bundle b = this.getIntent().getExtras();

        this.BUNDLE_PATIENT_ID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);

        //delete previous contact inserts
        UploadDocument.deleteContacts();

        this.txt_report_type = this.findViewById(id.spn_title);
        this.txt_report_details = this.findViewById(id.txt_report_dtls);
        this.txt_file_path1 = this.findViewById(id.robotoTextView);
        this.txt_file_path2 = this.findViewById(id.filepath);

        String firstt = this.getString(string.report_type);
        String second = this.getString(string.report_details);
        String third = this.getString(string.file_path);
        String fourth = this.getString(string.file_path);


        String nextt = "<font color='#EE0000'><b>*</b></font>";
        this.txt_report_type.setText(Html.fromHtml(firstt + nextt));
        this.txt_report_details.setText(Html.fromHtml(second + nextt));
        this.txt_file_path1.setText(Html.fromHtml(third + nextt));
        this.txt_file_path2.setText(Html.fromHtml(fourth + nextt));


        this.recyler_view = this.findViewById(id.recyler_view);

        this.lLayout = new GridLayoutManager(this, 2);
        this.recyler_view.setHasFixedSize(true);
        this.recyler_view.setLayoutManager(this.lLayout);
        this.recyler_view.setNestedScrollingEnabled(false);


        this.ic_back = this.toolbar.findViewById(id.ic_back);
        this.ic_back.setOnClickListener(this);

        this.patientid = this.findViewById(id.patient_id);
        this.patientname = this.findViewById(id.patient_name);
        this.patientagegen = this.findViewById(id.patient_agegen);
        this.patientimage = this.findViewById(id.patient_image);
        this.report_details = this.findViewById(id.report_details);
        this.report_type = this.findViewById(id.report_type);
        this.browse = this.findViewById(id.browse);
        this.filepath = this.findViewById(id.filepath);
        this.add_item = this.findViewById(id.add_item);
        this.add_item.setOnClickListener(this);

        this.browse.setOnClickListener(this);

        this.submit = this.findViewById(id.submit);
        this.cancel = this.findViewById(id.cancel);
        this.submit.setOnClickListener(this);
        this.cancel.setOnClickListener(this);

        String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');
        String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');

        this.patientid.setText(this.BUNDLE_PATIENT_ID);
        this.patientname.setText(Patient_Name);
        this.patientagegen.setText(Patient_AgeGender);

        Glide.with(this.patientimage.getContext()).asBitmap().load(new File(UploadDocument.filePath(this.BUNDLE_PATIENT_ID))).into(this.patientimage);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layout.simple_list_item_1, this.Report_Type);

        this.report_type.setAdapter(adapter);


        try {
            String state = Environment.getExternalStorageState();
            this.mFileTemp = Environment.MEDIA_MOUNTED.equals(state) ? new File(Environment.getExternalStorageDirectory(),
                    UploadDocument.TEMP_PHOTO_FILE_NAME) : new File(this.getFilesDir(), UploadDocument.TEMP_PHOTO_FILE_NAME);

            this.mFileTemp = new File(Environment
                    .getExternalStorageDirectory(),
                    UploadDocument.TEMP_PHOTO_FILE_NAME);
            if (this.mFileTemp.exists()) {
                this.mFileTemp.delete();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }


    //**********************************************************************************************

    @Override
    public final void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void startCropImage() {

        try {
            Intent intent = new Intent(this, CropImage.class);
            intent.putExtra(CropImage.IMAGE_PATH, this.mFileTemp.getPath());
            intent.putExtra(CropImage.SCALE, true);

            intent.putExtra(CropImage.ASPECT_X, 1);
            intent.putExtra(CropImage.ASPECT_Y, 1);
            intent.putExtra(CropImage.OUTPUT_X, 270);
            intent.putExtra(CropImage.OUTPUT_Y, 270);

            this.startActivityForResult(intent, UploadDocument.REQUEST_CODE_CROP_IMAGE);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onClick(View view) {

        switch (view.getId()) {
            case id.ic_back:
                finish();
                break;
            case id.cancel:
                finish();
                break;
            case id.submit:

                if (this.fileAdapter != null && this.fileAdapter.getItemCount() > 0) {
                    this.ContactstoReportGallery();

                    UploadDocument.deleteContacts();

                    new CustomKDMCDialog(this)
                            .setImage(drawable.ic_success_done)
                            .setTitle("Information")
                            .setLayoutColor(color.green_500)
                            .setDescription("Insert Successfully")
                            .setPossitiveButtonTitle("OK")
                            .setOnPossitiveListener(this::finish);


                } else {
                    //Toast.makeText(UploadDocument.this, "Atleast add one report or click cancel..", Toast.LENGTH_LONG).show();

                    new CustomKDMCDialog(this)
                            .setLayoutColor(color.orange_500)
                            .setImage(drawable.ic_warning_black_24dp)
                            .setTitle("Information")
                            .setDescription("Atleast add one report or click cancel..")
                            .setPossitiveButtonTitle("OK");


                }


                break;
            case id.browse:

                this.ImageChooser();
                break;
            case id.add_item:

                if (this.report_type.getSelectedItemId() == 0L) {
                    Toast.makeText(this, "Select Report Type...", Toast.LENGTH_SHORT).show();
                } else if (this.report_details.getText().toString().isEmpty()) {
                    this.report_details.setError("Enter Value");
                } else if (this.filepath.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Browse Image...", Toast.LENGTH_SHORT).show();
                } else {

                    //add contacts table to show listview
                    UploadDocument.insertContacts(this.Str_FileName, this.Str_FileExtension, this.filepath.getText().toString(), this.report_type.getSelectedItem().toString(), this.report_details.getText().toString());
                    this.showRecyclerListView();
                    this.filepath.setText("");
                    this.report_details.setText("");
                    this.report_type.setSelection(0);

                }
                break;
        }

    }

    private static final String filePath(String Doctorid) {

        String filepath = "";
        SQLiteDatabase db = BaseConfig.GetDb();

        Cursor c = db.rawQuery("select * from Patreg where Patid='" + Doctorid + '\'', null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    filepath = c.getString(c.getColumnIndex("PC"));
                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return filepath;
    }


    private final void ImageChooser() {
        Builder dialog = new Builder(this);
        dialog.setPositiveButton("File Chooser", (dialog12, which) -> this.showChooser());
        dialog.setNegativeButton("Take Photo", (dialog1, which) -> {

            // cameraIntent();
            this.takePicture();

        });
        dialog.setTitle("Choose Image");
        dialog.setMessage("Select option");
        dialog.show();

    }


    //Browse

    private void showChooser() {

      /*  // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(target, "Choose Dialog");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (Exception e) {
            // The reason for the existence of aFileChooser
            e.printStackTrace();
        }*/

    }

    private final void takePicture() {

        ++UploadDocument.Image_ID;

        long time = System.currentTimeMillis();
        this.mFileTemp = new File(Environment.getExternalStorageDirectory() + "/temp" + UploadDocument.Image_ID + '-' + time + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            mImageCaptureUri = Environment.MEDIA_MOUNTED.equals(state) ? Uri.fromFile(this.mFileTemp) : InternalStorageContentProvider.CONTENT_URI;
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            intent.putExtra("return-data", true);
            this.startActivityForResult(intent, UploadDocument.REQUEST_CODE_TAKE_PICTURE);
        } catch (RuntimeException ignored) {
        }
    }

    private final void showRecyclerListView() {
        this.fileGetSets = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery("select * from contacts", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    FileGetSet item = new FileGetSet();
                    item.setImageName(c.getString(c.getColumnIndex("Name")));
                    item.setImagePath(c.getString(c.getColumnIndex("ImageUrl")));
                    item.setId(c.getString(c.getColumnIndex("Id")));
                    item.setReportType(c.getString(c.getColumnIndex("Type")));
                    item.setUploadFileDetail(c.getString(c.getColumnIndex("ReportDetail")));

                    this.fileGetSets.add(item);

                } while (c.moveToNext());
            }
        }
        c.close();
        this.fileAdapter = new FileAdapter(this.fileGetSets, this.recyler_view);
        this.recyler_view.setAdapter(this.fileAdapter);
    }


    private static final void insertContacts(String image_name, String image_extns, String imagepath, String type, String reportdetail) {

        SQLiteDatabase db = BaseConfig.GetDb();
        ContentValues cv = new ContentValues();
        cv.put("FileName", image_name);
        cv.put("FileExtension", image_extns);
        cv.put("Name", type);
        cv.put("Image", imagepath);
        cv.put("ImageUrl", imagepath);
        cv.put("Type", type);
        cv.put("ReportDetail", reportdetail);
        db.insert("contacts", null, cv);
        db.close();

    }

    private static final void deleteContacts() {

        SQLiteDatabase db = BaseConfig.GetDb();
        db.delete("contacts", null, null);


    }

    // Getting All Contacts
    private final void ContactstoReportGallery() {

        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        String dttm = dateformt.format(date);

        // Select All Query
        SQLiteDatabase db = BaseConfig.GetDb();

        String selectQuery = "SELECT  * FROM contacts ORDER BY Name";

        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                String LTitle = c.getString(c.getColumnIndex("Name"));

                String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');
                String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + this.BUNDLE_PATIENT_ID + '\'');


                String Insert_Query = "Insert into ReportGallery(Docid,Patid,Name,agegender,Diagnosisid,dt,Remarks,IsUpdate,patientphoto,ReportPhoto,ReportType,ImageUrl,FileName,FileExtension)"
                        + " Values('" + BaseConfig.doctorId + "','" + this.BUNDLE_PATIENT_ID + "','" + Patient_Name + "','" + Patient_AgeGender + "','" + BaseConfig.digid + "','" + dttm + "','','0',''," +
                        '\'' + BaseConfig.ReportyGal + "','" + LTitle + "','" + c.getString(c.getColumnIndex("ImageUrl")) + "','" + c.getString(c.getColumnIndex("FileName")) + "'," +
                        '\'' + c.getString(c.getColumnIndex("FileExtension")) + "');";

                BaseConfig.SaveData(Insert_Query);
                Log.d("Title: ", Insert_Query);

            } while (c.moveToNext());
        }

        c.close();

        db.execSQL("delete from contacts");

        db.close();

    }
}