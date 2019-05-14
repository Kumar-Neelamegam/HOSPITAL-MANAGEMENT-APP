package kdmc_kumar.Masters_Modules;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.anim;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Masters_Modules.templates_list.TemplateRecylerAdapter.MyViewHolder;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;

public class templates_list extends AppCompatActivity implements OnClickListener {


    protected static final int RESULT_SPEECH = 1;

    private ImageView imgNoMedia;
    ArrayAdapter<CommonDataObjects.RowItem> adapter;

    private ListView listView;
    private List<CommonDataObjects.RowItem> rowItems;

    private RecyclerView recyler_view;
    private GridLayoutManager gridLayoutManager;

    private templates_list.TemplateRecylerAdapter templateRecylerAdapter;

    private Toolbar toolbar;

    private ImageView back;
    private ImageView home;
    private ImageView exit;
    private ImageView add_template;

    public templates_list() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.templates_list);

        this.toolbar = this.findViewById(id.toolbar);

        this.back = this.toolbar.findViewById(id.ic_back);
        this.home = this.toolbar.findViewById(id.ic_home);
        this.exit = this.toolbar.findViewById(id.ic_exit);
        this.add_template = this.toolbar.findViewById(id.ic_add);
        this.back.setOnClickListener(this);
        this.home.setOnClickListener(this);
        this.exit.setOnClickListener(this);

        this.add_template.setOnClickListener(view -> {

            finish();
            Intent add = new Intent(this.getApplicationContext(), templates_addnew.class);
            BaseConfig.temp_flag = "True";
            this.startActivity(add);

        });

        this.recyler_view = this.findViewById(id.recyler_view);

        this.gridLayoutManager = new GridLayoutManager(this, 2);
        this.gridLayoutManager.setSpanCount(1);
        this.recyler_view.setHasFixedSize(true);
        this.recyler_view.setLayoutManager(this.gridLayoutManager);


        BaseConfig.welcometoast = 0;

        this.rowItems = new ArrayList<>();

        this.SelectedGetPatientDetails();

        this.imgNoMedia = this.findViewById(id.imgNoMedia);
        this.imgNoMedia.setVisibility(View.GONE);
        this.listView = this.findViewById(id.list);


    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // =================================
    private final void SelectedGetPatientDetails() {
        ArrayList<templates_list.TemplateGetSet> templateGetSets = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();
        String pimg64 = "";
        int i = 1;

        Cursor c = db.rawQuery("select id,TemplateId,TemplateName from TemplateDtls group by TemplateName;", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String pdtls = c.getString(c.getColumnIndex("TemplateName"));


                    Bitmap img = BitmapFactory.decodeResource(this.getResources(), drawable.medical_history_icon);

                    // RowItem item = new RowItem(img, pdtls, "", "");
                    //rowItems.add(item);

                    templateGetSets.add(new templates_list.TemplateGetSet(c.getString(c.getColumnIndex("TemplateName")), c.getString(c.getColumnIndex("id"))));


                    ++i;

                } while (c.moveToNext());
            }
        }

        this.listView = this.findViewById(id.list);

        //adapter = new CustomListViewAdapter(this, R.layout.list_item, rowItems);
        //listView.setAdapter(adapter);


        this.templateRecylerAdapter = new templates_list.TemplateRecylerAdapter(templateGetSets);

        this.recyler_view.setLayoutManager(new GridLayoutManager(this.recyler_view.getContext(), 2));
        this.recyler_view.setAdapter(this.templateRecylerAdapter);

        c.close();

    }

    @Override
    public final void onClick(View view) {
        switch (view.getId()) {
            case id.ic_back:
                finish();
                Intent task1 = new Intent(this.getApplicationContext(), Masters_New.class);
                this.startActivity(task1);
                break;
            case id.ic_home:
                finish();
                Intent task = new Intent(this.getApplicationContext(), Dashboard_NavigationMenu.class);
                this.startActivityForResult(task, 500);
                this.overridePendingTransition(anim.abc_slide_in_top,
                        anim.abc_slide_in_top);
                break;
            case id.ic_exit:


                BaseConfig.ExitSweetDialog(this, null);


                break;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////

    static class TemplateGetSet {
        String name;
        String sno;

        TemplateGetSet(String name, String sno) {
            this.name = name;
            this.sno = sno;
        }

        final String getName() {
            return this.name;
        }

        public final void setName(String name) {
            this.name = name;
        }

        final String getSno() {
            return this.sno;
        }

        public final void setSno(String sno) {
            this.sno = sno;
        }
    }

    public class TemplateRecylerAdapter extends Adapter<MyViewHolder> {
        ArrayList<templates_list.TemplateGetSet> templateGetSets = new ArrayList<>();

        TemplateRecylerAdapter(ArrayList<templates_list.TemplateGetSet> templateGetSets) {
            this.templateGetSets = templateGetSets;
        }

        @NonNull
        @Override
        public final templates_list.TemplateRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layout.template_list_row, parent, false);

            return new templates_list.TemplateRecylerAdapter.MyViewHolder(view);
        }

        @Override
        public final void onBindViewHolder(@NonNull templates_list.TemplateRecylerAdapter.MyViewHolder holder, int position) {

            templates_list.TemplateGetSet item = this.templateGetSets.get(position);

            holder.sno.setText(item.getSno());
            //holder.template_name.setText("" + item.getName().split(":")[1]);
            holder.template_name.setText(item.getName());

            holder.card_view.setOnClickListener(v -> {
                BaseConfig.selectedtemplate = item.getName();
                BaseConfig.temp_flag = "false";
                finish();
                Intent lib = new Intent(v.getContext(), templates_addnew.class);
                templates_list.this.startActivity(lib);
            });

            holder.Delete.setOnClickListener(view -> {


                // Toast.makeText(templates_list.this, "Delete clicked" + item.getSno(), Toast.LENGTH_SHORT).show();


                new CustomKDMCDialog(templates_list.this)
                        .setLayoutColor(color.orange_500)
                        .setImage(drawable.ic_warning_black_24dp)
                        .setTitle(getString(string.close_online))
                        .setDescription("Are you sure want to delete?")
                        .setPossitiveButtonTitle(getString(string.yes))
                        .setNegativeButtonTitle(getString(string.no))
                        .setOnPossitiveListener(() -> {

                            try {
                                SQLiteDatabase db = BaseConfig.GetDb();
                                db.execSQL("delete from TemplateDtls where TemplateName='" + item.getName() + '\'');
                                db.close();

                                templates_list.this.SelectedGetPatientDetails();

                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            new CustomKDMCDialog(templates_list.this).setNegativeButtonVisible(View.GONE)
                                    .setImage(drawable.ic_success_done)
                                    .setTitle("Deleted").setNegativeButtonVisible(View.GONE)
                                    .setPossitiveButtonTitle("OK");


                        });


            });


        }

        @Override
        public final int getItemCount() {
            return this.templateGetSets.size();
        }

        public class MyViewHolder extends ViewHolder {
            final TextView sno;
            final TextView template_name;
            final CardView card_view;
            final ImageView Delete;

            MyViewHolder(View itemView) {
                super(itemView);
                this.sno = itemView.findViewById(id.serial_no);
                this.template_name = itemView.findViewById(id.template_name);
                this.card_view = itemView.findViewById(id.card_view);
                this.Delete = itemView.findViewById(id.ic_delete);
            }
        }
    }


}
