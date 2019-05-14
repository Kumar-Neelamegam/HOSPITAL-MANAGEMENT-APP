package kdmc_kumar.Adapters_GetterSetter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.color;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.DateTimelineRowitemAdapter.MyViewHolder;

public class DateTimelineRowitemAdapter extends Adapter<MyViewHolder>  {


    List<Timeline_Objects> objects;
    Context context;
    LayoutInflater layoutInflater;

    private static TextView selectedView;
    private static LinearLayout linearLayout;

    DateTimelineRowitemAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onItemClick(DateTimelineRowitemAdapter.MyViewHolder view, int position, List<Timeline_Objects> items);
    }

    public void setOnItemClickListener(DateTimelineRowitemAdapter.OnItemClickListener mItemClickListener) {
        mOnItemClickListener = mItemClickListener;
    }


    public DateTimelineRowitemAdapter(Context context, List<Timeline_Objects> objects) {
        this.context = context;
        this.objects = objects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(DateTimelineRowitemAdapter.MyViewHolder holder, int position) {

        String VISITED_DATE= "";
        String UNIQUE_ID ="";

        VISITED_DATE = this.objects.get(position).getUnique_Id();
        UNIQUE_ID = this.objects.get(position).getVisitedDate();

//        Log.e("VISITED DATE: ", VISITED_DATE);
//        Log.e("UNIQUE ID: ",UNIQUE_ID);

        holder.txtvwYear.setText(VISITED_DATE);

        try
        {
            String date = UNIQUE_ID;
            SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            Date newDate=spf.parse(date);
            spf= new SimpleDateFormat("dd-MMM");
            date = spf.format(newDate);
            System.out.println(date);

            holder.txtvwDaymonth.setText(String.format("%s-%s", date, this.objects.get(position).getVisitedDate().substring(0, 4)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.fab.setOnClickListener(view -> {
            this.selectItem(view, holder);
            this.mOnItemClickListener.onItemClick(holder, position, this.objects);
        });



        if(position==0)
        {
            holder.fab.performClick();
        }


    }

    private void selectItem(View view, DateTimelineRowitemAdapter.MyViewHolder holder) {
        //If FirstTime Checked
        if (DateTimelineRowitemAdapter.selectedView ==null) {

            DateTimelineRowitemAdapter.selectedView =holder.txtvwDaymonth;
            DateTimelineRowitemAdapter.linearLayout =holder.linearLayout;

            DateTimelineRowitemAdapter.selectedView.setBackgroundColor(Color.parseColor("#0a4991"));
            DateTimelineRowitemAdapter.selectedView.setTextColor(Color.parseColor("#ffffff"));
            DateTimelineRowitemAdapter.linearLayout.setBackgroundColor(view.getContext().getResources().getColor(color.colorPrimaryDark));

        }else
        {
            //Already Selected set White
            DateTimelineRowitemAdapter.selectedView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            DateTimelineRowitemAdapter.selectedView.setTextColor(Color.parseColor("#ffffff"));
            DateTimelineRowitemAdapter.linearLayout.setBackgroundColor(view.getContext().getResources().getColor(color.colorPrimary));


            //New Initilize to set Control
            DateTimelineRowitemAdapter.selectedView =holder.txtvwDaymonth;
            DateTimelineRowitemAdapter.linearLayout =holder.linearLayout;

            DateTimelineRowitemAdapter.selectedView.setBackgroundColor(Color.parseColor("#0a4991"));
            DateTimelineRowitemAdapter.selectedView.setTextColor(Color.parseColor("#ffffff"));
            DateTimelineRowitemAdapter.linearLayout.setBackgroundColor(view.getContext().getResources().getColor(color.colorPrimaryDark));
        }
    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    @Override
    public DateTimelineRowitemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout.date_timeline_rowitem, parent, false);

        return new DateTimelineRowitemAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends ViewHolder {

        @BindView(id.txtvw_daymonth)
        public TextView txtvwDaymonth;
        @BindView(id.fab)
        public ImageView fab;
        @BindView(id.txtvw_year)
        public TextView txtvwYear;
        @BindView(id.linearlayout)
        public LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }



}
