package kdmc_kumar.Webservices_NodeJSON.Investigation_TestList;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Core_Modules.BaseConfig;


class NewAdapter extends BaseExpandableListAdapter {
    public ArrayList<String> AllSubtestParents = new ArrayList<>();
    private ArrayList<String> groupItem;
    private ArrayList<String> tempChild;
    private ArrayList<Object> Childtem = new ArrayList<>();
    private LayoutInflater minflater;
    private Activity activity;
    // Hashmap for keeping track of our checkbox check states
    private HashMap<Integer, boolean[]> mChildCheckStates;
    private Context context;

    public NewAdapter(Context context) {
        this.context = context;

    }

    public NewAdapter(ArrayList<String> grList, ArrayList<Object> childItem) {
        this.groupItem = grList;
        Childtem = childItem;
        // Initialize our hashmap containing our check states here
        this.mChildCheckStates = new HashMap<>();
    }

    public final void setInflater(LayoutInflater mInflater, Activity act) {
        minflater = mInflater;
        this.activity = act;
    }

    @Nullable
    @Override
    public final Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public final long getChildId(int i, int i1) {
        return 0L;
    }

    //CheckBox chkbx;
    @Override
    public final View getChildView(int i, int i1,
                                   boolean b, View view, ViewGroup viewGroup) {

        View convertView1 = view;
        this.tempChild = new ArrayList<>();
        this.tempChild = (ArrayList<String>) this.Childtem.get(i);


	/*	if (convertView == null)
        {

			convertView = minflater.inflate(R.layout.childrow, null);

		}
		text = (TextView) convertView.findViewById(R.id.textView1);
		chkbx=(CheckBox)convertView.findViewById(R.id.checkBox111);

		text.setText(tempChild.get(childPosition));
*/

        NewAdapter.ViewHolder holder;

        if (convertView1 == null) {

            convertView1 = this.minflater.inflate(layout.childrow, viewGroup, false);
            holder = new NewAdapter.ViewHolder();
            holder.checkbox = convertView1.findViewById(id.checkBox111);
            convertView1.setTag(holder);
        } else {
            holder = (NewAdapter.ViewHolder) convertView1.getTag();
        }


        holder.checkbox.setOnCheckedChangeListener(null);

        if (this.mChildCheckStates.containsKey(Integer.valueOf(i))) {

            /*
             * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
             * the value of the parent view (group) of this child (aka, the key),
             * then retrive the boolean array getChecked[]
            */
            boolean getChecked[] = this.mChildCheckStates.get(Integer.valueOf(i));

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            holder.checkbox.setChecked(getChecked[i1]);

        } else {

            /*
            * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
            * contain the value of the parent view (group) of this child (aka, the key),
            * (aka, the key), then initialize getChecked[] as a new boolean array
            *  and set it's size to the total number of children associated with
            *  the parent group
            */
            boolean getChecked[] = new boolean[this.getChildrenCount(i)];

            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
            this.mChildCheckStates.put(Integer.valueOf(i), getChecked);

            // set the check state of this position's checkbox based on the
            // boolean value of getChecked[position]
            holder.checkbox.setChecked(false);

        }

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {


            if (isChecked) {


                this.tempChild = (ArrayList<String>) this.Childtem.get(i);
                boolean getChecked[] = this.mChildCheckStates.get(Integer.valueOf(i));
                getChecked[i1] = isChecked;
                this.mChildCheckStates.put(Integer.valueOf(i), getChecked);
                String chkitem = this.tempChild.get(i1);

                //Log.e("Checked Item: ", chkitem);

                if (chkitem.contains(" / ")) {
                    String str_TestName = chkitem.split(" / ")[0];
                    String str_SubTestName = chkitem.split(" / ")[1];

                    //String Insert_TABLE_TempTest ="INSERT INTO TempTest (Test,SubTest) values('"+groupItem.get(groupPosition).toString()+"','"+chkitem.toString()+"');";
                    String Insert_TABLE_TempTest = "INSERT INTO TempTest (Test,SubTest) values('" + str_TestName + "','" + str_SubTestName + "');";
                    BaseConfig.SaveData(Insert_TABLE_TempTest);

                } else {
                    String Insert_TABLE_TempTest = "INSERT INTO TempTest (Test,SubTest) values('" + this.groupItem.get(i) + "','" + chkitem + "');";
                    BaseConfig.SaveData(Insert_TABLE_TempTest);
                }


                // set the check state of this position's checkbox based on the
                // boolean value of getChecked[position]
                holder.checkbox.setChecked(getChecked[i1]);

                // TODO: 8/22/2017 Checked AllTest to Add Array
                        /*if(chkitem.startsWith("All"))
                        {
                            Toast.makeText(activity, "All is enable", Toast.LENGTH_SHORT).show();

                            AllSubtestParents.add(groupItem.get(groupPosition).toString());
                        }
                        else
                        {
                            boolean isExist=false;
                            for (int i = 0; i < AllSubtestParents.size(); i++) {
                                if(groupItem.get(groupPosition).contains(AllSubtestParents.get(i).toString()))
                                {
                                    //Already All Test Entered

                                    isExist=true;
                                }

                            }
                            if (isExist) {

                                holder.checkbox.setChecked(false);
                                Toast.makeText(activity, "Already All subtest "+groupItem.get(groupPosition)+""+"is Added", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                holder.checkbox.setChecked(true);
                                Toast.makeText(activity, " subtest is Added", Toast.LENGTH_SHORT).show();
                            }

                        }*/


            } else {

                this.tempChild = (ArrayList<String>) this.Childtem.get(i);
                boolean getChecked[] = this.mChildCheckStates.get(Integer.valueOf(i));
                getChecked[i1] = isChecked;
                this.mChildCheckStates.put(Integer.valueOf(i), getChecked);

                String chkitem = this.tempChild.get(i1);

                if (chkitem.contains(" / ")) {
                    String str_TestName = chkitem.split(" / ")[0];
                    String str_SubTestName = chkitem.split(" / ")[1];

                    String Delete_TABLE_TempTest = "Delete from TempTest where Test='" + str_TestName + "' and SubTest='" + str_SubTestName + "';";
                    BaseConfig.SaveData(Delete_TABLE_TempTest);

                } else {
                    String Delete_TABLE_TempTest = "Delete from TempTest where Test='" + this.groupItem.get(i) + "' and SubTest='" + chkitem + "';";
                    BaseConfig.SaveData(Delete_TABLE_TempTest);
                }


                // TODO: 8/22/2017 Checked AllTest to Add Array
                        /*if(chkitem.startsWith("All"))
                        {
                            Toast.makeText(activity, "All is enable", Toast.LENGTH_SHORT).show();

                            AllSubtestParents.add(groupItem.get(groupPosition).toString());
                        }
                        else
                        {
                            boolean isExist=false;
                            for (int i = 0; i < AllSubtestParents.size(); i++) {
                                if(groupItem.get(groupPosition).contains(AllSubtestParents.get(i).toString()))
                                {
                                    //Already All Test Entered

                                    isExist=true;
                                }

                            }
                            if (isExist) {
                                Toast.makeText(activity, "Alread All subtest "+groupItem.get(groupPosition)+""+"is Added", Toast.LENGTH_SHORT).show();
                                //((CheckedTextView) finalConvertView).setChecked(false);
                                isChildSelectable(groupPosition,childPosition);

                            }
                            else
                            {
                                Toast.makeText(activity, " subtest is Added", Toast.LENGTH_SHORT).show();
                            }

                        }*/

            }
        });


        holder.checkbox.setText(this.tempChild.get(i1));


        return convertView1;
    }

    @Override
    public final int getChildrenCount(int i) {
        return ((ArrayList<String>) this.Childtem.get(i)).size();
    }

    @Nullable
    @Override
    public final Object getGroup(int i) {
        return null;
    }

    @Override
    public final int getGroupCount() {
        return this.groupItem.size();
    }

    @Override
    public final long getGroupId(int i) {
        return 0L;
    }

    @Override
    public final View getGroupView(int i, boolean b,
                                   View view, ViewGroup viewGroup) {
        View convertView1 = view;
        if (convertView1 == null) {
            convertView1 = this.minflater.inflate(layout.grouprow, null);


        }
        ((CheckedTextView) convertView1).setText(this.groupItem.get(i));
        ((CheckedTextView) convertView1).setChecked(b);
        return convertView1;
    }

    @Override
    public final boolean hasStableIds() {
        return false;
    }

    @Override
    public final boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class ViewHolder {
        TextView text;
        CheckBox checkbox;

        ViewHolder() {
        }
    }


}
