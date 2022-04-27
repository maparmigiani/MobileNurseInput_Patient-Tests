package patient;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Patient.class}, version = 1)
public abstract class PatientDataBase extends RoomDatabase {
    private static PatientDataBase instance;

    public abstract PatientDao patientDao();

    public static synchronized PatientDataBase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    PatientDataBase.class, "patient_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;

    }

    private static  Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void,Void>
    {
        private PatientDao patientDao;
        private PopulateDbAsyncTask(PatientDataBase db){
            patientDao = db.patientDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            patientDao.insert(new Patient("Diego", "Hernandez", "UCI",1,1));
            patientDao.insert(new Patient("Diego2", "Hernandez", "UCI",1,2));
            patientDao.insert(new Patient("Diego3", "Hernandez", "UCI",1,3));
            patientDao.insert(new Patient("Diego4", "Hernandez", "UCI",1,4));
            patientDao.insert(new Patient("Diego5", "Hernandez", "UCI",1,5));
            patientDao.insert(new Patient("Diego6", "Hernandez", "UCI",1,6));
            patientDao.insert(new Patient("Diego7", "Hernandez", "UCI",1,7));
            patientDao.insert(new Patient("Diego8", "Hernandez", "UCI",1,8));
            patientDao.insert(new Patient("Diego9", "Hernandez", "UCI",1,9));
            return null;
        }
    }
}
