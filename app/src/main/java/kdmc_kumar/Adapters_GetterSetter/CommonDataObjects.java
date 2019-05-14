package kdmc_kumar.Adapters_GetterSetter;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Android on 4/6/2017.
 */

public class CommonDataObjects {
    public CommonDataObjects() {
    }

    //**********************************************************************************************
    public static class Fav_MedicineList {

        String Medicine;
        String Id;
        public Fav_MedicineList(String medicine, String id) {
            this.Medicine = medicine;
            this.Id = id;
        }

        public final String getMedicine() {
            return this.Medicine;
        }

        public final void setMedicine(String medicine) {
            this.Medicine = medicine;
        }

        public final String getId() {
            return this.Id;
        }

        public final void setId(String id) {
            this.Id = id;
        }
    }


    //**********************************************************************************************

    public static class Fav_TestList {


        String TestName;
        String SubTestName;
        String Id;

        public Fav_TestList(String id, String testName, String subTestName) {

            this.Id = id;
            this.TestName = testName;
            this.SubTestName = subTestName;
        }

        public final String getTestName() {
            return this.TestName;
        }

        public final void setTestName(String testName) {
            this.TestName = testName;
        }

        public final String getSubTestName() {
            return this.SubTestName;
        }

        public final void setSubTestName(String subTestName) {
            this.SubTestName = subTestName;
        }

        public final String getId() {
            return this.Id;
        }

        public final void setId(String id) {
            this.Id = id;
        }


    }
    //**********************************************************************************************

    static class Item {

        private String title;
        private String description;


        public Item(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public final String getTitle() {
            return this.title;
        }

        public final void setTitle(String title) {
            this.title = title;
        }

        public final String getDescription() {
            return this.description;
        }

        public final void setDescription(String description) {
            this.description = description;
        }


    }
    //**********************************************************************************************

    public static class ObjectDrawerItem {

        public final int icon;
        public final String name;

        // Constructor.
        public ObjectDrawerItem(int icon, String name) {

            this.icon = icon;
            this.name = name;
        }
    }

    //**********************************************************************************************

    /**
     * Online Consulatation Reports
     */
    public static class OnlineConsultationReport {
        String ReportName;
        String ReportPhoto;
        public OnlineConsultationReport(String reportName, String reportPhoto) {
            this.ReportName = reportName;
            this.ReportPhoto = reportPhoto;
        }

        public final String getReportName() {
            return this.ReportName;
        }

        public final void setReportName(String reportName) {
            this.ReportName = reportName;
        }

        public final String getReportPhoto() {
            return this.ReportPhoto;
        }

        public final void setReportPhoto(String reportPhoto) {
            this.ReportPhoto = reportPhoto;
        }
    }
    //**********************************************************************************************

    /**
     * Created by Ponnusamy M on 4/6/2017.
     */

    public static class DiagnosisItem {

        String S_No, Doctor, OPerationNotes, Position, OperationProcedure, PostOPerativeDiagnosis, PostOpertiveinstruction, Closure, PreOperativeDiagnosis;
        String Remark_Image;

        public DiagnosisItem(String s_No, String doctor, String OPerationNotes, String position, String operationProcedure, String postOPerativeDiagnosis, String postOpertiveinstruction, String closure, String preOperativeDiagnosis, String remark_Image) {
            this.S_No = s_No;
            this.Doctor = doctor;
            this.OPerationNotes = OPerationNotes;
            this.Position = position;
            this.OperationProcedure = operationProcedure;
            this.PostOPerativeDiagnosis = postOPerativeDiagnosis;
            this.PostOpertiveinstruction = postOpertiveinstruction;
            this.Closure = closure;
            this.PreOperativeDiagnosis = preOperativeDiagnosis;
            this.Remark_Image = remark_Image;
        }

        public final String getS_No() {
            return this.S_No;
        }

        public final void setS_No(String s_No) {
            this.S_No = s_No;
        }

        public final String getDoctor() {
            return this.Doctor;
        }

        public final void setDoctor(String doctor) {
            this.Doctor = doctor;
        }

        public final String getOPerationNotes() {
            return this.OPerationNotes;
        }

        public final void setOPerationNotes(String OPerationNotes) {
            this.OPerationNotes = OPerationNotes;
        }

        public final String getPosition() {
            return this.Position;
        }

        public final void setPosition(String position) {
            this.Position = position;
        }

        public final String getOperationProcedure() {
            return this.OperationProcedure;
        }

        public final void setOperationProcedure(String operationProcedure) {
            this.OperationProcedure = operationProcedure;
        }

        public final String getPostOPerativeDiagnosis() {
            return this.PostOPerativeDiagnosis;
        }

        public final void setPostOPerativeDiagnosis(String postOPerativeDiagnosis) {
            this.PostOPerativeDiagnosis = postOPerativeDiagnosis;
        }

        public final String getPostOpertiveinstruction() {
            return this.PostOpertiveinstruction;
        }

        public final void setPostOpertiveinstruction(String postOpertiveinstruction) {
            this.PostOpertiveinstruction = postOpertiveinstruction;
        }

        public final String getClosure() {
            return this.Closure;
        }

        public final void setClosure(String closure) {
            this.Closure = closure;
        }

        public final String getPreOperativeDiagnosis() {
            return this.PreOperativeDiagnosis;
        }

        public final void setPreOperativeDiagnosis(String preOperativeDiagnosis) {
            this.PreOperativeDiagnosis = preOperativeDiagnosis;
        }

        public final String getRemark_Image() {
            return this.Remark_Image;
        }

        public final void setRemark_Image(String remark_Image) {
            this.Remark_Image = remark_Image;
        }
    }


    //**********************************************************************************************
    public static class InjectionGetSet {

        String InjectionName = "";
        String Dosage = "";
        String Quantity = "";
        String InjectionId ="";

        public InjectionGetSet() {
            InjectionId = this.InjectionId;
            InjectionName = this.InjectionName;
            Dosage = this.Dosage;
            Quantity = this.Quantity;

        }

        public final String getInjectionId() {
            return this.InjectionId;
        }

        public final void setInjectionId(String injectionId) {
            this.InjectionId = injectionId;
        }

        public final String getInjectionName() {
            return this.InjectionName;
        }

        public final void setInjectionName(String injectionName) {
            this.InjectionName = injectionName;
        }

        public final String getDosage() {
            return this.Dosage;
        }

        public final void setDosage(String dosage) {
            this.Dosage = dosage;
        }

        public final String getQuantity() {
            return this.Quantity;
        }

        public final void setQuantity(String quantity) {
            this.Quantity = quantity;
        }
    }


    //**********************************************************************************************

    /**
     * Created by Ponnusamy M on 4/19/2017.
     */

    public static class MedicineGetSet {

        String Medicine_Name = "";
        String Morning_Value = "";
        String Afternoon_Value = "";
        String Evening_Value = "";
        String Night_Value = "";
        String Intake_Value = "";
        String Days_Value = "";

        String Medicine_Type = "";

        public MedicineGetSet() {

        }

        public MedicineGetSet(String medicine_Type, String medicine_Name, String morning_Value, String afternoon_Value, String evening_Value, String night_Value, String intake_Value, String days_Value) {
            this.Medicine_Type = medicine_Type;
            this.Medicine_Name = medicine_Name;
            this.Morning_Value = morning_Value;
            this.Evening_Value = evening_Value;
            this.Afternoon_Value = afternoon_Value;
            this.Night_Value = night_Value;
            this.Intake_Value = intake_Value;
            this.Days_Value = days_Value;
        }


        public final String getMedicine_Type() {
            return this.Medicine_Type;
        }

        public final void setMedicine_Type(String medicine_Type) {
            this.Medicine_Type = medicine_Type;
        }

        public final String getAfternoon_Value() {
            return this.Afternoon_Value;
        }

        public final void setAfternoon_Value(String afternoon_Value) {
            this.Afternoon_Value = afternoon_Value;
        }

        public final String getMedicine_Name() {
            return this.Medicine_Name;
        }

        public final void setMedicine_Name(String medicine_Name) {
            this.Medicine_Name = medicine_Name;
        }

        public final String getMorning_Value() {
            return this.Morning_Value;
        }

        public final void setMorning_Value(String morning_Value) {
            this.Morning_Value = morning_Value;
        }

        public final String getEvening_Value() {
            return this.Evening_Value;
        }

        public final void setEvening_Value(String evening_Value) {
            this.Evening_Value = evening_Value;
        }

        public final String getNight_Value() {
            return this.Night_Value;
        }

        public final void setNight_Value(String night_Value) {
            this.Night_Value = night_Value;
        }

        public final String getIntake_Value() {
            return this.Intake_Value;
        }

        public final void setIntake_Value(String intake_Value) {
            this.Intake_Value = intake_Value;
        }

        public final String getDays_Value() {
            return this.Days_Value;
        }

        public final void setDays_Value(String days_Value) {
            this.Days_Value = days_Value;
        }


    }
    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/17/2017.
     */

    public static class MyPatientGetSet {
        public boolean IsOnlinePatient;
        public String IsLabReport = "0";
        private String Patient_Name;
        private String Patient_Id;
        private String Patient_Age;
        private String Patient_Gender;
        private String Patient_Image;
        private String Patient_Prefix;
//00003075
        public MyPatientGetSet(String patient_prefix, String patient_Name, String patient_Id, String patient_Age, String patient_Gender, String Patient_Image) {
            this.Patient_Prefix = patient_prefix;
            this.Patient_Name = patient_Name;
            this.Patient_Id = patient_Id;
            this.Patient_Age = patient_Age;
            this.Patient_Gender = patient_Gender;
            this.Patient_Image = Patient_Image;

        }

        public final String getPatient_Prefix() {
            return this.Patient_Prefix;
        }

        public final void setPatient_Prefix(String patient_Prefix) {
            this.Patient_Prefix = patient_Prefix;
        }

        public final String getPatient_Image() {
            return this.Patient_Image;
        }

        public final void setPatient_Image(String patient_Image) {
            this.Patient_Image = patient_Image;
        }

        public final boolean isOnlinePatient() {
            return this.IsOnlinePatient;
        }

        public final void setOnlinePatient(boolean onlinePatient) {
            this.IsOnlinePatient = onlinePatient;
        }

        public final String getPatient_Name() {
            return this.Patient_Name;
        }

        public final void setPatient_Name(String patient_Name) {
            this.Patient_Name = patient_Name;
        }

        public final String getPatient_Id() {
            return this.Patient_Id;
        }

        public final void setPatient_Id(String patient_Id) {
            this.Patient_Id = patient_Id;
        }

        public final String getPatient_Age() {
            return this.Patient_Age;
        }

        public final void setPatient_Age(String patient_Age) {
            this.Patient_Age = patient_Age;
        }

        public final String getPatient_Gender() {
            return this.Patient_Gender;
        }

        public final void setPatient_Gender(String patient_Gender) {
            this.Patient_Gender = patient_Gender;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Kumar on 4/6/2017.
     */

    public static class OnlineConsultation_DataObjects {

        String Id;
        String Patient_Name;
        String Patient_Id;
        String Patient_Age;
        String Patient_Gender;
        String Patient_Photo;
        String ConsultationId;
        String MedId;

        public OnlineConsultation_DataObjects(String id, String patient_Name, String patient_Id, String patient_Age, String patient_Gender, String patient_Photo, String consultationId, String Medid) {
            this.Id = id;
            this.Patient_Name = patient_Name;
            this.Patient_Id = patient_Id;
            this.Patient_Age = patient_Age;
            this.Patient_Gender = patient_Gender;
            this.Patient_Photo = patient_Photo;
            this.ConsultationId = consultationId;
            this.MedId = Medid;
        }

        public final String getMedId() {
            return this.MedId;
        }

        public final void setMedId(String medId) {
            this.MedId = medId;
        }

        public final String getId() {
            return this.Id;
        }

        public final void setId(String id) {
            this.Id = id;
        }

        public final String getPatient_Name() {
            return this.Patient_Name;
        }

        public final void setPatient_Name(String patient_Name) {
            this.Patient_Name = patient_Name;
        }

        public final String getPatient_Id() {
            return this.Patient_Id;
        }

        public final void setPatient_Id(String patient_Id) {
            this.Patient_Id = patient_Id;
        }

        public final String getPatient_Age() {
            return this.Patient_Age;
        }

        public final void setPatient_Age(String patient_Age) {
            this.Patient_Age = patient_Age;
        }

        public final String getPatient_Gender() {
            return this.Patient_Gender;
        }

        public final void setPatient_Gender(String patient_Gender) {
            this.Patient_Gender = patient_Gender;
        }

        public final String getPatient_Photo() {
            return this.Patient_Photo;
        }

        public final void setPatient_Photo(String patient_Photo) {
            this.Patient_Photo = patient_Photo;
        }

        public final String getConsultationId() {
            return this.ConsultationId;
        }

        public final void setConsultationId(String consultationId) {
            this.ConsultationId = consultationId;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/6/2017.
     */

    public static class OperationItem {
        String SerialNo = "";
        String Operation_name = "";
        String Operation_date = "";
        String From_time = "";
        String To_Time = "";
        String Reference_doc = "";

        public OperationItem(String serialNo, String operation_name, String operation_date, String from_time, String to_Time, String reference_doc) {
            this.SerialNo = serialNo;
            this.Operation_name = operation_name;
            this.Operation_date = operation_date;
            this.From_time = from_time;
            this.To_Time = to_Time;
            this.Reference_doc = reference_doc;
        }

        public final String getSerialNo() {
            return this.SerialNo;
        }

        public final void setSerialNo(String serialNo) {
            this.SerialNo = serialNo;
        }

        public final String getOperation_name() {
            return this.Operation_name;
        }

        public final void setOperation_name(String operation_name) {
            this.Operation_name = operation_name;
        }

        public final String getOperation_date() {
            return this.Operation_date;
        }

        public final void setOperation_date(String operation_date) {
            this.Operation_date = operation_date;
        }

        public final String getFrom_time() {
            return this.From_time;
        }

        public final void setFrom_time(String from_time) {
            this.From_time = from_time;
        }

        public final String getTo_Time() {
            return this.To_Time;
        }

        public final void setTo_Time(String to_Time) {
            this.To_Time = to_Time;
        }

        public final String getReference_doc() {
            return this.Reference_doc;
        }

        public final void setReference_doc(String reference_doc) {
            this.Reference_doc = reference_doc;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/7/2017.
     */

    public static class PrescriptionItem {
        String Medicine_Name, Interval, Frequency, Duration, Doctorname, Sno;

        public PrescriptionItem(String sno, String medicine_Name, String interval, String frequency, String duration, String doctorname) {
            Sno = sno;
            this.Medicine_Name = medicine_Name;
            this.Interval = interval;
            this.Frequency = frequency;
            this.Duration = duration;
            this.Doctorname = doctorname;
        }

        public final String getSno() {
            return this.Sno;
        }

        public final void setSno(String sno) {
            this.Sno = sno;
        }

        public final String getMedicine_Name() {
            return this.Medicine_Name;
        }

        public final void setMedicine_Name(String medicine_Name) {
            this.Medicine_Name = medicine_Name;
        }

        public final String getInterval() {
            return this.Interval;
        }

        public final void setInterval(String interval) {
            this.Interval = interval;
        }

        public final String getFrequency() {
            return this.Frequency;
        }

        public final void setFrequency(String frequency) {
            this.Frequency = frequency;
        }

        public final String getDuration() {
            return this.Duration;
        }

        public final void setDuration(String duration) {
            this.Duration = duration;
        }

        public final String getDoctorname() {
            return this.Doctorname;
        }

        public final void setDoctorname(String doctorname) {
            this.Doctorname = doctorname;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/8/2017.
     */

    public static class TestItem {
        String Report_Name = "";
        String Report_Type = "";
        String Report_Date = "";
        String Report_Image = "";
        String Report_Summary = "";

        public TestItem(String report_Name, String report_Type, String report_Date, String report_Image, String report_Summary) {
            this.Report_Name = report_Name;
            this.Report_Type = report_Type;
            this.Report_Date = report_Date;
            this.Report_Image = report_Image;
            this.Report_Summary = report_Summary;
        }

        public final String getReport_Summary() {
            return this.Report_Summary;
        }

        public final void setReport_Summary(String report_Summary) {
            this.Report_Summary = report_Summary;
        }

        public final String getReport_Name() {
            return this.Report_Name;
        }

        public final void setReport_Name(String report_Name) {
            this.Report_Name = report_Name;
        }

        public final String getReport_Type() {
            return this.Report_Type;
        }

        public final void setReport_Type(String report_Type) {
            this.Report_Type = report_Type;
        }

        public final String getReport_Date() {
            return this.Report_Date;
        }

        public final void setReport_Date(String report_Date) {
            this.Report_Date = report_Date;
        }

        public final String getReport_Image() {
            return this.Report_Image;
        }

        public final void setReport_Image(String report_Image) {
            this.Report_Image = report_Image;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/2/2017.
     */

    public static class DataObject {
        String vaccinename = "";
        String schedule = "";
        String givendt = "";
        String weight = "";

        public DataObject() {
        }

        public final String getVaccinename() {
            return this.vaccinename;
        }

        public final void setVaccinename(String vaccinename) {
            this.vaccinename = vaccinename;
        }

        public final String getSchedule() {
            return this.schedule;
        }

        public final void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public final String getGivendt() {
            return this.givendt;
        }

        public final void setGivendt(String givendt) {
            this.givendt = givendt;
        }

        public final String getWeight() {
            return this.weight;
        }

        public final void setWeight(String weight) {
            this.weight = weight;
        }

    }
    //**********************************************************************************************


    public static class DrugItem {


        String System = "";
        String BrandName = "";
        String PharmaCompany = "";
        String Dosage = "";
        String serverid = "";

        public DrugItem() {
        }

        public final String getSystem() {
            return this.System;
        }

        public final void setSystem(String system) {
            this.System = system;
        }

        public final String getBrandName() {
            return this.BrandName;
        }

        public final void setBrandName(String brandName) {
            this.BrandName = brandName;
        }

        public final String getPharmaCompany() {
            return this.PharmaCompany;
        }

        public final void setPharmaCompany(String pharmaCompany) {
            this.PharmaCompany = pharmaCompany;
        }

        public final String getDosage() {
            return this.Dosage;
        }

        public final void setDosage(String dosage) {
            this.Dosage = dosage;
        }

        public final String getServerid() {
            return this.serverid;
        }

        public final void setServerid(String serverid) {
            this.serverid = serverid;
        }
    }

    //**********************************************************************************************


    public static class RowItem {

        public boolean IsOnlinePatient;
        String File_Path;
        public String UniqueId = "";
        private Bitmap imageId;
        private String title;
        private String desc;


        public RowItem(Bitmap imageId, String title, String desc, String File_Path) {
            this.imageId = imageId;
            this.title = title;
            this.desc = desc;
            this.File_Path = File_Path;
        }

        public final String getFile_Path() {
            return this.File_Path;
        }

        public final void setFile_Path(String file_Path) {
            this.File_Path = file_Path;
        }

        public final Bitmap getImageId() {
            return this.imageId;
        }

        public final void setImageId(Bitmap imageId) {

            this.imageId = imageId;
        }

        public final String getDesc() {
            return this.desc;
        }

        public final void setDesc(String desc) {
            this.desc = desc;
        }

        public final String getTitle() {
            return this.title;
        }

        public final void setTitle(String title) {
            this.title = title;
        }

        @Override
        public final String toString() {
            return this.title + '\n' + this.desc;
        }
    }
    //**********************************************************************************************


    public static class RowItemVaccination {

        private String title;
        private String desc;
        private String gvn;
        private String weight;


        public RowItemVaccination(String title, String desc, String gvn, String weight) {

            this.title = title;
            this.desc = desc;
            this.gvn = gvn;
            this.weight = weight;
        }


        public final String getTitle() {
            return this.title;
        }

        public final void setTitle(String title) {
            this.title = title;
        }


        public final String getDesc() {
            return this.desc;
        }

        public final void setDesc(String desc) {
            this.desc = desc;
        }


        public final String getgvn() {
            return this.gvn;
        }

        public final void setgvn(String gvn) {
            this.gvn = gvn;
        }


        public final String getweight() {
            return this.weight;
        }

        public final void setweight(String weight) {
            this.weight = weight;
        }

        @Override
        public final String toString() {
            return this.title + ',' + this.desc + ',' + this.gvn + ',' + this.weight;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/24/2017.
     */

    public static class NotificationGetSet {
        String _patientName;
        String _patientId;
        String _mTestId;
        ArrayList<CommonDataObjects.NotificationListGetSet> notificationListGetSets;

        public NotificationGetSet(String _patientName, String _patientId, String _mTestId, ArrayList<CommonDataObjects.NotificationListGetSet> notificationListGetSets) {
            this._patientName = _patientName;
            this._patientId = _patientId;
            this._mTestId = _mTestId;
            this.notificationListGetSets = notificationListGetSets;
        }

        public NotificationGetSet(String _patientName, String _patientId, ArrayList<CommonDataObjects.NotificationListGetSet> notificationListGetSets) {
            this._patientName = _patientName;
            this._patientId = _patientId;
            this.notificationListGetSets = notificationListGetSets;
        }

        public final String get_mTestId() {
            return this._mTestId;
        }

        public final void set_mTestId(String _mTestId) {
            this._mTestId = _mTestId;
        }

        public final String get_patientName() {
            return this._patientName;
        }

        public final void set_patientName(String _patientName) {
            this._patientName = _patientName;
        }

        public final String get_patientId() {
            return this._patientId;
        }

        public final void set_patientId(String _patientId) {
            this._patientId = _patientId;
        }

        public final ArrayList<CommonDataObjects.NotificationListGetSet> getNotificationListGetSets() {
            return this.notificationListGetSets;
        }

        public final void setNotificationListGetSets(ArrayList<CommonDataObjects.NotificationListGetSet> notificationListGetSets) {
            this.notificationListGetSets = notificationListGetSets;
        }
    }
    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/24/2017.
     */

    public static class NotificationListGetSet {
        String _testName;
        String _testValue;
        String _testRemark;

        public NotificationListGetSet(String _testName, String _testValue, String _testRemark) {
            this._testName = _testName;
            this._testValue = _testValue;
            this._testRemark = _testRemark;
        }

        public final String get_testName() {
            return this._testName;
        }

        public final void set_testName(String _testName) {
            this._testName = _testName;
        }

        public final String get_testValue() {
            return this._testValue;
        }

        public final void set_testValue(String _testValue) {
            this._testValue = _testValue;
        }

        public final String get_testRemark() {
            return this._testRemark;
        }

        public final void set_testRemark(String _testRemark) {
            this._testRemark = _testRemark;
        }
    }
    //**********************************************************************************************


    public  static class Clinical_MHData
    {
        String TreatmentFor="";
        String Medicine_Name="";
        String Period="";

        public Clinical_MHData(String treatmentFor, String medicine_Name, String period) {
            TreatmentFor = treatmentFor;
            Medicine_Name = medicine_Name;
            Period = period;
        }

        public String getTreatmentFor() {
            return this.TreatmentFor;
        }

        public void setTreatmentFor(String treatmentFor) {
            this.TreatmentFor = treatmentFor;
        }

        public String getMedicine_Name() {
            return this.Medicine_Name;
        }

        public void setMedicine_Name(String medicine_Name) {
            this.Medicine_Name = medicine_Name;
        }

        public String getPeriod() {
            return this.Period;
        }

        public void setPeriod(String period) {
            this.Period = period;
        }
    }

    public  static class TwoData
    {
        String Data1="";
        String Data2="";

        public TwoData(String data1, String data2) {
            this.Data1 = data1;
            this.Data2 = data2;
        }

        public String getData1() {
            return this.Data1;
        }

        public void setData1(String data1) {
            this.Data1 = data1;
        }

        public String getData2() {
            return this.Data2;
        }

        public void setData2(String data2) {
            this.Data2 = data2;
        }
    }



    public static class TemplateGetSet {
        String name;
        String sno;

        public TemplateGetSet(String name, String sno) {
            this.name = name;
            this.sno = sno;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSno() {
            return this.sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }
    }



    public static class MedicalCaseRecords {
        String date = "";
        String time = "";
        String clinical_notes = "";
        String treatment_diet = "";

        public MedicalCaseRecords(String date,String time, String clinical_notes, String treatment_diet) {
            this.date = date;
            this.time=time;
            this.clinical_notes = clinical_notes;
            this.treatment_diet = treatment_diet;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClinical_notes() {
            return this.clinical_notes;
        }

        public void setClinical_notes(String clinical_notes) {
            this.clinical_notes = clinical_notes;
        }

        public String getTreatment_diet() {
            return this.treatment_diet;
        }

        public void setTreatment_diet(String treatment_diet) {
            this.treatment_diet = treatment_diet;
        }

        public String getTime() {
            return this.time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


    public static class Addgetset {
        private String diet_name;
        private String food_name;
        private String calc;
        private String id;

        public Addgetset() {
        }

        public Addgetset(String title, String genre, String year) {
            diet_name = title;
            food_name = genre;
            calc = year;
        }

        public Addgetset(String id, String title, String genre, String year) {
            this.id = id;
            diet_name = title;
            food_name = genre;
            calc = year;
        }

        public final String getId() {
            return this.id;
        }

        public final void setId(String id) {
            this.id = id;
        }

        public final String getDiet_name() {
            return this.diet_name;
        }

        public final void setDiet_name(String diet_name) {
            this.diet_name = diet_name;
        }

        public final String getFood_name() {
            return this.food_name;
        }

        public final void setFood_name(String food_name) {
            this.food_name = food_name;
        }

        public final String getCalc() {
            return this.calc;
        }

        public final void setCalc(String calc) {
            this.calc = calc;
        }


    }


}//END
