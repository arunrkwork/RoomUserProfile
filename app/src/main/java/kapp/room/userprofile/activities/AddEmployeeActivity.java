package kapp.room.userprofile.activities;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kapp.room.userprofile.R;
import kapp.room.userprofile.adapters.DeptSpinnerAdapter;
import kapp.room.userprofile.crop.CropImage;
import kapp.room.userprofile.crop.CropImageView;
import kapp.room.userprofile.db.entity.Dept;
import kapp.room.userprofile.db.entity.Employee;
import kapp.room.userprofile.db.viewmodel.UIViewModel;
import kapp.room.userprofile.dialogs.DatePickerFragment;
import kapp.room.userprofile.utills.ImageUtils;


public class AddEmployeeActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener{

    private UIViewModel mUIViewModel;
    private Employee mEmployee;
    private List<Dept> deptList;
    Spinner spDept;
    EditText edFirstName, edLastName, edDate, edAatharNumber, edQualification, edAddress,
            edPhotoName, edDistrict;
    ImageView btnImagDatePicker, btnImgPhotoPicker, imgPhoto;
    FloatingActionButton fabAdd;
    Date date = new Date();
    DeptSpinnerAdapter mDeptSpinnerAdapter;
    String selectedDeptName = "";
    int selectedDeptId = 0;
    byte[] empPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        deptList = new ArrayList<>();
        mUIViewModel = ViewModelProviders.of(this).get(UIViewModel.class);

        spDept = findViewById(R.id.spDept);
        spDept.setOnItemSelectedListener(this);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edDate = findViewById(R.id.edDate);
        edPhotoName = findViewById(R.id.edPhotoName);
        edAatharNumber = findViewById(R.id.edAatharNumber);
        edQualification = findViewById(R.id.edQualification);
        edAddress = findViewById(R.id.edAddress);
        edDistrict = findViewById(R.id.edDistrict);
        btnImagDatePicker = findViewById(R.id.btnImgDatePicker);
        btnImagDatePicker.setOnClickListener(this);
        btnImgPhotoPicker = findViewById(R.id.btnImgPhotoPicker);
        btnImgPhotoPicker.setOnClickListener(this);
        imgPhoto = findViewById(R.id.imgPhoto);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);

        mDeptSpinnerAdapter = new DeptSpinnerAdapter(this, R.layout.dept_item_list,
                R.id.txtDeptName, deptList);
        spDept.setAdapter(mDeptSpinnerAdapter);

        mUIViewModel.getAllDept().observe(this, new Observer<List<Dept>>() {
            @Override
            public void onChanged(@Nullable List<Dept> list) {
                deptList.addAll(list);
                mDeptSpinnerAdapter.addItem(list);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == fabAdd) {
            Employee employee = new Employee();
            employee.setEmpFirstName(getValue(edFirstName));
            employee.setEmpLastName(getValue(edLastName));
            employee.setEmpBirthday(new Date());
            employee.setAadharNumber(getValue(edAatharNumber));
            employee.setQualification(getValue(edQualification));
            employee.setEmpPhoto(empPhoto != null ? empPhoto : null);
            employee.setEmpBirthday(date);
            employee.setDeptId(selectedDeptId);
            employee.setAddress(getValue(edAddress));
            employee.setDistrict(getValue(edDistrict));
            addEmployee(employee);
        } else if (v == btnImagDatePicker) {
            showDatePicker();
        } else if (v == btnImgPhotoPicker) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imgPhoto.setImageURI(resultUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    empPhoto = ImageUtils.bitmapToByte(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, ""
                        +error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date.setTime(calendar.getTimeInMillis());
            edDate.setText(String.valueOf(year) + "-" + s2d(monthOfYear + 1)
                    + "-" + s2d(dayOfMonth));
        }
    };

    private String s2d(int num) {
        return (num < 10 ? "0" : "") + num;
    }


    private String getValue(EditText editText) {
        String string = editText.getText().toString();
        if (TextUtils.isEmpty(string))
            return "";
        else return string;
    }

    private void addEmployee(Employee employee) {
        long insert = mUIViewModel.insertEmployee(employee);
        if (insert != -1) finish();
        Toast.makeText(this, "Employee : " + insert, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Dept dept = (Dept) parent.getItemAtPosition(position);
        selectedDeptId = dept.getDeptId();
        selectedDeptName = dept.getDeptName();
        Toast.makeText(this, "" + selectedDeptId + " " + selectedDeptName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Dept dept = (Dept) parent.getItemAtPosition(0);
        selectedDeptId = dept.getDeptId();
        selectedDeptName = dept.getDeptName();
        Toast.makeText(this, "" + selectedDeptId + " " + selectedDeptName, Toast.LENGTH_SHORT).show();
    }
}
