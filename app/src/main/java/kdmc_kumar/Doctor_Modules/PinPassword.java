package kdmc_kumar.Doctor_Modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.IndicatorDots.IndicatorType;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;


public class PinPassword extends AppCompatActivity {

    private static final String TAG = "PinLockView";
    private TextView profilename;
    private ImageView prfileimage;
    private ImageView switch_login;
    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;

    public PinPassword() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(layout.activity_pin_password);


        this.switch_login = this.findViewById(id.switch_login);
        this.switch_login.setOnClickListener(view -> {

            finish();
            BaseConfig.Assistant_Task = "False";
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("ALTERNATIVE", "TRUE");
            this.startActivityForResult(intent, 500);
            this.overridePendingTransition(anim.slide_in_right, anim.slide_out_left);


        });

        this.mPinLockView = this.findViewById(id.pin_lock_view);
        this.mIndicatorDots = this.findViewById(id.indicator_dots);

        this.mPinLockView.attachIndicatorDots(this.mIndicatorDots);

        String password = this.getIntent().getExtras().getString("PINNUMBER");

        String DecryptPin = new String(Base64.decode(password, Base64.DEFAULT));

        //Log.e("onCreate: Pin ", DecryptPin);

        PinLockListener mPinLockListener = new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                Log.d(PinPassword.TAG, "Pin complete: " + pin);


                // TODO: 7/20/2017 Pin 4 is Entered to check is valid or not

                if (pin.equalsIgnoreCase(DecryptPin)) {
                    //Sucess


                    finish();
                    BaseConfig.Assistant_Task = "False";
                    Intent intent = new Intent(PinPassword.this, Dashboard_NavigationMenu.class);
                    PinPassword.this.startActivityForResult(intent, 500);
                    PinPassword.this.overridePendingTransition(anim.slide_in_right, anim.slide_out_left);

                        BaseConfig.StartWebservice_Import(PinPassword.this,2);
                        BaseConfig.StartWebservice_Export(PinPassword.this,2);

                } else {

                    new CustomKDMCDialog(PinPassword.this)
                            .setLayoutColor(color.orange_500)
                            .setImage(drawable.ic_warning_black_24dp)
                            .setTitle(getString(string.warning))
                            .setDescription("Pin Number is incorrect Please Try again..")
                            .setPossitiveButtonTitle(getString(string.ok));

                    PinPassword.this.mIndicatorDots.requestFocus();


                }


            }

            @Override
            public void onEmpty() {
                Log.d(PinPassword.TAG, "Pin empty");
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                Log.d(PinPassword.TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
            }
        };


        this.mPinLockView.setPinLockListener(mPinLockListener);

        this.profilename = this.findViewById(id.profile_name);
        this.prfileimage = this.findViewById(id.profile_image);


        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        //PinNumber length
        this.mPinLockView.setPinLength(4);

        //Loading Details
        this.LoadDoctorInfo(this.profilename, this.prfileimage);

        this.mPinLockView.setTextColor(ContextCompat.getColor(this, color.white));

        this.mIndicatorDots.setIndicatorType(IndicatorType.FILL_WITH_ANIMATION);
    }


    /**
     * Load doctor photo and name if exists
     * Muthukumar
     * 07/04/2017
     */
    private final void LoadDoctorInfo(TextView WelcomeTitle, ImageView DoctorPhoto) {
        boolean status = BaseConfig.LoadReportsBooleanStatus("select Docid as dstatus1 from Drreg");
        if (status) {
            String Photo_Path = BaseConfig.GetValues("select docimage as ret_values from Drreg");
            String Doctor_Name = BaseConfig.GetValues("select name as ret_values from Drreg");

            //Log.e("LoadDoctorInfo: ", Doctor_Name);

            //Log.e("LoadDoctorInfo: ", Doctor_Name);

            WelcomeTitle.setText(this.getString(string.welcome) + " - " + Doctor_Name);


            try {

                BaseConfig.Glide_LoadImageView( DoctorPhoto,  Photo_Path);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    //End
}
