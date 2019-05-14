package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

/**
 * Created by bpncool on 2/23/2016.
 */
public class Item {

    private final String Subtestname;
    private final String TestName;
    private final int id;

    public Item(String testName, String subtestname, int id) {
        this.TestName = testName;
        this.Subtestname = subtestname;
        this.id = id;
    }


    public final String getTestName() {
        return this.TestName;
    }

    public final String getSubtestname() {
        return this.Subtestname;
    }

    public final int getId() {
        return this.id;
    }

    public final String setTestName() {
        return this.TestName;
    }

    public final String setSubtestname() {
        return this.Subtestname;
    }

    public final int setId() {
        return this.id;
    }


}
