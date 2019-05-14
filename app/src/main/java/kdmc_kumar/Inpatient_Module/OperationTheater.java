package kdmc_kumar.Inpatient_Module;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Inpatient_Module.OperationTheater.OperationListViewAdapter.DataObjectHolder;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class OperationTheater extends AppCompatActivity {


    private static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private static final int REQUEST_CODE_GALLERY = 0x4;
    private static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private RecyclerView mRecyclerview;
    private LinearLayout imageNoData;
    private ImageView remark_image_view;
    private LayoutManager mLayoutManager;
    private File mFileTemp;

    public OperationTheater() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(layout.activity_operation_theater);

       // getActionBar();

       // ActionBar bar = getSupportActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3d5987")));


        this.GetInitialize();

        this.ControlListener();


    }

    private void GetInitialize() {

        this.imageNoData =
                this.findViewById(id.no_data_image);

        this.mRecyclerview = this.findViewById(id.my_recycler_view);
// Camera
        String state = Environment.getExternalStorageState();
        this.mFileTemp = Environment.MEDIA_MOUNTED.equals(state) ? new File(Environment.getExternalStorageDirectory(),
                OperationTheater.TEMP_PHOTO_FILE_NAME) : new File(this.getFilesDir(), OperationTheater.TEMP_PHOTO_FILE_NAME);

    }


    private void ControlListener() {
        this.mRecyclerview.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerview.setLayoutManager(this.mLayoutManager);
        this.mRecyclerview.setLayoutManager(this.mLayoutManager);
        ArrayList<OperationTheater.DietItem> items = this.getDataSet();
        OperationTheater.OperationListViewAdapter mAdapter = new OperationTheater.OperationListViewAdapter(items, this);
        this.mRecyclerview.setAdapter(mAdapter);
        if (items.size() == 0) {
            this.imageNoData.setVisibility(View.VISIBLE);
        } else if (items.size() > 0) {
            this.imageNoData.setVisibility(View.GONE);
        }


    }

    private ArrayList getDataSet() {

        ArrayList results = new ArrayList<OperationTheater.DietItem>();

        String Query = "select  * from operation_details ";


        SQLiteDatabase db = BaseConfig.GetDb();//OperationTheater.this);

        Cursor c = db.rawQuery("select  * from operation_details where doctors like '%" + BaseConfig.doctorname + "%'", null);

        c.moveToFirst();
       /* StringBuilder stringBuilder = new StringBuilder();
        boolean isThere = false;*/

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    OperationTheater.DietItem item1 = new OperationTheater.DietItem();
                    item1.operation_name = c.getString(c.getColumnIndex("operation_name"));
                    item1.patient_name = c.getString(c.getColumnIndex("patient_name"));
                    item1.operation_date = c.getString(c.getColumnIndex("operation_date"));
                    item1.from_time = c.getString(c.getColumnIndex("from_time"));
                    item1.to_time = c.getString(c.getColumnIndex("to_time"));
                    item1.doctors = c.getString(c.getColumnIndex("doctors"));
                    item1.operation_no = c.getString(c.getColumnIndex("operation_no"));
                    results.add(item1);

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return results;
    }

    public final void showPopup() {
        Dialog dialog = new Dialog(this);


    }

    @Override
    public final void onBackPressed() {
        // Do Here what ever you want do on back press;

        finish();
        Intent in = new Intent(this, Inpatient_List.class);
        this.startActivity(in);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {

        return this.MenuSelecciona(item);

    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater();

        OperationTheater.CreateMenu(menu);
        return true;
    }

    private static void CreateMenu(Menu menu) {

        MenuItem item1 = menu.add(0, 0, 0, "Item 1");
        {
            // --Copio las imagenes que van en cada item
            item1.setIcon(drawable.prev_icon);
            item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        MenuItem item2 = menu.add(0, 1, 1, "Item 2");
        {

            item2.setIcon(drawable.home_ico);
            item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }


    }

    private boolean MenuSelecciona(MenuItem item) {

        switch (item.getItemId()) {

            case 0:

                BaseConfig.globalStartIntent(this, Inpatient_List.class, null);


                return true;
            case 1:

                BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

                return true;

            case android.R.id.home:

                return true;

        }
        return false;
    }

    private void showPopup(Activity context, String operation_nam, String time, String valuess) {
//      LayoutInflater factory = LayoutInflater.from(this);

        LayoutInflater factory = LayoutInflater.from(this);
        View deleteDialogView = factory.inflate(
                layout.operation_remarks_layout, null);
        AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        deleteDialog.setTitle("Operation Remarks");
        TextView operation_name = deleteDialogView.findViewById(id.operation_name);
        TextView scheduleValue = deleteDialogView.findViewById(id.scheduleValue);

        operation_name.setText(operation_nam);
        scheduleValue.setText(time);

        EditText remarks = deleteDialogView.findViewById(id.operation_remarks_value);

        /**
         @Ponnusamy
          * 5.11.2016
          * 4:44PM
          * ***/

        //new Fields
        EditText pre_operation_diagnosis_value = deleteDialogView.findViewById(id.pre_operation_diagnosis_value);
        EditText operation_notes_value = deleteDialogView.findViewById(id.operation_notes_value);
        EditText position_value = deleteDialogView.findViewById(id.position_value);
        EditText procedure_value = deleteDialogView.findViewById(id.procedure_value);
        EditText closure_value = deleteDialogView.findViewById(id.closure_value);
        EditText post_operative_diagnosis_value = deleteDialogView.findViewById(id.post_operative_diagnosis_value);
        EditText post_operative_instruction = deleteDialogView.findViewById(id.post_operative_instruction);

        /*****
         @Ponnusamy
          * Date 22.11.2016
          * Time 2:00
          *
          * *******/
        this.remark_image_view = deleteDialogView.findViewById(id.remark_image_view);


        this.remark_image_view.setOnClickListener(view -> {
            //ImagePicker Code
            this.add_image();

        });

        Button button_submit = deleteDialogView.findViewById(id.submit_pop);
        Button button_cancel = deleteDialogView.findViewById(id.cancel_pop);


        this.getPopupData(remarks, pre_operation_diagnosis_value, operation_notes_value, position_value, procedure_value, closure_value, post_operative_diagnosis_value, post_operative_instruction, valuess);

        button_cancel.setOnClickListener(view -> deleteDialog.dismiss());

        button_submit.setOnClickListener(view -> {

            try {


                if (remarks.getText().toString().isEmpty()) {
                    remarks.setError("Enter Value");
                } else if (pre_operation_diagnosis_value.getText().toString().isEmpty()) {
                    pre_operation_diagnosis_value.setError("Enter Value");
                } else if (operation_notes_value.getText().toString().isEmpty()) {
                    operation_notes_value.setError("Enter Value");
                } else if (position_value.getText().toString().isEmpty()) {
                    position_value.setError("Enter Value");
                } else if (procedure_value.getText().toString().isEmpty()) {
                    procedure_value.setError("Enter Value");
                } else if (closure_value.getText().toString().isEmpty()) {
                    closure_value.setError("Enter Value");
                } else if (post_operative_diagnosis_value.getText().toString().isEmpty()) {
                    post_operative_diagnosis_value.setError("Enter Value");
                } else if (post_operative_instruction.getText().toString().isEmpty()) {
                    post_operative_instruction.setError("Enter Value");
                } else {
                    SQLiteDatabase db = BaseConfig.GetDb();//OperationTheater.this);
                    /*

                      	`Pre_Oper_Diagnosis`	TEXT,
                     `Oper_Notes`	TEXT,
                     `Position`	TEXT,
                     `Procedure`	TEXT,
                     `Closure`	TEXT,
                     `Post_Oper_Diagnosis`	TEXT,
                     `Post_Oper_Instruction`	TEXT

                      **/


                    /******
                     Conver Image to Base 64 encod
                     @Ponnusamy
                      * Date 22.11.2016
                      * *****/
                    BitmapDrawable drawable = (BitmapDrawable) this.remark_image_view.getDrawable();
                    Bitmap bm = drawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String Remark_image_base64 = Base64.encodeToString(b, Base64.DEFAULT);


                    String Query = "UPDATE operation_details SET Remarks = '" + remarks.getText() + "' , IsUpdateLocal =  '0',Pre_Oper_Diagnosis='" + pre_operation_diagnosis_value.getText() + '\'' +
                            ",Oper_Notes='" + operation_notes_value.getText() + "',Position='" + position_value.getText() + "',Procedure='" + procedure_value.getText() + '\'' +
                            ",Closure='" + closure_value.getText() + "',Post_Oper_Diagnosis='" + post_operative_diagnosis_value.getText() + "',Post_Oper_Instruction='" + post_operative_instruction.getText() + "' , Remark_Image='" + Remark_image_base64 + "' WHERE  operation_no='" + valuess + "' ";

                    db.execSQL(Query);
                    //Log.e("delete Query", Query);

                    Toast.makeText(this, "Successfully Updated Remarks", Toast.LENGTH_SHORT).show();
//                        BaseConfig.SnackBar(this,  "Successfully Updated Remarks" , parentLayout);

                    deleteDialog.dismiss();

                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        deleteDialog.show();
    }

    private final void getPopupData(EditText remark, EditText operationdiagnosis, EditText oper_notes, EditText position, EditText procedure, EditText closure, EditText post_oper_diagno, EditText post_oper_inst, String value) {

        try {


            SQLiteDatabase db = BaseConfig.GetDb();

            //Cursor c = db.rawQuery("select  substr(Actdate,1,10) ||' - '|| mtestid  as acdate from medicaltest where Ptid='"+arry[1].trim()+"' order by id desc;", null);

            //modified on 25/02/2015
            Cursor c = db.rawQuery("select  * from operation_details where operation_no='" + value + "';", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        /**


                         `Remarks`	TEXT,
                         `IsNotified`	TEXT,
                         `IsUpdateLocal`	TEXT,
                         `Pre_Oper_Diagnosis`	TEXT,
                         `Oper_Notes`	TEXT,
                         `Position`	TEXT,
                         `Procedure`	TEXT,
                         `Closure`	TEXT,
                         `Post_Oper_Diagnosis`	TEXT,
                         `Post_Oper_Instruction`	TEXT


                         **/

                        remark.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Remarks")))));
                        operationdiagnosis.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Pre_Oper_Diagnosis")))));
                        oper_notes.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Oper_Notes")))));

                        position.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Position")))));
                        procedure.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Procedure")))));
                        closure.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Closure")))));

                        post_oper_diagno.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Post_Oper_Diagnosis")))));
                        post_oper_inst.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Post_Oper_Instruction")))));


                        //Set ImageView <code></code>

                        String image_value = c.getString(c.getColumnIndex("Remark_Image"));

                        byte[] decodedString = Base64.decode(image_value, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        this.remark_image_view.setImageBitmap(decodedByte);


                        //  c.getString(c.getColumnIndex("acdate"));


                    } while (c.moveToNext());
                }

            }

            db.close();
            c.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    public static final String CheckIsNull(String data) {
        String val = "";

        if (data.isEmpty() || data == null || data == null || data.equalsIgnoreCase(null)) {
            return "";
        } else if (!data.isEmpty() && data != null) {
            return data;
        }
        return val;
    }

    /*********
     * @Ponnusamy Date 22.11.2016
     * Time 3:00 PM
     * IMage Picker Code
     ************/
    private void add_image() {

        CharSequence[] items = {"Take Photo", "Choose from Library"};

        Builder builder = new Builder(this);

        builder.setTitle("Select Image");
        builder.setItems(items, (dialog, item) -> {


            try {

                if (item == 0) {
                    // camera
                    this.takePicture();

                } else {
                    // gallery
                    this.openGallery();
                }

            } catch (RuntimeException e) {
                e.printStackTrace();

            }

        });
        //builder.show();
        android.app.AlertDialog dialog = builder.create();
        // Change the title divider
        dialog.show();

    }

    //capture image
    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
           /* Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
				*//*
                 * The solution is taken from here:
				 * http://stackoverflow.com/questions
				 * /10042695/how-to-get-camera-result-as-a-uri-in-data-folder
				 *//*
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);*/

            this.startActivityForResult(intent, OperationTheater.REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {

            Log.d("Image Picker", "cannot take picture", e);
        }
    }

    private void openGallery() {

        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), OperationTheater.REQUEST_CODE_GALLERY);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OperationTheater.REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = Media.getBitmap(this.getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                this.remark_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == OperationTheater.REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                this.remark_image_view.setImageBitmap(photo);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    static class DietItem {
        String operation_name;
        String patient_name;
        String operation_date;
        String from_time;
        String to_time;
        String doctors;
        String Actate;
        Bitmap image;
        String operation_no;

        DietItem() {
        }
    }

    public class OperationListViewAdapter extendsAdapter<DataObjectHolder> {
        View view;
        private final String LOG_TAG = "MyRecyclerViewAdapter";
        private final ArrayList<OperationTheater.DietItem> mDataset;


        OperationListViewAdapter(ArrayList<OperationTheater.DietItem> myDataset, Context context) {
            this.mDataset = myDataset;

        }

        @NonNull
        @Override
        public final OperationTheater.OperationListViewAdapter.DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layout.each_operation_layout, parent, false);
            this.view = view;
            return new OperationTheater.OperationListViewAdapter.DataObjectHolder(view);
        }

        @Override
        public final void onBindViewHolder(@NonNull OperationTheater.OperationListViewAdapter.DataObjectHolder holder, int position) {

            OperationTheater.DietItem movie = this.mDataset.get(position);
            holder.Operation_name_textview.setText(movie.operation_name);
            String OperationDate = movie.operation_date;
            String finalDate = "";
            if (OperationDate.contains("T")) {
                String OpDate[] = OperationDate.split("T");
                // holder.operation_date_time.setText(movie.operation_date+" / "+movie.from_time+" - "+movie.to_time);

                finalDate = OpDate[0];
            }

            String fromDTime = "";
            if (movie.from_time.contains("T")) {
                String fromTimeS[] = movie.from_time.split("T");
                fromDTime = fromTimeS[1];
            }

            if (!movie.from_time.contains("T")) {
                fromDTime = movie.from_time;
            }

            String toDTime = "";
            if (movie.to_time.contains("T")) {
                String toTimeS[] = movie.to_time.split("T");
                toDTime = toTimeS[1];
            }

            if (!movie.to_time.contains("T")) {
                toDTime = movie.to_time;
            }

            String finalTime = "";
            String finalTime1 = fromDTime + " to " + toDTime;
            holder.operation_date_time.setText(finalDate + " / " + finalTime1);

            if (movie.doctors.contains(",")) {
                String drnames[] = movie.doctors.split(",");
                StringBuilder DrNames = new StringBuilder();
                DrNames.append("Doctor(s)  :  ");
                StringBuilder nurseName = new StringBuilder();
                nurseName.append("Assistant(s) : ");
                for (int i = 0; i <= drnames.length - 1; i++) {
                    if (drnames[i].contains("Dr")) {

                        DrNames.append(drnames[i]).append(",");

                    } else {
                        nurseName.append(drnames[i]);
                    }

                }

                String wholeString = DrNames + "\n" + nurseName;
                holder.Operation_doctor_name.setText(wholeString);

            } else {
                holder.Operation_doctor_name.setText(movie.doctors);

            }


            //holder.Operation_patient_name.setText(BaseConfig.GetPatientNameFromId(movie.patient_name, OperationTheater.this));
            holder.Operation_patient_name.setText(movie.patient_name);


            holder.reportBtn.setOnClickListener(view -> {

               /* Intent i = new Intent(OperationTheater.this, OperationReport.class);
                startActivity(i);
                finish();*/


            });
            holder.rooLayout.setOnClickListener(view -> OperationTheater.this.showPopup(OperationTheater.this, String.valueOf(holder.Operation_name_textview.getText()), String.valueOf(holder.operation_date_time.getText()), movie.operation_no));



/*


           */
/* holder.DoctorName.setText("Dr."+mDataset.get(position).doctorNameL);*//*

            holder.PatientName.setText(String.valueOf(mDataset.get(position).name));
            holder.PatientBmi.setText(String.valueOf("BMI - "+mDataset.get(position).bmi));
            if(mDataset.get(position).userIcon!= null)
            {
                holder.imageView.setImageBitmap(mDataset.get(position).userIcon);
            }
            holder.outerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(OperationTheater.this,AddDietActivity.class);

                    startActivity(i);
                    finish();


                }
            });

*/


        }

        public final void addItem(OperationTheater.DietItem dataObj, int index) {
            this.mDataset.add(index, dataObj);
            this.notifyItemInserted(index);
        }

        public final void deleteItem(int index) {
            this.mDataset.remove(index);
            this.notifyItemRemoved(index);
        }

        @Override
        public final int getItemCount() {
            return this.mDataset.size();
        }

        public class DataObjectHolder extends ViewHolder
                implementsOnClickListener {
            final TextView Operation_name_textview;
            final TextView operation_date_time;
            final TextView Operation_doctor_name;
            final TextView Operation_patient_name;
            CircleImageView imageView;
            final Button reportBtn;
            final LinearLayout rooLayout;

            LinearLayout outerLayout;


            DataObjectHolder(View itemView) {
                super(itemView);
                this.reportBtn = itemView.findViewById(id.report_btn);
                this.Operation_name_textview = itemView.findViewById(id.Operation_name_textview);
                this.operation_date_time = itemView.findViewById(id.operation_date_time);
                this.Operation_doctor_name = itemView.findViewById(id.Operation_doctor_name);
                this.Operation_patient_name = itemView.findViewById(id.Operation_patient_name);
                this.rooLayout = itemView.findViewById(id.layout_root);
            }

            @Override
            public void onClick(View view) {

            }
        }

    }
}
