package kapp.room.userprofile.db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import kapp.room.userprofile.db.AppDatabase;
import kapp.room.userprofile.db.dao.DeptDao;
import kapp.room.userprofile.db.dao.EmployeeDao;
import kapp.room.userprofile.db.entity.Dept;
import kapp.room.userprofile.db.entity.Employee;
import kapp.room.userprofile.db.entity.Employees;

/**
 * Created by Arunraj on 3/2/2018.
 */

public class DatabaseRepository {

    private DeptDao mDeptDao;
    private LiveData<List<Dept>> mAllDepts;

    private EmployeeDao mEmployeeDao;
    private LiveData<List<Employees>> mAllEmployees;

    private static final String TAG = "Repository";

    public DatabaseRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);

        // from dept
        mDeptDao = db.getDeptDAO();
        mAllDepts = mDeptDao.getAllDept();

        // from employee
        mEmployeeDao = db.getEmpDAO();
        mAllEmployees = mEmployeeDao.getAllEmployees();

        Log.d(TAG, "DatabaseRepository: ");
    }

    public boolean isDeptExists(String deptName) {
        try {
            Log.d(TAG, "isDeptExists: ");
            return new checkIsDeptExists(mDeptDao).execute(deptName).get().booleanValue();
        } catch (InterruptedException e) {
            Log.e(TAG, "isDeptExists: " + e.getMessage());
        } catch (ExecutionException e) {
            Log.e(TAG, "isDeptExists: " + e.getMessage());
        }
        return false;
    }

    public int deleteDept(Dept dept) {
        try {
            return new deleteDept(mDeptDao).execute(dept).get().intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static class deleteDept extends AsyncTask<Dept, Void, Integer> {

        private DeptDao mDeptDao;

        public deleteDept(DeptDao mDeptDao) {
            this.mDeptDao = mDeptDao;
        }

        @Override
        protected Integer doInBackground(Dept... depts) {
            return mDeptDao.deleteDept(depts[0]);
        }
    }

    private static class checkIsDeptExists extends AsyncTask<String, Void, Boolean>{

        private DeptDao mDeptDao;

        public checkIsDeptExists(DeptDao mDeptDao) {
            this.mDeptDao = mDeptDao;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
           // Dept dept = mDeptDao.isDeptExists(strings[0]);
            Log.d(TAG, " checkIsDeptExists doInBackground: ");
           // return dept == null ? false : true;
            return mDeptDao.isDeptExists(strings[0]);
        }
    }

    public LiveData<List<Dept>> getAllDept(){
        Log.d(TAG, "getAllDept: ");
        return mAllDepts;
    }

    public long insertDept(Dept dept){
        try {
            Log.d(TAG, "insertDept: ");
            return new insertDeptAsyncTask(mDeptDao).execute(dept).get().longValue();
        } catch (InterruptedException e) {
            Log.e(TAG, "insertDept: " + e.getMessage());
        } catch (ExecutionException e) {
            Log.e(TAG, "insertDept: " + e.getMessage());
        }
        return -1;
    }

    private static class insertDeptAsyncTask extends AsyncTask<Dept, Void, Long>{

        private DeptDao mDeptDao;

        public insertDeptAsyncTask(DeptDao mDeptDao) {
            this.mDeptDao = mDeptDao;
        }

        @Override
        protected Long doInBackground(Dept... params) {
            Log.d(TAG, "insertDeptAsyncTask doInBackground: ");
            return mDeptDao.insert(params[0]);
        }
    }

    public int updateDept(Dept dept){
        try {
            Log.d(TAG, "insertDept: ");
            return new updateDeptAsyncTask(mDeptDao).execute(dept).get().intValue();
        } catch (InterruptedException e) {
            Log.e(TAG, "insertDept: " + e.getMessage());
        } catch (ExecutionException e) {
            Log.e(TAG, "insertDept: " + e.getMessage());
        }
        return -1;
    }

    private static class updateDeptAsyncTask extends AsyncTask<Dept, Void, Integer>{

        private DeptDao mDeptDao;

        public updateDeptAsyncTask(DeptDao mDeptDao) {
            this.mDeptDao = mDeptDao;
        }

        @Override
        protected Integer doInBackground(Dept... params) {
            Log.d(TAG, "insertDeptAsyncTask doInBackground: ");
            return mDeptDao.update(params[0]);
        }
    }

    public LiveData<List<Employees>> getAllEmployees() {
        Log.d(TAG, "getAllEmployees: ");
        return mAllEmployees;
    }

    public long insertEmployee(Employee employee) {
        try {
            return new insertEmployeeAsyncTask(mEmployeeDao).execute(employee).get().longValue();
        } catch (InterruptedException e) {
            Log.e(TAG, "insertEmployee: ", e);
        } catch (ExecutionException e) {
            Log.e(TAG, "insertEmployee: ", e);
        }
        return -1;
    }

    private static class insertEmployeeAsyncTask extends AsyncTask<Employee, Void, Long> {

        private EmployeeDao mEmployeeDao;

        public insertEmployeeAsyncTask(EmployeeDao mEmployeeDao) {
            this.mEmployeeDao = mEmployeeDao;
        }

        @Override
        protected Long doInBackground(Employee... params) {
            return mEmployeeDao.insert(params[0]);
        }
    }
}
