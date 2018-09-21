package kapp.room.userprofile.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.graphics.Color;
import android.provider.CalendarContract;

import java.util.List;

import kapp.room.userprofile.db.Const;
import kapp.room.userprofile.db.entity.Employee;
import kapp.room.userprofile.db.entity.Employees;


/**
 * Created by Arunraj on 3/1/2018.
 */

@Dao
public interface EmployeeDao {

    @Insert
    long insert(Employee employee);

    @Update
    int update(Employee employee);

   // @Query("select * from " + Const.TABLE_EMP + " order by " + Const.KEY_EMP_ID + " desc")
  //  LiveData<List<Employee>> getAllEmployees();

    @Query("select " + Const.TABLE_EMP + "." + Const.KEY_EMP_ID + ", " + Const.TABLE_EMP + "." + Const.KEY_EMP_FIRST_NAME + ", "
            + Const.TABLE_EMP + "." + Const.KEY_EMP_LAST_NAME + ", " +Const.TABLE_EMP + "." +  Const.KEY_EMP_AADHAR_NUMBER + ", "
            + Const.TABLE_EMP + "." + Const.KEY_EMP_PHOTO+ ", " + Const.TABLE_EMP + "." + Const.KEY_EMP_BIRTHDAY + ", "
            + Const.TABLE_EMP + "." + Const.KEY_EMP_QUALIFICATION + ", " + Const.TABLE_EMP + "." + Const.KEY_DEPT_ID + ", "
            + Const.TABLE_EMP + "." + Const.KEY_EMP_ADDRESS+ ", " + Const.TABLE_EMP + "." + Const.KEY_EMP_DISTRICT + ", "
            + Const.TABLE_DEPT + "." +  Const.KEY_DEPT_NAME
            + " from " + Const.TABLE_EMP + " inner join " + Const.TABLE_DEPT + " on " +
            Const.TABLE_EMP + "." + Const.KEY_DEPT_ID + " = " +  Const.TABLE_DEPT + "." +
            Const.KEY_DEPT_ID + " order by " + Const.TABLE_EMP + "." + Const.KEY_EMP_ID + " desc")
    LiveData<List<Employees>> getAllEmployees();

}
