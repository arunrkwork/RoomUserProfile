package kapp.room.userprofile.db.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import kapp.room.userprofile.db.entity.Dept;
import kapp.room.userprofile.db.entity.Employee;
import kapp.room.userprofile.db.entity.Employees;
import kapp.room.userprofile.db.repository.DatabaseRepository;

/**
 * Created by Arunraj on 3/2/2018.
 */

public class UIViewModel extends AndroidViewModel {

    private DatabaseRepository mDatabaseRepository;
    private LiveData<List<Dept>> mAllDepts;
    private LiveData<List<Employees>> mAllEmployees;
    private static final String TAG = "ViewModel";

    public UIViewModel(Application application) {
        super(application);
        mDatabaseRepository = new DatabaseRepository(application);
        mAllDepts = mDatabaseRepository.getAllDept();
        mAllEmployees = mDatabaseRepository.getAllEmployees();
        Log.d(TAG, "UIViewModel: ");
    }

    public boolean isDeptExists(String deptName) {
        Log.d(TAG, "isDeptExists: ");
        return mDatabaseRepository.isDeptExists(deptName);
    }
    public LiveData<List<Dept>> getAllDept(){
        Log.d(TAG, "getAllDept: ");
        return mAllDepts;
    }

    public long insertDept(Dept dept){
        Log.d(TAG, "insertDept: ");
        return mDatabaseRepository.insertDept(dept);
    }

    public int updateDept(Dept dept){
        Log.d(TAG, "updateDept: ");
        return mDatabaseRepository.updateDept(dept);
    }

    public LiveData<List<Employees>> getAllEmployees() {
        Log.d(TAG, "getAllEmployees: ");
        return mAllEmployees;
    }

    public long insertEmployee(Employee employee){
        Log.d(TAG, "insertEmployee: ");
        return mDatabaseRepository.insertEmployee(employee);
    }


    public int deleteDept(Dept dept) {
        return mDatabaseRepository.deleteDept(dept);
    }
}
