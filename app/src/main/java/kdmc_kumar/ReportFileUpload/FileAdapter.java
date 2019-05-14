package kdmc_kumar.ReportFileUpload;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.ReportFileUpload.FileAdapter.MyViewHolder;

/**
 * Created by Ponnusamy M on 4/21/2017.
 */

public class FileAdapter extends Adapter<MyViewHolder> {

    private ArrayList<FileGetSet> fileGetSets = new ArrayList<>();
    private final RecyclerView recyclerView;


    public FileAdapter(ArrayList<FileGetSet> fileGetSets, RecyclerView recyclerView) {
        this.fileGetSets = fileGetSets;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public final FileAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.screen_list, parent, false);
        return new FileAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull FileAdapter.MyViewHolder holder, int position) {

        FileGetSet item = this.fileGetSets.get(position);

        holder.name.setText(item.getUploadFileDetail());
        holder.type.setText(item.getReportType());

        BaseConfig.Glide_LoadImageView( holder.image, item.getImagePath() );
        holder.card_view.setOnClickListener(v -> {
            Builder builder = new Builder(v.getContext());

            builder.setTitle("Confirm");
            builder.setMessage("Do you want to remove?");
            builder.setPositiveButton("Ok", (dialog, which) -> {
                FileAdapter.deleteContacts(item.getId(), v.getContext());

                this.fileGetSets.remove(position);
                this.recyclerView.setAdapter(new FileAdapter(this.fileGetSets, this.recyclerView));
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {

            });
            AlertDialog alertDialog = builder.create();

            alertDialog.show();


        });

    }

    @Override
    public final int getItemCount() {
        return this.fileGetSets.size();
    }

    private static final void deleteContacts(String id, Context context) {


        SQLiteDatabase db = BaseConfig.GetDb();//context);

        db.execSQL("DELETE FROM contacts where Id='" + id + '\'');
        db.close();
    }

    static class MyViewHolder extends ViewHolder {
        final TextView name;
        final TextView type;
        final ImageView image;
        final CardView card_view;

        MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(id.upload_file_details);
            this.type = itemView.findViewById(id.upload_filetype);
            this.image = itemView.findViewById(id.upload_image);
            this.card_view = itemView.findViewById(id.card_view);


        }
    }

}
