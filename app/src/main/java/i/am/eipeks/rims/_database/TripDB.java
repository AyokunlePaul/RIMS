package i.am.eipeks.rims._database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import i.am.eipeks.rims._classes._model_class.TripTemporary;


public class TripDB extends SQLiteOpenHelper {

    //Context
    private Context context;

    //Database information
    private static final String DB_NAME = "tripInformation.db";
    private static final int DB_VERSION = 1;

    //User's information;
    private static final String IDENTIFICATION_NUMBER = "IdentificationNumber";
    private static final String PASSWORD = "Password";

    //TripTemporary information table
    public static final String TABLE_NAME = "tripTable";
    public static final String ID = "_id";
    public static final String DISPLACEMENT = "Displacement";
    public static final String TOTAL_NUMBER_OF_PASSENGERS = "numberOfPassengers";

    //Vehicle information
    public static final String COLUMN_VEHICLE_NAME = "vehicleName";
    public static final String COLUMN_VEHICLE_MAKE = "vehicleMake";
    public static final String COLUMN_CAPACITY = "Capacity";
    public static final String COLUMN_WEIGHT = "Weight";
    public static final String COLUMN_ENGINE = "Engine";
    public static final String COLUMN_RT_SSS = "RT_SSS";

    //Passenger's information
    public static final String COLUMN_PASSENGER_NAME = "passengerName";
    public static final String COLUMN_PASSENGER_PHONE = "passengerPhone";
    public static final String COLUMN_PASSENGER_SEX = "Sex";
    public static final String COLUMN_PASSENGER_ADDRESS = "Address";
    public static final String COLUMN_PASSENGER_NEXT_OF_KIN = "Kin";
    public static final String COLUMN_PASSENGER_NEXT_OF_KIN_PHONE = "KinPhone";
    public static final String COLUMN_PASSENGER_SEAT_NUMBER = "seatNumber";

    //Driver information;
    public static final String DRIVER_S_NAME = "nameOfDriver";
    public static final String DRIVER_S_PHONE = "phoneOfDriver";

    //Time and date
    public static final String DATE = "date";
    public static final String TIME = "time";


    private static final String createTripTable = "CREATE TABLE " + TABLE_NAME +
            " (" + ID + " INTEGER PRIMARY KEY, " +

            IDENTIFICATION_NUMBER + " TEXT, " + PASSWORD + " TEXT, " +

            COLUMN_VEHICLE_NAME + " TEXT, " +
            COLUMN_VEHICLE_MAKE + " TEXT, " + COLUMN_CAPACITY + " INTEGER, " +
            COLUMN_WEIGHT + " TEXT, " + COLUMN_ENGINE + " TEXT, " +
            COLUMN_RT_SSS + " TEXT,  " +

            COLUMN_PASSENGER_NAME + " TEXT, " +
            COLUMN_PASSENGER_PHONE + " TEXT, " + COLUMN_PASSENGER_SEX + " TEXT, " +
            COLUMN_PASSENGER_ADDRESS + " TEXT, " + COLUMN_PASSENGER_NEXT_OF_KIN + " TEXT, " +
            COLUMN_PASSENGER_NEXT_OF_KIN_PHONE + " TEXT, " + COLUMN_PASSENGER_SEAT_NUMBER + " TEXT, " +

            TOTAL_NUMBER_OF_PASSENGERS + " TEXT, " + DISPLACEMENT + " TEXT, " +

            DRIVER_S_NAME + " TEXT, " + DRIVER_S_PHONE + " TEXT, " +
            DATE + " TEXT, " + TIME + " TEXT);";


    public TripDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTripTable);
        Toast.makeText(context, "TripTemporary table created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addTrip(TripTemporary tripTemporary){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VEHICLE_NAME, tripTemporary.getVehicle().getVehicleName());
        contentValues.put(COLUMN_VEHICLE_MAKE, tripTemporary.getVehicle().getVehicleMake());
        contentValues.put(COLUMN_CAPACITY, tripTemporary.getVehicle().getVehicleCapacity());
        contentValues.put(COLUMN_WEIGHT, tripTemporary.getVehicle().getVehicleWeight());
        contentValues.put(COLUMN_ENGINE, tripTemporary.getVehicle().getVehicleEngine());
        contentValues.put(COLUMN_RT_SSS, tripTemporary.getVehicle().getVehicleRTSSS());

        contentValues.put(COLUMN_PASSENGER_NAME, tripTemporary.getPassenger().getPassengerName());
        contentValues.put(COLUMN_PASSENGER_PHONE, tripTemporary.getPassenger().getPassengerPhone());
        contentValues.put(COLUMN_PASSENGER_ADDRESS, tripTemporary.getPassenger().getPassengerAddress());
        contentValues.put(COLUMN_PASSENGER_SEX, tripTemporary.getPassenger().getPassengerSex());
        contentValues.put(COLUMN_PASSENGER_NEXT_OF_KIN, tripTemporary.getPassenger().getNextOfKin());
        contentValues.put(COLUMN_PASSENGER_NEXT_OF_KIN_PHONE, tripTemporary.getPassenger().getNextOfKinPhone());
        contentValues.put(COLUMN_PASSENGER_SEAT_NUMBER, tripTemporary.getPassenger().getSeatNumber());

        contentValues.put(TOTAL_NUMBER_OF_PASSENGERS, tripTemporary.getNumberOfPassengers());

        contentValues.put(DISPLACEMENT, tripTemporary.getDisplacement());

        contentValues.put(DRIVER_S_NAME, tripTemporary.getDriver().getDriverName());
        contentValues.put(DRIVER_S_PHONE, tripTemporary.getDriver().getDriverPhone());

        contentValues.put(DATE, tripTemporary.getDateAndTime());
        this.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getTrip(){
        return this.getWritableDatabase().query(TABLE_NAME,
                new String[]{COLUMN_VEHICLE_NAME, COLUMN_VEHICLE_MAKE, COLUMN_CAPACITY, COLUMN_WEIGHT, COLUMN_ENGINE,COLUMN_RT_SSS,
                        COLUMN_PASSENGER_NAME, COLUMN_PASSENGER_PHONE, COLUMN_PASSENGER_ADDRESS, COLUMN_PASSENGER_SEX,
                        COLUMN_PASSENGER_NEXT_OF_KIN, COLUMN_PASSENGER_NEXT_OF_KIN_PHONE, COLUMN_PASSENGER_SEAT_NUMBER,
                        TOTAL_NUMBER_OF_PASSENGERS, DISPLACEMENT, DRIVER_S_NAME, DRIVER_S_PHONE, TIME, DATE},
                null, null, null, null, null);
    }

}
