package kdmc_kumar.Adapters_GetterSetter;

import android.R.drawable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.DietAdapter.MyViewHolder;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Inpatient_Module.AddDietActivity;


public class DietAdapter extends Adapter<MyViewHolder> {

    public final List<CommonDataObjects.Addgetset> moviesList;

    public DietAdapter(List<CommonDataObjects.Addgetset> moviesList, Context ctx) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public final DietAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout.activity_diet_adapter, parent, false);

        return new DietAdapter.MyViewHolder(itemView);
    }

    @Override
    public final void onBindViewHolder(@NonNull DietAdapter.MyViewHolder holder, int position) {
        CommonDataObjects.Addgetset movie = this.moviesList.get(position);
        holder.title.setText(movie.getDiet_name());
        holder.genre.setText(movie.getFood_name());
        holder.year.setText(movie.getCalc());

        Integer valuess=position+1;
        holder.serialno.setText(String.valueOf(valuess));




        holder.rel_layout.setOnClickListener(view -> {
            Builder adb = new Builder(view.getContext());


            adb.setTitle(string.delte);

            adb.setIcon(drawable.ic_dialog_alert);


            adb.setPositiveButton(string.ok, (dialog, which) -> {

                SQLiteDatabase db = BaseConfig.GetDb();//view.getContext());


                String Query = "UPDATE diet_entry SET IsDelete = 1 WHERE  Id='" + this.moviesList.get(position).getId() + "' ";

                db.execSQL(Query);
                //Log.e("delete Query", Query);


                //Update Total Calories Label

                Integer alreadyValue=Integer.parseInt(AddDietActivity.calories_total.getText().toString());
                Integer removeValue=Integer.parseInt(this.moviesList.get(position).getCalc());
                Integer updateValue=alreadyValue-removeValue;
                AddDietActivity.calories_total.setText(String.valueOf(updateValue));

                db.close();
                this.moviesList.remove(position);


                this.notifyDataSetChanged();

            });


            adb.setNegativeButton(string.cancel, (dialog, which) -> dialog.dismiss());
            adb.show();
        });
    }

    @Override
    public final int getItemCount() {
        return this.moviesList.size();
    }

    public static class MyViewHolder extends ViewHolder {
        final TextView title;
        final TextView year;
        final TextView genre;
        final TextView serialno;
        final CardView card_view;
        final LinearLayout rel_layout;

        MyViewHolder(View view) {
            super(view);
            this.title = view.findViewById(id.title);
            this.genre = view.findViewById(id.genre);
            this.year = view.findViewById(id.year);
            this.serialno = view.findViewById(id.serialno);
            this.card_view = view.findViewById(id.card_view);
            this.rel_layout = view.findViewById(id.rel_layout);
        }
    }


}