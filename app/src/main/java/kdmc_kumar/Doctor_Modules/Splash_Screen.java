package kdmc_kumar.Doctor_Modules;

import android.Manifest;
import android.Manifest.permission;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.LocalSharedPref;

/////////////////////////////////////////////////////////////////////////////////
public class Splash_Screen extends AppCompatActivity {


    private static final String SPLASH_SCREEN_OPTION = "com.csform.android.uiapptemplate.SplashScreensActivity";
    private static final String SPLASH_SCREEN_OPTION_1 = "Option 1";
    private static final String SPLASH_SCREEN_OPTION_3 = "Option 3";
    private static final int REQUEST_PERMISSIONS = 20;
    private final Handler handler = new Handler();
    @BindView(id.top_banner_splash)
    ImageView top_banner;
    @BindView(id.app_logo)
    ImageView app_logo;
    TextView txvwTitle;
    @BindView(id.app_title)
    TextView mLogo;
    @BindView(id.textprgrs)
    TextView progress_status;
    @BindView(id.progressBar)
    ProgressBar progressBar;
    private int progress;
    private int progressStatus;
    private final ProgressDialog pDialog;

    public Splash_Screen() {
    }

    public static boolean LoadReportsBooleanStatus(String Query) {
        try {
            int havcount = 0;


            SQLiteDatabase db = BaseConfig.GetDb();//ctx);

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        c.getString(c.getColumnIndex("dstatus1"));

                        havcount++;

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return havcount > 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.new_splash_layout);

        try {

            BaseConfig.bkproc = 1;


            BaseConfig.LoadAppConfiguration(this);


            this.GetIniliz();


        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    private void configApplicationLanguage() {
        // TODO: 22-06-2018 Load Language
        try {
            LocalSharedPref localSharedPref = new LocalSharedPref(this);
            String LanguageCode=localSharedPref.getValue("LocaleLanguage");
            if(!LanguageCode.equalsIgnoreCase("")&&!LanguageCode.isEmpty()) {
                Locale myLocale = new Locale(LanguageCode);
                Resources res = this.getResources();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                this.onConfigurationChanged(conf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private final void GetIMEI() {
        try {

            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

            if (ContextCompat.checkSelfPermission(this, permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            BaseConfig.Imeinum = tm.getDeviceId();


        } catch (RuntimeException ec) {
            BaseConfig.Imeinum = "";
            ec.printStackTrace();

        }
    }

    private final void GetMAC() {
        try {
            //Get Mac Address
            WifiManager manager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            BaseConfig.MacId = info.getMacAddress();


        } catch (RuntimeException ec) {
            BaseConfig.MacId = "";
            ec.printStackTrace();

        }
    }


    private final void GetIniliz() {

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //Getting Imei and mac id....
        this.GetIMEI();
        this.GetMAC();


        String category = Splash_Screen.SPLASH_SCREEN_OPTION_3;

        Bundle extras = this.getIntent().getExtras();
        if (extras != null && extras.containsKey(Splash_Screen.SPLASH_SCREEN_OPTION)) {
            category = extras.getString(Splash_Screen.SPLASH_SCREEN_OPTION, Splash_Screen.SPLASH_SCREEN_OPTION_1);
        }

        this.mLogo.setAlpha((float) 0);
        this.setAnimation(category);

        Animation anim_app_banner = AnimationUtils.loadAnimation(this, anim.up_to_bottom_splash);
        this.top_banner.startAnimation(anim_app_banner);

        BaseConfig.Glide_LoadImageView(this.app_logo, BaseConfig.AppLogo);

        Animation anim_app_logo = AnimationUtils.loadAnimation(this, anim.push_left_in);
        this.app_logo.startAnimation(anim_app_logo);


        this.CallNextIntent();

        this.configApplicationLanguage();

    }

    private void CallNextIntent() {

        new Thread(new Runnable() {
            public void run() {

                while (Splash_Screen.this.progressStatus < 100) {
                    Splash_Screen.this.progressStatus = this.doSomeWork();

                    Splash_Screen.this.handler.post(() -> {
                        Splash_Screen.this.progressBar.setProgress(Splash_Screen.this.progressStatus);
                        Splash_Screen.this.progress_status.setText((String.valueOf(Splash_Screen.this.progressStatus)));
                    });
                }

                Splash_Screen.this.handler.post(() -> {

                    Splash_Screen.this.progressBar.setVisibility(View.GONE);

                    Splash_Screen.this.finish();

                    try {

                        String chkquery = "select enablepin as dstatus1 from drsettings where enablepin='true'";
                        boolean pinlay = BaseConfig.LoadReportsBooleanStatus(chkquery);
                        if (pinlay) {

                            Splash_Screen.this.finish();
                            String PINNO = BaseConfig.GetValues("select pin as ret_values from drsettings");
                            Intent intent = new Intent(Splash_Screen.this.getApplicationContext(), PinPassword.class);
                            intent.putExtra("PINNUMBER", PINNO);
                            Splash_Screen.this.startActivity(intent);
                            CustomIntent.customType(Splash_Screen.this, 1);
                        } else {
                            Splash_Screen.this.finish();
                            Intent intent = new Intent(Splash_Screen.this.getApplicationContext(), Login.class);
                            Splash_Screen.this.startActivity(intent);
                            CustomIntent.customType(Splash_Screen.this, 1);


                        }


                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }

                });
            }

            private int doSomeWork() {
                try {
                    // ---simulate doing some work---
                    Thread.sleep(30L);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ++Splash_Screen.this.progress;
                return
                        Splash_Screen.this.progress;
            }
        }).start();

    }


    /**
     * Animation depends on category.
     */
    private void setAnimation(String category) {

        this.animation1();

    }

    private void animation1() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(this.mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200L);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(this.mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200L);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(this.mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500L);
        animatorSet.start();


    }


}//END


