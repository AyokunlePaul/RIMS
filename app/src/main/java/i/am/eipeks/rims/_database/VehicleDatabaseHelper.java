package i.am.eipeks.rims._database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import i.am.eipeks.rims._classes.Vehicle;


public class VehicleDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    //Database Information
    private static final String DB_NAME = "vehicleInfo.db";
    private static final int DB_VERSION = 1;

    //Owner's information
    public static final String COLUMN_IDENTIFICATION_NUMBER = "Identification";
    public static final String COLUMN_PASSWORD = "Password";

    //AuthVehicle information table columns
    private static final String VEHICLE_TABLE_NAME = "Vehicles";
    private static final String ID = "_id";
    private static final String COLUMN_VEHICLE_NAME = "Name";
    private static final String COLUMN_VEHICLE_MAKE = "Make";
    private static final String COLUMN_CAPACITY = "Capacity";
    private static final String COLUMN_WEIGHT = "Weight";
    private static final String COLUMN_ENGINE = "Engine";
    private static final String COLUMN_RT_SSS = "RT_SSS";
    private static final String COLUMN_REGISTRATION_NUMBER = "registrationNumber";

    //Create AuthVehicle Database
    private static final String createVehicleDatabaseTable = "CREATE TABLE " + VEHICLE_TABLE_NAME +
            " (" + ID + " INTEGER PRIMARY KEY, " + COLUMN_VEHICLE_NAME + " TEXT, " +
            COLUMN_VEHICLE_MAKE + " TEXT, " + COLUMN_CAPACITY + " INTEGER, " +
            COLUMN_WEIGHT + " TEXT, " + COLUMN_ENGINE + " TEXT, " +
            COLUMN_RT_SSS + " TEXT,  " + COLUMN_REGISTRATION_NUMBER + " TEXT);";


    public VehicleDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createVehicleDatabaseTable);
//        Toast.makeText(context, "AuthVehicle table created", Toast.LENGTH_SHORT).show();

        addDefaultValue(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void addDefaultValue(SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COLUMN_VEHICLE_NAME, "AGOFURE MOTORS LIMITED");
        contentValues1.put(COLUMN_VEHICLE_MAKE, "VDL");
        contentValues1.put(COLUMN_CAPACITY, 18);
        contentValues1.put(COLUMN_WEIGHT, "19.5T");
        contentValues1.put(COLUMN_ENGINE, "FMD2-01");
        contentValues1.put(COLUMN_RT_SSS, "#2013456");
        contentValues1.put(COLUMN_REGISTRATION_NUMBER, "AAAAAA-BBB");

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(COLUMN_VEHICLE_NAME, "Ekene Dili Chukwu");
        contentValues2.put(COLUMN_VEHICLE_MAKE, "Toyota Hiace");
        contentValues2.put(COLUMN_CAPACITY, 12);
        contentValues2.put(COLUMN_WEIGHT, "12.5T");
        contentValues2.put(COLUMN_ENGINE, "FMD2-01");
        contentValues2.put(COLUMN_RT_SSS, "#2013456");
        contentValues2.put(COLUMN_REGISTRATION_NUMBER, "ABCDEF");

        sqLiteDatabase.insert(VEHICLE_TABLE_NAME, null, contentValues1);
        sqLiteDatabase.insert(VEHICLE_TABLE_NAME, null, contentValues2);
//        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
    }

    public Vehicle getVehicle(String registrationNumber){

        Vehicle newVehicle = null;

        Cursor cursor = getWritableDatabase().query(VEHICLE_TABLE_NAME,
                new String[]{COLUMN_VEHICLE_NAME, COLUMN_VEHICLE_MAKE, COLUMN_CAPACITY,
                        COLUMN_WEIGHT, COLUMN_ENGINE, COLUMN_RT_SSS},
                COLUMN_REGISTRATION_NUMBER + " = ?",
                new String[]{registrationNumber}, null, null, null);

//        Toast.makeText(context, String.valueOf(cursor.), Toast.LENGTH_SHORT).show();

        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                String vehicleName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_NAME));
                String vehicleMake = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MAKE));
                String vehicleCapacity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAPACITY));
                String vehicleWeight = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT));
                String vehicleEngine = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGINE));
                String vehicleRTSS = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RT_SSS));

                newVehicle = new Vehicle(vehicleName, vehicleMake, vehicleCapacity,
                        vehicleWeight, vehicleEngine, vehicleRTSS, registrationNumber);
                cursor.close();
            }
        }
        return newVehicle;
    }

}
