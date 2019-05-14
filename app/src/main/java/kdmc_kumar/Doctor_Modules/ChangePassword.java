package kdmc_kumar.Doctor_Modules;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.ConnectionDetector;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Webservices_NodeJSON.ExportWebservices_NODEJS;


public class ChangePassword extends AppCompatActivity {


    @BindView(id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(id.toolbar)
    Toolbar toolbarChangepassword;
    @BindView(id.ic_back)
    AppCompatImageView icBack;
    @BindView(id.txvw_title)
    TextView txvwTitle;
    @BindView(id.ic_home)
    AppCompatImageView icHome;
    @BindView(id.ic_exit)
    AppCompatImageView icExit;
    @BindView(id.nestedscrollview_changepassword)
    NestedScrollView nestedscrollviewChangepassword;
    @BindView(id.imgvw_doctor_photo)
    CircleImageView imgvwDoctorPhoto;
    @BindView(id.txtvw_doctorname)
    TextView txtvwDoctorname;
    @BindView(id.txtvw_hospitalname)
    TextView txtvwHospitalname;
    @BindView(id.txtvw_specialization)
    TextView txtvwSpecialization;
    @BindView(id.edt_username)
    EditText edtUsername;
    @BindView(id.edt_entercurrent_password)
    EditText edtEntercurrentPassword;
    @BindView(id.edt_enter_new_password)
    EditText edtEnterNewPassword;
    @BindView(id.edt_enter_confirm_password)
    EditText edtEnterConfirmPassword;
    @BindView(id.button_cancel)
    Button Btn_cancel;
    @BindView(id.button_submit)
    Button Btn_update;

    private ConnectionDetector cd;

    public ChangePassword() {
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(layout.kdmc_layout_change_password);


        try {
            this.GETINITIALIZE();

            this.CONTROLLISTENERS();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }



        String Query="select docimage as ret_values from Drreg where Docid='" + BaseConfig.doctorId + '\'';
        String DoctorPhoto = BaseConfig.GetValues(Query);
        if (DoctorPhoto != null && DoctorPhoto.length() > 0) {
            BaseConfig.LoadPatientImage(DoctorPhoto, this.imgvwDoctorPhoto, 100);
        }
        this.txtvwDoctorname.setText(String.format("%s - %s", BaseConfig.doctorname, BaseConfig.docacademic));
        this.txtvwHospitalname.setText(BaseConfig.HOSPITALNAME);
        this.txtvwSpecialization.setText(BaseConfig.docspecli);

        this.edtUsername.setText(BaseConfig.username);

        this.Btn_cancel.setOnClickListener(v -> {
            finish();
            Intent changepassword = new Intent(this, Dashboard_NavigationMenu.class);
            this.startActivity(changepassword);
        });


        this.Btn_update.setOnClickListener(v -> {

            if (this.cd.isConnectingToInternet()) {
                if (this.edtEntercurrentPassword.getText().length() > 0) {
                    if (this.GetCurrentPassword()) {
                        if (this.edtEnterNewPassword.getText().length() > 0 && this.edtEnterConfirmPassword.getText().length() > 0) {
                            if (this.edtEnterNewPassword.getText().length() >= 6 && this.edtEnterNewPassword.getText().length() <= 10) {
                                if (this.edtEnterNewPassword.getText().toString().equalsIgnoreCase(this.edtEnterConfirmPassword.getText().toString())) {


                                    this.UpdatePasswordProcess();


                                } else {
                                    this.showSimplePopUp("New and confirm password fields does not match");
                                }
                            } else {
                                Toast.makeText(this, "length should be between 6 and 10", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            this.showSimplePopUp("Enter new and confirm password");
                            this.edtEnterConfirmPassword.setText(null);
                            this.edtEnterConfirmPassword.requestFocus();
                            this.edtEnterNewPassword.setText(null);
                            this.edtEnterNewPassword.requestFocus();

                        }
                    } else {
                        this.showSimplePopUp("Invalid current password");
                        this.edtEntercurrentPassword.setText(null);
                        this.edtEntercurrentPassword.requestFocus();
                    }
                } else {
                    this.showSimplePopUp("Enter current password");
                    this.edtEntercurrentPassword.setError("Required");
                    this.edtEntercurrentPassword.requestFocus();
                }
            } else {
                Toast.makeText(this, "Data Connection Not Available - Please Enable", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void UpdatePasswordProcess() {
        String cnfpswd = Base64.encodeToString(this.edtEnterConfirmPassword.getText().toString().getBytes(), Base64.DEFAULT).trim();

        String Update_Query_Password = "update users set password='" + cnfpswd + "',dt='1',status='1' where username='" + this.edtUsername.getText() + '\'';

        BaseConfig.SaveData(Update_Query_Password);

        try {
            this.ExportMobyDoctorUpdatedInformation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        this.ShowSweetAlert("New password saved successfully - please re-login");

        this.updateSharedPreferencePassword(this.edtEnterConfirmPassword.getText().toString().trim());
    }


    private void GETINITIALIZE() {

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        this.txvwTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.change_pwd)));

        this.setSupportActionBar(this.toolbarChangepassword);


        this.cd = new ConnectionDetector(this.getApplicationContext());

    }


    private void CONTROLLISTENERS() {

        this.icHome.setOnClickListener(view -> {

            finish();
            Intent intent = new Intent(this.getApplicationContext(), Dashboard_NavigationMenu.class);
            this.startActivity(intent);

        });


        this.icExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));


        this.icBack.setOnClickListener(view -> this.LoadBack());


    }

    private final void LoadBack()
    {

        BaseConfig.globalStartIntent(this, SettingActivity.class, null);

    }

    //#######################################################################################################
    private final void ShowSweetAlert(String str) {

        new CustomKDMCDialog(this)
                .setLayoutColor(color.green_500)
                .setImage(drawable.ic_success_done)
                .setTitle(getString(string.information)).setNegativeButtonVisible(View.GONE)
                .setDescription(str)
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle(getString(string.ok))
                .setOnPossitiveListener(() -> {

                    finish();
                    Intent intent = new Intent(this.getApplicationContext(), Login.class);
                    this.startActivity(intent);
                });

    }

    //#######################################################################################################
    private final boolean GetCurrentPassword() {
        int count = 0;
        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery(
                "select password from users where username='" + this.edtUsername.getText() + "';", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String oldpswd = Base64.encodeToString(this.edtEntercurrentPassword.getText().toString().getBytes(), Base64.DEFAULT).trim();

                    if (oldpswd.equals(c.getString(c.getColumnIndex("password")))) {

                        count++;
                        break;
                    }

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return count != 0;
    }

    private void
    showSimplePopUp(String msg) {

        int image= drawable.ic_success_done;
        int color= color.green_500;
        if(2 ==1)//Success
        {
            image= drawable.ic_success_done;
            color= color.green_500;
        }else if(2 ==2)//Warning
        {
            image= drawable.ic_warning_black_24dp;
            color= color.orange_500;
        }

        new CustomKDMCDialog(this)
                .setNegativeButtonVisible(View.GONE)
                .setLayoutColor(color)
                .setImage(image).setNegativeButtonVisible(View.GONE)
                .setTitle(getString(string.information))
                .setDescription(msg)
                .setOnPossitiveListener(() -> {

                })
                .setPossitiveButtonTitle(getString(string.ok));


    }

    @Override
    public final void onBackPressed() {

        this.LoadBack();

    }


    private void updateSharedPreferencePassword(String cnfpswd) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sp.edit();
        editor.putString("PWD", cnfpswd);
        editor.apply();
        editor.commit();
    }


    public void ExportMobyDoctorUpdatedInformation() {

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

                String results = ExportWebservices_NODEJS.postDataExportData(methodName, jsonData, this);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                }


                String ReturnValue = "";//= BaseConfig.ExportDataToServer("updatepasswordDr", export_jsonarray.toString());//Modified for exporting data to server @ MK
                JSONObject mainJson = new JSONObject(ReturnValue);
                //  JSONArray jsonArray = mainJson.getJSONArray("ReturnValue");
                JSONObject objJson = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);
                    String ServerId = objJson.getString("ServerId");
                    String Id = objJson.getString("Id");

                    ContentValues values = new ContentValues();

                    values.put("dt", "0");
                    values.put("ServerId", ServerId);
                    db.update("users", values, "id=" + Id + "", null);

                }

            }

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}//END
