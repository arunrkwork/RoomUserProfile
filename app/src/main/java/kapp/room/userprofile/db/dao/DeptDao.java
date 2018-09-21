package kapp.room.userprofile.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import kapp.room.userprofile.db.Const;
import kapp.room.userprofile.db.entity.Dept;

import static android.arch.persistence.room.ForeignKey.CASCADE;


/**
 * Created by Arunraj on 3/1/2018.
 */

@Dao
public interface DeptDao {

    @Insert/*(onConflict = OnConflictStrategy.REPLACE)*/
    long insert(Dept dept);

    @Update
    int update(Dept dept);

    @Query("select * from " + Const.TABLE_DEPT + " order by " + Const.KEY_DEPT_ID + " desc")
    LiveData<List<Dept>> getAllDept();

    @Query("select * from " + Const.TABLE_DEPT + " where " + Const.KEY_DEPT_NAME + " =:deptName")
    boolean isDeptExists(String deptName);

    @Query("delete from " + Const.TABLE_DEPT)
    void deleteAll();

    @Delete
    int deleteDept(Dept dept);
}
