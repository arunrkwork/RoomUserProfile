package kapp.room.userprofile.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import kapp.room.userprofile.R;
import kapp.room.userprofile.db.entity.Dept;

/**
 * Created by Arunraj on 3/6/2018.
 */

public class DeptSpinnerAdapter extends ArrayAdapter<Dept> {

    LayoutInflater flater;
    List<Dept> deptList;

    public DeptSpinnerAdapter(Context context, int resource, int textView, List<Dept> deptList) {
        super(context, resource, textView, deptList);
        this.deptList = deptList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        Dept rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.dept_item_list, null, false);

            holder.txtDeptName = (TextView) rowview.findViewById(R.id.txtDeptName);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
        holder.txtDeptName.setText(rowItem.getDeptName());

        return rowview;
    }

    public void addItem(List<Dept> list) {
        deptList.addAll(list);
        notifyDataSetChanged();
    }

    private class viewHolder{
        TextView txtDeptName;
    }
}
