package kdmc_kumar.Inpatient_Module.Inpatient_Charts;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Core_Modules.BaseConfig;

import static displ.mobydocmarathi.com.R.id.chart;


/**
 * Created by Android on 4/9/2017.
 */

public class Chart_Line_Inpatient extends AppCompatActivity {

    private LineChart lineChart;


    /**
     * Created @ Muthukumar & Vidhya
     * 08/04/2017
     */


    //**********************************************************************************************
    private Bundle b;
    private Button Close;
    //**********************************************************************************************
    private String Chart_Id;
    private String Patient_Id;
    private String Patient_Name;
    private String Patient_AgeGender;

    public Chart_Line_Inpatient() {
    }

    //**********************************************************************************************
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.new_line_chart_layout);

        try {
            this.GetInitialize();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    private void GetInitialize() {


        this.lineChart = this.findViewById(chart);
        this.lineChart.setDescription("");
        TextView name = this.findViewById(id.chart_name);
        name.setText("Inpatient - Chart");

        this.Close = this.findViewById(id.cancel);

        TextView pat_id = this.findViewById(id.tv_patient_id);
        TextView pat_name = this.findViewById(id.tv_patient_name);
        TextView pat_age = this.findViewById(id.tv_patient_agegender);


        this.b = this.getIntent().getExtras();

        if (this.b != null) {

            this.Chart_Id = this.b.getString("ID");
            this.Patient_Id = this.b.getString(BaseConfig.BUNDLE_PATIENT_ID);
            this.Patient_Name = this.b.getString("PATIENT_NAME");
            this.Patient_AgeGender = this.b.getString("PATIENT_AGEGENDER");


            String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + this.Patient_Id + '\'');
            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + this.Patient_Id + '\'');

            pat_id.setText(this.Patient_Id);
            pat_name.setText(Patient_Name);
            pat_age.setText(Patient_AgeGender);


        }

        this.Close.setOnClickListener(view -> finish());


        LineData data = new LineData(this.getXAxisValues(this.Chart_Id), this.getDataSet(this.Chart_Id));

        if (data != null) {
            this.lineChart.setData(data);
            this.lineChart.animateXY(2000, 2000);
            this.lineChart.invalidate();

        }


    }
//**********************************************************************************************


    private ArrayList<LineDataSet> getDataSet(String Id) {

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<Entry> valueSet2 = new ArrayList<>();
        ArrayList<Entry> valueSet3 = new ArrayList<>();
        ArrayList<Entry> valueSet4 = new ArrayList<>();
        ArrayList<Entry> valueSet5 = new ArrayList<>();
        ArrayList<Entry> valueSet6 = new ArrayList<>();


        Entry v1, v2, v3, v4, v5, v6;

        LineDataSet lineDataSet1 = null, lineDataSet2 = null, lineDataSet3 = null, lineDataSet4 = null, lineDataSet5 = null, lineDataSet6 = null;

        ArrayList<LineDataSet> dataSets1 = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_Inpatient.this);

        String Query = "select bp,bpd,pulse,temp,resp,IFNULL(spo2,'0')as spo2 from Inpatient_MainChart where patid='" + this.Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    v1 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("bp"))), c.getPosition()); // Jan
                    valueSet1.add(v1);

                    v2 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("bpd"))), c.getPosition()); // Jan
                    valueSet2.add(v2);

                    v3 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("pulse"))), c.getPosition()); // Jan
                    valueSet3.add(v3);

                    v4 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("temp"))), c.getPosition()); // Jan
                    valueSet4.add(v4);

                    v5 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("resp"))), c.getPosition()); // Jan
                    valueSet5.add(v5);

                    v6 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("spo2"))), c.getPosition()); // Jan
                    valueSet6.add(v6);

                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();


        //old code

        LineDataSet lineDataSet11 = new LineDataSet(valueSet1, "BPS");
        lineDataSet11.setColor(Color.rgb(200, 0, 0));
        lineDataSet11.setDrawFilled(true);
        lineDataSet11.setFillColor(Color.rgb(200, 0, 0));//Fill Color
      /*  lineDataSet1.setDrawCubic(true);
        lineDataSet1.setDrawValues(true);

        lineDataSet1.setLineWidth(2f);// Line With
        lineDataSet1.setDrawCircles(true);
*/

        LineDataSet lineDataSet21 = new LineDataSet(valueSet2, "BPD");
        lineDataSet21.setColor(Color.rgb(218, 165, 50));
        lineDataSet21.setDrawFilled(true);
        lineDataSet21.setFillColor(Color.rgb(218, 165, 50));//Fill Color
        /*lineDataSet2.setDrawCubic(true);
        lineDataSet2.setDrawValues(true);

        lineDataSet2.setLineWidth(2f);// Line With
        lineDataSet2.setDrawCircles(true);
*/

        LineDataSet lineDataSet31 = new LineDataSet(valueSet3, "PULSE");
        lineDataSet31.setColor(Color.rgb(150, 165, 32));
        lineDataSet31.setDrawFilled(true);
        lineDataSet31.setFillColor(Color.rgb(150, 165, 32));//Fill Color
        /*
        lineDataSet3.setDrawCubic(true);
        lineDataSet3.setDrawValues(true);

        lineDataSet3.setLineWidth(2f);// Line With
        lineDataSet3.setDrawCircles(true);
*/

        LineDataSet lineDataSet41 = new LineDataSet(valueSet4, "TEMP");
        lineDataSet41.setColor(Color.rgb(218, 150, 32));
        lineDataSet41.setDrawFilled(true);
        lineDataSet41.setFillColor(Color.rgb(218, 150, 32));//Fill Color
      /*  lineDataSet4.setDrawCubic(true);
        lineDataSet4.setDrawValues(true);

        lineDataSet4.setLineWidth(2f);// Line With
        lineDataSet4.setDrawCircles(true);
*/

        LineDataSet lineDataSet51 = new LineDataSet(valueSet5, "RESP");
        lineDataSet51.setColor(Color.rgb(218, 185, 32));
        lineDataSet51.setDrawFilled(true);
        lineDataSet51.setFillColor(Color.rgb(218, 185, 32));//Fill Color
        /*lineDataSet5.setDrawCubic(true);
        lineDataSet5.setDrawValues(true);

        lineDataSet5.setLineWidth(2f);// Line With
        lineDataSet5.setDrawCircles(true);
*/

        LineDataSet lineDataSet61 = new LineDataSet(valueSet6, "SPO2");
        lineDataSet61.setColor(Color.rgb(78, 165, 32));
        lineDataSet61.setDrawFilled(true);
        lineDataSet61.setFillColor(Color.rgb(78, 165, 32));//Fill Color
       /* lineDataSet6.setDrawCubic(true);
        lineDataSet6.setDrawValues(true);

        lineDataSet6.setLineWidth(2f);// Line With
        lineDataSet6.setDrawCircles(true);
*/

        dataSets1.add(lineDataSet11);
        dataSets1.add(lineDataSet21);
        dataSets1.add(lineDataSet31);
        dataSets1.add(lineDataSet41);
        dataSets1.add(lineDataSet51);
        dataSets1.add(lineDataSet61);


        return dataSets1;


    }


    //**********************************************************************************************
    private ArrayList<String> getXAxisValues(String Id) {
        ArrayList<String> xAxis1 = new ArrayList<>();


        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_Inpatient.this);
        String Query = "select  Actdate from Inpatient_MainChart where patid='" + this.Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    xAxis1.add(c.getString(c.getColumnIndex("Actdate")));

                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();
        return xAxis1;
    }
//**********************************************************************************************


}
