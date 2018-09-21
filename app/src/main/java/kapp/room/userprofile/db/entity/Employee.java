package kapp.room.userprofile.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import kapp.room.userprofile.db.Const;
import kapp.room.userprofile.db.converters.Converters;

import static android.arch.persistence.room.ForeignKey.CASCADE;


/**
 * Created by Arunraj on 3/1/2018.
 */

@Entity(
        //primaryKeys = {Const.KEY_EMP_AADHAR_NUMBER, Const.KEY_EMP_ADDRESS}, // composite primary key
        tableName = Const.TABLE_EMP, // mention the table if the class name and table name is not same. otherwise no need to use
        foreignKeys = @ForeignKey( // mention the tables relation ships
                entity = Dept.class,
                parentColumns = Const.KEY_DEPT_ID,
                childColumns = Const.KEY_DEPT_ID,
                onUpdate = CASCADE, // for child update
                onDelete = CASCADE // for delete child
        ),
        indices = { // indexing the table to increase table search efficiency
                @Index(
                        value = {
                                Const.KEY_EMP_FIRST_NAME,
                                Const.KEY_EMP_LAST_NAME
                        }
                ), @Index(
                        value = {Const.KEY_EMP_AADHAR_NUMBER}
                        , unique = true // refer to uniquely identify the aathar number
                )
        }
)
public class Employee {

    @PrimaryKey(autoGenerate = true) // mention the column as primary key is must
    @ColumnInfo(name = Const.KEY_EMP_ID, typeAffinity = ColumnInfo.INTEGER)
    /* mention the column name if the class data member name
       and table column is not same. Otherwise not required
    */
    public int empId;

    /*
     To specify individual column data type use typeAffinity property inside
     ColumnInfo annotation
    */

    @ColumnInfo(name = Const.KEY_EMP_FIRST_NAME, typeAffinity = ColumnInfo.TEXT)
    @NonNull
    public String empFirstName;

    @ColumnInfo(name = Const.KEY_EMP_LAST_NAME, typeAffinity = ColumnInfo.TEXT)
    public String empLastName;

    @ColumnInfo(name = Const.KEY_EMP_AADHAR_NUMBER, typeAffinity = ColumnInfo.TEXT)
    @NonNull
    public String aadharNumber;

    @ColumnInfo(name = Const.KEY_EMP_PHOTO,
            typeAffinity = ColumnInfo.BLOB)
    public byte[] empPhoto;

    @ColumnInfo(name = Const.KEY_EMP_BIRTHDAY)
    @TypeConverters(Converters.class)
    @NonNull
    public Date empBirthday;

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    @NonNull
    public String qualification;

    @ColumnInfo(name = Const.KEY_DEPT_ID, typeAffinity = ColumnInfo.INTEGER)
    public int deptId;

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    @NonNull
    public String address;


    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    public String district;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    @NonNull
    public String getEmpFirstName() {
        return empFirstName;
    }

    public void setEmpFirstName(@NonNull String empFirstName) {
        this.empFirstName = empFirstName;
    }

    public String getEmpLastName() {
        return empLastName;
    }

    public void setEmpLastName(String empLastName) {
        this.empLastName = empLastName;
    }

    @NonNull
    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(@NonNull String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public byte[] getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(byte[] empPhoto) {
        this.empPhoto = empPhoto;
    }

    @NonNull
    public Date getEmpBirthday() {
        return empBirthday;
    }

    public void setEmpBirthday(@NonNull Date empBirthday) {
        this.empBirthday = empBirthday;
    }

    @NonNull
    public String getQualification() {
        return qualification;
    }

    public void setQualification(@NonNull String qualification) {
        this.qualification = qualification;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

}
