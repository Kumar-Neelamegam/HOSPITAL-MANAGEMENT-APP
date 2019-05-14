package kdmc_kumar.Doctor_Modules;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.github.atzcx.appverupdater.AppVerUpdater;
import com.github.atzcx.appverupdater.UpdateErrors;
import com.github.atzcx.appverupdater.callback.Callback;
import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.ConnectionDetector;
import kdmc_kumar.Utilities_Others.LocalSharedPref;
import kdmc_kumar.Utilities_Others.SmsListener;
import kdmc_kumar.Utilities_Others.SmsReceiver;
import kdmc_kumar.Utilities_Others.Validation;
import kdmc_kumar.Webservices_NodeJSON.ImportWebservices_NODEJS;
import kdmc_kumar.Webservices_NodeJSON.MasterWebservices_NODEJS;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.checkCredential.controller.api.CheckLogin;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.checkCredential.controller.api.CheckLoginFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.checkCredential.model.beans.PostUsernamePasswordRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.checkCredential.model.beans.UsernamePasswordResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getDoctorDetails.controller.api.DoctorDetails;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getDoctorDetails.controller.api.DoctorDetailsFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getDoctorDetails.model.beans.DoctorIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getDoctorDetails.model.beans.PostDoctorIdRequest;


public class Login extends AppCompatActivity {

    private static final String randnumprefix = "Your%20Mobydoctor%20Verification%20Code:%20";
    private static int flag = 0;
    private static int IsRegistered = 0;
    private final String USER_AGENT = "Mozilla/5.0";
    private final Context contxt = this;
    @BindView(R.id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ic_exit)
    ImageView exit;
    @BindView(R.id.help_img2)
    ImageView serverImage;
    @BindView(R.id.help_img6)
    ImageView netwrk;
    @BindView(R.id.imgvw_doctorphoto1)
    ImageView DoctorPhoto;
    @BindView(R.id.title_welcome1)
    TextView WelcomeTitle;
    @BindView(R.id.txtdocname)
    AutoCompleteTextView Field_Username;
    @BindView(R.id.txtpname)
    EditText Field_Password;
    @BindView(R.id.checkBox1)
    CheckBox remember;
    @BindView(R.id.txt_frgtpswd)
    TextView forgotpassword;
    @BindView(R.id.pinlayout)
    LinearLayout passwordlayout;
    @BindView(R.id.editText1)
    EditText loginpinnum;
    @BindView(R.id.button4)
    Button login;
    @BindView(R.id.LoginCard)
    CardView LoginCard;
    @BindView(R.id.moby)
    ImageView Logo;
    @BindView(R.id.txtvw_title_username)
    TextView Title_Username;
    @BindView(R.id.txtvw_title_password)
    TextView Title_Password;

    BaseConfig bc = new BaseConfig();
    LocalSharedPref preferences;
    String Preference_CheckBox = "Login_CheckBox";
    String Preference_Username = "Login_UserName";
    String Preference_Password = "Login_Password";
    private AppVerUpdater appVerUpdater;
    private int VersionCode;
    private String VersionName = "";
    private String ApkName;
    private String AppName;
    private String BuildVersionPath = "";
    private String urlpath;
    private String PackageName;
    private String Text = "";
    private static ProgressDialog pDialog;
    //For checking trail exipry
    private CheckTrail Expiry = new CheckTrail();
    private ConnectionDetector cd;
    private String IMEICHK = "a";
    private String MACCHK = "a";
    private int regsuccess = -1;
    private int EmailStatus;
    private int smsStatus;
    private int payStatus = 0;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int count = 0;

    public static SMSResult sendSms(String message, String mobileNumber, String originator, String username, String password) {

        // Create a string representing the data elements to post, then convert it to a byte array
        StringBuilder postData = new StringBuilder();
        try {
            postData.append("message=").append(URLEncoder.encode(message, "UTF-8"));
            postData.append("&mobile_number=").append(URLEncoder.encode(mobileNumber, "UTF-8"));
            postData.append("&originator=").append(URLEncoder.encode(originator, "UTF-8"));
            postData.append("&username=").append(URLEncoder.encode(username, "UTF-8"));
            postData.append("&password=").append(URLEncoder.encode(password, "UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }
        String postDataStr = postData.toString();

        URL url;
        HttpURLConnection connection = null;
        try {
            // Create connection
            url = new URL("http://sms.dyaustechnoservices.com/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(postDataStr.getBytes().length));
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(postDataStr);
            wr.flush();
            wr.close();

            // Get Response
            InputStream inputStreams = connection.getInputStream();

            // Convert the response data into an XML document and parse the expected
            // elements to get the individual response fields
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStreams);

            String date = document.getElementsByTagName("response").item(0).getAttributes().getNamedItem("processed_date").getNodeValue();
            String reFromattedDate = date.substring(0, 22) + date.substring(23);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",Locale.ENGLISH);
            Date processedDate = format.parse(reFromattedDate);
            String messageId = document.getElementsByTagName("message_id").item(0).getFirstChild().getNodeValue();
            int creditsUsed = Integer.parseInt(document.getElementsByTagName("credits_used").item(0).getFirstChild().getNodeValue());
            SMSResult result = new SMSResult(processedDate, messageId, creditsUsed, null);
            return result;
        } catch (Exception e) {
            try {
                InputStream errorStream = connection.getErrorStream();

                // Convert the response data into an XML document and parse the expected
                // error elements to get the individual response fields
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(errorStream);

                String date = document.getElementsByTagName("response").item(0).getAttributes().getNamedItem("processed_date").getNodeValue();
                String reFromattedDate = date.substring(0, 22) + date.substring(23);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",Locale.ENGLISH);
                Date processedDate = format.parse(reFromattedDate);
                NodeList errorNodeList = document.getElementsByTagName("error");
                TextMarketerError[] errors = new TextMarketerError[errorNodeList.getLength()];
                for (int i = 0; i < errorNodeList.getLength(); i++) {
                    Node errorNode = errorNodeList.item(i);
                    String errorCode = errorNode.getAttributes().getNamedItem("code").getNodeValue();
                    String errorText = errorNode.getFirstChild().getNodeValue();
                    TextMarketerError error = new TextMarketerError(Integer.parseInt(errorCode), errorText);
                    errors[i] = error;
                }
                SMSResult result = new SMSResult(processedDate, null, 0, errors);
                return result;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_login_screen);

        //Testing Notification
        //LoadNotification();

        //DummyInsert();

        try {
            CHECKIMEI_DETAILS();
        } catch (Exception e) {
            //e.printStackTrace();
        }


        try {
            GETINITIALIZE();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            CONTROLLISTENERS();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            OTHERS();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        //setLocale("mr");
        //login.performClick();

    }//GET INITIALIZE END

    private void DummyInsert() {

        SQLiteDatabase db=BaseConfig.GetDb();

        //db.execSQL("Alter table scantest add column HID int ");
        //db.execSQL("Alter table ECGTEST add column HID int ");
        //db.execSQL("Alter table EEG add column HID int ");

        db.close();

    }

    private void OTHERS() {


        if (preferences.getBoolean(Preference_CheckBox)) {
            remember.setChecked(true);
        } else {
            remember.setChecked(false);
        }

        Field_Username.setText(preferences.getValue(Preference_Username));
        Field_Password.setText(preferences.getValue(Preference_Password));


        if (remember.isChecked()) {
            Field_Username.clearFocus();
            Field_Username.dismissDropDown();
            Field_Password.clearFocus();
            login.setFocusable(true);
        }

        LoadDoctorInfo();//Photo & Name


        Bundle b;
        b = new Bundle();

        String PinStatus = b.getString("ALTERNATIVE");

        if (PinStatus != null && PinStatus.toString().equalsIgnoreCase("ALTERNATIVE")) {
            String chkquery = "select enablepin as dstatus1 from drsettings where enablepin='true'";
            boolean pinlay = BaseConfig.LoadReportsBooleanStatus(chkquery);
            if (pinlay) {

                finish();
                String PINNO = BaseConfig.GetValues("select pin as ret_values from drsettings");
                Intent intent = new Intent(getApplicationContext(), PinPassword.class);
                intent.putExtra("PINNUMBER", PINNO);
                startActivity(intent);
            }
        }

        new CheckServerConnection().execute();

        //checking db update from server
        /**
         * @MUTHUKUMAR
         * checking auto update enabled in settings
         */
       /* try {
            String Query = "select EnableAutoUpdate as dstatus1 from drsettings";
            boolean b1 = BaseConfig.LoadReportsBooleanStatus(Query);
            if (b1) {

                if (bc.server_connectivity_status) {

                    new LoginBackgroundProcesses().execute();

                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }*/


    }


    //**********************************************************************************************

    private void LoadNetworkStatus() {

        try {


            if (BaseConfig.CheckNetwork(Login.this)) {
                netwrk.setImageBitmap(null);
                netwrk.setImageResource(R.drawable.ic_status_ready);
            } else {

                netwrk.setImageResource(R.drawable.ic_status_busy);

            }


            bc.checkNodeServer(serverImage);

        } catch (Exception e) {
            //e.printStackTrace();
        }


    }


    //**********************************************************************************************

    //Uninstall Apk
    private void UnInstallApplication(String packageName) {

        try {
            Uri packageURI = Uri.parse(packageName.toString());
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);

            BaseConfig.SnackBar(this, "Updated version of apk is available in download folder..", parentLayout, 1);


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    //**********************************************************************************************

    //Install Apk
    private void InstallApplication(String AppPackageName) {
        try {
            Uri packageURI = Uri.parse(AppPackageName.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    packageURI);

            intent.setDataAndType(
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                            + "/download/" + ApkName.toString())),
                    "application/vnd.android.package-archive");

            startActivity(intent);


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private void CHECKIMEI_DETAILS() {
        try {
            String[] str = CHECKING_IMEI_PROCESS().split("/");


            if (!str[0].toString().trim().equals("a")) {
                if (!str[0].toString().trim().equalsIgnoreCase(String.valueOf(BaseConfig.Imeinum.toString().trim())) || !str[1].toString().trim().equalsIgnoreCase(String.valueOf(BaseConfig.MacId.toString().trim()))) {
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }


    }

    private String CHECKING_IMEI_PROCESS() {


        try {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select Imei,MAC  from drreg;", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        IMEICHK = c.getString(c.getColumnIndex("Imei"));
                        MACCHK = c.getString(c.getColumnIndex("MAC"));

                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return IMEICHK + "/" + MACCHK;
    }

    //**********************************************************************************************

    @Override
    public void onBackPressed() {

        if (count == 1) {
            count = 0;

            finishAffinity();

        } else {
            Toast.makeText(getApplicationContext(), "Press again to quit...", Toast.LENGTH_SHORT).show();
            count++;
        }

    }


    //**********************************************************************************************

    private void GETINITIALIZE() {


        ButterKnife.bind(Login.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        preferences = new LocalSharedPref(Login.this);

        Animation AnimationBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top);
        Logo.startAnimation(AnimationBottom);

        Animation AnimationTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slid_up);
        LoginCard.startAnimation(AnimationTop);


        serverImage = (ImageView) findViewById(R.id.help_img2);
        netwrk = (ImageView) findViewById(R.id.help_img6);

        exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                BaseConfig.ExitSweetDialog(Login.this, null);

            }
        });

        Field_Username.setThreshold(1);

        BaseConfig.LoadValues(Field_Username, getApplicationContext(), "select distinct username as dvalue from users order by username;");
        BaseConfig.welcometoast = 1;

        boolean user_registered = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from users");
        if (user_registered) // Data if found
        {
            forgotpassword.setVisibility(View.VISIBLE);
        } else // Data not found
        {
            forgotpassword.setVisibility(View.GONE);
        }


        String username = getString(R.string.login_username);
        String password = getString(R.string.login_password);
        String nextt = "<font color='#EE0000'><b>*</b></font>";
        Title_Username.setText(Html.fromHtml(username + nextt));
        Title_Password.setText(Html.fromHtml(password + nextt));


    }


    //**********************************************************************************************

    private void LoadDoctorInfo() {

        boolean status = BaseConfig.LoadReportsBooleanStatus("select Docid as dstatus1 from Drreg");
        if (status == true) {
            String Photo_Path = BaseConfig.GetValues("select docimage as ret_values from Drreg");
            String Doctor_Name = BaseConfig.GetValues("select name as ret_values from Drreg");

            BaseConfig.doctorname = Doctor_Name;

            WelcomeTitle.setText(getString(R.string.welcome) + " - " + Doctor_Name.toString());

            try {

                BaseConfig.Glide_LoadImageView(DoctorPhoto, Photo_Path);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    //**********************************************************************************************

    public void SendSMS() {
        String mobnum = "9791853366";
        String msg = "Message From Android Apps";
        String urls = "http://sms.dyaustechnoservices.com/api/sendmsg.php?user=mobydr&pass=9.r_8[w(rhTDT6tmZWXs&sender=MobyDr&phone=" + mobnum + "&text=" + msg + "&priority=ndnd&stype=normal";

        try {
            URL url = new URL(urls);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            System.out.println(uc.getResponseMessage());

            uc.disconnect();
        } catch (Exception e) {
            // TODO Auto-generated catch block

            //e.printStackTrace();
        }
    }


    //**********************************************************************************************
    String RandomNumber="";

    private void SEND_SMS(String mnum, String Msg) {
        try {


            String mobnum = mnum;//"8675070345";
            String msg = Msg;//"Arumugam%20G%20pl%20start%20off";

            String urls = "http://mysms.dyaustechnoservices.com/API/WebSMS/Http/v1.0a/index.php?username=Mobydr&password=9.r_8[w(rhTDT6tmZWXs&sender=MobyDr&to=" + mobnum + "&message=" + msg + "&reqid=1&format={json|text}";
            URL url = new URL(urls);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setRequestMethod("GET");
            uc.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = uc.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(uc.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            System.out.println(uc.getResponseMessage());


        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }

    private void CONTROLLISTENERS() {


        Field_Username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (s.length() > 0) {
                    //clearbtn1.setVisibility(View.VISIBLE);
                    DoctorPhoto.setImageBitmap(null);
                    WelcomeTitle.setText("");
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Drawable rightDrawable = AppCompatResources.getDrawable(Field_Username.getContext(), R.drawable.ic_clear_button);
                if (Field_Username.getText().length() > 0) {
                    Field_Username.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    Field_Username.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= (Field_Username.getRight() - Field_Username.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                    // your action here
                                    DoctorPhoto.setImageBitmap(null);
                                    WelcomeTitle.setText("");
                                    Field_Username.setText("");
                                    Field_Username.requestFocus();
                                    return true;
                                }
                            }
                            return false;
                        }
                    });

                } else {
                    Field_Username.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                    Field_Username.setOnTouchListener(null);

                }


            }
        });

        Field_Password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (s.length() > 0) {
                    //clearbtn2.setVisibility(View.VISIBLE);
                    DoctorPhoto.setImageBitmap(null);
                    WelcomeTitle.setText("");
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                // BaseConfig.setEdittextDrawable(Field_Password);
                Drawable rightDrawable = AppCompatResources.getDrawable(Field_Password.getContext(), R.drawable.ic_clear_button);
                if (Field_Password.getText().length() > 0) {
                    Field_Password.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    Field_Password.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= (Field_Password.getRight() - Field_Password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                    // your action here
                                    DoctorPhoto.setImageBitmap(null);
                                    WelcomeTitle.setText("");
                                    Field_Password.setText("");
                                    Field_Password.requestFocus();
                                    return true;
                                }
                            }
                            return false;
                        }
                    });

                } else {
                    Field_Password.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                    Field_Password.setOnTouchListener(null);

                }

            }
        });


        forgotpassword.setOnClickListener(new OnClickListener() {


            ImageView close;
            AutoCompleteTextView textvwEmailaddress;
            TextView textvwMobilenumberhidden;
            EditText autocompleteMobilenumber;
            Button submitMobilenumber;


            public void onClick(View v) {


                try {
                    LayoutInflater layoutInflater = LayoutInflater.from(Login.this);
                    View promptView = layoutInflater.inflate(R.layout.kdmc_forgotpassword_layout, null);

                    close = (ImageView) promptView.findViewById(R.id.close);
                    textvwEmailaddress = promptView.findViewById(R.id.textvw_emailaddress);
                    textvwMobilenumberhidden = promptView.findViewById(R.id.textvw_mobilenumberhidden);
                    autocompleteMobilenumber = promptView.findViewById(R.id.autocomplete_mobilenumber);
                    submitMobilenumber = promptView.findViewById(R.id.button_verify);


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this).setCancelable(false);
                    alertDialogBuilder.setView(promptView);

                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    textvwEmailaddress.setText(Field_Username.getText().toString());

                    String Query = "select Mobile as ret_values from Drreg";

                    String str_result = BaseConfig.GetValues(Query);

                    Log.e("Login: ", Query+" / "+str_result);

                    textvwMobilenumberhidden.setText("********"+str_result.substring(7, 10));

                    submitMobilenumber.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String MobileNumber = autocompleteMobilenumber.getText().toString();


                            if (MobileNumber.length() > 0) {

                                String Query = "select Mobile as dstatus1 from Drreg";
                                if (BaseConfig.LoadReportsBooleanStatus(Query)) {

                                    Random rand = new Random();

                                    RandomNumber = String.valueOf(rand.nextInt(1000000));

                                    new SendSMS(MobileNumber, RandomNumber).execute();

                                    LoadOTPDialog();

                                    alertDialog.dismiss();

                                } else {
                                    autocompleteMobilenumber.setText("");
                                    Toast.makeText(autocompleteMobilenumber.getContext(), "Enter valid Mobile Number..", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                autocompleteMobilenumber.setError("Required");
                                autocompleteMobilenumber.requestFocus();
                            }


                        }
                    });


                    close.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

                        }
                    });


                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {


                try {


                    preferences.setBoolean(Preference_CheckBox, remember.isChecked());
                    if (remember.isChecked()) {
                        preferences.setValue(Preference_Username, Field_Username.getText().toString());
                        preferences.setValue(Preference_Password, Field_Password.getText().toString());

                    } else {
                        preferences.clearValue(Preference_Username);
                        preferences.clearValue(Preference_Password);
                    }

                    if (!loginpinnum.getText().toString().equals("")) {                                 // Using PIN (1)

                        Login_Using_Pin();

                    } else if (checkValidation()) {                                                     // Using username and password (2)

                        Login_Using_UserCredentials(Login.this);

                    } else {                                                                            //Enter user credentials (3)

                        BaseConfig.showSimplePopUp("Enter User Details for Authentication", "Information", Login.this, R.drawable.ic_warning_black_24dp, R.color.orange_500);
                    }
                } catch (Exception e) {
                    //
                }

            }

        });

    }

    public void LoadOTPDialog() {

        ImageView close;
        TextView txtvw_mobilenumber;
        Button button_resend,button_submit;
        EditText OTP_Edittext;


        LayoutInflater layoutInflater = LayoutInflater.from(Login.this);
        View promptView = layoutInflater.inflate(R.layout.kdmc_forgotpassword_otp_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this).setCancelable(false);
        alertDialogBuilder.setView(promptView);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        close = promptView.findViewById(R.id.close);
        button_resend = promptView.findViewById(R.id.button_resend);
        button_submit = promptView.findViewById(R.id.button_submit);
        OTP_Edittext = promptView.findViewById(R.id.autocomplete_otp);
        txtvw_mobilenumber = promptView.findViewById(R.id.txtvw_mobilenumber);

        String Query = "select Mobile as ret_values from Drreg";
        String str_result = BaseConfig.GetValues(Query);
        txtvw_mobilenumber.setText("("+str_result+")");

        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        });

        button_resend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                      if (BaseConfig.CheckNetwork(Login.this)) {
                        new SendSMS(str_result, RandomNumber).execute();
                        button_resend.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(Login.this,"Please Enable Internet Connection",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {

                }

            }
        });

        button_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateOTP(str_result, OTP_Edittext, alertDialog, Login.this);

            }
        });

        alertDialog.show();


    }


    //**********************************************************************************************

    private void Login_Using_UserCredentials(Context context) {

       /* if (Field_Username.getText().toString().equals(BaseConfig.adminentry) && Field_Password.getText().toString().equals(BaseConfig.adminentry)) { // CASE 1: ADMIN ENTRY


            //Login_As_Admin();


        } else*/

        if (LoadBooleanLoginStatus("select username,password,doctorname,clinicname,status,dremail,drid from users where username='" + Field_Username.getText().toString().trim() + "' and password='"
                + Base64.encodeToString(Field_Password.getText().toString().trim().getBytes(), Base64.DEFAULT).trim() + "';", Field_Username, Field_Password)) { // CASE 2: CHECK LOCAL DATABASE


            Login_Check_With_Local_Data(context);


        } else { // CASE 3: GET THE USER INFORMATION FROM SERVER

            cd = new ConnectionDetector(getApplicationContext());
            if (cd.isConnectingToInternet() || bc.server_connectivity_status) {


                new UserLoginCheckNodeJs(login, Field_Username.getText().toString(), Base64.encodeToString(Field_Password.getText().toString().getBytes(), Base64.DEFAULT).trim()).execute("");


            } else {

                BaseConfig
                        .showSimplePopUp(
                                "Internet/Server connection is required at initial login...",
                                "Information!", Login.this, R.drawable.ic_signal_cellular_connected_no_internet_3_bar_black_24dp, R.color.red_500);
            }
        }
    }

    private void Login_Check_With_Local_Data(Context context) {


        UpdateOnlineStatus();

        cd = new ConnectionDetector(getApplicationContext());

        try {
            if (cd.isConnectingToInternet()) {

                Device_Deactivate(context);

            } else {

                Login.this.finish();
                Intent intent = new Intent(login.getContext(), Dashboard_NavigationMenu.class);
                startActivity(intent);
                BaseConfig.Assistant_Task = "False";
                LOAD_WEBSERVICES();

            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    private void LOAD_WEBSERVICES() {

        try {
            BaseConfig.StartWebservice_Import(Login.this, 2);
            BaseConfig.StartWebservice_Export(Login.this, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //**********************************************************************************************

    private void Device_Deactivate(Context context) {

        BaseConfig.SnackBar(Login.this, "Data Connection Not Available - Please Enable to check update next time..", Field_Username, 2);


        try {

            String dstatus = "";

            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor cc = db.rawQuery("select IFNULL(device_deactivate,0) as dstatus1 from Drreg", null);
            if (cc != null) {
                if (cc.moveToFirst()) {
                    do {

                        if (cc.getString(cc.getColumnIndex("dstatus1")).equals("1") || cc.getString(cc.getColumnIndex("dstatus1")).equals("2")) {
                            dstatus = cc.getString(cc.getColumnIndex("dstatus1"));
                        } else {
                            dstatus = "3";
                        }


                    } while (cc.moveToNext());
                }
            }
            cc.close();
            db.close();


            if (dstatus.equalsIgnoreCase("1")) {

                //sound
                MediaPlayer mp = null;
                if (mp != null) {
                    mp.release();
                }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.notify);
                mp.start();


                View v1 = null;

                v1 = getWindow().getDecorView();
                v1.setBackgroundResource(android.R.color.transparent);
                LayoutInflater li = LayoutInflater.from(contxt);
                View promptsView = li.inflate(R.layout.blockscreen, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this).setCancelable(false);


                alertDialogBuilder.setView(promptsView);

                final TextView status_txt = (TextView) promptsView.findViewById(R.id.textView1);
                final Button exit_btn = (Button) promptsView.findViewById(R.id.button1);

                status_txt.setText("Sorry, We regret to inform you that your mobydoctor application has been deactivated");

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.anim.myanimation;
                alertDialog.show();

                exit_btn.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        Login.this.finish();

                    }
                });


            } else if (dstatus.equalsIgnoreCase("2")) {
                //sound
                MediaPlayer mp = null;
                if (mp != null) {
                    mp.release();
                }
                mp = MediaPlayer.create(getApplicationContext(), R.raw.notify);
                mp.start();


                View v1 = null;

                v1 = getWindow().getDecorView();
                v1.setBackgroundResource(android.R.color.transparent);
                LayoutInflater li = LayoutInflater.from(contxt);
                View promptsView = li.inflate(R.layout.blockscreen, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(contxt).setCancelable(false);


                alertDialogBuilder.setView(promptsView);
                final TextView status_txt = (TextView) promptsView.findViewById(R.id.textView1);
                final Button exit_btn = (Button) promptsView.findViewById(R.id.button1);

                status_txt.setText("All your mobydoctor data has been erased sucessfully from your device");


                // create alert dialog

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.anim.myanimation;
                alertDialog.show();

                exit_btn.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        Login.this.finish();

                    }
                });


            } else {
                Login.this.finish();
                Intent intent = new Intent(login.getContext(), Dashboard_NavigationMenu.class);
                startActivity(intent);
                LOAD_WEBSERVICES();


            }
        } catch (Exception e) {
            //e.printStackTrace();

        }
    }


    //**********************************************************************************************

    private void Login_Using_Pin() {
        if (loginpinnum.getText().length() == 4) {

            if (bc.LoadBooleanStatusPin("select pin as dstatus from drsettings where enablepin='true';")) {

                if (loginpinnum.getText().toString().equals(bc.DecryptPin)) {

                    bc.LoadUserValues();

                    UpdateOnlineStatus();

                    Login.this.finish();
                    BaseConfig.Assistant_Task = "False";
                    Intent intent = new Intent(login.getContext(), Dashboard_NavigationMenu.class);
                    startActivity(intent);
                    //  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    LOAD_WEBSERVICES();


                } else {
                    BaseConfig.showSimplePopUp("Invalid Pin Number", "Information", Login.this, R.drawable.ic_warning_black_24dp, R.color.red_500);
                    loginpinnum.setText(null);
                    loginpinnum.requestFocus();
                    loginpinnum.setError("Invalid");
                }

            } else {

                loginpinnum.setText(null);
                loginpinnum.requestFocus();
                loginpinnum.setError("Invalid");
            }

        } else {

            loginpinnum.setText(null);
            loginpinnum.requestFocus();
            loginpinnum.setError("Invalid");
        }
    }

    public boolean LoadBooleanLoginStatus(String Query, AutoCompleteTextView username, TextView pswd) {

        int havcount = 0;
        String DecryptPin = "";
        try {

            String PinStatus = "";
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery(Query, null);
            DecryptPin = Base64.encodeToString(pswd.getText().toString().getBytes(), Base64.DEFAULT);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        if (username.getText().toString().equals(c.getString(c.getColumnIndex("username"))) && DecryptPin.toString().trim().equals(c.getString(c.getColumnIndex("password")))) {


                            BaseConfig.username = c.getString(c.getColumnIndex("username"));
                            BaseConfig.doctorname = c.getString(c.getColumnIndex("doctorname"));
                            BaseConfig.clinicname = c.getString(c.getColumnIndex("clinicname"));
                            BaseConfig.Login_Docid = c.getString(c.getColumnIndex("drid"));


                            havcount++;
                            break;
                        }
                    } while (c.moveToNext());
                }
            }
            c.close();
            db.close();


        } catch (Exception e) {
            //return false;
        }
        return havcount > 0;
    }


    //**********************************************************************************************

    private void UpdateOnlineStatus() {

        String upquery = "Update users set Online='1'";
        BaseConfig.SaveData(upquery);

    }

    //**********************************************************************************************

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(Field_Username))
            ret = false;

        if (!Validation.haspwd(Field_Password))
            ret = false;

        return ret;
    }


    //**********************************************************************************************

    private void InsertUserLocally(String usrname, String pwrd, String rimeinum, String Docname, String Clinicname, String dremail, String drid) {

        SQLiteDatabase db = BaseConfig.GetDb();
        ContentValues values = new ContentValues();
        values.put("username", usrname);
        values.put("password", pwrd.toString());
        values.put("imeinum", rimeinum);
        values.put("clinicname", Clinicname);
        values.put("doctorname", Docname);
        values.put("dremail", dremail);
        values.put("status", "1");
        values.put("drid", drid);
        db.insert("Users", null, values);

    }


    //**********************************************************************************************

    private void CHECK_APP_UPDATE() {

        appVerUpdater = new AppVerUpdater()
                .setUpdateJSONUrl(BaseConfig.AppUpdateJson)
                .setShowNotUpdated(false)
                .setViewNotes(true)
                .setCallback(new Callback() {
                    @Override
                    public void onFailure(UpdateErrors error) {

                        if (error == UpdateErrors.NETWORK_NOT_AVAILABLE) {
                            BaseConfig.SnackBar(Login.this, "\"No internet connection.", parentLayout, 2);
                        } else if (error == UpdateErrors.ERROR_CHECKING_UPDATES) {
                            BaseConfig.SnackBar(Login.this, "\"An error occurred while checking for updates.", parentLayout, 2);
                        } else if (error == UpdateErrors.ERROR_DOWNLOADING_UPDATES) {
                            BaseConfig.SnackBar(Login.this, "\"An error occurred when downloading updates.", parentLayout, 2);
                        } else if (error == UpdateErrors.JSON_FILE_IS_MISSING) {
                            BaseConfig.SnackBar(Login.this, "\"File missing", parentLayout, 2);
                        } else if (error == UpdateErrors.FILE_JSON_NO_DATA) {
                            BaseConfig.SnackBar(Login.this, "\"The file containing information about the updates are empty.", parentLayout, 2);
                        }

                    }

                    @Override
                    public void onDownloadSuccess() {
                        // for example, record/reset license
                        UnInstallApplication(getPackageName());
                    }

                    @Override
                    public void onUpdateChecked(boolean downloading) {
                        // Happens after an update check, immediately after if update check was successful and there
                        // were no dialogs, or, when an update dialog is presented and user explicitly dismissed the dialog.
                        // "downloading" is true if user accepted the update
                        // Typically used for resetting next update check time
                    }
                })
                .setAlertDialogCancelable(true)
                .build(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        System.gc();

        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }


    }

    //**********************************************************************************************

    @Override
    protected void onResume() {
        super.onResume();
    }


    //**********************************************************************************************

    public void setLocale(String lang) {
        BaseConfig.AppLanguage = lang;
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    //**********************************************************************************************

    public void LoadNotification() {
        // Creates an explicit intent for an Activity in your app
       /* Intent resultIntent = new Intent(Login.this, Splash_Screen.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(Login.this);

        stackBuilder.addParentStack(Splash_Screen.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Login.this).setSmallIcon(R.drawable.consultation_timing).setContentTitle(getString(R.string.app_name) + " Notification");
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.edit_text_background_main);
        NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(largeIcon);
        s.setSummaryText("New update available..");
        mBuilder.setStyle(s);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());*/


        Notification foregroundNote;

        RemoteViews bigView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification_layout_big);

        // bigView.setOnClickPendingIntent() etc..
        Notification.Builder mNotifyBuilder = new Notification.Builder(this);
        foregroundNote = mNotifyBuilder.setContentTitle("Notification")
                .setContentText("Slide down on note to expand")
                .setSmallIcon(R.drawable.logo_high_pix)
                .build();

        foregroundNote.bigContentView = bigView;

        // now show notification..
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(1, foregroundNote);

    }


    //**********************************************************************************************

    public void LoadOTP() {
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text", messageText);
                Toast.makeText(Login.this, "Message: " + messageText, Toast.LENGTH_LONG).show();
            }
        });


    }

    //**********************************************************************************************

    private void ValidateOTP(String Mobilenumber, EditText otp, AlertDialog alertDialog, Context context) {

        String  OTP = otp.getText().toString();

        if (OTP.length() > 0) {

            if (OTP.equals(RandomNumber)) {

                String query = "select password as ret_values from users";
                String str_Password = BaseConfig.GetValues(query);
                String stringFromBase = new String(Base64.decode(str_Password, Base64.DEFAULT));
                String userpassword = "Your%20Mobydoctor%20Login%20Password:%20" + stringFromBase;

                new SendPassword(Mobilenumber, userpassword).execute();

                alertDialog.cancel();

            } else {
                otp.setText("");
                Toast.makeText(context, "Invalid Verification Code Try Again!", Toast.LENGTH_LONG).show();
            }
        } else {
            otp.setError("Required");
            otp.requestFocus();
        }
    }

    //**********************************************************************************************

    public static class SMSResult {

        final Date processedDate;
        final String messageId;
        final int creditsUsed;
        final TextMarketerError[] errors;

        SMSResult(Date processedDate, String messageId, int creditsUsed, TextMarketerError[] errors) {
            this.processedDate = processedDate;
            this.messageId = messageId;
            this.creditsUsed = creditsUsed;
            this.errors = errors;
        }


    }

    //**********************************************************************************************

    public static class TextMarketerError {

        final int errorCode;
        final String errorText;

        TextMarketerError(int errorCode, String errorText) {
            this.errorCode = errorCode;
            this.errorText = errorText;
        }
    }

    //**********************************************************************************************

    public class LoginBackgroundProcesses extends AsyncTask<Void, Void, Void> {

        String docid, ServerId = "";
        String[] db_updatequery;

        protected void onPostExecute(Void v) {

            if (pDialog.isShowing())
                pDialog.dismiss();

            if (BaseConfig.CheckNetwork(Login.this)) {

                CHECK_APP_UPDATE();

            } else {

                BaseConfig.SnackBar(Login.this, "Data Connection Not Available - Please Enable to check update next time..", Field_Username, 2);

                Device_Deactivate(Login.this);

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialog = new ProgressDialog(Login.this);
            pDialog.setTitle("Checking updates");
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {


            try {

                ImportWebservices_NODEJS.CheckDbUpdatesNodeJs(Login.this);

            } catch (Exception e) {

                e.printStackTrace();
            }


            return null;

        }

    }

    public class Login_Validation_Verification extends AsyncTask<Void, Void, Void> {

        protected void onPostExecute(Void v) {
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (flag == 1) {

                UpdateOnlineStatus();
                Login.this.finish();
                Intent intent = new Intent(login.getContext(), SettingActivity.class);
                startActivity(intent);

            } else {
                BaseConfig.showSimplePopUp("Invalid User", "Information", Login.this, R.drawable.ic_warning_black_24dp, R.color.red_500);
                Field_Username.setText("");
                Field_Password.setText("");
                Field_Username.requestFocus();
                Field_Username.setEnabled(true);
                Field_Password.setEnabled(true);

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Login.this);
            pDialog.setTitle(getString(R.string.verifying_credentials));
            pDialog.setMessage(getString(R.string.pleasewait_checking));
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {

            try {


                //returnSoapString();


            } catch (Exception e) {

                //e.printStackTrace();

            }


            return null;
        }

    }

    public class CheckServerConnection extends AsyncTask<Void, Void, Void> {

        protected void onPostExecute(Void v) {

            timerHandler = new Handler();

            timerRunnable = new Runnable() {
                @Override
                public void run() {

                    LoadNetworkStatus();

                    Log.e("Server Connectivity", "Running network check every: 1 Minute ");

                    timerHandler.postDelayed(this, 100000); //run every second

                }
            };

            timerHandler.postDelayed(timerRunnable, 500); //Start timer after 1 sec


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }

    }

    public class CheckTrail extends AsyncTask<Void, Void, Void> {

        protected void onPostExecute(Void v) {


            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor cc = db.rawQuery("select isactive from TrailCheck where isupdate='1'", null);
            if (cc != null) {
                if (cc.moveToFirst()) {
                    do {

                        if (cc.getString(cc.getColumnIndex("isactive")).equals("0")) {


                            //sound
                            MediaPlayer mp = null;
                            if (mp != null) {
                                mp.release();
                            }
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.notify);
                            mp.start();


                            View v1 = null;

                            v1 = getWindow().getDecorView();
                            v1.setBackgroundResource(android.R.color.transparent);
                            LayoutInflater li = LayoutInflater.from(Login.this);
                            View promptsView = li.inflate(R.layout.blockscreen, null);

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this).setCancelable(false);


                            alertDialogBuilder.setView(promptsView);

                            final TextView status_txt = (TextView) promptsView.findViewById(R.id.textView1);
                            final Button exit_btn = (Button) promptsView.findViewById(R.id.button1);

                            status_txt.setText("Sorry, this trail application has expired.\nTo continue, please purchase a full copy..");


                            // create alert dialog

                            final AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.getWindow().getAttributes().windowAnimations = R.anim.myanimation;
                            alertDialog.show();

                            exit_btn.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {


                                    Login.this.finish();

                                }
                            });
                        }


                    } while (cc.moveToNext());
                }
            }
            cc.close();

            db.close();


            Expiry.cancel(true);
            Expiry = new CheckTrail();

        }


        @Override
        protected void onPreExecute() {


            super.onPreExecute();


        }


        @Override
        protected Void doInBackground(Void... params) {

            //chkdb();

            return null;
        }
    }

    public class UserLoginCheckNodeJs extends AsyncTask<String, String, String> {

        final boolean IsException = false;
        final TextView login;
        final String username;
        final String password;
        boolean isFalse = false;
        //ProgressDialog mDialog;
        Dialog builderDialog;

        UserLoginCheckNodeJs(TextView login, String username, String password) {
            this.login = login;
            this.username = username;
            this.password = password;

        }

        @Override
        protected void onPreExecute() {

            builderDialog = BaseConfig.showCustomDialog(getString(R.string.please_wait), getString(R.string.preparing_txt), Login.this);
            builderDialog.setCancelable(false);
            builderDialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            SQLiteDatabase db = BaseConfig.GetDb();

            CheckLogin checkLogin;

            String docId = "";
            try {

                MasterWebservices_NODEJS masterWebservices_nodejs = new MasterWebservices_NODEJS(Login.this);

                //Check
                masterWebservices_nodejs.Mstr_Academic();
                masterWebservices_nodejs.Mstr_Special();

                // Instantiate a controller
                MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(contxt);
                CheckLoginFactory controllerFactory = new CheckLoginFactory(magnetClient);
                checkLogin = controllerFactory.obtainInstance();

                String contentType = "application/json";
                PostUsernamePasswordRequest body = new PostUsernamePasswordRequest();

                body.setImei(BaseConfig.Imeinum);
                body.setMac(BaseConfig.MacId);
                body.setUsername(username);
                body.setPassword(password);

                Call<UsernamePasswordResult> callObject = checkLogin.postUsernamePassword(contentType, body, null);

                UsernamePasswordResult result = callObject.get();

                StringBuilder stringBuilder = new StringBuilder(result.getResults());

                if (!stringBuilder.toString().equalsIgnoreCase("[]") && !stringBuilder.toString().equalsIgnoreCase("")) {


                    isFalse = true;
                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                    for (int i = 0; i <= jsonArray.length() - 1; i++) {


                        JSONObject EachObject = jsonArray.getJSONObject(i);
                        String Docid = EachObject.getString("Docid");
                        String Username = EachObject.getString("Username");
                        String Password = EachObject.getString("Password");
                        String IMEI = EachObject.getString("IMEI");
                        String Status = EachObject.getString("Status");
                        String Online = EachObject.getString("Online");
                        String Onlinetime = EachObject.getString("Onlinetime");
                        String MAC = EachObject.getString("MAC");
                        db.execSQL("delete from users");

                        ContentValues values = new ContentValues();
                        values.put("username", Username);
                        values.put("password", Password);
                        values.put("drid", Docid);
                        values.put("imeinum", IMEI);
                        values.put("status", Status);
                        values.put("dremail", Username);
                        values.put("Online", Online);
                        docId = Docid;

                        boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from users where username ='" + Username + "' and drid='" + Docid + "'");

                        if (!GetStatus) {
                            db.insert("users", null, values);
                        } else {
                            db.update("users", values, "username='" + username + "' and drid='" + Docid + "'", null);
                        }

                    }

                    isFalse = false;
                } else {
                    isFalse = true;
                }


            } catch (SchemaException e) {
                //e.printStackTrace();
            } catch (InterruptedException e) {
                //e.printStackTrace();
            } catch (Exception e) {
                //e.printStackTrace();
            }


            try {


                if (isFalse) {
                    isFalse = true;

                    return "FALSE";
                } else {


                    // get DrRegistration  information
                    if (docId.length() > 0) {


                        DoctorDetails doctorDetails;
                        // Instantiate a controller
                        MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(contxt);
                        DoctorDetailsFactory controllerFactory = new DoctorDetailsFactory(magnetClient);
                        doctorDetails = controllerFactory.obtainInstance();

                        String contentType = "application/json";
                        PostDoctorIdRequest body = new PostDoctorIdRequest();
                        body.setDocid(docId);

                        Call<DoctorIdResult> callObject = doctorDetails.postDoctorId(contentType, body, null);

                        DoctorIdResult doctorIdResult = callObject.get();

                        StringBuilder stringBuilder = new StringBuilder(doctorIdResult.getResults());


                        JSONArray jsonArrayDoc = new JSONArray(stringBuilder.toString());

                        for (int i = 0; i <= jsonArrayDoc.length() - 1; i++) {

                            JSONObject EachObject = jsonArrayDoc.getJSONObject(i);

                            String Id = EachObject.getString("Id");
                            String Docid = EachObject.getString("Docid");
                            String name = EachObject.getString("name");
                            String age = EachObject.getString("age");
                            String gender = EachObject.getString("gender");
                            String DOB = EachObject.getString("DOB");
                            String RegId = EachObject.getString("RegId");
                            String TaxNo = EachObject.getString("TaxNo");
                            String Academic = EachObject.getString("Academic_text");
                            String Specialization = EachObject.getString("Specialization_text");
                            String Country = EachObject.getString("Country");
                            String State = EachObject.getString("State");
                            String District = EachObject.getString("District");
                            String Address = EachObject.getString("Address");
                            String pincode = EachObject.getString("pincode");
                            String Lanline = EachObject.getString("Lanline");
                            String Mobile = EachObject.getString("Mobile");
                            String Time = EachObject.getString("Time");
                            String consultation = EachObject.getString("consultation");
                            String clinicname = EachObject.getString("clinicname");
                            String photo = EachObject.getString("photo");
                            String mail = EachObject.getString("mail");
                            String Ismail = EachObject.getString("Ismail");
                            String homephone = EachObject.getString("homephone");
                            String IsActive = EachObject.getString("IsActive");
                            String Actdate = EachObject.getString("Actdate");
                            String Imei = EachObject.getString("Imei");
                            String Isupdate = EachObject.getString("Isupdate");
                            String Issms = EachObject.getString("Issms");
                            String paymenttype = EachObject.getString("paymenttype");
                            String pay = EachObject.getString("pay");
                            String paymentdate = EachObject.getString("paymentdate");
                            String IsPaid = EachObject.getString("IsPaid");
                            String MCI_No = EachObject.getString("MCI_No");
                            String panno = EachObject.getString("panno");
                            String Dr_serviceno = EachObject.getString("Dr_serviceno");
                            String promotion = EachObject.getString("promotion");
                            String editdate = EachObject.getString("editdate");
                            String MAC = EachObject.getString("MAC");
                            String AppLicenseCode = EachObject.getString("AppLicenseCode");
                            String OnlineFee = EachObject.getString("OnlineFee");
                            String OnlineFeeOthers = EachObject.getString("OnlineFeeOthers");
                            String plainname = EachObject.getString("plainname");
                            String Isprfupdate = EachObject.getString("Isprfupdate");
                            String docSign = EachObject.getString("docsign");
                            String HID = EachObject.getString("HID");


                            ContentValues values = new ContentValues();
                            values.put("Docid", Docid);
                            if (name.contains("Dr.")) {
                                try {
                                    String nameDr[] = name.split("\\.");

                                    values.put("prefix", String.valueOf(nameDr[0]) + ".");
                                } catch (Exception e) {
                                    values.put("prefix", "Dr.");
                                }

                            }

                            values.put("drname", name);
                            values.put("name", name);

                            values.put("cliname", clinicname);
                            values.put("age", age);
                            values.put("gender", gender);
                            values.put("DOB", DOB);
                            values.put("PAN", panno);
                            values.put("MCI", MCI_No);
                            values.put("RegId", "");
                            values.put("ClinicTaxNo", Dr_serviceno);
                            values.put("TaxNo", TaxNo);
                            values.put("Academic", Academic);
                            values.put("Specialization", Specialization);
                            values.put("Country", Country);
                            values.put("State", State);
                            values.put("District", District);
                            values.put("Address", Address);
                            values.put("Address2", "");
                            values.put("pincode", pincode);
                            values.put("Lanline", Lanline);
                            values.put("Mobile", Mobile);
                            values.put("Homephone", homephone);
                            values.put("Actdate", BaseConfig.DeviceDate());
                            values.put("Imei", Imei);

                            String docimage = null, docsign = null;

                            try {

                                Bitmap theBitmap = BaseConfig.Glide_GetBitmap(Login.this, photo);
                                docimage = BaseConfig.saveURLImagetoSDcard(theBitmap, Docid.replace("/", "") + "_image");


                            } catch (Exception e) {
                                //e.printStackTrace();
                            }


                            values.put("docimage", docimage);
                            values.put("IsActive", IsActive);
                            values.put("emailid", mail);


                            try {

                                Bitmap theBitmap = BaseConfig.Glide_GetBitmap(Login.this, docSign);
                                docsign = BaseConfig.saveURLImagetoSDcard(theBitmap, Docid.replace("/", "") + "_sign");

                            } catch (Exception e) {
                                //e.printStackTrace();
                            }


                            values.put("docsign", docsign);
                            values.put("IsRegistered", "");
                            values.put("IsSmsSent", Issms);
                            values.put("IsEmailSent", Ismail);
                            values.put("Paymentpaid", IsPaid);
                            values.put("Isupdate", Isupdate);
                            values.put("IsEdited", "");
                            values.put("promotion", promotion);
                            values.put("MAC", BaseConfig.MacId);
                            values.put("AppLicenseCode", AppLicenseCode);
                            values.put("OnlineFee", OnlineFee);
                            values.put("OnlineFeeOthers", OnlineFeeOthers);
                            values.put("HID", HID);
                            values.put("device_deactivate", "");
                            values.put("device_status", "");

                            boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Docid as dstatus1 from Drreg where Docid ='" + Docid + "'");
                            db.execSQL("delete from Drreg");
                            db.execSQL("delete from Patreg");
                            db.execSQL("delete from drsettings");


                            if (!GetStatus) {

                                db.insert("Drreg", null, values);

                            } else {
                                db.update("Drreg", values, "Docid='" + Docid + "'", null);
                            }

                            ContentValues valuesDocNameUp = new ContentValues();
                            valuesDocNameUp.put("doctorname", name);
                            db.update("users", valuesDocNameUp, "drid = '" + Docid + "'", null);

                            //inserting values for consulation table
                            final String Insert_Consulation_Query = "Insert into Consultationhrs (workingdays,morning,evening) values('Monday,Tuesday,Wednesday,Thursday,Friday,Saturday','From 10:00-To 13:00','From 18:00-To 21:00');";
                            BaseConfig.SaveData(Insert_Consulation_Query);
                            return "SUCCESS";


                        }


                    }


                }

                db.close();

            } catch (Exception e) {

                //e.printStackTrace();
                return "";
            }


            return "";
        }


        @Override
        protected void onPostExecute(String s) {


            if (builderDialog.isShowing() && builderDialog != null) {
                builderDialog.dismiss();
            }


            if (s.equalsIgnoreCase("SUCCESS")) {
                login.performClick();
               /* Login.this.finish();
                Intent intent = new Intent(login.getContext(), Dashboard_NavigationMenu.class);
                startActivity(intent);
                BaseConfig.Assistant_Task = "False";
                LOAD_WEBSERVICES();*/

            } else if (s.equalsIgnoreCase("FALSE") || isFalse) {
                BaseConfig.showSimplePopUp("Username or Password wrong", "Login", Login.this, R.drawable.ic_warning_black_24dp, R.color.red_500);

            } else if (IsException) {
                BaseConfig.showSimplePopUp("Something went wrong Try again", "Login", Login.this, R.drawable.ic_warning_black_24dp, R.color.red_500);

            }

            super.onPostExecute(s);
        }

    }

    /**
     * MUTHUKUMAR N
     * 09-05-2018
     * FORGOT PASSWORD - SMS
     */
    public class SendSMS extends AsyncTask<Void, Void, Void> {

        String Str_MobileNumber="", Str_Random="";

        SendSMS(String MobileNumber, String RandomNumber)
        {
            this.Str_MobileNumber = MobileNumber;
            this.Str_Random = RandomNumber;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            SEND_SMS(Str_MobileNumber, randnumprefix + Str_Random);
            return null;
        }

    }

    public class SendPassword extends AsyncTask<Void, Void, Void> {

        String Str_MobileNumber="", Str_Password="";

        public SendPassword(String MobileNumber, String Password)
        {
            this.Str_MobileNumber = MobileNumber;
            this.Str_Password = Password;
        }

        protected void onPostExecute(Void v) {
            BaseConfig.showSimplePopUp("Password sent to registered mobile", "Information", Login.this, R.drawable.ic_success_done, R.color.green_400);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            SEND_SMS(Str_MobileNumber, Str_Password);
            return null;
        }

    }


    //**********************************************************************************************


}//END

