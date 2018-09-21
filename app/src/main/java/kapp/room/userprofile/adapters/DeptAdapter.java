package kapp.room.userprofile.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kapp.room.userprofile.R;
import kapp.room.userprofile.db.entity.Dept;

/**
 * Created by Arunraj on 3/2/2018.
 */

public class DeptAdapter extends RecyclerView.Adapter<DeptAdapter.DeptHolder> {

    List<Dept> list;

    public DeptAdapter(List<Dept> list) {
        this.list = list;
    }

    @Override
    public DeptHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeptHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dept_item_list, null));
    }

    @Override
    public void onBindViewHolder(DeptHolder holder, int position) {
        holder.txtDeptName.setText(list.get(position).getDeptName());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class DeptHolder extends RecyclerView.ViewHolder {

        TextView txtDeptName;

        public DeptHolder(View itemView) {
            super(itemView);
            txtDeptName = itemView.findViewById(R.id.txtDeptName);
        }
    }

    public void addItems(List<Dept> mList){
        list = mList;
        notifyDataSetChanged();
    }

}
