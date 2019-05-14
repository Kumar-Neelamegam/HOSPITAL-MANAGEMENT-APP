package kdmc_kumar.Core_Modules;

import android.R.color;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.OnlineConsultation_DataObjects;
import kdmc_kumar.Adapters_GetterSetter.OnlineConsultationRecylerAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;


public class OnlineConsultation extends AppCompatActivity {


    @BindView(id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(id.img_nodata)
    AppCompatImageView imgNodata;
    @BindView(id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(id.list)
    RecyclerView list;

    @BindView(id.toolbar)
    Toolbar toolbar;
    @BindView(id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(id.toolbar_title)
    TextView toolbarTitle;
    @BindView(id.toolbar_exit)
    AppCompatImageView toolbarExit;

    private OnlineConsultationRecylerAdapter onlineConsultationRecylerAdapter;
    private GridLayoutManager lLayout;


    public OnlineConsultation() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(layout.onlineconsultation_list);

        try {

            this.GETINITIALIZE();

            this.CONTROLLISTENERS();

            this.AUTOREFRESHPATIENTLIST();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Handler timerHandler;
    private Runnable timerRunnable;

    private void AUTOREFRESHPATIENTLIST() {


        this.timerHandler = new Handler();

        this.timerRunnable = new Runnable() {
            @Override
            public void run() {

                OnlineConsultation.this.SelectedGetPatientDetails();

                OnlineConsultation.this.timerHandler.postDelayed(this, 180000); //run every second

            }
        };

        this.timerHandler.postDelayed(this.timerRunnable, 180000); //Start timer after 1 sec

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (this.timerHandler != null) {
            this.timerHandler.removeCallbacks(this.timerRunnable);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.timerHandler != null) {
            this.timerHandler.removeCallbacks(this.timerRunnable);
        }
    }


    private void CONTROLLISTENERS() {

        this.toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        this.toolbarBack.setOnClickListener(view -> this.LoadBack());

        this.SelectedGetPatientDetails();


    }

    private void GETINITIALIZE() {

        ButterKnife.bind(this);


        BaseConfig.welcometoast = 0;

        this.toolbarTitle.setText(String.format("%s - %s", this.getString(string.app_name), this.getString(string.onlin_consultations)));

        this.setSupportActionBar(this.toolbar);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(this.toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }


        this.lLayout = new GridLayoutManager(this, 2);
        this.list.setHasFixedSize(true);
        this.list.setLayoutManager(this.lLayout);
        this.list.setNestedScrollingEnabled(false);
        this.list.setItemAnimator(new DefaultItemAnimator());

        this.swipeRefreshLayout = this.findViewById(id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(color.holo_green_dark, color.holo_red_dark, color.holo_blue_bright);
        this.swipeRefreshLayout.setOnRefreshListener(() -> {

            try {

                this.SelectedGetPatientDetails();

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        });


    }

    private void LoadBack() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);
    }


    private final void SelectedGetPatientDetails() {

        ArrayList<OnlineConsultation_DataObjects> allItems = new ArrayList<>();

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//OnlineConsultation.this);

            Cursor c = db.rawQuery("select * from OnlineConsultancy where (IsActive='True' or IsActive='1')  order by ServerId", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String Id = c.getString(c.getColumnIndex("id"));
                        String PatientName = c.getString(c.getColumnIndex("pname"));
                        String PatientId = c.getString(c.getColumnIndex("pid"));
                        String Age = c.getString(c.getColumnIndex("age"));
                        String Gender = c.getString(c.getColumnIndex("gender"));
                        //String PatientPhoto = c.getString(c.getColumnIndex("pphoto"));
                        String PatientPhoto = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='"+PatientId+"'");
                        String ConsultationId = c.getString(c.getColumnIndex("ServerId"));
                        String MedId = c.getString(c.getColumnIndex("Medid"));

                        allItems.add(new OnlineConsultation_DataObjects(Id, PatientName, PatientId, Age, Gender, PatientPhoto, ConsultationId, MedId));

                    } while (c.moveToNext());
                }
            }

            db.close();
            c.close();

            this.onlineConsultationRecylerAdapter = new OnlineConsultationRecylerAdapter(allItems, this);
            this.list.setAdapter(this.onlineConsultationRecylerAdapter);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        //nabeel
        if (this.imgNodata != null) {
            if (allItems.size() > 0) {
                this.imgNodata.setVisibility(View.GONE);
            } else {
                this.imgNodata.setVisibility(View.VISIBLE);
            }
        }


        // Stop refresh animation
        this.swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public final void onBackPressed() {

        this.LoadBack();

    }


}//END

		
		
		
		
		
