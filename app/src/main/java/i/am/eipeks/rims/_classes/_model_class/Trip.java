package i.am.eipeks.rims._classes._model_class;


import java.util.Arrays;
import java.util.List;

public class Trip {

    private String dateAndTime;
    private String displacement;
    private String totalNumberOfPassengers;
    private String departure;
    private String calendarDate;
    private String calendarMonth;
    private String calendarYear;

    public Trip(String dateAndTime, String displacement,
                String departure, String calendarDate, String calendarMonth) {
        this.dateAndTime = dateAndTime;
        this.displacement = displacement;
        this.departure = departure;
        this.totalNumberOfPassengers = Integer.toString(0);
        this.calendarDate = calendarDate;
        this.calendarMonth = calendarMonth;
    }

    public Trip(String dateAndTime, String displacement, String totalNumberOfPassengers,
                String departure, String calendarDate, String calendarMonth, String calendarYear) {
        this.dateAndTime = dateAndTime;
        this.displacement = displacement;
        this.totalNumberOfPassengers = totalNumberOfPassengers;
        this.departure = departure;
        this.calendarDate = calendarDate;
        this.calendarMonth = calendarMonth;
        this.calendarYear = calendarYear;
    }

    public Trip(String tripInformation){
        List<String> tripArray = Arrays.asList(tripInformation.split("_"));
        this.dateAndTime = tripArray.get(0);
        this.displacement = tripArray.get(1);
        this.departure = tripArray.get(2);
        this.calendarDate = tripArray.get(3);
        this.calendarMonth = tripArray.get(4);
        this.calendarYear = tripArray.get(5);
        this.totalNumberOfPassengers = Integer.toString(0);
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getDisplacement() {
        return displacement;
    }

    public String getTotalNumberOfPassengers() {
        return totalNumberOfPassengers;
    }

    public String getDeparture() {
        return departure;
    }

    public void setTotalNumberOfPassengers(String totalNumberOfPassengers){
        this.totalNumberOfPassengers = totalNumberOfPassengers;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public String getCalendarMonth() {
        return calendarMonth;
    }

    public String getCalendarYear() {
        return calendarYear;
    }
}