package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.Fav_TestList;
import kdmc_kumar.Adapters_GetterSetter.FavTestRecylerAdapter.MyViewHolder;


/**
 * Created by Ponnusamy M on 4/3/2017.
 */

public class FavTestRecylerAdapter extends Adapter<MyViewHolder> {

    private final ArrayList<Fav_TestList> favtestItems;
    //**********************************************************************************************

    public FavTestRecylerAdapter(ArrayList<Fav_TestList> drugItems) {

        favtestItems = drugItems;
    }
    //**********************************************************************************************

    @NonNull
    @Override
    public final FavTestRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout.activity_pretest_adap, parent, false);

        return new FavTestRecylerAdapter.MyViewHolder(view);
    }
    //**********************************************************************************************

    @Override
    public final void onBindViewHolder(@NonNull FavTestRecylerAdapter.MyViewHolder holder, int position) {

        Fav_TestList rowItem = this.favtestItems.get(position);

        holder.Testname.setText(rowItem.getTestName());
        holder.Subtestname.setText(rowItem.getSubTestName());

        holder.ParentView.setOnClickListener(view -> {

        });


    }
    //**********************************************************************************************

    @Override
    public final int getItemCount() {
        return this.favtestItems.size();
    }

    //**********************************************************************************************

    static class MyViewHolder extends ViewHolder {

        final TextView Testname;
        final TextView Subtestname;
        final CardView ParentView;

        MyViewHolder(View itemView) {
            super(itemView);

            this.Testname = itemView.findViewById(id.testname_tv);
            this.Subtestname = itemView.findViewById(id.subtestname_tv);

            this.ParentView = itemView.findViewById(id.card_view);


        }
    }
    //**********************************************************************************************

}
