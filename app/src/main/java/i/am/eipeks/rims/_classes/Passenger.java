package i.am.eipeks.rims._classes;


public class Passenger {

    private String passengerName, passengerPhone, passengerSex,
            passengerAddress, nextOfKin, seatNumber, nextOfKinPhone;

    public Passenger(String passengerName, String passengerPhone, String passengerSex,
                     String passengerAddress, String nextOfKin, String seatNumber, String nextOfKinPhone) {
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.passengerSex = passengerSex;
        this.passengerAddress = passengerAddress;
        this.nextOfKin = nextOfKin;
        this.seatNumber = seatNumber;
        this.nextOfKinPhone = nextOfKinPhone;
    }

    public String getPassengerName() {
        return passengerName.trim();
    }

    public String getPassengerPhone() {
        return passengerPhone.trim();
    }

    public String getPassengerSex() {
        return passengerSex.trim();
    }

    public String getPassengerAddress() {
        return passengerAddress.trim();
    }

    public String getNextOfKin() {
        return nextOfKin.trim();
    }

    public String getSeatNumber() {
        return seatNumber.trim();
    }

    public String getNextOfKinPhone() {
        return nextOfKinPhone.trim();
    }
}
