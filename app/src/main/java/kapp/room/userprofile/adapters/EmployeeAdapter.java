package kapp.room.userprofile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import kapp.room.userprofile.R;
import kapp.room.userprofile.db.entity.Employees;


/**
 * Created by Arunraj on 3/2/2018.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder> {

    List<Employees> list;
    Context context;
    public EmployeeAdapter(Context context, List<Employees> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public EmployeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmployeeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item_list, null));
    }

    @Override
    public void onBindViewHolder(EmployeeHolder holder, int position) {

        //holder.imageView.setImageBitmap(ImageUtils.byteToBitmap(list.get(position).getEmpPhoto()));
        Glide
                .with(context)
                .load(list.get(position).getEmpPhoto())
                .into(holder.imageView);
        holder.txtName.setText(list.get(position).getEmpFirstName() + " " + list.get(position).getEmpLastName());
        holder.txtAatharNumber.setText(list.get(position).getAadharNumber());
        holder.txtQualification.setText(list.get(position).getQualification());
        holder.txtDept.setText(list.get(position).getDeptName()+"");

        SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        holder.txtDate.setText(form.format(list.get(position).getEmpBirthday()));
        holder.txtAddress.setText(list.get(position).getAddress());
        holder.txtDistrict.setText(list.get(position).getDistrict());
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addItems(List<Employees> employees) {
        list = employees;
        notifyDataSetChanged();
    }

    public static class EmployeeHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtName, txtAatharNumber, txtDate, txtQualification, txtDept, txtAddress, txtDistrict;

        public EmployeeHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAatharNumber = itemView.findViewById(R.id.txtAatharNumber);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtQualification = itemView.findViewById(R.id.txtQualification);
            txtDept = itemView.findViewById(R.id.txtDept);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtDistrict = itemView.findViewById(R.id.txtDistrict);
        }
    }

}
