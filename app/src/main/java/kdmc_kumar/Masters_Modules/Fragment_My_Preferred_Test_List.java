package kdmc_kumar.Masters_Modules;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.drawable;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import displ.mobydocmarathi.com.R.string;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.Fav_TestList;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Masters_Modules.PreferredTestTemplate.Item;
import kdmc_kumar.Masters_Modules.PreferredTestTemplate.ItemClickListener;
import kdmc_kumar.Masters_Modules.PreferredTestTemplate.Section;
import kdmc_kumar.Masters_Modules.PreferredTestTemplate.SectionedExpandableLayoutHelper;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;

public class Fragment_My_Preferred_Test_List extends Fragment  {

    View root_view;

    @BindView(id.recyler_View)
    RecyclerView recyclerView;

    ArrayList<Fav_TestList> Fav_TestList;
    private SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;
    private ArrayList<Item> arrayList;

    public Fragment_My_Preferred_Test_List() {
    }

    public static Fragment_My_Preferred_Test_List newInstance() {
        Fragment_My_Preferred_Test_List fragment = new Fragment_My_Preferred_Test_List();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.root_view = inflater.inflate(layout.kdmc_layout_masters_main_recyler, container, false);
        ButterKnife.bind(this, this.root_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        gridLayoutManager.setSpanCount(1);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(gridLayoutManager);


        this.LoadSectionRecycler();


        return this.root_view;
    }


    private final void LoadSectionRecycler() {
        SQLiteDatabase db = BaseConfig.GetDb();
        //setting the recycler view
        this.sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this.getActivity(), this.recyclerView, this.itemClickListener, 3);

        String  Value_Id;

        Cursor c = db.rawQuery("select distinct TemplateName,id from MyFavTest where Isupdate='1' group by TemplateName;", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    Value_Id = c.getString(c.getColumnIndex("id"));
                    String TemplateName = c.getString(c.getColumnIndex("TemplateName"));
                    this.sectionedExpandableLayoutHelper.addSection(TemplateName, this.getTestTemplates(TemplateName));

                } while (c.moveToNext());
            }
        }

        c.close();

        this.sectionedExpandableLayoutHelper.notifyDataSetChanged();

        db.close();


    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    private final ArrayList<Item> getTestTemplates(String TemplateName) {


        this.arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = BaseConfig.GetDb();

        Cursor cursor = sqLiteDatabase.rawQuery("select distinct TemplateName,Testname,SubTest,id from MyFavTest where Isupdate='1' and TemplateName='" + TemplateName + "' order by id desc", null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    //String years=cursor.getString(cursor.getColumnIndex("YearPublished"));

                    this.arrayList.add(new
                            Item(cursor.getString(cursor.getColumnIndex("Testname")),
                            cursor.getString(cursor.getColumnIndex("SubTest")),
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")))));


                } while (cursor.moveToNext());

            }

        }

        cursor.close();
        sqLiteDatabase.close();

        return this.arrayList;


    }


    ItemClickListener itemClickListener= new ItemClickListener() {
        @Override
        public final void itemClicked(Item item) {

            //Toast.makeText(getActivity(), "Item: " + item.getId() + '/' + item.getTestName() + " clicked", Toast.LENGTH_SHORT).show();


            new CustomKDMCDialog(Fragment_My_Preferred_Test_List.this.getActivity())
                    .setLayoutColor(color.red_500)
                    .setImage(drawable.ic_delete_forever_black_24dp)
                    .setTitle(Fragment_My_Preferred_Test_List.this.getActivity().getString(string.information))
                    .setDescription("Are you sure want to delete\nthis test template?")
                    .setPossitiveButtonTitle("Yes, delete it")
                    .setNegativeButtonTitle("No")
                    .setOnPossitiveListener(() -> {
                        this.LoadDelete(item.getId());

                        new CustomKDMCDialog(Fragment_My_Preferred_Test_List.this.getActivity())
                                .setLayoutColor(color.green_500)
                                .setImage(drawable.ic_success_done)
                                .setTitle("OK").setNegativeButtonVisible(View.GONE)
                                .setDescription("Deleted")
                                .setPossitiveButtonTitle(Fragment_My_Preferred_Test_List.this.getActivity().getString(string.ok));

                        Fragment_My_Preferred_Test_List.this.LoadSectionRecycler();
                    });
        }

        private  final void LoadDelete(int Id) {
            try {
                SQLiteDatabase db = BaseConfig.GetDb();
                db.execSQL("delete from MyFavTest where Id='" + Id + '\'');
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public final void itemClicked(Section section) {

            Toast.makeText(Fragment_My_Preferred_Test_List.this.getActivity(), "Section: " + section.getName() + " clicked", Toast.LENGTH_SHORT).show();

        }
    };
}
