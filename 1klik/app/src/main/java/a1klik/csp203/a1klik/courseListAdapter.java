package a1klik.csp203.a1klik;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class courseListAdapter extends BaseAdapter {
    private Context mContext;
    private List<course> mCourseList;

    public courseListAdapter(Context mContext, List<course> mCourseList) {
        this.mContext = mContext;
        this.mCourseList = mCourseList;
    }

    @Override
    public int getCount() {

        return mCourseList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCourseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(mContext, R.layout.course_view,null);
        TextView courseName=(TextView)v.findViewById(R.id.course_id);
        TextView tmpid=(TextView)v.findViewById(R.id.id_number);
        courseName.setText(mCourseList.get(position).getCourseName());
        tmpid.setText(Integer.toString(mCourseList.get(position).getId()));
        v.setTag(mCourseList.get(position).getId());
        return v;
    }



}
