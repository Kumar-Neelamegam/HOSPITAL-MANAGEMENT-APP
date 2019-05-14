package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Masters_Modules.PreferredTestTemplate.SectionedExpandableGridAdapter.ViewHolder;

/**
 * Created by lenovo on 2/23/2016.
 */
public class SectionedExpandableGridAdapter extends Adapter<ViewHolder> {

    //view type
    private static final int VIEW_TYPE_SECTION = layout.layout_test_template_section;
    private static final int VIEW_TYPE_ITEM = layout.layout_test_template_item; //TODO : change this
    //context
    private final Context mContext;
    //listeners
    private final ItemClickListener mItemClickListener;
    private final SectionStateChangeListener mSectionStateChangeListener;
    //**********************************************************************************************
    //data array
    private final ArrayList<Object> mDataArrayList;

    //**********************************************************************************************
    public SectionedExpandableGridAdapter(Context context, ArrayList<Object> dataArrayList,
                                          GridLayoutManager gridLayoutManager, ItemClickListener itemClickListener,
                                          SectionStateChangeListener sectionStateChangeListener) {
        this.mContext = context;
        this.mItemClickListener = itemClickListener;
        this.mSectionStateChangeListener = sectionStateChangeListener;
        this.mDataArrayList = dataArrayList;

        gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return SectionedExpandableGridAdapter.this.isSection(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    //**********************************************************************************************
    private boolean isSection(int position) {
        return this.mDataArrayList.get(position) instanceof Section;
    }

    //**********************************************************************************************
    @NonNull
    @Override
    public final SectionedExpandableGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SectionedExpandableGridAdapter.ViewHolder(LayoutInflater.from(this.mContext).inflate(viewType, parent, false), viewType);
    }

    //**********************************************************************************************
    @Override
    public final void onBindViewHolder(@NonNull SectionedExpandableGridAdapter.ViewHolder holder, int position) {
        switch (holder.viewType) {

            case SectionedExpandableGridAdapter.VIEW_TYPE_ITEM:
                Item item = (Item) this.mDataArrayList.get(position);

                holder.itemTextView.setText(item.getTestName());
                holder.getItemTextView2.setText(item.getSubtestname());


                holder.view.setOnClickListener(v -> this.mItemClickListener.itemClicked(item));


                break;

            case SectionedExpandableGridAdapter.VIEW_TYPE_SECTION:
                Section section = (Section) this.mDataArrayList.get(position);
                holder.sectionTextView.setText(section.getName());
                holder.sectionTextView.setOnClickListener(v -> this.mItemClickListener.itemClicked(section));
                holder.sectionToggleButton.setChecked(section.isExpanded);
                holder.sectionToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    this.mSectionStateChangeListener.onSectionStateChanged(section, isChecked);
                    if (holder.sectionToggleButton.isChecked()) {
                        holder.sectionToggleButton.setBackground(this.mContext.getResources().getDrawable(drawable.icon_minus_control));
                    } else {
                        holder.sectionToggleButton.setBackground(this.mContext.getResources().getDrawable(drawable.icon_plus_control));
                    }
                });


                break;
        }


    }

    //**********************************************************************************************
    @Override
    public final int getItemCount() {
        return this.mDataArrayList.size();
    }

    //**********************************************************************************************
    @Override
    public final int getItemViewType(int position) {
        return this.isSection(position) ? SectionedExpandableGridAdapter.VIEW_TYPE_SECTION : SectionedExpandableGridAdapter.VIEW_TYPE_ITEM;
    }

    //**********************************************************************************************
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        final View view;
        final int viewType;

        //for section
        TextView sectionTextView;
        ToggleButton sectionToggleButton;

        //for item
        TextView itemTextView;
        TextView getItemTextView2;

        ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.view = view;
            if (viewType == SectionedExpandableGridAdapter.VIEW_TYPE_ITEM) {
                this.itemTextView = view.findViewById(id.txt_testname);
                this.getItemTextView2 = view.findViewById(id.txt_subtestname);

            } else {
                this.sectionTextView = view.findViewById(id.text_section);
                this.sectionToggleButton = view.findViewById(id.toggle_button_section);
            }
        }
    }
    //**********************************************************************************************

}
