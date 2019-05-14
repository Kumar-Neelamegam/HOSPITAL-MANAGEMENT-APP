package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by bpncool on 2/23/2016.
 */
public class SectionedExpandableLayoutHelper implements SectionStateChangeListener {

    //recycler view
    private final RecyclerView mRecyclerView;
    //data list
    private final LinkedHashMap<Section, ArrayList<Item>> mSectionDataMap = new LinkedHashMap<>();
    private final ArrayList<Object> mDataArrayList = new ArrayList<>();
    //section map
    //TODO : look for a way to avoid this
    private final HashMap<String, Section> mSectionMap = new HashMap<>();
    //adapter
    private final SectionedExpandableGridAdapter mSectionedExpandableGridAdapter;

    public SectionedExpandableLayoutHelper(Context context, RecyclerView recyclerView, ItemClickListener itemClickListener,
                                           int gridSpanCount) {

        //setting the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, gridSpanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        this.mSectionedExpandableGridAdapter = new SectionedExpandableGridAdapter(context, this.mDataArrayList,
                gridLayoutManager, itemClickListener, this);
        recyclerView.setAdapter(this.mSectionedExpandableGridAdapter);

        this.mRecyclerView = recyclerView;
    }

    public final void notifyDataSetChanged() {
        //TODO : handle this condition such that these functions won't be called if the recycler view is on scroll
        this.generateDataList();
        this.mSectionedExpandableGridAdapter.notifyDataSetChanged();
    }

    public final void addSection(String section, ArrayList<Item> items) {
        Section newSection;
        this.mSectionMap.put(section, (newSection = new Section(section)));
        this.mSectionDataMap.put(newSection, items);
    }

    public final void addItem(String section, Item item) {
        this.mSectionDataMap.get(this.mSectionMap.get(section)).add(item);
    }

    public final void removeItem(String section, Item item) {
        this.mSectionDataMap.get(this.mSectionMap.get(section)).remove(item);
    }

    public final void removeSection(String section) {
        this.mSectionDataMap.remove(this.mSectionMap.get(section));
        this.mSectionMap.remove(section);
    }

    private void generateDataList() {
        this.mDataArrayList.clear();
        for (Iterator<Entry<Section, ArrayList<Item>>> iterator = this.mSectionDataMap.entrySet().iterator(); iterator.hasNext(); ) {
            Entry<Section, ArrayList<Item>> entry = iterator.next();
            Section key;
            this.mDataArrayList.add((key = entry.getKey()));
            if (key.isExpanded)
                this.mDataArrayList.addAll(entry.getValue());
        }
    }

    @Override
    public final void onSectionStateChanged(Section section, boolean isOpen) {
        if (!this.mRecyclerView.isComputingLayout()) {
            section.isExpanded = isOpen;
            this.notifyDataSetChanged();
        }
    }
}
