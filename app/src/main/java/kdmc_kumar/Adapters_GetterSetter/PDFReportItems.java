package kdmc_kumar.Adapters_GetterSetter;

/**
 * Created by Android on 13-Mar-18.
 */


public class PDFReportItems {

    private int Id;
    private String PATIENTID;
    private String PATIENTNAME;
    private String SYMPTOMS;
    private String DIAGNOSIS;
    private String VISITEDON;

    public PDFReportItems(int Id, String PATIENTID, String PATIENTNAME, String SYMPTOMS, String DIAGNOSIS, String VISITEDON) {

        this.PATIENTID = PATIENTID;
        this.PATIENTNAME = PATIENTNAME;
        this.SYMPTOMS = SYMPTOMS;
        this.DIAGNOSIS = DIAGNOSIS;
        this.VISITEDON = VISITEDON;
        this.Id = Id;


    }

    public final int getId() {
        return this.Id;
    }

    public final void setId(int id) {
        this.Id = id;
    }

    public final String getDIAGNOSIS() {
        return this.DIAGNOSIS;
    }

    public final void setDIAGNOSIS(String DIAGNOSIS) {
        this.DIAGNOSIS = DIAGNOSIS;
    }

    public final String getPATIENTID() {
        return this.PATIENTID;
    }

    public final void setPATIENTID(String PATIENTID) {
        this.PATIENTID = PATIENTID;
    }

    public final String getPATIENTNAME() {
        return this.PATIENTNAME;
    }

    public final void setPATIENTNAME(String PATIENTNAME) {
        this.PATIENTNAME = PATIENTNAME;
    }

    public final String getSYMPTOMS() {
        return this.SYMPTOMS;
    }

    public final void setSYMPTOMS(String SYMPTOMS) {
        this.SYMPTOMS = SYMPTOMS;
    }

    public final String getVISITEDON() {
        return this.VISITEDON;
    }

    public final void setVISITEDON(String VISITEDON) {
        this.VISITEDON = VISITEDON;
    }

}

