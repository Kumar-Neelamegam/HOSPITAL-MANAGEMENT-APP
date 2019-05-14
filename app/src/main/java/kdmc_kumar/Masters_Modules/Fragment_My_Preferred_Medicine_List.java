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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.Fav_MedicineList;
import kdmc_kumar.Adapters_GetterSetter.FavMedicineRecylerAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

public class Fragment_My_Preferred_Medicine_List extends Fragment {


    @BindView(id.recyler_View)
    RecyclerView recyclerView;

    View root_view;
    private GridLayoutManager gridLayoutManager;
    public Fragment_My_Preferred_Medicine_List() {
    }

    public static Fragment_My_Preferred_Medicine_List newInstance() {
        Fragment_My_Preferred_Medicine_List fragment = new Fragment_My_Preferred_Medicine_List();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.root_view = inflater.inflate(layout.kdmc_layout_masters_main_recyler, container, false);

        ButterKnife.bind(this, this.root_view);


        this.gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        this.gridLayoutManager.setSpanCount(1);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.gridLayoutManager);

        this.LoadRecyclerView();

        return this.root_view;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////


    private final void LoadRecyclerView() {


        ArrayList<Fav_MedicineList> Fav_MedicineList = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();

        String Value_testname, Value_Id;

        Cursor c = db.rawQuery("select distinct medicine,id from NewMedicine where Isupdate='1';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    Value_Id = c.getString(c.getColumnIndex("id"));
                    Value_testname = c.getString(c.getColumnIndex("medicine"));
                    Fav_MedicineList item = new Fav_MedicineList(Value_testname, Value_Id);
                    Fav_MedicineList.add(item);

                } while (c.moveToNext());
            }
        }


        FavMedicineRecylerAdapter FavRecylerAdapter = new FavMedicineRecylerAdapter(Fav_MedicineList);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.recyclerView.getContext(), 2));
        this.recyclerView.setAdapter(FavRecylerAdapter);

        c.close();
        db.close();

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

}
