package kdmc_kumar.MyPatient_Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.MyPatient_Module.Patient_Immunization.SimpleItemRecyclerViewAdapter.ViewHolder;
import kdmc_kumar.Utilities_Others.Validation1;


public class Patient_Immunization extends Fragment {

    static final int DATE_DIALOG_ID = 1;
    private static final String TAG = "MainActivity";
    public static int mYear;
    public static int mMonth;
    public static int mDay;
    public static int mcYear;
    public static int mcDay;
    public static int mcMonth;
    // Declaration -------------------
    BaseConfig bcnfg = new BaseConfig();
    // /////////////////////////////////////////
    ArrayList<HashMap<String, String>> titles_list;
    ArrayAdapter<CommonDataObjects.RowItemVaccination> adapter;
    private List<CommonDataObjects.DataObject> filteredList;
    ArrayList<CommonDataObjects.RowItemVaccination> rowItems;
    // /////////////////////////////////////////
    private ImageView imgNoMedia;
    TextView info;
    // /////////////////////////////////////////
    ListView savedvaccineList;
    private GridLayoutManager lLayout;

    /*// RecycleView adapter object
    private RecyclerAdapter mAdapter;*/
    private EditText srchbx;
    Button search;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private String BUNDLE_PATIENT_ID;
    private ArrayList<CommonDataObjects.DataObject> results = new ArrayList<>();
    // List of all dictionary words
    private ArrayList<CommonDataObjects.DataObject> dictionaryWords;
    // RecycleView adapter object
    private Patient_Immunization.SimpleItemRecyclerViewAdapter mAdapter;

    public Patient_Immunization() {
    }

    // /////////////////////////////////////////////////////////////////////////////
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                layout.fragment_mypatient_vaccinationfragment, container,
                false);

        Bundle args = this.getArguments();
        this.BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);


        this.GetIniliz(rootView);


        this.srchbx = rootView.findViewById(id.edt);
        // search suggestions using the edittext widget
        this.srchbx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Log.e("onTextChanged: ", String.valueOf(dictionaryWords));


                Patient_Immunization.this.mAdapter.getFilter().filter(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        return rootView;
    }


    //**********************************************************************************************

    private void GetIniliz(View rootView) {
        // TODO Auto-generated method stub
        this.imgNoMedia = rootView.findViewById(id.imgNoMedia);
        this.imgNoMedia.setVisibility(View.GONE);


        this.recyclerView = rootView.findViewById(id.listVaccination);

        this.dictionaryWords = this.getDataSet();
        this.filteredList = new ArrayList<>();
        this.filteredList.addAll(this.dictionaryWords);

        //recyclerView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.setLayoutManager(mLayoutManager);


        this.lLayout = new GridLayoutManager(this.getActivity(), 2);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.lLayout);
        this.recyclerView.setNestedScrollingEnabled(false);


        assert this.recyclerView != null;
        this.mAdapter = new Patient_Immunization.SimpleItemRecyclerViewAdapter(this.filteredList);
        this.recyclerView.setAdapter(this.mAdapter);


        this.srchbx = rootView.findViewById(id.edt);
        // search suggestions using the edittext widget
        this.srchbx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Patient_Immunization.this.mAdapter.getFilter().filter(charSequence.toString());


                Patient_Immunization.this.dictionaryWords = Patient_Immunization.this.getDataSetSearch(charSequence.toString());
                Patient_Immunization.this.filteredList = new ArrayList<>();
                Patient_Immunization.this.filteredList.addAll(Patient_Immunization.this.dictionaryWords);

                Patient_Immunization.this.lLayout = new GridLayoutManager(Patient_Immunization.this.getActivity(), 2);
                Patient_Immunization.this.recyclerView.setHasFixedSize(true);
                Patient_Immunization.this.recyclerView.setLayoutManager(Patient_Immunization.this.lLayout);
                Patient_Immunization.this.recyclerView.setNestedScrollingEnabled(false);


                assert Patient_Immunization.this.recyclerView != null;
                Patient_Immunization.this.mAdapter = new Patient_Immunization.SimpleItemRecyclerViewAdapter(Patient_Immunization.this.filteredList);
                Patient_Immunization.this.recyclerView.setAdapter(Patient_Immunization.this.mAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }

    private ArrayList<CommonDataObjects.DataObject> getDataSet() {

        this.results = new ArrayList<>();

        String query;

        query = "select vaccinename,schedule,givendt,weight from vaccination where patid='"
                + this.BUNDLE_PATIENT_ID + '\'';


        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    CommonDataObjects.DataObject obj = new CommonDataObjects.DataObject();
                    obj.setVaccinename(c.getString(c.getColumnIndex("vaccinename")));
                    obj.setGivendt(c.getString(c.getColumnIndex("givendt")));
                    obj.setSchedule(c.getString(c.getColumnIndex("schedule")));
                    obj.setWeight(c.getString(c.getColumnIndex("weight")));


                    this.results.add(obj);

                }
                while (c.moveToNext());
            }
        }

        c.close();
        db.close();


        return this.results;

    }

    private ArrayList<CommonDataObjects.DataObject> getDataSetSearch(String str) {

        this.results = new ArrayList<>();

        String query;

        query = "select vaccinename,schedule,IFNULL(givendt,'')as givendt,IFNULL(weight,'')as weight from vaccination where patid='"
                + this.BUNDLE_PATIENT_ID + '\'';


        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    CommonDataObjects.DataObject obj = new CommonDataObjects.DataObject();
                    obj.setVaccinename(c.getString(c.getColumnIndex("vaccinename")));
                    obj.setGivendt(BaseConfig.CheckDBString(c.getString(c.getColumnIndex("givendt"))));
                    obj.setSchedule(c.getString(c.getColumnIndex("schedule")));
                    obj.setWeight(BaseConfig.CheckDBString(c.getString(c.getColumnIndex("weight"))));

                    if (c.getString(c.getColumnIndex("vaccinename")).toLowerCase().startsWith(str)) {
                        this.results.add(obj);
                    }


                }
                while (c.moveToNext());
            }
        }

        c.close();
        db.close();


        return this.results;

    }

    //**********************************************************************************************
    //Custom Filterable Recylcer View
    /*
    @Muthukumar
    14-7-16
    reference:
    http://inducesmile.com/android/android-recyclerview-filter-search-functionality-with-search-suggest/
     */

    // create a custom RecycleViewAdapter class

    public class SimpleItemRecyclerViewAdapter extends Adapter<ViewHolder> implements Filterable {

        Context context;
        final Patient_Immunization.SimpleItemRecyclerViewAdapter.CustomFilter mFilter;
        RecyclerView myrecyler;
        View view;
        private final List<CommonDataObjects.DataObject> mValues;


        SimpleItemRecyclerViewAdapter(List<CommonDataObjects.DataObject> items) {
            this.mValues = items;
            this.mFilter = new Patient_Immunization.SimpleItemRecyclerViewAdapter.CustomFilter(this);
        }


        @NonNull
        @Override
        public final Patient_Immunization.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layout.listrowimmunization, parent, false);
            return new Patient_Immunization.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }


        @Override
        public final void onBindViewHolder(@NonNull Patient_Immunization.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {

            CommonDataObjects.DataObject rowItem = this.mValues.get(position);

            holder.vaccinename.setText(BaseConfig.CheckDBString(rowItem.getVaccinename()));
            holder.schedule.setText(BaseConfig.CheckDBString(rowItem.getSchedule()));
            holder.givenon.setText(BaseConfig.CheckDBString(rowItem.getGivendt()));
            holder.weight.setText(BaseConfig.CheckDBString(rowItem.getWeight()));

            if (holder.givenon.getText().equals("")) {
                holder.vaccinename.setTextColor(Color.RED);
                holder.schedule.setTextColor(Color.RED);
                holder.givenon.setTextColor(Color.RED);
                holder.weight.setTextColor(Color.RED);
            } else {
                holder.vaccinename.setTextColor(Color.BLUE);
                holder.schedule.setTextColor(Color.BLUE);
                holder.givenon.setTextColor(Color.BLUE);
                holder.weight.setTextColor(Color.BLUE);
            }

            ////Log.e("RowItem Vaccination: ", rowItem.getGivendt().toString()+"/"+rowItem.getWeight().toString());


            holder.card_view.setOnClickListener(v -> {


                SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.ENGLISH);
                Date date = new Date();
                String dttm = dateformt.format(date);

                LayoutInflater li = LayoutInflater.from(v.getContext());
                View promptsView = li.inflate(layout.vaccipopup, null);

                ////Log.e("RowItem Vaccination: ", rowItem.getGivendt().toString()+"/"+rowItem.getWeight().toString());

                if (holder.givenon.getText().equals("")
                        && holder.weight.getText().equals("")) {

                    Builder alertDialogBuilder = new Builder(
                            v.getContext());

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    Button cancelbtn = promptsView
                            .findViewById(id.cancel_button);
                    Button replybtn = promptsView
                            .findViewById(id.ok_button);
                    TextView pid, pname, agegenn;
                    pid = promptsView.findViewById(id.textView5);
                    pname = promptsView.findViewById(id.textView6);
                    agegenn = promptsView
                            .findViewById(id.textView7);

                    pname.setText(rowItem.getVaccinename());
                    pid.setText(rowItem.getSchedule());

                    String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + Patient_Immunization.this.BUNDLE_PATIENT_ID + '\'');
                    agegenn.setText(Patient_AgeGender);

                    EditText vn;
                    EditText sch;
                    EditText gvon;
                    EditText wei;
                    vn = promptsView.findViewById(id.editText1);
                    sch = promptsView.findViewById(id.editText2);
                    gvon = promptsView.findViewById(id.editText3);
                    wei = promptsView.findViewById(id.editText4);

                    vn.setText(rowItem.getVaccinename());
                    sch.setText(rowItem.getSchedule());

                    gvon.setText(dttm);
                    if (holder.weight.getText().toString().equals("null")) {
                        wei.setText("");
                    } else {

                        wei.setText(holder.weight.getText().toString());
                    }

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                    replybtn.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            // TODO Auto-generated method stub

                            if (gvon.getText().length() > 0) {
                                if (wei.getText().length() > 0) {
                                    this.SaveLocal(vn, sch, gvon, wei);
                                    alertDialog.cancel();
                                } else {
                                    wei.setError("Required");
                                }
                            } else {
                                gvon.setError("Required");

                            }

                        }

                        private void SaveLocal(EditText vn, EditText sch,
                                               EditText gvon, EditText wei) {
                            // TODO Auto-generated method stub
                            try {
                                if (this.checkValidation(vn, sch, gvon, wei))

                                    this.submitForm(vn, sch, gvon, wei);
                                else

                                    Toast.makeText(Patient_Immunization.this.getContext(), "check for missing & valid Data", Toast.LENGTH_SHORT).show();
                                //BaseConfig.SnackBar(this,  "check for missing & valid Data", parentLayout);


                            } catch (RuntimeException ignored) {
                                // e.printStackTrace();

                            }
                        }

                        private void submitForm(EditText vn, EditText sch,
                                                EditText gvon, EditText wei) {

                            SimpleDateFormat dateformt = new SimpleDateFormat(
                                    "dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                            Date date = new Date();
                            String dttm = dateformt.format(date);

                            String Insert_Query = "update vaccination set givendt='"
                                    + gvon.getText()
                                    + "',weight='"
                                    + wei.getText()
                                    + "',dt='"
                                    + dttm
                                    + "',isactive='1',Isupdate='1',duedt = '00/00/0000 00:00:00',HID = '" + BaseConfig.HID + '\''
                                    + "where vaccinename='"
                                    + vn.getText()
                                    + "' and schedule='"
                                    + sch.getText()
                                    + "' and patid='"
                                    + Patient_Immunization.this.BUNDLE_PATIENT_ID
                                    + "' and isactive='0' and Isupdate='0'";

                            BaseConfig.SaveData(Insert_Query);

                            this.showSimplePopUpExit();
                        }

                        private void showSimplePopUpExit() {
                            // TODO Auto-generated method stub

                            Builder helpBuilder = new Builder(
                                    v.getContext());
                            helpBuilder.setTitle("Information");
                            helpBuilder.setMessage("Vaccination Added Successfully");
                            helpBuilder.setPositiveButton("Ok",
                                    (dialog, which) -> {


                                        dialog.cancel();

                                        Patient_Immunization.this.dictionaryWords = Patient_Immunization.this.getDataSet();
                                        Patient_Immunization.this.filteredList = new ArrayList<>();
                                        Patient_Immunization.this.filteredList.addAll(Patient_Immunization.this.dictionaryWords);

                                        Patient_Immunization.this.lLayout = new GridLayoutManager(Patient_Immunization.this.getActivity(), 2);
                                        Patient_Immunization.this.recyclerView.setHasFixedSize(true);
                                        Patient_Immunization.this.recyclerView.setLayoutManager(Patient_Immunization.this.lLayout);
                                        Patient_Immunization.this.recyclerView.setNestedScrollingEnabled(false);


                                        assert Patient_Immunization.this.recyclerView != null;
                                        Patient_Immunization.this.mAdapter = new Patient_Immunization.SimpleItemRecyclerViewAdapter(Patient_Immunization.this.filteredList);
                                        Patient_Immunization.this.recyclerView.setAdapter(Patient_Immunization.this.mAdapter);

                                    });

                            // Remember, create doesn't show the dialog
                            AlertDialog helpDialog = helpBuilder.create();
                            helpDialog.show();

                        }

                        private boolean checkValidation(EditText vn,
                                                        EditText sch, EditText gvon, EditText wei) {
                            boolean ret = true;
                            if (!Validation1.hasText(vn)) {
                                ret = false;
                            }

                            return ret;
                        }
                    });

                    cancelbtn.setOnClickListener(arg0 -> {
                        // TODO Auto-generated method stub

                        alertDialog.cancel();
                    });

                } else {

                    Toast.makeText(Patient_Immunization.this.getContext(), "Already Vaccination Given", Toast.LENGTH_SHORT).show();
                    //BaseConfig.SnackBar(this,  "Already Vaccination Given", parentLayout);

                }


            });
        }


        @Override
        public final int getItemCount() {
            return this.mValues.size();
        }


        @Override
        public final Filter getFilter() {
            return this.mFilter;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {


            public CommonDataObjects.DataObject mItem;
            final TextView vaccinename;
            final TextView schedule;
            final TextView givenon;
            final TextView weight;
            final CardView card_view;


            ViewHolder(View view) {
                super(view);


                this.vaccinename = view.findViewById(id.textView1);
                this.schedule = view.findViewById(id.textView2);
                this.givenon = view.findViewById(id.textView3);
                this.weight = view.findViewById(id.textView4);
                this.card_view = view.findViewById(id.card_view);


            }


        }


        class CustomFilter extends Filter {

            private final Patient_Immunization.SimpleItemRecyclerViewAdapter mAdapter;


            private CustomFilter(Patient_Immunization.SimpleItemRecyclerViewAdapter mAdapter) {
                this.mAdapter = mAdapter;

            }

            @Override
            protected final Filter.FilterResults performFiltering(CharSequence charSequence) {
                Patient_Immunization.this.filteredList = new ArrayList<>();
                Patient_Immunization.this.filteredList.clear();
                Filter.FilterResults results = new Filter.FilterResults();
                if (charSequence.length() == 0) {
                    Patient_Immunization.this.filteredList.addAll(Patient_Immunization.this.dictionaryWords);
                } else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (Iterator<CommonDataObjects.DataObject> iterator = Patient_Immunization.this.dictionaryWords.iterator(); iterator.hasNext(); ) {
                        CommonDataObjects.DataObject mWords = iterator.next();
                        if (mWords.getVaccinename().toLowerCase().startsWith(filterPattern)) {
                            Patient_Immunization.this.filteredList.add(mWords);
                        }
                    }
                }


                System.out.println("Count Number (filteredList)" + Patient_Immunization.this.filteredList.size());

                results.values = Patient_Immunization.this.filteredList;
                results.count = Patient_Immunization.this.filteredList.size();


                return results;
            }

            @Override
            protected final void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
                System.out.println("Count Number " + ((List<CommonDataObjects.DataObject>) filterResults.values).size());
                mAdapter.notifyDataSetChanged();


                if (Patient_Immunization.this.filteredList.size() > 0) {
                    System.out.println("Count Number " + Patient_Immunization.this.filteredList.get(0).getVaccinename());
                    //Log.e("Total : ",String.valueOf( filteredList.size()));
                    // Count.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else if (Patient_Immunization.this.filteredList.size() == 0 && ((List<CommonDataObjects.DataObject>) filterResults.values).size() == 0) {

                    //Log.e("filteredList","No search keyword found..click refresh");
                    //Count.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
                } else {
                    //Log.e("filteredList","Total : " + filteredList.size());
                    //Count.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }

        }
    }
    //**********************************************************************************************


}
