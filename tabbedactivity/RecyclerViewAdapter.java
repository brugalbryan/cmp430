package com.example.tabbedactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
{
    Context mContext;
    List<Course> mData;
    public List<Course> mDataSelect = new ArrayList<>();

    public RecyclerViewAdapter(Context mContext, List<Course> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_course, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.checkBox.setText(mData.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_item);
            checkBox.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            Course currentCourse = mData.get(this.getAdapterPosition());
            boolean isChecked = ((CheckBox)v).isChecked();
            // getOrCreateBadge();
            if(isChecked)
            {
                //check boolean ad
                //int num = MainActivity.badgeDrawable0.getNumber();
                //MainActivity.badgeDrawable0.setNumber(num+1);
                String courseName = currentCourse.getCourseName() ;
                String numCredits =currentCourse.getNumCredits() ;
                String reqCourse = currentCourse.getReqCourse();
                String courseProfs = currentCourse.getCourseProfs();
                String infos = "Course Name : "+courseName + "\n\n" + "Credits : " + numCredits + "\n\n" + "Pre-Requires : "+ reqCourse + "\n\n" + "Previous Professors : "+ courseProfs;
                showDialog(infos, LehmanCollege.switchCompat.isChecked());

                //course to each fragment
                mDataSelect.add(currentCourse);

                // add the course to the MainActivity set
                LehmanCollege.mCourseSelect.add(currentCourse);

            }
            else {

                mDataSelect.remove(currentCourse);

                // Remove the course from the MainActivity set
                LehmanCollege.mCourseSelect.remove(currentCourse);
            }

            if(!currentCourse.isAdvanced()) {
                LehmanCollege.badgeDrawable0.setNumber(mDataSelect.size());
            }
            else {
                LehmanCollege.badgeDrawable1.setNumber(mDataSelect.size());
            }

            // Total of classes selected
            int total = LehmanCollege.badgeDrawable0.getNumber() +  LehmanCollege.badgeDrawable1.getNumber();
            LehmanCollege.cardBadgeTextView.setText(total+"");
        }
    }
    private void showDialog(String msg, boolean b)
    {
        if(!b){
            FragmentActivity activity = (FragmentActivity)(mContext);
            FragmentManager fm = activity.getSupportFragmentManager();
            MyDialogFragment dialogFragment = new MyDialogFragment(msg);//DialogFragment
            dialogFragment.show(fm, "Mathias");
        }

    }
}
