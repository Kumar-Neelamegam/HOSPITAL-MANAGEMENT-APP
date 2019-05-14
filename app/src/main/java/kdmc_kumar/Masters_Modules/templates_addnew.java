package kdmc_kumar.Masters_Modules;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MedicineGetSet;
import kdmc_kumar.Core_Modules.BaseConfig;


@SuppressWarnings("ALL")
public class templates_addnew extends AppCompatActivity implements View.OnClickListener {

    public String tablet_type = "";
    AutoCompleteTextView template_name, medicine_name;
    CheckBox all, morning, afternoon, evening, night;
    EditText et_mrng, et_aftn, et_eve, et_night, search;
    Spinner duration;
    RadioButton before_food, after_food, with_food;
    Button add_btn, save_btn, cancel_btn;
    ListView listview;
    CheckBox chk_all, chk_morng, chk_aftr, chk_eve, chk_nite;
    TextView template_no;
    // titles mandatory colour
    TextView title_templatename, title_medicinename, title_duration, title_frequency;
    //declaration
    BaseConfig bcnfg = new BaseConfig();
    //Modified on 16/03/015
    //added syrup,ointment prcs
    Spinner syrupM1, syrupA2, syrupE3, syrupN4;
    LinearLayout optionfreq;
    List<String> listcnt = new ArrayList<String>();
    List<String> listtottabcount = new ArrayList<String>();


    RecyclerView recyler_view;
    String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";
    String s1 = "0", s2 = "0", s3 = "0", s4 = "0";

    LinearLayoutManager linearLayoutManager;
    MedicineRecylerAdapter medicineRecylerAdapter;


    ArrayList<MedicineGetSet> medicineGetSets;

    ArrayList<String> list1 = new ArrayList<>();

    Toolbar toolbar;

    ImageView back, home, exit;
    // ////////////////////////////////////////////////////////////////////////////////////////////////
    List<String> list = new ArrayList<String>();
    List<String> advlist = new ArrayList<String>();

    // /////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.templates_prescription);
        setContentView(R.layout.kdmc_layout_template_addnew);


        medicineGetSets = new ArrayList<>();
        getinitliaze();


        if (BaseConfig.temp_flag.equalsIgnoreCase("true")) {
            LoadTempId();
            template_name.setEnabled(true);
        } else {
            template_name.setEnabled(false);
            gettemplate();
        }


    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void gettemplate() {

        try {
            SQLiteDatabase db = BaseConfig.GetDb();

            String temp_name = "";
            medicineGetSets = new ArrayList<>();


            Cursor c = db.rawQuery("select MedicineName,TemplateId,TemplateName,Morning,Afternoon from TemplateDtls where TemplateName='" + BaseConfig.selectedtemplate.trim() + "';", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        JSONArray jsonArray = new JSONArray(c.getString(c.getColumnIndex("MedicineName")));
                        JSONObject objJson = null;

                        for (int i = 0; i < jsonArray.length(); i++) {

                            objJson = jsonArray.getJSONObject(i);

                            listcnt.add(objJson.get("TabType").toString());
                            listtottabcount.add(objJson.get("TabCount").toString());

                            String medicinename = objJson.get("InjectionName").toString();

                            list.add(medicinename);

                            String morningvalue, afternoonvalue, eveningvalue, nightvalue;

                            String values[] = medicinename.split("_");

                            String medicine_values = values[1].trim();

                            int mindex = medicine_values.indexOf("M");
                            int aindex = medicine_values.indexOf("A");
                            int eindex = medicine_values.indexOf("E");
                            int nindex = medicine_values.indexOf("N");


                            morningvalue = medicine_values.substring(mindex + 2, aindex - 1);
                            afternoonvalue = medicine_values.substring(aindex + 2, eindex - 1);
                            eveningvalue = medicine_values.substring(eindex + 2, nindex - 1);
                            nightvalue = medicine_values.substring(nindex + 2, medicine_values.length() - 1);


                            MedicineGetSet obj = new MedicineGetSet();
                            obj.setMedicine_Name(values[0]);
                            obj.setMorning_Value(session_set1 + "("
                                    + morningvalue + ")");
                            obj.setAfternoon_Value(session_set2 + "("
                                    + afternoonvalue + ")");
                            obj.setEvening_Value(session_set3 + "("
                                    + eveningvalue + ")");
                            obj.setNight_Value(session_set4 + "("
                                    + nightvalue + ")");
                            obj.setIntake_Value(values[2]);
                            obj.setDays_Value(values[3]);


                            medicineGetSets.add(obj);

                        }

                        template_no.setText(c.getString(c.getColumnIndex("TemplateId")));
                        temp_name = c.getString(c.getColumnIndex("TemplateName"));


                    } while (c.moveToNext());
                }

                save_btn.setText("Update Template");
                template_name.setText(temp_name);

            }

            ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            listview.setAdapter(listadapter);

            linearLayoutManager = new LinearLayoutManager(this);
            recyler_view.setLayoutManager(linearLayoutManager);

            medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, recyler_view);

            recyler_view.setAdapter(medicineRecylerAdapter);

            db.close();
            c.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getinitliaze() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        back = (ImageView) toolbar.findViewById(R.id.ic_back);
        home = (ImageView) toolbar.findViewById(R.id.ic_home);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);

        back.setOnClickListener(this);
        home.setOnClickListener(this);
        exit.setOnClickListener(this);

        recyler_view = (RecyclerView) findViewById(R.id.recyler_view);
        //for syrup
        syrupM1 = (Spinner) findViewById(R.id.syrup1);
        syrupA2 = (Spinner) findViewById(R.id.syrup2);
        syrupE3 = (Spinner) findViewById(R.id.syrup3);
        syrupN4 = (Spinner) findViewById(R.id.syrup4);

        optionfreq = (LinearLayout) findViewById(R.id.option_frequency);

        // //////////////////////////////////////////////////////////////////////////////////////////
        // Setting title colour
        title_templatename = (TextView) findViewById(R.id.TextView001);
        String first = "Template Name";
        String next = "<font color='#EE0000'><b>*</b></font>";
        title_templatename.setText(Html.fromHtml(first + next));

        title_medicinename = (TextView) findViewById(R.id.TextView002);
        String first1 = "Medicine Name";
        String next1 = "<font color='#EE0000'><b>*</b></font>";
        title_medicinename.setText(Html.fromHtml(first1 + next1));

        title_frequency = (TextView) findViewById(R.id.TextView003);
        String first2 = "Frequency";
        String next2 = "<font color='#EE0000'><b>*</b></font>";
        title_frequency.setText(Html.fromHtml(first2 + next2));

        // //////////////////////////////////////////////////////////////////////////////////////////
        // TODO Auto-generated method stub
        template_name = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        medicine_name = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        medicine_name.setThreshold(1);

        medicine_name.setThreshold(1);


        medicine_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (medicine_name.getText().toString().length() > 0) {
                    String Query = "select distinct dvalue from (SELECT id,(CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims  order by id) as temp order by dvalue";
                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, templates_addnew.this, medicine_name, charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        template_no = (TextView) findViewById(R.id.textView2);

        et_mrng = (EditText) findViewById(R.id.editText3);
        et_aftn = (EditText) findViewById(R.id.EditText01);
        et_eve = (EditText) findViewById(R.id.EditText02);
        et_night = (EditText) findViewById(R.id.EditText03);

        duration = (Spinner) findViewById(R.id.spinner1);

        after_food = (RadioButton) findViewById(R.id.radioButton1);
        before_food = (RadioButton) findViewById(R.id.RadioButton01);
        with_food = (RadioButton) findViewById(R.id.radioButton2);

        add_btn = (Button) findViewById(R.id.Add);
        save_btn = (Button) findViewById(R.id.Button01);
        cancel_btn = (Button) findViewById(R.id.Button02);

        listview = (ListView) findViewById(R.id.list);


        chk_all = (CheckBox) findViewById(R.id.checkBox5);
        chk_morng = (CheckBox) findViewById(R.id.checkBox1);
        chk_aftr = (CheckBox) findViewById(R.id.checkBox2);
        chk_eve = (CheckBox) findViewById(R.id.checkBox3);
        chk_nite = (CheckBox) findViewById(R.id.checkBox4);


        contrlistener();
        LoadDuration();


    }

    private void LoadTempId() {
        // TODO Auto-generated method stub
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery(
                "select tnum from TemplateMax;",
                null);

        List<String> list = new ArrayList<String>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    template_no.setText(c.getString(c.getColumnIndex("tnum")));


                } while (c.moveToNext());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);


        db.close();
        c.close();
    }
    // ////////////////////////////////////////////////////////////////////////////////////////////////

    public void onChecked(View view) {
        // Is the button now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.checkBox5:
                if (checked) {
                    chk_morng.setChecked(true);
                    chk_aftr.setChecked(true);
                    chk_eve.setChecked(true);
                    chk_nite.setChecked(true);
                    et_mrng.setEnabled(true);

                    et_aftn.setEnabled(true);

                    et_eve.setEnabled(true);

                    et_night.setEnabled(true);


                    syrupM1.setEnabled(true);

                    syrupA2.setEnabled(true);

                    syrupE3.setEnabled(true);

                    syrupN4.setEnabled(true);
                } else {
                    chk_morng.setChecked(false);
                    chk_aftr.setChecked(false);
                    chk_eve.setChecked(false);
                    chk_nite.setChecked(false);

                    et_mrng.setEnabled(false);
                    et_mrng.setText("1");
                    et_aftn.setEnabled(false);
                    et_aftn.setText("1");
                    et_eve.setEnabled(false);
                    et_eve.setText("1");
                    et_night.setEnabled(false);
                    et_night.setText("1");

                    syrupM1.setEnabled(false);
                    syrupM1.setSelection(1);

                    syrupA2.setEnabled(false);
                    syrupA2.setSelection(1);

                    syrupE3.setEnabled(false);
                    syrupE3.setSelection(1);

                    syrupN4.setEnabled(false);
                    syrupN4.setSelection(1);
                }
                break;
            case R.id.checkBox1:
                if (checked) {
                    et_mrng.setEnabled(true);
                    syrupM1.setEnabled(true);
                } else {
                    chk_all.setChecked(false);
                    et_mrng.setEnabled(false);
                    et_mrng.setText("1");
                    syrupM1.setEnabled(false);
                    syrupM1.setSelection(1);
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    et_aftn.setEnabled(true);
                    syrupA2.setEnabled(true);
                } else {
                    chk_all.setChecked(false);
                    et_aftn.setEnabled(false);
                    et_aftn.setText("1");
                    syrupA2.setEnabled(false);
                    syrupA2.setSelection(1);
                }
                break;
            case R.id.checkBox3:
                if (checked) {
                    et_eve.setEnabled(true);
                    syrupE3.setEnabled(true);
                } else {
                    chk_all.setChecked(false);
                    et_eve.setEnabled(false);
                    et_eve.setText("1");
                    syrupE3.setEnabled(false);
                    syrupE3.setSelection(1);
                }
                break;
            case R.id.checkBox4:
                if (checked) {
                    et_night.setEnabled(true);
                    syrupN4.setEnabled(true);
                } else {
                    chk_all.setChecked(false);
                    et_night.setEnabled(false);
                    et_night.setText("1");
                    syrupN4.setEnabled(false);
                    syrupN4.setSelection(1);
                }
                break;
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void contrlistener() {


        syrupM1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(MedicinePrescription.this, String.valueOf(syrupM1.getSelectedItemPosition()).toString() , 25).show();

                if (syrupA2.getSelectedItemPosition() < 1 || syrupE3.getSelectedItemPosition() < 1 || syrupN4.getSelectedItemPosition() < 1) {
                    syrupA2.setSelection(syrupM1.getSelectedItemPosition());
                    syrupE3.setSelection(syrupM1.getSelectedItemPosition());
                    syrupN4.setSelection(syrupM1.getSelectedItemPosition());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        syrupA2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (syrupM1.getSelectedItemPosition() < 1 || syrupE3.getSelectedItemPosition() < 1 || syrupN4.getSelectedItemPosition() < 1) {
                    syrupM1.setSelection(syrupA2.getSelectedItemPosition());
                    syrupE3.setSelection(syrupA2.getSelectedItemPosition());
                    syrupN4.setSelection(syrupA2.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        syrupE3.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (syrupM1.getSelectedItemPosition() < 1 || syrupA2.getSelectedItemPosition() < 1 || syrupN4.getSelectedItemPosition() < 1) {
                    syrupM1.setSelection(syrupE3.getSelectedItemPosition());
                    syrupA2.setSelection(syrupE3.getSelectedItemPosition());
                    syrupN4.setSelection(syrupE3.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        syrupN4.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (syrupM1.getSelectedItemPosition() < 1 && syrupA2.getSelectedItemPosition() < 1 && syrupE3.getSelectedItemPosition() < 1) {
                    syrupM1.setSelection(syrupN4.getSelectedItemPosition());
                    syrupA2.setSelection(syrupN4.getSelectedItemPosition());
                    syrupE3.setSelection(syrupN4.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });


        medicine_name.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) { // TODO Auto-generated method stub

                try {
                    String[] chktxt = medicine_name.getText().toString().split("_");

                    SQLiteDatabase db = openOrCreateDatabase(BaseConfig.AppDatabaseName,
                            SQLiteDatabase.CREATE_IF_NECESSARY, null);


                    //Toast.makeText(templates_addnew.this, medicine_name.getText().toString(), 25).show();

                    Cursor c = db.rawQuery("select distinct MEDICINEINTAKETYPE from cims where marketnameofdrug='" + chktxt[0] + "'", null);

                    if (c != null) {
                        if (c.moveToFirst()) {


                            //Toast.makeText(templates_addnew.this, c.getString(c.getColumnIndex("MEDICINEINTAKETYPE")), 25).show();

                            String iconchange = (c.getString(c.getColumnIndex("MEDICINEINTAKETYPE")).trim());


                            tablet_type = iconchange;

                            switch (iconchange) {
                                case "TAB":

                                    syrupM1.setVisibility(View.GONE);
                                    syrupA2.setVisibility(View.GONE);
                                    syrupE3.setVisibility(View.GONE);
                                    syrupN4.setVisibility(View.GONE);


                                    et_mrng.setVisibility(View.VISIBLE);
                                    et_aftn.setVisibility(View.VISIBLE);
                                    et_eve.setVisibility(View.VISIBLE);
                                    et_night.setVisibility(View.VISIBLE);

                                    break;

                                case "SYR":

                                    syrupM1.setVisibility(View.VISIBLE);
                                    syrupA2.setVisibility(View.VISIBLE);
                                    syrupE3.setVisibility(View.VISIBLE);
                                    syrupN4.setVisibility(View.VISIBLE);

                                    syrupM1.setEnabled(false);
                                    syrupA2.setEnabled(false);
                                    syrupE3.setEnabled(false);
                                    syrupN4.setEnabled(false);

                                    et_mrng.setVisibility(View.GONE);
                                    et_aftn.setVisibility(View.GONE);
                                    et_eve.setVisibility(View.GONE);
                                    et_night.setVisibility(View.GONE);

                                    break;

                                case "OINT":

                                    syrupM1.setVisibility(View.GONE);
                                    syrupA2.setVisibility(View.GONE);
                                    syrupE3.setVisibility(View.GONE);
                                    syrupN4.setVisibility(View.GONE);


                                    et_mrng.setVisibility(View.GONE);
                                    et_aftn.setVisibility(View.GONE);
                                    et_eve.setVisibility(View.GONE);
                                    et_night.setVisibility(View.GONE);

                                    optionfreq.setVisibility(View.GONE);

                                    break;

                                case "CAP":
                                    syrupM1.setVisibility(View.GONE);
                                    syrupA2.setVisibility(View.GONE);
                                    syrupE3.setVisibility(View.GONE);
                                    syrupN4.setVisibility(View.GONE);


                                    et_mrng.setVisibility(View.VISIBLE);
                                    et_aftn.setVisibility(View.VISIBLE);
                                    et_eve.setVisibility(View.VISIBLE);
                                    et_night.setVisibility(View.VISIBLE);

                                    break;

                                case "INJ":


                                    break;
                            }

                        }
                    }

                    c.close();
                    db.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        save_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if (template_name.getText().toString().length() > 0) {
                    savelocal();
                } else {
                    Toast.makeText(templates_addnew.this, "Enter template name..", Toast.LENGTH_LONG).show();
                    template_name.setError("Required");
                }


            }
        });


        cancel_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                templates_addnew.this.finish();
                Intent intent = new Intent(v.getContext(), templates_list.class);
                startActivity(intent);

            }
        });


        add_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                //changing the layout for prescription
                if (!tablet_type.trim().equalsIgnoreCase("")) {
                    if (tablet_type.equalsIgnoreCase("TAB")) {
                        AddListItem(1);

                    } else if (tablet_type.equalsIgnoreCase("CAP")) {
                        AddListItem(2);
                    } else if (tablet_type.equalsIgnoreCase("SYR")) {
                        AddListItemSyrup();

                    } else if (tablet_type.equalsIgnoreCase("OINT")) {
                        AddListItemOINT();
                    } else {
                        AddListItem(1);
                    }

                } else {
                    AddListItem(1);

                }


            }
        });

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //ConfirmDelete(position);

            }
        });

        et_mrng.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (et_mrng.isFocused()) {
                    et_mrng.setText(null);
                    et_mrng.requestFocus();

                }

            }
        });
        et_aftn.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (et_aftn.isFocused()) {
                    et_aftn.setText(null);
                    et_aftn.requestFocus();

                }

            }
        });
        et_eve.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (et_eve.isFocused()) {
                    et_eve.setText(null);
                    et_eve.requestFocus();

                }

            }
        });
        et_night.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (et_night.isFocused()) {
                    et_night.setText(null);
                    et_night.requestFocus();

                }

            }
        });
    }

    protected void ConfirmDelete(final int position, final RecyclerView recyclerView) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Do you want to Delete?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                medicineGetSets.remove(position);
                list.remove(position);

                ArrayAdapter<String> listadapter = new ArrayAdapter<>(recyclerView.getContext(), android.R.layout.simple_list_item_1, list);
                listview.setAdapter(listadapter);

                medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, recyclerView);
                recyclerView.setAdapter(medicineRecylerAdapter);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean IsCheckBoxChecked() {
        return chk_morng.isChecked() | chk_aftr.isChecked() | chk_eve.isChecked() | chk_nite.isChecked();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////


    @SuppressWarnings("StringConcatenationInLoop")
    public void AddListItem(int id) {
        // sb.append(testname.getText());
        // sb.append(" - ");
        // sb.append(remarks.getSelectedItem().toString());
        // sb.append(" - ");

        // list.clear();
        if (BaseConfig.CheckTextView(template_name)) {
            if (BaseConfig.CheckTextView(medicine_name)) {
                // if(BaseConfig.CheckSpinner(dose))
                // {

                if (BaseConfig.CheckTextView(et_mrng) && BaseConfig.CheckTextView(et_aftn) && BaseConfig.CheckTextView(et_eve) && BaseConfig.CheckTextView(et_night)) {
                    if (IsCheckBoxChecked()) {

                        if (BaseConfig.CheckSpinner(duration)) {
                            // if(BaseConfig.CheckSpinner(remarks))
                            // {
                            String remrks = null;
                            if (after_food.isChecked()) {
                                remrks = "After Food";

                            } else if (before_food.isChecked()) {
                                remrks = "Before Food";
                            } else if (with_food.isChecked()) {
                                remrks = "With Food";
                            }
                            String session1 = null, session2 = null, session3 = null, session4 = null;
                            String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";

                            if ((chk_morng.isChecked()) && (et_mrng.getText().length() >= 0)) {
                                session1 = chk_morng.getText().toString();
                                session_set1 = session1.substring(0, 1);
                            } else {
                                // session_set1 = "";
                                et_mrng.setText("0");
                            }

                            if (chk_aftr.isChecked()) {
                                session2 = chk_aftr.getText().toString();
                                session_set2 = session2.substring(0, 1);
                            } else {
                                // session_set2 = "";
                                et_aftn.setText("0");
                            }

                            if (chk_eve.isChecked()) {
                                session3 = chk_eve.getText().toString();
                                session_set3 = session3.substring(0, 1);
                            } else {
                                // session_set3 = "";
                                et_eve.setText("0");
                            }
                            if (chk_nite.isChecked()) {
                                session4 = chk_nite.getText().toString();
                                session_set4 = session4.substring(0, 1);
                            } else {
                                // session_set4 = "";
                                et_night.setText("0");
                            }


                            if (checklistforMedicineAddedduplicate(medicine_name.getText().toString())) {

                                BaseConfig.template_name = template_name.getText().toString();

                                list.add("[" + medicine_name.getText().toString() + "]  _  "

                                        + session_set1 + "("
                                        + et_mrng.getText().toString() + ")"

                                        + session_set2 + "("
                                        + et_aftn.getText().toString() + ")"

                                        + session_set3 + "("
                                        + et_eve.getText().toString() + ")"

                                        + session_set4 + "("
                                        + et_night.getText().toString() + ")"

                                        + "  _ " + remrks
                                        + "  _   "
                                        + duration.getSelectedItem().toString());
                                ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

                                listview.setAdapter(listadapter);

                                // TODO: 4/19/2017 Insert Array List and add Recyler ListView

                                MedicineGetSet obj = new MedicineGetSet();
                                obj.setMedicine_Name(medicine_name.getText().toString());
                                obj.setMorning_Value(session_set1 + "("
                                        + et_mrng.getText().toString() + ")");
                                obj.setAfternoon_Value(session_set2 + "("
                                        + et_aftn.getText().toString() + ")");
                                obj.setEvening_Value(session_set3 + "("
                                        + et_eve.getText().toString() + ")");
                                obj.setNight_Value(session_set4 + "("
                                        + et_night.getText().toString() + ")");
                                obj.setIntake_Value(remrks);
                                obj.setDays_Value(duration.getSelectedItem().toString());


                                medicineGetSets.add(obj);


                                // use this setting to improve performance if you know that changes
                                // in content do not change the layout size of the RecyclerView
                                //recyclerview.setHasFixedSize(true);

                                // use a linear layout manager
                                linearLayoutManager = new LinearLayoutManager(this);
                                recyler_view.setLayoutManager(linearLayoutManager);

                                medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, recyler_view);

                                recyler_view.setAdapter(medicineRecylerAdapter);


                                //for checking whether it is tablet
                                /*if 1 na tablet
                                 * if 2 na syrup
								 * if 3 na oinment
								 * */

                                if (id == 1) {
                                    listcnt.add("1-TAB");
                                } else if (id == 2) {
                                    listcnt.add("4-CAP");
                                }




								/*et_mrng.getText().toString();//0.5=1
                                et_aftn.getText().toString();
								et_eve.getText().toString();
								et_night.getText().toString();*/

                                //For calculating tablet count
                                //For calculating tablet count

                                int durcnt = 0;
                                String durstr = "";
                                int imonth = 30;
                                int flag = 0;
                                int tottab = 0;

                                Double m1 = Double.parseDouble(et_mrng.getText().toString());
                                Double m2 = Double.parseDouble(et_aftn.getText().toString());
                                Double m3 = Double.parseDouble(et_eve.getText().toString());
                                Double m4 = Double.parseDouble(et_night.getText().toString());

                                int testval1 = (int) Math.round(m1);
                                int testval2 = (int) Math.round(m2);
                                int testval3 = (int) Math.round(m3);
                                int testval4 = (int) Math.round(m4);


                                tottab = testval1 + testval2 + testval3 + testval4;


                                for (int i = 0; i < duration.getSelectedItem().toString().length(); i++) {

                                    String tablets = (String.valueOf(duration.getSelectedItem().toString().charAt(i)));
                                    try {
                                        if (tablets.equals("M")) {
                                            flag = 1;
                                        } else if (tablets.equals("T")) {
                                            flag = 2;
                                        }
                                        durcnt = Integer.parseInt(tablets);

                                        //noinspection StringConcatenationInLoop
                                        durstr += tablets;

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                }


                                if (flag == 1) {
                                    // tottab+=tabletval;
                                    durcnt = Integer.parseInt(durstr);
                                    durcnt = tottab * imonth * durcnt;
                                    //Toast.makeText(templates_addnew.this, tottab+"/"+imonth+"/"+durcnt, 35).show();
                                    tottab = 0;
                                } else if (flag == 2) {
                                    durcnt = 30;
                                    tottab = 0;
                                } else {
                                    durcnt = Integer.parseInt(durstr);
                                    durcnt = tottab * durcnt;
                                    //Toast.makeText(templates_addnew.this, tottab+"/"+durcnt, 35).show();
                                    tottab = 0;

                                }


                                listtottabcount.add(String.valueOf(durcnt));

                                //Toast.makeText(templates_addnew.this, durstr +"\n"+durcnt , 50).show();


                                medicine_name.setText(null);
                                duration.setSelection(0);
                                et_mrng.setText("1");
                                et_aftn.setText("1");
                                et_eve.setText("1");
                                et_night.setText("1");
                                et_mrng.setEnabled(false);
                                et_aftn.setEnabled(false);
                                et_eve.setEnabled(false);
                                et_night.setEnabled(false);
                                chk_all.setChecked(false);
                                chk_morng.setChecked(false);
                                chk_aftr.setChecked(false);
                                chk_eve.setChecked(false);
                                chk_nite.setChecked(false);
                                after_food.setChecked(true);
                                before_food.setChecked(false);
                                with_food.setChecked(false);
                                medicine_name.requestFocus();
                                duration.setSelection(0);

                            } else {
                                showSimplePopUp("Selected Medicine Already Added...");
                            }

                        } else {
                            showSimplePopUp("Select Duration");
                            duration.requestFocus();
                        }
                    } else {
                        showSimplePopUp("Select Frequency");

                    }

                } else {
                    showSimplePopUp("Check Frequency");
                }
            } else {
                showSimplePopUp("Enter Medicine Name");
                medicine_name.requestFocus();
            }
        } else {
            showSimplePopUp("Enter Template Name");
            template_name.requestFocus();
            template_name.setError("Required");
        }


    }


    public void AddListItemSyrup() {
        // sb.append(testname.getText());
        // sb.append(" - ");
        // sb.append(remarks.getSelectedItem().toString());
        // sb.append(" - ");


        if (BaseConfig.CheckTextView(medicine_name)) {
            // if(BaseConfig.CheckSpinner(dose))
            // {

            if (BaseConfig.CheckSpinner(syrupM1) && BaseConfig.CheckSpinner(syrupA2) && BaseConfig.CheckSpinner(syrupE3) && BaseConfig.CheckSpinner(syrupN4)) {


                if (IsCheckBoxChecked()) {

                    if (BaseConfig.CheckSpinner(duration)) {
                        // if(BaseConfig.CheckSpinner(remarks))
                        // {
                        String remrks = null;
                        if (after_food.isChecked()) {
                            remrks = "After Food";

                        } else if (before_food.isChecked()) {
                            remrks = "Before Food";
                        } else if (with_food.isChecked()) {
                            remrks = "With Food";
                        }
                        String session1 = null, session2 = null, session3 = null, session4 = null;

                        String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";


                        String syrmorning = "", syrafternn = "", syreve = "", syrnight = "";

                        if (syrupM1.getSelectedItemPosition() > 0) {
                            syrmorning = syrupM1.getSelectedItem().toString();
                        }

                        if (syrupA2.getSelectedItemPosition() > 0) {
                            syrafternn = syrupA2.getSelectedItem().toString();
                        }

                        if (syrupE3.getSelectedItemPosition() > 0) {
                            syreve = syrupE3.getSelectedItem().toString();
                        }

                        if (syrupN4.getSelectedItemPosition() > 0) {
                            syrnight = syrupN4.getSelectedItem().toString();
                        }


                        if ((chk_morng.isChecked()) && (syrupM1.getSelectedItemPosition() >= 0)) {
                            session1 = chk_morng.getText().toString();
                            session_set1 = session1.substring(0, 1);
                        } else {
                            // session_set1 = "";
                            //et_mrng.setText("0");
                            syrmorning = "0";

                        }

                        if (chk_aftr.isChecked()) {
                            session2 = chk_aftr.getText().toString();
                            session_set2 = session2.substring(0, 1);
                        } else {
                            // session_set2 = "";
                            //et_aftn.setText("0");
                            syrafternn = "0";
                        }

                        if (chk_eve.isChecked()) {
                            session3 = chk_eve.getText().toString();
                            session_set3 = session3.substring(0, 1);
                        } else {
                            // session_set3 = "";
                            //et_eve.setText("0");
                            syreve = "0";
                        }
                        if (chk_nite.isChecked()) {
                            session4 = chk_nite.getText().toString();
                            session_set4 = session4.substring(0, 1);
                        } else {
                            // session_set4 = "";
                            //et_night.setText("0");
                            syrnight = "0";
                        }


                        if (checklistforMedicineAddedduplicate(medicine_name.getText().toString())) {

                            BaseConfig.template_name = template_name.getText().toString();

                            list.add("[" + medicine_name.getText().toString() + "]  _  "

                                    + session_set1 + "("
                                    + syrmorning + ")"

                                    + session_set2 + "("
                                    + syrafternn + ")"

                                    + session_set3 + "("
                                    + syreve + ")"

                                    + session_set4 + "("
                                    + syrnight + ")"

                                    + "  _ " + remrks
                                    + "  _   "
                                    + duration.getSelectedItem().toString());
                            ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

                            listview.setAdapter(listadapter);


                            //for checking whether it is tablet
							/*if 1 na tablet
							 * if 2 na syrup
							 * if 3 na oinment
							 * */

                            listcnt.add("2-SYR");
                            listtottabcount.add("1");

                            medicine_name.setText(null);
                            duration.setSelection(0);

                            syrupM1.setSelection(0);
                            syrupA2.setSelection(0);
                            syrupE3.setSelection(0);
                            syrupN4.setSelection(0);

                            et_mrng.setEnabled(false);
                            et_aftn.setEnabled(false);
                            et_eve.setEnabled(false);
                            et_night.setEnabled(false);

                            chk_all.setChecked(false);
                            chk_morng.setChecked(false);
                            chk_aftr.setChecked(false);
                            chk_eve.setChecked(false);
                            chk_nite.setChecked(false);

                            et_mrng.setVisibility(View.VISIBLE);
                            et_aftn.setVisibility(View.VISIBLE);
                            et_eve.setVisibility(View.VISIBLE);
                            et_night.setVisibility(View.VISIBLE);

                            syrupM1.setVisibility(View.GONE);
                            syrupA2.setVisibility(View.GONE);
                            syrupE3.setVisibility(View.GONE);
                            syrupN4.setVisibility(View.GONE);


                            after_food.setChecked(true);
                            before_food.setChecked(false);
                            with_food.setChecked(false);

                            medicine_name.requestFocus();
                            duration.setSelection(0);

                        } else {
                            showSimplePopUp("Selected Medicine Already Added...");
                        }

                    } else {
                        showSimplePopUp("Select Duration");
                        duration.requestFocus();
                    }
                } else {
                    showSimplePopUp("Select Frequency");

                }
				/*}
			}*/

            } else {
                showSimplePopUp("Check Frequency");
            }

        } else {
            showSimplePopUp("Enter Medicine Name");
            medicine_name.requestFocus();
        }

    }


    public void AddListItemOINT() {
        if (BaseConfig.CheckTextView(medicine_name)) {

            //if (BaseConfig.CheckSpinner(syrupM1)&& BaseConfig.CheckSpinner(syrupA2)&& BaseConfig.CheckSpinner(syrupE3)&& BaseConfig.CheckSpinner(syrupN4))
            //{
            if (IsCheckBoxChecked()) {

                if (BaseConfig.CheckSpinner(duration)) {
                    // if(BaseConfig.CheckSpinner(remarks))
                    // {

                    String remrks = null;
                    if (after_food.isChecked()) {
                        remrks = "After Food";

                    } else if (before_food.isChecked()) {
                        remrks = "Before Food";
                    } else if (with_food.isChecked()) {
                        remrks = "With Food";
                    }

                    String session1 = null, session2 = null, session3 = null, session4 = null;
                    String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";
                    String s1 = "0", s2 = "0", s3 = "0", s4 = "0";

                    if ((chk_morng.isChecked()) && (et_mrng.getText().length() >= 0)) {
                        session1 = chk_morng.getText().toString();
                        session_set1 = session1.substring(0, 1);
                        s1 = "Apply";
                    } else {
                        // session_set1 = "";
                        et_mrng.setText("0");
                    }

                    if (chk_aftr.isChecked()) {
                        session2 = chk_aftr.getText().toString();
                        session_set2 = session2.substring(0, 1);
                        s2 = "Apply";
                    } else {
                        // session_set2 = "";
                        et_aftn.setText("0");
                    }

                    if (chk_eve.isChecked()) {
                        session3 = chk_eve.getText().toString();
                        session_set3 = session3.substring(0, 1);
                        s3 = "Apply";
                    } else {
                        // session_set3 = "";
                        et_eve.setText("0");
                    }
                    if (chk_nite.isChecked()) {
                        session4 = chk_nite.getText().toString();
                        session_set4 = session4.substring(0, 1);
                        s4 = "Apply";
                    } else {
                        // session_set4 = "";
                        et_night.setText("0");
                    }


                    if (checklistforMedicineAddedduplicate(medicine_name.getText().toString())) {

                        BaseConfig.template_name = template_name.getText().toString();

                        list.add("[" + medicine_name.getText().toString() + "]  _  "

                                + session_set1 + "("
                                + s1 + ")"

                                + session_set2 + "("
                                + s2 + ")"

                                + session_set3 + "("
                                + s3 + ")"

                                + session_set4 + "("
                                + s4 + ")"

                                + "  _   "
                                + duration.getSelectedItem().toString());
                        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

                        listview.setAdapter(listadapter);


                        //for checking whether it is tablet
						/*if 1 na tablet
						 * if 2 na syrup
						 * if 3 na oinment
						 * */

                        listcnt.add("3-OINT");
                        listtottabcount.add("1");

                        medicine_name.setText(null);
                        duration.setSelection(0);

                        et_mrng.setText("1");
                        et_aftn.setText("1");
                        et_eve.setText("1");
                        et_night.setText("1");

                        et_mrng.setEnabled(false);
                        et_aftn.setEnabled(false);
                        et_eve.setEnabled(false);
                        et_night.setEnabled(false);

                        chk_all.setChecked(false);
                        chk_morng.setChecked(false);
                        chk_aftr.setChecked(false);
                        chk_eve.setChecked(false);
                        chk_nite.setChecked(false);

                        after_food.setChecked(true);
                        before_food.setChecked(false);
                        with_food.setChecked(false);

                        optionfreq.setVisibility(View.VISIBLE);
                        et_mrng.setVisibility(View.VISIBLE);
                        et_aftn.setVisibility(View.VISIBLE);
                        et_eve.setVisibility(View.VISIBLE);
                        et_night.setVisibility(View.VISIBLE);

                        medicine_name.requestFocus();
                        duration.setSelection(0);

                    } else {
                        showSimplePopUp("Selected Medicine Already Added...");
                    }

                } else {
                    showSimplePopUp("Select Duration");
                    duration.requestFocus();
                }
            } else {
                showSimplePopUp("Select Frequency");

            }

			/*} else {
				showSimplePopUp("Check Frequency");
			}*/
        } else {
            showSimplePopUp("Enter Medicine Name");
            medicine_name.requestFocus();
        }

    }


    // /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showSimplePopUp(String msg) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Information");
        helpBuilder.setMessage(msg);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();


    }

    public boolean checklistforMedicineAddedduplicate(String medicine) {
        int flagm = 1;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                String[] addm = list.get(i).split("_");

                if (addm[0].trim()
                        .equalsIgnoreCase("[" + medicine.trim() + "]".trim())
                        )


                {
                    flagm = 0;
                    break;
                }

            }

        }

        return flagm != 0;
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////
    // Boolen Return Status
    public boolean LoadBooleanStatusCount(String Query) {
        try {
            int havcount = 0;


            SQLiteDatabase database = BaseConfig.GetDb();
            Cursor c = database.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String Status = c.getString(c.getColumnIndex("dstatus"));

                        if (Status.split(":")[1].trim().equalsIgnoreCase(BaseConfig.template_name)) {
                            havcount++;
                        }

                    } while (c.moveToNext());
                }
            }

            c.close();
            database.close();

            return havcount > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////
    public void savelocal() {


        if (checkValidation()) {

            if (BaseConfig.temp_flag.equalsIgnoreCase("true")) {

                String Query = "select TemplateName as dstatus1 from TemplateDtls where UPPER(TemplateName)='" + template_name.getText().toString().toUpperCase() + "';";

                boolean status = BaseConfig.LoadReportsBooleanStatus(Query);

                if (!status) {
                    submitForm();

                } else {
                    Toast.makeText(templates_addnew.this, "Template name already exists!", Toast.LENGTH_LONG).show();
                    template_name.setError("Duplicate - change a new template name");
                }

            } else {
                submitForm1();

            }

        } else {
            Toast.makeText(templates_addnew.this, "Add any template name and drug", Toast.LENGTH_LONG).show();
            template_name.setError("Required");
            medicine_name.setError("Required");
        }


    }

    ////////////////////////////////////
    private void submitForm() {
        // TODO Auto-generated method stub


        try {
            SQLiteDatabase db = BaseConfig.GetDb();
            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.ENGLISH);
            Date date = new Date();
            String dttm = dateformt.format(date);
            String tid = GetTempId();
            ContentValues contentValue;

            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();


            for (int j = 0; j < list.size(); j++) {
                from_db_obj = new JSONObject();
                from_db_obj.put("InjectionName", list.get(j));
                from_db_obj.put("TabType", listcnt.get(j));
                from_db_obj.put("TabCount", listtottabcount.get(j));
                export_jsonarray.put(from_db_obj);
            }


            contentValue = new ContentValues();
            contentValue.put("Docid", BaseConfig.doctorId);
            contentValue.put("TemplateId", "Temp" + tid);
            contentValue.put("TemplateName", template_name.getText().toString().toUpperCase());
            contentValue.put("MedicineName", export_jsonarray.toString());
            contentValue.put("dt", dttm);
            contentValue.put("Isupdate", "0");
            db.insert("TemplateDtls", null, contentValue);


            final String Update_Query = "update TemplateMax set tnum=tnum+1";
            db.execSQL(Update_Query);

            db.close();

            showSimplePopUpExit("Medicine Template Saved Successfully");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////

    private void submitForm1() {
        try {
            // TODO Auto-generated method stub
            SQLiteDatabase db = BaseConfig.GetDb();
            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dttm = dateformt.format(date);
            ContentValues contentValue;

            final String Delete_Query_Mpre = "Delete from TemplateDtls where TemplateName='" + template_name.getText().toString() + "';";

            BaseConfig.SaveData(Delete_Query_Mpre);

            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();


            for (int j = 0; j < list.size(); j++) {
                from_db_obj = new JSONObject();
                from_db_obj.put("InjectionName", list.get(j));
                from_db_obj.put("TabType", listcnt.get(j));
                from_db_obj.put("TabCount", listtottabcount.get(j));
                export_jsonarray.put(from_db_obj);
            }

            contentValue = new ContentValues();
            contentValue.put("Docid", BaseConfig.doctorId);
            contentValue.put("TemplateId", template_no.getText().toString());
            contentValue.put("TemplateName", template_name.getText().toString());
            contentValue.put("MedicineName", export_jsonarray.toString());
            contentValue.put("dt", dttm);
            db.insert("TemplateDtls", null, contentValue);


            db.close();
            showSimplePopUpExit("Medicine Template Updated Successfully");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String GetTempId() {
        String pid = null;
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery("select tnum from TemplateMax;", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    pid = c.getString(c.getColumnIndex("tnum"));

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        if (pid != null) {
            return pid;
        } else {
            return null;
        }

    }

    // ///////////////////////////////////////////////////////////////////////////////////////
    private void showSimplePopUpExit(String msg) {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Information");
        helpBuilder.setCancelable(false);
        //helpBuilder.setIcon(R.drawable.submitted_icon);
        helpBuilder.setMessage(msg);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        templates_addnew.this.finish();

                    }
                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();


    }

    // ///////////////////////////////////////////////////////////////////////
    private boolean checkValidation() {

        return listview.getCount() > 0;

    }

    //////////////////////////////////////////////////////////////////////
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    before_food.setChecked(false);
                with_food.setChecked(false);
                break;
            case R.id.RadioButton01:
                if (checked)
                    after_food.setChecked(false);
                with_food.setChecked(false);
                break;
            case R.id.radioButton2:
                if (checked)
                    after_food.setChecked(false);
                before_food.setChecked(false);
                break;
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        BaseConfig.temp_flag = "";
        this.finish();
        startActivity(new Intent(this, Masters_New.class));
        BaseConfig.temp_flag = "";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void LoadMedicine() // throws IOException, XmlPullParserException
    {

        SQLiteDatabase db = BaseConfig.GetDb();
		/*Cursor c = BaseConfig.db.rawQuery(
				"select medicine from Medicine where isactive='True' and medicine!='' order by id desc",
				null);*/


        Cursor c = db.rawQuery("select distinct dvalue from (SELECT id,(CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims  order by id) as temp order by dvalue", null);

        List<String> list = new ArrayList<String>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String firstName = c.getString(c.getColumnIndex("dvalue"));
                    list.add(firstName);

                } while (c.moveToNext());
            }
        }


        //BaseConfig.spinner2meth(getApplicationContext(), list, medicine_name);
        BaseConfig.loadSpinner(medicine_name, list);
        medicine_name.setThreshold(2);

        db.close();
        c.close();
    }

    public void LoadDuration() // throws IOException, XmlPullParserException
    {
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery(
                "select distinct duration from Duration;", null);

        List<String> list = new ArrayList<String>();
        list.add("Select Duration");

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String firstName = c
                            .getString(c.getColumnIndex("duration"));
                    list.add(firstName);

                } while (c.moveToNext());
            }
        }


        //BaseConfig.spinner2meth(getApplicationContext(), list, duration);
        BaseConfig.loadSpinner(duration, list);

        db.close();
        c.close();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ic_back:
                this.finish();
                BaseConfig.temp_flag = "";
                break;
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    public class MedicineRecylerAdapter extends RecyclerView.Adapter<MedicineRecylerAdapter.MyViewHolder> {
        ArrayList<MedicineGetSet> medicineGetSets = new ArrayList<>();
        RecyclerView recyclerView;
        MedicineRecylerAdapter medicineRecylerAdapter;

        public MedicineRecylerAdapter(ArrayList<MedicineGetSet> medicineGetSets, RecyclerView recyclerView) {
            this.medicineGetSets = medicineGetSets;
            this.recyclerView = recyclerView;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_medicine, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            MedicineGetSet item = medicineGetSets.get(position);

            holder.medicine_name.setText(item.getMedicine_Name());
            holder.morning.setText(item.getMorning_Value());
            holder.afternoon.setText(item.getAfternoon_Value());
            holder.evening.setText(item.getEvening_Value());
            holder.night.setText(item.getNight_Value());
            holder.intake.setText(item.getIntake_Value());
            holder.duration.setText(item.getDays_Value());
            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ConfirmDelete(position, recyclerView);


                }
            });
        }

        @Override
        public int getItemCount() {
            return medicineGetSets.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView medicine_name, morning, afternoon, evening, night, intake, duration;
            CardView card_view;

            public MyViewHolder(View itemView) {
                super(itemView);
                medicine_name = (TextView) itemView.findViewById(R.id.medicine_name);
                morning = (TextView) itemView.findViewById(R.id.medicine_moring);
                afternoon = (TextView) itemView.findViewById(R.id.medicine_afternoon);
                evening = (TextView) itemView.findViewById(R.id.medicine_eve);
                night = (TextView) itemView.findViewById(R.id.medicine_night);
                intake = (TextView) itemView.findViewById(R.id.medicine_food);
                duration = (TextView) itemView.findViewById(R.id.medicine_days);
                card_view = (CardView) itemView.findViewById(R.id.card_view);

            }
        }

    }


}
