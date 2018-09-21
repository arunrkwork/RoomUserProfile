package kapp.room.userprofile.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import kapp.room.userprofile.db.dao.DeptDao;
import kapp.room.userprofile.db.dao.EmployeeDao;
import kapp.room.userprofile.db.entity.Dept;
import kapp.room.userprofile.db.entity.Employee;

/**
 * Created by Arunraj on 3/1/2018.
 */

@Database(entities = {Employee.class, Dept.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "office";
    private static volatile AppDatabase instance;

    public abstract DeptDao getDeptDAO();

    public abstract EmployeeDao getEmpDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                instance = create(context);
            }
        }
        return instance;
    }

    static final Migration FROM_1_TO_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("alter table " + Const.TABLE_EMP + " add column " + Const.KEY_EMP_DISTRICT + " text");
        }
    };

    private static AppDatabase create(Context context) {

        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME)
                .addMigrations(FROM_1_TO_2)
                //.fallbackToDestructiveMigration() // don't use because it will erase all data
                // when upgrade version
                .build();
    }

}
