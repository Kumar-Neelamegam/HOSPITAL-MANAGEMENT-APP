package kdmc_kumar.Webservices_NodeJSON.Investigation_TestList;

import android.R.layout;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.drawable;
import kdmc_kumar.Core_Modules.BaseConfig;

public class Investigation_ExpandableTestList extends ExpandableListActivity //implements OnChildClickListener,ListView.OnScrollListener
{


    NewAdapter listAdapter;
    ExpandableListView expListView;

    Bundle d;
    String MYFAVTEST = "";
    private final ArrayList<String> groupItem = new ArrayList<>();
    private final ArrayList<Object> childItem = new ArrayList<>();

    private final String[] mStrings;

    public Investigation_ExpandableTestList() {
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);


        ExpandableListView expandbleLis = this.getExpandableListView();
        expandbleLis.setDividerHeight(2);
        expandbleLis.setGroupIndicator(null);
        expandbleLis.setClickable(true);

        try {
            switch (BaseConfig.expandlstflag) {
                case 1:
                    this.setGroupData();
                    this.setChildGroupData();
                    break;
                case 2:
                    this.setGroupDataMyFav();
                    this.setChildGroupDataMyFav();

                    break;
                case 3:
                    this.setGroupDataAdvice();
                    this.setChildGroupDataAdvice();

                    break;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        NewAdapter mNewAdapter = new NewAdapter(this.groupItem, this.childItem);
        mNewAdapter.setInflater((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);

        this.getExpandableListView().setAdapter(mNewAdapter);
        expandbleLis.setOnChildClickListener(this);
        //expandbleLis.setFastScrollEnabled(true);

	/*	//test
         // Use an existing ListAdapter that will map an array
        // of strings to TextViews
		expandbleLis.setOnScrollListener(this);
		 mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDialogText = (TextView) inflate.inflate(R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler.post(new Runnable() {

			public void run() {
				mReady = true;
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.TYPE_APPLICATION,
						WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
								| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
						PixelFormat.TRANSLUCENT);
				mWindowManager.addView(mDialogText, lp);
			}
		});*/
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
  /*  private void removeWindow() {
        if (mShowing) {
        	
            mShowing = false;
            mDialogText.setVisibility(View.INVISIBLE);
        }
    }
	
	
    @Override
    protected void onResume() {
        super.onResume();
        mReady = true;
    }

    
    @Override
    protected void onPause() {
        super.onPause();
        removeWindow();
        mReady = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mDialogText);
        mReady = false;
    }

    
   
    
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if (mReady) {
        
        	mStrings=new String[groupItem.size()];
        	mStrings = groupItem.toArray(mStrings);
        	//Log.e(mStrings.toString());
            char firstLetter = mStrings[firstVisibleItem].charAt(0);
            
            if (!mShowing && firstLetter != mPrevLetter) {

                mShowing = true;
                mDialogText.setVisibility(View.VISIBLE);
            }
            mDialogText.setText(((Character)firstLetter).toString());
            mHandler.removeCallbacks(mRemoveWindow);
            mHandler.postDelayed(mRemoveWindow, 3000);
            mPrevLetter = firstLetter;
        }
    }
    

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
    */

    private final void setGroupData() {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select distinct Testname from testname where IsActive='1' order by Testname;", null);

        List<String> list = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {


                do {

                    String pname = c.getString(c.getColumnIndex("Testname"));

                    this.groupItem.add(pname);

                } while (c.moveToNext());
            }
        }

        db.close();
        c.close();
    }


    private final void setChildGroupData() {

        ArrayList<String> child = new ArrayList<>();

        for (int i = 0; i < this.groupItem.size(); i++) {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select SubTest from testname where Testname='" + this.groupItem.get(i) + "' and IsActive='1' order by SubTest", null);

            child = new ArrayList<>();
            //List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String pname = c.getString(c.getColumnIndex("SubTest"));

                        child.add(pname);

                    } while (c.moveToNext());

                    this.childItem.add(child);
                }
            }

            //new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, list);

            db.close();
            c.close();


        }

    }


    private final void setGroupDataMyFav() {


        SQLiteDatabase db = BaseConfig.GetDb();//);
        //Cursor c = db.rawQuery("select distinct (TemplateName||' | '||Testname)as test from myfavTest order by Testname;", null);

        Cursor c = db.rawQuery("select distinct TemplateName from myfavTest group by TemplateName;", null);

        List<String> list = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    // String TestName = c.getString(c.getColumnIndex("Testname"));
                    //String TemplateName = c.getString(c.getColumnIndex("TemplateName"));

                    this.groupItem.add(c.getString(c.getColumnIndex("TemplateName")));

                } while (c.moveToNext());
            }
        }

        new ArrayAdapter<>(this, layout.simple_list_item_1, list);

        db.close();
        c.close();

    }


    private final void setChildGroupDataMyFav() {

        ArrayList<String> child = new ArrayList<>();

        for (int i = 0; i < this.groupItem.size(); i++) {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select (Testname||' / '||Subtest)as data from myfavTest where TemplateName='" + this.groupItem.get(i) + '\'', null);

            child = new ArrayList<>();
            //List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String pname = c.getString(c.getColumnIndex("data"));

                        child.add(pname);

                    } while (c.moveToNext());

                    this.childItem.add(child);
                }
            }

            //new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);

            db.close();
            c.close();


        }

    }


    private final void setGroupDataAdvice() {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select distinct Mfuladv from fulladv order by Mfuladv;", null);

        List<String> list = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    String pname = c.getString(c.getColumnIndex("Mfuladv"));

                    this.groupItem.add(pname);

                } while (c.moveToNext());
            }
        }

        new ArrayAdapter<>(this,
                layout.simple_list_item_1, list);

        db.close();
        c.close();

    }


    private final void setChildGroupDataAdvice() {

        ArrayList<String> child = new ArrayList<>();

        for (int i = 0; i < this.groupItem.size(); i++) {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery("select fulladvice from fulladv where Mfuladv='" + this.groupItem.get(i) + "'order by fulladvice", null);

            child = new ArrayList<>();
            //List<String> list = new ArrayList<String>();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String pname = c.getString(c.getColumnIndex("fulladvice"));

                        child.add(pname);

                    } while (c.moveToNext());

                    this.childItem.add(child);
                }
            }

            db.close();
            c.close();


        }

    }
	


    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater();

        Investigation_ExpandableTestList.CreateMenu(menu);
        return true;
    }

    private static void CreateMenu(Menu menu) {


        MenuItem item0 = menu.add(0, 0, 0, "Item 0");
        {

            item0.setIcon(drawable.prev_icon);
            item0.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            item0.setTitle("Cancel");

        }
        MenuItem item1 = menu.add(0, 1, 1, "Item 1");
        {

            item1.setIcon(drawable.icon_save_control);
            item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            item1.setTitle("Save");

        }

    }

    //#######################################################################################################
    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {

        return this.MenuSelecciona(item);

    }

    private boolean MenuSelecciona(MenuItem item) {

        switch (item.getItemId()) {

            case 0:

                Investigation_ExpandableTestList.LoadDeleteTempTest();
                finish();


                return true;


            case 1:
                Bundle b = new Bundle();
                b.putString("status", "done");
                Intent intent = new Intent();
                intent.putExtras(b);
                this.setResult(Activity.RESULT_OK, intent);
                this.finish();

                finish();
                return true;

        }
        return false;
    }

    //#######################################################################################################
    private static final void LoadDeleteTempTest() {
        String CREATE_TABLE_TempTest = "Delete from TempTest;";
        BaseConfig.SaveData(CREATE_TABLE_TempTest);
    }

    //#######################################################################################################
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
		/*	LoadDeleteTempTest();
			Investigation_ExpandableTestList.this.finish();*/

    }
	/*	//#######################################################################################################
		*//*
  The List row creator
 *//*
		class MyListAdaptor extends ArrayAdapter<String> implements SectionIndexer, ExpandableListAdapter {

			HashMap<String, Integer> alphaIndexer;
			String[] sections;

			public MyListAdaptor(Context context, List<String> items) 
			{
				super(context, R.layout.grouprow, items);

				alphaIndexer = new HashMap<String, Integer>();
				int size = items.size();

				for (int x = 0; x < size; x++) {
					String s = items.get(x);
					// get the first letter of the store
					String ch = s.substring(0, 1);
					// convert to uppercase otherwise lowercase a -z will be sorted
					// after upper A-Z
					ch = ch.toUpperCase();
					// put only if the key does not exist
					if (!alphaIndexer.containsKey(ch))
						alphaIndexer.put(ch, x);
				}

				Set<String> sectionLetters = alphaIndexer.keySet();
				// create a list from the set to sort
				ArrayList<String> sectionList = new ArrayList<String>(
						sectionLetters);
				Collections.sort(sectionList);
				sections = new String[sectionList.size()];
				sections = sectionList.toArray(sections);
			}

			@Override
			public int getPositionForSection(int section) {
				return alphaIndexer.get(sections[section]);
			}

			@Override
			public int getSectionForPosition(int position) {
				return 0;
			}

			@Override
			public Object[] getSections() {
				return sections;
			}

			@Override
			public int getGroupCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onGroupExpanded(int groupPosition) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGroupCollapsed(int groupPosition) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public long getCombinedChildId(long groupId, long childId) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getCombinedGroupId(long groupId) {
				// TODO Auto-generated method stub
				return 0;
			}
		}
		*/
}
