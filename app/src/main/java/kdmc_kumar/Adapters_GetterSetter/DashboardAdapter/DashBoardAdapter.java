package kdmc_kumar.Adapters_GetterSetter.DashboardAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter.MyView;
import kdmc_kumar.CaseNotes_Modules.CaseNotes;
import kdmc_kumar.Core_Modules.ClinicalInformation;
import kdmc_kumar.Core_Modules.CloudData;
import kdmc_kumar.Core_Modules.DoctorReferral;
import kdmc_kumar.Drug_Directory.Cims;
import kdmc_kumar.Core_Modules.Investigations;
import kdmc_kumar.Core_Modules.MedicinePrescription;
import kdmc_kumar.Core_Modules.MyPatient;
import kdmc_kumar.Core_Modules.OnlineConsultation;
import kdmc_kumar.Core_Modules.Patient_Registration;
import kdmc_kumar.Inpatient_Module.Inpatient_List;
import kdmc_kumar.Masters_Modules.Masters_New;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Explode;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Fade;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Transition;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Transition.EpicenterCallback;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.Transition.TransitionListenerAdapter;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.TransitionManager;
import kdmc_kumar.Utilities_Others.Transistion.transitionseverywhere.TransitionSet;

/**
 * Created by Ponnusamy on 9/17/2017.
 * KDMCMobyDoctor
 */

public class DashBoardAdapter extends Adapter<MyView> {


    public static final String VIEW_NAME_HEADER_TITLE = "0";
    /*private static final int[] fabbgstr = {
            R.color.ic_CaseNotes,
            R.color.ic_ClinicalInformation,
            R.color.ic_Investigation,
            R.color.ic_Prescription,
            R.color.ic_Referrals,
            R.color.ic_Mypatient,
            R.color.ic_DrugDirectory,
            R.color.ic_OnlineConsultation,
            R.color.ic_Inpatient,
            R.color.ic_Masters,
            R.color.ic_Cloud,
    };*/

    private static final int[] fabbgstr = {
            color.colorPrimary,
            color.ic_ClinicalInformation,
            color.ic_Investigation,
            color.ic_Prescription,
            color.ic_Referrals,
            color.ic_Mypatient,
            color.ic_DrugDirectory,
            color.ic_OnlineConsultation,
            color.ic_Inpatient,
            color.ic_Masters,
            color.ic_Cloud,
    };

    private final String[] web;

    private final int[] Imageid;
    private final Context mContext;

    public DashBoardAdapter(Context mContext, String[] web, int[] imageid) {
        this.mContext = mContext;
        this.web = web;
        this.Imageid = imageid;
    }

    @NonNull
    @Override
    public final DashBoardAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout.task_items, parent, false);
        return new DashBoardAdapter.MyView(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull DashBoardAdapter.MyView holder, int position) {

        holder.textView.setText(this.web[position]);

        holder.fab.setImageDrawable(this.mContext.getResources().getDrawable(this.Imageid[position]));

        holder.fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(this.mContext.getResources().getString(DashBoardAdapter.fabbgstr[0]))));

        holder.fab.setOnClickListener(v -> this.letsExplodeIt(holder.reippedlayout,holder,position,holder.textView));

        holder.textView.setOnClickListener(v -> this.letsExplodeIt(holder.reippedlayout,holder,position,holder.textView));

        holder.reippedlayout.setOnClickListener(v -> this.letsExplodeIt(holder.reippedlayout,holder,position,holder.textView));

    }



    @Override
    public final int getItemCount() {
        return this.Imageid.length;
    }

    public void onClickItem(Context context, int position, TextView textView) {

        Activity activity = (Activity) context;

        Intent intent=null;


        switch (position) {

            case 0:
                intent = new Intent(context, Patient_Registration.class);
                break;


            case 1:
                intent = new Intent(context, CaseNotes.class);
                break;

            case 2:
                intent = new Intent(context, ClinicalInformation.class);
                break;

            case 3:
                intent = new Intent(context, Investigations.class);
                break;

            case 4:
                intent = new Intent(context, MedicinePrescription.class);
                break;

            case 5:
                intent = new Intent(context, DoctorReferral.class);
                break;
            case 6:
                intent = new Intent(context, MyPatient.class);
                break;

            case 7:
                intent = new Intent(context, Cims.class);
                break;

            case 8:
                intent = new Intent(context, OnlineConsultation.class);
                break;

            case 9:
                intent = new Intent(context, Inpatient_List.class);
                break;

            case 10:
                intent = new Intent(context, Masters_New.class);
                break;


            case 11:
                intent = new Intent(context, CloudData.class);
                break;

        }


        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, textView, VIEW_NAME_HEADER_TITLE);
            ContextCompat.startActivity(context, intent, activityOptions.toBundle());

        } else {
            context.startActivity(intent);
            CustomIntent.customType(context, 1);
            activity.finish();

        }


    }



    private void letsExplodeIt(View clickedView ,DashBoardAdapter.MyView holder,int position, TextView txtvw) {
        // save rect of view in screen coordinated
        Rect viewRect = new Rect();
        clickedView.getGlobalVisibleRect(viewRect);

        TransitionSet set = new TransitionSet()
                .addTransition(new Explode().setEpicenterCallback(new EpicenterCallback() {
                    @Override
                    public Rect onGetEpicenter(Transition transition) {
                        return viewRect;
                    }
                }).excludeTarget(clickedView, true))
                .addTransition(new Fade().addTarget(clickedView))
                .addListener(new TransitionListenerAdapter() {
                    @Override
                    public void onTransitionEnd(Transition transition) {

                        //   ((Activity)clickedView.getContext()).onBackPressed();
                        DashBoardAdapter.this.onClickItem(clickedView.getContext(), position, txtvw);
                        transition.removeListener(this);
                    }
                });

        TransitionManager.beginDelayedTransition(TaskSelection_Final.grid, set);

        // remove all views from Recycler View
        TaskSelection_Final.grid.setAdapter(null);
    }


    public static class MyView extends ViewHolder {
        final TextView textView;
        //public ImageView imageView;
        final android.support.design.widget.FloatingActionButton fab;

        final LinearLayout reippedlayout;


        MyView(View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(id.tv_species);
            this.fab = itemView.findViewById(id.img_thumbnail);

            this.reippedlayout = itemView.findViewById(id.grid_items);
        }
    }
}
