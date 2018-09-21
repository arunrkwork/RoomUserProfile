package kapp.room.userprofile.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import kapp.room.userprofile.db.Const;


/**
 * Created by Arunraj on 3/1/2018.
 */

@Entity(
        tableName = Const.TABLE_DEPT,
        indices = {@Index(value = Const.KEY_DEPT_NAME, unique = true)}
)
public class Dept {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Const.KEY_DEPT_ID, typeAffinity = ColumnInfo.INTEGER)
    public int deptId;

    @ColumnInfo(name = Const.KEY_DEPT_NAME, typeAffinity = ColumnInfo.TEXT)
    @NonNull
    public String deptName;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @NonNull
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(@NonNull String deptName) {
        this.deptName = deptName;
    }
}
