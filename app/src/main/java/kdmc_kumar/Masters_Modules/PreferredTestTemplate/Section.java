package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

/**
 * Created by bpncool on 2/23/2016.
 */
public class Section {

    private final String name;

    public boolean isExpanded;

    public Section(String name) {
        this.name = name;
        this.isExpanded = true;
    }

    public final String getName() {
        return this.name;
    }
}
