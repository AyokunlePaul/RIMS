package i.am.eipeks.rims._database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

import i.am.eipeks.rims._classes.Driver;
import i.am.eipeks.rims._classes.MiniTripInfo;
import i.am.eipeks.rims._classes.Passenger;
import i.am.eipeks.rims._classes.SubTrip;
import i.am.eipeks.rims._classes.Trip;
import i.am.eipeks.rims._classes.Vehicle;


public class CentralDBHelper extends SQLiteOpenHelper {

    //Context

    //<editor-fold desc="Database-Wide Variables">
    private static final String DB_NAME = "central_database.db";
    private static final int DB_VERSION = 1;
    private static final String COLUMN_UUID = "COLUMN_UUID";
    private static final String ID = "_id";
    //</editor-fold>

    //<editor-fold desc="Table Names">
    private static final String VEHICLE_TABLE_NAME = "vehicleTable";
    private static final String DRIVERS_TABLE_NAME = "driversTable";
    private static final String PASSENGERS_TABLE_NAME = "passengersTable";
    private static final String TRIP_TABLE_NAME = "tripTable";
    //</editor-fold>

    //<editor-fold desc="AuthVehicle Table Information">
    private static final String COLUMN_VEHICLE_NAME = "vehicleName";
    private static final String COLUMN_VEHICLE_MAKE = "vehicleMake";
    private static final String COLUMN_VEHICLE_CAPACITY = "vehicleCapacity";
    private static final String COLUMN_VEHICLE_WEIGHT = "vehicleWeight";
    private static final String COLUMN_VEHICLE_ENGINE = "vehicleEngine";
    private static final String COLUMN_VEHICLE_RT_SSS = "vehicleRT_SSS";
    private static final String COLUMN_VEHICLE_REGISTRATION_NUMBER = "vehicleRegistrationNumber";
    //</editor-fold>

    //<editor-fold desc="Driver Table Information">
    private static final String COLUMN_DRIVERS_NAME = "driverName";
    private static final String COLUMN_DRIVERS_PHONE_NUMBER = "driverPhoneNumber";
    //</editor-fold>

    //<editor-fold desc="Passenger Table Information">
    private static final String COLUMN_PASSENGER_NAME = "passengersName";
    private static final String COLUMN_PASSENGER_PHONE = "passengersPhone";
    private static final String COLUMN_PASSENGER_SEX = "passengersSex";
    private static final String COLUMN_PASSENGER_ADDRESS = "passengersAddress";
    private static final String COLUMN_PASSENGER_NEXT_OF_KIN = "passengersKinName";
    private static final String COLUMN_PASSENGER_NEXT_OF_KIN_PHONE = "passengersKinPhoneNumber";
    private static final String COLUMN_PASSENGER_SEAT = "Seat";
    //</editor-fold>

    //<editor-fold desc="Trip Table Information">
    private static final String COLUMN_TRIP_DATE_AND_TIME = "dateAndTime";
    private static final String COLUMN_TRIP_DISPLACEMENT = "displacement";
    private static final String COLUMN_TRIP_DEPARTURE_INFORMATION = "departureInformation";
    private static final String COLUMN_TRIP_TOTAL_NUMBER_OF_PASSENGERS = "totalNumberOfPassengers";
    private static final String COLUMN_TRIP_CALENDAR_DATE = "calendarDate";
    private static final String COLUMN_TRIP_CALENDAR_MONTH = "calendarMonth";
    private static final String COLUMN_TRIP_CALENDAR_YEAR = "calendarYear";
    //</editor-fold>

    //<editor-fold desc="Create AuthVehicle Table Query">
    private static final String createVehicleTable = "CREATE TABLE " + VEHICLE_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " + COLUMN_VEHICLE_NAME + " TEXT, " +
            COLUMN_VEHICLE_MAKE + " TEXT, " + COLUMN_VEHICLE_CAPACITY + " INTEGER, " +
            COLUMN_VEHICLE_WEIGHT + " TEXT, " + COLUMN_VEHICLE_ENGINE + " TEXT, " + COLUMN_UUID + " TEXT, " +
            COLUMN_VEHICLE_RT_SSS + " TEXT,  " + COLUMN_VEHICLE_REGISTRATION_NUMBER + " TEXT);";
    //</editor-fold>
    //<editor-fold desc="Create Driver Table Query">
    private static final String createDriverTable = "CREATE TABLE " + DRIVERS_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " + COLUMN_DRIVERS_NAME + " TEXT, " + COLUMN_UUID + " TEXT, " +
            COLUMN_DRIVERS_PHONE_NUMBER + " TEXT);";
    //</editor-fold>
    //<editor-fold desc="Create Passenger Table Query">
    private static final String createPassengerTable = "CREATE TABLE " + PASSENGERS_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " + COLUMN_PASSENGER_NAME + " TEXT, " +
            COLUMN_PASSENGER_PHONE + " TEXT, " + COLUMN_PASSENGER_SEX + " TEXT, " +
            COLUMN_PASSENGER_ADDRESS + " TEXT, " + COLUMN_PASSENGER_NEXT_OF_KIN + " TEXT, " + COLUMN_UUID + " TEXT, " +
            COLUMN_PASSENGER_NEXT_OF_KIN_PHONE + " TEXT,  " + COLUMN_PASSENGER_SEAT + " TEXT);";
    //</editor-fold>
    //<editor-fold desc="Create Trip Table Query">
    private static final String createTripInformationTable = "CREATE TABLE " + TRIP_TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY, " + COLUMN_TRIP_DATE_AND_TIME + " TEXT, " +
            COLUMN_TRIP_DISPLACEMENT + " TEXT, " + COLUMN_TRIP_DEPARTURE_INFORMATION + " INTEGER, " +
            COLUMN_TRIP_CALENDAR_DATE + " TEXT, " + COLUMN_TRIP_CALENDAR_MONTH + " TEXT, " + COLUMN_TRIP_CALENDAR_YEAR + " TEXT, " +
            COLUMN_UUID + " TEXT, " + COLUMN_TRIP_TOTAL_NUMBER_OF_PASSENGERS + " TEXT);";
    //</editor-fold>

    public CentralDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //<editor-fold desc="Execute Statement">
        sqLiteDatabase.execSQL(createVehicleTable);
        sqLiteDatabase.execSQL(createDriverTable);
        sqLiteDatabase.execSQL(createPassengerTable);
        sqLiteDatabase.execSQL(createTripInformationTable);
        //</editor-fold>
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Vehicle getVehicle(String registrationNumber){
        Vehicle newVehicle = null;
        //<editor-fold desc="Cursor">
        Cursor cursor = getWritableDatabase().query(VEHICLE_TABLE_NAME,
                new String[]{COLUMN_VEHICLE_NAME, COLUMN_VEHICLE_MAKE, COLUMN_VEHICLE_CAPACITY,
                        COLUMN_VEHICLE_WEIGHT, COLUMN_VEHICLE_ENGINE, COLUMN_VEHICLE_RT_SSS},
                COLUMN_VEHICLE_REGISTRATION_NUMBER + " = ?",
                new String[]{registrationNumber}, null, null, null);
        //</editor-fold>
        //<editor-fold desc="Query">
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                String vehicleName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_NAME));
                String vehicleMake = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MAKE));
                String vehicleCapacity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_CAPACITY));
                String vehicleWeight = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_WEIGHT));
                String vehicleEngine = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_ENGINE));
                String vehicleRTSS = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_RT_SSS));

                newVehicle = new Vehicle(vehicleName, vehicleMake, vehicleCapacity,
                        vehicleWeight, vehicleEngine, vehicleRTSS, registrationNumber);
                cursor.close();
            }
        }
        //</editor-fold>
        return newVehicle;
    }

    public Vehicle getVehicle(String registrationNumber, String UUID){
        Vehicle newVehicle = null;
        //<editor-fold desc="Cursor">
        Cursor cursor = getWritableDatabase().query(VEHICLE_TABLE_NAME,
                new String[]{COLUMN_VEHICLE_NAME, COLUMN_VEHICLE_MAKE, COLUMN_VEHICLE_CAPACITY,
                        COLUMN_VEHICLE_WEIGHT, COLUMN_VEHICLE_ENGINE, COLUMN_VEHICLE_RT_SSS, COLUMN_VEHICLE_REGISTRATION_NUMBER},
                COLUMN_UUID + " = ?", new String[]{UUID}, null, null, null);
        //</editor-fold>
        //<editor-fold desc="Query">
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                String vehicleName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_NAME));
                String vehicleMake = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_MAKE));
                String vehicleCapacity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_CAPACITY));
                String vehicleWeight = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_WEIGHT));
                String vehicleEngine = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_ENGINE));
                String vehicleRTSS = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_RT_SSS));
                String vehicleRegistrationNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VEHICLE_REGISTRATION_NUMBER));

                newVehicle = new Vehicle(vehicleName, vehicleMake, vehicleCapacity,
                        vehicleWeight, vehicleEngine, vehicleRTSS, vehicleRegistrationNumber);
                cursor.close();
            }
        }
        //</editor-fold>
        return newVehicle;
    }

    public Driver getDriver(String UUID){
        Driver driver = null;
        //<editor-fold desc="Cursor">
        Cursor driverCursor = getWritableDatabase().query(
                DRIVERS_TABLE_NAME, new String[]{COLUMN_DRIVERS_NAME, COLUMN_DRIVERS_PHONE_NUMBER},
                COLUMN_UUID + " = ?", new String[]{UUID}, null, null, null);
        //</editor-fold>
        //<editor-fold desc="Query">
        if (driverCursor != null){
            if (driverCursor.getCount() > 0){
                driverCursor.moveToFirst();
                try {
                    String driverName = driverCursor.getString(driverCursor.getColumnIndexOrThrow(COLUMN_DRIVERS_NAME));
                    String driverPhoneNumber = driverCursor.getString(driverCursor.getColumnIndexOrThrow(COLUMN_DRIVERS_PHONE_NUMBER));

                    driver = new Driver(driverName, driverPhoneNumber);
                }catch (SQLiteException e){e.printStackTrace();}
                driverCursor.close();
            }
        }
        //</editor-fold>
        return driver;
    }

    public Trip getTrip(String UUID){
        Trip currentTrip = null;
        //<editor-fold desc="Cursor">
        Cursor cursor = getWritableDatabase().query(
                TRIP_TABLE_NAME, new String[]{COLUMN_TRIP_DATE_AND_TIME, COLUMN_TRIP_DEPARTURE_INFORMATION,
                COLUMN_TRIP_DISPLACEMENT, COLUMN_TRIP_TOTAL_NUMBER_OF_PASSENGERS,
                        COLUMN_TRIP_CALENDAR_MONTH, COLUMN_TRIP_CALENDAR_DATE, COLUMN_TRIP_CALENDAR_YEAR}, COLUMN_UUID + " = ?",
                new String[]{UUID}, null, null, null);
        //</editor-fold>
        //<editor-fold desc="Query">
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                String dateAndTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DATE_AND_TIME));
                String departure = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DEPARTURE_INFORMATION));
                String displacement = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DISPLACEMENT));
                String totalPassengers = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_TOTAL_NUMBER_OF_PASSENGERS));
                String calendarDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_CALENDAR_DATE));
                String calendarMonth = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_CALENDAR_MONTH));
                String calendarYear = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_CALENDAR_YEAR));

                currentTrip = new Trip(dateAndTime, displacement, totalPassengers,departure, calendarDate, calendarMonth, calendarYear);

                cursor.close();
            }
        }
        //</editor-fold>
        return currentTrip;
    }

    public ArrayList<MiniTripInfo> getMiniTripInfos(){
        ArrayList<MiniTripInfo> miniTripInfos = new ArrayList<>();
        //<editor-fold desc="Cursor">
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TRIP_TABLE_NAME, null);
        //</editor-fold>
        //<editor-fold desc="Query">
        if (cursor != null){
            if (cursor.getCount() > 0 && cursor.moveToFirst()){
                do {
                    try {
                        String dateAndTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DATE_AND_TIME));
                        String departure = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DEPARTURE_INFORMATION));
                        String displacement = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_DISPLACEMENT));
                        String totalNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_TOTAL_NUMBER_OF_PASSENGERS));
                        String uuid = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UUID));
                        String calendarDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_CALENDAR_DATE));
                        String calendarMonth = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_CALENDAR_MONTH));
                        String calendarYear = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRIP_CALENDAR_YEAR));

                        Trip trip = new Trip(dateAndTime, displacement, totalNumber, departure, calendarDate, calendarMonth, calendarYear);
                        Vehicle vehicle = getVehicle("1234567890", uuid);
                        Driver driver = getDriver(uuid);

                        MiniTripInfo miniTripInfo = new MiniTripInfo(vehicle, new SubTrip(trip, driver));
                        miniTripInfo.setUuid(uuid);
                        miniTripInfos.add(miniTripInfo);
                    } catch (SQLiteException e){e.printStackTrace();}
                } while (cursor.moveToNext());

                cursor.close();
            }
        }
        //</editor-fold>
        return miniTripInfos;
    }

    public ArrayList<Passenger> getPassengers(String UUID){
        ArrayList<Passenger> passengers = new ArrayList<>();
        //<editor-fold desc="Cursor">
        Cursor cursor = getWritableDatabase().query(PASSENGERS_TABLE_NAME,
                new String[]{COLUMN_PASSENGER_NAME, COLUMN_PASSENGER_PHONE, COLUMN_PASSENGER_ADDRESS, COLUMN_PASSENGER_SEX,
                            COLUMN_PASSENGER_SEAT, COLUMN_PASSENGER_NEXT_OF_KIN, COLUMN_PASSENGER_NEXT_OF_KIN_PHONE},
                COLUMN_UUID + " = ?", new String[]{UUID}, null, null, null);
        //</editor-fold>
        //<editor-fold desc="Query">
        if (cursor != null){
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                try{
                    do {
                        String passengerName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_NAME));
                        String passengerPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_PHONE));
                        String passengerAddress = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_ADDRESS));
                        String passengerSex = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_SEX));
                        String passengerNextOfKin = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_NEXT_OF_KIN));
                        String passengerNextOfKinPhone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_NEXT_OF_KIN_PHONE));
                        String passengerSeat = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSENGER_SEAT));

                        passengers.add(new Passenger(passengerName, passengerPhone, passengerSex,
                                passengerAddress, passengerNextOfKin, passengerSeat,passengerNextOfKinPhone));

                    } while (cursor.moveToNext());

                }catch (SQLiteException e){e.printStackTrace();}
                cursor.close();
            }
        }
        //</editor-fold>
        return passengers;
    }

    public void addVehicle(Vehicle vehicle, String UUID){
        //<editor-fold desc="ContentValues">
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VEHICLE_NAME, vehicle.getVehicleName());
        contentValues.put(COLUMN_VEHICLE_MAKE, vehicle.getVehicleMake());
        contentValues.put(COLUMN_VEHICLE_ENGINE, vehicle.getVehicleEngine());
        contentValues.put(COLUMN_VEHICLE_CAPACITY, vehicle.getVehicleCapacity());
        contentValues.put(COLUMN_VEHICLE_REGISTRATION_NUMBER, vehicle.getRegistrationNumber());
        contentValues.put(COLUMN_VEHICLE_RT_SSS, vehicle.getVehicleRTSSS());
        contentValues.put(COLUMN_VEHICLE_WEIGHT, vehicle.getVehicleWeight());
        contentValues.put(COLUMN_UUID, UUID);
        //</editor-fold>
        getWritableDatabase().insert(VEHICLE_TABLE_NAME, null, contentValues);
    }

    public void addPassenger(Passenger passenger, String UUID){
        //<editor-fold desc="ContentValues">
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSENGER_NAME, passenger.getPassengerName());
        contentValues.put(COLUMN_PASSENGER_ADDRESS, passenger.getPassengerAddress());
        contentValues.put(COLUMN_PASSENGER_PHONE, passenger.getPassengerPhone());
        contentValues.put(COLUMN_PASSENGER_SEX, passenger.getPassengerSex());
        contentValues.put(COLUMN_PASSENGER_SEAT, passenger.getSeatNumber());
        contentValues.put(COLUMN_PASSENGER_NEXT_OF_KIN, passenger.getNextOfKin());
        contentValues.put(COLUMN_PASSENGER_NEXT_OF_KIN_PHONE, passenger.getNextOfKinPhone());
        contentValues.put(COLUMN_UUID, UUID);
        //</editor-fold>
        getWritableDatabase().insert(PASSENGERS_TABLE_NAME, null, contentValues);
    }

    public void addTrip(Trip trip, String UUID){
        //<editor-fold desc="ContentValues">
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRIP_DATE_AND_TIME, trip.getDateAndTime());
        contentValues.put(COLUMN_TRIP_DEPARTURE_INFORMATION, trip.getDeparture());
        contentValues.put(COLUMN_TRIP_DISPLACEMENT, trip.getDisplacement());
        contentValues.put(COLUMN_TRIP_TOTAL_NUMBER_OF_PASSENGERS, trip.getTotalNumberOfPassengers());
        contentValues.put(COLUMN_UUID, UUID);
        contentValues.put(COLUMN_TRIP_CALENDAR_DATE, trip.getCalendarDate());
        contentValues.put(COLUMN_TRIP_CALENDAR_MONTH, trip.getCalendarMonth());
        contentValues.put(COLUMN_TRIP_CALENDAR_YEAR, trip.getCalendarYear());
        //</editor-fold>
        getWritableDatabase().insert(TRIP_TABLE_NAME, null, contentValues);
    }

    public void addDriver(Driver driver, String UUID){
        //<editor-fold desc="ContentValues">
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DRIVERS_NAME, driver.getDriverName());
        contentValues.put(COLUMN_DRIVERS_PHONE_NUMBER, driver.getDriverPhone());
        contentValues.put(COLUMN_UUID, UUID);
        //</editor-fold>
        getWritableDatabase().insert(DRIVERS_TABLE_NAME, null, contentValues);
    }

    public void deletePassengers(String UUID){

        getWritableDatabase().delete(PASSENGERS_TABLE_NAME, COLUMN_UUID + " = ?", new String[]{UUID});

    }

    public void deleteTrip(String UUID){

        getWritableDatabase().delete(TRIP_TABLE_NAME, COLUMN_UUID + " = ?", new String[]{UUID});

    }

    public void deleteDriver(String UUID){

        getWritableDatabase().delete(DRIVERS_TABLE_NAME, COLUMN_UUID + " = ?", new String[]{UUID});

    }

    public void deleteVehicle(String UUID){

        getWritableDatabase().delete(DRIVERS_TABLE_NAME, COLUMN_UUID + " = ?", new String[]{UUID});

    }

    public void modifyTrip(String totalNumberOfPassengers, String UUID){
        //<editor-fold desc="ContentValues">
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRIP_TOTAL_NUMBER_OF_PASSENGERS, totalNumberOfPassengers);
        //</editor-fold>
        getWritableDatabase().update(TRIP_TABLE_NAME, contentValues, COLUMN_UUID + " = ?", new String[]{UUID});
    }
}
