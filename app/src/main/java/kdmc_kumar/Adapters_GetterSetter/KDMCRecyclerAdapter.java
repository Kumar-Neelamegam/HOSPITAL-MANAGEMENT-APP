package kdmc_kumar.Utilities_Others;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import java.util.List;

public class KDMCRecyclerAdapter<Data, SampleViewHolder extends RecyclerView.ViewHolder> extends Adapter<SampleViewHolder> {

    private final List<Data> values;
    private SampleViewHolder viewHolder;
    private final int layoutId;
    private KDMCRecyclerAdapter.AdapterView adapterView;

    public KDMCRecyclerAdapter(List<Data> values, int layoutId) {
        this.values = values;
        this.layoutId = layoutId;
    }

    public KDMCRecyclerAdapter setRowItemView(KDMCRecyclerAdapter.AdapterView adapterView) {
        this.adapterView = adapterView;
        return this;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Object object = this.adapterView.setAdapterView(parent, viewType, layoutId);
        viewHolder = (SampleViewHolder) object;
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        Data data = this.values.get(position);
        this.adapterView.onBindView(holder, position, data, (List<Object>) values);
    }

    @Override
    public int getItemCount() {
        return this.values.size();
    }

    public interface AdapterView {

        Object setAdapterView(ViewGroup parent, int viewType, int layoutId);

        void onBindView(Object holder, int position, Object data, List<Object> dataList);

    }



    public void delete(int position){
        this.values.remove(position);
        this.notifyItemRemoved(position);
    }

    public int getSize(){
        return this.values.size();
    }


}
