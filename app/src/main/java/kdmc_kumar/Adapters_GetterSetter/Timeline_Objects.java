package kdmc_kumar.Adapters_GetterSetter;


public class Timeline_Objects {

    public Timeline_Objects(String id, String visitedDate_, String year) {
        Id = id;
        this.VisitedDate = visitedDate_;
        this.Unique_Id = year;
    }

    String Id;
    String VisitedDate;
    String Unique_Id;

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getVisitedDate() {
        return this.VisitedDate;
    }

    public void setVisitedDate(String visitedDate) {
        this.VisitedDate = visitedDate;
    }

    public String getUnique_Id() {
        return this.Unique_Id;
    }

    public void setUnique_Id(String unique_Id) {
        this.Unique_Id = unique_Id;
    }
}
